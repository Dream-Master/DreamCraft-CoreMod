package com.dreammaster.bartworksHandler;

import static com.dreammaster.bartworksHandler.BacteriaRegistry.CultureSet;
import static com.github.bartimaeusnek.bartworks.API.BioRecipeAdder.CLEANROOM;
import static com.github.bartimaeusnek.bartworks.API.BioRecipeAdder.addBacterialVatRecipe;
import static gregtech.api.enums.GT_Values.*;
import static gregtech.api.enums.Mods.BartWorks;
import static gregtech.api.enums.Mods.PamsHarvestCraft;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sFluidExtractionRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sMixerRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sMultiblockChemicalRecipes;
import static gregtech.api.util.GT_RecipeBuilder.MINUTES;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_RecipeConstants.UniversalChemical;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.github.bartimaeusnek.bartworks.MainMod;
import com.github.bartimaeusnek.bartworks.common.items.SimpleSubItemClass;
import com.github.bartimaeusnek.bartworks.util.BW_Util;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TierEU;
import gregtech.api.objects.GT_Fluid;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;

public class BioItemLoader {

    private static final Item BIOTEMS;
    private static final GT_Fluid[] BIOFLUIDS;
    private static ItemStack[] BIOTEMSSTACKS;

    static {
        BIOTEMS = new SimpleSubItemClass(new String[] { "itemCollagen", "itemGelatin", "itemAgar" })
                .setCreativeTab(MainMod.BIO_TAB);
        GameRegistry.registerItem(BIOTEMS, "GTNHBioItems", BartWorks.ID);
        BIOTEMSSTACKS = new ItemStack[] { new ItemStack(BIOTEMS, 1, 0), new ItemStack(BIOTEMS, 1, 1),
                new ItemStack(BIOTEMS, 1, 2), };
        BIOFLUIDS = new GT_Fluid[] {
                new GT_Fluid("GelatinMixture", "molten.autogenerated", new short[] { 255, 255, 125 }),
                new GT_Fluid("MeatExtract", "molten.autogenerated", new short[] { 160, 70, 50 }),
                new GT_Fluid("UnknownNutrientAgar", "molten.autogenerated", new short[] { 175, 133, 0 }),
                new GT_Fluid("SeaweedBroth", "molten.autogenerated", new short[] { 60, 200, 0 }) };

        for (GT_Fluid gtFluid : BIOFLUIDS) {
            FluidRegistry.registerFluid(gtFluid);
        }
        BIOTEMSSTACKS[0].stackSize = 2;

        GT_Values.RA.stdBuilder().itemInputs(Materials.MeatRaw.getDust(2), new ItemStack(Items.bone, 1))
                .itemOutputs(BIOTEMSSTACKS[0]).fluidInputs(Materials.DilutedSulfuricAcid.getFluid(1000))
                .fluidOutputs(Materials.Water.getFluid(1000)).duration(1 * MINUTES + 20 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(UniversalChemical);

        BIOTEMSSTACKS[0].stackSize = 1;

        GT_Values.RA.stdBuilder().itemInputs(Materials.MeatRaw.getDust(1), Materials.Bone.getDust(2))
                .itemOutputs(BIOTEMSSTACKS[0]).fluidInputs(Materials.DilutedSulfuricAcid.getFluid(500))
                .fluidOutputs(Materials.Water.getFluid(500)).duration(40 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(UniversalChemical);

        BIOTEMSSTACKS[0].stackSize = 4;

        GT_Values.RA.stdBuilder().itemInputs(BIOTEMSSTACKS[0], Materials.Water.getCells(3))
                .itemOutputs(Materials.Empty.getCells(3)).fluidInputs(Materials.PhosphoricAcid.getFluid(1000))
                .fluidOutputs(new FluidStack(BIOFLUIDS[0], 4000)).duration(1 * MINUTES + 20 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(UniversalChemical);

        GT_Values.RA.stdBuilder().itemInputs(BIOTEMSSTACKS[0], Materials.PhosphoricAcid.getCells(1))
                .itemOutputs(Materials.Empty.getCells(1)).fluidInputs(Materials.Water.getFluid(3000))
                .fluidOutputs(new FluidStack(BIOFLUIDS[0], 4000)).duration(1 * MINUTES + 20 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(UniversalChemical);

        BIOTEMSSTACKS[0].stackSize = 1;
        BIOTEMSSTACKS[1].stackSize = 4;

        GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(Materials.Phosphorus.getDust(1), BIOTEMSSTACKS[1])
                .fluidInputs(new FluidStack(BIOFLUIDS[0], 6000)).noFluidOutputs().duration(2 * MINUTES)
                .eut(TierEU.RECIPE_HV).addTo(sCentrifugeRecipes);

        BIOTEMSSTACKS[1].stackSize = 1;

        RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(11), BIOTEMSSTACKS[1]).itemOutputs(BIOTEMSSTACKS[2])
                .fluidInputs(GT_ModHandler.getDistilledWater(1000)).noFluidOutputs().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(sMixerRecipes);

        GT_Values.RA.stdBuilder().itemInputs(Materials.MeatRaw.getDust(1)).noItemOutputs().noFluidInputs()
                .fluidOutputs(new FluidStack(BIOFLUIDS[1], 125)).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(sFluidExtractionRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        BIOTEMSSTACKS[2].copy().splitStack(8),
                        ItemList.Circuit_Chip_Stemcell.get(16),
                        Materials.Salt.getDust(64))
                .noItemOutputs()
                .fluidInputs(
                        FluidRegistry.getFluidStack("unknowwater", 4000),
                        Materials.PhthalicAcid.getFluid(3000),
                        new FluidStack(BIOFLUIDS[1], 1000))
                .fluidOutputs(new FluidStack(BIOFLUIDS[2], 8000)).duration(60 * SECONDS).eut(TierEU.RECIPE_UV)
                .addTo(sMultiblockChemicalRecipes);

        addBacterialVatRecipe(
                new ItemStack[] { ItemList.IC2_Energium_Dust.get(8), Materials.Mytryl.getDust(1),
                        GT_ModHandler.getModItem(PamsHarvestCraft.ID, "seaweedItem", 64) },
                CultureSet.get("TcetiEBac"),
                new FluidStack[] { new FluidStack(BIOFLUIDS[2], 50) },
                new FluidStack[] { new FluidStack(BIOFLUIDS[3], 50) },
                1200,
                BW_Util.getMachineVoltageFromTier(8),
                100,
                8,
                0,
                false);
        for (int i = 0; i < OreDictionary.getOres("cropTcetiESeaweed").size(); i++) {
            GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(i + 1))
                    .itemOutputs(OreDictionary.getOres("cropTcetiESeaweed").get(i).copy().splitStack(64))
                    .fluidInputs(new FluidStack(BIOFLUIDS[3], 1000)).noFluidOutputs().duration(2 * SECONDS)
                    .eut(TierEU.RECIPE_UV).addTo(sCentrifugeRecipes);
        }
        addBacterialVatRecipe(
                new ItemStack[] { Materials.MeatRaw.getDust(4), Materials.Salt.getDust(4), Materials.Calcium.getDust(4),
                        BIOTEMSSTACKS[2].copy().splitStack(4), },
                CultureSet.get("OvumBac"),
                new FluidStack[] { FluidRegistry.getFluidStack("binnie.bacteria", 4) },
                new FluidStack[] { Materials.GrowthMediumRaw.getFluid(1) },
                1200,
                7680,
                Materials.Uranium,
                5,
                CLEANROOM,
                false);
        addBacterialVatRecipe(
                new ItemStack[] { Materials.MeatRaw.getDust(8), Materials.Salt.getDust(8), Materials.Calcium.getDust(8),
                        BIOTEMSSTACKS[2].copy().splitStack(4), },
                CultureSet.get("OvumBac"),
                new FluidStack[] { FluidRegistry.getFluidStack("bacterialsludge", 4) },
                new FluidStack[] { Materials.GrowthMediumRaw.getFluid(2) },
                1200,
                30720,
                Materials.Plutonium,
                6,
                CLEANROOM,
                false);
        addBacterialVatRecipe(
                new ItemStack[] { Materials.MeatRaw.getDust(12), Materials.Salt.getDust(12),
                        Materials.Calcium.getDust(12), BIOTEMSSTACKS[2].copy().splitStack(4), },
                CultureSet.get("OvumBac"),
                new FluidStack[] { FluidRegistry.getFluidStack("mutagen", 4) },
                new FluidStack[] { Materials.GrowthMediumRaw.getFluid(4) },
                1200,
                122880,
                Materials.NaquadahEnriched,
                7,
                CLEANROOM,
                true);
    }
}
