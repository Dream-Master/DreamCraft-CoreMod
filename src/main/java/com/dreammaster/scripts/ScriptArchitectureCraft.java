package com.dreammaster.scripts;

import static gregtech.api.enums.Mods.ArchitectureCraft;
import static gregtech.api.enums.Mods.GregTech;
import static gregtech.api.enums.Mods.Minecraft;
import static gregtech.api.enums.Mods.TinkerConstruct;
import static gregtech.api.util.GT_ModHandler.getModItem;

import java.util.Arrays;
import java.util.List;

public class ScriptArchitectureCraft implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Architecture Craft";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(ArchitectureCraft.ID, GregTech.ID, TinkerConstruct.ID);
    }

    @Override
    public void loadRecipes() {
        addShapedRecipe(
                getModItem(ArchitectureCraft.ID, "sawbench", 1),
                "screwSteel",
                getModItem(Minecraft.ID, "heavy_weighted_pressure_plate", 1),
                "screwSteel",
                getModItem(TinkerConstruct.ID, "trap.barricade.oak", 1),
                getModItem(ArchitectureCraft.ID, "sawblade", 1),
                getModItem(TinkerConstruct.ID, "trap.barricade.oak", 1),
                "craftingToolScrewdriver",
                getModItem(ArchitectureCraft.ID, "largePulley", 1),
                "craftingToolHardHammer");

        addShapedRecipe(
                getModItem(ArchitectureCraft.ID, "largePulley", 1),
                "screwSteel",
                "craftingToolScrewdriver",
                "screwSteel",
                "gearGtSmallAnyIron",
                "gearWood",
                "gearGtSmallAnyIron",
                "screwSteel",
                "craftingToolHardHammer",
                "screwSteel");

        addShapedRecipe(
                getModItem(ArchitectureCraft.ID, "hammer", 1),
                "plateSteel",
                "plateSteel",
                "ingotIron",
                "craftingToolFile",
                "stickWood",
                "ingotIron",
                null,
                "stickWood",
                "craftingToolHardHammer");

        addShapedRecipe(
                getModItem(ArchitectureCraft.ID, "chisel", 1),
                "craftingToolHardHammer",
                "plateIron",
                "plateSteel",
                "screwIron",
                "stickIron",
                "plateIron",
                "stickWood",
                "screwIron",
                "craftingToolScrewdriver");

        addShapelessRecipe(getModItem(ArchitectureCraft.ID, "sawblade", 1), "toolHeadBuzzSawAnyIron");

        addShapelessRecipe(
                getModItem(GregTech.ID, "gt.metaitem.02", 1, 15032),
                getModItem(ArchitectureCraft.ID, "sawblade", 1));
    }
}
