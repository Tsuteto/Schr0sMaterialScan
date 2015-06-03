package schr0.materialscan.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class MaterialScanRecipe
{

	// Recipeの初期設定
	public void init()
	{
		// クラフトレシピの登録
		registerCraftRecipe();
	}

	// クラフトレシピの登録
	private static void registerCraftRecipe()
	{
		// 『矛盾の塊(Contradiction Mass)』= 1
		// 土 * 1
		// ネザーラック * 1
		// エンドストーン * 1
		// 黒曜石 * 1
		// ソウルサンド * 1
		// 焼石 * 1
		// ネザースター * 1
		for (int i = 0; i < 3; i++)
		{
			Object[] upperItems = new Object[] { Blocks.dirt, Blocks.netherrack, Blocks.end_stone, };
			Object[] lowerItems = new Object[] { Blocks.stone, Blocks.soul_sand, Blocks.obsidian, };
			Object upperItem1, upperItem2, upperItem3, lowerItem1, lowerItem2, lowerItem3;

			switch (i)
			{
				default:
					upperItem1 = upperItems[0];
					upperItem2 = upperItems[1];
					upperItem3 = upperItems[2];
					lowerItem1 = lowerItems[0];
					lowerItem2 = lowerItems[1];
					lowerItem3 = lowerItems[2];
					break;
				case 1:
					upperItem1 = upperItems[2];
					upperItem2 = upperItems[0];
					upperItem3 = upperItems[1];
					lowerItem1 = lowerItems[2];
					lowerItem2 = lowerItems[0];
					lowerItem3 = lowerItems[1];
					break;
				case 2:
					upperItem1 = upperItems[1];
					upperItem2 = upperItems[2];
					upperItem3 = upperItems[0];
					lowerItem1 = lowerItems[1];
					lowerItem2 = lowerItems[2];
					lowerItem3 = lowerItems[0];
					break;
			}

			Object[] contradictionMassRecipe = new Object[] { "ABC", " G ", "DEF", 'A', upperItem1, 'B', upperItem2, 'C', upperItem3, 'D', lowerItem1, 'E', lowerItem2, 'F', lowerItem3, 'G', Items.nether_star, };

			GameRegistry.addRecipe(new ShapedOreRecipe(MaterialScanItem.contradictionMass, contradictionMassRecipe));
		}

		// 『矛盾の塊(Contradiction Mass)』= 3
		// 『矛盾の塊(Contradiction Mass)』 * 2
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MaterialScanItem.contradictionMass, 3), new Object[] { MaterialScanItem.contradictionMass, MaterialScanItem.contradictionMass, }));

		// 『真理の結晶(Truth Crystal)』= 1
		// 『矛盾の塊(Contradiction Mass)』 * 1
		// 原木 * 1
		// 溶岩バケツ * 1
		// 土 * 1
		// 金ブロック * 1
		// 水バケツ*1
		for (int i = 0; i < 5; i++)
		{
			Object[] attributeItems = new Object[] { "logWood", Items.lava_bucket, Blocks.dirt, Blocks.gold_block, Items.water_bucket, };
			Object attributeItem1, attributeItem2, attributeItem3, attributeItem4, attributeItem5;

			switch (i)
			{
				default:
					attributeItem1 = attributeItems[0];
					attributeItem2 = attributeItems[1];
					attributeItem3 = attributeItems[2];
					attributeItem4 = attributeItems[3];
					attributeItem5 = attributeItems[4];
					break;
				case 1:
					attributeItem1 = attributeItems[4];
					attributeItem2 = attributeItems[0];
					attributeItem3 = attributeItems[1];
					attributeItem4 = attributeItems[2];
					attributeItem5 = attributeItems[3];
					break;
				case 2:
					attributeItem1 = attributeItems[3];
					attributeItem2 = attributeItems[4];
					attributeItem3 = attributeItems[0];
					attributeItem4 = attributeItems[1];
					attributeItem5 = attributeItems[2];
					break;
				case 3:
					attributeItem1 = attributeItems[2];
					attributeItem2 = attributeItems[3];
					attributeItem3 = attributeItems[4];
					attributeItem4 = attributeItems[0];
					attributeItem5 = attributeItems[1];
					break;
				case 4:
					attributeItem1 = attributeItems[1];
					attributeItem2 = attributeItems[2];
					attributeItem3 = attributeItems[3];
					attributeItem4 = attributeItems[4];
					attributeItem5 = attributeItems[0];
					break;
			}

			Object[] truthCrystalRecipe = new Object[] { " A ", "EFB", "D C", 'A', attributeItem1, 'B', attributeItem2, 'C', attributeItem3, 'D', attributeItem4, 'E', attributeItem5, 'F', MaterialScanItem.contradictionMass, };

			GameRegistry.addRecipe(new ShapedOreRecipe(MaterialScanItem.truthCrystal, truthCrystalRecipe));
		}

		// 『物質走査器(Material Scanner)』= 1
		// 『真理の結晶(Truth Crystal)』 * 1
		// 板ガラス * 4
		// 鉄インゴット * 1
		GameRegistry.addRecipe(new ShapedOreRecipe(MaterialScanItem.materialScanner, new Object[] { " B ", "BAB", "CB ", 'A', MaterialScanItem.truthCrystal, 'B', Blocks.glass_pane, 'C', Items.iron_ingot, }));

		// 『物質密封紙(Material Card)』= 8
		// 『真理の結晶(Truth Crystal)』 * 1
		// 紙 * 8
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MaterialScanItem.materialCard, 8), new Object[] { "BBB", "BAB", "BBB", 'A', MaterialScanItem.truthCrystal, 'B', Items.paper, }));

		// 『物質解析書(Material Book)』= 1
		// 『真理の結晶(Truth Crystal)』 * 1
		// 本 * 1
		GameRegistry.addRecipe(new ShapelessOreRecipe(MaterialScanItem.materialBook, new Object[] { MaterialScanItem.truthCrystal, Items.book, }));
	}
}
