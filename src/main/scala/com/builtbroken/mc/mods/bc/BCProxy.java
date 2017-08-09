package com.builtbroken.mc.mods.bc;

import com.builtbroken.mc.lib.helper.WrenchUtility;
import com.builtbroken.mc.framework.mod.ModProxy;
import com.builtbroken.mc.framework.mod.Mods;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/26/2016.
 */
public class BCProxy extends ModProxy
{
    public BCProxy()
    {
        super(Mods.BC);
    }

    @Override
    public void init()
    {
        WrenchUtility.registerWrenchType(new BCWrenchProxy());
    }
}
