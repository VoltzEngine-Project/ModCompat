package com.builtbroken.mc.mods.rf;

import com.builtbroken.mc.api.energy.IEnergyBufferProvider;
import com.builtbroken.mc.core.Engine;
import com.builtbroken.mc.framework.computer.DataSystemHandler;
import com.builtbroken.mc.framework.energy.UniversalEnergySystem;
import com.builtbroken.mc.framework.mod.loadable.AbstractLoadable;
import com.builtbroken.mc.framework.multiblock.EnumMultiblock;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by Cow Pi on 8/10/2015.
 */
public class RFLoader extends AbstractLoadable
{
    public static double RF_RATIO = 2;

    @Override
    public void init()
    {
        super.init();
        Engine.logger().info("RF support loaded");
        UniversalEnergySystem.RF_HANDLER = new RFEnergyHandler(RF_RATIO); //TODO add config setting
        UniversalEnergySystem.register(UniversalEnergySystem.RF_HANDLER);

        if (Engine.multiBlock != null)
        {
            GameRegistry.registerTileEntity(TileMultiEnergyRF.class, EnumMultiblock.ENERGY_RF.getTileName());
            EnumMultiblock.ENERGY_RF.provider = (p_149915_1_, p_149915_2_) -> new TileMultiEnergyRF();

            DataSystemHandler.addSharedMethod("ueToRfRatio", tile -> {
                if (tile instanceof IEnergyBufferProvider)
                {
                    return (host, method, args) -> {
                        if (host instanceof IEnergyBufferProvider)
                        {
                            return new Object[]{RFEnergyHandler.TO_RF_FROM_UE};
                        }
                        return new Object[]{"Error: Object is not an energy storage"};
                    };
                }
                return null;
            });

            DataSystemHandler.addSharedMethod("rfToUeRatio", tile -> {
                if (tile instanceof IEnergyBufferProvider)
                {
                    return (host, method, args) -> {
                        if (host instanceof IEnergyBufferProvider)
                        {
                            return new Object[]{RFEnergyHandler.TO_UE_FROM_RF};
                        }
                        return new Object[]{"Error: Object is not an energy storage"};
                    };
                }
                return null;
            });
        }
    }
}
