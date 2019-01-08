package fvortex.simpauto.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityFarmer extends TileEntity implements ITickable
{
    public static final int RANGE = 9;
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
        if (isBlockHarvetable(this.world, currentManagingPos, managing))
            if (tile != null)
            {
                inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (inventory != null)
                {
                    harvest(this.world, currentManagingPos, managing, inventory);
                }
            }
    }

    private static boolean isBlockHarvetable(World world, BlockPos pos, IBlockState blockState)
    {
        Block block = blockState.getBlock();
        if (blockState.getBlock() instanceof IGrowable)
            return !((IGrowable)block).canGrow(world, pos, blockState, !world.isRemote);


        return false;
    }


    private static void harvest(World world, BlockPos pos, IBlockState blockState, IItemHandler inventory)
    {
        NonNullList<ItemStack> dropList = NonNullList.create();

        // check if there is enough place for harvest
        boolean shouldHarvest = true;
        blockState.getBlock().getDrops(dropList, world, pos, blockState, 0);
        for (ItemStack stack :
                dropList) {

            ItemStack leftOver;
            leftOver = stack;
            for (int i = 0; i < inventory.getSlots(); i++) {
                leftOver = inventory.insertItem(i, leftOver, true);
            }
            shouldHarvest = shouldHarvest && leftOver.isEmpty();
            if (!shouldHarvest) break;
        }

        PropertyInteger age = null;
        for (IProperty p:
                blockState.getPropertyKeys()) {
            if (p.getName().equals("age"))
            {
                age = (PropertyInteger) p;
                break;
            }
        }

        if (shouldHarvest && age != null)
        {
            for (ItemStack stack :
                    dropList) {
                for (int i = 0; i < inventory.getSlots() && !stack.isEmpty(); i++) {
                    stack = inventory.insertItem(i, stack, false);
                }
            }
            world.setBlockState(pos, blockState.withProperty(age, 0), 2);
        }
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