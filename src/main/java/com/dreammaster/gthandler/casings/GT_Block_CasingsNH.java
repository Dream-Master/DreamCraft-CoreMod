package com.dreammaster.gthandler.casings;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.dreammaster.gthandler.CustomItemList;
import com.dreammaster.gthandler.multiAirFilter.GT_MetaTileEntity_AirFilterT1;
import com.dreammaster.gthandler.multiAirFilter.GT_MetaTileEntity_AirFilterT2;
import com.dreammaster.gthandler.multiAirFilter.GT_MetaTileEntity_AirFilterT3;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GTCopiedBlockTexture;
import gregtech.api.util.GTLanguageManager;
import gregtech.common.blocks.BlockCasingsAbstract;
import gregtech.common.blocks.MaterialCasings;

/**
 * Created by danie_000 on 03.10.2016.
 */
public class GT_Block_CasingsNH extends BlockCasingsAbstract {

    public static boolean mConnectedMachineTextures = true;

    public GT_Block_CasingsNH() {
        super(GT_Item_CasingsNH.class, "gt.blockcasingsNH", MaterialCasings.INSTANCE);
        for (byte b = 0; b < 16; b = (byte) (b + 1)) {
            Textures.BlockIcons.casingTexturePages[8][b + 64] = new GTCopiedBlockTexture(this, 6, b);
            /* IMPORTANT for block recoloring */
        }
        GTLanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Air Filter Turbine Casing"); // adding
        GTLanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Air Filter Vent Casing"); // adding
        GTLanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Pyrolyse Oven Casing"); // adding
        GTLanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Advanced Air Filter Turbine Casing"); // adding
        GTLanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Advanced Air Filter Vent Casing"); // adding
        GTLanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Super Air Filter Turbine Casing"); // adding
        GTLanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Super Air Filter Vent Casing"); // adding

        GTLanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "UEV Machine Casing"); // adding
        GTLanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "UIV Machine Casing"); // adding
        GTLanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "UMV Machine Casing"); // adding
        GTLanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "UXV Machine Casing"); // adding
        GTLanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "MAX Machine Casing"); // adding

        CustomItemList.Casing_AirFilter_Turbine_T1.set(new ItemStack(this, 1, 0)); // adding
        CustomItemList.Casing_AirFilter_Vent_T1.set(new ItemStack(this, 1, 1)); // adding
        CustomItemList.Casing_Pyrolyse.set(new ItemStack(this, 1, 2)); // adding
        CustomItemList.Casing_AirFilter_Turbine_T2.set(new ItemStack(this, 1, 3)); // adding
        CustomItemList.Casing_AirFilter_Vent_T2.set(new ItemStack(this, 1, 4)); // adding
        CustomItemList.Casing_AirFilter_Turbine_T3.set(new ItemStack(this, 1, 5)); // adding
        CustomItemList.Casing_AirFilter_Vent_T3.set(new ItemStack(this, 1, 6)); // adding

        CustomItemList.Casing_UEV.set(new ItemStack(this, 1, 10));
        CustomItemList.Casing_UIV.set(new ItemStack(this, 1, 11));
        CustomItemList.Casing_UMV.set(new ItemStack(this, 1, 12));
        CustomItemList.Casing_UXV.set(new ItemStack(this, 1, 13));
        CustomItemList.Casing_MAXV.set(new ItemStack(this, 1, 14));
    }

    @Override
    public void registerBlockIcons(IIconRegister aIconRegister) {}

    @Override
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case 0:
                return Textures.BlockIcons.MACHINE_CASING_TURBINE_STEEL.getIcon();
            case 1:
                return Textures.BlockIcons.MACHINE_CASING_PIPE_STEEL.getIcon();
            case 2:
                return Textures.BlockIcons.MACHINE_8V_SIDE.getIcon();
            case 3:
                return Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM.getIcon();
            case 4:
                return Textures.BlockIcons.MACHINE_CASING_PIPE_TITANIUM.getIcon();
            case 5:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
            case 6:
                return Textures.BlockIcons.MACHINE_CASING_PIPE_TUNGSTENSTEEL.getIcon();
            default:
                if (aSide == 0) {
                    return Textures.BlockIcons.MACHINECASINGS_BOTTOM[aMeta].getIcon();
                }
                if (aSide == 1) {
                    return Textures.BlockIcons.MACHINECASINGS_TOP[aMeta].getIcon();
                }
                return Textures.BlockIcons.MACHINECASINGS_SIDE[aMeta].getIcon();
        }
    }

    @Override
    public int colorMultiplier(IBlockAccess aWorld, int aX, int aY, int aZ) {
        return aWorld.getBlockMetadata(aX, aY, aZ) <= 9 ? super.colorMultiplier(aWorld, aX, aY, aZ)
                : Dyes.MACHINE_METAL.mRGBa[0] << 16 | Dyes.MACHINE_METAL.mRGBa[1] << 8 | Dyes.MACHINE_METAL.mRGBa[2];
    }
}
