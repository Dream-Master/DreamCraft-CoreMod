package com.dreammaster.scripts;

import static gregtech.api.util.GT_ModHandler.getModItem;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.dreammaster.thaumcraft.TCHelper;

import gregtech.api.enums.Mods;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class ScriptMagicBees implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Magic Bees";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(Mods.MagicBees.ID, Mods.Thaumcraft.ID, Mods.Forestry.ID);
    }

    @Override
    public void loadRecipes() {
        TCHelper.removeInfusionRecipe(getModItem("MagicBees", "miscResources", 3, 17, missing));
        TCHelper.removeInfusionRecipe(getModItem("MagicBees", "miscResources", 3, 17, missing));
        TCHelper.removeCrucibleRecipe(getModItem("MagicBees", "miscResources", 1, 7, missing));
        TCHelper.removeCrucibleRecipe(getModItem("MagicBees", "miscResources", 1, 8, missing));
        TCHelper.removeCrucibleRecipe(getModItem("MagicBees", "miscResources", 1, 9, missing));
        TCHelper.removeCrucibleRecipe(getModItem("MagicBees", "miscResources", 1, 10, missing));
        TCHelper.removeArcaneRecipe(getModItem("MagicBees", "miscResources", 1, 11, missing));
        TCHelper.removeCrucibleRecipe(getModItem("MagicBees", "miscResources", 1, 12, missing));
        TCHelper.removeCrucibleRecipe(getModItem("MagicBees", "miscResources", 1, 12, missing));
        TCHelper.removeArcaneRecipe(getModItem("MagicBees", "frameMagic", 1, 0, missing));
        TCHelper.removeArcaneRecipe(getModItem("MagicBees", "visAuraProvider", 1, 0, missing));
        TCHelper.removeArcaneRecipe(getModItem("MagicBees", "item.thaumiumScoop", 1, 0, missing));
        TCHelper.removeArcaneRecipe(getModItem("MagicBees", "item.voidScoop", 1, 0, missing));
        TCHelper.removeArcaneRecipe(getModItem("MagicBees", "item.thaumiumGrafter", 1, 0, missing));
        TCHelper.removeArcaneRecipe(getModItem("MagicBees", "item.voidGrafter", 1, 0, missing));
        new ResearchItem(
                "MAGICAPIARY",
                "MAGICBEES",
                new AspectList().add(Aspect.getAspect("praecantatio"), 15).add(Aspect.getAspect("sano"), 12)
                        .add(Aspect.getAspect("ignis"), 9).add(Aspect.getAspect("lucrum"), 6),
                1,
                7,
                3,
                getModItem("MagicBees", "magicApiary", 1, 0, missing)).setParents("MB_DimensionalSingularity")
                        .setConcealed().setRound().setPages(new ResearchPage("MagicBees.research_page.MAGICAPIARY"))
                        .registerResearchItem();
        ThaumcraftApi.addInfusionCraftingRecipe(
                "MAGICAPIARY",
                getModItem("MagicBees", "magicApiary", 1, 0, missing),
                3,
                new AspectList().add(Aspect.getAspect("praecantatio"), 75).add(Aspect.getAspect("sano"), 75)
                        .add(Aspect.getAspect("ignis"), 50).add(Aspect.getAspect("lucrum"), 50)
                        .add(Aspect.getAspect("exanimis"), 25).add(Aspect.getAspect("herba"), 20),
                getModItem("Forestry", "apiculture", 1, 0, missing),
                new ItemStack[] { getModItem("MagicBees", "wax", 1, 0, missing),
                        getModItem("MagicBees", "wax", 1, 1, missing), getModItem("MagicBees", "wax", 1, 2, missing),
                        getModItem("MagicBees", "pollen", 1, 0, missing),
                        getModItem("MagicBees", "pollen", 1, 1, missing), });
        TCHelper.addResearchPage(
                "MAGICAPIARY",
                new ResearchPage(TCHelper.findInfusionRecipe(getModItem("MagicBees", "magicApiary", 1, 0, missing))));
        ThaumcraftApi.addWarpToResearch("MAGICAPIARY", 2);
        TCHelper.moveResearch("MB_VisAuraProvider", "MAGICBEES", -3, 7);
        ResearchCategories.getResearch("MB_VisAuraProvider").setRound();
        TCHelper.addResearchPrereq("MB_VisAuraProvider", "MB_DimensionalSingularity", false);
        TCHelper.setResearchAspects(
                "MB_VisAuraProvider",
                new AspectList().add(Aspect.getAspect("lucrum"), 15).add(Aspect.getAspect("metallum"), 12)
                        .add(Aspect.getAspect("vitreus"), 12).add(Aspect.getAspect("ordo"), 9)
                        .add(Aspect.getAspect("motus"), 6).add(Aspect.getAspect("machina"), 3));
        TCHelper.setResearchComplexity("MB_VisAuraProvider", 4);
        ResearchCategories.getResearch("MB_VisAuraProvider").setConcealed();
        ThaumcraftApi.addWarpToResearch("MB_VisAuraProvider", 2);
        TCHelper.clearPages("MB_VisAuraProvider");
        TCHelper.addResearchPage("MB_VisAuraProvider", new ResearchPage("tc.research_page.MB_VisAuraProvider.1"));
        ThaumcraftApi.addArcaneCraftingRecipe(
                "MB_VisAuraProvider",
                getModItem("MagicBees", "visAuraProvider", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 75).add(Aspect.getAspect("aqua"), 75)
                        .add(Aspect.getAspect("aer"), 75).add(Aspect.getAspect("perditio"), 75),
                "abc",
                "def",
                "ghi",
                'a',
                getModItem("MagicBees", "pollen", 1, 1, missing),
                'b',
                getModItem("Thaumcraft", "blockWoodenDevice", 1, 5, missing),
                'c',
                getModItem("MagicBees", "pollen", 1, 0, missing),
                'd',
                getModItem("Thaumcraft", "blockWoodenDevice", 1, 5, missing),
                'e',
                getModItem("Thaumcraft", "blockMetalDevice", 1, 2, missing),
                'f',
                getModItem("Thaumcraft", "blockWoodenDevice", 1, 5, missing),
                'g',
                getModItem("gregtech", "gt.metaitem.02", 1, 23086, missing),
                'h',
                getModItem("gregtech", "gt.blockmachines", 1, 4182, missing),
                'i',
                getModItem("gregtech", "gt.metaitem.02", 1, 23086, missing));
        TCHelper.addResearchPage(
                "MB_VisAuraProvider",
                new ResearchPage(TCHelper.findArcaneRecipe(getModItem("MagicBees", "visAuraProvider", 1, 0, missing))));
        new ResearchItem(
                "CONCENTRATEDCOMPOUND",
                "MAGICBEES",
                new AspectList().add(Aspect.getAspect("messis"), 15).add(Aspect.getAspect("sano"), 12)
                        .add(Aspect.getAspect("vitreus"), 9).add(Aspect.getAspect("perditio"), 6),
                2,
                -3,
                3,
                getModItem("MagicBees", "miscResources", 1, 2, missing)).setParents("MB_Root", "MB_EssenceLife")
                        .setConcealed().setPages(new ResearchPage("MagicBees.research_page.CONCENTRATEDCOMPOUND"))
                        .registerResearchItem();
        ThaumcraftApi.addCrucibleRecipe(
                "CONCENTRATEDCOMPOUND",
                getModItem("MagicBees", "miscResources", 1, 2, missing),
                getModItem("gregtech", "gt.metaitem.01", 1, 8530, missing),
                new AspectList().add(Aspect.getAspect("messis"), 6).add(Aspect.getAspect("vitreus"), 3));
        TCHelper.addResearchPage(
                "CONCENTRATEDCOMPOUND",
                new ResearchPage(TCHelper.findCrucibleRecipe(getModItem("MagicBees", "miscResources", 1, 2, missing))));
        TCHelper.clearPages("MB_EssenceLife");
        TCHelper.addResearchPage("MB_EssenceLife", new ResearchPage("tc.research_page.MB_EssenceLife.1"));
        ThaumcraftApi.addCrucibleRecipe(
                "MB_EssenceLife",
                getModItem("MagicBees", "miscResources", 1, 7, missing),
                getModItem("minecraft", "red_flower", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("metallum"), 16).add(Aspect.getAspect("permutatio"), 16)
                        .add(Aspect.getAspect("herba"), 16).add(Aspect.getAspect("terra"), 16));
        TCHelper.addResearchPage(
                "MB_EssenceLife",
                new ResearchPage(TCHelper.findCrucibleRecipe(getModItem("MagicBees", "miscResources", 1, 7, missing))));
        TCHelper.setResearchAspects(
                "MB_EssenceLife",
                new AspectList().add(Aspect.getAspect("metallum"), 12).add(Aspect.getAspect("permutatio"), 9)
                        .add(Aspect.getAspect("herba"), 6).add(Aspect.getAspect("terra"), 3));
        TCHelper.setResearchComplexity("MB_EssenceLife", 3);
        TCHelper.clearPages("MB_EssenceDeath");
        TCHelper.addResearchPage("MB_EssenceDeath", new ResearchPage("tc.reseacrh_page.MB_EssenceDeath.1"));
        ThaumcraftApi.addCrucibleRecipe(
                "MB_EssenceDeath",
                getModItem("MagicBees", "miscResources", 1, 8, missing),
                getModItem("minecraft", "rotten_flesh", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("mortuus"), 16).add(Aspect.getAspect("perditio"), 16)
                        .add(Aspect.getAspect("spiritus"), 16).add(Aspect.getAspect("exanimis"), 16));
        TCHelper.addResearchPage(
                "MB_EssenceDeath",
                new ResearchPage(TCHelper.findCrucibleRecipe(getModItem("MagicBees", "miscResources", 1, 8, missing))));
        TCHelper.setResearchAspects(
                "MB_EssenceDeath",
                new AspectList().add(Aspect.getAspect("mortuus"), 12).add(Aspect.getAspect("perditio"), 9)
                        .add(Aspect.getAspect("spiritus"), 6).add(Aspect.getAspect("exanimis"), 3));
        TCHelper.setResearchComplexity("MB_EssenceDeath", 3);
        TCHelper.clearPages("MB_EssenceTime");
        TCHelper.addResearchPage("MB_EssenceTime", new ResearchPage("tc.research_page.MB_EssenceTime.1"));
        ThaumcraftApi.addCrucibleRecipe(
                "MB_EssenceTime",
                getModItem("MagicBees", "miscResources", 1, 9, missing),
                getModItem("minecraft", "clock", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 16).add(Aspect.getAspect("vacuos"), 16)
                        .add(Aspect.getAspect("vinculum"), 16).add(Aspect.getAspect("tempus"), 16));
        TCHelper.addResearchPage(
                "MB_EssenceTime",
                new ResearchPage(TCHelper.findCrucibleRecipe(getModItem("MagicBees", "miscResources", 1, 9, missing))));
        TCHelper.setResearchAspects(
                "MB_EssenceTime",
                new AspectList().add(Aspect.getAspect("ordo"), 12).add(Aspect.getAspect("vacuos"), 9)
                        .add(Aspect.getAspect("vinculum"), 6).add(Aspect.getAspect("tempus"), 3));
        TCHelper.setResearchComplexity("MB_EssenceTime", 3);
        TCHelper.clearPages("MB_EssenceArmor");
        TCHelper.addResearchPage("MB_EssenceArmor", new ResearchPage("tc.research_page.MB_EssenceArmor.1"));
        ThaumcraftApi.addCrucibleRecipe(
                "MB_EssenceArmor",
                getModItem("MagicBees", "miscResources", 1, 10, missing),
                getModItem("minecraft", "iron_chestplate", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("tutamen"), 16).add(Aspect.getAspect("praecantatio"), 16)
                        .add(Aspect.getAspect("metallum"), 16).add(Aspect.getAspect("fabrico"), 16));
        TCHelper.addResearchPage(
                "MB_EssenceArmor",
                new ResearchPage(
                        TCHelper.findCrucibleRecipe(getModItem("MagicBees", "miscResources", 1, 10, missing))));
        TCHelper.setResearchAspects(
                "MB_EssenceArmor",
                new AspectList().add(Aspect.getAspect("tutamen"), 12).add(Aspect.getAspect("praecantatio"), 9)
                        .add(Aspect.getAspect("metallum"), 6).add(Aspect.getAspect("fabrico"), 3));
        TCHelper.setResearchComplexity("MB_EssenceArmor", 3);
        TCHelper.clearPages("MB_EssenceUnstable");
        TCHelper.addResearchPage("MB_EssenceUnstable", new ResearchPage("tc.research_page.MB_EssenceUnstable.1"));
        ThaumcraftApi.addCrucibleRecipe(
                "MB_EssenceUnstable",
                getModItem("MagicBees", "miscResources", 1, 12, missing),
                getModItem("MagicBees", "propolis", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("perditio"), 16).add(Aspect.getAspect("permutatio"), 16)
                        .add(Aspect.getAspect("limus"), 16).add(Aspect.getAspect("potentia"), 16));
        TCHelper.addResearchPage(
                "MB_EssenceUnstable",
                new ResearchPage(
                        TCHelper.findCrucibleRecipe(getModItem("MagicBees", "miscResources", 1, 12, missing))));
        TCHelper.setResearchAspects(
                "MB_EssenceUnstable",
                new AspectList().add(Aspect.getAspect("perditio"), 12).add(Aspect.getAspect("permutatio"), 9)
                        .add(Aspect.getAspect("limus"), 6).add(Aspect.getAspect("potentia"), 3));
        TCHelper.setResearchComplexity("MB_EssenceUnstable", 3);
        TCHelper.clearPages("MB_DimensionalSingularity");
        TCHelper.addResearchPrereq("MB_DimensionalSingularity", "INFUSION", false);
        ResearchCategories.getResearch("MB_DimensionalSingularity").setConcealed();
        TCHelper.addResearchPage(
                "MB_DimensionalSingularity",
                new ResearchPage("tc.research_page.MB_DimensionalSingularity.1"));
        ThaumcraftApi.addInfusionCraftingRecipe(
                "MB_DimensionalSingularity",
                getModItem("MagicBees", "miscResources", 1, 17, missing),
                6,
                new AspectList().add(Aspect.getAspect("praecantatio"), 24).add(Aspect.getAspect("permutatio"), 24)
                        .add(Aspect.getAspect("alienis"), 16).add(Aspect.getAspect("tenebrae"), 16),
                getModItem("minecraft", "gold_block", 1, 0, missing),
                new ItemStack[] { getModItem("MagicBees", "propolis", 1, 0, missing),
                        getModItem("minecraft", "ender_eye", 1, 0, missing),
                        getModItem("MagicBees", "propolis", 1, 0, missing),
                        getModItem("minecraft", "ender_eye", 1, 0, missing),
                        getModItem("MagicBees", "propolis", 1, 0, missing),
                        getModItem("minecraft", "ender_eye", 1, 0, missing), });
        TCHelper.addResearchPage(
                "MB_DimensionalSingularity",
                new ResearchPage(
                        TCHelper.findInfusionRecipe(getModItem("MagicBees", "miscResources", 1, 17, missing))));
        TCHelper.setResearchAspects(
                "MB_DimensionalSingularity",
                new AspectList().add(Aspect.getAspect("perditio"), 15).add(Aspect.getAspect("permutatio"), 12)
                        .add(Aspect.getAspect("limus"), 9).add(Aspect.getAspect("potentia"), 6));
        TCHelper.setResearchComplexity("MB_DimensionalSingularity", 3);
        TCHelper.moveResearch("MB_EssenceOblivion", "MAGICBEES", -7, 4);
        TCHelper.clearPages("MB_EssenceOblivion");
        TCHelper.addResearchPage("MB_EssenceOblivion", new ResearchPage("tc.research_page.MB_EssenceOblivion.1"));
        ThaumcraftApi.addInfusionCraftingRecipe(
                "MB_EssenceOblivion",
                getModItem("MagicBees", "miscResources", 1, 11, missing),
                8,
                new AspectList().add(Aspect.getAspect("alienis"), 100).add(Aspect.getAspect("praecantatio"), 100)
                        .add(Aspect.getAspect("lucrum"), 75).add(Aspect.getAspect("bestia"), 75),
                getModItem("minecraft", "dragon_egg", 1, 0, missing),
                new ItemStack[] { getModItem("MagicBees", "miscResources", 1, 17, missing),
                        getModItem("MagicBees", "miscResources", 1, 17, missing),
                        getModItem("MagicBees", "miscResources", 1, 17, missing), });
        TCHelper.addResearchPage(
                "MB_EssenceOblivion",
                new ResearchPage(
                        TCHelper.findInfusionRecipe(getModItem("MagicBees", "miscResources", 1, 11, missing))));
        TCHelper.setResearchAspects(
                "MB_EssenceOblivion",
                new AspectList().add(Aspect.getAspect("alienis"), 5).add(Aspect.getAspect("praecantatio"), 5)
                        .add(Aspect.getAspect("lucrum"), 5).add(Aspect.getAspect("bestia"), 5));
        TCHelper.setResearchComplexity("MB_EssenceOblivion", 3);
        TCHelper.moveResearch("MB_FrameMagic", "MAGICBEES", -4, 0);
        TCHelper.addResearchPrereq("MB_FrameMagic", "MB_Root", false);
        ResearchCategories.getResearch("MB_FrameMagic").setConcealed();
        TCHelper.clearPages("MB_FrameMagic");
        TCHelper.addResearchPage("MB_FrameMagic", new ResearchPage("tc.research_page.MB_FrameMagic.1"));
        ThaumcraftApi.addArcaneCraftingRecipe(
                "MB_FrameMagic",
                getModItem("MagicBees", "frameMagic", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 8).add(Aspect.getAspect("terra"), 8)
                        .add(Aspect.getAspect("aer"), 8).add(Aspect.getAspect("perditio"), 8)
                        .add(Aspect.getAspect("ignis"), 8).add(Aspect.getAspect("aqua"), 8),
                "abc",
                "def",
                "ghi",
                'a',
                "screwSteel",
                'b',
                getModItem("MagicBees", "wax", 1, 1, missing),
                'c',
                "screwSteel",
                'd',
                getModItem("MagicBees", "wax", 1, 0, missing),
                'e',
                getModItem("Forestry", "frameImpregnated", 1, 0, missing),
                'f',
                getModItem("MagicBees", "wax", 1, 2, missing),
                'g',
                "screwSteel",
                'h',
                getModItem("Thaumcraft", "ItemResource", 1, 7, missing),
                'i',
                "screwSteel");
        TCHelper.addResearchPage(
                "MB_FrameMagic",
                new ResearchPage(TCHelper.findArcaneRecipe(getModItem("MagicBees", "frameMagic", 1, 0, missing))));
        TCHelper.setResearchAspects(
                "MB_FrameMagic",
                new AspectList().add(Aspect.getAspect("ordo"), 15).add(Aspect.getAspect("terra"), 12)
                        .add(Aspect.getAspect("aer"), 9).add(Aspect.getAspect("perditio"), 6));
        TCHelper.setResearchComplexity("MB_FrameMagic", 4);
        new ResearchItem(
                "GENTLEFRAME",
                "MAGICBEES",
                new AspectList().add(Aspect.getAspect("victus"), 15).add(Aspect.getAspect("instrumentum"), 15)
                        .add(Aspect.getAspect("aer"), 12).add(Aspect.getAspect("ignis"), 9)
                        .add(Aspect.getAspect("terra"), 6).add(Aspect.getAspect("aqua"), 3),
                -7,
                2,
                3,
                getModItem("MagicBees", "frameGentle", 1, 0, missing)).setParents("MB_FrameMagic", "MB_EssenceLife")
                        .setConcealed().setPages(new ResearchPage("MagicBees.research_page.GENTLEFRAME"))
                        .registerResearchItem();
        ThaumcraftApi.addArcaneCraftingRecipe(
                "GENTLEFRAME",
                getModItem("MagicBees", "frameGentle", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 16).add(Aspect.getAspect("terra"), 16)
                        .add(Aspect.getAspect("aer"), 16).add(Aspect.getAspect("perditio"), 16)
                        .add(Aspect.getAspect("ignis"), 16).add(Aspect.getAspect("aqua"), 16),
                "abc",
                "def",
                "ghi",
                'a',
                "screwAluminium",
                'b',
                getModItem("MagicBees", "miscResources", 1, 7, missing),
                'c',
                "screwAluminium",
                'd',
                getModItem("MagicBees", "miscResources", 1, 18, missing),
                'e',
                getModItem("MagicBees", "frameMagic", 1, 0, missing),
                'f',
                getModItem("MagicBees", "miscResources", 1, 18, missing),
                'g',
                "screwAluminium",
                'h',
                getModItem("MagicBees", "miscResources", 1, 18, missing),
                'i',
                "screwAluminium");
        TCHelper.addResearchPage(
                "GENTLEFRAME",
                new ResearchPage(TCHelper.findArcaneRecipe(getModItem("MagicBees", "frameGentle", 1, 0, missing))));
        new ResearchItem(
                "RESILIENTFRAME",
                "MAGICBEES",
                new AspectList().add(Aspect.getAspect("tutamen"), 15).add(Aspect.getAspect("pannus"), 15)
                        .add(Aspect.getAspect("aer"), 12).add(Aspect.getAspect("ignis"), 9)
                        .add(Aspect.getAspect("terra"), 6).add(Aspect.getAspect("aqua"), 3),
                -7,
                1,
                3,
                getModItem("MagicBees", "frameResilient", 1, 0, missing)).setParents("MB_FrameMagic", "MB_EssenceArmor")
                        .setConcealed().setPages(new ResearchPage("MagicBees.research_page.RESILIENTFRAME"))
                        .registerResearchItem();
        ThaumcraftApi.addArcaneCraftingRecipe(
                "RESILIENTFRAME",
                getModItem("MagicBees", "frameResilient", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 16).add(Aspect.getAspect("terra"), 16)
                        .add(Aspect.getAspect("aer"), 16).add(Aspect.getAspect("perditio"), 16)
                        .add(Aspect.getAspect("ignis"), 16).add(Aspect.getAspect("aqua"), 16),
                "abc",
                "def",
                "ghi",
                'a',
                "screwAluminium",
                'b',
                getModItem("MagicBees", "miscResources", 1, 10, missing),
                'c',
                "screwAluminium",
                'd',
                getModItem("MagicBees", "miscResources", 1, 22, missing),
                'e',
                getModItem("MagicBees", "frameMagic", 1, 0, missing),
                'f',
                getModItem("MagicBees", "miscResources", 1, 22, missing),
                'g',
                "screwAluminium",
                'h',
                getModItem("MagicBees", "miscResources", 1, 22, missing),
                'i',
                "screwAluminium");
        TCHelper.addResearchPage(
                "RESILIENTFRAME",
                new ResearchPage(TCHelper.findArcaneRecipe(getModItem("MagicBees", "frameResilient", 1, 0, missing))));
        new ResearchItem(
                "NECROTICFRAME",
                "MAGICBEES",
                new AspectList().add(Aspect.getAspect("mortuus"), 15).add(Aspect.getAspect("fabrico"), 15)
                        .add(Aspect.getAspect("aer"), 12).add(Aspect.getAspect("ignis"), 9)
                        .add(Aspect.getAspect("terra"), 6).add(Aspect.getAspect("aqua"), 3),
                -7,
                0,
                3,
                getModItem("MagicBees", "frameNecrotic", 1, 0, missing)).setParents("MB_FrameMagic", "MB_EssenceDeath")
                        .setConcealed().setPages(new ResearchPage("MagicBees.research_page.NECROTICFRAME"))
                        .registerResearchItem();
        ThaumcraftApi.addArcaneCraftingRecipe(
                "NECROTICFRAME",
                getModItem("MagicBees", "frameNecrotic", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 16).add(Aspect.getAspect("terra"), 16)
                        .add(Aspect.getAspect("aer"), 16).add(Aspect.getAspect("perditio"), 16)
                        .add(Aspect.getAspect("ignis"), 16).add(Aspect.getAspect("aqua"), 16),
                "abc",
                "def",
                "ghi",
                'a',
                "screwAluminium",
                'b',
                getModItem("MagicBees", "miscResources", 1, 8, missing),
                'c',
                "screwAluminium",
                'd',
                getModItem("MagicBees", "miscResources", 1, 23, missing),
                'e',
                getModItem("MagicBees", "frameMagic", 1, 0, missing),
                'f',
                getModItem("MagicBees", "miscResources", 1, 23, missing),
                'g',
                "screwAluminium",
                'h',
                getModItem("MagicBees", "miscResources", 1, 23, missing),
                'i',
                "screwAluminium");
        TCHelper.addResearchPage(
                "NECROTICFRAME",
                new ResearchPage(TCHelper.findArcaneRecipe(getModItem("MagicBees", "frameNecrotic", 1, 0, missing))));
        new ResearchItem(
                "METABOLICFRAME",
                "MAGICBEES",
                new AspectList().add(Aspect.getAspect("limus"), 15).add(Aspect.getAspect("potentia"), 15)
                        .add(Aspect.getAspect("aer"), 12).add(Aspect.getAspect("ignis"), 9)
                        .add(Aspect.getAspect("terra"), 6).add(Aspect.getAspect("aqua"), 3),
                -7,
                -1,
                3,
                getModItem("MagicBees", "frameMetabolic", 1, 0, missing))
                        .setParents("MB_FrameMagic", "MB_EssenceUnstable").setConcealed()
                        .setPages(new ResearchPage("MagicBees.research_page.METABOLICFRAME")).registerResearchItem();
        ThaumcraftApi.addArcaneCraftingRecipe(
                "METABOLICFRAME",
                getModItem("MagicBees", "frameMetabolic", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 16).add(Aspect.getAspect("terra"), 16)
                        .add(Aspect.getAspect("aer"), 16).add(Aspect.getAspect("perditio"), 16)
                        .add(Aspect.getAspect("ignis"), 16).add(Aspect.getAspect("aqua"), 16),
                "abc",
                "def",
                "ghi",
                'a',
                "screwAluminium",
                'b',
                getModItem("MagicBees", "miscResources", 1, 12, missing),
                'c',
                "screwAluminium",
                'd',
                getModItem("MagicBees", "miscResources", 1, 20, missing),
                'e',
                getModItem("MagicBees", "frameMagic", 1, 0, missing),
                'f',
                getModItem("MagicBees", "miscResources", 1, 20, missing),
                'g',
                "screwAluminium",
                'h',
                getModItem("MagicBees", "miscResources", 1, 20, missing),
                'i',
                "screwAluminium");
        TCHelper.addResearchPage(
                "METABOLICFRAME",
                new ResearchPage(TCHelper.findArcaneRecipe(getModItem("MagicBees", "frameMetabolic", 1, 0, missing))));
        new ResearchItem(
                "TEMPORALFRAME",
                "MAGICBEES",
                new AspectList().add(Aspect.getAspect("tempus"), 15).add(Aspect.getAspect("vacuos"), 15)
                        .add(Aspect.getAspect("aer"), 12).add(Aspect.getAspect("ignis"), 9)
                        .add(Aspect.getAspect("terra"), 6).add(Aspect.getAspect("aqua"), 3),
                -7,
                -2,
                3,
                getModItem("MagicBees", "frameTemporal", 1, 0, missing)).setParents("MB_FrameMagic", "MB_EssenceTime")
                        .setConcealed().setPages(new ResearchPage("MagicBees.research_page.TEMPORALFRAME"))
                        .registerResearchItem();
        ThaumcraftApi.addArcaneCraftingRecipe(
                "TEMPORALFRAME",
                getModItem("MagicBees", "frameTemporal", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 16).add(Aspect.getAspect("terra"), 16)
                        .add(Aspect.getAspect("aer"), 16).add(Aspect.getAspect("perditio"), 16)
                        .add(Aspect.getAspect("ignis"), 16).add(Aspect.getAspect("aqua"), 16),
                "abc",
                "def",
                "ghi",
                'a',
                "screwAluminium",
                'b',
                getModItem("MagicBees", "miscResources", 1, 9, missing),
                'c',
                "screwAluminium",
                'd',
                getModItem("MagicBees", "miscResources", 1, 21, missing),
                'e',
                getModItem("MagicBees", "frameMagic", 1, 0, missing),
                'f',
                getModItem("MagicBees", "miscResources", 1, 21, missing),
                'g',
                "screwAluminium",
                'h',
                getModItem("MagicBees", "miscResources", 1, 22, missing),
                'i',
                "screwAluminium");
        TCHelper.addResearchPage(
                "TEMPORALFRAME",
                new ResearchPage(TCHelper.findArcaneRecipe(getModItem("MagicBees", "frameTemporal", 1, 0, missing))));
        new ResearchItem(
                "OBLIVIONFRAME",
                "MAGICBEES",
                new AspectList().add(Aspect.getAspect("tutamen"), 15).add(Aspect.getAspect("pannus"), 15)
                        .add(Aspect.getAspect("aer"), 12).add(Aspect.getAspect("ignis"), 9)
                        .add(Aspect.getAspect("terra"), 6).add(Aspect.getAspect("aqua"), 3),
                -10,
                0,
                3,
                getModItem("MagicBees", "frameOblivion", 1, 0, missing))
                        .setParents(
                                "INFUSION",
                                "GENTLEFRAME",
                                "RESILIENTFRAME",
                                "NECROTICFRAME",
                                "METABOLICFRAME",
                                "TEMPORALFRAME",
                                "MB_EssenceOblivion")
                        .setConcealed().setPages(new ResearchPage("MagicBees.research_page.OBLIVIONFRAME"))
                        .registerResearchItem();
        ThaumcraftApi.addArcaneCraftingRecipe(
                "OBLIVIONFRAME",
                getModItem("MagicBees", "frameOblivion", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 32).add(Aspect.getAspect("terra"), 32)
                        .add(Aspect.getAspect("aer"), 32).add(Aspect.getAspect("ignis"), 32)
                        .add(Aspect.getAspect("aqua"), 32).add(Aspect.getAspect("perditio"), 32),
                "abc",
                "def",
                "ghi",
                'a',
                "screwStainlessSteel",
                'b',
                getModItem("MagicBees", "miscResources", 1, 11, missing),
                'c',
                "screwStainlessSteel",
                'd',
                getModItem("MagicBees", "miscResources", 1, 19, missing),
                'e',
                getModItem("MagicBees", "frameMagic", 1, 0, missing),
                'f',
                getModItem("MagicBees", "miscResources", 1, 19, missing),
                'g',
                "screwStainlessSteel",
                'h',
                getModItem("MagicBees", "miscResources", 1, 19, missing),
                'i',
                "screwStainlessSteel");
        TCHelper.addResearchPage(
                "OBLIVIONFRAME",
                new ResearchPage(TCHelper.findArcaneRecipe(getModItem("MagicBees", "frameOblivion", 1, 0, missing))));
        TCHelper.clearPages("MB_Scoop");
        TCHelper.addResearchPage("MB_Scoop", new ResearchPage("tc.research_page.MB_Scoop.1"));
        ThaumcraftApi.addArcaneCraftingRecipe(
                "MB_Scoop",
                getModItem("MagicBees", "item.thaumiumScoop", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 10).add(Aspect.getAspect("terra"), 10)
                        .add(Aspect.getAspect("aer"), 10),
                "abc",
                "def",
                "ghi",
                'a',
                getModItem("gregtech", "gt.metaitem.01", 1, 23330, missing),
                'b',
                getModItem("Thaumcraft", "ItemResource", 1, 7, missing),
                'c',
                getModItem("gregtech", "gt.metaitem.01", 1, 23330, missing),
                'd',
                getModItem("gregtech", "gt.metaitem.01", 1, 23330, missing),
                'e',
                getModItem("gregtech", "gt.metaitem.01", 1, 23330, missing),
                'f',
                getModItem("gregtech", "gt.metaitem.01", 1, 23330, missing),
                'g',
                "craftingToolWireCutter",
                'h',
                getModItem("gregtech", "gt.metaitem.01", 1, 23330, missing),
                'i',
                "craftingToolHardHammer");
        TCHelper.addResearchPage(
                "MB_Scoop",
                new ResearchPage(
                        TCHelper.findArcaneRecipe(getModItem("MagicBees", "item.thaumiumScoop", 1, 0, missing))));
        TCHelper.setResearchAspects(
                "MB_Scoop",
                new AspectList().add(Aspect.getAspect("ordo"), 9).add(Aspect.getAspect("terra"), 6)
                        .add(Aspect.getAspect("aer"), 3));
        TCHelper.setResearchComplexity("MB_Scoop", 3);
        TCHelper.clearPages("MB_ScoopVoid");
        TCHelper.addResearchPage("MB_ScoopVoid", new ResearchPage("tc.research_page.MB_ScoopVoid.1"));
        ThaumcraftApi.addArcaneCraftingRecipe(
                "MB_ScoopVoid",
                getModItem("MagicBees", "item.voidScoop", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 20).add(Aspect.getAspect("terra"), 20)
                        .add(Aspect.getAspect("aer"), 20),
                "abc",
                "def",
                "ghi",
                'a',
                getModItem("gregtech", "gt.metaitem.01", 1, 23970, missing),
                'b',
                getModItem("Thaumcraft", "ItemResource", 1, 7, missing),
                'c',
                getModItem("gregtech", "gt.metaitem.01", 1, 23970, missing),
                'd',
                getModItem("gregtech", "gt.metaitem.01", 1, 23970, missing),
                'e',
                getModItem("gregtech", "gt.metaitem.01", 1, 23970, missing),
                'f',
                getModItem("gregtech", "gt.metaitem.01", 1, 23970, missing),
                'g',
                "craftingToolWireCutter",
                'h',
                getModItem("gregtech", "gt.metaitem.01", 1, 23970, missing),
                'i',
                "craftingToolHardHammer");
        TCHelper.addResearchPage(
                "MB_ScoopVoid",
                new ResearchPage(TCHelper.findArcaneRecipe(getModItem("MagicBees", "item.voidScoop", 1, 0, missing))));
        TCHelper.setResearchAspects(
                "MB_ScoopVoid",
                new AspectList().add(Aspect.getAspect("praecantatio"), 15).add(Aspect.getAspect("ordo"), 9)
                        .add(Aspect.getAspect("terra"), 6).add(Aspect.getAspect("aer"), 3));
        TCHelper.setResearchComplexity("MB_ScoopVoid", 3);
        ThaumcraftApi.addWarpToResearch("MB_ScoopVoid", 3);
        TCHelper.clearPages("MB_Grafter");
        TCHelper.addResearchPage("MB_Grafter", new ResearchPage("tc.research_page.MB_Grafter.1"));
        ThaumcraftApi.addArcaneCraftingRecipe(
                "MB_Grafter",
                getModItem("MagicBees", "item.thaumiumGrafter", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 15).add(Aspect.getAspect("terra"), 15)
                        .add(Aspect.getAspect("aer"), 15),
                "abc",
                "def",
                "ghi",
                'b',
                getModItem("gregtech", "gt.metaitem.01", 1, 23330, missing),
                'd',
                "craftingToolFile",
                'e',
                getModItem("gregtech", "gt.metaitem.01", 1, 23330, missing),
                'g',
                getModItem("gregtech", "gt.metaitem.01", 1, 17330, missing),
                'h',
                getModItem("Thaumcraft", "ItemResource", 1, 2, missing),
                'i',
                "craftingToolHardHammer");
        TCHelper.addResearchPage(
                "MB_Grafter",
                new ResearchPage(
                        TCHelper.findArcaneRecipe(getModItem("MagicBees", "item.thaumiumGrafter", 1, 0, missing))));
        TCHelper.setResearchAspects(
                "MB_Grafter",
                new AspectList().add(Aspect.getAspect("ordo"), 12).add(Aspect.getAspect("terra"), 9)
                        .add(Aspect.getAspect("aer"), 6));
        TCHelper.setResearchComplexity("MB_Grafter", 3);
        TCHelper.clearPages("MB_GrafterVoid");
        TCHelper.addResearchPage("MB_GrafterVoid", new ResearchPage("tc.research_page.MB_GrafterVoid.1"));
        ThaumcraftApi.addArcaneCraftingRecipe(
                "MB_GrafterVoid",
                getModItem("MagicBees", "item.voidGrafter", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 30).add(Aspect.getAspect("terra"), 30)
                        .add(Aspect.getAspect("aer"), 30),
                "abc",
                "def",
                "ghi",
                'b',
                getModItem("gregtech", "gt.metaitem.01", 1, 23970, missing),
                'd',
                "craftingToolFile",
                'e',
                getModItem("gregtech", "gt.metaitem.01", 1, 23970, missing),
                'g',
                getModItem("gregtech", "gt.metaitem.01", 1, 17970, missing),
                'h',
                getModItem("Thaumcraft", "ItemResource", 1, 16, missing),
                'i',
                "craftingToolHardHammer");
        TCHelper.addResearchPage(
                "MB_GrafterVoid",
                new ResearchPage(
                        TCHelper.findArcaneRecipe(getModItem("MagicBees", "item.voidGrafter", 1, 0, missing))));
        TCHelper.setResearchAspects(
                "MB_GrafterVoid",
                new AspectList().add(Aspect.getAspect("praecantatio"), 15).add(Aspect.getAspect("ordo"), 12)
                        .add(Aspect.getAspect("terra"), 9).add(Aspect.getAspect("aer"), 6));
        TCHelper.setResearchComplexity("MB_GrafterVoid", 3);
        ThaumcraftApi.addWarpToResearch("MB_GrafterVoid", 3);
        new ResearchItem(
                "ENCHANTEDEARTH",
                "MAGICBEES",
                new AspectList().add(Aspect.getAspect("terra"), 9).add(Aspect.getAspect("victus"), 9)
                        .add(Aspect.getAspect("sano"), 6).add(Aspect.getAspect("ordo"), 3),
                5,
                -3,
                2,
                getModItem("MagicBees", "magicbees.enchantedEarth", 1, 0, missing)).setParents("MB_EssenceLife")
                        .setConcealed().setPages(new ResearchPage("MagicBees.research_page.ENCHANTEDEARTH"))
                        .registerResearchItem();
        ThaumcraftApi.addArcaneCraftingRecipe(
                "ENCHANTEDEARTH",
                getModItem("MagicBees", "magicbees.enchantedEarth", 1, 0, missing),
                new AspectList().add(Aspect.getAspect("ordo"), 10).add(Aspect.getAspect("aqua"), 10)
                        .add(Aspect.getAspect("terra"), 10),
                "abc",
                "def",
                "ghi",
                'a',
                getModItem("minecraft", "dirt", 1, 0, missing),
                'c',
                getModItem("minecraft", "dirt", 1, 0, missing),
                'e',
                getModItem("MagicBees", "miscResources", 1, 7, missing),
                'g',
                getModItem("minecraft", "dirt", 1, 0, missing),
                'i',
                getModItem("minecraft", "dirt", 1, 0, missing));
        TCHelper.addResearchPage(
                "ENCHANTEDEARTH",
                new ResearchPage(
                        TCHelper.findArcaneRecipe(getModItem("MagicBees", "magicbees.enchantedEarth", 1, 0, missing))));
        TCHelper.refreshResearchPages("MB_VisAuraProvider");
        TCHelper.refreshResearchPages("MB_EssenceLife");
        TCHelper.refreshResearchPages("MB_EssenceDeath");
        TCHelper.refreshResearchPages("MB_EssenceTime");
        TCHelper.refreshResearchPages("MB_EssenceArmor");
        TCHelper.refreshResearchPages("MB_EssenceUnstable");
        TCHelper.refreshResearchPages("MB_DimensionalSingularity");
        TCHelper.refreshResearchPages("MB_Scoop");
        TCHelper.refreshResearchPages("MB_ScoopVoid");
        TCHelper.refreshResearchPages("MB_GrafterVoid");
    }
}
