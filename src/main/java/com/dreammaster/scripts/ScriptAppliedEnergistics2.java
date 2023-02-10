package com.dreammaster.scripts;

import static gregtech.api.util.GT_ModHandler.getModItem;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.dreammaster.gthandler.CustomItemList;

import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

public class ScriptAppliedEnergistics2 implements IScriptLoader {

    public ScriptAppliedEnergistics2() {}

    @Override
    public void initScriptData() {
        scriptName.setLength(0);
        scriptName.append("AppliedEnergistics2");
        dependencies.clear();
        dependencies
                .addAll(java.util.Arrays.asList("appliedenergistics2", "TConstruct", "Avaritia", "eternalsingularity"));
    }

    @Override
    public void loadRecipes() {
        final String AE2 = "appliedenergistics2";
        final String TiC = "TConstruct";
        final ItemStack CraftingUnit = getModItem(AE2, "tile.BlockCraftingUnit", 1);
        final ItemStack CoCraftingUnit4x = getModItem(AE2, "tile.BlockCraftingUnit", 1, 2);
        final ItemStack CoCraftingUnit16x = getModItem(AE2, "tile.BlockCraftingUnit", 1, 3);
        final ItemStack SuperSpeedCard = getModItem(AE2, "item.ItemMultiMaterial", 1, 56);
        final ItemStack GLASS_PANE = getModItem(TiC, "GlassPane", 1);
        final ItemStack CERTUS_PLATE = GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1L);
        final ItemStack AE2_ADVANCED_HOUSING = getModItem(AE2, "item.ItemMultiMaterial", 1, 61);
        final ItemStack AE2_HOUSING = getModItem(AE2, "item.ItemMultiMaterial", 1, 39);
        final ItemStack AE2_BLOCK_CONTAINER = getModItem(AE2, "item.ItemExtremeStorageCell.Container", 1, 0);
        final ItemStack AE2_ME_CHEST = getModItem(AE2, "tile.BlockChest", 1, 0);

        GT_ModHandler.addShapelessCraftingRecipe(
                SuperSpeedCard,
                new Object[] { getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 28),
                        CustomItemList.MysteriousCrystal });

        GT_Values.RA.addAssemblerRecipe(
                CraftingUnit,
                OrePrefixes.circuit.get(Materials.Elite),
                2,
                GT_Values.NF,
                CoCraftingUnit4x,
                100,
                480);

        GT_Values.RA.addAssemblerRecipe(
                CraftingUnit,
                OrePrefixes.circuit.get(Materials.Superconductor),
                2,
                GT_Values.NF,
                CoCraftingUnit16x,
                100,
                30720);

        // Advanced Storage Housing
        GT_ModHandler.removeRecipeByOutput(AE2_ADVANCED_HOUSING);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GLASS_PANE, CERTUS_PLATE,
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 3L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2L),
                        GT_Utility.getIntegratedCircuit(3) },
                GT_Values.NF,
                AE2_ADVANCED_HOUSING,
                100,
                16);
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
        final ItemStack[] components = new ItemStack[] { getModItem(AE2, "item.ItemMultiMaterial", 1, 57), // 256k
                getModItem(AE2, "item.ItemMultiMaterial", 1, 58), // 1024k
                getModItem(AE2, "item.ItemMultiMaterial", 1, 59), // 4096k
                getModItem(AE2, "item.ItemMultiMaterial", 1, 60), // 16384k
        };
        final ItemStack[] cells = new ItemStack[] { getModItem(AE2, "item.ItemAdvancedStorageCell.256k", 1), // 256k
                getModItem(AE2, "item.ItemAdvancedStorageCell.1024k", 1), // 1024k
                getModItem(AE2, "item.ItemAdvancedStorageCell.4096k", 1), // 4096k
                getModItem(AE2, "item.ItemAdvancedStorageCell.16384k", 1), // 16384k
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
            GT_ModHandler.addShapelessCraftingRecipe(cells[i], new Object[] { AE2_ADVANCED_HOUSING, components[i] });
        }
        GT_ModHandler.addCraftingRecipe(
                components[0],
                new Object[] { "CPC", "PXP", "CPC", 'C', "circuitData", 'P',
                        getModItem(AE2, "item.ItemMultiMaterial", 1, 38), // 64k
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
            GT_Values.RA.addCircuitAssemblerRecipe(
                    new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 16),
                            CustomItemList.EngineeringProcessorItemEmeraldCore.get(1),
                            ItemList.Circuit_Board_Fiberglass_Advanced.get(1), GT_Utility.getIntegratedCircuit(1) },
                    solder,
                    components[0],
                    200,
                    1920,
                    true);
            // 1024k
            GT_Values.RA.addCircuitAssemblerRecipe(
                    new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 16),
                            CustomItemList.EngineeringProcessorItemEmeraldCore.get(1),
                            ItemList.Circuit_Board_Multifiberglass_Elite.get(1), GT_Utility.getIntegratedCircuit(1) },
                    solder,
                    components[1],
                    200,
                    7680,
                    true);
            // 4096k
            GT_Values.RA.addCircuitAssemblerRecipe(
                    new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 16),
                            CustomItemList.EngineeringProcessorItemAdvEmeraldCore.get(1),
                            ItemList.Circuit_Board_Wetware_Extreme.get(1), GT_Utility.getIntegratedCircuit(1) },
                    solder,
                    components[2],
                    200,
                    30720,
                    true);
            // 16384k
            GT_Values.RA.addCircuitAssemblerRecipe(
                    new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Superconductor, 4),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 16),
                            CustomItemList.EngineeringProcessorItemAdvEmeraldCore.get(1),
                            ItemList.Circuit_Board_Bio_Ultra.get(1), GT_Utility.getIntegratedCircuit(1) },
                    solder,
                    components[3],
                    200,
                    500000,
                    true);
        }

        // Advanced Crafting Storage
        ItemStack[] storage = new ItemStack[] { getModItem(AE2, "tile.BlockAdvancedCraftingStorage", 1, 0), // 256k
                getModItem(AE2, "tile.BlockAdvancedCraftingStorage", 1, 1), // 1024k
                getModItem(AE2, "tile.BlockAdvancedCraftingStorage", 1, 2), // 4096k
                getModItem(AE2, "tile.BlockAdvancedCraftingStorage", 1, 3), // 16384k
        };
        for (int i = 0; i < storage.length; i++) {
            GT_ModHandler.removeRecipeByOutput(storage[i]);
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] { components[i], getModItem(AE2, "tile.BlockCraftingUnit", 1) },
                    null,
                    storage[i],
                    400,
                    1920);
        }

        // ME Block Container
        GT_ModHandler.removeRecipeByOutput(AE2_BLOCK_CONTAINER);
        GT_ModHandler.addCraftingRecipe(
                AE2_BLOCK_CONTAINER,
                new Object[] { " K ", "SMS", "dHw", 'K', getModItem(AE2, "item.ItemMultiMaterial", 1, 35), 'S',
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Titanium, 1), 'M', AE2_ME_CHEST, 'H',
                        AE2_HOUSING });
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { getModItem(AE2, "item.ItemMultiMaterial", 1, 35),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Titanium, 2), AE2_ME_CHEST, AE2_HOUSING,
                        GT_Utility.getIntegratedCircuit(4) },
                null,
                AE2_BLOCK_CONTAINER,
                40,
                120);

        // ME Quantum Storage
        GT_ModHandler.removeRecipeByOutput(getModItem(AE2, "item.ItemExtremeStorageCell.Quantum", 1));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(AE2, "item.ItemExtremeStorageCell.Quantum", 1),
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
        GT_ModHandler.removeRecipeByOutput(getModItem(AE2, "item.ItemExtremeStorageCell.Singularity", 1));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(AE2, "item.ItemExtremeStorageCell.Singularity", 1),
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
                getModItem("Avaritia", "Resource", 1, 5),
                'c',
                getModItem("gregtech", "gt.blockmachines", 1, 129),
                'd',
                components[3],
                'e',
                "blockInfinity",
                'f',
                getModItem("eternalsingularity", "eternal_singularity", 1));

        // ME Void Storage
        GT_ModHandler.removeRecipeByOutput(getModItem(AE2, "item.ItemVoidStorageCell", 1));
        addShapedRecipe(
                getModItem(AE2, "item.ItemVoidStorageCell", 1),
                new Object[] { "craftingToolHardHammer", "plateCertusQuartz", "screwCertusQuartz", "plateTungsten",
                        "gemEnderEye", "plateTungsten", "screwCertusQuartz", "plateTungsten",
                        "craftingToolScrewdriver" });
    }
}
