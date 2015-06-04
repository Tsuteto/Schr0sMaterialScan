package schr0.materialscan.packet.scan.aciton;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageScanAction implements IMessage
{

	// UUID
	private String uuid;

	// アクション判定用のID
	private int actionID;

	public MessageScanAction()
	{
		// none
	}

	public MessageScanAction(EntityPlayer player, boolean action)
	{
		this.uuid = player.getGameProfile().getId().toString();
		this.actionID = action ? (1) : (0);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.uuid = ByteBufUtils.readUTF8String(buf);
		this.actionID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.uuid);
		buf.writeInt(this.actionID);
	}

	// TODO /* ======================================== MessageScanAction START ===================================== */

	// UUIDのgetter
	public String getUUID()
	{
		return this.uuid;
	}

	// アクションの判定
	public boolean isAction()
	{
		return this.actionID == 1;
	}

}
