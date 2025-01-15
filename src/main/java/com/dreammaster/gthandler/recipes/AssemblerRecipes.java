package com.dreammaster.gthandler.recipes;

import static bartworks.system.material.WerkstoffLoader.LuVTierMaterial;
import static goodgenerator.loader.Loaders.advancedRadiationProtectionPlate;
import static gregtech.api.enums.GTValues.L;
import static gregtech.api.enums.GTValues.W;
import static gregtech.api.enums.Mods.*;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;
import static gregtech.api.util.GTRecipeBuilder.WILDCARD;
import static gtPlusPlus.core.block.ModBlocks.blockFishTrap;
import static gtPlusPlus.core.material.MaterialsAlloy.AQUATIC_STEEL;
import static gtPlusPlus.core.material.MaterialsAlloy.HASTELLOY_C276;
import static gtPlusPlus.core.material.MaterialsAlloy.HASTELLOY_W;
import static gtPlusPlus.core.material.MaterialsAlloy.HASTELLOY_X;
import static gtPlusPlus.core.material.MaterialsAlloy.INCONEL_792;
import static gtPlusPlus.core.material.MaterialsAlloy.INDALLOY_140;
import static gtPlusPlus.core.material.MaterialsAlloy.LEAGRISIUM;
import static gtPlusPlus.core.material.MaterialsAlloy.NITINOL_60;
import static gtPlusPlus.core.material.MaterialsAlloy.STELLITE;
import static gtPlusPlus.core.material.MaterialsAlloy.TALONITE;
import static gtPlusPlus.core.material.MaterialsAlloy.TRINIUM_NAQUADAH_CARBON;
import static gtPlusPlus.core.material.MaterialsElements.STANDALONE.CHRONOMATIC_GLASS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.dreammaster.block.BlockList;
import com.dreammaster.gthandler.CustomItemList;
import com.dreammaster.item.NHItemList;

import bartworks.common.loaders.ItemRegistry;
import bartworks.system.material.WerkstoffLoader;
import goodgenerator.items.GGMaterial;
import goodgenerator.loader.Loaders;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsKevlar;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaGeneratedTool01;
import gtPlusPlus.core.material.MaterialsAlloy;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtneioreplugin.plugin.block.ModBlocks;
import gtnhlanth.common.register.WerkstoffMaterialPool;
import kekztech.common.TileEntities;

public class AssemblerRecipes implements Runnable {

    // put the soldering Materials in this array
    final Materials[] solderingMaterials = new Materials[] { Materials.Lead, Materials.SolderingAlloy, Materials.Tin };

    ItemStack missing = new ItemStack(Blocks.fire);

    @Override
    public void run() {

        // New organized methods -- Most of these still need RA2 conversions done, but at least they're in more workable
        // chunks.
        makeEnderIORecipes();
        makeAE2Recipes();
        makeTinkersConstructRecipes();
        makeForestryRecipes();
        makeOpenComputersRecipes();
        makeJabbaRecipes();
        makeZTonesRecipes();
        makeGalacticraftRocketRecipes();
        makeIronTanksRecipes();
        makeChiselRecipes();
        makeLootBagRecipes();
        makeCoinRecipes();
        makeCoilRecipes();
        makePistonRecipes();
        makeAirFilterRecipes();
        makeMixedMetalIngotRecipes();
        makeReinforcedIronPlateRecipes();
        makeNEIPlanetRecipes();
        makeSolderingAlloyRecipes();
        makeElectricMachinePartRecipes();
        makeCircuitPartRecipes();

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 8),
                        GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Hull_Bronze.get(1)).duration(2 * SECONDS + 10 * TICKS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 5),
                        new ItemStack(Blocks.brick_block, 3),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Hull_Bronze_Bricks.get(1)).duration(2 * SECONDS + 10 * TICKS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 8),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Hull_HP.get(1)).duration(2 * SECONDS + 10 * TICKS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 5),
                        new ItemStack(Blocks.brick_block, 3),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Hull_HP_Bricks.get(1)).duration(2 * SECONDS + 10 * TICKS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(IndustrialCraft2.ID, "blockAlloyGlass", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 4L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Empty180SpCell.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Empty180SpCell.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 3L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(CustomItemList.Empty360SpCell.get(1L)).duration(10 * SECONDS).eut(256)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Empty180SpCell.get(3L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 6L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.Empty540SpCell.get(1L)).duration(15 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Empty180SpCell.get(1L),
                        CustomItemList.Empty360SpCell.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 7L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Empty540SpCell.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Empty540SpCell.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 6L),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.ElectrumFlux, 1L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(CustomItemList.Empty1080SpCell.get(1L)).duration(10 * SECONDS).eut(1024)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Polybenzimidazole, 1L),
                        GTModHandler.getIC2Item("carbonMesh", 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Duct_Tape.get(2L)).fluidInputs(Materials.AdvancedGlue.getFluid(200))
                .duration(6 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(ItemList.Hull_LV.get(1), ItemList.Duct_Tape.get(2), GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Hatch_Maintenance.get(1)).fluidInputs(Materials.AdvancedGlue.getFluid(200))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // 10,30 and 60K NAK Cells

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemCellEmpty", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 4L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.TenKCell.get(1L)).duration(10 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.TenKCell.get(3L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 6L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.ThirtyKCell.get(1L)).duration(15 * SECONDS).eut(60).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemCellEmpty", 3L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 6L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.ThirtyKCell.get(1L)).duration(15 * SECONDS).eut(60).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.ThirtyKCell.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 8L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.SixtyKCell.get(1L)).duration(20 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemCellEmpty", 6L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 8L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.SixtyKCell.get(1L)).duration(20 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorLuV, 64L),
                        CustomItemList.MicaInsulatorFoil.get(48L))
                .itemOutputs(ItemList.Casing_Coil_Superconductor.get(1L))
                .fluidInputs(Materials.Trinium.getMolten(1440L)).duration(50 * SECONDS).eut(9001)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorZPM, 32L),
                        CustomItemList.MicaInsulatorFoil.get(32L))
                .itemOutputs(ItemList.Casing_Coil_Superconductor.get(1L)).fluidInputs(Materials.Trinium.getMolten(576L))
                .duration(50 * SECONDS).eut(9001).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUV, 16L),
                        CustomItemList.MicaInsulatorFoil.get(16L))
                .itemOutputs(ItemList.Casing_Coil_Superconductor.get(1L)).fluidInputs(Materials.Trinium.getMolten(288L))
                .duration(50 * SECONDS).eut(9001).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUHV, 8L),
                        CustomItemList.MicaInsulatorFoil.get(8L))
                .itemOutputs(ItemList.Casing_Coil_Superconductor.get(1L)).fluidInputs(Materials.Trinium.getMolten(144L))
                .duration(50 * SECONDS).eut(9001).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUEV, 4L),
                        CustomItemList.MicaInsulatorFoil.get(4L))
                .itemOutputs(ItemList.Casing_Coil_Superconductor.get(1L)).fluidInputs(Materials.Trinium.getMolten(72L))
                .duration(50 * SECONDS).eut(9001).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUIV, 2L),
                        CustomItemList.MicaInsulatorFoil.get(2L))
                .itemOutputs(ItemList.Casing_Coil_Superconductor.get(1L)).fluidInputs(Materials.Trinium.getMolten(36L))
                .duration(50 * SECONDS).eut(9001).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUMV, 1L),
                        CustomItemList.MicaInsulatorFoil.get(1L))
                .itemOutputs(ItemList.Casing_Coil_Superconductor.get(1L)).fluidInputs(Materials.Trinium.getMolten(18L))
                .duration(50 * SECONDS).eut(9001).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.SteelBars.get(4L)).duration(20 * SECONDS).eut(48).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.AluminiumBars.get(4L)).duration(20 * SECONDS).eut(64)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.StainlessSteelBars.get(4L)).duration(20 * SECONDS).eut(96)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.TitaniumBars.get(4L)).duration(20 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Tungsten, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.TungstenBars.get(4L)).duration(20 * SECONDS).eut(192)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.TungstenSteelBars.get(4L)).duration(20 * SECONDS).eut(256)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.ChromeBars.get(4L)).duration(20 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.IridiumBars.get(4L)).duration(20 * SECONDS).eut(1024)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Osmium, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.OsmiumBars.get(4L)).duration(20 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Neutronium, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.NeutroniumBars.get(4L)).duration(20 * SECONDS).eut(4096)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.RedstoneAlloy, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.RedstoneAlloyBars.get(4L)).duration(20 * SECONDS).eut(48)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.ElectricalSteel, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.ElectricalSteelBars.get(4L)).duration(20 * SECONDS).eut(64)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.ConductiveIron, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.ConductiveIronBars.get(4L)).duration(20 * SECONDS).eut(160)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.EnergeticAlloy, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.EnergeticAlloyBars.get(4L)).duration(20 * SECONDS).eut(384)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.VibrantAlloy, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.VibrantAlloyBars.get(4L)).duration(20 * SECONDS).eut(768)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.PulsatingIron, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.PulsatingIronBars.get(4L)).duration(20 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Soularium, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.SoulariumBars.get(4L)).duration(20 * SECONDS).eut(64)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.EnderiumBase, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.EnderiumBaseBars.get(4L)).duration(20 * SECONDS).eut(256)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Enderium, 3L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.EnderiumBars.get(4L)).duration(20 * SECONDS).eut(1024)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(GTModHandler.getIC2Item("carbonPlate", 4L), CustomItemList.MoldBoots.get(0L))
                .itemOutputs(CustomItemList.CarbonPartBoots.get(1L)).duration(20 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(GTModHandler.getIC2Item("carbonPlate", 8L), CustomItemList.MoldChestplate.get(0L))
                .itemOutputs(CustomItemList.CarbonPartChestplate.get(1L)).duration(20 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(GTModHandler.getIC2Item("carbonPlate", 5L), CustomItemList.MoldHelmet.get(0L))
                .itemOutputs(CustomItemList.CarbonPartHelmet.get(1L)).duration(20 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(GTModHandler.getIC2Item("carbonPlate", 7L), CustomItemList.MoldLeggings.get(0L))
                .itemOutputs(CustomItemList.CarbonPartLeggings.get(1L)).duration(20 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemArmorRubBoots", 1L, 0),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoBoots", 1L, W))
                .itemOutputs(CustomItemList.NanoRubberBoots.get(1L)).duration(40 * SECONDS).eut(1024)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoLegs", 1L, W),
                        GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.StainlessSteel, 4L))
                .itemOutputs(CustomItemList.NanoPlatedLeggings.get(1L)).duration(40 * SECONDS).eut(1024)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoHelmet", 1L, W),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemArmorHazmatHelmet", 1L, 0))
                .itemOutputs(CustomItemList.NanoScubaHelmet.get(1L)).duration(40 * SECONDS).eut(1024)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.NanoRubberBoots.get(1L), CustomItemList.IridiumAlloyItemCasing.get(4L))
                .itemOutputs(CustomItemList.QuantumPartBoots.get(1L)).duration(60 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(GraviSuite.ID, "advNanoChestPlate", 1L, W),
                        CustomItemList.IridiumAlloyItemCasing.get(8L))
                .itemOutputs(CustomItemList.QuantumPartChestplate.get(1L)).duration(60 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.NanoScubaHelmet.get(1L), CustomItemList.IridiumAlloyItemCasing.get(5L))
                .itemOutputs(CustomItemList.QuantumPartHelmetEmpty.get(1L)).duration(60 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.QuantumPartHelmetEmpty.get(1L), CustomItemList.ReinforcedGlassLense.get(8L))
                .itemOutputs(CustomItemList.QuantumPartHelmet.get(1L)).duration(60 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.NanoPlatedLeggings.get(1L), CustomItemList.IridiumAlloyItemCasing.get(7L))
                .itemOutputs(CustomItemList.QuantumPartLeggings.get(1L)).duration(60 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.NagaScaleChip.get(4L), GTUtility.getIntegratedCircuit(4))
                .itemOutputs(CustomItemList.NagaScaleFragment.get(1L)).fluidInputs(Materials.Thaumium.getMolten(72L))
                .duration(10 * SECONDS).eut(48).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.NagaScaleFragment.get(6L), GTUtility.getIntegratedCircuit(6))
                .itemOutputs(GTModHandler.getModItem(TwilightForest.ID, "item.nagaScale", 1L, 0))
                .fluidInputs(Materials.Thaumium.getMolten(216L)).duration(10 * SECONDS).eut(96).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.LichBoneChip.get(4L), GTUtility.getIntegratedCircuit(4))
                .itemOutputs(CustomItemList.LichBoneFragment.get(1L)).fluidInputs(Materials.Thaumium.getMolten(72L))
                .duration(12 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.LichBoneFragment.get(6L), GTUtility.getIntegratedCircuit(6))
                .itemOutputs(CustomItemList.LichBone.get(1L)).fluidInputs(Materials.Thaumium.getMolten(216L))
                .duration(12 * SECONDS + 10 * TICKS).eut(256).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 4L, 14),
                        GTModHandler.getModItem(TwilightForest.ID, "item.trophy", 0L, 2))
                .itemOutputs(CustomItemList.LichBone.get(1L)).duration(45 * SECONDS).eut(256).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CarminiteChip.get(4L), GTUtility.getIntegratedCircuit(4))
                .itemOutputs(CustomItemList.CarminiteFragment.get(1L)).fluidInputs(Materials.Thaumium.getMolten(72L))
                .duration(17 * SECONDS + 10 * TICKS).eut(1024).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CarminiteFragment.get(6L), GTUtility.getIntegratedCircuit(6))
                .itemOutputs(GTModHandler.getModItem(TwilightForest.ID, "item.carminite", 1L, 0))
                .fluidInputs(Materials.Thaumium.getMolten(216L)).duration(17 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.SnowQueenBloodDrop.get(24L), GTUtility.getIntegratedCircuit(24))
                .itemOutputs(CustomItemList.SnowQueenBlood.get(1L)).fluidInputs(Materials.Thaumium.getMolten(216L))
                .duration(20 * SECONDS).eut(4096).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 10L, 14),
                        GTModHandler.getModItem(TwilightForest.ID, "item.trophy", 0L, 4))
                .itemOutputs(CustomItemList.SnowQueenBlood.get(1L)).duration(1 * MINUTES + 30 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(Thaumcraft.ID, "ItemResource", 16L, 14),
                        GTModHandler.getModItem(Witchery.ID, "ingredient", 1L, 130))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.gem, Materials.NetherStar, 1)).duration(45 * SECONDS)
                .eut(256).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1L),
                        GTOreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 3L))
                .itemOutputs(CustomItemList.WoodenCasing.get(1L)).fluidInputs(Materials.Iron.getMolten(32L))
                .duration(5 * SECONDS).eut(8).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.EngineCore.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                        GTUtility.getIntegratedCircuit(10))
                .itemOutputs(GTModHandler.getModItem(Forestry.ID, "engine", 1L, 0)).duration(10 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.EngineCore.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 10L),
                        GTUtility.getIntegratedCircuit(10))
                .itemOutputs(GTModHandler.getModItem(Forestry.ID, "engine", 1L, 1)).duration(10 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.EngineCore.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 10L),
                        GTUtility.getIntegratedCircuit(10))
                .itemOutputs(GTModHandler.getModItem(Forestry.ID, "engine", 1L, 2)).duration(10 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.EngineCore.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 10L),
                        GTUtility.getIntegratedCircuit(10))
                .itemOutputs(GTModHandler.getModItem(BuildCraftCore.ID, "engineBlock", 1L, 2)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.EngineCore.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 10L),
                        GTUtility.getIntegratedCircuit(10))
                .itemOutputs(GTModHandler.getModItem(Forestry.ID, "engine", 1L, 4)).duration(10 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        // HEE Ectoplasma and Arcane Shards

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.EctoplasmaChip.get(4L), GTUtility.getIntegratedCircuit(4))
                .itemOutputs(CustomItemList.EctoplasmaFragment.get(1L))
                .fluidInputs(FluidRegistry.getFluidStack("endergoo", 100)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.EctoplasmaFragment.get(6L), GTUtility.getIntegratedCircuit(6))
                .itemOutputs(GTModHandler.getModItem(HardcoreEnderExpansion.ID, "endoplasm", 1L, 0))
                .fluidInputs(FluidRegistry.getFluidStack("endergoo", 200)).duration(10 * SECONDS).eut(240)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.ArcaneShardChip.get(4L), GTUtility.getIntegratedCircuit(4))
                .itemOutputs(CustomItemList.ArcaneShardFragment.get(1L))
                .fluidInputs(FluidRegistry.getFluidStack("endergoo", 100)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.ArcaneShardFragment.get(6L), GTUtility.getIntegratedCircuit(6))
                .itemOutputs(GTModHandler.getModItem(HardcoreEnderExpansion.ID, "arcane_shard", 1L, 0))
                .fluidInputs(FluidRegistry.getFluidStack("endergoo", 200)).duration(10 * SECONDS).eut(240)
                .addTo(assemblerRecipes);
        // HEE Runes

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.RuneOfPowerFragment.get(9L), GTUtility.getIntegratedCircuit(9))
                .itemOutputs(GTModHandler.getModItem(HardcoreEnderExpansion.ID, "rune", 1L, 0))
                .fluidInputs(FluidRegistry.getFluidStack("endergoo", 1000)).duration(2 * MINUTES).eut(TierEU.RECIPE_IV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.RuneOfAgilityFragment.get(9L), GTUtility.getIntegratedCircuit(9))
                .itemOutputs(GTModHandler.getModItem(HardcoreEnderExpansion.ID, "rune", 1L, 1))
                .fluidInputs(FluidRegistry.getFluidStack("endergoo", 1000)).duration(2 * MINUTES).eut(TierEU.RECIPE_IV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.RuneOfVigorFragment.get(9L), GTUtility.getIntegratedCircuit(9))
                .itemOutputs(GTModHandler.getModItem(HardcoreEnderExpansion.ID, "rune", 1L, 2))
                .fluidInputs(FluidRegistry.getFluidStack("endergoo", 1000)).duration(2 * MINUTES).eut(TierEU.RECIPE_IV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.RuneOfDefenseFragment.get(9L), GTUtility.getIntegratedCircuit(9))
                .itemOutputs(GTModHandler.getModItem(HardcoreEnderExpansion.ID, "rune", 1L, 3))
                .fluidInputs(FluidRegistry.getFluidStack("endergoo", 1000)).duration(2 * MINUTES).eut(TierEU.RECIPE_IV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.RuneOfMagicFragment.get(9L), GTUtility.getIntegratedCircuit(9))
                .itemOutputs(GTModHandler.getModItem(HardcoreEnderExpansion.ID, "rune", 1L, 4))
                .fluidInputs(FluidRegistry.getFluidStack("endergoo", 1000)).duration(2 * MINUTES).eut(TierEU.RECIPE_IV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.RuneOfVoidFragment.get(9L), GTUtility.getIntegratedCircuit(9))
                .itemOutputs(GTModHandler.getModItem(HardcoreEnderExpansion.ID, "rune", 1L, 5))
                .fluidInputs(FluidRegistry.getFluidStack("endergoo", 1000)).duration(2 * MINUTES).eut(TierEU.RECIPE_IV)
                .addTo(assemblerRecipes);

        // IC2 Charging Batteries
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemBatREDischarged", 4L, W),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemBatChargeRE", 1L, 0))
                .fluidInputs(Materials.SolderingAlloy.getMolten(144L)).duration(10 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemAdvBat", 1L, W),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemAdvBat", 1L, W),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemAdvBat", 1L, W),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemAdvBat", 1L, W),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchSpread", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitch", 1L, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.Copper, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemBatChargeAdv", 1L, 0))
                .fluidInputs(Materials.SolderingAlloy.getMolten(288L)).duration(20 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemBatCrystal", 1L, W),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemBatCrystal", 1L, W),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemBatCrystal", 1L, W),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemBatCrystal", 1L, W),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchSpread", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchSpread", 1L, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Gold, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemBatChargeCrystal", 1L, 0))
                .fluidInputs(Materials.SolderingAlloy.getMolten(576L)).duration(40 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemBatLamaCrystal", 1L, W),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemBatLamaCrystal", 1L, W),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemBatLamaCrystal", 1L, W),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemBatLamaCrystal", 1L, W),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 1L, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt08, Materials.Aluminium, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemBatChargeLamaCrystal", 1L, 0))
                .fluidInputs(Materials.SolderingAlloy.getMolten(1440L)).duration(60 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Steel, 16L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 4L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.AdsorptionFilterCasing.get(1L)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_MV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 4L),
                        ItemList.Electric_Motor_MV.get(4L),
                        ItemList.Electric_Pump_MV.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Cobalt, 4L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OilDrill1.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(144))
                .duration(20 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.OilDrill1.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 8L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 4L),
                        ItemList.Electric_Motor_HV.get(4L),
                        ItemList.Electric_Pump_HV.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.BlueSteel, 8L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OilDrill2.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(288))
                .duration(20 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.OilDrill2.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 12L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 4L),
                        ItemList.Electric_Motor_EV.get(4L),
                        ItemList.Electric_Pump_EV.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Titanium, 12L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OilDrill3.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(576))
                .duration(20 * SECONDS).eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.OilDrill3.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 16L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4L),
                        ItemList.Electric_Motor_IV.get(4L),
                        ItemList.Electric_Pump_IV.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.TungstenSteel, 16L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OilDrill4.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(1440))
                .duration(20 * SECONDS).eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_EV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 4L),
                        ItemList.Electric_Motor_EV.get(4L),
                        ItemList.Electric_Pump_EV.get(4L),
                        ItemList.Conveyor_Module_EV.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Tungsten, 4L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OreDrill1.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(72))
                .duration(20 * SECONDS).eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.OreDrill1.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4L),
                        ItemList.Electric_Motor_IV.get(4L),
                        ItemList.Electric_Pump_IV.get(4L),
                        ItemList.Conveyor_Module_IV.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Iridium, 4L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OreDrill2.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(144))
                .duration(20 * SECONDS).eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.OreDrill2.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Osmiridium, 4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 4L),
                        ItemList.Electric_Motor_LuV.get(4L),
                        ItemList.Electric_Pump_LuV.get(4L),
                        ItemList.Conveyor_Module_LuV.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.gearGt, LuVTierMaterial.getBridgeMaterial(), 4L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OreDrill3.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(288))
                .duration(20 * SECONDS).eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.OreDrill3.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Europium, 4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ZPM, 4L),
                        ItemList.Electric_Motor_ZPM.get(4L),
                        ItemList.Electric_Pump_ZPM.get(4L),
                        ItemList.Conveyor_Module_ZPM.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.MysteriousCrystal, 4L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OreDrill4.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(576))
                .duration(20 * SECONDS).eut(TierEU.RECIPE_ZPM).addTo(assemblerRecipes);

        // LV Turbines

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.turbineBlade, Materials.Iron, 4L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 1L))
                .itemOutputs(
                        MetaGeneratedTool01.INSTANCE.getToolWithStats(170, 1, Materials.Iron, Materials.Steel, null))
                .duration(16 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.turbineBlade, Materials.WroughtIron, 4L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 1L))
                .itemOutputs(
                        MetaGeneratedTool01.INSTANCE
                                .getToolWithStats(170, 1, Materials.WroughtIron, Materials.Steel, null))
                .duration(16 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.turbineBlade, Materials.Steel, 4L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 1L))
                .itemOutputs(
                        MetaGeneratedTool01.INSTANCE.getToolWithStats(170, 1, Materials.Steel, Materials.Steel, null))
                .duration(16 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.LedoxCompressedPlate.get(1L),
                        CustomItemList.CallistoIceCompressedPlate.get(1L))
                .itemOutputs(CustomItemList.IceCompressedPlate.get(2L))
                .fluidInputs(new FluidStack(FluidRegistry.getFluid("ic2coolant"), 1000)).duration(15 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        // UEV-UXV casings+hulls

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bedrockium, 8),
                        GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_UEV.get(1L)).duration(2 * SECONDS + 10 * TICKS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 8L),
                        GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_UIV.get(1L)).duration(2 * SECONDS + 10 * TICKS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, MaterialsUEVplus.SpaceTime, 8L),
                        GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_UMV.get(1L)).duration(2 * SECONDS + 10 * TICKS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(
                GTOreDictUnificator
                        .get(OrePrefixes.plate, MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter, 4L),
                GTOreDictUnificator.get(OrePrefixes.plate, MaterialsUEVplus.MagMatter, 4L),
                GTUtility.getIntegratedCircuit(8)).itemOutputs(ItemList.Casing_UXV.get(1L))
                .duration(2 * SECONDS + 10 * TICKS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt08, Materials.Draconium, 2L),
                        ItemList.Casing_UEV.get(1L))
                .itemOutputs(ItemList.Hull_UEV.get(1L)).fluidInputs(Materials.Polybenzimidazole.getMolten(576L))
                .duration(2 * SECONDS + 10 * TICKS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt08, Materials.NetherStar, 2L),
                        ItemList.Casing_UIV.get(1L))
                .itemOutputs(ItemList.Hull_UIV.get(1L)).fluidInputs(Materials.Polybenzimidazole.getMolten(576L))
                .duration(2 * SECONDS + 10 * TICKS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt12, Materials.Quantium, 2L),
                        ItemList.Casing_UMV.get(1L))
                .itemOutputs(ItemList.Hull_UMV.get(1L)).fluidInputs(Materials.Polybenzimidazole.getMolten(576L))
                .duration(2 * SECONDS + 10 * TICKS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt16, Materials.BlackPlutonium, 2L),
                        ItemList.Casing_UXV.get(1L))
                .itemOutputs(ItemList.Hull_UXV.get(1L)).fluidInputs(MaterialsKevlar.Kevlar.getMolten(576L))
                .duration(2 * SECONDS + 10 * TICKS).eut(16).addTo(assemblerRecipes);

        // Hermetic casings

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Plastic, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 8L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_1.get(1L)).duration(5 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.PolyvinylChloride, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 8L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_2.get(1L)).duration(5 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Polytetrafluoroethylene, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 8L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_3.get(1L)).duration(5 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.StainlessSteel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_4.get(1L)).duration(5 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Titanium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 8L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_5.get(1L)).duration(5 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.TungstenSteel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 8L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_6.get(1L)).duration(5 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.NiobiumTitanium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 8L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_7.get(1L)).duration(5 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Enderium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Osmium, 8L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_8.get(1L)).duration(5 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Naquadah, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 8L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_9.get(1L)).duration(5 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.MysteriousCrystal, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bedrockium, 8L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_10.get(1L)).duration(5 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Items.diamond, 1, 0),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 4L))
                .itemOutputs(CustomItemList.TwilightCrystal.get(1L)).duration(30 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "goldDiamondUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).duration(30 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.RawLapotronCrystal.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 2L))
                .itemOutputs(GTModHandler.getIC2Item("lapotronCrystal", 1L, 26)).duration(16 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(ItemList.Firebrick.get(24), GTOreDictUnificator.get(OrePrefixes.dust, Materials.Gypsum, 8L))
                .itemOutputs(ItemList.Casing_Firebricks.get(4L))
                .fluidInputs(new FluidStack(FluidRegistry.getFluid("concrete"), 4608)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(ItemList.Firebrick.get(24), GTOreDictUnificator.get(OrePrefixes.dust, Materials.Gypsum, 8L))
                .itemOutputs(ItemList.Casing_Firebricks.get(4L)).fluidInputs(Materials.Concrete.getMolten(4608L))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.brick_block),
                        Materials.AnyBronze.getPlates(6),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Casing_BronzePlatedBricks.get(1L)).duration(10 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        Materials.Steel.getPlates(4),
                        Materials.Iron.getPlates(2),
                        ItemList.Casing_BronzePlatedBricks.get(1L))
                .itemOutputs(ItemList.Casing_Pyrolyse.get(1L)).duration(10 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // Extreme Engine Intake Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_RobustTungstenSteel.get(1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 4))
                .itemOutputs(ItemList.Casing_ExtremeEngineIntake.get(4)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        // Filter Machine Casing for cleanroom
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1L),
                        ItemList.Electric_Motor_MV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 1L),
                        ItemList.Component_Filter.get(2L),
                        CustomItemList.SteelBars.get(2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Casing_Vent.get(1L)).duration(30 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Carbon, 1L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Glowstone, 1L),
                        GTModHandler.getModItem(FloodLights.ID, "electricIncandescentLightBulb", 1L, 0),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(GalacticraftCore.ID, "tile.glowstoneTorch", 32L, 0))
                .duration(20 * SECONDS).eut(192).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.log, Materials.Wood, 4L),
                        GTOreDictUnificator.get(OrePrefixes.treeSapling, 4L),
                        new ItemStack(Items.compass, 1, 0))
                .itemOutputs(GTModHandler.getModItem(NaturesCompass.ID, "NaturesCompass", 1L, 0))
                .fluidInputs(Materials.Glue.getFluid(144L)).duration(10 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(new ItemStack(Items.string, 3, 0), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(new ItemStack(Blocks.wool, 1, 0)).duration(5 * SECONDS).eut(4).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.CarbonPartHelmet.get(1L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemNightvisionGoggles", 1L, W),
                        CustomItemList.NanoCrystal.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.EnergeticAlloy, 64L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoHelmet", 1))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(288L)).duration(30 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.CarbonPartChestplate.get(1L),
                        CustomItemList.NanoCrystal.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.EnergeticAlloy, 64L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoChestplate", 1))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(1440L)).duration(30 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.CarbonPartLeggings.get(1L),
                        CustomItemList.NanoCrystal.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.EnergeticAlloy, 64L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoLegs", 1))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(576L)).duration(30 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.CarbonPartBoots.get(1L),
                        CustomItemList.NanoCrystal.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.EnergeticAlloy, 64L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoBoots", 1))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(288L)).duration(30 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_LV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                        GTModHandler.getModItem(IronChests.ID, "BlockIronChest", 1L, 0))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "blockPersonal", 1L, 0))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Covers

        GTValues.RA.stdBuilder()
                .itemInputs(
                        Materials.Aluminium.getPlates(4),
                        Materials.Glass.getPlates(1),
                        Materials.Glowstone.getDust(1),
                        ItemList.DYE_ONLY_ITEMS[1].get(1),
                        ItemList.DYE_ONLY_ITEMS[10].get(1),
                        ItemList.DYE_ONLY_ITEMS[4].get(1))
                .itemOutputs(ItemList.Cover_Screen.get(1L)).duration(5 * SECONDS).eut(5).addTo(assemblerRecipes);
        // Nukes

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(21),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Lead, 4L),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Titanium, 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "blockMachine", 1L, 12))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "blockReactorChamber", 1L))
                .duration(60 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(22),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Lead, 2L),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Titanium, 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "blockReactorChamber", 3L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt08, Materials.Platinum, 1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 1L))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "blockGenerator", 1L, 5))
                .duration(60 * SECONDS).eut(960).addTo(assemblerRecipes);

        if (AdvancedSolarPanel.isModLoaded()) {
            // Irradiant Glass Pane
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 4L, 2),
                            GTModHandler.getModItem(IndustrialCraft2.ID, "blockAlloyGlass", 4L, 0),
                            Materials.Glowstone.getPlates(1),
                            GTUtility.getIntegratedCircuit(1))
                    .itemOutputs(GTModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 4L, 5))
                    .duration(5 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

            // Irradiant Uranium
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            Materials.Uranium.getIngots(1),
                            GTModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 1, 0))
                    .itemOutputs(GTModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 1, 2))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);
        }

        // Solar Light Splitter
        GTValues.RA.stdBuilder()
                .itemInputs(
                        NHItemList.BlueSpectralComponent.getIS(3),
                        NHItemList.RedSpectralComponent.getIS(3),
                        NHItemList.GreenSpectralComponent.getIS(3),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(NHItemList.SolarLightSplitter.getIS()).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        // Heat Exchangers
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(21),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Silver, 2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Copper, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitch", 1L, 1))
                .duration(3 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(21),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Copper, 2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Silver, 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitch", 1L, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchCore", 1L, 1))
                .duration(3 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(21),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitch", 1L, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchSpread", 1L, 1))
                .fluidInputs(Materials.StainlessSteel.getMolten(72)).duration(1 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(21),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitch", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitch", 1L, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 1L, 1))
                .duration(3 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Heat Vents

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(21),
                        ItemList.Electric_Motor_LV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2L),
                        new ItemStack(Blocks.iron_bars, 2))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorVent", 1L, 1)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(22),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Copper, 2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Silver, 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorVent", 1L, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorVentCore", 1L, 1))
                .duration(15 * SECONDS).eut(256).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(21),
                        ItemList.IC2_Industrial_Diamond.get(1L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorVent", 1L, 1),
                        CustomItemList.StainlessSteelBars.get(4L))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorVentDiamond", 1L, 1))
                .duration(15 * SECONDS).eut(256).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(22),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorVentDiamond", 1L, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorVentGold", 1L, 1))
                .fluidInputs(Materials.StainlessSteel.getMolten(72)).duration(20 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(21),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Tin, 2L),
                        CustomItemList.SteelBars.get(2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorVent", 1L, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorVentSpread", 1L))
                .duration(15 * SECONDS).eut(256).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(21),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 4L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchCore", 2L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorVentCore", 1L, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorCondensator", 1L, 1))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(21),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Lapis, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.RedAlloy, 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorVentGold", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchSpread", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorCondensator", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorCondensator", 1L, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorCondensatorLap", 1L, 1))
                .duration(45 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Easier chest upgrades

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.chest, Materials.Copper, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Iron, 1L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.chest, Materials.Iron, 1L)).duration(15 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.chest, Materials.Copper, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 1L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.chest, Materials.Steel, 1L)).duration(20 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.chest, Materials.Iron, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Gold, 1L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.chest, Materials.Gold, 1L)).duration(25 * SECONDS)
                .eut(64).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.chest, Materials.Steel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Gold, 1L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.chest, Materials.Gold, 1L)).duration(25 * SECONDS)
                .eut(64).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.chest, Materials.Gold, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 2L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.chest, Materials.Diamond, 1L)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        // Reactor Plating

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(22),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Lead, 1L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4L))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorPlating", 1L)).duration(20 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(21),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Copper, 2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Copper, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Silver, 1L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorPlating", 1L))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorPlatingHeat", 1L))
                .duration(30 * SECONDS).eut(256).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(23),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Lead, 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorPlating", 1L))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorPlatingExplosive", 1L))
                .duration(30 * SECONDS).eut(256).addTo(assemblerRecipes);
        // LV and MV Energy Hatches

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(4),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2),
                        ItemList.Electric_Pump_LV.get(1),
                        ItemList.Hull_LV.get(1),
                        ItemList.LV_Coil.get(2),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1))
                .itemOutputs(ItemList.Hatch_Energy_LV.get(1)).fluidInputs(Materials.Lubricant.getFluid(2000))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(4),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1),
                        ItemList.Electric_Pump_MV.get(1),
                        ItemList.Hull_MV.get(1),
                        ItemList.MV_Coil.get(2),
                        ItemList.Circuit_Chip_ULPIC.get(2))
                .itemOutputs(ItemList.Hatch_Energy_MV.get(1)).fluidInputs(Materials.Lubricant.getFluid(2000))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Neutron reflector recipes

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Copper, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 4L),
                        GTOreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 2L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 16L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1))
                .requiresCleanRoom().duration(60 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Beryllium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 4L),
                        GTOreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 2L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 8L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1))
                .requiresCleanRoom().duration(45 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenCarbide, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 4L),
                        GTOreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 2L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 4L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1))
                .requiresCleanRoom().duration(20 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Beryllium, 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0))
                .requiresCleanRoom().duration(30 * SECONDS).eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenCarbide, 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0))
                .requiresCleanRoom().duration(30 * SECONDS).eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Iridium, 2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Neutron_Reflector.get(1L)).requiresCleanRoom().duration(45 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Iridium, 2L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenCarbide, 36L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 64L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 32L),
                        GTOreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 48L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 64L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 32L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.Neutron_Reflector.get(1L)).requiresCleanRoom()
                .duration(2 * MINUTES + 37 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Iridium, 2L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Beryllium, 36L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 64L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 32L),
                        GTOreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 48L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 64L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 64L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 64L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.Neutron_Reflector.get(1L)).requiresCleanRoom()
                .duration(3 * MINUTES + 7 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);

        // Humongous input hatch
        GTValues.RA.stdBuilder().itemInputs(ItemList.Hatch_Input_UXV.get(1), ItemList.Quantum_Tank_IV.get(1))
                .itemOutputs(ItemRegistry.humongousInputHatch.copy())
                .fluidInputs(MaterialsUEVplus.Space.getMolten(5_760L)).duration(5 * SECONDS).eut(TierEU.RECIPE_UMV)
                .addTo(assemblerRecipes);

        // Wood Plates
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 64),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 64))
                .fluidInputs(Materials.Glue.getFluid(144L)).duration(2 * MINUTES).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        if (OpenBlocks.isModLoaded() && BloodMagic.isModLoaded()) {
            ItemStack[] trophies = { GTModHandler.getModItem(OpenBlocks.ID, "trophy", 1L),
                    GTModHandler.getModItem(OpenBlocks.ID, "trophy", 1L),
                    GTModHandler.getModItem(OpenBlocks.ID, "trophy", 1L),
                    GTModHandler.getModItem(OpenBlocks.ID, "trophy", 1L), };

            NBTTagCompound[] tag = { new NBTTagCompound(), new NBTTagCompound(), new NBTTagCompound(),
                    new NBTTagCompound(), };

            tag[0].setString("entity", "Bat");
            tag[1].setString("entity", "Cow");
            tag[2].setString("entity", "Chicken");
            tag[3].setString("entity", "Sheep");

            ItemStack flightpotion = GTModHandler.getModItem(BloodMagic.ID, "alchemyFlask", 1L);
            NBTTagCompound flighttag = new NBTTagCompound();
            NBTTagList flighsubtag = new NBTTagList();
            NBTTagCompound temp;

            temp = new NBTTagCompound();
            temp.setInteger("concentration", 0);
            temp.setInteger("durationFactor", 2);
            temp.setInteger("potionID", 104);
            temp.setInteger("tickDuration", 1200);
            flighsubtag.appendTag(temp);

            flighttag.setTag("CustomFlaskEffects", flighsubtag);
            flightpotion.setTagCompound(flighttag);

            for (int i = 0; i < 4; ++i) {
                trophies[i].setTagCompound(tag[i]);
            }

            GTValues.RA.stdBuilder().itemInputs(trophies[0], new ItemStack(Items.golden_carrot))
                    .itemOutputs(new ItemStack(Items.potionitem, 1, 8262)).duration(6 * SECONDS).eut(TierEU.RECIPE_MV)
                    .nbtSensitive().addTo(assemblerRecipes);

            GTValues.RA.stdBuilder().itemInputs(trophies[1], new ItemStack(Items.iron_sword))
                    .itemOutputs(new ItemStack(Items.leather, 64)).duration(6 * SECONDS).eut(TierEU.RECIPE_MV)
                    .nbtSensitive().addTo(assemblerRecipes);

            GTValues.RA.stdBuilder().itemInputs(trophies[2], new ItemStack(Items.feather)).itemOutputs(flightpotion)
                    .duration(6 * SECONDS).eut(TierEU.RECIPE_MV).nbtSensitive().addTo(assemblerRecipes);

            GTValues.RA.stdBuilder().itemInputs(trophies[3], new ItemStack(Items.shears))
                    .itemOutputs(new ItemStack(Blocks.wool, 64)).duration(6 * SECONDS).eut(TierEU.RECIPE_MV)
                    .nbtSensitive().addTo(assemblerRecipes);

        }

        if (PamsHarvestCraft.isModLoaded()) {
            for (int i = 0; i < OreDictionary.getOres("cropCotton").size(); ++i) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Items.string, 4),
                                OreDictionary.getOres("cropCotton").get(i).splitStack(3))
                        .itemOutputs(GTModHandler.getModItem(PamsHarvestCraft.ID, "wovencottonItem", 1L, 0))
                        .duration(20 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            }
        }

        if (PamsHarvestCraft.isModLoaded() && Forestry.isModLoaded() && OpenComputers.isModLoaded()) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sugar, 1L),
                            GTOreDictUnificator.get(OrePrefixes.cell, Materials.Fuel, 1L),
                            GTOreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricAcid, 1L),
                            GTOreDictUnificator.get(OrePrefixes.cell, Materials.Acetone, 1L),
                            GTModHandler.getModItem(PamsHarvestCraft.ID, "pepperoniItem", 1L, 0),
                            GTOreDictUnificator.get(OrePrefixes.dye, Materials.Red, 1L),
                            GTUtility.getIntegratedCircuit(1))
                    .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 18))
                    .fluidInputs(FluidRegistry.getFluidStack("mead", 1000)).duration(10 * MINUTES).eut(TierEU.RECIPE_HV)
                    .addTo(assemblerRecipes);
        }

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.WroughtIron, 1L),
                        CustomItemList.SteelBars.get(6L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(new ItemStack(blockFishTrap, 1, 0)).duration(10 * SECONDS).eut(64).addTo(assemblerRecipes);

        if (ExtraBees.isModLoaded()) {
            ItemStack alveary = GTModHandler.getModItem(Forestry.ID, "alveary", 1L, 0);

            // Mutator
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 12),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.EnderEye, 2L),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.EnderPearl, 2L),
                            GTModHandler.getModItem(ExtraBees.ID, "hiveFrame.soul", 1L, 0))
                    .itemOutputs(GTModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 0))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                    .addTo(assemblerRecipes);

            // Frame Housing
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 10),
                            GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.WoodSealed, 1L),
                            GTModHandler.getModItem(Forestry.ID, "frameProven", 1L, 0),
                            GTModHandler.getModItem(Forestry.ID, "frameProven", 1L, 0),
                            GTModHandler.getModItem(Forestry.ID, "frameProven", 1L, 0),
                            GTModHandler.getModItem(Forestry.ID, "frameProven", 1L, 0))
                    .itemOutputs(GTModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 1))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                    .addTo(assemblerRecipes);

            // Rain Shield
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 8),
                            GTModHandler.getModItem(IndustrialCraft2.ID, "blockRubber", 4L),
                            new ItemStack(Blocks.brick_block, 1, 0))
                    .itemOutputs(GTModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 2))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                    .addTo(assemblerRecipes);

            // Alveary Lighting
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 2),
                            new ItemStack(Blocks.stained_glass, 4, W),
                            new ItemStack(Blocks.redstone_lamp, 1, 0))
                    .itemOutputs(GTModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 3))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                    .addTo(assemblerRecipes);

            // Electrical Stimulator
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 1),
                            GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Silver, 2L),
                            GTModHandler.getModItem(Forestry.ID, "chipsets", 2L, 2),
                            ItemList.Electric_Motor_HV.get(1L))
                    .itemOutputs(GTModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 4))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                    .addTo(assemblerRecipes);

            // Hatchery
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 3),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.Redstone, 2L),
                            ItemList.Conveyor_Module_MV.get(2L),
                            GTModHandler.getModItem(Forestry.ID, "apiculture", 1L, 0))
                    .itemOutputs(GTModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 5))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                    .addTo(assemblerRecipes);

            // Alveary Transmission
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 0),
                            GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                            GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.Aluminium, 3L),
                            ItemList.Hull_HV.get(1L))
                    .itemOutputs(GTModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 6))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                    .addTo(assemblerRecipes);

            // Alveary Unlighting
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 8),
                            GTModHandler.getModItem(ExtraUtilities.ID, "curtains", 4L, 0),
                            new ItemStack(Blocks.wool, 1, 15))
                    .itemOutputs(GTModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 7))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                    .addTo(assemblerRecipes);

        }

        if (Gendustry.isModLoaded()) {
            // pollen collection kit
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTModHandler.getModItem(Gendustry.ID, "Labware", 1),
                            GTModHandler.getModItem(Minecraft.ID, "string", 1),
                            GTModHandler.getModItem(Minecraft.ID, "paper", 1))
                    .itemOutputs(GTModHandler.getModItem(Gendustry.ID, "PollenKit", 1)).duration(10 * SECONDS)
                    .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        }

        if (ExtraUtilities.isModLoaded()) {

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.dust, Materials.Bedrockium, 9L),
                            GTUtility.getIntegratedCircuit(1))
                    .itemOutputs(GTModHandler.getModItem(ExtraUtilities.ID, "bedrockiumIngot", 1L, 0))
                    .duration(1 * TICKS).eut(TierEU.RECIPE_ZPM).addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.dust, Materials.Unstable, 9L),
                            GTUtility.getIntegratedCircuit(1))
                    .itemOutputs(GTModHandler.getModItem(ExtraUtilities.ID, "unstableingot", 1L, 2)).duration(1 * TICKS)
                    .eut(TierEU.RECIPE_ZPM).addTo(assemblerRecipes);

        }

        if (ExtraUtilities.isModLoaded() && IronChests.isModLoaded()) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTModHandler.getModItem(IronChests.ID, "BlockIronChest", 1L, 0),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4L),
                            GTUtility.getIntegratedCircuit(1))
                    .itemOutputs(GTModHandler.getModItem(ExtraUtilities.ID, "filing", 1L, 0))
                    .fluidInputs(Materials.Plastic.getMolten(144L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                    .addTo(assemblerRecipes);
        }

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeQuadruple, Materials.Infinity, 1L),
                        ItemList.Hull_UEV.get(1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Hatch_Input_Multi_2x2_UEV.get(1L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(2304L)).duration(30 * SECONDS).eut(TierEU.RECIPE_UEV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeQuadruple, MaterialsUEVplus.TranscendentMetal, 1L),
                        ItemList.Hull_UIV.get(1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Hatch_Input_Multi_2x2_UIV.get(1L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(2304L)).duration(30 * SECONDS).eut(TierEU.RECIPE_UIV)
                .addTo(assemblerRecipes);

        // Gear Box Casings
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Bronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Bronze, 2),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 4),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Gearbox_Bronze.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Steel, 2),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Gearbox_Steel.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 1),
                        GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Titanium, 2),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Gearbox_Titanium.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        // Quantum Armor
        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.QuantumPartHelmet.get(1L),
                        CustomItemList.QuantumCrystal.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 2),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 4),
                        ItemList.Energy_LapotronicOrb.get(1L),
                        ItemList.Sensor_IV.get(1L),
                        ItemList.Field_Generator_EV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 4),
                        GTUtility.getIntegratedCircuit(10))
                .itemOutputs(GTModHandler.getIC2Item("quantumHelmet", 1L, 26))
                .fluidInputs(Materials.Titanium.getMolten(1728L)).duration(1 * MINUTES + 15 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.QuantumPartChestplate.get(1L),
                        CustomItemList.QuantumCrystal.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 2),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 6),
                        ItemList.Energy_LapotronicOrb.get(1L),
                        ItemList.Field_Generator_EV.get(3L),
                        ItemList.Electric_Motor_IV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 4),
                        GTUtility.getIntegratedCircuit(11))
                .itemOutputs(GTModHandler.getIC2Item("quantumBodyarmor", 1L, 26))
                .fluidInputs(Materials.Titanium.getMolten(2880L)).duration(1 * MINUTES + 15 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.QuantumPartLeggings.get(1L),
                        CustomItemList.QuantumCrystal.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 2),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 6),
                        ItemList.Energy_LapotronicOrb.get(1L),
                        ItemList.Field_Generator_EV.get(2L),
                        ItemList.Electric_Motor_IV.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 4),
                        GTUtility.getIntegratedCircuit(12))
                .itemOutputs(GTModHandler.getIC2Item("quantumLeggings", 1L, 26))
                .fluidInputs(Materials.Titanium.getMolten(2304L)).duration(1 * MINUTES + 15 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.QuantumPartBoots.get(1L),
                        CustomItemList.QuantumCrystal.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 2),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 4),
                        ItemList.Energy_LapotronicOrb.get(1L),
                        ItemList.Field_Generator_EV.get(1L),
                        ItemList.Electric_Piston_IV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 4),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getIC2Item("quantumBoots", 1L, 26))
                .fluidInputs(Materials.Titanium.getMolten(1440L)).duration(1 * MINUTES + 15 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        // EBF Controller
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getIC2Item("ironFurnace", 3L),
                        ItemList.Casing_HeatProof.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 3),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2))
                .itemOutputs(ItemList.Machine_Multi_BlastFurnace.get(1L)).duration(10 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // Vacuum Freezer Controller
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Pump_HV.get(3L),
                        ItemList.Casing_FrostProof.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 3),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 2))
                .itemOutputs(ItemList.Machine_Multi_VacuumFreezer.get(1)).duration(10 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // Large Chemical Reactor
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(1L),
                        ItemList.Hull_HV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Polytetrafluoroethylene, 2L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 4),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Machine_Multi_LargeChemicalReactor.get(1)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        // Industrial Autoclave
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.AutoclaveLuV.get(2L),
                        ItemList.Casing_Autoclave.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 4L),
                        MaterialsAlloy.LAFIUM.getPlate(8))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(8 * 144))
                .itemOutputs(ItemList.Machine_Multi_Autoclave.get(1L)).duration(30 * SECONDS).eut(TierEU.RECIPE_LuV)
                .addTo(assemblerRecipes);

        // YOTT Casings
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.BlackSteel, 4L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Polytetrafluoroethylene, 2L),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 2L),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.BlackSteel, 1L))
                .itemOutputs(new ItemStack(Loaders.yottaFluidTankCasing, 1, 0)).duration(24 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        // Industrial Autoclave
        GTValues.RA.stdBuilder()
                .itemInputs(GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Polytetrafluoroethylene, 1L))
                .fluidInputs(Materials.StainlessSteel.getMolten(8 * 144)).itemOutputs(ItemList.Casing_Autoclave.get(1L))
                .duration(20 * SECONDS).eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        if (GraviSuite.isModLoaded() && GalacticraftMars.isModLoaded()) {
            // Gravitational Engine
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.wireGt12, Materials.SuperconductorLuV, 2),
                            GTModHandler.getModItem(IndustrialCraft2.ID, "blockMachine2", 4, 1),
                            ItemList.Transformer_LuV_IV.get(1),
                            GTModHandler.getModItem(GraviSuite.ID, "itemSimpleItem", 2, 2))
                    .itemOutputs(GTModHandler.getModItem(GraviSuite.ID, "itemSimpleItem", 1, 3)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
            // Fluid Manipulator
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Large_Fluid_Cell_Steel.get(1),
                            GTOreDictUnificator.get(OrePrefixes.pipeQuadruple, Materials.Steel, 4),
                            ItemList.Electric_Motor_HV.get(4))
                    .itemOutputs(GTModHandler.getModItem(GalacticraftMars.ID, "item.null", 1, 6)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
            // Cooling Core With NaK
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTModHandler.getModItem(IndustrialCraft2.ID, "reactorVentDiamond", 2, 1),
                            GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 2, 1),
                            Materials.CallistoIce.getPlates(1),
                            ItemList.Reactor_Coolant_NaK_3.get(2),
                            GTModHandler.getModItem(IndustrialCraft2.ID, "reactorPlatingHeat", 2, 0))
                    .itemOutputs(GTModHandler.getModItem(GraviSuite.ID, "itemSimpleItem", 1, 2)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
            // Cooling Core With Helium
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTModHandler.getModItem(IndustrialCraft2.ID, "reactorVentDiamond", 2, 1),
                            GTModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 2, 1),
                            Materials.CallistoIce.getPlates(1),
                            ItemList.Reactor_Coolant_He_3.get(2),
                            GTModHandler.getModItem(IndustrialCraft2.ID, "reactorPlatingHeat", 2, 0))
                    .itemOutputs(GTModHandler.getModItem(GraviSuite.ID, "itemSimpleItem", 1, 2)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        }
        // ULV Transformer
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Lead, 4),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1),
                        ItemList.Hull_ULV.get(1))
                .itemOutputs(ItemList.Transformer_LV_ULV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // LV Transformer
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 4),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 1),
                        ItemList.Hull_LV.get(1))
                .itemOutputs(ItemList.Transformer_MV_LV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // MV Transformer
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 4),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
                        ItemList.Hull_MV.get(1),
                        ItemList.Circuit_Parts_InductorSMD.get(2))
                .itemOutputs(ItemList.Transformer_HV_MV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // HV Transformer
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 4),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1),
                        ItemList.Hull_HV.get(1),
                        ItemList.Circuit_Chip_ULPIC.get(2))
                .itemOutputs(ItemList.Transformer_EV_HV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // EV Transformer
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 4),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1),
                        ItemList.Hull_EV.get(1),
                        ItemList.Circuit_Chip_LPIC.get(2))
                .itemOutputs(ItemList.Transformer_IV_EV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // IV Transformer
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 4),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.VanadiumGallium, 1),
                        ItemList.Circuit_Chip_PIC.get(2),
                        ItemList.Hull_IV.get(1))
                .itemOutputs(ItemList.Transformer_LuV_IV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // LuV transformer
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.VanadiumGallium, 4),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Naquadah, 1),
                        ItemList.Circuit_Chip_HPIC.get(2),
                        ItemList.Hull_LuV.get(1))
                .itemOutputs(ItemList.Transformer_ZPM_LuV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // ZPM Transformer
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Naquadah, 4),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.NaquadahAlloy, 1),
                        ItemList.Hull_ZPM.get(1),
                        ItemList.Circuit_Chip_UHPIC.get(2))
                .itemOutputs(ItemList.Transformer_UV_ZPM.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // UV Transformer
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.NaquadahAlloy, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Bedrockium, 1),
                        ItemList.Hull_UV.get(1),
                        ItemList.Circuit_Chip_NPIC.get(2))
                .itemOutputs(ItemList.Transformer_MAX_UV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // Tesla Coil
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Gold, 1),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 2),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemRecipePart", 5, 0),
                        ItemList.Transformer_HV_MV.get(1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "blockMachine2", 1, 1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Super Tank V
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.circuit.get(Materials.IV), 4),
                        Materials.Titanium.getPlates(2),
                        ItemList.Electric_Pump_EV.get(1),
                        ItemList.Field_Generator_HV.get(1),
                        ItemList.Casing_Tank_5.get(1))
                .itemOutputs(ItemList.Super_Tank_IV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        // Super Chest V
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.circuit.get(Materials.IV), 4),
                        GTOreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Titanium, 3),
                        ItemList.Field_Generator_HV.get(1),
                        ItemList.Automation_ChestBuffer_IV.get(1))
                .itemOutputs(ItemList.Super_Chest_IV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        // Chest Buffer IV
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Conveyor_Module_IV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.circuit.get(Materials.IV), 1),
                        ItemList.Hull_IV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.chest, Materials.Wood, 1))
                .itemOutputs(ItemList.Automation_ChestBuffer_IV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        // Quantum Tank V
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.circuit.get(Materials.UEV), 4),
                        Materials.Americium.getPlates(2),
                        ItemList.Electric_Pump_UV.get(1),
                        ItemList.Field_Generator_UV.get(1),
                        ItemList.Casing_Tank_10.get(1))
                .itemOutputs(ItemList.Quantum_Tank_IV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);
        // Quantum Tank IV
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.circuit.get(Materials.UHV), 4),
                        Materials.Europium.getPlates(2),
                        ItemList.Electric_Pump_ZPM.get(1),
                        ItemList.Field_Generator_ZPM.get(1),
                        ItemList.Casing_Tank_9.get(1))
                .itemOutputs(ItemList.Quantum_Tank_EV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);
        // Quantum Chest V
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.circuit.get(Materials.UEV), 4),
                        Materials.Americium.getPlates(3),
                        ItemList.Field_Generator_UV.get(1),
                        ItemList.Automation_ChestBuffer_UEV.get(1L))
                .itemOutputs(ItemList.Quantum_Chest_IV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);
        // Ultra High Voltage Chest Buffer
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Conveyor_Module_UEV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.circuit.get(Materials.UEV), 1),
                        ItemList.Hull_UEV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.chest, Materials.Wood, 1))
                .itemOutputs(ItemList.Automation_ChestBuffer_UEV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        // UV Compressor
        GTValues.RA.stdBuilder().itemInputs(
                ItemList.Hull_UV.get(1),
                // UV circuit, but internal naming is SuperconductorUHV?
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UV, 2),
                ItemList.Electric_Piston_UV.get(2),
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.ElectrumFlux, 4))
                .itemOutputs(ItemList.CompressorUV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_UV)
                .addTo(assemblerRecipes);

        // UV Microwave Transmitter
        GTValues.RA.stdBuilder().itemInputs(
                ItemList.Hull_UV.get(1),
                // UV circuit, but internal naming is SuperconductorUHV?
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UV, 2),
                ItemList.Emitter_UV.get(4),
                ItemList.Field_Generator_UV.get(1),
                ItemList.Energy_Module.get(1)).itemOutputs(ItemList.MicroTransmitter_UV.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_UV).addTo(assemblerRecipes);

        // Industrial Tinted Glass
        ItemStack[] dyes = new ItemStack[] { ItemList.Color_15.get(1), // white
                ItemList.Color_07.get(1), // light gray
                ItemList.Color_08.get(1), // gray
                ItemList.Color_00.get(1) // black
        };

        ItemStack[] glasses = new ItemStack[] { ItemList.GlassTintedIndustrialWhite.get(1),
                ItemList.GlassTintedIndustrialLightGray.get(1), ItemList.GlassTintedIndustrialGray.get(1),
                ItemList.GlassTintedIndustrialBlack.get(1), };

        for (int meta = 0; meta < 4; ++meta) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1),
                            new ItemStack(Blocks.glass, 4),
                            dyes[meta])
                    .itemOutputs(glasses[meta]).eut(TierEU.RECIPE_LV).duration(10 * SECONDS).addTo(assemblerRecipes);
        }

        // Chemical grade glass
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1),
                        new ItemStack(Blocks.glass))
                .fluidInputs(Materials.Polytetrafluoroethylene.getMolten(4 * 144))
                .itemOutputs(ItemList.GlassPHResistant.get(1)).eut(TierEU.RECIPE_EV).duration(10 * SECONDS)
                .addTo(assemblerRecipes);

        // Ultraviolet emitter casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1),
                        ItemList.Emitter_UV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Naquadria, 4))
                .fluidInputs(Materials.Titanium.getPlasma(4 * 144))
                .itemOutputs(ItemList.BlockUltraVioletLaserEmitter.get(1)).eut(TierEU.RECIPE_UV).duration(10 * SECONDS)
                .addTo(assemblerRecipes);

        // Sterile Water Plant Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Ledox, 4),
                        ItemList.Electric_Motor_IV.get(2),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.PolyvinylChloride, 4))
                .fluidInputs(Materials.Ledox.getMolten(4 * 144))
                .itemOutputs(ItemList.BlockIndustrialWaterPlantCasing.get(1)).eut(TierEU.RECIPE_EV)
                .duration(10 * SECONDS).addTo(assemblerRecipes);

        // Reinforced Sterile Water Plant Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.BlockIndustrialWaterPlantCasing.get(1),
                        WerkstoffLoader.Ruridit.get(OrePrefixes.bolt, 16))
                .fluidInputs(Materials.Concrete.getMolten(8 * 144))
                .itemOutputs(ItemList.BlockSterileWaterPlantCasing.get(1)).eut(TierEU.RECIPE_IV).duration(10 * SECONDS)
                .addTo(assemblerRecipes);

        // Stabilized Naquadah Water Plant Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.NaquadahAlloy, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 6),
                        GTOreDictUnificator.get(OrePrefixes.pipeSmall, Materials.NiobiumTitanium, 4))
                .fluidInputs(Materials.SuperCoolant.getFluid(4000))
                .itemOutputs(ItemList.BlockNaquadahReinforcedWaterPlantCasing.get(1)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);

        // Inert Neutralization Water Plant Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        HASTELLOY_C276.getFrameBox(1),
                        HASTELLOY_C276.getPlate(3),
                        HASTELLOY_X.getPlate(3),
                        ItemList.Electric_Pump_LuV.get(1))
                .fluidInputs(Materials.Polytetrafluoroethylene.getMolten(4 * 144))
                .itemOutputs(ItemList.BlockExtremeCorrosionResistantCasing.get(1)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);

        // Naquadria Reinforced Water Plant Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.BlockNaquadahReinforcedWaterPlantCasing.get(1),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Naquadria, 1),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Naquadria, 8),
                        ItemList.Electric_Pump_ZPM.get(1))
                .fluidInputs(Materials.SuperCoolant.getFluid(8000))
                .itemOutputs(ItemList.BlockNaquadriaReinforcedWaterPlantCasing.get(1)).eut(TierEU.RECIPE_ZPM)
                .duration(10 * SECONDS).addTo(assemblerRecipes);

        // Inert Filtration Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        HASTELLOY_C276.getFrameBox(1),
                        HASTELLOY_X.getPlate(6),
                        HASTELLOY_C276.getRotor(2),
                        HASTELLOY_C276.getGear(2),
                        ItemList.Electric_Pump_IV.get(1))
                .fluidInputs(Materials.Polytetrafluoroethylene.getMolten(4 * 144))
                .itemOutputs(ItemList.BlockOzoneCasing.get(1)).eut(TierEU.RECIPE_IV).duration(10 * SECONDS)
                .addTo(assemblerRecipes);

        // Reactive gas containment casing
        GTValues.RA.stdBuilder()
                .itemInputs(HASTELLOY_W.getFrameBox(1), HASTELLOY_W.getPlate(6), HASTELLOY_W.getRotor(1))
                .fluidInputs(Materials.Polytetrafluoroethylene.getMolten(4 * 144))
                .itemOutputs(ItemList.BlockHighPressureResistantCasing.get(1)).eut(TierEU.RECIPE_IV)
                .duration(10 * SECONDS).addTo(assemblerRecipes);

        // slick sterile flocculation casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.StainlessSteel, 6),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 4),
                        ItemList.Electric_Pump_IV.get(1))
                .fluidInputs(Materials.Polytetrafluoroethylene.getMolten(4 * 144))
                .itemOutputs(ItemList.BlockFlocculationCasing.get(1)).eut(TierEU.RECIPE_LuV).duration(10 * SECONDS)
                .addTo(assemblerRecipes);

        // Trinium plated heat-resistant casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Trinium, 6),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.NaquadahAlloy, 16),
                        ItemList.UV_Coil.get(2))
                .fluidInputs(Materials.Osmiridium.getMolten(4 * 144))
                .itemOutputs(ItemList.BlockPlasmaHeatingCasing.get(1)).eut(TierEU.RECIPE_ZPM).duration(10 * SECONDS)
                .addTo(assemblerRecipes);

        // Ph sensor hatch
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_LuV.get(1),
                        ItemList.Cover_ActivityDetector.get(1),
                        ItemList.Cover_Screen.get(1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 4),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 1),
                        ItemList.Sensor_LuV.get(1),
                        ItemList.Emitter_LuV.get(1),
                        GTUtility.getIntegratedCircuit(1))
                .fluidInputs(Materials.SolderingAlloy.getMolten(8 * 144)).itemOutputs(ItemList.Hatch_pHSensor.get(1))
                .eut(TierEU.RECIPE_LuV).duration(10 * SECONDS).addTo(assemblerRecipes);

        // Lens housing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hatch_Input_Bus_UV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Neutronium, 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Draconium, 4),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.SuperconductorUHV, 1),
                        GTUtility.getIntegratedCircuit(1))
                .fluidInputs(Materials.SolderingAlloy.getMolten(8 * 144)).itemOutputs(ItemList.Hatch_LensHousing.get(1))
                .eut(TierEU.RECIPE_ZPM).duration(10 * SECONDS).addTo(assemblerRecipes);

        // lens indicator hatch
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_UV.get(1),
                        ItemList.Cover_ActivityDetector.get(1),
                        ItemList.Cover_Screen.get(1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 4),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.SuperconductorUHV, 1),
                        ItemList.Sensor_UV.get(1),
                        ItemList.Emitter_UV.get(1),
                        GTUtility.getIntegratedCircuit(1))
                .fluidInputs(Materials.SolderingAlloy.getMolten(8 * 144))
                .itemOutputs(ItemList.Hatch_LensIndicator.get(1)).eut(TierEU.RECIPE_ZPM).duration(10 * SECONDS)
                .addTo(assemblerRecipes);

        // degasser control hatch
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_UV.get(1),
                        ItemList.Cover_ActivityDetector.get(1),
                        ItemList.Cover_Screen.get(1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Infinity, 4),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UHV, 1),
                        ItemList.Sensor_UHV.get(1),
                        ItemList.Emitter_UHV.get(1),
                        GTUtility.getIntegratedCircuit(1))
                .fluidInputs(Materials.SolderingAlloy.getMolten(8 * 144))
                .itemOutputs(ItemList.Hatch_DegasifierControl.get(1)).eut(TierEU.RECIPE_UHV).duration(10 * SECONDS)
                .addTo(assemblerRecipes);

        if (HardcoreEnderExpansion.isModLoaded()) {
            // Biome Compass
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            Materials.HeeEndium.getPlates(1),
                            GTOreDictUnificator.get(OrePrefixes.bolt, Materials.HeeEndium, 1),
                            GTOreDictUnificator.get(OrePrefixes.screw, Materials.HeeEndium, 2),
                            GTOreDictUnificator.get(OrePrefixes.ring, Materials.HeeEndium, 2),
                            GTOreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 1),
                            GTModHandler.getModItem(HardcoreEnderExpansion.ID, "end_powder", 1, 0))
                    .itemOutputs(GTModHandler.getModItem(HardcoreEnderExpansion.ID, "biome_compass", 1, 0))
                    .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        }
        if (StorageDrawers.isModLoaded()) {
            // Alternate Storage Template Recipe
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Electric_Piston_LV.get(1),
                            GTOreDictUnificator.get("drawerBasic", 1),
                            GTUtility.getIntegratedCircuit(2))
                    .itemOutputs(GTModHandler.getModItem(StorageDrawers.ID, "upgradeTemplate", 3, 0))
                    .duration(60 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        }
        // Display
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Cover_Screen.get(1),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 2),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.Iron, 2))
                .fluidInputs(Materials.Plastic.getMolten(144)).itemOutputs(CustomItemList.Display.get(1))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Ender-Quantum Component
        if (AdvancedSolarPanel.isModLoaded()) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.InfinityCatalyst, 1),
                            GTModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 4, 3),
                            Materials.ElectrumFlux.getPlates(4))
                    .itemOutputs(NHItemList.EnderQuantumComponent.getIS()).duration(10 * SECONDS).eut(TierEU.RECIPE_HV)
                    .addTo(assemblerRecipes);
        }

        // 16A Zpm to LuV transformer
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.VanadiumGallium, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Naquadah, 1),
                        ItemList.Transformer_ZPM_LuV.get(1),
                        ItemList.Casing_Coil_TungstenSteel.get(1))
                .itemOutputs(GregtechItemList.Transformer_HA_ZPM_LuV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        // 64A Zpm To LuV transformer
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt16, Materials.VanadiumGallium, 2),
                        GTOreDictUnificator.get(OrePrefixes.cableGt08, Materials.Naquadah, 1),
                        GTOreDictUnificator.get(OrePrefixes.spring, Materials.Naquadah, 1),
                        GTOreDictUnificator.get(OrePrefixes.springSmall, Materials.VanadiumGallium, 1),
                        GregtechItemList.Transformer_HA_ZPM_LuV.get(1),
                        ItemList.Electric_Pump_LV.get(1))
                .fluidInputs(Materials.Lubricant.getFluid(2000)).itemOutputs(ItemList.WetTransformer_ZPM_LuV.get(1))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        // Hastelloy-X Structural Block
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_EV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2),
                        HASTELLOY_X.getGear(1),
                        HASTELLOY_C276.getFrameBox(1),
                        MaterialsAlloy.INCONEL_792.getRing(2))
                .itemOutputs(GregtechItemList.Casing_Refinery_Structural.get(2)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        // MagTech Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 6L),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 1L))
                .itemOutputs(ItemList.Casing_Electromagnetic_Separator.get(1)).duration(2 * SECONDS + 10 * TICKS)
                .eut(16).addTo(assemblerRecipes);

        // Neutronium Stabilization Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_MAX.get(1),
                        ItemList.Field_Generator_ZPM.get(1),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.NaquadahAlloy, 4),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.NaquadahAlloy, 24),
                        GTUtility.getIntegratedCircuit(16))
                .itemOutputs(ItemList.Neutronium_Stable_Casing.get(4)).duration(10 * SECONDS).eut(TierEU.RECIPE_UV)
                .addTo(assemblerRecipes);

        // Neutronium Compressor conversion
        GTValues.RA.stdBuilder()
                .itemInputs(
                        getModItem(Avaritia.ID, "Neutronium_Compressor", 1, 0, missing),
                        GTUtility.getIntegratedCircuit(16))
                .itemOutputs(ItemList.Machine_Multi_NeutroniumCompressor.get(1)).duration(2 * SECONDS + 10 * TICKS)
                .eut(16).addTo(assemblerRecipes);

        // Background Radiation Absorbent Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_RadiationProof.get(1),
                        new ItemStack(advancedRadiationProtectionPlate, 8),
                        GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Naquadria, 1),
                        GTUtility.getIntegratedCircuit(16))
                .fluidInputs(Materials.UUMatter.getFluid(64000L))
                .itemOutputs(ItemList.Background_Radiation_Casing.get(1)).duration(10).eut(TierEU.RECIPE_UIV)
                .addTo(assemblerRecipes);

        // Extreme Density Space-Bending Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        BlockList.NeutroniumPlatedReinforcedStone.getIS(),
                        GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Steel, 64),
                        GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Iron, 64),
                        GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Bedrockium, 64),
                        GTUtility.getIntegratedCircuit(16))
                .fluidInputs(FluidRegistry.getFluidStack("wet.concrete", 16000))
                .itemOutputs(ItemList.Extreme_Density_Casing.get(1)).duration(5).eut(TierEU.RECIPE_UHV)
                .addTo(assemblerRecipes);

        // Laser Containment Casing
        GTValues.RA.stdBuilder().itemInputs(STELLITE.getPlate(6), NITINOL_60.getFrameBox(1))
                .itemOutputs(ItemList.Casing_Laser.get(1)).duration(2 * SECONDS + 10 * TICKS).eut(16)
                .addTo(assemblerRecipes);

        // Solidifier Casing
        GTValues.RA.stdBuilder().itemInputs(INCONEL_792.getPlate(4), AQUATIC_STEEL.getFrameBox(1), TALONITE.getPlate(2))
                .itemOutputs(ItemList.Casing_Fluid_Solidifier.get(1)).duration(2 * SECONDS + 10 * TICKS).eut(16)
                .addTo(assemblerRecipes);

        // Solidifier Radiator Casing
        GTValues.RA.stdBuilder().itemInputs(ItemList.Casing_Fluid_Solidifier.get(1), ItemList.Electric_Pump_IV.get(1))
                .fluidInputs(LEAGRISIUM.getFluidStack(1008)).itemOutputs(ItemList.Radiator_Fluid_Solidifier.get(2))
                .duration(2 * SECONDS + 10 * TICKS).eut(16).addTo(assemblerRecipes);

        // Electric Compressor Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GGMaterial.incoloy903.get(OrePrefixes.plate, 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 1))
                .itemOutputs(ItemList.Compressor_Casing.get(1)).duration(2 * SECONDS + 10 * TICKS).eut(16)
                .addTo(assemblerRecipes);

        // Compression Pipe Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GGMaterial.incoloy903.get(OrePrefixes.plate, 4),
                        GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Titanium, 1),
                        GGMaterial.incoloy903.get(OrePrefixes.pipeSmall, 4))
                .itemOutputs(ItemList.Compressor_Pipe_Casing.get(1)).duration(2 * SECONDS + 10 * TICKS).eut(16)
                .addTo(assemblerRecipes);

        // Heating Duct Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_Pipe_Bronze.get(1),
                        ItemList.Electric_Pump_LuV.get(2),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Invar, 8),
                        CustomItemList.MicaInsulatorFoil.get(64),
                        CustomItemList.MicaInsulatorFoil.get(64),
                        CustomItemList.MicaInsulatorFoil.get(64),
                        TRINIUM_NAQUADAH_CARBON.getComponentByPrefix(OrePrefixes.pipeMedium, 2),
                        HASTELLOY_X.getComponentByPrefix(OrePrefixes.pipeMedium, 2),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Ultimate, 2))
                .fluidInputs(FluidRegistry.getFluidStack("pyrotheum", 16000))
                .itemOutputs(ItemList.Heating_Duct_Casing.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LuV)
                .addTo(assemblerRecipes);

        // Coolant Duct Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_Pipe_TungstenSteel.get(1),
                        ItemList.Electric_Pump_LuV.get(2),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Aluminium, 8),
                        ItemList.Reactor_Coolant_Sp_3.get(1),
                        ItemList.Reactor_Coolant_Sp_3.get(1),
                        ItemList.Reactor_Coolant_Sp_3.get(1),
                        TRINIUM_NAQUADAH_CARBON.getComponentByPrefix(OrePrefixes.pipeMedium, 2),
                        HASTELLOY_X.getComponentByPrefix(OrePrefixes.pipeMedium, 2),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Ultimate, 2))
                .fluidInputs(FluidRegistry.getFluidStack("cryotheum", 16000))
                .itemOutputs(ItemList.Coolant_Duct_Casing.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LuV)
                .addTo(assemblerRecipes);

        // Heat Sensor Hatch
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_ZPM.get(1),
                        ItemList.Compressor_Casing.get(1),
                        ItemList.Sensor_ZPM.get(2),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UV, 4),
                        GTUtility.getIntegratedCircuit(4))
                .fluidInputs(INDALLOY_140.getFluidStack(16000)).itemOutputs(ItemList.Hatch_HeatSensor.get(1))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);

        // Reinforced Wooden Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.WoodSealed, 6),
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1))
                .itemOutputs(ItemList.Casing_Reinforced_Wood.get(1)).duration(2 * SECONDS + 10 * TICKS).eut(16)
                .addTo(assemblerRecipes);

        // Iron Electromagnet
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.IronMagnetic, 8L),
                        ItemList.IV_Coil.get(8L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorIV, 16L))
                .itemOutputs(ItemList.Electromagnet_Iron.get(1)).fluidInputs(Materials.Cobalt.getMolten(1152))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        // Ultimate Time Anomaly
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_UV.get(2),
                        ItemList.Electric_Pump_UV.get(1),
                        ItemList.Conveyor_Module_UV.get(1),
                        ItemList.Robot_Arm_UV.get(2),
                        ItemList.Electric_Piston_UV.get(2),
                        ItemList.Hull_UV.get(1))
                .itemOutputs(ItemList.AcceleratorUV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        // LSC controller
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.battery, Materials.EV, 4),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 2),
                        ItemList.Circuit_Chip_PIC.get(2),
                        new ItemStack(kekztech.common.Blocks.lscLapotronicEnergyUnit, 1, 0))
                .itemOutputs(TileEntities.lsc.getStackForm(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeQuadruple, MaterialsUEVplus.SpaceTime, 1),
                        ItemList.Hull_UMV.get(1),
                        GTUtility.getIntegratedCircuit(4))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(2304))
                .itemOutputs(ItemList.Hatch_Input_Multi_2x2_UMV.get(1)).duration(30 * SECONDS).eut(TierEU.RECIPE_UMV)
                .addTo(assemblerRecipes);
        // Spacetime quadruple is temp replacement until Universium Pipes.
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.pipeQuadruple, MaterialsUEVplus.SpaceTime, 4),
                        ItemList.Hull_UXV.get(1),
                        GTUtility.getIntegratedCircuit(4))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(2304))
                .itemOutputs(ItemList.Hatch_Input_Multi_2x2_UXV.get(1)).duration(30 * SECONDS).eut(TierEU.RECIPE_UXV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemRegistry.humongousInputHatch.copy(),
                        GTOreDictUnificator.get(OrePrefixes.pipeHuge, MaterialsUEVplus.SpaceTime, 4),
                        GTUtility.getIntegratedCircuit(4))
                .fluidInputs(MaterialsUEVplus.Space.getMolten(5760))
                .itemOutputs(ItemList.Hatch_Input_Multi_2x2_Humongous.get(1)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_UXV).addTo(assemblerRecipes);

        // Diamond Gear
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Diamond, 4L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 4L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Diamond, 1L))
                .fluidInputs(Materials.Lubricant.getFluid(250L)).duration(10 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.CosmicNeutronium, 2),
                        CHRONOMATIC_GLASS.getFineWire(2))
                .itemOutputs(tectech.thing.CustomItemList.DATApipe.get(64))
                .fluidInputs(Materials.RadoxPolymer.getMolten(144L)).duration(10 * SECONDS).eut(TierEU.RECIPE_UV)
                .addTo(assemblerRecipes);

        // Fusion Coil Block
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 4L),
                        ItemList.Neutron_Reflector.get(2L),
                        ItemList.Field_Generator_MV.get(2L),
                        ItemList.Casing_Coil_Superconductor.get(1L))
                .itemOutputs(ItemList.Casing_Fusion_Coil.get(1L)).duration(15 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        if (VisualProspecting.isModLoaded()) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 2L),
                            new ItemStack(Items.writable_book, 1, WILDCARD),
                            new ItemStack(Items.gold_nugget, 1, WILDCARD))
                    .fluidInputs(Materials.Glue.getFluid(20L))
                    .itemOutputs(
                            GTModHandler.getModItem(VisualProspecting.ID, "item.visualprospecting.prospectorslog", 1))
                    .duration(6 * SECONDS).eut(8).addTo(assemblerRecipes);
        }

        // Superconductor Solenoid MV
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(6),
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorMV, 6L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.AnnealedCopper, 2L),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Aluminium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Iron, 8L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 2L),
                        new ItemStack[] { ItemList.Reactor_Coolant_He_1.get(1), ItemList.Reactor_Coolant_NaK_1.get(1) },
                        ItemList.Electric_Pump_MV.get(1))
                .fluidInputs(Materials.SolderingAlloy.getMolten(L * 2))
                .itemOutputs(ItemList.Superconducting_Magnet_Solenoid_MV.get(1)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Superconductor Solenoid HV
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(6),
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorHV, 8L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Silver, 2L),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Iron, 8L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.BlackSteel, 2L),
                        new ItemStack[] { ItemList.Reactor_Coolant_He_3.get(1), ItemList.Reactor_Coolant_NaK_3.get(1) },
                        ItemList.Electric_Pump_HV.get(1))
                .fluidInputs(Materials.SolderingAlloy.getMolten(L * 2))
                .itemOutputs(ItemList.Superconducting_Magnet_Solenoid_HV.get(1)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        // Superconductor Solenoid EV
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(6),
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorEV, 8L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Aluminium, 2L),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 1L),
                        WerkstoffMaterialPool.Permalloy.get(OrePrefixes.stickLong, 8),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 2L),
                        new ItemStack[] { ItemList.Reactor_Coolant_He_6.get(1), ItemList.Reactor_Coolant_NaK_6.get(1) },
                        ItemList.Electric_Pump_EV.get(1))
                .fluidInputs(Materials.SolderingAlloy.getMolten(L * 2))
                .itemOutputs(ItemList.Superconducting_Magnet_Solenoid_EV.get(1)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        // Superconductor Solenoid IV
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(6),
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorIV, 8L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.Tungsten, 2L),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 1L),
                        WerkstoffMaterialPool.Permalloy.get(OrePrefixes.stickLong, 8),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.HSSG, 2L),
                        ItemList.Reactor_Coolant_Sp_3.get(1),
                        ItemList.Electric_Pump_IV.get(1))
                .fluidInputs(Materials.SolderingAlloy.getMolten(L * 2))
                .itemOutputs(ItemList.Superconducting_Magnet_Solenoid_IV.get(1)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        // EV Battery Buffer (16 Slots)
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_EV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt16, Materials.Aluminium, 4),
                        new ItemStack(Blocks.chest))
                .itemOutputs(ItemList.Battery_Buffer_4by4_EV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        // Firebox Casings
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Bronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Bronze, 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 4),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Firebox_Bronze.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(ItemList.Casing_Firebox_Steel.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 1),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(ItemList.Casing_Firebox_Titanium.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 4),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(ItemList.Casing_Firebox_TungstenSteel.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // Alloy Blast Smelter
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Machine_IV_AlloySmelter.get(1),
                        MaterialsAlloy.ZIRCONIUM_CARBIDE.getPlate(2),
                        GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Tungsten, 2),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2))
                .itemOutputs(GregtechItemList.Industrial_AlloyBlastSmelter.get(1)).duration(1 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        // IV Alloy Smelter
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_IV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.TPV, 4),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Platinum, 2),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2))
                .itemOutputs(ItemList.Machine_IV_AlloySmelter.get(1)).duration(1 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        // LuV World Accelerator
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LuV.get(2),
                        ItemList.Electric_Pump_LuV.get(1),
                        ItemList.Conveyor_Module_LuV.get(1),
                        ItemList.Robot_Arm_LuV.get(2),
                        ItemList.Electric_Piston_LuV.get(2),
                        ItemList.Hull_LuV.get(1))
                .itemOutputs(ItemList.AcceleratorLuV.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        // Assembler Machine Casing
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1),
                        ItemList.Electric_Motor_IV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ZPM, 6),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 1))
                .itemOutputs(ItemList.Casing_Assembler.get(1)).duration(10 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        if (IronChests.isModLoaded()) {
            for (FluidStack fluid : new FluidStack[] { Materials.SolderingAlloy.getMolten(L / 2),
                    Materials.Tin.getMolten(L), Materials.Lead.getMolten(L * 2), }) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.chest),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                                GTUtility.getIntegratedCircuit(1))
                        .fluidInputs(fluid.copy()).itemOutputs(ItemList.Cover_Chest_Basic.get(1)).duration(40 * SECONDS)
                        .eut(16).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(IronChests.ID, "BlockIronChest", 1, 3),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1),
                                GTUtility.getIntegratedCircuit(1))
                        .fluidInputs(fluid.copy()).itemOutputs(ItemList.Cover_Chest_Good.get(1)).duration(40 * SECONDS)
                        .eut(16).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(IronChests.ID, "BlockIronChest", 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                                GTUtility.getIntegratedCircuit(1))
                        .fluidInputs(fluid.copy()).itemOutputs(ItemList.Cover_Chest_Advanced.get(1))
                        .duration(40 * SECONDS).eut(16).addTo(assemblerRecipes);
            }
        }
    }

    private void makeElectricMachinePartRecipes() {
        // Motors

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.AnyIron, 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                .itemOutputs(ItemList.Electric_Motor_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                .itemOutputs(ItemList.Electric_Motor_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.AnyIron, 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                .itemOutputs(ItemList.Electric_Motor_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                .itemOutputs(ItemList.Electric_Motor_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.Cupronickel, 4L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L))
                .itemOutputs(ItemList.Electric_Motor_MV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.Cupronickel, 4L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnnealedCopper, 2L))
                .itemOutputs(ItemList.Electric_Motor_MV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Electrum, 4L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.Silver, 2L))
                .itemOutputs(ItemList.Electric_Motor_HV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.NeodymiumMagnetic, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.BlackSteel, 4L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.Aluminium, 2L))
                .itemOutputs(ItemList.Electric_Motor_EV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.NeodymiumMagnetic, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Graphene, 4L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.Tungsten, 2L))
                .itemOutputs(ItemList.Electric_Motor_IV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Pumps

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1))
                .itemOutputs(ItemList.Electric_Pump_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1))
                .itemOutputs(ItemList.Electric_Pump_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1))
                .itemOutputs(ItemList.Electric_Pump_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1))
                .itemOutputs(ItemList.Electric_Pump_MV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1))
                .itemOutputs(ItemList.Electric_Pump_MV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1))
                .itemOutputs(ItemList.Electric_Pump_MV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1))
                .itemOutputs(ItemList.Electric_Pump_HV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1))
                .itemOutputs(ItemList.Electric_Pump_HV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1))
                .itemOutputs(ItemList.Electric_Pump_HV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 1))
                .itemOutputs(ItemList.Electric_Pump_EV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 1))
                .itemOutputs(ItemList.Electric_Pump_EV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 1))
                .itemOutputs(ItemList.Electric_Pump_EV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_IV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 1))
                .itemOutputs(ItemList.Electric_Pump_IV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_IV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 1))
                .itemOutputs(ItemList.Electric_Pump_IV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Conveyors

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1))
                .itemOutputs(ItemList.Conveyor_Module_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1))
                .itemOutputs(ItemList.Conveyor_Module_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1))
                .itemOutputs(ItemList.Conveyor_Module_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 1))
                .itemOutputs(ItemList.Conveyor_Module_MV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 1))
                .itemOutputs(ItemList.Conveyor_Module_MV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 1))
                .itemOutputs(ItemList.Conveyor_Module_MV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1))
                .itemOutputs(ItemList.Conveyor_Module_HV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1))
                .itemOutputs(ItemList.Conveyor_Module_HV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1))
                .itemOutputs(ItemList.Conveyor_Module_HV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1))
                .itemOutputs(ItemList.Conveyor_Module_EV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1))
                .itemOutputs(ItemList.Conveyor_Module_EV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1))
                .itemOutputs(ItemList.Conveyor_Module_EV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_IV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1))
                .itemOutputs(ItemList.Conveyor_Module_IV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_IV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1))
                .itemOutputs(ItemList.Conveyor_Module_IV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Pistons

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 3),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2),
                        GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 1))
                .itemOutputs(ItemList.Electric_Piston_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 3),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 2),
                        GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Aluminium, 1))
                .itemOutputs(ItemList.Electric_Piston_MV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 3),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 2),
                        GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.StainlessSteel, 1))
                .itemOutputs(ItemList.Electric_Piston_HV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 3),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2),
                        GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Titanium, 1))
                .itemOutputs(ItemList.Electric_Piston_EV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_IV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 3),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 2),
                        GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.TungstenSteel, 1))
                .itemOutputs(ItemList.Electric_Piston_IV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Robot Arms

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(2L),
                        ItemList.Electric_Piston_LV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 3))
                .itemOutputs(ItemList.Robot_Arm_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(2L),
                        ItemList.Electric_Piston_MV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 3))
                .itemOutputs(ItemList.Robot_Arm_MV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(2L),
                        ItemList.Electric_Piston_HV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 3))
                .itemOutputs(ItemList.Robot_Arm_HV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(2L),
                        ItemList.Electric_Piston_EV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 3))
                .itemOutputs(ItemList.Robot_Arm_EV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_IV.get(2L),
                        ItemList.Electric_Piston_IV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 3))
                .itemOutputs(ItemList.Robot_Arm_IV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Emitter

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 4),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 2L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2))
                .itemOutputs(ItemList.Emitter_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.EnderPearl, 1),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Electrum, 4),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 2L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 2))
                .itemOutputs(ItemList.Emitter_MV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.EnderEye, 1),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 4),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 2L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 2))
                .itemOutputs(ItemList.Emitter_HV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.QuantumEye.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Platinum, 4),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 2L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2))
                .itemOutputs(ItemList.Emitter_EV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.QuantumStar.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 4),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 2))
                .itemOutputs(ItemList.Emitter_IV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Sensor

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L))
                .itemOutputs(ItemList.Sensor_LV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.gemFlawless, Materials.Emerald, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Electrum, 1),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L))
                .itemOutputs(ItemList.Sensor_MV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.EnderEye, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 4),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 1),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L))
                .itemOutputs(ItemList.Sensor_HV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.QuantumEye.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Platinum, 1),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 1L))
                .itemOutputs(ItemList.Sensor_EV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.QuantumStar.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 4),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 1),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1L))
                .itemOutputs(ItemList.Sensor_IV.get(1L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Field Generator

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.EnderPearl, 1),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Field_Generator_LV.get(1)).fluidInputs(Materials.RedSteel.getMolten(288))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.EnderEye, 1),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Field_Generator_MV.get(1)).fluidInputs(Materials.TungstenSteel.getMolten(288))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.QuantumEye.get(1),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Field_Generator_HV.get(1)).fluidInputs(Materials.NiobiumTitanium.getMolten(576))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.NetherStar, 1),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 4),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(ItemList.Field_Generator_EV.get(1)).fluidInputs(Materials.HSSG.getMolten(576))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.QuantumStar.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ZPM, 4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Field_Generator_IV.get(1L)).fluidInputs(Materials.HSSS.getMolten(576))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

    }

    private void makeCircuitPartRecipes() {
        // Circuits and Boards

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 8),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 32),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Board_Coated_Basic.get(8L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 1152)).duration(1 * MINUTES + 20 * SECONDS).eut(8)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 8),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 32),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Board_Coated_Basic.get(8L)).fluidInputs(Materials.Glue.getFluid(576L))
                .duration(1 * MINUTES + 20 * SECONDS).eut(8).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 8),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 32),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Board_Coated_Basic.get(16L))
                .fluidInputs(Materials.Plastic.getMolten(288L)).duration(1 * MINUTES + 20 * SECONDS).eut(8)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 8),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 32),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Board_Coated_Basic.get(16L))
                .fluidInputs(Materials.Polytetrafluoroethylene.getMolten(144L)).duration(1 * MINUTES + 20 * SECONDS)
                .eut(8).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 8),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 32),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Board_Coated_Basic.get(24L)).fluidInputs(Materials.Epoxid.getMolten(144L))
                .duration(1 * MINUTES + 20 * SECONDS).eut(8).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 8),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 32),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Board_Coated_Basic.get(32L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(72L)).duration(1 * MINUTES + 20 * SECONDS).eut(8)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 8),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Circuit_Board_Phenolic.get(8L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).duration(2 * MINUTES).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 8),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Circuit_Board_Phenolic.get(8L)).fluidInputs(Materials.Glue.getFluid(288))
                .duration(2 * MINUTES).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 8),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Circuit_Board_Phenolic.get(16L)).fluidInputs(Materials.BisphenolA.getFluid(144))
                .duration(2 * MINUTES).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 8),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Circuit_Board_Phenolic.get(24L)).fluidInputs(Materials.Epoxid.getMolten(144L))
                .duration(2 * MINUTES).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Parts_Glass_Tube.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(2L)).fluidInputs(Materials.Redstone.getMolten(144L))
                .duration(8 * SECONDS).eut(8).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Parts_Glass_Tube.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(4L)).fluidInputs(Materials.RedAlloy.getMolten(72L))
                .duration(8 * SECONDS).eut(8).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Parts_Glass_Tube.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(8L))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(72L)).duration(8 * SECONDS).eut(8)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Parts_Glass_Tube.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(8L)).fluidInputs(Materials.RedAlloy.getMolten(72L))
                .duration(8 * SECONDS).eut(8).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Parts_Glass_Tube.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(16L))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(72L)).duration(8 * SECONDS).eut(8)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L)).fluidInputs(Materials.Glue.getFluid(288))
                .duration(16 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L)).fluidInputs(Materials.Glue.getFluid(288))
                .duration(16 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Charcoal, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L)).fluidInputs(Materials.Glue.getFluid(288))
                .duration(16 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Lignite, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L)).fluidInputs(Materials.Glue.getFluid(288))
                .duration(16 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).duration(16 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).duration(16 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Charcoal, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).duration(16 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Lignite, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).duration(16 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L)).fluidInputs(Materials.Glue.getFluid(288))
                .duration(16 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L)).fluidInputs(Materials.Glue.getFluid(288))
                .duration(16 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Charcoal, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L)).fluidInputs(Materials.Glue.getFluid(288))
                .duration(16 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Lignite, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L)).fluidInputs(Materials.Glue.getFluid(288))
                .duration(16 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).duration(16 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).duration(16 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Charcoal, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).duration(16 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Lignite, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).duration(16 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.GalliumArsenide, 1),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(4L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.GalliumArsenide, 1),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(2L)).fluidInputs(Materials.Glass.getMolten(288L))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.GalliumArsenide, 1),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(2L))
                .fluidInputs(FluidRegistry.getFluidStack("glass.molten", 1000)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        ItemList.Circuit_Silicon_Wafer.get(1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(2L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        ItemList.Circuit_Silicon_Wafer.get(1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(1L))
                .fluidInputs(FluidRegistry.getFluidStack("glass.molten", 1000)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        ItemList.Circuit_Silicon_Wafer.get(1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(1L)).fluidInputs(Materials.Glass.getMolten(288L))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        ItemList.Circuit_Silicon_Wafer.get(1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(4L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Tantalum, 4),
                        ItemList.Circuit_Silicon_Wafer.get(1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(8L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Coil.get(2L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .duration(16 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.NickelZincFerrite, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Coil.get(4L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .duration(16 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 2),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Coil.get(4L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .duration(16 * SECONDS).eut(60).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.NickelZincFerrite, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 2),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Coil.get(8L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .duration(16 * SECONDS).eut(60).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Plastic, 1),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Tantalum, 2),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Capacitor.get(12L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .duration(16 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Plastic, 1),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Aluminium, 2),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Capacitor.get(8L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .duration(16 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.SiliconSG, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Tin, 8),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.Circuit_Parts_Transistor.get(6L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .duration(16 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // All SMD Component recipes.

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 4),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_ResistorSMD.get(16L)).fluidInputs(Materials.Plastic.getMolten(288L))
                .duration(16 * SECONDS).eut(96).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Tantalum, 4),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_ResistorSMD.get(32L)).fluidInputs(Materials.Plastic.getMolten(288L))
                .duration(16 * SECONDS).eut(96).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.GalliumArsenide, 1),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(6L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Tantalum, 4),
                        GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.GalliumArsenide, 1),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(16L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 16),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.GalliumArsenide, 1),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_DiodeSMD.get(64L)).fluidInputs(Materials.Plastic.getMolten(576L))
                .duration(60 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Gallium, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 8),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_TransistorSMD.get(16L))
                .fluidInputs(Materials.Plastic.getMolten(288L)).duration(16 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Gallium, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Tantalum, 8),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_TransistorSMD.get(32L))
                .fluidInputs(Materials.Plastic.getMolten(288L)).duration(16 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Normal SMD

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Silicone, 2),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Aluminium, 2),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_CapacitorSMD.get(16L))
                .fluidInputs(Materials.Plastic.getMolten(144L)).duration(16 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.PolyvinylChloride, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Aluminium, 2),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_CapacitorSMD.get(24L))
                .fluidInputs(Materials.Plastic.getMolten(144L)).duration(16 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Silicone, 2),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Tantalum, 2),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_CapacitorSMD.get(32L))
                .fluidInputs(Materials.Plastic.getMolten(144L)).duration(16 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.PolyvinylChloride, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Tantalum, 2),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_CapacitorSMD.get(48L))
                .fluidInputs(Materials.Plastic.getMolten(144L)).duration(16 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Neodymium, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.ConductiveIron, 8),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_InductorSMD.get(24L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .duration(16 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Neodymium, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 8),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_InductorSMD.get(32L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .duration(16 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Neodymium, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Tantalum, 8),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_InductorSMD.get(48L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .duration(16 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Neodymium, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Iridium, 8),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_InductorSMD.get(64L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .duration(16 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        // ASMD

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Graphene, 2),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 16),
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 4),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Parts_ResistorASMD.get(64L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(576L)).duration(15 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.IndiumGalliumPhosphide, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 16),
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 4),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Parts_DiodeASMD.get(64L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(576L)).duration(15 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.VanadiumGallium, 2),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.HSSG, 16),
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 4),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Parts_TransistorASMD.get(64L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(576L)).duration(15 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.HSSS, 2),
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 4),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Parts_CapacitorASMD.get(64L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(576L)).duration(15 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ring, Materials.Samarium, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.HSSE, 32),
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 4),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Parts_InductorASMD.get(64L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(576L)).duration(8 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);
        // xSMD
        // Resistor

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Naquadria, 4L),
                        MaterialsAlloy.PIKYONIUM.getFoil(2),
                        GTOreDictUnificator
                                .get(OrePrefixes.foil, Materials.Tetranaquadahdiindiumhexaplatiumosminid, 1L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 1L),
                        GTUtility.getIntegratedCircuit(9))
                .itemOutputs(ItemList.Circuit_Parts_ResistorXSMD.get(32L))
                .fluidInputs(Materials.Xenoxene.getFluid(144L)).duration(8 * SECONDS).eut(TierEU.RECIPE_ZPM)
                .addTo(assemblerRecipes);
        // Transistor

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.BlackPlutonium, 4L),
                        MaterialsAlloy.TRINIUM_REINFORCED_STEEL.getFoil(2),
                        GTOreDictUnificator
                                .get(OrePrefixes.foil, Materials.Tetranaquadahdiindiumhexaplatiumosminid, 1L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 1L),
                        GTUtility.getIntegratedCircuit(9))
                .itemOutputs(ItemList.Circuit_Parts_TransistorXSMD.get(32L))
                .fluidInputs((Materials.Xenoxene.getFluid(144L))).duration(8 * SECONDS).eut(TierEU.RECIPE_ZPM)
                .addTo(assemblerRecipes);
        // Capacitor

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Draconium, 4L),
                        MaterialsAlloy.CINOBITE.getFoil(2),
                        GTOreDictUnificator
                                .get(OrePrefixes.foil, Materials.Tetranaquadahdiindiumhexaplatiumosminid, 1L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 1L),
                        GTUtility.getIntegratedCircuit(9))
                .itemOutputs(ItemList.Circuit_Parts_CapacitorXSMD.get(32L))
                .fluidInputs((Materials.Xenoxene.getFluid(144L))).duration(8 * SECONDS).eut(TierEU.RECIPE_ZPM)
                .addTo(assemblerRecipes);
        // Diode

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Tritanium, 4L),
                        MaterialsAlloy.LAFIUM.getFoil(2),
                        GTOreDictUnificator
                                .get(OrePrefixes.foil, Materials.Tetranaquadahdiindiumhexaplatiumosminid, 1L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 1L),
                        GTUtility.getIntegratedCircuit(9))
                .itemOutputs(ItemList.Circuit_Parts_DiodeXSMD.get(64L)).fluidInputs((Materials.Xenoxene.getFluid(144L)))
                .duration(8 * SECONDS).eut(TierEU.RECIPE_ZPM).addTo(assemblerRecipes);
        // Inductor

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GGMaterial.hikarium.get(OrePrefixes.foil, 4),
                        GGMaterial.artheriumSn.get(OrePrefixes.foil, 1),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 1L),
                        GTUtility.getIntegratedCircuit(9))
                .itemOutputs(ItemList.Circuit_Parts_InductorXSMD.get(32L))
                .fluidInputs((Materials.Xenoxene.getFluid(144L))).duration(8 * SECONDS).eut(TierEU.RECIPE_ZPM)
                .addTo(assemblerRecipes);

    }

    private void makeAirFilterRecipes() {
        // Air Filter stuff

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1L),
                        GTUtility.getIntegratedCircuit(1),
                        CustomItemList.SteelBars.get(2))
                .itemOutputs(ItemList.Casing_AirFilter_Vent_T1.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(72)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1L),
                        GTUtility.getIntegratedCircuit(1),
                        CustomItemList.SteelBars.get(2))
                .itemOutputs(ItemList.Casing_AirFilter_Vent_T1.get(1L)).fluidInputs(Materials.Tin.getMolten(144))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1L),
                        GTUtility.getIntegratedCircuit(1),
                        CustomItemList.SteelBars.get(2))
                .itemOutputs(ItemList.Casing_AirFilter_Vent_T1.get(1L)).fluidInputs(Materials.Lead.getMolten(288))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_AirFilter_Vent_T1.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 2L),
                        ItemList.Electric_Motor_LV.get(2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Casing_AirFilter_Turbine_T1.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(72)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_AirFilter_Vent_T1.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 2L),
                        ItemList.Electric_Motor_LV.get(2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Casing_AirFilter_Turbine_T1.get(1L)).fluidInputs(Materials.Tin.getMolten(144))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_AirFilter_Vent_T1.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 2L),
                        ItemList.Electric_Motor_LV.get(2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Casing_AirFilter_Turbine_T1.get(1L)).fluidInputs(Materials.Lead.getMolten(288))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_LV.get(1L),
                        ItemList.Casing_Turbine.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L),
                        ItemList.Electric_Motor_LV.get(2L),
                        ItemList.Electric_Pump_LV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Machine_Multi_AirFilterT1.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(144)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_LV.get(1L),
                        ItemList.Casing_Turbine.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L),
                        ItemList.Electric_Motor_LV.get(2L),
                        ItemList.Electric_Pump_LV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Machine_Multi_AirFilterT1.get(1L)).fluidInputs(Materials.Tin.getMolten(288))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_LV.get(1L),
                        ItemList.Casing_Turbine.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L),
                        ItemList.Electric_Motor_LV.get(2L),
                        ItemList.Electric_Pump_LV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Machine_Multi_AirFilterT1.get(1L)).fluidInputs(Materials.Lead.getMolten(576))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 1L),
                        GTUtility.getIntegratedCircuit(1),
                        CustomItemList.TitaniumBars.get(2))
                .itemOutputs(ItemList.Casing_AirFilter_Vent_T2.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(72)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 1L),
                        GTUtility.getIntegratedCircuit(1),
                        CustomItemList.TitaniumBars.get(2))
                .itemOutputs(ItemList.Casing_AirFilter_Vent_T2.get(1L)).fluidInputs(Materials.Tin.getMolten(144))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 1L),
                        GTUtility.getIntegratedCircuit(1),
                        CustomItemList.TitaniumBars.get(2))
                .itemOutputs(ItemList.Casing_AirFilter_Vent_T2.get(1L)).fluidInputs(Materials.Lead.getMolten(288))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_AirFilter_Vent_T2.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Titanium, 2L),
                        ItemList.Electric_Motor_HV.get(2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Casing_AirFilter_Turbine_T2.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(72)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_AirFilter_Vent_T2.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Titanium, 2L),
                        ItemList.Electric_Motor_HV.get(2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Casing_AirFilter_Turbine_T2.get(1L)).fluidInputs(Materials.Tin.getMolten(144))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_AirFilter_Vent_T2.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Titanium, 2L),
                        ItemList.Electric_Motor_HV.get(2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Casing_AirFilter_Turbine_T2.get(1L)).fluidInputs(Materials.Lead.getMolten(288))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_HV.get(1L),
                        ItemList.Casing_Turbine2.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Titanium, 1L),
                        ItemList.Electric_Motor_HV.get(2L),
                        ItemList.Electric_Pump_HV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Machine_Multi_AirFilterT2.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(144)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_HV.get(1L),
                        ItemList.Casing_Turbine2.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Titanium, 1L),
                        ItemList.Electric_Motor_HV.get(2L),
                        ItemList.Electric_Pump_HV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Machine_Multi_AirFilterT2.get(1L)).fluidInputs(Materials.Tin.getMolten(288))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_HV.get(1L),
                        ItemList.Casing_Turbine2.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Titanium, 1L),
                        ItemList.Electric_Motor_HV.get(2L),
                        ItemList.Electric_Pump_HV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Machine_Multi_AirFilterT2.get(1L)).fluidInputs(Materials.Lead.getMolten(576))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1L),
                        GTUtility.getIntegratedCircuit(1),
                        CustomItemList.TungstenSteelBars.get(2))
                .itemOutputs(ItemList.Casing_AirFilter_Vent_T3.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(72)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1L),
                        GTUtility.getIntegratedCircuit(1),
                        CustomItemList.TungstenSteelBars.get(2))
                .itemOutputs(ItemList.Casing_AirFilter_Vent_T3.get(1L)).fluidInputs(Materials.Tin.getMolten(144))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1L),
                        GTUtility.getIntegratedCircuit(1),
                        CustomItemList.TungstenSteelBars.get(2))
                .itemOutputs(ItemList.Casing_AirFilter_Vent_T3.get(1L)).fluidInputs(Materials.Lead.getMolten(288))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_AirFilter_Vent_T3.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.TungstenSteel, 2L),
                        ItemList.Electric_Motor_IV.get(2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Casing_AirFilter_Turbine_T3.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(72)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_AirFilter_Vent_T3.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.TungstenSteel, 2L),
                        ItemList.Electric_Motor_IV.get(2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Casing_AirFilter_Turbine_T3.get(1L)).fluidInputs(Materials.Tin.getMolten(144))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_AirFilter_Vent_T3.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.TungstenSteel, 2L),
                        ItemList.Electric_Motor_IV.get(2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Casing_AirFilter_Turbine_T3.get(1L)).fluidInputs(Materials.Lead.getMolten(288))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_IV.get(1L),
                        ItemList.Casing_Turbine3.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1L),
                        ItemList.Electric_Motor_IV.get(2L),
                        ItemList.Electric_Pump_IV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Machine_Multi_AirFilterT3.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(144)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_IV.get(1L),
                        ItemList.Casing_Turbine3.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1L),
                        ItemList.Electric_Motor_IV.get(2L),
                        ItemList.Electric_Pump_IV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Machine_Multi_AirFilterT3.get(1L)).fluidInputs(Materials.Tin.getMolten(288))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_IV.get(1L),
                        ItemList.Casing_Turbine3.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1L),
                        ItemList.Electric_Motor_IV.get(2L),
                        ItemList.Electric_Pump_IV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Machine_Multi_AirFilterT3.get(1L)).fluidInputs(Materials.Lead.getMolten(576))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

    }

    private void makeEnderIORecipes() {
        if (!EnderIO.isModLoaded()) {
            return;
        }

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(EnderIO.ID, "blockFusedQuartz", 1L),
                        Materials.Glowstone.getDust(4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "blockFusedQuartz", 1L, 2)).duration(25 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        for (int i = 0; i < OreDictionary.getOres("dyeBlack").size(); i++) {

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTModHandler.getModItem(EnderIO.ID, "blockFusedQuartz", 1L),
                            OreDictionary.getOres("dyeBlack").get(i).splitStack(4))
                    .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "blockFusedQuartz", 1L, 4)).duration(25 * SECONDS)
                    .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        }

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 7))
                .fluidInputs(Materials.Plastic.getMolten(288)).duration(10 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Aluminium, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(288)).duration(10 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Tantalum, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 1))
                .fluidInputs(Materials.EnergeticAlloy.getMolten(576)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.EnderEye, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 8))
                .fluidInputs(Materials.EnergeticSilver.getMolten(576)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Chrome, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Glowstone, 4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 2))
                .fluidInputs(Materials.EnergeticAlloy.getMolten(576)).duration(10 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.EnderEye, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Glowstone, 4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 9))
                .fluidInputs(Materials.EnergeticSilver.getMolten(576)).duration(10 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Platinum, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        ItemList.QuantumEye.get(1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 3))
                .fluidInputs(Materials.CrystallineAlloy.getMolten(864)).duration(10 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Titanium, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        ItemList.QuantumEye.get(1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 3))
                .fluidInputs(Materials.CrystallineAlloy.getMolten(864)).duration(10 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.NetherStar, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.EndSteel, 1),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 4))
                .fluidInputs(Materials.MelodicAlloy.getMolten(1152)).duration(10 * SECONDS).eut(TierEU.RECIPE_IV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.VanadiumGallium, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        ItemList.QuantumStar.get(1L),
                        GTModHandler.getModItem(EnderIO.ID, "itemMaterial", 1L, 13),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 5))
                .fluidInputs(Materials.StellarAlloy.getMolten(1440)).duration(10 * SECONDS).eut(TierEU.RECIPE_LuV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GGMaterial.extremelyUnstableNaquadah.get(OrePrefixes.bolt, 4),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        ItemList.Gravistar.get(1L),
                        GTModHandler.getModItem(EnderIO.ID, "itemFrankenSkull", 1L, 5),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StellarAlloy, 2),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 6))
                .fluidInputs(Materials.Neutronium.getMolten(1440)).duration(10 * SECONDS).eut(TierEU.RECIPE_ZPM)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 5),
                        ItemList.Gravistar.get(1L),
                        GTModHandler.getModItem(EnderIO.ID, "itemFrankenSkull", 1L, 5),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StellarAlloy, 2))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 6))
                .fluidInputs(Materials.Neutronium.getMolten(1152)).duration(10 * SECONDS).eut(TierEU.RECIPE_ZPM)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(Minecraft.ID, "skull", 1L, 0),
                        GTModHandler.getModItem(Minecraft.ID, "rotten_flesh", 2L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.SiliconSG, 1))
                .itemOutputs(GTModHandler.getModItem(EnderIO.ID, "itemFrankenSkull", 1L, 5))
                .fluidInputs(Materials.Soularium.getMolten(288)).duration(5 * SECONDS).eut(TierEU.RECIPE_ZPM)
                .addTo(assemblerRecipes);

    }

    private void makeAE2Recipes() {
        if (!AppliedEnergistics2.isModLoaded()) {
            return;
        }

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 24),
                        CustomItemList.EssentiaCircuit.get(1L))
                .itemOutputs(CustomItemList.EngineeringProcessorEssentiaPulsatingCore.get(1L)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        // AE2 Illuminated Panel Assembler Recipe

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Cover_Screen.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Glowstone, 2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1L))
                .itemOutputs(GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1L, 180))
                .fluidInputs(Materials.SolderingAlloy.getMolten(144L)).duration(5 * SECONDS).eut(4)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.glass_pane, 1, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 2L))
                .itemOutputs(GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 39))
                .duration(5 * SECONDS).eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hatch_Output_Bus_HV.get(1L),
                        GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1L, 440),
                        GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 4L, 30),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Hatch_Output_Bus_ME.get(1)).duration(15 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hatch_Output_HV.get(1L),
                        GTModHandler.getModItem(AE2FluidCraft.ID, "part_fluid_interface", 1L),
                        GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 4L, 30),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Hatch_Output_ME.get(1)).duration(15 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        // Stocking Input Bus (ME)
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hatch_Input_Bus_HV.get(1L),
                        GTModHandler.getModItem(AppliedEnergistics2.ID, "tile.BlockInterface", 1L),
                        // Acceleration Card
                        GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 4L, 30),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Hatch_Input_Bus_ME.get(1)).duration(15 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        // Stocking Input Hatch (ME)
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hatch_Input_Multi_2x2_UV.get(4L),
                        GTModHandler.getModItem(AE2FluidCraft.ID, "fluid_interface", 1L),
                        ItemList.Circuit_Chip_NeuroCPU.get(1L),
                        ItemList.Electric_Pump_UV.get(1L),
                        // 4096k Me Fluid Storage Component
                        GTModHandler.getModItem(AE2FluidCraft.ID, "fluid_part", 4L, 6),
                        // Hyper-Acceleration Card
                        GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 56))
                .itemOutputs(ItemList.Hatch_Input_ME.get(1)).duration(15 * SECONDS).eut(TierEU.RECIPE_UV)
                .addTo(assemblerRecipes);

        // Cell Workbench
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23),
                        ItemList.Cover_Screen.get(1),
                        getModItem(Minecraft.ID, "crafting_table", 1),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockCellWorkbench", 1))
                .fluidInputs(Materials.Titanium.getMolten(36L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        // Controller
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 2L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 2, 24),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockFluix", 1, 0),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockController", 1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Energy Cells
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 2L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Fluix, 1),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockFluix", 1, 0),
                        GTOreDictUnificator.get(OrePrefixes.cableGt08, Materials.Aluminium, 1),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 2L),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 5, 0),
                        ItemList.Battery_Buffer_4by4_EV.get(1L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockDenseEnergyCell", 1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Void Cell
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 3L),
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.EnderEye, 1),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemVoidStorageCell", 1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Quantum Link
        GTValues.RA.stdBuilder()
                .itemInputs(
                        getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 2, 22),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockQuantumRing", 1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 4, 9),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGlass", 1, 0),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockQuantumLinkChamber", 1))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Spatial Pylon
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 2, 16),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Fluix, 2L),
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.Fluix, 1L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockSpatialPylon", 1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Spatial IO
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 2, 16),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockSpatialPylon", 1),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockIOPort", 1),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockSpatialIOPort", 1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // ME IO Port
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 3L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 3, 16),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockDrive", 2, 0),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockIOPort", 1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // ME Chest
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 4L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 2, 16),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 2L),
                        getModItem(IronChests.ID, "BlockIronChest", 1, 4),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockChest", 1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        // ME Drive
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 2, 16),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockChest", 1),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockDrive", 1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Interface
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 2, 16),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 44),
                        ItemList.Casing_EV.get(1L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockInterface", 1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Crafting Unit
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 2L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Formation Core
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.CertusQuartz, 4L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 4, 22),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 12),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 2, 43)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Annihilation Core
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.NetherQuartz, 4L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 4, 22),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 12),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 2, 44)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Wireless Receiver
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.EnderEye, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 2L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 2, 140),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 9),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 41)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Molecular Assembler
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Machine_EV_Assembler.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4L),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 44),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockMolecularAssembler", 1))
                .fluidInputs(Materials.Glass.getMolten(288L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        // Basic Card
        GTValues.RA.stdBuilder().itemInputs(
                // Calculation Processor
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 2),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 3),
                GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 25)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        // Advanced Card
        GTValues.RA.stdBuilder().itemInputs(
                // Calculation Processor
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Platinum, 2),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 3),
                GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 28)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        // Acceleration Card
        GTValues.RA.stdBuilder().itemInputs(
                // Advanced Card
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 28),
                // Logic Processor
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22),
                // Engineering Processor
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24),
                // Fluix Crystal
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 7),
                GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 30)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        // Hyper-Acceleration Card
        GTValues.RA.stdBuilder().itemInputs(
                // Advanced Card
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 28),
                // Engineering Processor
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24),
                CustomItemList.MysteriousCrystal.get(1L),
                ItemList.AcceleratorLuV.get(1L),
                GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 56)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        // Advanced Blocking Card
        GTValues.RA.stdBuilder().itemInputs(
                // Advanced Card
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 28),
                // ME Storage Bus
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 220),
                // ME Level Emitter
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 280),
                GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 63)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        // Pattern Capacity Card
        GTValues.RA.stdBuilder().itemInputs(
                // Advanced Card
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 28),
                // 16k ME Storage Component
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 2, 37),
                // ME Interface
                getModItem(AppliedEnergistics2.ID, "tile.BlockInterface", 1, 0),
                GTUtility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 54)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        // ME interface (flat version)
        GTValues.RA.stdBuilder().itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4),
                // Fluix Cable
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 2, 16),
                // Formation Core
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43),
                // Annihilation Core
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 44),
                ItemList.Casing_EV.get(1L),
                GTUtility.getIntegratedCircuit(3))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 440)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        if (AE2FluidCraft.isModLoaded()) {
            // Dual Interface
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 2L),
                            getModItem(AppliedEnergistics2.ID, "tile.BlockInterface", 1),
                            NHItemList.EngineeringProcessorFluidDiamondCore.getIS(2),
                            GTUtility.getIntegratedCircuit(2))
                    .itemOutputs(getModItem(AE2FluidCraft.ID, "fluid_interface", 1, 0)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

            // Dual Interface (flat version)
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 2L),
                            getModItem(AppliedEnergistics2.ID, "tile.BlockInterface", 1),
                            NHItemList.EngineeringProcessorFluidDiamondCore.getIS(2),
                            GTUtility.getIntegratedCircuit(3))
                    .itemOutputs(getModItem(AE2FluidCraft.ID, "part_fluid_interface", 1, 0)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

            // Fluid Storage Housing
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1L),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 2L),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1L),
                            GTOreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2L),
                            GTUtility.getIntegratedCircuit(3))
                    .itemOutputs(getModItem(AE2FluidCraft.ID, "fluid_storage_housing", 1, 0)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1L),
                            NITINOL_60.getPlate(2),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1L),
                            GTOreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2L),
                            GTUtility.getIntegratedCircuit(3))
                    .itemOutputs(getModItem(AE2FluidCraft.ID, "fluid_storage_housing", 1, 1)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
            // Multi Fluid Storage Housing
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1L),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 2L),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1L),
                            GTOreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2L),
                            GTUtility.getIntegratedCircuit(3))
                    .itemOutputs(getModItem(AE2FluidCraft.ID, "fluid_storage_housing", 1, 2)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1L),
                            MaterialsAlloy.ZERON_100.getPlate(2),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1L),
                            GTOreDictUnificator.get(OrePrefixes.screw, Materials.CertusQuartz, 2L),
                            GTUtility.getIntegratedCircuit(3))
                    .itemOutputs(getModItem(AE2FluidCraft.ID, "fluid_storage_housing", 1, 3)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        }
        if (AE2Stuff.isModLoaded()) {
            // Wireless Connector
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 2L),
                            getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 4, 12),
                            getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 2, 24),
                            getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 41),
                            GTUtility.getIntegratedCircuit(2))
                    .itemOutputs(getModItem(AE2Stuff.ID, "Wireless", 1)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                    .addTo(assemblerRecipes);
        }

        if (TinkerConstruct.isModLoaded()) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 1L, 0),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1L),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1L),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 2L))
                    .itemOutputs(GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 39))
                    .duration(5 * SECONDS).eut(16).addTo(assemblerRecipes);

        }
    }

    private void makeTinkersConstructRecipes() {
        if (!TinkerConstruct.isModLoaded()) {
            return;
        }

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Stone, 2L),
                        GTModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Tin, 1L))
                .itemOutputs(
                        GTModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowercobblestone", 1L))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L),
                        GTModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 1L))
                .itemOutputs(GTModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowerwood", 1L))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L),
                        GTModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 1L))
                .itemOutputs(GTModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowerwood", 1L))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.SandStoneRod.get(2L),
                        GTModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Cupronickel, 1L))
                .itemOutputs(
                        GTModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowersandstone", 1L))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(ForgeMicroblocks.ID, "stoneRod", 2L),
                        GTModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Nickel, 1L))
                .itemOutputs(GTModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowerstone", 1L))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.NetherQuartz, 2L),
                        GTModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Silver, 1L))
                .itemOutputs(GTModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowerquartz", 1L))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 2L),
                        GTModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Electrum, 1L))
                .itemOutputs(GTModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepoweriron", 1L))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Gold, 2L),
                        GTModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Aluminium, 1L))
                .itemOutputs(GTModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowergold", 1L))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Emerald, 2L),
                        GTModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Nichrome, 1L))
                .itemOutputs(
                        GTModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepoweremerald", 1L))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Diamond, 2L),
                        GTModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Platinum, 1L))
                .itemOutputs(
                        GTModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowerdiamond", 1L))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        // Assembler

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(TinkerConstruct.ID, "slime.gel", 1L, 1),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "blockITNT", 1L, 0),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(TinkerConstruct.ID, "explosive.slime", 1L, 0))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(TinkerConstruct.ID, "slime.gel", 1L, 0),
                        GTModHandler.getModItem(TinkerConstruct.ID, "explosive.slime", 1L, 0),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(TinkerConstruct.ID, "explosive.slime", 1L, 2))
                .duration(30 * SECONDS).eut(64).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Aluminium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 4L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(TinkerConstruct.ID, "heartCanister", 1L, 0)).duration(2 * MINUTES)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

    }

    private void makeForestryRecipes() {
        if (!Forestry.isModLoaded()) {
            return;
        }

        ItemStack alveary = GTModHandler.getModItem(Forestry.ID, "alveary", 1L, 0);

        // Impregnated Frame
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(Railcraft.ID, "slab", 3L, 38),
                        GTModHandler.getModItem(PamsHarvestCraft.ID, "wovencottonItem", 1L),
                        GTModHandler.getModItem(Forestry.ID, "oakStick", 5L, 0))
                .itemOutputs(GTModHandler.getModItem(Forestry.ID, "frameImpregnated", 1L, 0))
                .fluidInputs(Materials.SeedOil.getFluid(250L)).duration(60 * SECONDS).eut(64).addTo(assemblerRecipes);

        // Apiary
        List<ItemStack> fence = OreDictionary.getOres("fenceWood");
        for (ItemStack stack : fence) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTModHandler.getModItem(Forestry.ID, "frameImpregnated", 1L, 0),
                            GTOreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 2L),
                            GTOreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 2L),
                            GTModHandler.getModItem(Forestry.ID, "beeCombs", 1L, W),
                            GTModHandler.getModItem(Forestry.ID, "apiculture", 2L, 2),
                            stack.splitStack(2))
                    .itemOutputs(GTModHandler.getModItem(Forestry.ID, "apiculture", 1L, 0))
                    .fluidInputs(Materials.SeedOil.getFluid(1000L)).duration(60 * SECONDS).eut(64)
                    .addTo(assemblerRecipes);
        }

        // Scented Paneling

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 2L),
                        GTModHandler.getModItem(Forestry.ID, "royalJelly", 1L, 0),
                        GTModHandler.getModItem(Forestry.ID, "oakStick", 3L, 0),
                        GTModHandler.getModItem(Forestry.ID, "beeswax", 2L, 0),
                        GTModHandler.getModItem(Forestry.ID, "pollen", 1L, W))
                .itemOutputs(GTModHandler.getModItem(Forestry.ID, "craftingMaterial", 1L, 6))
                .fluidInputs(Materials.Honey.getFluid(1000L)).duration(60 * SECONDS).eut(64).addTo(assemblerRecipes);
        // Swarmer

        GTValues.RA.stdBuilder()
                .itemInputs(
                        alveary,
                        GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 5),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.RoseGold, 2L),
                        GTModHandler.getModItem(Forestry.ID, "royalJelly", 2L, 0),
                        GTModHandler.getModItem(Forestry.ID, "frameProven", 1L, 0))
                .itemOutputs(GTModHandler.getModItem(Forestry.ID, "alveary", 1L, 2))
                .fluidInputs(Materials.Honey.getFluid(5000L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Alveary Fan

        GTValues.RA.stdBuilder()
                .itemInputs(
                        alveary,
                        GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 11),
                        CustomItemList.SteelBars.get(3L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1L),
                        ItemList.Electric_Motor_MV.get(1L))
                .itemOutputs(GTModHandler.getModItem(Forestry.ID, "alveary", 1L, 3))
                .fluidInputs(Materials.Honey.getFluid(5000L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Alveary Heater

        GTValues.RA.stdBuilder()
                .itemInputs(
                        alveary,
                        GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 7),
                        CustomItemList.SteelBars.get(1L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemRecipePart", 3L),
                        ItemList.Electric_Motor_MV.get(1L))
                .itemOutputs(GTModHandler.getModItem(Forestry.ID, "alveary", 1L, 4))
                .fluidInputs(Materials.Honey.getFluid(5000L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Alveary Hygroregulator

        GTValues.RA.stdBuilder()
                .itemInputs(
                        alveary,
                        GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 6),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                        GTModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 2L, 0),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1L))
                .itemOutputs(GTModHandler.getModItem(Forestry.ID, "alveary", 1L, 5))
                .fluidInputs(Materials.Honey.getFluid(5000L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Alveary Stabiliser

        GTValues.RA.stdBuilder()
                .itemInputs(
                        alveary,
                        GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 4),
                        NHItemList.ChargedCertusQuartzPlate.getIS(2),
                        ItemList.Component_Filter.get(2L),
                        GTModHandler.getModItem(Forestry.ID, "royalJelly", 1L, 0))
                .itemOutputs(GTModHandler.getModItem(Forestry.ID, "alveary", 1L, 6))
                .fluidInputs(Materials.Honey.getFluid(5000L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Alveary Sieve

        GTValues.RA.stdBuilder()
                .itemInputs(
                        alveary,
                        GTModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 9),
                        GTModHandler.getModItem(Forestry.ID, "craftingMaterial", 4L, 3),
                        GTModHandler.getModItem(Forestry.ID, "pollenFertile", 1L, GTValues.W))
                .itemOutputs(GTModHandler.getModItem(Forestry.ID, "alveary", 1L, 7))
                .fluidInputs(Materials.Honey.getFluid(5000L)).duration(60 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

    }

    private void makeOpenComputersRecipes() {
        if (!OpenComputers.isModLoaded()) {
            return;
        }
        // cable

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
                        GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.RedstoneAlloy, 1),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "cable", 1L, 0)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
                        GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Emerald, 1),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "cable", 1L, 0)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 9),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.RedstoneAlloy, 1),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "cable", 9L, 0)).duration(90 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 9),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Emerald, 1),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "cable", 9L, 0)).duration(90 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // keyboard

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone_button, 64),
                        new ItemStack(Blocks.stone_button, 40),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1),
                        GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 1L))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "keyboard", 1L, 0)).duration(10 * SECONDS)
                .eut(64).addTo(assemblerRecipes);
        // case 1

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_MV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 2),
                        GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "case1", 1, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // case 2

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_HV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 2),
                        GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "case2", 1, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(256).addTo(assemblerRecipes);
        // case 3

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_EV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 2),
                        ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 2),
                        GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "case3", 1, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);
        // micro case 1

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_MV.get(1L),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 82))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // mirco case 2

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_HV.get(1L),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                        GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 86))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(256).addTo(assemblerRecipes);
        // drone case 1

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 82),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 71),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 4L),
                        GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 1L),
                        ItemList.Electric_Motor_HV.get(4L))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1, 83))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(256).addTo(assemblerRecipes);
        // drone case 2

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 86),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 72),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Titanium, 4L),
                        GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 1L),
                        ItemList.Electric_Motor_EV.get(4L))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1, 87))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);
        // Card

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 1),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Gold, 1),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 33)).duration(10 * SECONDS).eut(64)
                .addTo(assemblerRecipes);
        // floppy

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 19),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Plastic, 4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 4)).duration(10 * SECONDS).eut(64)
                .addTo(assemblerRecipes);
        // HDD 1

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 19),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 4L, 1),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 5)).duration(15 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        // HDD 2

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 4L, 19),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 4L, 2),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 6)).duration(15 * SECONDS).eut(256)
                .addTo(assemblerRecipes);
        // HDD 3

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 2L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 8L, 19),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 4L, 38),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 7)).duration(15 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // disk

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.lens, Materials.Glass, 1),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Aluminium, 4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 19))
                .fluidInputs(Materials.Cobalt.getMolten(36L)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // rack

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "relay", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "powerDistributor", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                        ItemList.Circuit_Board_Plastic_Advanced.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 4L),
                        GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 4L),
                        CustomItemList.SteelBars.get(2L))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "rack", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(256).addTo(assemblerRecipes);
        // adapter block

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_LV.get(1L),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "adapter", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // assembler

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_HV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        ItemList.Robot_Arm_LV.get(3L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "assembler", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(256).addTo(assemblerRecipes);
        // disassembler

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_HV.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        ItemList.Robot_Arm_HV.get(2L))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "disassembler", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(256).addTo(assemblerRecipes);
        // capacitor

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_LV.get(2L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "blockElectric", 1L, 7),
                        ItemList.Circuit_Board_Plastic_Advanced.get(2L),
                        ItemList.Circuit_Parts_Transistor.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "capacitor", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // charger

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_HV.get(1L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "blockChargepad", 2L, 2),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "charger", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(256).addTo(assemblerRecipes);
        // diskDrive

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_LV.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 3L, 25),
                        ItemList.Electric_Motor_MV.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.springSmall, Materials.Copper, 1),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1),
                        GTOreDictUnificator.get(OrePrefixes.lens, Materials.Glass, 1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "diskDrive", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // diskDriver

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "diskDrive", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        CustomItemList.SteelBars.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 4),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 109))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);
        // geolyzer

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_MV.get(1L),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 0),
                        ItemList.Sensor_MV.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderEye, 2),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "geolyzer", 1L, 0)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        // hologram1

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Plastic_Advanced.get(2L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 2),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 2),
                        GTOreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 1),
                        GTUtility.getIntegratedCircuit(10))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "hologram1", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // hologram2

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Epoxy_Advanced.get(2L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 2),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 4),
                        GTOreDictUnificator.get(OrePrefixes.lens, Materials.Emerald, 1),
                        GTOreDictUnificator.get(OrePrefixes.lens, Materials.Ruby, 1),
                        GTOreDictUnificator.get(OrePrefixes.lens, Materials.Sapphire, 1),
                        GTUtility.getIntegratedCircuit(10))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "hologram2", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(256).addTo(assemblerRecipes);
        // motionSensor

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 42),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 2),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        new ItemStack(Blocks.daylight_detector, 2, 0),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "motionSensor", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // netSplitter

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_LV.get(1L),
                        ItemList.Circuit_Board_Plastic_Advanced.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 4),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 3L, 0),
                        ItemList.Electric_Piston_LV.get(1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "netSplitter", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(64).addTo(assemblerRecipes);
        // printer

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Machine_HV_Printer.get(1L),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 4),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "printer", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(256).addTo(assemblerRecipes);
        // powerConverter

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Transformer_HV_MV.get(1L),
                        ItemList.Circuit_Board_Plastic_Advanced.get(2L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "powerConverter", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(256).addTo(assemblerRecipes);
        // powerDistributor

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_MV.get(1L),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "blockElectric", 1L, 7),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 2))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "powerDistributor", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(256).addTo(assemblerRecipes);
        // raid

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "diskDrive", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 43),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 1),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 4),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "raid", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(256).addTo(assemblerRecipes);
        // redstone

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_MV.get(1L),
                        ItemList.Circuit_Board_Plastic_Advanced.get(2L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Redstone, 2),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 66),
                        ItemList.Cover_ActivityDetector.get(1L))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "redstone", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // relay

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_MV.get(1L),
                        ItemList.Circuit_Board_Plastic_Advanced.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 2),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "relay", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // transposer

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Automation_ChestBuffer_LV.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 61),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 77),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 2),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "transposer", 2L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(20 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // waypoint

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_MV.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 48),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        ItemList.Circuit_Parts_Transistor.get(2L),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "waypoint", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(10 * SECONDS).eut(64).addTo(assemblerRecipes);
        // cartridge empty

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        ItemList.Circuit_Parts_Transistor.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.pipeTiny, Materials.Plastic, 8),
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 8L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 94))
                .fluidInputs(Materials.Plastic.getMolten(36L)).duration(10 * SECONDS).eut(64).addTo(assemblerRecipes);
        // cartridge full

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 94),
                        ItemList.Color_00.get(1L),
                        ItemList.Color_06.get(1L),
                        ItemList.Color_11.get(1L),
                        ItemList.Color_13.get(1L))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 95))
                .fluidInputs(Materials.Water.getFluid(1000L)).duration(10 * SECONDS).eut(64).addTo(assemblerRecipes);
        // Interweb

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 1),
                        new ItemStack(Items.string, 8, 0),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 48))
                .fluidInputs(Materials.Glue.getFluid(576L)).duration(10 * SECONDS).eut(64).addTo(assemblerRecipes);
        // Linked Card

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 13),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 28),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 48),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 51))
                .fluidInputs(FluidRegistry.getFluidStack("ender", 250)).duration(20 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);
        // Manual

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Items.book, 1, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 98))
                .fluidInputs(Materials.Glue.getFluid(144L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Nano Machine

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "capacitor", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 2),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 42),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 18),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 9L, 96))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 107))
                .fluidInputs(Materials.Plastic.getMolten(144L)).duration(20 * SECONDS).eut(256).addTo(assemblerRecipes);
        // Server Tier 1

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "case1", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 3),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 45))
                .fluidInputs(Materials.Plastic.getMolten(144L)).duration(20 * SECONDS).eut(256).addTo(assemblerRecipes);
        // Server Tier 2

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "case2", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 38),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 46))
                .fluidInputs(Materials.Plastic.getMolten(144L)).duration(20 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);
        // Server Tier 3

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "case3", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 39),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 40))
                .fluidInputs(Materials.Plastic.getMolten(144L)).duration(20 * SECONDS).eut(1024)
                .addTo(assemblerRecipes);
        // Tablet Case Tier 1

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 70),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Electrum, 4L))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 74))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(20 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Tablet Case Tier 2

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 72),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.Electrum, 4L))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 92))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(20 * SECONDS).eut(256).addTo(assemblerRecipes);
        // remote Monitor

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 34),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                        GTModHandler.getModItem(OpenComputers.ID, "keyboard", 1L, 0),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 41))
                .fluidInputs(Materials.Plastic.getMolten(144L)).duration(20 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Terminal Server

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 3L, 13),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 4L),
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 16L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 108))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(20 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);
        // Open Printers
        // Printer

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Machine_MV_Printer.get(1L),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenPrinters.ID, "openprinter.printer", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Shredder

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Machine_MV_Macerator.get(1L),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenPrinters.ID, "openprinter.shredder", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Paper Roll

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Items.paper, 64, 0),
                        new ItemStack(Items.paper, 64, 0),
                        new ItemStack(Items.paper, 64, 0),
                        new ItemStack(Items.paper, 64, 0),
                        GTUtility.getIntegratedCircuit(12))
                .itemOutputs(GTModHandler.getModItem(OpenPrinters.ID, "openprinter.printerPaperRoll", 1L, 0))
                .duration(10 * SECONDS).eut(64).addTo(assemblerRecipes);
        // Black Ink Cartridge

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 94),
                        ItemList.Color_00.get(1L),
                        ItemList.Color_00.get(1L),
                        ItemList.Color_00.get(1L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenPrinters.ID, "openprinter.printerInkBlack", 1L, W))
                .fluidInputs(Materials.Water.getFluid(1000L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenPrinters.ID, "openprinter.printerInkBlack", 1L, W),
                        ItemList.Color_00.get(1L),
                        ItemList.Color_00.get(1L),
                        ItemList.Color_00.get(1L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenPrinters.ID, "openprinter.printerInkBlack", 1L, W))
                .fluidInputs(Materials.Water.getFluid(1000L)).duration(7 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Color Ink Cartridge

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 94),
                        ItemList.Color_01.get(1L),
                        ItemList.Color_02.get(1L),
                        ItemList.Color_04.get(1L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenPrinters.ID, "openprinter.printerInkColor", 1L, W))
                .fluidInputs(Materials.Water.getFluid(1000L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenPrinters.ID, "openprinter.printerInkColor", 1L, W),
                        ItemList.Color_01.get(1L),
                        ItemList.Color_02.get(1L),
                        ItemList.Color_04.get(1L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenPrinters.ID, "openprinter.printerInkColor", 1L, W))
                .fluidInputs(Materials.Water.getFluid(1000L)).duration(7 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Open Security
        // Magnetic Card Reader

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        ItemList.Circuit_Parts_Transistor.get(2L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "magreader", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(256).addTo(assemblerRecipes);
        // RFID Reader

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.rfidReaderCard", 1L, 0),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "rfidreader", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Card writer

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 1),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        ItemList.Circuit_Parts_Transistor.get(2L))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "rfidwriter", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Alarm

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.noteblock, 1, 0),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "alarm", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Entity Detector

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 1),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "entitydetector", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Door Controler

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        ItemList.Circuit_Parts_Transistor.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 4L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "doorcontroller", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Data Block

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 104),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 1L, 0),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.DataBlock", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // swtichable Hub

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "relay", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 4L, 0),
                        ItemList.Circuit_Parts_Transistor.get(2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.SwitchableHub", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // kvm Hub

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "relay", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 4L, 0),
                        ItemList.Circuit_Parts_Transistor.get(2L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.BlockKVM", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // energy turret

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenModularTurrets.ID, "laserTurret", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 4L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 4L, 0),
                        ItemList.Circuit_Parts_Transistor.get(2L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "energyTurretBlock", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // keypad

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "keyboard", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        ItemList.Circuit_Parts_Transistor.get(2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "keypadLock", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // biometric reader

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.lens, Materials.Glass, 4L),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        ItemList.Circuit_Parts_Transistor.get(2L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "biometricScanner", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // magnetic stipe card

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 4L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.IronMagnetic, 2L),
                        ItemList.Circuit_Parts_Transistor.get(1L),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.magCard", 2L, 0))
                .fluidInputs(Materials.Glue.getFluid(144L)).duration(7 * SECONDS + 10 * TICKS).eut(64)
                .addTo(assemblerRecipes);
        // RFID Card

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 4L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.IronMagnetic, 2L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        ItemList.Circuit_Parts_Transistor.get(1L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.rfidCard", 2L, 0))
                .fluidInputs(Materials.Glue.getFluid(144L)).duration(7 * SECONDS + 10 * TICKS).eut(64)
                .addTo(assemblerRecipes);
        // RFID Reader Card

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 1),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.rfidReaderCard", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Secure Network Card

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 11),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.secureNetworkCard", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(256).addTo(assemblerRecipes);
        // Secure Door

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Items.iron_door, 1),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        ItemList.Circuit_Parts_Transistor.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 4L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.securityDoor", 1L, 0))
                .duration(15 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        // Secure Private Door

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Items.iron_door, 1),
                        GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        ItemList.Circuit_Parts_Transistor.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 4L),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.securityDoorPrivate", 1L, 0))
                .duration(15 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        // Damage Upgrade

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        new ItemStack(Blocks.tnt, 1, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.damageUpgrade", 1L, 0))
                .fluidInputs(Materials.Redstone.getMolten(72L)).duration(15 * SECONDS).eut(256).addTo(assemblerRecipes);
        // Cooldown upgrade

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getIC2Item("reactorCoolantSimple", 1L, 1),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.cooldownUpgrade", 1L, 0))
                .fluidInputs(Materials.Redstone.getMolten(72L)).duration(15 * SECONDS).eut(256).addTo(assemblerRecipes);
        // Energy Upgrade

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 63),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.energyUpgrade", 1L, 0))
                .fluidInputs(Materials.Redstone.getMolten(72L)).duration(15 * SECONDS).eut(256).addTo(assemblerRecipes);
        // Movement Upgrade

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        ItemList.Electric_Piston_MV.get(1L),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenSecurity.ID, "opensecurity.movementUpgrade", 1L, 0))
                .fluidInputs(Materials.Redstone.getMolten(72L)).duration(15 * SECONDS).eut(256).addTo(assemblerRecipes);
        // OpenGlasses Terminal

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "geolyzer", 1L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 43),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 38),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenGlasses.ID, "openglassesterminal", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(144L)).duration(15 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);
        // Open Glasses

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "screen3", 2L, 0),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 10),
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(OpenGlasses.ID, "openglasses", 1L, W))
                .fluidInputs(Materials.Plastic.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

    }

    private void makeJabbaRecipes() {
        if (!JABBA.isModLoaded()) {
            return;
        }
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 2),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 0)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Copper, 2),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Copper, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 1)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 2),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 2)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 2),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Bronze, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 3)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 4)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 5)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 2),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 6)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 2),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 7)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 2),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 8)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        LuVTierMaterial.get(OrePrefixes.plate, 2),
                        LuVTierMaterial.get(OrePrefixes.stick, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 9)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 2),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 10)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Osmium, 2),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Osmium, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 11)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 2),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Neutronium, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 12)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 2),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.BlackPlutonium, 2),
                        GTUtility.getIntegratedCircuit(13))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 13)).duration(10 * SECONDS)
                .eut(16).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "upgradeCore", 1L, 0),
                        GTModHandler.getModItem(JABBA.ID, "upgradeCore", 1L, 3),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeSide", 1L, 1)).duration(60 * SECONDS).eut(16)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(JABBA.ID, "upgradeCore", 1L, 0),
                        GTModHandler.getModItem(JABBA.ID, "upgradeCore", 1L, 2),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(GTModHandler.getModItem(JABBA.ID, "upgradeSide", 1L, 2)).duration(60 * SECONDS).eut(16)
                .addTo(assemblerRecipes);
    }

    private void makeZTonesRecipes() {
        if (!ZTones.isModLoaded()) {
            return;
        }

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone_slab, 4),
                        new ItemStack(Blocks.stone, 1),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(ZTones.ID, "stoneTile", 8L, 0)).duration(8 * SECONDS).eut(4)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.glass, 4),
                        new ItemStack(Items.dye, 1, W),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(ZTones.ID, "auroraBlock", 8L, 0)).duration(8 * SECONDS).eut(4)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.sand, 4, W),
                        new ItemStack(Blocks.dirt, 4, W),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(ZTones.ID, "cleanDirt", 8L, 0))
                .fluidInputs(Materials.SeedOil.getFluid(5L)).duration(8 * SECONDS).eut(4).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(new ItemStack(Blocks.stone_pressure_plate, 1), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTModHandler.getModItem(ZTones.ID, "booster", 1L, 0))
                .fluidInputs(Materials.Blaze.getMolten(8L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        // This replaces the type of block, and the item used to make it for the 0-15 meta, and the 21+12 different
        // versions of ztones blocks (glaxx is separate)
        // Normal crafting recipes are not affected, so they might be crafted differently
        String[] blockName = { "agon", "azur", "bitt", "cray", "fort", "iszm", "jelt", "korp", "kryp", "lair", "lave",
                "mint", "myst", "reds", "reed", "roen", "sols", "sync", "tank", "vect", "vena" };
        String[] zblockName = { "zane", "zech", "zest", "zeta", "zion", "zkul", "zoea", "zome", "zone", "zorg", "ztyl",
                "zyth" };

        Map<String, ItemStack> itemMap = new HashMap<>();
        itemMap.put(blockName[0], ItemList.Color_07.get(1)); // agon
        itemMap.put(blockName[1], ItemList.Color_04.get(1)); // azur
        itemMap.put(blockName[2], new ItemStack(Blocks.wool, 1, 0)); // bitt
        itemMap.put(blockName[3], new ItemStack(Blocks.hardened_clay, 1, 0)); // cray
        itemMap.put(blockName[4], ItemList.Color_03.get(1)); // fort
        itemMap.put(blockName[5], ItemList.Color_08.get(1)); // iszm
        itemMap.put(blockName[6], new ItemStack(Items.gold_ingot, 1, 0));// jelt
        itemMap.put(blockName[7], new ItemStack(Blocks.obsidian, 1, 0)); // korp
        itemMap.put(blockName[8], new ItemStack(Blocks.soul_sand, 1, 0));// kryp
        itemMap.put(blockName[9], new ItemStack(Blocks.netherrack, 1, 0));// lair
        itemMap.put(blockName[10], new ItemStack(Blocks.ice, 1, 0));// lave
        itemMap.put(blockName[11], new ItemStack(Items.slime_ball, 1, 0));// mint
        itemMap.put(blockName[12], new ItemStack(Blocks.brown_mushroom, 1, 0));// myst
        itemMap.put(blockName[13], new ItemStack(Items.redstone, 1, 0));// reds
        itemMap.put(blockName[14], new ItemStack(Items.reeds, 1, 0));// reed
        itemMap.put(blockName[15], new ItemStack(Blocks.sandstone, 1, 0));// roen
        itemMap.put(blockName[16], new ItemStack(Items.blaze_powder, 1, 0));// sols
        itemMap.put(blockName[17], new ItemStack(Items.emerald, 1, 0));// sync
        itemMap.put(blockName[18], new ItemStack(Items.iron_ingot, 1, 0));// tank
        itemMap.put(blockName[19], new ItemStack(Items.ghast_tear, 1, 0));// vect
        itemMap.put(blockName[20], new ItemStack(Items.ender_pearl, 1, 0));// vena
        Map<String, ItemStack> zitemMap = new HashMap<>();
        zitemMap.put(zblockName[0], ItemList.Color_00.get(1)); // zane
        zitemMap.put(zblockName[1], ItemList.Color_01.get(1)); // zech
        zitemMap.put(zblockName[2], ItemList.Color_02.get(1)); // zest
        zitemMap.put(zblockName[3], ItemList.Color_05.get(1)); // zeta
        zitemMap.put(zblockName[4], ItemList.Color_06.get(1)); // zion
        zitemMap.put(zblockName[5], ItemList.Color_09.get(1)); // zkul
        zitemMap.put(zblockName[6], ItemList.Color_10.get(1)); // zoea
        zitemMap.put(zblockName[7], ItemList.Color_11.get(1)); // zome
        zitemMap.put(zblockName[8], ItemList.Color_12.get(1)); // zone
        zitemMap.put(zblockName[9], ItemList.Color_13.get(1)); // zorg
        zitemMap.put(zblockName[10], ItemList.Color_14.get(1)); // ztyl
        zitemMap.put(zblockName[11], ItemList.Color_15.get(1)); // zyth

        final int ztoneVariants = 16;

        final ItemStack ztoneStone = GTModHandler.getModItem(ZTones.ID, "stoneTile", 4L, 0);
        final ItemStack ztoneAurora = GTModHandler.getModItem(ZTones.ID, "auroraBlock", 4L, 0);

        for (String name : blockName) {
            for (int i = 0; i < ztoneVariants; i++) {
                GTValues.RA.stdBuilder()
                        .itemInputs(ztoneStone, itemMap.get(name), GTUtility.getIntegratedCircuit(i == 0 ? 24 : i))
                        .itemOutputs(GTModHandler.getModItem(ZTones.ID, "tile." + name + "Block", 8L, i))
                        .duration(10 * SECONDS).eut(16).addTo(assemblerRecipes);
            }
        }
        for (String name : zblockName) {
            for (int i = 0; i < ztoneVariants; i++) {
                GTValues.RA.stdBuilder()
                        .itemInputs(ztoneAurora, zitemMap.get(name), GTUtility.getIntegratedCircuit(i == 0 ? 24 : i))
                        .itemOutputs(GTModHandler.getModItem(ZTones.ID, "tile." + name + "Block", 8L, i))
                        .duration(10 * SECONDS).eut(16).addTo(assemblerRecipes);
            }
        }
        for (int i = 0; i < ztoneVariants; i++) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTModHandler.getModItem(ZTones.ID, "auroraBlock", 1L, 0),
                            new ItemStack(Blocks.glass, 4, 0),
                            GTUtility.getIntegratedCircuit(i == 0 ? 24 : i))
                    .itemOutputs(GTModHandler.getModItem(ZTones.ID, "tile.glaxx", 8L, i)).duration(10 * SECONDS).eut(16)
                    .addTo(assemblerRecipes);
        }
    }

    private void makeGalacticraftRocketRecipes() {
        if (!GalaxySpace.isModLoaded()) {
            return;
        }

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIVBase, 30L),
                        GTOreDictUnificator.get(OrePrefixes.pipeTiny, MaterialsUEVplus.TranscendentMetal, 20L),
                        ItemList.Electric_Pump_UIV.get(1L),
                        GTUtility.getIntegratedCircuit(9))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 30L))
                .fluidInputs(new FluidStack(FluidRegistry.getFluid("liquid helium"), 34000))
                .duration(2 * MINUTES + 40 * SECONDS).eut(TierEU.RECIPE_UIV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 7),
                        GTModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 3))
                .itemOutputs(CustomItemList.LeadOriharukonPlate.get(2L))
                .fluidInputs(new FluidStack(FluidRegistry.getFluid("ic2coolant"), 1000)).duration(15 * SECONDS)
                .eut(TierEU.RECIPE_UV).addTo(assemblerRecipes);

        // rocket parts

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualBronze", 1L, 0),
                        GTModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualAluminium", 1L, 0),
                        GTModHandler.getModItem(GalaxySpace.ID, "item.CompressedCoal", 1L, 0),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.RawSDHCAlloy.get(1L)).fluidInputs(Materials.StainlessSteel.getMolten(72L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualBronze", 1L, 0),
                        GTModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualAluminium", 1L, 0),
                        GTModHandler.getModItem(GalaxySpace.ID, "item.ModuleSmallCanister", 1L, 0),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(GTModHandler.getModItem(GalaxySpace.ID, "item.ModuleSmallFuelCanister", 1L, 0))
                .fluidInputs(Materials.StainlessSteel.getMolten(72L)).duration(20 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(GalaxySpace.ID, "item.ModuleSmallFuelCanister", 1L, 0),
                        CustomItemList.TitaniumDualCompressedPlates.get(1),
                        CustomItemList.DeshDualCompressedPlates.get(1),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(CustomItemList.MediumFuelCanister.get(1)).fluidInputs(Materials.Titanium.getMolten(72L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.MediumFuelCanister.get(1),
                        CustomItemList.QuantinumDualCompressedPlates.get(1),
                        CustomItemList.IceDualCompressedPlates.get(1),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(CustomItemList.LargeFuelCanister.get(1L)).fluidInputs(Materials.Chrome.getMolten(72L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.LargeFuelCanister.get(1),
                        CustomItemList.MytrylDualCompressedPlates.get(1),
                        CustomItemList.MysteriousCrystalDualCompressedPlates.get(1),
                        GTUtility.getIntegratedCircuit(2))
                .itemOutputs(CustomItemList.ExtraLargeFuelCanister.get(1L))
                .fluidInputs(Materials.Iridium.getMolten(72L)).duration(20 * TICKS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);
        // Engine

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 2L, 0),
                        GTModHandler.getModItem(GalacticraftCore.ID, "item.heavyPlating", 4L, 0),
                        GTModHandler.getModItem(GalacticraftCore.ID, "item.oilCanisterPartial", 1L, 1001),
                        ItemList.Casing_Firebox_Steel.get(1L),
                        ItemList.Cover_ActivityDetector.get(1L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(GTModHandler.getModItem(GalacticraftCore.ID, "item.engine", 1L, 0)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(GalacticraftCore.ID, "item.engine", 2L, 1),
                        GTModHandler.getModItem(GalacticraftCore.ID, "item.engine", 2L, 0),
                        GTModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 5L, 0),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(GTModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 1L, 1))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Tier2Booster.get(2),
                        GTModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 2L, 1),
                        CustomItemList.HeavyDutyPlateTier5.get(5),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.HeavyDutyRocketEngineTier3.get(1L)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Tier3Booster.get(2),
                        CustomItemList.HeavyDutyRocketEngineTier3.get(2L),
                        CustomItemList.HeavyDutyPlateTier7.get(5),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.HeavyDutyRocketEngineTier4.get(1L)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        // nose cones

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler
                                .getModItem(ProjectRedIllumination.ID, "projectred.illumination.cagelamp2.inv", 1L, 14),
                        GTModHandler.getModItem(GalacticraftCore.ID, "item.heavyPlating", 4L, 0),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(GalacticraftCore.ID, "item.noseCone", 1L, 0))
                .fluidInputs(Materials.StainlessSteel.getMolten(36L)).duration(2 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(GalacticraftCore.ID, "item.noseCone", 1L, 0),
                        GTModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 4L, 0),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(GalacticraftMars.ID, "item.heavyNoseCone", 1L, 0))
                .fluidInputs(Materials.Titanium.getMolten(36L)).duration(2 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(GalacticraftMars.ID, "item.heavyNoseCone", 1L, 0),
                        CustomItemList.HeavyDutyPlateTier5.get(4),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(CustomItemList.HeavyDutyNoseConeTier3.get(1L))
                .fluidInputs(Materials.TungstenSteel.getMolten(36L)).duration(2 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.HeavyDutyNoseConeTier3.get(1L),
                        CustomItemList.HeavyDutyPlateTier7.get(4),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(CustomItemList.HeavyDutyNoseConeTier4.get(1L))
                .fluidInputs(Materials.NaquadahAlloy.getMolten(36L)).duration(2 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);
        // rocket fins

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(GalacticraftCore.ID, "item.basicItem", 2L, 9),
                        GTModHandler.getModItem(GalacticraftCore.ID, "item.heavyPlating", 4L, 0),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(GTModHandler.getModItem(GalacticraftCore.ID, "item.rocketFins", 1L, 0))
                .duration(2 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(GalacticraftMars.ID, "item.null", 2L, 3),
                        GTModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 4L, 0),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(GTModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 1L, 2))
                .duration(2 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.HeavyDutyPlateTier4.get(2),
                        CustomItemList.HeavyDutyPlateTier5.get(4),
                        GTModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(CustomItemList.HeavyDutyRocketFinsTier3.get(1L)).duration(2 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.HeavyDutyPlateTier6.get(2),
                        CustomItemList.HeavyDutyPlateTier7.get(4),
                        GTModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0),
                        GTUtility.getIntegratedCircuit(5))
                .itemOutputs(CustomItemList.HeavyDutyRocketFinsTier4.get(1L)).duration(2 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);
        // // booster

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(GalacticraftCore.ID, "item.meteoricIronIngot", 3L, 1),
                        GTModHandler.getModItem(GalacticraftCore.ID, "item.heavyPlating", 4L, 0),
                        GTModHandler.getModItem(GalacticraftCore.ID, "item.airVent", 1L, 0),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(GTModHandler.getModItem(GalacticraftCore.ID, "item.engine", 1L, 1))
                .fluidInputs(Materials.Fuel.getFluid(1000L)).duration(2 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.LedoxCompressedPlate.get(3),
                        GTModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 4L, 0),
                        GTModHandler.getModItem(GalacticraftCore.ID, "item.engine", 1L, 1),
                        GTModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(CustomItemList.Tier2Booster.get(1L)).duration(2 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.MytrylCompressedPlate.get(3),
                        CustomItemList.HeavyDutyPlateTier5.get(4),
                        CustomItemList.Tier2Booster.get(1L),
                        GTModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(CustomItemList.Tier3Booster.get(1L)).duration(2 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.BlackPlutoniumCompressedPlate.get(3),
                        CustomItemList.HeavyDutyPlateTier7.get(4),
                        CustomItemList.Tier3Booster.get(1L),
                        GTModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0),
                        GTUtility.getIntegratedCircuit(6))
                .itemOutputs(CustomItemList.Tier4Booster.get(1L)).duration(2 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

    }

    private void makeIronTanksRecipes() {
        if (!IronTanks.isModLoaded() || !BuildCraftFactory.isModLoaded()) {
            return;
        }
        // Copper Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Copper, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "copperTank", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(144L)).duration(15 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Iron Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Iron, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "ironTank", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(144L)).duration(20 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Steel Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "silverTank", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(144L)).duration(25 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Gold Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Gold, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "goldTank", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(144L)).duration(30 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Diamond Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "diamondTank", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(144L)).duration(40 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Obsidian Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Obsidian, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "obsidianTank", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(144L)).duration(45 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // Aluminium Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Aluminium, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "emeraldTank", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(144L)).duration(50 * SECONDS).eut(60).addTo(assemblerRecipes);
        // Stainless Steel Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.StainlessSteel, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "stainlesssteelTank", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(144L)).duration(55 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // Titanium Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "titaniumTank", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(144L)).duration(60 * SECONDS).eut(256).addTo(assemblerRecipes);
        // Tungsten Steel Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "tungstensteelTank", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(144L)).duration(1 * MINUTES + 5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);
        // --- Upgrade Glass to Copper Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Copper, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "glassCopperUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).duration(15 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // --- Upgrade Glass to Iron Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Iron, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "glassIronUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).duration(20 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // --- Upgrade Copper to Iron Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Copper, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Iron, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "copperIronUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).duration(25 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // --- Upgrade Copper to Steel Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Copper, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "copperSilverUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).duration(30 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // --- Upgrade Iron to Gold Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Gold, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "ironGoldUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).duration(35 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // --- Upgrade Steel to Gold Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Gold, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "silverGoldUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).duration(35 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // --- Upgrade Steel to Diamond Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 2L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "silverDiamondUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).duration(40 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // --- Upgrade Diamond to Obsidian

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Obsidian, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "diamondObsidianUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).duration(45 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);
        // --- Upgrade Diamond to Aluminium Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Aluminium, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "diamondEmeraldUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).duration(50 * SECONDS).eut(60).addTo(assemblerRecipes);
        // --- Upgrade Aluminium to Stainless Steel Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.StainlessSteel, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "emeraldStainlesssteelUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).duration(55 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);
        // --- Upgrade Stainless Steel to Titanium Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "stainlesssteelTitaniumUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).duration(60 * SECONDS).eut(256).addTo(assemblerRecipes);
        // --- Upgrade Titanium to Tungsten Steel Tank

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 1L),
                        GTUtility.getIntegratedCircuit(4))
                .itemOutputs(GTModHandler.getModItem(IronTanks.ID, "titaniumTungstensteelUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).duration(1 * MINUTES + 5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);
    }

    private void makeChiselRecipes() {
        if (!Chisel.isModLoaded()) {
            return;
        }
        // --- Items
        // Chisel

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 2L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "chisel", 1L, 0)).duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Obsidian Chisel

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 2L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.WroughtIron, 2L))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "obsidianChisel", 1L, 0)).duration(20 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Diamond Chisel

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 2L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "diamondChisel", 1L, 0)).duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Nether Star Chisel

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Bedrockium, 2L),
                        GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.VanadiumSteel, 2L))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "netherStarChisel", 1L, 0)).duration(20 * MINUTES)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // --- Blocks
        // Factory Block

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "factoryblock", 16L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Technical Block

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 5),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L),
                        GTUtility.getIntegratedCircuit(11))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "technical", 16L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Tyrian

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L),
                        GTUtility.getIntegratedCircuit(14))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "tyrian", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Futura

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 4),
                        new ItemStack(Items.redstone, 1),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "futura", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Fantasy Block

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 4),
                        new ItemStack(Items.gold_nugget, 1),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "fantasyblock", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Grimstone

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 4),
                        new ItemStack(Items.coal, 1),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "grimstone", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Hex Plating

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 2),
                        new ItemStack(Items.coal, 4),
                        GTUtility.getIntegratedCircuit(11))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "hexPlating", 4L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Holystone

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 4),
                        new ItemStack(Items.feather, 1),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "holystone", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Laboratory Block

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 4),
                        new ItemStack(Items.quartz, 1),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "laboratoryblock", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Lavastone

        GTValues.RA.stdBuilder().itemInputs(new ItemStack(Blocks.stone, 4), GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "lavastone", 8L, 0))
                .fluidInputs(Materials.Lava.getFluid(1000L)).duration(5 * SECONDS).eut(24).addTo(assemblerRecipes);
        // Paperwall

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Items.paper, 4),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 1L),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "paperwall", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Paperwall Block

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Items.paper, 4),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 1L),
                        GTUtility.getIntegratedCircuit(11))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "paperwall_block", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Road Lines

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Items.redstone, 3),
                        GTOreDictUnificator.get(ItemList.Dye_Bonemeal.get(3L)),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "road_line", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Temple Block

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 4),
                        GTOreDictUnificator.get(new ItemStack(Items.dye, 1, 4)),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "templeblock", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Mossy Temple Block

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(Chisel.ID, "templeblock", 8L, 0),
                        GTModHandler.getModItem(BiomesOPlenty.ID, "moss", 8L, 0),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "mossy_templeblock", 4L, 0)).duration(5 * SECONDS)
                .eut(24).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 4),
                        GTOreDictUnificator.get(new ItemStack(Items.dye, 1, 9)),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "valentines", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Voidstone

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 2),
                        new ItemStack(Blocks.obsidian, 2),
                        new ItemStack(Items.ender_pearl, 1),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "voidstone", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Energised Voidstone

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 2),
                        new ItemStack(Blocks.obsidian, 2),
                        new ItemStack(Items.ender_pearl, 1),
                        new ItemStack(Items.glowstone_dust, 1),
                        GTUtility.getIntegratedCircuit(11))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "voidstone2", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Warning Sign

        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 2),
                        new ItemStack(Items.sign, 1),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "warningSign", 4L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);
        // Waterstone

        GTValues.RA.stdBuilder().itemInputs(new ItemStack(Blocks.stone, 4), GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "waterstone", 8L, 0))
                .fluidInputs(Materials.Water.getFluid(1000L)).duration(5 * SECONDS).eut(24).addTo(assemblerRecipes);

        // Hempcrete Sand
        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.gravel, 3, 0),
                        GTOreDictUnificator.get("sand", 3L),
                        GTOreDictUnificator.get("itemWheat", 2L),
                        ItemList.IC2_Plantball.get(1L))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "hempcretesand", 12)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Anti Block
        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 4),
                        new ItemStack(Items.glowstone_dust, 1),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "antiBlock", 8L, 15)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);

        // Carpet
        for (int meta = 0; meta < 16; ++meta) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            new ItemStack(Blocks.wool, 4, meta),
                            new ItemStack(Items.string, 1),
                            GTUtility.getIntegratedCircuit(24))
                    .itemOutputs(GTModHandler.getModItem(Chisel.ID, "carpet_block", 8L, meta)).duration(5 * SECONDS)
                    .eut(24).addTo(assemblerRecipes);
        }

        // Cubits
        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 4),
                        new ItemStack(Blocks.wool, 1, 15),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "cubit", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);

        // Fantasy Block
        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 4),
                        new ItemStack(Items.gold_nugget, 1),
                        new ItemStack(Items.dye, 1, 15),
                        GTUtility.getIntegratedCircuit(11))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "fantasyblock2", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);

        // Neonite
        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Items.emerald, 8),
                        new ItemStack(Items.glowstone_dust, 1),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "neonite", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);

        // GloTek
        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Items.emerald, 8),
                        new ItemStack(Items.glowstone_dust, 2),
                        GTUtility.getIntegratedCircuit(11))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "glotek", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);

        // Rebellious Block
        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 2),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 2),
                        new ItemStack(Items.gold_nugget, 1),
                        GTUtility.getIntegratedCircuit(23))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "rebel", 32L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);

        // Structural Concrete
        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.gravel, 5),
                        new ItemStack(Blocks.sand, 2),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Clay, 1),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "nucrete", 16L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);

        // Purpur Block
        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.end_stone, 4),
                        new ItemStack(Items.dye, 4, 5),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "purpur", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);

        // Svelstone
        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.stone, 4),
                        GTModHandler.getModItem(Chisel.ID, "andesite", 1, 0, missing),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(GTModHandler.getModItem(Chisel.ID, "sveltstone", 8L, 0)).duration(5 * SECONDS).eut(24)
                .addTo(assemblerRecipes);

        // Woolen Clay
        for (int meta = 0; meta < 16; ++meta) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            new ItemStack(Blocks.wool, 2, meta),
                            new ItemStack(Blocks.stained_hardened_clay, 2, meta),
                            GTUtility.getIntegratedCircuit(24))
                    .itemOutputs(GTModHandler.getModItem(Chisel.ID, "woolen_clay", 8L, meta)).duration(5 * SECONDS)
                    .eut(24).addTo(assemblerRecipes);
        }
    }

    private void makeReinforcedIronPlateRecipes() {

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCarbonPlate", 4),
                        CustomItemList.AluminiumIronPlate.get(1))
                .itemOutputs(CustomItemList.ReinforcedAluminiumIronPlate.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.SiliconSG, 4),
                        CustomItemList.TitaniumIronPlate.get(1))
                .itemOutputs(CustomItemList.ReinforcedTitaniumIronPlate.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 4),
                        CustomItemList.TungstenIronPlate.get(1))
                .itemOutputs(CustomItemList.ReinforcedTungstenIronPlate.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 4),
                        CustomItemList.TungstenSteelIronPlate.get(1))
                .itemOutputs(CustomItemList.ReinforcedTungstenSteelIronPlate.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 4),
                        CustomItemList.ChromeIronPlate.get(1))
                .itemOutputs(CustomItemList.ReinforcedChromeIronPlate.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartIridium", 4),
                        GTModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 1, 6))
                .itemOutputs(GTModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 1, 7))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_ZPM).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.MysteriousCrystal, 4),
                        CustomItemList.NaquadriaIronPlate.get(1))
                .itemOutputs(CustomItemList.ReinforcedNaquadriaIronPlate.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_UV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 4),
                        CustomItemList.NeutroniumIronPlate.get(1))
                .itemOutputs(CustomItemList.ReinforcedNeutroniumIronPlate.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_UHV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 4),
                        CustomItemList.BedrockiumIronPlate.get(1))
                .itemOutputs(CustomItemList.ReinforcedBedrockiumIronPlate.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_UEV).addTo(assemblerRecipes);

    }

    private void makeMixedMetalIngotRecipes() {

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyIron, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyIron, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyIron, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyIron, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyIron, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyIron, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Nickel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Nickel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Nickel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 4L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 4L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 4L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 4L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 4L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 4L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 5L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 5L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 5L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 5L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 6L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 6L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 8L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 8L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 8L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 10L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 10L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 10L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnnealedCopper, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 12L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.RoseGold, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 12L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AstralSilver, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 12L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnnealedCopper, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 14L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.RoseGold, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 14L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AstralSilver, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 14L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnnealedCopper, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 16L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.RoseGold, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 16L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AstralSilver, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 16L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSE, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnnealedCopper, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 18L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSE, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.RoseGold, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 18L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSE, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AstralSilver, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 18L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AnnealedCopper, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 20L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.RoseGold, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 20L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.AstralSilver, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 20L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 22L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSE, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 24L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSS, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 26L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Osmiridium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 28L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Osmiridium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSE, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 30L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Osmiridium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSS, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 32L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.EnergeticAlloy, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 34L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.EnergeticAlloy, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 36L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.EnergeticAlloy, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Draconium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 38L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.EnergeticAlloy, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 40L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Sunnarium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 42L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Sunnarium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Draconium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 44L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSS, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 48L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 52L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 56L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.getIntegratedCircuit(3),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 1))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 64L, 4)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

    }

    private void makeLootBagRecipes() {
        if (!EnhancedLootBags.isModLoaded()) {
            return;
        }
        // Basic -> Steam Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 1, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 2, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Steam -> LV Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 2, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 4, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // LV -> MV Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 4, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 5, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        // MV -> HV Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 5, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 6, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // HV -> EV Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 6, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 7, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);
        // EV -> IV Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 7, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 8, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);
        // IV -> LuV Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 8, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 41, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);
        // Forest Ranger Basic -> Forest Ranger Advanced Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 19, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 20, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Forest Ranger Advanced -> Forest Ranger Expert Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 20, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 21, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        // BM Novice -> BM Adept Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 16, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 17, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        // BM Adept -> BM Master Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 17, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 18, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        // Bees Basic -> Bees Advanced Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 25, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 26, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Bees Advanced -> Bees Expert Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 26, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 27, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
        // Space Invaders Moon -> Space Invaders Mars Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 22, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 23, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);
        // Space Invaders Mars -> Space Invaders Asteroids Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 23, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 24, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);
        // Fast Food -> Slow Food Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 28, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 29, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Slow Food -> Haute Cuisine Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 29, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 30, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Haute Cuisine -> Dessert Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 30, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 31, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Transportation 3 -> 2 Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 32, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 33, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Transportation 2 -> 1 Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 33, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 34, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Magic Novice -> Magic Adept Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 9, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 10, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Magic Adept -> Magic Master Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 10, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 11, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        // Magic Master -> Magic Grandmaster Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 11, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 12, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
        // Magic Grandmaster -> Magic Grandmaster Unique Lootbags
        GTValues.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 16, 12, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 13, missing)).duration(SECONDS * 30)
                .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
    }

    private void makeCoinRecipes() {
        // Coins

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinAdventure.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinAdventureI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinAdventureI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinAdventureII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinAdventureII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinAdventureIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.CoinAdventureIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinAdventureIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBees.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinBeesI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBeesI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinBeesII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBeesII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinBeesIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBeesIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinBeesIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBlood.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinBloodI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBloodI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinBloodII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBloodII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinBloodIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBloodIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinBloodIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinChemist.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinChemistI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinChemistI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinChemistII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinChemistII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinChemistIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinChemistIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinChemistIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinCook.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinCookI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinCookI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinCookII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinCookII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinCookIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinCookIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinCookIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinDarkWizard.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinDarkWizardI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinDarkWizardI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinDarkWizardII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.CoinDarkWizardII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinDarkWizardIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.CoinDarkWizardIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinDarkWizardIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFarmer.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinFarmerI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFarmerI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinFarmerII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFarmerII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinFarmerIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFarmerIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinFarmerIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinForestry.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinForestryI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinForestryI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinForestryII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinForestryII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinForestryIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinForestryIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinForestryIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSpace.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinSpaceI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSpaceI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinSpaceII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSpaceII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinSpaceIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSpaceIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinSpaceIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSurvivor.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinSurvivorI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSurvivorI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinSurvivorII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSurvivorII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinSurvivorIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSurvivorIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinSurvivorIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinTechnician.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinTechnicianI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinTechnicianI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinTechnicianII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.CoinTechnicianII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinTechnicianIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.CoinTechnicianIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinTechnicianIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSmith.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinSmithI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSmithI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinSmithII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSmithII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinSmithIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSmithIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinSmithIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinWitch.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinWitchI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinWitchI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinWitchII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinWitchII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinWitchIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinWitchIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinWitchIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFlower.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinFlowerI.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFlowerI.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinFlowerII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFlowerII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinFlowerIII.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFlowerIII.get(10L), GTUtility.getIntegratedCircuit(10))
                .itemOutputs(CustomItemList.CoinFlowerIV.get(1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinAdventureI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinAdventure.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinAdventureII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinAdventureI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinAdventureIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinAdventureII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinAdventureIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinAdventureIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBeesI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinBees.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBeesII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinBeesI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBeesIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinBeesII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBeesIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinBeesIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBloodI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinBlood.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBloodII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinBloodI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBloodIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinBloodII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinBloodIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinBloodIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinChemistI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinChemist.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinChemistII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinChemistI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinChemistIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinChemistII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinChemistIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinChemistIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinCookI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinCook.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinCookII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinCookI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinCookIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinCookII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinCookIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinCookIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinDarkWizardI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinDarkWizard.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinDarkWizardII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinDarkWizardI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinDarkWizardIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinDarkWizardII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinDarkWizardIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinDarkWizardIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFarmerI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinFarmer.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFarmerII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinFarmerI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFarmerIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinFarmerII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFarmerIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinFarmerIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinForestryI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinForestry.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinForestryII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinForestryI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinForestryIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinForestryII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinForestryIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinForestryIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSpaceI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinSpace.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSpaceII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinSpaceI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSpaceIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinSpaceII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSpaceIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinSpaceIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSurvivorI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinSurvivor.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSurvivorII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinSurvivorI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSurvivorIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinSurvivorII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSurvivorIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinSurvivorIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinTechnicianI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinTechnician.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinTechnicianII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinTechnicianI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinTechnicianIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinTechnicianII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinTechnicianIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinTechnicianIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSmithI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinSmith.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSmithII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinSmithI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSmithIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinSmithII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinSmithIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinSmithIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinWitchI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinWitch.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinWitchII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinWitchI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinWitchIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinWitchII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinWitchIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinWitchIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFlowerI.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinFlower.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFlowerII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinFlowerI.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFlowerIII.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinFlowerII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(CustomItemList.CoinFlowerIV.get(1L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.CoinFlowerIII.get(10L)).duration(5 * SECONDS).eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);

    }

    private void makePistonRecipes() {
        // Vanilla Piston Assembler recipe
        List<ItemStack> fenceWood = OreDictionary.getOres("fenceWood");
        for (ItemStack oreStack : fenceWood) {

            ItemStack stack = oreStack.splitStack(1);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            new ItemStack(Blocks.cobblestone, 1, 0),
                            GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                            stack,
                            ItemList.Plank_Oak.get(6L),
                            GTUtility.getIntegratedCircuit(1))
                    .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            new ItemStack(Blocks.cobblestone, 1, 0),
                            GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                            stack,
                            ItemList.Plank_Spruce.get(6L),
                            GTUtility.getIntegratedCircuit(1))
                    .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            new ItemStack(Blocks.cobblestone, 1, 0),
                            GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                            stack,
                            ItemList.Plank_Birch.get(6L),
                            GTUtility.getIntegratedCircuit(1))
                    .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            new ItemStack(Blocks.cobblestone, 1, 0),
                            GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                            stack,
                            ItemList.Plank_Jungle.get(6L),
                            GTUtility.getIntegratedCircuit(1))
                    .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            new ItemStack(Blocks.cobblestone, 1, 0),
                            GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                            stack,
                            ItemList.Plank_Acacia.get(6L),
                            GTUtility.getIntegratedCircuit(1))
                    .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            new ItemStack(Blocks.cobblestone, 1, 0),
                            GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                            stack,
                            ItemList.Plank_DarkOak.get(6L),
                            GTUtility.getIntegratedCircuit(1))
                    .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            if (Forestry.isModLoaded()) {

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Larch.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Teak.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Acacia_Green.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Lime.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Chestnut.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Wenge.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Baobab.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Sequoia.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Kapok.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Ebony.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Mahagony.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Balsa.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Willow.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Walnut.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Greenheart.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Cherry.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Mahoe.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Poplar.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Palm.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Papaya.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Pine.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Plum.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Maple.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                new ItemStack(Blocks.cobblestone, 1, 0),
                                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L),
                                stack,
                                ItemList.Plank_Citrus.get(6L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(new ItemStack(Blocks.piston, 1, 0)).fluidInputs(Materials.Redstone.getMolten(72L))
                        .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

            }
        }
    }

    private void makeCoilRecipes() {

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.Cupronickel, 8L),
                        CustomItemList.AluminoSilicateWool.get(12L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Coil_Cupronickel.get(1L)).fluidInputs(Materials.Tin.getMolten(288L))
                .duration(5 * SECONDS).eut(7).addTo(assemblerRecipes);
        // TODO: Rework other stuff (that depends on certain Heat Levels, mostly LuV+ stuff) so this can be wrapped into
        // a oneliner.

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.Cupronickel, 8L),
                        CustomItemList.MicaInsulatorFoil.get(8L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Coil_Cupronickel.get(1L)).fluidInputs(Materials.Tin.getMolten(144L))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.Kanthal, 8L),
                        CustomItemList.MicaInsulatorFoil.get(12L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Coil_Kanthal.get(1L)).fluidInputs(Materials.Cupronickel.getMolten(144L))
                .duration(15 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.Nichrome, 8L),
                        CustomItemList.MicaInsulatorFoil.get(16L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Coil_Nichrome.get(1L)).fluidInputs(Materials.Kanthal.getMolten(144L))
                .duration(20 * SECONDS).eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.TPV, 8L),
                        CustomItemList.MicaInsulatorFoil.get(20L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Coil_TungstenSteel.get(1L)).fluidInputs(Materials.Nichrome.getMolten(144L))
                .duration(25 * SECONDS).eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.HSSG, 8L),
                        CustomItemList.MicaInsulatorFoil.get(24L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Coil_HSSG.get(1L)).fluidInputs(Materials.TPV.getMolten(144L))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.HSSS, 8L),
                        CustomItemList.MicaInsulatorFoil.get(28L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Coil_HSSS.get(1L)).fluidInputs(Materials.HSSG.getMolten(144L))
                .duration(35 * SECONDS).eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.Naquadah, 8L),
                        CustomItemList.MicaInsulatorFoil.get(32L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Coil_Naquadah.get(1L)).fluidInputs(Materials.HSSS.getMolten(144L))
                .duration(40 * SECONDS).eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.NaquadahAlloy, 8L),
                        CustomItemList.MicaInsulatorFoil.get(36L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Coil_NaquadahAlloy.get(1L)).fluidInputs(Materials.Naquadah.getMolten(144L))
                .duration(45 * SECONDS).eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.Trinium, 8L),
                        CustomItemList.MicaInsulatorFoil.get(40L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Coil_Trinium.get(1L)).fluidInputs(Materials.NaquadahAlloy.getMolten(144L))
                .duration(50 * SECONDS).eut(TierEU.RECIPE_ZPM).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.ElectrumFlux, 8L),
                        CustomItemList.MicaInsulatorFoil.get(44L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Coil_ElectrumFlux.get(1L)).fluidInputs(Materials.Trinium.getMolten(144L))
                .duration(55 * SECONDS).eut(TierEU.RECIPE_UV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.DraconiumAwakened, 8L),
                        CustomItemList.MicaInsulatorFoil.get(48L),
                        GTUtility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Coil_AwakenedDraconium.get(1L))
                .fluidInputs(Materials.ElectrumFlux.getMolten(144L)).duration(60 * SECONDS).eut(TierEU.RECIPE_UHV)
                .addTo(assemblerRecipes);

    }

    private void makeNEIPlanetRecipes() {
        if (!Botania.isModLoaded()) {
            return;
        }

        final OrePrefixes[] allOrePrefixes = { OrePrefixes.ore, OrePrefixes.oreNetherrack, OrePrefixes.oreEndstone,
                OrePrefixes.oreBlackgranite, OrePrefixes.oreRedgranite, OrePrefixes.oreMarble, OrePrefixes.oreBasalt };

        // NEI Ore Plugin planets
        // T0 Planets
        // Overworld
        GTValues.RA.stdBuilder()
                .itemInputs(
                        getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                        getModItem(Minecraft.ID, "stone", 64, 0),
                        getModItem(Minecraft.ID, "grass", 64, 0),
                        getModItem(Minecraft.ID, "sapling", 1, 0),
                        GTUtility.getIntegratedCircuit(17))
                .fluidInputs(Materials.Water.getFluid(10000))
                .itemOutputs(new ItemStack(ModBlocks.blocks.get("Ow"), 1, 0)).duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        // Twilight Forest
        if (TwilightForest.isModLoaded()) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(Minecraft.ID, "stone", 64, 0),
                            getModItem(Minecraft.ID, "grass", 64, 0),
                            getModItem(TwilightForest.ID, "tile.TFSapling", 1, 1),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Water.getFluid(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("TF"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        }
        // Nether
        if (BiomesOPlenty.isModLoaded()) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(Minecraft.ID, "netherrack", 64, 0),
                            getModItem(Minecraft.ID, "soul_sand", 64, 0),
                            getModItem(BiomesOPlenty.ID, "ash", 64, 0),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Lava.getFluid(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Ne"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        }
        // End
        if (Natura.isModLoaded()) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(Minecraft.ID, "end_stone", 64, 0),
                            getModItem(Minecraft.ID, "obsidian", 64, 0),
                            getModItem(Natura.ID, "Cloud", 64, 1),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(FluidRegistry.getFluidStack("ender", 10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("ED"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        }
        // Far End Asteroids
        if (HardcoreEnderExpansion.isModLoaded()) {
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(HardcoreEnderExpansion.ID, "end_stone_terrain", 64, 0),
                            getModItem(HardcoreEnderExpansion.ID, "end_stone_terrain", 64, 1),
                            getModItem(HardcoreEnderExpansion.ID, "end_stone_terrain", 64, 2),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(FluidRegistry.getFluidStack("endergoo", 10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("EA"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        }

        if (GalaxySpace.isModLoaded()) {
            // T1 Planets
            // The Moon
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalacticraftCore.ID, "tile.moonBlock", 64, 3),
                            getModItem(GalacticraftCore.ID, "tile.moonBlock", 64, 4),
                            getModItem(GalacticraftCore.ID, "tile.moonBlock", 64, 14),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.SaltWater.getFluid(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Mo"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
            // T2 Planets
            // Deimos
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalaxySpace.ID, "deimosblocks", 64, 0),
                                getModItem(GalaxySpace.ID, "deimosblocks", 64, 1),
                                GTOreDictUnificator.get(orePrefix, Materials.Uranium, 64),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.SulfuricAcid.getFluid(10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("De"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);
            }
            // Phobos
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "phobosblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "phobosblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "phobosblocks", 64, 2),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.SulfuricAcid.getFluid(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Ph"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);
            // Mars
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalacticraftMars.ID, "tile.mars", 64, 5),
                            getModItem(GalacticraftMars.ID, "tile.mars", 64, 6),
                            getModItem(GalacticraftMars.ID, "tile.mars", 64, 7),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Chlorobenzene.getFluid(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Ma"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_EV).addTo(assemblerRecipes);
            // T3 Planets
            // Ceres
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "ceresblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "ceresblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "ceresblocks", 64, 2),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Oxygen.getGas(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Ce"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);
            // Callisto
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalaxySpace.ID, "callistoblocks", 64, 0),
                                getModItem(GalaxySpace.ID, "callistoblocks", 64, 1),
                                GTOreDictUnificator.get(orePrefix, Materials.CallistoIce, 64L),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.LiquidAir.getFluid(10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("Ca"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);
            }
            // Asteroids
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalacticraftMars.ID, "tile.asteroidsBlock", 64, 0),
                            getModItem(GalacticraftMars.ID, "tile.asteroidsBlock", 64, 1),
                            getModItem(GalacticraftMars.ID, "tile.denseIce", 64, 0),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(FluidRegistry.getFluidStack("ice", 10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("As"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);
            // Ganymede
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalaxySpace.ID, "ganymedeblocks", 64, 0),
                                getModItem(GalaxySpace.ID, "ganymedeblocks", 64, 1),
                                GTOreDictUnificator.get(orePrefix, Materials.Platinum, 64L),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.Oxygen.getGas(10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("Ga"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);
            }
            // Europa
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalaxySpace.ID, "europagrunt", 64, 0),
                                getModItem(GalaxySpace.ID, "europagrunt", 64, 1),
                                GTOreDictUnificator.get(orePrefix, Materials.Manganese, 64L),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.Water.getFluid(10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("Eu"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);
            }
            // Ross 128b
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(Minecraft.ID, "dirt", 64, 2),
                            getModItem(Minecraft.ID, "stone", 64, 0),
                            ItemList.Generator_Naquadah_Mark_I.get(1),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Lava.getFluid(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Rb"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);
            // T4 Planets
            // Io
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "ioblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "ioblocks", 64, 2),
                            getModItem(GalaxySpace.ID, "ioblocks", 64, 3),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Lead.getMolten(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Io"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);
            // Mercury
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "mercuryblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "mercuryblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "mercuryblocks", 64, 2),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Iron.getMolten(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Me"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);
            // Venus
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalaxySpace.ID, "venusblocks", 64, 0),
                                getModItem(GalaxySpace.ID, "venusblocks", 64, 1),
                                GTOreDictUnificator.get(orePrefix, Materials.Quantium, 64),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.CarbonDioxide.getGas(10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("Ve"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);
            }
            // T5 Planets
            // Miranda
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "mirandablocks", 64, 0),
                            getModItem(GalaxySpace.ID, "mirandablocks", 64, 1),
                            getModItem(GalaxySpace.ID, "mirandablocks", 64, 2),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.HydricSulfide.getGas(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Mi"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_ZPM).addTo(assemblerRecipes);
            // Oberon
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "oberonblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "oberonblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "oberonblocks", 64, 2),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.CarbonMonoxide.getGas(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Ob"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_ZPM).addTo(assemblerRecipes);
            // Enceladus
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "enceladusblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "enceladusblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "enceladusblocks", 64, 2),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Oxygen.getGas(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("En"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_ZPM).addTo(assemblerRecipes);
            // Titan
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "titanblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "titanblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "titanblocks", 64, 2),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Methane.getGas(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Ti"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_ZPM).addTo(assemblerRecipes);
            // Ross 128ba
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalacticraftCore.ID, "tile.moonBlock", 64, 3),
                            getModItem(GalacticraftCore.ID, "tile.moonBlock", 64, 4),
                            getModItem(GalacticraftCore.ID, "tile.moonBlock", 64, 5),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Helium_3.getGas(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Ra"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_ZPM).addTo(assemblerRecipes);
            // T6 Planets
            // Triton
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "tritonblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "tritonblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "tritonblocks", 64, 2),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Ethylene.getGas(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Tr"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_UV).addTo(assemblerRecipes);
            // Proteus
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "proteusblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "proteusblocks", 64, 2),
                            getModItem(GalaxySpace.ID, "proteusblocks", 64, 3),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Deuterium.getGas(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Pr"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_UV).addTo(assemblerRecipes);
            // T7 Planets
            // Haumea
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalaxySpace.ID, "haumeablocks", 64, 0),
                                GTOreDictUnificator.get(orePrefix, Materials.NetherStar, 64),
                                GTOreDictUnificator.get(orePrefix, Materials.Naquadah, 64),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.InfusedGold.getMolten(10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("Ha"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_UHV).addTo(assemblerRecipes);
            }
            // Pluto
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "plutoblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "plutoblocks", 64, 4),
                            getModItem(GalaxySpace.ID, "plutoblocks", 64, 6),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Fluorine.getGas(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("Pl"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_UHV).addTo(assemblerRecipes);
            // Makemake
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalaxySpace.ID, "makemakegrunt", 64, 0),
                                getModItem(GalaxySpace.ID, "makemakegrunt", 64, 1),
                                GTOreDictUnificator.get(orePrefix, Materials.Chrysotile, 64),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.HydrofluoricAcid.getFluid(10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("MM"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_UHV).addTo(assemblerRecipes);
            }
            // Kuiper Belt
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalacticraftMars.ID, "tile.asteroidsBlock", 64, 0),
                                getModItem(GalacticraftMars.ID, "tile.denseIce", 64, 0),
                                GTOreDictUnificator.get(orePrefix, Materials.Neutronium, 64),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.HydrofluoricAcid.getFluid(10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("KB"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_UHV).addTo(assemblerRecipes);
            }
            // T8 Planets
            // Vega B
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalaxySpace.ID, "vegabgrunt", 64, 0),
                                getModItem(GalaxySpace.ID, "vegabsubgrunt", 64, 0),
                                GTOreDictUnificator.get(orePrefix, Materials.InfinityCatalyst, 64),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.Neutronium.getMolten(10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("VB"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_UEV).addTo(assemblerRecipes);
            }
            // Barnard C
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(Minecraft.ID, "stone", 64, 0),
                            getModItem(GalaxySpace.ID, "barnardaCgrass", 64, 0),
                            getModItem(GalaxySpace.ID, "barnardaCsapling", 1, 1),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(FluidRegistry.getFluidStack("unknowwater", 10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("BC"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_UEV).addTo(assemblerRecipes);
            // Barnard E
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "barnardaEgrunt", 64, 0),
                            getModItem(GalaxySpace.ID, "barnardaEsubgrunt", 64, 0),
                            GTOreDictUnificator.get(OrePrefixes.block, Materials.Unstable, 64),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.LiquidAir.getFluid(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("BE"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_UEV).addTo(assemblerRecipes);
            // Barnard F
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalaxySpace.ID, "barnardaFgrunt", 64, 0),
                                getModItem(GalaxySpace.ID, "barnardaFsubgrunt", 64, 0),
                                GTOreDictUnificator.get(orePrefix, Materials.Bedrockium, 64),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.Tin.getMolten(10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("BF"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_UEV).addTo(assemblerRecipes);
            }
            // T Ceti E
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "tcetieblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "tcetieblocks", 64, 2),
                            getModItem(GalaxySpace.ID, "tcetiedandelions", 64, 4),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(GTModHandler.getDistilledWater(10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("TE"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_UEV).addTo(assemblerRecipes);
            // Alpha Centauri Bb
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalaxySpace.ID, "acentauribbgrunt", 64, 0),
                                getModItem(GalaxySpace.ID, "acentauribbsubgrunt", 64, 0),
                                GTOreDictUnificator.get(orePrefix, Materials.Samarium, 64),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.Copper.getMolten(10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("CB"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_UEV).addTo(assemblerRecipes);
            }
            // T9 Planets
            // Seth
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(Minecraft.ID, "snow", 64, 0),
                                getModItem(Minecraft.ID, "packed_ice", 64, 0),
                                GTOreDictUnificator.get(orePrefix, Materials.TengamRaw, 64),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(FluidRegistry.getFluidStack("ice", 10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("Se"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_UIV).addTo(assemblerRecipes);
            }
            // Anubis
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalacticraftAmunRa.ID, "tile.baseBlockGround", 64, 1),
                                getModItem(GalacticraftAmunRa.ID, "tile.baseBlockRock", 64, 1),
                                GTOreDictUnificator.get(orePrefix, Materials.InfinityCatalyst, 64),
                                GTUtility.getIntegratedCircuit(17))
                        // This breaks the pattern but the theme of the planet is that it's super dry so
                        // :shrug:, maybe add eventual pumpable fluid
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("An"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_UIV).addTo(assemblerRecipes);
            }
            // Neper
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(Minecraft.ID, "grass", 64, 0),
                                getModItem(Minecraft.ID, "stone", 64, 0),
                                GTOreDictUnificator.get(orePrefix, Materials.Dilithium, 64),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.Water.getFluid(10000)) // There isn't actually water on Neper, but it
                                                                      // fits
                        // the grass
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("Np"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_UIV).addTo(assemblerRecipes);
            }
            // Maahes
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalacticraftAmunRa.ID, "tile.baseGrass", 64, 0),
                                getModItem(GalacticraftAmunRa.ID, "tile.saplings", 1, 0),
                                GTOreDictUnificator.get(orePrefix, Materials.Naquadria, 64),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.Water.getFluid(10000)) // Same as Neper (but the grass is red)
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("Mh"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_UIV).addTo(assemblerRecipes);
            }
            // Horus
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(GalacticraftAmunRa.ID, "tile.baseFalling", 64, 0), // Obsidian Sand
                                getModItem(GalacticraftAmunRa.ID, "tile.baseBlockRock", 64, 9), // Obsidian Brick
                                GTOreDictUnificator.get(orePrefix, Materials.CosmicNeutronium, 64),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(Materials.Lava.getFluid(10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("Ho"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_UIV).addTo(assemblerRecipes);
            }
            // Mehen Belt
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            GTOreDictUnificator.get(OrePrefixes.stone, Materials.GraniteBlack, 64L),
                            getModItem(GalacticraftMars.ID, "tile.denseIce", 64, 0),
                            GTModHandler.getModItem(GalacticraftAmunRa.ID, "tile.baseBlockRock", 1L, 14),
                            GTUtility.getIntegratedCircuit(17))
                    .fluidInputs(FluidRegistry.getFluidStack("ice", 10000))
                    .itemOutputs(new ItemStack(ModBlocks.blocks.get("MB"), 1, 0)).duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_UIV).addTo(assemblerRecipes);

            // Technically T10
            // Deep Dark
            for (OrePrefixes orePrefix : allOrePrefixes) {
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                                getModItem(Minecraft.ID, "cobblestone", 64, 0),
                                GTOreDictUnificator.get(orePrefix, Materials.Rubidium, 64),
                                GTOreDictUnificator.get(orePrefix, Materials.Pumice, 64),
                                GTUtility.getIntegratedCircuit(17))
                        .fluidInputs(MaterialsUEVplus.SpaceTime.getMolten(10000))
                        .itemOutputs(new ItemStack(ModBlocks.blocks.get("DD"), 1, 0)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_UMV).addTo(assemblerRecipes);
            }
        }
        if (Computronics.isModLoaded()) {
            // Audio Cable
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.Silver, 1),
                            GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Paper, 1))
                    .fluidInputs(Materials.Bismuth.getMolten(GTValues.L))
                    .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.audioCable", 1L))
                    .duration(3 * SECONDS + 4 * TICKS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.Silver, 1),
                            GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Paper, 1))
                    .fluidInputs(Materials.Lead.getMolten(GTValues.L))
                    .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.audioCable", 1L))
                    .duration(3 * SECONDS + 4 * TICKS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);
        }
    }

    private void makeSolderingAlloyRecipes() {
        for (Materials tMat : solderingMaterials) { // TODO dream things using soldering go in here!

            int tMultiplier = tMat.contains(SubTag.SOLDERING_MATERIAL_GOOD) ? 1
                    : tMat.contains(SubTag.SOLDERING_MATERIAL_BAD) ? 4 : 2;

            if (StevesCarts2.isModLoaded()) {

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Coated_Basic.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1),
                                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 8),
                                GTUtility.getIntegratedCircuit(2))
                        .itemOutputs(GTModHandler.getModItem(StevesCarts2.ID, "ModuleComponents", 1L, 9))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(10 * SECONDS)
                        .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Phenolic_Good.get(1L),
                                GTModHandler.getModItem(StevesCarts2.ID, "ModuleComponents", 2L, 9),
                                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 16),
                                GTUtility.getIntegratedCircuit(2))
                        .itemOutputs(GTModHandler.getModItem(StevesCarts2.ID, "ModuleComponents", 1L, 16))
                        .fluidInputs(tMat.getMolten(288L * tMultiplier / 2L)).duration(10 * SECONDS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

            }

            // --- Advanced Solar Panel
            if (AdvancedSolarPanel.isModLoaded()) {
                // Conversion recipes to gt solars
                GTValues.RA.stdBuilder()
                        .itemInputs(GTModHandler.getModItem(AdvancedSolarPanel.ID, "BlockAdvSolarPanel", 1L, 0))
                        .itemOutputs(ItemList.Machine_LV_SolarPanel.get(1L)).duration(1 * SECONDS).eut(TierEU.RECIPE_LV)
                        .addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(GTModHandler.getModItem(AdvancedSolarPanel.ID, "BlockAdvSolarPanel", 1L, 1))
                        .itemOutputs(ItemList.Machine_MV_SolarPanel.get(1L)).duration(1 * SECONDS).eut(TierEU.RECIPE_LV)
                        .addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(GTModHandler.getModItem(AdvancedSolarPanel.ID, "BlockAdvSolarPanel", 1L, 2))
                        .itemOutputs(ItemList.Machine_HV_SolarPanel.get(1L)).duration(1 * SECONDS).eut(TierEU.RECIPE_LV)
                        .addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(GTModHandler.getModItem(AdvancedSolarPanel.ID, "BlockAdvSolarPanel", 1L, 3))
                        .itemOutputs(ItemList.Machine_EV_SolarPanel.get(1L)).duration(1 * SECONDS).eut(TierEU.RECIPE_LV)
                        .addTo(assemblerRecipes);
            }

            // --- Super Solar Panel
            if (SuperSolarPanels.isModLoaded()) {
                // Conversion recipes to gt solars
                GTValues.RA.stdBuilder()
                        .itemInputs(GTModHandler.getModItem(SuperSolarPanels.ID, "SpectralSolarPanel", 1L, 0))
                        .itemOutputs(ItemList.Machine_IV_SolarPanel.get(1L)).duration(1 * SECONDS).eut(TierEU.RECIPE_LV)
                        .addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(GTModHandler.getModItem(SuperSolarPanels.ID, "SingularSolarPanel", 1L, 0))
                        .itemOutputs(ItemList.Machine_LuV_SolarPanel.get(1L)).duration(1 * SECONDS)
                        .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(GTModHandler.getModItem(SuperSolarPanels.ID, "AdminSolarPanel", 1L, 0))
                        .itemOutputs(ItemList.Machine_ZPM_SolarPanel.get(1L)).duration(1 * SECONDS)
                        .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(GTModHandler.getModItem(SuperSolarPanels.ID, "PhotonicSolarPanel", 1L, 0))
                        .itemOutputs(ItemList.Machine_UV_SolarPanel.get(1L)).duration(1 * SECONDS).eut(TierEU.RECIPE_LV)
                        .addTo(assemblerRecipes);
            }

            // GT solars

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Hull_LV.get(1L),
                            ItemList.Cover_SolarPanel_LV.get(1L),
                            ItemList.Robot_Arm_LV.get(1L),
                            ItemList.Battery_RE_LV_Lithium.get(1L),
                            GTUtility.getIntegratedCircuit(2))
                    .itemOutputs(ItemList.Machine_LV_SolarPanel.get(1L))
                    .fluidInputs(tMat.getMolten(288L * tMultiplier / 2L)).duration(40 * SECONDS).eut(TierEU.RECIPE_MV)
                    .addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Hull_MV.get(1L),
                            ItemList.Cover_SolarPanel_MV.get(1L),
                            ItemList.Robot_Arm_MV.get(1L),
                            ItemList.Battery_RE_MV_Lithium.get(1L),
                            GTUtility.getIntegratedCircuit(2))
                    .itemOutputs(ItemList.Machine_MV_SolarPanel.get(1L))
                    .fluidInputs(tMat.getMolten(576L * tMultiplier / 2L)).duration(50 * SECONDS).eut(TierEU.RECIPE_HV)
                    .addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Hull_HV.get(1L),
                            ItemList.Cover_SolarPanel_HV.get(1L),
                            ItemList.Robot_Arm_HV.get(1L),
                            ItemList.Battery_RE_HV_Lithium.get(1L),
                            GTUtility.getIntegratedCircuit(2))
                    .itemOutputs(ItemList.Machine_HV_SolarPanel.get(1L))
                    .fluidInputs(tMat.getMolten(864L * tMultiplier / 2L)).duration(60 * SECONDS).eut(TierEU.RECIPE_EV)
                    .addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Hull_EV.get(1L),
                            ItemList.Cover_SolarPanel_EV.get(1L),
                            ItemList.Robot_Arm_EV.get(1L),
                            ItemList.BatteryHull_EV_Full.get(1L),
                            GTUtility.getIntegratedCircuit(2))
                    .itemOutputs(ItemList.Machine_EV_SolarPanel.get(1L))
                    .fluidInputs(tMat.getMolten(1152L * tMultiplier / 2L)).duration(1 * MINUTES + 10 * SECONDS)
                    .eut(TierEU.RECIPE_IV).addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Hull_IV.get(1L),
                            ItemList.Cover_SolarPanel_IV.get(1L),
                            ItemList.Robot_Arm_IV.get(1L),
                            ItemList.BatteryHull_IV_Full.get(1L),
                            GTUtility.getIntegratedCircuit(2))
                    .itemOutputs(ItemList.Machine_IV_SolarPanel.get(1L))
                    .fluidInputs(tMat.getMolten(1440 * tMultiplier / 2L)).duration(1 * MINUTES + 20 * SECONDS)
                    .eut(TierEU.RECIPE_LuV).addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Hull_LuV.get(1L),
                            ItemList.Cover_SolarPanel_LuV.get(1L),
                            ItemList.Robot_Arm_LuV.get(1L),
                            ItemList.BatteryHull_LuV_Full.get(1L),
                            GTUtility.getIntegratedCircuit(2))
                    .itemOutputs(ItemList.Machine_LuV_SolarPanel.get(1L))
                    .fluidInputs(tMat.getMolten(1728 * tMultiplier / 2L)).duration(1 * MINUTES + 30 * SECONDS)
                    .eut(TierEU.RECIPE_ZPM).addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Hull_ZPM.get(1L),
                            ItemList.Cover_SolarPanel_ZPM.get(1L),
                            ItemList.Robot_Arm_ZPM.get(1L),
                            ItemList.BatteryHull_ZPM_Full.get(1L),
                            GTUtility.getIntegratedCircuit(2))
                    .itemOutputs(ItemList.Machine_ZPM_SolarPanel.get(1L))
                    .fluidInputs(tMat.getMolten(2016 * tMultiplier / 2L)).duration(1 * MINUTES + 40 * SECONDS)
                    .eut(TierEU.RECIPE_UV).addTo(assemblerRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Hull_UV.get(1L),
                            ItemList.Cover_SolarPanel_UV.get(1L),
                            ItemList.Robot_Arm_UV.get(1L),
                            ItemList.BatteryHull_UV_Full.get(1L),
                            GTUtility.getIntegratedCircuit(2))
                    .itemOutputs(ItemList.Machine_UV_SolarPanel.get(1L))
                    .fluidInputs(tMat.getMolten(2304 * tMultiplier / 2L)).duration(1 * MINUTES + 50 * SECONDS)
                    .eut(TierEU.RECIPE_UHV).addTo(assemblerRecipes);

            // solar 1EU

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Circuit_Silicon_Wafer.get(2),
                            GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 2L),
                            GTModHandler.getIC2Item("reinforcedGlass", 1L),
                            GTOreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 1L),
                            GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.RedAlloy, 2L),
                            CustomItemList.AluminiumIronPlate.get(1))
                    .itemOutputs(ItemList.Cover_SolarPanel.get(1L)).fluidInputs(tMat.getMolten(72L * tMultiplier / 2L))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
            // solar 8EU

            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 2L),
                            ItemList.Cover_SolarPanel.get(2L),
                            GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Tin, 2L),
                            ItemList.Circuit_Silicon_Wafer.get(1),
                            GTOreDictUnificator.get(OrePrefixes.plate, Materials.GalliumArsenide, 1L),
                            CustomItemList.ReinforcedAluminiumIronPlate.get(1))
                    .itemOutputs(ItemList.Cover_SolarPanel_8V.get(1L))
                    .fluidInputs(tMat.getMolten(72L * tMultiplier / 2L)).duration(20 * SECONDS).eut(TierEU.RECIPE_MV)
                    .addTo(assemblerRecipes);

            if (OpenComputers.isModLoaded()) {

                // display t1

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Casing_MV.get(1L),
                                ItemList.Cover_Screen.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 2L),
                                ItemList.Circuit_Parts_Transistor.get(2L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "screen1", 1L, 0))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS).eut(64)
                        .addTo(assemblerRecipes);
                // display t2

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Casing_HV.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "screen1", 1L, 0),
                                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 2L),
                                ItemList.Circuit_Parts_Transistor.get(4L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // display t3

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Casing_EV.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0),
                                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 2L),
                                ItemList.Circuit_Parts_Transistor.get(8L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "screen3", 1L, 0))
                        .fluidInputs(tMat.getMolten(288L * tMultiplier / 2L)).duration(12 * SECONDS).eut(256)
                        .addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Casing_EV.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0),
                                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 2L),
                                ItemList.Circuit_Parts_TransistorASMD.get(1L),
                                GTUtility.getIntegratedCircuit(2))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "screen3", 1L, 0))
                        .fluidInputs(tMat.getMolten(288L * tMultiplier / 2L)).duration(6 * SECONDS).eut(256)
                        .addTo(assemblerRecipes);
                // angel upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GTModHandler.getModItem(ExtraUtilities.ID, "angelBlock", 1L, 0),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 49))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // hover upgrade Tier 1

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 4L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                ItemList.Electric_Motor_MV.get(1L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 99))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // hover upgrade Tier 2

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 4L),
                                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 8L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                ItemList.Electric_Motor_HV.get(1L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 100))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // battery upgrade 1

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GTModHandler.getModItem(OpenComputers.ID, "capacitor", 1L, 0),
                                ItemList.Circuit_Parts_Transistor.get(2L),
                                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 4L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 63))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // battery upgrade 2

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                                GTModHandler.getModItem(OpenComputers.ID, "capacitor", 2L, 0),
                                ItemList.Circuit_Parts_Transistor.get(4L),
                                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 8L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 64))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // battery upgrade 3

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 2L),
                                GTModHandler.getModItem(OpenComputers.ID, "capacitor", 4L, 0),
                                ItemList.Circuit_Parts_Transistor.get(8L),
                                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 16L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 65))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 2L),
                                GTModHandler.getModItem(OpenComputers.ID, "capacitor", 4L, 0),
                                ItemList.Circuit_Parts_TransistorASMD.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 16L),
                                GTUtility.getIntegratedCircuit(2))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 65))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(6 * SECONDS + 5 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // inventory upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 8L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 53))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // inventory controller upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 53),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 8L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 61))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // tank upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GTModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 8L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 76))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // tank controller upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 76),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 8L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 77))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // beekeper upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                                GTModHandler.getModItem(Forestry.ID, "beealyzer", 1L, 0),
                                ItemList.Sensor_MV.get(2L),
                                GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderEye, 2),
                                GTUtility.getIntegratedCircuit(10))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item.forestry", 1L, 0))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(10 * SECONDS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // configurator upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(OpenComputers.ID, "wrench", 1L, 0),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 115))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // riteg upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getIC2Item("RTGPellets", 1),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 3L, 26),
                                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Lead, 4L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 116))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(15 * SECONDS).eut(256)
                        .addTo(assemblerRecipes);
                // card container 1

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 57))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // card container 2

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 58))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // card container 3

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 59))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // upgrade container 1

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.StainlessSteel, 8L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 54))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // upgrade container 2

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Titanium, 8L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 55))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // upgrade container 3

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 8L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 56))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // database upgrade 1

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 0),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 5),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 78))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // database upgrade 2

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 0),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 6),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 79))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // database upgrade 3

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 0),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 7),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 80))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // experience upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 0),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Emerald, 2L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 52))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // crafting component

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Hull_LV.get(1L),
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                ItemList.Cover_Crafting.get(1L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 14))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // generator upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(EnderIO.ID, "blockStirlingGenerator", 1L, 0),
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 1L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 15))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // leash upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                                new ItemStack(Items.lead, 4),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 85))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // mfu upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(OpenComputers.ID, "adapter", 1L, 0),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 51),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 4L, 96),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 2L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 112))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // navigation upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Hull_MV.get(1L),
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                new ItemStack(Items.compass, 1),
                                new ItemStack(Items.map, 1),
                                GTOreDictUnificator.get(OrePrefixes.cell, Materials.Water, 1L))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 36))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // piston upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                ItemList.Electric_Piston_MV.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 1L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 75))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // sing IO upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                ItemList.Dye_SquidInk.get(1L),
                                new ItemStack(Items.sign, 1),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 35))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);
                // solar upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                ItemList.Cover_SolarPanel_LV.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 34))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // tractor beam upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(OpenComputers.ID, "capacitor", 1L, 0),
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                                ItemList.Electric_Piston_HV.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 4L),
                                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 16L))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 67))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // trading upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 3L, 25),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 3L),
                                ItemList.Electric_Piston_MV.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Emerald, 2L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 110))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // hover Boots

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(OpenComputers.ID, "capacitor", 1L, 0),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 83),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 100),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 4L),
                                ItemList.Electric_Piston_MV.get(2L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "hoverBoots", 1L, W))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(17 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);

                // ME Upgrade 1

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Chrome, 2L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                                GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 41),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item.ae", 1L))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // ME Upgrade 2

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                                GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 41),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item.ae", 1L, 1))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(1024).addTo(assemblerRecipes);
                // ME Upgrade 3

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 2L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                                GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 41),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(OpenComputers.ID, "item.ae", 1L, 2))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(4096).addTo(assemblerRecipes);

            }

            if (Computronics.isModLoaded()) {
                // Camera Upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(Computronics.ID, "computronics.camera", 1L, 0),
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 0))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // Chat Upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(Computronics.ID, "computronics.chatBox", 1L, 0),
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 1))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // Radar Upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(Computronics.ID, "computronics.radar", 1L, 0),
                                ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                ItemList.Circuit_Parts_TransistorSMD.get(4L),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 4L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 48),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 2))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(Computronics.ID, "computronics.radar", 1L, 0),
                                ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                ItemList.Circuit_Parts_TransistorASMD.get(2L),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 4L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 48),
                                GTUtility.getIntegratedCircuit(2))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 2))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // Particel Card

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                                new ItemStack(Items.firework_charge, 1, W),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 3))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // Spoofing Card

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 11),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 50),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Clay, 2L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 4))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // Beep Card

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                                GTModHandler.getModItem(Computronics.ID, "computronics.speaker", 1L, 0),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 5))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // Self Destruction Card

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 66),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                                GTModHandler.getModItem(IndustrialCraft2.ID, "blockITNT", 2L, 0),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 6))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // Colorful Upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L),
                                GTModHandler.getModItem(Computronics.ID, "computronics.colorfulLamp", 1L, 0),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 4L, 96),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 7))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // Noise Card

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 5),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 1),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 27),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 2L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 8))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // Sound Card

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 8),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 38),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 29),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 9))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(1024).addTo(assemblerRecipes);
                // Light Board

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTModHandler.getModItem(Computronics.ID, "computronics.colorfulLamp", 1L, 0),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 2L),
                                ItemList.Dye_SquidInk.get(4L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 10))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // Server Selfdestructor

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 2L, 6),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                ItemList.Dye_SquidInk.get(4L),
                                GTModHandler.getModItem(IndustrialCraft2.ID, "blockITNT", 2L, 0),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 11))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // Rack Capacitor

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "capacitor", 1L, 0),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 2L),
                                ItemList.Dye_SquidInk.get(4L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 12))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // Switch Board

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                new ItemStack(Blocks.stone_button, 64, 30720),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 2L),
                                ItemList.Dye_SquidInk.get(4L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 13))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // Speech Upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(Computronics.ID, "computronics.speechBox", 1L, 0),
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 14))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(256).addTo(assemblerRecipes);
                // Drone Docking Station

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GTModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L),
                                GTOreDictUnificator.get(OrePrefixes.plate, Materials.EnderPearl, 2L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.droneStation", 1L, 0))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
                // Drone Docking Station Upgrade

                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTModHandler.getModItem(Computronics.ID, "computronics.droneStation", 1L, 0),
                                GTModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L),
                                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GTUtility.getIntegratedCircuit(1))
                        .itemOutputs(GTModHandler.getModItem(Computronics.ID, "computronics.dockingUpgrade", 1L, 0))
                        .fluidInputs(tMat.getMolten(144L * tMultiplier / 2L)).duration(12 * SECONDS + 10 * TICKS)
                        .eut(TierEU.RECIPE_HV).addTo(assemblerRecipes);
            }
        }
    }
}
