package com.builtbroken.mc.mods;

import com.builtbroken.mc.framework.mod.Mods;
import com.builtbroken.mc.framework.mod.loadable.LoadableHandler;
import com.builtbroken.mc.mods.nei.NEIProxy;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 12/3/2017.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void loadModules(LoadableHandler loader)
    {
        super.loadModules(loader);
        loader.applyModule(NEIProxy.class, Mods.NEI.isLoaded());
    }
}
