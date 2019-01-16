package fvortex.simpauto.tileentity;

import fvortex.simpauto.helper.FarmHelper;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class TileEntityFarmer extends TileEntity implements ITickable
{
    private static int RANGE = 0;
    private static int DIAMETER = 1;
    private static int AREA = 0;
    private int ticker;
    private int posCounter;
    private static int TICK_INTERVAL = 5;
    private BlockPos currentManagingPos;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("managingPosId", posCounter);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.posCounter = compound.getInteger("managingPosId");
    }


    public static void setRange(int range)
    {
        if (range > 0) {
            TileEntityFarmer.RANGE = range;
            TileEntityFarmer.DIAMETER = 2 * range + 1;
            TileEntityFarmer.AREA = DIAMETER * DIAMETER - 1;
        }
        else {
            TileEntityFarmer.RANGE = TileEntityFarmer.AREA = 0;
            TileEntityFarmer.DIAMETER = 1;
        }
    }
    public static void setTickInterval(int interval)
    {
        if (interval > 0) TICK_INTERVAL = interval;
        else TICK_INTERVAL = 1;
    }

    public TileEntityFarmer()
    {
        super();
        ticker = TICK_INTERVAL;
        posCounter = -1;
        currentManagingPos = this.pos.add(-RANGE, 0, -RANGE);
    }

    private void toNextPos()
    {
        ++posCounter;
        if (posCounter << 1 == AREA) ++posCounter; // to save time, don't try to harvest the machine itself
        currentManagingPos = this.pos.add(posCounter / DIAMETER - RANGE, 0, posCounter % DIAMETER - RANGE);
        if (posCounter == AREA)
            posCounter = -1;
    }

    private void managePos()
    {
        TileEntity tile = this.world.getTileEntity(this.pos.up());
        IItemHandler inventory;
        if (tile == null)
            tile = this.world.getTileEntity(this.pos.down());

        IBlockState managing = this.world.getBlockState(currentManagingPos);
        if (FarmHelper.isHarvestable(world, currentManagingPos, this))
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
            ItemDye.spawnBonemealParticles(world, pos, 0);
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

    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return oldState.getBlock() != newSate.getBlock();
    }


    public void update() {
        if (!this.world.isRemote)
        {
            if (--ticker == 0)
            {
                toNextPos();
                managePos();
                ticker = TICK_INTERVAL;
            }
        }
    }
}