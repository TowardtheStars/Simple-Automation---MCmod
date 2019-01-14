package fvortex.simpauto.block;

import fvortex.simpauto.tileentity.TileEntityFarmer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockFarmer extends BlockContainer
{

    public BlockFarmer()
    {
        super(Material.IRON, MapColor.IRON);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("", 0);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFarmer();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.translateToLocal("tile.farmer.tooltip"));
    }
}