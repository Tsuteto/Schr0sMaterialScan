package schr0.materialscan.init;

import schr0.materialscan.MaterialScanCore;
import schr0.materialscan.packet.scan.aciton.MessageScanAction;
import schr0.materialscan.packet.scan.aciton.MessageScanActionHandler;
import schr0.materialscan.packet.scan.effect.MessageScanEffect;
import schr0.materialscan.packet.scan.effect.MessageScanEffectHandler;
import schr0.materialscan.packet.scan.finish.MessageScanFnish;
import schr0.materialscan.packet.scan.finish.MessageScanFnishHandler;
import schr0.materialscan.packet.scan.process.MessageScanProcess;
import schr0.materialscan.packet.scan.process.MessageScanProcessHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MaterialScanPacket
{

	// Packetのチャンネル
	public static final SimpleNetworkWrapper DISPATCHER = NetworkRegistry.INSTANCE.newSimpleChannel(MaterialScanCore.MODID);

	// PacketのID
	public static final int SERVER_SCAN_TARGET_ID = 0;
	public static final int SERVER_SCAN_FINISH_ID = 1;
	public static final int CLIENT_SCAN_ACTION_ID = 2;
	public static final int CLIENT_SCAN_EFFECT_ID = 3;

	// Packetの初期設定
	public void init()
	{
		// MessageScanProcess
		DISPATCHER.registerMessage(MessageScanProcessHandler.class, MessageScanProcess.class, SERVER_SCAN_TARGET_ID, Side.SERVER);

		// MessageScanFnish
		DISPATCHER.registerMessage(MessageScanFnishHandler.class, MessageScanFnish.class, SERVER_SCAN_FINISH_ID, Side.SERVER);
	}

	// Packetの初期設定(クライアント側)
	@SideOnly(Side.CLIENT)
	public void initClient()
	{
		// MessageScanAction
		DISPATCHER.registerMessage(MessageScanActionHandler.class, MessageScanAction.class, CLIENT_SCAN_ACTION_ID, Side.CLIENT);

		// MessageScanEffect
		DISPATCHER.registerMessage(MessageScanEffectHandler.class, MessageScanEffect.class, CLIENT_SCAN_EFFECT_ID, Side.CLIENT);
	}

}
