package com.dreammaster.gthandler.recipes;

import static gregtech.api.enums.Mods.Automagy;
import static gregtech.api.enums.Mods.BartWorks;
import static gregtech.api.recipe.RecipeMaps.distillationTowerRecipes;
import static gregtech.api.recipe.RecipeMaps.distilleryRecipes;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_RecipeBuilder.TICKS;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

public class DistillationRecipes implements Runnable {

    @Override
    public void run() {
        distilleryRecipes();
        distillationTowerRecipes();
    }

    public static void distillationTowerRecipes() {
        GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 1))
                .fluidInputs(new FluidStack(FluidRegistry.getFluid("pollution"), 1000))
                .fluidOutputs(
                        Materials.SulfuricAcid.getFluid(150),
                        Materials.NitrogenDioxide.getGas(150),
                        Materials.Methane.getGas(150),
                        Materials.Mercury.getFluid(10))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_HV).addTo(distillationTowerRecipes);

        GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(2))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 10))
                .fluidInputs(new FluidStack(FluidRegistry.getFluid("pollution"), 10000))
                .fluidOutputs(
                        Materials.SulfuricAcid.getFluid(3000),
                        Materials.NitrogenDioxide.getGas(3000),
                        Materials.Methane.getGas(3000),
                        Materials.Mercury.getFluid(200))
                .duration(30 * SECONDS).eut(TierEU.RECIPE_EV).addTo(distillationTowerRecipes);

        if (BartWorks.isModLoaded()) {

            GT_Values.RA.stdBuilder().fluidInputs(Materials.Grade2PurifiedWater.getFluid(1000))
                    .fluidOutputs(
                            Materials.Grade3PurifiedWater.getFluid(900),
                            Materials.Grade2PurifiedWater.getFluid(50),
                            Materials.Grade1PurifiedWater.getFluid(100),
                            Materials.Oil.getFluid(50),
                            Materials.Ammonia.getGas(100))
                    .duration(250 * SECONDS).eut(TierEU.RECIPE_ZPM).addTo(distillationTowerRecipes);

            GT_Values.RA.stdBuilder().fluidInputs(Materials.Grade6PurifiedWater.getFluid(1000))
                    .fluidOutputs(
                            Materials.Grade7PurifiedWater.getFluid(900),
                            Materials.Grade6PurifiedWater.getFluid(50),
                            Materials.Grade5PurifiedWater.getFluid(100),
                            Materials.Ethanol.getFluid(50))
                    .duration(250 * SECONDS).eut(TierEU.RECIPE_UHV).addTo(distillationTowerRecipes);
        }
    }

    public static void distilleryRecipes() {
        // Sodium Potassium
        GT_Values.RA.stdBuilder().itemInputs(Materials.RockSalt.getDust(1)).fluidInputs(Materials.Sodium.getFluid(1000))
                .fluidOutputs(FluidRegistry.getFluidStack("sodiumpotassium", 1000)).duration(20 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(distilleryRecipes);

        GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(1))
                .fluidInputs(Materials.Biomass.getFluid(40)).fluidOutputs(Materials.Ethanol.getFluid(20))
                .duration(16 * TICKS).eut(24).addTo(distilleryRecipes);

        // unify milk from Automagy

        if (Automagy.isModLoaded()) {
            FluidStack fluidMilk = FluidRegistry.getFluidStack("fluidmilk", 1000);

            GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(1))
                    .fluidInputs(Materials.Milk.getFluid(1000)).fluidOutputs(fluidMilk.copy()).duration(5 * SECONDS)
                    .eut(2).addTo(distilleryRecipes);

            GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(1)).fluidInputs(fluidMilk.copy())
                    .fluidOutputs(Materials.Milk.getFluid(1000)).duration(5 * SECONDS).eut(2).addTo(distilleryRecipes);
        }
    }
}
