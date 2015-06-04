package schr0.materialscan.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import schr0.materialscan.MaterialScanCore;
import schr0.materialscan.creativetab.MaterialScanCreativeTabs;
import schr0.materialscan.init.MaterialScanPacket;
import schr0.materialscan.init.MaterialScanResource;
import schr0.materialscan.library.LibMouseOver;
import schr0.materialscan.library.LibScan;
import schr0.materialscan.packet.scan.finish.MessageScanFnish;
import schr0.materialscan.packet.scan.process.MessageScanProcess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMaterialScanner extends Item
{

	// スキャン時間
	private int scanTime;

	public ItemMaterialScanner(String name)
	{
		this.setUnlocalizedName(name);

		this.setTextureName(MaterialScanResource.DOMAIN + "material_scanner");

		this.setCreativeTab(MaterialScanCreativeTabs.ITEM_TAB);

		this.setFull3D();

		this.setMaxStackSize(1);
	}

	// ツールチップの追加
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		IChatComponent iChatUse = new ChatComponentTranslation("item.materialScanner.use", new Object[0]);
		String use = EnumChatFormatting.DARK_RED + iChatUse.getUnformattedTextForChat();

		IChatComponent iChatManual = new ChatComponentTranslation("item.materialScanner.manual", new Object[0]);
		String manual = EnumChatFormatting.GREEN + iChatManual.getUnformattedTextForChat();

		list.add(use);
		list.add(manual);
	}

	// アイテム使用状態の最大時間
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 72000;
	}

	// アイテム使用状態のアクション
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.block;
	}

	// 耐久バーを表示する判定
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return (0 < this.scanTime);
	}

	// 耐久バーの値
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return (double) (this.scanTime) / (double) (this.getMaxScanTime(stack));
	}

	// アイテム使用状態が終了した際の処理
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int count)
	{
		this.clearScanTime();
	}

	// ItemのonUpdate
	@Override
	public void onUpdate(ItemStack invStack, World world, Entity owner, int slot, boolean isHeld)
	{
		if (!world.isRemote)
		{
			return;
		}

		if (owner instanceof EntityPlayer && isHeld)
		{
			EntityPlayer player = (EntityPlayer) owner;

			if (MaterialScanCore.proxy.getMinecraft().gameSettings.keyBindUseItem.getIsKeyPressed())
			{
				MovingObjectPosition mop = LibMouseOver.getReachMouseOver(player, 9.0D);

				if (mop != null && mop.entityHit instanceof EntityLivingBase)
				{
					MaterialScanPacket.DISPATCHER.sendToServer(new MessageScanProcess(player, mop.entityHit));

					return;
				}
			}

			MaterialScanPacket.DISPATCHER.sendToServer(new MessageScanFnish(player));
		}
	}

	// TODO /* ======================================== ItemMaterialScanner START ===================================== */

	// スキャン時間の最大時間
	public int getMaxScanTime(ItemStack stack)
	{
		return 5 * 20;
	}

	// スキャン時間のgetter
	public int getScanTime()
	{
		return this.scanTime;
	}

	// スキャン時間のsetter
	public void setScanTime(int time)
	{
		this.scanTime = time;
	}

	// スキャン時間の初期化
	public void clearScanTime()
	{
		if (this.scanTime != 0)
		{
			this.scanTime = 0;
		}
	}

	// スキャン回数
	public int getScanCountAmount(ItemStack stack)
	{
		return 1;
	}

	// スキャナーのonUpdate
	public void onScannerUpdate(ItemStack stack, EntityPlayer player, EntityLivingBase target, int maxTime, int time, int maxCount, int count)
	{
		onScannerSound(player, maxTime, time);
	}

	// スキャナー完了の処理
	public void onScannerFnish(ItemStack stack, EntityPlayer player, EntityLivingBase target, int maxTime, int time, int maxCount, int count)
	{
		onScannerInfo(player, target, maxCount, count);
	}

	// スキャンの稼動音
	private static void onScannerSound(EntityLivingBase target, int maxTime, int time)
	{
		if (maxTime == time)
		{
			target.worldObj.playSoundAtEntity(target, "random.levelup", 1.0F, 1.0F);
		}
		else if (time % 20 == 0)
		{
			float pitch = 0.2F * (time / 20);

			target.worldObj.playSoundAtEntity(target, "random.click", 1.0F, pitch);
		}
	}

	// スキャンの通知
	private static void onScannerInfo(EntityPlayer player, EntityLivingBase target, int maxCount, int count)
	{
		World world = player.worldObj;

		IChatComponent countText = new ChatComponentText(count + " / " + maxCount);
		countText.getChatStyle().setColor(EnumChatFormatting.GREEN);

		IChatComponent text = new ChatComponentTranslation("item.materialScanner.scanInfo", new Object[]{countText});
		text.getChatStyle().setColor(EnumChatFormatting.RED);

		if (LibScan.isCompleteScan(target))
		{
			text = new ChatComponentTranslation("item.materialScanner.scanCompletion");
			text.getChatStyle().setColor(EnumChatFormatting.GOLD);
		}

		if (!world.isRemote)
		{
			player.addChatComponentMessage(text);
		}
	}

}