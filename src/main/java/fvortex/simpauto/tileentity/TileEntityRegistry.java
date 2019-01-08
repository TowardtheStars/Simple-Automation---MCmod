package fvortex.simpauto.tileentity;

import fvortex.simpauto.Reference;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {
    public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(Reference.modID, id));
    }
    public TileEntityRegistry(FMLPreInitializationEvent event)
    {
        registerTileEntity(TileEntityFarmer.class, "AutoFarmer");
//        registerTileEntity(TileEntityItemDropper.class, "ItemDropper");
//        registerTileEntity(TileEntityUpSideDownHopper.class, "UpsideDownDropper");
    }
}
