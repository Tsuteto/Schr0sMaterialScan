package schr0.materialscan.proxy;

import net.minecraft.client.Minecraft;
import schr0.materialscan.init.MaterialScanEventHook;
import schr0.materialscan.init.MaterialScanPacket;
import schr0.materialscan.init.MaterialScanResource;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends ServerProxy
{

	// 前・modの初期設定(クライアント側)
	@Override
	public void clientPreInit(FMLPreInitializationEvent event)
	{
		// none
	}

	// modの初期設定(クライアント側)
	@Override
	public void clientInit(FMLInitializationEvent event)
	{
		// Resourceの初期設定
		(new MaterialScanResource()).init();

		// Packetの初期設定(クライアント側)
		(new MaterialScanPacket()).initClient();

		// Eventの初期設定(クライアント側)
		(new MaterialScanEventHook()).initClient();
	}

	// 後・modの初期設定(クライアント側)
	@Override
	public void clientPostInit(FMLPostInitializationEvent event)
	{
		// none
	}

	// Minecraftのgetter
	@Override
	public Minecraft getMinecraft()
	{
		return FMLClientHandler.instance().getClient();
	}

	// Log出力
	@Override
	public void modInfo(String format, Object... data)
	{
		FMLLog.info(format, data);
	}

}