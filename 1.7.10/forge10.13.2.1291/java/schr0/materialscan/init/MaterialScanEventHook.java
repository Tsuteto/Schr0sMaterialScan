package schr0.materialscan.init;

import net.minecraftforge.common.MinecraftForge;
import schr0.materialscan.event.EventHookScanTarget;
import schr0.materialscan.particles.MaterialScanParticle;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MaterialScanEventHook
{

	// Eventの初期設定
	public void init()
	{
		// Eventの登録
		registerEvent();
	}

	// Eventの登録
	private static void registerEvent()
	{
		// EventHookScanTarget
		MinecraftForge.EVENT_BUS.register(new EventHookScanTarget());
	}

	// Eventの初期設定(クライアント側)
	@SideOnly(Side.CLIENT)
	public void initClient()
	{
		// Eventの登録(クライアント側)
		registerEventClient();
	}

	// Eventの登録(クライアント側)
	@SideOnly(Side.CLIENT)
	private static void registerEventClient()
	{
		// MaterialScanParticle
		MinecraftForge.EVENT_BUS.register(new MaterialScanParticle().getInstance());
	}

}
