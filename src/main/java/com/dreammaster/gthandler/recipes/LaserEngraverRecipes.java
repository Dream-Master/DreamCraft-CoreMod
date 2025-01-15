package com.dreammaster.gthandler.recipes;

import static gregtech.api.enums.Mods.GalacticraftCore;
import static gregtech.api.enums.Mods.OpenComputers;
import static gregtech.api.recipe.RecipeMaps.laserEngraverRecipes;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.WaferEngravingRecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.dreammaster.gthandler.CustomItemList;
import com.dreammaster.item.NHItemList;

import bartworks.system.material.WerkstoffLoader;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;

public class LaserEngraverRecipes implements Runnable {

    @Override
    public void run() {
        // Energised tesseract
        GTValues.RA.stdBuilder().itemInputs(ItemList.Tesseract.get(1), GregtechItemList.Laser_Lens_Special.get(0))
                .itemOutputs(ItemList.EnergisedTesseract.get(1))
                .fluidOutputs(MaterialsUEVplus.ExcitedDTEC.getFluid(100)).requiresCleanRoom().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_UIV).noOptimize().addTo(laserEngraverRecipes);

        // Mysterious crystal upgrading

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.MysteriousCrystal, 9L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderPearl, 1)))
                .itemOutputs(CustomItemList.MysteriousCrystal.get(1L)).requiresCleanRoom().duration(60 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(laserEngraverRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.MysteriousCrystal, 9L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderEye, 1)))
                .itemOutputs(CustomItemList.MysteriousCrystal.get(1L)).requiresCleanRoom().duration(60 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(laserEngraverRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.MysteriousCrystal.get(3L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderPearl, 1)))
                .itemOutputs(CustomItemList.MysteriousCrystalGemFlawless.get(1L)).requiresCleanRoom()
                .duration(30 * SECONDS).eut(TierEU.RECIPE_ZPM).addTo(laserEngraverRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.MysteriousCrystal.get(3L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderEye, 1)))
                .itemOutputs(CustomItemList.MysteriousCrystalGemFlawless.get(1L)).requiresCleanRoom()
                .duration(30 * SECONDS).eut(TierEU.RECIPE_ZPM).addTo(laserEngraverRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.MysteriousCrystalGemFlawless.get(3L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderPearl, 1)))
                .itemOutputs(CustomItemList.MysteriousCrystalGemExquisite.get(1L)).requiresCleanRoom()
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LuV).addTo(laserEngraverRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        CustomItemList.MysteriousCrystalGemFlawless.get(3L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderEye, 1)))
                .itemOutputs(CustomItemList.MysteriousCrystalGemExquisite.get(1L)).requiresCleanRoom()
                .duration(30 * SECONDS).eut(TierEU.RECIPE_LuV).addTo(laserEngraverRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Mytryl, 9L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.Topaz, 1)))
                .itemOutputs(CustomItemList.MytrylCrystal.get(1L)).requiresCleanRoom().duration(60 * SECONDS).eut(256)
                .addTo(laserEngraverRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Mytryl, 9L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.Amber, 1)))
                .itemOutputs(CustomItemList.MytrylCrystal.get(1L)).requiresCleanRoom().duration(60 * SECONDS).eut(256)
                .addTo(laserEngraverRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer2.get(1L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderPearl, 1)))
                .itemOutputs(ItemList.Circuit_Wafer_NAND.get(1L)).requiresCleanRoom().duration(45 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(WaferEngravingRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer3.get(1L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderPearl, 1)))
                .itemOutputs(ItemList.Circuit_Wafer_NAND.get(4L)).requiresCleanRoom().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(WaferEngravingRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer4.get(1L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderPearl, 1)))
                .itemOutputs(ItemList.Circuit_Wafer_NAND.get(8L)).requiresCleanRoom().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(WaferEngravingRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer5.get(1L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderPearl, 1)))
                .itemOutputs(ItemList.Circuit_Wafer_NAND.get(16L)).requiresCleanRoom().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LuV).addTo(WaferEngravingRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer2.get(1L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderEye, 1)))
                .itemOutputs(ItemList.Circuit_Wafer_NOR.get(1L)).requiresCleanRoom().duration(45 * SECONDS)
                .eut(TierEU.RECIPE_HV).addTo(WaferEngravingRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer3.get(1L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderEye, 1)))
                .itemOutputs(ItemList.Circuit_Wafer_NOR.get(4L)).requiresCleanRoom().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_EV).addTo(WaferEngravingRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer4.get(1L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderEye, 1)))
                .itemOutputs(ItemList.Circuit_Wafer_NOR.get(8L)).requiresCleanRoom().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_IV).addTo(WaferEngravingRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer5.get(1L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.EnderEye, 1)))
                .itemOutputs(ItemList.Circuit_Wafer_NOR.get(16L)).requiresCleanRoom().duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LuV).addTo(WaferEngravingRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(ItemList.Circuit_Silicon_Wafer3.get(1L), CustomItemList.ReinforcedGlassLense.get(0))
                .itemOutputs(CustomItemList.WaferTier3.get(1L)).duration(10 * SECONDS).eut(1024)
                .addTo(WaferEngravingRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Wafer_SoC2.get(1L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.NetherStar, 1)))
                .itemOutputs(NHItemList.RawPicoWafer.getIS()).requiresCleanRoom().duration(5 * MINUTES)
                .eut((GTValues.V[8] - (GTValues.V[8] / 10))).addTo(laserEngraverRecipes);

        // Optical Boule
        GTValues.RA.stdBuilder().itemInputs(
                ItemList.Circuit_Silicon_Ingot5.get(1L), // Americium Boule
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.lens, 0), // Magneto resonatic lens
                WerkstoffLoader.Fayalit.get(OrePrefixes.lens, 0), // Fayalite lens
                CustomItemList.MysteriousCrystalLens.get(0)).itemOutputs(ItemList.Circuit_Silicon_Ingot6.get(1L))
                .fluidInputs(Materials.UUMatter.getFluid(16000L)).duration(30 * SECONDS).eut(7_864_320)
                .requiresCleanRoom().addTo(laserEngraverRecipes);

        Fluid oganesson = FluidRegistry.getFluid("oganesson") != null ? FluidRegistry.getFluid("oganesson")
                : FluidRegistry.getFluid("radon");

        // Photonically Enhanced Wafer
        GTValues.RA.stdBuilder().itemInputs(
                ItemList.Circuit_Silicon_Wafer6.get(1L), // Photonically Prepared Wafer
                Materials.Glowstone.getNanite(1),
                NHItemList.SolarLightSplitter.getIS(0) // Solar Light Splitter
        ).itemOutputs(ItemList.Circuit_Silicon_Wafer7.get(1L))
                .fluidInputs(Materials.Tin.getPlasma(1000L), new FluidStack(oganesson, 4000))
                .fluidOutputs(Materials.Tin.getMolten(1000L)).duration(10 * SECONDS).eut(7_864_320).requiresCleanRoom()
                .addTo(laserEngraverRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 1)),
                        ItemList.Circuit_Chip_CrystalSoC2.get(1L))
                .itemOutputs(ItemList.Circuit_Parts_Crystal_Chip_Wetware.get(1))
                .fluidInputs(Materials.BioMediumSterilized.getFluid(50L)).duration(60 * SECONDS).eut(160_000)
                .requiresCleanRoom().addTo(laserEngraverRecipes);

        // GC/GS Wafer
        if (GalacticraftCore.isModLoaded()) {

            GTValues.RA.stdBuilder()
                    .itemInputs(ItemList.Circuit_Silicon_Wafer.get(1L), CustomItemList.ReinforcedGlassLense.get(0))
                    .itemOutputs(GTModHandler.getModItem(GalacticraftCore.ID, "item.basicItem", 1L, 13))
                    .duration(10 * SECONDS).eut(256).addTo(laserEngraverRecipes);

            GTValues.RA.stdBuilder()
                    .itemInputs(ItemList.Circuit_Silicon_Wafer2.get(1L), CustomItemList.ReinforcedGlassLense.get(0))
                    .itemOutputs(GTModHandler.getModItem(GalacticraftCore.ID, "item.basicItem", 1L, 14))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_HV).addTo(laserEngraverRecipes);

        }

        // Optical to Wafer Buff Recipes

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer6.get(1L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.NetherStar, 1)))
                .itemOutputs(CustomItemList.RawPicoWafer.get(16)).requiresCleanRoom().duration(5 * MINUTES).eut(1887440)
                .addTo(WaferEngravingRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer6.get(1L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.Emerald, 1)))
                .itemOutputs(ItemList.Circuit_Wafer_SoC2.get(32)).requiresCleanRoom().duration(45 * 20)
                .eut(TierEU.RECIPE_ZPM).addTo(WaferEngravingRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer6.get(1L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.Sapphire, 1)))
                .itemOutputs(ItemList.Circuit_Wafer_QPIC.get(16)).requiresCleanRoom().duration(120 * 20).eut(1887440)
                .addTo(WaferEngravingRecipes);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        ItemList.Circuit_Silicon_Wafer6.get(1L),
                        GTUtility.copyAmount(0, GTOreDictUnificator.get(OrePrefixes.lens, Materials.Ruby, 1)))
                .itemOutputs(ItemList.Circuit_Wafer_NPIC.get(32)).requiresCleanRoom().duration(60 * 20)
                .eut(TierEU.RECIPE_UV).addTo(WaferEngravingRecipes);

        // Protomatter recipes
        GTValues.RA.stdBuilder().fluidInputs(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(100L))
                .duration(10 * SECONDS).eut(TierEU.RECIPE_UIV).fluidOutputs(MaterialsUEVplus.Protomatter.getFluid(100L))
                .requiresCleanRoom().noOptimize().addTo(laserEngraverRecipes);

        GTValues.RA.stdBuilder().fluidInputs(MaterialsUEVplus.SpaceTime.getMolten(100L)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_UMV).fluidOutputs(MaterialsUEVplus.Protomatter.getFluid(1000L)).requiresCleanRoom()
                .noOptimize().addTo(laserEngraverRecipes);

        GTValues.RA.stdBuilder().fluidInputs(MaterialsUEVplus.PrimordialMatter.getFluid(100L)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_UXV).fluidOutputs(MaterialsUEVplus.Protomatter.getFluid(10000L)).requiresCleanRoom()
                .noOptimize().addTo(laserEngraverRecipes);

        // Bootstrap antimatter recipe
        GTValues.RA.stdBuilder()
                .itemInputs(GTOreDictUnificator.get(OrePrefixes.plateSuperdense, MaterialsUEVplus.HotProtoHalkonite, 1))
                .fluidInputs(MaterialsUEVplus.Protomatter.getFluid(100L)).duration(10000 * SECONDS)
                .eut(TierEU.RECIPE_UIV).fluidOutputs(MaterialsUEVplus.Antimatter.getFluid(1L)).requiresCleanRoom()
                .addTo(laserEngraverRecipes);

        if (OpenComputers.isModLoaded()) {
            // floppys w NBT
            makeFloppy("OpenOS (Operating System)", "openos", 2, 1);
            makeFloppy("Plan9k (Operating System)", "plan9k", 1, 2);
            makeFloppy("Network (Network Stack)", "network", 10, 3);
            makeFloppy("Data Card Software", "data", 9, 4);
            makeFloppy("OpenLoader (Boot Loader)", "openloader", 13, 5);
            makeFloppy("Builder", "builder", 11, 6);
            makeFloppy("Generator", "generator", 5, 7);
            makeFloppy("OPPM (Package Manager)", "oppm", 6, 8);
            makeFloppy("Digger", "dig", 3, 9);
            makeFloppy("Mazer", "maze", 14, 10);
            makeFloppy("OpenIRC (IRC Client)", "irc", 12, 11);
            // eeprom with lua bios
            makeLuaBios();
        }
    }

    private static final boolean makeFloppy(String displayname, String name, int color, int circuit) {
        ItemStack floppy = GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 4);
        NBTTagCompound tag = new NBTTagCompound(), subtag = new NBTTagCompound();
        subtag.setString("Name", displayname);
        tag.setTag("display", subtag);
        subtag = new NBTTagCompound();
        subtag.setString("oc:fs.label", name);
        tag.setTag("oc:data", subtag);
        tag.setInteger("oc:color", color);
        tag.setString("oc:lootFactory", "OpenComputers:" + name);
        floppy.setTagCompound(tag);
        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "item", 1L, 4),
                        GTUtility.getIntegratedCircuit(circuit))
                .itemOutputs(floppy).duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(laserEngraverRecipes);
        return true;
    }

    private static final boolean makeLuaBios() {
        ItemStack lua = GTModHandler.getModItem(OpenComputers.ID, "eeprom", 1L, 0);
        NBTTagCompound tag = new NBTTagCompound(), subtag = new NBTTagCompound();
        subtag.setString("oc:label", "EEPROM (Lua Bios)");
        subtag.setBoolean("oc:readonly", false);
        final byte[] b = { 108, 111, 99, 97, 108, 32, 99, 111, 109, 112, 111, 110, 101, 110, 116, 95, 105, 110, 118,
                111, 107, 101, 32, 61, 32, 99, 111, 109, 112, 111, 110, 101, 110, 116, 46, 105, 110, 118, 111, 107, 101,
                10, 102, 117, 110, 99, 116, 105, 111, 110, 32, 98, 111, 111, 116, 95, 105, 110, 118, 111, 107, 101, 40,
                97, 100, 100, 114, 101, 115, 115, 44, 32, 109, 101, 116, 104, 111, 100, 44, 32, 46, 46, 46, 41, 10, 32,
                32, 108, 111, 99, 97, 108, 32, 114, 101, 115, 117, 108, 116, 32, 61, 32, 116, 97, 98, 108, 101, 46, 112,
                97, 99, 107, 40, 112, 99, 97, 108, 108, 40, 99, 111, 109, 112, 111, 110, 101, 110, 116, 95, 105, 110,
                118, 111, 107, 101, 44, 32, 97, 100, 100, 114, 101, 115, 115, 44, 32, 109, 101, 116, 104, 111, 100, 44,
                32, 46, 46, 46, 41, 41, 10, 32, 32, 105, 102, 32, 110, 111, 116, 32, 114, 101, 115, 117, 108, 116, 91,
                49, 93, 32, 116, 104, 101, 110, 10, 32, 32, 32, 32, 114, 101, 116, 117, 114, 110, 32, 110, 105, 108, 44,
                32, 114, 101, 115, 117, 108, 116, 91, 50, 93, 10, 32, 32, 101, 108, 115, 101, 10, 32, 32, 32, 32, 114,
                101, 116, 117, 114, 110, 32, 116, 97, 98, 108, 101, 46, 117, 110, 112, 97, 99, 107, 40, 114, 101, 115,
                117, 108, 116, 44, 32, 50, 44, 32, 114, 101, 115, 117, 108, 116, 46, 110, 41, 10, 32, 32, 101, 110, 100,
                10, 101, 110, 100, 10, 10, 45, 45, 32, 98, 97, 99, 107, 119, 97, 114, 100, 115, 32, 99, 111, 109, 112,
                97, 116, 105, 98, 105, 108, 105, 116, 121, 44, 32, 109, 97, 121, 32, 114, 101, 109, 111, 118, 101, 32,
                108, 97, 116, 101, 114, 10, 108, 111, 99, 97, 108, 32, 101, 101, 112, 114, 111, 109, 32, 61, 32, 99,
                111, 109, 112, 111, 110, 101, 110, 116, 46, 108, 105, 115, 116, 40, 34, 101, 101, 112, 114, 111, 109,
                34, 41, 40, 41, 10, 99, 111, 109, 112, 117, 116, 101, 114, 46, 103, 101, 116, 66, 111, 111, 116, 65,
                100, 100, 114, 101, 115, 115, 32, 61, 32, 102, 117, 110, 99, 116, 105, 111, 110, 40, 41, 10, 32, 32,
                114, 101, 116, 117, 114, 110, 32, 98, 111, 111, 116, 95, 105, 110, 118, 111, 107, 101, 40, 101, 101,
                112, 114, 111, 109, 44, 32, 34, 103, 101, 116, 68, 97, 116, 97, 34, 41, 10, 101, 110, 100, 10, 99, 111,
                109, 112, 117, 116, 101, 114, 46, 115, 101, 116, 66, 111, 111, 116, 65, 100, 100, 114, 101, 115, 115,
                32, 61, 32, 102, 117, 110, 99, 116, 105, 111, 110, 40, 97, 100, 100, 114, 101, 115, 115, 41, 10, 32, 32,
                114, 101, 116, 117, 114, 110, 32, 98, 111, 111, 116, 95, 105, 110, 118, 111, 107, 101, 40, 101, 101,
                112, 114, 111, 109, 44, 32, 34, 115, 101, 116, 68, 97, 116, 97, 34, 44, 32, 97, 100, 100, 114, 101, 115,
                115, 41, 10, 101, 110, 100, 10, 10, 100, 111, 10, 32, 32, 108, 111, 99, 97, 108, 32, 115, 99, 114, 101,
                101, 110, 32, 61, 32, 99, 111, 109, 112, 111, 110, 101, 110, 116, 46, 108, 105, 115, 116, 40, 34, 115,
                99, 114, 101, 101, 110, 34, 41, 40, 41, 10, 32, 32, 108, 111, 99, 97, 108, 32, 103, 112, 117, 32, 61,
                32, 99, 111, 109, 112, 111, 110, 101, 110, 116, 46, 108, 105, 115, 116, 40, 34, 103, 112, 117, 34, 41,
                40, 41, 10, 32, 32, 105, 102, 32, 103, 112, 117, 32, 97, 110, 100, 32, 115, 99, 114, 101, 101, 110, 32,
                116, 104, 101, 110, 10, 32, 32, 32, 32, 98, 111, 111, 116, 95, 105, 110, 118, 111, 107, 101, 40, 103,
                112, 117, 44, 32, 34, 98, 105, 110, 100, 34, 44, 32, 115, 99, 114, 101, 101, 110, 41, 10, 32, 32, 101,
                110, 100, 10, 101, 110, 100, 10, 108, 111, 99, 97, 108, 32, 102, 117, 110, 99, 116, 105, 111, 110, 32,
                116, 114, 121, 76, 111, 97, 100, 70, 114, 111, 109, 40, 97, 100, 100, 114, 101, 115, 115, 41, 10, 32,
                32, 108, 111, 99, 97, 108, 32, 104, 97, 110, 100, 108, 101, 44, 32, 114, 101, 97, 115, 111, 110, 32, 61,
                32, 98, 111, 111, 116, 95, 105, 110, 118, 111, 107, 101, 40, 97, 100, 100, 114, 101, 115, 115, 44, 32,
                34, 111, 112, 101, 110, 34, 44, 32, 34, 47, 105, 110, 105, 116, 46, 108, 117, 97, 34, 41, 10, 32, 32,
                105, 102, 32, 110, 111, 116, 32, 104, 97, 110, 100, 108, 101, 32, 116, 104, 101, 110, 10, 32, 32, 32,
                32, 114, 101, 116, 117, 114, 110, 32, 110, 105, 108, 44, 32, 114, 101, 97, 115, 111, 110, 10, 32, 32,
                101, 110, 100, 10, 32, 32, 108, 111, 99, 97, 108, 32, 98, 117, 102, 102, 101, 114, 32, 61, 32, 34, 34,
                10, 32, 32, 114, 101, 112, 101, 97, 116, 10, 32, 32, 32, 32, 108, 111, 99, 97, 108, 32, 100, 97, 116,
                97, 44, 32, 114, 101, 97, 115, 111, 110, 32, 61, 32, 98, 111, 111, 116, 95, 105, 110, 118, 111, 107,
                101, 40, 97, 100, 100, 114, 101, 115, 115, 44, 32, 34, 114, 101, 97, 100, 34, 44, 32, 104, 97, 110, 100,
                108, 101, 44, 32, 109, 97, 116, 104, 46, 104, 117, 103, 101, 41, 10, 32, 32, 32, 32, 105, 102, 32, 110,
                111, 116, 32, 100, 97, 116, 97, 32, 97, 110, 100, 32, 114, 101, 97, 115, 111, 110, 32, 116, 104, 101,
                110, 10, 32, 32, 32, 32, 32, 32, 114, 101, 116, 117, 114, 110, 32, 110, 105, 108, 44, 32, 114, 101, 97,
                115, 111, 110, 10, 32, 32, 32, 32, 101, 110, 100, 10, 32, 32, 32, 32, 98, 117, 102, 102, 101, 114, 32,
                61, 32, 98, 117, 102, 102, 101, 114, 32, 46, 46, 32, 40, 100, 97, 116, 97, 32, 111, 114, 32, 34, 34, 41,
                10, 32, 32, 117, 110, 116, 105, 108, 32, 110, 111, 116, 32, 100, 97, 116, 97, 10, 32, 32, 98, 111, 111,
                116, 95, 105, 110, 118, 111, 107, 101, 40, 97, 100, 100, 114, 101, 115, 115, 44, 32, 34, 99, 108, 111,
                115, 101, 34, 44, 32, 104, 97, 110, 100, 108, 101, 41, 10, 32, 32, 114, 101, 116, 117, 114, 110, 32,
                108, 111, 97, 100, 40, 98, 117, 102, 102, 101, 114, 44, 32, 34, 61, 105, 110, 105, 116, 34, 41, 10, 101,
                110, 100, 10, 108, 111, 99, 97, 108, 32, 105, 110, 105, 116, 44, 32, 114, 101, 97, 115, 111, 110, 10,
                105, 102, 32, 99, 111, 109, 112, 117, 116, 101, 114, 46, 103, 101, 116, 66, 111, 111, 116, 65, 100, 100,
                114, 101, 115, 115, 40, 41, 32, 116, 104, 101, 110, 10, 32, 32, 105, 110, 105, 116, 44, 32, 114, 101,
                97, 115, 111, 110, 32, 61, 32, 116, 114, 121, 76, 111, 97, 100, 70, 114, 111, 109, 40, 99, 111, 109,
                112, 117, 116, 101, 114, 46, 103, 101, 116, 66, 111, 111, 116, 65, 100, 100, 114, 101, 115, 115, 40, 41,
                41, 10, 101, 110, 100, 10, 105, 102, 32, 110, 111, 116, 32, 105, 110, 105, 116, 32, 116, 104, 101, 110,
                10, 32, 32, 99, 111, 109, 112, 117, 116, 101, 114, 46, 115, 101, 116, 66, 111, 111, 116, 65, 100, 100,
                114, 101, 115, 115, 40, 41, 10, 32, 32, 102, 111, 114, 32, 97, 100, 100, 114, 101, 115, 115, 32, 105,
                110, 32, 99, 111, 109, 112, 111, 110, 101, 110, 116, 46, 108, 105, 115, 116, 40, 34, 102, 105, 108, 101,
                115, 121, 115, 116, 101, 109, 34, 41, 32, 100, 111, 10, 32, 32, 32, 32, 105, 110, 105, 116, 44, 32, 114,
                101, 97, 115, 111, 110, 32, 61, 32, 116, 114, 121, 76, 111, 97, 100, 70, 114, 111, 109, 40, 97, 100,
                100, 114, 101, 115, 115, 41, 10, 32, 32, 32, 32, 105, 102, 32, 105, 110, 105, 116, 32, 116, 104, 101,
                110, 10, 32, 32, 32, 32, 32, 32, 99, 111, 109, 112, 117, 116, 101, 114, 46, 115, 101, 116, 66, 111, 111,
                116, 65, 100, 100, 114, 101, 115, 115, 40, 97, 100, 100, 114, 101, 115, 115, 41, 10, 32, 32, 32, 32, 32,
                32, 98, 114, 101, 97, 107, 10, 32, 32, 32, 32, 101, 110, 100, 10, 32, 32, 101, 110, 100, 10, 101, 110,
                100, 10, 105, 102, 32, 110, 111, 116, 32, 105, 110, 105, 116, 32, 116, 104, 101, 110, 10, 32, 32, 101,
                114, 114, 111, 114, 40, 34, 110, 111, 32, 98, 111, 111, 116, 97, 98, 108, 101, 32, 109, 101, 100, 105,
                117, 109, 32, 102, 111, 117, 110, 100, 34, 32, 46, 46, 32, 40, 114, 101, 97, 115, 111, 110, 32, 97, 110,
                100, 32, 40, 34, 58, 32, 34, 32, 46, 46, 32, 116, 111, 115, 116, 114, 105, 110, 103, 40, 114, 101, 97,
                115, 111, 110, 41, 41, 32, 111, 114, 32, 34, 34, 41, 44, 32, 48, 41, 10, 101, 110, 100, 10, 99, 111,
                109, 112, 117, 116, 101, 114, 46, 98, 101, 101, 112, 40, 49, 48, 48, 48, 44, 32, 48, 46, 50, 41, 10,
                105, 110, 105, 116, 40, 41 };
        subtag.setByteArray("oc:eeprom", b);
        tag.setTag("oc:data", subtag);
        lua.setTagCompound(tag);

        GTValues.RA.stdBuilder()
                .itemInputs(
                        GTModHandler.getModItem(OpenComputers.ID, "eeprom", 1L, 0),
                        GTUtility.getIntegratedCircuit(1))
                .itemOutputs(lua).duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(laserEngraverRecipes);

        return true;
    }
}
