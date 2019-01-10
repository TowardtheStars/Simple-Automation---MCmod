package fvortex.simpauto.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class TileEntityFarmer extends TileEntity implements ITickable
{
    private static final int RANGE = 9;
    private int ticker;
    private int posCounter;
    private static final int TICK_SPEED = 5;
    private BlockPos currentManagingPos;

    public TileEntityFarmer()
    {
        super();
        ticker = TICK_SPEED;
        posCounter = -1;
        currentManagingPos = this.pos.add(-RANGE/2, 0, -RANGE/2);
    }

    private void toNextPos()
    {
        ++posCounter;
        currentManagingPos = this.pos.add(posCounter / RANGE - RANGE/2, 0, posCounter % RANGE - RANGE/2);
        if (posCounter == RANGE * RANGE - 1)
            posCounter = -1;
    }

    private void managePos()
    {
        TileEntity tile = this.world.getTileEntity(this.pos.up());
        IItemHandler inventory;
        if (tile == null)
            tile = this.world.getTileEntity(this.pos.down());

        IBlockState managing = this.world.getBlockState(currentManagingPos);
        if (isBlockHarvestable(this.world, currentManagingPos, managing))
            if (tile != null)
            {
                inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (inventory != null)
                {
                    harvest(this.world, currentManagingPos, managing, inventory);
                }
            }
    }

    private static boolean isBlockHarvestable(World world, BlockPos pos, IBlockState blockState)
    {
        Block block = blockState.getBlock();
        return (block instanceof IGrowable) && !((IGrowable) block).canGrow(world, pos, blockState, !world.isRemote);
    }

    private static List<ItemStack> getHarvestItemList
            (World world, BlockPos pos, IBlockState blockState)
    {
        List<ItemStack> dropList;
        Block block = blockState.getBlock();
        dropList = block.getDrops(world, pos, blockState, 0);
        for (ItemStack stack :
                dropList) {
            if (stack.getCount() > 1)
                stack.shrink(1);
        }
        return dropList;
    }


    private static void harvest(World world, BlockPos pos, IBlockState blockState, IItemHandler inventory)
    {
        List<ItemStack> dropList;

        // check if there is enough place for harvest first
        dropList = getHarvestItemList(world, pos, blockState);
        if (canInsertItemStackList(dropList, inventory))
        {
            for (ItemStack stack :
                    dropList) {
                for (int i = 0; i < inventory.getSlots() && !stack.isEmpty(); i++) {
                    stack = inventory.insertItem(i, stack, false);
                }
            }
            world.setBlockState(pos, blockState.getBlock().getDefaultState());
        }
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


    public void update() {
        if (!this.world.isRemote)
        {
            if (--ticker == 0)
            {
                toNextPos();
                managePos();
                ticker = TICK_SPEED;
            }
        }
    }
}