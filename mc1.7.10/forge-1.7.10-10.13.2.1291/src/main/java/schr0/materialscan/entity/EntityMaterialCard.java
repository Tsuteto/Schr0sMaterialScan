package schr0.materialscan.entity;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import schr0.materialscan.MaterialScanCore;
import schr0.materialscan.damagesource.MaterialScanDamageSource;
import schr0.materialscan.init.MaterialScanItem;
import schr0.materialscan.item.ItemMaterialCard;
import schr0.materialscan.library.LibName;
import schr0.materialscan.library.LibScan;

public class EntityMaterialCard extends EntityThrowable
{

	// --- DataWatcherのID --- //
	// 『カード(ItemStack)』 : 10
	// 『投擲者(UUID)』 : 11
	private static final int ITEMSTACK_DATEVALUE = 10;
	private static final int THROWER_DATEVALUE = 11;

	// --- NBTのtag & ID --- //
	// ITEMSTACK_TAG : 10(NBTTagCompound)
	// UUID_TAG : 8(NBTTagString)
	private static final String ITEMSTACK_TAG = MaterialScanCore.MODID + "cardItemStack";
	private static final int ITEMSTACK_TAG_ID = 10;
	private static final String UUID_TAG = MaterialScanCore.MODID + "ownerUUID";
	private static final int UUID_TAG_ID = 8;

	public EntityMaterialCard(World world)
	{
		super(world);
	}

	public EntityMaterialCard(World world, double pX, double pY, double pZ)
	{
		super(world, pX, pY, pZ);
	}

	public EntityMaterialCard(World world, EntityPlayer player, ItemStack mobCard)
	{
		super(world, player);

		// 『投擲者(UUID)』のsetter
		this.setOwnerUUID(player.getUniqueID().toString());

		// 『カード(ItemStack)』のsetter
		this.setCardItemStack(mobCard);
	}

	// Entityの初期設定
	@Override
	protected void entityInit()
	{
		// 『カード(ItemStack)』 : 10
		this.getDataWatcher().addObjectByDataType(ITEMSTACK_DATEVALUE, 5);

		// 『投擲者(UUID)』 : 11
		this.getDataWatcher().addObject(THROWER_DATEVALUE, "");
	}

	// 何かに当たった場合の処理
	@Override
	protected void onImpact(MovingObjectPosition movingObjectPosition)
	{
		ItemStack stackCard = this.getCardItemStack();
		ItemMaterialCard itemCard = (ItemMaterialCard) this.getCardItemStack().getItem();

		double hitX = this.posX;
		double hitY = this.posY;
		double hitZ = this.posZ;

		if (movingObjectPosition.entityHit != null)
		{
			hitX = movingObjectPosition.entityHit.posX;
			hitY = movingObjectPosition.entityHit.posY;
			hitZ = movingObjectPosition.entityHit.posZ;
		}
		else
		{
			int bX = movingObjectPosition.blockX;
			int bY = movingObjectPosition.blockY;
			int bZ = movingObjectPosition.blockZ;

			hitX = (double) bX + 0.5D;
			hitY = (double) bY + 0.5D;
			hitZ = (double) bZ + 0.5D;

			switch (movingObjectPosition.sideHit)
			{
				case 0 :
					--hitY;
					break;
				case 1 :
					++hitY;
					break;
				case 2 :
					--hitZ;
					break;
				case 3 :
					++hitZ;
					break;
				case 4 :
					--hitX;
					break;
				case 5 :
					++hitX;
			}
		}

		if (stackCard.getItemDamage() == 1)
		{
			itemCard.spawnContentEntityInWorld(stackCard, this.worldObj, hitX, hitY, hitZ);

			stackCard = new ItemStack(MaterialScanItem.materialCard, 1, 0);
		}
		else
		{
			if (movingObjectPosition.entityHit instanceof EntityLivingBase)
			{
				EntityLivingBase livingBase = (EntityLivingBase) movingObjectPosition.entityHit;

				boolean canCapture = canCaptureTarget(livingBase);

				if (canCapture)
				{
					if (livingBase instanceof EntityPlayer)
					{
						stackCard = itemCard.setContentEntity(stackCard, getDoppelgangerZombie((EntityPlayer) livingBase));

						// 『崩壊ダメージ』 : floatの最大値 -> livingBase
						livingBase.attackEntityFrom(MaterialScanDamageSource.decay, Float.MAX_VALUE);
					}
					else
					{
						stackCard = itemCard.setContentEntity(stackCard, livingBase);

						if (!this.worldObj.isRemote)
						{
							livingBase.isDead = true;
						}
					}
				}

				if (this.getThrowPlayer() != null)
				{
					onCaptureInfo(this.getThrowPlayer(), livingBase, canCapture);
				}
			}
		}

		if (!this.worldObj.isRemote)
		{
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY + (double) 1.0, this.posZ, stackCard));

			this.setDead();
		}
	}

	// NBTの書き込み
	@Override
	public void writeEntityToNBT(NBTTagCompound nbtTagCompound)
	{
		nbtTagCompound.setTag(ITEMSTACK_TAG, this.getCardItemStack().writeToNBT(new NBTTagCompound()));

		if (this.getOwnerUUID() == null)
		{
			nbtTagCompound.setString(UUID_TAG, "");
		}
		else
		{
			nbtTagCompound.setString(UUID_TAG, this.getOwnerUUID());
		}
	}

	// NBTの読み込み
	@Override
	public void readEntityFromNBT(NBTTagCompound nbtTagCompound)
	{
		if (nbtTagCompound.hasKey(ITEMSTACK_TAG, ITEMSTACK_TAG_ID))
		{
			this.setCardItemStack(ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(ITEMSTACK_TAG)));
		}

		String uuid = "";

		if (nbtTagCompound.hasKey(UUID_TAG, UUID_TAG_ID))
		{
			uuid = nbtTagCompound.getString(UUID_TAG);
		}
		else
		{
			String owner = nbtTagCompound.getString("Owner");
			uuid = PreYggdrasilConverter.func_152719_a(owner);
		}

		if (0 < uuid.length())
		{
			this.setOwnerUUID(uuid);
		}
	}

	// TODO /* ======================================== EntityMaterialCard START ===================================== */

	// 『カード(ItemStack)』のgetter
	public ItemStack getCardItemStack()
	{
		ItemStack itemstack = this.getDataWatcher().getWatchableObjectItemStack(ITEMSTACK_DATEVALUE);

		if (itemstack != null && itemstack.getItem() instanceof ItemMaterialCard)
		{
			itemstack.stackSize = 1;
			return itemstack;
		}

		return new ItemStack(MaterialScanItem.materialCard, 1, 0);
	}

	// 『カード(ItemStack)』のsetter
	public void setCardItemStack(ItemStack stack)
	{
		this.getDataWatcher().updateObject(ITEMSTACK_DATEVALUE, stack);
		this.getDataWatcher().setObjectWatched(ITEMSTACK_DATEVALUE);
	}

	// 『投擲者(UUID)』のgetter
	public String getOwnerUUID()
	{
		return this.dataWatcher.getWatchableObjectString(THROWER_DATEVALUE);
	}

	// 『投擲者(UUID)』のsetter
	public void setOwnerUUID(String uuid)
	{
		this.dataWatcher.updateObject(THROWER_DATEVALUE, uuid);
	}

	// 『投擲者(EntityPlayer)』のgetter
	public EntityPlayer getThrowPlayer()
	{
		try
		{
			UUID uuid = UUID.fromString(this.getOwnerUUID());
			return uuid == null ? null : this.worldObj.func_152378_a(uuid);
		}
		catch (IllegalArgumentException illegalargumentexception)
		{
			return null;
		}
	}

	// 捕獲の判定
	private static boolean canCaptureTarget(EntityLivingBase target)
	{
		return LibScan.isCompleteScan(target);
	}

	// 捕獲の通知
	private static void onCaptureInfo(EntityPlayer player, EntityLivingBase target, boolean canCapture)
	{
		World world = player.worldObj;

		IChatComponent targetName = LibName.getTrueName(target);
		targetName.getChatStyle().setColor(EnumChatFormatting.GREEN);

		IChatComponent text = new ChatComponentTranslation("entity.materialCard.captureFailed", new Object[]{targetName});
		text.getChatStyle().setColor(EnumChatFormatting.RED);

		String sound = "random.fizz";

		if (canCapture)
		{
			text = new ChatComponentTranslation("entity.materialCard.captureSuccess", new Object[]{targetName});
			text.getChatStyle().setColor(EnumChatFormatting.GOLD);

			sound = "random.levelup";
		}

		if (!world.isRemote)
		{
			player.addChatComponentMessage(text);
		}

		world.playSoundAtEntity(player, sound, 1.0F, 1.0F);
	}

	// 『ドッペルゲンガーゾンビ(EntityZombie)』のgetter
	private static EntityZombie getDoppelgangerZombie(EntityPlayer player)
	{
		EntityZombie zombie = new EntityZombie(player.worldObj);

		zombie.setCustomNameTag(player.getCommandSenderName());

		zombie.setAlwaysRenderNameTag(true);

		zombie.func_110163_bv();

		for (int slot = 0; slot < 5; slot++)
		{
			ItemStack slotStack = player.getEquipmentInSlot(slot);

			if (slotStack != null)
			{
				zombie.setCurrentItemOrArmor(slot, slotStack);
				player.setCurrentItemOrArmor(slot, null);

				zombie.setEquipmentDropChance(slot, 2.0F);
			}
		}

		return zombie;
	}

}