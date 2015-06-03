package schr0.materialscan.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import schr0.materialscan.init.MaterialScanResource;
import schr0.materialscan.library.LibName;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMaterialBook extends GuiContainer
{

	// ResourceLocation
	private static final ResourceLocation MATERIAL_BOOK_INFO_TEXTURE = new ResourceLocation(MaterialScanResource.DOMAIN_GUI + "material_book_info.png");

	// ContainerMaterialBook
	private ContainerMaterialBook containerMaterialBook;

	// マウスの座標
	private float xSizeFloat;
	private float ySizeFloat;

	public GuiMaterialBook(EntityPlayer player, ItemStack stack)
	{
		super(new ContainerMaterialBook(player, stack));

		// 描画の座標
		this.xSize = 255;
		this.ySize = 247;

		// ContainerMaterialBook
		this.containerMaterialBook = (ContainerMaterialBook) this.inventorySlots;
	}

	// 描画処理
	@Override
	public void drawScreen(int xMouse, int yMouse, float renderPartialTicks)
	{
		super.drawScreen(xMouse, yMouse, renderPartialTicks);

		// マウスの座標
		this.xSizeFloat = (float) xMouse;
		this.ySizeFloat = (float) yMouse;
	}

	// 文字などの描画処理
	@Override
	protected void drawGuiContainerForegroundLayer(int xMouse, int yMouse)
	{
		if (this.containerMaterialBook.getCardEntity() != null)
		{
			EntityLivingBase livingBase = this.containerMaterialBook.getCardEntity();

			String mobStatus = I18n.format("gui.status", new Object[0]);
			this.fontRendererObj.drawString(mobStatus, (128) + ((128) / 2 - this.fontRendererObj.getStringWidth(mobStatus) / 2), (9), 0);

			int titleXpos = 139;
			int xpos = 144;
			int strHeight = 12;

			this.fontRendererObj.drawString(I18n.format("gui.status.name", new Object[0]), titleXpos, (20) + (strHeight * 0), 0x808080);
			this.fontRendererObj.drawString(LibName.getTrueName(livingBase).getUnformattedTextForChat(), xpos, (20) + (strHeight * 1), 0);

			this.fontRendererObj.drawString(I18n.format("gui.status.type", new Object[0]), titleXpos, (20) + (strHeight * 2), 0x808080);
			this.fontRendererObj.drawString(this.getType(livingBase), xpos, (20) + (strHeight * 3), 0);

			this.fontRendererObj.drawString(I18n.format("gui.status.age", new Object[0]), titleXpos, (20) + (strHeight * 4), 0x808080);
			this.fontRendererObj.drawString(this.getAge(livingBase), xpos, (20) + (strHeight * 5), 0);

			this.fontRendererObj.drawString(I18n.format("gui.status.size", new Object[0]), titleXpos, (20) + (strHeight * 6), 0x808080);
			this.fontRendererObj.drawString(this.getSize(livingBase), xpos, (20) + (strHeight * 7), 0);

			this.fontRendererObj.drawString(I18n.format("gui.status.health", new Object[0]), titleXpos, (20) + (strHeight * 8), 0x808080);
			this.fontRendererObj.drawString(this.getHealth(livingBase), xpos, (20) + (strHeight * 9), 0);
		}
	}

	// テクスチャ設定などの描画処理
	@Override
	protected void drawGuiContainerBackgroundLayer(float renderPartialTicks, int xMouse, int yMouse)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(MATERIAL_BOOK_INFO_TEXTURE);

		int xd = (this.width - this.xSize) / 2;
		int yd = (this.height - this.ySize) / 2;

		this.drawTexturedModalRect(xd, yd, 0, 0, this.xSize, this.ySize);

		if (this.containerMaterialBook.getCardEntity() != null)
		{
			EntityLivingBase livingBase = this.containerMaterialBook.getCardEntity();

			showCardEntity(xd + (68), yd + (101), 25, (float) (xd + (68)) - this.xSizeFloat, (float) (yd + (101) - 50) - this.ySizeFloat, livingBase);
		}
	}

	// TODO /* ======================================== GuiMaterialBook START ===================================== */

	// GuiにカードEntityに映す処理
	private static void showCardEntity(int xd, int yd, int size, float xMouse, float yMouse, EntityLivingBase target)
	{
		// ↓ GuiInventory.func_147046_a( 表示物のX中心, 表示物のY終点, 表示物のサイズ, 目線移動のX座標, 目線移動のY座標, EntityLivingBase) ↓ //
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) xd, (float) yd, 50.0F);
		GL11.glScalef((float) (-size), (float) size, (float) size);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float f2 = target.renderYawOffset;
		float f3 = target.rotationYaw;
		float f4 = target.rotationPitch;
		float f5 = target.prevRotationYawHead;
		float f6 = target.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan((double) (yMouse / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		target.renderYawOffset = (float) Math.atan((double) (xMouse / 40.0F)) * 20.0F;
		target.rotationYaw = (float) Math.atan((double) (xMouse / 40.0F)) * 40.0F;
		target.rotationPitch = -((float) Math.atan((double) (yMouse / 40.0F))) * 20.0F;
		target.rotationYawHead = target.rotationYaw;
		target.prevRotationYawHead = target.rotationYaw;
		GL11.glTranslatef(0.0F, target.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(target, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		target.renderYawOffset = f2;
		target.rotationYaw = f3;
		target.rotationPitch = f4;
		target.prevRotationYawHead = f5;
		target.rotationYawHead = f6;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	// 種類
	private String getType(EntityLivingBase livingBase)
	{
		if (livingBase instanceof IBossDisplayData)
		{
			return I18n.format("gui.status.type.boss", new Object[0]);
		}

		if (livingBase instanceof INpc)
		{
			return I18n.format("gui.status.type.npc", new Object[0]);
		}

		if (livingBase instanceof IMob)
		{
			return I18n.format("gui.status.type.mob", new Object[0]);
		}

		if (livingBase instanceof IAnimals)
		{
			return I18n.format("gui.status.type.animal", new Object[0]);
		}

		return I18n.format("gui.status.unknown", new Object[0]);
	}

	// 体格
	private String getSize(EntityLivingBase livingBase)
	{
		float sizeWidth = livingBase.width;
		float sizeHeight = livingBase.height;

		if (2.5F <= sizeWidth || 2.5F <= sizeHeight)
		{
			return I18n.format("gui.status.size.large", new Object[0]);
		}

		if ((1.0F <= sizeWidth && sizeWidth < 2.5F) || (1.0F <= sizeHeight && sizeHeight < 2.5F))
		{
			return I18n.format("gui.status.size.medium", new Object[0]);
		}

		if ((0.0F <= sizeWidth && sizeWidth < 1.0F) || (0.0F <= sizeHeight && sizeHeight < 1.0F))
		{
			return I18n.format("gui.status.size.small", new Object[0]);
		}

		return I18n.format("gui.status.unknown", new Object[0]);
	}

	// 年齢
	private String getAge(EntityLivingBase livingBase)
	{
		if (livingBase.isChild())
		{
			return I18n.format("gui.status.age.child", new Object[0]);
		}

		return I18n.format("gui.status.age.adult", new Object[0]);
	}

	// 状態
	private String getHealth(EntityLivingBase livingBase)
	{
		if (livingBase.getHealth() > 0.0F && livingBase.getHealth() < livingBase.getMaxHealth())
		{
			if (livingBase.getHealth() < (livingBase.getMaxHealth() / 10))
			{
				return I18n.format("gui.status.health.dying", new Object[0]);
			}

			if (livingBase.getHealth() < (livingBase.getMaxHealth() / 2))
			{
				return I18n.format("gui.status.health.seriousInjury", new Object[0]);
			}

			return I18n.format("gui.status.health.injury", new Object[0]);
		}

		return I18n.format("gui.status.health.healthy", new Object[0]);
	}

}
