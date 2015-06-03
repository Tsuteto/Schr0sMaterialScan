package schr0.materialscan.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import schr0.materialscan.MaterialScanCore;
import schr0.materialscan.init.MaterialScanItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MaterialScanCreativeTabs
{

	// 『Material Scan Item』のCreativeTab
	public static final CreativeTabs ITEM_TAB = new CreativeTabs(MaterialScanCore.MODID + "." + "item")
	{

		// CreativeTabのアイコン
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return MaterialScanItem.materialBook;
		}

	};

}
