package com.dreammaster.scripts;

import static gregtech.api.util.GT_ModHandler.addShapelessCraftingRecipe;
import static gregtech.api.util.GT_ModHandler.getModItem;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sBrewingRecipes;

import java.util.Arrays;
import java.util.List;

import net.minecraftforge.fluids.FluidRegistry;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Mods;

public class ScriptAdvancedBackpacks implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Advanced Backpacks";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(Mods.AdventureBackpack.ID);
    }

    @Override
    public void loadRecipes() {

        // AUTOGENERATED

        addShapedRecipe(
                getModItem("adventurebackpack", "adventureBackpack", 1, 0, missing),
                new Object[] { getModItem("minecraft", "dye", 1, 15, missing),
                        getModItem("minecraft", "dye", 1, 15, missing), getModItem("minecraft", "dye", 1, 15, missing),
                        getModItem("minecraft", "dye", 1, 15, missing),
                        getModItem("adventurebackpack", "adventureBackpack", 1, 0, missing),
                        getModItem("minecraft", "dye", 1, 15, missing), getModItem("minecraft", "dye", 1, 15, missing),
                        getModItem("minecraft", "dye", 1, 15, missing),
                        getModItem("minecraft", "dye", 1, 15, missing) });
        addShapedRecipe(
                createItemStack("adventurebackpack", "adventureBackpack", 1, 0, "{wearableData:{type:0b}}", missing),
                new Object[] { "materialHardenedleather", "plateAluminium", "materialHardenedleather",
                        getModItem("adventurebackpack", "backpackComponent", 1, 2, missing), "chestIron",
                        getModItem("adventurebackpack", "backpackComponent", 1, 2, missing), "materialHardenedleather",
                        getModItem("adventurebackpack", "backpackComponent", 1, 1, missing),
                        "materialHardenedleather" });
        addShapedRecipe(
                createItemStack("adventurebackpack", "adventureBackpack", 1, 0, "{wearableData:{type:0b}}", missing),
                new Object[] { "materialHardenedleather", "plateAluminium", "materialHardenedleather",
                        getModItem("BuildCraft|Factory", "tankBlock", 1, 0, missing), "chestIron",
                        getModItem("BuildCraft|Factory", "tankBlock", 1, 0, missing), "materialHardenedleather",
                        getModItem("sleepingbag", "sleepingBag", 1, 0, missing), "materialHardenedleather" });
        addShapedRecipe(
                getModItem("adventurebackpack", "adventureBackpack", 1, 2, missing),
                new Object[] { "itemLeather", getModItem("IC2", "itemNightvisionGoggles", 1, 1, missing), "itemLeather",
                        getModItem("minecraft", "potion", 1, 8262, missing),
                        getModItem("adventurebackpack", "adventureBackpack", 1, 0, missing),
                        getModItem("minecraft", "potion", 1, 8262, missing), "blockWoolBlack", "blockWoolBlack",
                        "blockWoolBlack" });
        addShapedRecipe(
                getModItem("adventurebackpack", "adventureBackpack", 1, 11, missing),
                new Object[] { "itemLeather", getModItem("minecraft", "red_flower", 1, 0, missing), "itemLeather",
                        getModItem("minecraft", "enchanted_book", 1, 0, missing),
                        getModItem("adventurebackpack", "adventureBackpack", 1, 0, missing),
                        getModItem("minecraft", "enchanted_book", 1, 0, missing), "plateIron",
                        getModItem("IC2", "itemArmorRubBoots", 1, 0, missing), "plateIron" });
        addShapedRecipe(
                getModItem("adventurebackpack", "adventureBackpack", 1, 62, missing),
                new Object[] { "itemLeather", getModItem("GalacticraftCore", "item.oxygenMask", 1, 0, missing),
                        "itemLeather", getModItem("minecraft", "potion", 1, 8269, missing),
                        getModItem("adventurebackpack", "adventureBackpack", 1, 0, missing),
                        getModItem("minecraft", "potion", 1, 8269, missing), "blockWoolBlue",
                        getModItem("minecraft", "dye", 1, 0, missing), "blockWoolBlue" });
        addShapelessCraftingRecipe(
                getModItem("adventurebackpack", "backpackComponent", 1, 2, missing),
                new Object[] { getModItem("BuildCraft|Factory", "tankBlock", 1, 0, missing) });
        addShapelessCraftingRecipe(
                getModItem("BuildCraft|Factory", "tankBlock", 1, 0, missing),
                new Object[] { getModItem("adventurebackpack", "backpackComponent", 1, 2, missing) });
        addShapelessCraftingRecipe(
                getModItem("adventurebackpack", "backpackComponent", 1, 1, missing),
                new Object[] { getModItem("sleepingbag", "sleepingBag", 1, 0, missing) });
        addShapedRecipe(
                getModItem("adventurebackpack", "machete", 1, 0, missing),
                new Object[] { "screwAnyIron", "craftingToolFile", "plateAnyIron", "craftingToolScrewdriver",
                        "plateAnyIron", "plateAnyIron",
                        getModItem("adventurebackpack", "backpackComponent", 1, 4, missing), "screwAnyIron",
                        "craftingToolHardHammer" });
        addShapedRecipe(
                getModItem("adventurebackpack", "backpackComponent", 1, 4, missing),
                new Object[] { "dyeYellow", "screwAnyIron", "dyeYellow", "dyeBlue", "stickAnyIron", "dyeBlue", "dyeRed",
                        "dyeBlack", "dyeRed" });
        addShapedRecipe(
                getModItem("adventurebackpack", "adventureHat", 1, 0, missing),
                new Object[] { null, "craftingToolScrewdriver", null,
                        getModItem("Backpack", "tannedLeather", 1, 0, missing),
                        getModItem("minecraft", "leather_helmet", 1, 0, missing),
                        getModItem("Backpack", "tannedLeather", 1, 0, missing), null, "screwGold", null });
        addShapedRecipe(
                getModItem("adventurebackpack", "backpackComponent", 1, 3, missing),
                new Object[] { "screwAnyIron", "pipeTinyBronze", "screwAnyIron", "springSmallAnyIron",
                        getModItem("minecraft", "stone_button", 1, 0, missing), "springSmallAnyIron", null,
                        "craftingToolScrewdriver", null });
        addShapedRecipe(
                createItemStack(
                        "adventurebackpack",
                        "backpackHose",
                        1,
                        0,
                        "{mode:-1L,amount:0,fluid:\"None\",tank:-1L}",
                        missing),
                new Object[] { "plateAnyRubber", getModItem("adventurebackpack", "backpackComponent", 1, 3, missing),
                        "plateAnyRubber", "plateAnyRubber", "dyeGreen", "plateAnyRubber", "plateAnyRubber", "dyeGreen",
                        "plateAnyRubber" });
        addShapedRecipe(
                getModItem("adventurebackpack", "backpackComponent", 1, 5, missing),
                new Object[] { "plateSteel", "plateSteel", "plateSteel",
                        getModItem("gregtech", "gt.metaitem.01", 1, 32641, missing), "pipeLargeSteel",
                        getModItem("gregtech", "gt.metaitem.01", 1, 32641, missing), "plateSteel",
                        getModItem("gregtech", "gt.blockmachines", 1, 1111, missing), "plateSteel" });
        addShapedRecipe(
                getModItem("adventurebackpack", "backpackComponent", 1, 6, missing),
                new Object[] { getModItem("IC2", "itemRecipePart", 1, 12, missing), "screwSteel",
                        getModItem("IC2", "itemRecipePart", 1, 12, missing), "screwSteel", "gearSteel", "screwSteel",
                        getModItem("IC2", "itemRecipePart", 1, 12, missing), "craftingToolScrewdriver",
                        getModItem("IC2", "itemRecipePart", 1, 12, missing) });
        addShapedRecipe(
                createItemStack(
                        "adventurebackpack",
                        "copterPack",
                        1,
                        0,
                        "{wearableData:{fuelTank:{Empty:\"\"}}}",
                        missing),
                new Object[] { "screwDiamond", getModItem("adventurebackpack", "backpackComponent", 1, 6, missing),
                        "screwDiamond", getModItem("adventurebackpack", "backpackComponent", 1, 2, missing),
                        getModItem("gregtech", "gt.metaitem.01", 1, 32601, missing), "pipeTinySteel", "pipeTinySteel",
                        getModItem("adventurebackpack", "backpackComponent", 1, 5, missing), "pipeTinySteel" });
        addShapedRecipe(
                createItemStack(
                        "adventurebackpack",
                        "copterPack",
                        1,
                        0,
                        "{wearableData:{fuelTank:{Empty:\"\"}}}",
                        missing),
                new Object[] { "screwDiamond", getModItem("adventurebackpack", "backpackComponent", 1, 6, missing),
                        "screwDiamond", getModItem("BuildCraft|Factory", "tankBlock", 1, 0, missing),
                        getModItem("gregtech", "gt.metaitem.01", 1, 32601, missing), "pipeTinySteel", "pipeTinySteel",
                        getModItem("adventurebackpack", "backpackComponent", 1, 5, missing), "pipeTinySteel" });
        addShapedRecipe(
                getModItem("adventurebackpack", "backpackComponent", 1, 7, missing),
                new Object[] { "plateAnyRubber", "craftingToolWrench", "plateAnyRubber", "plateAnyRubber",
                        getModItem("gregtech", "gt.metaitem.01", 1, 32610, missing), "plateAnyRubber", "plateAnyRubber",
                        "plateAnyRubber", "plateAnyRubber" });
        addShapedRecipe(
                getModItem("adventurebackpack", "backpackComponent", 1, 9, missing),
                new Object[] { "turbineBladeSteel", "screwSteel", "turbineBladeSteel", "screwSteel", "gearGtSmallSteel",
                        "screwSteel", "turbineBladeSteel", "craftingToolScrewdriver", "turbineBladeSteel" });
        addShapedRecipe(
                getModItem("adventurebackpack", "pistonBoots", 1, 0, missing),
                new Object[] { getModItem("Backpack", "tannedLeather", 1, 0, missing), "craftingToolScrewdriver",
                        getModItem("Backpack", "tannedLeather", 1, 0, missing), "plateAnyRubber",
                        getModItem("minecraft", "leather_boots", 1, 0, missing), "plateAnyRubber", "craftingPiston",
                        "screwBronze", "craftingPiston" });
        addShapedRecipe(
                getModItem("adventurebackpack", "pistonBoots", 1, 0, missing),
                new Object[] { "string", getModItem("Backpack", "tannedLeather", 1, 0, missing), "string",
                        "plateAnyRubber", getModItem("adventurebackpack", "pistonBoots", 1, 32767, missing),
                        "plateAnyRubber", null, null, null });
        addShapedRecipe(
                getModItem("adventurebackpack", "adventureSuit", 1, 0, missing),
                new Object[] { getModItem("Backpack", "tannedLeather", 1, 0, missing), "craftingToolScrewdriver",
                        getModItem("Backpack", "tannedLeather", 1, 0, missing),
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing),
                        getModItem("minecraft", "leather_chestplate", 1, 0, missing),
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing),
                        getModItem("Backpack", "tannedLeather", 1, 0, missing), "screwBronze",
                        getModItem("Backpack", "tannedLeather", 1, 0, missing) });
        addShapedRecipe(
                getModItem("adventurebackpack", "adventurePants", 1, 0, missing),
                new Object[] { getModItem("Backpack", "tannedLeather", 1, 0, missing), "craftingToolScrewdriver",
                        getModItem("Backpack", "tannedLeather", 1, 0, missing),
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing),
                        getModItem("minecraft", "leather_leggings", 1, 0, missing),
                        getModItem("harvestcraft", "wovencottonItem", 1, 0, missing),
                        getModItem("Backpack", "tannedLeather", 1, 0, missing), "screwBronze",
                        getModItem("Backpack", "tannedLeather", 1, 0, missing) });
        addShapedRecipe(
                getModItem("adventurebackpack", "clockworkCrossbow", 1, 0, missing),
                new Object[] { "springSmallSteel", getModItem("minecraft", "bow", 1, 0, missing), "springSmallSteel",
                        "stickSteel", "wireFineSteel", "stickSteel", "screwSteel", "craftingToolScrewdriver",
                        "screwSteel" });
        addShapedRecipe(
                createItemStack(
                        "adventurebackpack",
                        "coalJetpack",
                        1,
                        0,
                        "{wearableData:{waterTank:{Empty:\"\"}}}",
                        missing),
                new Object[] { "plateAluminium", "screwSteel", "plateAluminium",
                        getModItem("adventurebackpack", "backpackComponent", 1, 2, missing),
                        getModItem("gregtech", "gt.metaitem.01", 1, 32601, missing),
                        getModItem("adventurebackpack", "backpackComponent", 1, 2, missing), "pipeTinySteel",
                        getModItem("Railcraft", "machine.beta", 1, 5, missing), "pipeTinySteel" });
        addShapedRecipe(
                createItemStack(
                        "adventurebackpack",
                        "coalJetpack",
                        1,
                        0,
                        "{wearableData:{waterTank:{Empty:\"\"}}}",
                        missing),
                new Object[] { "plateAluminium", "screwSteel", "plateAluminium",
                        getModItem("BuildCraft|Factory", "tankBlock", 1, 0, missing),
                        getModItem("gregtech", "gt.metaitem.01", 1, 32601, missing),
                        getModItem("BuildCraft|Factory", "tankBlock", 1, 0, missing), "pipeTinySteel",
                        getModItem("Railcraft", "machine.beta", 1, 5, missing), "pipeTinySteel" });

        GT_Values.RA.stdBuilder().itemInputs(getModItem("minecraft", "melon", 1, 0, missing)).noItemOutputs()
                .fluidInputs(FluidRegistry.getFluidStack("water", 750))
                .fluidOutputs(FluidRegistry.getFluidStack("melonjuice", 750)).duration(128).eut(4)
                .addTo(sBrewingRecipes);

    }
}
