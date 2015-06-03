package schr0.materialscan.library;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import schr0.materialscan.MaterialScanCore;

public class LibScan
{

	// --- NBTのtag & ID --- //
	// SCANCOUNT_TAG : 3(NBTTagInt)
	public static final String SCANCOUNT_TAG = MaterialScanCore.MODID + "scanCount";
	public static final int SCANCOUNT_TAG_ID = 3;

	// スキャンが完了の判定
	public static boolean isCompleteScan(EntityLivingBase target)
	{
		NBTTagCompound targetCustomNBT = target.getEntityData();

		if (targetCustomNBT.hasKey(SCANCOUNT_TAG, SCANCOUNT_TAG_ID))
		{
			return getMaxScanCount(target) <= getScanCount(target);
		}

		return false;
	}

	// 最大スキャン回数のgetter
	public static int getMaxScanCount(EntityLivingBase target)
	{
		// TODO
		int max = (int) target.getMaxHealth() / 10;

		return 0 < max ? (max) : (1);
	}

	// スキャン回数のgetter
	public static int getScanCount(EntityLivingBase target)
	{
		NBTTagCompound targetCustomNBT = target.getEntityData();

		if (targetCustomNBT.hasKey(SCANCOUNT_TAG, SCANCOUNT_TAG_ID))
		{
			return targetCustomNBT.getInteger(SCANCOUNT_TAG);
		}

		return 0;
	}

	// スキャン回数のsetter
	public static void setScanCount(EntityLivingBase target, int count)
	{
		target.getEntityData().setInteger(SCANCOUNT_TAG, count);
	}

	// スキャン回数の初期化
	public static void clearScanCount(EntityLivingBase target)
	{
		NBTTagCompound targetCustomNBT = target.getEntityData();

		if (targetCustomNBT.hasKey(SCANCOUNT_TAG, SCANCOUNT_TAG_ID))
		{
			targetCustomNBT.removeTag(SCANCOUNT_TAG);
		}
	}

}