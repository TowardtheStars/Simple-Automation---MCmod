package fvortex.simpauto.helper.farmer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ActionHarvestCrop {

    /**
     * This decides whether a block should be treated as defined below when being harvested
     * This function shouldn't judge whether the block can be harvested
     * @param world
     * @param pos
     * @param state
     * @return
     */
    public boolean shouldApplyAction(World world, BlockPos pos, IBlockState state)
    {
        return false;
    }

    /**
     * Decide whether the crop need to be replanted after harvest
     * @return
     */
    public boolean needReplant()
    {
        return false;
    }

    /**
     * Judge whether the block can be harvested
     * @param world
     * @param pos
     * @param state
     * @return
     */
    public boolean canHarvest(World world, BlockPos pos, IBlockState state)
    {
        return false;
    }

    /**
     * Same as getDrop for Block. Override to be compatible with BlockStem.
     * @param itemStackList
     * @param world
     * @param pos
     * @param fortune
     */
    public void getCropDrop(NonNullList<ItemStack> itemStackList, World world, BlockPos pos, IBlockState state, int fortune)
    {
        world.getBlockState(pos).getBlock().getDrops(itemStackList, world, pos, state, fortune);
    }

    /**
     * After harvesting, we need to reset the crop
     * @param world
     * @param pos
     */
    public void resetCrop(World world, BlockPos pos, IBlockState state)
    {
        world.setBlockState(pos, state.getBlock().getDefaultState());
    }


}
