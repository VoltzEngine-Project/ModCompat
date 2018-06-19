package com.builtbroken.mc.mods.cc;

import com.builtbroken.mc.api.computer.IDataSystem;
import com.builtbroken.mc.api.tile.multiblock.IMultiTile;
import com.builtbroken.mc.api.tile.node.ITileNodeHost;
import com.builtbroken.mc.framework.computer.DataSystemHandler;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Wraps a tile as a CC
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 6/18/2018.
 */
public class CCWrapper implements IPeripheral
{
    public final IDataSystem dataSystem;
    public final Object host;

    public CCWrapper(IDataSystem dataSystem, Object host)
    {
        this.dataSystem = dataSystem;
        this.host = host;
    }

    @Override
    public String getType()
    {
        return dataSystem.getSystemType(host);
    }

    @Override
    public String[] getMethodNames()
    {
        return dataSystem.getMethods(host);
    }

    @Override
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method_id, Object[] arguments) throws LuaException, InterruptedException
    {
        try
        {
            if (method_id >= 0 && method_id < getMethodNames().length)
            {
                String method = getMethodNames()[method_id];
                if (method != null)
                {
                    return dataSystem.runMethod(host, method, arguments);
                }
            }
            throw new Exception("Error: Method  with id '" + method_id + "' could not be found");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            String message = e.getMessage();
            if (message == null)
            {
                message = "Error.";
            }
            else if (!message.startsWith("Error:"))
            {
                message = "Error: " + message;
            }
            return new Object[]{message};
        }
    }

    @Override
    public void attach(IComputerAccess computer)
    {

    }

    @Override
    public void detach(IComputerAccess computer)
    {

    }

    @Override
    public boolean equals(IPeripheral other)
    {
        return this == other;
    }

    public static class Provider implements IPeripheralProvider
    {
        @Override
        public IPeripheral getPeripheral(World world, int x, int y, int z, int side)
        {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile != null)
            {
                //Try base tile
                IPeripheral peripheral = get(tile);

                //Try multi-block
                if (peripheral == null && tile instanceof IMultiTile)
                {
                    peripheral = get(((IMultiTile) tile).getHost());
                }

                //Try node
                if (peripheral == null && tile instanceof ITileNodeHost)
                {
                    return get(((ITileNodeHost) tile).getTileNode());
                }
                return peripheral;
            }
            return null;
        }

        protected IPeripheral get(Object tile)
        {
            if (tile instanceof IDataSystem)
            {
                return new CCWrapper((IDataSystem) tile, tile);
            }

            IDataSystem system = DataSystemHandler.getSystemInfo(tile);
            if (system != null)
            {
                return new CCWrapper(system, tile);
            }
            return null;
        }
    }
}
