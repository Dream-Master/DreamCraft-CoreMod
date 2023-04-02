package com.dreammaster.scripts;

import static com.dreammaster.MissingModIDs.ForgeMicroblocks;
import static gregtech.api.enums.ModIDs.NewHorizonsCoreMod;
import static gregtech.api.util.GT_ModHandler.getModItem;

import java.util.Collections;
import java.util.List;

public class ScriptForgeMultipart implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Forge Multipart";
    }

    @Override
    public List<String> getDependencies() {
        return Collections.singletonList(ForgeMicroblocks.modID);
    }

    @Override
    public void loadRecipes() {
        addShapedRecipe(
                getModItem(ForgeMicroblocks.modID, "sawStone", 1),
                new Object[] { "stickWood", "stickStone", "stickStone", "stickWood",
                        getModItem(NewHorizonsCoreMod.modID, "item.SawBladeStone", 1), "stickStone", null, null,
                        null });

        addShapedRecipe(
                getModItem(ForgeMicroblocks.modID, "sawIron", 1),
                new Object[] { "stickWood", "stickIron", "stickIron", "stickWood", "toolHeadSawIron", "stickIron", null,
                        null, null });

        addShapedRecipe(
                getModItem(ForgeMicroblocks.modID, "sawDiamond", 1),
                new Object[] { "stickWood", "stickSteel", "stickSteel", "stickWood",
                        getModItem(NewHorizonsCoreMod.modID, "item.SawBladeDiamond", 1), "stickSteel", null, null,
                        null });

        addShapedRecipe(
                getModItem(ForgeMicroblocks.modID, "stoneRod", 2),
                new Object[] { "stone", null, null, "stone", null, null, null, null, null });

        addShapedRecipe(
                getModItem(ForgeMicroblocks.modID, "stoneRod", 4),
                new Object[] { "craftingToolSaw", null, null, "stone", null, null, "stone", null, null });
    }
}
