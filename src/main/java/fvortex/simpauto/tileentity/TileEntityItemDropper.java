package fvortex.simpauto.tileentity;

import fvortex.simpauto.block.BlockItemDropper;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityItemDropper extends TileEntityDropper implements ITickable{

    public TileEntityItemDropper()
    {
        super();
        System.out.println("A new TileEntityItemDropper was created");
    }
    private int transferCooldown = -1;

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        transferCooldown = nbt.getInteger("TransferCoolDown");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("TransferCoolDown", transferCooldown);
        return nbt;
    }

    @Override
    public void update() {
        if (this.world!=null && !this.world.isRemote && !(this.world.getBlockState(pos).getValue((BlockItemDropper.TRIGGERED)))) {
            --transferCooldown;
            if (transferCooldown <= 0) {
                Block block = this.world.getBlockState(this.pos).getBlock();
                if (block instanceof BlockItemDropper) {
                    ((BlockItemDropper) block).dispense(world, pos);
                }
                transferCooldown = 8;
            }
            this.markDirty();
        }
    }

    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return oldState.getBlock() != newSate.getBlock();
    }

}
