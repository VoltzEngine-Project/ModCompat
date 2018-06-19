package com.builtbroken.mc.mods.cc;

import com.builtbroken.mc.framework.mod.ModProxy;
import com.builtbroken.mc.framework.mod.Mods;
import dan200.computercraft.api.ComputerCraftAPI;

/**
 * Handles loading support for Computer Craft
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 6/18/2018.
 */
public class CCProxy extends ModProxy
{
    public CCProxy()
    {
        super(Mods.CC);
    }

    @Override
    public void init()
    {
        ComputerCraftAPI.registerPeripheralProvider(new CCWrapper.Provider());
    }
}
