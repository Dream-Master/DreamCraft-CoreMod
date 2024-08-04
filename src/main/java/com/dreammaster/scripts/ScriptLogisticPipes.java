package com.dreammaster.scripts;

import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.BartWorks;
import static gregtech.api.enums.Mods.BuildCraftSilicon;
import static gregtech.api.enums.Mods.ExtraUtilities;
import static gregtech.api.enums.Mods.Forestry;
import static gregtech.api.enums.Mods.GalacticraftCore;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.enums.Mods.IronChests;
import static gregtech.api.enums.Mods.LogisticsPipes;
import static gregtech.api.enums.Mods.Minecraft;
import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.enums.Mods.OpenComputers;
import static gregtech.api.enums.Mods.ProjectRedCore;
import static gregtech.api.enums.Mods.ProjectRedExpansion;
import static gregtech.api.enums.Mods.Railcraft;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.recipe.RecipeMaps.formingPressRecipes;
import static gregtech.api.util.GT_ModHandler.getModItem;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;

import java.util.Arrays;
import java.util.List;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.dreammaster.gthandler.CustomItemList;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

public class ScriptLogisticPipes implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Logistic Pipes";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(
                LogisticsPipes.ID,
                ProjectRedExpansion.ID,
                ProjectRedCore.ID,
                AppliedEnergistics2.ID,
                BartWorks.ID,
                BuildCraftSilicon.ID,
                ExtraUtilities.ID,
                Forestry.ID,
                GalacticraftCore.ID,
                IndustrialCraft2.ID,
                IronChests.ID,
                OpenComputers.ID,
                Railcraft.ID,
                Thaumcraft.ID);
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
        loadModuleRecipes();
        loadUpgradeRecipes();
        loadPipeRecipes();
        loadMiscRecipes();
    }

    private void loadModuleRecipes() {
        for (Materials solderingMaterial : solderingMaterials) {
            // Blank Module
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Primitive, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Gold, 4L),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 4, 0, missing)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            // ItemSink Module
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.itemModule", 4, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Primitive, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 32, missing),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 4, 1, missing)).duration(25 * SECONDS)
                    .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            // Passive Supplier Module
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.itemModule", 4, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Primitive, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 8L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 20, missing),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 4, 2, missing)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            // Extractor Module
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.itemModule", 2, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Primitive, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 8L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 30, missing),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 2, 3, missing)).duration(25 * SECONDS)
                    .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            // Polymorphic ItemSink Module
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.itemModule", 2, 1, missing),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 4L),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Primitive, 2L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 2L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 20, missing),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 2, 4, missing)).duration(20 * SECONDS)
                    .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.itemModule", 4, 1, missing),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Primitive, 2L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 20, missing),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 4, 4, missing)).duration(30 * SECONDS)
                    .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            // QuickSort Module
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.itemModule", 1, 501, missing),
                            ItemList.Robot_Arm_LV.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.BlueAlloy, 8L),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 5, missing))
                    .duration(120 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

            // Advanced Extractor Module
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.itemModule", 1, 3, missing),
                            getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 5, missing),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 7, missing)).duration(45 * SECONDS)
                    .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            // Provider Module
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.itemModule", 4, 0, missing),
                            ItemList.Electric_Motor_LV.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Primitive, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Gold, 8L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 30, missing),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 4, 500, missing))
                    .duration(20 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            // Bee Analyzer module
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.itemModule", 1, 9, missing),
                            GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Glass, 1L),
                            ItemList.Conveyor_Module_MV.get(1L),
                            ItemList.Sensor_MV.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 8, missing)).duration(80 * SECONDS)
                    .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

            // Apiary Refiller Module
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.itemModule", 1, 9, missing),
                            ItemList.Robot_Arm_MV.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.StainlessSteel, 4L),
                            ItemList.Sensor_MV.get(1L),
                            ItemList.Emitter_MV.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 10, missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

            // Crafting Module
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.itemModule", 5, 0, missing),
                            ItemList.Emitter_MV.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                            ItemList.Cover_Crafting.get(5L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 30, missing),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 5, 600, missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            // Type Filter ItemSink module
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.itemModule", 2, 1, missing),
                            ItemList.Conveyor_Module_LV.get(1L),
                            ItemList.Circuit_Parts_Diode.get(2L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 30, missing),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 2, 17, missing))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        }

        // Terminus Module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 0, missing),
                        getModItem(ExtraUtilities.ID, "trashcan", 1, 0, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 6, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // Extractor MK2 module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 3, missing),
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 5, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 103, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // Advanced Extractor MK2
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 7, missing),
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 5, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 107, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 103, missing),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 5, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 107, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // Extractor MK3 module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 3, missing),
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 6, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 203, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // Advanced Extractor MK3
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 7, missing),
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 6, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 207, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 203, missing),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 5, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 207, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // Provider module MK2
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 500, missing),
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 6, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 501, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // Electric Manager module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 1, missing),
                        getModItem(IndustrialCraft2.ID, "itemBatREDischarged", 1, 0, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 300, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 1, missing),
                        getModItem(IndustrialCraft2.ID, "itemBatRE", 1, wildcard, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 300, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // BeeSink module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 1, missing),
                        getModItem(Forestry.ID, "beeswax", 4, missing),
                        ItemList.Circuit_Parts_Diode.get(2L),
                        GT_Utility.getIntegratedCircuit(18))
                .fluidInputs(FluidRegistry.getFluidStack("for.honey", 1000))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 9, missing)).duration(40 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Drone Terminus Module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 9, missing),
                        getModItem(ExtraUtilities.ID, "trashcan", 1, 0, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 11, missing)).duration(80 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(formingPressRecipes);

        // Mod Based ItemSink module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 1, missing),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Gold, 2L))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 12, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // OreDict ItemSink module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 1, missing),
                        getModItem(Minecraft.ID, "writable_book", 1, wildcard, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 13, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // Thaumic AspectSink module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 1, missing),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 5, missing),
                        getModItem(Thaumcraft.ID, "ItemShard", 2, 6, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 30, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // Enchantment Sink module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 1, missing),
                        getModItem(Minecraft.ID, "enchanted_book", 1, wildcard, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 31, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // Enchantment Sink module MK2
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 31, missing),
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 5, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 131, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // CC Based QuickSort module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 5, missing),
                        getModItem(OpenComputers.ID, "item", 2, 24, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 14, missing)).duration(120 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(formingPressRecipes);

        // CC Based ItemSink module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 1, missing),
                        getModItem(OpenComputers.ID, "item", 2, 24, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 15, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(formingPressRecipes);

        // Crafting module MK2
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 4, 600, missing),
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 5, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 4, 601, missing)).duration(40 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(formingPressRecipes);

        // Crafting module MK3
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 4, 601, missing),
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 6, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 4, 602, missing)).duration(90 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(formingPressRecipes);

        // Active Supplier Module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 4, 2, missing),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 2, missing),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 28, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 4, 502, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // Creative Tab Based ItemSink module
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 1, missing),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 2, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemModule", 1, 16, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);
    }

    private void loadUpgradeRecipes() {
        // Blank Upgrade
        for (Materials solderingMaterial : solderingMaterials) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Primitive, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Tin, 4L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4L),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.logisticsParts", 4, 4, missing))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Primitive, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Tin, 4L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4L),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.logisticsParts", 4, 4, missing))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        }

        // Sneaky upgrades
        for (int side = 0; side < 6; ++side) {
            // Sides: Up, Down, N, S, E, W
            // Circuits: 5 - 6 - 1 - 2 - 3 - 4
            int meta = side;
            int circuit = side < 2 ? side + 5 : side - 1;

            // Sneaky Upgrade
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.logisticsParts", 4, 4, missing),
                            ItemList.Electric_Piston_LV.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Bronze, 2L),
                            getModItem(ProjectRedCore.ID, "projectred.core.part", 4, 33, missing),
                            GT_Utility.getIntegratedCircuit(circuit))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemUpgrade", 4, meta, missing))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            // Modification

            // UP
            addShapedRecipe(
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 0, missing),
                    null,
                    null,
                    "craftingToolScrewdriver",
                    null,
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, meta, missing),
                    null,
                    null,
                    null,
                    null);

            // DOWN
            addShapedRecipe(
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 1, missing),
                    null,
                    null,
                    null,
                    null,
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, meta, missing),
                    null,
                    null,
                    null,
                    "craftingToolScrewdriver");

            // NORTH
            addShapedRecipe(
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 2, missing),
                    null,
                    "craftingToolScrewdriver",
                    null,
                    null,
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, meta, missing),
                    null,
                    null,
                    null,
                    null);

            // SOUTH
            addShapedRecipe(
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 3, missing),
                    null,
                    null,
                    null,
                    null,
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, meta, missing),
                    null,
                    null,
                    "craftingToolScrewdriver",
                    null);

            // EAST
            addShapedRecipe(
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 4, missing),
                    null,
                    null,
                    null,
                    null,
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, meta, missing),
                    "craftingToolScrewdriver",
                    null,
                    null,
                    null);

            // WEST
            addShapedRecipe(
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 5, missing),
                    null,
                    null,
                    null,
                    "craftingToolScrewdriver",
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, meta, missing),
                    null,
                    null,
                    null,
                    null);
        }

        // Disconnection upgrades
        for (int side = 0; side < 6; ++side) {
            // Sides: Up, Down, N, S, E, W
            // Circuits: 5 - 6 - 1 - 2 - 3 - 4
            int meta = side + 10;
            int circuit = side < 2 ? side + 5 : side - 1;

            // Disconnection Upgrade
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 4, missing),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L),
                            GT_Utility.getIntegratedCircuit(circuit))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemUpgrade", 4, meta, missing))
                    .duration(8 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            // Modification

            // UP
            addShapedRecipe(
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 10, missing),
                    null,
                    null,
                    "craftingToolScrewdriver",
                    null,
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, meta, missing),
                    null,
                    null,
                    null,
                    null);

            // DOWN
            addShapedRecipe(
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 11, missing),
                    null,
                    null,
                    null,
                    null,
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, meta, missing),
                    null,
                    null,
                    null,
                    "craftingToolScrewdriver");

            // NORTH
            addShapedRecipe(
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 12, missing),
                    null,
                    "craftingToolScrewdriver",
                    null,
                    null,
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, meta, missing),
                    null,
                    null,
                    null,
                    null);

            // SOUTH
            addShapedRecipe(
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 13, missing),
                    null,
                    null,
                    null,
                    null,
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, meta, missing),
                    null,
                    null,
                    "craftingToolScrewdriver",
                    null);

            // EAST
            addShapedRecipe(
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 14, missing),
                    null,
                    null,
                    null,
                    null,
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, meta, missing),
                    "craftingToolScrewdriver",
                    null,
                    null,
                    null);

            // WEST
            addShapedRecipe(
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 15, missing),
                    null,
                    null,
                    null,
                    "craftingToolScrewdriver",
                    getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, meta, missing),
                    null,
                    null,
                    null,
                    null);
        }

        // Item Speed Upgrade
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 8, 4, missing),
                        ItemList.Electric_Motor_LV.get(1L),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 32, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemUpgrade", 8, 20, missing))
                .fluidInputs(Materials.Lubricant.getFluid(500)).duration(30 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // Advanced Satellite Upgrade
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 2, 4, missing),
                        ItemList.Sensor_LV.get(1L),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 2, missing),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 23, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemUpgrade", 2, 21, missing)).duration(40 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Fluid Crafting Upgrade
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 4, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 600, missing),
                        ItemList.Large_Fluid_Cell_Steel.get(1L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemUpgrade", 4, 22, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Crafting Byproduct Extraction Upgrade
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 2, 4, missing),
                        ItemList.Conveyor_Module_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.RedAlloy, 1L),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 33, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemUpgrade", 2, 23, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Placement Rules Upgrade
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 2, 4, missing),
                        ItemList.Robot_Arm_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.RedAlloy, 1L),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 20, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemUpgrade", 2, 24, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Fuzzy Upgrade
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 2, 4, missing),
                        getModItem(Minecraft.ID, "wool", 1, wildcard, missing),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Steel, 1L),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 26, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemUpgrade", 2, 25, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // CC Remote Control Upgrade
        // No recipe.

        // Crafting Monitoring Upgrade
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 4, missing),
                        ItemList.Emitter_MV.get(1L),
                        ItemList.Sensor_MV.get(1L),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 3, missing),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 24, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 41, missing)).duration(60 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Opaque Upgrade
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 4, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1L))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemUpgrade", 4, 42, missing)).duration(8 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // Crafting Cleanup Upgrade
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 4, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 107, missing),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 2, missing),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 1, 33, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 26, missing)).duration(60 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Upgrade Module Upgrade
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 2, 4, missing),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Primitive, 1L),
                        getModItem(Minecraft.ID, "chest", 1, 0, missing),
                        getModItem(ProjectRedCore.ID, "projectred.core.part", 2, 34, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.itemUpgrade", 2, 44, missing)).duration(20 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
    }

    private void loadPipeRecipes() {
        for (Materials solderingMaterial : solderingMaterials) {
            // Basic Logistics Pipe
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.PipeItemsBasicTransport", 8, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Gold, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Diamond, 1),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 16),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 8, 0, missing))
                    .duration(20 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.PipeItemsBasicTransport", 16, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Gold, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Diamond, 1),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2L),
                            GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedstoneAlloy, 8),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 16, 0, missing))
                    .duration(20 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        }

        // Request Logistics Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 1, 0, missing),
                        ItemList.Emitter_LV.get(1L),
                        ItemList.Conveyor_Module_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderPearl, 4),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsRequestLogistics", 1, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Request Logistics Pipe Mk2
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsRequestLogistics", 1, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 2, 5, missing),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 4, 1, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsRequestLogisticsMk2", 1, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Provider Logistics Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 3, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 500, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsProviderLogistics", 3, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Provider Logistics Pipe Mk2
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsProviderLogistics", 2, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 6, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsProviderLogisticsMk2", 2, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 2, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 501, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsProviderLogisticsMk2", 2, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Crafting Logistics Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 2, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 600, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsCraftingLogistics", 2, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Crafting Logistics Pipe Mk2
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsCraftingLogistics", 2, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 5, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsCraftingLogisticsMk2", 2, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 2, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 601, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsCraftingLogisticsMk2", 2, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Crafting Logistics Pipe Mk3
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsCraftingLogisticsMk2", 2, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 6, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsCraftingLogisticsMk3", 2, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 2, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 602, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsCraftingLogisticsMk3", 2, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Satellite Logistics Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 2, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 0, missing),
                        ItemList.Robot_Arm_LV.get(1L),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 2, 5, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderPearl, 1L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsSatelliteLogistics", 2, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Supplier Logistics Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 3, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 502, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsSupplierLogistics", 3, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Logistics Chassis Mk1
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 2L),
                        getModItem(Minecraft.ID, "chest", 1, 0, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeLogisticsChassiMk1", 2, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Logistics Chassis Mk2
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeLogisticsChassiMk1", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2L),
                        getModItem(IronChests.ID, "BlockIronChest", 1, 3, missing),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 5, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeLogisticsChassiMk2", 2, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 6, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4L),
                        getModItem(IronChests.ID, "BlockIronChest", 1, 3, missing),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeLogisticsChassiMk2", 6, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Logistics Chassis Mk3
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeLogisticsChassiMk2", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Electrum, 2L),
                        getModItem(IronChests.ID, "BlockIronChest", 1, 0, missing),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 2, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeLogisticsChassiMk3", 2, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 6, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Electrum, 4L),
                        getModItem(IronChests.ID, "BlockIronChest", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeLogisticsChassiMk3", 6, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Logistics Chassis Mk4
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeLogisticsChassiMk3", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Kanthal, 2L),
                        getModItem(IronChests.ID, "BlockIronChest", 1, 4, missing),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 3, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeLogisticsChassiMk4", 2, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 6, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Kanthal, 8L),
                        getModItem(IronChests.ID, "BlockIronChest", 2, 4, missing),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeLogisticsChassiMk4", 6, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Logistics Chassis Mk5
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeLogisticsChassiMk4", 2, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.VibrantAlloy, 2L),
                        getModItem(IronChests.ID, "BlockIronChest", 1, 1, missing),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 7, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeLogisticsChassiMk5", 2, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 12, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.VibrantAlloy, 6L),
                        getModItem(IronChests.ID, "BlockIronChest", 2, 1, missing),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1L),
                        getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 6, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeLogisticsChassiMk5", 12, 0, missing))
                .duration(60 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Remote Orderer Logistics Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.remoteOrdererItem", 0, wildcard, missing),
                        getModItem(LogisticsPipes.ID, "item.PipeItemsRequestLogistics", 1, 0, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsRemoteOrdererLogistics", 1, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Bee Analyzer Logistics Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 2, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 8, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsApiaristAnalyser", 2, 0, missing))
                .duration(80 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // BeeSink Logistics Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 3, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 9, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsApiaristSink", 3, 0, missing))
                .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Logistics Firewall Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 1, 0, missing),
                        getModItem(Minecraft.ID, "web", 1, 0, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsFirewall", 1, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Logistics Fluid Container Supplier
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeFluidBasic", 5, 0, missing),
                        ItemList.Robot_Arm_LV.get(1L),
                        getModItem(Minecraft.ID, "bucket", 1, 0, missing),
                        ItemList.Large_Fluid_Cell_Steel.get(1L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsFluidSupplier", 5, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Logistics Fluid Basic Pipe
        for (Materials solderingMaterial : solderingMaterials) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 12, 0, missing),
                            ItemList.Electric_Pump_LV.get(1L),
                            ItemList.Large_Fluid_Cell_Steel.get(1L),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 144))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeFluidBasic", 12, 0, missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        }

        // Logistics Fluid Insertion Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeFluidBasic", 6, 0, missing),
                        ItemList.Electric_Pump_MV.get(1L),
                        ItemList.Large_Fluid_Cell_Steel.get(1L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeFluidInsertion", 6, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Logistics Fluid Provider Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeFluidBasic", 3, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 500, missing),
                        ItemList.Large_Fluid_Cell_Steel.get(1L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeFluidProvider", 3, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Logistics Fluid Request Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeFluidBasic", 1, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.PipeItemsRequestLogistics", 1, 0, missing),
                        ItemList.Large_Fluid_Cell_Steel.get(1L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeFluidRequestLogistics", 1, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Logistics Fluid Extractor Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeFluidBasic", 4, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 3, missing),
                        ItemList.Large_Fluid_Cell_Steel.get(1L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeFluidExtractor", 4, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Logistics Fluid Satellite Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeFluidBasic", 1, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 0, missing),
                        ItemList.Robot_Arm_LV.get(1L),
                        getModItem(BuildCraftSilicon.ID, "redstoneChipset", 2, 5, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderPearl, 1L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeFluidSatellite", 1, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Logistics Fluid Supplier
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "item.PipeFluidBasic", 4, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 502, missing),
                        ItemList.Large_Fluid_Cell_Steel.get(1L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeFluidSupplierMk2", 4, 0, missing))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Unrouted Transport Pipe
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 2L),
                        getModItem(BartWorks.ID, "BWPumpParts", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 2L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsBasicTransport", 8, 0, missing))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 2L),
                        getModItem(Railcraft.ID, "glass", 4, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 8L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsBasicTransport", 32, 0, missing))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 2L),
                        getModItem(ExtraUtilities.ID, "decorativeBlock2", 4, 5, missing),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 8L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsBasicTransport", 32, 0, missing))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Aluminium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.ReinforceGlass, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 16L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsBasicTransport", 64, 0, missing))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Aluminium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BorosilicateGlass, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 16L),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeItemsBasicTransport", 64, 0, missing))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
    }

    private void loadMiscRecipes() {

        // Logistics Power Junction
        for (Materials solderingMaterial : solderingMaterials) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Transformer_MV_LV.get(1L),
                            getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 4, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2L),
                            GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.Copper, 4L),
                            GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 8L),
                            GT_Utility.getIntegratedCircuit(24))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 288))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "logisticsSolidBlock", 1, 1, missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Transformer_MV_LV.get(1L),
                            getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 4, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2L),
                            GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.AnnealedCopper, 4L),
                            GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 8L),
                            GT_Utility.getIntegratedCircuit(24))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 288))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "logisticsSolidBlock", 1, 1, missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        }

        // Logistics Security Station
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_MV.get(1L),
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 4, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Electrum, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.Copper, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 4L),
                        GT_Utility.getIntegratedCircuit(24))
                .fluidInputs(Materials.Plastic.getMolten(576))
                .itemOutputs(getModItem(LogisticsPipes.ID, "logisticsSolidBlock", 1, 2, missing)).duration(80 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Logistics Crafting Table
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_LV.get(1L),
                        getModItem(Forestry.ID, "factory2", 1, 2, missing),
                        ItemList.Electric_Piston_LV.get(1L),
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 1, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 600, missing),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.Copper, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.BlueAlloy, 4L),
                        GT_Utility.getIntegratedCircuit(24))
                .fluidInputs(Materials.Plastic.getMolten(576))
                .itemOutputs(getModItem(LogisticsPipes.ID, "logisticsSolidBlock", 1, 3, missing)).duration(45 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_LV.get(1L),
                        getModItem(ProjectRedExpansion.ID, "projectred.expansion.machine2", 1, 10, missing),
                        ItemList.Electric_Piston_LV.get(1L),
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 1, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 600, missing),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.Copper, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.BlueAlloy, 4L),
                        GT_Utility.getIntegratedCircuit(24))
                .fluidInputs(Materials.Plastic.getMolten(576))
                .itemOutputs(getModItem(LogisticsPipes.ID, "logisticsSolidBlock", 1, 3, missing)).duration(45 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Logistics Fuzzy Crafting Table
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(LogisticsPipes.ID, "logisticsSolidBlock", 1, 3, missing),
                        getModItem(LogisticsPipes.ID, "item.itemUpgrade", 1, 25, missing))
                .itemOutputs(getModItem(LogisticsPipes.ID, "logisticsSolidBlock", 1, 4, missing)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(formingPressRecipes);

        // Logistics Statistics Table
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_MV.get(1L),
                        getModItem(LogisticsPipes.ID, "item.PipeItemsBasicLogistics", 4, 0, missing),
                        ItemList.Sensor_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Copper, 4L),
                        GT_Utility.getIntegratedCircuit(24))
                .fluidInputs(Materials.Plastic.getMolten(576))
                .itemOutputs(getModItem(LogisticsPipes.ID, "logisticsSolidBlock", 1, 5, missing)).duration(80 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Logistics Request Table
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_MV.get(1L),
                        CustomItemList.Display.get(1L),
                        ItemList.Cover_Crafting.get(1L),
                        getModItem(OpenComputers.ID, "keyboard", 1, 0, missing),
                        getModItem(LogisticsPipes.ID, "item.PipeItemsRequestLogistics", 1, 0, missing),
                        getModItem(IronChests.ID, "BlockIronChest", 1, 3, missing),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2L),
                        GT_Utility.getIntegratedCircuit(24))
                .fluidInputs(Materials.Plastic.getMolten(576))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.PipeBlockRequestTable", 1, 0, missing))
                .duration(80 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        for (Materials solderingMaterial : solderingMaterials) {
            // Gold Upgrade Chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(BuildCraftSilicon.ID, "redstoneChipset", 2, 2, missing),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 2L),
                            ItemList.Circuit_Parts_Diode.get(1L),
                            getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                            GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 4L),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.logisticsParts", 4, 5, missing))
                    .duration(20 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            // Diamond Upgrade Chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.logisticsParts", 2, 5, missing),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 2L),
                            ItemList.Circuit_Parts_Diode.get(2L),
                            getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                            GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Platinum, 4L),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 36))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.logisticsParts", 4, 6, missing))
                    .duration(40 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

            // Advanced Gold Crafting Chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 5, missing),
                            getModItem(NewHorizonsCoreMod.ID, "item.GoldCoreChip", 1, 0, missing),
                            ItemList.Robot_Arm_MV.get(2L),
                            getModItem(IndustrialCraft2.ID, "upgradeModule", 2, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedSteel, 16L),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 72))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 7, missing))
                    .duration(45 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

            // Advanced Diamond Crafting Chip
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 7, missing),
                            getModItem(NewHorizonsCoreMod.ID, "item.DiamondCoreChip", 1, 0, missing),
                            ItemList.Robot_Arm_HV.get(1L),
                            getModItem(IndustrialCraft2.ID, "upgradeModule", 4, 0, missing),
                            GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedSteel, 32L),
                            GT_Utility.getIntegratedCircuit(18))
                    .fluidInputs(getSolderingFluid(solderingMaterial, 144))
                    .itemOutputs(getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 8, missing))
                    .duration(45 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        }

        // Remote Orderer
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Emitter_MV.get(1L),
                        ItemList.Sensor_MV.get(1L),
                        CustomItemList.Display.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Diamond, 4L),
                        GT_Utility.getIntegratedCircuit(18))
                .fluidInputs(Materials.Plastic.getMolten(144))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.remoteOrdererItem", 1, 0, missing))
                .duration(80 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Dyeing of Remote Orderer is in /gthandler/recipes/ChemicalBathRecipes.java

        // Crafting Sign Creator
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Cover_Screen.get(1),
                        getModItem(Minecraft.ID, "sign", 1, 0, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.ItemPipeSignCreator", 1, 0, missing))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Logistics Pipe Controller
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Cover_Screen.get(1),
                        getModItem(LogisticsPipes.ID, "item.itemModule", 1, 0, missing),
                        GT_Utility.getIntegratedCircuit(18))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.pipeController", 1, 0, missing)).duration(80 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Logistics Disk
        addShapedRecipe(
                getModItem(LogisticsPipes.ID, "item.itemDisk", 1, 0, missing),
                "plateCarbon",
                "plateCarbon",
                "plateCarbon",
                "plateCarbon",
                getModItem(OpenComputers.ID, "item", 1, 19, missing),
                "plateCarbon",
                "plateCarbon",
                "circuitPrimitive",
                "plateCarbon");

        // Logistics HUD Glasses
        addShapedRecipe(
                getModItem(LogisticsPipes.ID, "item.logisticsHUDGlasses", 1, 0, missing),
                getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 1, missing),
                getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 2, missing),
                getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 1, missing),
                "screwAluminium",
                "circuitGood",
                "screwAluminium",
                getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 0, missing),
                "craftingToolScrewdriver",
                getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 0, missing));

        // Logistics HUD Bow
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Aluminium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Diamond, 1L),
                        GT_Utility.getIntegratedCircuit(18))
                .fluidInputs(Materials.Aluminium.getMolten(144))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 0, missing)).duration(80 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Logistics HUD Glass
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Glass, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Aluminium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                        GT_Utility.getIntegratedCircuit(18))
                .fluidInputs(Materials.Aluminium.getMolten(144))
                .itemOutputs(getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 1, missing)).duration(40 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Logistics HUD Nose Bridge
        addShapedRecipe(
                getModItem(LogisticsPipes.ID, "item.logisticsParts", 1, 2, missing),
                getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 3, missing),
                "stickDiamond",
                getModItem(BuildCraftSilicon.ID, "redstoneChipset", 1, 3, missing),
                "craftingToolFile",
                "screwAluminium",
                "craftingToolSoftHammer",
                null,
                "craftingToolScrewdriver",
                null);
    }
}