package com.dreammaster.gthandler.recipes;

import static gregtech.api.enums.Mods.BiomesOPlenty;
import static gregtech.api.enums.Mods.GalaxySpace;
import static gregtech.api.enums.Mods.Natura;
import static gregtech.api.enums.Mods.PamsHarvestCraft;
import static gregtech.api.enums.Mods.SGCraft;
import static gregtech.api.enums.Mods.TinkerConstruct;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

public class ExtractorRecipes implements Runnable {

    @Override
    public void run() {

        if (GalaxySpace.isModLoaded() && SGCraft.isModLoaded()) {
            for (int i = 0; i < 6; ++i) {
                GT_Values.RA.addExtractorRecipe(
                        GT_ModHandler.getModItem(GalaxySpace.ID, "tcetiedandelions", 64L, i),
                        com.dreammaster.item.ItemList.TCetiESeaweedExtract.getIS(),
                        3600,
                        262144);
            }
        }

        if (TinkerConstruct.isModLoaded()) {
            GT_ModHandler.addExtractionRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "Smeltery", 1L, 2),
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "materials", 4L, 2));

            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "slime.sapling", 1L, 0),
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1L, 0),
                    300,
                    2);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "slime.gel", 1L, 0),
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 4L, 0),
                    300,
                    2);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "slime.gel", 1L, 1),
                    new ItemStack(Items.slime_ball, 4, 0),
                    300,
                    2);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "slime.leaves", 16L, 0),
                    GT_OreDictUnificator.get(OrePrefixes.dust, Materials.RawRubber, 1L),
                    300,
                    2);
        }

        if (BiomesOPlenty.isModLoaded()) {
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(BiomesOPlenty.ID, "flowers", 2L, 3),
                    new ItemStack(Items.glowstone_dust, 1, 0),
                    300,
                    2);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(BiomesOPlenty.ID, "mushrooms", 2L, 3),
                    new ItemStack(Items.glowstone_dust, 1, 0),
                    300,
                    2);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(BiomesOPlenty.ID, "coral1", 2L, 15),
                    new ItemStack(Items.glowstone_dust, 1, 0),
                    300,
                    2);

            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(BiomesOPlenty.ID, "flowers", 2L, 13),
                    new ItemStack(Items.spider_eye, 1, 0),
                    300,
                    2);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(BiomesOPlenty.ID, "mudball", 1L, 0),
                    new ItemStack(Items.clay_ball, 1, 0),
                    200,
                    2);
        }

        if (PamsHarvestCraft.isModLoaded()) {
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(PamsHarvestCraft.ID, "pamMaple", 1L, 0),
                    GT_ModHandler.getModItem(PamsHarvestCraft.ID, "maplesyrupItem", 1L, 0),
                    200,
                    2);
        }

        if (Natura.isModLoaded()) {
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "florasapling", 1L, 5),
                    GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L),
                    100,
                    120);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "floraleavesnocolor", 4L, 2),
                    GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L),
                    100,
                    120);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "bloodwood", 1L, 0),
                    GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L),
                    100,
                    120);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "bloodwood", 1L, 15),
                    GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L),
                    100,
                    120);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "florasapling", 2L, 7),
                    GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gunpowder, 1L),
                    100,
                    120);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "Dark Leaves", 8L, 3),
                    GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gunpowder, 1L),
                    100,
                    120);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "Dark Tree", 2L, 1),
                    GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gunpowder, 1L),
                    100,
                    120);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "florasapling", 2L, 6),
                    ItemList.IC2_Fertilizer.get(1),
                    100,
                    120);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "Dark Leaves", 8L, 0),
                    ItemList.IC2_Fertilizer.get(1),
                    100,
                    120);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "Dark Tree", 2L, 0),
                    ItemList.IC2_Fertilizer.get(1),
                    100,
                    120);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "Natura.netherfood", 1L, 0),
                    ItemList.IC2_Fertilizer.get(4),
                    100,
                    120);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "florasapling", 2L, 4),
                    GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bone, 1L),
                    100,
                    120);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "floraleavesnocolor", 4L, 1),
                    GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bone, 1L),
                    100,
                    120);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "tree", 2L, 2),
                    GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bone, 1L),
                    100,
                    120);

            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "Glowshroom", 2L, 0),
                    new ItemStack(Items.glowstone_dust, 1, 0),
                    300,
                    2);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "Glowshroom", 2L, 1),
                    new ItemStack(Items.glowstone_dust, 1, 0),
                    300,
                    2);
            GT_Values.RA.addExtractorRecipe(
                    GT_ModHandler.getModItem(Natura.ID, "Glowshroom", 2L, 2),
                    new ItemStack(Items.glowstone_dust, 1, 0),
                    300,
                    2);
        }

    }
}
