package com.dreammaster.scripts;

import static gregtech.api.enums.Mods.BartWorks;
import static gregtech.api.enums.Mods.BiomesOPlenty;
import static gregtech.api.enums.Mods.Botania;
import static gregtech.api.enums.Mods.Botany;
import static gregtech.api.enums.Mods.BuildCraftFactory;
import static gregtech.api.enums.Mods.ExtraBees;
import static gregtech.api.enums.Mods.ForbiddenMagic;
import static gregtech.api.enums.Mods.Forestry;
import static gregtech.api.enums.Mods.GTPlusPlus;
import static gregtech.api.enums.Mods.GalacticraftAmunRa;
import static gregtech.api.enums.Mods.Gendustry;
import static gregtech.api.enums.Mods.GregTech;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.enums.Mods.MCFrames;
import static gregtech.api.enums.Mods.Minecraft;
import static gregtech.api.enums.Mods.Natura;
import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.enums.Mods.OpenBlocks;
import static gregtech.api.enums.Mods.OpenComputers;
import static gregtech.api.enums.Mods.PamsHarvestCraft;
import static gregtech.api.enums.Mods.ProjectRedCore;
import static gregtech.api.enums.Mods.ProjectRedExpansion;
import static gregtech.api.enums.Mods.ProjectRedExploration;
import static gregtech.api.enums.Mods.ProjectRedFabrication;
import static gregtech.api.enums.Mods.ProjectRedTransmission;
import static gregtech.api.enums.Mods.ProjectRedTransportation;
import static gregtech.api.enums.Mods.Railcraft;
import static gregtech.api.enums.Mods.StevesCarts2;
import static gregtech.api.enums.Mods.ThaumicHorizons;
import static gregtech.api.enums.Mods.TinkerConstruct;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.recipe.RecipeMaps.cannerRecipes;
import static gregtech.api.recipe.RecipeMaps.chemicalBathRecipes;
import static gregtech.api.recipe.RecipeMaps.circuitAssemblerRecipes;
import static gregtech.api.recipe.RecipeMaps.formingPressRecipes;
import static gregtech.api.recipe.RecipeMaps.laserEngraverRecipes;
import static gregtech.api.recipe.RecipeMaps.maceratorRecipes;
import static gregtech.api.recipe.RecipeMaps.mixerRecipes;
import static gregtech.api.recipe.RecipeMaps.packagerRecipes;
import static gregtech.api.recipe.RecipeMaps.unpackagerRecipes;
import static gregtech.api.util.GT_ModHandler.getModItem;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_RecipeBuilder.TICKS;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.dreammaster.chisel.ChiselHelper;
import com.dreammaster.tinkersConstruct.TConstructHelper;

import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import tconstruct.library.crafting.Smeltery;

public class ScriptProjectRed implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Project Red";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(
                ProjectRedCore.ID,
                ProjectRedExploration.ID,
                ProjectRedTransmission.ID,
                ProjectRedTransportation.ID,
                TinkerConstruct.ID,
                GalacticraftAmunRa.ID,
                MCFrames.ID,
                ProjectRedExpansion.ID,
                ProjectRedFabrication.ID,
                BartWorks.ID,
                BiomesOPlenty.ID,
                Botania.ID,
                Botany.ID,
                BuildCraftFactory.ID,
                ExtraBees.ID,
                ForbiddenMagic.ID,
                Forestry.ID,
                Gendustry.ID,
                GTPlusPlus.ID,
                IndustrialCraft2.ID,
                Natura.ID,
                OpenBlocks.ID,
                OpenComputers.ID,
                PamsHarvestCraft.ID,
                Railcraft.ID,
                StevesCarts2.ID,
                ThaumicHorizons.ID,
                TinkerConstruct.ID);
    }

    // This should ideally be shared somewhere; pending more cleanup/rewrite.
    private static final Materials[] solderingMaterials = new Materials[] { Materials.SolderingAlloy, Materials.Tin,
            Materials.Lead };

    /**
     * Gives a FluidStack of soldering material of appropriate size.
     *
     * @param material   Material to use. Must be one of {@link #solderingMaterials}.
     * @param baseAmount Amount of soldering alloy to use. Tin is 2x, Lead is 4x this amount.
     * @return FluidStack of the appropriate material and amount.
     */
    private static FluidStack getSolderingFluid(Materials material, long baseAmount) {
        if (material.contains(SubTag.SOLDERING_MATERIAL_GOOD)) return material.getMolten(baseAmount);
        if (material.contains(SubTag.SOLDERING_MATERIAL_BAD)) return material.getMolten(baseAmount * 4);
        if (material.contains(SubTag.SOLDERING_MATERIAL)) return material.getMolten(baseAmount * 2);
        return null;
    }

    @Override
    public void loadRecipes() {
        craftingRecipes();

        recipes1();
        circuitAssemblerRecipes();
        recipes2();

        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(ProjectRedFabrication.ID, "projectred.fabrication.icchip", 1, 1, missing),
                "aaaaaaaaa",
                "abbbbbbba",
                "abcccccba",
                "abcdedcba",
                "abcefecba",
                "abcdedcba",
                "abcccccba",
                "abbbbbbba",
                "aaaaaaaaa",
                'a',
                "plateNeutronium",
                'b',
                "circuitMaster",
                'c',
                "circuitUltimate",
                'd',
                "circuitSuperconductor",
                'e',
                "circuitInfinite",
                'f',
                getModItem(OpenComputers.ID, "item", 1, 43, missing));

        ChiselHelper.addGroup("ruby");
        ChiselHelper.addGroup("sapphire");
        ChiselHelper.addGroup("peridot");
        TConstructHelper.removeSmelterAlloyMix(FluidRegistry.getFluidStack("redmetal.molten", 144));
        Smeltery.addAlloyMixing(
                FluidRegistry.getFluidStack("redmetal.molten", 144),
                FluidRegistry.getFluidStack("redstone.molten", 576),
                FluidRegistry.getFluidStack("copper.molten", 144));
        ChiselHelper.addVariationFromStack("ruby", getModItem(BiomesOPlenty.ID, "gemOre", 1, 3, missing));
        ChiselHelper.addVariationFromStack("ruby", getModItem(GregTech.ID, "gt.blockgem2", 1, 11, missing));
        ChiselHelper.addVariationFromStack(
                "ruby",
                getModItem(ProjectRedExploration.ID, "projectred.exploration.stone", 1, 5, missing));
        ChiselHelper.addVariationFromStack("sapphire", getModItem(BiomesOPlenty.ID, "gemOre", 1, 13, missing));
        ChiselHelper.addVariationFromStack("sapphire", getModItem(GregTech.ID, "gt.blockgem2", 1, 12, missing));
        ChiselHelper.addVariationFromStack(
                "sapphire",
                getModItem(ProjectRedExploration.ID, "projectred.exploration.stone", 1, 6, missing));
        ChiselHelper.addVariationFromStack("peridot", getModItem(BiomesOPlenty.ID, "gemOre", 1, 5, missing));
        ChiselHelper
                .addVariationFromStack("peridot", GT_OreDictUnificator.get(OrePrefixes.block, Materials.Olivine, 1L));
        ChiselHelper.addVariationFromStack(
                "peridot",
                getModItem(ProjectRedExploration.ID, "projectred.exploration.stone", 1, 7, missing));

    }

    private void craftingRecipes() {
        addShapedRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine1", 1, 1, missing),
                getModItem(Minecraft.ID, "brick_block", 1, 0, missing),
                getModItem(Minecraft.ID, "brick_block", 1, 0, missing),
                getModItem(Minecraft.ID, "brick_block", 1, 0, missing),
                "itemCasingSteel",
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.emptybattery", 1, 0, missing),
                "itemCasingSteel",
                "circuitBasic",
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.emptybattery", 1, 0, missing),
                "circuitBasic");
        addShapedRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 0, missing),
                "itemCasingSteel",
                ItemList.Electric_Motor_LV.get(1L),
                "itemCasingSteel",
                com.dreammaster.item.ItemList.DiamondDrillTip.getIS(1),
                getModItem(IndustrialCraft2.ID, "blockMiningPipe", 1, 0, missing),
                "circuitBasic",
                "itemCasingSteel",
                "gearSteel",
                "itemCasingSteel");
        addShapelessRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 0, missing),
                getModItem(OpenBlocks.ID, "blockbreaker", 1, 0, missing));
        addShapedRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 1, missing),
                "itemCasingSteel",
                ItemList.Electric_Motor_LV.get(1L),
                "itemCasingSteel",
                "circuitBasic",
                "pipeMediumTin",
                ItemList.Conveyor_Module_LV.get(1L),
                "itemCasingSteel",
                "craftingPiston",
                "itemCasingSteel");
        addShapedRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 2, missing),
                "itemCasingSteel",
                ItemList.Electric_Motor_LV.get(1L),
                "itemCasingSteel",
                ItemList.Conveyor_Module_LV.get(1L),
                "pipeMediumTin",
                "circuitBasic",
                "itemCasingSteel",
                "craftingPiston",
                "itemCasingSteel");
        addShapelessRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 2, missing),
                getModItem(OpenBlocks.ID, "blockPlacer", 1, 0, missing));
        addShapedRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 3, missing),
                "itemCasingAluminium",
                ItemList.Electric_Motor_MV.get(1L),
                "itemCasingAluminium",
                "circuitGood",
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 1, missing),
                ItemList.Conveyor_Module_MV.get(1L),
                "itemCasingAluminium",
                "craftingFilter",
                "itemCasingAluminium");
        addShapedRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 4, missing),
                getModItem(TinkerConstruct.ID, "heavyPlate", 1, 7, missing),
                ItemList.Electric_Motor_LV.get(1L),
                getModItem(TinkerConstruct.ID, "heavyPlate", 1, 7, missing),
                "circuitBasic",
                getModItem(Minecraft.ID, "flint_and_steel", 1, 0, missing),
                ItemList.Conveyor_Module_LV.get(1L),
                "itemCasingSteel",
                "craftingPiston",
                "itemCasingSteel");
        addShapedRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 5, missing),
                "plateSteel",
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.emptybattery", 1, 0, missing),
                "plateSteel",
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.emptybattery", 1, 0, missing),
                ItemList.Hull_LV.get(1L),
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.emptybattery", 1, 0, missing),
                "plateSteel",
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.emptybattery", 1, 0, missing),
                "plateSteel");
        addShapelessRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.solar_panel", 1, 0, missing),
                ItemList.Cover_SolarPanel.get(1L));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 0, missing),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.RedAlloy, 1L));
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.sawgold", 1, 0, missing),
                "stickWood",
                "stickSteel",
                "stickSteel",
                "stickWood",
                GT_OreDictUnificator.get(OrePrefixes.toolHeadSaw, Materials.Gold, 1L),
                "stickSteel",
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.sawruby", 1, 0, missing),
                "stickWood",
                "stickSteel",
                "stickSteel",
                "stickWood",
                com.dreammaster.item.ItemList.SawBladeRuby.getIS(1),
                "stickSteel",
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.sawsapphire", 1, 0, missing),
                "stickWood",
                "stickSteel",
                "stickSteel",
                "stickWood",
                com.dreammaster.item.ItemList.SawBladeSapphire.getIS(1),
                "stickSteel",
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.sawperidot", 1, 0, missing),
                "stickWood",
                "stickSteel",
                "stickSteel",
                "stickWood",
                com.dreammaster.item.ItemList.SawBladePeridot.getIS(1),
                "stickSteel",
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.rubyboots", 1, 0, missing),
                "plateRuby",
                null,
                "plateRuby",
                "plateRuby",
                "craftingToolHardHammer",
                "plateRuby",
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.rubychestplate", 1, 0, missing),
                "plateRuby",
                "craftingToolHardHammer",
                "plateRuby",
                "plateRuby",
                "plateRuby",
                "plateRuby",
                "plateRuby",
                "plateRuby",
                "plateRuby");
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.rubyhelmet", 1, 0, missing),
                "plateRuby",
                "plateRuby",
                "plateRuby",
                "plateRuby",
                "craftingToolHardHammer",
                "plateRuby",
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.rubyleggings", 1, 0, missing),
                "plateRuby",
                "plateRuby",
                "plateRuby",
                "plateRuby",
                "craftingToolHardHammer",
                "plateRuby",
                "plateRuby",
                null,
                "plateRuby");
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.sapphireboots", 1, 0, missing),
                "plateSapphire",
                null,
                "plateSapphire",
                "plateSapphire",
                "craftingToolHardHammer",
                "plateSapphire",
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.sapphirechestplate", 1, 0, missing),
                "plateSapphire",
                "craftingToolHardHammer",
                "plateSapphire",
                "plateSapphire",
                "plateSapphire",
                "plateSapphire",
                "plateSapphire",
                "plateSapphire",
                "plateSapphire");
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.sapphirehelmet", 1, 0, missing),
                "plateSapphire",
                "plateSapphire",
                "plateSapphire",
                "plateSapphire",
                "craftingToolHardHammer",
                "plateSapphire",
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.sapphireleggings", 1, 0, missing),
                "plateSapphire",
                "plateSapphire",
                "plateSapphire",
                "plateSapphire",
                "craftingToolHardHammer",
                "plateSapphire",
                "plateSapphire",
                null,
                "plateSapphire");
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.peridotboots", 1, 0, missing),
                "plateOlivine",
                null,
                "plateOlivine",
                "plateOlivine",
                "craftingToolHardHammer",
                "plateOlivine",
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.peridotchestplate", 1, 0, missing),
                "plateOlivine",
                "craftingToolHardHammer",
                "plateOlivine",
                "plateOlivine",
                "plateOlivine",
                "plateOlivine",
                "plateOlivine",
                "plateOlivine",
                "plateOlivine");
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.peridothelmet", 1, 0, missing),
                "plateOlivine",
                "plateOlivine",
                "plateOlivine",
                "plateOlivine",
                "craftingToolHardHammer",
                "plateOlivine",
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedExploration.ID, "projectred.exploration.peridotleggings", 1, 0, missing),
                "plateOlivine",
                "plateOlivine",
                "plateOlivine",
                "plateOlivine",
                "craftingToolHardHammer",
                "plateOlivine",
                "plateOlivine",
                null,
                "plateOlivine");
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 44, missing),
                "wireGt01Gold",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 0, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 0, missing),
                "wireGt01Gold",
                "circuitPrimitive",
                "wireGt01Gold",
                "screwSteel",
                "craftingToolScrewdriver",
                "screwSteel");
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.screwdriver", 1, 0, missing),
                null,
                "craftingToolFile",
                "stickSteel",
                "stickAnyRubber",
                "stickSteel",
                "craftingToolHardHammer",
                "stickAnyRubber",
                "stickAnyRubber",
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.wiredebugger", 1, 0, missing),
                "wireGt01RedAlloy",
                "stickSteelMagnetic",
                "wireGt01RedAlloy",
                "itemCasingSteel",
                "plateGlowstone",
                "itemCasingSteel",
                "screwSteel",
                "craftingToolScrewdriver",
                "screwSteel");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 0, missing),
                "plateAnyRubber");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 1, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyeWhite");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 1, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 2, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyeOrange");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 2, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 3, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyeMagenta");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 3, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 4, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyeLightBlue");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 4, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 5, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyeYellow");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 5, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 6, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyeLime");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 6, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 7, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyePink");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 7, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 8, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyeGray");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 8, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 9, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyeLightGray");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 9, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 10, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyeCyan");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 10, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 11, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyePurple");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 11, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 12, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyeBlue");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 12, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 13, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyeBrown");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 13, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 14, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyeGreen");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 14, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 15, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                "dyeRed");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 15, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 18, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeWhite");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 18, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 19, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeOrange");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 19, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 20, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeMagenta");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 20, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 21, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeLightBlue");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 21, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 22, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeYellow");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 22, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 23, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeLime");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 23, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 24, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyePink");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 24, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 25, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeGray");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 25, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 26, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeLightGray");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 26, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 27, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeCyan");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 27, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 28, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyePurple");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 28, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 29, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeBlue");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 29, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 30, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeBrown");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 30, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 31, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeGreen");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 31, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 32, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeRed");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 32, missing));
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 33, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                "dyeBlack");
        addShapelessRecipe(
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 33, missing));
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickIron",
                getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                "stickIron",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickIron",
                "paneGlass",
                "stickIron",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickIron",
                "plateGlass",
                "stickIron",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 2, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickLongIron",
                getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                "stickLongIron",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 2, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickLongIron",
                "paneGlass",
                "stickLongIron",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 2, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickLongIron",
                "plateGlass",
                "stickLongIron",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 4, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickSteel",
                getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                "stickSteel",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 4, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickSteel",
                "paneGlass",
                "stickSteel",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 4, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickSteel",
                "plateGlass",
                "stickSteel",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 8, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickLongSteel",
                getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                "stickLongSteel",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 8, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickLongSteel",
                "paneGlass",
                "stickLongSteel",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 8, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickLongSteel",
                "plateGlass",
                "stickLongSteel",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 16, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickAluminium",
                getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                "stickAluminium",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 16, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickAluminium",
                "paneGlass",
                "stickAluminium",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 16, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickAluminium",
                "plateGlass",
                "stickAluminium",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 32, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickLongAluminium",
                getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                "stickLongAluminium",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 32, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickLongAluminium",
                "paneGlass",
                "stickLongAluminium",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 32, 0, missing),
                null,
                "craftingToolFile",
                null,
                "stickLongAluminium",
                "plateGlass",
                "stickLongAluminium",
                null,
                "craftingToolSaw",
                null);
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 4, 1, missing),
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 0, missing),
                "stickSteel",
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 0, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 33, missing),
                "gearSteel",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 32, missing),
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 0, missing),
                "stickSteel",
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 0, missing));
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 2, missing),
                "screwIron",
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 1, missing),
                "screwIron",
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L),
                ItemList.Circuit_Board_Coated_Basic.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L),
                "screwIron",
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Redstone, 1L),
                "screwIron");
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 4, missing),
                "screwSteel",
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 1, missing),
                "screwSteel",
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L),
                ItemList.Circuit_Board_Coated_Basic.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L),
                "screwSteel",
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Redstone, 1L),
                "screwSteel");
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routingchip", 1, 0, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 20, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 20, missing),
                "plateCopper",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 44, missing),
                "circuitBasic",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 20, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 20, missing));
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routingchip", 1, 1, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 20, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 28, missing),
                "plateBronze",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 44, missing),
                "circuitBasic",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 20, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 28, missing));
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routingchip", 1, 2, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 32, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 32, missing),
                "plateIron",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 44, missing),
                "circuitBasic",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 32, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 32, missing));
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routingchip", 1, 3, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 29, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 26, missing),
                "plateTin",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 44, missing),
                "circuitBasic",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 29, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 26, missing));
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routingchip", 1, 4, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 28, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 28, missing),
                "plateSteel",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 44, missing),
                "circuitBasic",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 28, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 28, missing));
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routingchip", 1, 5, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 21, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 21, missing),
                "plateGold",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 44, missing),
                "circuitBasic",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 21, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 21, missing));
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routingchip", 1, 6, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 30, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 30, missing),
                "plateCobaltBrass",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 44, missing),
                "circuitBasic",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 30, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 30, missing));
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routingchip", 1, 7, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 24, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 24, missing),
                "plateGlowstone",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 44, missing),
                "circuitBasic",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 24, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 24, missing));
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routingchip", 1, 8, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 33, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 33, missing),
                "plateSilver",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 44, missing),
                "circuitBasic",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 33, missing),
                "wireGt01RedAlloy",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 33, missing));
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routerutil", 1, 0, missing),
                "wireGt01RedAlloy",
                "itemCasingSteel",
                "wireGt01RedAlloy",
                "circuitBasic",
                "plateEmerald",
                "circuitBasic",
                "itemCasingSteel",
                createItemStack(GregTech.ID, "gt.metaitem.01", 1, 32510, "{GT.ItemCharge:18000L}", missing),
                "itemCasingSteel");
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routerutil", 1, 0, missing),
                "wireGt01RedAlloy",
                "itemCasingSteel",
                "wireGt01RedAlloy",
                "circuitBasic",
                "plateEmerald",
                "circuitBasic",
                "itemCasingSteel",
                createItemStack(GregTech.ID, "gt.metaitem.01", 1, 32511, "{GT.ItemCharge:32000L}", missing),
                "itemCasingSteel");
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routerutil", 1, 0, missing),
                "wireGt01RedAlloy",
                "itemCasingSteel",
                "wireGt01RedAlloy",
                "circuitBasic",
                "plateEmerald",
                "circuitBasic",
                "itemCasingSteel",
                createItemStack(GregTech.ID, "gt.metaitem.01", 1, 32517, "{GT.ItemCharge:75000L}", missing),
                "itemCasingSteel");
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routerutil", 1, 0, missing),
                "wireGt01RedAlloy",
                "itemCasingSteel",
                "wireGt01RedAlloy",
                "circuitBasic",
                "plateEmerald",
                "circuitBasic",
                "itemCasingSteel",
                createItemStack(GregTech.ID, "gt.metaitem.01", 1, 32518, "{GT.ItemCharge:100000L}", missing),
                "itemCasingSteel");
        addShapedRecipe(
                getModItem(ProjectRedTransportation.ID, "projectred.transportation.routerutil", 1, 0, missing),
                "wireGt01RedAlloy",
                "itemCasingSteel",
                "wireGt01RedAlloy",
                "circuitBasic",
                "plateEmerald",
                "circuitBasic",
                "itemCasingSteel",
                createItemStack(GregTech.ID, "gt.metaitem.01", 1, 32519, "{GT.ItemCharge:50000L}", missing),
                "itemCasingSteel");
        addShapedRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.jetpack", 1, 6400, missing),
                "circuitBasic",
                "wireGt04Gold",
                "circuitBasic",
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.emptybattery", 1, 0, missing),
                getModItem(Minecraft.ID, "diamond_chestplate", 1, 0, missing),
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.emptybattery", 1, 0, missing),
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.emptybattery", 1, 0, missing),
                "plateGlowstone",
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.emptybattery", 1, 0, missing));
        addShapedRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 6, missing),
                "wireGt04Copper",
                "plateSteel",
                "wireGt04Copper",
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.emptybattery", 1, 0, missing),
                ItemList.Hull_LV.get(1L),
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.emptybattery", 1, 0, missing),
                "circuitBasic",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 16, missing),
                "circuitBasic");
        addShapedRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 8, missing),
                "circuitBasic",
                "plateSteel",
                "circuitBasic",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 16, missing),
                ItemList.Hull_LV.get(1L),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 16, missing),
                "wireGt04Copper",
                ItemList.Electric_Motor_LV.get(1L),
                "wireGt04Copper");
        addShapedRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 9, missing),
                "plateSteel",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 59, missing),
                "plateSteel",
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 16, missing),
                GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1L),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 16, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 18, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 59, missing),
                getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 18, missing));
        addShapedRecipe(
                getModItem(ProjectRedFabrication.ID, "projectred.integration.icblock", 1, 0, missing),
                "plateSteel",
                "plateSteel",
                "plateSteel",
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1L),
                getModItem(BuildCraftFactory.ID, "autoWorkbenchBlock", 1, 0, missing),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1L),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1L),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1L),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1L));
        addShapedRecipe(
                getModItem(ProjectRedFabrication.ID, "projectred.integration.icblock", 1, 1, missing),
                "blockGlass",
                "blockGlass",
                "blockGlass",
                "plateObsidian",
                "stickSteel",
                "plateObsidian",
                "stickSteel",
                ItemList.Machine_LV_Printer.get(1L),
                "stickSteel");
        addShapelessRecipe(
                getModItem(MCFrames.ID, "mcframes.frame", 1, 0, missing),
                GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1L),
                GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1L));
        addShapelessRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 10, missing),
                getModItem(Forestry.ID, "factory2", 1, 2, missing));
        addShapelessRecipe(
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 11, missing),
                getModItem(BuildCraftFactory.ID, "autoWorkbenchBlock", 1, 0, missing));
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 19, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 15, missing),
                getModItem(Minecraft.ID, "dye", 1, 15, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 19, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "misc", 1, 8, missing),
                getModItem(BiomesOPlenty.ID, "misc", 1, 8, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 19, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ExtraBees.ID, "misc", 1, 23, missing),
                getModItem(ExtraBees.ID, "misc", 1, 23, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 19, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 77, missing),
                getModItem(Botany.ID, "pigment", 1, 77, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 19, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_15.get(1L),
                ItemList.Color_15.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 19, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 0, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 20, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 14, missing),
                getModItem(Minecraft.ID, "dye", 1, 14, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 20, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_14.get(1L),
                ItemList.Color_14.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 20, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 1, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 1, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 20, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 51, missing),
                getModItem(Botany.ID, "pigment", 1, 51, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 21, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 13, missing),
                getModItem(Minecraft.ID, "dye", 1, 13, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 21, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 40, missing),
                getModItem(Botany.ID, "pigment", 1, 40, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 21, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 2, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 2, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 21, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_13.get(1L),
                ItemList.Color_13.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 22, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 12, missing),
                getModItem(Minecraft.ID, "dye", 1, 12, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 22, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 22, missing),
                getModItem(Botany.ID, "pigment", 1, 22, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 22, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 3, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 3, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 22, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_12.get(1L),
                ItemList.Color_12.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 23, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 11, missing),
                getModItem(Minecraft.ID, "dye", 1, 11, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 23, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ExtraBees.ID, "misc", 1, 20, missing),
                getModItem(ExtraBees.ID, "misc", 1, 20, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 23, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 78, missing),
                getModItem(Botany.ID, "pigment", 1, 78, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 23, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 4, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 4, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 23, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_11.get(1L),
                ItemList.Color_11.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 24, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 10, missing),
                getModItem(Minecraft.ID, "dye", 1, 10, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 24, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 38, missing),
                getModItem(Botany.ID, "pigment", 1, 38, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 24, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 5, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 5, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 24, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_10.get(1L),
                ItemList.Color_10.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 25, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 9, missing),
                getModItem(Minecraft.ID, "dye", 1, 9, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 25, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 29, missing),
                getModItem(Botany.ID, "pigment", 1, 29, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 25, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 6, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 6, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 25, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_09.get(1L),
                ItemList.Color_09.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 26, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 8, missing),
                getModItem(Minecraft.ID, "dye", 1, 8, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 26, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 10, missing),
                getModItem(Botany.ID, "pigment", 1, 10, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 26, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_08.get(1L),
                ItemList.Color_08.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 26, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 7, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 7, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 27, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 7, missing),
                getModItem(Minecraft.ID, "dye", 1, 7, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 27, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 8, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 8, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 27, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_07.get(1L),
                ItemList.Color_07.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 27, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 35, missing),
                getModItem(Botany.ID, "pigment", 1, 35, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 28, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 6, missing),
                getModItem(Minecraft.ID, "dye", 1, 6, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 28, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 8, missing),
                getModItem(Botany.ID, "pigment", 1, 8, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 28, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_06.get(1L),
                ItemList.Color_06.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 28, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 9, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 9, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 29, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 5, missing),
                getModItem(Minecraft.ID, "dye", 1, 5, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 29, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 20, missing),
                getModItem(Botany.ID, "pigment", 1, 20, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 29, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 10, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 10, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 29, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_05.get(1L),
                ItemList.Color_05.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 30, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 4, missing),
                getModItem(Minecraft.ID, "dye", 1, 15, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 30, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "misc", 1, 5, missing),
                getModItem(Minecraft.ID, "dye", 1, 5, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 30, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ExtraBees.ID, "misc", 1, 21, missing),
                getModItem(ExtraBees.ID, "misc", 1, 21, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 30, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 24, missing),
                getModItem(Botany.ID, "pigment", 1, 24, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 30, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 11, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 11, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 30, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_04.get(1L),
                ItemList.Color_04.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 30, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Dye_Indigo.get(1L),
                ItemList.Dye_Indigo.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 31, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "misc", 1, 6, missing),
                getModItem(BiomesOPlenty.ID, "misc", 1, 6, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 31, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ExtraBees.ID, "misc", 1, 25, missing),
                getModItem(ExtraBees.ID, "misc", 1, 25, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 31, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 3, missing),
                getModItem(Minecraft.ID, "dye", 1, 3, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 31, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 65, missing),
                getModItem(Botany.ID, "pigment", 1, 65, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 31, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_03.get(1L),
                ItemList.Color_03.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 31, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 12, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 12, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 32, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 2, missing),
                getModItem(Minecraft.ID, "dye", 1, 2, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 32, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "misc", 1, 7, missing),
                getModItem(BiomesOPlenty.ID, "misc", 1, 7, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 32, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ExtraBees.ID, "misc", 1, 22, missing),
                getModItem(ExtraBees.ID, "misc", 1, 22, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 32, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 28, missing),
                getModItem(Botany.ID, "pigment", 1, 28, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 32, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 13, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 13, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 32, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_02.get(1L),
                ItemList.Color_02.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 33, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 1, missing),
                getModItem(Minecraft.ID, "dye", 1, 1, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 33, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ExtraBees.ID, "misc", 1, 19, missing),
                getModItem(ExtraBees.ID, "misc", 1, 19, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 33, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 59, missing),
                getModItem(Botany.ID, "pigment", 1, 59, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 33, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 14, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 14, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 33, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_01.get(1L),
                ItemList.Color_01.get(1L),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 34, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 0, missing),
                getModItem(Minecraft.ID, "dye", 1, 0, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 34, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "misc", 1, 9, missing),
                getModItem(BiomesOPlenty.ID, "misc", 1, 9, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 34, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ExtraBees.ID, "misc", 1, 24, missing),
                getModItem(ExtraBees.ID, "misc", 1, 24, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 34, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Botany.ID, "pigment", 1, 1, missing),
                getModItem(Botany.ID, "pigment", 1, 1, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 34, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 15, missing),
                getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 1, 15, missing),
                null,
                null,
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 34, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                getModItem(Minecraft.ID, "glowstone_dust", 1, 0, missing),
                ItemList.Color_00.get(1L),
                ItemList.Color_00.get(1L),
                null,
                null,
                null,
                null,
                null);
    }

    private void recipes1() {
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 0, missing),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 1, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(10 * SECONDS).eut(16)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 1, missing),
                        getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 0, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 2, missing))
                .duration(15 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 1, missing),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 1L))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 2, missing))
                .duration(15 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 1, missing),
                        getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 3, missing))
                .duration(20 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 1, missing),
                        getModItem(Minecraft.ID, "redstone", 3, 0, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 4, missing))
                .duration(20 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 1, missing),
                        getModItem(Minecraft.ID, "redstone_torch", 1, 0, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 5, missing))
                .duration(20 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 5, missing),
                        GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Ruby, 1L))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 6, missing))
                .duration(25 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 5, missing),
                        GT_OreDictUnificator.get(OrePrefixes.lens, Materials.FoolsRuby, 1L))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 6, missing))
                .duration(25 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 2, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Plastic, 4L))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 9, missing))
                .duration(15 * SECONDS).eut(64).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Paper, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.datacard", 1, 0, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 288)).duration(10 * SECONDS).eut(16)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedCore.ID, "projectred.core.screwdriver", 1, 0, missing),
                        getModItem(ProjectRedExpansion.ID, "projectred.expansion.battery", 1, 0, missing))
                .itemOutputs(
                        getModItem(ProjectRedExpansion.ID, "projectred.expansion.electric_screwdriver", 1, 0, missing))
                .duration(5 * SECONDS).eut(16).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 5, 0, missing),
                        getModItem(GregTech.ID, "gt.metaitem.01", 5, 17880, missing))
                .itemOutputs(getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing))
                .duration(15 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 5, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 5L))
                .itemOutputs(getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing))
                .duration(15 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 5, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 5L))
                .itemOutputs(getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 17, missing))
                .duration(15 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 2, 0, missing))
                .duration(5 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glass_pane", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 2, 0, missing))
                .duration(5 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 2, 0, missing))
                .duration(5 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Iron, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 4, 0, missing))
                .duration(5 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glass_pane", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Iron, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 4, 0, missing))
                .duration(5 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Iron, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 4, 0, missing))
                .duration(5 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 8, 0, missing))
                .duration(10 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glass_pane", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 8, 0, missing))
                .duration(10 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 8, 0, missing))
                .duration(10 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 16, 0, missing))
                .duration(10 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glass_pane", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 16, 0, missing))
                .duration(10 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 16, 0, missing))
                .duration(10 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 32, 0, missing))
                .duration(20 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glass_pane", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 32, 0, missing))
                .duration(20 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 32, 0, missing))
                .duration(20 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Aluminium, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 64, 0, missing))
                .duration(20 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glass_pane", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Aluminium, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 64, 0, missing))
                .duration(20 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Aluminium, 2L),
                        GT_Utility.getIntegratedCircuit(19))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 64, 0, missing))
                .duration(20 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 8, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 8, 1, missing))
                .duration(40 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 16, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Aluminium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 4L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 16, 1, missing))
                .duration(60 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 32, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.StainlessSteel, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 4L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 32, 1, missing))
                .duration(80 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 64, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Titanium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 4L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 64, 1, missing))
                .duration(100 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 2, 1, missing),
                        ItemList.Circuit_Board_Coated_Basic.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 2, 2, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(20 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 4, 1, missing),
                        ItemList.Circuit_Board_Phenolic_Good.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 4, 2, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(30 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 8, 1, missing),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 8, 2, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(40 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 16, 1, missing),
                        ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 16, 2, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(50 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 32, 1, missing),
                        ItemList.Circuit_Board_Multifiberglass_Elite.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 32, 2, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(60 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 64, 1, missing),
                        ItemList.Circuit_Board_Wetware_Extreme.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 64, 2, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(70 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 1, missing),
                        ItemList.Circuit_Board_Coated_Basic.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.ULV, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 4, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(20 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 2, 1, missing),
                        ItemList.Circuit_Board_Phenolic_Good.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 2, 4, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(30 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 4, 1, missing),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 4, 4, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(40 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 8, 1, missing),
                        ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 8, 4, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(50 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 16, 1, missing),
                        ItemList.Circuit_Board_Multifiberglass_Elite.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 16, 4, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(60 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 32, 1, missing),
                        ItemList.Circuit_Board_Wetware_Extreme.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 32, 4, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(70 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 1, missing),
                        ItemList.Component_Filter.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 6, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 1440)).duration(20 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 0, missing),
                        getModItem(StevesCarts2.ID, "ModuleComponents", 1, 60, missing))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 9, missing))
                .duration(10 * SECONDS).eut(30).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 0, missing))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 10, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.rubber", 1440)).duration(10 * SECONDS).eut(30)
                .addTo(chemicalBathRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 0, missing))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 10, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.styrenebutadienerubber", 720)).duration(10 * SECONDS)
                .eut(30).addTo(chemicalBathRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 0, missing))
                .itemOutputs(getModItem(ProjectRedTransportation.ID, "projectred.transportation.pipe", 1, 10, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.silicone", 384)).duration(10 * SECONDS).eut(30)
                .addTo(chemicalBathRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 1L))
                .itemOutputs(getModItem(ProjectRedFabrication.ID, "projectred.fabrication.icchip", 1, 0, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(15 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Epoxy.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 4L))
                .itemOutputs(getModItem(ProjectRedFabrication.ID, "projectred.fabrication.icblueprint", 1, 0, missing))
                .fluidInputs(FluidRegistry.getFluidStack("molten.redstone", 144)).duration(30 * SECONDS).eut(30)
                .addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Paper, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(getModItem(ProjectRedExpansion.ID, "projectred.expansion.plan", 1, 0, missing))
                .duration(5 * SECONDS).eut(8).addTo(assemblerRecipes);
    }

    private void circuitAssemblerRecipes() {
        for (Materials solderingMaterial : solderingMaterials) {
            // Null-Logic Routing Chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.ULV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Gold, 4L))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 44, missing))
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Gold, 6L))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 44, missing))
                    .duration(20 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 16, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Gold, 8L))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 16, 44, missing))
                    .duration(25 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            // Item Responder chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Copper, 1),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 1L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 20, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    4,
                                    0,
                                    missing))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 2L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 20, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    8,
                                    0,
                                    missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 16, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnnealedCopper, 1),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 4L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 20, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    16,
                                    0,
                                    missing))
                    .duration(50 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            // Dynamic Item Responder chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 1L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 20, missing),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 28, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    4,
                                    1,
                                    missing))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                            getModItem(GTPlusPlus.ID, "itemPlatePotin", 1, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 2L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 20, missing),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 28, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    8,
                                    1,
                                    missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 16, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BlackBronze, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 2L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 20, missing),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 28, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    16,
                                    1,
                                    missing))
                    .duration(50 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            // Item Overflow Responder chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 1L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 32, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    4,
                                    2,
                                    missing))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 2L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 32, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    8,
                                    2,
                                    missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 16, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.ConductiveIron, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 4L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 32, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    16,
                                    2,
                                    missing))
                    .duration(50 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            // Item Terminator chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 1L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 29, missing),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 26, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    4,
                                    3,
                                    missing))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TinAlloy, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 2L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 29, missing),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 26, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    8,
                                    3,
                                    missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 16, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderiumBase, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 4L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 29, missing),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 26, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    16,
                                    3,
                                    missing))
                    .duration(50 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            // Item Extractor chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 1L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 28, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    4,
                                    4,
                                    missing))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.ElectricalSteel, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 2L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 28, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    8,
                                    4,
                                    missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 16, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DarkSteel, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 4L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 28, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    16,
                                    4,
                                    missing))
                    .duration(50 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            // Item Broadcaster chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 1L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 21, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    4,
                                    5,
                                    missing))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Electrum, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 2L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 21, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    8,
                                    5,
                                    missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 16, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnergeticAlloy, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 4L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 21, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    16,
                                    5,
                                    missing))
                    .duration(50 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            // Item Stock Keeper chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CobaltBrass, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 1L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 30, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    4,
                                    6,
                                    missing))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Cobalt, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 2L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 30, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    8,
                                    6,
                                    missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 16, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Manyullyn, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 4L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 30, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    16,
                                    6,
                                    missing))
                    .duration(50 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            // Item Crafting chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Glowstone, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 1L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 24, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    4,
                                    7,
                                    missing))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 2L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 24, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    8,
                                    7,
                                    missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 16, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BlueAlloy, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 4L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 24, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    16,
                                    7,
                                    missing))
                    .duration(50 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            // Item Crafting Extension chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silver, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 1L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 33, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    4,
                                    8,
                                    missing))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.SterlingSilver, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 2L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 33, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    8,
                                    8,
                                    missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 16, 44, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnergeticSilver, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 4L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 33, missing))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(
                            getModItem(
                                    ProjectRedTransportation.ID,
                                    "projectred.transportation.routingchip",
                                    16,
                                    8,
                                    missing))
                    .duration(50 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);
        }
    }

    private void recipes2() {
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 6, 56, missing),
                        ItemList.Battery_Hull_LV.get(1L))
                .itemOutputs(getModItem(ProjectRedExpansion.ID, "projectred.expansion.emptybattery", 1, 0, missing))
                .duration(5 * SECONDS).eut(2).addTo(cannerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 0, missing),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 13, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 7, missing))
                .duration(30 * SECONDS).eut(30).addTo(formingPressRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 0, missing),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 14, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 8, missing))
                .duration(30 * SECONDS).eut(30).addTo(formingPressRecipes);
        for (ItemStack itemStack : OreDictionary.getOres("craftingLensRed")) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 42, missing),
                            GT_Utility.copyAmount(0, itemStack))
                    .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 13, missing))
                    .duration(30 * SECONDS).eut(30).addTo(laserEngraverRecipes);
        }
        for (ItemStack itemStack : OreDictionary.getOres("craftingLensYellow")) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 43, missing),
                            GT_Utility.copyAmount(0, itemStack))
                    .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 14, missing))
                    .duration(30 * SECONDS).eut(30).addTo(laserEngraverRecipes);
        }
        for (ItemStack itemStack : OreDictionary.getOres("craftingLensBlue")) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 58, missing),
                            GT_Utility.copyAmount(0, itemStack))
                    .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 59, missing))
                    .duration(30 * SECONDS).eut(30).addTo(laserEngraverRecipes);
        }
        GT_Values.RA.stdBuilder()
                .itemInputs(getModItem(ProjectRedExploration.ID, "projectred.exploration.stone", 1, 11, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 9, 56, missing)).outputChances(10000)
                .duration(15 * SECONDS).eut(2).addTo(maceratorRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(ItemList.Circuit_Silicon_Wafer.get(1L), getModItem(Minecraft.ID, "redstone", 8, 0, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 42, missing))
                .duration(20 * SECONDS).eut(30).addTo(formingPressRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer2.get(1L),
                        getModItem(Minecraft.ID, "redstone", 16, 0, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 42, missing))
                .duration(40 * SECONDS).eut(30).addTo(formingPressRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer3.get(1L),
                        getModItem(Minecraft.ID, "redstone", 32, 0, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 42, missing))
                .duration(60 * SECONDS).eut(30).addTo(formingPressRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer.get(1L),
                        getModItem(Minecraft.ID, "glowstone_dust", 8, 0, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 43, missing))
                .duration(20 * SECONDS).eut(30).addTo(formingPressRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer2.get(1L),
                        getModItem(Minecraft.ID, "glowstone_dust", 16, 0, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 43, missing))
                .duration(40 * SECONDS).eut(30).addTo(formingPressRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer3.get(1L),
                        getModItem(Minecraft.ID, "glowstone_dust", 32, 0, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 43, missing))
                .duration(60 * SECONDS).eut(30).addTo(formingPressRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer.get(1L),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 56, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 58, missing))
                .duration(20 * SECONDS).eut(30).addTo(formingPressRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer2.get(1L),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 16, 56, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 58, missing))
                .duration(40 * SECONDS).eut(30).addTo(formingPressRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer3.get(1L),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 32, 56, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 58, missing))
                .duration(60 * SECONDS).eut(30).addTo(formingPressRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "iron_ingot", 1, 0, missing),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 56, missing))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 57, missing))
                .duration(20 * SECONDS).eut(30).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 15, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 19, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 0, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 19, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(BiomesOPlenty.ID, "misc", 2, 8, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 19, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_15.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 19, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ExtraBees.ID, "misc", 2, 23, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 19, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 0, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 19, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 25, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 19, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 14, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 20, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 1, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 20, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_14.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 20, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 1, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 20, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 24, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 20, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 13, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 21, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 2, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 21, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_13.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 21, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 2, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 21, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 23, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 21, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 12, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 22, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 3, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 22, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_12.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 22, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 3, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 22, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 22, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 22, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 11, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 23, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 4, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 23, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.YellowLimonite, 2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 23, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_11.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 23, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ExtraBees.ID, "misc", 2, 20, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 23, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 4, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 23, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 21, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 23, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 10, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 24, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 5, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 24, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_10.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 24, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 5, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 24, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 20, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 24, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Soapstone, 2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 24, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 9, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 25, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 6, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 25, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_09.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 25, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 6, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 25, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 19, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 25, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 8, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 26, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 7, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 26, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_08.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 26, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 7, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 26, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 18, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 26, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 7, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 27, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 8, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 27, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_07.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 27, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 8, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 27, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 17, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 27, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 6, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 28, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 9, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 28, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lazurite, 2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 28, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Lazurite, 2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 28, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_06.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 28, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 9, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 28, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 16, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 28, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 5, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 29, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 10, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 29, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_05.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 29, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 10, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 29, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 15, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 29, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 4, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 30, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(IndustrialCraft2.ID, "itemDust", 2, 12, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 30, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 11, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 30, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Natura.ID, "barleyFood", 2, 8, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 30, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(BiomesOPlenty.ID, "misc", 2, 5, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 30, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sodalite, 2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 30, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lapis, 2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 30, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sodalite, 2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 30, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Dye_Indigo.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 30, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_04.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 30, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ExtraBees.ID, "misc", 2, 21, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 30, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 11, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 30, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 14, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 30, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 3, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 31, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(GalacticraftAmunRa.ID, "item.baseItem", 2, 29, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 31, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 12, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 31, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(BiomesOPlenty.ID, "misc", 2, 6, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 31, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(PamsHarvestCraft.ID, "cocoapowderItem", 2, 0, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 31, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(GregTech.ID, "gt.metaitem.01", 2, 2887, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 31, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(GregTech.ID, "gt.metaitem.01", 2, 2888, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 31, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.BrownLimonite, 2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 31, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_03.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 31, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(IndustrialCraft2.ID, "itemCofeePowder", 2, 0, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 31, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ExtraBees.ID, "misc", 2, 25, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 31, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 12, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 31, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 13, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 31, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 2, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 32, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 13, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 32, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(BiomesOPlenty.ID, "misc", 2, 7, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 32, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_02.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 32, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ExtraBees.ID, "misc", 2, 22, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 32, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 13, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 32, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 12, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 32, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 1, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 33, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 14, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 33, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_01.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 33, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ExtraBees.ID, "misc", 2, 19, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 33, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 14, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 33, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 11, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 33, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Minecraft.ID, "dye", 2, 0, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 34, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Botania.ID, "dye", 2, 15, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 34, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(BiomesOPlenty.ID, "misc", 2, 9, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 34, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        ItemList.Color_00.get(2L),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 34, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ForbiddenMagic.ID, "FMResource", 2, 1, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 34, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ThaumicHorizons.ID, "inkEgg", 2, 0, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 34, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ExtraBees.ID, "misc", 2, 24, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 34, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(ProjectRedExploration.ID, "projectred.exploration.lilyseed", 2, 15, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 34, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glowstone_dust", 2, 0, missing),
                        getModItem(Gendustry.ID, "HoneyDrop", 2, 10, missing),
                        GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(getModItem(ProjectRedCore.ID, "projectred.core.part", 8, 34, missing))
                .duration(2 * SECONDS + 10 * TICKS).eut(8).addTo(mixerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 0, missing),
                        getModItem(GregTech.ID, "gt.metaitem.01", 1, 17880, missing))
                .itemOutputs(getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing))
                .duration(5 * SECONDS).eut(8).addTo(packagerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 1L))
                .itemOutputs(getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing))
                .duration(5 * SECONDS).eut(8).addTo(packagerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 1L))
                .itemOutputs(getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing))
                .duration(5 * SECONDS).eut(8).addTo(packagerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        com.dreammaster.item.ItemList.ElectrotineWire.getIS(1),
                        getModItem(GregTech.ID, "gt.metaitem.01", 1, 17880, missing))
                .itemOutputs(getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 34, missing))
                .duration(5 * SECONDS).eut(8).addTo(packagerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        com.dreammaster.item.ItemList.ElectrotineWire.getIS(1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 1L))
                .itemOutputs(getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 34, missing))
                .duration(5 * SECONDS).eut(8).addTo(packagerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        com.dreammaster.item.ItemList.ElectrotineWire.getIS(1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 1L))
                .itemOutputs(getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 34, missing))
                .duration(5 * SECONDS).eut(8).addTo(packagerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 16, missing))
                .itemOutputs(
                        getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 0, missing),
                        getModItem(GregTech.ID, "gt.metaitem.01", 1, 17880, missing))
                .duration(5 * SECONDS).eut(8).addTo(unpackagerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 34, missing))
                .itemOutputs(
                        com.dreammaster.item.ItemList.ElectrotineWire.getIS(1),
                        getModItem(GregTech.ID, "gt.metaitem.01", 1, 17880, missing))
                .duration(5 * SECONDS).eut(8).addTo(unpackagerRecipes);

    }
}
