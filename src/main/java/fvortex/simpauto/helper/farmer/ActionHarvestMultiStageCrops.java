package fvortex.simpauto.helper.farmer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ActionHarvestMultiStageCrops extends ActionHarvestCrop {

    protected List<Integer> harvestableMeta;

    public ActionHarvestMultiStageCrops()
    {
        harvestableMeta = new ArrayList<>();
    }

    public ActionHarvestMultiStageCrops(Collection<Integer> collection)
    {
        harvestableMeta = new ArrayList<>();
        harvestableMeta.addAll(collection);
    }

    public void addHarvestableMeta(int meta)
    {
        harvestableMeta.add(meta);
    }

    @Override
    public boolean canHarvest(World world, BlockPos pos, IBlockState state) {
        return harvestableMeta.contains(state.getBlock().getMetaFromState(state));
    }

    public abstract int getResetMeta();

    /**
     * After harvesting, we need to reset the crop
     *
     * @param world
     * @param pos
     */
    @Override
    public void resetCrop(World world, BlockPos pos, IBlockState state) {
        if (state.getBlock().getMetaFromState(state) == getResetMeta())
            super.resetCrop(world, pos, state);
    }
}
