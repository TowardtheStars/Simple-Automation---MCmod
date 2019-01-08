package fvortex.simpauto.block;

import fvortex.simpauto.SimpleAutomation;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class BlockRegistry {
    public static List<Block> blockList = new ArrayList<>();

    public static BlockFarmer blockFarmer = new BlockFarmer();
    public static BlockItemDropper blockItemDropper = new BlockItemDropper();
    public static BlockUpSideDownHopper blockUpSideDownHopper = new BlockUpSideDownHopper();

    public static void registerBlock(Block block, String registryName)
    {
        block.setCreativeTab(SimpleAutomation.modTab);
        block.setRegistryName(registryName);
        block.setUnlocalizedName(registryName);
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        blockList.add(block);
    }

    public static void preInit()
    {
        registerBlock(blockFarmer, "farmer");
//        registerBlock(blockItemDropper, "item_dropper");
//        registerBlock(blockUpSideDownHopper, "upside_down_hopper");
    }

    @SideOnly(Side.CLIENT)
    public static void clientInit()
    {
        for (Block block:blockList) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
                    new ModelResourceLocation(block.getRegistryName(), "normal"));
        }
    }
}
