package schr0.materialscan.packet.scan.effect;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageScanEffect implements IMessage
{

	// EntityのID
	private int entityID;

	// エフェククト回数
	private int effectCount;

	public MessageScanEffect()
	{
		// none
	}

	public MessageScanEffect(Entity entity, int count)
	{
		this.entityID = entity.getEntityId();
		this.effectCount = count;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityID = buf.readInt();
		this.effectCount = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityID);
		buf.writeInt(this.effectCount);
	}

	// TODO /* ======================================== MessageScanEffect START ===================================== */

	// Entityのgetter
	public Entity getEntityFromID(World world)
	{
		return world.getEntityByID(this.entityID);
	}

	// エフェクト回数のgetter
	public int getEffectCount()
	{
		return this.effectCount;
	}

}
