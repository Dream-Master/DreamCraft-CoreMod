package com.dreammaster.gthandler.recipes;

import static gregtech.api.enums.Mods.GalacticraftCore;
import static gregtech.api.recipe.RecipeMaps.benderRecipes;
import static gregtech.api.util.GT_ModHandler.getModItem;
import static gregtech.api.util.GT_RecipeBuilder.MINUTES;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.dreammaster.gthandler.CustomItemList;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

public class BendingMachineRecipes implements Runnable {

    @Override
    public void run() {
        ItemStack missing = new ItemStack(Blocks.fire);

        GT_Values.RA.stdBuilder()
                .itemInputs(CustomItemList.MicaInsulatorSheet.get(1L), ItemList.Circuit_Integrated.getWithDamage(0, 1))
                .itemOutputs(CustomItemList.MicaInsulatorFoil.get(4L)).duration(5 * SECONDS).eut(TierEU.RECIPE_LV)
                .addTo(benderRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.Steel, 1L),
                        ItemList.Circuit_Integrated.getWithDamage(0, 1))
                .itemOutputs(ItemList.Shape_Empty.get(1L)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(benderRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Obsidian, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 1L)).duration(20 * SECONDS)
                .eut(24).addTo(benderRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Obsidian, 9L),
                        GT_Utility.getIntegratedCircuit(9))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Obsidian, 1L))
                .duration(3 * MINUTES).eut(TierEU.RECIPE_MV).addTo(benderRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 9L),
                        GT_Utility.getIntegratedCircuit(9))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Obsidian, 1L))
                .duration(3 * MINUTES).eut(TierEU.RECIPE_MV).addTo(benderRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 9L),
                        GT_Utility.getIntegratedCircuit(9))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Lapis, 1L))
                .duration(3 * MINUTES).eut(TierEU.RECIPE_MV * 3 / 4).addTo(benderRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Plastic, 1L),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Plastic, 1L)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV / 2).addTo(benderRecipes);

        if (GalacticraftCore.isModLoaded()) {
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(GalacticraftCore.ID, "item.basicItem", 2, 7, missing),
                            GT_Utility.getIntegratedCircuit(2))
                    .itemOutputs(getModItem(GalacticraftCore.ID, "item.canister", 1, 0, missing)).duration(10 * SECONDS)
                    .eut(8).addTo(benderRecipes);
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(GalacticraftCore.ID, "item.basicItem", 2, 6, missing),
                            GT_Utility.getIntegratedCircuit(2))
                    .itemOutputs(getModItem(GalacticraftCore.ID, "item.canister", 1, 1, missing)).duration(10 * SECONDS)
                    .eut(8).addTo(benderRecipes);
        }
    }
}
