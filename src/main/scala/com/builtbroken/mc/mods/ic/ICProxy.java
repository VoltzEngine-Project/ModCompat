package com.builtbroken.mc.mods.ic;

import com.builtbroken.mc.api.energy.IEnergyBufferProvider;
import com.builtbroken.mc.framework.computer.DataSystemHandler;
import com.builtbroken.mc.framework.energy.UniversalEnergySystem;
import com.builtbroken.mc.framework.mod.ModProxy;
import com.builtbroken.mc.framework.mod.Mods;
import com.builtbroken.mc.lib.helper.WrenchUtility;
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

        DataSystemHandler.addSharedMethod("ueToIcRatio", tile -> {
            if (tile instanceof IEnergyBufferProvider)
            {
                return (host, method, args) -> {
                    if (host instanceof IEnergyBufferProvider)
                    {
                        return new Object[]{ICHandler.TO_UE};
                    }
                    return new Object[]{"Error: Object is not an energy storage"};
                };
            }
            return null;
        });

        DataSystemHandler.addSharedMethod("icToUeRatio", tile -> {
            if (tile instanceof IEnergyBufferProvider)
            {
                return (host, method, args) -> {
                    if (host instanceof IEnergyBufferProvider)
                    {
                        return new Object[]{ICHandler.FROM_UE};
                    }
                    return new Object[]{"Error: Object is not an energy storage"};
                };
            }
            return null;
        });
    }
}
