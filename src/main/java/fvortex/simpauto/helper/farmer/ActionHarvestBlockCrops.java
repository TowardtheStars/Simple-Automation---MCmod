package fvortex.simpauto.helper.farmer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ActionHarvestBlockCrops extends ActionHarvestCrop{
    @Override
    public boolean shouldApplyAction(World world, BlockPos pos, IBlockState state)
    {
        return state.getBlock() instanceof BlockCrops;
    }

    @Override
    public boolean canHarvest(World world, BlockPos pos, IBlockState state)
    {
        BlockCrops crops = (BlockCrops) state.getBlock();
        return crops.isMaxAge(state);
    }

}
