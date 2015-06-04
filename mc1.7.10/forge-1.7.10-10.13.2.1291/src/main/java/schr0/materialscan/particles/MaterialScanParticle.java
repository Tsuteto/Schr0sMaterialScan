package schr0.materialscan.particles;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import schr0.materialscan.init.MaterialScanResource;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MaterialScanParticle
{

	// IIconの配列
	private IIcon iicons[];

	// IIcon名の配列
	private static final String[] IICON_NAMES = {MaterialScanResource.DOMAIN + "particle_scaned_0", MaterialScanResource.DOMAIN + "particle_scaned_1",};

	// Instance
	private static MaterialScanParticle instance;

	// Instanceのgetter
	public static MaterialScanParticle getInstance()
	{
		if (instance == null)
		{
			instance = new MaterialScanParticle();
		}

		return instance;
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void handleTextureRemap(TextureStitchEvent.Pre event)
	{
		if (event.map.getTextureType() == 1)
		{
			this.getInstance().registerIcons(event.map);
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		iicons = new IIcon[IICON_NAMES.length];

		for (int i = 0; i < iicons.length; ++i)
		{
			iicons[i] = par1IconRegister.registerIcon(IICON_NAMES[i]);
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(String iconName)
	{
		for (int i = 0; i < IICON_NAMES.length; ++i)
		{
			if (iconName.equalsIgnoreCase(IICON_NAMES[i]))
			{
				return iicons[i];
			}
		}

		return null;
	}

}
