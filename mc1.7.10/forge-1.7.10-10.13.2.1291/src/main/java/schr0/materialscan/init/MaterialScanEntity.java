package schr0.materialscan.init;

import schr0.materialscan.MaterialScanCore;
import schr0.materialscan.entity.EntityMaterialCard;
import cpw.mods.fml.common.registry.EntityRegistry;

public class MaterialScanEntity
{

	// EntityのID
	public static final int MATERIAL_CARD_ID = 0;

	// Entityの初期設定
	public void init()
	{
		// Entityの登録
		registerModEntity();
	}

	// Entityの登録
	private static void registerModEntity()
	{
		// EntityMaterialCard
		EntityRegistry.registerModEntity(EntityMaterialCard.class, "materialCard", MATERIAL_CARD_ID, MaterialScanCore.instance, 250, 1, true);
	}

}
