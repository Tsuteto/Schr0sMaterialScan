package schr0.materialscan.gui;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import schr0.materialscan.item.ItemMaterialCard;

public class ContainerMaterialBook extends Container
{

	// Containerを開いているEntityPlayer
	private EntityPlayer containerPlayer;

	// containerPlayerが手に持っているItemStack
	private ItemStack stackHeldItem;

	// カードEntity
	private EntityLivingBase cardEntity;

	public ContainerMaterialBook(EntityPlayer player, ItemStack stack)
	{
		// bookInventoryを開く
		this.bookInventory.openInventory();

		// slot番号 & x座標 & y座標
		int invslot = 0;
		int xpos = 0;
		int ypos = 0;

		// slot番号 & x座標 & y座標
		invslot = (0);
		xpos = (61);
		ypos = (131);

		// Slotを設置
		this.addSlotToContainer(new Slot(this.bookInventory, invslot, xpos, ypos)
		{

			// スタック数の最大値
			@Override
			public int getSlotStackLimit()
			{
				return 1;
			}

			// Slotに入るかどうかの判定
			@Override
			public boolean isItemValid(ItemStack stack)
			{
				if (stack.getItem() instanceof ItemMaterialCard)
				{
					return ((ItemMaterialCard) stack.getItem()).hasContent(stack);
				}

				return false;
			}

		});

		// 列 & 行
		int column = 0;
		int row = 0;

		for (column = 0; column < 3; ++column)
		{
			for (row = 0; row < 9; ++row)
			{
				// slot番号 & x座標 & y座標
				invslot = 9 + row + column * 9;
				xpos = (48) + row * 18;
				ypos = (165) + column * 18;

				// Slotを設置
				this.addSlotToContainer(new Slot(player.inventory, invslot, xpos, ypos));
			}
		}

		for (row = 0; row < 9; ++row)
		{
			// slot番号 & x座標 & y座標
			invslot = row;
			xpos = (48) + row * 18;
			ypos = (223);

			// Slotを設置
			this.addSlotToContainer(new Slot(player.inventory, invslot, xpos, ypos));
		}

		// 変数を代入
		this.containerPlayer = player;
		this.stackHeldItem = stack;
	}

	// Shift + 左クリックの処理
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotindex)
	{
		// クリックされたスロットを取得
		Slot slot = (Slot) this.inventorySlots.get(slotindex);

		// スロットに何もない場合
		if (slot == null || slot.getHasStack() == false)
		{
			return null;
		}

		// 移動されるアイテム
		ItemStack stackDstItem = slot.getStack();

		// 移動されるアイテム元のアイテム
		ItemStack stackSrcItem = stackDstItem.copy();

		// -------- スロット番号 : 内容（スロット数） -------- //

		// --- 0 : 追加インベントリ（1）--- //
		// 0 : カード用のインベントリ（1）

		// --- 1-36 : プレイヤーインベントリ（36）--- //
		// 1-27 : プレイヤーインベントリ上部（27）
		// 28-36 : プレイヤーインベントリ下部（9）

		// 『0 : カード用のインベントリ（1）』をクリック
		if (slotindex == 0)
		{
			// 『1-36 : プレイヤーインベントリ（36）』へ移動
			if (!this.mergeItemStack(stackDstItem, 1, 36, false))
			{
				return null;
			}

			slot.onSlotChange(stackDstItem, stackSrcItem);
		}
		// 『1-36 : プレイヤーインベントリ（36）』へ移動
		else if (1 <= slotindex && slotindex <= 36)
		{
			if (stackSrcItem.getItem() instanceof ItemMaterialCard && ((ItemMaterialCard) stackSrcItem.getItem()).hasContent(stackSrcItem))
			{
				// 『0 : カード用のインベントリ（1）』へ移動
				if (!this.mergeItemStack(stackDstItem, 0, 1, false))
				{
					return null;
				}
			}
		}

		// 移動先スロットが溢れなかった場合は移動元スロットを空にする
		if (stackDstItem.stackSize == 0)
		{
			slot.putStack((ItemStack) null);
		}
		// 移動先スロットが溢れた場合は数だけ変わって元スロットにアイテムが残るので更新通知
		else
		{
			slot.onSlotChanged();
		}

		// シフトクリック前後で数が変わらなかった -> 移動失敗
		if (stackDstItem.stackSize == stackSrcItem.stackSize)
		{
			return null;
		}

		slot.onPickupFromSlot(player, stackDstItem);

		return stackSrcItem;
	}

	// 開いていられる判定
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return ItemStack.areItemStacksEqual(this.stackHeldItem, player.getHeldItem());
	}

	// 閉じる際の処理
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);

		if (!player.worldObj.isRemote)
		{
			ItemStack stack = this.bookInventory.getStackInSlotOnClosing(0);

			if (stack != null)
			{
				player.dropPlayerItemWithRandomChoice(stack, false);
			}
		}
	}

	// TODO /* ======================================== ContainerMaterialBook START ===================================== */

	// カードEntityのgetter
	public EntityLivingBase getCardEntity()
	{
		return this.cardEntity;
	}

	// カードEntityのsetter
	public void setCardEntity(World newWorld, EntityLivingBase livingBase)
	{
		if (livingBase != null)
		{
			livingBase.setWorld(newWorld);

			if (livingBase instanceof EntityLiving && ((EntityLiving) livingBase).hasCustomNameTag())
			{
				((EntityLiving) livingBase).setAlwaysRenderNameTag(true);
			}
		}

		this.cardEntity = livingBase;
	}

	// ContainerMaterialBook用のIInventory
	public IInventory bookInventory = new InventoryBasic("Book Inventory", false, 1)
	{

		// 更新の処理
		@Override
		public void markDirty()
		{
			super.markDirty();
			ContainerMaterialBook.this.onCraftMatrixChanged(this);

			if (this.getStackInSlot(0) != null && this.getStackInSlot(0).getItem() instanceof ItemMaterialCard)
			{
				ItemStack stackCard = bookInventory.getStackInSlot(0);
				ItemMaterialCard itemCard = (ItemMaterialCard) stackCard.getItem();
				World world = ContainerMaterialBook.this.containerPlayer.worldObj;

				if (itemCard.hasContent(stackCard))
				{
					EntityLivingBase entity = null;
					boolean tmp = world.isRemote;

					try
					{
						world.isRemote = false;
						entity = itemCard.getContentEntity(stackCard, world);
					}
					catch (Throwable e)
					{
						world.isRemote = tmp;
						entity = itemCard.getContentEntity(stackCard, world);
					}
					finally
					{
						world.isRemote = tmp;
					}

					ContainerMaterialBook.this.setCardEntity(world, entity);
				}
			}
			else
			{
				ContainerMaterialBook.this.setCardEntity((World) null, (EntityLivingBase) null);
			}
		}

	};

}
