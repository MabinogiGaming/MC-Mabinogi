package com.mabinogi.lib.model.item.baked;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mabinogi.lib.model.item.LayeredItemModel;
import com.mabinogi.lib.model.item.override.LayeredItemOverrideList;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.client.model.BakedItemModel;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.TRSRTransformation;

public class LayeredItemBakedModel extends BakedItemModel {

	public final IModel parent;
	
	public LayeredItemBakedModel(IModel parent, VertexFormat format, ImmutableList<BakedQuad> quads, TextureAtlasSprite particle, ImmutableMap<TransformType, TRSRTransformation> transforms)
	{
		super(quads, particle, transforms, LayeredItemOverrideList.INSTANCE);
		
		this.parent = parent;
	}
	
	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType type)
	{
		Pair<? extends IBakedModel, Matrix4f> basePair = ((LayeredItemModel) parent).baseBakedModel.handlePerspective(type); 
		return Pair.of(this, basePair.getRight());
	}
	
}