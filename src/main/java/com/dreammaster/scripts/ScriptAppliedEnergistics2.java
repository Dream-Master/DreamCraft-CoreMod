package com.dreammaster.scripts;

import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.Avaritia;
import static gregtech.api.enums.Mods.BartWorks;
import static gregtech.api.enums.Mods.EternalSingularity;
import static gregtech.api.enums.Mods.ExtraUtilities;
import static gregtech.api.enums.Mods.GregTech;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.enums.Mods.IronChests;
import static gregtech.api.enums.Mods.Minecraft;
import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.enums.Mods.TinkerConstruct;
import static gregtech.api.util.GT_ModHandler.getModItem;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sAlloySmelterRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sAssemblerRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sBlastRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sCircuitAssemblerRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sMaceratorRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sWiremillRecipes;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_RecipeBuilder.TICKS;
import static gregtech.api.util.GT_RecipeConstants.UniversalChemical;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.dreammaster.avaritia.AvaritiaHelper;
import com.dreammaster.gthandler.CustomItemList;

import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

public class ScriptAppliedEnergistics2 implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "AppliedEnergistics2";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(
                AppliedEnergistics2.ID,
                TinkerConstruct.ID,
                Avaritia.ID,
                EternalSingularity.ID,
                BartWorks.ID,
                ExtraUtilities.ID,
                IndustrialCraft2.ID,
                IronChests.ID,
                Thaumcraft.ID);
    }

    @Override
    public void loadRecipes() {
        final ItemStack CraftingUnit = getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1);
        final ItemStack CoCraftingUnit4x = getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1, 2);
        final ItemStack CoCraftingUnit16x = getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1, 3);
        final ItemStack SuperSpeedCard = getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 56);
        final ItemStack GLASS_PANE = getModItem(TinkerConstruct.ID, "GlassPane", 1);
        final ItemStack CERTUS_PLATE = GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1L);
        final ItemStack AE2_ADVANCED_HOUSING = getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 61);
        final ItemStack AE2_HOUSING = getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 39);
        final ItemStack AE2_BLOCK_CONTAINER = getModItem(
                AppliedEnergistics2.ID,
                "item.ItemExtremeStorageCell.Container",
                1,
                0);
        final ItemStack AE2_ME_CHEST = getModItem(AppliedEnergistics2.ID, "tile.BlockChest", 1, 0);
        final ItemStack AE2_ME_Glass_Cable = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16);
        final ItemStack AE2_ME_Covered_Cable = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 36);
        final ItemStack AE2_ME_Dense_Covered_Cable = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 536);
        final ItemStack AE2_ME_Backbone_Covered_Cable = getModItem(
                AppliedEnergistics2.ID,
                "item.ItemMultiPart",
                1,
                556);
        ItemStack[] FluixCoveredCableColor = new ItemStack[16];
        ItemStack[] FluixDenseCoveredCableColor = new ItemStack[16];
        ItemStack[] FluixBackboneCoveredCableColor = new ItemStack[16];
        ItemStack[] FluixSmartCableColor = new ItemStack[16];
        ItemStack[] FluixDenseSmartCableColor = new ItemStack[16];
        ItemStack[] FluixBackboneSmartCableColor = new ItemStack[16];
        addShapelessRecipe(
                SuperSpeedCard,
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 28),
                CustomItemList.MysteriousCrystal);

        // Quad Core
        GT_Values.RA.stdBuilder()
                .itemInputs(CraftingUnit, GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Elite), 2))
                .itemOutputs(CoCraftingUnit4x).noFluidInputs().noFluidOutputs().duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        // 16 core
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CraftingUnit,
                        GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Superconductor), 2))
                .itemOutputs(CoCraftingUnit16x).noFluidInputs().noFluidOutputs().duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LuV).addTo(sAssemblerRecipes);

        // Advanced Storage Housing
        GT_ModHandler.removeRecipeByOutput(AE2_ADVANCED_HOUSING);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GLASS_PANE,
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 3L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2L),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(AE2_ADVANCED_HOUSING).noFluidInputs().noFluidOutputs().duration(5 * SECONDS).eut(16)
                .addTo(sAssemblerRecipes);
        GT_ModHandler.addCraftingRecipe(
                AE2_ADVANCED_HOUSING,
                new Object[] { "hPS", "CGC", "SCd", 'P', CERTUS_PLATE, 'S',
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 1L), 'C',
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1L), 'G', GLASS_PANE });
        GT_ModHandler.addCraftingRecipe(
                AE2_ADVANCED_HOUSING,
                new Object[] { "dPS", "CGC", "SCh", 'P', CERTUS_PLATE, 'S',
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 1L), 'C',
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1L), 'G', GLASS_PANE });

        // Advanced Storage Cells
        final ItemStack[] components = new ItemStack[] {
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 57), // 256k
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 58), // 1024k
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 59), // 4096k
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 60), // 16384k
        };
        final ItemStack[] cells = new ItemStack[] {
                getModItem(AppliedEnergistics2.ID, "item.ItemAdvancedStorageCell.256k", 1), // 256k
                getModItem(AppliedEnergistics2.ID, "item.ItemAdvancedStorageCell.1024k", 1), // 1024k
                getModItem(AppliedEnergistics2.ID, "item.ItemAdvancedStorageCell.4096k", 1), // 4096k
                getModItem(AppliedEnergistics2.ID, "item.ItemAdvancedStorageCell.16384k", 1), // 16384k
        };
        for (int i = 0; i < components.length; i++) {
            GT_ModHandler.removeRecipeByOutput(cells[i]);
            GT_ModHandler.removeRecipeByOutput(components[i]);
        }
        for (int i = 0; i < components.length; i++) {
            GT_ModHandler.addCraftingRecipe(
                    cells[i],
                    new Object[] { "hPS", "CGC", "SCd", 'P', CERTUS_PLATE, 'S',
                            GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 1L), 'C',
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1L), 'G', components[i] });
            GT_ModHandler.addCraftingRecipe(
                    cells[i],
                    new Object[] { "dPS", "CGC", "SCh", 'P', CERTUS_PLATE, 'S',
                            GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 1L), 'C',
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1L), 'G', components[i] });
            addShapelessRecipe(cells[i], AE2_ADVANCED_HOUSING, components[i]);
        }
        GT_ModHandler.addCraftingRecipe(
                components[0],
                new Object[] { "CPC", "PXP", "CPC", 'C', "circuitData", 'P',
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 38), // 64k
                        'X', CustomItemList.EngineeringProcessorItemEmeraldCore.get(1) });
        GT_ModHandler.addCraftingRecipe(
                components[1],
                new Object[] { "CPC", "PXP", "CPC", 'C', "circuitElite", 'P', components[0], 'X',
                        CustomItemList.EngineeringProcessorItemEmeraldCore.get(1) });
        GT_ModHandler.addCraftingRecipe(
                components[2],
                new Object[] { "CPC", "PXP", "CPC", 'C', "circuitMaster", 'P', components[1], 'X',
                        CustomItemList.EngineeringProcessorItemAdvEmeraldCore.get(1) });
        GT_ModHandler.addCraftingRecipe(
                components[3],
                new Object[] { "CPC", "PXP", "CPC", 'C', "circuitSuperconductor", 'P', components[2], 'X',
                        CustomItemList.EngineeringProcessorItemAdvEmeraldCore.get(1) });
        FluidStack[] solders = new FluidStack[] { Materials.Lead.getMolten(288), Materials.Tin.getMolten(144),
                Materials.SolderingAlloy.getMolten(72), };
        for (FluidStack solder : solders) {
            // 256k
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 16),
                            CustomItemList.EngineeringProcessorItemEmeraldCore.get(1),
                            ItemList.Circuit_Board_Fiberglass_Advanced.get(1),
                            GT_Utility.getIntegratedCircuit(1))
                    .itemOutputs(components[0]).fluidInputs(solder).noFluidOutputs().duration(10 * SECONDS)
                    .eut(TierEU.RECIPE_EV).requiresCleanRoom().addTo(sCircuitAssemblerRecipes);
            // 1024k
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 16),
                            CustomItemList.EngineeringProcessorItemEmeraldCore.get(1),
                            ItemList.Circuit_Board_Multifiberglass_Elite.get(1),
                            GT_Utility.getIntegratedCircuit(1))
                    .itemOutputs(components[1]).fluidInputs(solder).noFluidOutputs().duration(10 * SECONDS)
                    .eut(TierEU.RECIPE_IV).requiresCleanRoom().addTo(sCircuitAssemblerRecipes);
            // 4096k
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 16),
                            CustomItemList.EngineeringProcessorItemAdvEmeraldCore.get(1),
                            ItemList.Circuit_Board_Wetware_Extreme.get(1),
                            GT_Utility.getIntegratedCircuit(1))
                    .itemOutputs(components[2]).fluidInputs(solder).noFluidOutputs().duration(10 * SECONDS)
                    .eut(TierEU.RECIPE_LuV).requiresCleanRoom().addTo(sCircuitAssemblerRecipes);
            // 16384k
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 4),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 16),
                            CustomItemList.EngineeringProcessorItemAdvEmeraldCore.get(1),
                            ItemList.Circuit_Board_Bio_Ultra.get(1),
                            GT_Utility.getIntegratedCircuit(1))
                    .itemOutputs(components[3]).fluidInputs(solder).noFluidOutputs().duration(10 * SECONDS)
                    .eut(TierEU.RECIPE_UV).requiresCleanRoom().addTo(sCircuitAssemblerRecipes);
        }

        // Advanced Crafting Storage
        ItemStack[] storage = new ItemStack[] {
                getModItem(AppliedEnergistics2.ID, "tile.BlockAdvancedCraftingStorage", 1, 0), // 256k
                getModItem(AppliedEnergistics2.ID, "tile.BlockAdvancedCraftingStorage", 1, 1), // 1024k
                getModItem(AppliedEnergistics2.ID, "tile.BlockAdvancedCraftingStorage", 1, 2), // 4096k
                getModItem(AppliedEnergistics2.ID, "tile.BlockAdvancedCraftingStorage", 1, 3), // 16384k
        };
        for (int i = 0; i < storage.length; i++) {
            GT_ModHandler.removeRecipeByOutput(storage[i]);
            GT_Values.RA.stdBuilder()
                    .itemInputs(components[i], getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1))
                    .itemOutputs(storage[i]).noFluidInputs().noFluidOutputs().duration(20 * SECONDS)
                    .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        }

        // ME Block Container
        GT_ModHandler.removeRecipeByOutput(AE2_BLOCK_CONTAINER);
        GT_ModHandler.addCraftingRecipe(
                AE2_BLOCK_CONTAINER,
                new Object[] { " K ", "SMS", "dHw", 'K',
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35), 'S',
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Titanium, 1), 'M', AE2_ME_CHEST, 'H',
                        AE2_HOUSING });
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Titanium, 2),
                        AE2_ME_CHEST,
                        AE2_HOUSING,
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(AE2_BLOCK_CONTAINER).noFluidInputs().noFluidOutputs().duration(2 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // --- Fluix Covered Cable
        for (int i = 0; i < 16; i++) {
            FluixCoveredCableColor[i] = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 20 + i);

            addShapelessRecipe(
                    getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1L, 36),
                    FluixCoveredCableColor[i]);
        }
        GT_Values.RA.stdBuilder().itemInputs(AE2_ME_Glass_Cable, GT_Utility.getIntegratedCircuit(24))
                .itemOutputs(AE2_ME_Covered_Cable).fluidInputs(Materials.Rubber.getMolten(144L)).noFluidOutputs()
                .duration(7 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder().itemInputs(AE2_ME_Glass_Cable, GT_Utility.getIntegratedCircuit(24))
                .itemOutputs(AE2_ME_Covered_Cable).fluidInputs(Materials.StyreneButadieneRubber.getMolten(108L))
                .noFluidOutputs().duration(7 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder().itemInputs(AE2_ME_Glass_Cable, GT_Utility.getIntegratedCircuit(24))
                .itemOutputs(AE2_ME_Covered_Cable).fluidInputs(Materials.Silicone.getMolten(72L)).noFluidOutputs()
                .duration(7 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        AE2_ME_Glass_Cable,
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.PolyvinylChloride, 1))
                .itemOutputs(AE2_ME_Covered_Cable).fluidInputs(Materials.StyreneButadieneRubber.getMolten(36L))
                .noFluidOutputs().duration(7 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        AE2_ME_Glass_Cable,
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.PolyvinylChloride, 1))
                .itemOutputs(AE2_ME_Covered_Cable).fluidInputs(Materials.Silicone.getMolten(36L)).noFluidOutputs()
                .duration(7 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        AE2_ME_Glass_Cable,
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Polydimethylsiloxane, 1))
                .itemOutputs(AE2_ME_Covered_Cable).fluidInputs(Materials.StyreneButadieneRubber.getMolten(36L))
                .noFluidOutputs().duration(7 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        AE2_ME_Glass_Cable,
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Polydimethylsiloxane, 1))
                .itemOutputs(AE2_ME_Covered_Cable).fluidInputs(Materials.Silicone.getMolten(36L)).noFluidOutputs()
                .duration(7 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 16),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.PolyvinylChloride, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 36))
                .fluidInputs(Materials.StyreneButadieneRubber.getMolten(144L)).noFluidOutputs().duration(25 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 16),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.PolyvinylChloride, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 36))
                .fluidInputs(Materials.Silicone.getMolten(144L)).noFluidOutputs().duration(25 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 16),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Polydimethylsiloxane, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 36))
                .fluidInputs(Materials.StyreneButadieneRubber.getMolten(144L)).noFluidOutputs().duration(25 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 16),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Polydimethylsiloxane, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 36))
                .fluidInputs(Materials.Silicone.getMolten(144L)).noFluidOutputs().duration(25 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        // --- Fluix Dense Covered Cable
        for (int i = 0; i < 16; i++) {
            FluixDenseCoveredCableColor[i] = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 520 + i);

            addShapelessRecipe(
                    getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1L, 536),
                    FluixDenseCoveredCableColor[i]);
        }

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 36),
                        GT_Utility.getIntegratedCircuit(24))
                .itemOutputs(AE2_ME_Dense_Covered_Cable).fluidInputs(Materials.StyreneButadieneRubber.getMolten(216L))
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 36),
                        GT_Utility.getIntegratedCircuit(24))
                .itemOutputs(AE2_ME_Dense_Covered_Cable).fluidInputs(Materials.Silicone.getMolten(144L))
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 36),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.PolyvinylChloride, 1))
                .itemOutputs(AE2_ME_Dense_Covered_Cable).fluidInputs(Materials.StyreneButadieneRubber.getMolten(72L))
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 36),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.PolyvinylChloride, 1))
                .itemOutputs(AE2_ME_Dense_Covered_Cable).fluidInputs(Materials.Silicone.getMolten(72L)).noFluidOutputs()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 36),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Polydimethylsiloxane, 1))
                .itemOutputs(AE2_ME_Dense_Covered_Cable).fluidInputs(Materials.StyreneButadieneRubber.getMolten(72L))
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 36),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Polydimethylsiloxane, 1))
                .itemOutputs(AE2_ME_Dense_Covered_Cable).fluidInputs(Materials.Silicone.getMolten(72L)).noFluidOutputs()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 16, 36),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.PolyvinylChloride, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 536))
                .fluidInputs(Materials.StyreneButadieneRubber.getMolten(288L)).noFluidOutputs().duration(35 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 16, 36),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.PolyvinylChloride, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 536))
                .fluidInputs(Materials.Silicone.getMolten(288L)).noFluidOutputs().duration(35 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 16, 36),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Polydimethylsiloxane, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 536))
                .fluidInputs(Materials.StyreneButadieneRubber.getMolten(288L)).noFluidOutputs().duration(35 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 16, 36),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Polydimethylsiloxane, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 536))
                .fluidInputs(Materials.Silicone.getMolten(288L)).noFluidOutputs().duration(35 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        // --- Fluix Backbone Covered Cable
        for (int i = 0; i < 16; i++) {
            FluixBackboneCoveredCableColor[i] = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 540 + i);

            addShapelessRecipe(
                    getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1L, 556),
                    FluixBackboneCoveredCableColor[i]);
        }

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 536),
                        GT_Utility.getIntegratedCircuit(24))
                .itemOutputs(AE2_ME_Backbone_Covered_Cable)
                .fluidInputs(Materials.StyreneButadieneRubber.getMolten(432L)).noFluidOutputs()
                .duration(12 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 536),
                        GT_Utility.getIntegratedCircuit(24))
                .itemOutputs(AE2_ME_Backbone_Covered_Cable).fluidInputs(Materials.Silicone.getMolten(288L))
                .noFluidOutputs().duration(12 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 536),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.PolyvinylChloride, 1))
                .itemOutputs(AE2_ME_Backbone_Covered_Cable)
                .fluidInputs(Materials.StyreneButadieneRubber.getMolten(144L)).noFluidOutputs()
                .duration(12 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 536),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.PolyvinylChloride, 1))
                .itemOutputs(AE2_ME_Backbone_Covered_Cable).fluidInputs(Materials.Silicone.getMolten(144L))
                .noFluidOutputs().duration(12 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 536),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Polydimethylsiloxane, 1))
                .itemOutputs(AE2_ME_Backbone_Covered_Cable)
                .fluidInputs(Materials.StyreneButadieneRubber.getMolten(144L)).noFluidOutputs()
                .duration(12 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 536),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Polydimethylsiloxane, 1))
                .itemOutputs(AE2_ME_Backbone_Covered_Cable).fluidInputs(Materials.Silicone.getMolten(144L))
                .noFluidOutputs().duration(12 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 16, 536),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.PolyvinylChloride, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 556))
                .fluidInputs(Materials.StyreneButadieneRubber.getMolten(576L)).noFluidOutputs().duration(45 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 16, 536),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.PolyvinylChloride, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 556))
                .fluidInputs(Materials.Silicone.getMolten(576L)).noFluidOutputs().duration(45 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 16, 536),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Polydimethylsiloxane, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 556))
                .fluidInputs(Materials.StyreneButadieneRubber.getMolten(576L)).noFluidOutputs().duration(45 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 16, 536),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Polydimethylsiloxane, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 556))
                .fluidInputs(Materials.Silicone.getMolten(576L)).noFluidOutputs().duration(45 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        // ME Smart Cable Fluix
        for (int i = 0; i < 16; i++) {
            FluixSmartCableColor[i] = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 40 + i);

            addShapelessRecipe(
                    getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1L, 56),
                    FluixSmartCableColor[i]);
        }
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 16),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 56))
                .fluidInputs(Materials.ConductiveIron.getMolten(144L)).noFluidOutputs().duration(5 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 36),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 56))
                .fluidInputs(Materials.ConductiveIron.getMolten(144L)).noFluidOutputs().duration(5 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        // --- ME Smart Dense Cable Fluix
        for (int i = 0; i < 16; i++) {
            FluixDenseSmartCableColor[i] = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 60 + i);
            addShapelessRecipe(
                    getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1L, 76),
                    FluixDenseSmartCableColor[i]);
        }
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 16, 56),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 76))
                .fluidInputs(Materials.EnergeticAlloy.getMolten(144L)).noFluidOutputs()
                .duration(7 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 536),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 76))
                .fluidInputs(Materials.EnergeticAlloy.getMolten(144L)).noFluidOutputs()
                .duration(7 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        // --- ME Smart Backbone Cable Fluix
        for (int i = 0; i < 16; i++) {
            FluixBackboneSmartCableColor[i] = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 560 + i);
            addShapelessRecipe(
                    getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1L, 576),
                    FluixBackboneSmartCableColor[i]);
        }
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 16, 76),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 576))
                .fluidInputs(Materials.VibrantAlloy.getMolten(144L)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 556),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 576))
                .fluidInputs(Materials.VibrantAlloy.getMolten(144L)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        // ME Quantum Storage
        GT_ModHandler
                .removeRecipeByOutput(getModItem(AppliedEnergistics2.ID, "item.ItemExtremeStorageCell.Quantum", 1));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemExtremeStorageCell.Quantum", 1),
                "---------",
                "----a----",
                "---bdb---",
                "--bcdcb--",
                "-addedda-",
                "--bcdcb--",
                "---bdb---",
                "----a----",
                "---------",
                'a',
                "blockCosmicNeutronium",
                'b',
                "plateDenseNeutronium",
                'c',
                "circuitInfinite",
                'd',
                components[3],
                'e',
                AE2_ADVANCED_HOUSING);

        // ME Digital Singularity
        GT_ModHandler
                .removeRecipeByOutput(getModItem(AppliedEnergistics2.ID, "item.ItemExtremeStorageCell.Singularity", 1));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemExtremeStorageCell.Singularity", 1),
                "----a----",
                "---aba---",
                "--ecdce--",
                "-acdddca-",
                "abddfddba",
                "-acdddca-",
                "--ecdce--",
                "---aba---",
                "----a----",
                'a',
                "blockCosmicNeutronium",
                'b',
                getModItem(Avaritia.ID, "Resource", 1, 5),
                'c',
                getModItem(GregTech.ID, "gt.blockmachines", 1, 129),
                'd',
                components[3],
                'e',
                "blockInfinity",
                'f',
                getModItem(EternalSingularity.ID, "eternal_singularity", 1));

        // ME Void Storage
        GT_ModHandler.removeRecipeByOutput(getModItem(AppliedEnergistics2.ID, "item.ItemVoidStorageCell", 1));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemVoidStorageCell", 1),
                "craftingToolHardHammer",
                "plateCertusQuartz",
                "screwCertusQuartz",
                "plateTungsten",
                "gemEnderEye",
                "plateTungsten",
                "screwCertusQuartz",
                "plateTungsten",
                "craftingToolScrewdriver");

        AvaritiaHelper.removeExtremeCraftingRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockCreativeEnergyCell", 1, 0, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockCreativeEnergyCell", 1, 0, missing),
                "aaaaaaaaa",
                "abbbcbbba",
                "abbbcbbba",
                "abbbcbbba",
                "acccdccca",
                "abbbcbbba",
                "abbbcbbba",
                "abbbcbbba",
                "aaaaaaaaa",
                'a',
                "ingotInfinity",
                'b',
                getModItem(AppliedEnergistics2.ID, "tile.BlockDenseEnergyCell", 1, 0, missing),
                'c',
                getModItem(NewHorizonsCoreMod.ID, "item.EngineeringProcessorItemAdvEmeraldCore", 1, 0, missing),
                'd',
                "blockCosmicNeutronium");

        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockController", 1, 0, missing),
                "plateTitanium",
                "circuitAdvanced",
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockFluix", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                "plateTitanium",
                "circuitAdvanced",
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1, 0, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 8, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "circuitAdvanced",
                getModItem(AppliedEnergistics2.ID, "tile.BlockFluix", 1, 0, missing),
                "circuitAdvanced",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "cableGt08Aluminium",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockDenseEnergyCell", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1, 0, missing),
                "circuitMaster",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                "circuitMaster",
                getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1, 0, missing),
                getModItem(GregTech.ID, "gt.blockmachines", 1, 194, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1, 0, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockChest", 1, 0, missing),
                "plateStainlessSteel",
                "circuitGood",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                getModItem(IronChests.ID, "BlockIronChest", 1, 4, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                "plateStainlessSteel",
                "circuitGood",
                "plateStainlessSteel");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockDrive", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockChest", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                "plateTitanium",
                "circuitAdvanced",
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGrowthAccelerator", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGlass", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                getModItem(GregTech.ID, "gt.blockmachines", 1, 574, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                "plateTitanium",
                "circuitBasic",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                "circuitBasic",
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23, missing),
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockMolecularAssembler", 1, 0, missing),
                "plateTitanium",
                "blockGlass",
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 44, missing),
                getModItem(GregTech.ID, "gt.blockmachines", 1, 214, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43, missing),
                "plateTitanium",
                "blockGlass",
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockCharger", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                "plateTitanium",
                "cableGt01Copper",
                getModItem(GregTech.ID, "gt.blockmachines", 1, 374, missing),
                "cableGt01Copper",
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuantumRing", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuantumLinkChamber", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 9, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 9, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGlass", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 9, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 9, missing),
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockSpatialPylon", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 8, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 8, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockSpatialIOPort", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "tile.BlockSpatialPylon", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockIOPort", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockInterface", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 44, missing),
                getModItem(GregTech.ID, "gt.blockcasings", 1, 4, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                "plateTitanium");
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockInterface", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 440, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockCellWorkbench", 1, 0, missing),
                "craftingToolScrewdriver",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32740, missing),
                "craftingToolWrench",
                "screwTitanium",
                getModItem(Minecraft.ID, "crafting_table", 1, 0, missing),
                "screwTitanium",
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23, missing),
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockIOPort", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockDrive", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockDrive", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockCondenser", 1, 0, missing),
                "plateTitanium",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32643, missing),
                "plateTitanium",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32643, missing),
                getModItem(GregTech.ID, "gt.blockmachines", 1, 14, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32643, missing),
                "plateTitanium",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32643, missing),
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyAcceptor", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                "plateGlowstone",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockVibrationChamber", 1, 0, missing),
                "plateTitanium",
                "screwTitanium",
                "plateTitanium",
                "craftingToolScrewdriver",
                getModItem(GregTech.ID, "gt.blockmachines", 1, 264, missing),
                "craftingToolWrench",
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyAcceptor", 1, 0, missing),
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockSecurity", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "tile.BlockChest", 1, 0, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 37, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.SkyStoneStairBlock", 4, 0, missing),
                null,
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 0, missing),
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 0, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.SkyStoneBlockStairBlock", 4, 0, missing),
                null,
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 1, missing),
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 1, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 1, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 1, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 1, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 1, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.SkyStoneBrickStairBlock", 4, 0, missing),
                null,
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 2, missing),
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 2, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 2, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 2, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 2, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 2, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.SkyStoneSmallBrickStairBlock", 4, 0, missing),
                null,
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 3, missing),
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 3, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 3, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 3, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 3, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1, 3, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.FluixStairBlock", 4, 0, missing),
                null,
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockFluix", 1, 0, missing),
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockFluix", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockFluix", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockFluix", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockFluix", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockFluix", 1, 0, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.QuartzStairBlock", 4, 0, missing),
                null,
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartz", 1, 0, missing),
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartz", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartz", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartz", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartz", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartz", 1, 0, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.ChiseledQuartzStairBlock", 4, 0, missing),
                null,
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzChiseled", 1, 0, missing),
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzChiseled", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzChiseled", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzChiseled", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzChiseled", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzChiseled", 1, 0, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.QuartzPillarStairBlock", 4, 0, missing),
                null,
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzPillar", 1, 0, missing),
                null,
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzPillar", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzPillar", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzPillar", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzPillar", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzPillar", 1, 0, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 2, 16, missing),
                "stickCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 8, missing),
                "stickCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 140, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 140, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 140, missing),
                "stickCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 8, missing),
                "stickCertusQuartz");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 2, 44, missing),
                "stickNetherQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                "stickNetherQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 12, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                "stickNetherQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                "stickNetherQuartz");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 2, 43, missing),
                "stickCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                "stickCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 12, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                "stickCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                "stickCertusQuartz");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 9, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 12, missing),
                "plateEnderEye",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 12, missing),
                "plateEnderEye",
                "gemEnderPearl",
                "plateEnderEye",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 12, missing),
                "plateEnderEye",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 12, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 9, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                "plateEnderEye",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                "plateEnderEye",
                "gemEnderPearl",
                "plateEnderEye",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                "plateEnderEye",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 41, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 140, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 9, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 140, missing),
                null,
                "stickEnderEye",
                null,
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "circuitAdvanced",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyCompass", 1, 0, missing),
                "craftingToolScrewdriver",
                getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                "screwCertusQuartz",
                "plateSteel",
                "stickSteelMagnetic",
                "plateSteel",
                "screwCertusQuartz",
                "plateSteel",
                "craftingToolHardHammer");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockSkyCompass", 1, 0, missing),
                "craftingToolHardHammer",
                getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                "screwCertusQuartz",
                "plateSteel",
                "stickSteelMagnetic",
                "plateSteel",
                "screwCertusQuartz",
                "plateSteel",
                "craftingToolScrewdriver");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 180, missing),
                "craftingToolScrewdriver",
                "plateTitanium",
                "craftingToolHardHammer",
                "screwCertusQuartz",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32740, missing),
                "screwCertusQuartz",
                "plateGlowstone",
                "plateRedAlloy",
                "plateGlowstone");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 380, missing),
                "stickNetherQuartz",
                "screwQuartzite",
                "stickNetherQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 180, missing),
                "circuitGood",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "stickNetherQuartz",
                "craftingToolScrewdriver",
                "stickNetherQuartz");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 380, missing),
                "stickNetherQuartz",
                "craftingToolScrewdriver",
                "stickNetherQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 180, missing),
                "circuitGood",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "stickNetherQuartz",
                "screwQuartzite",
                "stickNetherQuartz");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 360, missing),
                "craftingToolScrewdriver",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 380, missing),
                "craftingToolSoftHammer",
                "screwCertusQuartz",
                getModItem(TinkerConstruct.ID, "CraftingSlab", 1, 0, missing),
                "screwCertusQuartz",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 500, missing),
                "craftingToolScrewdriver",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 360, missing),
                "craftingToolSoftHammer",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32635, missing),
                getModItem(NewHorizonsCoreMod.ID, "item.EngineeringProcessorItemAdvEmeraldCore", 1, 0, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32635, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32606, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32655, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32606, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 480, missing),
                "craftingToolScrewdriver",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 380, missing),
                "craftingToolSoftHammer",
                "screwCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 440, missing),
                "screwCertusQuartz",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 420, missing),
                "craftingToolScrewdriver",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 400, missing),
                "craftingToolHardHammer",
                "screwCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43, missing),
                "screwCertusQuartz",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 44, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 340, missing),
                "craftingToolScrewdriver",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 380, missing),
                "craftingToolSoftHammer",
                "screwCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 52, missing),
                "screwCertusQuartz",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ToolWirelessTerminal", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 41, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 380, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 41, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockDenseEnergyCell", 1, 0, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 28, missing),
                "platePlatinum",
                "plateTitanium",
                null,
                "plateRedAlloy",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23, missing),
                "plateTitanium",
                "platePlatinum",
                "plateTitanium",
                null);
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 25, missing),
                "plateGold",
                "plateAluminium",
                null,
                "plateRedAlloy",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23, missing),
                "plateAluminium",
                "plateGold",
                "plateAluminium",
                null);
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 52, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGlass", 1, 0, missing),
                "plateGlowstone",
                getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGlass", 1, 0, missing),
                "plateGlowstone",
                "itemCertusQuartz",
                "plateGlowstone",
                "plateAluminium",
                "plateAluminium",
                "plateAluminium");
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 27, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 25, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 1, missing));
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 53, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 25, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35, missing),
                "craftingWorkBench");
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 26, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 25, missing),
                "craftingRedstoneTorch",
                "craftingRedstoneTorch",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23, missing));
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 29, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 28, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23, missing));
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 31, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 28, missing),
                getModItem(IndustrialCraft2.ID, "upgradeModule", 1, 5, missing),
                getModItem(IndustrialCraft2.ID, "upgradeModule", 1, 5, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23, missing));
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 30, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 28, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing));
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 55, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 28, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                getModItem(GregTech.ID, "gt.blockmachines", 1, 9255, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 42, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 8, missing),
                "gemCertusQuartz",
                "plateEnderPearl",
                "plateTitanium",
                "plateAluminium",
                "plateTitanium",
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 300, missing),
                "craftingToolScrewdriver",
                getModItem(AppliedEnergistics2.ID, "item.ToolCertusQuartzPickaxe", 1, 0, missing),
                "craftingToolHardHammer",
                "screwCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 44, missing),
                "screwCertusQuartz",
                "gemFluix",
                "gemFluix",
                "gemFluix");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 120, missing),
                "boltIron",
                null,
                null,
                "boltIron");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 120, missing),
                "boltCopper",
                null,
                null,
                "boltCopper");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 120, missing),
                "boltBronze",
                null,
                null,
                "boltBronze");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 120, missing),
                "boltTin",
                null,
                null,
                "boltTin");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 2, 120, missing),
                "boltSteel",
                null,
                null,
                "boltSteel");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 2, 120, missing),
                "boltAluminium",
                null,
                null,
                "boltAluminium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 120, missing),
                "boltLead",
                null,
                null,
                "boltLead");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 120, missing),
                "boltNickel",
                null,
                null,
                "boltNickel");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 120, missing),
                "boltSilver",
                null,
                null,
                "boltSilver");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 120, missing),
                "boltBrass",
                null,
                null,
                "boltBrass");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 120, missing),
                "boltInvar",
                null,
                null,
                "boltInvar");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 3, 120, missing),
                "boltStainlessSteel",
                null,
                null,
                "boltStainlessSteel");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 4, 120, missing),
                "boltTitanium",
                null,
                null,
                "boltTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 260, missing),
                "craftingToolScrewdriver",
                "plateTitanium",
                "craftingToolHardHammer",
                "screwCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43, missing),
                "screwCertusQuartz",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32640, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 320, missing),
                "craftingToolScrewdriver",
                "blockHopper",
                "craftingToolHardHammer",
                "screwCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43, missing),
                "screwCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 240, missing),
                "craftingToolScrewdriver",
                "plateTitanium",
                "craftingToolHardHammer",
                "screwCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 44, missing),
                "screwCertusQuartz",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32640, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 460, missing),
                "craftingToolScrewdriver",
                "plateTitanium",
                "craftingToolHardHammer",
                "screwCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                "screwCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ToolCertusQuartzWrench", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 1, missing),
                "craftingToolWrench",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 1, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 1, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 1, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 1, missing),
                null,
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 1, missing),
                null);
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ToolNetherQuartzWrench", 1, 0, missing),
                "gemQuartz",
                "craftingToolWrench",
                "gemQuartz",
                "gemQuartz",
                "gemQuartz",
                "gemQuartz",
                null,
                "gemQuartz",
                null);
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ToolMassCannon", 1, 0, missing),
                "plateTitanium",
                "plateTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 38, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockDenseEnergyCell", 1, 0, missing),
                null,
                "plateTitanium",
                null,
                null);
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ToolMemoryCard", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23, missing),
                "circuitData",
                "plateTitanium",
                "plateGold",
                "plateRedAlloy",
                "plateGold",
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ToolChargedStaff", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 1, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 1, missing),
                null,
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 1, missing),
                getModItem(Thaumcraft.ID, "FocusShock", 1, 0, missing),
                null,
                null,
                null,
                "stickThaumium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ToolEntropyManipulator", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                null,
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1, 0, missing),
                null,
                null,
                null,
                "stickThaumium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ToolColorApplicator", 1, 0, missing),
                "wireGt01Aluminium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43, missing),
                "wireGt01Aluminium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 36, missing),
                getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 36, missing),
                null,
                "stickSteel",
                null);
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ToolBiometricCard", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32740, missing),
                "plateTitanium",
                "plateGold",
                "plateRedAlloy",
                "plateGold",
                null,
                null,
                null);
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockWireless", 1, 0, missing),
                null,
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 41, missing),
                null,
                "screwTitanium",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23, missing),
                "screwTitanium",
                "craftingToolScrewdriver",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                "craftingToolWrench");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 400, missing),
                "craftingToolScrewdriver",
                "itemIlluminatedPanel",
                "craftingToolHardHammer",
                "screwCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 280, missing),
                "screwCertusQuartz",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 220, missing),
                "craftingToolScrewdriver",
                "chestWood",
                "craftingToolHardHammer",
                "screwCertusQuartz",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 440, missing),
                "screwCertusQuartz",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 32640, missing),
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17522, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.1k", 1, 0, missing),
                "craftingToolHardHammer",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolScrewdriver");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.1k", 1, 0, missing),
                "craftingToolScrewdriver",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolHardHammer");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.4k", 1, 0, missing),
                "craftingToolHardHammer",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 36, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolScrewdriver");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.4k", 1, 0, missing),
                "craftingToolScrewdriver",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 36, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolHardHammer");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.16k", 1, 0, missing),
                "craftingToolHardHammer",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 37, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolScrewdriver");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.16k", 1, 0, missing),
                "craftingToolScrewdriver",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 37, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolHardHammer");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.64k", 1, 0, missing),
                "craftingToolHardHammer",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 38, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolScrewdriver");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.64k", 1, 0, missing),
                "craftingToolScrewdriver",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 38, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolHardHammer");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemViewCell", 1, 0, missing),
                "craftingToolScrewdriver",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                "gemCertusQuartz",
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolHardHammer");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemViewCell", 1, 0, missing),
                "craftingToolHardHammer",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                "gemCertusQuartz",
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolScrewdriver");
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemViewCell", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 39, missing),
                "gemCertusQuartz");
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.1k", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 39, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35, missing));
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.4k", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 39, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 36, missing));
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.16k", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 39, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 37, missing));
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.64k", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 39, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 38, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemSpatialStorageCell.2Cubed", 1, 0, missing),
                "craftingToolHardHammer",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 32, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolScrewdriver");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemSpatialStorageCell.2Cubed", 1, 0, missing),
                "craftingToolScrewdriver",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 32, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolHardHammer");
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemSpatialStorageCell.2Cubed", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 39, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 32, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemSpatialStorageCell.16Cubed", 1, 0, missing),
                "craftingToolHardHammer",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 33, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolScrewdriver");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemSpatialStorageCell.16Cubed", 1, 0, missing),
                "craftingToolScrewdriver",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 33, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolHardHammer");
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemSpatialStorageCell.16Cubed", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 39, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 33, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemSpatialStorageCell.128Cubed", 1, 0, missing),
                "craftingToolHardHammer",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 34, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolScrewdriver");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemSpatialStorageCell.128Cubed", 1, 0, missing),
                "craftingToolScrewdriver",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 34, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolHardHammer");
        addShapelessRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemSpatialStorageCell.128Cubed", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 39, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 34, missing));
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 39, missing),
                "craftingToolHardHammer",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolScrewdriver");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 39, missing),
                "craftingToolScrewdriver",
                getModItem(GregTech.ID, "gt.metaitem.01", 1, 17516, missing),
                "screwCertusQuartz",
                "plateStainlessSteel",
                getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing),
                "plateStainlessSteel",
                "screwCertusQuartz",
                "plateAluminium",
                "craftingToolHardHammer");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35, missing),
                "circuitPrimitive",
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartzCharged, 1),
                "circuitPrimitive",
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartzCharged, 1),
                getModItem(NewHorizonsCoreMod.ID, "item.LogicProcessorItemGoldCore", 1, 0, missing),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartzCharged, 1),
                "circuitPrimitive",
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartzCharged, 1),
                "circuitPrimitive");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 36, missing),
                "circuitBasic",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35, missing),
                "circuitBasic",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35, missing),
                getModItem(NewHorizonsCoreMod.ID, "item.LogicProcessorItemGoldCore", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35, missing),
                "circuitBasic",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35, missing),
                "circuitBasic");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 37, missing),
                "circuitGood",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 36, missing),
                "circuitGood",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 36, missing),
                getModItem(NewHorizonsCoreMod.ID, "item.EngineeringProcessorItemDiamondCore", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 36, missing),
                "circuitGood",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 36, missing),
                "circuitGood");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 38, missing),
                "circuitAdvanced",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 37, missing),
                "circuitAdvanced",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 37, missing),
                getModItem(NewHorizonsCoreMod.ID, "item.EngineeringProcessorItemDiamondCore", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 37, missing),
                "circuitAdvanced",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 37, missing),
                "circuitAdvanced");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 32, missing),
                "plateGlowstone",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 9, missing),
                "plateGlowstone",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 9, missing),
                getModItem(NewHorizonsCoreMod.ID, "item.EngineeringProcessorSpatialPulsatingCore", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 9, missing),
                "plateGlowstone",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 9, missing),
                "plateGlowstone");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 33, missing),
                "plateEnderPearl",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 32, missing),
                "plateEnderPearl",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 32, missing),
                getModItem(NewHorizonsCoreMod.ID, "item.EngineeringProcessorSpatialPulsatingCore", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 32, missing),
                "plateEnderPearl",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 32, missing),
                "plateEnderPearl");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 34, missing),
                "plateEnderEye",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 33, missing),
                "plateEnderEye",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 33, missing),
                getModItem(NewHorizonsCoreMod.ID, "item.EngineeringProcessorSpatialPulsatingCore", 1, 0, missing),
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 33, missing),
                "plateEnderEye",
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 33, missing),
                "plateEnderEye");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ToolPortableCell", 1, 0, missing),
                null,
                getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.1k", 1, 0, missing),
                null,
                "screwTitanium",
                getModItem(AppliedEnergistics2.ID, "tile.BlockChest", 1, 0, missing),
                "screwTitanium",
                "craftingToolScrewdriver",
                getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1, 0, missing),
                "craftingToolWrench");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "tile.BlockInscriber", 1, 0, missing),
                "plateTitanium",
                "gemFluix",
                "plateTitanium",
                getModItem(IndustrialCraft2.ID, "itemRecipePart", 1, 0, missing),
                getModItem(GregTech.ID, "gt.blockmachines", 1, 604, missing),
                getModItem(IndustrialCraft2.ID, "itemRecipePart", 1, 0, missing),
                "plateTitanium",
                "gemFluix",
                "plateTitanium");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ToolCertusQuartzCuttingKnife", 1, 0, missing),
                "craftingToolHardHammer",
                "screwTitanium",
                "stickWood",
                "gemCertusQuartz",
                "stickWood",
                "screwTitanium",
                "plateCertusQuartz",
                "gemCertusQuartz",
                "craftingToolFile");
        addShapedRecipe(
                getModItem(AppliedEnergistics2.ID, "item.ToolNetherQuartzCuttingKnife", 1, 0, missing),
                "craftingToolHardHammer",
                "screwTitanium",
                "stickWood",
                "gemNetherQuartz",
                "stickWood",
                "screwTitanium",
                "plateNetherQuartz",
                "gemNetherQuartz",
                "craftingToolFile");

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGlass", 1, 0, missing),
                        getModItem(Minecraft.ID, "glowstone_dust", 8, 0, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzLamp", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(ExtraUtilities.ID, "decorativeBlock2", 1, 7, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 2, 8, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzLamp", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 8, 0, missing),
                        getModItem(Minecraft.ID, "chest", 1, 0, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockSkyChest", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 8, 1, missing),
                        getModItem(Minecraft.ID, "chest", 1, 0, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockSkyChest", 1, 1, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 2, 24, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1, 1, missing))
                .noFluidInputs().noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 35, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingStorage", 1, 0, missing))
                .noFluidInputs().noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 36, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingStorage", 1, 1, missing))
                .noFluidInputs().noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 37, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingStorage", 1, 2, missing))
                .noFluidInputs().noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 38, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingStorage", 1, 3, missing))
                .noFluidInputs().noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 400, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingMonitor", 1, 0, missing))
                .noFluidInputs().noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 1, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzTorch", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(GregTech.ID, "gt.metaitem.01", 1, 8516, missing),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockLightDetector", 1, 0, missing))
                .noFluidInputs().noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 180, missing),
                        getModItem(GregTech.ID, "gt.integrated_circuit", 0, 2, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 200, missing)).noFluidInputs()
                .noFluidOutputs().duration(5 * SECONDS).eut(4).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 180, missing),
                        getModItem(GregTech.ID, "gt.integrated_circuit", 0, 3, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 160, missing)).noFluidInputs()
                .noFluidOutputs().duration(5 * SECONDS).eut(4).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 200, missing),
                        getModItem(GregTech.ID, "gt.integrated_circuit", 0, 1, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 180, missing)).noFluidInputs()
                .noFluidOutputs().duration(5 * SECONDS).eut(4).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 160, missing),
                        getModItem(GregTech.ID, "gt.integrated_circuit", 0, 1, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 180, missing)).noFluidInputs()
                .noFluidOutputs().duration(5 * SECONDS).eut(4).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "redstone_torch", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 280, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 280, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 2, 16, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 80, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 100, missing),
                        getModItem(GregTech.ID, "gt.integrated_circuit", 0, 1, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 80, missing)).noFluidInputs()
                .noFluidOutputs().duration(5 * SECONDS).eut(4).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 80, missing),
                        getModItem(GregTech.ID, "gt.integrated_circuit", 0, 2, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 100, missing)).noFluidInputs()
                .noFluidOutputs().duration(5 * SECONDS).eut(4).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 3, 140, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 2, 8, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 3, 16, missing)).noFluidInputs()
                .noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "chest", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 440, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 2),
                        getModItem(GregTech.ID, "gt.metaitem.01", 1, 32640, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 220, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 44, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 2),
                        getModItem(GregTech.ID, "gt.metaitem.01", 1, 32640, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 240, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 2),
                        getModItem(GregTech.ID, "gt.metaitem.01", 1, 32640, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 260, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // ME Terminal
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.NetherQuartz, 4),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Quartzite, 1),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 180, missing),
                        GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Good), 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 380, missing)).noFluidInputs()
                .noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // ME Crafting Terminal
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 380, missing),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2),
                        getModItem(TinkerConstruct.ID, "CraftingSlab", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 360, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // ME Processing Pattern Terminal
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 360, missing),
                        getModItem(GregTech.ID, "gt.metaitem.01", 2, 32635, missing),
                        getModItem(NewHorizonsCoreMod.ID, "item.EngineeringProcessorItemAdvEmeraldCore", 1, 0, missing),
                        getModItem(GregTech.ID, "gt.metaitem.01", 2, 32606, missing),
                        getModItem(GregTech.ID, "gt.metaitem.01", 1, 32655, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 500, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // ME Interface Terminal
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 380, missing),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 440, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 480, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // ME Conversion Monitor
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 400, missing),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 44, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 420, missing)).noFluidInputs()
                .noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // ME Pattern Terminal
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 380, missing),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 52, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 340, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // ME Formation Plane
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "hopper", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 3, 7, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 320, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // ME Annihilation Plane
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ToolCertusQuartzPickaxe", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 44, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 3, 7, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 300, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // P2P Tunnel - ME
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 3, 7, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 460, missing)).noFluidInputs()
                .noFluidOutputs().duration(18 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // ME Storage Monitor
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 180, missing),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 280, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 3))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 400, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 160, missing),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 280, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 3))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 400, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 200, missing),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 280, missing),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 3))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 400, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // Portable Cell
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "item.ItemBasicStorageCell.1k", 1, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Titanium, 2),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockChest", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1, 0, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ToolPortableCell", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // Quartz Glass
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glass", 4, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 4))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGlass", 4, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).specialValue(1000).noOptimize()
                .addTo(sBlastRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "glass", 4, 0, missing),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 4))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGlass", 4, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(20 * SECONDS).eut(16).noOptimize().addTo(sAlloySmelterRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem(IndustrialCraft2.ID, "blockITNT", 1, 0, missing))
                .itemOutputs(
                        getModItem(AppliedEnergistics2.ID, "tile.BlockTinyTNT", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockTinyTNT", 1, 0, missing))
                .noFluidInputs().noFluidOutputs().duration(30 * SECONDS).eut(5).addTo(sCentrifugeRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 1),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartzCharged, 1))
                .noFluidInputs().noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(UniversalChemical);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartzCharged, 3),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 3, 1, missing))
                .fluidInputs(FluidRegistry.getFluidStack("water", 1000)).noFluidOutputs().duration(45 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(UniversalChemical);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartzCharged, 3),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 3, 1, missing))
                .fluidInputs(FluidRegistry.getFluidStack("ic2distilledwater", 1000)).noFluidOutputs()
                .duration(35 * SECONDS).eut(TierEU.RECIPE_LV).addTo(UniversalChemical);
        GT_Values.RA.stdBuilder().itemInputs(getModItem(AppliedEnergistics2.ID, "tile.BlockFluix", 1, 0, missing))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 4, 8, missing))
                .outputChances(10000).noFluidInputs().noFluidOutputs().duration(15 * SECONDS).eut(2)
                .addTo(sMaceratorRecipes);
        // Quartz Fiber
        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.CertusQuartz, 2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 140, missing)).noFluidInputs()
                .noFluidOutputs().duration(4 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sWiremillRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.CertusQuartzCharged, 1))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 140, missing)).noFluidInputs()
                .noFluidOutputs().duration(4 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sWiremillRecipes);
        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.NetherQuartz, 8))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 140, missing)).noFluidInputs()
                .noFluidOutputs().duration(4 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sWiremillRecipes);
        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Quartzite, 4))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 140, missing)).noFluidInputs()
                .noFluidOutputs().duration(4 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sWiremillRecipes);

    }
}
