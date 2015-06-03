package schr0.materialscan.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityScanFX extends EntityFX
{

	public EntityScanFX(World par1World, double eX, double eY, double eZ, double velX, double velY, double velZ)
	{
		super(par1World, eX, eY, eZ, velX, velY, velZ);

		this.particleScale = 1.0F;
		this.motionX = velX;
		this.motionY = velY;
		this.motionZ = velZ;
		this.noClip = true;
	}

	// ???([2]を指定しておく)
	@Override
	public int getFXLayer()
	{
		return 2;
	}

	// EntityFXのonUpdate
	@Override
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.posY -= 0.01D;

		if ((this.particleScale -= 0.05D) < 0)
		{
			this.setDead();
		}
	}

}