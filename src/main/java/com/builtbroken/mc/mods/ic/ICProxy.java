package com.builtbroken.mc.mods.ic;

import com.builtbroken.mc.lib.energy.UniversalEnergySystem;
import com.builtbroken.mc.lib.helper.WrenchUtility;
import com.builtbroken.mc.lib.mod.ModProxy;
import com.builtbroken.mc.lib.mod.Mods;
import net.minecraftforge.common.MinecraftForge;

/**
 * Handles loading support for Industrial Craft 2 mod
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/26/2016.
 */
public class ICProxy extends ModProxy
{
    public ICProxy()
    {
        super(Mods.IC2);
    }

    @Override
    public void init()
    {
        WrenchUtility.registerWrenchType(new ICWrenchProxy());
        MinecraftForge.EVENT_BUS.register(ICStaticForwarder.INSTANCE);
        UniversalEnergySystem.register(new ICHandler());
    }
}
