package com.dreammaster.scripts;

import static gregtech.api.util.GT_ModHandler.getModItem;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sAlloySmelterRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sAssemblerRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sExtruderRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sMaceratorRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sPressRecipes;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import com.dreammaster.avaritia.AvaritiaHelper;
import com.dreammaster.thaumcraft.TCHelper;

import fox.spiteful.avaritia.crafting.CompressorManager;
import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Mods;

public class ScriptAvaritia implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Avaritia";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(
                Mods.Avaritia.ID,
                Mods.Thaumcraft.ID,
                Mods.ThaumicBases.ID,
                Mods.Gadomancy.ID,
                Mods.TaintedMagic.ID);
    }

    @Override
    public void loadRecipes() {

        addShapedRecipe(
                getModItem("Avaritia", "Dire_Crafting", 1, 0, missing),
                new Object[] { getModItem("gregtech", "gt.metaitem.01", 1, 32652, missing),
                        getModItem("Avaritia", "Triple_Craft", 1, 0, missing),
                        getModItem("gregtech", "gt.metaitem.01", 1, 32652, missing), "waferAdvanced",
                        getModItem("Avaritia", "Crystal_Matrix", 1, 0, missing), "waferAdvanced",
                        getModItem("gregtech", "gt.metaitem.01", 1, 32642, missing), "circuitElite",
                        getModItem("gregtech", "gt.metaitem.01", 1, 32642, missing) });
        addShapedRecipe(
                getModItem("Avaritia", "Resource", 1, 0, missing),
                new Object[] { "screwDiamond", "plateDiamond", "screwDiamond", "plateDiamond",
                        getModItem("dreamcraft", "item.StainlessSteelBars", 1, 0, missing), "plateDiamond",
                        "screwDiamond", "plateDiamond", "screwDiamond" });

        AvaritiaHelper.removeExtremeCraftingRecipe(getModItem("Avaritia", "Neutron_Collector", 1, 0, missing));
        AvaritiaHelper.removeExtremeCraftingRecipe(getModItem("Avaritia", "Neutronium_Compressor", 1, 0, missing));
        AvaritiaHelper
                .removeExtremeCraftingRecipe(getModItem("eternalsingularity", "eternal_singularity", 1, 0, missing));
        AvaritiaHelper.removeExtremeCraftingRecipe(getModItem("Avaritia", "Orb_Armok", 1, 0, missing));
        AvaritiaHelper.removeExtremeCraftingRecipe(getModItem("Avaritia", "Infinity_Sword", 1, 0, missing));
        AvaritiaHelper.removeExtremeCraftingRecipe(getModItem("Avaritia", "Infinity_Pickaxe", 1, 0, missing));
        AvaritiaHelper.removeExtremeCraftingRecipe(getModItem("Avaritia", "Infinity_Shovel", 1, 0, missing));
        AvaritiaHelper.removeExtremeCraftingRecipe(
                createItemStack("Thaumcraft", "WandCasting", 1, 9000, "{cap:\"matrix\",rod:\"infinity\"}", missing));
        AvaritiaHelper.removeExtremeCraftingRecipe(getModItem("Avaritia", "Infinity_Axe", 1, 0, missing));
        AvaritiaHelper.removeExtremeCraftingRecipe(getModItem("Avaritia", "Infinity_Bow", 1, 0, missing));
        AvaritiaHelper.removeExtremeCraftingRecipe(getModItem("Avaritia", "Infinity_Helm", 1, 0, missing));
        AvaritiaHelper.removeExtremeCraftingRecipe(getModItem("Avaritia", "Infinity_Chest", 1, 0, missing));
        AvaritiaHelper.removeExtremeCraftingRecipe(getModItem("Avaritia", "Infinity_Pants", 1, 0, missing));
        AvaritiaHelper.removeExtremeCraftingRecipe(getModItem("Avaritia", "Infinity_Shoes", 1, 0, missing));
        AvaritiaHelper.removeExtremeCraftingRecipe(getModItem("Avaritia", "Skull_Sword", 1, 0, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("eternalsingularity", "combined_singularity", 1, 0, missing),
                "---------",
                "--aa--a--",
                "-a--b--a-",
                "---cde-a-",
                "--fdgdh--",
                "-a-idj---",
                "-a--k--a-",
                "--a--aa--",
                "---------",
                'a',
                "blockBlackPlutonium",
                'b',
                getModItem("Avaritia", "Singularity", 1, 2, missing),
                'c',
                getModItem("Avaritia", "Singularity", 1, 1, missing),
                'd',
                getModItem("ExtraUtilities", "block_bedrockium", 1, 0, missing),
                'e',
                getModItem("Avaritia", "Singularity", 1, 8, missing),
                'f',
                getModItem("Avaritia", "Singularity", 1, 0, missing),
                'g',
                getModItem("Avaritia", "Singularity", 1, 3, missing),
                'h',
                getModItem("Avaritia", "Singularity", 1, 6, missing),
                'i',
                getModItem("Avaritia", "Singularity", 1, 7, missing),
                'j',
                getModItem("Avaritia", "Singularity", 1, 5, missing),
                'k',
                getModItem("Avaritia", "Singularity", 1, 4, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("eternalsingularity", "combined_singularity", 1, 1, missing),
                "---------",
                "--aa--a--",
                "-a--b--a-",
                "---cde-a-",
                "--fdgdh--",
                "-a-idj---",
                "-a--k--a-",
                "--a--aa--",
                "---------",
                'a',
                "blockBlackPlutonium",
                'b',
                getModItem("Avaritia", "Singularity", 1, 9, missing),
                'c',
                getModItem("Avaritia", "Singularity", 1, 10, missing),
                'd',
                getModItem("ExtraUtilities", "block_bedrockium", 1, 0, missing),
                'e',
                getModItem("universalsingularities", "universal.vanilla.singularity", 1, 0, missing),
                'f',
                getModItem("universalsingularities", "universal.vanilla.singularity", 1, 2, missing),
                'g',
                getModItem("universalsingularities", "universal.vanilla.singularity", 1, 1, missing),
                'h',
                getModItem("universalsingularities", "universal.general.singularity", 1, 3, missing),
                'i',
                getModItem("universalsingularities", "universal.general.singularity", 1, 0, missing),
                'j',
                getModItem("universalsingularities", "universal.general.singularity", 1, 1, missing),
                'k',
                getModItem("universalsingularities", "universal.general.singularity", 1, 2, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("eternalsingularity", "combined_singularity", 1, 2, missing),
                "---------",
                "--aa--a--",
                "-a--b--a-",
                "---cde-a-",
                "--fdgdh--",
                "-a-idj---",
                "-a--k--a-",
                "--a--aa--",
                "---------",
                'a',
                "blockBlackPlutonium",
                'b',
                getModItem("universalsingularities", "universal.general.singularity", 1, 4, missing),
                'c',
                getModItem("universalsingularities", "universal.general.singularity", 1, 5, missing),
                'd',
                getModItem("ExtraUtilities", "block_bedrockium", 1, 0, missing),
                'e',
                getModItem("universalsingularities", "universal.general.singularity", 1, 6, missing),
                'f',
                getModItem("universalsingularities", "universal.general.singularity", 1, 7, missing),
                'g',
                getModItem("universalsingularities", "universal.general.singularity", 1, 8, missing),
                'h',
                getModItem("universalsingularities", "universal.general.singularity", 1, 9, missing),
                'i',
                getModItem("universalsingularities", "universal.general.singularity", 1, 10, missing),
                'j',
                getModItem("universalsingularities", "universal.general.singularity", 1, 11, missing),
                'k',
                getModItem("universalsingularities", "universal.general.singularity", 1, 12, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("eternalsingularity", "combined_singularity", 1, 3, missing),
                "---------",
                "--aa--a--",
                "-a--b--a-",
                "---cde-a-",
                "--fdgdh--",
                "-a-idj---",
                "-a--k--a-",
                "--a--aa--",
                "---------",
                'a',
                "blockBlackPlutonium",
                'b',
                getModItem("universalsingularities", "universal.general.singularity", 1, 13, missing),
                'c',
                getModItem("universalsingularities", "universal.general.singularity", 1, 14, missing),
                'd',
                getModItem("ExtraUtilities", "block_bedrockium", 1, 0, missing),
                'e',
                getModItem("universalsingularities", "universal.general.singularity", 1, 15, missing),
                'f',
                getModItem("universalsingularities", "universal.general.singularity", 1, 16, missing),
                'g',
                getModItem("universalsingularities", "universal.general.singularity", 1, 17, missing),
                'h',
                getModItem("universalsingularities", "universal.general.singularity", 1, 18, missing),
                'i',
                getModItem("universalsingularities", "universal.general.singularity", 1, 19, missing),
                'j',
                getModItem("universalsingularities", "universal.general.singularity", 1, 20, missing),
                'k',
                getModItem("universalsingularities", "universal.general.singularity", 1, 21, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("eternalsingularity", "combined_singularity", 1, 4, missing),
                "---------",
                "--aa--a--",
                "-a--b--a-",
                "---cde-a-",
                "--fdgdh--",
                "-a-idj---",
                "-a--k--a-",
                "--a--aa--",
                "---------",
                'a',
                "blockBlackPlutonium",
                'b',
                getModItem("universalsingularities", "universal.general.singularity", 1, 22, missing),
                'c',
                getModItem("universalsingularities", "universal.general.singularity", 1, 23, missing),
                'd',
                getModItem("ExtraUtilities", "block_bedrockium", 1, 0, missing),
                'e',
                getModItem("universalsingularities", "universal.general.singularity", 1, 24, missing),
                'f',
                getModItem("universalsingularities", "universal.general.singularity", 1, 25, missing),
                'g',
                getModItem("universalsingularities", "universal.general.singularity", 1, 26, missing),
                'h',
                getModItem("universalsingularities", "universal.general.singularity", 1, 27, missing),
                'i',
                getModItem("universalsingularities", "universal.general.singularity", 1, 28, missing),
                'j',
                getModItem("universalsingularities", "universal.general.singularity", 1, 29, missing),
                'k',
                getModItem("universalsingularities", "universal.general.singularity", 1, 30, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("eternalsingularity", "combined_singularity", 1, 5, missing),
                "---------",
                "--aa--a--",
                "-a--b--a-",
                "---cde-a-",
                "--fdgdh--",
                "-a-idj---",
                "-a--k--a-",
                "--a--aa--",
                "---------",
                'a',
                "blockBlackPlutonium",
                'b',
                getModItem("universalsingularities", "universal.draconicEvolution.singularity", 1, 0, missing),
                'c',
                getModItem("universalsingularities", "universal.draconicEvolution.singularity", 1, 1, missing),
                'd',
                getModItem("ExtraUtilities", "block_bedrockium", 1, 0, missing),
                'e',
                getModItem("universalsingularities", "universal.enderIO.singularity", 1, 0, missing),
                'f',
                getModItem("universalsingularities", "universal.enderIO.singularity", 1, 1, missing),
                'g',
                getModItem("universalsingularities", "universal.enderIO.singularity", 1, 2, missing),
                'h',
                getModItem("universalsingularities", "universal.enderIO.singularity", 1, 3, missing),
                'i',
                getModItem("universalsingularities", "universal.enderIO.singularity", 1, 4, missing),
                'j',
                getModItem("universalsingularities", "universal.enderIO.singularity", 1, 5, missing),
                'k',
                getModItem("universalsingularities", "universal.enderIO.singularity", 1, 6, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("eternalsingularity", "combined_singularity", 1, 6, missing),
                "---------",
                "--aa--a--",
                "-a--b--a-",
                "---cde-a-",
                "--fdgdh--",
                "-a-idj---",
                "-a--k--a-",
                "--a--aa--",
                "---------",
                'a',
                "blockBlackPlutonium",
                'b',
                getModItem("universalsingularities", "universal.enderIO.singularity", 1, 7, missing),
                'c',
                getModItem("universalsingularities", "universal.extraUtilities.singularity", 1, 0, missing),
                'd',
                getModItem("ExtraUtilities", "block_bedrockium", 1, 0, missing),
                'e',
                getModItem("universalsingularities", "universal.projectRed.singularity", 1, 0, missing),
                'f',
                getModItem("universalsingularities", "universal.tinkersConstruct.singularity", 1, 0, missing),
                'g',
                getModItem("universalsingularities", "universal.tinkersConstruct.singularity", 1, 1, missing),
                'h',
                getModItem("universalsingularities", "universal.tinkersConstruct.singularity", 1, 2, missing),
                'i',
                getModItem("universalsingularities", "universal.tinkersConstruct.singularity", 1, 3, missing),
                'j',
                getModItem("universalsingularities", "universal.tinkersConstruct.singularity", 1, 4, missing),
                'k',
                getModItem("universalsingularities", "universal.tinkersConstruct.singularity", 1, 6, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("eternalsingularity", "eternal_singularity", 1, 0, missing),
                "-----a---",
                "--bb--b--",
                "-b--c--b-",
                "a--dae-b-",
                "--aafaa--",
                "-b-gah--a",
                "-b--i--b-",
                "--b--bb--",
                "---a-----",
                'a',
                getModItem("Avaritia", "Resource_Block", 1, 0, missing),
                'b',
                "blockBlackPlutonium",
                'c',
                getModItem("eternalsingularity", "combined_singularity", 1, 0, missing),
                'd',
                getModItem("eternalsingularity", "combined_singularity", 1, 1, missing),
                'e',
                getModItem("eternalsingularity", "combined_singularity", 1, 2, missing),
                'f',
                getModItem("eternalsingularity", "combined_singularity", 1, 3, missing),
                'g',
                getModItem("eternalsingularity", "combined_singularity", 1, 4, missing),
                'h',
                getModItem("eternalsingularity", "combined_singularity", 1, 5, missing),
                'i',
                getModItem("eternalsingularity", "combined_singularity", 1, 6, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("Avaritia", "Orb_Armok", 1, 0, missing),
                "---aaa---",
                "--ababa--",
                "--aacaa--",
                "-dababad-",
                "ddeafagdd",
                "-dddhddd-",
                "---ddd---",
                "---------",
                "---------",
                'a',
                "plateInfinity",
                'b',
                getModItem("ForbiddenMagic", "EldritchOrb", 1, 0, missing),
                'c',
                getModItem("BloodArsenal", "blood_infused_diamond_block", 1, 0, missing),
                'd',
                "plateCosmicNeutronium",
                'e',
                getModItem("TaintedMagic", "ItemFocusTime", 1, 0, missing),
                'f',
                getModItem("Avaritia", "Resource", 1, 5, missing),
                'g',
                getModItem("TaintedMagic", "ItemFocusEldritch", 1, 0, missing),
                'h',
                getModItem("TConstruct", "heavyPlate", 1, 500, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("Avaritia", "Infinity_Sword", 1, 0, missing),
                "-------ab",
                "------aca",
                "-----ada-",
                "----aea--",
                "-f-aga---",
                "--fha----",
                "--if-----",
                "-i--f----",
                "j--------",
                'a',
                "plateInfinity",
                'b',
                createItemStack(
                        "TGregworks",
                        "tGregToolPartLargeSwordBlade",
                        1,
                        1511,
                        "{material:\"Neutronium\"}",
                        missing),
                'c',
                getModItem("TaintedMagic", "ItemPrimordialEdge", 1, 0, missing),
                'd',
                getModItem("IC2", "itemNanoSaber", 1, 32767, missing),
                'e',
                getModItem("ThaumicTinkerer", "ichorSwordGem", 1, 32767, missing),
                'f',
                getModItem("TaintedMagic", "ItemMaterial", 1, 5, missing),
                'g',
                getModItem("Avaritia", "Skull_Sword", 1, 0, missing),
                'h',
                getModItem("DraconicEvolution", "draconicDistructionStaff", 1, 32767, missing),
                'i',
                "blockCosmicNeutronium",
                'j',
                getModItem("Avaritia", "Resource", 1, 5, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                createItemStack("Avaritia", "Infinity_Pickaxe", 1, 0, "{ench:[0:{lvl:10s,id:35s}],hammer:0b}", missing),
                "-aaaaaaa-",
                "abbcdebba",
                "aa-fgh-aa",
                "----i----",
                "----i----",
                "----i----",
                "----i----",
                "----i----",
                "---jkj---",
                'a',
                "plateInfinity",
                'b',
                createItemStack("TGregworks", "tGregToolPartLargePlate", 1, 1511, "{material:\"Neutronium\"}", missing),
                'c',
                getModItem("BloodArsenal", "blood_infused_pickaxe_diamond", 1, 32767, missing),
                'd',
                getModItem("DraconicEvolution", "draconicPickaxe", 1, 0, missing),
                'e',
                getModItem("GraviSuite", "advDDrill", 1, 1, missing),
                'f',
                getModItem("TaintedMagic", "ItemFocusEldritch", 1, 0, missing),
                'g',
                getModItem("ThaumicTinkerer", "ichorPickGem", 1, 32767, missing),
                'h',
                getModItem("TaintedMagic", "ItemFocusTime", 1, 0, missing),
                'i',
                "blockCosmicNeutronium",
                'j',
                getModItem("Avaritia", "big_pearl", 1, 0, missing),
                'k',
                getModItem("Avaritia", "Resource", 1, 5, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                createItemStack("Avaritia", "Infinity_Shovel", 1, 0, "{destroyer:0b}", missing),
                "------aaa",
                "-----abca",
                "------dea",
                "-----f-a-",
                "----f----",
                "---f-----",
                "-gf------",
                "hfi------",
                "jh-------",
                'a',
                "plateInfinity",
                'b',
                getModItem("GraviSuite", "advDDrill", 1, 1, missing),
                'c',
                getModItem("DraconicEvolution", "draconicShovel", 1, 0, missing),
                'd',
                getModItem("ThaumicTinkerer", "ichorShovelGem", 1, 32767, missing),
                'e',
                getModItem("BloodArsenal", "blood_infused_shovel_diamond", 1, 0, missing),
                'f',
                "blockCosmicNeutronium",
                'g',
                getModItem("TaintedMagic", "ItemFocusEldritch", 1, 0, missing),
                'h',
                getModItem("Avaritia", "big_pearl", 1, 0, missing),
                'i',
                getModItem("TaintedMagic", "ItemFocusTime", 1, 0, missing),
                'j',
                getModItem("Avaritia", "Resource", 1, 5, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                createItemStack("Thaumcraft", "WandCasting", 1, 9000, "{cap:\"matrix\",rod:\"infinity\"}", missing),
                "--a------",
                "-aba-----",
                "acdef----",
                "-aegeh---",
                "--iegej--",
                "---kegea-",
                "----ledma",
                "-----aba-",
                "------a--",
                'a',
                "plateInfinity",
                'b',
                getModItem("ThaumicTinkerer", "blockTalisman", 1, 0, missing),
                'c',
                getModItem("TaintedMagic", "ItemFocusEldritch", 1, 0, missing),
                'd',
                getModItem("Avaritia", "Resource", 1, 5, missing),
                'e',
                "plateCosmicNeutronium",
                'f',
                getModItem("AWWayofTime", "dawnScribeTool", 1, 0, missing),
                'g',
                createItemStack(
                        "Thaumcraft",
                        "WandCasting",
                        1,
                        562,
                        "{cap:\"ICHOR\",rod:\"ICHORCLOTH\",sceptre:1b}",
                        missing),
                'h',
                getModItem("AWWayofTime", "waterScribeTool", 1, 0, missing),
                'i',
                getModItem("AWWayofTime", "duskScribeTool", 1, 0, missing),
                'j',
                getModItem("AWWayofTime", "airScribeTool", 1, 0, missing),
                'k',
                getModItem("AWWayofTime", "fireScribeTool", 1, 0, missing),
                'l',
                getModItem("AWWayofTime", "earthScribeTool", 1, 0, missing),
                'm',
                getModItem("TaintedMagic", "ItemFocusTime", 1, 0, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("Avaritia", "Infinity_Axe", 1, 0, missing),
                "-a-------",
                "abaaa----",
                "acda-----",
                "-ae------",
                "--e------",
                "--e------",
                "--e------",
                "--e------",
                "-fgh-----",
                'a',
                "plateInfinity",
                'b',
                getModItem("GraviSuite", "advChainsaw", 1, 1, missing),
                'c',
                getModItem("ThaumicTinkerer", "ichorAxeGem", 1, 32767, missing),
                'd',
                getModItem("DraconicEvolution", "draconicAxe", 1, 0, missing),
                'e',
                "blockCosmicNeutronium",
                'f',
                getModItem("TaintedMagic", "ItemFocusEldritch", 1, 0, missing),
                'g',
                getModItem("Avaritia", "Resource", 1, 5, missing),
                'h',
                getModItem("TaintedMagic", "ItemFocusTime", 1, 0, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("Avaritia", "Infinity_Bow", 1, 0, missing),
                "---aab---",
                "--a-c----",
                "-a--c----",
                "a---c----",
                "d---ef---",
                "a---c----",
                "-a--c----",
                "--a-c----",
                "---aab---",
                'a',
                "plateInfinity",
                'b',
                getModItem("TaintedMagic", "ItemFocusMeteorology", 1, 0, missing),
                'c',
                getModItem("ThaumicTinkerer", "kamiResource", 1, 1, missing),
                'd',
                getModItem("Avaritia", "Resource", 1, 5, missing),
                'e',
                getModItem("DraconicEvolution", "draconicBow", 1, 0, missing),
                'f',
                getModItem("TaintedMagic", "ItemFocusTime", 1, 0, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("Avaritia", "Infinity_Helm", 1, 0, missing),
                "--aaaaa--",
                "-abbcbba-",
                "-a-ded-a-",
                "-abfghba-",
                "-abbibba-",
                "-abjklba-",
                "---------",
                "---------",
                "---------",
                'a',
                "plateCosmicNeutronium",
                'b',
                "plateInfinity",
                'c',
                getModItem("AdvancedSolarPanel", "ultimate_solar_helmet", 1, 32767, missing),
                'd',
                getModItem("Avaritia", "Resource", 1, 5, missing),
                'e',
                getModItem("ThaumicTinkerer", "ichorclothHelmGem", 1, 32767, missing),
                'f',
                createItemStack("BloodArsenal", "life_imbued_helmet", 1, 0, "{LPStored:0}", missing),
                'g',
                getModItem("DraconicEvolution", "draconicHelm", 1, 0, missing),
                'h',
                getModItem("AWWayofTime", "sanguineHelmet", 1, 0, missing),
                'i',
                getModItem("GalaxySpace", "item.spacesuit_helmetglasses", 1, 0, missing),
                'j',
                getModItem("TaintedMagic", "ItemFocusTime", 1, 0, missing),
                'k',
                getModItem("BloodArsenal", "sigil_of_divinity", 1, 0, missing),
                'l',
                getModItem("EMT", "ShieldFocus", 1, 0, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("Avaritia", "Infinity_Chest", 1, 0, missing),
                "-aa---aa-",
                "aba---aba",
                "abba-abba",
                "-abbcbba-",
                "-abdefba-",
                "-abghiba-",
                "-abjklba-",
                "-abbbbba-",
                "--aaaaa--",
                'a',
                "plateCosmicNeutronium",
                'b',
                "plateInfinity",
                'c',
                getModItem("GraviSuite", "graviChestPlate", 1, 32767, missing),
                'd',
                createItemStack("BloodArsenal", "life_imbued_chestplate", 1, 0, "{LPStored:0}", missing),
                'e',
                getModItem("ThaumicTinkerer", "ichorclothChestGem", 1, 32767, missing),
                'f',
                getModItem("AWWayofTime", "sanguineRobe", 1, 0, missing),
                'g',
                getModItem("GalaxySpace", "item.spacesuit_jetplate", 1, 0, missing),
                'h',
                getModItem("Avaritia", "Resource", 1, 5, missing),
                'i',
                getModItem("DraconicEvolution", "draconicChest", 1, 0, missing),
                'j',
                getModItem("EMT", "ShieldFocus", 1, 0, missing),
                'k',
                getModItem("BloodArsenal", "sigil_of_divinity", 1, 0, missing),
                'l',
                getModItem("TaintedMagic", "ItemFocusTime", 1, 0, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("Avaritia", "Infinity_Pants", 1, 0, missing),
                "aaaaaaaaa",
                "abcdefgha",
                "aiiijiiia",
                "aiaakaaia",
                "aia---aia",
                "ala---ala",
                "aia---aia",
                "aia---aia",
                "aaa---aaa",
                'a',
                "plateCosmicNeutronium",
                'b',
                getModItem("TaintedMagic", "ItemFocusTime", 1, 0, missing),
                'c',
                getModItem("GalaxySpace", "item.spacesuit_leg", 1, 0, missing),
                'd',
                createItemStack("BloodArsenal", "life_imbued_leggings", 1, 0, "{LPStored:0}", missing),
                'e',
                getModItem("BloodArsenal", "sigil_of_divinity", 1, 0, missing),
                'f',
                getModItem("ThaumicTinkerer", "ichorclothLegsGem", 1, 32767, missing),
                'g',
                getModItem("IC2", "itemArmorQuantumLegs", 1, 32767, missing),
                'h',
                getModItem("EMT", "ShieldFocus", 1, 0, missing),
                'i',
                "plateInfinity",
                'j',
                getModItem("DraconicEvolution", "draconicLeggs", 1, 0, missing),
                'k',
                getModItem("Avaritia", "Resource", 1, 5, missing),
                'l',
                getModItem("Avaritia", "big_pearl", 1, 0, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("Avaritia", "Infinity_Shoes", 1, 0, missing),
                "---------",
                "---------",
                "-aaa-aaa-",
                "-aba-aba-",
                "-aba-aba-",
                "aaba-abaa",
                "acda-aefa",
                "agha-aija",
                "bbbbkbbbb",
                'a',
                "plateCosmicNeutronium",
                'b',
                "plateInfinity",
                'c',
                getModItem("ThaumicTinkerer", "ichorclothBootsGem", 1, 32767, missing),
                'd',
                getModItem("DraconicEvolution", "draconicBoots", 1, 0, missing),
                'e',
                getModItem("IC2", "itemArmorQuantumBoots", 1, 32767, missing),
                'f',
                createItemStack("BloodArsenal", "life_imbued_boots", 1, 0, "{LPStored:0}", missing),
                'g',
                getModItem("TaintedMagic", "ItemFocusTime", 1, 0, missing),
                'h',
                getModItem("AWWayofTime", "sanguineBoots", 1, 0, missing),
                'i',
                getModItem("GalaxySpace", "item.spacesuit_gravityboots", 1, 0, missing),
                'j',
                getModItem("EMT", "ShieldFocus", 1, 0, missing),
                'k',
                getModItem("BloodArsenal", "sigil_of_divinity", 1, 0, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("Avaritia", "Neutron_Collector", 1, 0, missing),
                "abcdedcba",
                "b---c---b",
                "c-f-g-f-c",
                "d-h-g-h-d",
                "eijikijie",
                "d-h-g-h-d",
                "c-f-g-f-c",
                "b---c---b",
                "abcdedcba",
                'a',
                "blockBlackPlutonium",
                'b',
                getModItem("Avaritia", "Resource_Block", 1, 0, missing),
                'c',
                getModItem("TConstruct", "heavyPlate", 1, 315, missing),
                'd',
                getModItem("TConstruct", "heavyPlate", 1, 500, missing),
                'e',
                getModItem("Avaritia", "Crystal_Matrix", 1, 0, missing),
                'f',
                getModItem("gregtech", "gt.metaitem.01", 1, 32697, missing),
                'g',
                "circuitInfinite",
                'h',
                getModItem("gregtech", "gt.metaitem.01", 1, 32687, missing),
                'i',
                "plateInfinity",
                'j',
                getModItem("gregtech", "gt.metaitem.01", 1, 32608, missing),
                'k',
                getModItem("Avaritia", "Resource", 1, 5, missing));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                getModItem("Avaritia", "Neutronium_Compressor", 1, 0, missing),
                "abacdcaba",
                "c-e-f-e-c",
                "dghgfghgd",
                "b-eijie-b",
                "bffjkjffb",
                "b-eijie-b",
                "dghgfghgd",
                "c-e-f-e-c",
                "abacdcaba",
                'a',
                getModItem("Avaritia", "Resource_Block", 1, 0, missing),
                'b',
                getModItem("TConstruct", "heavyPlate", 1, 500, missing),
                'c',
                getModItem("Avaritia", "Resource", 1, 1, missing),
                'd',
                getModItem("TConstruct", "heavyPlate", 1, 315, missing),
                'e',
                getModItem("gregtech", "gt.metaitem.01", 1, 32647, missing),
                'f',
                createItemStack(
                        "TGregworks",
                        "tGregToolPartLargePlate",
                        1,
                        1657,
                        "{material:\"BlackPlutonium\"}",
                        missing),
                'g',
                getModItem("gregtech", "gt.metaitem.01", 1, 32637, missing),
                'h',
                getModItem("gregtech", "gt.metaitem.01", 1, 32608, missing),
                'i',
                "circuitInfinite",
                'j',
                "plateNeutronium",
                'k',
                getModItem("gregtech", "gt.blockmachines", 1, 10812, missing));

        CompressorManager.addRecipe(
                getModItem("Avaritia", "Resource", 1, 5, missing),
                64,
                getModItem("gregtech", "gt.metaitem.01", 1, 2394));

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("Avaritia", "Resource", 1, 4, missing),
                        getModItem("gregtech", "gt.metaitem.01", 0, 32309, missing))
                .itemOutputs(getModItem("Avaritia", "Resource", 9, 3, missing)).noFluidInputs().noFluidOutputs()
                .duration(2400).eut(480).addTo(sAlloySmelterRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("Avaritia", "Resource_Block", 1, 0, missing),
                        getModItem("gregtech", "gt.metaitem.01", 0, 32306, missing))
                .itemOutputs(getModItem("Avaritia", "Resource", 9, 4, missing)).noFluidInputs().noFluidOutputs()
                .duration(4800).eut(480).addTo(sAlloySmelterRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("Avaritia", "Resource", 4, 0, missing),
                        getModItem("minecraft", "nether_star", 2, 0, missing))
                .itemOutputs(getModItem("Avaritia", "Resource", 1, 1, missing)).noFluidInputs().noFluidOutputs()
                .duration(1200).eut(480).addTo(sAssemblerRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("Avaritia", "Resource_Block", 1, 0, missing),
                        getModItem("gregtech", "gt.metaitem.01", 0, 32355, missing))
                .itemOutputs(getModItem("Avaritia", "Resource", 9, 4, missing)).noFluidInputs().noFluidOutputs()
                .duration(1000).eut(524000).addTo(sExtruderRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("Avaritia", "Resource_Block", 1, 1, missing),
                        getModItem("gregtech", "gt.metaitem.01", 0, 32355, missing))
                .itemOutputs(getModItem("Avaritia", "Resource", 9, 6, missing)).noFluidInputs().noFluidOutputs()
                .duration(1200).eut(524000).addTo(sExtruderRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("Avaritia", "Resource", 1, 6, missing),
                        getModItem("gregtech", "gt.metaitem.01", 0, 32350, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.01", 1, 17397, missing)).noFluidInputs()
                .noFluidOutputs().duration(4000).eut(524000).addTo(sExtruderRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 1, 11397, missing),
                        getModItem("gregtech", "gt.metaitem.01", 0, 32350, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.01", 1, 17397, missing)).noFluidInputs()
                .noFluidOutputs().duration(4000).eut(524000).addTo(sExtruderRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("Avaritia", "Resource", 1, 4, missing),
                        getModItem("gregtech", "gt.metaitem.01", 0, 32350, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.01", 1, 17982, missing)).noFluidInputs()
                .noFluidOutputs().duration(3000).eut(524000).addTo(sExtruderRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 1, 11982, missing),
                        getModItem("gregtech", "gt.metaitem.01", 0, 32350, missing))
                .itemOutputs(getModItem("gregtech", "gt.metaitem.01", 1, 17982, missing)).noFluidInputs()
                .noFluidOutputs().duration(3000).eut(524000).addTo(sExtruderRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 4, 17500, missing),
                        getModItem("dreamcraft", "item.StainlessSteelBars", 1, 0, missing))
                .itemOutputs(getModItem("Avaritia", "Resource", 1, 0, missing)).noFluidInputs().noFluidOutputs()
                .duration(200).eut(120).addTo(sPressRecipes);
        GT_Values.RA.stdBuilder().itemInputs(getModItem("Avaritia", "Resource", 1, 3, missing))
                .itemOutputs(getModItem("Avaritia", "Resource", 9, 2, missing)).outputChances(10000).noFluidInputs()
                .noFluidOutputs().duration(300).eut(2).addTo(sMaceratorRecipes);

        TCHelper.removeInfusionRecipe(getModItem("Avaritia", "Akashic_Record", 1, 0, missing));
        ThaumcraftApi.addInfusionCraftingRecipe(
                "AKASHIC",
                getModItem("Avaritia", "Akashic_Record", 1, 0, missing),
                24,
                new AspectList().add(Aspect.getAspect("praecantatio"), 512).add(Aspect.getAspect("cognitio"), 128)
                        .add(Aspect.getAspect("sensus"), 96).add(Aspect.getAspect("luxuria"), 96)
                        .add(Aspect.getAspect("tempus"), 64).add(Aspect.getAspect("terminus"), 128),
                getModItem("gregtech", "gt.metaitem.01", 1, 17397, missing),
                new ItemStack[] { getModItem("TaintedMagic", "ItemFocusTime", 1, 0, missing),
                        getModItem("thaumicbases", "knoseFragment", 1, 6, missing),
                        getModItem("thaumicbases", "knoseFragment", 1, 6, missing),
                        getModItem("thaumicbases", "knoseFragment", 1, 6, missing),
                        getModItem("thaumicbases", "knoseFragment", 1, 6, missing),
                        getModItem("Avaritia", "big_pearl", 1, 0, missing),
                        getModItem("gadomancy", "BlockKnowledgeBook", 1, 0, missing),
                        getModItem("Thaumcraft", "ItemThaumonomicon", 1, 0, missing),
                        getModItem("TaintedMagic", "ItemFocusMeteorology", 1, 0, missing),
                        getModItem("Thaumcraft", "ItemEldritchObject", 1, 1, missing),
                        getModItem("gadomancy", "BlockKnowledgeBook", 1, 0, missing),
                        getModItem("Avaritia", "big_pearl", 1, 0, missing),
                        getModItem("thaumicbases", "knoseFragment", 1, 6, missing),
                        getModItem("thaumicbases", "knoseFragment", 1, 6, missing),
                        getModItem("thaumicbases", "knoseFragment", 1, 6, missing),
                        getModItem("thaumicbases", "knoseFragment", 1, 6, missing), });
        TCHelper.refreshResearchPages("AKASHIC");
    }
}
