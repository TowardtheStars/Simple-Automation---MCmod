package fvortex.simpauto.proxy;

import fvortex.simpauto.ConfigLoader;
import fvortex.simpauto.block.BlockRegistry;
import fvortex.simpauto.helper.farmer.FarmHelper;
import fvortex.simpauto.item.ItemRegistry;
import fvortex.simpauto.tileentity.TileEntityRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void init(FMLInitializationEvent event)
    {
        FarmHelper.init();
    }

    public void preInit(FMLPreInitializationEvent event)
    {
        BlockRegistry.preInit();
        ItemRegistry.preInit();
        new TileEntityRegistry(event);
        new ConfigLoader(event);
    }
}
