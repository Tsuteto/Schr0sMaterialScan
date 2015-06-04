package schr0.materialscan.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import schr0.materialscan.creativetab.MaterialScanCreativeTabs;
import schr0.materialscan.init.MaterialScanResource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTruthCrystal extends Item
{

	public ItemTruthCrystal(String name)
	{
		this.setUnlocalizedName(name);

		this.setTextureName(MaterialScanResource.DOMAIN + "truth_crystal");

		this.setCreativeTab(MaterialScanCreativeTabs.ITEM_TAB);
	}

	// ツールチップの追加
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		IChatComponent iChatManual = new ChatComponentTranslation("item.truthCrystal.manual", new Object[0]);
		String manual = EnumChatFormatting.GREEN + iChatManual.getUnformattedTextForChat();

		list.add(manual);
	}

}
