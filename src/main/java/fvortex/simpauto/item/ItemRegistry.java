package fvortex.simpauto.item;

import fvortex.simpauto.SimpleAutomation;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemRegistry {
    public static final List<Item> itemList = new ArrayList<>();
    public static final ItemAutoCore itemAutoCore = new ItemAutoCore();

    public static void registerItem(Item item, String name)
    {
        item.setRegistryName(name);
        item.setUnlocalizedName(name);
        ForgeRegistries.ITEMS.register(item);
        item.setCreativeTab(SimpleAutomation.modTab);
        itemList.add(item);
    }

    @SideOnly(Side.CLIENT)
    public static void clientInit()
    {
        for (Item item :
                itemList) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));
        }
    }

    public static void preInit()
    {
        registerItem(itemAutoCore, "auto_core");
    }
}
