package fvortex.simpauto.block;

import fvortex.simpauto.tileentity.TileEntityItemDropper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockSourceImpl;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockItemDropper extends BlockDropper {
    static class MuteBehaviorDispenseItem implements IBehaviorDispenseItem {
        public final ItemStack dispense(IBlockSource source, ItemStack stack)
        {
            ItemStack itemstack = this.dispenseStack(source, stack);
            return itemstack;
        }

        protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
        {
            EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue(BlockDispenser.FACING);
            IPosition iposition = BlockDispenser.getDispensePosition(source);
            ItemStack itemstack = stack.splitStack(1);
            doDispense(source.getWorld(), itemstack, 6, enumfacing, iposition);
            return stack;
        }

        public static void doDispense(World worldIn, ItemStack stack, int speed, EnumFacing facing, IPosition position)
        {
            double d0 = position.getX();
            double d1 = position.getY();
            double d2 = position.getZ();

            if (facing.getAxis() == EnumFacing.Axis.Y)
            {
                d1 = d1 - 0.125D;
            }
            else
            {
                d1 = d1 - 0.15625D;
            }

            EntityItem entityitem = new EntityItem(worldIn, d0, d1, d2, stack);
//            double d3 = worldIn.rand.nextDouble() * 0.1D + 0.2D;
//            entityitem.motionX = (double)facing.getFrontOffsetX() * d3;
//            entityitem.motionY = 0.20000000298023224D;
//            entityitem.motionZ = (double)facing.getFrontOffsetZ() * d3;
//            entityitem.motionX = 0.007499999832361937D * (double)speed;
//            entityitem.motionY = 0.007499999832361937D * (double)speed;
//            entityitem.motionZ = 0.007499999832361937D * (double)speed;
            entityitem.motionX = entityitem.motionY = entityitem.motionZ = 0.0D;
            worldIn.spawnEntity(entityitem);
        }
    }

    private final IBehaviorDispenseItem behaviorDispenseItem = new MuteBehaviorDispenseItem();
    public BlockItemDropper()
    {
        super();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityItemDropper();
    }

    @Override
    public IBehaviorDispenseItem getBehavior(ItemStack stack)
    {
        return this.behaviorDispenseItem;
    }
    public void dispense(World worldIn, BlockPos pos)
    {
        BlockSourceImpl blocksourceimpl = new BlockSourceImpl(worldIn, pos);
        TileEntityDispenser tileentitydispenser = (TileEntityDispenser)blocksourceimpl.getBlockTileEntity();

        if (tileentitydispenser != null)
        {
            int i = tileentitydispenser.getDispenseSlot();

            if (i >= 0)
            {
                ItemStack itemstack = tileentitydispenser.getStackInSlot(i);

                if (!itemstack.isEmpty() && net.minecraftforge.items.VanillaInventoryCodeHooks.dropperInsertHook(worldIn, pos, tileentitydispenser, i, itemstack))
                {
                    ItemStack itemstack1 = this.getBehavior(itemstack).dispense(blocksourceimpl, itemstack);
                    tileentitydispenser.setInventorySlotContents(i, itemstack1);
                }
            }
        }
    }
}
