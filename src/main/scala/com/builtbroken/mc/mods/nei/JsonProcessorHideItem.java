package com.builtbroken.mc.mods.nei;

import com.builtbroken.mc.framework.json.processors.JsonProcessor;
import com.builtbroken.mc.mods.ModCompatLoader;
import com.google.gson.JsonElement;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 8/11/2017.
 */
public class JsonProcessorHideItem extends JsonProcessor<JsonDataHideItem>
{
    @Override
    public JsonDataHideItem process(JsonElement element)
    {
        Object object = getItemFromJson(element);
        return new JsonDataHideItem(this, object);
    }

    @Override
    public String getMod()
    {
        return ModCompatLoader.DOMAIN;
    }

    @Override
    public String getJsonKey()
    {
        return "hideItem";
    }

    @Override
    public String getLoadOrder()
    {
        return "after:item";
    }
}
