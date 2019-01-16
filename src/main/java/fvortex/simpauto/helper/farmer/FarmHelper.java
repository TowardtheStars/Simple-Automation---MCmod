package fvortex.simpauto.helper.farmer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;


public class FarmHelper {

    public static List<ActionHarvestCrop> actionList = new ArrayList<>();

    public static void init()
    {
        actionList.add(new ActionHarvestBlockCrops());
        actionList.add(new ActionHarvestBlockReed());
        actionList.add(new ActionHarvestBlockStem());
        actionList.add(new ActionHarvestNetherWart());
        if (Loader.isModLoaded("harvestcraft"))
            actionList.add(new ActionHarvestPamCropGrowable());
    }

    public static boolean canInsertItemStackList(List<ItemStack> dropList, IItemHandler inventory)
    {
        for (ItemStack stack : dropList) {
            ItemStack leftOver;
            leftOver = stack;
            for (int i = 0; i < inventory.getSlots(); i++) {
                leftOver = inventory.insertItem(i, leftOver, true);
            }
            if (!leftOver.isEmpty()) return false;
        }
        return true;
    }

    public static void harvestBlock(World world, BlockPos pos, IBlockState state, IItemHandler inventory, int fortune)
    {
        ActionHarvestCrop actionType = null;
        NonNullList<ItemStack> dropList = NonNullList.create();
        for (ActionHarvestCrop action :
                actionList) {
            if (action.shouldApplyAction(world, pos, state))
            {
                actionType = action;
                break;
            }
        }
        if (actionType != null && actionType.canHarvest(world, pos, state))
        {
            actionType.getCropDrop(dropList, world, pos, state, fortune);
            if (canInsertItemStackList(dropList, inventory))
            {
                for (ItemStack stack :
                        dropList) {
                    for (int i = 0; i < inventory.getSlots() && !stack.isEmpty(); i++) {
                        stack = inventory.insertItem(i, stack, false);
                    }
                }
                actionType.resetCrop(world, pos);
                ItemDye.spawnBonemealParticles(world, pos, 15);
            }
        }

    }

}
