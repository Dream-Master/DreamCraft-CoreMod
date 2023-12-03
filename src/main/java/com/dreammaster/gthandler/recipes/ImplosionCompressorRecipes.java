package com.dreammaster.gthandler.recipes;

import static gregtech.api.enums.Mods.Avaritia;
import static gregtech.api.enums.Mods.BartWorks;
import static gregtech.api.enums.Mods.DraconicEvolution;
import static gregtech.api.enums.Mods.ExtraUtilities;
import static gregtech.api.enums.Mods.GalacticraftCore;
import static gregtech.api.enums.Mods.GalacticraftMars;
import static gregtech.api.enums.Mods.GalaxySpace;
import static gregtech.api.enums.Mods.StevesCarts2;
import static gregtech.api.enums.Mods.Translocator;
import static gregtech.api.recipe.RecipeMaps.implosionRecipes;
import static gregtech.api.util.GT_RecipeBuilder.TICKS;
import static gregtech.api.util.GT_RecipeConstants.ADDITIVE_AMOUNT;

import com.dreammaster.gthandler.CustomItemList;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

public class ImplosionCompressorRecipes implements Runnable {

    @Override
    public void run() {
        if (GalaxySpace.isModLoaded()) {

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Aluminium, 2L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualAluminium", 1L, 0),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Bronze, 2L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualBronze", 1L, 0),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(CustomItemList.RawSDHCAlloy.get(1L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.StainlessSteel, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 5).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Coal, 1L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedCoal", 1L, 0),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.BlackPlutonium, 1L))
                    .itemOutputs(
                            CustomItemList.BlackPlutoniumCompressedPlate.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Adamantium, 1L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 0),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Cobalt, 1L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 1),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Duralumin, 1L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 2),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Lead, 1L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 3),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Magnesium, 1L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 4),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Mithril, 1L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 5),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Nickel, 1L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 6),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Oriharukon, 1L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 7),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Platinum, 1L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 8),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Tungsten, 1L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedPlates", 1L, 9),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

        }
        if (GalacticraftMars.isModLoaded()) {

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 2L, 6))
                    .itemOutputs(
                            CustomItemList.TitaniumDualCompressedPlates.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(GalacticraftMars.ID, "item.null", 2L, 5))
                    .itemOutputs(
                            CustomItemList.DeshDualCompressedPlates.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

        }

        GT_Values.RA.stdBuilder().itemInputs(CustomItemList.IceCompressedPlate.get(2L))
                .itemOutputs(
                        CustomItemList.IceDualCompressedPlates.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

        GT_Values.RA.stdBuilder().itemInputs(CustomItemList.QuantinumCompressedPlate.get(2L))
                .itemOutputs(
                        CustomItemList.QuantinumDualCompressedPlates.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Ledox, 1L))
                .itemOutputs(
                        CustomItemList.LedoxCompressedPlate.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Mytryl, 1L))
                .itemOutputs(
                        CustomItemList.MytrylCompressedPlate.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.Quantium, 1L))
                .itemOutputs(
                        CustomItemList.QuantinumCompressedPlate.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.CallistoIce, 1L))
                .itemOutputs(
                        CustomItemList.CallistoIceCompressedPlate.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

        GT_Values.RA.stdBuilder().itemInputs(CustomItemList.MytrylCompressedPlate.get(2L))
                .itemOutputs(
                        CustomItemList.MytrylDualCompressedPlates.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

        GT_Values.RA.stdBuilder().itemInputs(CustomItemList.MysteriousCrystalCompressedPlate.get(2L))
                .itemOutputs(
                        CustomItemList.MysteriousCrystalDualCompressedPlates.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

        if (GalacticraftCore.isModLoaded()) {

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(GalacticraftCore.ID, "item.basicItem", 2L, 9))
                    .itemOutputs(
                            CustomItemList.SteelDualCompressedPlates.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(GalacticraftCore.ID, "item.basicItem", 2L, 7))
                    .itemOutputs(
                            CustomItemList.TinDualCompressedPlates.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(GalacticraftCore.ID, "item.basicItem", 2L, 6))
                    .itemOutputs(
                            CustomItemList.CopperDualCompressedPlates.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(GalacticraftCore.ID, "item.basicItem", 2L, 11))
                    .itemOutputs(
                            CustomItemList.IronDualCompressedPlates.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(GalacticraftCore.ID, "item.meteoricIronIngot", 2L, 1))
                    .itemOutputs(
                            CustomItemList.MeteoricIronDualCompressedPlates.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

        }
        if (!BartWorks.isModLoaded()) {

            GT_Values.RA.stdBuilder().itemInputs(CustomItemList.HeavyDutyAlloyIngotT4.get(1L))
                    .itemOutputs(
                            CustomItemList.HeavyDutyPlateTier4.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Osmiridium, 4L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 32).addTo(implosionRecipes);

        }

        GT_Values.RA.stdBuilder().itemInputs(CustomItemList.HeavyDutyAlloyIngotT5.get(1L))
                .itemOutputs(
                        CustomItemList.HeavyDutyPlateTier5.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Europium, 5L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 40).addTo(implosionRecipes);

        GT_Values.RA.stdBuilder().itemInputs(CustomItemList.HeavyDutyAlloyIngotT6.get(1L))
                .itemOutputs(
                        CustomItemList.HeavyDutyPlateTier6.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Tritanium, 6L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 48).addTo(implosionRecipes);

        GT_Values.RA.stdBuilder().itemInputs(CustomItemList.HeavyDutyAlloyIngotT7.get(1L))
                .itemOutputs(
                        CustomItemList.HeavyDutyPlateTier7.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Neutronium, 7L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 56).addTo(implosionRecipes);

        GT_Values.RA.stdBuilder().itemInputs(CustomItemList.HeavyDutyAlloyIngotT8.get(1L))
                .itemOutputs(
                        CustomItemList.HeavyDutyPlateTier8.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.BlackPlutonium, 8L))
                .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 64).addTo(implosionRecipes);

        // Avaritia recipes
        if (Avaritia.isModLoaded()) {

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(Avaritia.ID, "Resource", 9L, 2))
                    .itemOutputs(GT_ModHandler.getModItem(Avaritia.ID, "Resource", 1L, 3)).duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(Avaritia.ID, "Resource", 9L, 3))
                    .itemOutputs(GT_ModHandler.getModItem(Avaritia.ID, "Resource", 1L, 4)).duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 4).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(Avaritia.ID, "Resource", 9L, 4))
                    .itemOutputs(GT_ModHandler.getModItem(Avaritia.ID, "Resource_Block", 1L, 0)).duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 16).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(Avaritia.ID, "Resource", 9L, 6))
                    .itemOutputs(GT_ModHandler.getModItem(Avaritia.ID, "Resource_Block", 1L, 1)).duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 64).addTo(implosionRecipes);

        }
        // extra utils
        if (ExtraUtilities.isModLoaded()) {
            // CC

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 9L, 0))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1L, 1))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 9L, 1))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1L, 2))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 9L, 2))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1L, 3))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 9L, 3))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1L, 4))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 9L, 4))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1L, 5))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 8).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 9L, 5))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1L, 6))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 10).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 9L, 6))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1L, 7))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 16).addTo(implosionRecipes);
            // CD

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 9L, 8))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1L, 9))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 9L, 9))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1L, 10))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 9L, 10))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1L, 11))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 4).addTo(implosionRecipes);
            // CG

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 9L, 12))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1L, 13))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);
            // CS

            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 9L, 14))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "cobblestone_compressed", 1L, 15))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "bedrockiumIngot", 9))
                    .itemOutputs(GT_ModHandler.getModItem(ExtraUtilities.ID, "block_bedrockium", 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 8).addTo(implosionRecipes);

        }

        if (StevesCarts2.isModLoaded()) {

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(StevesCarts2.ID, "ModuleComponents", 1L, 18))
                    .itemOutputs(GT_ModHandler.getModItem(StevesCarts2.ID, "ModuleComponents", 1L, 19))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 1).addTo(implosionRecipes);

        }

        if (Translocator.isModLoaded()) {

            GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Diamond, 1L))
                    .itemOutputs(
                            GT_ModHandler.getModItem(Translocator.ID, "diamondNugget", 2L, 0),
                            GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 1L))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

        }

        if (DraconicEvolution.isModLoaded()) {

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(DraconicEvolution.ID, "chaosFragment", 9L, 2))
                    .itemOutputs(GT_ModHandler.getModItem(DraconicEvolution.ID, "chaosShard", 1L, 0))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 8).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(DraconicEvolution.ID, "chaosFragment", 9L, 1))
                    .itemOutputs(GT_ModHandler.getModItem(DraconicEvolution.ID, "chaosFragment", 1L, 2))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 4).addTo(implosionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(DraconicEvolution.ID, "chaosFragment", 9L, 0))
                    .itemOutputs(GT_ModHandler.getModItem(DraconicEvolution.ID, "chaosFragment", 1L, 1))
                    .duration(20 * TICKS).eut(TierEU.RECIPE_LV).metadata(ADDITIVE_AMOUNT, 2).addTo(implosionRecipes);

        }
    }
}
