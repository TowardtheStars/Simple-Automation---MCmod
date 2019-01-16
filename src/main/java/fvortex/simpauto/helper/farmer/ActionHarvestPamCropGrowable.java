package fvortex.simpauto.helper.farmer;

import com.pam.harvestcraft.blocks.growables.PamCropGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

import java.util.List;


public class ActionHarvestPamCropGrowable extends ActionHarvestCrop {
    @Override
    @Optional.Method(modid = "harvestcraft")
    public boolean shouldApplyAction(World world, BlockPos pos, IBlockState state) {
        return state.getBlock() instanceof PamCropGrowable;
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
    @Optional.Method(modid = "harvestcraft")
    public boolean canHarvest(World world, BlockPos pos, IBlockState state) {
        return ((PamCropGrowable)state.getBlock()).isMature(state);
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
    @Optional.Method(modid = "harvestcraft")
    public void getCropDrop(NonNullList<ItemStack> itemStackList, World world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> newList = ((PamCropGrowable)state.getBlock()).getDrops(world, pos, state, fortune);
        itemStackList.addAll(newList);
    }

}
