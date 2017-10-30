package com.builtbroken.mc.mods.waila;

import com.builtbroken.mc.core.Engine;
import com.builtbroken.mc.framework.multiblock.TileMulti;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * Fixes waila showing multi-block tiles as something other than the host block
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 10/29/2017.
 */
public class WailaMultiBlockHandler extends WailaHandler
{
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block block = accessor.getBlock();
        if (block == Engine.multiBlock)
        {
            TileEntity tile = accessor.getTileEntity();
            if (tile instanceof TileMulti && ((TileMulti) tile).getHost() != null)
            {
                return ((TileMulti) tile).getHost().getHostAsStack(accessor.getPlayer(), accessor.getPosition());
            }
        }
        return null;
    }
}
