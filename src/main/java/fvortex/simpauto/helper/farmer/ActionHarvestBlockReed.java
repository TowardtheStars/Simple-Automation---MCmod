package fvortex.simpauto.helper.farmer;

import net.minecraft.block.BlockReed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ActionHarvestBlockReed extends ActionHarvestCrop {

    public int getMaxHeight()
    {
        return 3;
    }

    @Override
    public boolean shouldApplyAction(World world, BlockPos pos, IBlockState state) {
        return state.getBlock() instanceof BlockReed;
    }

    @Override
    public boolean canHarvest(World world, BlockPos pos, IBlockState state) {
        return world.getBlockState(pos.up()).getBlock() instanceof BlockReed;
    }

    @Override
    public void getCropDrop(NonNullList<ItemStack> itemStackList, World world, BlockPos pos, IBlockState state, int fortune) {
        IBlockState state1;
        for (int i = 1; i < getMaxHeight(); i++) {
            state1 = world.getBlockState(pos.up(i));
            if (state1.getBlock() instanceof BlockReed)
                state1.getBlock().getDrops(itemStackList, world, pos.up(i), state, fortune);
            else break;
        }
    }

    @Override
    public void resetCrop(World world, BlockPos pos) {

        for (int i = getMaxHeight()- 1; i > 0; i--) {
            if (world.getBlockState(pos.up(i)).getBlock() instanceof BlockReed)
                world.setBlockToAir(pos.up(i));
        }
    }
}
