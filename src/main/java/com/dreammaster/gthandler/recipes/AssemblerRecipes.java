package com.dreammaster.gthandler.recipes;

import static com.dreammaster.bartworksHandler.BartWorksMaterials.getBartWorksMaterialByIGNName;
import static com.dreammaster.gthandler.GT_CoreModSupport.Xenoxene;
import static gregtech.api.enums.GT_Values.W;
import static gregtech.api.enums.Mods.*;
import static gregtech.api.util.GT_ModHandler.getModItem;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sAssemblerRecipes;
import static gregtech.api.util.GT_RecipeBuilder.MINUTES;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_RecipeBuilder.TICKS;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.dreammaster.gthandler.CustomItemList;
import com.github.bartimaeusnek.bartworks.common.loaders.ItemRegistry;

import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.GT_MetaGenerated_Tool_01;

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

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockAlloyGlass", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 4L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Empty180SpCell.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Empty180SpCell.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 3L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(CustomItemList.Empty360SpCell.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(10 * SECONDS).eut(256).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Empty180SpCell.get(3L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 6L),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.Empty540SpCell.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Empty180SpCell.get(1L),
                        CustomItemList.Empty360SpCell.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 7L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Empty540SpCell.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(5 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Empty540SpCell.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 6L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.ElectrumFlux, 1L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(CustomItemList.Empty1080SpCell.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(10 * SECONDS).eut(1024).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Polybenzimidazole, 1L),
                        GT_ModHandler.getIC2Item("carbonMesh", 1L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Duct_Tape.get(2L)).fluidInputs(Materials.AdvancedGlue.getFluid(200))
                .noFluidOutputs().duration(6 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        // 10,30 and 60K NAK Cells

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemCellEmpty", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 4L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.TenKCell.get(1L)).noFluidInputs().noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.TenKCell.get(3L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 6L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.ThirtyKCell.get(1L)).noFluidInputs().noFluidOutputs().duration(15 * SECONDS)
                .eut(60).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemCellEmpty", 3L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 6L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.ThirtyKCell.get(1L)).noFluidInputs().noFluidOutputs().duration(15 * SECONDS)
                .eut(60).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.ThirtyKCell.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 8L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.SixtyKCell.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemCellEmpty", 6L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 8L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.SixtyKCell.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorLuV, 64L),
                CustomItemList.MicaInsulatorFoil.get(48L),
                Materials.Trinium.getMolten(1440L),
                ItemList.Casing_Coil_Superconductor.get(1L),
                1000,
                9001);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorZPM, 32L),
                CustomItemList.MicaInsulatorFoil.get(32L),
                Materials.Trinium.getMolten(576L),
                ItemList.Casing_Coil_Superconductor.get(1L),
                1000,
                9001);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUV, 16L),
                CustomItemList.MicaInsulatorFoil.get(16L),
                Materials.Trinium.getMolten(288L),
                ItemList.Casing_Coil_Superconductor.get(1L),
                1000,
                9001);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUHV, 8L),
                CustomItemList.MicaInsulatorFoil.get(8L),
                Materials.Trinium.getMolten(144L),
                ItemList.Casing_Coil_Superconductor.get(1L),
                1000,
                9001);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUEV, 4L),
                CustomItemList.MicaInsulatorFoil.get(4L),
                Materials.Trinium.getMolten(72L),
                ItemList.Casing_Coil_Superconductor.get(1L),
                1000,
                9001);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUIV, 2L),
                CustomItemList.MicaInsulatorFoil.get(2L),
                Materials.Trinium.getMolten(36L),
                ItemList.Casing_Coil_Superconductor.get(1L),
                1000,
                9001);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUMV, 1L),
                CustomItemList.MicaInsulatorFoil.get(1L),
                Materials.Trinium.getMolten(18L),
                ItemList.Casing_Coil_Superconductor.get(1L),
                1000,
                9001);

        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.SteelBars.get(4L),
                400,
                48);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.AluminiumBars.get(4L),
                400,
                64);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.StainlessSteelBars.get(4L),
                400,
                96);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.TitaniumBars.get(4L),
                400,
                120);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Tungsten, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.TungstenBars.get(4L),
                400,
                192);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.TungstenSteelBars.get(4L),
                400,
                256);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.ChromeBars.get(4L),
                400,
                480);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.IridiumBars.get(4L),
                400,
                1024);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Osmium, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.OsmiumBars.get(4L),
                400,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Neutronium, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.NeutroniumBars.get(4L),
                400,
                4096);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.RedstoneAlloy, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.RedstoneAlloyBars.get(4L),
                400,
                48);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.ElectricalSteel, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.ElectricalSteelBars.get(4L),
                400,
                64);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.ConductiveIron, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.ConductiveIronBars.get(4L),
                400,
                160);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.EnergeticAlloy, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.EnergeticAlloyBars.get(4L),
                400,
                384);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.VibrantAlloy, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.VibrantAlloyBars.get(4L),
                400,
                768);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.PulsatingIron, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.PulsatingIronBars.get(4L),
                400,
                120);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Soularium, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.SoulariumBars.get(4L),
                400,
                64);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.EnderiumBase, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.EnderiumBaseBars.get(4L),
                400,
                256);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Enderium, 3L),
                GT_Utility.getIntegratedCircuit(3),
                CustomItemList.EnderiumBars.get(4L),
                400,
                1024);
        GT_Values.RA.addFormingPressRecipe(
                GT_ModHandler.getIC2Item("carbonPlate", 4L),
                CustomItemList.MoldBoots.get(0L),
                CustomItemList.CarbonPartBoots.get(1L),
                400,
                120);
        GT_Values.RA.addFormingPressRecipe(
                GT_ModHandler.getIC2Item("carbonPlate", 8L),
                CustomItemList.MoldChestplate.get(0L),
                CustomItemList.CarbonPartChestplate.get(1L),
                400,
                120);
        GT_Values.RA.addFormingPressRecipe(
                GT_ModHandler.getIC2Item("carbonPlate", 5L),
                CustomItemList.MoldHelmet.get(0L),
                CustomItemList.CarbonPartHelmet.get(1L),
                400,
                120);
        GT_Values.RA.addFormingPressRecipe(
                GT_ModHandler.getIC2Item("carbonPlate", 7L),
                CustomItemList.MoldLeggings.get(0L),
                CustomItemList.CarbonPartLeggings.get(1L),
                400,
                120);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorRubBoots", 1L, 0),
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoBoots", 1L, W),
                CustomItemList.NanoRubberBoots.get(1L),
                800,
                1024);

        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoLegs", 1L, W),
                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.StainlessSteel, 4L),
                CustomItemList.NanoPlatedLeggings.get(1L),
                800,
                1024);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoHelmet", 1L, W),
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorHazmatHelmet", 1L, 0),
                CustomItemList.NanoScubaHelmet.get(1L),
                800,
                1024);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.NanoRubberBoots.get(1L),
                CustomItemList.IridiumAlloyItemCasing.get(4L),
                CustomItemList.QuantumPartBoots.get(1L),
                1200,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem(GraviSuite.ID, "advNanoChestPlate", 1L, W),
                CustomItemList.IridiumAlloyItemCasing.get(8L),
                CustomItemList.QuantumPartChestplate.get(1L),
                1200,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.NanoScubaHelmet.get(1L),
                CustomItemList.IridiumAlloyItemCasing.get(5L),
                CustomItemList.QuantumPartHelmetEmpty.get(1L),
                1200,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.QuantumPartHelmetEmpty.get(1L),
                CustomItemList.ReinforcedGlassLense.get(8L),
                CustomItemList.QuantumPartHelmet.get(1L),
                1200,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.NanoPlatedLeggings.get(1L),
                CustomItemList.IridiumAlloyItemCasing.get(7L),
                CustomItemList.QuantumPartLeggings.get(1L),
                1200,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.NagaScaleChip.get(4L),
                GT_Utility.getIntegratedCircuit(4),
                Materials.Thaumium.getMolten(72L),
                CustomItemList.NagaScaleFragment.get(1L),
                200,
                48);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.NagaScaleFragment.get(6L),
                GT_Utility.getIntegratedCircuit(6),
                Materials.Thaumium.getMolten(216L),
                GT_ModHandler.getModItem(TwilightForest.ID, "item.nagaScale", 1L, 0),
                200,
                96);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.LichBoneChip.get(4L),
                GT_Utility.getIntegratedCircuit(4),
                Materials.Thaumium.getMolten(72L),
                CustomItemList.LichBoneFragment.get(1L),
                250,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.LichBoneFragment.get(6L),
                GT_Utility.getIntegratedCircuit(6),
                Materials.Thaumium.getMolten(216L),
                CustomItemList.LichBone.get(1L),
                250,
                256);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem(Thaumcraft.ID, "ItemResource", 4L, 14),
                GT_ModHandler.getModItem(TwilightForest.ID, "item.trophy", 0L, 2),
                CustomItemList.LichBone.get(1L),
                900,
                256);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CarminiteChip.get(4L),
                GT_Utility.getIntegratedCircuit(4),
                Materials.Thaumium.getMolten(72L),
                CustomItemList.CarminiteFragment.get(1L),
                350,
                1024);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CarminiteFragment.get(6L),
                GT_Utility.getIntegratedCircuit(6),
                Materials.Thaumium.getMolten(216L),
                GT_ModHandler.getModItem(TwilightForest.ID, "item.carminite", 1L, 0),
                350,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.SnowQueenBloodDrop.get(24L),
                GT_Utility.getIntegratedCircuit(24),
                Materials.Thaumium.getMolten(216L),
                CustomItemList.SnowQueenBlood.get(1L),
                400,
                4096);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem(Thaumcraft.ID, "ItemResource", 10L, 14),
                GT_ModHandler.getModItem(TwilightForest.ID, "item.trophy", 0L, 4),
                CustomItemList.SnowQueenBlood.get(1L),
                1800,
                7680);
        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem(Thaumcraft.ID, "ItemResource", 16L, 14),
                GT_ModHandler.getModItem(Witchery.ID, "ingredient", 1L, 130),
                GT_OreDictUnificator.get(OrePrefixes.gem, Materials.NetherStar, 1),
                900,
                256);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1L),
                GT_OreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 3L),
                Materials.Iron.getMolten(32L),
                CustomItemList.WoodenCasing.get(1L),
                100,
                8);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.EngineCore.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),
                        GT_Utility.getIntegratedCircuit(10))
                .itemOutputs(GT_ModHandler.getModItem(Forestry.ID, "engine", 1L, 0)).noFluidInputs().noFluidOutputs()
                .duration(10 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.EngineCore.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 10L),
                        GT_Utility.getIntegratedCircuit(10))
                .itemOutputs(GT_ModHandler.getModItem(Forestry.ID, "engine", 1L, 1)).noFluidInputs().noFluidOutputs()
                .duration(10 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.EngineCore.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 10L),
                        GT_Utility.getIntegratedCircuit(10))
                .itemOutputs(GT_ModHandler.getModItem(Forestry.ID, "engine", 1L, 2)).noFluidInputs().noFluidOutputs()
                .duration(10 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.EngineCore.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 10L),
                        GT_Utility.getIntegratedCircuit(10))
                .itemOutputs(GT_ModHandler.getModItem(BuildCraftCore.ID, "engineBlock", 1L, 2)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.EngineCore.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 10L),
                        GT_Utility.getIntegratedCircuit(10))
                .itemOutputs(GT_ModHandler.getModItem(Forestry.ID, "engine", 1L, 4)).noFluidInputs().noFluidOutputs()
                .duration(10 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        // HEE Ectoplasma and Arcane Shards
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.EctoplasmaChip.get(4L),
                GT_Utility.getIntegratedCircuit(4),
                FluidRegistry.getFluidStack("endergoo", 100),
                CustomItemList.EctoplasmaFragment.get(1L),
                200,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.EctoplasmaFragment.get(6L),
                GT_Utility.getIntegratedCircuit(6),
                FluidRegistry.getFluidStack("endergoo", 200),
                GT_ModHandler.getModItem(HardcoreEnderExpansion.ID, "endoplasm", 1L, 0),
                200,
                240);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.ArcaneShardChip.get(4L),
                GT_Utility.getIntegratedCircuit(4),
                FluidRegistry.getFluidStack("endergoo", 100),
                CustomItemList.ArcaneShardFragment.get(1L),
                200,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.ArcaneShardFragment.get(6L),
                GT_Utility.getIntegratedCircuit(6),
                FluidRegistry.getFluidStack("endergoo", 200),
                GT_ModHandler.getModItem(HardcoreEnderExpansion.ID, "arcane_shard", 1L, 0),
                200,
                240);
        // HEE Runes
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.RuneOfPowerFragment.get(9L),
                GT_Utility.getIntegratedCircuit(9),
                FluidRegistry.getFluidStack("endergoo", 1000),
                GT_ModHandler.getModItem(HardcoreEnderExpansion.ID, "rune", 1L, 0),
                2400,
                7680);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.RuneOfAgilityFragment.get(9L),
                GT_Utility.getIntegratedCircuit(9),
                FluidRegistry.getFluidStack("endergoo", 1000),
                GT_ModHandler.getModItem(HardcoreEnderExpansion.ID, "rune", 1L, 1),
                2400,
                7680);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.RuneOfVigorFragment.get(9L),
                GT_Utility.getIntegratedCircuit(9),
                FluidRegistry.getFluidStack("endergoo", 1000),
                GT_ModHandler.getModItem(HardcoreEnderExpansion.ID, "rune", 1L, 2),
                2400,
                7680);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.RuneOfDefenseFragment.get(9L),
                GT_Utility.getIntegratedCircuit(9),
                FluidRegistry.getFluidStack("endergoo", 1000),
                GT_ModHandler.getModItem(HardcoreEnderExpansion.ID, "rune", 1L, 3),
                2400,
                7680);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.RuneOfMagicFragment.get(9L),
                GT_Utility.getIntegratedCircuit(9),
                FluidRegistry.getFluidStack("endergoo", 1000),
                GT_ModHandler.getModItem(HardcoreEnderExpansion.ID, "rune", 1L, 4),
                2400,
                7680);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.RuneOfVoidFragment.get(9L),
                GT_Utility.getIntegratedCircuit(9),
                FluidRegistry.getFluidStack("endergoo", 1000),
                GT_ModHandler.getModItem(HardcoreEnderExpansion.ID, "rune", 1L, 5),
                2400,
                7680);

        // IC2 Charging Batteries
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatREDischarged", 4L, W),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatChargeRE", 1L, 0))
                .fluidInputs(Materials.SolderingAlloy.getMolten(144L)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemAdvBat", 1L, W),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemAdvBat", 1L, W),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemAdvBat", 1L, W),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemAdvBat", 1L, W),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchSpread", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitch", 1L, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Copper, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatChargeAdv", 1L, 0))
                .fluidInputs(Materials.SolderingAlloy.getMolten(288L)).noFluidOutputs().duration(20 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatCrystal", 1L, W),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatCrystal", 1L, W),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatCrystal", 1L, W),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatCrystal", 1L, W),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchSpread", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchSpread", 1L, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.Gold, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatChargeCrystal", 1L, 0))
                .fluidInputs(Materials.SolderingAlloy.getMolten(576L)).noFluidOutputs().duration(40 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatLamaCrystal", 1L, W),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatLamaCrystal", 1L, W),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatLamaCrystal", 1L, W),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatLamaCrystal", 1L, W),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 1L, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.Aluminium, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatChargeLamaCrystal", 1L, 0))
                .fluidInputs(Materials.SolderingAlloy.getMolten(1440L)).noFluidOutputs().duration(60 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Steel, 16L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 4L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.AdsorptionFilterCasing.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 4L),
                        ItemList.Electric_Motor_MV.get(4L),
                        ItemList.Electric_Pump_MV.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Cobalt, 4L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OilDrill1.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(144))
                .noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.OilDrill1.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4L),
                        ItemList.Electric_Motor_HV.get(4L),
                        ItemList.Electric_Pump_HV.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.BlueSteel, 8L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OilDrill2.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(288))
                .noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.OilDrill2.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 12L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4L),
                        ItemList.Electric_Motor_EV.get(4L),
                        ItemList.Electric_Pump_EV.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Titanium, 12L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OilDrill3.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(576))
                .noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.OilDrill3.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 16L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4L),
                        ItemList.Electric_Motor_IV.get(4L),
                        ItemList.Electric_Pump_IV.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.TungstenSteel, 16L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OilDrill4.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(1440))
                .noFluidOutputs().duration(20 * SECONDS).eut(7860).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4L),
                        ItemList.Electric_Motor_EV.get(4L),
                        ItemList.Electric_Pump_EV.get(4L),
                        ItemList.Conveyor_Module_EV.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Tungsten, 4L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OreDrill1.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(72))
                .noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.OreDrill1.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4L),
                        ItemList.Electric_Motor_IV.get(4L),
                        ItemList.Electric_Pump_IV.get(4L),
                        ItemList.Conveyor_Module_IV.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Iridium, 4L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OreDrill2.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(144))
                .noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.OreDrill2.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Osmiridium, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4L),
                        ItemList.Electric_Motor_LuV.get(4L),
                        ItemList.Electric_Pump_LuV.get(4L),
                        ItemList.Conveyor_Module_LuV.get(4L),
                        GT_OreDictUnificator.get(
                                OrePrefixes.gearGt,
                                BartWorks.isModLoaded() ? getBartWorksMaterialByIGNName("Rhodium-Plated Palladium")
                                        : Materials.Chrome,
                                4L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OreDrill3.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(288))
                .noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_LuV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.OreDrill3.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Europium, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 4L),
                        ItemList.Electric_Motor_ZPM.get(4L),
                        ItemList.Electric_Pump_ZPM.get(4L),
                        ItemList.Conveyor_Module_ZPM.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.MysteriousCrystal, 4L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.OreDrill4.get(1L)).fluidInputs(Materials.SolderingAlloy.getMolten(576))
                .noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_ZPM).addTo(sAssemblerRecipes);

        // LV Turbines
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.turbineBlade, Materials.Iron, 4L),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 1L),
                GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(170, 1, Materials.Iron, Materials.Steel, null),
                320,
                30);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.turbineBlade, Materials.WroughtIron, 4L),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 1L),
                GT_MetaGenerated_Tool_01.INSTANCE
                        .getToolWithStats(170, 1, Materials.WroughtIron, Materials.Steel, null),
                320,
                30);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.turbineBlade, Materials.Bronze, 4L),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 1L),
                GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(170, 1, Materials.Bronze, Materials.Steel, null),
                320,
                30);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.turbineBlade, Materials.Steel, 4L),
                GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 1L),
                GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(170, 1, Materials.Steel, Materials.Steel, null),
                320,
                30);

        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.LedoxCompressedPlate.get(1L),
                CustomItemList.CallistoIceCompressedPlate.get(1L),
                new FluidStack(FluidRegistry.getFluid("ic2coolant"), 1000),
                CustomItemList.IceCompressedPlate.get(2L),
                300,
                7680);

        // UEV-UXV casings+hulls
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(CustomItemList.BedrockiumPlate.get(8L)),
                GT_Utility.getIntegratedCircuit(8),
                CustomItemList.Casing_UEV.get(1L),
                50,
                16);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 8L),
                GT_Utility.getIntegratedCircuit(8),
                CustomItemList.Casing_UIV.get(1L),
                50,
                16);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.plate, MaterialsUEVplus.SpaceTime, 8L),
                GT_Utility.getIntegratedCircuit(8),
                CustomItemList.Casing_UMV.get(1L),
                50,
                16);
        if (GoodGenerator.isModLoaded()) {
            GT_Values.RA.stdBuilder().itemInputs(
                    GT_OreDictUnificator
                            .get(OrePrefixes.plate, MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter, 4L),
                    GT_OreDictUnificator.get("plateShirabon", 4),
                    GT_Utility.getIntegratedCircuit(8)).itemOutputs(CustomItemList.Casing_UXV.get(1L)).noFluidInputs()
                    .noFluidOutputs().duration(2 * SECONDS + 10 * TICKS).eut(16).addTo(sAssemblerRecipes);

        }
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.Draconium, 2L),
                CustomItemList.Casing_UEV.get(1L),
                Materials.Polybenzimidazole.getMolten(576L),
                CustomItemList.Hull_UEV.get(1L),
                50,
                16);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.NetherStar, 2L),
                CustomItemList.Casing_UIV.get(1L),
                Materials.Polybenzimidazole.getMolten(576L),
                CustomItemList.Hull_UIV.get(1L),
                50,
                16);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.wireGt12, Materials.Quantium, 2L),
                CustomItemList.Casing_UMV.get(1L),
                Materials.Polybenzimidazole.getMolten(576L),
                CustomItemList.Hull_UMV.get(1L),
                50,
                16);
        GT_Values.RA.addAssemblerRecipe(
                GT_OreDictUnificator.get(OrePrefixes.wireGt16, Materials.BlackPlutonium, 2L),
                CustomItemList.Casing_UXV.get(1L),
                MaterialsKevlar.Kevlar.getMolten(576L),
                CustomItemList.Hull_UXV.get(1L),
                50,
                16);

        // Hermetic casings

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Plastic, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 8L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_1.get(1L)).noFluidInputs().noFluidOutputs().duration(5 * SECONDS)
                .eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.PolyvinylChloride, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 8L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_2.get(1L)).noFluidInputs().noFluidOutputs().duration(5 * SECONDS)
                .eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Polytetrafluoroethylene, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 8L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_3.get(1L)).noFluidInputs().noFluidOutputs().duration(5 * SECONDS)
                .eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.StainlessSteel, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_4.get(1L)).noFluidInputs().noFluidOutputs().duration(5 * SECONDS)
                .eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Titanium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 8L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_5.get(1L)).noFluidInputs().noFluidOutputs().duration(5 * SECONDS)
                .eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.TungstenSteel, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 8L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_6.get(1L)).noFluidInputs().noFluidOutputs().duration(5 * SECONDS)
                .eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.NiobiumTitanium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 8L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_7.get(1L)).noFluidInputs().noFluidOutputs().duration(5 * SECONDS)
                .eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Enderium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Osmium, 8L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_8.get(1L)).noFluidInputs().noFluidOutputs().duration(5 * SECONDS)
                .eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Naquadah, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 8L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_9.get(1L)).noFluidInputs().noFluidOutputs().duration(5 * SECONDS)
                .eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.MysteriousCrystal, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Bedrockium, 8L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Casing_Tank_10.get(1L)).noFluidInputs().noFluidOutputs().duration(5 * SECONDS)
                .eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.addAssemblerRecipe(
                new ItemStack(Items.diamond, 1, 0),
                ItemList.Circuit_Basic.get(4L),
                CustomItemList.TwilightCrystal.get(1L),
                600,
                16);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack(Items.diamond, 1, 0),
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 4L, 0),
                CustomItemList.TwilightCrystal.get(1L),
                600,
                16);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(IronTanks.ID, "goldDiamondUpgrade", 1L, 0))
                .fluidInputs(Materials.Glass.getMolten(72L)).noFluidOutputs().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.RawLapotronCrystal.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2L))
                .itemOutputs(GT_ModHandler.getIC2Item("lapotronCrystal", 1L, 26)).noFluidInputs().noFluidOutputs()
                .duration(16 * SECONDS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        GT_Values.RA.addAssemblerRecipe(
                ItemList.Firebrick.get(24),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gypsum, 8L),
                new FluidStack(FluidRegistry.getFluid("concrete"), 4608),
                ItemList.Casing_Firebricks.get(4L),
                200,
                30);
        GT_Values.RA.addAssemblerRecipe(
                ItemList.Firebrick.get(24),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gypsum, 8L),
                Materials.Concrete.getMolten(4608L),
                ItemList.Casing_Firebricks.get(4L),
                200,
                30);

        GT_Values.RA.stdBuilder().itemInputs(new ItemStack(Blocks.brick_block), Materials.AnyBronze.getPlates(6))
                .itemOutputs(ItemList.Casing_BronzePlatedBricks.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        Materials.Steel.getPlates(4),
                        Materials.Iron.getPlates(2),
                        ItemList.Casing_BronzePlatedBricks.get(1L))
                .itemOutputs(CustomItemList.Casing_Pyrolyse.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        // Extreme Engine Intake Casing
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Casing_RobustTungstenSteel.get(1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 4))
                .itemOutputs(ItemList.Casing_ExtremeEngineIntake.get(1)).noFluidInputs().noFluidOutputs()
                .duration(5 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        // Filter Machine Casing for cleanroom
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1L),
                        ItemList.Electric_Motor_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 1L),
                        ItemList.Component_Filter.get(2L),
                        CustomItemList.SteelBars.get(2L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Casing_Vent.get(1L)).noFluidInputs().noFluidOutputs().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Carbon, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glowstone, 1L),
                        GT_ModHandler.getModItem(FloodLights.ID, "electricIncandescentLightBulb", 1L, 0),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(GalacticraftCore.ID, "tile.glowstoneTorch", 32L, 0))
                .noFluidInputs().noFluidOutputs().duration(20 * SECONDS).eut(192).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.log, Materials.Wood, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.treeSapling, 4L),
                        new ItemStack(Items.compass, 1, 0))
                .itemOutputs(GT_ModHandler.getModItem(NaturesCompass.ID, "NaturesCompass", 1L, 0))
                .fluidInputs(Materials.Glue.getFluid(144L)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.addAssemblerRecipe(
                new ItemStack(Items.string, 3, 0),
                GT_Utility.getIntegratedCircuit(1),
                new ItemStack(Blocks.wool, 1, 0),
                100,
                4);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.CarbonPartHelmet.get(1L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemNightvisionGoggles", 1L, W),
                        CustomItemList.NanoCrystal.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.EnergeticAlloy, 64L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoHelmet", 1))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(288L)).noFluidOutputs().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.CarbonPartChestplate.get(1L),
                        CustomItemList.NanoCrystal.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.EnergeticAlloy, 64L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoChestplate", 1))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(1440L)).noFluidOutputs().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.CarbonPartLeggings.get(1L),
                        CustomItemList.NanoCrystal.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.EnergeticAlloy, 64L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoLegs", 1))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(576L)).noFluidOutputs().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.CarbonPartBoots.get(1L),
                        CustomItemList.NanoCrystal.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.EnergeticAlloy, 64L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorNanoBoots", 1))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(288L)).noFluidOutputs().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),
                        GT_ModHandler.getModItem(IronChests.ID, "BlockIronChest", 1L, 0))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockPersonal", 1L, 0)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Covers

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        Materials.Aluminium.getPlates(4),
                        Materials.Glass.getPlates(1),
                        Materials.Glowstone.getDust(1),
                        new ItemStack(Items.dye, 1, 1),
                        new ItemStack(Items.dye, 1, 10),
                        new ItemStack(Items.dye, 1, 4))
                .itemOutputs(ItemList.Cover_Screen.get(1L)).noFluidInputs().noFluidOutputs().duration(5 * SECONDS)
                .eut(5).addTo(sAssemblerRecipes);
        // Nukes

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(21),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Lead, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Titanium, 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockMachine", 1L, 12))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockReactorChamber", 1L)).noFluidInputs()
                .noFluidOutputs().duration(60 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(22),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Lead, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Titanium, 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockReactorChamber", 3L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.Platinum, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1L))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockGenerator", 1L, 5)).noFluidInputs()
                .noFluidOutputs().duration(60 * SECONDS).eut(960).addTo(sAssemblerRecipes);

        if (AdvancedSolarPanel.isModLoaded()) {
            // Irradiant Glass Pane
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_ModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 4L, 2),
                            GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockAlloyGlass", 4L, 0),
                            Materials.Glowstone.getPlates(1),
                            GT_Utility.getIntegratedCircuit(1))
                    .itemOutputs(GT_ModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 4L, 5))
                    .noFluidInputs().noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_HV)
                    .addTo(sAssemblerRecipes);

            // Irradiant Uranium
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            Materials.Uranium.getIngots(1),
                            GT_ModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 1, 0))
                    .noFluidInputs().noFluidOutputs()
                    .itemOutputs(GT_ModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 1, 2))
                    .duration(30 * SECONDS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        }
        // Solar Light Splitter
        if (SuperSolarPanels.isModLoaded()) {

            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_ModHandler.getModItem(SuperSolarPanels.ID, "bluecomponent", 3L, 0),
                            GT_ModHandler.getModItem(SuperSolarPanels.ID, "redcomponent", 3L, 0),
                            GT_ModHandler.getModItem(SuperSolarPanels.ID, "greencomponent", 3L, 0),
                            GT_Utility.getIntegratedCircuit(1))
                    .itemOutputs(GT_ModHandler.getModItem(SuperSolarPanels.ID, "solarsplitter", 1L, 0)).noFluidInputs()
                    .noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        }
        // Heat Exchangers

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(21),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silver, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Copper, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1L))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitch", 1L, 1)).noFluidInputs()
                .noFluidOutputs().duration(3 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(21),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Copper, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silver, 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitch", 1L, 1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchCore", 1L, 1))
                .noFluidInputs().noFluidOutputs().duration(3 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(21),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitch", 1L, 1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchSpread", 1L, 1))
                .fluidInputs(Materials.StainlessSteel.getMolten(72)).noFluidOutputs().duration(1 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(21),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitch", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitch", 1L, 1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 1L, 1))
                .noFluidInputs().noFluidOutputs().duration(3 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        // Heat Vents

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(21),
                        ItemList.Electric_Motor_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2L),
                        new ItemStack(Blocks.iron_bars, 2))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorVent", 1L, 1)).noFluidInputs()
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(22),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Copper, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silver, 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorVent", 1L, 1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorVentCore", 1L, 1)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(256).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(21),
                        ItemList.IC2_Industrial_Diamond.get(1L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorVent", 1L, 1),
                        CustomItemList.StainlessSteelBars.get(4L))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorVentDiamond", 1L, 1)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(256).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(22),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorVentDiamond", 1L, 1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorVentGold", 1L, 1))
                .fluidInputs(Materials.StainlessSteel.getMolten(72)).noFluidOutputs().duration(20 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(21),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Tin, 2L),
                        CustomItemList.SteelBars.get(2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorVent", 1L, 1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorVentSpread", 1L)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(256).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(21),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 4L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchCore", 2L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorVentCore", 1L, 1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorCondensator", 1L, 1)).noFluidInputs()
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(21),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Lapis, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.RedAlloy, 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorVentGold", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchSpread", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorCondensator", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorCondensator", 1L, 1))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorCondensatorLap", 1L, 1))
                .noFluidInputs().noFluidOutputs().duration(45 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        // Easier chest upgrades

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.chest, Materials.Copper, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Iron, 1L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.chest, Materials.Iron, 1L)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.chest, Materials.Copper, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 1L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.chest, Materials.Steel, 1L)).noFluidInputs()
                .noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.chest, Materials.Iron, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Gold, 1L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.chest, Materials.Gold, 1L)).noFluidInputs()
                .noFluidOutputs().duration(25 * SECONDS).eut(64).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.chest, Materials.Steel, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Gold, 1L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.chest, Materials.Gold, 1L)).noFluidInputs()
                .noFluidOutputs().duration(25 * SECONDS).eut(64).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.chest, Materials.Gold, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 2L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.chest, Materials.Diamond, 1L)).noFluidInputs()
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // Reactor Plating

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(22),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Lead, 1L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4L))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorPlating", 1L)).noFluidInputs()
                .noFluidOutputs().duration(20 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(21),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Copper, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Copper, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silver, 1L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorPlating", 1L))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorPlatingHeat", 1L)).noFluidInputs()
                .noFluidOutputs().duration(30 * SECONDS).eut(256).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(23),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Lead, 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorPlating", 1L))
                .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorPlatingExplosive", 1L))
                .noFluidInputs().noFluidOutputs().duration(30 * SECONDS).eut(256).addTo(sAssemblerRecipes);
        // LV and MV Energy Hatches

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(4),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2),
                        ItemList.Electric_Pump_LV.get(1),
                        ItemList.Hull_LV.get(1),
                        ItemList.LV_Coil.get(2),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1))
                .itemOutputs(ItemList.Hatch_Energy_LV.get(1)).fluidInputs(Materials.Lubricant.getFluid(2000))
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_Utility.getIntegratedCircuit(4),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1),
                        ItemList.Electric_Pump_MV.get(1),
                        ItemList.Hull_MV.get(1),
                        ItemList.MV_Coil.get(2),
                        ItemList.Circuit_Chip_ULPIC.get(2))
                .itemOutputs(ItemList.Hatch_Energy_MV.get(1)).fluidInputs(Materials.Lubricant.getFluid(2000))
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        // Neutron reflector recipes
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Copper, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 16L),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                1200,
                480,
                true);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Beryllium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 8L),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                900,
                480,
                true);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenCarbide, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 4L),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                400,
                480,
                true);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Beryllium, 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                600,
                1920,
                true);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenCarbide, 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflector", 1L, 1),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                600,
                1920,
                true);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Iridium, 2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorReflectorThick", 1L, 0),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                ItemList.Neutron_Reflector.get(1L),
                900,
                7680,
                true);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Iridium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenCarbide, 36L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 32L),
                        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 48L),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 32L),
                        GT_Utility.getIntegratedCircuit(2) },
                GT_Values.NF,
                ItemList.Neutron_Reflector.get(1L),
                3150,
                30720,
                true);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Iridium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Beryllium, 36L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 32L),
                        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 48L),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 64L),
                        GT_Utility.getIntegratedCircuit(2) },
                GT_Values.NF,
                ItemList.Neutron_Reflector.get(1L),
                3750,
                30720,
                true);

        if (BartWorks.isModLoaded()) {
            // Humongous input hatch
            GT_Values.RA.stdBuilder().itemInputs(CustomItemList.Hatch_Input_UXV.get(1), ItemList.Quantum_Tank_IV.get(1))
                    .itemOutputs(ItemRegistry.humongousInputHatch.copy())
                    .fluidInputs(MaterialsUEVplus.Space.getMolten(5_760L)).noFluidOutputs().duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_UMV).addTo(sAssemblerRecipes);

        }

        // Wood Plates
        GT_Recipe.GT_Recipe_Map.sAssemblerRecipes.addRecipe(
                false,
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 64),
                        GT_Utility.getIntegratedCircuit(2) },
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 64) },
                null,
                new FluidStack[] { Materials.Glue.getFluid(144L) },
                null,
                2400,
                30,
                0);

        if (OpenBlocks.isModLoaded() && BloodMagic.isModLoaded()) {
            ItemStack[] trophies = { GT_ModHandler.getModItem(OpenBlocks.ID, "trophy", 1L),
                    GT_ModHandler.getModItem(OpenBlocks.ID, "trophy", 1L),
                    GT_ModHandler.getModItem(OpenBlocks.ID, "trophy", 1L),
                    GT_ModHandler.getModItem(OpenBlocks.ID, "trophy", 1L), };

            NBTTagCompound[] tag = { new NBTTagCompound(), new NBTTagCompound(), new NBTTagCompound(),
                    new NBTTagCompound(), };

            tag[0].setString("entity", "Bat");
            tag[1].setString("entity", "Cow");
            tag[2].setString("entity", "Chicken");
            tag[3].setString("entity", "Sheep");

            ItemStack flightpotion = GT_ModHandler.getModItem(BloodMagic.ID, "alchemyFlask", 1L);
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

            GT_Values.RA.addAssemblerRecipe(
                    trophies[0],
                    new ItemStack(Items.golden_carrot),
                    new ItemStack(Items.potionitem, 1, 8262),
                    120,
                    120);
            GT_Values.RA.addAssemblerRecipe(
                    trophies[1],
                    new ItemStack(Items.iron_sword),
                    new ItemStack(Items.leather, 64),
                    120,
                    120);
            GT_Values.RA.addAssemblerRecipe(trophies[2], new ItemStack(Items.feather), flightpotion, 120, 120);
            GT_Values.RA.addAssemblerRecipe(
                    trophies[3],
                    new ItemStack(Items.shears),
                    new ItemStack(Blocks.wool, 64),
                    120,
                    120);
        }

        if (PamsHarvestCraft.isModLoaded()) {
            for (int i = 0; i < OreDictionary.getOres("cropCotton").size(); ++i) {
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Items.string, 4),
                                OreDictionary.getOres("cropCotton").get(i).splitStack(3) },
                        GT_Values.NF,
                        GT_ModHandler.getModItem(PamsHarvestCraft.ID, "wovencottonItem", 1L, 0),
                        400,
                        30,
                        false);
            }
        }

        if (PamsHarvestCraft.isModLoaded() && Forestry.isModLoaded() && OpenComputers.isModLoaded()) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sugar, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Fuel, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricAcid, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Acetone, 1L),
                            GT_ModHandler.getModItem(PamsHarvestCraft.ID, "pepperoniItem", 1L, 0),
                            GT_OreDictUnificator.get(OrePrefixes.dye, Materials.Red, 1L),
                            GT_Utility.getIntegratedCircuit(1))
                    .itemOutputs(GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 18))
                    .fluidInputs(FluidRegistry.getFluidStack("mead", 1000)).noFluidOutputs().duration(10 * MINUTES)
                    .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        }

        if (GTPlusPlus.isModLoaded()) { // GT++, remember to remove later
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.WroughtIron, 1L),
                            CustomItemList.SteelBars.get(6L),
                            GT_Utility.getIntegratedCircuit(1))
                    .itemOutputs(GT_ModHandler.getModItem(GTPlusPlus.ID, "blockFishTrap", 1L, 0)).noFluidInputs()
                    .noFluidOutputs().duration(10 * SECONDS).eut(64).addTo(sAssemblerRecipes);
        }

        if (ExtraBees.isModLoaded()) {
            ItemStack alveary = GT_ModHandler.getModItem(Forestry.ID, "alveary", 1L, 0);

            // Mutator
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 12),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderEye, 2L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderPearl, 2L),
                            GT_ModHandler.getModItem(ExtraBees.ID, "hiveFrame.soul", 1L, 0))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 0))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).noFluidOutputs().duration(60 * SECONDS)
                    .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

            // Frame Housing
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 10),
                            GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.WoodSealed, 1L),
                            GT_ModHandler.getModItem(Forestry.ID, "frameProven", 1L, 0),
                            GT_ModHandler.getModItem(Forestry.ID, "frameProven", 1L, 0),
                            GT_ModHandler.getModItem(Forestry.ID, "frameProven", 1L, 0),
                            GT_ModHandler.getModItem(Forestry.ID, "frameProven", 1L, 0))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 1))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).noFluidOutputs().duration(60 * SECONDS)
                    .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

            // Rain Shield
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 8),
                            GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockRubber", 4L),
                            new ItemStack(Blocks.brick_block, 1, 0))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 2))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).noFluidOutputs().duration(60 * SECONDS)
                    .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

            // Alveary Lighting
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 2),
                            new ItemStack(Blocks.stained_glass, 4, W),
                            new ItemStack(Blocks.redstone_lamp, 1, 0))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 3))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).noFluidOutputs().duration(60 * SECONDS)
                    .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

            // Electrical Stimulator
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 1),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Silver, 2L),
                            GT_ModHandler.getModItem(Forestry.ID, "chipsets", 2L, 2),
                            ItemList.Electric_Motor_HV.get(1L))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 4))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).noFluidOutputs().duration(60 * SECONDS)
                    .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

            // Hatchery
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 3),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Redstone, 2L),
                            ItemList.Conveyor_Module_MV.get(2L),
                            GT_ModHandler.getModItem(Forestry.ID, "apiculture", 1L, 0))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 5))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).noFluidOutputs().duration(60 * SECONDS)
                    .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

            // Alveary Transmission
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 0),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Aluminium, 3L),
                            ItemList.Hull_HV.get(1L))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 6))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).noFluidOutputs().duration(60 * SECONDS)
                    .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

            // Alveary Unlighting
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            alveary,
                            GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 8),
                            GT_ModHandler.getModItem(ExtraUtilities.ID, "curtains", 4L, 0),
                            new ItemStack(Blocks.wool, 1, 15))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraBees.ID, "alveary", 1L, 7))
                    .fluidInputs(Materials.Honey.getFluid(7500L)).noFluidOutputs().duration(60 * SECONDS)
                    .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        }

        if (Gendustry.isModLoaded()) {
            // pollen collection kit
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_ModHandler.getModItem(Gendustry.ID, "Labware", 1),
                            GT_ModHandler.getModItem(Minecraft.ID, "string", 1),
                            GT_ModHandler.getModItem(Minecraft.ID, "paper", 1))
                    .itemOutputs(GT_ModHandler.getModItem(Gendustry.ID, "PollenKit", 1)).noFluidInputs()
                    .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);

        }

        if (ExtraUtilities.isModLoaded()) {
            GT_Values.RA.addAssemblerRecipe(
                    GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bedrockium, 9L),
                    GT_Utility.getIntegratedCircuit(1),
                    Materials.UUMatter.getMolten(1000L),
                    GT_ModHandler.getModItem(ExtraUtilities.ID, "bedrockiumIngot", 1L, 0),
                    1,
                    122880);
            GT_Values.RA.addAssemblerRecipe(
                    GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Unstable, 9L),
                    GT_Utility.getIntegratedCircuit(1),
                    Materials.UUMatter.getMolten(1000L),
                    GT_ModHandler.getModItem(ExtraUtilities.ID, "unstableingot", 1L, 2),
                    1,
                    122880);

        }

        if (ExtraUtilities.isModLoaded() && IronChests.isModLoaded()) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_ModHandler.getModItem(IronChests.ID, "BlockIronChest", 1L, 0),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4L),
                            GT_Utility.getIntegratedCircuit(1))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "filing", 1L, 0))
                    .fluidInputs(Materials.Plastic.getMolten(144L)).noFluidOutputs().duration(15 * SECONDS)
                    .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        }

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeQuadruple, Materials.Infinity, 1L),
                        CustomItemList.Hull_UEV.get(1L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Hatch_Input_Multi_2x2_UEV.get(1L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(2304L)).noFluidOutputs().duration(30 * SECONDS)
                .eut(24).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeQuadruple, MaterialsUEVplus.TranscendentMetal, 1L),
                        CustomItemList.Hull_UIV.get(1L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Hatch_Input_Multi_2x2_UIV.get(1L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(2304L)).noFluidOutputs().duration(30 * SECONDS)
                .eut(24).addTo(sAssemblerRecipes);

        // Gear Box Casings
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Bronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Bronze, 2),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 4),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Gearbox_Bronze.get(1)).noFluidInputs().noFluidOutputs()
                .duration(5 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Steel, 2),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Gearbox_Steel.get(1)).noFluidInputs().noFluidOutputs()
                .duration(5 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Titanium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Casing_Gearbox_Titanium.get(1)).noFluidInputs().noFluidOutputs()
                .duration(5 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        // Quantum Armor
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.QuantumPartHelmet.get(1L),
                        CustomItemList.QuantumCrystal.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 4),
                        ItemList.Energy_LapotronicOrb.get(1L),
                        ItemList.Sensor_IV.get(1L),
                        ItemList.Field_Generator_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 4),
                        GT_Utility.getIntegratedCircuit(10))
                .itemOutputs(GT_ModHandler.getIC2Item("quantumHelmet", 1L, 26))
                .fluidInputs(Materials.Titanium.getMolten(1728L)).noFluidOutputs().duration(1 * MINUTES + 15 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.QuantumPartChestplate.get(1L),
                        CustomItemList.QuantumCrystal.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 6),
                        ItemList.Energy_LapotronicOrb.get(1L),
                        ItemList.Field_Generator_EV.get(3L),
                        ItemList.Electric_Motor_IV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 4),
                        GT_Utility.getIntegratedCircuit(11))
                .itemOutputs(GT_ModHandler.getIC2Item("quantumBodyarmor", 1L, 26))
                .fluidInputs(Materials.Titanium.getMolten(2880L)).noFluidOutputs().duration(1 * MINUTES + 15 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.QuantumPartLeggings.get(1L),
                        CustomItemList.QuantumCrystal.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 6),
                        ItemList.Energy_LapotronicOrb.get(1L),
                        ItemList.Field_Generator_EV.get(2L),
                        ItemList.Electric_Motor_IV.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 4),
                        GT_Utility.getIntegratedCircuit(12))
                .itemOutputs(GT_ModHandler.getIC2Item("quantumLeggings", 1L, 26))
                .fluidInputs(Materials.Titanium.getMolten(2304L)).noFluidOutputs().duration(1 * MINUTES + 15 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.QuantumPartBoots.get(1L),
                        CustomItemList.QuantumCrystal.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 4),
                        ItemList.Energy_LapotronicOrb.get(1L),
                        ItemList.Field_Generator_EV.get(1L),
                        ItemList.Electric_Piston_IV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 4),
                        GT_Utility.getIntegratedCircuit(13))
                .itemOutputs(GT_ModHandler.getIC2Item("quantumBoots", 1L, 26))
                .fluidInputs(Materials.Titanium.getMolten(1440L)).noFluidOutputs().duration(1 * MINUTES + 15 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);

        if (GraviSuite.isModLoaded() && GalacticraftMars.isModLoaded()) {
            // Gravitational Engine
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.wireGt12, Materials.SuperconductorLuV, 2),
                            GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockMachine2", 4, 1),
                            ItemList.Transformer_LuV_IV.get(1),
                            GT_ModHandler.getModItem(GraviSuite.ID, "itemSimpleItem", 2, 2))
                    .noFluidInputs().noFluidOutputs()
                    .itemOutputs(GT_ModHandler.getModItem(GraviSuite.ID, "itemSimpleItem", 1, 3)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
            // IV Transformer
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 4),
                            GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.VanadiumGallium, 1),
                            ItemList.Circuit_Chip_PIC.get(2),
                            ItemList.Hull_IV.get(1))
                    .noFluidInputs().noFluidOutputs().itemOutputs(ItemList.Transformer_LuV_IV.get(1))
                    .duration(5 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
            // Tesla Coil
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.Gold, 1),
                            ItemList.Circuit_Nanoprocessor.get(2),
                            GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemRecipePart", 5, 0),
                            ItemList.Transformer_HV_MV.get(1))
                    .noFluidInputs().noFluidOutputs()
                    .itemOutputs(GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockMachine2", 1, 1))
                    .duration(5 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
            // MV Transformer
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 4),
                            GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
                            ItemList.Hull_MV.get(1),
                            ItemList.Circuit_Parts_InductorSMD.get(2))
                    .noFluidOutputs().noFluidInputs().itemOutputs(ItemList.Transformer_HV_MV.get(1))
                    .duration(5 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
            // Cooling Core With NaK
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorVentDiamond", 2, 1),
                            GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 2, 1),
                            Materials.CallistoIce.getPlates(1),
                            ItemList.Reactor_Coolant_NaK_3.get(2),
                            GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorPlatingHeat", 2, 0))
                    .noFluidInputs().noFluidOutputs()
                    .itemOutputs(GT_ModHandler.getModItem(GraviSuite.ID, "itemSimpleItem", 1, 2)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
            // Cooling Core With Helium
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorVentDiamond", 2, 1),
                            GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 2, 1),
                            Materials.CallistoIce.getPlates(1),
                            ItemList.Reactor_Coolant_He_3.get(2),
                            GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorPlatingHeat", 2, 0))
                    .noFluidInputs().noFluidOutputs()
                    .itemOutputs(GT_ModHandler.getModItem(GraviSuite.ID, "itemSimpleItem", 1, 2)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
            // Fluid Manipulator
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Large_Fluid_Cell_Steel.get(1),
                            GT_OreDictUnificator.get(OrePrefixes.pipeQuadruple, Materials.Steel, 4),
                            ItemList.Electric_Motor_HV.get(4))
                    .noFluidOutputs().noFluidInputs()
                    .itemOutputs(GT_ModHandler.getModItem(GalacticraftMars.ID, "item.null", 1, 6)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        }
        // Quantum Tank V
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Bio), 4),
                        Materials.Americium.getPlates(2),
                        ItemList.Electric_Pump_UV.get(1),
                        ItemList.Field_Generator_UV.get(1),
                        ItemList.Casing_Tank_10.get(1))
                .noFluidInputs().noFluidOutputs().itemOutputs(ItemList.Quantum_Tank_IV.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        // Quantum Tank IV
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Infinite), 4),
                        Materials.Europium.getPlates(2),
                        ItemList.Electric_Pump_ZPM.get(1),
                        ItemList.Field_Generator_ZPM.get(1),
                        ItemList.Casing_Tank_9.get(1))
                .noFluidInputs().noFluidOutputs().itemOutputs(ItemList.Quantum_Tank_EV.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        // Quantum Chest V
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Bio), 4),
                        Materials.Americium.getPlates(3),
                        ItemList.Field_Generator_UV.get(1),
                        CustomItemList.Automation_ChestBuffer_UEV.get(1L))
                .noFluidOutputs().noFluidInputs().itemOutputs(ItemList.Quantum_Chest_IV.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        // Ultra High Voltage Chest Buffer
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Conveyor_Module_UEV.get(1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Bio), 1),
                        CustomItemList.Hull_UEV.get(1),
                        GT_OreDictUnificator.get(OrePrefixes.chest, Materials.Wood, 1))
                .noFluidOutputs().noFluidInputs().itemOutputs(CustomItemList.Automation_ChestBuffer_UEV.get(1L))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        if (HardcoreEnderExpansion.isModLoaded()) {
            // Biome Compass
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            Materials.HeeEndium.getPlates(1),
                            GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HeeEndium, 1),
                            GT_OreDictUnificator.get(OrePrefixes.screw, Materials.HeeEndium, 2),
                            GT_OreDictUnificator.get(OrePrefixes.ring, Materials.HeeEndium, 2),
                            GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 1),
                            GT_ModHandler.getModItem(HardcoreEnderExpansion.ID, "end_powder", 1, 0))
                    .noFluidInputs().noFluidOutputs()
                    .itemOutputs(GT_ModHandler.getModItem(HardcoreEnderExpansion.ID, "biome_compass", 1, 0))
                    .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        }
        if (StorageDrawers.isModLoaded()) {
            // Alternate Storage Template Recipe
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Electric_Piston_LV.get(1),
                            GT_OreDictUnificator.get("drawerBasic", 1),
                            GT_Utility.getIntegratedCircuit(2))
                    .noFluidOutputs().noFluidInputs()
                    .itemOutputs(GT_ModHandler.getModItem(StorageDrawers.ID, "upgradeTemplate", 3, 0))
                    .duration(60 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        }
        // Display
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Cover_Screen.get(1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Iron, 2))
                .fluidInputs(Materials.Plastic.getMolten(144)).noFluidOutputs()
                .itemOutputs(CustomItemList.Display.get(1)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(sAssemblerRecipes);

        // Ender-Quantum Component
        if (SuperSolarPanels.isModLoaded() && (AdvancedSolarPanel.isModLoaded())) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.InfinityCatalyst, 1),
                            GT_ModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 4, 3),
                            Materials.ElectrumFlux.getPlates(4))
                    .noFluidInputs().noFluidOutputs()
                    .itemOutputs(GT_ModHandler.getModItem(SuperSolarPanels.ID, "enderquantumcomponent", 1, 0))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        }
        // 4A Zpm to LuV transformer
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.VanadiumGallium, 4),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Naquadah, 1),
                        ItemList.Circuit_Chip_HPIC.get(2),
                        ItemList.Hull_LuV.get(1))
                .noFluidInputs().noFluidOutputs().itemOutputs(ItemList.Transformer_ZPM_LuV.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        if (GTPlusPlus.isModLoaded()) {
            // 16A Zpm to LuV transformer
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.VanadiumGallium, 4),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Naquadah, 1),
                            ItemList.Transformer_ZPM_LuV.get(1),
                            ItemList.Casing_Coil_TungstenSteel.get(1))
                    .noFluidInputs().noFluidOutputs()
                    .itemOutputs(GT_ModHandler.getModItem(GregTech.ID, "gt.blockmachines", 1, 883))
                    .duration(5 * SECONDS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

            // 64A Zpm To LuV transformer
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.cableGt16, Materials.VanadiumGallium, 2),
                            GT_OreDictUnificator.get(OrePrefixes.cableGt08, Materials.Naquadah, 1),
                            GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Naquadah, 1),
                            GT_OreDictUnificator.get(OrePrefixes.springSmall, Materials.VanadiumGallium, 1),
                            GT_ModHandler.getModItem(GregTech.ID, "gt.blockmachines", 1, 883),
                            ItemList.Electric_Pump_LV.get(1))
                    .fluidInputs(Materials.Lubricant.getFluid(2000)).noFluidOutputs()
                    .itemOutputs(CustomItemList.WetTransformer_ZPM_LuV.get(1)).duration(5 * SECONDS)
                    .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

            // Hastelloy-X Structural Block
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            ItemList.Casing_EV.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2),
                            getModItem(GTPlusPlus.ID, "itemGearHastelloyX", 1L),
                            getModItem(GTPlusPlus.ID, "blockFrameGtHastelloyC276", 1L),
                            getModItem(GTPlusPlus.ID, "itemRingInconel792", 2L))
                    .itemOutputs(getModItem(GTPlusPlus.ID, "gtplusplus.blockcasings.2", 1L, 2)).noFluidInputs()
                    .noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        }
        // Ultimate Time Anomaly
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_UV.get(2),
                        ItemList.Electric_Pump_UV.get(1),
                        ItemList.Conveyor_Module_UV.get(1),
                        ItemList.Robot_Arm_UV.get(2),
                        ItemList.Electric_Piston_UV.get(2),
                        ItemList.Hull_UV.get(1))
                .noFluidInputs().noFluidOutputs().itemOutputs(CustomItemList.AcceleratorUV.get(1)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        if (KekzTech.isModLoaded()) {
            // LSC controller
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.battery, Materials.Master, 4),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 2),
                            ItemList.Circuit_Chip_PIC.get(2))
                    .noFluidInputs().noFluidOutputs()
                    .itemOutputs(GT_ModHandler.getModItem(GregTech.ID, "gt.blockmachines", 1, 13106))
                    .duration(5 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        }
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeQuadruple, MaterialsUEVplus.SpaceTime, 1),
                        CustomItemList.Hull_UMV.get(1),
                        GT_Utility.getIntegratedCircuit(4))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(2304)).noFluidOutputs()
                .itemOutputs(ItemList.Hatch_Input_Multi_2x2_UMV.get(1)).duration(30 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(sAssemblerRecipes);
        // Spacetime quadruple is temp replacement until Universium Pipes.
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.pipeQuadruple, MaterialsUEVplus.SpaceTime, 4),
                        CustomItemList.Hull_UXV.get(1),
                        GT_Utility.getIntegratedCircuit(4))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(2304)).noFluidOutputs()
                .itemOutputs(ItemList.Hatch_Input_Multi_2x2_UXV.get(1)).duration(30 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(sAssemblerRecipes);
        if (BartWorks.isModLoaded()) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            ItemRegistry.humongousInputHatch.copy(),
                            GT_OreDictUnificator.get(OrePrefixes.pipeHuge, MaterialsUEVplus.SpaceTime, 4),
                            GT_Utility.getIntegratedCircuit(4))
                    .fluidInputs(MaterialsUEVplus.Space.getMolten(5760)).noFluidOutputs()
                    .itemOutputs(ItemList.Hatch_Input_Multi_2x2_Humongous.get(1)).duration(30 * SECONDS)
                    .eut(TierEU.RECIPE_UMV).addTo(sAssemblerRecipes);
        }

        // Diamond Gear
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Diamond, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 4L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Diamond, 1L))
                .fluidInputs(Materials.Lubricant.getFluid(250L)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
    }

    private void makeElectricMachinePartRecipes() {
        // Motors

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.AnyIron, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                .itemOutputs(ItemList.Electric_Motor_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                .itemOutputs(ItemList.Electric_Motor_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.AnyIron, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                .itemOutputs(ItemList.Electric_Motor_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                .itemOutputs(ItemList.Electric_Motor_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Cupronickel, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L))
                .itemOutputs(ItemList.Electric_Motor_MV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Cupronickel, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnnealedCopper, 2L))
                .itemOutputs(ItemList.Electric_Motor_MV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Electrum, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Silver, 2L))
                .itemOutputs(ItemList.Electric_Motor_HV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.NeodymiumMagnetic, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.BlackSteel, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Aluminium, 2L))
                .itemOutputs(ItemList.Electric_Motor_EV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.NeodymiumMagnetic, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Graphene, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Tungsten, 2L))
                .itemOutputs(ItemList.Electric_Motor_IV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Pumps

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1))
                .itemOutputs(ItemList.Electric_Pump_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1))
                .itemOutputs(ItemList.Electric_Pump_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1))
                .itemOutputs(ItemList.Electric_Pump_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1))
                .itemOutputs(ItemList.Electric_Pump_MV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1))
                .itemOutputs(ItemList.Electric_Pump_MV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1))
                .itemOutputs(ItemList.Electric_Pump_MV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1))
                .itemOutputs(ItemList.Electric_Pump_HV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1))
                .itemOutputs(ItemList.Electric_Pump_HV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1))
                .itemOutputs(ItemList.Electric_Pump_HV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 1))
                .itemOutputs(ItemList.Electric_Pump_EV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 1))
                .itemOutputs(ItemList.Electric_Pump_EV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 1))
                .itemOutputs(ItemList.Electric_Pump_EV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_IV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 1))
                .itemOutputs(ItemList.Electric_Pump_IV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_IV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 1))
                .itemOutputs(ItemList.Electric_Pump_IV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Conveyors

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1))
                .itemOutputs(ItemList.Conveyor_Module_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1))
                .itemOutputs(ItemList.Conveyor_Module_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1))
                .itemOutputs(ItemList.Conveyor_Module_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 1))
                .itemOutputs(ItemList.Conveyor_Module_MV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 1))
                .itemOutputs(ItemList.Conveyor_Module_MV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 1))
                .itemOutputs(ItemList.Conveyor_Module_MV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1))
                .itemOutputs(ItemList.Conveyor_Module_HV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1))
                .itemOutputs(ItemList.Conveyor_Module_HV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1))
                .itemOutputs(ItemList.Conveyor_Module_HV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1))
                .itemOutputs(ItemList.Conveyor_Module_EV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1))
                .itemOutputs(ItemList.Conveyor_Module_EV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1))
                .itemOutputs(ItemList.Conveyor_Module_EV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_IV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1))
                .itemOutputs(ItemList.Conveyor_Module_IV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_IV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1))
                .itemOutputs(ItemList.Conveyor_Module_IV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Pistons

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 3),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 1))
                .itemOutputs(ItemList.Electric_Piston_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 3),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 2),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Aluminium, 1))
                .itemOutputs(ItemList.Electric_Piston_MV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 3),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 2),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.StainlessSteel, 1))
                .itemOutputs(ItemList.Electric_Piston_HV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 3),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Titanium, 1))
                .itemOutputs(ItemList.Electric_Piston_EV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_IV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 3),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 2),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.TungstenSteel, 1))
                .itemOutputs(ItemList.Electric_Piston_IV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Robot Arms

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_LV.get(2L),
                        ItemList.Electric_Piston_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 3))
                .itemOutputs(ItemList.Robot_Arm_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_MV.get(2L),
                        ItemList.Electric_Piston_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 3))
                .itemOutputs(ItemList.Robot_Arm_MV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_HV.get(2L),
                        ItemList.Electric_Piston_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 3))
                .itemOutputs(ItemList.Robot_Arm_HV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_EV.get(2L),
                        ItemList.Electric_Piston_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 3))
                .itemOutputs(ItemList.Robot_Arm_EV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Motor_IV.get(2L),
                        ItemList.Electric_Piston_IV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 3))
                .itemOutputs(ItemList.Robot_Arm_IV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Emitter

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2))
                .itemOutputs(ItemList.Emitter_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.gem, Materials.EnderPearl, 1),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Electrum, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnyCopper, 2))
                .itemOutputs(ItemList.Emitter_MV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.gem, Materials.EnderEye, 1),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 2))
                .itemOutputs(ItemList.Emitter_HV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.QuantumEye.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Platinum, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2))
                .itemOutputs(ItemList.Emitter_EV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.QuantumStar.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 2))
                .itemOutputs(ItemList.Emitter_IV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Sensor

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L))
                .itemOutputs(ItemList.Sensor_LV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.gemFlawless, Materials.Emerald, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Electrum, 1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L))
                .itemOutputs(ItemList.Sensor_MV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.gem, Materials.EnderEye, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1L))
                .itemOutputs(ItemList.Sensor_HV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.QuantumEye.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Platinum, 1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1L))
                .itemOutputs(ItemList.Sensor_EV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.QuantumStar.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 1L))
                .itemOutputs(ItemList.Sensor_IV.get(1L)).noFluidInputs().noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Field Generator

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderPearl, 1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Field_Generator_LV.get(1)).fluidInputs(Materials.RedSteel.getMolten(288))
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderEye, 1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Field_Generator_MV.get(1)).fluidInputs(Materials.TungstenSteel.getMolten(288))
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.QuantumEye.get(1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Field_Generator_HV.get(1)).fluidInputs(Materials.NiobiumTitanium.getMolten(576))
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.gem, Materials.NetherStar, 1),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4),
                        GT_Utility.getIntegratedCircuit(13))
                .itemOutputs(ItemList.Field_Generator_EV.get(1)).fluidInputs(Materials.HSSG.getMolten(576))
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.QuantumStar.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Ultimate, 4),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Field_Generator_IV.get(1L)).fluidInputs(Materials.HSSS.getMolten(576))
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);

    }

    private void makeCircuitPartRecipes() {
        // Circuits and Boards

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 8),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 32),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Board_Coated_Basic.get(8L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 1152)).noFluidOutputs()
                .duration(1 * MINUTES + 20 * SECONDS).eut(8).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 8),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 32),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Board_Coated_Basic.get(8L)).fluidInputs(Materials.Glue.getFluid(576L))
                .noFluidOutputs().duration(1 * MINUTES + 20 * SECONDS).eut(8).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 8),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 32),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Board_Coated_Basic.get(16L))
                .fluidInputs(Materials.Plastic.getMolten(288L)).noFluidOutputs().duration(1 * MINUTES + 20 * SECONDS)
                .eut(8).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 8),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 32),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Board_Coated_Basic.get(16L))
                .fluidInputs(Materials.Polytetrafluoroethylene.getMolten(144L)).noFluidOutputs()
                .duration(1 * MINUTES + 20 * SECONDS).eut(8).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 8),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 32),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Board_Coated_Basic.get(24L)).fluidInputs(Materials.Epoxid.getMolten(144L))
                .noFluidOutputs().duration(1 * MINUTES + 20 * SECONDS).eut(8).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 8),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 32),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Board_Coated_Basic.get(32L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(72L)).noFluidOutputs()
                .duration(1 * MINUTES + 20 * SECONDS).eut(8).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 8),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Circuit_Board_Phenolic.get(8L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).noFluidOutputs().duration(2 * MINUTES).eut(16)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 8),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Circuit_Board_Phenolic.get(8L)).fluidInputs(Materials.Glue.getFluid(288))
                .noFluidOutputs().duration(2 * MINUTES).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 8),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Circuit_Board_Phenolic.get(16L)).fluidInputs(Materials.BisphenolA.getFluid(144))
                .noFluidOutputs().duration(2 * MINUTES).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 8),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Circuit_Board_Phenolic.get(24L)).fluidInputs(Materials.Epoxid.getMolten(144L))
                .noFluidOutputs().duration(2 * MINUTES).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Parts_Glass_Tube.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(2L)).fluidInputs(Materials.Redstone.getMolten(144L))
                .noFluidOutputs().duration(8 * SECONDS).eut(8).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Parts_Glass_Tube.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(4L)).fluidInputs(Materials.RedAlloy.getMolten(72L))
                .noFluidOutputs().duration(8 * SECONDS).eut(8).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Parts_Glass_Tube.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(8L))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(72L)).noFluidOutputs().duration(8 * SECONDS).eut(8)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Parts_Glass_Tube.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(8L)).fluidInputs(Materials.RedAlloy.getMolten(72L))
                .noFluidOutputs().duration(8 * SECONDS).eut(8).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Parts_Glass_Tube.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(16L))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(72L)).noFluidOutputs().duration(8 * SECONDS).eut(8)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L)).fluidInputs(Materials.Glue.getFluid(288))
                .noFluidOutputs().duration(16 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L)).fluidInputs(Materials.Glue.getFluid(288))
                .noFluidOutputs().duration(16 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Charcoal, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L)).fluidInputs(Materials.Glue.getFluid(288))
                .noFluidOutputs().duration(16 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lignite, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L)).fluidInputs(Materials.Glue.getFluid(288))
                .noFluidOutputs().duration(16 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).noFluidOutputs().duration(16 * SECONDS).eut(16)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).noFluidOutputs().duration(16 * SECONDS).eut(16)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Charcoal, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).noFluidOutputs().duration(16 * SECONDS).eut(16)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lignite, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(4L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).noFluidOutputs().duration(16 * SECONDS).eut(16)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L)).fluidInputs(Materials.Glue.getFluid(288))
                .noFluidOutputs().duration(16 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L)).fluidInputs(Materials.Glue.getFluid(288))
                .noFluidOutputs().duration(16 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Charcoal, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L)).fluidInputs(Materials.Glue.getFluid(288))
                .noFluidOutputs().duration(16 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lignite, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L)).fluidInputs(Materials.Glue.getFluid(288))
                .noFluidOutputs().duration(16 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).noFluidOutputs().duration(16 * SECONDS).eut(16)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).noFluidOutputs().duration(16 * SECONDS).eut(16)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Charcoal, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).noFluidOutputs().duration(16 * SECONDS).eut(16)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lignite, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(ItemList.Circuit_Parts_Resistor.get(8L))
                .fluidInputs(FluidRegistry.getFluidStack("glue", 576)).noFluidOutputs().duration(16 * SECONDS).eut(16)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.GalliumArsenide, 1),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(4L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.GalliumArsenide, 1),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(2L)).fluidInputs(Materials.Glass.getMolten(288L))
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.GalliumArsenide, 1),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(2L))
                .fluidInputs(FluidRegistry.getFluidStack("glass.molten", 1000)).noFluidOutputs().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        ItemList.Circuit_Silicon_Wafer.get(1L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(2L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        ItemList.Circuit_Silicon_Wafer.get(1L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(1L))
                .fluidInputs(FluidRegistry.getFluidStack("glass.molten", 1000)).noFluidOutputs().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 4),
                        ItemList.Circuit_Silicon_Wafer.get(1L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(1L)).fluidInputs(Materials.Glass.getMolten(288L))
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        ItemList.Circuit_Silicon_Wafer.get(1L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(4L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tantalum, 4),
                        ItemList.Circuit_Silicon_Wafer.get(1L),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(8L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Coil.get(2L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .noFluidOutputs().duration(16 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.NickelZincFerrite, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Coil.get(4L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .noFluidOutputs().duration(16 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 2),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Coil.get(4L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .noFluidOutputs().duration(16 * SECONDS).eut(60).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.NickelZincFerrite, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 2),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Coil.get(8L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .noFluidOutputs().duration(16 * SECONDS).eut(60).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Plastic, 1),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Tantalum, 2),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Capacitor.get(12L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .noFluidOutputs().duration(16 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Plastic, 1),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Aluminium, 2),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_Capacitor.get(8L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .noFluidOutputs().duration(16 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.SiliconSG, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tin, 8),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(ItemList.Circuit_Parts_Transistor.get(6L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .noFluidOutputs().duration(16 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // All SMD Component recipes.

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 4),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_ResistorSMD.get(16L)).fluidInputs(Materials.Plastic.getMolten(288L))
                .noFluidOutputs().duration(16 * SECONDS).eut(96).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tantalum, 4),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_ResistorSMD.get(32L)).fluidInputs(Materials.Plastic.getMolten(288L))
                .noFluidOutputs().duration(16 * SECONDS).eut(96).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.GalliumArsenide, 1),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(6L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tantalum, 4),
                        GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.GalliumArsenide, 1),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(ItemList.Circuit_Parts_Diode.get(16L)).fluidInputs(Materials.Plastic.getMolten(144L))
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 16),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.GalliumArsenide, 1),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_DiodeSMD.get(64L)).fluidInputs(Materials.Plastic.getMolten(576L))
                .noFluidOutputs().duration(60 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Gallium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 8),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_TransistorSMD.get(16L))
                .fluidInputs(Materials.Plastic.getMolten(288L)).noFluidOutputs().duration(16 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Gallium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tantalum, 8),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_TransistorSMD.get(32L))
                .fluidInputs(Materials.Plastic.getMolten(288L)).noFluidOutputs().duration(16 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // Normal SMD

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicone, 2),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Aluminium, 2),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_CapacitorSMD.get(16L))
                .fluidInputs(Materials.Plastic.getMolten(144L)).noFluidOutputs().duration(16 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.PolyvinylChloride, 4),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Aluminium, 2),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_CapacitorSMD.get(24L))
                .fluidInputs(Materials.Plastic.getMolten(144L)).noFluidOutputs().duration(16 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicone, 2),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Tantalum, 2),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_CapacitorSMD.get(32L))
                .fluidInputs(Materials.Plastic.getMolten(144L)).noFluidOutputs().duration(16 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.PolyvinylChloride, 4),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Tantalum, 2),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_CapacitorSMD.get(48L))
                .fluidInputs(Materials.Plastic.getMolten(144L)).noFluidOutputs().duration(16 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Neodymium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.ConductiveIron, 8),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_InductorSMD.get(24L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .noFluidOutputs().duration(16 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Neodymium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 8),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_InductorSMD.get(32L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .noFluidOutputs().duration(16 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Neodymium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tantalum, 8),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_InductorSMD.get(48L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .noFluidOutputs().duration(16 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Neodymium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Iridium, 8),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_InductorSMD.get(64L)).fluidInputs(Materials.Plastic.getMolten(36L))
                .noFluidOutputs().duration(16 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // ASMD

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphene, 2),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 16),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 4),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Parts_ResistorASMD.get(64L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(576L)).noFluidOutputs().duration(15 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.IndiumGalliumPhosphide, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 16),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 4),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Parts_DiodeASMD.get(64L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(576L)).noFluidOutputs().duration(15 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.VanadiumGallium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.HSSG, 16),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 4),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Parts_TransistorASMD.get(64L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(576L)).noFluidOutputs().duration(15 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 4),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.HSSS, 2),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 4),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Parts_CapacitorASMD.get(64L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(576L)).noFluidOutputs().duration(15 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Samarium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.HSSE, 32),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 4),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Circuit_Parts_InductorASMD.get(64L))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(576L)).noFluidOutputs().duration(8 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        // xSMD
        // Resistor

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Naquadria, 4L),
                        GT_ModHandler.getModItem(GTPlusPlus.ID, "itemFoilPikyonium64B", 2L),
                        GT_OreDictUnificator
                                .get(OrePrefixes.foil, Materials.Tetranaquadahdiindiumhexaplatiumosminid, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 1L),
                        GT_Utility.getIntegratedCircuit(9))
                .itemOutputs(ItemList.Circuit_Parts_ResistorXSMD.get(32L)).fluidInputs(Xenoxene.getFluid(144L))
                .noFluidOutputs().duration(8 * SECONDS).eut(120000).addTo(sAssemblerRecipes);
        // Transistor

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlackPlutonium, 4L),
                        GT_ModHandler.getModItem(GTPlusPlus.ID, "itemFoilArceusAlloy2B", 2L),
                        GT_OreDictUnificator
                                .get(OrePrefixes.foil, Materials.Tetranaquadahdiindiumhexaplatiumosminid, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 1L),
                        GT_Utility.getIntegratedCircuit(9))
                .itemOutputs(ItemList.Circuit_Parts_TransistorXSMD.get(32L)).fluidInputs(Xenoxene.getFluid(144L))
                .noFluidOutputs().duration(8 * SECONDS).eut(120000).addTo(sAssemblerRecipes);
        // Capacitor

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Draconium, 4L),
                        GT_ModHandler.getModItem(GTPlusPlus.ID, "itemFoilCinobiteA243", 2L),
                        GT_OreDictUnificator
                                .get(OrePrefixes.foil, Materials.Tetranaquadahdiindiumhexaplatiumosminid, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 1L),
                        GT_Utility.getIntegratedCircuit(9))
                .itemOutputs(ItemList.Circuit_Parts_CapacitorXSMD.get(32L)).fluidInputs(Xenoxene.getFluid(144L))
                .noFluidOutputs().duration(8 * SECONDS).eut(120000).addTo(sAssemblerRecipes);
        // Diode

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Tritanium, 4L),
                        GT_ModHandler.getModItem(GTPlusPlus.ID, "itemFoilLafiumCompound", 2L),
                        GT_OreDictUnificator
                                .get(OrePrefixes.foil, Materials.Tetranaquadahdiindiumhexaplatiumosminid, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 1L),
                        GT_Utility.getIntegratedCircuit(9))
                .itemOutputs(ItemList.Circuit_Parts_DiodeXSMD.get(64L)).fluidInputs(Xenoxene.getFluid(144L))
                .noFluidOutputs().duration(8 * SECONDS).eut(120000).addTo(sAssemblerRecipes);
        // Inductor

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedfoil", 4L, 10105),
                        GT_ModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedfoil", 1L, 10102),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 1L),
                        GT_Utility.getIntegratedCircuit(9))
                .itemOutputs(ItemList.Circuit_Parts_InductorXSMD.get(32L)).fluidInputs(Xenoxene.getFluid(144L))
                .noFluidOutputs().duration(8 * SECONDS).eut(120000).addTo(sAssemblerRecipes);

    }

    private void makeAirFilterRecipes() {
        // Air Filter stuff

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1L),
                        GT_Utility.getIntegratedCircuit(1),
                        CustomItemList.SteelBars.get(2))
                .itemOutputs(CustomItemList.Casing_AirFilter_Vent_T1.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(72)).noFluidOutputs().duration(5 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1L),
                        GT_Utility.getIntegratedCircuit(1),
                        CustomItemList.SteelBars.get(2))
                .itemOutputs(CustomItemList.Casing_AirFilter_Vent_T1.get(1L)).fluidInputs(Materials.Tin.getMolten(144))
                .noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1L),
                        GT_Utility.getIntegratedCircuit(1),
                        CustomItemList.SteelBars.get(2))
                .itemOutputs(CustomItemList.Casing_AirFilter_Vent_T1.get(1L)).fluidInputs(Materials.Lead.getMolten(288))
                .noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Casing_AirFilter_Vent_T1.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 2L),
                        ItemList.Electric_Motor_LV.get(2L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Casing_AirFilter_Turbine_T1.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(72)).noFluidOutputs().duration(5 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Casing_AirFilter_Vent_T1.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 2L),
                        ItemList.Electric_Motor_LV.get(2L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Casing_AirFilter_Turbine_T1.get(1L))
                .fluidInputs(Materials.Tin.getMolten(144)).noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Casing_AirFilter_Vent_T1.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Steel, 2L),
                        ItemList.Electric_Motor_LV.get(2L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Casing_AirFilter_Turbine_T1.get(1L))
                .fluidInputs(Materials.Lead.getMolten(288)).noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_LV.get(1L),
                        ItemList.Casing_Turbine.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L),
                        ItemList.Electric_Motor_LV.get(2L),
                        ItemList.Electric_Pump_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Machine_Multi_AirFilterT1.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(144)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_LV.get(1L),
                        ItemList.Casing_Turbine.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L),
                        ItemList.Electric_Motor_LV.get(2L),
                        ItemList.Electric_Pump_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Machine_Multi_AirFilterT1.get(1L)).fluidInputs(Materials.Tin.getMolten(288))
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_LV.get(1L),
                        ItemList.Casing_Turbine.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L),
                        ItemList.Electric_Motor_LV.get(2L),
                        ItemList.Electric_Pump_LV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Machine_Multi_AirFilterT1.get(1L))
                .fluidInputs(Materials.Lead.getMolten(576)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 1L),
                        GT_Utility.getIntegratedCircuit(1),
                        CustomItemList.TitaniumBars.get(2))
                .itemOutputs(CustomItemList.Casing_AirFilter_Vent_T2.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(72)).noFluidOutputs().duration(5 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 1L),
                        GT_Utility.getIntegratedCircuit(1),
                        CustomItemList.TitaniumBars.get(2))
                .itemOutputs(CustomItemList.Casing_AirFilter_Vent_T2.get(1L)).fluidInputs(Materials.Tin.getMolten(144))
                .noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 1L),
                        GT_Utility.getIntegratedCircuit(1),
                        CustomItemList.TitaniumBars.get(2))
                .itemOutputs(CustomItemList.Casing_AirFilter_Vent_T2.get(1L)).fluidInputs(Materials.Lead.getMolten(288))
                .noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Casing_AirFilter_Vent_T2.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Titanium, 2L),
                        ItemList.Electric_Motor_HV.get(2L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Casing_AirFilter_Turbine_T2.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(72)).noFluidOutputs().duration(5 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Casing_AirFilter_Vent_T2.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Titanium, 2L),
                        ItemList.Electric_Motor_HV.get(2L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Casing_AirFilter_Turbine_T2.get(1L))
                .fluidInputs(Materials.Tin.getMolten(144)).noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Casing_AirFilter_Vent_T2.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Titanium, 2L),
                        ItemList.Electric_Motor_HV.get(2L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Casing_AirFilter_Turbine_T2.get(1L))
                .fluidInputs(Materials.Lead.getMolten(288)).noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_HV.get(1L),
                        ItemList.Casing_Turbine2.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Titanium, 1L),
                        ItemList.Electric_Motor_HV.get(2L),
                        ItemList.Electric_Pump_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Machine_Multi_AirFilterT2.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(144)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_HV.get(1L),
                        ItemList.Casing_Turbine2.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Titanium, 1L),
                        ItemList.Electric_Motor_HV.get(2L),
                        ItemList.Electric_Pump_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Machine_Multi_AirFilterT2.get(1L)).fluidInputs(Materials.Tin.getMolten(288))
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_HV.get(1L),
                        ItemList.Casing_Turbine2.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Titanium, 1L),
                        ItemList.Electric_Motor_HV.get(2L),
                        ItemList.Electric_Pump_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Machine_Multi_AirFilterT2.get(1L))
                .fluidInputs(Materials.Lead.getMolten(576)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1L),
                        GT_Utility.getIntegratedCircuit(1),
                        CustomItemList.TungstenSteelBars.get(2))
                .itemOutputs(CustomItemList.Casing_AirFilter_Vent_T3.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(72)).noFluidOutputs().duration(5 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1L),
                        GT_Utility.getIntegratedCircuit(1),
                        CustomItemList.TungstenSteelBars.get(2))
                .itemOutputs(CustomItemList.Casing_AirFilter_Vent_T3.get(1L)).fluidInputs(Materials.Tin.getMolten(144))
                .noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1L),
                        GT_Utility.getIntegratedCircuit(1),
                        CustomItemList.TungstenSteelBars.get(2))
                .itemOutputs(CustomItemList.Casing_AirFilter_Vent_T3.get(1L)).fluidInputs(Materials.Lead.getMolten(288))
                .noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Casing_AirFilter_Vent_T3.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.TungstenSteel, 2L),
                        ItemList.Electric_Motor_IV.get(2L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Casing_AirFilter_Turbine_T3.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(72)).noFluidOutputs().duration(5 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Casing_AirFilter_Vent_T3.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.TungstenSteel, 2L),
                        ItemList.Electric_Motor_IV.get(2L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Casing_AirFilter_Turbine_T3.get(1L))
                .fluidInputs(Materials.Tin.getMolten(144)).noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Casing_AirFilter_Vent_T3.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.TungstenSteel, 2L),
                        ItemList.Electric_Motor_IV.get(2L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Casing_AirFilter_Turbine_T3.get(1L))
                .fluidInputs(Materials.Lead.getMolten(288)).noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_IV.get(1L),
                        ItemList.Casing_Turbine3.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1L),
                        ItemList.Electric_Motor_IV.get(2L),
                        ItemList.Electric_Pump_IV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Machine_Multi_AirFilterT3.get(1L))
                .fluidInputs(Materials.SolderingAlloy.getMolten(144)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_IV.get(1L),
                        ItemList.Casing_Turbine3.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1L),
                        ItemList.Electric_Motor_IV.get(2L),
                        ItemList.Electric_Pump_IV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Machine_Multi_AirFilterT3.get(1L)).fluidInputs(Materials.Tin.getMolten(288))
                .noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hull_IV.get(1L),
                        ItemList.Casing_Turbine3.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1L),
                        ItemList.Electric_Motor_IV.get(2L),
                        ItemList.Electric_Pump_IV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.Machine_Multi_AirFilterT3.get(1L))
                .fluidInputs(Materials.Lead.getMolten(576)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

    }

    private void makeEnderIORecipes() {
        if (!EnderIO.isModLoaded()) {
            return;
        }

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(EnderIO.ID, "blockFusedQuartz", 1L),
                        Materials.Glowstone.getDust(4),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "blockFusedQuartz", 1L, 2)).noFluidInputs()
                .noFluidOutputs().duration(25 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        for (int i = 0; i < OreDictionary.getOres("dyeBlack").size(); i++) {
            GT_Values.RA.addAssemblerRecipe(
                    GT_ModHandler.getModItem(EnderIO.ID, "blockFusedQuartz", 1L),
                    OreDictionary.getOres("dyeBlack").get(i).splitStack(4),
                    GT_ModHandler.getModItem(EnderIO.ID, "blockFusedQuartz", 1L, 4),
                    500,
                    30);
        }

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 4),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 7))
                .fluidInputs(Materials.Plastic.getMolten(288)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Aluminium, 4),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 0))
                .fluidInputs(Materials.Plastic.getMolten(288)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Tantalum, 4),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 4),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 1))
                .fluidInputs(Materials.EnergeticAlloy.getMolten(576)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.EnderEye, 4),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 4),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 8))
                .fluidInputs(Materials.EnergeticSilver.getMolten(576)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Chrome, 4),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glowstone, 4),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 2))
                .fluidInputs(Materials.EnergeticAlloy.getMolten(576)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.EnderEye, 4),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glowstone, 4),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 9))
                .fluidInputs(Materials.EnergeticSilver.getMolten(576)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Platinum, 4),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        ItemList.QuantumEye.get(1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 3))
                .fluidInputs(Materials.CrystallineAlloy.getMolten(864)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Titanium, 4),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        ItemList.QuantumEye.get(1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 3))
                .fluidInputs(Materials.CrystallineAlloy.getMolten(864)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 4),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        GT_OreDictUnificator.get(OrePrefixes.gem, Materials.NetherStar, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EndSteel, 1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 4))
                .fluidInputs(Materials.MelodicAlloy.getMolten(1152)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.VanadiumGallium, 4),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        ItemList.QuantumStar.get(1L),
                        GT_ModHandler.getModItem(EnderIO.ID, "itemMaterial", 1L, 13),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 5))
                .fluidInputs(Materials.StellarAlloy.getMolten(1440)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LuV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedbolt", 4L, 10024),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Manganese, 4),
                        ItemList.Gravistar.get(1L),
                        GT_ModHandler.getModItem(EnderIO.ID, "itemFrankenSkull", 1L, 5),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StellarAlloy, 2),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 6))
                .fluidInputs(Materials.Neutronium.getMolten(1440)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_ZPM).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 5),
                        ItemList.Gravistar.get(1L),
                        GT_ModHandler.getModItem(EnderIO.ID, "itemFrankenSkull", 1L, 5),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StellarAlloy, 2))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "itemBasicCapacitor", 1L, 6))
                .fluidInputs(Materials.Neutronium.getMolten(1152)).noFluidOutputs().duration(10 * SECONDS)
                .eut(TierEU.RECIPE_ZPM).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(Minecraft.ID, "skull", 1L, 0),
                        GT_ModHandler.getModItem(Minecraft.ID, "rotten_flesh", 2L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.SiliconSG, 1))
                .itemOutputs(GT_ModHandler.getModItem(EnderIO.ID, "itemFrankenSkull", 1L, 5))
                .fluidInputs(Materials.Soularium.getMolten(288)).noFluidOutputs().duration(5 * SECONDS)
                .eut(TierEU.RECIPE_ZPM).addTo(sAssemblerRecipes);

    }

    private void makeAE2Recipes() {
        if (!AppliedEnergistics2.isModLoaded()) {
            return;
        }

        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 24),
                CustomItemList.EssentiaCircuit.get(1L),
                CustomItemList.EngineeringProcessorEssentiaPulsatingCore.get(1L),
                100,
                1920);
        // AE2 Illuminated Panel Assembler Recipe

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Cover_Screen.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Glowstone, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1L))
                .itemOutputs(GT_ModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1L, 180))
                .fluidInputs(Materials.SolderingAlloy.getMolten(144L)).noFluidOutputs().duration(5 * SECONDS).eut(4)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.glass_pane, 1, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 2L))
                .itemOutputs(GT_ModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 39))
                .noFluidInputs().noFluidOutputs().duration(5 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hatch_Output_Bus_HV.get(1L),
                        GT_ModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1L, 440),
                        GT_ModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 4L, 30),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Hatch_Output_Bus_ME.get(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hatch_Output_HV.get(1L),
                        GT_ModHandler.getModItem(AE2FluidCraft.ID, "part_fluid_interface", 1L),
                        GT_ModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 4L, 30),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Hatch_Output_ME.get(1)).noFluidInputs().noFluidOutputs().duration(15 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        ItemList.Hatch_Input_Bus_HV.get(1L),
                        GT_ModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1L, 440),
                        GT_ModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 4L, 30),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Hatch_Input_Bus_ME.get(1)).noFluidInputs().noFluidOutputs().duration(15 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        // Cell Workbench
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 2),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23),
                        ItemList.Cover_Screen.get(1),
                        getModItem(Minecraft.ID, "crafting_table", 1),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockCellWorkbench", 1))
                .fluidInputs(Materials.Titanium.getMolten(36L)).noFluidOutputs().duration(5 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        if (TinkerConstruct.isModLoaded()) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_ModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 1L, 0),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CertusQuartz, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 2L))
                    .itemOutputs(GT_ModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 39))
                    .noFluidInputs().noFluidOutputs().duration(5 * SECONDS).eut(16).addTo(sAssemblerRecipes);

        }
    }

    private void makeTinkersConstructRecipes() {
        if (!TinkerConstruct.isModLoaded()) {
            return;
        }

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Stone, 2L),
                        GT_ModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Tin, 1L))
                .itemOutputs(
                        GT_ModHandler
                                .getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowercobblestone", 1L))
                .noFluidInputs().noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L),
                        GT_ModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 1L))
                .itemOutputs(GT_ModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowerwood", 1L))
                .noFluidInputs().noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L),
                        GT_ModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 1L))
                .itemOutputs(GT_ModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowerwood", 1L))
                .noFluidInputs().noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.SandStoneRod.get(2L),
                        GT_ModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Cupronickel, 1L))
                .itemOutputs(
                        GT_ModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowersandstone", 1L))
                .noFluidInputs().noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(ForgeMicroblocks.ID, "stoneRod", 2L),
                        GT_ModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Nickel, 1L))
                .itemOutputs(GT_ModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowerstone", 1L))
                .noFluidInputs().noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.NetherQuartz, 2L),
                        GT_ModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Silver, 1L))
                .itemOutputs(
                        GT_ModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowerquartz", 1L))
                .noFluidInputs().noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 2L),
                        GT_ModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Electrum, 1L))
                .itemOutputs(GT_ModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepoweriron", 1L))
                .noFluidInputs().noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Gold, 2L),
                        GT_ModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Aluminium, 1L))
                .itemOutputs(GT_ModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowergold", 1L))
                .noFluidInputs().noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Emerald, 2L),
                        GT_ModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Nichrome, 1L))
                .itemOutputs(
                        GT_ModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepoweremerald", 1L))
                .noFluidInputs().noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Diamond, 2L),
                        GT_ModHandler.getModItem(TinkerConstruct.ID, "GlassPane", 2L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Platinum, 1L))
                .itemOutputs(
                        GT_ModHandler.getModItem(BuildCraftTransport.ID, "item.buildcraftPipe.pipepowerdiamond", 1L))
                .noFluidInputs().noFluidOutputs().duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // Assembler

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(TinkerConstruct.ID, "slime.gel", 1L, 1),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockITNT", 1L, 0),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(TinkerConstruct.ID, "explosive.slime", 1L, 0)).noFluidInputs()
                .noFluidOutputs().duration(30 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(TinkerConstruct.ID, "slime.gel", 1L, 0),
                        GT_ModHandler.getModItem(TinkerConstruct.ID, "explosive.slime", 1L, 0),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(TinkerConstruct.ID, "explosive.slime", 1L, 2)).noFluidInputs()
                .noFluidOutputs().duration(30 * SECONDS).eut(64).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Aluminium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 4L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(TinkerConstruct.ID, "heartCanister", 1L, 0)).noFluidInputs()
                .noFluidOutputs().duration(2 * MINUTES).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

    }

    private void makeForestryRecipes() {
        if (!Forestry.isModLoaded()) {
            return;
        }

        ItemStack alveary = GT_ModHandler.getModItem(Forestry.ID, "alveary", 1L, 0);

        // Impregnated Frame
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(Railcraft.ID, "slab", 3L, 38),
                        GT_ModHandler.getModItem(PamsHarvestCraft.ID, "wovencottonItem", 1L),
                        GT_ModHandler.getModItem(Forestry.ID, "oakStick", 5L, 0))
                .itemOutputs(GT_ModHandler.getModItem(Forestry.ID, "frameImpregnated", 1L, 0))
                .fluidInputs(Materials.SeedOil.getFluid(250L)).noFluidOutputs().duration(60 * SECONDS).eut(64)
                .addTo(sAssemblerRecipes);

        // Apiary
        List<ItemStack> fence = OreDictionary.getOres("fenceWood");
        for (ItemStack stack : fence) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_ModHandler.getModItem(Forestry.ID, "frameImpregnated", 1L, 0),
                            GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 2L),
                            GT_OreDictUnificator.get(OrePrefixes.slab, Materials.Wood, 2L),
                            GT_ModHandler.getModItem(Forestry.ID, "beeCombs", 1L, W),
                            GT_ModHandler.getModItem(Forestry.ID, "apiculture", 2L, 2),
                            stack.splitStack(2))
                    .itemOutputs(GT_ModHandler.getModItem(Forestry.ID, "apiculture", 1L, 0))
                    .fluidInputs(Materials.SeedOil.getFluid(1000L)).noFluidOutputs().duration(60 * SECONDS).eut(64)
                    .addTo(sAssemblerRecipes);
        }

        // Scented Paneling

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 2L),
                        GT_ModHandler.getModItem(Forestry.ID, "royalJelly", 1L, 0),
                        GT_ModHandler.getModItem(Forestry.ID, "oakStick", 3L, 0),
                        GT_ModHandler.getModItem(Forestry.ID, "beeswax", 2L, 0),
                        GT_ModHandler.getModItem(Forestry.ID, "pollen", 1L, W))
                .itemOutputs(GT_ModHandler.getModItem(Forestry.ID, "craftingMaterial", 1L, 6))
                .fluidInputs(Materials.Honey.getFluid(1000L)).noFluidOutputs().duration(60 * SECONDS).eut(64)
                .addTo(sAssemblerRecipes);
        // Swarmer

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        alveary,
                        GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 5),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.RoseGold, 2L),
                        GT_ModHandler.getModItem(Forestry.ID, "royalJelly", 2L, 0),
                        GT_ModHandler.getModItem(Forestry.ID, "frameProven", 1L, 0))
                .itemOutputs(GT_ModHandler.getModItem(Forestry.ID, "alveary", 1L, 2))
                .fluidInputs(Materials.Honey.getFluid(5000L)).noFluidOutputs().duration(60 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // Alveary Fan

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        alveary,
                        GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 11),
                        CustomItemList.SteelBars.get(3L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1L),
                        ItemList.Electric_Motor_MV.get(1L))
                .itemOutputs(GT_ModHandler.getModItem(Forestry.ID, "alveary", 1L, 3))
                .fluidInputs(Materials.Honey.getFluid(5000L)).noFluidOutputs().duration(60 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // Alveary Heater

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        alveary,
                        GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 7),
                        CustomItemList.SteelBars.get(1L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemRecipePart", 3L),
                        ItemList.Electric_Motor_MV.get(1L))
                .itemOutputs(GT_ModHandler.getModItem(Forestry.ID, "alveary", 1L, 4))
                .fluidInputs(Materials.Honey.getFluid(5000L)).noFluidOutputs().duration(60 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // Alveary Hygroregulator

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        alveary,
                        GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 6),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
                        GT_ModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 2L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1L))
                .itemOutputs(GT_ModHandler.getModItem(Forestry.ID, "alveary", 1L, 5))
                .fluidInputs(Materials.Honey.getFluid(5000L)).noFluidOutputs().duration(60 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // Alveary Stabiliser

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        alveary,
                        GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 4),
                        com.dreammaster.item.ItemList.ChargedCertusQuartzPlate.getIS(2),
                        ItemList.Component_Filter.get(2L),
                        GT_ModHandler.getModItem(Forestry.ID, "royalJelly", 1L, 0))
                .itemOutputs(GT_ModHandler.getModItem(Forestry.ID, "alveary", 1L, 6))
                .fluidInputs(Materials.Honey.getFluid(5000L)).noFluidOutputs().duration(60 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // Alveary Sieve

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        alveary,
                        GT_ModHandler.getModItem(Forestry.ID, "thermionicTubes", 4L, 9),
                        GT_ModHandler.getModItem(Forestry.ID, "craftingMaterial", 4L, 3),
                        GT_ModHandler.getModItem(Forestry.ID, "pollenFertile", 1L, GT_Values.W))
                .itemOutputs(GT_ModHandler.getModItem(Forestry.ID, "alveary", 1L, 7))
                .fluidInputs(Materials.Honey.getFluid(5000L)).noFluidOutputs().duration(60 * SECONDS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

    }

    private void makeOpenComputersRecipes() {
        if (!OpenComputers.isModLoaded()) {
            return;
        }
        // cable
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.RedstoneAlloy, 1),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(OpenComputers.ID, "cable", 1L, 0),
                200,
                120);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Emerald, 1),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(OpenComputers.ID, "cable", 1L, 0),
                200,
                120);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 9),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.RedstoneAlloy, 1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(OpenComputers.ID, "cable", 9L, 0)).noFluidInputs()
                .noFluidOutputs().duration(90 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 9),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Emerald, 1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_ModHandler.getModItem(OpenComputers.ID, "cable", 9L, 0)).noFluidInputs()
                .noFluidOutputs().duration(90 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        // keyboard
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone_button, 64), new ItemStack(Blocks.stone_button, 40),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1),
                        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 1L) },
                GT_Values.NF,
                GT_ModHandler.getModItem(OpenComputers.ID, "keyboard", 1L, 0),
                200,
                64);
        // case 1
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "case1", 1, 0),
                200,
                120);
        // case 2
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "case2", 1, 0),
                200,
                256);
        // case 3
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_EV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 2),
                        ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "case3", 1, 0),
                200,
                480);
        // micro case 1
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_MV.get(1L), ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 82),
                200,
                120);
        // mirco case 2
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_HV.get(1L), ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 86),
                200,
                256);
        // drone case 1
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 82),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 71),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 1L),
                        ItemList.Electric_Motor_HV.get(4L) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1, 83),
                300,
                256);
        // drone case 2
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 86),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 72),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Titanium, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 1L),
                        ItemList.Electric_Motor_EV.get(4L) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1, 87),
                300,
                480);
        // Card
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 1),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Gold, 1),
                        GT_Utility.getIntegratedCircuit(2) },
                GT_Values.NF,
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                200,
                64);
        // floppy
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 19),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Plastic, 4),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 4),
                200,
                64);

        // HDD 1
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 19),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 4L, 1), GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 5),
                300,
                120);
        // HDD 2
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 4L, 19),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 4L, 2), GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 6),
                300,
                256);
        // HDD 3
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 8L, 19),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 4L, 38),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 7),
                300,
                480);
        // disk
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Glass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Aluminium, 4),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Cobalt.getMolten(36L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 19),
                200,
                120);
        // rack
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "relay", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "powerDistributor", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                        ItemList.Circuit_Board_Plastic_Advanced.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Aluminium, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 4L),
                        CustomItemList.SteelBars.get(2L) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "rack", 1L, 0),
                200,
                256);
        // adapter block
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_LV.get(1L), ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "adapter", 1L, 0),
                200,
                120);
        // assembler
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_HV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25), ItemList.Robot_Arm_LV.get(3L),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "assembler", 1L, 0),
                200,
                256);
        // disassembler
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_HV.get(1L), GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25), ItemList.Robot_Arm_HV.get(2L) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "disassembler", 1L, 0),
                200,
                256);
        // capacitor
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_LV.get(2L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockElectric", 1L, 7),
                        ItemList.Circuit_Board_Plastic_Advanced.get(2L), ItemList.Circuit_Parts_Transistor.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2), GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "capacitor", 1L, 0),
                200,
                120);
        // charger
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_HV.get(1L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockChargepad", 2L, 2),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2), GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "charger", 1L, 0),
                200,
                256);
        // diskDrive
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_LV.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 3L, 25), ItemList.Electric_Motor_MV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.springSmall, Materials.Copper, 1),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1),
                        GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Glass, 1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "diskDrive", 1L, 0),
                200,
                120);
        // diskDriver
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "diskDrive", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L), CustomItemList.SteelBars.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 4),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 109),
                200,
                480);
        // geolyzer
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_MV.get(1L), ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 0), ItemList.Sensor_MV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.lens, Materials.EnderEye, 2),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(OpenComputers.ID, "geolyzer", 1L, 0),
                200,
                120);
        // hologram1
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 2),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 2),
                        GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 1),
                        GT_Utility.getIntegratedCircuit(10) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "hologram1", 1L, 0),
                200,
                120);
        // hologram2
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 2),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 4),
                        GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Emerald, 1),
                        GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Ruby, 1),
                        GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Sapphire, 1),
                        GT_Utility.getIntegratedCircuit(10) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "hologram2", 1L, 0),
                200,
                256);
        // motionSensor
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 42),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 2),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        new ItemStack(Blocks.daylight_detector, 2, 0), GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "motionSensor", 1L, 0),
                200,
                120);
        // netSplitter
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_LV.get(1L), ItemList.Circuit_Board_Plastic_Advanced.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 4),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 3L, 0), ItemList.Electric_Piston_LV.get(1L),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "netSplitter", 1L, 0),
                200,
                64);
        // printer
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Machine_HV_Printer.get(1L), ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 4),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "printer", 1L, 0),
                200,
                256);
        // powerConverter
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Transformer_HV_MV.get(1L), ItemList.Circuit_Board_Plastic_Advanced.get(2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "powerConverter", 1L, 0),
                200,
                256);
        // powerDistributor
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_MV.get(1L),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockElectric", 1L, 7),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "powerDistributor", 1L, 0),
                200,
                256);
        // raid
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "diskDrive", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 43),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 1),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 4),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "raid", 1L, 0),
                200,
                256);
        // redstone
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_MV.get(1L), ItemList.Circuit_Board_Plastic_Advanced.get(2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Redstone, 2),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 66),
                        ItemList.Cover_ActivityDetector.get(1L) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "redstone", 1L, 0),
                200,
                120);
        // relay
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_MV.get(1L), ItemList.Circuit_Board_Plastic_Advanced.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 2),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "relay", 1L, 0),
                200,
                120);
        // transposer
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Automation_ChestBuffer_LV.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 61),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 77),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 2),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "transposer", 2L, 0),
                400,
                120);
        // waypoint
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Casing_MV.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 48),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        ItemList.Circuit_Parts_Transistor.get(2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "waypoint", 1L, 0),
                200,
                64);
        // cartridge empty
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        ItemList.Circuit_Parts_Transistor.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.pipeTiny, Materials.Plastic, 8),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 8L),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(36L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 94),
                200,
                64);
        // cartridge full
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 94),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32414),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32420),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32425),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32427) },
                Materials.Water.getFluid(1000L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 95),
                200,
                64);
        // Interweb
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Plastic, 1),
                        new ItemStack(Items.string, 8, 0), GT_Utility.getIntegratedCircuit(2) },
                Materials.Glue.getFluid(576L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 48),
                200,
                64);
        // Linked Card
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 13),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 28),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 48),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.EnderEye.getMolten(288L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 51),
                400,
                480);
        // Manual
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Items.book, 1, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Glue.getFluid(144L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 98),
                100,
                30);
        // Nano Machine
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "capacitor", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 2),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 42),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 18),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 9L, 96) },
                Materials.Plastic.getMolten(144L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 107),
                400,
                256);
        // Server Tier 1
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "case1", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 3), GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(144L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 45),
                400,
                256);
        // Server Tier 2
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "case2", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 38),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(144L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 46),
                400,
                480);
        // Server Tier 3
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "case3", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 39),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(144L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 40),
                400,
                1024);
        // Tablet Case Tier 1
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 70),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Electrum, 4L) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 74),
                400,
                120);
        // Tablet Case Tier 2
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 72),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Electrum, 4L) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 92),
                400,
                256);
        // remote Monitor
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 34),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                        GT_ModHandler.getModItem(OpenComputers.ID, "keyboard", 1L, 0),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(144L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 41),
                400,
                120);
        // Terminal Server
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 3L, 13),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 16L),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 108),
                400,
                480);

        // Open Printers
        // Printer
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Machine_MV_Printer.get(1L), ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenPrinters.ID, "openprinter.printer", 1L, 0),
                300,
                120);
        // Shredder
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Machine_MV_Macerator.get(1L),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenPrinters.ID, "openprinter.shredder", 1L, 0),
                300,
                120);
        // Paper Roll
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Items.paper, 64, 0), new ItemStack(Items.paper, 64, 0),
                        new ItemStack(Items.paper, 64, 0), new ItemStack(Items.paper, 64, 0),
                        GT_Utility.getIntegratedCircuit(12) },
                GT_Values.NF,
                GT_ModHandler.getModItem(OpenPrinters.ID, "openprinter.printerPaperRoll", 1L, 0),
                200,
                64);
        // Black Ink Cartridge
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 94),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32414),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32414),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32414),
                        GT_Utility.getIntegratedCircuit(2) },
                Materials.Water.getFluid(1000L),
                GT_ModHandler.getModItem(OpenPrinters.ID, "openprinter.printerInkBlack", 1L, W),
                300,
                120);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenPrinters.ID, "openprinter.printerInkBlack", 1L, W),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32414),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32414),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32414),
                        GT_Utility.getIntegratedCircuit(2) },
                Materials.Water.getFluid(1000L),
                GT_ModHandler.getModItem(OpenPrinters.ID, "openprinter.printerInkBlack", 1L, W),
                150,
                120);
        // Color Ink Cartridge
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 94),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32415),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32416),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32418),
                        GT_Utility.getIntegratedCircuit(2) },
                Materials.Water.getFluid(1000L),
                GT_ModHandler.getModItem(OpenPrinters.ID, "openprinter.printerInkColor", 1L, W),
                300,
                120);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenPrinters.ID, "openprinter.printerInkColor", 1L, W),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32415),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32416),
                        GT_ModHandler.getModItem(GregTech.ID, "gt.metaitem.02", 1L, 32418),
                        GT_Utility.getIntegratedCircuit(2) },
                Materials.Water.getFluid(1000L),
                GT_ModHandler.getModItem(OpenPrinters.ID, "openprinter.printerInkColor", 1L, W),
                150,
                120);
        // Open Security
        // Magnetic Card Reader
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        ItemList.Circuit_Parts_Transistor.get(2L), GT_Utility.getIntegratedCircuit(2) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "magreader", 1L, 0),
                300,
                256);
        // RFID Reader
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.rfidReaderCard", 1L, 0),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GT_Utility.getIntegratedCircuit(2) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "rfidreader", 1L, 0),
                300,
                120);
        // Card writer
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 1),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        ItemList.Circuit_Parts_Transistor.get(2L) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "rfidwriter", 1L, 0),
                300,
                120);
        // Alarm
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.noteblock, 1, 0),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "alarm", 1L, 0),
                300,
                120);
        // Entity Detector
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 1),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "entitydetector", 1L, 0),
                300,
                120);
        // Door Controler
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        ItemList.Circuit_Parts_Transistor.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 4L),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "doorcontroller", 1L, 0),
                300,
                120);
        // Data Block
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 104),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 1L, 0),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.DataBlock", 1L, 0),
                300,
                120);
        // swtichable Hub
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "relay", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 4L, 0),
                        ItemList.Circuit_Parts_Transistor.get(2L), GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.SwitchableHub", 1L, 0),
                300,
                120);
        // kvm Hub
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "relay", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 4L, 0),
                        ItemList.Circuit_Parts_Transistor.get(2L), GT_Utility.getIntegratedCircuit(2) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.BlockKVM", 1L, 0),
                300,
                120);
        // energy turret
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenModularTurrets.ID, "laserTurret", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 4L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 4L, 0),
                        ItemList.Circuit_Parts_Transistor.get(2L), GT_Utility.getIntegratedCircuit(2) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "energyTurretBlock", 1L, 0),
                300,
                120);
        // keypad
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "keyboard", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        ItemList.Circuit_Parts_Transistor.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L),
                        GT_Utility.getIntegratedCircuit(2) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "keypadLock", 1L, 0),
                300,
                120);
        // biometric reader
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Glass, 4L),
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                        ItemList.Circuit_Parts_Transistor.get(2L), GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "biometricScanner", 1L, 0),
                300,
                120);
        // magnetic stipe card
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.IronMagnetic, 2L),
                        ItemList.Circuit_Parts_Transistor.get(1L), GT_Utility.getIntegratedCircuit(1) },
                Materials.Glue.getMolten(144L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.magCard", 2L, 0),
                150,
                64);
        // RFID Card
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.IronMagnetic, 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        ItemList.Circuit_Parts_Transistor.get(1L), GT_Utility.getIntegratedCircuit(2) },
                Materials.Glue.getMolten(144L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.rfidCard", 2L, 0),
                150,
                64);
        // RFID Reader Card
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 1),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                        GT_Utility.getIntegratedCircuit(2) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.rfidReaderCard", 1L, 0),
                300,
                120);
        // Secure Network Card
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 11),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.secureNetworkCard", 1L, 0),
                300,
                256);
        // Secure Door
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Items.iron_door, 1),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        ItemList.Circuit_Parts_Transistor.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 4L),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.securityDoor", 1L, 0),
                300,
                120);
        // Secure Private Door
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Items.iron_door, 1),
                        GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                        ItemList.Circuit_Parts_Transistor.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 4L),
                        GT_Utility.getIntegratedCircuit(2) },
                GT_Values.NF,
                GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.securityDoorPrivate", 1L, 0),
                300,
                120);
        // Damage Upgrade
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L), new ItemStack(Blocks.tnt, 1, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Redstone.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.damageUpgrade", 1L, 0),
                300,
                256);
        // Cooldown upgrade
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getIC2Item("reactorCoolantSimple", 1L, 1),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Redstone.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.cooldownUpgrade", 1L, 0),
                300,
                256);
        // Energy Upgrade
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 63),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Redstone.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.energyUpgrade", 1L, 0),
                300,
                256);
        // Movement Upgrade
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L), ItemList.Electric_Piston_MV.get(1L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Redstone.getMolten(72L),
                GT_ModHandler.getModItem(OpenSecurity.ID, "opensecurity.movementUpgrade", 1L, 0),
                300,
                256);
        // OpenGlasses Terminal
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "geolyzer", 1L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 43),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 38),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(144L),
                GT_ModHandler.getModItem(OpenGlasses.ID, "openglassesterminal", 1L, 0),
                300,
                480);
        // Open Glasses
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "screen3", 2L, 0),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 10),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                        GT_Utility.getIntegratedCircuit(1) },
                Materials.Plastic.getMolten(72L),
                GT_ModHandler.getModItem(OpenGlasses.ID, "openglasses", 1L, W),
                300,
                480);
    }

    private void makeJabbaRecipes() {
        if (!JABBA.isModLoaded()) {
            return;
        }
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 2),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2),
                        GT_Utility.getIntegratedCircuit(13) },
                GT_Values.NF,
                GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 0),
                200,
                16);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Copper, 2),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Copper, 2),
                        GT_Utility.getIntegratedCircuit(13) },
                GT_Values.NF,
                GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 1),
                200,
                16);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 2),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 2),
                        GT_Utility.getIntegratedCircuit(13) },
                GT_Values.NF,
                GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 2),
                200,
                16);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 2),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Bronze, 2),
                        GT_Utility.getIntegratedCircuit(13) },
                GT_Values.NF,
                GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 3),
                200,
                16);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2),
                        GT_Utility.getIntegratedCircuit(13) },
                GT_Values.NF,
                GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 4),
                200,
                16);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2),
                        GT_Utility.getIntegratedCircuit(13) },
                GT_Values.NF,
                GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 5),
                200,
                16);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 2),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2),
                        GT_Utility.getIntegratedCircuit(13) },
                GT_Values.NF,
                GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 6),
                200,
                16);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2),
                        GT_Utility.getIntegratedCircuit(13) },
                GT_Values.NF,
                GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 7),
                200,
                16);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 2),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2),
                        GT_Utility.getIntegratedCircuit(13) },
                GT_Values.NF,
                GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 8),
                200,
                16);
        if (BartWorks.isModLoaded()) {
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                            GT_ModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedplate", 2L, 88),
                            GT_ModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedstick", 2L, 88),
                            GT_Utility.getIntegratedCircuit(13) },
                    GT_Values.NF,
                    GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 9),
                    200,
                    16);
        } else {
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 2),
                            GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 2),
                            GT_Utility.getIntegratedCircuit(13) },
                    GT_Values.NF,
                    GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 9),
                    200,
                    16);
        }
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 2),
                        GT_Utility.getIntegratedCircuit(13) },
                GT_Values.NF,
                GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 10),
                200,
                16);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Osmium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Osmium, 2),
                        GT_Utility.getIntegratedCircuit(13) },
                GT_Values.NF,
                GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 11),
                200,
                16);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Neutronium, 2),
                        GT_Utility.getIntegratedCircuit(13) },
                GT_Values.NF,
                GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 12),
                200,
                16);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(JABBA.ID, "barrel", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 2),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.BlackPlutonium, 2),
                        GT_Utility.getIntegratedCircuit(13) },
                GT_Values.NF,
                GT_ModHandler.getModItem(JABBA.ID, "upgradeStructural", 1L, 13),
                200,
                16);
    }

    private void makeZTonesRecipes() {
        if (!ZTones.isModLoaded()) {
            return;
        }
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone_slab, 4), new ItemStack(Blocks.stone, 1),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(ZTones.ID, "stoneTile", 8L, 0),
                160,
                4);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.glass, 4), new ItemStack(Items.dye, 1, W),
                        GT_Utility.getIntegratedCircuit(1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(ZTones.ID, "auroraBlock", 8L, 0),
                160,
                4);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.sand, 4, W), new ItemStack(Blocks.dirt, 4, W),
                        GT_Utility.getIntegratedCircuit(2) },
                Materials.SeedOil.getFluid(5L),
                GT_ModHandler.getModItem(ZTones.ID, "cleanDirt", 8L, 0),
                160,
                4);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone_pressure_plate, 1), GT_Utility.getIntegratedCircuit(1) },
                Materials.Blaze.getMolten(8L),
                GT_ModHandler.getModItem(ZTones.ID, "booster", 1L, 0),
                100,
                30);

        // This replaces the type of block, and the item used to make it for the 0-15 meta, and the 21+12 different
        // versions of ztones blocks (glaxx is separate)
        // Normal crafting recipes are not affected, so they might be crafted differently
        String[] blockName = { "agon", "azur", "bitt", "cray", "fort", "iszm", "jelt", "korp", "kryp", "lair", "lave",
                "mint", "myst", "reds", "reed", "roen", "sols", "sync", "tank", "vect", "vena" };
        String[] zblockName = { "zane", "zech", "zest", "zeta", "zion", "zkul", "zoea", "zome", "zone", "zorg", "ztyl",
                "zyth" };
        ItemStack[] item = { new ItemStack(Items.dye, 1, 7), new ItemStack(Items.dye, 1, 4),
                new ItemStack(Blocks.wool, 1, 0), new ItemStack(Blocks.hardened_clay, 1, 0),
                new ItemStack(Items.dye, 1, 3), new ItemStack(Items.dye, 1, 8), new ItemStack(Items.gold_ingot, 1, 0),
                new ItemStack(Blocks.obsidian, 1, 0), new ItemStack(Blocks.soul_sand, 1, 0),
                new ItemStack(Blocks.netherrack, 1, 0), new ItemStack(Blocks.ice, 1, 0),
                new ItemStack(Items.slime_ball, 1, 0), new ItemStack(Blocks.brown_mushroom, 1, 0),
                new ItemStack(Items.redstone, 1, 0), new ItemStack(Items.reeds, 1, 0),
                new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Items.blaze_powder, 1, 0),
                new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_ingot, 1, 0),
                new ItemStack(Items.ghast_tear, 1, 0), new ItemStack(Items.ender_pearl, 1, 0) };
        ItemStack[] zitem = { new ItemStack(Items.dye, 1, 0), new ItemStack(Items.dye, 1, 1),
                new ItemStack(Items.dye, 1, 2), new ItemStack(Items.dye, 1, 5), new ItemStack(Items.dye, 1, 6),
                new ItemStack(Items.dye, 1, 9), new ItemStack(Items.dye, 1, 10), new ItemStack(Items.dye, 1, 11),
                new ItemStack(Items.dye, 1, 12), new ItemStack(Items.dye, 1, 13), new ItemStack(Items.dye, 1, 14),
                new ItemStack(Items.dye, 1, 15) };

        for (int j = 0; j < 21; j++) {
            for (int i = 0; i < 16; i++) {
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(ZTones.ID, "stoneTile", 4L, 0), item[j],
                                GT_Utility.getIntegratedCircuit(i == 0 ? 24 : i) },
                        GT_Values.NF,
                        GT_ModHandler.getModItem(ZTones.ID, "tile." + blockName[j] + "Block", 8L, i),
                        200,
                        16);
            }
        }

        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < 16; i++) {
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(ZTones.ID, "auroraBlock", 4L, 0), zitem[j],
                                GT_Utility.getIntegratedCircuit(i == 0 ? 24 : i) },
                        GT_Values.NF,
                        GT_ModHandler.getModItem(ZTones.ID, "tile." + zblockName[j] + "Block", 8L, i),
                        200,
                        16);
            }
        }

        for (int i = 0; i < 16; i++) {
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] { GT_ModHandler.getModItem(ZTones.ID, "auroraBlock", 4L, 0),
                            new ItemStack(Blocks.glass, 1, 0), GT_Utility.getIntegratedCircuit(i == 0 ? 24 : i) },
                    GT_Values.NF,
                    GT_ModHandler.getModItem(ZTones.ID, "tile.glaxx", 8L, i),
                    200,
                    16);
        }
    }

    private void makeGalacticraftRocketRecipes() {
        if (!GalaxySpace.isModLoaded()) {
            return;
        }

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIVBase, 30L),
                        GT_OreDictUnificator.get(OrePrefixes.pipeTiny, MaterialsUEVplus.TranscendentMetal, 20L),
                        ItemList.Electric_Pump_UIV.get(1L),
                        GT_Utility.getIntegratedCircuit(9))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 30L))
                .fluidInputs(new FluidStack(FluidRegistry.getFluid("liquid helium"), 34000)).noFluidOutputs()
                .duration(2 * MINUTES + 40 * SECONDS).eut(TierEU.RECIPE_UIV).addTo(sAssemblerRecipes);

        GT_Values.RA.addAssemblerRecipe(
                GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 7),
                GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 3),
                new FluidStack(FluidRegistry.getFluid("ic2coolant"), 1000),
                CustomItemList.LeadOriharukonPlate.get(2L),
                300,
                500000);

        // rocket parts

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualBronze", 1L, 0),
                        GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualAluminium", 1L, 0),
                        GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedCoal", 1L, 0),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(CustomItemList.RawSDHCAlloy.get(1L)).fluidInputs(Materials.StainlessSteel.getMolten(72L))
                .noFluidOutputs().duration(20 * TICKS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualBronze", 1L, 0),
                        GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualAluminium", 1L, 0),
                        GT_ModHandler.getModItem(GalaxySpace.ID, "item.ModuleSmallCanister", 1L, 0),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(GT_ModHandler.getModItem(GalaxySpace.ID, "item.ModuleSmallFuelCanister", 1L, 0))
                .fluidInputs(Materials.StainlessSteel.getMolten(72L)).noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(GalaxySpace.ID, "item.ModuleSmallFuelCanister", 1L, 0),
                        CustomItemList.TitaniumDualCompressedPlates.get(1),
                        CustomItemList.DeshDualCompressedPlates.get(1),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(CustomItemList.MediumFuelCanister.get(1)).fluidInputs(Materials.Titanium.getMolten(72L))
                .noFluidOutputs().duration(20 * TICKS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.MediumFuelCanister.get(1),
                        CustomItemList.QuantinumDualCompressedPlates.get(1),
                        CustomItemList.IceDualCompressedPlates.get(1),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(CustomItemList.LargeFuelCanister.get(1L)).fluidInputs(Materials.Chrome.getMolten(72L))
                .noFluidOutputs().duration(20 * TICKS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.LargeFuelCanister.get(1),
                        CustomItemList.MytrylDualCompressedPlates.get(1),
                        CustomItemList.MysteriousCrystalDualCompressedPlates.get(1),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(CustomItemList.ExtraLargeFuelCanister.get(1L))
                .fluidInputs(Materials.Iridium.getMolten(72L)).noFluidOutputs().duration(20 * TICKS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        // Engine

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 2L, 0),
                        GT_ModHandler.getModItem(GalacticraftCore.ID, "item.heavyPlating", 4L, 0),
                        GT_ModHandler.getModItem(GalacticraftCore.ID, "item.oilCanisterPartial", 1L, 1001),
                        ItemList.Casing_Firebox_Steel.get(1L),
                        ItemList.Cover_ActivityDetector.get(1L),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(GT_ModHandler.getModItem(GalacticraftCore.ID, "item.engine", 1L, 0)).noFluidInputs()
                .noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(GalacticraftCore.ID, "item.engine", 2L, 1),
                        GT_ModHandler.getModItem(GalacticraftCore.ID, "item.engine", 2L, 0),
                        GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 5L, 0),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 1L, 1))
                .noFluidInputs().noFluidOutputs().duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Tier2Booster.get(2),
                        GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 2L, 1),
                        CustomItemList.HeavyDutyPlateTier5.get(5),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.HeavyDutyRocketEngineTier3.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(5 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.Tier3Booster.get(2),
                        CustomItemList.HeavyDutyRocketEngineTier3.get(2L),
                        CustomItemList.HeavyDutyPlateTier7.get(5),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(CustomItemList.HeavyDutyRocketEngineTier4.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(5 * SECONDS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        // nose cones

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler
                                .getModItem(ProjectRedIllumination.ID, "projectred.illumination.cagelamp2.inv", 1L, 14),
                        GT_ModHandler.getModItem(GalacticraftCore.ID, "item.heavyPlating", 4L, 0),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(GT_ModHandler.getModItem(GalacticraftCore.ID, "item.noseCone", 1L, 0))
                .fluidInputs(Materials.StainlessSteel.getFluid(36L)).noFluidOutputs().duration(2 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(GalacticraftCore.ID, "item.noseCone", 1L, 0),
                        GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 4L, 0),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(GT_ModHandler.getModItem(GalacticraftMars.ID, "item.heavyNoseCone", 1L, 0))
                .fluidInputs(Materials.Titanium.getFluid(36L)).noFluidOutputs().duration(2 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(GalacticraftMars.ID, "item.heavyNoseCone", 1L, 0),
                        CustomItemList.HeavyDutyPlateTier5.get(4),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(CustomItemList.HeavyDutyNoseConeTier3.get(1L))
                .fluidInputs(Materials.TungstenSteel.getMolten(36L)).noFluidOutputs().duration(2 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.HeavyDutyNoseConeTier3.get(1L),
                        CustomItemList.HeavyDutyPlateTier7.get(4),
                        GT_Utility.getIntegratedCircuit(4))
                .itemOutputs(CustomItemList.HeavyDutyNoseConeTier4.get(1L))
                .fluidInputs(Materials.NaquadahAlloy.getMolten(36L)).noFluidOutputs().duration(2 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        // rocket fins

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(GalacticraftCore.ID, "item.basicItem", 2L, 9),
                        GT_ModHandler.getModItem(GalacticraftCore.ID, "item.heavyPlating", 4L, 0),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(GT_ModHandler.getModItem(GalacticraftCore.ID, "item.rocketFins", 1L, 0)).noFluidInputs()
                .noFluidOutputs().duration(2 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder().noItemInputs()
                .itemOutputs(GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 1L, 2))
                .noFluidInputs().noFluidOutputs().duration(2 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV)
                .addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.HeavyDutyPlateTier4.get(2),
                        CustomItemList.HeavyDutyPlateTier5.get(4),
                        GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(CustomItemList.HeavyDutyRocketFinsTier3.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(2 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.HeavyDutyPlateTier6.get(2),
                        CustomItemList.HeavyDutyPlateTier7.get(4),
                        GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0),
                        GT_Utility.getIntegratedCircuit(5))
                .itemOutputs(CustomItemList.HeavyDutyRocketFinsTier4.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(2 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        // // booster

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(GalacticraftCore.ID, "item.meteoricIronIngot", 3L, 1),
                        GT_ModHandler.getModItem(GalacticraftCore.ID, "item.heavyPlating", 4L, 0),
                        GT_ModHandler.getModItem(GalacticraftCore.ID, "item.airVent", 1L, 0),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(GT_ModHandler.getModItem(GalacticraftCore.ID, "item.engine", 1L, 1))
                .fluidInputs(Materials.Fuel.getFluid(1000L)).noFluidOutputs().duration(2 * SECONDS + 10 * TICKS)
                .eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.LedoxCompressedPlate.get(3),
                        GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 4L, 0),
                        GT_ModHandler.getModItem(GalacticraftCore.ID, "item.engine", 1L, 1),
                        GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(CustomItemList.Tier2Booster.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(2 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.MytrylCompressedPlate.get(3),
                        CustomItemList.HeavyDutyPlateTier5.get(4),
                        CustomItemList.Tier2Booster.get(1L),
                        GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(CustomItemList.Tier3Booster.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(2 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.BlackPlutoniumCompressedPlate.get(3),
                        CustomItemList.HeavyDutyPlateTier7.get(4),
                        CustomItemList.Tier3Booster.get(1L),
                        GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0),
                        GT_Utility.getIntegratedCircuit(6))
                .itemOutputs(CustomItemList.Tier4Booster.get(1L)).noFluidInputs().noFluidOutputs()
                .duration(2 * SECONDS + 10 * TICKS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);

    }

    private void makeIronTanksRecipes() {
        if (!IronTanks.isModLoaded() || !BuildCraftFactory.isModLoaded()) {
            return;
        }
        // Copper Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Copper, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(144L),
                GT_ModHandler.getModItem(IronTanks.ID, "copperTank", 1L, 0),
                300,
                30);
        // Iron Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Iron, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(144L),
                GT_ModHandler.getModItem(IronTanks.ID, "ironTank", 1L, 0),
                400,
                30);
        // Steel Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(144L),
                GT_ModHandler.getModItem(IronTanks.ID, "silverTank", 1L, 0),
                500,
                30);
        // Gold Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Gold, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(144L),
                GT_ModHandler.getModItem(IronTanks.ID, "goldTank", 1L, 0),
                600,
                30);
        // Diamond Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(144L),
                GT_ModHandler.getModItem(IronTanks.ID, "diamondTank", 1L, 0),
                800,
                30);
        // Obsidian Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Obsidian, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(144L),
                GT_ModHandler.getModItem(IronTanks.ID, "obsidianTank", 1L, 0),
                900,
                30);
        // Aluminium Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Aluminium, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(144L),
                GT_ModHandler.getModItem(IronTanks.ID, "emeraldTank", 1L, 0),
                1000,
                60);
        // Stainless Steel Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.StainlessSteel, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(144L),
                GT_ModHandler.getModItem(IronTanks.ID, "stainlesssteelTank", 1L, 0),
                1100,
                120);
        // Titanium Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(144L),
                GT_ModHandler.getModItem(IronTanks.ID, "titaniumTank", 1L, 0),
                1200,
                256);
        // Tungsten Steel Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_ModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(144L),
                GT_ModHandler.getModItem(IronTanks.ID, "tungstensteelTank", 1L, 0),
                1300,
                480);

        // --- Upgrade Glass to Copper Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Copper, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(72L),
                GT_ModHandler.getModItem(IronTanks.ID, "glassCopperUpgrade", 1L, 0),
                300,
                30);
        // --- Upgrade Glass to Iron Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Iron, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(72L),
                GT_ModHandler.getModItem(IronTanks.ID, "glassIronUpgrade", 1L, 0),
                400,
                30);
        // --- Upgrade Copper to Iron Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Copper, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Iron, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(72L),
                GT_ModHandler.getModItem(IronTanks.ID, "copperIronUpgrade", 1L, 0),
                500,
                30);
        // --- Upgrade Copper to Steel Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Copper, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(72L),
                GT_ModHandler.getModItem(IronTanks.ID, "copperSilverUpgrade", 1L, 0),
                600,
                30);
        // --- Upgrade Iron to Gold Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Gold, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(72L),
                GT_ModHandler.getModItem(IronTanks.ID, "ironGoldUpgrade", 1L, 0),
                700,
                30);
        // --- Upgrade Steel to Gold Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Gold, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(72L),
                GT_ModHandler.getModItem(IronTanks.ID, "silverGoldUpgrade", 1L, 0),
                700,
                30);
        // --- Upgrade Steel to Diamond Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 2L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(72L),
                GT_ModHandler.getModItem(IronTanks.ID, "silverDiamondUpgrade", 1L, 0),
                800,
                30);
        // --- Upgrade Diamond to Obsidian
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Obsidian, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(72L),
                GT_ModHandler.getModItem(IronTanks.ID, "diamondObsidianUpgrade", 1L, 0),
                900,
                30);
        // --- Upgrade Diamond to Aluminium Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Aluminium, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(72L),
                GT_ModHandler.getModItem(IronTanks.ID, "diamondEmeraldUpgrade", 1L, 0),
                1000,
                60);
        // --- Upgrade Aluminium to Stainless Steel Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.StainlessSteel, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(72L),
                GT_ModHandler.getModItem(IronTanks.ID, "emeraldStainlesssteelUpgrade", 1L, 0),
                1100,
                120);
        // --- Upgrade Stainless Steel to Titanium Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(72L),
                GT_ModHandler.getModItem(IronTanks.ID, "stainlesssteelTitaniumUpgrade", 1L, 0),
                1200,
                256);
        // --- Upgrade Titanium to Tungsten Steel Tank
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 1L),
                        GT_Utility.getIntegratedCircuit(4) },
                Materials.Glass.getMolten(72L),
                GT_ModHandler.getModItem(IronTanks.ID, "titaniumTungstensteelUpgrade", 1L, 0),
                1300,
                480);
    }

    private void makeChiselRecipes() {
        if (!Chisel.isModLoaded()) {
            return;
        }
        // --- Items
        // Chisel
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "chisel", 1L, 0),
                300,
                30);
        // Obsidian Chisel
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.WroughtIron, 2L) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "obsidianChisel", 1L, 0),
                400,
                30);
        // Diamond Chisel
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "diamondChisel", 1L, 0),
                600,
                30);
        // Nether Star Chisel
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Bedrockium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.VanadiumSteel, 2L) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "netherStarChisel", 1L, 0),
                24000,
                480);

        // --- Blocks
        // Factory Block
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L),
                        GT_Utility.getIntegratedCircuit(24) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "factoryblock", 16L, 0),
                100,
                24);
        // Technical Block
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 5),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L),
                        GT_Utility.getIntegratedCircuit(11) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "technical", 16L, 0),
                100,
                24);
        // Tyrian
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L),
                        GT_Utility.getIntegratedCircuit(14) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "tyrian", 8L, 0),
                100,
                24);
        // Futura
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 4), new ItemStack(Items.redstone, 1),
                        GT_Utility.getIntegratedCircuit(24) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "futura", 8L, 0),
                100,
                24);
        // Fantasy Block
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 4), new ItemStack(Items.gold_nugget, 1),
                        GT_Utility.getIntegratedCircuit(24) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "fantasyblock", 8L, 0),
                100,
                24);
        // Grimstone
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 4), new ItemStack(Items.coal, 1),
                        GT_Utility.getIntegratedCircuit(24) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "grimstone", 8L, 0),
                100,
                24);
        // Hex Plating
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 2), new ItemStack(Items.coal, 4),
                        GT_Utility.getIntegratedCircuit(11) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "hexPlating", 4L, 0),
                100,
                24);
        // Holystone
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 4), new ItemStack(Items.feather, 1),
                        GT_Utility.getIntegratedCircuit(24) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "holystone", 8L, 0),
                100,
                24);
        // Laboratory Block
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 4), new ItemStack(Items.quartz, 1),
                        GT_Utility.getIntegratedCircuit(24) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "laboratoryblock", 8L, 0),
                100,
                24);
        // Lavastone
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 4), GT_Utility.getIntegratedCircuit(24) },
                Materials.Lava.getFluid(1000L),
                GT_ModHandler.getModItem(Chisel.ID, "lavastone", 8L, 0),
                100,
                24);
        // Paperwall
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Items.paper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 1L),
                        GT_Utility.getIntegratedCircuit(24) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "paperwall", 8L, 0),
                100,
                24);
        // Paperwall Block
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Items.paper, 4),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 1L),
                        GT_Utility.getIntegratedCircuit(11) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "paperwall_block", 8L, 0),
                100,
                24);
        // Road Lines
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Items.redstone, 3),
                        GT_OreDictUnificator.get(ItemList.Dye_Bonemeal.get(3L)), GT_Utility.getIntegratedCircuit(24) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "road_line", 8L, 0),
                100,
                24);
        // Temple Block
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 4),
                        GT_OreDictUnificator.get(new ItemStack(Items.dye, 1, 4)), GT_Utility.getIntegratedCircuit(24) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "templeblock", 8L, 0),
                100,
                24);
        // Mossy Temple Block
        if (BiomesOPlenty.isModLoaded()) {
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] { GT_ModHandler.getModItem(Chisel.ID, "templeblock", 8L, 0),
                            GT_ModHandler.getModItem(BiomesOPlenty.ID, "moss", 8L, 0),
                            GT_Utility.getIntegratedCircuit(24) },
                    GT_Values.NF,
                    GT_ModHandler.getModItem(Chisel.ID, "mossy_templeblock", 4L, 0),
                    100,
                    24);
        }
        // Valentines Block
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 4),
                        GT_OreDictUnificator.get(new ItemStack(Items.dye, 1, 9)), GT_Utility.getIntegratedCircuit(24) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "valentines", 8L, 0),
                100,
                24);
        // Voidstone
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 2), new ItemStack(Blocks.obsidian, 2),
                        new ItemStack(Items.ender_pearl, 1), GT_Utility.getIntegratedCircuit(24) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "voidstone", 8L, 0),
                100,
                24);
        // Energised Voidstone
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 2), new ItemStack(Blocks.obsidian, 2),
                        new ItemStack(Items.ender_pearl, 1), new ItemStack(Items.glowstone_dust, 1),
                        GT_Utility.getIntegratedCircuit(11) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "voidstone2", 8L, 0),
                100,
                24);
        // Warning Sign
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 2), new ItemStack(Items.sign, 1),
                        GT_Utility.getIntegratedCircuit(24) },
                GT_Values.NF,
                GT_ModHandler.getModItem(Chisel.ID, "warningSign", 4L, 0),
                100,
                24);
        // Waterstone
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { new ItemStack(Blocks.stone, 4), GT_Utility.getIntegratedCircuit(24) },
                Materials.Water.getFluid(1000L),
                GT_ModHandler.getModItem(Chisel.ID, "waterstone", 8L, 0),
                100,
                24);
        // Hempcrete Sand
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Blocks.gravel, 3, 0),
                        GT_OreDictUnificator.get("sand", 3L),
                        GT_OreDictUnificator.get("itemWheat", 2L),
                        ItemList.IC2_Plantball.get(1L))
                .noFluidOutputs().noFluidInputs().itemOutputs(GT_ModHandler.getModItem(Chisel.ID, "hempcretesand", 12))
                .duration(5 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
    }

    private void makeReinforcedIronPlateRecipes() {
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartCarbonPlate", 4),
                        CustomItemList.AluminiumIronPlate.get(1) },
                GT_Values.NF,
                CustomItemList.ReinforcedAluminiumIronPlate.get(1),
                100,
                120);

        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.SiliconSG, 4),
                        CustomItemList.TitaniumIronPlate.get(1) },
                GT_Values.NF,
                CustomItemList.ReinforcedTitaniumIronPlate.get(1),
                100,
                480);

        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 4),
                        CustomItemList.TungstenIronPlate.get(1) },
                GT_Values.NF,
                CustomItemList.ReinforcedTungstenIronPlate.get(1),
                100,
                1920);

        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 4),
                        CustomItemList.TungstenSteelIronPlate.get(1) },
                GT_Values.NF,
                CustomItemList.ReinforcedTungstenSteelIronPlate.get(1),
                100,
                7680);

        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 4),
                        CustomItemList.ChromeIronPlate.get(1) },
                GT_Values.NF,
                CustomItemList.ReinforcedChromeIronPlate.get(1),
                100,
                30720);

        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartIridium", 4),
                        GT_ModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 1, 6) },
                GT_Values.NF,
                GT_ModHandler.getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 1, 7),
                100,
                122880);

        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.MysteriousCrystal, 4),
                        CustomItemList.NaquadriaIronPlate.get(1) },
                GT_Values.NF,
                CustomItemList.ReinforcedNaquadriaIronPlate.get(1),
                100,
                491520);

        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 4),
                        CustomItemList.NeutroniumIronPlate.get(1) },
                GT_Values.NF,
                CustomItemList.ReinforcedNeutroniumIronPlate.get(1),
                100,
                1966080);

        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemPartAlloy", 4),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 4),
                        CustomItemList.BedrockiumIronPlate.get(1) },
                GT_Values.NF,
                CustomItemList.ReinforcedBedrockiumIronPlate.get(1),
                100,
                7864320);
    }

    private void makeMixedMetalIngotRecipes() {
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyIron, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyIron, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyIron, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyIron, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyIron, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyIron, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Nickel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Nickel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Nickel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Nickel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Nickel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Nickel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 1L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 2L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 3L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 4L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 4L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 4L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 4L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 4L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 4L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 5L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 5L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 5L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 5L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnyBronze, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 6L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Brass, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 6L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 8L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 8L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 8L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Zinc, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 10L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 10L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 10L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnnealedCopper, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 12L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RoseGold, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 12L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AstralSilver, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 12L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnnealedCopper, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 14L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RoseGold, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 14L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AstralSilver, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 14L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnnealedCopper, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 16L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RoseGold, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 16L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AstralSilver, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 16L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSE, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnnealedCopper, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 18L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSE, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RoseGold, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 18L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSE, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AstralSilver, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 18L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnnealedCopper, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 20L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RoseGold, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 20L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AstralSilver, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 20L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 22L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSE, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 24L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSE, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 26L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Osmiridium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 28L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Osmiridium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSE, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 30L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Osmiridium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSE, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 32L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnergeticAlloy, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 34L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnergeticAlloy, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 36L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnergeticAlloy, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Draconium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 38L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnergeticAlloy, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 40L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Sunnarium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 42L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Sunnarium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Draconium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 44L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.HSSS, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 48L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Naquadah, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 52L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 56L, 4),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_Utility.getIntegratedCircuit(3),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 1) },
                GT_Values.NF,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemIngot", 64L, 4),
                100,
                30);
    }

    private void makeLootBagRecipes() {
        if (!EnhancedLootBags.isModLoaded()) {
            return;
        }
        // Basic -> Steam Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 1, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 2, missing)).noFluidInputs().noFluidOutputs()
                .duration(SECONDS * 30).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Steam -> LV Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 2, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 4, missing)).noFluidInputs().noFluidOutputs()
                .duration(SECONDS * 30).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // LV -> MV Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 4, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 5, missing)).noFluidInputs().noFluidOutputs()
                .duration(SECONDS * 30).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // MV -> HV Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 5, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 6, missing)).noFluidInputs().noFluidOutputs()
                .duration(SECONDS * 30).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        // HV -> EV Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 6, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 7, missing)).noFluidInputs().noFluidOutputs()
                .duration(SECONDS * 30).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        // EV -> IV Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 7, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 8, missing)).noFluidInputs().noFluidOutputs()
                .duration(SECONDS * 30).eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);
        // IV -> LuV Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 8, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 41, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_LuV).addTo(sAssemblerRecipes);
        // Forest Ranger Basic -> Forest Ranger Advanced Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 19, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 20, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Forest Ranger Advanced -> Forest Ranger Expert Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 20, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 21, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // BM Novice -> BM Adept Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 16, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 17, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // BM Adept -> BM Master Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 17, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 18, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // Bees Basic -> Bees Advanced Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 25, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 26, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Bees Advanced -> Bees Expert Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 26, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 27, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
        // Space Invaders Moon -> Space Invaders Mars Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 22, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 23, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        // Space Invaders Mars -> Space Invaders Asteroids Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 23, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 24, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
        // Fast Food -> Slow Food Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 28, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 29, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Slow Food -> Haute Cuisine Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 29, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 30, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Haute Cuisine -> Dessert Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 30, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 31, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Transportation 3 -> 2 Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 32, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 33, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Transportation 2 -> 1 Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 33, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 34, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Magic Novice -> Magic Adept Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 9, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 10, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Magic Adept -> Magic Master Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 10, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 11, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // Magic Master -> Magic Grandmaster Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 3, 11, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 12, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
        // Magic Grandmaster -> Magic Grandmaster Unique Lootbags
        GT_Values.RA.stdBuilder().itemInputs(getModItem(EnhancedLootBags.ID, "lootbag", 16, 12, missing))
                .itemOutputs(getModItem(EnhancedLootBags.ID, "lootbag", 1, 13, missing)).noFluidInputs()
                .noFluidOutputs().duration(SECONDS * 30).eut(TierEU.RECIPE_MV).addTo(sAssemblerRecipes);
    }

    private void makeCoinRecipes() {
        // Coins
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinAdventure.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinAdventureI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinAdventureI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinAdventureII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinAdventureII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinAdventureIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinAdventureIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinAdventureIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBees.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinBeesI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBeesI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinBeesII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBeesII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinBeesIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBeesIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinBeesIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBlood.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinBloodI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBloodI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinBloodII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBloodII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinBloodIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBloodIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinBloodIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinChemist.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinChemistI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinChemistI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinChemistII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinChemistII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinChemistIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinChemistIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinChemistIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinCook.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinCookI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinCookI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinCookII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinCookII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinCookIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinCookIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinCookIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinDarkWizard.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinDarkWizardI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinDarkWizardI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinDarkWizardII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinDarkWizardII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinDarkWizardIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinDarkWizardIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinDarkWizardIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFarmer.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinFarmerI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFarmerI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinFarmerII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFarmerII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinFarmerIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFarmerIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinFarmerIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinForestry.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinForestryI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinForestryI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinForestryII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinForestryII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinForestryIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinForestryIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinForestryIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSpace.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinSpaceI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSpaceI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinSpaceII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSpaceII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinSpaceIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSpaceIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinSpaceIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSurvivor.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinSurvivorI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSurvivorI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinSurvivorII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSurvivorII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinSurvivorIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSurvivorIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinSurvivorIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinTechnician.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinTechnicianI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinTechnicianI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinTechnicianII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinTechnicianII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinTechnicianIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinTechnicianIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinTechnicianIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSmith.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinSmithI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSmithI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinSmithII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSmithII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinSmithIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSmithIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinSmithIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinWitch.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinWitchI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinWitchI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinWitchII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinWitchII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinWitchIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinWitchIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinWitchIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFlower.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinFlowerI.get(1L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFlowerI.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinFlowerII.get(1L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFlowerII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinFlowerIII.get(1L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFlowerIII.get(10L),
                GT_Utility.getIntegratedCircuit(10),
                CustomItemList.CoinFlowerIV.get(1L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinAdventureI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinAdventure.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinAdventureII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinAdventureI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinAdventureIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinAdventureII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinAdventureIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinAdventureIII.get(10L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBeesI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinBees.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBeesII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinBeesI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBeesIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinBeesII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBeesIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinBeesIII.get(10L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBloodI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinBlood.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBloodII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinBloodI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBloodIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinBloodII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinBloodIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinBloodIII.get(10L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinChemistI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinChemist.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinChemistII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinChemistI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinChemistIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinChemistII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinChemistIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinChemistIII.get(10L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinCookI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinCook.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinCookII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinCookI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinCookIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinCookII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinCookIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinCookIII.get(10L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinDarkWizardI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinDarkWizard.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinDarkWizardII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinDarkWizardI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinDarkWizardIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinDarkWizardII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinDarkWizardIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinDarkWizardIII.get(10L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFarmerI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinFarmer.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFarmerII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinFarmerI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFarmerIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinFarmerII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFarmerIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinFarmerIII.get(10L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinForestryI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinForestry.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinForestryII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinForestryI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinForestryIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinForestryII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinForestryIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinForestryIII.get(10L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSpaceI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinSpace.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSpaceII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinSpaceI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSpaceIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinSpaceII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSpaceIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinSpaceIII.get(10L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSurvivorI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinSurvivor.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSurvivorII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinSurvivorI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSurvivorIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinSurvivorII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSurvivorIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinSurvivorIII.get(10L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinTechnicianI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinTechnician.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinTechnicianII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinTechnicianI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinTechnicianIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinTechnicianII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinTechnicianIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinTechnicianIII.get(10L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSmithI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinSmith.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSmithII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinSmithI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSmithIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinSmithII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinSmithIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinSmithIII.get(10L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinWitchI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinWitch.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinWitchII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinWitchI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinWitchIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinWitchII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinWitchIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinWitchIII.get(10L),
                100,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFlowerI.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinFlower.get(10L),
                100,
                30);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFlowerII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinFlowerI.get(10L),
                100,
                120);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFlowerIII.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinFlowerII.get(10L),
                100,
                480);
        GT_Values.RA.addAssemblerRecipe(
                CustomItemList.CoinFlowerIV.get(1L),
                GT_Utility.getIntegratedCircuit(1),
                CustomItemList.CoinFlowerIII.get(10L),
                100,
                1920);
    }

    private void makePistonRecipes() {
        // Vanilla Piston Assembler recipe
        List<ItemStack> fenceWood = OreDictionary.getOres("fenceWood");
        for (ItemStack stack : fenceWood) {
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                            ItemList.Plank_Oak.get(6L), GT_Utility.getIntegratedCircuit(1) },
                    Materials.Redstone.getMolten(72L),
                    new ItemStack(Blocks.piston, 1, 0),
                    200,
                    30);
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                            ItemList.Plank_Spruce.get(6L), GT_Utility.getIntegratedCircuit(1) },
                    Materials.Redstone.getMolten(72L),
                    new ItemStack(Blocks.piston, 1, 0),
                    200,
                    30);
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                            ItemList.Plank_Birch.get(6L), GT_Utility.getIntegratedCircuit(1) },
                    Materials.Redstone.getMolten(72L),
                    new ItemStack(Blocks.piston, 1, 0),
                    200,
                    30);
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                            ItemList.Plank_Jungle.get(6L), GT_Utility.getIntegratedCircuit(1) },
                    Materials.Redstone.getMolten(72L),
                    new ItemStack(Blocks.piston, 1, 0),
                    200,
                    30);
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                            ItemList.Plank_Acacia.get(6L), GT_Utility.getIntegratedCircuit(1) },
                    Materials.Redstone.getMolten(72L),
                    new ItemStack(Blocks.piston, 1, 0),
                    200,
                    30);
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                            ItemList.Plank_DarkOak.get(6L), GT_Utility.getIntegratedCircuit(1) },
                    Materials.Redstone.getMolten(72L),
                    new ItemStack(Blocks.piston, 1, 0),
                    200,
                    30);
            if (Forestry.isModLoaded()) {
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Larch.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Teak.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Acacia_Green.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Lime.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Chestnut.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Wenge.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Baobab.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Sequoia.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Kapok.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Ebony.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Mahagony.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Balsa.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Willow.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Walnut.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Greenheart.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Cherry.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Mahoe.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Poplar.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Palm.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Papaya.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Pine.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Plum.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Maple.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { new ItemStack(Blocks.cobblestone, 1, 0),
                                GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Iron, 1L), stack,
                                ItemList.Plank_Citrus.get(6L), GT_Utility.getIntegratedCircuit(1) },
                        Materials.Redstone.getMolten(72L),
                        new ItemStack(Blocks.piston, 1, 0),
                        200,
                        30);
            }
        }
    }

    private void makeCoilRecipes() {
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Cupronickel, 8L),
                        CustomItemList.AluminoSilicateWool.get(12L), GT_Utility.getIntegratedCircuit(3) },
                Materials.Tin.getMolten(288L),
                ItemList.Casing_Coil_Cupronickel.get(1L),
                100,
                7);

        // TODO: Rework other stuff (that depends on certain Heat Levels, mostly LuV+ stuff) so this can be wrapped into
        // a oneliner.
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Cupronickel, 8L),
                        CustomItemList.MicaInsulatorFoil.get(8L), GT_Utility.getIntegratedCircuit(3) },
                Materials.Tin.getMolten(144L),
                ItemList.Casing_Coil_Cupronickel.get(1L),
                200,
                30);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Kanthal, 8L),
                        CustomItemList.MicaInsulatorFoil.get(12L), GT_Utility.getIntegratedCircuit(3) },
                Materials.Cupronickel.getMolten(144L),
                ItemList.Casing_Coil_Kanthal.get(1L),
                300,
                120);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Nichrome, 8L),
                        CustomItemList.MicaInsulatorFoil.get(16L), GT_Utility.getIntegratedCircuit(3) },
                Materials.Kanthal.getMolten(144L),
                ItemList.Casing_Coil_Nichrome.get(1L),
                400,
                480);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.TPV, 8L),
                        CustomItemList.MicaInsulatorFoil.get(20L), GT_Utility.getIntegratedCircuit(3) },
                Materials.Nichrome.getMolten(144L),
                ItemList.Casing_Coil_TungstenSteel.get(1L),
                500,
                1920);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.HSSG, 8L),
                        CustomItemList.MicaInsulatorFoil.get(24L), GT_Utility.getIntegratedCircuit(3) },
                Materials.TPV.getMolten(144L),
                ItemList.Casing_Coil_HSSG.get(1L),
                600,
                7680);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.HSSS, 8L),
                        CustomItemList.MicaInsulatorFoil.get(28L), GT_Utility.getIntegratedCircuit(3) },
                Materials.HSSG.getMolten(144L),
                ItemList.Casing_Coil_HSSS.get(1L),
                700,
                7680);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Naquadah, 8L),
                        CustomItemList.MicaInsulatorFoil.get(32L), GT_Utility.getIntegratedCircuit(3) },
                Materials.HSSS.getMolten(144L),
                ItemList.Casing_Coil_Naquadah.get(1L),
                800,
                30720);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.NaquadahAlloy, 8L),
                        CustomItemList.MicaInsulatorFoil.get(36L), GT_Utility.getIntegratedCircuit(3) },
                Materials.Naquadah.getMolten(144L),
                ItemList.Casing_Coil_NaquadahAlloy.get(1L),
                900,
                30720);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Trinium, 8L),
                        CustomItemList.MicaInsulatorFoil.get(40L), GT_Utility.getIntegratedCircuit(3) },
                Materials.NaquadahAlloy.getMolten(144L),
                ItemList.Casing_Coil_Trinium.get(1L),
                1000,
                122880);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.ElectrumFlux, 8L),
                        CustomItemList.MicaInsulatorFoil.get(44L), GT_Utility.getIntegratedCircuit(3) },
                Materials.Trinium.getMolten(144L),
                ItemList.Casing_Coil_ElectrumFlux.get(1L),
                1100,
                500000);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.DraconiumAwakened, 8L),
                        CustomItemList.MicaInsulatorFoil.get(48L), GT_Utility.getIntegratedCircuit(3) },
                Materials.ElectrumFlux.getMolten(144L),
                ItemList.Casing_Coil_AwakenedDraconium.get(1L),
                1200,
                2000000);
    }

    private void makeNEIPlanetRecipes() {
        if (!NEIOrePlugin.isModLoaded() || !Botania.isModLoaded()) {
            return;
        }

        // NEI Ore Plugin planets
        // T0 Planets
        // Overworld
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                        getModItem(Minecraft.ID, "stone", 64, 0),
                        getModItem(Minecraft.ID, "grass", 64, 0),
                        getModItem(Minecraft.ID, "sapling", 1, 0),
                        GT_Utility.getIntegratedCircuit(17))
                .fluidInputs(Materials.Water.getFluid(10000))
                .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Ow", 1, 0)).noFluidOutputs()
                .duration(15 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        // Twilight Forest
        if (TwilightForest.isModLoaded()) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(Minecraft.ID, "stone", 64, 0),
                            getModItem(Minecraft.ID, "grass", 64, 0),
                            getModItem(TwilightForest.ID, "tile.TFSapling", 1, 1),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Water.getFluid(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_TF", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        }
        // Nether
        if (BiomesOPlenty.isModLoaded()) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(Minecraft.ID, "netherrack", 64, 0),
                            getModItem(Minecraft.ID, "soul_sand", 64, 0),
                            getModItem(BiomesOPlenty.ID, "ash", 64, 0),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Lava.getFluid(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Ne", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        }
        // End
        if (Natura.isModLoaded()) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(Minecraft.ID, "end_stone", 64, 0),
                            getModItem(Minecraft.ID, "obsidian", 64, 0),
                            getModItem(Natura.ID, "Cloud", 64, 1),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(FluidRegistry.getFluidStack("ender", 10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_ED", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        }
        // Far End Asteroids
        if (HardcoreEnderExpansion.isModLoaded()) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(HardcoreEnderExpansion.ID, "end_stone_terrain", 64, 0),
                            getModItem(HardcoreEnderExpansion.ID, "end_stone_terrain", 64, 1),
                            getModItem(HardcoreEnderExpansion.ID, "end_stone_terrain", 64, 2),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(FluidRegistry.getFluidStack("endergoo", 10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_EA", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_LV).addTo(sAssemblerRecipes);
        }

        if (GalaxySpace.isModLoaded()) {
            // T1 Planets
            // The Moon
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalacticraftCore.ID, "tile.moonBlock", 64, 3),
                            getModItem(GalacticraftCore.ID, "tile.moonBlock", 64, 4),
                            getModItem(GalacticraftCore.ID, "tile.moonBlock", 64, 14),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.SaltWater.getFluid(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Mo", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_HV).addTo(sAssemblerRecipes);
            // T2 Planets
            // Deimos
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "deimosblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "deimosblocks", 64, 1),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.Uranium, 64),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.SulfuricAcid.getFluid(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_De", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
            // Phobos
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "phobosblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "phobosblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "phobosblocks", 64, 2),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.SulfuricAcid.getFluid(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Ph", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
            // Mars
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalacticraftMars.ID, "tile.mars", 64, 5),
                            getModItem(GalacticraftMars.ID, "tile.mars", 64, 6),
                            getModItem(GalacticraftMars.ID, "tile.mars", 64, 7),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Chlorobenzene.getFluid(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Ma", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_EV).addTo(sAssemblerRecipes);
            // T3 Planets
            // Ceres
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "ceresblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "ceresblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "ceresblocks", 64, 2),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Oxygen.getGas(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Ce", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);
            // Callisto
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "callistoblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "callistoblocks", 64, 1),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.CallistoIce, 64L),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.LiquidAir.getFluid(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Ca", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);
            // Asteroids
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalacticraftMars.ID, "tile.asteroidsBlock", 64, 0),
                            getModItem(GalacticraftMars.ID, "tile.asteroidsBlock", 64, 1),
                            getModItem(GalacticraftMars.ID, "tile.denseIce", 64, 0),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(FluidRegistry.getFluidStack("ice", 10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_As", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);
            // Ganymede
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "ganymedeblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "ganymedeblocks", 64, 1),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.Platinum, 64L),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Oxygen.getGas(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Ga", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);
            // Europa
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "europagrunt", 64, 0),
                            getModItem(GalaxySpace.ID, "europagrunt", 64, 1),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.Manganese, 64L),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Water.getFluid(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Eu", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);
            // Ross 128b
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(Minecraft.ID, "dirt", 64, 2),
                            getModItem(Minecraft.ID, "stone", 64, 0),
                            ItemList.Generator_Naquadah_Mark_I.get(1),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Lava.getFluid(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Rb", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_IV).addTo(sAssemblerRecipes);
            // T4 Planets
            // Io
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "ioblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "ioblocks", 64, 2),
                            getModItem(GalaxySpace.ID, "ioblocks", 64, 3),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Lead.getMolten(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Io", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_LuV).addTo(sAssemblerRecipes);
            // Mercury
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "mercuryblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "mercuryblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "mercuryblocks", 64, 2),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Iron.getMolten(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Me", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_LuV).addTo(sAssemblerRecipes);
            // Venus
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "venusblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "venusblocks", 64, 1),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.Quantium, 64),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.CarbonDioxide.getGas(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Ve", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_LuV).addTo(sAssemblerRecipes);
            // T5 Planets
            // Miranda
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "mirandablocks", 64, 0),
                            getModItem(GalaxySpace.ID, "mirandablocks", 64, 1),
                            getModItem(GalaxySpace.ID, "mirandablocks", 64, 2),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.HydricSulfide.getGas(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Mi", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_ZPM).addTo(sAssemblerRecipes);
            // Oberon
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "oberonblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "oberonblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "oberonblocks", 64, 2),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.CarbonMonoxide.getGas(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Ob", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_ZPM).addTo(sAssemblerRecipes);
            // Enceladus
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "enceladusblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "enceladusblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "enceladusblocks", 64, 2),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Oxygen.getGas(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_En", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_ZPM).addTo(sAssemblerRecipes);
            // Titan
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "titanblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "titanblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "titanblocks", 64, 2),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Methane.getGas(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Ti", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_ZPM).addTo(sAssemblerRecipes);
            // Ross 128ba
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalacticraftCore.ID, "tile.moonBlock", 64, 3),
                            getModItem(GalacticraftCore.ID, "tile.moonBlock", 64, 4),
                            getModItem(GalacticraftCore.ID, "tile.moonBlock", 64, 5),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Helium_3.getGas(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Ra", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_ZPM).addTo(sAssemblerRecipes);
            // T6 Planets
            // Triton
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "tritonblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "tritonblocks", 64, 1),
                            getModItem(GalaxySpace.ID, "tritonblocks", 64, 2),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Ethylene.getGas(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Tr", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_UV).addTo(sAssemblerRecipes);
            // Proteus
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "proteusblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "proteusblocks", 64, 2),
                            getModItem(GalaxySpace.ID, "proteusblocks", 64, 3),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Deuterium.getGas(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Pr", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_UV).addTo(sAssemblerRecipes);
            // T7 Planets
            // Haumea
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "haumeablocks", 64, 0),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.NetherStar, 64),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.Naquadah, 64),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.InfusedGold.getMolten(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Ha", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_UHV).addTo(sAssemblerRecipes);
            // Pluto
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "plutoblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "plutoblocks", 64, 4),
                            getModItem(GalaxySpace.ID, "plutoblocks", 64, 6),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Fluorine.getGas(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_Pl", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_UHV).addTo(sAssemblerRecipes);
            // Makemake
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "makemakegrunt", 64, 0),
                            getModItem(GalaxySpace.ID, "makemakegrunt", 64, 1),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.Chrysotile, 64),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.HydrofluoricAcid.getFluid(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_MM", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_UHV).addTo(sAssemblerRecipes);
            // Kuiper Belt
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalacticraftMars.ID, "tile.asteroidsBlock", 64, 0),
                            getModItem(GalacticraftMars.ID, "tile.denseIce", 64, 0),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.Neutronium, 64),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.HydrofluoricAcid.getFluid(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_KB", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_UHV).addTo(sAssemblerRecipes);
            // T8 Planets
            // Vega B
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "vegabgrunt", 64, 0),
                            getModItem(GalaxySpace.ID, "vegabsubgrunt", 64, 0),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.InfinityCatalyst, 64),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Neutronium.getMolten(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_VB", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_UEV).addTo(sAssemblerRecipes);
            // Barnard C
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(Minecraft.ID, "stone", 64, 0),
                            getModItem(GalaxySpace.ID, "barnardaCgrass", 64, 0),
                            getModItem(GalaxySpace.ID, "barnardaCsapling", 1, 0),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(FluidRegistry.getFluidStack("unknowwater", 10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_BC", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_UEV).addTo(sAssemblerRecipes);
            // Barnard E
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "barnardaEgrunt", 64, 0),
                            getModItem(GalaxySpace.ID, "barnardaEsubgrunt", 64, 0),
                            GT_OreDictUnificator.get(OrePrefixes.block, Materials.Unstable, 64),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.LiquidAir.getFluid(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_BE", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_UEV).addTo(sAssemblerRecipes);
            // Barnard F
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "barnardaFgrunt", 64, 0),
                            getModItem(GalaxySpace.ID, "barnardaFsubgrunt", 64, 0),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.Bedrockium, 64),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Tin.getMolten(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_BF", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_UEV).addTo(sAssemblerRecipes);
            // T Ceti E
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "tcetieblocks", 64, 0),
                            getModItem(GalaxySpace.ID, "tcetieblocks", 64, 2),
                            getModItem(GalaxySpace.ID, "tcetiedandelions", 64, 4),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(GT_ModHandler.getDistilledWater(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_TE", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_UEV).addTo(sAssemblerRecipes);
            // Alpha Centauri Bb
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(GalaxySpace.ID, "acentauribbgrunt", 64, 0),
                            getModItem(GalaxySpace.ID, "acentauribbsubgrunt", 64, 0),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.Samarium, 64),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(Materials.Copper.getMolten(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_CB", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_UEV).addTo(sAssemblerRecipes);
            // Technically T9
            // Deep Dark
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(Botania.ID, "tinyPlanetBlock", 1, 0),
                            getModItem(Minecraft.ID, "acentauribbgrunt", 64, 0),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.Dilithium, 64),
                            GT_OreDictUnificator.get(OrePrefixes.ore, Materials.Pumice, 64),
                            GT_Utility.getIntegratedCircuit(17))
                    .fluidInputs(MaterialsUEVplus.SpaceTime.getMolten(10000))
                    .itemOutputs(getModItem(NEIOrePlugin.ID, "blockDimensionDisplay_DD", 1, 0)).noFluidOutputs()
                    .duration(15 * SECONDS).eut(TierEU.RECIPE_UMV).addTo(sAssemblerRecipes);
        }
    }

    private void makeSolderingAlloyRecipes() {
        for (Materials tMat : solderingMaterials) { // TODO dream things using soldering go in here!

            int tMultiplier = tMat.contains(SubTag.SOLDERING_MATERIAL_GOOD) ? 1
                    : tMat.contains(SubTag.SOLDERING_MATERIAL_BAD) ? 4 : 2;

            if (StevesCarts2.isModLoaded()) {
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Coated_Basic.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1),
                                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 8),
                                GT_Utility.getIntegratedCircuit(2) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(StevesCarts2.ID, "ModuleComponents", 1L, 9),
                        200,
                        30);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Phenolic_Good.get(1L),
                                GT_ModHandler.getModItem(StevesCarts2.ID, "ModuleComponents", 2L, 9),
                                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 16),
                                GT_Utility.getIntegratedCircuit(2) },
                        tMat.getMolten(288L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(StevesCarts2.ID, "ModuleComponents", 1L, 16),
                        200,
                        120);
            }

            // --- Advanced Solar Panel
            if (AdvancedSolarPanel.isModLoaded()) {
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Hull_LV.get(1L), ItemList.Cover_SolarPanel_LV.get(1L),
                                ItemList.Robot_Arm_LV.get(1L), ItemList.Battery_RE_LV_Lithium.get(1L),
                                GT_Utility.getIntegratedCircuit(2) },
                        tMat.getMolten(288L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(AdvancedSolarPanel.ID, "BlockAdvSolarPanel", 1L, 0),
                        800,
                        120);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Hull_MV.get(1L), ItemList.Cover_SolarPanel_MV.get(1L),
                                ItemList.Robot_Arm_MV.get(1L), ItemList.Battery_RE_MV_Lithium.get(1L),
                                GT_Utility.getIntegratedCircuit(2) },
                        tMat.getMolten(576L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(AdvancedSolarPanel.ID, "BlockAdvSolarPanel", 1L, 1),
                        1000,
                        480);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Hull_HV.get(1L), ItemList.Cover_SolarPanel_HV.get(1L),
                                ItemList.Robot_Arm_HV.get(1L), ItemList.Battery_RE_HV_Lithium.get(1L),
                                GT_Utility.getIntegratedCircuit(2) },
                        tMat.getMolten(864L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(AdvancedSolarPanel.ID, "BlockAdvSolarPanel", 1L, 2),
                        1200,
                        1920);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Hull_EV.get(1L), ItemList.Cover_SolarPanel_EV.get(1L),
                                ItemList.Robot_Arm_EV.get(1L), CustomItemList.BatteryHull_EV_Full.get(1L),
                                GT_Utility.getIntegratedCircuit(2) },
                        tMat.getMolten(1152L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(AdvancedSolarPanel.ID, "BlockAdvSolarPanel", 1L, 3),
                        1400,
                        7860);
            }

            // --- Super Solar Panel
            if (SuperSolarPanels.isModLoaded()) {
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Hull_IV.get(1L), ItemList.Cover_SolarPanel_IV.get(1L),
                                ItemList.Robot_Arm_IV.get(1L), CustomItemList.BatteryHull_IV_Full.get(1L),
                                GT_Utility.getIntegratedCircuit(2) },
                        tMat.getMolten(1440 * tMultiplier / 2L),
                        GT_ModHandler.getModItem(SuperSolarPanels.ID, "SpectralSolarPanel", 1L, 0),
                        1600,
                        30720);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Hull_LuV.get(1L), ItemList.Cover_SolarPanel_LuV.get(1L),
                                ItemList.Robot_Arm_LuV.get(1L), CustomItemList.BatteryHull_LuV_Full.get(1L),
                                GT_Utility.getIntegratedCircuit(2) },
                        tMat.getMolten(1728 * tMultiplier / 2L),
                        GT_ModHandler.getModItem(SuperSolarPanels.ID, "SingularSolarPanel", 1L, 0),
                        1800,
                        122880);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Hull_ZPM.get(1L), ItemList.Cover_SolarPanel_ZPM.get(1L),
                                ItemList.Robot_Arm_ZPM.get(1L), CustomItemList.BatteryHull_ZPM_Full.get(1L),
                                GT_Utility.getIntegratedCircuit(2) },
                        tMat.getMolten(2016 * tMultiplier / 2L),
                        GT_ModHandler.getModItem(SuperSolarPanels.ID, "AdminSolarPanel", 1L, 0),
                        2000,
                        500000);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Hull_UV.get(1L), ItemList.Cover_SolarPanel_UV.get(1L),
                                ItemList.Robot_Arm_UV.get(1L), CustomItemList.BatteryHull_UV_Full.get(1L),
                                GT_Utility.getIntegratedCircuit(2) },
                        tMat.getMolten(2304 * tMultiplier / 2L),
                        GT_ModHandler.getModItem(SuperSolarPanels.ID, "PhotonicSolarPanel", 1L, 0),
                        2200,
                        2000000);
            }

            // solar 1EU
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] { ItemList.Circuit_Silicon_Wafer.get(2),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2L),
                            GT_ModHandler.getIC2Item("reinforcedGlass", 1L),
                            GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.RedAlloy, 2L),
                            CustomItemList.AluminiumIronPlate.get(1) },
                    tMat.getMolten(72L * tMultiplier / 2L),
                    ItemList.Cover_SolarPanel.get(1L),
                    200,
                    120);

            // solar 8EU
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2L),
                            ItemList.Cover_SolarPanel.get(2L),
                            GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Tin, 2L),
                            ItemList.Circuit_Silicon_Wafer.get(1),
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.GalliumArsenide, 1L),
                            CustomItemList.ReinforcedAluminiumIronPlate.get(1) },
                    tMat.getMolten(72L * tMultiplier / 2L),
                    ItemList.Cover_SolarPanel_8V.get(1L),
                    400,
                    120);

            if (OpenComputers.isModLoaded()) {

                // display t1
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Casing_MV.get(1L), ItemList.Cover_Screen.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2L),
                                ItemList.Circuit_Parts_Transistor.get(2L), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "screen1", 1L, 0),
                        240,
                        64);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Casing_MV.get(1L), ItemList.Cover_Screen.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2L),
                                ItemList.Circuit_Parts_TransistorSMD.get(1L), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "screen1", 1L, 0),
                        240,
                        64);
                // display t2
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Casing_HV.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "screen1", 1L, 0),
                                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2L),
                                ItemList.Circuit_Parts_Transistor.get(4L), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0),
                        240,
                        120);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Casing_HV.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "screen1", 1L, 0),
                                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2L),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0),
                        240,
                        120);
                // display t3
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Casing_EV.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0),
                                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2L),
                                ItemList.Circuit_Parts_Transistor.get(8L), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(288L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "screen3", 1L, 0),
                        240,
                        256);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Casing_EV.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0),
                                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2L),
                                ItemList.Circuit_Parts_TransistorSMD.get(4L), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(288L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "screen3", 1L, 0),
                        240,
                        256);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Casing_EV.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "screen2", 1L, 0),
                                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2L),
                                ItemList.Circuit_Parts_TransistorASMD.get(1L), GT_Utility.getIntegratedCircuit(2) },
                        tMat.getMolten(288L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "screen3", 1L, 0),
                        120,
                        256);

                // angel upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GT_ModHandler.getModItem(ExtraUtilities.ID, "angelBlock", 1L, 0),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 49),
                        250,
                        256);
                // hover upgrade Tier 1
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 4L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                ItemList.Electric_Motor_MV.get(1L), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 99),
                        250,
                        256);
                // hover upgrade Tier 2
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 4L),
                                GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 8L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                ItemList.Electric_Motor_HV.get(1L), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 100),
                        250,
                        480);
                // battery upgrade 1
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "capacitor", 1L, 0),
                                ItemList.Circuit_Parts_Transistor.get(2L),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 4L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 63),
                        250,
                        120);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "capacitor", 1L, 0),
                                ItemList.Circuit_Parts_TransistorSMD.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 4L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 63),
                        250,
                        120);
                // battery upgrade 2
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "capacitor", 2L, 0),
                                ItemList.Circuit_Parts_Transistor.get(4L),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 8L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 64),
                        250,
                        256);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "capacitor", 2L, 0),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 8L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 64),
                        250,
                        256);
                // battery upgrade 3
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 2L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "capacitor", 4L, 0),
                                ItemList.Circuit_Parts_Transistor.get(8L),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 16L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 65),
                        250,
                        480);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 2L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "capacitor", 4L, 0),
                                ItemList.Circuit_Parts_TransistorSMD.get(4L),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 16L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 65),
                        250,
                        480);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 2L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "capacitor", 4L, 0),
                                ItemList.Circuit_Parts_TransistorASMD.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 16L),
                                GT_Utility.getIntegratedCircuit(2) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 65),
                        125,
                        480);
                // inventory upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 8L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 53),
                        300,
                        120);
                // inventory controller upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 53),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 8L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 61),
                        300,
                        120);
                // tank upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GT_ModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1L, 0),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 8L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 76),
                        300,
                        120);
                // tank controller upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 76),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 8L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 77),
                        300,
                        120);

                // beekeper upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                                GT_ModHandler.getModItem(Forestry.ID, "beealyzer", 1L, 0), ItemList.Sensor_MV.get(2L),
                                GT_OreDictUnificator.get(OrePrefixes.lens, Materials.EnderEye, 2),
                                GT_Utility.getIntegratedCircuit(10) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item.forestry", 1L, 0),
                        200,
                        120);
                // configurator upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "wrench", 1L, 0),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 115),
                        300,
                        120);
                // riteg upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getIC2Item("RTGPellets", 1),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 3L, 26),
                                GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Lead, 4L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 116),
                        300,
                        256);
                // card container 1
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 57),
                        250,
                        120);
                // card container 2
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 58),
                        250,
                        256);
                // card container 3
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 59),
                        250,
                        480);
                // upgrade container 1
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.StainlessSteel, 8L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 54),
                        250,
                        120);
                // upgrade container 2
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Titanium, 8L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 55),
                        250,
                        256);
                // upgrade container 3
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 2L),
                                new ItemStack(Blocks.chest, 1),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                                GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.TungstenSteel, 8L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 56),
                        250,
                        480);

                // database upgrade 1
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 0),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 5),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 78),
                        250,
                        120);
                // database upgrade 2
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 0),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 6),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 79),
                        250,
                        256);
                // database upgrade 3
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 0),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 7),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 80),
                        250,
                        480);
                // experience upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 0),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Emerald, 2L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 52),
                        250,
                        120);
                // crafting component
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Hull_LV.get(1L), ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                ItemList.Cover_Crafting.get(1L), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 14),
                        250,
                        120);
                // generator upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(EnderIO.ID, "blockStirlingGenerator", 1L, 0),
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 1L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 15),
                        250,
                        120);
                // leash upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                                new ItemStack(Items.lead, 4), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 85),
                        250,
                        120);
                // mfu upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "adapter", 1L, 0),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 51),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 4L, 96),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 2L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 112),
                        250,
                        480);
                // navigation upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Hull_MV.get(1L), ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                new ItemStack(Items.compass, 1), new ItemStack(Items.map, 1),
                                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Water, 1L) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 36),
                        250,
                        256);
                // piston upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                ItemList.Electric_Piston_MV.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 1L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 75),
                        250,
                        120);
                // sing IO upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                ItemList.Dye_SquidInk.get(1L), new ItemStack(Items.sign, 1),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 35),
                        250,
                        120);
                // solar upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                ItemList.Cover_SolarPanel_LV.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 34),
                        250,
                        256);
                // tractor beam upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "capacitor", 1L, 0),
                                ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                                ItemList.Electric_Piston_HV.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 4L),
                                GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 16L) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 67),
                        250,
                        480);
                // trading upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 3L, 25),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 3L),
                                ItemList.Electric_Piston_MV.get(1L),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Emerald, 2L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 110),
                        250,
                        256);
                // hover Boots
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "capacitor", 1L, 0),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 83),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 100),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 4L),
                                ItemList.Electric_Piston_MV.get(2L), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(OpenComputers.ID, "hoverBoots", 1L, W),
                        350,
                        256);
                // ME Upgrade 1
                GT_Values.RA
                        .addAssemblerRecipe(
                                new ItemStack[] { ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                                        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Chrome, 2L),
                                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                                        GT_ModHandler
                                                .getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 41),
                                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 24),
                                        GT_Utility.getIntegratedCircuit(1) },
                                tMat.getMolten(144L * tMultiplier / 2L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item.ae", 1L),
                                250,
                                256);
                // ME Upgrade 2
                GT_Values.RA
                        .addAssemblerRecipe(
                                new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Titanium, 2L),
                                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                                        GT_ModHandler
                                                .getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 41),
                                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                                        GT_Utility.getIntegratedCircuit(1) },
                                tMat.getMolten(144L * tMultiplier / 2L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item.ae", 1L, 1),
                                250,
                                1024);
                // ME Upgrade 3
                GT_Values.RA
                        .addAssemblerRecipe(
                                new ItemStack[] { ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                        GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 2L),
                                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 13),
                                        GT_ModHandler
                                                .getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 41),
                                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 26),
                                        GT_Utility.getIntegratedCircuit(1) },
                                tMat.getMolten(144L * tMultiplier / 2L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item.ae", 1L, 2),
                                250,
                                4096);
            }

            if (Computronics.isModLoaded()) {
                // Camera Upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(Computronics.ID, "computronics.camera", 1L, 0),
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 0),
                        250,
                        256);
                // Chat Upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(Computronics.ID, "computronics.chatBox", 1L, 0),
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 1),
                        250,
                        256);
                // Radar Upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(Computronics.ID, "computronics.radar", 1L, 0),
                                ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                ItemList.Circuit_Parts_TransistorSMD.get(4L),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 4L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 48),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 2),
                        250,
                        480);
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(Computronics.ID, "computronics.radar", 1L, 0),
                                ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                ItemList.Circuit_Parts_TransistorASMD.get(2L),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 4L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 48),
                                GT_Utility.getIntegratedCircuit(2) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 2),
                        250,
                        480);
                // Particel Card
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 25),
                                new ItemStack(Items.firework_charge, 1, W), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 3),
                        250,
                        480);
                // Spoofing Card
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 11),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 50),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Clay, 2L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 4),
                        250,
                        480);
                // Beep Card
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 33),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                                GT_ModHandler.getModItem(Computronics.ID, "computronics.speaker", 1L, 0),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 5),
                        250,
                        480);
                // Self Destruction Card
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 66),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 28),
                                GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockITNT", 2L, 0),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 6),
                        250,
                        256);
                // Colorful Upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L),
                                GT_ModHandler.getModItem(Computronics.ID, "computronics.colorfulLamp", 1L, 0),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 4L, 96),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 7),
                        250,
                        480);
                // Noise Card
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 5),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 1),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 27),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 2L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 8),
                        250,
                        480);
                // Sound Card
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 8),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 38),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 1L, 29),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 9),
                        250,
                        1024);
                // Light Board
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_ModHandler.getModItem(Computronics.ID, "computronics.colorfulLamp", 1L, 0),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 2L),
                                ItemList.Dye_SquidInk.get(4L), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 10),
                        250,
                        256);
                // Server Selfdestructor
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 2L, 6),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                ItemList.Dye_SquidInk.get(4L),
                                GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockITNT", 2L, 0),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 11),
                        250,
                        256);
                // Rack Capacitor
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "capacitor", 1L, 0),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 2L),
                                ItemList.Dye_SquidInk.get(4L), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 12),
                        250,
                        256);
                // Switch Board
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 24),
                                new ItemStack(Blocks.stone_button, 64, 30720),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Glass, 2L),
                                ItemList.Dye_SquidInk.get(4L), GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 13),
                        250,
                        256);
                // Speech Upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(Computronics.ID, "computronics.speechBox", 1L, 0),
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.ocParts", 1L, 14),
                        250,
                        256);
                // Drone Docking Station
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                GT_ModHandler.getModItem(OpenComputers.ID, "cable", 2L, 0),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 25),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L),
                                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderPearl, 2L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.droneStation", 1L, 0),
                        250,
                        480);
                // Drone Docking Station Upgrade
                GT_Values.RA.addAssemblerRecipe(
                        new ItemStack[] { GT_ModHandler.getModItem(Computronics.ID, "computronics.droneStation", 1L, 0),
                                GT_ModHandler.getModItem(OpenComputers.ID, "item", 2L, 26),
                                ItemList.Circuit_Parts_TransistorSMD.get(2L),
                                GT_OreDictUnificator.get(OrePrefixes.itemCasing, Materials.Aluminium, 2L),
                                GT_Utility.getIntegratedCircuit(1) },
                        tMat.getMolten(144L * tMultiplier / 2L),
                        GT_ModHandler.getModItem(Computronics.ID, "computronics.dockingUpgrade", 1L, 0),
                        250,
                        480);
            }
        }
    }
}
