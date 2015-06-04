package schr0.materialscan.packet.scan.aciton;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import schr0.materialscan.MaterialScanCore;
import schr0.materialscan.item.ItemMaterialScanner;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageScanActionHandler implements IMessageHandler<MessageScanAction, IMessage>
{

	@Override
	public IMessage onMessage(MessageScanAction message, MessageContext ctx)
	{
		EntityPlayer player = MaterialScanCore.proxy.getMinecraft().thePlayer;

		if (player != null && player.getGameProfile().getId().toString().equals(message.getUUID()))
		{
			if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemMaterialScanner)
			{
				ItemStack stackHeldItem = player.getHeldItem();
				ItemMaterialScanner itemMaterialScanner = (ItemMaterialScanner) stackHeldItem.getItem();

				if (message.isAction())
				{
					player.setItemInUse(stackHeldItem, itemMaterialScanner.getMaxItemUseDuration(stackHeldItem));
				}
				else
				{
					player.stopUsingItem();
				}
			}
		}

		return null;
	}

}
