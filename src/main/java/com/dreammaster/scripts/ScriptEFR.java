package com.dreammaster.scripts;

import static gregtech.api.enums.Mods.AdventureBackpack;
import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.BiomesOPlenty;
import static gregtech.api.enums.Mods.Botania;
import static gregtech.api.enums.Mods.DraconicEvolution;
import static gregtech.api.enums.Mods.EnderIO;
import static gregtech.api.enums.Mods.EtFuturumRequiem;
import static gregtech.api.enums.Mods.ExtraUtilities;
import static gregtech.api.enums.Mods.HardcoreEnderExpansion;
import static gregtech.api.enums.Mods.Minecraft;
import static gregtech.api.enums.Mods.PamsHarvestCraft;
import static gregtech.api.enums.Mods.StevesCarts2;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.enums.Mods.ThaumicBases;
import static gregtech.api.enums.Mods.TinkerConstruct;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.recipe.RecipeMaps.compressorRecipes;
import static gregtech.api.recipe.RecipeMaps.fluidExtractionRecipes;
import static gregtech.api.recipe.RecipeMaps.hammerRecipes;
import static gregtech.api.recipe.RecipeMaps.mixerRecipes;
import static gregtech.api.recipe.RecipeMaps.multiblockChemicalReactorRecipes;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.dreammaster.recipes.CustomItem;
import com.dreammaster.thaumcraft.TCHelper;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class ScriptEFR implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Et Futurum Requiem";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(EtFuturumRequiem.ID, ExtraUtilities.ID, Thaumcraft.ID);
    }

    @Override
    public void loadRecipes() {
        long bits = GTModHandler.RecipeBits.NOT_REMOVABLE | GTModHandler.RecipeBits.REVERSIBLE
                | GTModHandler.RecipeBits.BUFFERED;
        GTModHandler.addCraftingRecipe(
                GTModHandler.getModItem(EtFuturumRequiem.ID, "observer", 1L),
                bits,
                new Object[] { "AEA", "BCD", "AAA", 'A', "cobblestone", 'B', "dustRedstone", 'C',
                        GTModHandler.getModItem(ExtraUtilities.ID, "budoff", 1, 0), 'D',
                        GTModHandler.getModItem(Minecraft.ID, "comparator", 1, 0), 'E', "gearGtSmallAnyIron" });

        GTModHandler.addCraftingRecipe(
                GTModHandler.getModItem(EtFuturumRequiem.ID, "cherry_trapdoor", 1L),
                bits,
                new Object[] { "ABA", "BCB", "ABA", 'A',
                        GTModHandler.getModItem(EtFuturumRequiem.ID, "wood_slab", 1L, 3), 'B', "stickWood", 'C',
                        "itemFlint" });

        GTModHandler.addCraftingRecipe(
                GTModHandler.getModItem(EtFuturumRequiem.ID, "cherry_trapdoor", 2L),
                bits,
                new Object[] { "ABA", "BCB", "ABA", 'A',
                        GTModHandler.getModItem(EtFuturumRequiem.ID, "wood_slab", 1L, 3), 'B', "stickWood", 'C',
                        "screwIron" });

        GTModHandler.addCraftingRecipe(
                GTModHandler.getModItem(EtFuturumRequiem.ID, "cherry_trapdoor", 3L),
                bits,
                new Object[] { "ABA", "BCB", "ABA", 'A',
                        GTModHandler.getModItem(EtFuturumRequiem.ID, "wood_slab", 1L, 3), 'B', "stickWood", 'C',
                        "screwSteel" });
        for (int i = 0; i < 16; i++) {
            addShapelessRecipe(
                    GTModHandler.getModItem(EtFuturumRequiem.ID, "banner", 1L, i),
                    new CustomItem.NBTItem(getModItem(Thaumcraft.ID, "blockWoodenDevice", 1, 8))
                            .setNBT("{color:" + i + "b}"));
            addShapelessRecipe(
                    createItemStack(Thaumcraft.ID, "blockWoodenDevice", 1, 8, "{color:" + i + "b}", missing),
                    GTModHandler.getModItem(EtFuturumRequiem.ID, "banner", 1L, i));
        }

        GTModHandler.addCraftingRecipe(
                getModItem(EtFuturumRequiem.ID, "barrel", 1, 0, missing),
                bits,
                new Object[] { "hPs", "PCP", " P ", 'P', GTOreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1L),
                        'C', "chestWood" });
        GTValues.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "chest", 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 2L))
                .itemOutputs(getModItem(EtFuturumRequiem.ID, "barrel", 1L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(assemblerRecipes);

        GTModHandler.addCraftingRecipe(
                getModItem(EtFuturumRequiem.ID, "lantern", 1, 0, missing),
                bits,
                new Object[] { "IGI", "PCP", "III", 'I', "plateIron", 'G', "dustGlowstone", 'P', "paneGlassColorless",
                        'C', getModItem(PamsHarvestCraft.ID, "pamcandleDeco1", 1, 0, missing) });

        GTModHandler.addSmeltingRecipe(
                getModItem(Minecraft.ID, "stone", 1, 0, missing),
                getModItem(EtFuturumRequiem.ID, "smooth_stone", 1, 0, missing));

        GTModHandler.addSmeltingRecipe(
                getModItem(Minecraft.ID, "quartz_block", 1, 0, missing),
                getModItem(EtFuturumRequiem.ID, "smooth_quartz", 1, 0, missing));

        GTModHandler.addCraftingRecipe(
                getModItem(EtFuturumRequiem.ID, "blast_furnace", 1, 0, missing),
                bits,
                new Object[] { "PPP", "PFP", "SSS", 'S', getModItem(EtFuturumRequiem.ID, "smooth_stone", 1, 0, missing),
                        'F', getModItem(Minecraft.ID, "furnace", 1, 0, missing), 'P', "plateIron" });

        GTModHandler.addCraftingRecipe(
                getModItem(Minecraft.ID, "leather", 1, 0, missing),
                bits,
                new Object[] { "SSS", "HHH", "SSS", 'S', getModItem(Minecraft.ID, "string", 1, 0, missing), 'H',
                        getModItem(EtFuturumRequiem.ID, "rabbit_hide", 1, 0, missing) });
        GTModHandler.addCraftingRecipe(
                getModItem(EtFuturumRequiem.ID, "soul_lantern", 1, 0, missing),
                bits,
                new Object[] { "IGI", "PCP", "III", 'I', "plateIron", 'G', "dustInfusedWater", 'P',
                        "paneGlassColorless", 'C', getModItem(EtFuturumRequiem.ID, "soul_torch", 1, 0, missing) });

        GTModHandler.addCraftingRecipe(
                getModItem(EtFuturumRequiem.ID, "target", 1, 0, missing),
                bits,
                new Object[] { "WTR", "CHD", "RTW", 'W', "blockWoolWhite", 'R', "blockWoolRed", 'T', "dustRedstone",
                        'C', GTModHandler.getModItem(Minecraft.ID, "comparator", 1, 0), 'H',
                        GTModHandler.getModItem(Minecraft.ID, "hay_block", 1, 0), 'D',
                        GTModHandler.getModItem(ExtraUtilities.ID, "budoff", 1, 0) });

        GTModHandler.addCraftingRecipe(
                getModItem(EtFuturumRequiem.ID, "smoker", 1, 0, missing),
                bits,
                new Object[] { "PhP", "PFP", "SCS", 'P', "plateIron", 'F',
                        getModItem(Minecraft.ID, "furnace", 1, 0, missing), 'S',
                        getModItem(EtFuturumRequiem.ID, "smooth_stone", 1, 0, missing), 'C',
                        getModItem(AdventureBackpack.ID, "blockCampFire", 1, 0, missing) });

        GTModHandler.addCraftingRecipe(
                getModItem(EtFuturumRequiem.ID, "end_crystal", 1, 0, missing),
                bits,
                new Object[] { "VGV", "VSV", "VQV", 'V',
                        getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzLamp", 1, 0, missing), 'G',
                        getModItem(HardcoreEnderExpansion.ID, "living_matter", 1, 0, missing), 'S',
                        getModItem(DraconicEvolution.ID, "wyvernCore", 1, 0, missing), 'Q',
                        ItemList.QuantumEye.get(1), });

        GTModHandler.addCraftingRecipe(
                getModItem(EtFuturumRequiem.ID, "smithing_table", 1, 0, missing),
                bits,
                new Object[] { "TT ", "GG ", "GG ", 'T',
                        GTModHandler.getModItem(TinkerConstruct.ID, "CraftingSlab", 1, 5), 'G',
                        GTModHandler.getModItem(Thaumcraft.ID, "blockWoodenDevice", 1, 6), });

        GTValues.RA.stdBuilder().itemInputs(getModItem(Minecraft.ID, "magma_cream", 4, 0, missing))
                .fluidInputs(new FluidStack(FluidRegistry.getFluid("lava"), 1000))
                .itemOutputs(getModItem(EtFuturumRequiem.ID, "magma", 1, 0, missing)).duration(10 * SECONDS).eut(2)
                .addTo(compressorRecipes);

        GTValues.RA.stdBuilder().itemInputs(getModItem(Minecraft.ID, "dye", 9, 15, missing))
                .itemOutputs(getModItem(EtFuturumRequiem.ID, "bone", 1, 0, missing)).duration(8 * SECONDS).eut(2)
                .addTo(compressorRecipes);

        GTValues.RA.stdBuilder().itemInputs(getModItem(EtFuturumRequiem.ID, "magma", 1, 0, missing))
                .itemOutputs(getModItem(Minecraft.ID, "magma_cream", 4, 0, missing))
                .fluidOutputs(new FluidStack(FluidRegistry.getFluid("lava"), 1000)).duration(10 * SECONDS).eut(48)
                .addTo(fluidExtractionRecipes);

        GTValues.RA.stdBuilder().itemInputs(getModItem(BiomesOPlenty.ID, "hardIce", 4, 0, missing))
                .itemOutputs(getModItem(EtFuturumRequiem.ID, "blue_ice", 1, 0, missing)).duration(8 * SECONDS).eut(2)
                .addTo(compressorRecipes);

        GTValues.RA.stdBuilder().itemInputs(getModItem(Minecraft.ID, "nether_wart", 9L))
                .itemOutputs(getModItem(EtFuturumRequiem.ID, "nether_wart", 1L)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        getModItem(Minecraft.ID, "gravel", 4L),
                        getModItem(TinkerConstruct.ID, "CraftedSoil", 4L, 1))
                .itemOutputs(getModItem(EtFuturumRequiem.ID, "old_gravel", 8L)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        getModItem(EtFuturumRequiem.ID, "old_gravel", 4L),
                        getModItem(BiomesOPlenty.ID, "driedDirt", 4L))
                .fluidInputs(new FluidStack(FluidRegistry.getFluid("steam"), 400))
                .itemOutputs(getModItem(EtFuturumRequiem.ID, "coarse_dirt", 8, 0, missing)).duration(8 * SECONDS).eut(2)
                .addTo(mixerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        getModItem(TinkerConstruct.ID, "slime.gel", 4L, 1),
                        getModItem(TinkerConstruct.ID, "GlueBlock", 4L, 0),
                        getModItem(TinkerConstruct.ID, "materials", 1L, 1))
                .fluidInputs(new FluidStack(FluidRegistry.getFluid("steam"), 4000))
                .itemOutputs(getModItem(EtFuturumRequiem.ID, "slime", 16, 0, missing)).duration(40 * SECONDS).eut(2)
                .addTo(mixerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        getModItem(HardcoreEnderExpansion.ID, "laboratory_obsidian", 16L),
                        getModItem(HardcoreEnderExpansion.ID, "spectral_tear", 1L, 0))
                .fluidInputs(new FluidStack(FluidRegistry.getFluid("ic2distilledwater"), 4000))
                .itemOutputs(getModItem(EtFuturumRequiem.ID, "crying_obsidian", 16L)).duration(5 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcite, 9L))
                .itemOutputs(getModItem(EtFuturumRequiem.ID, "calcite", 1, 0, missing)).duration(8 * SECONDS).eut(2)
                .addTo(compressorRecipes);

        GTValues.RA.stdBuilder().itemInputs(getModItem(EtFuturumRequiem.ID, "calcite", 1, 0, missing))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcite, 9L)).duration(8 * SECONDS)
                .eut(2).addTo(hammerRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.MelodicAlloy, 8L),
                        getModItem(Botania.ID, "fertilizer", 3, 0, missing),
                        getModItem(ThaumicBases.ID, "genLeaves", 1, 3, missing),
                        GTUtility.getIntegratedCircuit(24))
                .itemOutputs(getModItem(EtFuturumRequiem.ID, "chorus_flower", 1, 0, missing))
                .fluidInputs(
                        new FluidStack(FluidRegistry.getFluid("endergoo"), 4000),
                        new FluidStack(FluidRegistry.getFluid("ender"), 1000))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_HV).addTo(multiblockChemicalReactorRecipes);

        new ResearchItem(
                "UNDYINGTOTEM",
                "NEWHORIZONS",
                new AspectList().add(Aspect.getAspect("infernus"), 15).add(Aspect.getAspect("lucrum"), 12)
                        .add(Aspect.getAspect("praecantatio"), 12).add(Aspect.getAspect("spiritus"), 9)
                        .add(Aspect.getAspect("fames"), 6).add(Aspect.getAspect("corpus"), 3),
                -6,
                -7,
                3,
                getModItem(EtFuturumRequiem.ID, "totem_of_undying", 1, 0, missing)).setParents("GREENHEART")
                        .setConcealed().setRound().setPages(new ResearchPage("TConstruct.research_page.UNDYINGTOTEM.1"))
                        .registerResearchItem();
        ThaumcraftApi.addInfusionCraftingRecipe(
                "UNDYINGTOTEM",
                getModItem(EtFuturumRequiem.ID, "totem_of_undying", 1, 0, missing),
                15,
                new AspectList().add(Aspect.getAspect("exanimis"), 100).add(Aspect.getAspect("ignis"), 150)
                        .add(Aspect.getAspect("lucrum"), 150).add(Aspect.getAspect("sano"), 200)
                        .add(Aspect.getAspect("praecantatio"), 200),
                getModItem(TinkerConstruct.ID, "heartCanister", 1, 5, missing),
                new ItemStack[] { GTOreDictUnificator.get(OrePrefixes.plate, Materials.InfusedGold, 1L),
                        GTOreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Emerald, 1L),
                        getModItem(ThaumicBases.ID, "oldGold", 1, 0, missing),
                        getModItem(StevesCarts2.ID, "BlockMetalStorage", 1, 2, missing),
                        getModItem(ThaumicBases.ID, "oldGold", 1, 0, missing),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.InfusedGold, 1L),
                        getModItem(EnderIO.ID, "itemFrankenSkull", 1, 5, missing),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.InfusedGold, 1L),
                        getModItem(ThaumicBases.ID, "oldGold", 1, 0, missing),
                        getModItem(StevesCarts2.ID, "BlockMetalStorage", 1, 2, missing),
                        getModItem(ThaumicBases.ID, "oldGold", 1, 0, missing),
                        GTOreDictUnificator.get(OrePrefixes.gemExquisite, Materials.GreenSapphire, 1L), });
        TCHelper.addResearchPage(
                "UNDYINGTOTEM",
                new ResearchPage(
                        Objects.requireNonNull(
                                TCHelper.findInfusionRecipe(
                                        getModItem(EtFuturumRequiem.ID, "totem_of_undying", 1, 0, missing)))));
        ThaumcraftApi.addWarpToResearch("UNDYINGTOTEM", 3);

    }
}
