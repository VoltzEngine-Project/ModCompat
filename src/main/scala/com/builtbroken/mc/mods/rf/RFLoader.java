package com.builtbroken.mc.mods.rf;

import com.builtbroken.mc.core.Engine;
import com.builtbroken.mc.framework.multiblock.EnumMultiblock;
import com.builtbroken.mc.framework.energy.UniversalEnergySystem;
import com.builtbroken.mc.framework.mod.loadable.AbstractLoadable;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
        Engine.instance.logger().info("RF support loaded");
        UniversalEnergySystem.RF_HANDLER = new RFEnergyHandler(RF_RATIO); //TODO add config setting
        UniversalEnergySystem.register(UniversalEnergySystem.RF_HANDLER);

        if (Engine.multiBlock != null)
        {
            GameRegistry.registerTileEntity(TileMultiEnergyRF.class, EnumMultiblock.ENERGY_RF.getTileName());
            EnumMultiblock.ENERGY_RF.provider = new ITileEntityProvider()
            {
                @Override
                public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
                {
                    return new TileMultiEnergyRF();
                }
            };
        }
    }
}
