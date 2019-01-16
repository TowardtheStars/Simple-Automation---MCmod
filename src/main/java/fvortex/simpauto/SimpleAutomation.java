package fvortex.simpauto;


import fvortex.simpauto.block.BlockRegistry;
import fvortex.simpauto.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid=Reference.modID, name = Reference.name, version = Reference.version, dependencies = "after:harvestcraft")
public class SimpleAutomation {
    @Mod.Instance(Reference.modID)
    public static SimpleAutomation instance;
    public static CreativeTabs modTab = new CreativeTabs(Reference.modID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(BlockRegistry.blockUpSideDownHopper);
        }
    };

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    private Logger logger;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    public Logger getLogger() {
        return logger;
    }

}
