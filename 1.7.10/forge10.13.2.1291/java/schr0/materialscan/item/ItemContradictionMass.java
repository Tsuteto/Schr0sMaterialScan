package schr0.materialscan.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import schr0.materialscan.creativetab.MaterialScanCreativeTabs;
import schr0.materialscan.damagesource.MaterialScanDamageSource;
import schr0.materialscan.init.MaterialScanResource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemContradictionMass extends Item
{

	public ItemContradictionMass(String name)
	{
		this.setUnlocalizedName(name);

		this.setTextureName(MaterialScanResource.DOMAIN + "contradiction_mass");

		this.setCreativeTab(MaterialScanCreativeTabs.ITEM_TAB);
	}

	// ツールチップの追加
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		IChatComponent iChatManual = new ChatComponentTranslation("item.contradictionMass.manual", new Object[0]);
		String manual = EnumChatFormatting.GREEN + iChatManual.getUnformattedTextForChat();

		list.add(manual);
	}

	// ItemのonUpdate
	@Override
	public void onUpdate(ItemStack invStack, World world, Entity owner, int slot, boolean isHeld)
	{
		if (owner instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) owner;

			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20 * 5, 2));
			player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 20 * 5, 2));
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, 20 * 5, 0));
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 20 * 5, 0));
			player.addPotionEffect(new PotionEffect(Potion.hunger.id, 20 * 5, 2));

			// 『崩壊ダメージ』 : 0.5F -> player
			player.attackEntityFrom(MaterialScanDamageSource.decay, 0.5F);
		}
	}

}
