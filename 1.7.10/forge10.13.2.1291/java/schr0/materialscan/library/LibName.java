package schr0.materialscan.library;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

public class LibName
{

	// 真名のgetter
	public static IChatComponent getTrueName(EntityLivingBase livingBase)
	{
		String name = livingBase.getCommandSenderName();

		try
		{
			if (livingBase instanceof EntityLiving && ((EntityLiving) livingBase).hasCustomNameTag())
			{
				NBTTagCompound livingBaseNBT = new NBTTagCompound();
				livingBase.writeMountToNBT(livingBaseNBT);
				EntityLiving copyLiving = (EntityLiving) EntityList.createEntityFromNBT(livingBaseNBT, livingBase.worldObj);

				copyLiving.setCustomNameTag("");
				name = copyLiving.getCommandSenderName();
			}
		}
		catch (Exception exception)
		{
			String entityString = EntityList.getEntityString(livingBase);

			if (entityString == null)
			{
				entityString = "generic";
			}

			return new ChatComponentText(StatCollector.translateToLocal("entity." + entityString + ".name"));
		}

		return new ChatComponentText(StatCollector.translateToLocal(name));
	}

	// 渾名のgetter
	public static IChatComponent getNickname(EntityLivingBase livingBase)
	{
		if (livingBase instanceof EntityLiving && ((EntityLiving) livingBase).hasCustomNameTag())
		{
			return new ChatComponentText(((EntityLiving) livingBase).getCustomNameTag());
		}

		return getNoName(livingBase);
	}

	// 名無しのgetter
	public static IChatComponent getNoName(EntityLivingBase livingBase)
	{
		return new ChatComponentTranslation("item.materialCard.entityNoName", new Object[0]);
	}

}
