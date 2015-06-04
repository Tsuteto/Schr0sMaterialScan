package schr0.materialscan.init;

import schr0.materialscan.entity.EntityMaterialCard;
import schr0.materialscan.entity.RenderMaterialCard;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MaterialScanResource
{

	// DOMAIN
	public static final String DOMAIN = "schr0smaterialscan:";
	public static final String DOMAIN_GUI = DOMAIN + "textures/gui/";

	// Resourceの初期設定
	public void init()
	{
		// EntityのRenderer登録
		registerEntityRenderer();
	}

	// EntityのRenderer登録
	private static void registerEntityRenderer()
	{
		// RenderMaterialCard
		RenderingRegistry.registerEntityRenderingHandler(EntityMaterialCard.class, new RenderMaterialCard());
	}

}
