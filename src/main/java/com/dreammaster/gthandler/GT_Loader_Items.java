package com.dreammaster.gthandler;

import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.addItemTooltip;
import static gregtech.client.GTTooltipHandler.Tier.*;
import static gregtech.client.GTTooltipHandler.registerTieredTooltip;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.dreammaster.item.NHItemList;

import gregtech.api.enums.SubTag;
import gregtech.common.items.MetaGeneratedItem01;

public class GT_Loader_Items {

    public void run() {
        GT = MetaGeneratedItem01.INSTANCE;
        registerItems();
        registerTooltips();
    }

    private MetaGeneratedItem01 GT;

    private void registerItems() {
        // ID Range: 600 - 699
        // Ingame offset: 32000

        // 600 - 609 Motors
        // 610 - 619 Pumps
        // 620 - 629 Rotors (Unused now, but don't re-use these IDs. Might cause world corruption)
        // 630 - 639 Conveyors
        // 640 - 649 Pistons
        // 650 - 659 Robot Arms
        // 660 - 664 Fluid Regulators
        // 670 - 679 Field Generators
        // 680 - 689 Emitters
        // 690 - 699 Sensors

        // CoreMod Items
        CustomItemList.EtchedLowVoltageWiring.set(NHItemList.EtchedLowVoltageWiring.getIS());
        CustomItemList.EtchedInsaneVoltageWiring.set(NHItemList.EtchedInsaneVoltageWiring.getIS());
        CustomItemList.EtchedLudicrousVoltageWiring.set(NHItemList.EtchedLudicrousVoltageWiring.getIS());
        CustomItemList.SteelBars.set(NHItemList.SteelBars.getIS());
        CustomItemList.AluminiumBars.set(NHItemList.AluminiumBars.getIS());
        CustomItemList.StainlessSteelBars.set(NHItemList.StainlessSteelBars.getIS());
        CustomItemList.TungstenBars.set(NHItemList.TungstenBars.getIS());
        CustomItemList.TungstenSteelBars.set(NHItemList.TungstenSteelBars.getIS());
        CustomItemList.IridiumBars.set(NHItemList.IridiumBars.getIS());
        CustomItemList.OsmiumBars.set(NHItemList.OsmiumBars.getIS());
        CustomItemList.ChromeBars.set(NHItemList.ChromeBars.getIS());
        CustomItemList.TitaniumBars.set(NHItemList.TitaniumBars.getIS());
        CustomItemList.NeutroniumBars.set(NHItemList.NeutroniumBars.getIS());
        CustomItemList.RedstoneAlloyBars.set(NHItemList.RedstoneAlloyBars.getIS());
        CustomItemList.ElectricalSteelBars.set(NHItemList.ElectricalSteelBars.getIS());
        CustomItemList.ConductiveIronBars.set(NHItemList.ConductiveIronBars.getIS());
        CustomItemList.EnergeticAlloyBars.set(NHItemList.EnergeticAlloyBars.getIS());
        CustomItemList.VibrantAlloyBars.set(NHItemList.VibrantAlloyBars.getIS());
        CustomItemList.PulsatingIronBars.set(NHItemList.PulsatingIronBars.getIS());
        CustomItemList.SoulariumBars.set(NHItemList.SoulariumBars.getIS());
        CustomItemList.EnderiumBaseBars.set(NHItemList.EnderiumBaseBars.getIS());
        CustomItemList.EnderiumBars.set(NHItemList.EnderiumBars.getIS());
        CustomItemList.NagaScaleChip.set(NHItemList.NagaScaleChip.getIS());
        CustomItemList.NagaScaleFragment.set(NHItemList.NagaScaleFragment.getIS());
        CustomItemList.LichBoneChip.set(NHItemList.LichBoneChip.getIS());
        CustomItemList.LichBoneFragment.set(NHItemList.LichBoneFragment.getIS());
        CustomItemList.LichBone.set(NHItemList.LichBone.getIS());
        CustomItemList.FieryBloodDrop.set(NHItemList.FieryBloodDrop.getIS());
        CustomItemList.CarminiteChip.set(NHItemList.CarminiteChip.getIS());
        CustomItemList.CarminiteFragment.set(NHItemList.CarminiteFragment.getIS());
        CustomItemList.SnowQueenBloodDrop.set(NHItemList.SnowQueenBloodDrop.getIS());
        CustomItemList.SnowQueenBlood.set(NHItemList.SnowQueenBlood.getIS());
        CustomItemList.AdvancedBoard.set(NHItemList.AdvancedBoard.getIS());
        CustomItemList.AdvancedProcessorBoard.set(NHItemList.AdvancedProcessorBoard.getIS());
        CustomItemList.HighEnergyFlowCircuit.set(NHItemList.HighEnergyFlowCircuit.getIS());
        CustomItemList.NanoCircuit.set(NHItemList.NanoCircuit.getIS());
        CustomItemList.PikoCircuit.set(NHItemList.PikoCircuit.getIS());
        CustomItemList.QuantumCircuit.set(NHItemList.QuantumCircuit.getIS());
        CustomItemList.CarbonPartBoots.set(NHItemList.CarbonPartBoots.getIS());
        CustomItemList.CarbonPartChestplate.set(NHItemList.CarbonPartChestplate.getIS());
        CustomItemList.CarbonPartHelmet.set(NHItemList.CarbonPartHelmet.getIS());
        CustomItemList.CarbonPartHelmetNightVision.set(NHItemList.CarbonPartHelmetNightVision.getIS());
        CustomItemList.CarbonPartLeggings.set(NHItemList.CarbonPartLeggings.getIS());
        CustomItemList.NanoRubberBoots.set(NHItemList.NanoRubberBoots.getIS());
        CustomItemList.NanoChestJetPack.set(NHItemList.NanoChestJetPack.getIS());
        CustomItemList.NanoPlatedLeggings.set(NHItemList.NanoPlatedLeggings.getIS());
        CustomItemList.NanoScubaHelmet.set(NHItemList.NanoScubaHelmet.getIS());
        CustomItemList.QuantumPartBoots.set(NHItemList.QuantumPartBoots.getIS());
        CustomItemList.QuantumPartChestplate.set(NHItemList.QuantumPartChestplate.getIS());
        CustomItemList.QuantumPartHelmetEmpty.set(NHItemList.QuantumPartHelmetEmpty.getIS());
        CustomItemList.QuantumPartHelmet.set(NHItemList.QuantumPartHelmet.getIS());
        CustomItemList.QuantumPartLeggings.set(NHItemList.QuantumPartLeggings.getIS());
        CustomItemList.HeavyDutyAlloyIngotT4.set(NHItemList.HeavyDutyAlloyIngotT4.getIS());
        CustomItemList.HeavyDutyAlloyIngotT5.set(NHItemList.HeavyDutyAlloyIngotT5.getIS());
        CustomItemList.HeavyDutyAlloyIngotT6.set(NHItemList.HeavyDutyAlloyIngotT6.getIS());
        CustomItemList.HeavyDutyAlloyIngotT7.set(NHItemList.HeavyDutyAlloyIngotT7.getIS());
        CustomItemList.HeavyDutyAlloyIngotT8.set(NHItemList.HeavyDutyAlloyIngotT8.getIS());
        CustomItemList.HeavyDutyAlloyIngotT9.set(NHItemList.HeavyDutyAlloyIngotT9.getIS());
        CustomItemList.HighEnergyCircuitParts.set(NHItemList.HighEnergyCircuitParts.getIS());
        CustomItemList.EngravedDiamondCrystalChip.set(NHItemList.EngravedDiamondCrystalChip.getIS());
        CustomItemList.EngravedEnergyChip.set(NHItemList.EngravedEnergyChip.getIS());
        CustomItemList.NanoBoard.set(NHItemList.NanoBoard.getIS());
        CustomItemList.NanoCrystal.set(NHItemList.NanoCrystal.getIS());
        CustomItemList.NanoProcessorBoard.set(NHItemList.NanoProcessorBoard.getIS());
        CustomItemList.QuantumBoard.set(NHItemList.QuantumBoard.getIS());
        CustomItemList.QuantumProcessorBoard.set(NHItemList.QuantumProcessorBoard.getIS());
        CustomItemList.EngravedQuantumChip.set(NHItemList.EngravedQuantumChip.getIS());
        CustomItemList.IridiumAlloyItemCasing.set(NHItemList.IridiumAlloyItemCasing.getIS());
        CustomItemList.ReinforcedGlassPLate.set(NHItemList.ReinforcedGlassPlate.getIS());
        CustomItemList.ReinforcedGlassLense.set(NHItemList.ReinforcedGlassLense.getIS());
        CustomItemList.MarshmallowForm.set(NHItemList.MarshmallowForm.getIS());
        CustomItemList.MarshmallowFormMold.set(NHItemList.MarshmallowFormMold.getIS());
        CustomItemList.UncookedSlush.set(NHItemList.UncookedSlush.getIS());
        CustomItemList.MalformedSlush.set(NHItemList.MalformedSlush.getIS());
        CustomItemList.GlowingMarshmallow.set(NHItemList.GlowingMarshmallow.getIS());
        CustomItemList.Marshmallow.set(NHItemList.Marshmallow.getIS());
        CustomItemList.MoldHelmet.set(NHItemList.MoldHelmet.getIS());
        CustomItemList.MoldChestplate.set(NHItemList.MoldChestplate.getIS());
        CustomItemList.MoldLeggings.set(NHItemList.MoldLeggings.getIS());
        CustomItemList.MoldBoots.set(NHItemList.MoldBoots.getIS());
        CustomItemList.IchoriumCap.set(NHItemList.IchoriumCap.getIS());
        CustomItemList.CoinDarkWizard.set(NHItemList.CoinDarkWizard.getIS());
        CustomItemList.CoinDarkWizardI.set(NHItemList.CoinDarkWizardI.getIS());
        CustomItemList.CoinDarkWizardII.set(NHItemList.CoinDarkWizardII.getIS());
        CustomItemList.CoinDarkWizardIII.set(NHItemList.CoinDarkWizardIII.getIS());
        CustomItemList.CoinDarkWizardIV.set(NHItemList.CoinDarkWizardIV.getIS());
        CustomItemList.CoinTechnician.set(NHItemList.CoinTechnician.getIS());
        CustomItemList.CoinTechnicianI.set(NHItemList.CoinTechnicianI.getIS());
        CustomItemList.CoinTechnicianII.set(NHItemList.CoinTechnicianII.getIS());
        CustomItemList.CoinTechnicianIII.set(NHItemList.CoinTechnicianIII.getIS());
        CustomItemList.CoinTechnicianIV.set(NHItemList.CoinTechnicianIV.getIS());
        CustomItemList.CoinAdventure.set(NHItemList.CoinAdventure.getIS());
        CustomItemList.CoinAdventureI.set(NHItemList.CoinAdventureI.getIS());
        CustomItemList.CoinAdventureII.set(NHItemList.CoinAdventureII.getIS());
        CustomItemList.CoinAdventureIII.set(NHItemList.CoinAdventureIII.getIS());
        CustomItemList.CoinAdventureIV.set(NHItemList.CoinAdventureIV.getIS());
        CustomItemList.CoinBees.set(NHItemList.CoinBees.getIS());
        CustomItemList.CoinBeesI.set(NHItemList.CoinBeesI.getIS());
        CustomItemList.CoinBeesII.set(NHItemList.CoinBeesII.getIS());
        CustomItemList.CoinBeesIII.set(NHItemList.CoinBeesIII.getIS());
        CustomItemList.CoinBeesIV.set(NHItemList.CoinBeesIV.getIS());
        CustomItemList.CoinBlood.set(NHItemList.CoinBlood.getIS());
        CustomItemList.CoinBloodI.set(NHItemList.CoinBloodI.getIS());
        CustomItemList.CoinBloodII.set(NHItemList.CoinBloodII.getIS());
        CustomItemList.CoinBloodIII.set(NHItemList.CoinBloodIII.getIS());
        CustomItemList.CoinBloodIV.set(NHItemList.CoinBloodIV.getIS());
        CustomItemList.CoinFarmer.set(NHItemList.CoinFarmer.getIS());
        CustomItemList.CoinFarmerI.set(NHItemList.CoinFarmerI.getIS());
        CustomItemList.CoinFarmerII.set(NHItemList.CoinFarmerII.getIS());
        CustomItemList.CoinFarmerIII.set(NHItemList.CoinFarmerIII.getIS());
        CustomItemList.CoinFarmerIV.set(NHItemList.CoinFarmerIV.getIS());
        CustomItemList.CoinForestry.set(NHItemList.CoinForestry.getIS());
        CustomItemList.CoinForestryI.set(NHItemList.CoinForestryI.getIS());
        CustomItemList.CoinForestryII.set(NHItemList.CoinForestryII.getIS());
        CustomItemList.CoinForestryIII.set(NHItemList.CoinForestryIII.getIS());
        CustomItemList.CoinForestryIV.set(NHItemList.CoinForestryIV.getIS());
        CustomItemList.CoinSurvivor.set(NHItemList.CoinSurvivor.getIS());
        CustomItemList.CoinSurvivorI.set(NHItemList.CoinSurvivorI.getIS());
        CustomItemList.CoinSurvivorII.set(NHItemList.CoinSurvivorII.getIS());
        CustomItemList.CoinSurvivorIII.set(NHItemList.CoinSurvivorIII.getIS());
        CustomItemList.CoinSurvivorIV.set(NHItemList.CoinSurvivorIV.getIS());
        CustomItemList.CoinSpace.set(NHItemList.CoinSpace.getIS());
        CustomItemList.CoinSpaceI.set(NHItemList.CoinSpaceI.getIS());
        CustomItemList.CoinSpaceII.set(NHItemList.CoinSpaceII.getIS());
        CustomItemList.CoinSpaceIII.set(NHItemList.CoinSpaceIII.getIS());
        CustomItemList.CoinSpaceIV.set(NHItemList.CoinSpaceIV.getIS());
        CustomItemList.CoinChunkloaderTierI.set(NHItemList.CoinChunkloaderTierI.getIS());
        CustomItemList.CoinChunkloaderTierI.getItem().setMaxStackSize(4);
        CustomItemList.CoinChunkloaderTierII.set(NHItemList.CoinChunkloaderTierII.getIS());
        CustomItemList.CoinChunkloaderTierII.getItem().setMaxStackSize(4);
        CustomItemList.CoinChunkloaderTierIII.set(NHItemList.CoinChunkloaderTierIII.getIS());
        CustomItemList.CoinChunkloaderTierIII.getItem().setMaxStackSize(4);
        CustomItemList.CoinChunkloaderTierIV.set(NHItemList.CoinChunkloaderTierIV.getIS());
        CustomItemList.CoinChunkloaderTierIV.getItem().setMaxStackSize(4);
        CustomItemList.CoinChunkloaderTierV.set(NHItemList.CoinChunkloaderTierV.getIS());
        CustomItemList.CoinChunkloaderTierV.getItem().setMaxStackSize(4);
        CustomItemList.CoinChemist.set(NHItemList.CoinChemist.getIS());
        CustomItemList.CoinChemistI.set(NHItemList.CoinChemistI.getIS());
        CustomItemList.CoinChemistII.set(NHItemList.CoinChemistII.getIS());
        CustomItemList.CoinChemistIII.set(NHItemList.CoinChemistIII.getIS());
        CustomItemList.CoinChemistIV.set(NHItemList.CoinChemistIV.getIS());
        CustomItemList.CoinCook.set(NHItemList.CoinCook.getIS());
        CustomItemList.CoinCookI.set(NHItemList.CoinCookI.getIS());
        CustomItemList.CoinCookII.set(NHItemList.CoinCookII.getIS());
        CustomItemList.CoinCookIII.set(NHItemList.CoinCookIII.getIS());
        CustomItemList.CoinCookIV.set(NHItemList.CoinCookIV.getIS());
        CustomItemList.CoinBlank.set(NHItemList.CoinBlank.getIS());
        CustomItemList.CoinBlankI.set(NHItemList.CoinBlankI.getIS());
        CustomItemList.CoinBlankII.set(NHItemList.CoinBlankII.getIS());
        CustomItemList.CoinBlankIII.set(NHItemList.CoinBlankIII.getIS());
        CustomItemList.CoinBlankIV.set(NHItemList.CoinBlankIV.getIS());
        CustomItemList.CoinSmith.set(NHItemList.CoinSmith.getIS());
        CustomItemList.CoinSmithI.set(NHItemList.CoinSmithI.getIS());
        CustomItemList.CoinSmithII.set(NHItemList.CoinSmithII.getIS());
        CustomItemList.CoinSmithIII.set(NHItemList.CoinSmithIII.getIS());
        CustomItemList.CoinSmithIV.set(NHItemList.CoinSmithIV.getIS());
        CustomItemList.CoinWitch.set(NHItemList.CoinWitch.getIS());
        CustomItemList.CoinWitchI.set(NHItemList.CoinWitchI.getIS());
        CustomItemList.CoinWitchII.set(NHItemList.CoinWitchII.getIS());
        CustomItemList.CoinWitchIII.set(NHItemList.CoinWitchIII.getIS());
        CustomItemList.CoinWitchIV.set(NHItemList.CoinWitchIV.getIS());
        CustomItemList.CoinFlower.set(NHItemList.CoinFlower.getIS());
        CustomItemList.CoinFlowerI.set(NHItemList.CoinFlowerI.getIS());
        CustomItemList.CoinFlowerII.set(NHItemList.CoinFlowerII.getIS());
        CustomItemList.CoinFlowerIII.set(NHItemList.CoinFlowerIII.getIS());
        CustomItemList.CoinFlowerIV.set(NHItemList.CoinFlowerIV.getIS());
        CustomItemList.CoinDonation.set(NHItemList.CoinDonation.getIS());
        CustomItemList.WetTofu.set(NHItemList.WetTofu.getIS());
        CustomItemList.NetherStarFragment.set(NHItemList.NetherStarFragment.getIS());
        CustomItemList.WeakOrb.set(NHItemList.WeakOrb.getIS());
        CustomItemList.ApprenticeOrb.set(NHItemList.ApprenticeOrb.getIS());
        CustomItemList.MasterOrb.set(NHItemList.MasterOrb.getIS());
        CustomItemList.MagicianOrb.set(NHItemList.MagicianOrb.getIS());
        CustomItemList.ArchmageOrb.set(NHItemList.ArchmageOrb.getIS());
        CustomItemList.TranscendentOrb.set(NHItemList.TranscendentOrb.getIS());
        CustomItemList.ArcaneSlate.set(NHItemList.ArcaneSlate.getIS());
        CustomItemList.RawOrbTier1.set(NHItemList.RawOrbTier1.getIS());
        CustomItemList.RawOrbTier2.set(NHItemList.RawOrbTier2.getIS());
        CustomItemList.RawOrbTier3.set(NHItemList.RawOrbTier3.getIS());
        CustomItemList.RawOrbTier4.set(NHItemList.RawOrbTier4.getIS());
        CustomItemList.RawOrbTier5.set(NHItemList.RawOrbTier5.getIS());
        CustomItemList.RawOrbTier6.set(NHItemList.RawOrbTier6.getIS());
        CustomItemList.TeleposerFrame.set(NHItemList.TeleposerFrame.getIS());
        CustomItemList.MysteriousCrystal.set(NHItemList.MysteriousCrystal.getIS());
        CustomItemList.WoodenCasing.set(NHItemList.WoodenCasing.getIS());
        CustomItemList.EssentiaCircuit.set(NHItemList.EssentiaCircuit.getIS());
        CustomItemList.EngineeringProcessorEssentiaPulsatingCore
                .set(NHItemList.EngineeringProcessorEssentiaPulsatingCore.getIS());
        CustomItemList.ManyullynCrystal.set(NHItemList.ManyullynCrystal.getIS());
        CustomItemList.SchematicsAstroMiner.set(NHItemList.SchematicsAstroMiner.getIS());
        CustomItemList.SchematicsCargoRocket.set(NHItemList.SchematicsCargoRocket.getIS());
        CustomItemList.SchematicsMoonBuggy.set(NHItemList.SchematicsMoonBuggy.getIS());
        CustomItemList.SchematicsTier1.set(NHItemList.SchematicsTier1.getIS());
        CustomItemList.SchematicsTier2.set(NHItemList.SchematicsTier2.getIS());
        CustomItemList.SchematicsTier3.set(NHItemList.SchematicsTier3.getIS());
        CustomItemList.SchematicsTier4.set(NHItemList.SchematicsTier4.getIS());
        CustomItemList.SchematicsTier5.set(NHItemList.SchematicsTier5.getIS());
        CustomItemList.SchematicsTier6.set(NHItemList.SchematicsTier6.getIS());
        CustomItemList.SchematicsTier7.set(NHItemList.SchematicsTier7.getIS());
        CustomItemList.SchematicsTier8.set(NHItemList.SchematicsTier8.getIS());
        CustomItemList.RawSDHCAlloy.set(NHItemList.RawSDHCAlloy.getIS());
        CustomItemList.TungstenString.set(NHItemList.TungstenString.getIS());
        CustomItemList.MeteoricIronString.set(NHItemList.MeteoricIronString.getIS());
        CustomItemList.BlackPlutoniumCompressedPlate.set(NHItemList.BlackPlutoniumCompressedPlate.getIS());
        CustomItemList.DeshDualCompressedPlates.set(NHItemList.DeshDualCompressedPlates.getIS());
        CustomItemList.TitaniumDualCompressedPlates.set(NHItemList.TitaniumDualCompressedPlates.getIS());
        CustomItemList.IceCompressedPlate.set(NHItemList.IceCompressedPlate.getIS());
        CustomItemList.LedoxCompressedPlate.set(NHItemList.LedoxCompressedPlate.getIS());
        CustomItemList.IceDualCompressedPlates.set(NHItemList.IceDualCompressedPlates.getIS());
        CustomItemList.QuantinumCompressedPlate.set(NHItemList.QuantinumCompressedPlate.getIS());
        CustomItemList.QuantinumDualCompressedPlates.set(NHItemList.QuantinumDualCompressedPlates.getIS());
        CustomItemList.MytrylCompressedPlate.set(NHItemList.MytrylCompressedPlate.getIS());
        CustomItemList.MytrylDualCompressedPlates.set(NHItemList.MytrylDualCompressedPlates.getIS());
        CustomItemList.MysteriousCrystalCompressedPlate.set(NHItemList.MysteriousCrystalCompressedPlate.getIS());
        CustomItemList.MysteriousCrystalDualCompressedPlates
                .set(NHItemList.MysteriousCrystalDualCompressedPlates.getIS());
        CustomItemList.SteelDualCompressedPlates.set(NHItemList.SteelDualCompressedPlates.getIS());
        CustomItemList.TinDualCompressedPlates.set(NHItemList.TinDualCompressedPlates.getIS());
        CustomItemList.CopperDualCompressedPlates.set(NHItemList.CopperDualCompressedPlates.getIS());
        CustomItemList.IronDualCompressedPlates.set(NHItemList.IronDualCompressedPlates.getIS());
        CustomItemList.MeteoricIronDualCompressedPlates.set(NHItemList.MeteoricIronDualCompressedPlates.getIS());
        CustomItemList.MediumFuelCanister.set(NHItemList.MediumFuelCanister.getIS());
        CustomItemList.LargeFuelCanister.set(NHItemList.LargeFuelCanister.getIS());
        CustomItemList.ExtraLargeFuelCanister.set(NHItemList.ExtraLargeFuelCanister.getIS());
        CustomItemList.MytrylCrystal.set(NHItemList.MytrylCrystal.getIS());
        CustomItemList.CallistoIceCompressedPlate.set(NHItemList.CallistoIceCompressedPlate.getIS());
        CustomItemList.HeavyDutyRocketEngineTier3.set(NHItemList.HeavyDutyRocketEngineTier3.getIS());
        CustomItemList.HeavyDutyRocketEngineTier4.set(NHItemList.HeavyDutyRocketEngineTier4.getIS());
        CustomItemList.HeavyDutyNoseConeTier3.set(NHItemList.HeavyDutyNoseConeTier3.getIS());
        CustomItemList.HeavyDutyNoseConeTier4.set(NHItemList.HeavyDutyNoseConeTier4.getIS());
        CustomItemList.HeavyDutyRocketFinsTier3.set(NHItemList.HeavyDutyRocketFinsTier3.getIS());
        CustomItemList.HeavyDutyRocketFinsTier4.set(NHItemList.HeavyDutyRocketFinsTier4.getIS());
        CustomItemList.Tier2Booster.set(NHItemList.Tier2Booster.getIS());
        CustomItemList.Tier3Booster.set(NHItemList.Tier3Booster.getIS());
        CustomItemList.Tier4Booster.set(NHItemList.Tier4Booster.getIS());
        CustomItemList.HeavyDutyPlateTier4.set(NHItemList.HeavyDutyPlateTier4.getIS());
        CustomItemList.HeavyDutyPlateTier5.set(NHItemList.HeavyDutyPlateTier5.getIS());
        CustomItemList.HeavyDutyPlateTier6.set(NHItemList.HeavyDutyPlateTier6.getIS());
        CustomItemList.HeavyDutyPlateTier7.set(NHItemList.HeavyDutyPlateTier7.getIS());
        CustomItemList.HeavyDutyPlateTier8.set(NHItemList.HeavyDutyPlateTier8.getIS());
        CustomItemList.LeadNickelPlate.set(NHItemList.LeadNickelPlate.getIS());
        CustomItemList.LeadOriharukonPlate.set(NHItemList.LeadOriharukonPlate.getIS());
        CustomItemList.MoonStoneDust.set(NHItemList.MoonStoneDust.getIS());
        CustomItemList.MarsStoneDust.set(NHItemList.MarsStoneDust.getIS());
        CustomItemList.AsteroidsStoneDust.set(NHItemList.AsteroidsStoneDust.getIS());
        CustomItemList.PhobosStoneDust.set(NHItemList.PhobosStoneDust.getIS());
        CustomItemList.DeimosStoneDust.set(NHItemList.DeimosStoneDust.getIS());
        CustomItemList.CeresStoneDust.set(NHItemList.CeresStoneDust.getIS());
        CustomItemList.EuropaIceDust.set(NHItemList.EuropaIceDust.getIS());
        CustomItemList.EuropaStoneDust.set(NHItemList.EuropaStoneDust.getIS());
        CustomItemList.GanymedeStoneDust.set(NHItemList.GanymedeStoneDust.getIS());
        CustomItemList.CallistoStoneDust.set(NHItemList.CallistoStoneDust.getIS());
        CustomItemList.IoStoneDust.set(NHItemList.IoStoneDust.getIS());
        CustomItemList.VenusStoneDust.set(NHItemList.VenusStoneDust.getIS());
        CustomItemList.MercuryStoneDust.set(NHItemList.MercuryStoneDust.getIS());
        CustomItemList.EnceladusIceDust.set(NHItemList.EnceladusIceDust.getIS());
        CustomItemList.EnceladusStoneDust.set(NHItemList.EnceladusStoneDust.getIS());
        CustomItemList.TitanStoneDust.set(NHItemList.TitanStoneDust.getIS());
        CustomItemList.OberonStoneDust.set(NHItemList.OberonStoneDust.getIS());
        CustomItemList.ProteusStoneDust.set(NHItemList.ProteusStoneDust.getIS());
        CustomItemList.TritonStoneDust.set(NHItemList.TritonStoneDust.getIS());
        CustomItemList.PlutoIceDust.set(NHItemList.PlutoIceDust.getIS());
        CustomItemList.PlutoStoneDust.set(NHItemList.PlutoStoneDust.getIS());
        CustomItemList.HaumeaStoneDust.set(NHItemList.HaumeaStoneDust.getIS());
        CustomItemList.MakeMakeStoneDust.set(NHItemList.MakeMakeStoneDust.getIS());
        CustomItemList.MercuryCoreDust.set(NHItemList.MercuryCoreDust.getIS());
        CustomItemList.CentauriAStoneDust.set(NHItemList.CentauriAStoneDust.getIS());
        CustomItemList.CentauriASurfaceDust.set(NHItemList.CentauriASurfaceDust.getIS());
        CustomItemList.VegaBStoneDust.set(NHItemList.VegaBStoneDust.getIS());
        CustomItemList.BarnardaEStoneDust.set(NHItemList.BarnardaEStoneDust.getIS());
        CustomItemList.BarnardaFStoneDust.set(NHItemList.BarnardaFStoneDust.getIS());
        CustomItemList.IndustryFrame.set(NHItemList.IndustryFrame.getIS());
        CustomItemList.TCetiEStoneDust.set(NHItemList.TCetiEStoneDust.getIS());
        CustomItemList.AdsorptionFilterCasing.set(NHItemList.AdsorptionFilterCasing.getIS());
        CustomItemList.AdsorptionFilter.set(NHItemList.AdsorptionFilter.getIS());
        CustomItemList.AdsorptionFilterDirty.set(NHItemList.AdsorptionFilterDirty.getIS());
        CustomItemList.VoidSeed.set(NHItemList.VoidSeed.getIS());
        CustomItemList.WaferTier3.set(NHItemList.WaferTier3.getIS());
        CustomItemList.MirandaStoneDust.set(NHItemList.MirandaStoneDust.getIS());
        CustomItemList.MicaBasedPulp.set(NHItemList.MicaBasedPulp.getIS());
        CustomItemList.MicaBasedSheet.set(NHItemList.MicaBasedSheet.getIS());
        CustomItemList.MicaInsulatorSheet.set(NHItemList.MicaInsulatorSheet.getIS());
        CustomItemList.MicaInsulatorFoil.set(NHItemList.MicaInsulatorFoil.getIS());
        CustomItemList.HotNetherrackBrick.set(NHItemList.HotNetherrackBrick.getIS());
        CustomItemList.InfernalBrick.set(NHItemList.InfernalBrick.getIS());
        CustomItemList.AdvancedCokeOvenBrick.set(NHItemList.AdvancedCokeOvenBrick.getIS());
        CustomItemList.AdvancedCokeOvenBrickDust.set(NHItemList.AdvancedCokeOvenBrickDust.getIS());
        CustomItemList.CokeOvenBrick.set(NHItemList.CokeOvenBrick.getIS());
        CustomItemList.CokeOvenBrickDust.set(NHItemList.CokeOvenBrickDust.getIS());
        CustomItemList.MedalDerp.set(NHItemList.MedalDerp.getIS());
        CustomItemList.MedalDerp.getItem().setMaxStackSize(1);
        CustomItemList.MedalGTExplosion.set(NHItemList.MedalGTExplosion.getIS());
        CustomItemList.MedalGTExplosion.getItem().setMaxStackSize(1);
        CustomItemList.MedalBuilder.set(NHItemList.MedalBuilder.getIS());
        CustomItemList.MedalBuilder.getItem().setMaxStackSize(1);
        CustomItemList.MedalEngineer.set(NHItemList.MedalEngineer.getIS());
        CustomItemList.MedalEngineer.getItem().setMaxStackSize(1);
        CustomItemList.MedalWarp.set(NHItemList.MedalWarp.getIS());
        CustomItemList.MedalWarp.getItem().setMaxStackSize(1);
        CustomItemList.AluminoSilicateWool.set(NHItemList.AluminoSilicateWool.getIS());
        CustomItemList.MaceratedPlantmass.set(NHItemList.MaceratedPlantmass.getIS());
        CustomItemList.EngineCore.set(NHItemList.EngineCore.getIS());
        CustomItemList.TwilightCrystal.set(NHItemList.TwilightCrystal.getIS());
        CustomItemList.UnfiredClayBrick.set(NHItemList.UnfiredClayBrick.getIS());
        CustomItemList.UnfiredSearedBrick.set(NHItemList.UnfiredSearedBrick.getIS());
        CustomItemList.UnfiredCokeOvenBrick.set(NHItemList.UnfiredCokeOvenBrick.getIS());
        CustomItemList.UnfiredSlimeSoulBrick.set(NHItemList.UnfiredSlimeSoulBrick.getIS());
        CustomItemList.RawLapotronCrystal.set(NHItemList.RawLapotronCrystal.getIS());
        CustomItemList.LapotronDust.set(NHItemList.LapotronDust.getIS());
        CustomItemList.AluminiumIronPlate.set(NHItemList.AluminiumIronPlate.getIS());
        CustomItemList.ReinforcedAluminiumIronPlate.set(NHItemList.ReinforcedAluminiumIronPlate.getIS());
        CustomItemList.IrradiantReinforcedAluminiumPlate.set(NHItemList.IrradiantReinforcedAluminiumPlate.getIS());
        CustomItemList.IrradiantReinforcedTitaniumPlate.set(NHItemList.IrradiantReinforcedTitaniumPlate.getIS());
        CustomItemList.IrradiantReinforcedTungstenPlate.set(NHItemList.IrradiantReinforcedTungstenPlate.getIS());
        CustomItemList.IrradiantReinforcedTungstenSteelPlate
                .set(NHItemList.IrradiantReinforcedTungstenSteelPlate.getIS());
        CustomItemList.IrradiantReinforcedChromePlate.set(NHItemList.IrradiantReinforcedChromePlate.getIS());
        CustomItemList.IrradiantReinforcedNaquadriaPlate.set(NHItemList.IrradiantReinforcedNaquadriaPlate.getIS());
        CustomItemList.IrradiantReinforcedNeutroniumPlate.set(NHItemList.IrradiantReinforcedNeutroniumPlate.getIS());
        CustomItemList.IrradiantReinforcedBedrockiumPlate.set(NHItemList.IrradiantReinforcedBedrockiumPlate.getIS());
        CustomItemList.QuantumCrystal.set(NHItemList.QuantumCrystal.getIS());
        CustomItemList.MysteriousCrystalLens.set(NHItemList.MysteriousCrystalLens.getIS());
        CustomItemList.MysteriousCrystalGemFlawless.set(NHItemList.MysteriousCrystalGemFlawless.getIS());
        CustomItemList.MysteriousCrystalGemExquisite.set(NHItemList.MysteriousCrystalGemExquisite.getIS());
        CustomItemList.PrimordialPearlFragment.set(NHItemList.PrimordialPearlFragment.getIS());
        CustomItemList.SandStoneRod.set(NHItemList.SandStoneRod.getIS());
        CustomItemList.TheBigEgg.set(NHItemList.TheBigEgg.getIS());
        CustomItemList.MutatedEgg.set(NHItemList.MutatedEgg.getIS());
        CustomItemList.DraconiumEgg.set(NHItemList.DraconiumEgg.getIS());
        CustomItemList.EnderEgg.set(NHItemList.EnderEgg.getIS());
        CustomItemList.ChargedCertusQuartzDust.set(NHItemList.ChargedCertusQuartzDust.getIS());
        CustomItemList.RawPicoWafer.set(NHItemList.RawPicoWafer.getIS());
        CustomItemList.PicoWafer.set(NHItemList.PicoWafer.getIS());
        CustomItemList.StargateFramePart.set(NHItemList.StargateFramePart.getIS());
        CustomItemList.StargateChevron.set(NHItemList.StargateChevron.getIS());
        CustomItemList.StargateShieldingFoil.set(NHItemList.StargateShieldingFoil.getIS());
        CustomItemList.StargateCrystalDust.set(NHItemList.StargateCrystalDust.getIS());
        CustomItemList.TCetiESeaweedExtract.set(NHItemList.TCetiESeaweedExtract.getIS());
        CustomItemList.RawNeutronium.set(NHItemList.RawNeutronium.getIS());
        CustomItemList.NeutronReflectorSmallParts.set(NHItemList.NeutronReflectorSmallParts.getIS());
        CustomItemList.NeutronReflectorParts.set(NHItemList.NeutronReflectorParts.getIS());
        CustomItemList.Empty180SpCell.set(NHItemList.Empty180SpCell.getIS());
        CustomItemList.Empty360SpCell.set(NHItemList.Empty360SpCell.getIS());
        CustomItemList.Empty540SpCell.set(NHItemList.Empty540SpCell.getIS());
        CustomItemList.Empty1080SpCell.set(NHItemList.Empty1080SpCell.getIS());
        CustomItemList.TenKCell.set(NHItemList.TenKCell.getIS());
        CustomItemList.ThirtyKCell.set(NHItemList.ThirtyKCell.getIS());
        CustomItemList.SixtyKCell.set(NHItemList.SixtyKCell.getIS());
        CustomItemList.ArtificialLeather.set(NHItemList.ArtificialLeather.getIS());
        CustomItemList.EctoplasmaChip.set(NHItemList.EctoplasmaChip.getIS());
        CustomItemList.EctoplasmaFragment.set(NHItemList.EctoplasmaFragment.getIS());
        CustomItemList.ArcaneShardChip.set(NHItemList.ArcaneShardChip.getIS());
        CustomItemList.ArcaneShardFragment.set(NHItemList.ArcaneShardFragment.getIS());
        CustomItemList.RuneOfPowerFragment.set(NHItemList.RuneOfPowerFragment.getIS());
        CustomItemList.RuneOfAgilityFragment.set(NHItemList.RuneOfAgilityFragment.getIS());
        CustomItemList.RuneOfVigorFragment.set(NHItemList.RuneOfVigorFragment.getIS());
        CustomItemList.RuneOfDefenseFragment.set(NHItemList.RuneOfDefenseFragment.getIS());
        CustomItemList.RuneOfMagicFragment.set(NHItemList.RuneOfMagicFragment.getIS());
        CustomItemList.RuneOfVoidFragment.set(NHItemList.RuneOfVoidFragment.getIS());
        CustomItemList.NandChipBoard.set(
                GT.addItem(81, "NAND Chip Array", "Chips on Board", "circuitPrimitiveArray", SubTag.NO_UNIFICATION));
        CustomItemList.LogicProcessorItemGoldCore.set(NHItemList.LogicProcessorItemGoldCore.getIS());
        CustomItemList.EngineeringProcessorItemDiamondCore.set(NHItemList.EngineeringProcessorItemDiamondCore.getIS());
        CustomItemList.EngineeringProcessorItemEmeraldCore.set(NHItemList.EngineeringProcessorItemEmeraldCore.getIS());
        CustomItemList.EngineeringProcessorItemAdvEmeraldCore
                .set(NHItemList.EngineeringProcessorItemAdvEmeraldCore.getIS());
        CustomItemList.Display.set(NHItemList.Display.getIS());
        CustomItemList.TitaniumIronPlate.set(NHItemList.TitaniumIronPlate.getIS());
        CustomItemList.ReinforcedTitaniumIronPlate.set(NHItemList.ReinforcedTitaniumIronPlate.getIS());
        CustomItemList.TungstenIronPlate.set(NHItemList.TungstenIronPlate.getIS());
        CustomItemList.ReinforcedTungstenIronPlate.set(NHItemList.ReinforcedTungstenIronPlate.getIS());
        CustomItemList.TungstenSteelIronPlate.set(NHItemList.TungstenSteeLIronPlate.getIS());
        CustomItemList.ReinforcedTungstenSteelIronPlate.set(NHItemList.ReinforcedTungstenSteelIronPlate.getIS());
        CustomItemList.ChromeIronPlate.set(NHItemList.ChromeIronPlate.getIS());
        CustomItemList.ReinforcedChromeIronPlate.set(NHItemList.ReinforcedChromeIronPlate.getIS());
        CustomItemList.NaquadriaIronPlate.set(NHItemList.NaquadriaIronPlate.getIS());
        CustomItemList.ReinforcedNaquadriaIronPlate.set(NHItemList.ReinforcedNaquadriaIronPlate.getIS());
        CustomItemList.NeutroniumIronPlate.set(NHItemList.NeutroniumIronPlate.getIS());
        CustomItemList.ReinforcedNeutroniumIronPlate.set(NHItemList.ReinforcedNeutroniumIronPlate.getIS());
        CustomItemList.BedrockiumIronPlate.set(NHItemList.BedrockiumIronPlate.getIS());
        CustomItemList.ReinforcedBedrockiumIronPlate.set(NHItemList.ReinforcedBedrockiumIronPlate.getIS());
        CustomItemList.BioBall.set(NHItemList.BioBall.getIS());
        CustomItemList.PotassiumHydroxideDust.set(NHItemList.PotassiumHydroxideDust.getIS());
        CustomItemList.RadoxPolymerLens.set(NHItemList.RadoxPolymerLens.getIS());
        CustomItemList.ChromaticLens.set(NHItemList.ChromaticLens.getIS());
        CustomItemList.ChromaticGem.set(NHItemList.ChromaticGem.getIS());
        CustomItemList.ChromaticGemFlawless.set(NHItemList.ChromaticGemFlawless.getIS());
        CustomItemList.ChromaticGemExquisite.set(NHItemList.ChromaticGemExquisite.getIS());
        CustomItemList.NanoCircuitOrigin.set(NHItemList.NanoCircuitOrigin.getIS());
        CustomItemList.ChevronOrigin.set(NHItemList.ChevronOrigin.getIS());
        CustomItemList.FramePartOrigin.set(NHItemList.FramePartOrigin.getIS());
        CustomItemList.GatePlateOrigin.set(NHItemList.GatePlateOrigin.getIS());
        CustomItemList.PikoCircuitPolychrome.set(NHItemList.PikoCircuitPolychrome.getIS());
        CustomItemList.QuantumCircuitPolychrome.set(NHItemList.QuantumCircuitPolychrome.getIS());
        CustomItemList.GatePlatePolychrome.set(NHItemList.GatePlatePolychrome.getIS());
        CustomItemList.ChevronPolychrome.set(NHItemList.ChevronPolychrome.getIS());
        CustomItemList.FramePartPolychrome.set(NHItemList.FramePartPolychrome.getIS());
        CustomItemList.PikoCircuitDimensional.set(NHItemList.PikoCircuitDimensional.getIS());
        CustomItemList.QuantumCircuitDimensional.set(NHItemList.QuantumCircuitDimensional.getIS());
        CustomItemList.GatePlateDimensional.set(NHItemList.GatePlateDimensional.getIS());
        CustomItemList.ChevronDimensional.set(NHItemList.ChevronDimensional.getIS());
        CustomItemList.FramePartDimensional.set(NHItemList.FramePartDimensional.getIS());
        CustomItemList.GatePlateHarmonic.set(NHItemList.GatePlateHarmonic.getIS());
        CustomItemList.ChevronHarmonic.set(NHItemList.ChevronHarmonic.getIS());
        CustomItemList.FramePartHarmonic.set(NHItemList.FramePartHarmonic.getIS());
        CustomItemList.StargateDustAncients.set(NHItemList.StargateDustAncients.getIS());
        CustomItemList.StargateCrystalAncients.set(NHItemList.StargateCrystalAncients.getIS());
        CustomItemList.SolarLightSplitter.set(NHItemList.SolarLightSplitter.getIS());
        CustomItemList.EnderQuantumComponent.set(NHItemList.EnderQuantumComponent.getIS());
        CustomItemList.BlueSpectralComponent.set(NHItemList.BlueSpectralComponent.getIS());
        CustomItemList.GreenSpectralComponent.set(NHItemList.GreenSpectralComponent.getIS());
        CustomItemList.RedSpectralComponent.set(NHItemList.RedSpectralComponent.getIS());
    }

    private void registerTooltips() {
        registerTieredTooltip(CustomItemList.PikoCircuit.get(1), UMV);
        registerTieredTooltip(CustomItemList.QuantumCircuit.get(1), UXV);
        List<ItemStack> origin = Arrays.asList(
                NHItemList.ChevronOrigin.getIS(),
                NHItemList.FramePartOrigin.getIS(),
                NHItemList.GatePlateOrigin.getIS(),
                NHItemList.NanoCircuitOrigin.getIS(),
                NHItemList.StargateDustAncients.getIS(),
                NHItemList.StargateCrystalAncients.getIS());
        List<ItemStack> polychrome = Arrays.asList(
                NHItemList.PikoCircuitPolychrome.getIS(),
                NHItemList.QuantumCircuitPolychrome.getIS(),
                NHItemList.GatePlatePolychrome.getIS(),
                NHItemList.ChevronPolychrome.getIS(),
                NHItemList.FramePartPolychrome.getIS());
        List<ItemStack> dimensional = Arrays.asList(
                NHItemList.PikoCircuitDimensional.getIS(),
                NHItemList.QuantumCircuitDimensional.getIS(),
                NHItemList.GatePlateDimensional.getIS(),
                NHItemList.ChevronDimensional.getIS(),
                NHItemList.FramePartDimensional.getIS());
        List<ItemStack> harmonic = Arrays.asList(
                NHItemList.GatePlateHarmonic.getIS(),
                NHItemList.ChevronHarmonic.getIS(),
                NHItemList.FramePartHarmonic.getIS());
        for (ItemStack itemStack : origin) {
            addItemTooltip(
                    itemStack,
                    () -> EnumChatFormatting.YELLOW + StatCollector.translateToLocal("item.Origin.tooltip")
                            + EnumChatFormatting.RESET
                            + EnumChatFormatting.BLUE
                            + " "
                            + StatCollector.translateToLocal("item.Origin.version"));
        }

        for (ItemStack itemStack : polychrome) {
            addItemTooltip(
                    itemStack,
                    () -> EnumChatFormatting.AQUA + StatCollector.translateToLocal("item.Polychrome.tooltip")
                            + EnumChatFormatting.RESET
                            + EnumChatFormatting.BLUE
                            + " "
                            + StatCollector.translateToLocal("item.Polychrome.version"));
        }

        for (ItemStack itemStack : dimensional) {
            addItemTooltip(
                    itemStack,
                    () -> EnumChatFormatting.LIGHT_PURPLE + StatCollector.translateToLocal("item.Dimensional.tooltip")
                            + EnumChatFormatting.RESET
                            + EnumChatFormatting.BLUE
                            + " "
                            + StatCollector.translateToLocal("item.Dimensional.version"));
        }

        for (ItemStack itemStack : harmonic) {
            addItemTooltip(
                    itemStack,
                    () -> EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("item.Harmonic.tooltip")
                            + EnumChatFormatting.RESET
                            + EnumChatFormatting.BLUE
                            + " "
                            + StatCollector.translateToLocal("item.Harmonic.version"));
        }
    }
}
