package fvortex.simpauto.proxy;

import fvortex.simpauto.block.BlockRegistry;
import fvortex.simpauto.item.ItemRegistry;
import fvortex.simpauto.tileentity.TileEntityRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void init(FMLInitializationEvent event)
    {


    }

    public void preInit(FMLPreInitializationEvent event)
    {
        BlockRegistry.preInit();
        ItemRegistry.preInit();
        new TileEntityRegistry(event);
    }
}
