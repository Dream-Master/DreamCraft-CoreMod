package com.dreammaster.scripts;

import static gregtech.api.util.GT_ModHandler.addShapelessCraftingRecipe;
import static gregtech.api.util.GT_ModHandler.getModItem;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_Utility;

public class ScriptOpenBlocks implements IScriptLoader {

    public ScriptOpenBlocks() {}

    @Override
    public void initScriptData() {
        scriptName.setLength(0);
        scriptName.append("OpenBlocks");
        dependencies.clear();
        dependencies.addAll(Arrays.asList("OpenBlocks", "RandomThings", "ExtraUtilities", "gregtech"));
    }

    @Override
    public void loadRecipes() {
        ItemStack devNull = getModItem("OpenBlocks", "devnull", 1);
        ItemStack voidDropFilter = getModItem("RandomThings", "dropFilter", 1, 1);
        ItemStack trashCan = getModItem("ExtraUtilities", "trashcan", 1);
        ItemStack circuit2 = GT_Utility.getIntegratedCircuit(2);

        FluidStack ender250 = FluidRegistry.getFluidStack("ender", 250);

        addShapelessCraftingRecipe(devNull, new Object[] { voidDropFilter });

        GT_Values.RA.addAssemblerRecipe(new ItemStack[] { trashCan, circuit2 }, ender250, devNull, 300, 30);
    }
}
