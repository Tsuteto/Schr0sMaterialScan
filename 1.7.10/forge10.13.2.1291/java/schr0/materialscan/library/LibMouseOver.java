package schr0.materialscan.library;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import schr0.materialscan.MaterialScanCore;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LibMouseOver
{

	// Minecraft
	private static Minecraft mc = MaterialScanCore.proxy.getMinecraft();

	// renderPartialTicks(※リフレクション)
	private static float renderPartialTicks = ((Timer) ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, mc, 16)).renderPartialTicks;

	// リーチ指定可能なマウスオーバー
	public static MovingObjectPosition getReachMouseOver(EntityLivingBase viewingEntity, double reach)
	{
		MovingObjectPosition mop = null;

		if (viewingEntity != null)
		{
			if (viewingEntity.worldObj != null)
			{
				mop = viewingEntity.rayTrace(reach, renderPartialTicks);
				Vec3 viewPosition = viewingEntity.getPosition(renderPartialTicks);
				double d1 = 0;

				if (mop != null)
				{
					d1 = mop.hitVec.distanceTo(viewPosition);
				}

				Vec3 lookVector = viewingEntity.getLook(renderPartialTicks);
				Vec3 reachVector = viewPosition.addVector(lookVector.xCoord * reach, lookVector.yCoord * reach, lookVector.zCoord * reach);
				Vec3 vec33 = null;
				float f1 = 1.0F;
				@SuppressWarnings("unchecked")
				List<Entity> list = viewingEntity.worldObj.getEntitiesWithinAABBExcludingEntity(viewingEntity, viewingEntity.boundingBox.addCoord(lookVector.xCoord * reach, lookVector.yCoord * reach, lookVector.zCoord * reach).expand(f1, f1, f1));
				double d2 = d1;
				Entity pointedEntity = null;

				for (Entity entity : list)
				{
					if (entity.canBeCollidedWith())
					{
						float collisionSize = entity.getCollisionBorderSize();
						AxisAlignedBB axisalignedbb = entity.boundingBox.expand(collisionSize, collisionSize, collisionSize);
						MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(viewPosition, reachVector);

						if (axisalignedbb.isVecInside(viewPosition))
						{
							if (0.0D < d2 || d2 == 0.0D)
							{
								pointedEntity = entity;
								vec33 = movingobjectposition == null ? viewPosition : movingobjectposition.hitVec;
								d2 = 0.0D;
							}
						}
						else if (movingobjectposition != null)
						{
							double d3 = viewPosition.distanceTo(movingobjectposition.hitVec);

							if (d3 < d2 || d2 == 0.0D)
							{
								if (entity == viewingEntity.ridingEntity && !entity.canRiderInteract())
								{
									if (d2 == 0.0D)
									{
										pointedEntity = entity;
										vec33 = movingobjectposition.hitVec;
									}
								}
								else
								{
									pointedEntity = entity;
									vec33 = movingobjectposition.hitVec;
									d2 = d3;
								}
							}
						}
					}
				}

				if (pointedEntity != null && (d2 < d1 || mop == null))
				{
					mop = new MovingObjectPosition(pointedEntity, vec33);
				}
			}
		}

		return mop;
	}

}
