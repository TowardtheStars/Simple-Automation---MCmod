package fvortex.simpauto.helper;

import com.pam.harvestcraft.blocks.growables.BlockPamFruit;
import com.pam.harvestcraft.blocks.growables.BlockPamFruitLog;
import com.pam.harvestcraft.blocks.growables.PamCropGrowable;
import fvortex.simpauto.tileentity.TileEntityFarmer;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;


public class FarmHelper {

    @Optional.Method(modid = "harvestcraft")
    public static boolean isPamCrop(IBlockState state)
    {
        return state.getBlock() instanceof PamCropGrowable;
    }

    @Optional.Method(modid = "harvestcraft")
    public static boolean isPamCropRipe(IBlockState state)
    {
        PamCropGrowable block = (PamCropGrowable) state.getBlock();
        return block.isMature(state);
    }

    public static boolean isHarvestable(World world, BlockPos pos, TileEntityFarmer farmer)
    {
        IBlockState state = world.getBlockState(pos);
        if (Loader.isModLoaded("harvestcraft"))
        {
            if (isPamCrop(state))
                return isPamCropRipe(state);
        }
        if (state.getBlock() instanceof BlockCrops)
        {
            return ((BlockCrops) state.getBlock()).isMaxAge(state);
        }
        return false;
    }

}
