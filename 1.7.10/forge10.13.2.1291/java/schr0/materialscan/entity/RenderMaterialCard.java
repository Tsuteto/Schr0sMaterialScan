package schr0.materialscan.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMaterialCard extends Render
{

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRenderMaterialCard((EntityMaterialCard) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	// ResourceLocationのgetter
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return TextureMap.locationItemsTexture;
	}

	// TODO /* ======================================== RenderEntityCard START ===================================== */

	// EntityMaterialCard用のdoRender
	private void doRenderMaterialCard(EntityMaterialCard card, double par2, double par3, double par4, float par5, float par6)
	{
		ItemStack stack = card.getCardItemStack();
		IIcon iicon = stack.getItem().getIconFromDamage(stack.getItemDamage());

		if (iicon != null)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef((float) par2, (float) par3, (float) par4);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			this.bindEntityTexture(card);
			Tessellator tessellator = Tessellator.instance;
			this.setTessellatorWithIIcon(tessellator, iicon);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}

	// TessellatorとIIconの設定
	private void setTessellatorWithIIcon(Tessellator tessellator, IIcon iicon)
	{
		float f = iicon.getMinU();
		float f1 = iicon.getMaxU();
		float f2 = iicon.getMinV();
		float f3 = iicon.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV((double) (0.0F - f5), (double) (0.0F - f6), 0.0D, (double) f, (double) f3);
		tessellator.addVertexWithUV((double) (f4 - f5), (double) (0.0F - f6), 0.0D, (double) f1, (double) f3);
		tessellator.addVertexWithUV((double) (f4 - f5), (double) (f4 - f6), 0.0D, (double) f1, (double) f2);
		tessellator.addVertexWithUV((double) (0.0F - f5), (double) (f4 - f6), 0.0D, (double) f, (double) f2);
		tessellator.draw();
	}

}