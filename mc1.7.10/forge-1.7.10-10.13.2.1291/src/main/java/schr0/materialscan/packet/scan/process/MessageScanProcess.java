package schr0.materialscan.packet.scan.process;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageScanProcess implements IMessage
{

	// UUID
	private String uuid;

	// EntityのID
	private int entityID;

	public MessageScanProcess()
	{
		// none
	}

	public MessageScanProcess(EntityPlayer player, Entity entity)
	{
		this.uuid = player.getGameProfile().getId().toString();
		this.entityID = entity.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.uuid = ByteBufUtils.readUTF8String(buf);
		this.entityID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.uuid);
		buf.writeInt(this.entityID);
	}

	// TODO /* ======================================== MessageScanProcess START ===================================== */

	// UUIDのgetter
	public String getUUID()
	{
		return this.uuid;
	}

	// Entityのgetter
	public Entity getEntityFromID(World world)
	{
		return world.getEntityByID(this.entityID);
	}

}
