package schr0.materialscan;

import schr0.materialscan.init.MaterialScanConfig;
import schr0.materialscan.init.MaterialScanEntity;
import schr0.materialscan.init.MaterialScanEventHook;
import schr0.materialscan.init.MaterialScanGui;
import schr0.materialscan.init.MaterialScanItem;
import schr0.materialscan.init.MaterialScanPacket;
import schr0.materialscan.init.MaterialScanRecipe;
import schr0.materialscan.proxy.ServerProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MaterialScanCore.MODID, name = MaterialScanCore.NAME, version = MaterialScanCore.VERSION, dependencies = MaterialScanCore.DEPENDENCIES)
public class MaterialScanCore
{

	// modのID & modの名称 & modのVer & modの依存関係
	public static final String MODID = "Schr0sMaterialScan";
	public static final String NAME = "Schr0's Material Scan";
	public static final String VERSION = "ver0.4-mc1.7.10";
	public static final String DEPENDENCIES = "required-after:Forge@[10.13.2.1291,)";

	// modのInstance
	@Mod.Instance(MaterialScanCore.MODID)
	public static MaterialScanCore instance;

	// modのProxy
	@SidedProxy(clientSide = "schr0.materialscan.proxy.ClientProxy", serverSide = "schr0.materialscan.proxy.ServerProxy")
	public static ServerProxy proxy;

	// 前・modの初期設定
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// Configの初期設定
		(new MaterialScanConfig()).init();

		// Itemの初期設定
		(new MaterialScanItem()).init();

		// 前・modの初期設定(クライアント側)
		this.proxy.clientPreInit(event);
	}
	// modの初期設定
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		// Recipeの初期設定
		(new MaterialScanRecipe()).init();

		// Entityの初期設定
		(new MaterialScanEntity()).init();

		// Guiの初期設定
		(new MaterialScanGui()).init();

		// Packetの初期設定
		(new MaterialScanPacket()).init();

		// Eventの初期設定
		(new MaterialScanEventHook()).init();

		// modの初期設定(クライアント側)
		this.proxy.clientInit(event);
	}

	// 後・modの初期設定
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		// 後・modの初期設定(クライアント側)
		this.proxy.clientPostInit(event);
	}

}
