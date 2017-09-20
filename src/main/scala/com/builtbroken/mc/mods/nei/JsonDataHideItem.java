package com.builtbroken.mc.mods.nei;

import com.builtbroken.mc.framework.json.imp.IJsonProcessor;
import com.builtbroken.mc.framework.json.processors.JsonGenData;
import com.builtbroken.mc.framework.json.data.JsonItemEntry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 8/11/2017.
 */
public class JsonDataHideItem extends JsonGenData
{
    public Object item;

    public JsonDataHideItem(IJsonProcessor processor, Object item)
    {
        super(processor);
        this.item = item;
    }

    @Override
    public void register()
    {
        if (item instanceof Block)
        {
            NEIProxy.hideItem((Block) item);
        }
        else if (item instanceof Item)
        {
            NEIProxy.hideItem((Item) item);
        }
        else if (item instanceof ItemStack)
        {
            NEIProxy.hideItem((ItemStack) item);
        }
        else if (canConvertToItem(item))
        {
            Object object = convertItemEntry(item);
            if (!(object instanceof String || object instanceof JsonItemEntry))
            {
                item = object;
                register();
            }
        }
        else
        {
            //TODO throw error?
        }
    }

    @Override
    public String getContentID()
    {
        return null;
    }

    @Override
    public String getUniqueID()
    {
        return null;
    }
}
