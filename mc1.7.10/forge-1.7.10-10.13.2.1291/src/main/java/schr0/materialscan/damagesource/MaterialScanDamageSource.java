package schr0.materialscan.damagesource;

import net.minecraft.util.DamageSource;
import schr0.materialscan.MaterialScanCore;

public class MaterialScanDamageSource
{

	// 『崩壊ダメージ』のDamageSource
	public static DamageSource decay = new DamageSource(MaterialScanCore.MODID + "." + "decay").setDamageBypassesArmor().setDamageAllowedInCreativeMode();

}
