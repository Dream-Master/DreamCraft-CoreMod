package com.dreammaster.scripts;

import static gregtech.api.GregTechAPI.sBlockOres1;
import static gregtech.api.enums.Mods.AdvancedSolarPanel;
import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.BiomesOPlenty;
import static gregtech.api.enums.Mods.BloodArsenal;
import static gregtech.api.enums.Mods.CropsPlusPlus;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.enums.Mods.MCFrames;
import static gregtech.api.enums.Mods.Minecraft;
import static gregtech.api.enums.Mods.Natura;
import static gregtech.api.enums.Mods.OpenBlocks;
import static gregtech.api.enums.Mods.PamsHarvestCraft;
import static gregtech.api.enums.Mods.ProjectRedExpansion;
import static gregtech.api.enums.Mods.ProjectRedTransmission;
import static gregtech.api.enums.Mods.SGCraft;
import static gregtech.api.enums.Mods.StructureLib;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.enums.Mods.ThaumicBases;
import static gregtech.api.enums.Mods.TinkerConstruct;
import static gregtech.api.recipe.RecipeMaps.alloySmelterRecipes;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.recipe.RecipeMaps.autoclaveRecipes;
import static gregtech.api.recipe.RecipeMaps.extractorRecipes;
import static gregtech.api.recipe.RecipeMaps.extruderRecipes;
import static gregtech.api.recipe.RecipeMaps.formingPressRecipes;
import static gregtech.api.recipe.RecipeMaps.hammerRecipes;
import static gregtech.api.recipe.RecipeMaps.maceratorRecipes;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;
import static gregtech.api.util.GTRecipeConstants.UniversalChemical;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import com.dreammaster.gthandler.CustomItemList;
import com.dreammaster.item.NHItemList;

import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.recipe.RecipeCategories;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import tconstruct.library.TConstructRegistry;
import vexatos.tgregworks.reference.PartTypes;
import vexatos.tgregworks.util.TGregUtils;

public class ScriptGregtech implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Gregtech";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(
                MCFrames.ID,
                ProjectRedExpansion.ID,
                ProjectRedTransmission.ID,
                StructureLib.ID,
                AdvancedSolarPanel.ID,
                BiomesOPlenty.ID,
                BloodArsenal.ID,
                CropsPlusPlus.ID,
                IndustrialCraft2.ID,
                Natura.ID,
                OpenBlocks.ID,
                PamsHarvestCraft.ID,
                SGCraft.ID,
                Thaumcraft.ID,
                ThaumicBases.ID,
                TinkerConstruct.ID);
    }

    @Override
    public void loadRecipes() {
        addShapedRecipe(
                ItemList.Teleporter.get(1L),
                "circuitOptical",
                getModItem(SGCraft.ID, "ic2PowerUnit", 1, 0, missing),
                "circuitOptical",
                getModItem(SGCraft.ID, "ic2Capacitor", 1, 0, missing),
                getModItem(SGCraft.ID, "stargateBase", 1, 0, missing),
                getModItem(SGCraft.ID, "ic2Capacitor", 1, 0, missing),
                getModItem(SGCraft.ID, "ic2Capacitor", 1, 0, missing),
                getModItem(SGCraft.ID, "ocInterface", 1, 0, missing),
                getModItem(SGCraft.ID, "ic2Capacitor", 1, 0, missing));
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.ring, Materials.Wood, 1L),
                "craftingToolKnife",
                null,
                null,
                null,
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1L),
                null);
        addShapelessRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 1L),
                "craftingToolMortar",
                getModItem(TinkerConstruct.ID, "GlassBlock", 1, 0, missing));
        addShapelessRecipe(
                GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Glass, 1L),
                "craftingToolMortar",
                getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing));
        addShapelessRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.MeatRaw, 1L),
                "craftingToolMortar",
                "listAllmeatraw");
        addShapelessRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.MeatRaw, 1L),
                "craftingToolMortar",
                "listAllfishraw");
        addShapelessRecipe(
                getModItem(Minecraft.ID, "sugar", 4, 0, missing),
                "craftingToolMortar",
                getModItem(CropsPlusPlus.ID, "foodBerries", 1, 1, missing));
        addShapelessRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.MeatCooked, 1L),
                "craftingToolMortar",
                "listAllmeatcooked");
        addShapelessRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.MeatCooked, 1L),
                "craftingToolMortar",
                "listAllfishcooked");
        addShapelessRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 2L),
                "craftingToolMortar",
                "logWood");
        addShapelessRecipe(
                GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Brick, 1L),
                "craftingToolMortar",
                getModItem(Minecraft.ID, "brick", 1, 0, missing));
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Plastic, 1L),
                "stickPlastic",
                "craftingToolHardHammer",
                "stickPlastic");
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L),
                "cropBarley",
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L),
                "cropWheat",
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L),
                "cropRye",
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L),
                "cropOats",
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Salt, 1L),
                new ItemStack(sBlockOres1, 1, 817),
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Salt, 1L),
                GTOreDictUnificator.get(OrePrefixes.oreNetherrack, Materials.Salt, 1L),
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Salt, 1L),
                GTOreDictUnificator.get(OrePrefixes.oreEndstone, Materials.Salt, 1L),
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Salt, 1L),
                GTOreDictUnificator.get(OrePrefixes.oreBlackgranite, Materials.Salt, 1L),
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Salt, 1L),
                GTOreDictUnificator.get(OrePrefixes.oreRedgranite, Materials.Salt, 1L),
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapelessRecipe(
                GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Clay, 1L),
                "craftingToolMortar",
                getModItem(Minecraft.ID, "clay_ball", 1, 0, missing));
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Iron, 1L),
                "nuggetIron",
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Gold, 1L),
                "nuggetGold",
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Copper, 1L),
                "nuggetCopper",
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Tin, 1L),
                "nuggetTin",
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Silver, 1L),
                "nuggetSilver",
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Lead, 1L),
                "nuggetLead",
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Zinc, 1L),
                "nuggetZinc",
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Nickel, 1L),
                "nuggetNickel",
                null,
                null,
                "craftingToolMortar",
                null,
                null);
        addShapedRecipe(
                ItemList.Casing_Motor.get(1L),
                "itemCasingStainlessSteel",
                "circuitAdvanced",
                "itemCasingStainlessSteel",
                ItemList.Electric_Motor_HV.get(1L),
                ItemList.Casing_SolidSteel.get(1L),
                ItemList.Electric_Motor_HV.get(1L),
                "itemCasingStainlessSteel",
                "circuitAdvanced",
                "itemCasingStainlessSteel");
        addShapelessRecipe(
                ItemList.Cover_SolarPanel.get(1L),
                getModItem(ProjectRedExpansion.ID, "projectred.expansion.solar_panel", 1, 0, missing));
        addShapelessRecipe(
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.RedAlloy, 1L),
                getModItem(ProjectRedTransmission.ID, "projectred.transmission.wire", 1, 0, missing));
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Rubber, 1),
                "stickRubber",
                "craftingToolHardHammer",
                "stickRubber");
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Apatite, 1L),
                "stickApatite",
                "craftingToolHardHammer",
                "stickApatite");
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Lapis, 1L),
                "stickLapis",
                "craftingToolHardHammer",
                "stickLapis");
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Lazurite, 1L),
                "stickLazurite",
                "craftingToolHardHammer",
                "stickLazurite");
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Sodalite, 1L),
                "stickSodalite",
                "craftingToolHardHammer",
                "stickSodalite");
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.EnderPearl, 1L),
                "stickEnderPearl",
                "craftingToolHardHammer",
                "stickEnderPearl");
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.EnderEye, 1L),
                "stickEnderEye",
                "craftingToolHardHammer",
                "stickEnderEye");
        addShapelessRecipe(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 2L),
                getModItem(MCFrames.ID, "mcframes.frame", 1, 0, missing));
        addShapelessRecipe(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Wood, 1L),
                getModItem(OpenBlocks.ID, "scaffolding", 1, 0, missing));
        addShapelessRecipe(
                GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Glass, 1L),
                "craftingToolMortar",
                getModItem(BloodArsenal.ID, "glass_shard", 1, 0, missing));
        addShapedRecipe(
                ItemList.Casing_Pump.get(1L),
                "itemCasingStainlessSteel",
                "circuitAdvanced",
                "itemCasingStainlessSteel",
                ItemList.Electric_Pump_HV.get(1L),
                ItemList.Casing_SolidSteel.get(1L),
                ItemList.Electric_Pump_HV.get(1L),
                "itemCasingStainlessSteel",
                "circuitAdvanced",
                "itemCasingStainlessSteel");
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.GraniteBlack, 1L),
                "stoneGraniteBlack",
                null,
                null,
                null,
                "craftingToolFile",
                null);
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.GraniteRed, 1L),
                "stoneGraniteRed",
                null,
                null,
                null,
                "craftingToolFile",
                null);
        addShapedRecipe(
                ItemList.Casing_Processor.get(1L),
                "itemCasingStainlessSteel",
                "circuitElite",
                "itemCasingStainlessSteel",
                "circuitElite",
                ItemList.Casing_SolidSteel.get(1L),
                "circuitElite",
                "itemCasingStainlessSteel",
                "circuitElite",
                "itemCasingStainlessSteel");
        addShapedRecipe(
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 1L),
                "craftingToolHardHammer",
                null,
                null,
                "ingotObsidian",
                null,
                null,
                "ingotObsidian",
                null,
                null);
        addShapedRecipe(
                ItemList.WirelessHeadphones.get(1),
                GTOreDictUnificator.get(OrePrefixes.rod, Materials.Rubber, 1L),
                ItemList.Sensor_LV.get(1),
                GTOreDictUnificator.get(OrePrefixes.rod, Materials.Rubber, 1L),
                "circuitBasic",
                null,
                "circuitBasic",
                getModItem(Minecraft.ID, "noteblock", 1, 0, missing),
                null,
                getModItem(Minecraft.ID, "noteblock", 1, 0, missing));
        addShapedRecipe(
                ItemList.BetterJukebox_LV.get(1),
                ItemList.Electric_Piston_LV.get(1),
                ItemList.Emitter_LV.get(1),
                ItemList.Electric_Motor_LV.get(1),
                getModItem(Minecraft.ID, "noteblock", 1, 0, missing),
                ItemList.Hull_LV.get(1),
                getModItem(Minecraft.ID, "noteblock", 1, 0, missing),
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1L),
                "circuitBasic",
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1L));
        addShapedRecipe(
                ItemList.BetterJukebox_MV.get(1),
                ItemList.Electric_Piston_MV.get(1),
                ItemList.Emitter_MV.get(1),
                ItemList.Electric_Motor_MV.get(1),
                getModItem(Minecraft.ID, "noteblock", 1, 0, missing),
                ItemList.Hull_MV.get(1),
                getModItem(Minecraft.ID, "noteblock", 1, 0, missing),
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1L),
                "circuitGood",
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1L));
        addShapedRecipe(
                ItemList.BetterJukebox_HV.get(1),
                ItemList.Electric_Piston_HV.get(1),
                ItemList.Emitter_HV.get(1),
                ItemList.Electric_Motor_HV.get(1),
                getModItem(Minecraft.ID, "noteblock", 1, 0, missing),
                ItemList.Hull_HV.get(1),
                getModItem(Minecraft.ID, "noteblock", 1, 0, missing),
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1L),
                "circuitAdvanced",
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1L));
        addShapedRecipe(
                ItemList.BetterJukebox_EV.get(1),
                ItemList.Electric_Piston_EV.get(1),
                ItemList.Emitter_EV.get(1),
                ItemList.Electric_Motor_EV.get(1),
                getModItem(Minecraft.ID, "noteblock", 1, 0, missing),
                ItemList.Hull_EV.get(1),
                getModItem(Minecraft.ID, "noteblock", 1, 0, missing),
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1L),
                "circuitData",
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1L));
        addShapedRecipe(
                ItemList.BetterJukebox_IV.get(1),
                ItemList.Electric_Piston_IV.get(1),
                ItemList.Emitter_IV.get(1),
                ItemList.Electric_Motor_IV.get(1),
                getModItem(Minecraft.ID, "noteblock", 1, 0, missing),
                ItemList.Hull_IV.get(1),
                getModItem(Minecraft.ID, "noteblock", 1, 0, missing),
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1L),
                "circuitElite",
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1L));
        addShapedRecipe(
                new ItemStack(GregTechAPI.sBlockStones, 1, 2),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                new ItemStack(GregTechAPI.sBlockStones, 1, 1),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing));
        addShapedRecipe(
                new ItemStack(GregTechAPI.sBlockStones, 1, 5),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                new ItemStack(GregTechAPI.sBlockStones, 1, 3),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing));
        addShapedRecipe(
                new ItemStack(GregTechAPI.sBlockConcretes, 1, 10),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                new ItemStack(GregTechAPI.sBlockConcretes, 1, 9),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing));
        addShapedRecipe(
                new ItemStack(GregTechAPI.sBlockConcretes, 1, 13),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                new ItemStack(GregTechAPI.sBlockConcretes, 1, 11),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing));
        addShapedRecipe(
                new ItemStack(GregTechAPI.sBlockStones, 1, 10),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                new ItemStack(GregTechAPI.sBlockStones, 1, 9),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing));
        addShapedRecipe(
                new ItemStack(GregTechAPI.sBlockStones, 1, 13),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                new ItemStack(GregTechAPI.sBlockStones, 1, 11),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing));
        addShapedRecipe(
                new ItemStack(GregTechAPI.sBlockGranites, 1, 2),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                new ItemStack(GregTechAPI.sBlockGranites, 1, 1),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing));
        addShapedRecipe(
                new ItemStack(GregTechAPI.sBlockGranites, 1, 5),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                new ItemStack(GregTechAPI.sBlockGranites, 1, 3),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing));
        addShapedRecipe(
                new ItemStack(GregTechAPI.sBlockGranites, 1, 10),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                new ItemStack(GregTechAPI.sBlockGranites, 1, 9),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing));
        addShapedRecipe(
                new ItemStack(GregTechAPI.sBlockGranites, 1, 13),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                new ItemStack(GregTechAPI.sBlockGranites, 1, 11),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing),
                getModItem(BiomesOPlenty.ID, "moss", 1, 0, missing));
        addShapelessRecipe(ItemList.Color_03.get(1L), "dyeBrown");
        addShapelessRecipe(ItemList.Color_07.get(1L), "dyeLightGray");
        addShapelessRecipe(ItemList.Color_11.get(1L), "dyeYellow");
        addShapelessRecipe(ItemList.Color_15.get(1L), "dyeWhite");
        addShapelessRecipe(ItemList.Color_04.get(1L), "dyeBlue");
        addShapelessRecipe(ItemList.Color_08.get(1L), "dyeGray");
        addShapelessRecipe(ItemList.Color_10.get(1L), "dyeLime");
        addShapelessRecipe(ItemList.Color_14.get(1L), "dyeOrange");
        addShapelessRecipe(ItemList.Color_01.get(1L), "dyeRed");
        addShapelessRecipe(ItemList.Color_05.get(1L), "dyePurple");
        addShapelessRecipe(ItemList.Color_09.get(1L), "dyePink");
        addShapelessRecipe(ItemList.Color_13.get(1L), "dyeMagenta");
        addShapelessRecipe(ItemList.Color_00.get(1L), "dyeBlack");
        addShapelessRecipe(ItemList.Color_02.get(1L), "dyeGreen");
        addShapelessRecipe(ItemList.Color_06.get(1L), "dyeCyan");
        addShapelessRecipe(ItemList.Color_12.get(1L), "dyeLightBlue");
        addShapelessRecipe(
                getModItem(StructureLib.ID, "item.structurelib.constructableTrigger", 1, 0, missing),
                "dyeBlue",
                "platePaper",
                "dyeBlue",
                "dyeWhite");

        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                ItemList.Cover_SolarPanel_LV.get(1L),
                "---------",
                "---------",
                "---aba---",
                "---cdc---",
                "---efe---",
                "---cdc---",
                "---aba---",
                "---------",
                "---------",
                'a',
                "wireGt01SuperconductorMV",
                'b',
                CustomItemList.IrradiantReinforcedAluminiumPlate.get(1L),
                'c',
                ItemList.Circuit_Silicon_Wafer2.get(1L),
                'd',
                "platePolytetrafluoroethylene",
                'e',
                "circuitAdvanced",
                'f',
                ItemList.Cover_SolarPanel_8V.get(1L));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                ItemList.Cover_SolarPanel_MV.get(1L),
                "---------",
                "----a----",
                "---bcb---",
                "---ded---",
                "---fgf---",
                "---ded---",
                "---bcb---",
                "----a----",
                "---------",
                'a',
                ItemList.Circuit_Chip_ULPIC.get(1L),
                'b',
                "wireGt01SuperconductorHV",
                'c',
                CustomItemList.IrradiantReinforcedTitaniumPlate.get(1L),
                'd',
                ItemList.Circuit_Silicon_Wafer2.get(1L),
                'e',
                "plateEpoxid",
                'f',
                "circuitData",
                'g',
                ItemList.Cover_SolarPanel_LV.get(1L));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                ItemList.Cover_SolarPanel_HV.get(1L),
                "---------",
                "----a----",
                "---bcb---",
                "---ded---",
                "--ffgff--",
                "---ded---",
                "---bcb---",
                "----a----",
                "---------",
                'a',
                ItemList.Circuit_Chip_LPIC.get(1L),
                'b',
                "wireGt01SuperconductorEV",
                'c',
                CustomItemList.IrradiantReinforcedTungstenPlate.get(1L),
                'd',
                ItemList.Circuit_Silicon_Wafer2.get(1L),
                'e',
                "plateIndiumGalliumPhosphide",
                'f',
                "circuitElite",
                'g',
                ItemList.Cover_SolarPanel_MV.get(1L));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                ItemList.Cover_SolarPanel_EV.get(1L),
                "---------",
                "----a----",
                "--bcdcb--",
                "---efe---",
                "-bgghggb-",
                "---efe---",
                "--bcdcb--",
                "----a----",
                "---------",
                'a',
                ItemList.Circuit_Chip_PIC.get(1L),
                'b',
                "wireGt01SuperconductorIV",
                'c',
                "plateTripleSiliconSolarGrade",
                'd',
                CustomItemList.IrradiantReinforcedTungstenSteelPlate.get(1L),
                'e',
                ItemList.Circuit_Silicon_Wafer3.get(1L),
                'f',
                "platePolybenzimidazole",
                'g',
                "circuitMaster",
                'h',
                ItemList.Cover_SolarPanel_HV.get(1L));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                ItemList.Cover_SolarPanel_IV.get(1L),
                "---------",
                "----a----",
                "--bcdcb--",
                "--efgfe--",
                "-bhhihhb-",
                "--efgfe--",
                "--bcdcb--",
                "----a----",
                "---------",
                'a',
                ItemList.Circuit_Chip_HPIC.get(1L),
                'b',
                "wireGt01SuperconductorLuV",
                'c',
                ItemList.Circuit_Silicon_Wafer2.get(1L),
                'd',
                CustomItemList.IrradiantReinforcedChromePlate.get(1L),
                'e',
                "plateQuadrupleSiliconSolarGrade",
                'f',
                ItemList.Circuit_Silicon_Wafer3.get(1L),
                'g',
                "plateDoublePolybenzimidazole",
                'h',
                "circuitUltimate",
                'i',
                ItemList.Cover_SolarPanel_EV.get(1L));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                ItemList.Cover_SolarPanel_LuV.get(1L),
                "----a----",
                "---bcb---",
                "--bdedb--",
                "--fghgf--",
                "-icjkjci-",
                "--fghgf--",
                "--bdedb--",
                "---bcb---",
                "----a----",
                'a',
                ItemList.Circuit_Chip_UHPIC.get(1L),
                'b',
                "wireGt02SuperconductorZPM",
                'c',
                "circuitUltimate",
                'd',
                ItemList.Circuit_Silicon_Wafer3.get(1L),
                'e',
                getModItem(AdvancedSolarPanel.ID, "asp_crafting_items", 1, 8, missing),
                'f',
                "plateQuintupleSiliconSolarGrade",
                'g',
                ItemList.Circuit_Silicon_Wafer4.get(1L),
                'h',
                GTOreDictUnificator.get(OrePrefixes.plateTriple, Materials.Polybenzimidazole, 1L),
                'i',
                NHItemList.SolarLightSplitter.getIS(),
                'j',
                "circuitSuperconductor",
                'k',
                ItemList.Cover_SolarPanel_IV.get(1L));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                ItemList.Cover_SolarPanel_ZPM.get(1L),
                "---aba---",
                "--cadac--",
                "--aefea--",
                "--ghihg--",
                "-fjklkjf-",
                "--ghihg--",
                "--aefea--",
                "--cadac--",
                "---aba---",
                'a',
                "wireGt02SuperconductorUV",
                'b',
                ItemList.Circuit_Wafer_SoC2.get(1L),
                'c',
                ItemList.Circuit_Chip_NPIC.get(1L),
                'd',
                ItemList.Circuit_Wafer_QPIC.get(1L),
                'e',
                ItemList.Circuit_Silicon_Wafer4.get(1L),
                'f',
                CustomItemList.IrradiantReinforcedNaquadriaPlate.get(1L),
                'g',
                getModItem(IndustrialCraft2.ID, "itemPartCarbonPlate", 1, 0, missing),
                'h',
                ItemList.Circuit_Silicon_Wafer5.get(1L),
                'i',
                GTOreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Polybenzimidazole, 1L),
                'j',
                "circuitSuperconductor",
                'k',
                "circuitInfinite",
                'l',
                ItemList.Cover_SolarPanel_LuV.get(1L));
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                ItemList.Cover_SolarPanel_UV.get(1L),
                "--abcba--",
                "-daefead-",
                "-agbhbga-",
                "-aibjbia-",
                "ahklmlkha",
                "-aibjbia-",
                "-agbhbga-",
                "-daefead-",
                "--abcba--",
                'a',
                "wireGt02Superconductor",
                'b',
                "plateDenseSiliconSolarGrade",
                'c',
                ItemList.Circuit_Chip_CrystalSoC2.get(1L),
                'd',
                ItemList.Circuit_Chip_PPIC.get(1L),
                'e',
                ItemList.Circuit_Silicon_Wafer5.get(1L),
                'f',
                CustomItemList.RawPicoWafer.get(1L),
                'g',
                CustomItemList.PicoWafer.get(1L),
                'h',
                CustomItemList.IrradiantReinforcedNeutroniumPlate.get(1L),
                'i',
                getModItem(IndustrialCraft2.ID, "itemPartCarbonPlate", 1, 0, missing),
                'j',
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Polybenzimidazole, 1L),
                'k',
                "circuitInfinite",
                'l',
                "circuitBio",
                'm',
                ItemList.Cover_SolarPanel_ZPM.get(1L));

        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                ItemList.Spray_Color_Infinite.get(1L),
                "---RR----",
                "---PsR---",
                "---RRR---",
                "--rrrrr--",
                "--If-AI--",
                "--IF-AI--",
                "--IWCcI--",
                "--IbSEI--",
                "---III---",
                'I',
                TGregUtils.newItemStack(Materials.Iridium, PartTypes.LargePlate, 1),
                'R',
                TGregUtils.newItemStack(Materials.Tungsten, PartTypes.LargePlate, 1),
                'r',
                TGregUtils.newItemStack(Materials.Palladium, PartTypes.LargePlate, 1),
                'S',
                getModItem(Minecraft.ID, "sand", 1, 0, missing),
                'C',
                getModItem(ThaumicBases.ID, "rainbowCactus", 1, 0, missing),
                'E',
                getModItem(AppliedEnergistics2.ID, "tile.BlockDenseEnergyCell", 1, 0, missing),
                'W',
                ItemList.AcceleratorIV.get(1L),
                'F',
                "fenceWood",
                'f',
                getModItem(OpenBlocks.ID, "fan", 1, 0, missing),
                'c',
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 36),
                'A',
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 300, missing),
                's',
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 57),
                'P',
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 320, missing),
                'b',
                ItemList.Battery_Buffer_3by3_IV.get(1L));

        GTValues.RA.stdBuilder().itemInputs(getModItem(TinkerConstruct.ID, "strangeFood", 1, 0, missing))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.RawRubber, 2L)).duration(15 * SECONDS)
                .eut(2).addTo(extractorRecipes);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Empty.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                null,
                false,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Plate.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormPlate.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Casing.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormCasing.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Gear.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormGear.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Credit.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormCoinage.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Bottle.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormBottle.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Ingot.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormIngot.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Ball.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormBall.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Block.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormBlock.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Nugget.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormNuggets.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Bun.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormBuns.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Bread.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormBread.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Baguette.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormBaguette.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Cylinder.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormCylinder.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Anvil.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormAnvil.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Name.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormName.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Arrow.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormArrowHead.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Gear_Small.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormSmallGear.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Rod.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormStick.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Bolt.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormBolt.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Round.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormRound.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Screw.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormScrew.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Ring.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormRing.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Rod_Long.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormStickLong.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Turbine_Blade.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormTurbineBlade.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Rotor.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormRotor.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Pipe_Tiny.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormPipeTiny.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Pipe_Small.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormPipeSmall.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Pipe_Medium.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormPipeMedium.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Pipe_Large.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormPipeLarge.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_Pipe_Huge.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormPipeHuge.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Mold_ToolHeadDrill.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.MoldFormDrillHead.getIS(1),
                true,
                100);

        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Plate.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapePlate.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Rod.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeRod.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Bolt.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeBolt.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Cell.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeCell.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Ring.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeRing.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Ingot.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeIngot.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Wire.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeWire.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Casing.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeCasing.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Pipe_Tiny.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeTinyPipe.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Pipe_Small.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeSmallPipe.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Pipe_Medium.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeNormalPipe.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Pipe_Large.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeLargePipe.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Pipe_Huge.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeHugePipe.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Block.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeBlock.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Sword.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeSwordBlade.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Pickaxe.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapePickaxeHead.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Shovel.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeShovelHead.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Axe.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeAxeHead.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Hoe.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeHoeHead.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Hammer.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeHammerHead.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_File.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeFileHead.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Saw.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeSawBlade.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Gear.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeGear.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Bottle.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeBottle.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Rotor.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeRotor.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Turbine_Blade.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeTurbineBlade.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_Small_Gear.get(1L),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeSmallGear.getIS(1),
                true,
                100);
        TConstructRegistry.getTableCasting().addCastingRecipe(
                ItemList.Shape_Extruder_ToolHeadDrill.get(1),
                FluidRegistry.getFluidStack("steel.molten", 576),
                NHItemList.ShapeDrillHead.getIS(1),
                true,
                100);

        GTModHandler.addSmeltingRecipe(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.MeatRaw, 1L),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.MeatCooked, 1L));
        GTValues.RA.stdBuilder()
                .itemInputs(
                        getModItem(IndustrialCraft2.ID, "blockElectric", 1, 3, missing),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                .itemOutputs(ItemList.Transformer_MV_LV.get(1L)).duration(15 * SECONDS).eut(30).addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        getModItem(IndustrialCraft2.ID, "blockElectric", 1, 4, missing),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L))
                .itemOutputs(ItemList.Transformer_HV_MV.get(1L)).duration(12 * SECONDS + 10 * TICKS).eut(120)
                .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        getModItem(IndustrialCraft2.ID, "blockElectric", 1, 5, missing),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 2L))
                .itemOutputs(ItemList.Transformer_EV_HV.get(1L)).duration(10 * SECONDS).eut(480)
                .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        getModItem(IndustrialCraft2.ID, "blockElectric", 1, 6, missing),
                        GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.Aluminium, 2L))
                .itemOutputs(ItemList.Transformer_IV_EV.get(1L)).duration(5 * SECONDS).eut(1920)
                .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Iron, 6L),
                        getModItem(IndustrialCraft2.ID, "itemPlutonium", 3, 0, missing))
                .itemOutputs(getModItem(IndustrialCraft2.ID, "itemRTGPellet", 1, 0, missing))
                .fluidInputs(FluidRegistry.getFluidStack("ic2coolant", 1000)).duration(1 * MINUTES).eut(120)
                .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Electric_Piston_EV.get(2, missing),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 2),
                        ItemList.Electric_Motor_EV.get(2, missing),
                        ItemList.Hull_EV.get(1, missing),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, missing, 1L),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(ItemList.Machine_EV_Bender.get(1, missing)).duration(10 * SECONDS).eut(1920)
                .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.nugget, Materials.Tin, 9L),
                        GTOreDictUnificator.get(OrePrefixes.nugget, Materials.Copper, 27L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 4L)).duration(10 * SECONDS)
                .eut(16).addTo(alloySmelterRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.nugget, Materials.Tin, 9L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 3L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 4L)).duration(10 * SECONDS)
                .eut(16).addTo(alloySmelterRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.nugget, Materials.Tin, 9L),
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 3L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 4L)).duration(10 * SECONDS)
                .eut(16).addTo(alloySmelterRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.nugget, Materials.Copper, 27L),
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Tin, 1L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 4L)).duration(10 * SECONDS)
                .eut(16).addTo(alloySmelterRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.nugget, Materials.Copper, 27L),
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 4L)).duration(10 * SECONDS)
                .eut(16).addTo(alloySmelterRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(getModItem(ThaumicBases.ID, "resource", 9, 0, missing), ItemList.Shape_Mold_Ingot.get(0L))
                .itemOutputs(getModItem(ThaumicBases.ID, "resource", 1, 1, missing)).duration(10 * SECONDS).eut(16)
                .addTo(alloySmelterRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(getModItem(ThaumicBases.ID, "resource", 1, 1, missing), ItemList.Shape_Mold_Nugget.get(0L))
                .itemOutputs(getModItem(ThaumicBases.ID, "resource", 9, 0, missing)).duration(10 * SECONDS).eut(16)
                .addTo(alloySmelterRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 1L),
                        ItemList.Shape_Mold_Block.get(0L))
                .itemOutputs(getModItem(Minecraft.ID, "glass", 1, 0, missing)).duration(10 * SECONDS).eut(16)
                .addTo(alloySmelterRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Glass, 4L),
                        ItemList.Shape_Mold_Block.get(0L))
                .itemOutputs(getModItem(Minecraft.ID, "glass", 1, 0, missing)).duration(10 * SECONDS).eut(16)
                .addTo(alloySmelterRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Glass, 9L),
                        ItemList.Shape_Mold_Block.get(0L))
                .itemOutputs(getModItem(Minecraft.ID, "glass", 1, 0, missing)).duration(10 * SECONDS).eut(16)
                .addTo(alloySmelterRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedAir, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 0, missing)).outputChances(8000)
                .fluidInputs(FluidRegistry.getFluidStack("water", 200)).duration(1 * MINUTES + 40 * SECONDS).eut(30)
                .addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedAir, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 0, missing)).outputChances(9000)
                .fluidInputs(FluidRegistry.getFluidStack("ic2distilledwater", 100)).duration(1 * MINUTES + 15 * SECONDS)
                .eut(30).addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedAir, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 0, missing)).outputChances(10000)
                .fluidInputs(FluidRegistry.getFluidStack("molten.void", 36)).duration(50 * SECONDS).eut(30)
                .addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedFire, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 1, missing)).outputChances(8000)
                .fluidInputs(FluidRegistry.getFluidStack("water", 200)).duration(1 * MINUTES + 40 * SECONDS).eut(30)
                .addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedFire, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 1, missing)).outputChances(9000)
                .fluidInputs(FluidRegistry.getFluidStack("ic2distilledwater", 100)).duration(1 * MINUTES + 15 * SECONDS)
                .eut(30).addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedFire, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 1, missing)).outputChances(10000)
                .fluidInputs(FluidRegistry.getFluidStack("molten.void", 36)).duration(50 * SECONDS).eut(30)
                .addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedWater, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 2, missing)).outputChances(8000)
                .fluidInputs(FluidRegistry.getFluidStack("water", 200)).duration(1 * MINUTES + 40 * SECONDS).eut(30)
                .addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedWater, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 2, missing)).outputChances(9000)
                .fluidInputs(FluidRegistry.getFluidStack("ic2distilledwater", 100)).duration(1 * MINUTES + 15 * SECONDS)
                .eut(30).addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedWater, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 2, missing)).outputChances(10000)
                .fluidInputs(FluidRegistry.getFluidStack("molten.void", 36)).duration(50 * SECONDS).eut(30)
                .addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedEarth, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 3, missing)).outputChances(8000)
                .fluidInputs(FluidRegistry.getFluidStack("water", 200)).duration(1 * MINUTES + 40 * SECONDS).eut(30)
                .addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedEarth, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 3, missing)).outputChances(9000)
                .fluidInputs(FluidRegistry.getFluidStack("ic2distilledwater", 100)).duration(1 * MINUTES + 15 * SECONDS)
                .eut(30).addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedEarth, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 3, missing)).outputChances(10000)
                .fluidInputs(FluidRegistry.getFluidStack("molten.void", 36)).duration(50 * SECONDS).eut(30)
                .addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedOrder, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 4, missing)).outputChances(8000)
                .fluidInputs(FluidRegistry.getFluidStack("water", 200)).duration(1 * MINUTES + 40 * SECONDS).eut(30)
                .addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedOrder, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 4, missing)).outputChances(9000)
                .fluidInputs(FluidRegistry.getFluidStack("ic2distilledwater", 100)).duration(1 * MINUTES + 15 * SECONDS)
                .eut(30).addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedOrder, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 4, missing)).outputChances(10000)
                .fluidInputs(FluidRegistry.getFluidStack("molten.void", 36)).duration(50 * SECONDS).eut(30)
                .addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedEntropy, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 5, missing)).outputChances(8000)
                .fluidInputs(FluidRegistry.getFluidStack("water", 200)).duration(1 * MINUTES + 40 * SECONDS).eut(30)
                .addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedEntropy, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 5, missing)).outputChances(9000)
                .fluidInputs(FluidRegistry.getFluidStack("ic2distilledwater", 100)).duration(1 * MINUTES + 15 * SECONDS)
                .eut(30).addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.InfusedEntropy, 1L))
                .itemOutputs(getModItem(Thaumcraft.ID, "ItemShard", 1, 5, missing)).outputChances(10000)
                .fluidInputs(FluidRegistry.getFluidStack("molten.void", 36)).duration(50 * SECONDS).eut(30)
                .addTo(autoclaveRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(CustomItemList.PotassiumHydroxideDust.get(3L), GTUtility.getIntegratedCircuit(1))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Saltpeter, 5L))
                .fluidInputs(FluidRegistry.getFluidStack("nitricacid", 5000))
                .fluidOutputs(FluidRegistry.getFluidStack("steam", 16000)).duration(10).eut(30)
                .addTo(UniversalChemical);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 1, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "toolRod", 1, 11, missing))
                .duration(30 * SECONDS + 6 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 2, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "pickaxeHead", 1, 11, missing))
                .duration(1 * MINUTES + 13 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 3, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "shovelHead", 1, 11, missing))
                .duration(1 * MINUTES + 13 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 4, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "hatchetHead", 1, 11, missing))
                .duration(1 * MINUTES + 13 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 5, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "swordBlade", 1, 11, missing))
                .duration(1 * MINUTES + 13 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 6, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "wideGuard", 1, 11, missing))
                .duration(30 * SECONDS + 6 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 7, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "handGuard", 1, 11, missing))
                .duration(30 * SECONDS + 6 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 8, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "crossbar", 1, 11, missing))
                .duration(30 * SECONDS + 6 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 9, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "binding", 1, 11, missing))
                .duration(30 * SECONDS + 6 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 10, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "frypanHead", 1, 11, missing))
                .duration(1 * MINUTES + 13 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 11, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "signHead", 1, 11, missing))
                .duration(1 * MINUTES + 13 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 12, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "knifeBlade", 1, 11, missing))
                .duration(30 * SECONDS + 6 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 13, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "chiselHead", 1, 11, missing))
                .duration(30 * SECONDS + 6 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 3L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 14, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "toughRod", 1, 11, missing))
                .duration(3 * MINUTES + 1 * SECONDS + 19 * TICKS).recipeCategory(RecipeCategories.ticPartExtruding)
                .eut(120).addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 3L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 15, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "toughBinding", 1, 11, missing))
                .duration(3 * MINUTES + 1 * SECONDS + 19 * TICKS).recipeCategory(RecipeCategories.ticPartExtruding)
                .eut(120).addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 8L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 16, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "heavyPlate", 1, 11, missing))
                .duration(8 * MINUTES + 5 * SECONDS + 4 * TICKS).recipeCategory(RecipeCategories.ticPartExtruding)
                .eut(120).addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 8L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 17, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "broadAxeHead", 1, 11, missing))
                .duration(8 * MINUTES + 5 * SECONDS + 4 * TICKS).recipeCategory(RecipeCategories.ticPartExtruding)
                .eut(120).addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 8L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 18, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "scytheBlade", 1, 11, missing))
                .duration(8 * MINUTES + 5 * SECONDS + 4 * TICKS).recipeCategory(RecipeCategories.ticPartExtruding)
                .eut(120).addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 8L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 19, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "excavatorHead", 1, 11, missing))
                .duration(8 * MINUTES + 5 * SECONDS + 4 * TICKS).recipeCategory(RecipeCategories.ticPartExtruding)
                .eut(120).addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 8L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 20, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "largeSwordBlade", 1, 11, missing))
                .duration(8 * MINUTES + 5 * SECONDS + 4 * TICKS).recipeCategory(RecipeCategories.ticPartExtruding)
                .eut(120).addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 8L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 21, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "hammerHead", 1, 11, missing))
                .duration(8 * MINUTES + 5 * SECONDS + 4 * TICKS).recipeCategory(RecipeCategories.ticPartExtruding)
                .eut(120).addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 3L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 22, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "fullGuard", 1, 11, missing))
                .duration(3 * MINUTES + 1 * SECONDS + 19 * TICKS).recipeCategory(RecipeCategories.ticPartExtruding)
                .eut(120).addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "metalPattern", 0, 25, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "arrowhead", 1, 11, missing))
                .duration(1 * MINUTES + 13 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 1L),
                        getModItem(TinkerConstruct.ID, "Cast", 0, 0, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "ShurikenPart", 1, 11, missing))
                .duration(30 * SECONDS + 6 * TICKS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 4L),
                        getModItem(TinkerConstruct.ID, "Cast", 0, 1, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "CrossbowLimbPart", 1, 11, missing))
                .duration(4 * MINUTES + 2 * SECONDS + 12 * TICKS).eut(120)
                .recipeCategory(RecipeCategories.ticPartExtruding).addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 5L),
                        getModItem(TinkerConstruct.ID, "Cast", 0, 2, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "CrossbowBodyPart", 1, 11, missing))
                .duration(5 * MINUTES + 3 * SECONDS + 5 * TICKS).eut(120)
                .recipeCategory(RecipeCategories.ticPartExtruding).addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.ingot, Materials.Ardite, 2L),
                        getModItem(TinkerConstruct.ID, "Cast", 0, 3, missing))
                .itemOutputs(getModItem(TinkerConstruct.ID, "BowLimbPart", 1, 11, missing))
                .duration(1 * MINUTES + 31 * SECONDS).eut(120).recipeCategory(RecipeCategories.ticPartExtruding)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        getModItem(TinkerConstruct.ID, "materials", 1, 18, missing),
                        ItemList.Shape_Extruder_Plate.get(0L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 1L)).duration(20).eut(64)
                .addTo(extruderRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.stick, Materials.Plastic, 2L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Plastic, 1L)).duration(20).eut(16)
                .addTo(hammerRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.stick, Materials.Rubber, 2L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Rubber, 1)).duration(20).eut(16)
                .addTo(hammerRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.stick, Materials.Apatite, 2L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Apatite, 1L)).duration(20).eut(16)
                .addTo(hammerRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.stick, Materials.Lapis, 2L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Lapis, 1L)).duration(20).eut(16)
                .addTo(hammerRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.stick, Materials.Lazurite, 2L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Lazurite, 1L)).duration(20)
                .eut(16).addTo(hammerRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.stick, Materials.Sodalite, 2L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Sodalite, 1L)).duration(20)
                .eut(16).addTo(hammerRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.stick, Materials.EnderPearl, 2L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.EnderPearl, 1L)).duration(20)
                .eut(16).addTo(hammerRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.stick, Materials.EnderEye, 2L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.EnderEye, 1L)).duration(20)
                .eut(16).addTo(hammerRecipes);
        GTValues.RA.stdBuilder().itemInputs(getModItem(Minecraft.ID, "flint", 1, 0, missing))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Flint, 2L))
                .duration(1 * SECONDS + 12 * TICKS).eut(16).addTo(hammerRecipes);
        GTValues.RA.stdBuilder().itemInputs(getModItem(TinkerConstruct.ID, "GlassBlock", 1, 0, missing))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.gem, Materials.Glass, 1L)).duration(10).eut(16)
                .addTo(hammerRecipes);
        GTValues.RA.stdBuilder().itemInputs(GTOreDictUnificator.get(OrePrefixes.gemChipped, Materials.Glass, 1L))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Glass, 1L)).duration(10).eut(16)
                .addTo(hammerRecipes);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L),
                        ItemList.Shape_Mold_Credit.get(0L))
                .itemOutputs(CustomItemList.CoinBlank.get(1L)).duration(10 * SECONDS).eut(30)
                .addTo(formingPressRecipes);
        GTValues.RA.stdBuilder().itemInputs(getModItem(Natura.ID, "barleyFood", 1, 0, missing))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L)).outputChances(10000)
                .duration(15 * SECONDS).eut(2).addTo(maceratorRecipes);
        GTValues.RA.stdBuilder().itemInputs(getModItem(PamsHarvestCraft.ID, "barleyItem", 1, 0, missing))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L)).outputChances(10000)
                .duration(15 * SECONDS).eut(2).addTo(maceratorRecipes);
        GTValues.RA.stdBuilder().itemInputs(getModItem(BiomesOPlenty.ID, "plants", 1, 6, missing))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L)).outputChances(10000)
                .duration(15 * SECONDS).eut(2).addTo(maceratorRecipes);
        GTValues.RA.stdBuilder().itemInputs(getModItem(PamsHarvestCraft.ID, "ryeItem", 1, 0, missing))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L)).outputChances(10000)
                .duration(15 * SECONDS).eut(2).addTo(maceratorRecipes);
        GTValues.RA.stdBuilder().itemInputs(getModItem(PamsHarvestCraft.ID, "oatsItem", 1, 0, missing))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L)).outputChances(10000)
                .duration(15 * SECONDS).eut(2).addTo(maceratorRecipes);
        GTValues.RA.stdBuilder().itemInputs(getModItem(Thaumcraft.ID, "blockTaint", 1, 2, missing))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.MeatRaw, 9L)).outputChances(10000)
                .duration(15 * SECONDS).eut(2).addTo(maceratorRecipes);
        GTValues.RA.stdBuilder().itemInputs(getModItem(TinkerConstruct.ID, "GlassBlock", 1, 0, missing))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 1L)).outputChances(10000)
                .duration(15 * SECONDS).eut(2).addTo(maceratorRecipes);
        GTValues.RA.stdBuilder().itemInputs(getModItem(TinkerConstruct.ID, "GlassPane", 1, 0, missing))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Glass, 1L)).outputChances(10000)
                .duration(15 * SECONDS).eut(2).addTo(maceratorRecipes);
        GTValues.RA.stdBuilder().itemInputs(getModItem(BloodArsenal.ID, "glass_shard", 2, 0, missing))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 1L)).outputChances(10000)
                .duration(15 * SECONDS).eut(2).addTo(maceratorRecipes);
        GTModHandler.addSmeltingRecipe(
                GTOreDictUnificator.get(OrePrefixes.ore, Materials.Magnetite, 1L),
                getModItem(Minecraft.ID, "iron_ingot", 1, 0, missing));
        GTModHandler.addSmeltingRecipe(
                GTOreDictUnificator.get(OrePrefixes.oreNetherrack, Materials.Magnetite, 1L),
                getModItem(Minecraft.ID, "iron_ingot", 1, 0, missing));
        GTModHandler.addSmeltingRecipe(
                GTOreDictUnificator.get(OrePrefixes.oreEndstone, Materials.Magnetite, 1L),
                getModItem(Minecraft.ID, "iron_ingot", 1, 0, missing));
        GTModHandler.addSmeltingRecipe(
                GTOreDictUnificator.get(OrePrefixes.oreBlackgranite, Materials.Magnetite, 1L),
                getModItem(Minecraft.ID, "iron_ingot", 1, 0, missing));
        GTModHandler.addSmeltingRecipe(
                GTOreDictUnificator.get(OrePrefixes.oreRedgranite, Materials.Magnetite, 1L),
                getModItem(Minecraft.ID, "iron_ingot", 1, 0, missing));
    }
}
