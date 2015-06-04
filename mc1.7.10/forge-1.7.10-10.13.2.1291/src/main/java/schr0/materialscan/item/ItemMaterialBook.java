package schr0.materialscan.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import schr0.materialscan.MaterialScanCore;
import schr0.materialscan.creativetab.MaterialScanCreativeTabs;
import schr0.materialscan.gui.ContainerMaterialBook;
import schr0.materialscan.init.MaterialScanGui;
import schr0.materialscan.init.MaterialScanResource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMaterialBook extends Item
{

	public ItemMaterialBook(String name)
	{
		this.setUnlocalizedName(name);

		this.setTextureName(MaterialScanResource.DOMAIN + "material_book");

		this.setCreativeTab(MaterialScanCreativeTabs.ITEM_TAB);

		this.setMaxStackSize(1);
	}

	// ツールチップの追加
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		IChatComponent iChatUse = new ChatComponentTranslation("item.materialBook.use", new Object[0]);
		String use = EnumChatFormatting.DARK_RED + iChatUse.getUnformattedTextForChat();

		IChatComponent iChatManual = new ChatComponentTranslation("item.materialBook.manual", new Object[0]);
		String manual = EnumChatFormatting.GREEN + iChatManual.getUnformattedTextForChat();

		list.add(use);
		list.add(manual);
	}

	// 右クリックを押した際のItemStack
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			player.openGui(MaterialScanCore.instance, MaterialScanGui.MATERIAL_BOOK_ID, world, 0, 0, 0);
		}

		world.playSoundAtEntity(player, MaterialScanResource.DOMAIN + "page_open", 0.5F, 1.0F);

		return stack;
	}

	// ItemのonUpdate
	@Override
	public void onUpdate(ItemStack invStack, World world, Entity owner, int slot, boolean isHeld)
	{
		if (owner instanceof EntityPlayer && isHeld)
		{
			EntityPlayer player = (EntityPlayer) owner;

			if (player.openContainer instanceof ContainerMaterialBook)
			{
				ContainerMaterialBook containerMaterialBook = (ContainerMaterialBook) player.openContainer;

				if (containerMaterialBook.getCardEntity() != null)
				{
					EntityLivingBase livingBase = containerMaterialBook.getCardEntity();

					++livingBase.ticksExisted;

					if (2400 < livingBase.ticksExisted)
					{
						livingBase.ticksExisted = 0;
					}
				}
			}
		}
	}

}