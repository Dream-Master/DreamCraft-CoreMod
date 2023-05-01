package com.dreammaster.scripts;

import static gregtech.api.enums.Mods.AE2Stuff;
import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.util.GT_ModHandler.getModItem;

import java.util.Arrays;
import java.util.List;

public class ScriptAE2Stuff implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "AE2Stuff";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(AE2Stuff.ID, AppliedEnergistics2.ID);
    }

    @Override
    public void loadRecipes() {

        // AUTOGENERATED

        addShapedRecipe(
                getModItem(AE2Stuff.ID, "Encoder", 1, 0, missing),
                new Object[] { "plateTitanium",
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 340, missing), "plateTitanium",
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23, missing),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockCraftingUnit", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23, missing), "plateTitanium",
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing), "plateTitanium" });
        addShapedRecipe(
                getModItem(AE2Stuff.ID, "Grower", 1, 0, missing),
                new Object[] { getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGrowthAccelerator", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGrowthAccelerator", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGrowthAccelerator", 1, 0, missing),
                        "chestIron",
                        getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGrowthAccelerator", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGrowthAccelerator", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGrowthAccelerator", 1, 0, missing) });
        addShapedRecipe(
                getModItem(AE2Stuff.ID, "Wireless", 1, 0, missing),
                new Object[] { "crystalPureFluix", "plateTitanium", "crystalPureFluix",
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 41, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                        "crystalPureFluix", "plateTitanium", "crystalPureFluix" });
        addShapedRecipe(
                getModItem(AE2Stuff.ID, "WirelessKit", 1, 0, missing),
                new Object[] { getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 41, missing),
                        "plateTitanium", getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 41, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 180, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                        "crystalPureFluix", getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16, missing),
                        "crystalPureFluix" });
        addShapedRecipe(
                getModItem(AE2Stuff.ID, "Visualiser", 1, 0, missing),
                new Object[] { "crystalPureFluix",
                        getModItem(AppliedEnergistics2.ID, "item.ToolNetworkTool", 1, 0, missing), "crystalPureFluix",
                        "screwTitanium", getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 41, missing),
                        "screwTitanium", "craftingToolScrewdriver",
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                        "craftingToolWrench" });
        addShapedRecipe(
                getModItem(AE2Stuff.ID, "Inscriber", 1, 0, missing),
                new Object[] { "plateTungstenSteel",
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                        "plateTungstenSteel",
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                        getModItem(AppliedEnergistics2.ID, "tile.BlockInscriber", 1, 0, missing),
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22, missing),
                        "plateTungstenSteel",
                        getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24, missing),
                        "plateTungstenSteel" });

    }
}
