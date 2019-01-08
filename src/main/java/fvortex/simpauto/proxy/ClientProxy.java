package fvortex.simpauto.proxy;

import fvortex.simpauto.block.BlockRegistry;
import fvortex.simpauto.item.ItemRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        BlockRegistry.clientInit();
        ItemRegistry.clientInit();
    }

    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }
}
