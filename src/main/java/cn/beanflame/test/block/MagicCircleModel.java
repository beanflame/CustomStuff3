package cn.beanflame.test.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MagicCircleModel extends ModelBase {
	public ModelRenderer Main;
	public MagicCircleModel() {
		textureWidth = 16;
		textureHeight = 16;
		Main = new ModelRenderer(this);
		Main.setRotationPoint(0.0F, 24.0F, 0.0F);
		Main.cubeList.add(new ModelBox(Main, 0, 0, -8.0F, -1.0F, -8.0F, 16, 0, 16, 0.0F));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Main.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}


	public void renderAll()
	{
		//this.chestKnob.rotateAngleX = this.chestLid.rotateAngleX;
		//this.chestLid.render(0.0625F);
		//this.chestKnob.render(0.0625F);
		this.Main.render(0.0625F);
	}
}