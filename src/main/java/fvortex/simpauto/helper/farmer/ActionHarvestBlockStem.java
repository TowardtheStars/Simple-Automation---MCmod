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
        System.out.println("can harvest crop.");
        return state.getValue(BlockStem.FACING) != EnumFacing.UP;

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
        BlockPos fruitPos = getFruitPos(pos, state);
        world.getBlockState(fruitPos).getBlock().getDrops(itemStackList, world, fruitPos, world.getBlockState(fruitPos), fortune);
    }

    private static BlockPos getFruitPos(BlockPos pos, IBlockState state)
    {
        EnumFacing facing = state.getValue(BlockStem.FACING);
        BlockPos pos1 = pos.add(facing.getFrontOffsetX(), 0, facing.getFrontOffsetZ());
        System.out.println("fruit pos:" + pos1.toString());
        return pos1;
    }

    /**
     * After harvesting, we need to reset the crop
     *
     * @param world
     * @param pos
     */
    @Override
    public void resetCrop(World world, BlockPos pos) {
        world.setBlockToAir(getFruitPos(pos, world.getBlockState(pos)));
        System.out.println("reset success");
    }
}
