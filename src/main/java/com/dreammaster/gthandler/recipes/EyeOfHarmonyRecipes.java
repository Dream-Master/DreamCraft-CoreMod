package com.dreammaster.gthandler.recipes;

import static com.github.technus.tectech.recipe.TecTechRecipeMaps.eyeOfHarmonyRecipes;
import static gregtech.api.enums.Mods.Botania;
import static gregtech.api.enums.Mods.DraconicEvolution;
import static gregtech.api.util.GT_ModHandler.getModItem;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.dreammaster.gthandler.CustomItemList;
import com.github.technus.tectech.recipe.EyeOfHarmonyRecipe;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsBotania;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import pers.gwyog.gtneioreplugin.plugin.block.BlockDimensionDisplay;
import pers.gwyog.gtneioreplugin.plugin.block.ModBlocks;

public class EyeOfHarmonyRecipes {

    public static final HashMap<String, EyeOfHarmonyRecipe> recipeHashMap = new HashMap<>() {

        {
            put(
                    "Rb",
                    new EyeOfHarmonyRecipe(
                            null,
                            null,
                            (BlockDimensionDisplay) ModBlocks.getBlock("Rb"),
                            1.6,
                            11_000_000_000L,
                            11_000_000_000L,
                            520_658,
                            9,
                            0.5));

        }
    };

    static HashMap<Block, String> invertedBlocksMap = new HashMap<>() {

        {
            ModBlocks.blocks.forEach((dimString, dimBlock) -> { put(dimBlock, dimString); });
        }
    };

    public EyeOfHarmonyRecipe specialRecipeLookUp(final ItemStack aStack) {
        String dimAbbreviation = invertedBlocksMap.get(Block.getBlockFromItem(aStack.getItem()));
        return recipeHashMap.get(dimAbbreviation);
    }

    public EyeOfHarmonyRecipes() {
        eyeOfHarmonyRecipes.addRecipe(
                false,
                new ItemStack[] { GT_ModHandler.getModItem(Botania.ID, "dice", 0L, 0) },
                new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.dust, MaterialsBotania.Terrasteel, 12_325_347),
                        GT_OreDictUnificator.get(OrePrefixes.dust, MaterialsBotania.GaiaSpirit, 12_325_347),
                        GT_OreDictUnificator.get(OrePrefixes.dust, MaterialsBotania.Manasteel, 12_325_347),
                        CustomItemList.QuantumCircuit.get(9L), ItemList.EnergisedTesseract.get(32),
                        CustomItemList.PikoCircuit.get(12L),
                        GT_OreDictUnificator.get(OrePrefixes.nanite, MaterialsUEVplus.Universium, 16L),
                        GT_OreDictUnificator.get(OrePrefixes.nanite, MaterialsUEVplus.BlackDwarfMatter, 32L),
                        GT_OreDictUnificator.get(OrePrefixes.nanite, MaterialsUEVplus.WhiteDwarfMatter, 32L),
                        ItemList.ZPM4.get(1L), ItemList.Electric_Motor_UXV.get(4L),
                        getModItem(DraconicEvolution.ID, "chaosFragment", 4L, 2)

                },
                new EyeOfHarmonyRecipe(
                        null,
                        null,
                        (BlockDimensionDisplay) ModBlocks.getBlock("Rb"),
                        1.6,
                        11_000_000_000L,
                        11_000_000_000L,
                        520_658,
                        9,
                        0.5),
                null,
                new FluidStack[] { Materials.Hydrogen.getGas(0), Materials.Helium.getGas(0),
                        MaterialsUEVplus.RawStarMatter.getFluid(0) },
                new FluidStack[] { Materials.Gallium.getPlasma(80_000_000), Materials.Phosphorus.getPlasma(80_000_000),
                        Materials.Carbon.getPlasma(80_000_000), Materials.Ytterbium.getPlasma(80_000_000),
                        Materials.Copper.getPlasma(80_000_000), Materials.Gadolinium.getPlasma(80_000_000),
                        Materials.Antimony.getPlasma(80_000_000), Materials.Gold.getPlasma(80_000_000),
                        Materials.Cadmium.getPlasma(80_000_000), Materials.Rubidium.getPlasma(80_000_000),
                        Materials.Neodymium.getPlasma(80_000_000), Materials.Beryllium.getPlasma(80_000_000),
                        Materials.Molybdenum.getPlasma(80_000_000), Materials.Naquadah.getPlasma(80_000_000),
                        Materials.Lithium.getPlasma(80_000_000), Materials.Caesium.getPlasma(80_000_000),
                        MaterialsUEVplus.RawStarMatter.getFluid(1_000_000),
                        MaterialsUEVplus.Universium.getMolten(73_728) },
                10_413_168,
                0,
                0);
    }
}
