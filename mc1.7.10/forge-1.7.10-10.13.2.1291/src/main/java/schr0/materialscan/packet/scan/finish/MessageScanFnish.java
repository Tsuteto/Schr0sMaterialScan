package schr0.materialscan.packet.scan.finish;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageScanFnish implements IMessage
{

	// UUID
	private String uuid;

	public MessageScanFnish()
	{
		// none
	}

	public MessageScanFnish(EntityPlayer player)
	{
		this.uuid = player.getGameProfile().getId().toString();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.uuid = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.uuid);
	}

	// TODO /* ======================================== MessageScanFnish START ===================================== */

	// UUIDのgetter
	public String getUUID()
	{
		return this.uuid;
	}

}
