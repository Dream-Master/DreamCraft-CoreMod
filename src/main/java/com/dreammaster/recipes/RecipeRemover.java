package com.dreammaster.recipes;

import static com.dreammaster.scripts.IScriptLoader.missing;
import static com.dreammaster.scripts.IScriptLoader.wildcard;
import static gregtech.api.enums.Mods.ForbiddenMagic;
import static gregtech.api.util.GT_ModHandler.getModItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.dreammaster.main.MainRegistry;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

public class RecipeRemover {

    private static boolean bufferingRecipes = true;

    @SuppressWarnings("unchecked")
    private static final ArrayList<IRecipe> tList = (ArrayList<IRecipe>) CraftingManager.getInstance().getRecipeList();

    private static final HashMap<GT_Utility.ItemId, List<Function<IRecipe, Boolean>>> bufferMap = new HashMap<>();

    private static void addToBuffer(HashSet<GT_Utility.ItemId> outputs, Function<IRecipe, Boolean> whenToRemove) {
        for (GT_Utility.ItemId output : outputs) {
            bufferMap.computeIfAbsent(output, o -> new ArrayList<>()).add(whenToRemove);
        }
        if (!bufferingRecipes) stopBuffering();
    }

    private static void stopBuffering() {
        int i = tList.size();
        tList.removeIf(r -> {
            ItemStack rCopy = r.getRecipeOutput();
            if (rCopy == null) {
                return false;
            }
            if (rCopy.getItem() == null) {
                MainRegistry.Logger.warn("Someone is adding recipes with null items!");
                for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
                    MainRegistry.Logger.warn(element.toString());
                }
                return true;
            }
            if (rCopy.stackTagCompound != null) {
                rCopy = rCopy.copy();
                rCopy.stackTagCompound = null;
            }
            GT_Utility.ItemId key = GT_Utility.ItemId.createNoCopy(rCopy);
            rCopy = rCopy.copy();
            Items.feather.setDamage(rCopy, wildcard);
            GT_Utility.ItemId keyWildcard = GT_Utility.ItemId.createNoCopy(rCopy);
            List<Function<IRecipe, Boolean>> listWhenToRemove = bufferMap.get(key);
            if (listWhenToRemove == null) listWhenToRemove = bufferMap.get(keyWildcard);
            if (listWhenToRemove == null) return false;
            for (Function<IRecipe, Boolean> whenToRemove : listWhenToRemove) {
                if (whenToRemove.apply(r)) return true;
            }
            return false;
        });
        MainRegistry.Logger.info("Removed " + (i - tList.size()) + " recipes!");
        bufferMap.clear();
    }

    private static HashSet<GT_Utility.ItemId> getItemsHashed(Object item, boolean includeWildcardVariants) {
        HashSet<GT_Utility.ItemId> hashedItems = new HashSet<>();
        if (item instanceof ItemStack) {
            ItemStack iCopy = ((ItemStack) item).copy();
            iCopy.stackTagCompound = null;
            hashedItems.add(GT_Utility.ItemId.createNoCopy(iCopy));
            if (includeWildcardVariants) {
                iCopy = iCopy.copy();
                Items.feather.setDamage(iCopy, wildcard);
                hashedItems.add(GT_Utility.ItemId.createNoCopy(iCopy));
            }
        } else if (item instanceof String) {
            for (ItemStack stack : OreDictionary.getOres((String) item)) {
                hashedItems.add(GT_Utility.ItemId.createNoCopy(stack));
                if (includeWildcardVariants) {
                    stack = stack.copy();
                    Items.feather.setDamage(stack, wildcard);
                    hashedItems.add(GT_Utility.ItemId.createNoCopy(stack));
                }
            }
        } else if (item instanceof ArrayList) {
            // noinspection unchecked
            for (ItemStack stack : (ArrayList<ItemStack>) item) {
                ItemStack iCopy = stack.copy();
                iCopy.stackTagCompound = null;
                hashedItems.add(GT_Utility.ItemId.createNoCopy(iCopy));
                if (includeWildcardVariants) {
                    iCopy = iCopy.copy();
                    Items.feather.setDamage(iCopy, wildcard);
                    hashedItems.add(GT_Utility.ItemId.createNoCopy(iCopy));
                }
            }
        } else throw new IllegalArgumentException("Invalid input");
        return hashedItems;
    }

    /**
     * Removes only shapeless recipes by output and inputs, supports OreDictionary tags
     *
     * @author kuba6000
     */
    static void removeRecipeShapelessDelayed(Object aOutput, Object... aRecipe) {
        ArrayList<Object> aRecipeList = new ArrayList<>(Arrays.asList(aRecipe));
        addToBuffer(getItemsHashed(aOutput, false), r -> {
            if (!(r instanceof ShapelessOreRecipe) && !(r instanceof ShapelessRecipes)) return false;
            if (aRecipeList.isEmpty()) return true;
            @SuppressWarnings("unchecked")
            ArrayList<Object> recipe = (ArrayList<Object>) aRecipeList.clone();
            List<?> rInputs = (r instanceof ShapelessOreRecipe ? ((ShapelessOreRecipe) r).getInput()
                    : ((ShapelessRecipes) r).recipeItems);
            for (Object rInput : rInputs) {
                HashSet<GT_Utility.ItemId> rInputHashed;
                HashSet<GT_Utility.ItemId> rInputHashedNoWildcard;
                try {
                    rInputHashed = getItemsHashed(rInput, true);
                    rInputHashedNoWildcard = getItemsHashed(rInput, false);
                } catch (Exception ex) {
                    return false;
                }
                boolean found = false;
                for (Iterator<Object> iterator = recipe.iterator(); iterator.hasNext();) {
                    Object o = iterator.next();
                    for (GT_Utility.ItemId id : getItemsHashed(o, false)) {
                        if (rInputHashed.contains(id)) {
                            found = true;
                            iterator.remove();
                            break;
                        }
                    }
                    if (!found) {
                        for (GT_Utility.ItemId id : getItemsHashed(o, true)) {
                            if (rInputHashedNoWildcard.contains(id)) {
                                found = true;
                                iterator.remove();
                                break;
                            }
                        }
                    }
                    if (found) break;
                }
                if (!found) return false;
            }
            return recipe.isEmpty();
        });
    }

    private static Field recipeWidthField = null;

    /**
     * Removes only shaped recipes by output and inputs, supports OreDictionary tags
     *
     * @author kuba6000
     */
    static void removeRecipeShapedDelayed(Object aOutput, Object[] row1, Object[] row2, Object[] row3) {
        if (recipeWidthField == null) {
            try {
                recipeWidthField = ShapedOreRecipe.class.getDeclaredField("width");
                recipeWidthField.setAccessible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        Object[][] recipe = new Object[][] { row1, row2, row3 };
        addToBuffer(getItemsHashed(aOutput, false), r -> {
            if (!(r instanceof ShapedOreRecipe) && !(r instanceof ShapedRecipes)) return false;
            Object[] inputs = (r instanceof ShapedOreRecipe ? ((ShapedOreRecipe) r).getInput()
                    : ((ShapedRecipes) r).recipeItems);
            int width;
            try {
                width = (r instanceof ShapedOreRecipe ? (int) recipeWidthField.get(r)
                        : ((ShapedRecipes) r).recipeWidth);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            int height = (r instanceof ShapedOreRecipe ? r.getRecipeSize() / width : ((ShapedRecipes) r).recipeHeight);

            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    Object rStack = (y >= height || x >= width ? null : inputs[x + y * width]);
                    Object rRecipe = (x >= recipe[y].length ? null : recipe[y][x]);
                    if (rStack == null ^ rRecipe == null) return false;
                    if (rStack == null) continue;
                    HashSet<GT_Utility.ItemId> rInputHashed;
                    try {
                        rInputHashed = getItemsHashed(rStack, true);
                    } catch (Exception ex) {
                        return false;
                    }
                    boolean found = false;
                    for (GT_Utility.ItemId id : getItemsHashed(rRecipe, false)) {
                        if (rInputHashed.contains(id)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        rInputHashed = getItemsHashed(rStack, false);
                        for (GT_Utility.ItemId id : getItemsHashed(rRecipe, true)) {
                            if (rInputHashed.contains(id)) {
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) return false;
                }
            }

            return true;
        });
    }

    /**
     * Removes only shaped recipes by output, supports OreDictionary tag
     *
     * @author kuba6000
     */
    static void removeRecipeShapedDelayed(Object aOutput) {
        addToBuffer(getItemsHashed(aOutput, false), r -> r instanceof ShapedOreRecipe || r instanceof ShapedRecipes);
    }

    /**
     * Removes recipes by output, supports OreDictionary tags
     *
     * @author kuba6000
     */
    static void removeRecipeByOutputDelayed(Object aOutput) {
        addToBuffer(getItemsHashed(aOutput, false), r -> true);
    }

    public static void run() {

        final long timeStart = System.currentTimeMillis();

        // AUTOGENERATED FROM SCRIPTS

        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Sunnarium, 1L));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Sunnarium, 1L));
        GT_ModHandler
                .removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, Materials.Sunnarium, 1L));
        GT_ModHandler.removeFurnaceSmelting(
                GT_OreDictUnificator.get(OrePrefixes.toolHeadUniversalSpade, Materials.Sunnarium, 1L));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Sunnarium, 1L));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Sunnarium, 1L));
        GT_ModHandler.removeFurnaceSmelting(getModItem("Backpack", "boundLeather", 1, wildcard, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("minecraft", "gravel", 1, wildcard, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("EMT", "EMTItems", 1, 10, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("EnderIO", "itemMaterial", 1, 2, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("minecraft", "quartz_block", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 7, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("ExtraUtilities", "decorativeBlock1", 1, 9, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("FloodLights", "rawFilament", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1, 4, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("GalacticraftMars", "tile.asteroidsBlock", 1, 4, missing));
        GT_ModHandler
                .removeFurnaceSmelting(getModItem("GalacticraftCore", "item.meteoricIronRaw", 1, wildcard, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("GalacticraftMars", "item.null", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("minecraft", "chainmail_boots", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("IC2", "blockRubWood", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("minecraft", "slime_ball", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("IC2", "itemMugCoffee", 1, wildcard, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("IC2", "itemRecipePart", 1, 4, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("IC2", "itemOreIridium", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("minecraft", "obsidian", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("minecraft", "clay_ball", 1, wildcard, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("minecraft", "netherrack", 1, wildcard, missing));
        GT_ModHandler.removeFurnaceSmelting(ItemList.Food_Raw_Bread.get(1L));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "oreBerries", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "oreBerries", 1, 1, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "oreBerries", 1, 2, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "oreBerries", 1, 3, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "oreBerries", 1, 4, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("minecraft", "sand", 1, wildcard, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("minecraft", "soul_sand", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("Natura", "heatsand", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("Natura", "tree", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("Natura", "tree", 1, 1, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("Natura", "tree", 1, 2, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("Natura", "tree", 1, 3, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("Natura", "redwood", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("Natura", "redwood", 1, 1, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("Natura", "redwood", 1, 2, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("minecraft", "stone", 1, wildcard, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("ProjRed|Core", "projectred.core.part", 1, 41, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("ProjRed|Core", "projectred.core.part", 1, 42, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("ProjRed|Core", "projectred.core.part", 1, 43, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("ProjRed|Core", "projectred.core.part", 1, 57, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("ProjRed|Core", "projectred.core.part", 1, 58, missing));
        GT_ModHandler
                .removeFurnaceSmelting(getModItem("ProjRed|Exploration", "projectred.exploration.ore", 1, 6, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("StevesCarts", "ModuleComponents", 1, 46, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("StevesCarts", "ModuleComponents", 1, 48, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("StevesCarts", "ModuleComponents", 1, 18, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("StevesCarts", "ModuleComponents", 1, 11, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("StevesCarts", "ModuleComponents", 1, 21, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("EMT", "EMTItems", 1, 1, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("EMT", "EMTItems", 1, 2, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("Thaumcraft", "blockCustomOre", 1, 7, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("BiomesOPlenty", "gemOre", 1, 14, missing));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.oreBasalt, Materials.Amber, 1L));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.oreRedgranite, Materials.Amber, 1L));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.oreEndstone, Materials.Amber, 1L));
        GT_ModHandler.removeFurnaceSmelting(getModItem("gregtech", "gt.blockores", 1, 514, missing));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.oreMarble, Materials.Amber, 1L));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.oreBlackgranite, Materials.Amber, 1L));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.oreNetherrack, Materials.Amber, 1L));
        GT_ModHandler.removeFurnaceSmelting(getModItem("Thaumcraft", "ItemShard", 1, 6, missing));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 1L));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Obsidian, 1L));
        GT_ModHandler.removeFurnaceSmelting(getModItem("IC2", "itemDust", 1, 11, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("IC2", "itemPlates", 1, 7, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("Railcraft", "dust", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TwilightForest", "tile.GiantObsidian", 1, wildcard, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("Railcraft", "machine.beta", 1, 10, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("EnderIO", "itemPowderIngot", 1, 7, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("IC2", "itemDensePlates", 1, 7, missing));
        GT_ModHandler.removeFurnaceSmelting(ItemList.Conveyor_Module_LV.get(1L));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "materials", 1, 36, missing));
        GT_ModHandler.removeFurnaceSmelting(ItemList.Automation_ItemDistributor_ULV.get(1L));
        GT_ModHandler.removeFurnaceSmelting(ItemList.Automation_ItemDistributor_LV.get(1L));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "CraftedSoil", 1, 0, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "CraftedSoil", 1, 2, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "materials", 1, 39, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "materials", 1, 38, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "materials", 1, 41, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "materials", 1, 42, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "SearedBrick", 1, 5, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "GravelOre", 1, 4, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "materials", 1, 40, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "CraftedSoil", 1, 1, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TConstruct", "CraftedSoil", 1, 6, missing));
        GT_ModHandler.removeFurnaceSmelting(getModItem("TwilightForest", "item.ironwoodRaw", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("ae2stuff", "Encoder", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ae2stuff", "Grower", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ae2stuff", "Inscriber", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ae2stuff", "Wireless", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ae2stuff", "WirelessKit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ae2stuff", "Visualiser", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "BlockMolecularTransformer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "advanced_solar_helmet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "hybrid_solar_helmet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "ultimate_solar_helmet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("Automagy", "blockHourglass", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Avaritia", "Double_Craft", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Avaritia", "Triple_Craft", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Avaritia", "Resource", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Avaritia", "Resource", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Avaritia", "Dire_Crafting", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Avaritia", "Crystal_Matrix", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Avaritia", "Resource_Block", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Avaritia", "Resource_Block", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Avaritia", "Resource", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Avaritia", "Resource", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Avaritia", "Resource", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Avaritia", "Resource", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("Avaritia", "Resource", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("eternalsingularity", "combined_singularity", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Backpack", "backpack", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Backpack", "workbenchbackpack", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Backpack", "boundLeather", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("betterbuilderswands", "wandStone", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("betterbuilderswands", "wandIron", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("betterbuilderswands", "wandDiamond", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("betterbuilderswands", "wandUnbreakable", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("questbook", "ItemQuestBook", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BinnieCore", "fieldKit", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("BiomesOPlenty", "gemOre", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("BiomesOPlenty", "gems", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("BiomesOPlenty", "jarEmpty", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BiomesOPlenty", "dartBlower", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BiomesOPlenty", "dart", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BiomesOPlenty", "scytheDiamond", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BiomesOPlenty", "ash", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "coal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BiomesOPlenty", "gems", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("BiomesOPlenty", "gemOre", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("BiomesOPlenty", "gemOre", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("BiomesOPlenty", "gemOre", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_iron_block", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_iron", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_planks", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_stick", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_stained_glass", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_stained_ice", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_stained_ice_packed", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_glowstone", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_diamond_block", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_lamp", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "life_infuser", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "lp_materializer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "compacter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_pickaxe_wood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_axe_wood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_shovel_wood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_sword_wood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_pickaxe_iron", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_axe_iron", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_shovel_iron", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_sword_iron", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_pickaxe_diamond", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_axe_diamond", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_shovel_diamond", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_infused_sword_diamond", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_diamond", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_burned_string", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_money", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_money", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_money", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "life_imbued_helmet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "life_imbued_chestplate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "life_imbued_leggings", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "life_imbued_boots", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "amorphic_catalyst", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "transparent_orb", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "sigil_of_augmented_holding", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "sigil_of_lightning", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "sigil_of_swimming", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "sigil_of_ender", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "sigil_of_divinity", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "self_sacrifice_amulet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "sacrifice_amulet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "empowered_sacrifice_amulet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "empowered_self_sacrifice_amulet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "glass_sacrificial_dagger", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "glass_dagger_of_sacrifice", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "vampire_ring", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "Altar", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "sacrificialKnife", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_stone", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blankSlate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "AlchemicalWizardrybloodRune", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockWritingTable", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "waterSigil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "lavaSigil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "reinforcedSlate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_stone", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "imbuedSlate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_stone", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "demonicSlate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_stone", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 27, missing));
        removeRecipeByOutputDelayed(getModItem("BloodArsenal", "blood_stone", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "divinationSigil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "speedRune", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "ritualStone", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "masterStone", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "largeBloodStoneBrick", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockHomHeart", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "lavaCrystal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "runeOfSacrifice", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "runeOfSelfSacrifice", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "airSigil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "sigilOfTheFastMiner", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "growthSigil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "voidSigil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "itemBloodPack", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "emptySocket", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "armourForge", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "AlchemicalWizardrybloodRune", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "AlchemicalWizardrybloodRune", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "sigilOfElementalAffinity", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "sigilOfHolding", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "sigilOfTheBridge", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "sigilOfMagnetism", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "itemBloodLightSigil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "seerSigil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "imperfectRitualStone", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "itemRitualDiviner", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "itemRitualDiviner", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "itemRitualDiviner", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "ritualDismantler", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "AlchemicalWizardrybloodRune", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "AlchemicalWizardrybloodRune", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "AlchemicalWizardrybloodRune", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "AlchemicalWizardrybloodRune", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 32, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockTeleposer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "reinforcedTelepositionFocus", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "demonicTelepositionFocus", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "energyBazooka", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockPedestal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockPlinth", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockAlchemicCalcinator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "itemAttunedCrystal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "itemDestinationClearer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "itemTankSegmenter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockCrystalBelljar", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockReagentConduit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockCrystal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockStabilityGlyph", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockEnchantmentGlyph", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockEnchantmentGlyph", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 25, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 31, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 18, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 19, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 20, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 21, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 22, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 23, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 24, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 26, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicBaseItems", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "itemKeyOfDiablo", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "armourInhibitor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "inputRoutingFocus", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "outputRoutingFocus", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "outputRoutingFocus", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "outputRoutingFocus", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "outputRoutingFocus", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "inputRoutingFocus", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "sigilOfHaste", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "sigilOfWind", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "sigilOfSupression", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "sigilOfEnderSeverance", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "itemHarvestSigil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "itemCompressionSigil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockCrucible", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "blockConduit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AWWayofTime", "bloodMagicIncenseItem", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("AWWayofTime", "AlchemicalWizardrytile.blockSpellParadigm", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("AWWayofTime", "AlchemicalWizardrytile.blockSpellParadigm", 1, 1, missing));
        removeRecipeByOutputDelayed(
                getModItem("AWWayofTime", "AlchemicalWizardrytile.blockSpellParadigm", 1, 2, missing));
        removeRecipeByOutputDelayed(
                getModItem("AWWayofTime", "AlchemicalWizardrytile.blockSpellParadigm", 1, 3, missing));
        removeRecipeByOutputDelayed(
                getModItem("AWWayofTime", "AlchemicalWizardrytile.blockSpellModifier", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("AWWayofTime", "AlchemicalWizardrytile.blockSpellModifier", 1, 1, missing));
        removeRecipeByOutputDelayed(
                getModItem("AWWayofTime", "AlchemicalWizardrytile.blockSpellModifier", 1, 2, missing));
        removeRecipeByOutputDelayed(
                getModItem("AWWayofTime", "AlchemicalWizardrytile.blockSpellModifier", 1, 3, missing));
        removeRecipeByOutputDelayed(
                getModItem("AWWayofTime", "AlchemicalWizardrytile.blockSpellEffect", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("AWWayofTime", "AlchemicalWizardrytile.blockSpellEffect", 1, 1, missing));
        removeRecipeByOutputDelayed(
                getModItem("AWWayofTime", "AlchemicalWizardrytile.blockSpellEffect", 1, 2, missing));
        removeRecipeByOutputDelayed(
                getModItem("AWWayofTime", "AlchemicalWizardrytile.blockSpellEffect", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Botany", "trowelWood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Botany", "trowelStone", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Botany", "trowelIron", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Botany", "trowelGold", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Botany", "trowelDiamond", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Botany", "insulatedTube", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Botany", "soilMeter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "itemCarpentersChisel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "itemCarpentersHammer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersSafe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersDaylightSensor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "itemCarpentersBed", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersTorch", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersGarageDoor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersLadder", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersBarrier", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersPressurePlate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersButton", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersFlowerPot", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersGate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersHatch", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersLever", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersSlope", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "blockCarpentersStairs", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "itemCarpentersTile", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("CarpentersBlocks", "itemCarpentersDoor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("catwalks", "support_column", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("catwalks", "sturdy_rail_powered", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("catwalks", "cagedLadder_north_unlit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("catwalks", "scaffold", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("catwalks", "sturdy_rail_detector", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("catwalks", "catwalk_unlit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("catwalks", "sturdy_rail", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("catwalks", "sturdy_rail_activator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("catwalks", "ropeLight", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("catwalks", "steelgrate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("catwalks", "blowtorch", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("chisel", "chisel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("chisel", "obsidianChisel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("chisel", "diamondChisel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("chisel", "netherStarChisel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("chisel", "cloudinabottle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("chisel", "ballomoss", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("chisel", "smashingrock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("chisel", "autoChisel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("chisel", "upgrade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("chisel", "upgrade", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("chisel", "upgrade", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("chisel", "upgrade", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("chisel", "voidstone2", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "BlockCkg", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "BlockCkg", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "BlockCkg", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "BlockCkg", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "BlockCkg", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "BlockCkg", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "BlockCkg", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "BlockCkg", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "BlockCkg", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "BlockCkg", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "BlockCkg", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "BlockCkg", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "IridiumBlade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("compactkineticgenerators", "IridiumRotor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("DraconicEvolution", "dissEnchanter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "blockCosmeticSolid", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EMT", "DiamondChainsaw", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("EMT", "EMTItems", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("EMT", "FeatherWing", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EMT", "EMTItems", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("EMT", "EMTItems", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("EMT", "EMTItems", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("EMT", "EMTItems", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("EMT", "EMTItems", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("EMT", "EMTItems", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("EMT", "Omnitool", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockStirlingGenerator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockCombustionGenerator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockZombieGenerator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockFrankenzombieGenerator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockEnderGenerator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockSolarPanel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockSolarPanel", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockSolarPanel", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockSagMill", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockAlloySmelter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockCapBank", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockPainter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockCrafter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemBasicCapacitor", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMachinePart", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockVat", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockPowerMonitor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockFarmStation", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockWirelessCharger", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockTank", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockTank", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockReservoir", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockVacuumChest", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockTransceiver", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockBuffer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockBuffer", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockBuffer", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockInventoryPanel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMachinePart", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMaterial", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMaterial", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMaterial", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMaterial", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBall", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBall", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBall", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBall", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBall", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBall", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBall", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBall", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBall", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBallEndergy", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBallEndergy", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBallEndergy", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBallEndergy", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBallEndergy", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBallEndergy", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGrindingBallEndergy", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.endSteel_helmet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.endSteel_chestplate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.endSteel_leggings", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.endSteel_boots", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.endSteel_pickaxe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.endSteel_axe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.endSteel_sword", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMaterial", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockEnderIo", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockTravelAnchor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockTelePad", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockSliceAndSplice", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockSoulBinder", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockPoweredSpawner", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockKillerJoe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockAttractor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockSpawnGuard", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockExperienceObelisk", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockWeatherObelisk", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockEnchanter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockDarkSteelPressurePlate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockDarkSteelPressurePlate", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockDarkSteelAnvil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockDarkIronBars", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockSoulariumBars", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockEndSteelBars", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockDarkSteelLadder", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockElectricLight", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockElectricLight", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockElectricLight", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockElectricLight", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockElectricLight", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockElectricLight", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "blockReinforcedObsidian", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemCoordSelector", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemFusedQuartzFrame", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemConduitFacade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemConduitFacade", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemRedstoneConduit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemRedstoneConduit", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemRedstoneConduit", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduit", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduit", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduitEndergy", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduitEndergy", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduitEndergy", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduitEndergy", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduitEndergy", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduitEndergy", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduitEndergy", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduitEndergy", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduitEndergy", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduitEndergy", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduitEndergy", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerConduitEndergy", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemLiquidConduit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemLiquidConduit", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemLiquidConduit", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemLiquidConduit", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemLiquidConduit", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemLiquidConduit", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemLiquidConduit", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemItemConduit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemBasicFilterUpgrade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemBasicFilterUpgrade", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemLimitedItemFilter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemExistingItemFilter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemModItemFilter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemBigFilterUpgrade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemBigFilterUpgrade", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemPowerItemFilter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemExtractSpeedUpgrade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemExtractSpeedUpgrade", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemFunctionUpgrade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemYetaWrench", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemConduitProbe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemTravelStaff", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemXpTransfer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemSoulVessel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGliderWing", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemGliderWing", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMagnet", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemOCConduit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMachinePart", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMachinePart", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMaterial", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMaterial", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMaterial", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMaterial", 1, 16, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "itemMaterial", 1, 17, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.darkSteel_shears", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.stellar_pickaxe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.stellar_axe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.stellar_sword", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.stellar_helmet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.stellar_chestplate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.stellar_leggings", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("EnderIO", "item.stellar_boots", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "alveary", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "alveary", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "alveary", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "alveary", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "alveary", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "alveary", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "alveary", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "hiveFrame.cage", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "hiveFrame.clay", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "hiveFrame.cocoa", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "hiveFrame.soul", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "hiveFrame.clay", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "hiveFrame.cocoa", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "hiveFrame.cage", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraBees", "hiveFrame.soul", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "machine", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "machine", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "machine", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "machine", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "machine", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "machine", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "machine", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "machine", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "misc", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "hammer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "durableHammer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "misc", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "misc", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "misc", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "misc", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "misc", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "misc", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "misc", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "misc", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "door", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "gate", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "fence", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraTrees", "multifence", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "angelRing", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "angelRing", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "angelRing", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "angelRing", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "angelRing", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "enderQuarry", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "enderThermicPump", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "endMarker", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock1", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock1", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock1", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock1", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "curtains", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "timer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock2", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock2", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "enderQuarryUpgrade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "enderQuarryUpgrade", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "enderQuarryUpgrade", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "enderQuarryUpgrade", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "enderQuarryUpgrade", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "enderQuarryUpgrade", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "enderQuarryUpgrade", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "enderQuarryUpgrade", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "enderQuarryUpgrade", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "enderQuarryUpgrade", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "nodeUpgrade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "nodeUpgrade", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "nodeUpgrade", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "nodeUpgrade", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "nodeUpgrade", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "nodeUpgrade", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "nodeUpgrade", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "nodeUpgrade", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "nodeUpgrade", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "paintbrush", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "drum", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "drum", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "bedrockiumIngot", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "block_bedrockium", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "angelBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "conveyor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "filing", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "filing", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "watering_can", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "magnumTorch", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "sound_muffler", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "sound_muffler", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "pipes", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "pipes", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "pipes", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "pipes", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "pipes.1", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "pipes", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "pipes", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "pipes", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "pipes", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "extractor_base_remote", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "extractor_base_remote", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "extractor_base", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "extractor_base", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "extractor_base", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "extractor_base", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "cobblestone_compressed", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "trashcan", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "trashcan", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "trashcan", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock2", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock1", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock2", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock2", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock2", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock2", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock2", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock2", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock2", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock2", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock2", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock1", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "dark_portal", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "dark_portal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "chestFull", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "chestMini", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "enderCollector", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "endConstructor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "endConstructor", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "budoff", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "budoff", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "chandelier", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock1", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock1", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock1", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock1", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock1", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock1", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "decorativeBlock1", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "trading_post", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "spike_base_wood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "spike_base", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "spike_base_gold", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "spike_base_diamond", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "generator", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "generator.8", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "generator.64", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "heatingElement", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "nodeUpgrade", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "golden_lasso", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "scanner", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "builderswand", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "creativebuilderswand", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "shears", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "ethericsword", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "lawSword", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "erosionShovel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "destructionpickaxe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "defoliageAxe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ExtraUtilities", "temporalHoe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("FloodLights", "electricFloodlight", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("FloodLights", "carbonFloodlight", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("FloodLights", "smallElectricFloodlightMetaBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("FloodLights", "smallElectricFloodlightMetaBlock", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("FloodLights", "uvFloodlight", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("FloodLights", "rawFilament", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("FloodLights", "electricIncandescentLightBulb", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("FloodLights", "carbonDissolver", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("FloodLights", "carbonLantern", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("FloodLights", "mantle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("FloodLights", "growLight", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ForbiddenMagic", "RidingCrop", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem(ForbiddenMagic.ID, "WandCaps", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "sturdyMachine", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "core", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "factory", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "factory", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "factory", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "factory", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "factory", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "factory", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "factory", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "factory", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "factory2", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "factory2", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "factory2", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "mail", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "mail", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "mulch", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "engine", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "ffarm", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "ffarm", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "ffarm", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "ffarm", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "ffarm", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "bronzePickaxe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "kitPickaxe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "bronzeShovel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "kitShovel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "infuser", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "canEmpty", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "waxCapsule", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "refractoryEmpty", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "gearBronze", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "gearCopper", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "gearTin", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "stamps", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "stamps", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "stamps", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "stamps", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "stamps", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "stamps", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "stamps", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "wrench", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "pipette", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "naturalistHelmet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "soil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "soil", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "craftingMaterial", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "fertilizerBio", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "fertilizerCompound", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "bituminousPeat", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "habitatLocator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "scoop", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "frameUntreated", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "frameImpregnated", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "waxCast", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "apiaristHelmet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "apiaristChest", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "apiaristLegs", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "apiaristBoots", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "candle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "honeyedSlice", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "ambrosia", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "honeyPot", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "letters", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "fencesFireproof", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "catalogue", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "apiaristBag", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "lepidopteristBag", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "minerBag", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "diggerBag", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "foresterBag", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "hunterBag", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "adventurerBag", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "builderBag", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "apiculture", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "apiculture", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "apicultureChest", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "alveary", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "alveary", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "alveary", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "alveary", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "alveary", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "alveary", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "alveary", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "arboriculture", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "lepidopterology", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "fences", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Forestry", "cart.beehouse", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("gregtech", "gt.blockmachines", 1, 875, missing));
        removeRecipeByOutputDelayed(getModItem("miscutils", "frameAccelerated", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("miscutils", "frameMutagenic", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("miscutils", "frameBusy", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("miscutils", "frameDecaying", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("miscutils", "frameSlowing", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("miscutils", "frameStabilizing", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("miscutils", "frameArborists", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("miscutils", "personalCloakingDevice-0.0", 1, 0, missing));
        removeRecipeByOutputDelayed(ItemList.Electric_Motor_UHV.get(1L));
        removeRecipeByOutputDelayed(ItemList.Electric_Pump_UHV.get(1L));
        removeRecipeByOutputDelayed(ItemList.Conveyor_Module_UHV.get(1L));
        removeRecipeByOutputDelayed(ItemList.Electric_Piston_UHV.get(1L));
        removeRecipeByOutputDelayed(ItemList.Robot_Arm_UHV.get(1L));
        removeRecipeByOutputDelayed(ItemList.Emitter_UHV.get(1L));
        removeRecipeByOutputDelayed(ItemList.Sensor_UHV.get(1L));
        removeRecipeByOutputDelayed(ItemList.Field_Generator_UHV.get(1L));
        removeRecipeByOutputDelayed(getModItem("gregtech", "gt.blockmachines", 1, 946, missing));
        removeRecipeByOutputDelayed(getModItem("gregtech", "gt.blockmachines", 1, 947, missing));
        removeRecipeByOutputDelayed(getModItem("gregtech", "gt.blockmachines", 1, 948, missing));
        removeRecipeByOutputDelayed(getModItem("gregtech", "gt.blockmachines", 1, 949, missing));
        removeRecipeByOutputDelayed(getModItem("gregtech", "gt.blockmachines", 1, 950, missing));
        removeRecipeByOutputDelayed(getModItem("gregtech", "gt.blockmachines", 1, 798, missing));
        removeRecipeByOutputDelayed(getModItem("gregtech", "gt.blockmachines", 1, 753, missing));
        removeRecipeByOutputDelayed(getModItem("miscutils", "itemBoilerChassis", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gregtech", "gt.blockmachines", 1, 754, missing));
        removeRecipeByOutputDelayed(getModItem("miscutils", "itemBoilerChassis", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("gregtech", "gt.blockmachines", 1, 755, missing));
        removeRecipeByOutputDelayed(getModItem("miscutils", "itemBoilerChassis", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("gregtech", "gt.blockmachines", 1, 820, missing));
        removeRecipeByOutputDelayed(getModItem("miscutils", "blockCompressedObsidian", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "IndustrialApiary", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "MutagenProducer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "Mutatron", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "Imprinter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "Sampler", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "MutatronAdv", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "Liquifier", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "Extractor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "Transposer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "Replicator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "UpgradeFrame", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "Labware", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "MutagenTank", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "BeeReceptacle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "PowerModule", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "GeneticsProcessor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "EnvProcessor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "ApiaryUpgrade", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "IndustrialGrafter", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "IndustrialScoop", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "MutagenTank", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "BeeReceptacle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "PowerModule", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "ClimateModule", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "GeneticsProcessor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "EnvProcessor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "EjectCover", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "ImportCover", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gendustry", "ErrorSensorCover", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "misc", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "misc", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "misc", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "misc", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "misc", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "misc", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "misc", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "misc", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "misc", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "machine", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "machine", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "machine", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "machine", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "labMachine", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "labMachine", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "labMachine", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "labMachine", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "labMachine", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "advMachine", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "misc", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "misc", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "analyst", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Genetics", "registry", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("GraviSuite", "itemSimpleItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("GraviSuite", "itemSimpleItem", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("GraviSuite", "itemSimpleItem", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("GraviSuite", "itemSimpleItem", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("GraviSuite", "itemSimpleItem", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("GraviSuite", "vajra", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("GraviSuite", "itemSimpleItem", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("GraviSuite", "advChainsaw", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("GraviSuite", "advDDrill", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("GraviSuite", "graviTool", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("GraviSuite", "ultimateLappack", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("gravisuiteneo", "epicLappack", 1, wildcard, missing));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, Materials.Diamond, 1L));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, Materials.Diamond, 1L));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, Materials.Diamond, 1L));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, Materials.Diamond, 1L));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, Materials.Diamond, 1L));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemRTGPellet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemOreIridium", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicbases", "resource", 1, 0, missing));
        removeRecipeByOutputDelayed(ItemList.Teleporter.get(1L));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "altar_nexus", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "essence_altar", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "void_chest", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "endium_block", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "biome_compass", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "endium_ingot", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "obsidian_rod", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "ravaged_brick_slab", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "decomposition_table", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "experience_table", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "energy_extraction_table", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "accumulation_table", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "spectral_tear", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "living_matter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "energy_wand_core", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "energy_wand", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "spooky_log", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "spooky_leaves", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "blank_gem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "spatial_dash_gem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "transference_gem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "schorching_pickaxe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "charm_pouch", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 256, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 257, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 258, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 259, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 260, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 261, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 262, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 263, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 264, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 265, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("HardcoreEnderExpansion", "curse", 1, 266, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "sink", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "market", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "spamcompressedsaltBlockalt", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "oven", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "mixingbowlItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "cuttingboardItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "mortarandpestleItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "bakewareItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "juicerItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "apiary", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "wovencottonItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "mortarandpestleItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "potItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "presser", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "skilletItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "saucepanItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "hardenedleatherItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "hardenedleatherhelmItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "hardenedleatherchestItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "hardenedleatherleggingsItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "hardenedleatherbootsItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "waxItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco1", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco2", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco3", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco4", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco5", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco6", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco7", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco8", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco9", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco10", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco11", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco12", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco13", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco14", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco15", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco16", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "pamcandleDeco16", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "churn", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "quern", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "freshwaterItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "freshmilkItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "boiledeggItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "onionsoupItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "honeycombchocolatebarItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestthenether", "netherPlanks", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestthenether", "netherbedItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestthenether", "Quartz Sword", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestthenether", "Quartz Shovel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestthenether", "Quartz Pickaxe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestthenether", "Quartz Axe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestthenether", "Quartz Hoe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestthenether", "quartzhelmItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestthenether", "quartzchestItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestthenether", "quartzleggingsItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestthenether", "quartzbootsItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockGenerator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockGenerator", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockHeatGenerator", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockGenerator", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockKineticGenerator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockKineticGenerator", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockKineticGenerator", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockReactorChamber", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockHeatGenerator", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockGenerator", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockElectric", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockElectric", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockElectric", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockElectric", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemRecipePart", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemRecipePart", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockElectric", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockElectric", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockElectric", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockElectric", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockScaffold", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockIronScaffold", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMetal", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolMiningLaser", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockITNT", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorPlating", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorPlatingHeat", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorPlatingExplosive", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolCutter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemCable", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemCable", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemCable", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemCable", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemPartCoalChunk", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemPartCarbonMesh", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockAlloyGlass", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolIridiumDrill", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolWrenchElectric", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "windmeter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemRecipePart", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemwoodrotor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemRecipePart", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemironrotor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemRecipePart", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemsteelrotor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemRecipePart", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemwcarbonrotor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolWrench", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemupgradekit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemFluidCell", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorReflector", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorReflectorThick", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorVent", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorVentCore", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorVentGold", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorVentDiamond", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorVentSpread", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorHeatSwitch", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorHeatSwitchCore", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorHeatSwitchSpread", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorHeatSwitchDiamond", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorCondensator", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorCondensatorLap", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemTreetap", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockRubber", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemRecipePart", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemSteamTurbineBlade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemSteamTurbine", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemIronBlockCuttingBlade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemAdvIronBlockCuttingBlade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemDiamondBlockCuttingBlade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemBarrel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemMugEmpty", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemMugCoffee", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemMugCoffee", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemMugCoffee", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemBoat", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemBoat", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemBoat", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemDynamiteSticky", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemDynamite", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemWeedingTrowel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemCropnalyzer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolMEter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemFuelPlantBall", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemScrapbox", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemPartCoalBall", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemPartCFPowder", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemDoorAlloy", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterBlack", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterRed", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterGreen", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterBrown", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterBlue", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterPurple", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterCyan", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterLightGrey", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterDarkGrey", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterPink", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterLime", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterYellow", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterCloud", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterMagenta", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterOrange", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolPainterWhite", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemBatSU", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemRecipePart", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemRemote", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemFreq", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "upgradeModule", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "upgradeModule", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "upgradeModule", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "upgradeModule", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "upgradeModule", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "upgradeModule", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "upgradeModule", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemTFBP", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemTFBPCultivation", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemTFBPIrrigation", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemTFBPChilling", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemTFBPDesertification", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemTFBPFlatification", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemTFBPMushroom", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemArmorHazmatHelmet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemArmorHazmatChestplate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemArmorHazmatLeggings", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemArmorHazmatLeggings", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorCoolantSimple", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorCoolantTriple", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "reactorCoolantSix", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemArmorRubBoots", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemStaticBoots", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockGenerator", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockKineticGenerator", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockKineticGenerator", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockKineticGenerator", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockGenerator", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockHeatGenerator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockHeatGenerator", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockGenerator", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockPersonal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockCrop", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemFoamSprayer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemArmorCFPack", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemArmorAlloyChestplate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemNanoSaber", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "obscurator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemScanner", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemScannerAdv", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemOreIridium", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemPlutonium", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemUran235small", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemUran235", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemLathingTool", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemTurningBlanksWood", 1, 209715, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemTurningBlanks", 1, 349525, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemContainmentbox", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemToolbox", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemPlutoniumSmall", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemMOX", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemUran", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemRecipePart", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemRecipePart", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemRecipePart", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockNuke", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockLuminatorDark", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockLuminator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine3", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockPersonal", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockPersonal", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine3", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine3", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine3", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine3", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine3", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine2", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMachine", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockChargepad", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockChargepad", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockChargepad", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockChargepad", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockreactorvessel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockReactorFluidPort", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockReactorAccessHatch", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockReactorRedstonePort", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockMiningPipe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "blockGenerator", 1, 5, missing));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1L));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, Materials.Iridium, 1L));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, Materials.Iridium, 1L));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.toolHeadSense, Materials.Iridium, 1L));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, Materials.Iridium, 1L));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.toolHeadPlow, Materials.Iridium, 1L));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, Materials.Iridium, 1L));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.toolHeadSaw, Materials.Iridium, 1L));
        removeRecipeByOutputDelayed(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, Materials.Iridium, 1L));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemBatLamaCrystal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemDust2", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2", "itemPartCFPowder", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "backpack.thaumaturgeT1", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "capsule.magic", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "capsule.void", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "miscResources", 1, 17, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "effectJar", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "miscResources", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "miscResources", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "miscResources", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "miscResources", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "miscResources", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "miscResources", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "magicApiary", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "frameMagic", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "frameResilient", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "frameGentle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "frameMetabolic", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "frameNecrotic", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "frameTemporal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "frameOblivion", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "miscResources", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "miscResources", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "miscResources", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "moonDial", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "magnet", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("MagicBees", "magicbees.enchantedEarth", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "iron_trapdoor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "sliding_trapdoor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "player_sensor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "vanishing_block", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "vanishing_block", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "vanishing_block", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "vanishing_block", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "block_mixer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "door_factory", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "rustyHatch", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "garage_door", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.rustyHandle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "rustyLadder", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.door_acacia", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.door_birch", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.door_dark_oak", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.door_jungle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.door_spruce", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.wood_sliding_door", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.iron_sliding_door", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.jail_door", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.laboratory_door", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.factory_door", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.shoji_door", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_purple", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_yellow", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_magenta", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_pink", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_white", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_blue", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_gray", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_cyan", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_red", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_brown", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_lime", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_orange", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_silver", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_green", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_light_blue", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.curtain_black", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.saloon", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "item.forcefieldItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "acaciaFenceGate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "birchFenceGate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "darkOakFenceGate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "jungleFenceGate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "spruceFenceGate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "trapdoor_acacia", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "trapdoor_birch", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "trapdoor_dark_oak", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "trapdoor_jungle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("malisisdoors", "trapdoor_spruce", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TMechworks", "RedstoneMachine", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TMechworks", "RedstoneMachine", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TMechworks", "RedstoneMachine", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("TMechworks", "RedstoneMachine", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TMechworks", "SignalBus", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TMechworks", "SignalTerminal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TMechworks", "LengthWire", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TMechworks", "SpoolWire", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "chest", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "trapped_chest", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "crafting_table", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "furnace", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "bedrock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "dispenser", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "dropper", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "piston", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "sticky_piston", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "rail", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "golden_rail", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "detector_rail", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "activator_rail", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "noteblock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "jukebox", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "bookshelf", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "mossy_cobblestone", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stonebrick", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "lit_pumpkin", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "trapdoor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stone_pressure_plate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "torch", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "lever", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stone_button", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "glass_pane", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "redstone_torch", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "ladder", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "fence", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "fence_gate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "enchanting_table", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "redstone_lamp", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "ender_chest", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "tripwire_hook", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "beacon", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "anvil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "daylight_detector", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "hopper", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "book", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "bow", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "brick_block", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wooden_pressure_plate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "saddle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wool", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_glass_pane", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "carpet", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "stained_hardened_clay", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "sandstone", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "sandstone", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "sandstone", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "tnt", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "snow", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "clay", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "nether_brick", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "quartz_block", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "quartz_block", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "bookshelf", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "brewing_stand", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "clock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "compass", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "diamond_sword", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "diamond_shovel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "diamond_pickaxe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "diamond_axe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "diamond_hoe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "diamond_helmet", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "diamond_chestplate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "diamond_leggings", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "diamond_boots", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "ender_eye", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "golden_apple", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "golden_apple", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "golden_carrot", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "cake", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "pumpkin_seeds", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "melon_seeds", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "fermented_spider_eye", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "magma_cream", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "fire_charge", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "melon_block", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "soul_sand", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "nether_brick_fence", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "name_tag", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "lead", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "pumpkin_pie", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "comparator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "repeater", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "skull", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "skull", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "skull", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "flower_pot", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "hay_block", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wheat", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "slime_ball", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "item_frame", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "bed", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "fishing_rod", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "poisonous_potato", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "sign", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "boat", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "painting", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "flint_and_steel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "dragon_egg", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "map", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wooden_door", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "iron_door", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "skull", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "shears", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "nether_star", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "wooden_door", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("minecraft", "iron_door", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "planks", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "natura.stick", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "Natura.workbench", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "natura.flintandblaze", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "Natura.bookshelf", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "Natura.fence", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "plankSlab1", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "plankSlab2", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "pressureplate.eucalyptus", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "pressureplate.sakura", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "pressureplate.ghostwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "pressureplate.redwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "pressureplate.bloodwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "pressureplate.hopseed", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "pressureplate.maple", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "pressureplate.amaranth", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "pressureplate.silverbell", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "pressureplate.tiger", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "pressureplate.willow", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "pressureplate.darkwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "pressureplate.fusewood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "NetherPressurePlate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "trapdoor.eucalyptus", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "trapdoor.sakura", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "trapdoor.ghostwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "trapdoor.redwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "trapdoor.bloodwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "trapdoor.hopseed", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "trapdoor.maple", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "trapdoor.amaranth", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "trapdoor.silverbell", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "trapdoor.tiger", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "trapdoor.willow", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "trapdoor.darkwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "trapdoor.fusewood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "button.eucalyptus", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "button.sakura", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "button.ghostwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "button.redwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "button.bloodwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "button.hopseed", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "button.maple", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "button.amaranth", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "button.silverbell", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "button.tiger", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "button.willow", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "button.darkwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "button.fusewood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "fenceGate.eucalyptus", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "fenceGate.sakura", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "fenceGate.ghostwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "fenceGate.redwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "fenceGate.bloodwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "fenceGate.hopseed", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "fenceGate.maple", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "fenceGate.amaranth", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "fenceGate.silverbell", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "fenceGate.tiger", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "fenceGate.willow", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "fenceGate.darkwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "fenceGate.fusewood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "natura.emptybowl", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "redwoodDoorItem", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "blackberryItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "blueberryItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("harvestcraft", "raspberryItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "Blazerail", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "BrailPowered", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "BrailDetector", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "BrailActivator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "NetherFurnace", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "Obelisk", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "NetherButton", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "NetherLever", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "natura.bow.ghostwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "natura.bow.bloodwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "natura.bow.darkwood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "natura.bow.fusewood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Natura", "barleyFood", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "blockNuclearControlMain", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "blockNuclearControlMain", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "blockNuclearControlMain", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "blockNuclearControlMain", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "blockNuclearControlMain", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "blockNuclearControlMain", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "blockNuclearControlMain", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "blockNuclearControlMain", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "blockNuclearControlMain", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "blockNuclearControlMain", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "blockNuclearControlMain", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "blockNuclearControlLight", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "blockNuclearControlLight", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "ItemToolThermometer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "ItemToolDigitalThermometer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "ItemRemoteSensorKit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "ItemEnergySensorKit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "ItemMultipleSensorKit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "ItemMultipleSensorKit", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "ItemMultipleSensorKit", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "KitAppeng", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "ItemTimeCard", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "ItemUpgrade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "ItemUpgrade", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "ItemTextCard", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "networkLink", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "remoteMonitor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("IC2NuclearControl", "ItemVanilliaKit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "hangglider", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "generic", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "luggage", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "autoenchantmenttable", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "autoanvil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "xpdrain", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "path", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "fan", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "elevator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "elevator_rotating", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "vacuumhopper", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "sprinkler", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "guide", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "builder_guide", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "blockPlacer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "blockbreaker", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "itemDropper", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "sleepingBag", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "ropeladder", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "ladder", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "beartrap", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "cannon", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "sponge", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "goldenegg", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "village_highlighter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "sky", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "sky", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "projector", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "paintmixer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "canvas", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "xpbottler", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "generic", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "generic", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "drawingtable", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "imaginary", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "imaginary", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "xpshower", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "scaffolding", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "craneControl", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "generic", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "generic", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "generic", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "generic", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "craneBackpack", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "generic", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "generic", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "emptyMap", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "generic", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "cartographer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "slimalyzer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "sonicglasses", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "devnull", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "pedometer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "tastyClay", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("OpenBlocks", "cursor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openglasses", "openglassesterminal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openglasses", "openglasses", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "baseTierWood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "baseTierOneBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "baseTierTwoBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "baseTierThreeBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "baseTierFourBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "sensorTierOneItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "sensorTierTwoItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "sensorTierThreeItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "sensorTierFiveItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "sensorTierFourItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "chamberTierOne", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "chamberTierTwo", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "chamberTierThree", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "chamberTierFour", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "chamberTierFive", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "barrelTierOne", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "barrelTierTwo", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "barrelTierThree", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "barrelTierFour", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "barrelTierFive", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "disposeItemTurret", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "potatoCannonTurret", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "machineGunTurret", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "incendiaryTurret", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "grenadeTurret", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "relativisticTurret", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "rocketTurret", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "teleporterTurret", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "railGunTurret", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "laserTurret", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "hardWallTierOne", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "hardWallTierTwo", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "hardWallTierThree", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "hardWallTierFour", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "hardWallTierFive", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "fenceTierOne", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "fenceTierTwo", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "fenceTierThree", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "fenceTierFour", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "fenceTierFive", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "ioBus", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "expanderPowerTierOne", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "expanderPowerTierTwo", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "expanderPowerTierThree", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "expanderPowerTierFour", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "expanderPowerTierFive", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "expanderInvTierOne", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "expanderInvTierTwo", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "expanderInvTierThree", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "expanderInvTierFour", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "expanderInvTierFive", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "leverBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "bulletCraftable", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "blazingClayCraftable", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "grenadeCraftable", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "rocketCraftable", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "ferroSlug", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "accuraccyUpgradeItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "fireRateUpgradeItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "rangeUpgradeItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "scattershotUpgradeItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "efficiencyUpgradeItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "damageAmpAddon", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "solarPanelAddon", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "serialPortAddon", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "concealerAddon", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "recyclerAddon", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "potentiaAddon", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("openmodularturrets", "redstoneReactorAddon", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine1", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine1", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine2", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine2", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine2", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine2", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine2", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine2", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.solar_panel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.battery", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 56, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Exploration", "projectred.exploration.stone", 1, 11, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.backpack", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Exploration", "projectred.exploration.barrel", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transmission", "projectred.transmission.wire", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Exploration", "projectred.exploration.sawgold", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Exploration", "projectred.exploration.sawruby", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.sawsapphire", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.sawperidot", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.rubyboots", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.rubychestplate", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.rubyhelmet", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.rubyleggings", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.sapphireboots", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.sapphirechestplate", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.sapphirehelmet", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.sapphireleggings", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.peridotboots", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.peridotchestplate", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.peridothelmet", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Exploration", "projectred.exploration.peridotleggings", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 41, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 42, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 43, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 40, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 57, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 58, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.jetpack", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 19, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 20, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 21, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 22, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 23, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 24, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 25, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 26, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 27, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 28, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 29, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 30, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 31, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 32, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 33, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 34, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.part", 1, 44, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.screwdriver", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.wiredebugger", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Core", "projectred.core.datacard", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Expansion", "projectred.expansion.electric_screwdriver", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.pipe", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.pipe", 1, 1, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.pipe", 1, 2, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.pipe", 1, 3, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.pipe", 1, 4, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.pipe", 1, 5, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.pipe", 1, 6, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.pipe", 1, 7, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.pipe", 1, 8, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.pipe", 1, 9, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.pipe", 1, 10, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.routingchip", 1, 0, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.routingchip", 1, 1, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.routingchip", 1, 2, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.routingchip", 1, 3, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.routingchip", 1, 4, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.routingchip", 1, 5, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.routingchip", 1, 6, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.routingchip", 1, 7, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.routingchip", 1, 8, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Transportation", "projectred.transportation.routerutil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine2", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine2", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine2", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine2", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Fabrication", "projectred.integration.icblock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Fabrication", "projectred.integration.icblock", 1, 1, missing));
        removeRecipeByOutputDelayed(
                getModItem("ProjRed|Fabrication", "projectred.fabrication.icblueprint", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Fabrication", "projectred.fabrication.icchip", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("MCFrames", "mcframes.frame", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine2", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.machine2", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("ProjRed|Expansion", "projectred.expansion.plan", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "stair", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "stair", 1, 43, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "stair", 1, 40, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "stair", 1, 41, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "stair", 1, 42, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "stair", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "stair", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.stone", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.stone", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.stone", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.stone", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.stone", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.stone", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.stone", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.stone", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.stone", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.stone", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.metal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.metal", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.metal", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.metal", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.metal", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "lantern.metal", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "cube", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "fuel.coke", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "cube", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "slab", 1, 38, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "cube", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "slab", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "anvil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "track", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "track.elevator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "signal", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "signal", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "signal", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "signal", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "signal", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "signal", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "signal", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "signal", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "signal", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "signal", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "signal", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "signal", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "signal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "part.railbed", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "part.railbed", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "part.rail", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "part.tie", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "detector", 1, 16, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.gamma", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.gamma", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.gamma", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.gamma", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.gamma", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.gamma", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.gamma", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.gamma", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.gamma", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.gamma", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.epsilon", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.epsilon", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.epsilon", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.epsilon", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "post", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "post", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "post", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "post", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "post", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "part.signal.lamp", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "glass", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "part.gear", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "part.gear", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "part.gear", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "part.gear", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.alpha", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "brick.sandy", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "backpack.trackman.t1", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "backpack.iceman.t1", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "backpack.apothecary.t1", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "frame", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "tool.steel.shears", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "brick.infernal", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "upgrade.lapotron", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "cart.cargo", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "cart.track.relayer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "cart.undercutter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "cart.track.layer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "cart.track.remover", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "firestone.cut", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "firestone.refined", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "cart.redstone.flux", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.gamma", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "machine.gamma", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("Railcraft", "brick.bloodstained", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "onlineDetector", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "moonSensor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "bloodMoonSensor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "lapisLamp", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "imbuingStation", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "energyDistributor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "enderEnergyDistributor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "itemCollector", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "advancedItemCollector", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "dyeingMachine", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "playerinterface", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "filter", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "ingredient", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "fertilizedDirt", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "fluidDisplay", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "advancedFluidDisplay", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "wirelessLever", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "voidStone", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "dropFilter", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("RandomThings", "spectreKey", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "tile.remote_interface", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "tile.machine", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "tile.machine", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "skylight", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "intelligentWorkbench", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "item.wireless_transmitter", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "item.chip.location", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "item.io_tool", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "item.blank_plate", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "item.chip.transfer", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "item.chip.upgrade", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "item.pda", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "item.remoteAccessor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("RIO", "item.linker", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "stargateRing", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "stargateRing", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "stargateBase", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "stargateController", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "naquadahBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "naquadahOre", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "ic2PowerUnit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "rfPowerUnit", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "ocInterface", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "naquadahIngot", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "naquadah", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "sgChevronUpgrade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "sgIrisUpgrade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "sgIrisBlade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("SGCraft", "ic2Capacitor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockCartAssembler", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockCargoManager", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockDistributor", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockDetector", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockActivator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockDetector", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockDetector", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockDetector", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockDetector", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockAdvDetector", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockJunction", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockMetalStorage", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockMetalStorage", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockMetalStorage", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 22, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 47, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 49, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 23, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 82, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 37, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 38, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 39, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 81, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 42, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 43, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 20, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 80, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 81, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 83, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 84, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 44, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 45, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 56, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 69, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 70, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 44, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 58, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 30, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 31, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 32, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 34, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 35, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 36, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 37, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 38, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 39, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 84, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 79, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 80, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 61, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 62, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 63, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 28, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 21, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 22, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 23, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 24, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 51, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 52, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 53, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 58, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 88, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "BlockLiquidManager", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 24, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 29, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 26, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 48, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 33, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 40, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 59, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 16, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 17, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 18, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "upgrade", 1, 19, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 41, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 101, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 42, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 65, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 41, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 64, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 30, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 71, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 87, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 92, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 91, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 93, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 28, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 25, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 27, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 26, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 29, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 33, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 34, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 31, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 43, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 59, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 36, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 27, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 60, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 57, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 25, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 49, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 85, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 68, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 32, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 16, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 95, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 18, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 19, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 20, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 40, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 75, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 77, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 78, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 82, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 83, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 86, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 89, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 94, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "CartModule", 1, 99, missing));
        removeRecipeByOutputDelayed(getModItem("StevesCarts", "ModuleComponents", 1, 17, missing));
        removeRecipeByOutputDelayed(getModItem("StevesAddons", "cable_rf", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesAddons", "cable_ae", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesAddons", "duplicator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("StevesAddons", "labeler", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TaintedMagic", "ItemShadowmetalHoe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TaintedMagic", "ItemShadowmetalPick", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TaintedMagic", "ItemShadowmetalAxe", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TaintedMagic", "ItemShadowmetalSpade", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TaintedMagic", "ItemShadowmetalSword", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "blockCrystal", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "blockCosmeticOpaque", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "blockCosmeticOpaque", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "WandCap", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "WandCasting", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemResource", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "blockCosmeticSolid", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "blockCandle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemNugget", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemResource", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemNugget", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemThaumometer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "blockTable", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemBaubleBlanks", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemBaubleBlanks", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemBaubleBlanks", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "blockCosmeticSolid", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "blockMetalDevice", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "blockCosmeticSlabStone", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "blockTaint", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemResource", 1, 16, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemNugget", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemAxeVoid", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemSwordVoid", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemPickVoid", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemShovelVoid", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemHoeVoid", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemHelmetVoid", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemChestplateVoid", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemLeggingsVoid", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("Thaumcraft", "ItemBootsVoid", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicbases", "voidFAS", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicbases", "voidShears", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicbases", "quicksilverBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "wireless.essentia.terminal", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "part.base", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "part.base", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "storage.casing", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "storage.essentia", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "storage.essentia", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "storage.essentia", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "storage.essentia", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "storage.essentia", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "storage.essentia", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "storage.essentia", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "storage.essentia", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "storage.essentia", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "storage.essentia", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("thaumicenergistics", "storage.essentia", 1, 10, missing));
        removeRecipeByOutputDelayed(
                getModItem("thaumicenergistics", "thaumicenergistics.block.essentia.cell.workbench", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ThaumicExploration", "blankSeal", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("ThaumicTinkerer", "shareBook", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ThaumicTinkerer", "darkQuartzItem", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ThaumicTinkerer", "darkQuartz", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ThaumicTinkerer", "darkQuartz", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("ThaumicTinkerer", "darkQuartz", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("ThaumicTinkerer", "darkQuartzSlab", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("ThaumicTinkerer", "kamiResource", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "MetalBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "MetalBlock", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "MetalBlock", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "MetalBlock", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "MetalBlock", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "MetalBlock", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "MetalBlock", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "MetalBlock", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "MetalBlock", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "MetalBlock", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "slime.gel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "slime.gel", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "LavaTank", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "LavaTankNether", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "Smeltery", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SmelteryNether", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPane", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "helmetWood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "chestplateWood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "leggingsWood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "bootsWood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "Smeltery", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SmelteryNether", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "Smeltery", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SmelteryNether", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "Smeltery", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SmelteryNether", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "Smeltery", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SmelteryNether", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SpeedBlock", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SpeedBlock", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SpeedBlock", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SpeedBlock", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SpeedBlock", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickfancy", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickfancy", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickfancy", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickfancy", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickfancy", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickfancy", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickfancy", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickfancy", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickfancy", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickfancy", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickmetal", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickmetal", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickmetal", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.multibrickmetal", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "heartCanister", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 25, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 26, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CraftedSoil", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CraftedSoil", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "toolRod", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "blankPattern", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "decoration.stoneladder", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "trap.punji", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 22, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "MeatBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "WoolSlab1", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "WoolSlab2", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CraftedSoil", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedSlab", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedSlab", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedSlab", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedSlab", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedSlab", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedSlab", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedSlab", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedSlab", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "Smeltery", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SmelteryNether", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "Smeltery", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SmelteryNether", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedBlockNether", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedBlock", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedBlockNether", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CastingChannel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CastingChannel", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedBlock", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SearedBlockNether", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "LavaTank", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "LavaTankNether", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "LavaTank", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "LavaTankNether", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassBlock.StainedClear", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 41, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 42, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 20, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 21, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 31, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 24, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 32, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 27, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 28, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 29, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 30, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 16, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 8, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 9, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 11, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 13, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "GlassPaneClearStained", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "knapsack", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "travelGoggles", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "travelVest", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "travelGlove", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "travelWings", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "travelBelt", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "travelBoots", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "trap.barricade.oak", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "trap.barricade.spruce", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "trap.barricade.birch", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "trap.barricade.jungle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "explosive.slime", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "slime.channel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "blood.channel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "slime.pad", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "rail.wood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CraftingStation", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CraftingSlab", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "ToolStationBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CraftingSlab", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "ToolStationBlock", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CraftingSlab", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CraftingSlab", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CraftingSlab", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "ToolForgeBlock", 1, wildcard, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CraftingSlab", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "FurnaceSlab", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CraftedSoil", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "CraftedSoil", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SpeedSlab", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SpeedSlab", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SpeedSlab", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SpeedSlab", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SpeedSlab", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SpeedSlab", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SpeedSlab", 1, 6, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "SpeedSlab", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "strangeFood", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "goldHead", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "Armor.DryingRack", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 5, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 14, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 15, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 18, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "diamondApple", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "heartCanister", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "heartCanister", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "heartCanister", 1, 4, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "materials", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "Redstone.Landmine", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "Redstone.Landmine", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "Redstone.Landmine", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("TConstruct", "Redstone.Landmine", 1, 3, missing));
        removeRecipeByOutputDelayed(getModItem("tinkersdefense", "AeonSteelIngot", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("tinkersdefense", "AeonSteelBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("tinkersdefense", "QueensGoldIngot", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("tinkersdefense", "QueensGoldBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("tinkersdefense", "DogbeariumIngot", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("tinkersdefense", "DogbeariumBlock", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TwilightForest", "item.carminite", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TwilightForest", "tile.TFTowerDevice", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("TwilightForest", "item.emptyMagicMap", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TwilightForest", "item.ironwoodRaw", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("TwilightForest", "tile.TFUncraftingTable", 1, 0, missing));

        String[] materials = new String[] { "Twilight", "Canopy", "Mangrove", "Darkwood", "Time", "Trans", "Mine",
                "Sort" };
        for (int i = 0; i < materials.length; i++) {
            removeRecipeByOutputDelayed(getModItem("TwilightForest", "tile.TFTrapDoor" + materials[i], 1, 0, missing));
            removeRecipeByOutputDelayed(getModItem("TwilightForest", "item.door" + materials[i], 1, 0, missing));
        }

        removeRecipeByOutputDelayed(getModItem("WR-CBE|Core", "obsidianStick", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Core", "stoneBowl", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Core", "retherPearl", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Core", "wirelessTransceiver", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Core", "blazeTransceiver", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Core", "recieverDish", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Core", "blazeRecieverDish", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Logic", "wirelessLogic", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Logic", "wirelessLogic", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Logic", "wirelessLogic", 1, 2, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Addons", "triangulator", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Addons", "remote", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Addons", "sniffer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Addons", "map", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Addons", "tracker", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Addons", "rep", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("WR-CBE|Addons", "psniffer", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "witchesovenidle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "fumefunnel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "filteredfumefunnel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 73, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "altar", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 1, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "kettle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "arthana", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 153, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 16, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 26, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "spinningwheel", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "distilleryidle", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 140, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 7, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 81, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 106, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 127, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 46, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 107, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "cauldronbook", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 47, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 48, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 49, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "chalkritual", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "circletalisman", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "earmuffs", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 10, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 92, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "ingredient", 1, 12, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "rubyslippers", 1, 0, missing));
        removeRecipeByOutputDelayed(getModItem("witchery", "snowpressureplate", 1, 0, missing));

        removeRecipeByOutputDelayed("nuggetLead");
        removeRecipeByOutputDelayed("nuggetSilver");
        removeRecipeByOutputDelayed("nuggetTin");
        removeRecipeByOutputDelayed("nuggetCopper");
        removeRecipeByOutputDelayed("nuggetSteel");
        removeRecipeByOutputDelayed("nuggetPulsatingIron");
        removeRecipeByOutputDelayed("nuggetVibrantAlloy");
        removeRecipeByOutputDelayed("nuggetDraconium");
        removeRecipeByOutputDelayed("nuggetDraconiumAwakened");
        removeRecipeByOutputDelayed("blockObsidian");
        removeRecipeByOutputDelayed("nuggetGold");
        removeRecipeByOutputDelayed("nuggetIron");
        removeRecipeByOutputDelayed("torchStone");

        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L));
        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L));
        removeRecipeShapelessDelayed(getModItem("minecraft", "dye", 1, 4, missing));
        removeRecipeShapelessDelayed(
                getModItem("minecraft", "dye", 3, 15, missing),
                getModItem("BiomesOPlenty", "bones", 1, 0, missing));
        removeRecipeShapelessDelayed(
                getModItem("minecraft", "dye", 6, 15, missing),
                getModItem("BiomesOPlenty", "bones", 1, 1, missing));
        removeRecipeShapelessDelayed(
                getModItem("minecraft", "dye", 12, 15, missing),
                getModItem("BiomesOPlenty", "bones", 1, 2, missing));
        removeRecipeShapelessDelayed(
                getModItem("BiomesOPlenty", "misc", 1, 9, missing),
                getModItem("BiomesOPlenty", "flowers", 1, 2, missing));
        removeRecipeShapelessDelayed(
                getModItem("BiomesOPlenty", "misc", 1, 8, missing),
                getModItem("BiomesOPlenty", "flowers", 1, 9, missing));
        removeRecipeShapelessDelayed(
                getModItem("BiomesOPlenty", "misc", 1, 8, missing),
                getModItem("BiomesOPlenty", "flowers2", 1, 1, missing));
        removeRecipeShapelessDelayed(
                getModItem("BiomesOPlenty", "misc", 1, 5, missing),
                getModItem("BiomesOPlenty", "flowers2", 1, 5, missing));
        removeRecipeShapelessDelayed(
                getModItem("BiomesOPlenty", "misc", 1, 5, missing),
                getModItem("BiomesOPlenty", "mushrooms", 1, 2, missing));
        removeRecipeShapelessDelayed(
                getModItem("BiomesOPlenty", "misc", 1, 7, missing),
                getModItem("BiomesOPlenty", "moss", 1, 0, missing));
        removeRecipeShapelessDelayed(
                getModItem("BiomesOPlenty", "misc", 1, 6, missing),
                getModItem("BiomesOPlenty", "mushrooms", 1, 4, missing));
        removeRecipeShapelessDelayed(
                getModItem("BiomesOPlenty", "misc", 1, 6, missing),
                getModItem("BiomesOPlenty", "plants", 1, 7, missing));
        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L));
        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L));
        removeRecipeShapelessDelayed(getModItem("minecraft", "dye", 1, 4, missing));
        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Apatite, 1L));
        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Apatite, 1L));
        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Diamond, 1L));
        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L));
        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L));
        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.NetherStar, 1L));
        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Iridium, 1L));
        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Saltpeter, 1L));
        removeRecipeShapelessDelayed("dustEnderPearl");
        removeRecipeShapelessDelayed("gemEnderPearl");
        removeRecipeShapelessDelayed("dustEnderEye");
        removeRecipeShapelessDelayed("gemEnderEye");
        removeRecipeShapelessDelayed("dustRuby");
        removeRecipeShapelessDelayed("gemRuby");
        removeRecipeShapelessDelayed("dustSapphire");
        removeRecipeShapelessDelayed("gemSapphire");
        removeRecipeShapelessDelayed("dustGreenSapphire");
        removeRecipeShapelessDelayed("gemGreenSapphire");
        removeRecipeShapelessDelayed("dustOlivine");
        removeRecipeShapelessDelayed("gemOlivine");
        removeRecipeShapelessDelayed("dustTopaz");
        removeRecipeShapelessDelayed("gemTopaz");
        removeRecipeShapelessDelayed("dustTanzanite");
        removeRecipeShapelessDelayed("gemTanzanite");
        removeRecipeShapelessDelayed("dustAmethyst");
        removeRecipeShapelessDelayed("gemAmethyst");
        removeRecipeShapelessDelayed("dustJasper");
        removeRecipeShapelessDelayed("gemJasper");
        removeRecipeShapelessDelayed("dustGarnetYellow");
        removeRecipeShapelessDelayed("gemGarnetYellow");
        removeRecipeShapelessDelayed("dustGarnetRed");
        removeRecipeShapelessDelayed("gemGarnetRed");
        removeRecipeShapelessDelayed("dustForce");
        removeRecipeShapelessDelayed("gemForce");
        removeRecipeShapelessDelayed("dustForcillium");
        removeRecipeShapelessDelayed("gemForcillium");
        removeRecipeShapelessDelayed("dustForcicium");
        removeRecipeShapelessDelayed("gemForcicium");
        removeRecipeShapelessDelayed("dustDilithium");
        removeRecipeShapelessDelayed("gemDilithium");
        removeRecipeShapelessDelayed("dustAmber");
        removeRecipeShapelessDelayed("gemAmber");
        removeRecipeShapelessDelayed("dustFoolsRuby");
        removeRecipeShapelessDelayed("gemFoolsRuby");
        removeRecipeShapelessDelayed("dustBlueTopaz");
        removeRecipeShapelessDelayed("gemBlueTopaz");
        removeRecipeShapelessDelayed("dustMonazite");
        removeRecipeShapelessDelayed("gemMonazite");
        removeRecipeShapelessDelayed("dustQuartzite");
        removeRecipeShapelessDelayed("gemQuartzite");
        removeRecipeShapelessDelayed("dustLazurite");
        removeRecipeShapelessDelayed("gemLazurite");
        removeRecipeShapelessDelayed("dustSodalite");
        removeRecipeShapelessDelayed("gemSodalite");
        removeRecipeShapelessDelayed("dustNiter");
        removeRecipeShapelessDelayed("gemNiter");
        removeRecipeShapelessDelayed("dustTricalciumPhosphate");
        removeRecipeShapelessDelayed("gemTricalciumPhosphate");
        removeRecipeShapelessDelayed("dustLignite");
        removeRecipeShapelessDelayed("gemLignite");
        removeRecipeShapelessDelayed("dustInfusedEntropy");
        removeRecipeShapelessDelayed("gemInfusedEntropy");
        removeRecipeShapelessDelayed("dustInfusedOrder");
        removeRecipeShapelessDelayed("gemInfusedOrder");
        removeRecipeShapelessDelayed("dustVinteum");
        removeRecipeShapelessDelayed("gemVinteum");
        removeRecipeShapelessDelayed("dustInfusedAir");
        removeRecipeShapelessDelayed("gemInfusedAir");
        removeRecipeShapelessDelayed("dustInfusedFire");
        removeRecipeShapelessDelayed("gemInfusedFire");
        removeRecipeShapelessDelayed("dustInfusedEarth");
        removeRecipeShapelessDelayed("gemInfusedEarth");
        removeRecipeShapelessDelayed("dustInfusedWater");
        removeRecipeShapelessDelayed("gemInfusedWater");
        removeRecipeShapelessDelayed("dustCoal"/* ERRORSTACK <- itemcount */, "blockCoal");
        removeRecipeShapelessDelayed(
                getModItem("minecraft", "coal", 9, 1, missing),
                GT_OreDictUnificator.get(OrePrefixes.block, Materials.Charcoal, 1L));
        removeRecipeShapelessDelayed(
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Charcoal, 9L),
                GT_OreDictUnificator.get(OrePrefixes.block, Materials.Charcoal, 1L));
        removeRecipeShapelessDelayed(getModItem("harvestcraft", "cottonItem", 1, 0, missing));
        removeRecipeShapelessDelayed(
                getModItem("IC2", "itemCofeePowder", 1, 0, missing),
                getModItem("IC2", "itemCofeeBeans", 1, 0, missing));
        removeRecipeShapelessDelayed(getModItem("Thaumcraft", "ItemShard", 1, 0, missing));
        removeRecipeShapelessDelayed(getModItem("Thaumcraft", "ItemShard", 1, 1, missing));
        removeRecipeShapelessDelayed(getModItem("Thaumcraft", "ItemShard", 1, 2, missing));
        removeRecipeShapelessDelayed(getModItem("Thaumcraft", "ItemShard", 1, 3, missing));
        removeRecipeShapelessDelayed(getModItem("Thaumcraft", "ItemShard", 1, 4, missing));
        removeRecipeShapelessDelayed(getModItem("Thaumcraft", "ItemShard", 1, 5, missing));
        removeRecipeShapelessDelayed(getModItem("minecraft", "string", 1, 0, missing));
        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lapis, 1L));
        removeRecipeShapelessDelayed(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Emerald, 1L));
        removeRecipeShapelessDelayed(
                getModItem("minecraft", "gunpowder", 1, 0, missing),
                getModItem("minecraft", "coal", 1, 1, missing),
                getModItem("minecraft", "coal", 1, 1, missing),
                getModItem("minecraft", "coal", 1, 1, missing),
                getModItem("minecraft", "glowstone_dust", 1, 0, missing));
        removeRecipeShapelessDelayed(
                getModItem("minecraft", "dye", 3, 15, missing),
                getModItem("minecraft", "bone", 1, 0, missing));
        removeRecipeShapelessDelayed(getModItem("minecraft", "nether_star", 1, 0, missing));
        removeRecipeShapelessDelayed(getModItem("minecraft", "sand", 1, 0, missing));
        removeRecipeShapelessDelayed(getModItem("minecraft", "glowstone_dust", 1, 0, missing));
        removeRecipeShapelessDelayed(getModItem("minecraft", "glass", 1, 0, missing));
        removeRecipeShapelessDelayed(
                getModItem("Natura", "barleyFood", 1, 8, missing),
                getModItem("Natura", "Bluebells", 1, 0, missing));
        removeRecipeShapelessDelayed(getModItem("Thaumcraft", "ItemResource", 1, 6, missing));
        removeRecipeShapelessDelayed(
                getModItem("Thaumcraft", "ItemInkwell", 1, 0, missing),
                getModItem("minecraft", "glass_bottle", 1, 0, missing),
                getModItem("minecraft", "feather", 1, 0, missing),
                "dyeBlack");
        removeRecipeShapelessDelayed(getModItem("TwilightForest", "item.fieryIngot", 1, 0, missing));
        removeRecipeShapelessDelayed(
                getModItem("witchery", "ingredient", 1, 130, missing),
                getModItem("witchery", "ingredient", 1, 130, missing),
                getModItem("minecraft", "magma_cream", 1, 0, missing),
                getModItem("minecraft", "blaze_powder", 1, 0, missing));
        removeRecipeShapelessDelayed(
                "ingotThauminite",
                getModItem("thaumicbases", "resource", 1, 0, missing),
                getModItem("thaumicbases", "resource", 1, 0, missing),
                getModItem("thaumicbases", "resource", 1, 0, missing),
                getModItem("thaumicbases", "resource", 1, 0, missing),
                getModItem("thaumicbases", "resource", 1, 0, missing),
                getModItem("thaumicbases", "resource", 1, 0, missing),
                getModItem("thaumicbases", "resource", 1, 0, missing),
                getModItem("thaumicbases", "resource", 1, 0, missing),
                getModItem("thaumicbases", "resource", 1, 0, missing));

        removeRecipeShapedDelayed(getModItem("BinnieCore", "storage", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("BinnieCore", "storage", 1, 1, missing));
        removeRecipeShapedDelayed(getModItem("BinnieCore", "storage", 1, 2, missing));
        removeRecipeShapedDelayed(getModItem("BinnieCore", "storage", 1, 3, missing));
        removeRecipeShapedDelayed(getModItem("BinnieCore", "storage", 1, 4, missing));
        removeRecipeShapedDelayed(getModItem("BinnieCore", "storage", 1, 5, missing));
        removeRecipeShapedDelayed(
                getModItem("minecraft", "wheat", 1, 0, missing),
                new Object[] { getModItem("BiomesOPlenty", "plants", 1, 6, missing),
                        getModItem("BiomesOPlenty", "plants", 1, 6, missing),
                        getModItem("BiomesOPlenty", "plants", 1, 6, missing) },
                new Object[0],
                new Object[0]);
        removeRecipeShapedDelayed(
                getModItem("EnderIO", "itemAlloy", 1, 6, missing),
                new Object[] { "nuggetDarkSteel", "nuggetDarkSteel", "nuggetDarkSteel" },
                new Object[] { "nuggetDarkSteel", "nuggetDarkSteel", "nuggetDarkSteel" },
                new Object[] { "nuggetDarkSteel", "nuggetDarkSteel", "nuggetDarkSteel" });
        removeRecipeShapedDelayed(
                getModItem("EnderIO", "itemAlloy", 1, 8, missing),
                new Object[] { getModItem("EnderIO", "itemMaterial", 1, 11, missing),
                        getModItem("EnderIO", "itemMaterial", 1, 11, missing),
                        getModItem("EnderIO", "itemMaterial", 1, 11, missing) },
                new Object[] { getModItem("EnderIO", "itemMaterial", 1, 11, missing),
                        getModItem("EnderIO", "itemMaterial", 1, 11, missing),
                        getModItem("EnderIO", "itemMaterial", 1, 11, missing) },
                new Object[] { getModItem("EnderIO", "itemMaterial", 1, 11, missing),
                        getModItem("EnderIO", "itemMaterial", 1, 11, missing),
                        getModItem("EnderIO", "itemMaterial", 1, 11, missing) });
        removeRecipeShapedDelayed(
                getModItem("minecraft", "ender_pearl", 1, 0, missing),
                new Object[] { getModItem("EnderIO", "itemPowderIngot", 1, 5, missing),
                        getModItem("EnderIO", "itemPowderIngot", 1, 5, missing),
                        getModItem("EnderIO", "itemPowderIngot", 1, 5, missing) },
                new Object[] { getModItem("EnderIO", "itemPowderIngot", 1, 5, missing),
                        getModItem("EnderIO", "itemPowderIngot", 1, 5, missing),
                        getModItem("EnderIO", "itemPowderIngot", 1, 5, missing) },
                new Object[] { getModItem("EnderIO", "itemPowderIngot", 1, 5, missing),
                        getModItem("EnderIO", "itemPowderIngot", 1, 5, missing),
                        getModItem("EnderIO", "itemPowderIngot", 1, 5, missing) });
        removeRecipeShapedDelayed(
                getModItem("minecraft", "ender_pearl", 1, 0, missing),
                new Object[] { null, getModItem("EnderZoo", "enderFragment", 1, 0, missing), null },
                new Object[] { getModItem("EnderZoo", "enderFragment", 1, 0, missing),
                        getModItem("EnderZoo", "enderFragment", 1, 0, missing),
                        getModItem("EnderZoo", "enderFragment", 1, 0, missing) },
                new Object[] { null, getModItem("EnderZoo", "enderFragment", 1, 0, missing), null });
        removeRecipeShapedDelayed(
                getModItem("ExtraUtilities", "unstableingot", 1, 0, missing),
                new Object[] { getModItem("minecraft", "iron_ingot", 1, 0, missing) },
                new Object[] { getModItem("ExtraUtilities", "divisionSigil", 1, 0, missing) },
                new Object[] { getModItem("minecraft", "diamond", 1, 0, missing) });
        removeRecipeShapedDelayed(
                getModItem("ForbiddenMagic", "FMResource", 9, 0, missing),
                new Object[] { getModItem("minecraft", "emerald", 1, 0, missing) },
                new Object[0],
                new Object[0]);
        removeRecipeShapedDelayed(getModItem("ForbiddenMagic", "FMResource", 2, 1, missing));
        removeRecipeShapedDelayed(getModItem("gendustry", "GeneSampleBlank", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("gendustry", "GeneTemplate", 1, 0, missing));
        removeRecipeShapedDelayed(
                GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Apatite, 1L),
                new Object[] { getModItem("MagicBees", "beeNugget", 1, 7), getModItem("MagicBees", "beeNugget", 1, 7),
                        getModItem("MagicBees", "beeNugget", 1, 7) },
                new Object[] { getModItem("MagicBees", "beeNugget", 1, 7), getModItem("MagicBees", "beeNugget", 1, 7),
                        getModItem("MagicBees", "beeNugget", 1, 7) },
                new Object[] { getModItem("MagicBees", "beeNugget", 1, 7), getModItem("MagicBees", "beeNugget", 1, 7),
                        getModItem("MagicBees", "beeNugget", 1, 7) });
        removeRecipeShapedDelayed(
                "ingotSilver",
                new Object[] { "nuggetSilver", "nuggetSilver", "nuggetSilver" },
                new Object[] { "nuggetSilver", "nuggetSilver", "nuggetSilver" },
                new Object[] { "nuggetSilver", "nuggetSilver", "nuggetSilver" });
        removeRecipeShapedDelayed(
                "ingotLead",
                new Object[] { "nuggetLead", "nuggetLead", "nuggetLead" },
                new Object[] { "nuggetLead", "nuggetLead", "nuggetLead" },
                new Object[] { "nuggetLead", "nuggetLead", "nuggetLead" });
        removeRecipeShapedDelayed(
                "ingotDraconium",
                new Object[] { "nuggetDraconium", "nuggetDraconium", "nuggetDraconium" },
                new Object[] { "nuggetDraconium", "nuggetDraconium", "nuggetDraconium" },
                new Object[] { "nuggetDraconium", "nuggetDraconium", "nuggetDraconium" });
        removeRecipeShapedDelayed(
                "ingotDraconiumAwakened",
                new Object[] { "nuggetDraconiumAwakened", "nuggetDraconiumAwakened", "nuggetDraconiumAwakened" },
                new Object[] { "nuggetDraconiumAwakened", "nuggetDraconiumAwakened", "nuggetDraconiumAwakened" },
                new Object[] { "nuggetDraconiumAwakened", "nuggetDraconiumAwakened", "nuggetDraconiumAwakened" });
        removeRecipeShapedDelayed(
                "ingotVibrantAlloy",
                new Object[] { "nuggetVibrantAlloy", "nuggetVibrantAlloy", "nuggetVibrantAlloy" },
                new Object[] { "nuggetVibrantAlloy", "nuggetVibrantAlloy", "nuggetVibrantAlloy" },
                new Object[] { "nuggetVibrantAlloy", "nuggetVibrantAlloy", "nuggetVibrantAlloy" });
        removeRecipeShapedDelayed(
                "ingotPulsatingIron",
                new Object[] { "nuggetPulsatingIron", "nuggetPulsatingIron", "nuggetPulsatingIron" },
                new Object[] { "nuggetPulsatingIron", "nuggetPulsatingIron", "nuggetPulsatingIron" },
                new Object[] { "nuggetPulsatingIron", "nuggetPulsatingIron", "nuggetPulsatingIron" });
        removeRecipeShapedDelayed(getModItem("minecraft", "stone_button", 1, 0, missing));
        removeRecipeShapedDelayed(
                getModItem("minecraft", "string", 1, 0, missing),
                new Object[] { "cropCotton", "cropCotton", "cropCotton" },
                new Object[0],
                new Object[0]);
        removeRecipeShapedDelayed(getModItem("minecraft", "speckled_melon", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("minecraft", "stone_slab", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("minecraft", "stone_slab", 1, 1, missing));
        removeRecipeShapedDelayed(getModItem("minecraft", "stone_slab", 1, 3, missing));
        removeRecipeShapedDelayed(getModItem("minecraft", "stone_slab", 1, 4, missing));
        removeRecipeShapedDelayed(getModItem("minecraft", "stone_slab", 1, 5, missing));
        removeRecipeShapedDelayed(getModItem("minecraft", "stone_slab", 1, 6, missing));
        removeRecipeShapedDelayed(getModItem("minecraft", "stone_slab", 1, 7, missing));
        removeRecipeShapedDelayed(getModItem("minecraft", "bookshelf", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("minecraft", "packed_ice", 4, 0, missing));
        removeRecipeShapedDelayed(
                getModItem("minecraft", "gunpowder", 1, 0, missing),
                new Object[] { getModItem("Natura", "barleyFood", 1, 4, missing),
                        getModItem("Natura", "barleyFood", 1, 4, missing), null },
                new Object[] { getModItem("Natura", "barleyFood", 1, 4, missing),
                        getModItem("Natura", "barleyFood", 1, 4, missing), null },
                new Object[0]);
        removeRecipeShapedDelayed(getModItem("minecraft", "chest_minecart", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("minecraft", "furnace_minecart", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("minecraft", "hopper_minecart", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("minecraft", "tnt_minecart", 1, 0, missing));
        removeRecipeShapedDelayed(
                getModItem("minecraft", "iron_ingot", 1, 0, missing),
                new Object[] { "nuggetIron", "nuggetIron", "nuggetIron" },
                new Object[] { "nuggetIron", "nuggetIron", "nuggetIron" },
                new Object[] { "nuggetIron", "nuggetIron", "nuggetIron" });
        removeRecipeShapedDelayed("nuggetIron");
        removeRecipeShapedDelayed(
                getModItem("minecraft", "gold_ingot", 1, 0, missing),
                new Object[] { "nuggetGold", "nuggetGold", "nuggetGold" },
                new Object[] { "nuggetGold", "nuggetGold", "nuggetGold" },
                new Object[] { "nuggetGold", "nuggetGold", "nuggetGold" });
        removeRecipeShapedDelayed(
                getModItem("minecraft", "wooden_slab", 6, 0, missing),
                new Object[] { getModItem("minecraft", "planks", 1, 0, missing),
                        getModItem("minecraft", "planks", 1, 0, missing),
                        getModItem("minecraft", "planks", 1, 0, missing) },
                new Object[0],
                new Object[0]);
        removeRecipeShapedDelayed(
                getModItem("minecraft", "blaze_powder", 1, 0, missing),
                new Object[] { getModItem("Thaumcraft", "blockCustomPlant", 1, 3, missing) },
                new Object[0],
                new Object[0]);
        removeRecipeShapedDelayed(
                getModItem("minecraft", "leather_helmet", 1, 0, missing),
                new Object[] { getModItem("harvestcraft", "wovencottonItem", 1, 0, missing),
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing),
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing) },
                new Object[] { getModItem("harvestcraft", "wovencottonItem", 1, 0, missing), null,
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing) },
                new Object[0]);
        removeRecipeShapedDelayed(
                getModItem("minecraft", "leather_chestplate", 1, 0, missing),
                new Object[] { getModItem("harvestcraft", "wovencottonItem", 1, 0, missing), null,
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing) },
                new Object[] { getModItem("harvestcraft", "wovencottonItem", 1, 0, missing),
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing),
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing) },
                new Object[] { getModItem("harvestcraft", "wovencottonItem", 1, 0, missing),
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing),
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing) });
        removeRecipeShapedDelayed(
                getModItem("minecraft", "leather_leggings", 1, 0, missing),
                new Object[] { getModItem("harvestcraft", "wovencottonItem", 1, 0, missing),
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing),
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing) },
                new Object[] { getModItem("harvestcraft", "wovencottonItem", 1, 0, missing), null,
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing) },
                new Object[] { getModItem("harvestcraft", "wovencottonItem", 1, 0, missing), null,
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing) });
        removeRecipeShapedDelayed(
                getModItem("minecraft", "leather_boots", 1, 0, missing),
                new Object[] { getModItem("harvestcraft", "wovencottonItem", 1, 0, missing), null,
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing) },
                new Object[] { getModItem("harvestcraft", "wovencottonItem", 1, 0, missing), null,
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing) },
                new Object[0]);
        removeRecipeShapedDelayed(
                getModItem("minecraft", "emerald", 1, 0, missing),
                new Object[] { getModItem("ForbiddenMagic", "FMResource", 1, 0, missing),
                        getModItem("ForbiddenMagic", "FMResource", 1, 0, missing),
                        getModItem("ForbiddenMagic", "FMResource", 1, 0, missing) },
                new Object[] { getModItem("ForbiddenMagic", "FMResource", 1, 0, missing),
                        getModItem("ForbiddenMagic", "FMResource", 1, 0, missing),
                        getModItem("ForbiddenMagic", "FMResource", 1, 0, missing) },
                new Object[] { getModItem("ForbiddenMagic", "FMResource", 1, 0, missing),
                        getModItem("ForbiddenMagic", "FMResource", 1, 0, missing),
                        getModItem("ForbiddenMagic", "FMResource", 1, 0, missing) });
        removeRecipeShapedDelayed(
                getModItem("minecraft", "diamond", 1, 0, missing),
                new Object[] { getModItem("MagicBees", "beeNugget", 1, 5, missing),
                        getModItem("MagicBees", "beeNugget", 1, 5, missing),
                        getModItem("MagicBees", "beeNugget", 1, 5, missing) },
                new Object[] { getModItem("MagicBees", "beeNugget", 1, 5, missing),
                        getModItem("MagicBees", "beeNugget", 1, 5, missing),
                        getModItem("MagicBees", "beeNugget", 1, 5, missing) },
                new Object[] { getModItem("MagicBees", "beeNugget", 1, 5, missing),
                        getModItem("MagicBees", "beeNugget", 1, 5, missing),
                        getModItem("MagicBees", "beeNugget", 1, 5, missing) });
        removeRecipeShapedDelayed(
                getModItem("minecraft", "paper", 2, 0, missing),
                new Object[] { getModItem("minecraft", "reeds", 1, 0, missing),
                        getModItem("minecraft", "reeds", 1, 0, missing),
                        getModItem("minecraft", "reeds", 1, 0, missing) },
                new Object[0],
                new Object[0]);
        removeRecipeShapedDelayed(getModItem("minecraft", "sand", 1, 0, missing));
        removeRecipeShapedDelayed(
                getModItem("minecraft", "leather", 1, 0, missing),
                new Object[] { getModItem("Natura", "barleyFood", 1, 6, missing),
                        getModItem("Natura", "barleyFood", 1, 6, missing) },
                new Object[] { getModItem("Natura", "barleyFood", 1, 6, missing),
                        getModItem("Natura", "barleyFood", 1, 6, missing) },
                new Object[0]);
        removeRecipeShapedDelayed(getModItem("OpenBlocks", "paintBrush", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("OpenBlocks", "goldenEye", 1, wildcard, missing));
        removeRecipeShapedDelayed(getModItem("Railcraft", "cart.energy.batbox", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("Railcraft", "cart.energy.cesu", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("Railcraft", "cart.energy.mfe", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("Railcraft", "cart.tank", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("Railcraft", "cart.anchor.personal", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("Railcraft", "cart.anchor", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("Railcraft", "cart.work", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("Railcraft", "tool.electric.meter", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("Railcraft", "armor.goggles", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("Railcraft", "tool.magnifying.glass", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("Railcraft", "tool.signal.tuner", 1, 0, missing));
        removeRecipeShapedDelayed(getModItem("Railcraft", "tool.surveyor", 1, 0, missing));
        removeRecipeShapedDelayed(
                getModItem("Thaumcraft", "ItemResource", 1, 6, missing),
                new Object[] { getModItem("Thaumcraft", "blockCosmeticOpaque", 1, 0, missing) },
                new Object[0],
                new Object[0]);
        removeRecipeShapedDelayed(
                getModItem("Thaumcraft", "ItemResource", 1, 6, missing),
                new Object[] { getModItem("Thaumcraft", "blockCosmeticOpaque", 1, 1, missing) },
                new Object[0],
                new Object[0]);
        removeRecipeShapedDelayed(
                "ingotThaumium",
                new Object[] { "nuggetThaumium", "nuggetThaumium", "nuggetThaumium" },
                new Object[] { "nuggetThaumium", "nuggetThaumium", "nuggetThaumium" },
                new Object[] { "nuggetThaumium", "nuggetThaumium", "nuggetThaumium" });
        removeRecipeShapedDelayed(getModItem("thaumicbases", "crystalSlab", 1, wildcard, missing));
        removeRecipeShapedDelayed(getModItem("thaumicbases", "genericSlab", 1, wildcard, missing));
        removeRecipeShapedDelayed(
                "ingotTin",
                new Object[] { "nuggetTin", "nuggetTin", "nuggetTin" },
                new Object[] { "nuggetTin", "nuggetTin", "nuggetTin" },
                new Object[] { "nuggetTin", "nuggetTin", "nuggetTin" });
        removeRecipeShapedDelayed(
                "ingotCopper",
                new Object[] { "nuggetCopper", "nuggetCopper", "nuggetCopper" },
                new Object[] { "nuggetCopper", "nuggetCopper", "nuggetCopper" },
                new Object[] { "nuggetCopper", "nuggetCopper", "nuggetCopper" });
        removeRecipeShapedDelayed(
                "ingotSteel",
                new Object[] { "nuggetSteel", "nuggetSteel", "nuggetSteel" },
                new Object[] { "nuggetSteel", "nuggetSteel", "nuggetSteel" },
                new Object[] { "nuggetSteel", "nuggetSteel", "nuggetSteel" });
        removeRecipeShapedDelayed(
                "ingotBronze",
                new Object[] { "nuggetBronze", "nuggetBronze", "nuggetBronze" },
                new Object[] { "nuggetBronze", "nuggetBronze", "nuggetBronze" },
                new Object[] { "nuggetBronze", "nuggetBronze", "nuggetBronze" });
        removeRecipeShapedDelayed(
                getModItem("gregtech", "gt.metaitem.01", 1, 11019, missing),
                new Object[] { getModItem("TConstruct", "materials", 1, 22, missing),
                        getModItem("TConstruct", "materials", 1, 22, missing),
                        getModItem("TConstruct", "materials", 1, 22, missing) },
                new Object[] { getModItem("TConstruct", "materials", 1, 22, missing),
                        getModItem("TConstruct", "materials", 1, 22, missing),
                        getModItem("TConstruct", "materials", 1, 22, missing) },
                new Object[] { getModItem("TConstruct", "materials", 1, 22, missing),
                        getModItem("TConstruct", "materials", 1, 22, missing),
                        getModItem("TConstruct", "materials", 1, 22, missing) });
        removeRecipeShapedDelayed(
                getModItem("gregtech", "gt.metaitem.01", 1, 11019, missing),
                new Object[] { getModItem("TConstruct", "materials", 1, 12, missing), null, null },
                new Object[0],
                new Object[0]);

        stopBuffering();
        bufferingRecipes = false;

        final long timeToLoad = System.currentTimeMillis() - timeStart;
        MainRegistry.Logger.info("Recipes removal took " + timeToLoad + " ms.");
    }
}
