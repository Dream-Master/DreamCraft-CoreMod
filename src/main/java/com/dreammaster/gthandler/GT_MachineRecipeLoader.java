package com.dreammaster.gthandler;

import com.dreammaster.gthandler.recipes.AlloySmelterRecipes;
import com.dreammaster.gthandler.recipes.ArcFurnaceRecipes;
import com.dreammaster.gthandler.recipes.AssemblerRecipes;
import com.dreammaster.gthandler.recipes.AssemblingLineRecipes;
import com.dreammaster.gthandler.recipes.AutoclaveRecipes;
import com.dreammaster.gthandler.recipes.BendingMachineRecipes;
import com.dreammaster.gthandler.recipes.BlastFurnaceRecipes;
import com.dreammaster.gthandler.recipes.BrewingMachineRecipes;
import com.dreammaster.gthandler.recipes.CannerRecipes;
import com.dreammaster.gthandler.recipes.CentrifugeRecipes;
import com.dreammaster.gthandler.recipes.ChemicalBathRecipes;
import com.dreammaster.gthandler.recipes.ChemicalReactorRecipes;
import com.dreammaster.gthandler.recipes.CircuitAssemblerRecipes;
import com.dreammaster.gthandler.recipes.CompressorRecipes;
import com.dreammaster.gthandler.recipes.CrackingRecipes;
import com.dreammaster.gthandler.recipes.CuttingMachineRecipes;
import com.dreammaster.gthandler.recipes.DistillationRecipes;
import com.dreammaster.gthandler.recipes.ElectrolyzerRecipes;
import com.dreammaster.gthandler.recipes.ExtractorRecipes;
import com.dreammaster.gthandler.recipes.ExtruderRecipes;
import com.dreammaster.gthandler.recipes.FermenterRecipes;
import com.dreammaster.gthandler.recipes.FluidCannerRecipes;
import com.dreammaster.gthandler.recipes.FluidExtractorRecipes;
import com.dreammaster.gthandler.recipes.FluidHeaterRecipes;
import com.dreammaster.gthandler.recipes.FluidSolidifierRecipes;
import com.dreammaster.gthandler.recipes.ForgeHammerRecipes;
import com.dreammaster.gthandler.recipes.FormingPressRecipes;
import com.dreammaster.gthandler.recipes.ImplosionCompressorRecipes;
import com.dreammaster.gthandler.recipes.LaserEngraverRecipes;
import com.dreammaster.gthandler.recipes.LatheRecipes;
import com.dreammaster.gthandler.recipes.MaceratorRecipes;
import com.dreammaster.gthandler.recipes.MixerRecipes;
import com.dreammaster.gthandler.recipes.NeutroniumCompressorRecipes;
import com.dreammaster.gthandler.recipes.PolarizerRecipes;
import com.dreammaster.gthandler.recipes.RecipeRemoval;
import com.dreammaster.gthandler.recipes.SifterRecipes;
import com.dreammaster.gthandler.recipes.SolarFactoryRecipes;
import com.dreammaster.gthandler.recipes.SpaceAssemblerRecipes;
import com.dreammaster.gthandler.recipes.VacuumFreezerRecipes;
import com.dreammaster.gthandler.recipes.VacuumFurnaceRecipes;
import com.dreammaster.gthandler.recipes.WiremillRecipes;

public class GT_MachineRecipeLoader implements Runnable {

    @Override
    public void run() {
        new RecipeRemoval().run();

        new AlloySmelterRecipes().run();
        new ArcFurnaceRecipes().run();
        new AssemblerRecipes().run();
        new AssemblingLineRecipes().run();
        new AutoclaveRecipes().run();
        new BendingMachineRecipes().run();
        new BlastFurnaceRecipes().run();
        new BrewingMachineRecipes().run();
        new CannerRecipes().run();
        new CentrifugeRecipes().run();
        new ChemicalBathRecipes().run();
        new ChemicalReactorRecipes().run();
        new CircuitAssemblerRecipes().run();
        new CompressorRecipes().run();
        new CuttingMachineRecipes().run();
        new DistillationRecipes().run();
        new ElectrolyzerRecipes().run();
        new ExtractorRecipes().run();
        new ExtruderRecipes().run();
        new FermenterRecipes().run();
        new FluidCannerRecipes().run();
        new FluidExtractorRecipes().run();
        new FluidHeaterRecipes().run();
        new FluidSolidifierRecipes().run();
        new ForgeHammerRecipes().run();
        new FormingPressRecipes().run();
        new ImplosionCompressorRecipes().run();
        new LaserEngraverRecipes().run();
        new LatheRecipes().run();
        new MaceratorRecipes().run();
        new MixerRecipes().run();
        new NeutroniumCompressorRecipes().run();
        new SifterRecipes().run();
        new SolarFactoryRecipes().run();
        new VacuumFreezerRecipes().run();
        new WiremillRecipes().run();
        new CrackingRecipes().run();
        new SpaceAssemblerRecipes().run();
        new VacuumFurnaceRecipes().run();
        new PolarizerRecipes().run();
    }

}
