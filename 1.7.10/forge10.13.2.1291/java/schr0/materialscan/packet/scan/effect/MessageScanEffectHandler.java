package schr0.materialscan.packet.scan.effect;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import schr0.materialscan.MaterialScanCore;
import schr0.materialscan.init.MaterialScanResource;
import schr0.materialscan.particles.EntityScanFX;
import schr0.materialscan.particles.MaterialScanParticle;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageScanEffectHandler implements IMessageHandler<MessageScanEffect, IMessage>
{

	@Override
	public IMessage onMessage(MessageScanEffect message, MessageContext ctx)
	{
		Minecraft mc = MaterialScanCore.proxy.getMinecraft();
		World world = mc.theWorld;
		Entity target = message.getEntityFromID(world);

		if (target != null)
		{
			onScanEffect(mc, world, target, message.getEffectCount());
		}

		return null;
	}

	// TODO /* ======================================== MessageScanEffectHandler START ===================================== */

	// スキャンエフェクトの処理
	private static void onScanEffect(Minecraft mc, World world, Entity target, int count)
	{
		for (int i = 0; i <= count; ++i)
		{
			Random random = new Random();
			double pX = target.posX + (random.nextDouble() - 0.5D) * (double) target.width;
			double pY = target.posY + (random.nextDouble() * (double) target.height);
			double pZ = target.posZ + (random.nextDouble() - 0.5D) * (double) target.width;
			double velX = (random.nextDouble() - 0.5D) * 2.0D;
			double velY = -random.nextDouble();
			double velZ = (random.nextDouble() - 0.5D) * 2.0D;

			EntityScanFX fxScan = new EntityScanFX(world, pX, pY, pZ, velX, velY, velZ);

			if (random.nextInt(2) == 0)
			{
				fxScan.setParticleIcon(MaterialScanParticle.getInstance().getIcon(MaterialScanResource.DOMAIN + "particle_scaned_0"));
			}
			else
			{
				fxScan.setParticleIcon(MaterialScanParticle.getInstance().getIcon(MaterialScanResource.DOMAIN + "particle_scaned_1"));
			}

			mc.effectRenderer.addEffect(fxScan);
		}
	}

}
