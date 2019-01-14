package fvortex.simpauto.block;

import fvortex.simpauto.tileentity.TileEntityFarmer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFarmer extends BlockContainer
{

    public BlockFarmer()
    {
        super(Material.IRON, MapColor.IRON);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("", 0);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFarmer();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

}