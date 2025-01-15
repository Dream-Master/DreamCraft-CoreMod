package com.dreammaster.gthandler.recipes;

import static gregtech.api.enums.Mods.*;
import static gregtech.api.recipe.RecipeMaps.plasmaForgeRecipes;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.COIL_HEAT;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.dreammaster.block.BlockList;
import com.dreammaster.gthandler.CustomItemList;
import com.dreammaster.gthandler.DTPFCalculator;
import com.dreammaster.item.NHItemList;

import goodgenerator.items.GGMaterial;
import goodgenerator.util.ItemRefer;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.common.items.CombType;
import gregtech.loaders.misc.GTBees;
import gtPlusPlus.core.material.MaterialsAlloy;
import gtPlusPlus.core.material.MaterialsElements;
import gtPlusPlus.core.material.Particle;
import gtPlusPlus.core.recipe.common.CI;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;

public class DTPFRecipes implements Runnable {

    @Override
    public void run() {
        // Dimensionally transcendent plasma forge recipes.
        // Ordered so that recipes using higher tier catalysts are prioritized.

        {
            // Coils
            int awakened_heat = 10800;
            int infinity_heat = awakened_heat + 900;
            int hypogen_heat = infinity_heat + 900;
            int eternal_heat = hypogen_heat + 900;

            // ------------------------------------------------------------------------------------------
            // ------------------------------------- Plasma Forge ---------------------------------------
            // ------------------------------------------------------------------------------------------

            {
                // Neutronium

                long base_quantity = 512L;
                long tier_up_multiplier = 2L;

                long tier_1_quantity = 144L * base_quantity;
                long tier_2_quantity = tier_1_quantity * tier_up_multiplier;
                long tier_3_quantity = tier_2_quantity * tier_up_multiplier;
                long tier_4_quantity = tier_3_quantity * tier_up_multiplier;

                DTPFCalculator neutronium = new DTPFCalculator().setBaseParallel(base_quantity)
                        .setCustomEBFinputItem(CustomItemList.RawNeutronium.get(1L))
                        .calculateGenericEBFBasedRecipe(Materials.Neutronium);

                // Tier 4
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTEC.getFluid(neutronium.getCatalystAmount(3)),
                                Materials.Iron.getMolten(tier_4_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(neutronium.getResidueAmount(3)),
                                Materials.Neutronium.getMolten(tier_4_quantity))
                        .duration(neutronium.getDuration(3)).eut(neutronium.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 3
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTRC.getFluid(neutronium.getCatalystAmount(2)),
                                Materials.Iron.getMolten(tier_3_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(neutronium.getResidueAmount(2)),
                                Materials.Neutronium.getMolten(tier_3_quantity))
                        .duration(neutronium.getDuration(2)).eut(neutronium.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 2
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTPC.getFluid(neutronium.getCatalystAmount(1)),
                                Materials.Iron.getMolten(tier_2_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(neutronium.getResidueAmount(1)),
                                Materials.Neutronium.getMolten(tier_2_quantity))
                        .duration(neutronium.getDuration(1)).eut(neutronium.getEUt(1))
                        .metadata(COIL_HEAT, infinity_heat).addTo(plasmaForgeRecipes);

                // Tier 1
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTCC.getFluid(neutronium.getCatalystAmount(0)),
                                Materials.Iron.getMolten(tier_1_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(neutronium.getResidueAmount(0)),
                                Materials.Neutronium.getMolten(tier_1_quantity))
                        .duration(neutronium.getDuration(0)).eut(neutronium.getEUt(0))
                        .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);
            }

            {
                // Cosmic Neutronium

                long base_quantity = 512L;
                long tier_up_multiplier = 2L;

                long tier_1_quantity = 144L * base_quantity;
                long tier_2_quantity = tier_1_quantity * tier_up_multiplier;
                long tier_3_quantity = tier_2_quantity * tier_up_multiplier;
                long tier_4_quantity = tier_3_quantity * tier_up_multiplier;

                DTPFCalculator cosmic_neutronium = new DTPFCalculator().setBaseParallel(base_quantity)
                        .calculateGenericEBFBasedRecipe(Materials.CosmicNeutronium);

                // Tier 4 - Normal
                GTValues.RA.stdBuilder().itemInputs(GTUtility.getIntegratedCircuit(4))
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTEC.getFluid(cosmic_neutronium.getCatalystAmount(3)),
                                Materials.Copper.getMolten(tier_4_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(cosmic_neutronium.getResidueAmount(3)),
                                Materials.CosmicNeutronium.getMolten(tier_4_quantity))
                        .duration(cosmic_neutronium.getDuration(3)).eut(cosmic_neutronium.getEUt(3))
                        .metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);

                // Tier 3 - Normal
                GTValues.RA.stdBuilder().itemInputs(GTUtility.getIntegratedCircuit(4))
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTRC.getFluid(cosmic_neutronium.getCatalystAmount(2)),
                                Materials.Copper.getMolten(tier_3_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(cosmic_neutronium.getResidueAmount(2)),
                                Materials.CosmicNeutronium.getMolten(tier_3_quantity))
                        .duration(cosmic_neutronium.getDuration(2)).eut(cosmic_neutronium.getEUt(2))
                        .metadata(COIL_HEAT, hypogen_heat).addTo(plasmaForgeRecipes);

                // Tier 2 - Normal
                GTValues.RA.stdBuilder().itemInputs(GTUtility.getIntegratedCircuit(4))
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTPC.getFluid(cosmic_neutronium.getCatalystAmount(1)),
                                Materials.Copper.getMolten(tier_2_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(cosmic_neutronium.getResidueAmount(1)),
                                Materials.CosmicNeutronium.getMolten(tier_2_quantity))
                        .duration(cosmic_neutronium.getDuration(1)).eut(cosmic_neutronium.getEUt(1))
                        .metadata(COIL_HEAT, infinity_heat).addTo(plasmaForgeRecipes);

                // Tier 1 - Normal
                GTValues.RA.stdBuilder().itemInputs(GTUtility.getIntegratedCircuit(4))
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTCC.getFluid(cosmic_neutronium.getCatalystAmount(0)),
                                Materials.Copper.getMolten(tier_1_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(cosmic_neutronium.getResidueAmount(0)),
                                Materials.CosmicNeutronium.getMolten(tier_1_quantity))
                        .duration(cosmic_neutronium.getDuration(0)).eut(cosmic_neutronium.getEUt(0))
                        .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);

                if (Forestry.isModLoaded()) {
                    long tier_1_bee_quantity = 144L * base_quantity;
                    long tier_2_bee_quantity = tier_1_bee_quantity * tier_up_multiplier;
                    long tier_3_bee_quantity = tier_2_bee_quantity * tier_up_multiplier;
                    long tier_4_bee_quantity = tier_3_bee_quantity * tier_up_multiplier;

                    DTPFCalculator cosmic_neutronium_bee = new DTPFCalculator().setBaseParallel(base_quantity)
                            .setCatalystDiscount(15).setProcessingTimeDiscount(50)
                            .calculateGenericEBFBasedRecipe(Materials.CosmicNeutronium);

                    // Tier 4 - Combs
                    GTValues.RA.stdBuilder()
                            .itemInputs(
                                    GTBees.combs.getStackForType(CombType.COSMICNEUTRONIUM),
                                    GTUtility.getIntegratedCircuit(1))
                            .fluidInputs(
                                    MaterialsUEVplus.ExcitedDTEC.getFluid(cosmic_neutronium_bee.getCatalystAmount(3)),
                                    Materials.Copper.getMolten(tier_4_bee_quantity))
                            .fluidOutputs(
                                    MaterialsUEVplus.DimensionallyTranscendentResidue
                                            .getFluid(cosmic_neutronium_bee.getResidueAmount(3)),
                                    Materials.CosmicNeutronium.getMolten(tier_4_bee_quantity))
                            .duration(cosmic_neutronium_bee.getDuration(3)).eut(cosmic_neutronium_bee.getEUt(3))
                            .metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);

                    // Tier 3 - Combs
                    GTValues.RA.stdBuilder()
                            .itemInputs(
                                    GTBees.combs.getStackForType(CombType.COSMICNEUTRONIUM),
                                    GTUtility.getIntegratedCircuit(1))
                            .fluidInputs(
                                    MaterialsUEVplus.ExcitedDTRC.getFluid(cosmic_neutronium_bee.getCatalystAmount(2)),
                                    Materials.Copper.getMolten(tier_3_bee_quantity))
                            .fluidOutputs(
                                    MaterialsUEVplus.DimensionallyTranscendentResidue
                                            .getFluid(cosmic_neutronium_bee.getResidueAmount(2)),
                                    Materials.CosmicNeutronium.getMolten(tier_3_bee_quantity))
                            .duration(cosmic_neutronium_bee.getDuration(2)).eut(cosmic_neutronium_bee.getEUt(2))
                            .metadata(COIL_HEAT, hypogen_heat).addTo(plasmaForgeRecipes);

                    // Tier 2 - Combs
                    GTValues.RA.stdBuilder()
                            .itemInputs(
                                    GTBees.combs.getStackForType(CombType.COSMICNEUTRONIUM),
                                    GTUtility.getIntegratedCircuit(1))
                            .fluidInputs(
                                    MaterialsUEVplus.ExcitedDTPC.getFluid(cosmic_neutronium_bee.getCatalystAmount(1)),
                                    Materials.Copper.getMolten(tier_2_bee_quantity))
                            .fluidOutputs(
                                    MaterialsUEVplus.DimensionallyTranscendentResidue
                                            .getFluid(cosmic_neutronium_bee.getResidueAmount(1)),
                                    Materials.CosmicNeutronium.getMolten(tier_2_bee_quantity))
                            .duration(cosmic_neutronium_bee.getDuration(1)).eut(cosmic_neutronium_bee.getEUt(1))
                            .metadata(COIL_HEAT, infinity_heat).addTo(plasmaForgeRecipes);

                    // Tier 1 - Combs
                    GTValues.RA.stdBuilder()
                            .itemInputs(
                                    GTBees.combs.getStackForType(CombType.COSMICNEUTRONIUM),
                                    GTUtility.getIntegratedCircuit(1))
                            .fluidInputs(
                                    MaterialsUEVplus.ExcitedDTCC.getFluid(cosmic_neutronium_bee.getCatalystAmount(0)),
                                    Materials.Copper.getMolten(tier_1_quantity))
                            .fluidOutputs(
                                    MaterialsUEVplus.DimensionallyTranscendentResidue
                                            .getFluid(cosmic_neutronium_bee.getResidueAmount(0)),
                                    Materials.CosmicNeutronium.getMolten(tier_1_quantity))
                            .duration(cosmic_neutronium_bee.getDuration(0)).eut(cosmic_neutronium_bee.getEUt(0))
                            .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);
                }

            }

            {
                // Bedrockium

                long base_quantity = 2048L;
                long tier_up_multiplier = 2L;

                long tier_1_quantity = 144L * base_quantity;
                long tier_2_quantity = tier_1_quantity * tier_up_multiplier;
                long tier_3_quantity = tier_2_quantity * tier_up_multiplier;
                long tier_4_quantity = tier_3_quantity * tier_up_multiplier;

                DTPFCalculator bedrockium = new DTPFCalculator().setBaseParallel(base_quantity)
                        .calculateGenericEBFBasedRecipe(Materials.Bedrockium);

                // Tier 4
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTEC.getFluid(bedrockium.getCatalystAmount(3)),
                                Materials.Steel.getMolten(tier_4_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(bedrockium.getResidueAmount(3)),
                                Materials.Bedrockium.getMolten(tier_4_quantity))
                        .duration(bedrockium.getDuration(3)).eut(bedrockium.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 3
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTRC.getFluid(bedrockium.getCatalystAmount(2)),
                                Materials.Steel.getMolten(tier_3_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(bedrockium.getResidueAmount(2)),
                                Materials.Bedrockium.getMolten(tier_3_quantity))
                        .duration(bedrockium.getDuration(2)).eut(bedrockium.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 2
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTPC.getFluid(bedrockium.getCatalystAmount(1)),
                                Materials.Steel.getMolten(tier_2_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(bedrockium.getResidueAmount(1)),
                                Materials.Bedrockium.getMolten(tier_2_quantity))
                        .duration(bedrockium.getDuration(1)).eut(bedrockium.getEUt(1))
                        .metadata(COIL_HEAT, infinity_heat).addTo(plasmaForgeRecipes);

                // Tier 1
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTCC.getFluid(bedrockium.getCatalystAmount(0)),
                                Materials.Steel.getMolten(tier_1_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(bedrockium.getResidueAmount(0)),
                                Materials.Bedrockium.getMolten(tier_1_quantity))
                        .duration(bedrockium.getDuration(0)).eut(bedrockium.getEUt(0))
                        .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);
            }

            {
                // Hypogen

                // V4
                GTValues.RA.stdBuilder().itemInputs(GregtechItemList.Compressed_Fusion_Reactor.get(0))
                        .fluidInputs(
                                Materials.Neutronium.getMolten(5760L * 2),
                                new FluidStack(MaterialsAlloy.QUANTUM.getFluid(), 5760),
                                Materials.Infinity.getMolten(1440L),
                                MaterialsUEVplus.ExcitedDTSC.getFluid(1000))
                        .fluidOutputs(
                                new FluidStack(MaterialsElements.STANDALONE.HYPOGEN.getFluid(), 5760 * 2),
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(2000L))
                        .duration(1 * MINUTES + 15 * SECONDS).eut(2_000_000_000).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // V3
                GTValues.RA.stdBuilder().itemInputs(GregtechItemList.Compressed_Fusion_Reactor.get(0))
                        .fluidInputs(
                                Materials.Neutronium.getMolten(5760L),
                                new FluidStack(MaterialsAlloy.QUANTUM.getFluid(), 5760),
                                Materials.Infinity.getMolten(1440L),
                                MaterialsUEVplus.ExcitedDTEC.getFluid(1000))
                        .fluidOutputs(
                                new FluidStack(MaterialsElements.STANDALONE.HYPOGEN.getFluid(), 5760),
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000L))
                        .duration(1 * MINUTES + 15 * SECONDS).eut(1_600_000_000).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // V2
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Neutronium.getMolten(5760L),
                                new FluidStack(MaterialsAlloy.QUANTUM.getFluid(), 5760),
                                Materials.Infinity.getMolten(1440L),
                                MaterialsUEVplus.ExcitedDTRC.getFluid(1000))
                        .fluidOutputs(
                                new FluidStack(MaterialsElements.STANDALONE.HYPOGEN.getFluid(), 2880),
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000L / 2))
                        .duration(1 * MINUTES + 15 * SECONDS).eut(1_200_000_000).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                // V1
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                new FluidStack(MaterialsElements.STANDALONE.HYPOGEN.getFluid(), 144),
                                Materials.Neutronium.getMolten(5760L),
                                new FluidStack(MaterialsAlloy.QUANTUM.getFluid(), 5760),
                                Materials.Infinity.getMolten(1440L),
                                MaterialsUEVplus.ExcitedDTPC.getFluid(1000))
                        .fluidOutputs(
                                new FluidStack(MaterialsElements.STANDALONE.HYPOGEN.getFluid(), 1584),
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000L / 4))
                        .duration(1 * MINUTES + 15 * SECONDS).eut(800_000_000).metadata(COIL_HEAT, infinity_heat)
                        .addTo(plasmaForgeRecipes);
            }

            {
                // Chromatic Glass

                long base_quantity = 256L;
                long tier_up_multiplier = 2L;

                long tier_1_quantity = 144L * base_quantity;
                long tier_2_quantity = tier_1_quantity * tier_up_multiplier;
                long tier_3_quantity = tier_2_quantity * tier_up_multiplier;
                long tier_4_quantity = tier_3_quantity * tier_up_multiplier;

                DTPFCalculator chromatic_glass = new DTPFCalculator().setBaseParallel(base_quantity)
                        .calculateNonEBFRecipe(1_966_080, 6_000);

                // Tier 4
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTEC.getFluid(chromatic_glass.getCatalystAmount(3)),
                                Materials.Glass.getMolten(tier_4_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(chromatic_glass.getResidueAmount(3)),
                                new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_4_quantity))
                        .duration(chromatic_glass.getDuration(3)).eut(chromatic_glass.getEUt(3))
                        .metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);

                // Tier 3
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTRC.getFluid(chromatic_glass.getCatalystAmount(2)),
                                Materials.Glass.getMolten(tier_3_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(chromatic_glass.getResidueAmount(2)),
                                new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_3_quantity))
                        .duration(chromatic_glass.getDuration(2)).eut(chromatic_glass.getEUt(2))
                        .metadata(COIL_HEAT, hypogen_heat).addTo(plasmaForgeRecipes);

                // Tier 2
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTPC.getFluid(chromatic_glass.getCatalystAmount(1)),
                                Materials.Glass.getMolten(tier_2_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(chromatic_glass.getResidueAmount(1)),
                                new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_2_quantity))
                        .duration(chromatic_glass.getDuration(1)).eut(chromatic_glass.getEUt(1))
                        .metadata(COIL_HEAT, infinity_heat).addTo(plasmaForgeRecipes);

                // Tier 1
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTCC.getFluid(chromatic_glass.getCatalystAmount(0)),
                                Materials.Glass.getMolten(tier_1_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(chromatic_glass.getResidueAmount(0)),
                                new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_1_quantity))
                        .duration(chromatic_glass.getDuration(0)).eut(chromatic_glass.getEUt(0))
                        .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);
            }

            {
                // Spacetime

                // V2
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                ItemList.EnergisedTesseract.get(1),
                                GregtechItemList.Compressed_Fusion_Reactor.get(0))
                        .fluidInputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(5000L),
                                Materials.Infinity.getMolten(1152L))
                        .fluidOutputs(MaterialsUEVplus.SpaceTime.getMolten(576L)).duration(20 * SECONDS)
                        .eut(2_000_000_000).metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);

                // V1
                GTValues.RA.stdBuilder().itemInputs(ItemList.EnergisedTesseract.get(1))
                        .fluidInputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(10000L),
                                Materials.Infinity.getMolten(2304L),
                                new FluidStack(MaterialsElements.STANDALONE.HYPOGEN.getFluid(), 1152))
                        .fluidOutputs(MaterialsUEVplus.SpaceTime.getMolten(144L)).duration(40 * SECONDS)
                        .eut(1_000_000_000).metadata(COIL_HEAT, hypogen_heat).addTo(plasmaForgeRecipes);
            }

            {
                // Raw Tesseract
                // 16 Vertices, 24 faces and 32 edges.

                // V3
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTOreDictUnificator.get(OrePrefixes.stick, MaterialsUEVplus.TranscendentMetal, 32L),
                                MaterialsAlloy.BLACK_TITANIUM.getPlate(24),
                                MaterialsAlloy.ZERON_100.getScrew(16),
                                GregtechItemList.Laser_Lens_Special.get(1))
                        .itemOutputs(ItemList.Tesseract.get(16))
                        .fluidInputs(MaterialsUEVplus.ExcitedDTSC.getFluid(1000))
                        .fluidOutputs(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(2000))
                        .duration(40 * SECONDS).eut(512_000_000).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // V2
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTOreDictUnificator.get(OrePrefixes.stick, Materials.CosmicNeutronium, 12L),
                                GGMaterial.tairitsu.get(OrePrefixes.stick, 12),
                                GTOreDictUnificator.get(OrePrefixes.stick, MaterialsUEVplus.TranscendentMetal, 8L),
                                MaterialsAlloy.BOTMIUM.getPlate(24),
                                MaterialsAlloy.ARCANITE.getScrew(16),
                                NHItemList.EnderQuantumComponent.getIS())
                        .itemOutputs(ItemList.Tesseract.get(8)).fluidInputs(MaterialsUEVplus.ExcitedDTEC.getFluid(1000))
                        .fluidOutputs(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000))
                        .duration(40 * SECONDS).eut(128_000_000).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // V1
                GTValues.RA.stdBuilder()
                        .itemInputs(
                                GTOreDictUnificator.get(OrePrefixes.stick, Materials.CosmicNeutronium, 8L),
                                MaterialsAlloy.OCTIRON.getRod(8),
                                GGMaterial.tairitsu.get(OrePrefixes.stick, 8),
                                GTOreDictUnificator.get(OrePrefixes.stick, Materials.Sunnarium, 8L),
                                MaterialsAlloy.ABYSSAL.getPlate(24),
                                MaterialsAlloy.BOTMIUM.getScrew(16),
                                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ZPM, 1L))
                        .itemOutputs(ItemList.Tesseract.get(4)).fluidInputs(MaterialsUEVplus.ExcitedDTRC.getFluid(1000))
                        .fluidOutputs(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000 / 2))
                        .duration(40 * SECONDS).eut(32_000_000).metadata(COIL_HEAT, infinity_heat)
                        .addTo(plasmaForgeRecipes);
            }

            if (Avaritia.isModLoaded()) {
                if (GalacticraftAmunRa.isModLoaded()) {
                    // Dark Matter
                    GTValues.RA.stdBuilder()
                            .itemInputs(
                                    GTOreDictUnificator.get(OrePrefixes.block, MaterialsUEVplus.TranscendentMetal, 16),
                                    GTModHandler.getModItem(Avaritia.ID, "Resource", 16L, 8),
                                    GTUtility.copyAmount(0, Particle.getBaseParticle(Particle.HIGGS_BOSON)))
                            .fluidInputs(
                                    MaterialsUEVplus.ExcitedDTEC.getFluid(1797693L),
                                    Materials.CosmicNeutronium.getMolten(16384 * 144),
                                    GGMaterial.tairitsu.getMolten(16384 * 144),
                                    MaterialsElements.STANDALONE.CELESTIAL_TUNGSTEN.getFluidStack(4096 * 144))
                            .itemOutputs(GTModHandler.getModItem(GalacticraftAmunRa.ID, "tile.baseBlockRock", 1L, 14))
                            .duration(80 * SECONDS)
                            .fluidOutputs(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1797693L))
                            .eut(TierEU.RECIPE_UMV).metadata(COIL_HEAT, hypogen_heat).addTo(plasmaForgeRecipes);
                }

                {
                    // Infinity

                    long base_time = 10_000;
                    long base_quantity = 64L;

                    DTPFCalculator infinity = new DTPFCalculator().setBaseParallel(base_quantity)
                            .setLowestCatalystTier(2).setHighestCatalystTier(4).setEUtDivisor(2)
                            .calculateNonEBFRecipe(32_000_000, base_time);

                    // Tier 5
                    GTValues.RA.stdBuilder()
                            .itemInputs(
                                    GTModHandler.getModItem(Avaritia.ID, "Resource", 4L, 5),
                                    GTUtility.getIntegratedCircuit(4))
                            .fluidInputs(MaterialsUEVplus.ExcitedDTSC.getFluid(infinity.getCatalystAmount(4)))
                            .fluidOutputs(
                                    MaterialsUEVplus.DimensionallyTranscendentResidue
                                            .getFluid(infinity.getResidueAmount(4)),
                                    Materials.Infinity.getMolten(256L * 144L))
                            .duration(infinity.getDuration(4)).eut(infinity.getEUt(4)).metadata(COIL_HEAT, eternal_heat)
                            .addTo(plasmaForgeRecipes);

                    // Tier 4
                    GTValues.RA.stdBuilder()
                            .itemInputs(
                                    GTModHandler.getModItem(Avaritia.ID, "Resource", 2L, 5),
                                    GTUtility.getIntegratedCircuit(4))
                            .fluidInputs(MaterialsUEVplus.ExcitedDTEC.getFluid(infinity.getCatalystAmount(3)))
                            .fluidOutputs(
                                    MaterialsUEVplus.DimensionallyTranscendentResidue
                                            .getFluid(infinity.getResidueAmount(3)),
                                    Materials.Infinity.getMolten(128L * 144L))
                            .duration(infinity.getDuration(3)).eut(infinity.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                            .addTo(plasmaForgeRecipes);

                    // Tier 3
                    GTValues.RA.stdBuilder()
                            .itemInputs(
                                    GTModHandler.getModItem(Avaritia.ID, "Resource", 1L, 5),
                                    GTUtility.getIntegratedCircuit(4))
                            .fluidInputs(MaterialsUEVplus.ExcitedDTRC.getFluid(infinity.getCatalystAmount(2) / 64))
                            .fluidOutputs(
                                    MaterialsUEVplus.DimensionallyTranscendentResidue
                                            .getFluid(infinity.getResidueAmount(2) / 64),
                                    Materials.Infinity.getMolten(144L))
                            .duration(infinity.getDuration(2) / 128).eut(infinity.getEUt(2) / 64)
                            .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);

                    // Tier 3.5
                    GTValues.RA.stdBuilder()
                            .itemInputs(
                                    GTModHandler.getModItem(Avaritia.ID, "Resource", 1L, 5),
                                    GTUtility.getIntegratedCircuit(1))
                            .fluidInputs(MaterialsUEVplus.ExcitedDTRC.getFluid(infinity.getCatalystAmount(2)))
                            .fluidOutputs(
                                    MaterialsUEVplus.DimensionallyTranscendentResidue
                                            .getFluid(infinity.getResidueAmount(2)),
                                    Materials.Infinity.getMolten(64L * 144L))
                            .duration(infinity.getDuration(2)).eut(infinity.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                            .addTo(plasmaForgeRecipes);

                    if (Forestry.isModLoaded()) {
                        DTPFCalculator infinity_bee = new DTPFCalculator().setBaseParallel(base_quantity)
                                .setLowestCatalystTier(2).setHighestCatalystTier(4).setCatalystDiscount(15)
                                .setProcessingTimeDiscount(50).setEUtDivisor(2)
                                .calculateNonEBFRecipe(32_000_000, base_time);

                        // Tier 5 - Combs
                        GTValues.RA.stdBuilder()
                                .itemInputs(
                                        GTModHandler.getModItem(Avaritia.ID, "Resource", 4L, 5),
                                        GTBees.combs.getStackForType(CombType.INFINITY),
                                        GTUtility.getIntegratedCircuit(3))
                                .fluidInputs(MaterialsUEVplus.ExcitedDTSC.getFluid(infinity_bee.getCatalystAmount(4)))
                                .fluidOutputs(
                                        MaterialsUEVplus.DimensionallyTranscendentResidue
                                                .getFluid(infinity_bee.getResidueAmount(4)),
                                        Materials.Infinity.getMolten(256L * 144L))
                                .duration(infinity_bee.getDuration(4)).eut(infinity_bee.getEUt(4))
                                .metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);

                        // Tier 4 - Combs
                        GTValues.RA.stdBuilder()
                                .itemInputs(
                                        GTModHandler.getModItem(Avaritia.ID, "Resource", 2L, 5),
                                        GTBees.combs.getStackForType(CombType.INFINITY),
                                        GTUtility.getIntegratedCircuit(3))
                                .fluidInputs(MaterialsUEVplus.ExcitedDTEC.getFluid(infinity_bee.getCatalystAmount(3)))
                                .fluidOutputs(
                                        MaterialsUEVplus.DimensionallyTranscendentResidue
                                                .getFluid(infinity_bee.getResidueAmount(3)),
                                        Materials.Infinity.getMolten(128L * 144L))
                                .duration(infinity_bee.getDuration(3)).eut(infinity_bee.getEUt(3))
                                .metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);

                        // Tier 3.5 - Combs
                        GTValues.RA.stdBuilder()
                                .itemInputs(
                                        GTModHandler.getModItem(Avaritia.ID, "Resource", 1L, 5),
                                        GTBees.combs.getStackForType(CombType.INFINITY),
                                        GTUtility.getIntegratedCircuit(2))
                                .fluidInputs(MaterialsUEVplus.ExcitedDTRC.getFluid(infinity_bee.getCatalystAmount(2)))
                                .fluidOutputs(
                                        MaterialsUEVplus.DimensionallyTranscendentResidue
                                                .getFluid(infinity_bee.getResidueAmount(2)),
                                        Materials.Infinity.getMolten(64L * 144L))
                                .duration(infinity_bee.getDuration(2)).eut(infinity_bee.getEUt(2))
                                .metadata(COIL_HEAT, hypogen_heat).addTo(plasmaForgeRecipes);

                        // Tier 3 - Combs
                        GTValues.RA.stdBuilder()
                                .itemInputs(
                                        GTModHandler.getModItem(Avaritia.ID, "Resource", 1L, 5),
                                        GTBees.combs.getStackForType(CombType.INFINITY),
                                        GTUtility.getIntegratedCircuit(3))
                                .fluidInputs(
                                        MaterialsUEVplus.ExcitedDTRC.getFluid(infinity_bee.getCatalystAmount(2) / 64))
                                .fluidOutputs(
                                        MaterialsUEVplus.DimensionallyTranscendentResidue
                                                .getFluid(infinity_bee.getResidueAmount(2) / 64),
                                        Materials.Infinity.getMolten(144L))
                                .duration(infinity_bee.getDuration(2) / 128).eut(infinity_bee.getEUt(2) / 64)
                                .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);
                    }

                }

            }

            {
                // LuV Superconductor

                long base_quantity = 8190L;
                long tier_up_multiplier = 2L;

                long tier_1_quantity = 144L * base_quantity;
                long tier_2_quantity = tier_1_quantity * tier_up_multiplier;
                long tier_3_quantity = tier_2_quantity * tier_up_multiplier;
                long tier_4_quantity = tier_3_quantity * tier_up_multiplier;

                DTPFCalculator baseLuV = new DTPFCalculator().setBaseParallel(base_quantity)
                        .calculateGenericEBFBasedRecipe(
                                Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid);

                // Tier 4
                GTValues.RA.stdBuilder().itemInputs(GTUtility.getIntegratedCircuit(6))
                        .fluidInputs(
                                Materials.Indium.getMolten(4L * tier_4_quantity / 30),
                                Materials.Tin.getMolten(2L * tier_4_quantity / 30),
                                Materials.Barium.getMolten(2L * tier_4_quantity / 30),
                                Materials.Titanium.getMolten(tier_4_quantity / 30),
                                Materials.Copper.getMolten(7L * tier_4_quantity / 30),
                                Materials.Oxygen.getPlasma(14_000L / 32 * tier_4_quantity / 30 / 144),
                                MaterialsUEVplus.ExcitedDTEC.getFluid(baseLuV.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseLuV.getResidueAmount(3)),
                                Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid
                                        .getMolten(tier_4_quantity))
                        .duration(baseLuV.getDuration(3)).eut(baseLuV.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 3
                GTValues.RA.stdBuilder().itemInputs(GTUtility.getIntegratedCircuit(6))
                        .fluidInputs(
                                Materials.Indium.getMolten(4L * tier_3_quantity / 30),
                                Materials.Tin.getMolten(2L * tier_3_quantity / 30),
                                Materials.Barium.getMolten(2L * tier_3_quantity / 30),
                                Materials.Titanium.getMolten(tier_3_quantity / 30),
                                Materials.Copper.getMolten(7L * tier_3_quantity / 30),
                                Materials.Oxygen.getPlasma(14_000L / 32 * tier_3_quantity / 30 / 144),
                                MaterialsUEVplus.ExcitedDTRC.getFluid(baseLuV.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseLuV.getResidueAmount(2)),
                                Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid
                                        .getMolten(tier_3_quantity))
                        .duration(baseLuV.getDuration(2)).eut(baseLuV.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 2
                GTValues.RA.stdBuilder().itemInputs(GTUtility.getIntegratedCircuit(6))
                        .fluidInputs(
                                Materials.Indium.getMolten(4L * tier_2_quantity / 30),
                                Materials.Tin.getMolten(2L * tier_2_quantity / 30),
                                Materials.Barium.getMolten(2L * tier_2_quantity / 30),
                                Materials.Titanium.getMolten(tier_2_quantity / 30),
                                Materials.Copper.getMolten(7L * tier_2_quantity / 30),
                                Materials.Oxygen.getPlasma(14_000L / 32 * tier_2_quantity / 30 / 144),
                                MaterialsUEVplus.ExcitedDTPC.getFluid(baseLuV.getCatalystAmount(1)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseLuV.getResidueAmount(1)),
                                Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid
                                        .getMolten(tier_2_quantity))
                        .duration(baseLuV.getDuration(1)).eut(baseLuV.getEUt(1)).metadata(COIL_HEAT, infinity_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 1
                GTValues.RA.stdBuilder().itemInputs(GTUtility.getIntegratedCircuit(6))
                        .fluidInputs(
                                Materials.Indium.getMolten(4L * tier_1_quantity / 30),
                                Materials.Tin.getMolten(2L * tier_1_quantity / 30),
                                Materials.Barium.getMolten(2L * tier_1_quantity / 30),
                                Materials.Titanium.getMolten(tier_1_quantity / 30),
                                Materials.Copper.getMolten(7L * tier_1_quantity / 30),
                                Materials.Oxygen.getPlasma(14_000L / 32 * tier_1_quantity / 30 / 144),
                                MaterialsUEVplus.ExcitedDTCC.getFluid(baseLuV.getCatalystAmount(0)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseLuV.getResidueAmount(0)),
                                Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid
                                        .getMolten(tier_1_quantity))
                        .duration(baseLuV.getDuration(0)).eut(baseLuV.getEUt(0)).metadata(COIL_HEAT, awakened_heat)
                        .addTo(plasmaForgeRecipes);
            }

            {
                // ZPM Superconductor

                long base_quantity = 4095L;
                long tier_up_multiplier = 2L;

                long tier_1_quantity = 144L * base_quantity;
                long tier_2_quantity = tier_1_quantity * tier_up_multiplier;
                long tier_3_quantity = tier_2_quantity * tier_up_multiplier;
                long tier_4_quantity = tier_3_quantity * tier_up_multiplier;

                DTPFCalculator baseZPM = new DTPFCalculator().setBaseParallel(base_quantity)
                        .calculateGenericEBFBasedRecipe(Materials.Tetranaquadahdiindiumhexaplatiumosminid);

                // Tier 4
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Palladium.getMolten(6L * tier_4_quantity / 13),
                                Materials.Indium.getMolten(2L * tier_4_quantity / 13),
                                Materials.Osmium.getMolten(tier_4_quantity / 13),
                                Materials.Naquadah.getMolten(4L * tier_4_quantity / 13),
                                MaterialsUEVplus.ExcitedDTEC.getFluid(baseZPM.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseZPM.getResidueAmount(3)),
                                Materials.Tetranaquadahdiindiumhexaplatiumosminid.getMolten(tier_4_quantity))
                        .duration(baseZPM.getDuration(3)).eut(baseZPM.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 3
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Palladium.getMolten(6L * tier_3_quantity / 13),
                                Materials.Indium.getMolten(2L * tier_3_quantity / 13),
                                Materials.Osmium.getMolten(tier_3_quantity / 13),
                                Materials.Naquadah.getMolten(4L * tier_3_quantity / 13),
                                MaterialsUEVplus.ExcitedDTRC.getFluid(baseZPM.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseZPM.getResidueAmount(2)),
                                Materials.Tetranaquadahdiindiumhexaplatiumosminid.getMolten(tier_3_quantity))
                        .duration(baseZPM.getDuration(2)).eut(baseZPM.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 2
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Palladium.getMolten(6L * tier_2_quantity / 13),
                                Materials.Indium.getMolten(2L * tier_2_quantity / 13),
                                Materials.Osmium.getMolten(tier_2_quantity / 13),
                                Materials.Naquadah.getMolten(4L * tier_2_quantity / 13),
                                MaterialsUEVplus.ExcitedDTPC.getFluid(baseZPM.getCatalystAmount(1)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseZPM.getResidueAmount(1)),
                                Materials.Tetranaquadahdiindiumhexaplatiumosminid.getMolten(tier_2_quantity))
                        .duration(baseZPM.getDuration(1)).eut(baseZPM.getEUt(1)).metadata(COIL_HEAT, infinity_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 1
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Palladium.getMolten(6L * tier_1_quantity / 13),
                                Materials.Indium.getMolten(2L * tier_1_quantity / 13),
                                Materials.Osmium.getMolten(tier_1_quantity / 13),
                                Materials.Naquadah.getMolten(4L * tier_1_quantity / 13),
                                MaterialsUEVplus.ExcitedDTCC.getFluid(baseZPM.getCatalystAmount(0)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseZPM.getResidueAmount(0)),
                                Materials.Tetranaquadahdiindiumhexaplatiumosminid.getMolten(tier_1_quantity))
                        .duration(baseZPM.getDuration(0)).eut(baseZPM.getEUt(0)).metadata(COIL_HEAT, awakened_heat)
                        .addTo(plasmaForgeRecipes);
            }

            {
                // UV Superconductor

                long base_quantity = 2043L;
                long tier_up_multiplier = 2L;

                long tier_1_quantity = 144L * base_quantity;
                long tier_2_quantity = tier_1_quantity * tier_up_multiplier;
                long tier_3_quantity = tier_2_quantity * tier_up_multiplier;
                long tier_4_quantity = tier_3_quantity * tier_up_multiplier;

                DTPFCalculator baseUV = new DTPFCalculator().setBaseParallel(base_quantity)
                        .calculateGenericEBFBasedRecipe(Materials.Longasssuperconductornameforuvwire);

                // Tier 4
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Samarium.getMolten(tier_4_quantity / 9),
                                Materials.Europium.getMolten(tier_4_quantity / 9),
                                Materials.Osmiridium.getMolten(3L * tier_4_quantity / 9),
                                Materials.Naquadria.getMolten(4L * tier_4_quantity / 9),
                                MaterialsUEVplus.ExcitedDTEC.getFluid(baseUV.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUV.getResidueAmount(3)),
                                Materials.Longasssuperconductornameforuvwire.getMolten(tier_4_quantity))
                        .duration(baseUV.getDuration(3)).eut(baseUV.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 3
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Samarium.getMolten(tier_3_quantity / 9),
                                Materials.Europium.getMolten(tier_3_quantity / 9),
                                Materials.Osmiridium.getMolten(3L * tier_3_quantity / 9),
                                Materials.Naquadria.getMolten(4L * tier_3_quantity / 9),
                                MaterialsUEVplus.ExcitedDTRC.getFluid(baseUV.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUV.getResidueAmount(2)),
                                Materials.Longasssuperconductornameforuvwire.getMolten(tier_3_quantity))
                        .duration(baseUV.getDuration(2)).eut(baseUV.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 2
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Samarium.getMolten(tier_2_quantity / 9),
                                Materials.Europium.getMolten(tier_2_quantity / 9),
                                Materials.Osmiridium.getMolten(3L * tier_2_quantity / 9),
                                Materials.Naquadria.getMolten(4L * tier_2_quantity / 9),
                                MaterialsUEVplus.ExcitedDTPC.getFluid(baseUV.getCatalystAmount(1)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUV.getResidueAmount(1)),
                                Materials.Longasssuperconductornameforuvwire.getMolten(tier_2_quantity))
                        .duration(baseUV.getDuration(1)).eut(baseUV.getEUt(1)).metadata(COIL_HEAT, infinity_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 1
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Samarium.getMolten(tier_1_quantity / 9),
                                Materials.Europium.getMolten(tier_1_quantity / 9),
                                Materials.Osmiridium.getMolten(3L * tier_1_quantity / 9),
                                Materials.Naquadria.getMolten(4L * tier_1_quantity / 9),
                                MaterialsUEVplus.ExcitedDTCC.getFluid(baseUV.getCatalystAmount(0)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUV.getResidueAmount(0)),
                                Materials.Longasssuperconductornameforuvwire.getMolten(tier_1_quantity))
                        .duration(baseUV.getDuration(0)).eut(baseUV.getEUt(0)).metadata(COIL_HEAT, awakened_heat)
                        .addTo(plasmaForgeRecipes);
            }

            {
                // UHV Superconductor

                long base_quantity = 1008L;
                long tier_up_multiplier = 2L;

                long tier_2_quantity = 144L * base_quantity;
                long tier_3_quantity = tier_2_quantity * tier_up_multiplier;
                long tier_4_quantity = tier_3_quantity * tier_up_multiplier;
                long tier_5_quantity = tier_4_quantity * tier_up_multiplier;

                DTPFCalculator baseUHV = new DTPFCalculator().setBaseParallel(base_quantity).setLowestCatalystTier(1)
                        .setHighestCatalystTier(4)
                        .calculateGenericEBFBasedRecipe(Materials.Longasssuperconductornameforuhvwire);

                // Tier 5
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Draconium.getMolten(6L * tier_5_quantity / 24),
                                Materials.Americium.getMolten(6L * tier_5_quantity / 24),
                                Materials.CosmicNeutronium.getMolten(7L * tier_5_quantity / 24),
                                Materials.Tritanium.getMolten(5L * tier_5_quantity / 24),
                                MaterialsUEVplus.ExcitedDTSC.getFluid(baseUHV.getCatalystAmount(4)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUHV.getResidueAmount(4)),
                                Materials.Longasssuperconductornameforuhvwire.getMolten(tier_5_quantity))
                        .duration(baseUHV.getDuration(4)).eut(baseUHV.getEUt(4)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 4
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Draconium.getMolten(6L * tier_4_quantity / 24),
                                Materials.Americium.getMolten(6L * tier_4_quantity / 24),
                                Materials.CosmicNeutronium.getMolten(7L * tier_4_quantity / 24),
                                Materials.Tritanium.getMolten(5L * tier_4_quantity / 24),
                                MaterialsUEVplus.ExcitedDTEC.getFluid(baseUHV.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUHV.getResidueAmount(3)),
                                Materials.Longasssuperconductornameforuhvwire.getMolten(tier_4_quantity))
                        .duration(baseUHV.getDuration(3)).eut(baseUHV.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 3
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Draconium.getMolten(6L * tier_3_quantity / 24),
                                Materials.Americium.getMolten(6L * tier_3_quantity / 24),
                                Materials.CosmicNeutronium.getMolten(7L * tier_3_quantity / 24),
                                Materials.Tritanium.getMolten(5L * tier_3_quantity / 24),
                                MaterialsUEVplus.ExcitedDTRC.getFluid(baseUHV.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUHV.getResidueAmount(2)),
                                Materials.Longasssuperconductornameforuhvwire.getMolten(tier_3_quantity))
                        .duration(baseUHV.getDuration(2)).eut(baseUHV.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 2
                GTValues.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Draconium.getMolten(6L * tier_2_quantity / 24),
                                Materials.Americium.getMolten(6L * tier_2_quantity / 24),
                                Materials.CosmicNeutronium.getMolten(7L * tier_2_quantity / 24),
                                Materials.Tritanium.getMolten(5L * tier_2_quantity / 24),
                                MaterialsUEVplus.ExcitedDTPC.getFluid(baseUHV.getCatalystAmount(1)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUHV.getResidueAmount(1)),
                                Materials.Longasssuperconductornameforuhvwire.getMolten(tier_2_quantity))
                        .duration(baseUHV.getDuration(1)).eut(baseUHV.getEUt(1)).metadata(COIL_HEAT, infinity_heat)
                        .addTo(plasmaForgeRecipes);
            }

            {
                // UEV Superconductor

                long base_quantity = 504L;
                long tier_up_multiplier = 2L;

                long tier_3_quantity = 144L * base_quantity;
                long tier_4_quantity = tier_3_quantity * tier_up_multiplier;
                long tier_5_quantity = tier_4_quantity * tier_up_multiplier;

                DTPFCalculator baseUEV = new DTPFCalculator().setBaseParallel(base_quantity).setLowestCatalystTier(2)
                        .setHighestCatalystTier(4).calculateGenericEBFBasedRecipe(Materials.SuperconductorUEVBase);

                // Tier 5
                GTValues.RA.stdBuilder().fluidInputs(
                        new FluidStack(FluidRegistry.getFluid("molten.advancednitinol"), (int) tier_5_quantity / 12),
                        new FluidStack(FluidRegistry.getFluid("molten.celestialtungsten"), (int) tier_5_quantity / 12),
                        Materials.DraconiumAwakened.getMolten(5L * tier_5_quantity / 12),
                        Materials.Infinity.getMolten(5L * tier_5_quantity / 12),
                        Materials.Iron.getPlasma(tier_5_quantity / 12),
                        MaterialsUEVplus.ExcitedDTSC.getFluid(baseUEV.getCatalystAmount(4)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUEV.getResidueAmount(4)),
                                Materials.SuperconductorUEVBase.getMolten(tier_5_quantity))
                        .duration(baseUEV.getDuration(4)).eut(baseUEV.getEUt(4)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 4
                GTValues.RA.stdBuilder().fluidInputs(
                        new FluidStack(FluidRegistry.getFluid("molten.advancednitinol"), (int) tier_4_quantity / 12),
                        new FluidStack(FluidRegistry.getFluid("molten.celestialtungsten"), (int) tier_4_quantity / 12),
                        Materials.DraconiumAwakened.getMolten(5L * tier_4_quantity / 12),
                        Materials.Infinity.getMolten(5L * tier_4_quantity / 12),
                        Materials.Iron.getPlasma(tier_4_quantity / 12),
                        MaterialsUEVplus.ExcitedDTEC.getFluid(baseUEV.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUEV.getResidueAmount(3)),
                                Materials.SuperconductorUEVBase.getMolten(tier_4_quantity))
                        .duration(baseUEV.getDuration(3)).eut(baseUEV.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 3
                GTValues.RA.stdBuilder().fluidInputs(
                        new FluidStack(FluidRegistry.getFluid("molten.advancednitinol"), (int) tier_3_quantity / 12),
                        new FluidStack(FluidRegistry.getFluid("molten.celestialtungsten"), (int) tier_3_quantity / 12),
                        Materials.DraconiumAwakened.getMolten(5L * tier_3_quantity / 12),
                        Materials.Infinity.getMolten(5L * tier_3_quantity / 12),
                        Materials.Iron.getPlasma(tier_3_quantity / 12),
                        MaterialsUEVplus.ExcitedDTRC.getFluid(baseUEV.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUEV.getResidueAmount(2)),
                                Materials.SuperconductorUEVBase.getMolten(tier_3_quantity))
                        .duration(baseUEV.getDuration(2)).eut(baseUEV.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);
            }

            {
                // UIV Superconductor

                long base_quantity = 250L;
                long tier_up_multiplier = 2L;

                long tier_3_quantity = 144L * base_quantity;
                long tier_4_quantity = tier_3_quantity * tier_up_multiplier;
                long tier_5_quantity = tier_4_quantity * tier_up_multiplier;

                DTPFCalculator baseUIV = new DTPFCalculator().setBaseParallel(base_quantity).setLowestCatalystTier(2)
                        .setHighestCatalystTier(4).calculateGenericEBFBasedRecipe(Materials.SuperconductorUIVBase);

                // Tier 5
                GTValues.RA.stdBuilder().fluidInputs(
                        Materials.RadoxPolymer.getMolten(4L * tier_5_quantity / 25),
                        MaterialsUEVplus.TranscendentMetal.getMolten(10L * tier_5_quantity / 25),
                        new FluidStack(FluidRegistry.getFluid("molten.rhugnor"), (int) tier_5_quantity * 6 / 25),
                        new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_5_quantity * 5 / 25),
                        Materials.Bismuth.getPlasma(tier_5_quantity / 25),
                        MaterialsUEVplus.ExcitedDTSC.getFluid(baseUIV.getCatalystAmount(4)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUIV.getResidueAmount(4)),
                                Materials.SuperconductorUIVBase.getMolten(tier_5_quantity))
                        .duration(baseUIV.getDuration(4)).eut(baseUIV.getEUt(4)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 4
                GTValues.RA.stdBuilder().fluidInputs(
                        Materials.RadoxPolymer.getMolten(4L * tier_4_quantity / 25),
                        MaterialsUEVplus.TranscendentMetal.getMolten(10L * tier_4_quantity / 25),
                        new FluidStack(FluidRegistry.getFluid("molten.rhugnor"), (int) tier_4_quantity * 6 / 25),
                        new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_4_quantity * 5 / 25),
                        Materials.Bismuth.getPlasma(tier_4_quantity / 25),
                        MaterialsUEVplus.ExcitedDTEC.getFluid(baseUIV.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUIV.getResidueAmount(3)),
                                Materials.SuperconductorUIVBase.getMolten(tier_4_quantity))
                        .duration(baseUIV.getDuration(3)).eut(baseUIV.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 3
                GTValues.RA.stdBuilder().fluidInputs(
                        Materials.RadoxPolymer.getMolten(4L * tier_3_quantity / 25),
                        MaterialsUEVplus.TranscendentMetal.getMolten(10L * tier_3_quantity / 25),
                        new FluidStack(FluidRegistry.getFluid("molten.rhugnor"), (int) tier_3_quantity * 6 / 25),
                        new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_3_quantity * 5 / 25),
                        Materials.Bismuth.getPlasma(tier_3_quantity / 25),
                        MaterialsUEVplus.ExcitedDTRC.getFluid(baseUIV.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUIV.getResidueAmount(2)),
                                Materials.SuperconductorUIVBase.getMolten(tier_3_quantity))
                        .duration(baseUIV.getDuration(2)).eut(baseUIV.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);
            }

            {
                // UMV Superconductor

                long base_quantity = 243L;
                long tier_up_multiplier = 2L;

                long tier_4_quantity = 144L * base_quantity;
                long tier_5_quantity = tier_4_quantity * tier_up_multiplier;

                DTPFCalculator baseUMV = new DTPFCalculator().setBaseParallel(base_quantity).setLowestCatalystTier(3)
                        .setHighestCatalystTier(4).calculateGenericEBFBasedRecipe(Materials.SuperconductorUMVBase);

                // Tier 5
                GTValues.RA.stdBuilder().fluidInputs(
                        MaterialsUEVplus.SpaceTime.getMolten(6L * tier_5_quantity / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.orundum"), (int) tier_5_quantity * 3 / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.hypogen"), (int) tier_5_quantity * 11 / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.titansteel"), (int) tier_5_quantity * 5 / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.dragonblood"), (int) tier_5_quantity * 2 / 27),
                        Materials.Oxygen.getPlasma(tier_5_quantity / 27),
                        MaterialsUEVplus.ExcitedDTSC.getFluid(baseUMV.getCatalystAmount(4)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUMV.getResidueAmount(4)),
                                Materials.SuperconductorUMVBase.getMolten(tier_5_quantity))
                        .duration(baseUMV.getDuration(4)).eut(baseUMV.getEUt(4)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                // Tier 4
                GTValues.RA.stdBuilder().fluidInputs(
                        MaterialsUEVplus.SpaceTime.getMolten(6L * tier_4_quantity / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.orundum"), (int) tier_4_quantity * 3 / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.hypogen"), (int) tier_4_quantity * 11 / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.titansteel"), (int) tier_4_quantity * 5 / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.dragonblood"), (int) tier_4_quantity * 2 / 27),
                        Materials.Oxygen.getPlasma(tier_4_quantity / 27),
                        MaterialsUEVplus.ExcitedDTEC.getFluid(baseUMV.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUMV.getResidueAmount(3)),
                                Materials.SuperconductorUMVBase.getMolten(tier_4_quantity))
                        .duration(baseUMV.getDuration(3)).eut(baseUMV.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);
            }

            // Transcendent metal
            long total_EU_per_ingot = 113_246_208_000L + 629_145_600L;
            long recipe_time = 180 * 20;

            DTPFCalculator transcendent_metal = new DTPFCalculator().setBaseParallel(360).setLowestCatalystTier(4)
                    .setScalingFactor(8).setHighestCatalystTier(4)
                    .calculateNonEBFRecipe(total_EU_per_ingot / recipe_time, recipe_time);

            GTValues.RA.stdBuilder()
                    .itemInputs(ItemList.Tesseract.get(32), GTOreDictUnificator.get("blockCosmicNeutronium", 40))
                    .itemOutputs(
                            MaterialsUEVplus.TranscendentMetal.getBlocks(40),
                            MaterialsUEVplus.TranscendentMetal.getDust(24))
                    .fluidInputs(
                            MaterialsAlloy.TITANSTEEL.getFluidStack(144 * 40 * 9),
                            Materials.CallistoIce.getMolten(144 * 20 * 9),
                            Materials.Ledox.getMolten(144 * 20 * 9),
                            Materials.Tungsten.getMolten(144 * 40 * 9),
                            MaterialsUEVplus.ExcitedDTSC.getFluid(transcendent_metal.getCatalystAmount(4)))
                    .fluidOutputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue
                                    .getFluid(transcendent_metal.getResidueAmount(4)),
                            MaterialsElements.STANDALONE.CELESTIAL_TUNGSTEN.getFluidStack(144 * 20 * 9))
                    .duration(transcendent_metal.getDuration(4)).eut(transcendent_metal.getEUt(4))
                    .metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);

            // Quantum + Astral Titanium

            // Based on quantum abs recipe
            long total_EU_per_quantum_ingot = (long) (9_437_184_000L * 0.9);
            // Laser engraver recipe
            long total_EU_per_astral_dust = 4_718_592_000L;
            // Astral titanium ebf
            long total_EU_per_astral_ingot_ebf = 22_118_400_000L;
            // Astral titanium freezer
            long total_EU_per_astral_ingot_freezer = 1_639_710_720L;
            // 2 astral titanium blocks per 320 quantum ingots
            float astral_ingots_per_quantum_ingot = 18 / 320f;
            // Based on quantum abs including coil discount
            long recipe_time_per_quantum_ingot = (long) (600 * 20 / 10 * 0.9);
            long total_EUt = (long) (total_EU_per_quantum_ingot + astral_ingots_per_quantum_ingot
                    * (total_EU_per_astral_dust + total_EU_per_astral_ingot_ebf + total_EU_per_astral_ingot_freezer))
                    / recipe_time_per_quantum_ingot;
            DTPFCalculator quantum = new DTPFCalculator().setBaseParallel(320).setLowestCatalystTier(3)
                    .setHighestCatalystTier(4).setEUtDivisor(1.12f)
                    .calculateNonEBFRecipe(total_EUt, recipe_time_per_quantum_ingot);
            GTValues.RA.stdBuilder().itemInputs(
                    CI.getEnergyCore(9, 0),
                    BlockList.Quantinum.getIS(2),
                    // Quantum Anomaly
                    GregtechItemList.Laser_Lens_Special.get(2))
                    .itemOutputs(MaterialsElements.STANDALONE.ASTRAL_TITANIUM.getBlock(2))
                    .fluidInputs(
                            MaterialsAlloy.BLACK_TITANIUM.getFluidStack(144 * 320 * 7 / 10),
                            Materials.Americium.getMolten(144 * 320 / 10),
                            Materials.Bismuth.getMolten(144 * 320 / 10),
                            MaterialsElements.STANDALONE.HYPOGEN.getFluidStack(144 * 320 / 10),
                            Materials.Titanium.getPlasma(144 * 9 * 2 * 2),
                            MaterialsUEVplus.ExcitedDTEC.getFluid(quantum.getCatalystAmount(3)))
                    .fluidOutputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(quantum.getResidueAmount(3)),
                            MaterialsAlloy.QUANTUM.getFluidStack(144 * 320))
                    .duration(quantum.getDuration(3)).eut(quantum.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                    .addTo(plasmaForgeRecipes);

            // Quantum + Astral Titanium v2
            GTValues.RA.stdBuilder().itemInputs(
                    CI.getEnergyCore(9, 0),
                    BlockList.Quantinum.getIS(4),
                    // Quantum Anomaly
                    GregtechItemList.Laser_Lens_Special.get(3))
                    .itemOutputs(MaterialsElements.STANDALONE.ASTRAL_TITANIUM.getBlock(4))
                    .fluidInputs(
                            MaterialsAlloy.BLACK_TITANIUM.getFluidStack(144 * 640 * 7 / 10),
                            Materials.Americium.getMolten(144 * 640 / 10),
                            Materials.Bismuth.getMolten(144 * 640 / 10),
                            MaterialsElements.STANDALONE.HYPOGEN.getFluidStack(144 * 640 / 10),
                            Materials.Titanium.getPlasma(144 * 9 * 4 * 2),
                            MaterialsUEVplus.ExcitedDTSC.getFluid(quantum.getCatalystAmount(4)))
                    .fluidOutputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(quantum.getResidueAmount(4)),
                            MaterialsAlloy.QUANTUM.getFluidStack(144 * 640))
                    .duration(quantum.getDuration(4)).eut(quantum.getEUt(4)).metadata(COIL_HEAT, eternal_heat)
                    .addTo(plasmaForgeRecipes);

            // Timepiece
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GregtechItemList.SpaceTimeBendingCore.get(0),
                            GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.DarkIron, 1L),
                            Materials.Dilithium.getGems(32),
                            MaterialsUEVplus.Universium.getNanite(1))
                    .itemOutputs(ItemList.Timepiece.get(3))
                    .fluidInputs(
                            MaterialsUEVplus.Time.getMolten(18432L * 8),
                            MaterialsUEVplus.ExcitedDTSC.getFluid(100000L))
                    .fluidOutputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(100000L * 2),
                            MaterialsUEVplus.Space.getMolten(18432L * 8))
                    .duration(80 * SECONDS).eut(TierEU.RECIPE_MAX).metadata(COIL_HEAT, 13500).addTo(plasmaForgeRecipes);

            // Time to Space
            GTValues.RA.stdBuilder().itemInputs(
                    // Spacetime Continuum Ripper
                    GregtechItemList.SpaceTimeContinuumRipper.get(0),
                    ItemList.EnergisedTesseract.get(2),
                    // Quantum Anomaly
                    GregtechItemList.Laser_Lens_Special.get(16)).itemOutputs(ItemList.Tesseract.get(1))
                    .fluidInputs(
                            MaterialsUEVplus.Time.getMolten(9216L * 64),
                            MaterialsUEVplus.SpaceTime.getMolten(9216L * 16),
                            MaterialsUEVplus.ExcitedDTSC.getFluid(1000L))
                    .fluidOutputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000L * 2),
                            MaterialsUEVplus.Space.getMolten(9216L * 64))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_MAX).metadata(COIL_HEAT, 13500).addTo(plasmaForgeRecipes);

            // Space to Time
            GTValues.RA.stdBuilder().itemInputs(
                    // Spacetime Continuum Ripper
                    GregtechItemList.SpaceTimeContinuumRipper.get(0),
                    ItemList.EnergisedTesseract.get(2),
                    ItemList.Timepiece.get(16)).itemOutputs(ItemList.Tesseract.get(1))
                    .fluidInputs(
                            MaterialsUEVplus.Space.getMolten(9216L * 64),
                            MaterialsUEVplus.SpaceTime.getMolten(9216L * 16),
                            MaterialsUEVplus.ExcitedDTSC.getFluid(1000L))
                    .fluidOutputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000L * 2),
                            MaterialsUEVplus.Time.getMolten(9216L * 64))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_MAX).metadata(COIL_HEAT, 13500).addTo(plasmaForgeRecipes);

            // Quantum anomaly
            GTValues.RA.stdBuilder()
                    .itemInputs(
                            GregtechItemList.Laser_Lens_Special.get(1),
                            new ItemStack(Particle.getBaseParticle(Particle.GRAVITON).getItem(), 4),
                            CustomItemList.MysteriousCrystalLens.get(0),
                            ItemRefer.HiC_T5.get(0))
                    .fluidInputs(MaterialsUEVplus.ExcitedDTEC.getFluid(92), Materials.Tritanium.getMolten(144))
                    .itemOutputs(GregtechItemList.Laser_Lens_Special.get(4)).duration(5 * SECONDS)
                    .fluidOutputs(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(92))
                    .eut((int) TierEU.RECIPE_UIV).metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);
        }
    }
}
