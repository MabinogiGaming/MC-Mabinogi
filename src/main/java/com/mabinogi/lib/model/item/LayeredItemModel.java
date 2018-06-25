package com.mabinogi.lib.model.item;

import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mabinogi.lib.model.AbstractItemModel;
import com.mabinogi.lib.model.item.baked.LayeredItemBakedModel;
import com.mabinogi.lib.util.ModelUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

public class LayeredItemModel extends AbstractItemModel {
	
	public Map<String, IBakedModel> cache = Maps.newHashMap();
	public Map<String, Integer> iconMap;

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
	{
		TextureAtlasSprite particleSprite = null;
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

        if (iconMap != null)
        {
	        for (Entry<String, Integer> entry : iconMap.entrySet())
	        {
	        	TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(entry.getKey());
	        	builder.addAll(ModelUtil.getQuadsForSprite(-1, entry.getValue(), sprite, format, Optional.empty()));
	        }
        }

        return new LayeredItemBakedModel(this, format, builder.build(), particleSprite, ImmutableMap.of());
	}

}
