package com.dreammaster.gthandler.recipes;

import static gregtech.api.enums.Mods.Natura;
import static gregtech.api.recipe.RecipeMaps.wiremillRecipes;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.dreammaster.gthandler.CustomItemList;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_RecipeRegistrator;
import gregtech.api.util.GT_Utility;

public class WiremillRecipes implements Runnable {

    @Override
    public void run() {
        GT_Values.RA.stdBuilder()
                .itemInputs(GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.MeteoricIron, 1L))
                .itemOutputs(CustomItemList.MeteoricIronString.get(4L)).duration(30 * SECONDS).eut(TierEU.RECIPE_HV)
                .addTo(wiremillRecipes);

        GT_Values.RA.stdBuilder().itemInputs(GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Tungsten, 1L))
                .itemOutputs(CustomItemList.TungstenString.get(4L)).duration(60 * SECONDS).eut(1024)
                .addTo(wiremillRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(Materials.BorosilicateGlass.getIngots(1), GT_Utility.getIntegratedCircuit(3))
                .itemOutputs(ItemList.Circuit_Parts_GlassFiber.get(8L)).duration(10 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(wiremillRecipes);

        GT_RecipeRegistrator.registerWiremillRecipes(Materials.NetherStar, 200, 4);

        if (Natura.isModLoaded()) {

            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(Natura.ID, "barleyFood", 2L, 3))
                    .itemOutputs(new ItemStack(Items.string, 1, 0)).duration(10 * SECONDS).eut(8)
                    .addTo(wiremillRecipes);
        }
    }
}
