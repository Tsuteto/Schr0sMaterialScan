package schr0.materialscan.proxy;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy
{

	// 前・modの初期設定(クライアント側)
	public void clientPreInit(FMLPreInitializationEvent event)
	{
		// none
	}

	// modの初期設定(クライアント側)
	public void clientInit(FMLInitializationEvent event)
	{
		// none
	}

	// 後・modの初期設定(クライアント側)
	public void clientPostInit(FMLPostInitializationEvent event)
	{
		// none
	}

	// Minecraftのgetter
	public Minecraft getMinecraft()
	{
		return null;
	}

	// Log出力
	public void modInfo(String format, Object... data)
	{
		// none
	}

}