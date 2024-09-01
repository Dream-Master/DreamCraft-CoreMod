package com.dreammaster.gthandler;

import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.BiomesOPlenty;
import static gregtech.api.enums.Mods.BloodArsenal;
import static gregtech.api.enums.Mods.Computronics;
import static gregtech.api.enums.Mods.EnderIO;
import static gregtech.api.enums.Mods.ExtraUtilities;
import static gregtech.api.enums.Mods.ForbiddenMagic;
import static gregtech.api.enums.Mods.GalacticraftCore;
import static gregtech.api.enums.Mods.GalacticraftMars;
import static gregtech.api.enums.Mods.GalaxySpace;
import static gregtech.api.enums.Mods.Gendustry;
import static gregtech.api.enums.Mods.HardcoreEnderExpansion;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.enums.Mods.IronChests;
import static gregtech.api.enums.Mods.Natura;
import static gregtech.api.enums.Mods.PamsHarvestCraft;
import static gregtech.api.enums.Mods.Railcraft;
import static gregtech.api.enums.Mods.StevesCarts2;
import static gregtech.api.enums.Mods.TaintedMagic;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.enums.Mods.ThaumicTinkerer;
import static gregtech.api.util.GT_ModHandler.getModItem;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.dreammaster.block.BlockList;
import com.dreammaster.item.ItemList;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

public class GT_Loader_OreDictionary extends gregtech.loaders.preload.GT_Loader_OreDictionary implements Runnable {

    @Override
    public void run() {
        GT_Log.out.println("Core-Mod: Register OreDict Entries of Non-GT-Items.");

        // Custom Stuff
        GT_OreDictUnificator.registerOre(
                "ingotBloodInfusedIron",
                GT_ModHandler.getModItem(BloodArsenal.ID, "blood_infused_iron", 1L, 0));
        GT_OreDictUnificator.registerOre(
                "blockBloodInfusedIron",
                GT_ModHandler.getModItem(BloodArsenal.ID, "blood_infused_iron_block", 1L, 0));

        GT_OreDictUnificator.registerOre(
                OrePrefixes.log,
                Materials.Wood,
                GT_ModHandler.getModItem(BiomesOPlenty.ID, "logs4", 1L, 3));
        GT_OreDictUnificator.registerOre("cropCarrot", GT_ModHandler.getModItem(BiomesOPlenty.ID, "food", 1L, 2));

        GT_OreDictUnificator.registerOre(OrePrefixes.ingot, Materials.Mytryl, ItemList.MytrylIngot.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.dust, Materials.Mytryl, ItemList.MytrylDust.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.plate, Materials.Mytryl, ItemList.MytrylPlate.getIS());
        GT_OreDictUnificator
                .registerOre(OrePrefixes.compressed, Materials.Mytryl, ItemList.MytrylCompressedPlate.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.plateDense, Materials.Mytryl, ItemList.MytrylDensePlate.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.block, Materials.Mytryl, BlockList.Mytryl.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.ingot, Materials.CallistoIce, ItemList.CallistoIceIngot.getIS());
        GT_OreDictUnificator.registerOre("ingotColdCallistoIce", ItemList.CallistoIceColdIngot.getIS());
        GT_OreDictUnificator.registerOre("blockCallistoIce", BlockList.CallistoColdIce.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.dust, Materials.CallistoIce, ItemList.CallistoIceDust.getIS());
        GT_OreDictUnificator.registerOre("plateColdCallistoIce", ItemList.CallistoIcePlate.getIS());
        GT_OreDictUnificator
                .registerOre(OrePrefixes.plateDense, Materials.CallistoIce, ItemList.CallistoIceDensePlate.getIS());
        GT_OreDictUnificator.registerOre(
                OrePrefixes.compressed,
                Materials.CallistoIce,
                ItemList.CallistoIceCompressedPlate.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.ingot, Materials.Ledox, ItemList.LedoxIngot.getIS());
        GT_OreDictUnificator.registerOre("ingotColdLedox", ItemList.LedoxColdIngot.getIS());
        GT_OreDictUnificator.registerOre("blockLedox", BlockList.Ledox.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.dust, Materials.Ledox, ItemList.LedoxDust.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.plate, Materials.Ledox, ItemList.LedoxPlate.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.plateDense, Materials.Ledox, ItemList.LedoxDensePlate.getIS());
        GT_OreDictUnificator
                .registerOre(OrePrefixes.compressed, Materials.Ledox, ItemList.LedoxCompressedPlate.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.stick, Materials.Stone, ItemList.CobbleStoneRod.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.plate, Materials.Stone, ItemList.StonePlate.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.plate, Materials.Void, ItemList.VoidPlate.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.stick, Materials.Void, ItemList.VoidRod.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.ring, Materials.Void, ItemList.VoidRing.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.foil, Materials.Void, ItemList.VoidFoil.getIS());
        GT_OreDictUnificator.registerOre("stickSandstone", ItemList.SandStoneRod.getIS());
        GT_OreDictUnificator.registerOre("lensReinforcedGlass", ItemList.ReinforcedGlassLense.getIS());
        GT_OreDictUnificator.registerOre("plateReinforcedGlass", ItemList.ReinforcedGlassPlate.getIS());
        GT_OreDictUnificator.registerOre("blockQuantium", BlockList.Quantinum.getIS());
        GT_OreDictUnificator
                .registerOre(OrePrefixes.compressed, Materials.Quantium, ItemList.QuantinumCompressedPlate.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.dust, Materials.Quantium, ItemList.QuantinumDust.getIS());
        GT_OreDictUnificator
                .registerOre(OrePrefixes.plateDense, Materials.Quantium, ItemList.QuantinumDensePlate.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.plate, Materials.Quantium, ItemList.QuantinumPlate.getIS());
        GT_OreDictUnificator
                .registerOre(OrePrefixes.block, Materials.MysteriousCrystal, BlockList.MysteriousCrystal.getIS());
        GT_OreDictUnificator.registerOre(
                OrePrefixes.compressed,
                Materials.MysteriousCrystal,
                ItemList.MysteriousCrystalCompressedPlate.getIS());
        GT_OreDictUnificator.registerOre(
                OrePrefixes.plate,
                Materials.Bedrockium,
                com.dreammaster.item.ItemList.BedrockiumPlate.getIS(1));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.plate,
                Materials.BlackPlutonium,
                com.dreammaster.item.ItemList.BlackPlutoniumPlate.getIS(1));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.plateDense,
                Materials.BlackPlutonium,
                com.dreammaster.item.ItemList.BlackPlutoniumDensePlate.getIS(1));
        GT_OreDictUnificator.registerOre(OrePrefixes.block, Materials.BlackPlutonium, BlockList.BlackPlutonium.getIS());
        GT_OreDictUnificator.registerOre(
                OrePrefixes.plate,
                Materials.Manyullyn,
                com.dreammaster.item.ItemList.ManyullynPlate.getIS(1));
        GT_OreDictUnificator
                .registerOre(OrePrefixes.plate, Materials.Ardite, com.dreammaster.item.ItemList.ArditePlate.getIS(1));
        GT_OreDictUnificator.registerOre(OrePrefixes.dust, Materials.Alumite, ItemList.AlumiteDust.getIS());
        GT_OreDictUnificator.registerOre("dustQuartzSand", com.dreammaster.item.ItemList.SandDust.getIS(1));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.dust,
                Materials.CertusQuartzCharged,
                com.dreammaster.item.ItemList.ChargedCertusQuartzDust.getIS(1));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.plate,
                Materials.CertusQuartzCharged,
                com.dreammaster.item.ItemList.ChargedCertusQuartzPlate.getIS(1));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.stick,
                Materials.CertusQuartzCharged,
                com.dreammaster.item.ItemList.ChargedCertusQuartzRod.getIS(1));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.crystal,
                Materials.CertusQuartzCharged,
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 1));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.gem,
                Materials.CertusQuartzCharged,
                getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 1));
        GT_OreDictUnificator.registerOre("dustCokeOvenBrick", com.dreammaster.item.ItemList.CokeOvenBrickDust.getIS(1));
        GT_OreDictUnificator.registerOre("ingotCokeOvenBrick", com.dreammaster.item.ItemList.CokeOvenBrick.getIS(1));
        GT_OreDictUnificator.registerOre("leather", com.dreammaster.item.ItemList.ArtificialLeather.getIS(1));
        GT_OreDictUnificator.registerOre("itemLeather", com.dreammaster.item.ItemList.ArtificialLeather.getIS(1));
        GT_OreDictUnificator
                .registerOre("dustPotassiumHydroxide", com.dreammaster.item.ItemList.PotassiumHydroxideDust.getIS(1));

        GT_OreDictUnificator.registerOre(OrePrefixes.stickLong, Materials.Obsidian, ItemList.LongObsidianRod.getIS());

        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.Aluminium, ItemList.AluminiumBars.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.Chrome, ItemList.ChromeBars.getIS());
        GT_OreDictUnificator
                .registerOre(OrePrefixes.bars, Materials.ConductiveIron, ItemList.ConductiveIronBars.getIS());
        GT_OreDictUnificator
                .registerOre(OrePrefixes.bars, Materials.ElectricalSteel, ItemList.ElectricalSteelBars.getIS());
        GT_OreDictUnificator
                .registerOre(OrePrefixes.bars, Materials.EnergeticAlloy, ItemList.EnergeticAlloyBars.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.Enderium, ItemList.EnderiumBars.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.EnderiumBase, ItemList.EnderiumBaseBars.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.Iridium, ItemList.IridiumBars.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.Neutronium, ItemList.NeutroniumBars.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.Osmium, ItemList.OsmiumBars.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.PulsatingIron, ItemList.PulsatingIronBars.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.RedstoneAlloy, ItemList.RedstoneAlloyBars.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.Soularium, ItemList.SoulariumBars.getIS());
        GT_OreDictUnificator.registerOre(
                OrePrefixes.bars,
                Materials.Soularium,
                GT_ModHandler.getModItem(EnderIO.ID, "blockSoulariumBars", 1L, 0));
        GT_OreDictUnificator
                .registerOre(OrePrefixes.bars, Materials.StainlessSteel, ItemList.StainlessSteelBars.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.Steel, ItemList.SteelBars.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.Titanium, ItemList.TitaniumBars.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.Tungsten, ItemList.TungstenBars.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.bars, Materials.TungstenSteel, ItemList.TungstenSteelBars.getIS());

        GT_OreDictUnificator.registerOre(
                OrePrefixes.ore,
                Materials.Desh,
                GT_ModHandler.getModItem(GalacticraftMars.ID, "tile.mars", 1L, 2));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.stick,
                Materials.Desh,
                GT_ModHandler.getModItem(GalacticraftMars.ID, "item.null", 1L, 1));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.ore,
                Materials.Ilmenite,
                GT_ModHandler.getModItem(GalacticraftMars.ID, "tile.asteroidsBlock", 1L, 4));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.MeteoricIron,
                GT_ModHandler.getModItem(GalacticraftCore.ID, "tile.gcBlockCore", 1L, 12));

        GT_OreDictUnificator.registerOre(
                OrePrefixes.ingot,
                Materials.HeeEndium,
                GT_ModHandler.getModItem(HardcoreEnderExpansion.ID, "endium_ingot", 1L, 0));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.HeeEndium,
                GT_ModHandler.getModItem(HardcoreEnderExpansion.ID, "endium_block", 1L, 0));

        GT_OreDictUnificator
                .registerOre("oreAdamantium", GT_ModHandler.getModItem(GalaxySpace.ID, "oberonblocks", 1L, 3));
        GT_OreDictUnificator
                .registerOre("ingotAdamantium", GT_ModHandler.getModItem(GalaxySpace.ID, "item.Ingots", 1L, 0));
        GT_OreDictUnificator.registerOre("oreCobalt", GT_ModHandler.getModItem(GalaxySpace.ID, "phobosblocks", 1L, 4));
        GT_OreDictUnificator.registerOre("ingotCobalt", GT_ModHandler.getModItem(GalaxySpace.ID, "item.Ingots", 1L, 1));

        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Lead,
                GT_ModHandler.getModItem(GalaxySpace.ID, "metalsblock", 1L, 0));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Adamantium,
                GT_ModHandler.getModItem(GalaxySpace.ID, "metalsblock", 1L, 1));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Cobalt,
                GT_ModHandler.getModItem(GalaxySpace.ID, "metalsblock", 1L, 2));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Magnesium,
                GT_ModHandler.getModItem(GalaxySpace.ID, "metalsblock", 1L, 3));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Mithril,
                GT_ModHandler.getModItem(GalaxySpace.ID, "metalsblock", 1L, 4));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Nickel,
                GT_ModHandler.getModItem(GalaxySpace.ID, "metalsblock", 1L, 5));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Oriharukon,
                GT_ModHandler.getModItem(GalaxySpace.ID, "metalsblock", 1L, 6));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Platinum,
                GT_ModHandler.getModItem(GalaxySpace.ID, "metalsblock", 1L, 7));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Tungsten,
                GT_ModHandler.getModItem(GalaxySpace.ID, "metalsblock", 1L, 8));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Copper,
                GT_ModHandler.getModItem(GalaxySpace.ID, "metalsblock", 1L, 9));

        GT_OreDictUnificator
                .registerOre("chestSteel", GT_ModHandler.getModItem(IronChests.ID, "BlockIronChest", 1L, 4));

        GT_OreDictUnificator.registerOre("craftingToolShears", new ItemStack(Items.shears, 1, 32767));

        GT_OreDictUnificator.registerOre("cropBarley", GT_ModHandler.getModItem(Natura.ID, "barleyFood", 1L, 0));

        GT_OreDictUnificator
                .registerOre("craftingToolShears", GT_ModHandler.getModItem(ExtraUtilities.ID, "shears", 1L, 32767));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Unstable,
                GT_ModHandler.getModItem(ExtraUtilities.ID, "decorativeBlock1", 1L, 5));

        GT_OreDictUnificator.registerOre("logWood", GT_ModHandler.getModItem(ForbiddenMagic.ID, "TaintLog", 1L, 32767));
        GT_OreDictUnificator
                .registerOre("plankWood", GT_ModHandler.getModItem(ForbiddenMagic.ID, "TaintPlank", 1L, 32767));

        GT_OreDictUnificator.registerOre("beeComb", GT_ModHandler.getModItem(Gendustry.ID, "HoneyComb", 1L));

        GT_OreDictUnificator
                .registerOre("itemBeeswax", GT_ModHandler.getModItem(PamsHarvestCraft.ID, "beeswaxItem", 1L, 0));
        GT_OreDictUnificator
                .registerOre("foodFlour", GT_ModHandler.getModItem(PamsHarvestCraft.ID, "flourItem", 1L, 0));
        GT_OreDictUnificator
                .registerOre("listAllmeatcooked", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.MeatCooked, 1L));
        GT_OreDictUnificator
                .registerOre("listAllporkcooked", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.MeatCooked, 1L));
        GT_OreDictUnificator.registerOre(
                "listAllchickencooked",
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.MeatCooked, 1L));
        GT_OreDictUnificator
                .registerOre("listAllbeefcooked", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.MeatCooked, 1L));
        GT_OreDictUnificator
                .registerOre("listAllmeatraw", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.MeatRaw, 1L));
        GT_OreDictUnificator
                .registerOre("listAllporkraw", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.MeatRaw, 1L));
        GT_OreDictUnificator
                .registerOre("listAllchickenraw", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.MeatRaw, 1L));
        GT_OreDictUnificator
                .registerOre("listAllbeefraw", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.MeatRaw, 1L));
        GT_OreDictUnificator.registerOre("foodSalt", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Salt, 1L));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Salt,
                GT_ModHandler.getModItem(PamsHarvestCraft.ID, "spamcompressedsaltBlockalt", 1L, 0));

        GT_OreDictUnificator.registerOre(
                OrePrefixes.dust,
                Materials.Wheat,
                GT_ModHandler.getModItem(PamsHarvestCraft.ID, "flourItem", 1L, 0));

        GT_OreDictUnificator
                .registerOre("beeComp", GT_ModHandler.getModItem(Computronics.ID, "computronics.partsForestry", 1L, 0));

        GT_OreDictUnificator
                .registerOre("craftingToolShears", GT_ModHandler.getModItem(Railcraft.ID, "tool.steel.shears", 1L, 0));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.plate,
                Materials.Lead,
                GT_ModHandler.getModItem(Railcraft.ID, "part.plate", 1L, 4));
        GT_OreDictUnificator
                .registerOre("craftingToolCrowbar", GT_ModHandler.getModItem(Railcraft.ID, "tool.crowbar", 1L, 0));
        GT_OreDictUnificator.registerOre(
                "craftingToolCrowbar",
                GT_ModHandler.getModItem(Railcraft.ID, "tool.crowbar.reinforced", 1L, 0));
        GT_OreDictUnificator.registerOre(
                "craftingToolCrowbar",
                GT_ModHandler.getModItem(Railcraft.ID, "tool.crowbar.magic", 1L, 0));
        GT_OreDictUnificator
                .registerOre("craftingToolCrowbar", GT_ModHandler.getModItem(Railcraft.ID, "tool.crowbar.void", 1L, 0));

        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Reinforced,
                GT_ModHandler.getModItem(StevesCarts2.ID, "BlockMetalStorage", 1L, 0));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Galgadorian,
                GT_ModHandler.getModItem(StevesCarts2.ID, "BlockMetalStorage", 1L, 1));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.EnhancedGalgadorian,
                GT_ModHandler.getModItem(StevesCarts2.ID, "BlockMetalStorage", 1L, 2));

        GT_OreDictUnificator.registerOre(
                OrePrefixes.nugget,
                Materials.Void,
                GT_ModHandler.getModItem(Thaumcraft.ID, "ItemNugget", 1L, 7));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Amber,
                GT_ModHandler.getModItem(Thaumcraft.ID, "blockCosmeticOpaque", 1L, 0));

        GT_OreDictUnificator.registerOre(
                OrePrefixes.ingot,
                Materials.Bedrockium,
                GT_ModHandler.getModItem(ExtraUtilities.ID, "bedrockiumIngot", 1L, 0));

        GT_OreDictUnificator.registerOre(
                OrePrefixes.block,
                Materials.Shadow,
                GT_ModHandler.getModItem(TaintedMagic.ID, "BlockShadowmetal", 1L, 0));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.ingot,
                Materials.Shadow,
                GT_ModHandler.getModItem(TaintedMagic.ID, "ItemMaterial", 1L, 0));

        GT_OreDictUnificator.registerOre(
                OrePrefixes.ingot,
                Materials.Ichorium,
                GT_ModHandler.getModItem(ThaumicTinkerer.ID, "kamiResource", 1L, 2));
        GT_OreDictUnificator.registerOre(
                OrePrefixes.nugget,
                Materials.Ichorium,
                GT_ModHandler.getModItem(ThaumicTinkerer.ID, "kamiResource", 1L, 3));

        GT_OreDictUnificator
                .registerOre("dyeLime", GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Soapstone, 1L));

        GT_OreDictUnificator.registerOre(
                OrePrefixes.log,
                Materials.Wood,
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockRubWood", 1L, 0));

        // oreDict UMV and UXV circuits
        GT_OreDictUnificator.registerOre(OrePrefixes.circuit, Materials.UMV, ItemList.PikoCircuit.getIS());
        GT_OreDictUnificator.registerOre(OrePrefixes.circuit, Materials.UXV, ItemList.QuantumCircuit.getIS());
        GT_OreDictUnificator.addToBlacklist(ItemList.PikoCircuit.getIS());
        GT_OreDictUnificator.addToBlacklist(ItemList.QuantumCircuit.getIS());

        // Add ore dictionary entries for sand and red sand to craft unfired coke oven bricks.
        GT_OreDictUnificator.registerOre("sand", new ItemStack(Blocks.sand, 1, 0));
        GT_OreDictUnificator.registerOre("sand", new ItemStack(Blocks.sand, 1, 1));
    }
}
