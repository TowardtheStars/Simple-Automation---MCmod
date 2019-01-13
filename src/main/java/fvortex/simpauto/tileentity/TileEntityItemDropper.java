package fvortex.simpauto.tileentity;

import fvortex.simpauto.block.BlockItemDropper;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.util.ITickable;

public class TileEntityItemDropper extends TileEntityDropper implements ITickable{

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
        if (this.world!=null && !this.world.isRemote) {
            --transferCooldown;
            if (transferCooldown <= 0) {
                Block block = this.world.getBlockState(this.pos).getBlock();
                if (block instanceof BlockItemDropper) {
                    ((BlockItemDropper) block).dispense(world, pos);
                }
                transferCooldown = 8;
            }
        }
    }
}
