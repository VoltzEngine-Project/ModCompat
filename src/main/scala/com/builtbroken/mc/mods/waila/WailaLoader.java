package com.builtbroken.mc.mods.waila;

import com.builtbroken.mc.framework.mod.Mods;
import com.builtbroken.mc.framework.mod.loadable.AbstractLoadable;
import com.builtbroken.mc.framework.multiblock.BlockMultiblock;
import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaRegistrar;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 10/29/2017.
 */
public class WailaLoader extends AbstractLoadable
{
    @Override
    public void preInit()
    {
        FMLInterModComms.sendMessage(Mods.WAILA.mod_id, "register", "com.builtbroken.mc.mods.waila.WailaLoader.onWailaCall");
    }

    public static void onWailaCall(IWailaRegistrar registrar)
    {
        registrar.registerStackProvider(new WailaMultiBlockHandler(), BlockMultiblock.class);
    }
}