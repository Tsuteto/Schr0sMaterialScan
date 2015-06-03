package schr0.materialscan.init;

import net.minecraft.item.Item;
import schr0.materialscan.MaterialScanCore;
import schr0.materialscan.item.ItemContradictionMass;
import schr0.materialscan.item.ItemMaterialBook;
import schr0.materialscan.item.ItemMaterialCard;
import schr0.materialscan.item.ItemMaterialScanner;
import schr0.materialscan.item.ItemTruthCrystal;
import cpw.mods.fml.common.registry.GameRegistry;

public class MaterialScanItem
{

	// Item
	public static Item contradictionMass;
	public static Item truthCrystal;
	public static Item materialScanner;
	public static Item materialCard;
	public static Item materialBook;

	// Itemの内部名
	public static final String CONTRADICT_MASS_NAME = "contradictionMass";
	public static final String TRUTH_CRYSTAL_NAME = "truthCrystal";
	public static final String MATERIAL_SCANNER_NAME = "materialScanner";
	public static final String MATERIAL_CARD_NAME = "materialCard";
	public static final String MATERIAL_BOOK_NAME = "materialBook";

	// Itemの初期設定
	public void init()
	{
		// Itemの登録
		registerItem();
	}

	// Itemの登録
	private static void registerItem()
	{
		// 『矛盾の塊(Contradiction Mass)』
		contradictionMass = new ItemContradictionMass(CONTRADICT_MASS_NAME);

		// 『真理の結晶(Truth Crystal)』
		truthCrystal = new ItemTruthCrystal(TRUTH_CRYSTAL_NAME);

		// 『物質走査器(Material Scanner)』
		materialScanner = new ItemMaterialScanner(MATERIAL_SCANNER_NAME);

		// 『物質密封紙(Material Card)』
		materialCard = new ItemMaterialCard(MATERIAL_CARD_NAME);

		// 『物質解析書(Material Book)』
		materialBook = new ItemMaterialBook(MATERIAL_BOOK_NAME);

		// Itemの登録
		GameRegistry.registerItem(contradictionMass, CONTRADICT_MASS_NAME, MaterialScanCore.MODID);
		GameRegistry.registerItem(truthCrystal, TRUTH_CRYSTAL_NAME, MaterialScanCore.MODID);
		GameRegistry.registerItem(materialScanner, MATERIAL_SCANNER_NAME, MaterialScanCore.MODID);
		GameRegistry.registerItem(materialCard, MATERIAL_CARD_NAME, MaterialScanCore.MODID);
		GameRegistry.registerItem(materialBook, MATERIAL_BOOK_NAME, MaterialScanCore.MODID);
	}

}
