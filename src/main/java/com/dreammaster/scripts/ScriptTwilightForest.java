package com.dreammaster.scripts;

import static gregtech.api.enums.Mods.TwilightForest;
import static gregtech.api.util.GT_ModHandler.getModItem;

import java.util.Collections;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ScriptTwilightForest implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "TwilightForest";
    }

    @Override
    public List<String> getDependencies() {
        return Collections.singletonList(TwilightForest.ID);
    }

    @Override
    public void loadRecipes() {

        final ItemStack TF_ROOF_TILE = getModItem(TwilightForest.ID, "tile.CastleBrick", 8, 3);
        addShapedRecipe(
                TF_ROOF_TILE,
                new Object[] { Blocks.cobblestone, Blocks.stone, Blocks.cobblestone, Blocks.cobblestone, "dyeBlack",
                        Blocks.cobblestone, Blocks.cobblestone, Blocks.cobblestone, Blocks.cobblestone });
    }
}
