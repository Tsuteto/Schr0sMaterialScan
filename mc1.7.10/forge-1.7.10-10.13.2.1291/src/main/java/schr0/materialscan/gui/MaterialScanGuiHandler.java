package schr0.materialscan.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import schr0.materialscan.init.MaterialScanGui;
import schr0.materialscan.init.MaterialScanItem;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MaterialScanGuiHandler implements IGuiHandler
{

	// サーバー側のGui設定(Container)
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		if (id == MaterialScanGui.MATERIAL_BOOK_ID)
		{
			ItemStack stackHeldItem = player.getHeldItem();

			if (stackHeldItem != null && stackHeldItem.getItem() == MaterialScanItem.materialBook)
			{
				return new ContainerMaterialBook(player, stackHeldItem);
			}
		}

		return null;
	}

	// クライアント側のGui設定(Gui)
	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		if (id == MaterialScanGui.MATERIAL_BOOK_ID)
		{
			ItemStack stackHeldItem = player.getHeldItem();

			if (stackHeldItem != null && stackHeldItem.getItem() == MaterialScanItem.materialBook)
			{
				return new GuiMaterialBook(player, stackHeldItem);
			}
		}

		return null;
	}
}
