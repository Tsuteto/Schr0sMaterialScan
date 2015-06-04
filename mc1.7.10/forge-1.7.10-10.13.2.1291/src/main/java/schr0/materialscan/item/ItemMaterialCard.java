package schr0.materialscan.item;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import schr0.materialscan.MaterialScanCore;
import schr0.materialscan.creativetab.MaterialScanCreativeTabs;
import schr0.materialscan.entity.EntityMaterialCard;
import schr0.materialscan.init.MaterialScanResource;
import schr0.materialscan.library.LibName;
import schr0.materialscan.library.LibScan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMaterialCard extends Item
{

	// IIconの配列
	private IIcon[] iiconList;

	// 内部名の配列
	private static final String[] NAMES = new String[]{"empty", "full",};

	// テクスチャ名の配列
	private static final String[] TEXTURES = new String[]{"material_card_empty", "material_card_full",};

	// --- NBTのtag & ID --- //
	// ENTITY_LISTTAG : 10(NBTTagCompound), 9(NBTTagCompound)
	private static final String ENTITY_LISTTAG = MaterialScanCore.MODID + "cardEntity";
	private static final int ENTITY_LISTTAG_ID = 9;
	private static final int ENTITY_TAG_ID = 10;

	public ItemMaterialCard(String name)
	{
		this.setUnlocalizedName(name);

		this.setCreativeTab(MaterialScanCreativeTabs.ITEM_TAB);

		this.setHasSubtypes(true);

		this.setMaxDamage(0);

		this.setMaxStackSize(16);
	}

	// ダメージ値からのIIcon
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		return this.iiconList[MathHelper.clamp_int(damage, 0, NAMES.length)];
	}

	// IIconの登録
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		this.iiconList = new IIcon[TEXTURES.length];

		for (int i = 0; i < TEXTURES.length; ++i)
		{
			this.iiconList[i] = iconRegister.registerIcon(MaterialScanResource.DOMAIN + TEXTURES[i]);
		}
	}

	// CreativeTabの追加
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
	{
		for (int i = 0; i < NAMES.length; ++i)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}

	// エフェクトを持つ判定
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass)
	{
		if (stack.getItemDamage() == 1)
		{
			return true;
		}

		return false;
	}

	// ツールチップの追加
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		IChatComponent iChatEntityNoName = LibName.getNoName(player);
		String entityNoName = iChatEntityNoName.getUnformattedTextForChat();

		IChatComponent iChatEntityName = new ChatComponentTranslation("item.materialCard.entityName", new Object[0]);
		String entityName = EnumChatFormatting.AQUA + iChatEntityName.getUnformattedTextForChat();

		IChatComponent iChatEntityNickname = new ChatComponentTranslation("item.materialCard.entityNickname", new Object[0]);
		String entityNickname = EnumChatFormatting.DARK_PURPLE + iChatEntityNickname.getUnformattedTextForChat();

		if (stack.getItemDamage() == 1)
		{
			IChatComponent iChatUseFull = new ChatComponentTranslation("item.materialCard.use.full", new Object[0]);
			String useFull = EnumChatFormatting.DARK_RED + iChatUseFull.getUnformattedTextForChat();

			IChatComponent iChatManualFull = new ChatComponentTranslation("item.materialCard.manual.full", new Object[0]);
			String manualFull = EnumChatFormatting.GREEN + iChatManualFull.getUnformattedTextForChat();

			list.add(useFull);
			list.add(manualFull);
			list.add("");

			if (this.hasContent(stack))
			{
				EntityLivingBase livingBase = this.getContentEntity(stack, player.worldObj);

				String name = LibName.getTrueName(livingBase).getUnformattedTextForChat();
				String nickname = LibName.getNickname(livingBase).getUnformattedTextForChat();

				list.add(entityName);
				list.add(" : " + name);
				list.add(entityNickname);
				list.add(" : " + EnumChatFormatting.ITALIC + nickname);
			}
			else
			{
				list.add(entityName);
				list.add(" : " + "???");
				list.add(entityNickname);
				list.add(" : " + EnumChatFormatting.ITALIC + "Schr0");
			}
		}
		else
		{
			IChatComponent iChatUseFull = new ChatComponentTranslation("item.materialCard.use.empty", new Object[0]);
			String useEmpty = EnumChatFormatting.DARK_RED + iChatUseFull.getUnformattedTextForChat();

			IChatComponent iChatManualEmpty = new ChatComponentTranslation("item.materialCard.manual.empty", new Object[0]);
			String manualEmpty = EnumChatFormatting.GREEN + iChatManualEmpty.getUnformattedTextForChat();

			list.add(useEmpty);
			list.add(manualEmpty);
			list.add("");

			list.add(entityName);
			list.add(" : " + entityNoName);
			list.add(entityNickname);
			list.add(" : " + EnumChatFormatting.ITALIC + entityNoName);
		}
	}

	// Itemの内部名
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int damage = MathHelper.clamp_int(stack.getItemDamage(), 0, NAMES.length);
		return super.getUnlocalizedName() + "." + NAMES[damage];
	}

	// 右クリックを押した際のItemStack
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		boolean canThrow = (stack.getItemDamage() == 0) || (stack.getItemDamage() == 1 && player.isSneaking());

		if (canThrow)
		{
			if (!world.isRemote)
			{
				world.spawnEntityInWorld(new EntityMaterialCard(world, player, stack));
			}

			if (!player.capabilities.isCreativeMode)
			{
				--stack.stackSize;
			}

			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		}

		return stack;
	}

	// TODO /* ======================================== ItemMaterialCard START ===================================== */

	// 中身Entityの判定
	public boolean hasContent(ItemStack stack)
	{
		if (stack.getItemDamage() == 1)
		{
			return (stack.getTagCompound() != null && stack.getTagCompound().hasKey(ENTITY_LISTTAG, ENTITY_LISTTAG_ID));
		}

		return false;
	}

	// 中身Entityのgetter
	public EntityLivingBase getContentEntity(ItemStack stack, World world)
	{
		if (this.hasContent(stack))
		{
			NBTTagCompound nbtStack = (NBTTagCompound) stack.getTagCompound().copy();
			NBTTagList nbtListStack = nbtStack.getTagList(ENTITY_LISTTAG, ENTITY_TAG_ID);

			for (int i = 0; i < nbtListStack.tagCount(); i++)
			{
				Entity entity = EntityList.createEntityFromNBT(nbtListStack.getCompoundTagAt(i), world);

				if (entity instanceof EntityLivingBase)
				{
					return (EntityLivingBase) entity;
				}
			}
		}

		return null;
	}

	// 中身Entityのsetter
	public ItemStack setContentEntity(ItemStack stack, EntityLivingBase livingBase)
	{
		LibScan.clearScanCount(livingBase);

		if (livingBase instanceof EntityLiving)
		{
			EntityLiving living = (EntityLiving) livingBase;

			living.clearLeashed(true, true);
		}

		if (!livingBase.worldObj.isRemote)
		{
			if (livingBase.riddenByEntity != null)
			{
				livingBase.riddenByEntity.mountEntity((Entity) null);
			}

			if (livingBase.ridingEntity != null)
			{
				livingBase.mountEntity((Entity) null);
			}
		}

		stack.setItemDamage(1);

		NBTTagCompound nbtStack = new NBTTagCompound();
		NBTTagList nbtListStack = new NBTTagList();

		NBTTagCompound livingBaseNBT = new NBTTagCompound();
		livingBase.writeMountToNBT(livingBaseNBT);
		nbtListStack.appendTag(livingBaseNBT);

		nbtStack.setTag(ENTITY_LISTTAG, nbtListStack);
		stack.setTagCompound(nbtStack);

		return stack;
	}

	// 中身Entityのスポーン処理
	public void spawnContentEntityInWorld(ItemStack stack, World world, double pX, double pY, double pZ)
	{
		EntityLivingBase livingBase;

		if (this.hasContent(stack))
		{
			livingBase = this.getContentEntity(stack, world);
		}
		else
		{
			livingBase = (EntityLivingBase) this.getSchr0Entity(world);
		}

		if (stack.hasDisplayName() && livingBase instanceof EntityLiving)
		{
			((EntityLiving) livingBase).setCustomNameTag(stack.getDisplayName());
			((EntityLiving) livingBase).func_110163_bv();
		}

		if (!world.isRemote)
		{
			livingBase.setPositionAndUpdate(pX, pY, pZ);

			world.spawnEntityInWorld(livingBase);
		}

		if (livingBase instanceof EntityLiving)
		{
			((EntityLiving) livingBase).playLivingSound();
		}
	}

	// 『Schr0』のgetter
	private static EntityLiving getSchr0Entity(World world)
	{
		EntityLiving living = new EntityPig(world);

		Iterator iterator = EntityList.entityEggs.values().iterator();
		while (iterator.hasNext())
		{
			int spawnedID = ((EntityList.EntityEggInfo) iterator.next()).spawnedID;

			if (EntityList.entityEggs.containsKey(Integer.valueOf(spawnedID)) && EntityList.createEntityByID(spawnedID, world) instanceof EntityLiving)
			{
				if (world.rand.nextInt(10) == 0)
				{
					living = (EntityLiving) EntityList.createEntityByID(spawnedID, world);
					living.rotationYawHead = living.rotationYaw;
					living.renderYawOffset = living.rotationYaw;
					living.onSpawnWithEgg((IEntityLivingData) null);

					living.func_110163_bv();

					living.setCustomNameTag("Schr0");

					living.setAlwaysRenderNameTag(true);

					for (int id = 0; id < Potion.potionTypes.length; id++)
					{
						if (Potion.potionTypes[id] != null && id != Potion.invisibility.id && !Potion.potionTypes[id].isBadEffect() && !Potion.potionTypes[id].isInstant())
						{
							living.addPotionEffect(new PotionEffect(id, Integer.MAX_VALUE, 3));
						}
					}

					return living;
				}
			}
		}

		return living;
	}

}