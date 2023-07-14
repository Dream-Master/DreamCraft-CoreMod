package com.dreammaster.gthandler.recipes;

import static gregtech.api.enums.Mods.AdvancedSolarPanel;
import static gregtech.api.enums.Mods.ExtraTrees;
import static gregtech.api.enums.Mods.ExtraUtilities;
import static gregtech.api.enums.Mods.GTPlusPlus;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.enums.Mods.Minecraft;
import static gregtech.api.enums.Mods.Natura;
import static gregtech.api.enums.Mods.OpenComputers;
import static gregtech.api.enums.Mods.Railcraft;
import static gregtech.api.enums.Mods.StevesCarts2;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.enums.Mods.TinkerConstruct;
import static gregtech.api.util.GT_ModHandler.getModItem;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sCompressorRecipes;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.dreammaster.block.BlockList;
import com.dreammaster.gthandler.CustomItemList;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_OreDictUnificator;

public class CompressorRecipes implements Runnable {

    ItemStack missing = new ItemStack(Blocks.fire);

    @Override
    public void run() {
        makeAdvancedSolarPanelRecipes();
        makeExtraUtilitiesRecipes();
        makeGTPlusPlusRecipes();
        makeTinkerConstructRecipes();
        makeThaumcraftRecipes();

        // custom dust to plate compression
        Materials[] dustToPlateList = new Materials[] { Materials.CertusQuartz, Materials.NetherQuartz,
                Materials.Quartzite, Materials.Lazurite, Materials.Sodalite, Materials.CertusQuartzCharged };
        for (Materials material : dustToPlateList) {
            GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.dust, material, 1L))
                    .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.plate, material, 1L)).noFluidInputs()
                    .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        }

        // custom dust to ingot compression
        Materials[] dustToIngotList = new Materials[] { Materials.Carbon, Materials.Ledox };
        for (Materials material : dustToIngotList) {
            GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.dust, material, 1L))
                    .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.ingot, material, 1L)).noFluidInputs()
                    .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        }

        // custom ingot to block compression
        Materials[] ingotToBlockList = new Materials[] { Materials.Cobalt, Materials.Ardite, Materials.Manyullyn,
                Materials.Alumite };
        for (Materials material : ingotToBlockList) {
            GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.ingot, material, 9L))
                    .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.block, material, 1L)).noFluidInputs()
                    .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        }

        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.WroughtIron, 9L))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.WroughtIron, 1L)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);

        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.NetherStar, 9))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.block, Materials.NetherStar, 1)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(TierEU.RECIPE_UV).addTo(sCompressorRecipes);

        // compressed coal variants
        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Charcoal, 9))
                .itemOutputs(BlockList.CompressedCharcoal.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Coal, 9))
                .itemOutputs(BlockList.CompressedCoal.getIS(1)).noFluidInputs().noFluidOutputs().duration(15 * SECONDS)
                .eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem(Railcraft.ID, "cube", 9, 0, missing))
                .itemOutputs(BlockList.CompressedCoalCoke.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);

        GT_Values.RA.stdBuilder().itemInputs(BlockList.CompressedCharcoal.getIS(9))
                .itemOutputs(BlockList.DoubleCompressedCharcoal.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(BlockList.CompressedCoal.getIS(9))
                .itemOutputs(BlockList.DoubleCompressedCoal.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(BlockList.CompressedCoalCoke.getIS(9))
                .itemOutputs(BlockList.DoubleCompressedCoalCoke.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);

        GT_Values.RA.stdBuilder().itemInputs(BlockList.DoubleCompressedCharcoal.getIS(9))
                .itemOutputs(BlockList.TripleCompressedCharcoal.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(BlockList.DoubleCompressedCoal.getIS(9))
                .itemOutputs(BlockList.TripleCompressedCoal.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(BlockList.DoubleCompressedCoalCoke.getIS(9))
                .itemOutputs(BlockList.TripleCompressedCoalCoke.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);

        GT_Values.RA.stdBuilder().itemInputs(BlockList.TripleCompressedCharcoal.getIS(9))
                .itemOutputs(BlockList.QuadrupleCompressedCharcoal.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(BlockList.TripleCompressedCoal.getIS(9))
                .itemOutputs(BlockList.QuadrupleCompressedCoal.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(BlockList.TripleCompressedCoalCoke.getIS(9))
                .itemOutputs(BlockList.QuadrupleCompressedCoalCoke.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);

        GT_Values.RA.stdBuilder().itemInputs(BlockList.QuadrupleCompressedCharcoal.getIS(9))
                .itemOutputs(BlockList.QuintupleCompressedCharcoal.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(BlockList.QuadrupleCompressedCoal.getIS(9))
                .itemOutputs(BlockList.QuintupleCompressedCoal.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(BlockList.QuadrupleCompressedCoalCoke.getIS(9))
                .itemOutputs(BlockList.QuintupleCompressedCoalCoke.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);

        GT_Values.RA.stdBuilder().itemInputs(CustomItemList.BioBall.get(1L))
                .itemOutputs(com.dreammaster.item.ItemList.CompressedBioBall.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(com.dreammaster.item.ItemList.BioOrganicMesh.getIS(1))
                .itemOutputs(com.dreammaster.item.ItemList.BioCarbonPlate.getIS(1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);

        if (Natura.isModLoaded()) {
            GT_Values.RA.stdBuilder().itemInputs(getModItem(Natura.ID, "barleyFood", 8, 0))
                    .itemOutputs(ItemList.IC2_Plantball.get(1)).noFluidInputs().noFluidOutputs().duration(15 * SECONDS)
                    .eut(2).addTo(sCompressorRecipes);
        }

        if (StevesCarts2.isModLoaded()) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.EnhancedGalgadorian, 9L))
                    .itemOutputs(getModItem(StevesCarts2.ID, "ModuleComponents", 1L, 48)).noFluidInputs()
                    .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        }

        if (OpenComputers.isModLoaded()) {
            // Block of Chamelium
            GT_Values.RA.stdBuilder().itemInputs(getModItem(OpenComputers.ID, "item", 9L, 96))
                    .itemOutputs(getModItem(OpenComputers.ID, "chameliumBlock", 1L, 0)).noFluidInputs().noFluidOutputs()
                    .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        }

        if (IndustrialCraft2.isModLoaded()) {
            GT_Values.RA.stdBuilder().itemInputs(getModItem(IndustrialCraft2.ID, "itemWeed", 16L))
                    .itemOutputs(ItemList.IC2_Plantball.get(1L)).noFluidInputs().noFluidOutputs().duration(15 * SECONDS)
                    .eut(2).addTo(sCompressorRecipes);
        }

        if (ExtraTrees.isModLoaded()) {
            GT_Values.RA.stdBuilder().itemInputs(getModItem(ExtraTrees.ID, "food", 64L, 24))
                    .itemOutputs(ItemList.IC2_Plantball.get(1L)).noFluidInputs().noFluidOutputs().duration(15 * SECONDS)
                    .eut(2).addTo(sCompressorRecipes);
        }
    }

    private void makeAdvancedSolarPanelRecipes() {
        if (!AdvancedSolarPanel.isModLoaded()) {
            return;
        }
        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sunnarium, 1L))
                .itemOutputs(getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 1L, 0)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Sunnarium, 1L))
                .itemOutputs(getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 1L, 9)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 9L, 9))
                .itemOutputs(getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 1L, 0)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
    }

    private void makeExtraUtilitiesRecipes() {
        if (!ExtraUtilities.isModLoaded()) {
            return;
        }
        GT_Values.RA.stdBuilder().itemInputs(getModItem(ExtraUtilities.ID, "unstableingot", 9, 2, missing))
                .itemOutputs(getModItem(ExtraUtilities.ID, "decorativeBlock1", 1, 5, missing)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem(ExtraUtilities.ID, "unstableingot", 9, 0, missing))
                .itemOutputs(getModItem(ExtraUtilities.ID, "decorativeBlock1", 1, 5, missing)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
    }

    private void makeGTPlusPlusRecipes() {
        if (!GTPlusPlus.isModLoaded()) {
            return;
        }
        // Compressed Glowstone
        GT_Values.RA.stdBuilder().itemInputs(new ItemStack(Blocks.glowstone, 9))
                .itemOutputs(getModItem(GTPlusPlus.ID, "blockCompressedObsidian", 1L, 6)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);

        // Double Compressed Glowstone
        GT_Values.RA.stdBuilder().itemInputs(getModItem(GTPlusPlus.ID, "blockCompressedObsidian", 9L, 6))
                .itemOutputs(getModItem(GTPlusPlus.ID, "blockCompressedObsidian", 1L, 7)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
    }

    private void makeTinkerConstructRecipes() {
        if (!TinkerConstruct.isModLoaded()) {
            return;
        }
        GT_Values.RA.stdBuilder().itemInputs(getModItem(TinkerConstruct.ID, "materials", 9L, 14))
                .itemOutputs(getModItem(TinkerConstruct.ID, "MetalBlock", 1L, 7)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem(TinkerConstruct.ID, "materials", 4L, 2))
                .itemOutputs(getModItem(TinkerConstruct.ID, "Smeltery", 1L, 2)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 1L))
                .itemOutputs(getModItem(TinkerConstruct.ID, "materials", 1L, 12)).noFluidInputs().noFluidOutputs()
                .duration(5 * SECONDS).eut(2).addTo(sCompressorRecipes);

        // Slime crystals
        GT_Values.RA.stdBuilder().itemInputs(getModItem(TinkerConstruct.ID, "CraftedSoil", 4L, 0))
                .itemOutputs(getModItem(TinkerConstruct.ID, "materials", 1L, 1)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem(TinkerConstruct.ID, "CraftedSoil", 4L, 2))
                .itemOutputs(getModItem(TinkerConstruct.ID, "materials", 1L, 17)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);

        GT_Values.RA.stdBuilder().itemInputs(new ItemStack(Items.paper, 64, 0))
                .itemOutputs(getModItem(TinkerConstruct.ID, "materials", 1L, 0)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);

        GT_Values.RA.stdBuilder().itemInputs(getModItem(TinkerConstruct.ID, "strangeFood", 4, 0, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "slime.gel", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem(Minecraft.ID, "slime_ball", 4, 0, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "slime.gel", 1, 1, missing)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
    }

    private void makeThaumcraftRecipes() {
        if (!Thaumcraft.isModLoaded()) {
            return;
        }
        // Arcane Slabs -> Arcane Stone
        GT_Values.RA.stdBuilder().itemInputs(getModItem(Thaumcraft.ID, "blockCosmeticSlabStone", 4L))
                .itemOutputs(getModItem(Thaumcraft.ID, "blockCosmeticSolid", 1L, 6)).noFluidInputs().noFluidOutputs()
                .duration(8 * SECONDS).eut(4).addTo(sCompressorRecipes);

        GT_Values.RA.stdBuilder().itemInputs(getModItem(Thaumcraft.ID, "ItemResource", 9, 4, missing))
                .itemOutputs(getModItem(Thaumcraft.ID, "blockCosmeticSolid", 1, 5, missing)).noFluidInputs()
                .noFluidOutputs().duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem(Minecraft.ID, "rotten_flesh", 9, 0, missing))
                .itemOutputs(getModItem(Thaumcraft.ID, "blockTaint", 1, 2, missing)).noFluidInputs().noFluidOutputs()
                .duration(15 * SECONDS).eut(2).addTo(sCompressorRecipes);
    }
}
