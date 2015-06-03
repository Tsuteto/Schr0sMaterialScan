package schr0.materialscan.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import schr0.materialscan.init.MaterialScanPacket;
import schr0.materialscan.library.LibScan;
import schr0.materialscan.packet.scan.effect.MessageScanEffect;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventHookScanTarget
{

	@SubscribeEvent
	public void onScanTargetUpdateEvent(LivingEvent.LivingUpdateEvent event)
	{
		EntityLivingBase scanTarget = event.entityLiving;

		if (scanTarget.isEntityAlive() && LibScan.isCompleteScan(scanTarget))
		{
			if (!scanTarget.worldObj.isRemote)
			{
				MaterialScanPacket.DISPATCHER.sendToAll(new MessageScanEffect(scanTarget, 2));
			}
		}
	}

	@SubscribeEvent
	public void onScanTargetHurtEvent(LivingHurtEvent event)
	{
		EntityLivingBase scanTarget = event.entityLiving;

		if (LibScan.isCompleteScan(scanTarget))
		{
			event.ammount *= 2;
		}
	}

	@SubscribeEvent
	public void onScanTargetAttackEvent(LivingHurtEvent event)
	{
		DamageSource damageSource = event.source;

		if (damageSource.getEntity() instanceof EntityLivingBase && LibScan.isCompleteScan((EntityLivingBase) damageSource.getEntity()))
		{
			event.ammount /= 2;
		}
	}

	@SubscribeEvent
	public void onScanTargeDeathEvent(LivingDeathEvent event)
	{
		EntityLivingBase target = event.entityLiving;

		if (LibScan.isCompleteScan(target))
		{
			LibScan.clearScanCount(target);
		}
	}

}