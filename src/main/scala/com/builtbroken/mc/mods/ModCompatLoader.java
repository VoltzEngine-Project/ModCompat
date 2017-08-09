package com.builtbroken.mc.mods;

import com.builtbroken.mc.core.Engine;
import com.builtbroken.mc.core.References;
import com.builtbroken.mc.lib.mod.Mods;
import com.builtbroken.mc.lib.mod.loadable.LoadableHandler;
import com.builtbroken.mc.mods.ae.AEProxy;
import com.builtbroken.mc.mods.bc.BCProxy;
import com.builtbroken.mc.mods.ic.ICProxy;
import com.builtbroken.mc.mods.mek.MekProxy;
import com.builtbroken.mc.mods.nei.NEIProxy;
import com.builtbroken.mc.mods.oc.OCProxy;
import com.builtbroken.mc.mods.pe.ProjectEProxy;
import com.builtbroken.mc.mods.rf.RFLoader;
import com.builtbroken.mc.mods.te.TEProxy;
import com.builtbroken.mc.mods.tinkers.TinkerProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 8/9/2017.
 */
@Mod(modid = ModCompatLoader.DOMAIN, name = "Voltz Engine Mod Compatibility Loader", version = References.VERSION, dependencies = "required-after:voltzengine")
public class ModCompatLoader
{
    public static final String DOMAIN = References.DOMAIN + "modcompat";

    public static LoadableHandler loader = new LoadableHandler();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //Mod Support
        Engine.config.setCategoryComment("Mod_Support", "If true the proxy class for the mod will be loaded enabling support, set to false if support is not required or breaks the game.");
        loader.applyModule(NEIProxy.class); //Uses reflection instead of API files
        loader.applyModule(OCProxy.class, Mods.OC.isLoaded());
        loader.applyModule(TinkerProxy.class, Mods.TINKERS.isLoaded());
        loader.applyModule(AEProxy.class, Mods.AE.isLoaded());
        loader.applyModule(ICProxy.class, Mods.IC2.isLoaded());
        loader.applyModule(BCProxy.class, Mods.BC.isLoaded());
        loader.applyModule(MekProxy.class, Mods.MEKANISM.isLoaded());
        loader.applyModule(ProjectEProxy.class, Mods.PROJECT_E.isLoaded());



        //Check if RF api exists
        boolean shouldLoadRFHandler = true;
        for (String s : new String[]{"IEnergyConnection", "IEnergyContainerItem", "IEnergyHandler", "IEnergyProvider", "IEnergyReceiver", "IEnergyStorage"})
        {
            try
            {
                Class clazz = Class.forName("cofh.api.energy." + s, false, this.getClass().getClassLoader());
                if (clazz == null)
                {
                    shouldLoadRFHandler = false;
                    break;
                }
            }
            catch (ClassNotFoundException e)
            {
                shouldLoadRFHandler = false;
                Engine.logger().error("Not loading RF support as we couldn't detect " + "cofh.api.energy." + s + " class or interface.");
                break;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if (shouldLoadRFHandler)
        {
            loader.applyModule(RFLoader.class);
            loader.applyModule(TEProxy.class, Mods.TF_EXPANSION.isLoaded());
        }

        loader.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        loader.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        loader.postInit();
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event)
    {
        loader.loadComplete();
    }
}
