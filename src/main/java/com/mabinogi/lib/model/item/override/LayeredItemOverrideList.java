package com.mabinogi.lib.model.item.override;

import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.mabinogi.lib.item.iface.ILayeredModelItem;
import com.mabinogi.lib.model.item.LayeredItemModel;
import com.mabinogi.lib.model.item.baked.LayeredItemBakedModel;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LayeredItemOverrideList extends ItemOverrideList {
	
	public static LayeredItemOverrideList INSTANCE = new LayeredItemOverrideList();

	public LayeredItemOverrideList()
	{
		super(ImmutableList.of());
	}
	
	@Override
	public IBakedModel handleItemState(IBakedModel baseModel, ItemStack stack, World world, EntityLivingBase entity)
	{
		//create the model
        LayeredItemModel model = (LayeredItemModel) ((LayeredItemBakedModel) baseModel).parent;

        //generate the cache from the nbt
        if (stack.getItem() != null && stack.getItem() instanceof ILayeredModelItem)
        {
        	//get the iconmap from the item
        	Map<String, Integer> iconMap = ((ILayeredModelItem) stack.getItem()).getLayerMap();
        	
        	//get a string representation of the icon map
        	String cache = iconMap.toString();
    
	        //if we already have this cache, return it
	        if (model.cache.containsKey(cache))
	        {
	        	return model.cache.get(cache);
	        }
	        
	        //if not then use the new icon map
	        model.iconMap = iconMap;
	        
	        //bake the model and place it into the cache
	        IBakedModel bakedModel = model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM, null);
	        model.cache.put(cache, bakedModel);
	        return bakedModel;
        }
        
		return baseModel;
	}

}