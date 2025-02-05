package com.dreammaster.gthandler;

import static gregtech.api.enums.GTValues.W;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import gregtech.api.interfaces.IItemContainer;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;

public enum CustomItemList implements IItemContainer {

    // CoreModItems
    EtchedLowVoltageWiring,
    EtchedInsaneVoltageWiring,
    EtchedLudicrousVoltageWiring,
    SteelBars,
    AluminiumBars,
    StainlessSteelBars,
    TungstenBars,
    TungstenSteelBars,
    IridiumBars,
    OsmiumBars,
    ChromeBars,
    TitaniumBars,
    NeutroniumBars,
    RedstoneAlloyBars,
    ElectricalSteelBars,
    ConductiveIronBars,
    EnergeticAlloyBars,
    VibrantAlloyBars,
    PulsatingIronBars,
    SoulariumBars,
    EnderiumBaseBars,
    EnderiumBars,
    PistonBlock,
    PistonPlate,
    ReinforcedGlassLense,
    ReinforcedGlassPLate,
    IridiumAlloyItemCasing,
    NagaScaleChip,
    NagaScaleFragment,
    LichBoneChip,
    LichBoneFragment,
    LichBone,
    FieryBloodDrop,
    CarminiteChip,
    CarminiteFragment,
    SnowQueenBloodDrop,
    SnowQueenBlood,
    NetherStarFragment,
    AdvancedBoard,
    AdvancedProcessorBoard,
    NanoBoard,
    NanoCrystal,
    NanoProcessorBoard,
    QuantumBoard,
    QuantumProcessorBoard,
    EngravedQuantumChip,
    HighEnergyCircuitParts,
    HighEnergyFlowCircuit,
    NanoCircuit,
    PikoCircuit,
    QuantumCircuit,
    EngravedDiamondCrystalChip,
    EngravedEnergyChip,
    CarbonPartBoots,
    CarbonPartChestplate,
    CarbonPartHelmet,
    CarbonPartHelmetNightVision,
    CarbonPartLeggings,
    NanoRubberBoots,
    NanoChestJetPack,
    NanoPlatedLeggings,
    NanoScubaHelmet,
    QuantumPartBoots,
    QuantumPartChestplate,
    QuantumPartHelmetEmpty,
    QuantumPartHelmet,
    QuantumPartLeggings,
    HeavyDutyAlloyIngotT4,
    HeavyDutyAlloyIngotT5,
    HeavyDutyAlloyIngotT6,
    HeavyDutyAlloyIngotT7,
    HeavyDutyAlloyIngotT8,
    HeavyDutyAlloyIngotT9,
    BlackPlutoniumCompressedPlate,
    DeshDualCompressedPlates,
    TitaniumDualCompressedPlates,
    IceCompressedPlate,
    IceDualCompressedPlates,
    QuantinumCompressedPlate,
    QuantinumDualCompressedPlates,
    MytrylDualCompressedPlates,
    MytrylCompressedPlate,
    MysteriousCrystalCompressedPlate,
    MysteriousCrystalDualCompressedPlates,
    SteelDualCompressedPlates,
    TinDualCompressedPlates,
    CopperDualCompressedPlates,
    IronDualCompressedPlates,
    MeteoricIronDualCompressedPlates,
    LedoxCompressedPlate,
    MytrylCrystal,
    CallistoIceCompressedPlate,
    MarshmallowForm,
    MarshmallowFormMold,
    UncookedSlush,
    MalformedSlush,
    GlowingMarshmallow,
    Marshmallow,
    MoldHelmet,
    MoldChestplate,
    MoldLeggings,
    MoldBoots,
    IchoriumCap,
    CoinTechnician,
    CoinTechnicianI,
    CoinTechnicianII,
    CoinTechnicianIII,
    CoinTechnicianIV,
    CoinDarkWizard,
    CoinDarkWizardI,
    CoinDarkWizardII,
    CoinDarkWizardIII,
    CoinDarkWizardIV,
    CoinAdventure,
    CoinAdventureI,
    CoinAdventureII,
    CoinAdventureIII,
    CoinAdventureIV,
    CoinBees,
    CoinBeesI,
    CoinBeesII,
    CoinBeesIII,
    CoinBeesIV,
    CoinBlood,
    CoinBloodI,
    CoinBloodII,
    CoinBloodIII,
    CoinBloodIV,
    CoinFarmer,
    CoinFarmerI,
    CoinFarmerII,
    CoinFarmerIII,
    CoinFarmerIV,
    CoinForestry,
    CoinForestryI,
    CoinForestryII,
    CoinForestryIII,
    CoinForestryIV,
    CoinSurvivor,
    CoinSurvivorI,
    CoinSurvivorII,
    CoinSurvivorIII,
    CoinSurvivorIV,
    CoinSpace,
    CoinSpaceI,
    CoinSpaceII,
    CoinSpaceIII,
    CoinSpaceIV,
    CoinChunkloaderTierI,
    CoinChunkloaderTierII,
    CoinChunkloaderTierIII,
    CoinChunkloaderTierIV,
    CoinChunkloaderTierV,
    CoinChemist,
    CoinChemistI,
    CoinChemistII,
    CoinChemistIII,
    CoinChemistIV,
    CoinCook,
    CoinCookI,
    CoinCookII,
    CoinCookIII,
    CoinCookIV,
    CoinBlank,
    CoinBlankI,
    CoinBlankII,
    CoinBlankIII,
    CoinBlankIV,
    CoinSmith,
    CoinSmithI,
    CoinSmithII,
    CoinSmithIII,
    CoinSmithIV,
    CoinWitch,
    CoinWitchI,
    CoinWitchII,
    CoinWitchIII,
    CoinWitchIV,
    CoinFlower,
    CoinFlowerI,
    CoinFlowerII,
    CoinFlowerIII,
    CoinFlowerIV,
    CoinDonation,
    WetTofu,
    WeakOrb,
    ApprenticeOrb,
    MasterOrb,
    MagicianOrb,
    ArchmageOrb,
    TranscendentOrb,
    RawOrbTier1,
    RawOrbTier2,
    RawOrbTier3,
    RawOrbTier4,
    RawOrbTier5,
    RawOrbTier6,
    ArcaneSlate,
    TeleposerFrame,
    MysteriousCrystal,
    ManyullynCrystal,
    WoodenCasing,
    EssentiaCircuit,
    EngineeringProcessorEssentiaPulsatingCore,
    SchematicsAstroMiner,
    SchematicsCargoRocket,
    SchematicsMoonBuggy,
    SchematicsTier1,
    SchematicsTier2,
    SchematicsTier3,
    SchematicsTier4,
    SchematicsTier5,
    SchematicsTier6,
    SchematicsTier7,
    SchematicsTier8,
    RawSDHCAlloy,
    TungstenString,
    MeteoricIronString,
    MediumFuelCanister,
    LargeFuelCanister,
    ExtraLargeFuelCanister,
    HeavyDutyRocketEngineTier3,
    HeavyDutyRocketEngineTier4,
    HeavyDutyNoseConeTier3,
    HeavyDutyNoseConeTier4,
    HeavyDutyRocketFinsTier3,
    HeavyDutyRocketFinsTier4,
    Tier2Booster,
    Tier3Booster,
    Tier4Booster,
    HeavyDutyPlateTier4,
    HeavyDutyPlateTier5,
    HeavyDutyPlateTier6,
    HeavyDutyPlateTier7,
    HeavyDutyPlateTier8,
    LeadNickelPlate,
    LeadOriharukonPlate,
    MoonStoneDust,
    MarsStoneDust,
    AsteroidsStoneDust,
    PhobosStoneDust,
    DeimosStoneDust,
    CeresStoneDust,
    EuropaIceDust,
    EuropaStoneDust,
    GanymedeStoneDust,
    CallistoStoneDust,
    IoStoneDust,
    VenusStoneDust,
    MercuryStoneDust,
    EnceladusIceDust,
    EnceladusStoneDust,
    TitanStoneDust,
    OberonStoneDust,
    ProteusStoneDust,
    TritonStoneDust,
    PlutoIceDust,
    PlutoStoneDust,
    HaumeaStoneDust,
    MakeMakeStoneDust,
    MercuryCoreDust,
    CentauriAStoneDust,
    CentauriASurfaceDust,
    VegaBStoneDust,
    BarnardaEStoneDust,
    BarnardaFStoneDust,
    TCetiEStoneDust,
    MirandaStoneDust,
    WaferTier3,
    IndustryFrame,
    AdsorptionFilterCasing,
    AdsorptionFilter,
    AdsorptionFilterDirty,
    VoidSeed,
    MicaBasedPulp,
    MicaBasedSheet,
    MicaInsulatorSheet,
    MicaInsulatorFoil,
    AluminoSilicateWool,
    HotNetherrackBrick,
    InfernalBrick,
    AdvancedCokeOvenBrick,
    AdvancedCokeOvenBrickDust,
    CokeOvenBrick,
    CokeOvenBrickDust,
    MedalDerp,
    MedalGTExplosion,
    MedalBuilder,
    MedalEngineer,
    MedalWarp,
    MaceratedPlantmass,
    EngineCore,
    TwilightCrystal,
    UnfiredClayBrick,
    UnfiredSearedBrick,
    UnfiredCokeOvenBrick,
    UnfiredSlimeSoulBrick,
    RawLapotronCrystal,
    LapotronDust,
    AluminiumIronPlate,
    ReinforcedAluminiumIronPlate,
    IrradiantReinforcedAluminiumPlate,
    IrradiantReinforcedTitaniumPlate,
    IrradiantReinforcedTungstenPlate,
    IrradiantReinforcedTungstenSteelPlate,
    IrradiantReinforcedChromePlate,
    IrradiantReinforcedNaquadriaPlate,
    IrradiantReinforcedNeutroniumPlate,
    IrradiantReinforcedBedrockiumPlate,
    QuantumCrystal,
    MysteriousCrystalLens,
    MysteriousCrystalGemFlawless,
    MysteriousCrystalGemExquisite,
    PrimordialPearlFragment,
    SandStoneRod,
    TheBigEgg,
    MutatedEgg,
    DraconiumEgg,
    EnderEgg,
    ChargedCertusQuartzDust,
    RawNeutronium,
    NeutronReflectorSmallParts,
    NeutronReflectorParts,
    TenKCell,
    ThirtyKCell,
    SixtyKCell,
    Empty180SpCell,
    Empty360SpCell,
    Empty540SpCell,
    Empty1080SpCell,
    RawPicoWafer,
    PicoWafer,
    StargateFramePart,
    StargateChevron,
    StargateShieldingFoil,
    StargateCrystalDust,
    TCetiESeaweedExtract,
    ArtificialLeather,
    EctoplasmaChip,
    EctoplasmaFragment,
    ArcaneShardChip,
    ArcaneShardFragment,
    RuneOfPowerFragment,
    RuneOfAgilityFragment,
    RuneOfVigorFragment,
    RuneOfDefenseFragment,
    RuneOfMagicFragment,
    RuneOfVoidFragment,
    NandChipBoard,
    LogicProcessorItemGoldCore,
    EngineeringProcessorItemDiamondCore,
    EngineeringProcessorItemEmeraldCore,
    EngineeringProcessorItemAdvEmeraldCore,
    Display,
    TitaniumIronPlate,
    ReinforcedTitaniumIronPlate,
    TungstenIronPlate,
    ReinforcedTungstenIronPlate,
    TungstenSteelIronPlate,
    ReinforcedTungstenSteelIronPlate,
    ChromeIronPlate,
    ReinforcedChromeIronPlate,
    NaquadriaIronPlate,
    ReinforcedNaquadriaIronPlate,
    NeutroniumIronPlate,
    ReinforcedNeutroniumIronPlate,
    BedrockiumIronPlate,
    ReinforcedBedrockiumIronPlate,
    BioBall,
    PotassiumHydroxideDust,
    ChromaticLens,
    ChromaticGem,
    ChromaticGemFlawless,
    ChromaticGemExquisite,
    RadoxPolymerLens,
    NanoCircuitOrigin,
    ChevronOrigin,
    FramePartOrigin,
    GatePlateOrigin,
    PikoCircuitPolychrome,
    QuantumCircuitPolychrome,
    GatePlatePolychrome,
    ChevronPolychrome,
    FramePartPolychrome,
    PikoCircuitDimensional,
    QuantumCircuitDimensional,
    GatePlateDimensional,
    ChevronDimensional,
    FramePartDimensional,
    GatePlateHarmonic,
    ChevronHarmonic,
    FramePartHarmonic,
    StargateDustAncients,
    StargateCrystalAncients,
    SolarLightSplitter,
    EnderQuantumComponent,
    BlueSpectralComponent,
    GreenSpectralComponent,
    RedSpectralComponent,

    ;

    private ItemStack mStack;
    private boolean mHasNotBeenSet = true;

    @Override
    public IItemContainer set(Item aItem) {
        mHasNotBeenSet = false;
        if (aItem == null) {
            return this;
        }
        ItemStack aStack = new ItemStack(aItem, 1, 0);
        mStack = GTUtility.copyAmount(1, aStack);
        return this;
    }

    @Override
    public IItemContainer set(ItemStack aStack) {
        mHasNotBeenSet = false;
        mStack = GTUtility.copyAmount(1, aStack);
        return this;
    }

    @Override
    public Item getItem() {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GTUtility.isStackInvalid(mStack)) {
            return null;
        }
        return mStack.getItem();
    }

    @Override
    public Block getBlock() {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        return GTUtility.getBlockFromItem(getItem());
    }

    @Override
    public final boolean hasBeenSet() {
        return !mHasNotBeenSet;
    }

    @Override
    public boolean isStackEqual(Object aStack) {
        return isStackEqual(aStack, false, false);
    }

    @Override
    public boolean isStackEqual(Object aStack, boolean aWildcard, boolean aIgnoreNBT) {
        return !GTUtility.isStackInvalid(aStack)
                && GTUtility.areUnificationsEqual((ItemStack) aStack, aWildcard ? getWildcard(1) : get(1), aIgnoreNBT);
    }

    @Override
    public ItemStack get(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GTUtility.isStackInvalid(mStack)) {
            return GTUtility.copyAmount(aAmount, aReplacements);
        }
        return GTUtility.copyAmount(aAmount, GTOreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getWildcard(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GTUtility.isStackInvalid(mStack)) {
            return GTUtility.copyAmount(aAmount, aReplacements);
        }
        return GTUtility.copyAmountAndMetaData(aAmount, W, GTOreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getUndamaged(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GTUtility.isStackInvalid(mStack)) {
            return GTUtility.copyAmount(aAmount, aReplacements);
        }
        return GTUtility.copyAmountAndMetaData(aAmount, 0, GTOreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getAlmostBroken(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GTUtility.isStackInvalid(mStack)) {
            return GTUtility.copyAmount(aAmount, aReplacements);
        }
        return GTUtility.copyAmountAndMetaData(aAmount, mStack.getMaxDamage() - 1, GTOreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getWithName(long aAmount, String aDisplayName, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (GTUtility.isStackInvalid(rStack)) {
            return null;
        }
        rStack.setStackDisplayName(aDisplayName);
        return GTUtility.copyAmount(aAmount, rStack);
    }

    @Override
    public ItemStack getWithCharge(long aAmount, int aEnergy, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (GTUtility.isStackInvalid(rStack)) {
            return null;
        }
        GTModHandler.chargeElectricItem(rStack, aEnergy, Integer.MAX_VALUE, true, false);
        return GTUtility.copyAmount(aAmount, rStack);
    }

    @Override
    public ItemStack getWithDamage(long aAmount, long aMetaValue, Object... aReplacements) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        if (GTUtility.isStackInvalid(mStack)) {
            return GTUtility.copyAmount(aAmount, aReplacements);
        }
        return GTUtility.copyAmountAndMetaData(aAmount, aMetaValue, GTOreDictUnificator.get(mStack));
    }

    @Override
    public IItemContainer registerOre(Object... aOreNames) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        for (Object tOreName : aOreNames) {
            GTOreDictUnificator.registerOre(tOreName, get(1));
        }
        return this;
    }

    @Override
    public IItemContainer registerWildcardAsOre(Object... aOreNames) {
        if (mHasNotBeenSet) {
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        }
        for (Object tOreName : aOreNames) {
            GTOreDictUnificator.registerOre(tOreName, getWildcard(1));
        }
        return this;
    }
}
