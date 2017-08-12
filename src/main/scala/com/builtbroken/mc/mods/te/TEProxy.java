package com.builtbroken.mc.mods.te;

import com.builtbroken.mc.core.Engine;
import com.builtbroken.mc.framework.mod.ModProxy;
import com.builtbroken.mc.framework.mod.Mods;
import com.builtbroken.mc.mods.rf.RFEnergyHandler;

/**
 * Loads support for thermal expansion
 * Created by Dark(DarkGuardsman, Robert) on 8/10/2016.
 */
public class TEProxy extends ModProxy
{
    public TEProxy()
    {
        super(Mods.TF_EXPANSION);
    }

    @Override
    public void init()
    {
        super.init();
        Engine.logger().info("Thermal Expansion support loaded");
        RFEnergyHandler.thermalExpansionHandler = new ThermalExpansionEnergyHandler();
    }
}
