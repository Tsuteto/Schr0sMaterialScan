package schr0.materialscan.init;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import schr0.materialscan.MaterialScanCore;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class MaterialScanConfig
{

	// Configの初期設定
	public void init()
	{
		// Configの登録
		registerConfiguration(new Configuration(new File(Loader.instance().getConfigDir(), "schr0/" + MaterialScanCore.MODID + ".cfg")));
	}

	// Configの登録
	private static void registerConfiguration(Configuration cfg)
	{
		try
		{
			cfg.load();

			// none
		}
		catch (Exception e)
		{
			FMLLog.severe(MaterialScanCore.MODID + " is Configuration Error");
		}
		finally
		{
			cfg.save();
		}
	}

}
