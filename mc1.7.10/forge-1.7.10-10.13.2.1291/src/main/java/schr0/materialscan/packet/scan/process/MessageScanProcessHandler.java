package schr0.materialscan.packet.scan.process;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import schr0.materialscan.init.MaterialScanPacket;
import schr0.materialscan.item.ItemMaterialScanner;
import schr0.materialscan.library.LibScan;
import schr0.materialscan.packet.scan.aciton.MessageScanAction;
import schr0.materialscan.packet.scan.effect.MessageScanEffect;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageScanProcessHandler implements IMessageHandler<MessageScanProcess, IMessage>
{

	@Override
	public IMessage onMessage(MessageScanProcess message, MessageContext ctx)
	{
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		Entity target = message.getEntityFromID(player.worldObj);

		if (player != null && player.getGameProfile().getId().toString().equals(message.getUUID()))
		{
			if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemMaterialScanner)
			{
				ItemStack stackHeldItem = player.getHeldItem();
				ItemMaterialScanner itemMaterialScanner = (ItemMaterialScanner) stackHeldItem.getItem();

				if (target instanceof EntityLivingBase)
				{
					EntityLivingBase livingBase = (EntityLivingBase) target;

					if (LibScan.isCompleteScan(livingBase))
					{
						MaterialScanPacket.DISPATCHER.sendToAll(new MessageScanAction(player, false));
					}
					else
					{
						MaterialScanPacket.DISPATCHER.sendToAll(new MessageScanAction(player, true));

						onScannerProcess(itemMaterialScanner, stackHeldItem, player, livingBase);
					}
				}
			}
		}

		return null;
	}

	// TODO /* ======================================== MessageScanProcessHandler START ===================================== */

	// スキャナー処理
	private static void onScannerProcess(ItemMaterialScanner itemMaterialScanner, ItemStack stack, EntityPlayer player, EntityLivingBase target)
	{
		int scanMaxTime = itemMaterialScanner.getMaxScanTime(stack);
		int scanTime = itemMaterialScanner.getScanTime();
		int scanMaxCount = LibScan.getMaxScanCount(target);
		int scanCount = LibScan.getScanCount(target);

		scanTime++;

		if (scanMaxTime <= scanTime)
		{
			scanCount += itemMaterialScanner.getScanCountAmount(stack);

			LibScan.setScanCount(target, scanCount);

			itemMaterialScanner.onScannerFnish(stack, player, target, scanMaxTime, scanTime, scanMaxCount, scanCount);

			MaterialScanPacket.DISPATCHER.sendToAll(new MessageScanEffect(target, 10));
		}

		itemMaterialScanner.onScannerUpdate(stack, player, target, scanMaxTime, scanTime, scanMaxCount, scanCount);

		if (scanMaxTime <= scanTime)
		{
			itemMaterialScanner.clearScanTime();
		}
		else
		{
			itemMaterialScanner.setScanTime(scanTime);
		}
	}
}
