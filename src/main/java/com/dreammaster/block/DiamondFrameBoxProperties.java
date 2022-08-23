package com.dreammaster.block;

import eu.usrv.yamcore.iface.IExtendedBlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * This is the helper class to define extended properties for your block. If you decide to use it,
 * you *MUST* define every value or it will not register properly or may even crash.
 * @author Namikon
 *
 */
public class DiamondFrameBoxProperties implements IExtendedBlockProperties {
    @Override
    public Material getMaterial() {
        return Material.iron;
    }

    @Override
    public boolean getUnbreakable() {
        return false;
    }

    @Override
    public int getHarvestLevel() {
        return 3;
    }

    @Override
    public int getOpacity() {
        return 1;
    }

    @Override
    public float getHardness() {
        return 25.0F;
    }

    @Override
    public float getLightLevel() {
        return 0;
    }

    @Override
    public float getResistance() {
        return 50.0F;
    }

    @Override
    public String getBlockName() {
        return "DiamondFrameBox";
    }

    @Override
    public String getTextureName() {
        return "";
    }

    @Override
    public String getHarvestToolClass() {
        return "pickaxe";
    }

    @Override
    public Block.SoundType getStepSound() {
        return Block.soundTypeMetal;
    }

    @Override
    public boolean getIsOpaqueCube() {
        return false;
    }
}
