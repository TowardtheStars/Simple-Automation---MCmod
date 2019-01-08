package fvortex.simpauto.block;

import fvortex.simpauto.tileentity.TileEntityItemDropper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockItemDropper extends BlockContainer {
    public BlockItemDropper()
    {
        super(Material.ROCK);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityItemDropper();
    }
}
