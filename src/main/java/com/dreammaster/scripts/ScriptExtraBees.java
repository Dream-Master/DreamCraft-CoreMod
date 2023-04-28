package com.dreammaster.scripts;

import static gregtech.api.util.GT_ModHandler.addShapelessCraftingRecipe;
import static gregtech.api.util.GT_ModHandler.getModItem;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sFluidExtractionRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sMaceratorRecipes;

import java.util.Arrays;
import java.util.List;

import net.minecraftforge.fluids.FluidRegistry;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

import com.dreammaster.thaumcraft.TCHelper;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Mods;

public class ScriptExtraBees implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Extra Bees";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(
                Mods.ExtraBees.ID,
                Mods.Thaumcraft.ID,
                Mods.MagicBees.ID,
                Mods.Forestry.ID,
                Mods.PamsHarvestCraft.ID);
    }

    @Override
    public void loadRecipes() {

        addShapelessCraftingRecipe(
                getModItem("ExtraBees", "dictionary", 1, 0, missing),
                new Object[] { getModItem("ExtraBees", "dictionary", 1, 0, missing) });

        GT_Values.RA.stdBuilder().itemInputs(getModItem("ExtraBees", "misc", 1, 1, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.01", 1, 500, missing)).outputChances(10000)
                .noFluidInputs().noFluidOutputs().duration(300).eut(2).addTo(sMaceratorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem("ExtraBees", "misc", 1, 2, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.01", 1, 501, missing)).outputChances(10000)
                .noFluidInputs().noFluidOutputs().duration(300).eut(2).addTo(sMaceratorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem("ExtraBees", "misc", 1, 3, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.01", 1, 502, missing)).outputChances(10000)
                .noFluidInputs().noFluidOutputs().duration(300).eut(2).addTo(sMaceratorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem("ExtraBees", "misc", 1, 4, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.01", 1, 503, missing)).outputChances(10000)
                .noFluidInputs().noFluidOutputs().duration(300).eut(2).addTo(sMaceratorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem("ExtraBees", "misc", 1, 5, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.01", 1, 526, missing)).outputChances(10000)
                .noFluidInputs().noFluidOutputs().duration(300).eut(2).addTo(sMaceratorRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem("ExtraBees", "honeyDrop", 1, 8, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.02", 1, 32415, missing)).outputChances(1000)
                .noFluidInputs().fluidOutputs(FluidRegistry.getFluidStack("for.honey", 200)).duration(32).eut(7)
                .addTo(sFluidExtractionRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem("ExtraBees", "honeyDrop", 1, 9, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.02", 1, 32425, missing)).outputChances(1000)
                .noFluidInputs().fluidOutputs(FluidRegistry.getFluidStack("for.honey", 200)).duration(32).eut(7)
                .addTo(sFluidExtractionRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem("ExtraBees", "honeyDrop", 1, 10, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.02", 1, 32418, missing)).outputChances(1000)
                .noFluidInputs().fluidOutputs(FluidRegistry.getFluidStack("for.honey", 200)).duration(32).eut(7)
                .addTo(sFluidExtractionRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem("ExtraBees", "honeyDrop", 1, 11, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.02", 1, 32416, missing)).outputChances(1000)
                .noFluidInputs().fluidOutputs(FluidRegistry.getFluidStack("for.honey", 200)).duration(32).eut(7)
                .addTo(sFluidExtractionRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem("ExtraBees", "honeyDrop", 1, 13, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.02", 1, 32429, missing)).outputChances(1000)
                .noFluidInputs().fluidOutputs(FluidRegistry.getFluidStack("for.honey", 200)).duration(32).eut(7)
                .addTo(sFluidExtractionRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem("ExtraBees", "honeyDrop", 1, 12, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.02", 1, 32414, missing)).outputChances(1000)
                .noFluidInputs().fluidOutputs(FluidRegistry.getFluidStack("for.honey", 200)).duration(32).eut(7)
                .addTo(sFluidExtractionRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem("ExtraBees", "honeyDrop", 1, 14, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.02", 1, 32417, missing)).outputChances(1000)
                .noFluidInputs().fluidOutputs(FluidRegistry.getFluidStack("for.honey", 200)).duration(32).eut(7)
                .addTo(sFluidExtractionRecipes);

        new ResearchItem(
                "HEALINGFRAME",
                "MAGICBEES",
                new AspectList().add(Aspect.getAspect("praecantatio"), 15).add(Aspect.getAspect("fabrico"), 12)
                        .add(Aspect.getAspect("cognitio"), 9).add(Aspect.getAspect("victus"), 6),
                7,
                -2,
                3,
                getModItem("ExtraBees", "hiveFrame.clay", 1, 0, missing)).setParents("MB_EssenceLife").setConcealed()
                        .setPages(new ResearchPage("ExtraBees.research_page.HEALINGFRAME")).registerResearchItem();
        ThaumcraftApi.addArcaneCraftingRecipe(
                "HEALINGFRAME",
                getModItem("ExtraBees", "hiveFrame.clay", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 4).add(Aspect.getAspect("terra"), 4)
                        .add(Aspect.getAspect("aer"), 4).add(Aspect.getAspect("perditio"), 4)
                        .add(Aspect.getAspect("ignis"), 4).add(Aspect.getAspect("aqua"), 4),
                "abc",
                "def",
                "ghi",
                'a',
                "screwIron",
                'b',
                getModItem("gregtech", "gt.metaitem.01", 1, 2805, missing),
                'c',
                "screwIron",
                'd',
                getModItem("gregtech", "gt.metaitem.01", 1, 2805, missing),
                'e',
                getModItem("Forestry", "frameImpregnated", 1, 0, missing),
                'f',
                getModItem("gregtech", "gt.metaitem.01", 1, 2805, missing),
                'g',
                "screwIron",
                'h',
                getModItem("gregtech", "gt.metaitem.01", 1, 2805, missing),
                'i',
                "screwIron");
        TCHelper.addResearchPage(
                "HEALINGFRAME",
                new ResearchPage(TCHelper.findArcaneRecipe(getModItem("ExtraBees", "hiveFrame.clay", 1, 0, missing))));
        new ResearchItem(
                "CHOCOLATEFRAME",
                "MAGICBEES",
                new AspectList().add(Aspect.getAspect("praecantatio"), 15).add(Aspect.getAspect("fabrico"), 12)
                        .add(Aspect.getAspect("cognitio"), 9).add(Aspect.getAspect("fames"), 6),
                7,
                2,
                3,
                getModItem("ExtraBees", "hiveFrame.cocoa", 1, 0, missing)).setParents("MB_EssenceUnstable")
                        .setConcealed()
                        .setPages(
                                new ResearchPage("ExtraBees.research_page.CHOCOLATEFRAME_1"),
                                new ResearchPage("ExtraBees.research_page.CHOCOLATEFRAME_2"))
                        .registerResearchItem();
        ThaumcraftApi.addArcaneCraftingRecipe(
                "CHOCOLATEFRAME",
                getModItem("ExtraBees", "hiveFrame.cocoa", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 4).add(Aspect.getAspect("terra"), 4)
                        .add(Aspect.getAspect("aer"), 4).add(Aspect.getAspect("perditio"), 4)
                        .add(Aspect.getAspect("ignis"), 4).add(Aspect.getAspect("aqua"), 4),
                "abc",
                "def",
                "ghi",
                'a',
                "screwIron",
                'b',
                getModItem("minecraft", "sugar", 1, 0, missing),
                'c',
                "screwIron",
                'd',
                getModItem("harvestcraft", "cocoapowderItem", 1, 0, missing),
                'e',
                getModItem("Forestry", "frameImpregnated", 1, 0, missing),
                'f',
                getModItem("harvestcraft", "cocoapowderItem", 1, 0, missing),
                'g',
                "screwIron",
                'h',
                getModItem("minecraft", "sugar", 1, 0, missing),
                'i',
                "screwIron");
        TCHelper.addResearchPage(
                "CHOCOLATEFRAME",
                new ResearchPage(TCHelper.findArcaneRecipe(getModItem("ExtraBees", "hiveFrame.cocoa", 1, 0, missing))));
        new ResearchItem(
                "RESTRAINTFRAME",
                "MAGICBEES",
                new AspectList().add(Aspect.getAspect("praecantatio"), 15).add(Aspect.getAspect("fabrico"), 12)
                        .add(Aspect.getAspect("cognitio"), 9).add(Aspect.getAspect("metallum"), 6),
                9,
                0,
                3,
                getModItem("ExtraBees", "hiveFrame.cage", 1, 0, missing))
                        .setParents("PROVENFRAME", "HEALINGFRAME", "CHOCOLATEFRAME").setConcealed()
                        .setPages(new ResearchPage("ExtraBees.research_page.RESTRAINTFRAME")).registerResearchItem();
        ThaumcraftApi.addArcaneCraftingRecipe(
                "RESTRAINTFRAME",
                getModItem("ExtraBees", "hiveFrame.cage", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 8).add(Aspect.getAspect("terra"), 8)
                        .add(Aspect.getAspect("aer"), 8).add(Aspect.getAspect("perditio"), 8)
                        .add(Aspect.getAspect("ignis"), 8).add(Aspect.getAspect("aqua"), 8),
                "abc",
                "def",
                "ghi",
                'a',
                "screwSteel",
                'b',
                "barsIron",
                'c',
                "screwSteel",
                'd',
                "barsIron",
                'e',
                getModItem("Forestry", "frameImpregnated", 1, 0, missing),
                'f',
                "barsIron",
                'g',
                "screwSteel",
                'h',
                "barsIron",
                'i',
                "screwSteel");
        TCHelper.addResearchPage(
                "RESTRAINTFRAME",
                new ResearchPage(TCHelper.findArcaneRecipe(getModItem("ExtraBees", "hiveFrame.cage", 1, 0, missing))));
        new ResearchItem(
                "SOULFRAME",
                "MAGICBEES",
                new AspectList().add(Aspect.getAspect("praecantatio"), 15).add(Aspect.getAspect("fabrico"), 12)
                        .add(Aspect.getAspect("cognitio"), 9).add(Aspect.getAspect("spiritus"), 6),
                11,
                0,
                3,
                getModItem("ExtraBees", "hiveFrame.soul", 1, 0, missing)).setParents("RESTRAINTFRAME").setConcealed()
                        .setPages(new ResearchPage("ExtraBees.research_page.SOULFRAME")).registerResearchItem();
        ThaumcraftApi.addArcaneCraftingRecipe(
                "SOULFRAME",
                getModItem("ExtraBees", "hiveFrame.soul", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 16).add(Aspect.getAspect("terra"), 16)
                        .add(Aspect.getAspect("aer"), 16).add(Aspect.getAspect("perditio"), 16)
                        .add(Aspect.getAspect("ignis"), 16).add(Aspect.getAspect("aqua"), 16),
                "abc",
                "def",
                "ghi",
                'a',
                "screwAluminium",
                'b',
                getModItem("minecraft", "soul_sand", 1, 0, missing),
                'c',
                "screwAluminium",
                'd',
                getModItem("minecraft", "soul_sand", 1, 0, missing),
                'e',
                getModItem("ExtraBees", "hiveFrame.cage", 1, 0, missing),
                'f',
                getModItem("minecraft", "soul_sand", 1, 0, missing),
                'g',
                "screwAluminium",
                'h',
                getModItem("minecraft", "soul_sand", 1, 0, missing),
                'i',
                "screwAluminium");
        TCHelper.addResearchPage(
                "SOULFRAME",
                new ResearchPage(TCHelper.findArcaneRecipe(getModItem("ExtraBees", "hiveFrame.soul", 1, 0, missing))));
        ThaumcraftApi.addWarpToResearch("SOULFRAME", 1);
    }
}
