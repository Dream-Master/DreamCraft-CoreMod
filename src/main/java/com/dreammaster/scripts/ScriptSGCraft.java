package com.dreammaster.scripts;

import static gregtech.api.enums.Mods.BartWorks;
import static gregtech.api.enums.Mods.EnderIO;
import static gregtech.api.enums.Mods.EternalSingularity;
import static gregtech.api.enums.Mods.GTPlusPlus;
import static gregtech.api.enums.Mods.GoodGenerator;
import static gregtech.api.enums.Mods.SGCraft;
import static gregtech.api.enums.Mods.TecTech;
import static gregtech.api.util.GT_ModHandler.getModItem;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.dreammaster.gthandler.CustomItemList;
import com.github.technus.tectech.recipe.TT_recipeAdder;

import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsBotania;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_OreDictUnificator;

public class ScriptSGCraft implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Stargate Craft";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(SGCraft.ID, BartWorks.ID, EnderIO.ID, GoodGenerator.ID, TecTech.ID);
    }

    @Override
    public void loadRecipes() {

        final int baseStargateTime = 125_000 * 20 * 16;

        // Stargate shield foil
        TT_recipeAdder.addResearchableAssemblylineRecipe(
                GT_OreDictUnificator.get(OrePrefixes.plate, MaterialsUEVplus.MagMatter, 1L),
                (int) TierEU.RECIPE_MAX,
                32768,
                (int) TierEU.RECIPE_MAX,
                64,
                new ItemStack[] { ItemList.Casing_Dim_Bridge.get(64),
                        CustomItemList.StabilisationFieldGeneratorTier10.get(64),
                        GT_OreDictUnificator.get("blockTerrasteel", 64L),
                        GT_OreDictUnificator.get("blockTerrasteel", 64L),
                        GT_OreDictUnificator.get(OrePrefixes.block, MaterialsUEVplus.MagMatter, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Quantum, 16L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, MaterialsUEVplus.MagMatter, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, MaterialsUEVplus.Universium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, MaterialsBotania.Terrasteel, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, MaterialsBotania.GaiaSpirit, 8L),
                        ItemList.Sensor_MAX.get(32L), ItemList.Emitter_MAX.get(16L),
                        getModItem(EternalSingularity.ID, "eternal_singularity", 16L),
                        MaterialsUEVplus.Universium.getNanite(64), MaterialsUEVplus.BlackDwarfMatter.getNanite(64),
                        MaterialsUEVplus.WhiteDwarfMatter.getNanite(64) },
                new FluidStack[] { Materials.Infinity.getMolten(32_768_000L),
                        MaterialsUEVplus.SpaceTime.getMolten(4 * 36864L),
                        Materials.SuperconductorUMVBase.getMolten(4 * 36864L),
                        MaterialsUEVplus.ExcitedDTEC.getFluid(4 * 36864L) },
                CustomItemList.StargateShieldingFoil.get(1L),
                baseStargateTime,
                (int) TierEU.RECIPE_MAX);

        // Stargate chevron
        TT_recipeAdder.addResearchableAssemblylineRecipe(
                CustomItemList.StargateShieldingFoil.get(1L),
                (int) TierEU.RECIPE_MAX,
                32_768_000,
                (int) TierEU.RECIPE_MAX,
                64,
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.block, MaterialsUEVplus.TranscendentMetal, 64L),
                        GT_OreDictUnificator.get("blockShirabon", 64),
                        com.github.technus.tectech.thing.CustomItemList.EOH_Reinforced_Spatial_Casing.get(64),
                        com.github.technus.tectech.thing.CustomItemList.EOH_Reinforced_Spatial_Casing.get(64),

                        GT_OreDictUnificator.get(OrePrefixes.frameGt, MaterialsUEVplus.MagMatter, 16L),
                        GT_OreDictUnificator.get(
                                OrePrefixes.frameGt,
                                MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter,
                                64L),
                        GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Ruby, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Jasper, 63L),

                        GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Opal, 62L),
                        GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Sapphire, 61L),
                        GT_OreDictUnificator.get(
                                OrePrefixes.plateDense,
                                MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter,
                                8L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, MaterialsBotania.GaiaSpirit, 64L),

                        ItemList.Electric_Motor_MAX.get(64L), ItemList.Electric_Piston_MAX.get(64L),
                        ItemList.Field_Generator_MAX.get(16L), CustomItemList.QuantumCircuit.get(32L) },
                new FluidStack[] { Materials.Infinity.getMolten(32_768_000L),
                        MaterialsUEVplus.SpaceTime.getMolten(4 * 36864L),
                        MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter.getMolten(36864L),
                        MaterialsUEVplus.ExcitedDTEC.getFluid(4 * 36864L) },
                CustomItemList.StargateChevron.get(1L),
                baseStargateTime,
                (int) TierEU.RECIPE_MAX);

        // Stargate Frame Part
        TT_recipeAdder.addResearchableAssemblylineRecipe(
                GT_OreDictUnificator.get(OrePrefixes.frameGt, MaterialsUEVplus.MagMatter, 1L),
                (int) TierEU.RECIPE_MAX,
                32_768,
                500_000_000,
                64,
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.stickLong, MaterialsBotania.Terrasteel, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, MaterialsUEVplus.SpaceTime, 64L),
                        GT_OreDictUnificator.get(
                                OrePrefixes.stickLong,
                                MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter,
                                64L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, MaterialsBotania.GaiaSpirit, 64L),

                        GT_OreDictUnificator.get(OrePrefixes.stickLong, MaterialsUEVplus.MagMatter, 64L),
                        GT_OreDictUnificator.get("stickLongShirabon", 64),
                        getModItem(BartWorks.ID, "gt.bwMetaGeneratedstickLong", 64L, 39),
                        getModItem(GTPlusPlus.ID, "itemRodLongQuantum", 64L),

                        getModItem(GTPlusPlus.ID, "itemRodLongHypogen", 64L),
                        getModItem(GTPlusPlus.ID, "itemRodLongCelestialTungsten", 64L),
                        getModItem(BartWorks.ID, "gt.bwMetaGeneratedstickLong", 64L, 10106),
                        getModItem(GTPlusPlus.ID, "itemRodLongAstralTitanium", 64L),

                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.SuperconductorUMVBase, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, MaterialsUEVplus.Universium, 64L),
                        getModItem(GTPlusPlus.ID, "itemRodLongAbyssalAlloy", 64L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, MaterialsUEVplus.TranscendentMetal, 64L), },
                new FluidStack[] { Materials.Infinity.getMolten(32_768_000L),
                        MaterialsUEVplus.SpaceTime.getMolten(4 * 36864L),
                        MaterialsUEVplus.Universium.getMolten(4 * 36864L),
                        MaterialsUEVplus.ExcitedDTEC.getFluid(4 * 36864L) },
                CustomItemList.StargateFramePart.get(1L),
                baseStargateTime,
                (int) TierEU.RECIPE_MAX);

        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(SGCraft.ID, "stargateRing", 1, 0, missing),
                "aaabcbbdd",
                "aaabbbdee",
                "aaabbde--",
                "fffbbde--",
                "fffbcde--",
                "fffbbde--",
                "aaabbde--",
                "aaabbbdee",
                "aaabcbbdd",
                'a',
                GT_OreDictUnificator.get(OrePrefixes.block, MaterialsUEVplus.MagMatter, 1L),
                'b',
                CustomItemList.StargateFramePart.get(1L),
                'c',
                CustomItemList.StargateChevron.get(1L),
                'd',
                CustomItemList.StargateShieldingFoil.get(1L),
                'e',
                ItemList.Field_Generator_MAX.get(1L),
                'f',
                GT_OreDictUnificator.get(OrePrefixes.block, MaterialsUEVplus.TranscendentMetal, 1L));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(SGCraft.ID, "sgChevronUpgrade", 1, 0, missing),
                "---------",
                "---aba---",
                "---cac---",
                "---dbd---",
                "---cec---",
                "---dcd---",
                "---cac---",
                "---aba---",
                "---------",
                'a',
                ItemList.Electric_Piston_MAX.get(1L),
                'b',
                CustomItemList.StargateChevron.get(1L),
                'c',
                CustomItemList.StargateFramePart.get(1L),
                'd',
                ItemList.Field_Generator_MAX.get(1L),
                'e',
                ItemList.Sensor_MAX.get(1L));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(SGCraft.ID, "stargateRing", 1, 1, missing),
                "---------",
                "----a----",
                "---aba---",
                "--acdca--",
                "-abdedba-",
                "--acdca--",
                "---aba---",
                "----a----",
                "---------",
                'a',
                GT_OreDictUnificator.get(OrePrefixes.block, MaterialsUEVplus.BlackDwarfMatter, 1L),
                'b',
                ItemList.Field_Generator_MAX.get(1L),
                'c',
                CustomItemList.QuantumCircuit.get(1L),
                'd',
                getModItem(SGCraft.ID, "sgChevronUpgrade", 1, 0, missing),
                'e',
                getModItem(SGCraft.ID, "stargateRing", 1, 0, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(SGCraft.ID, "stargateBase", 1, 0, missing),
                "abbcdcbba",
                "befghgfeb",
                "beigjgieb",
                "cklmnmlkc",
                "dklnonlkd",
                "cklmnmlkc",
                "beigjgieb",
                "befghgfeb",
                "abbcdcbba",
                'a',
                ItemList.Sensor_MAX.get(1L),
                'b',
                ItemList.Field_Generator_MAX.get(1L),
                'c',
                ItemList.Emitter_MAX.get(1L),
                'd',
                GT_OreDictUnificator.get(OrePrefixes.nanite, MaterialsUEVplus.Universium, 1L),
                'e',
                CustomItemList.SpacetimeCompressionFieldGeneratorTier10.get(1L),
                'f',
                getModItem(BartWorks.ID, "bw.werkstoffblocks.01", 1, 10112, missing),
                'g',
                CustomItemList.StargateShieldingFoil.get(1L),
                'h',
                CustomItemList.StargateFramePart.get(1L),
                'i',
                ItemList.ZPM6.get(1L),
                'j',
                GT_OreDictUnificator.get(OrePrefixes.plateDense, MaterialsUEVplus.MagMatter, 1L),
                'k',
                CustomItemList.StabilisationFieldGeneratorTier10.get(1L),
                'l',
                CustomItemList.Mega_EoH.get(1L),
                'm',
                getModItem(SGCraft.ID, "stargateRing", 1, 0, missing),
                'n',
                getModItem(SGCraft.ID, "stargateRing", 1, 1, missing),
                'o',
                getModItem(SGCraft.ID, "sgCoreCrystal", 1, 0, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(SGCraft.ID, "ic2Capacitor", 1, 0, missing),
                "---------",
                "---------",
                "---aaa---",
                "--abcba--",
                "--acbca--",
                "--abcba--",
                "---aaa---",
                "---------",
                "---------",
                'a',
                CustomItemList.StargateShieldingFoil.get(1L),
                'b',
                ItemList.Field_Generator_MAX.get(1L),
                'c',
                createItemStack(
                        EnderIO.ID,
                        "blockCapBank",
                        1,
                        0,
                        "{type:\"CREATIVE\",storedEnergyRF:2500000}",
                        missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(SGCraft.ID, "sgIrisBlade", 1, 0, missing),
                "-----aaaa",
                "----aaaa-",
                "---aaaa--",
                "--aaaa---",
                "-aaaaa---",
                "abaaaa---",
                "abaaaaa--",
                "acbbaaaa-",
                "daaaaaaaa",
                'a',
                GT_OreDictUnificator.get(OrePrefixes.plateDense, MaterialsUEVplus.WhiteDwarfMatter, 1L),
                'b',
                ItemList.Electric_Piston_MAX.get(1L),
                'c',
                ItemList.ZPM6.get(1L),
                'd',
                getModItem(GoodGenerator.ID, "compactFusionCoil", 1, 3, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(SGCraft.ID, "sgIrisUpgrade", 1, 0, missing),
                "--aaaaa--",
                "-b-----b-",
                "a-------a",
                "a-------a",
                "a-------a",
                "a-------a",
                "a-------a",
                "-b-----b-",
                "--aaaaa--",
                'a',
                getModItem(SGCraft.ID, "sgIrisBlade", 1, 0, missing),
                'b',
                getModItem(GoodGenerator.ID, "compactFusionCoil", 1, 3, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(SGCraft.ID, "rfPowerUnit", 1, 0, missing),
                "aabcccbaa",
                "a-b---b-a",
                "a-b-d-b-a",
                "c--beb--c",
                "c-defed-c",
                "c--beb--c",
                "agg-d-gga",
                "a-g---g-a",
                "aaacccaaa",
                'a',
                GT_OreDictUnificator.get(OrePrefixes.block, MaterialsUEVplus.BlackDwarfMatter, 1L),
                'b',
                getModItem(GoodGenerator.ID, "compactFusionCoil", 1, 3, missing),
                'c',
                GT_OreDictUnificator.get(OrePrefixes.block, MaterialsUEVplus.TranscendentMetal, 1L),
                'd',
                CustomItemList.QuantumCircuit.get(1L),
                'e',
                getModItem(SGCraft.ID, "ic2Capacitor", 1, 0, missing),
                'f',
                getModItem(SGCraft.ID, "stargateRing", 1, 0, missing),
                'g',
                GT_OreDictUnificator.get(OrePrefixes.nanite, MaterialsUEVplus.BlackDwarfMatter, 1L));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem(SGCraft.ID, "ocInterface", 1, 0, missing),
                "abccdccea",
                "ed--d--db",
                "c-d-d-d-c",
                "c--ddd--c",
                "ddddadddd",
                "c--ddd--c",
                "c-d-d-d-c",
                "bd--d--de",
                "aeccdccba",
                'a',
                CustomItemList.QuantumCircuit.get(1L),
                'b',
                ItemList.Emitter_MAX.get(1L),
                'c',
                GT_OreDictUnificator.get(OrePrefixes.block, MaterialsUEVplus.BlackDwarfMatter, 1L),
                'd',
                getModItem(GoodGenerator.ID, "compactFusionCoil", 1, 3, missing),
                'e',
                ItemList.Sensor_MAX.get(1L));

    }
}
