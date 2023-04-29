package com.dreammaster.scripts;

import static gregtech.api.util.GT_ModHandler.getModItem;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sLaserEngraverRecipes;

import java.util.Arrays;
import java.util.List;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Mods;

public class ScriptSuperSolarPanels implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "Super Solar Panels";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(Mods.SuperSolarPanels.ID);
    }

    @Override
    public void loadRecipes() {
        addShapedRecipe(
                getModItem("gregtech", "gt.metaitem.01", 1, 32756, missing),
                new Object[] { "craftingToolCrowbar",
                        getModItem("supersolarpanel", "SpectralSolarPanel", 1, 0, missing), "craftingToolScrewdriver",
                        "craftingToolWrench", "craftingToolHardHammer", "craftingToolFile" });
        addShapedRecipe(
                getModItem("gregtech", "gt.metaitem.01", 1, 32757, missing),
                new Object[] { "craftingToolCrowbar",
                        getModItem("supersolarpanel", "SingularSolarPanel", 1, 0, missing), "craftingToolScrewdriver",
                        "craftingToolWrench", "craftingToolHardHammer", "craftingToolFile" });
        addShapedRecipe(
                getModItem("gregtech", "gt.metaitem.01", 1, 32758, missing),
                new Object[] { "craftingToolCrowbar", getModItem("supersolarpanel", "AdminSolarPanel", 1, 0, missing),
                        "craftingToolScrewdriver", "craftingToolWrench", "craftingToolHardHammer",
                        "craftingToolFile" });
        addShapedRecipe(
                getModItem("gregtech", "gt.metaitem.01", 1, 32759, missing),
                new Object[] { "craftingToolCrowbar",
                        getModItem("supersolarpanel", "PhotonicSolarPanel", 1, 0, missing), "craftingToolScrewdriver",
                        "craftingToolWrench", "craftingToolHardHammer", "craftingToolFile" });
        addShapedRecipe(
                getModItem("supersolarpanel", "solarsplitter", 1, 0, missing),
                new Object[] { getModItem("supersolarpanel", "redcomponent", 1, 0, missing),
                        getModItem("supersolarpanel", "greencomponent", 1, 0, missing),
                        getModItem("supersolarpanel", "bluecomponent", 1, 0, missing),
                        getModItem("supersolarpanel", "redcomponent", 1, 0, missing),
                        getModItem("supersolarpanel", "greencomponent", 1, 0, missing),
                        getModItem("supersolarpanel", "bluecomponent", 1, 0, missing),
                        getModItem("supersolarpanel", "redcomponent", 1, 0, missing),
                        getModItem("supersolarpanel", "greencomponent", 1, 0, missing),
                        getModItem("supersolarpanel", "bluecomponent", 1, 0, missing) });
        addShapedRecipe(
                getModItem("supersolarpanel", "enderquantumcomponent", 1, 0, missing),
                new Object[] { getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 3, missing),
                        "plateElectrumFlux", getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 3, missing),
                        "plateElectrumFlux", "plateDenseInfinityCatalyst", "plateElectrumFlux",
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 3, missing), "plateElectrumFlux",
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 3, missing) });

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24501, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "greencomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24504, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "greencomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24505, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "greencomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24542, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "greencomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24503, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "bluecomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24510, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "bluecomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24513, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "bluecomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24543, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "bluecomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24347, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "redcomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24511, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "redcomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24512, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "redcomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24502, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "redcomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24527, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "redcomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        getModItem("gregtech", "gt.metaitem.01", 0, 24541, missing),
                        getModItem("AdvancedSolarPanel", "asp_crafting_items", 1, 5, missing))
                .itemOutputs(getModItem("supersolarpanel", "redcomponent", 1, 0, missing)).noFluidInputs()
                .noFluidOutputs().duration(1000).eut(7680).addTo(sLaserEngraverRecipes);

    }
}
