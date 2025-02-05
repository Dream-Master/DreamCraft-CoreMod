package com.dreammaster.scripts;

import static gregtech.api.enums.Mods.ExtraUtilities;
import static gregtech.api.enums.Mods.TinkerConstruct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;

import com.dreammaster.main.MainRegistry;

public class ScriptLoader {

    public static void run() {
        IScriptLoader.missing.setStackDisplayName(EnumChatFormatting.RED + "Missing item! Please report it on github!");

        List<IScriptLoader> scripts = new ArrayList<>(
                Arrays.asList(
                        new ScriptAdvancedBackpacks(),
                        new ScriptAdvancedSolarPanel(),
                        new ScriptAE2FC(),
                        new ScriptAE2Stuff(),
                        new ScriptAE2WC(),
                        new ScriptAFSU(),
                        new ScriptAkashicTome(),
                        new ScriptAmunRa(),
                        new ScriptAppliedEnergistics2(),
                        new ScriptArchitectureCraft(),
                        new ScriptAutomagy(),
                        new ScriptAvaritiaAddons(),
                        new ScriptBackpack(),
                        new ScriptBattleGear(),
                        new ScriptBetterBuildersWands(),
                        new ScriptBetterQuesting(),
                        new ScriptBiblioCraft(),
                        new ScriptBiblioWoodsBoP(),
                        new ScriptBiblioWoodsForestry(),
                        new ScriptBiblioWoodsNatura(),
                        new ScriptBinniesCore(),
                        new ScriptBinniesMods(),
                        new ScriptBiomesOPlenty(),
                        new ScriptBloodArsenal(),
                        new ScriptBloodMagic(),
                        new ScriptBotany(),
                        new ScriptBuildCraft(),
                        new ScriptCarpentersBlocks(),
                        new ScriptCatWalk(),
                        new ScriptChisel(),
                        new ScriptCompactKineticGenerators(),
                        new ScriptComputronics(),
                        new ScriptCoreMod(),
                        new ScriptDraconicEvolution(),
                        new ScriptEFR(),
                        new ScriptEMT(),
                        new ScriptEnderIO(),
                        new ScriptEnderZoo(),
                        new ScriptEnhancedLootBags(),
                        new ScriptExtraBees(),
                        new ScriptExtraTrees(),
                        new ScriptExtraUtilities(),
                        new ScriptFloodLight(),
                        new ScriptForbiddenMagic(),
                        new ScriptForestry(),
                        new ScriptForgeMultipart(),
                        new ScriptGadomancy(),
                        new ScriptGalacticraft(),
                        new ScriptGalaxySpace(),
                        new ScriptGendustry(),
                        new ScriptGenetics(),
                        new ScriptGraviSuite(),
                        new ScriptGregtech(),
                        new ScriptGregtechPlusPlus(),
                        new ScriptHardcoreEnderExpansion(),
                        new ScriptHarvestcraft(),
                        new ScriptHarvestcraftNether(),
                        new ScriptHoloInventory(),
                        new ScriptIguanaTweaks(),
                        new ScriptIndustrialApiary(),
                        new ScriptIndustrialCraft(),
                        new ScriptIronChests(),
                        new ScriptIronChestsMinecarts(),
                        new ScriptJABBA(),
                        new ScriptLogisticPipes(),
                        new ScriptMagicBees(),
                        new ScriptMalisDoors(),
                        new ScriptMechworks(),
                        new ScriptMinecraft(),
                        new ScriptNatura(),
                        new ScriptNuclearControl(),
                        new ScriptOpenBlocks(),
                        new ScriptOpenComputers(),
                        new ScriptOpenModularTurrets(),
                        new ScriptProjectRed(),
                        new ScriptRailcraft(),
                        new ScriptRandomThings(),
                        new ScriptRemoteIO(),
                        new ScriptRunicTablet(),
                        new ScriptSGCraft(),
                        new ScriptSleepingBags(),
                        new ScriptSpiceOfLife(),
                        new ScriptStevesCarts(),
                        new ScriptStevesFactoryManager(),
                        new ScriptTaintedMagic(),
                        new ScriptTCCoreMod(),
                        new ScriptThaumcraft(),
                        new ScriptThaumicBases(),
                        new ScriptThaumicEnergistics(),
                        new ScriptThaumicExploration(),
                        new ScriptThaumicHorizons(),
                        new ScriptThaumicMachina(),
                        new ScriptThaumicTinkerer(),
                        new ScriptTinkersConstruct(),
                        new ScriptTinkersDefence(),
                        new ScriptTranslocator(),
                        new ScriptTravellersGear(),
                        new ScriptTwilightForest(),
                        new ScriptWarpTheory(),
                        new ScriptWirelessRedstone(),
                        new ScriptWitchery(),
                        new ScriptTB(),
                        ScriptZZClientOnly.instance));

        // Java somehow tries to load XU / TiC class when instantiating this class
        // but @Optional.Method cannot have multiple mod ids
        if (TinkerConstruct.isModLoaded() && ExtraUtilities.isModLoaded()) {
            scripts.add(new ScriptAvaritia());
        }

        ArrayList<String> errored = new ArrayList<>();
        final long totalTimeStart = System.currentTimeMillis();
        for (IScriptLoader script : scripts) {
            if (script.isScriptLoadable()) {
                try {
                    final long timeStart = System.currentTimeMillis();
                    script.loadRecipes();
                    final long timeToLoad = System.currentTimeMillis() - timeStart;
                    MainRegistry.Logger.info("Loaded " + script.getScriptName() + " script in " + timeToLoad + " ms.");
                } catch (Exception ex) {
                    errored.add(script.getScriptName());
                    MainRegistry.Logger.error(
                            "There was an error while loading " + script.getScriptName() + "! Printing stacktrace:");
                    ex.printStackTrace();
                }
            } else {
                MainRegistry.Logger.info(
                        "Missing dependencies to load " + script.getScriptName() + " script. It won't be loaded.");
            }
        }
        final long totalTimeToLoad = System.currentTimeMillis() - totalTimeStart;
        MainRegistry.Logger.info("Script loader took " + totalTimeToLoad + " ms.");
        if (!errored.isEmpty()) throw new RuntimeException(
                "Scripts " + errored + " thrown an exception! Scroll up the log to see the stacktrace!");
    }
}
