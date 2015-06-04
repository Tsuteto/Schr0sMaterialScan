package schr0.materialscan.packet.scan.finish;

import net.minecraft.entity.player.EntityPlayer;
import schr0.materialscan.init.MaterialScanPacket;
import schr0.materialscan.packet.scan.aciton.MessageScanAction;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageScanFnishHandler implements IMessageHandler<MessageScanFnish, IMessage>
{

	@Override
	public IMessage onMessage(MessageScanFnish message, MessageContext ctx)
	{
		EntityPlayer player = ctx.getServerHandler().playerEntity;

		if (player != null && player.getGameProfile().getId().toString().equals(message.getUUID()))
		{
			MaterialScanPacket.DISPATCHER.sendToAll(new MessageScanAction(player, false));
		}

		return null;
	}

}
