package fvortex.simpauto.helper.farmer;

import net.minecraft.block.BlockStem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ActionHarvestBlockStem extends ActionHarvestCrop {
    /**
     * This decides whether a block should be treated as defined below when being harvested
     * This function shouldn't judge whether the block can be harvested
     *
     * @param world
     * @param pos
     * @param state
     * @return
     */
    @Override
    public boolean shouldApplyAction(World world, BlockPos pos, IBlockState state) {
        return state.getBlock() instanceof BlockStem;
    }

    /**
     * Judge whether the block can be harvested
     *
     * @param world
     * @param pos
     * @param state
     * @return
     */
    @Override
    public boolean canHarvest(World world, BlockPos pos, IBlockState state) {
        return state.getBlock().getActualState(state, world, pos).getValue(BlockStem.FACING) != EnumFacing.UP;
    }


    /**
     * Same as getDrop for Block. Override to be compatible with BlockStem.
     *
     * @param itemStackList
     * @param world
     * @param pos
     * @param state
     * @param fortune
     */
    @Override
    public void getCropDrop(NonNullList<ItemStack> itemStackList, World world, BlockPos pos, IBlockState state, int fortune) {
        BlockPos fruitPos = getFruitPos(world, pos, state);
        world.getBlockState(fruitPos).getBlock().getDrops(itemStackList, world, fruitPos, world.getBlockState(fruitPos), fortune);
    }

    private static BlockPos getFruitPos(World world, BlockPos pos, IBlockState state)
    {
        EnumFacing facing = state.getBlock().getActualState(state, world, pos).getValue(BlockStem.FACING);
        return pos.add(facing.getFrontOffsetX(), 0, facing.getFrontOffsetZ());
    }

    /**
     * After harvesting, we need to reset the crop
     *
     * @param world
     * @param pos
     */
    @Override
    public void resetCrop(World world, BlockPos pos, IBlockState state) {
        world.setBlockToAir(getFruitPos(world, pos, state));
    }
}
