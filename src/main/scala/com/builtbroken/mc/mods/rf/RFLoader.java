package com.builtbroken.mc.mods.rf;

import com.builtbroken.mc.core.Engine;
import com.builtbroken.mc.lib.energy.UniversalEnergySystem;
import com.builtbroken.mc.lib.mod.loadable.AbstractLoadable;
import com.builtbroken.mc.framework.multiblock.EnumMultiblock;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Cow Pi on 8/10/2015.
 */
public class RFLoader extends AbstractLoadable
{
    @Override
    public void init()
    {
        super.init();
        Engine.instance.logger().info("RF support loaded");
        UniversalEnergySystem.register(RFEnergyHandler.INSTANCE); //TODO add config setting

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
