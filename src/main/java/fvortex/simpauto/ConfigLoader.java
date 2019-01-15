package fvortex.simpauto;

import fvortex.simpauto.tileentity.TileEntityFarmer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

public class ConfigLoader {
    private static Logger logger;
    private static Configuration config;

    private static final String COMMENT_FARMER_RANGE = "Radius that farmer machines can harvest, means it can harvest (2*radius+1)^2 area";
    private static final String COMMENT_FARMER_INTERVAL = "Interval ticks between harvesting for farmer machines";
    public ConfigLoader(FMLPreInitializationEvent e)
    {
        logger = e.getModLog();
        config = new Configuration(e.getSuggestedConfigurationFile());
        config.load();
        load();
    }

    public static void load()
    {
        logger.info("Start loading config for Simple Automation");
        TileEntityFarmer.setRange(config.getInt("farmerHarvestRadius", Configuration.CATEGORY_GENERAL, 4, 0, 16, COMMENT_FARMER_RANGE));
        TileEntityFarmer.setTickInterval(config.getInt("farmerHarvestInterval", Configuration.CATEGORY_GENERAL, 5, 1, 20, COMMENT_FARMER_INTERVAL));

        config.save();
        logger.info("Finished loading config. ");
    }
}
