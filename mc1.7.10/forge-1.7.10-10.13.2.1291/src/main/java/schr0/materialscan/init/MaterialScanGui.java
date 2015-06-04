package schr0.materialscan.init;

import schr0.materialscan.MaterialScanCore;
import schr0.materialscan.gui.MaterialScanGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class MaterialScanGui
{

	// GuiのID
	public static final int MATERIAL_BOOK_ID = 0;

	// Guiの初期設定
	public void init()
	{
		// GuiHandlerの登録
		NetworkRegistry.INSTANCE.registerGuiHandler(MaterialScanCore.instance, new MaterialScanGuiHandler());
	}

}