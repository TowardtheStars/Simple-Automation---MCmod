package fvortex.simpauto.helper.farmer;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ActionHarvestNetherWart extends ActionHarvestCrop{
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
        return state.getBlock() instanceof BlockNetherWart;
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
        return state.getValue(BlockNetherWart.AGE) == 3;
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
        state.getBlock().getDrops(itemStackList, world, pos, state, fortune);
    }

}
