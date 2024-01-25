package com.dreammaster.gthandler.recipes;

import static gregtech.api.enums.Mods.*;
import static gregtech.api.recipe.RecipeMaps.autoclaveRecipes;
import static gregtech.api.util.GT_RecipeBuilder.MINUTES;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import com.dreammaster.gthandler.CustomItemList;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.core.material.ELEMENT;

public class AutoclaveRecipes implements Runnable {

    @Override
    public void run() {
        spaceRecipes();

        GT_Values.RA.stdBuilder().itemInputs(CustomItemList.LapotronDust.get(30L))
                .itemOutputs(CustomItemList.RawLapotronCrystal.get(1L)).outputChances(10000)
                .fluidInputs(Materials.EnergeticAlloy.getMolten(576L)).duration(2 * MINUTES).eut(TierEU.RECIPE_HV)
                .addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder().itemInputs(CustomItemList.LapotronDust.get(30L))
                .itemOutputs(CustomItemList.RawLapotronCrystal.get(1L)).outputChances(10000)
                .fluidInputs(Materials.VibrantAlloy.getMolten(288L)).duration(60 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(EnderZoo.ID, "enderFragment", 4L, 0),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(new ItemStack(Items.ender_pearl, 1, 0)).outputChances(8000)
                .fluidInputs(Materials.Water.getFluid(100)).duration(60 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(EnderZoo.ID, "enderFragment", 4L, 0),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(new ItemStack(Items.ender_pearl, 1, 0)).outputChances(9000)
                .fluidInputs(GT_ModHandler.getDistilledWater(50L)).duration(45 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_ModHandler.getModItem(EnderZoo.ID, "enderFragment", 4L, 0),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(new ItemStack(Items.ender_pearl, 1, 0)).outputChances(10000)
                .fluidInputs(Materials.Void.getMolten(18)).duration(30 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.EnderPearl, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(new ItemStack(Items.ender_pearl, 1, 0)).outputChances(8000)
                .fluidInputs(Materials.Water.getFluid(100)).duration(1 * MINUTES + 15 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.EnderPearl, 1L),
                        GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(new ItemStack(Items.ender_pearl, 1, 0)).outputChances(9000)
                .fluidInputs(GT_ModHandler.getDistilledWater(75L)).duration(60 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.EnderPearl, 1L),
                        GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(new ItemStack(Items.ender_pearl, 1, 0)).outputChances(10000)
                .fluidInputs(Materials.Void.getMolten(36)).duration(45 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder().itemInputs(ItemList.Circuit_Parts_RawCrystalParts.get(1L))
                .itemOutputs(ItemList.Circuit_Parts_RawCrystalChip.get(1L)).outputChances(10000)
                .fluidInputs(Materials.Europium.getMolten(16)).duration(10 * MINUTES).eut(TierEU.RECIPE_HV)
                .addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.GalliumArsenide, 2))
                .itemOutputs(ItemList.GalliumArsenideCrystal.get(1L)).outputChances(8000)
                .fluidInputs(Materials.Water.getFluid(200L)).duration(20 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.GalliumArsenide, 2))
                .itemOutputs(ItemList.GalliumArsenideCrystal.get(1L)).outputChances(10000)
                .fluidInputs(Materials.Void.getMolten(36L)).duration(20 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.GalliumArsenide, 2))
                .itemOutputs(ItemList.GalliumArsenideCrystal.get(1L)).outputChances(9000)
                .fluidInputs(GT_ModHandler.getDistilledWater(100L)).duration(20 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(autoclaveRecipes);

        if (GalacticraftMars.isModLoaded()) {

            GT_Values.RA.stdBuilder().itemInputs(ItemList.Circuit_Parts_RawCrystalParts.get(1L))
                    .itemOutputs(ItemList.Circuit_Parts_RawCrystalChip.get(1L)).outputChances(6000)
                    .fluidInputs(FluidRegistry.getFluidStack("bacterialsludge", 250)).duration(10 * MINUTES)
                    .eut(TierEU.RECIPE_HV).addTo(autoclaveRecipes);

        }
        if (Gendustry.isModLoaded()) {
            GT_Values.RA.stdBuilder().itemInputs(ItemList.Circuit_Parts_RawCrystalParts.get(1L))
                    .itemOutputs(ItemList.Circuit_Parts_RawCrystalChip.get(1L)).outputChances(8000)
                    .fluidInputs(FluidRegistry.getFluidStack("mutagen", 250)).duration(10 * MINUTES)
                    .eut(TierEU.RECIPE_HV).addTo(autoclaveRecipes);

        }
        if (GTPlusPlus.isModLoaded()) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(ItemList.Circuit_Silicon_Wafer6.get(1L), ELEMENT.STANDALONE.HYPOGEN.getDust(1))
                    .itemOutputs(ItemList.Circuit_Wafer_Bioware.get(1L)).outputChances(10000)
                    .fluidInputs(Materials.BioMediumSterilized.getFluid(8_000L)).duration(60 * SECONDS)
                    .eut(TierEU.RECIPE_UHV).addTo(autoclaveRecipes);
        }
    }

    public static void spaceRecipes() {

        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Emerald, 1))
                .itemOutputs(ItemList.Circuit_Parts_RawCrystalChip.get(1L)).outputChances(1000)
                .fluidInputs(Materials.Europium.getMolten(16)).requiresCleanRoom().requiresLowGravity()
                .duration(10 * MINUTES).eut(320).addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Olivine, 1))
                .itemOutputs(ItemList.Circuit_Parts_RawCrystalChip.get(1L)).outputChances(1000)
                .fluidInputs(Materials.Europium.getMolten(16)).requiresCleanRoom().requiresLowGravity()
                .duration(10 * MINUTES).eut(320).addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Emerald, 12))
                .itemOutputs(ItemList.Tool_DataOrb.get(1L)).outputChances(10000)
                .fluidInputs(Materials.UUMatter.getFluid(250L)).requiresCleanRoom().requiresLowGravity()
                .duration(10 * MINUTES).eut(TierEU.RECIPE_EV).addTo(autoclaveRecipes);

        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Olivine, 12))
                .itemOutputs(ItemList.Tool_DataOrb.get(1L)).outputChances(10000)
                .fluidInputs(Materials.UUMatter.getFluid(250L)).requiresCleanRoom().requiresLowGravity()
                .duration(10 * MINUTES).eut(TierEU.RECIPE_EV).addTo(autoclaveRecipes);

        if (SGCraft.isModLoaded()) {

            GT_Values.RA.stdBuilder()
                    .itemInputs(com.dreammaster.item.ItemList.StargateCrystalDust.getIS().splitStack(64))
                    .itemOutputs(GT_ModHandler.getModItem(SGCraft.ID, "sgCoreCrystal", 1L)).outputChances(10000)
                    .fluidInputs(Materials.Silver.getPlasma(8000L)).requiresCleanRoom().requiresLowGravity()
                    .duration(3 * MINUTES).eut(131000).addTo(autoclaveRecipes);

        }

    }
}
