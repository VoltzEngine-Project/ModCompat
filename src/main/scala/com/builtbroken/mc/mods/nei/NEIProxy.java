package com.builtbroken.mc.mods.nei;

import com.builtbroken.mc.core.Engine;
import com.builtbroken.mc.framework.mod.ModProxy;
import com.builtbroken.mc.framework.mod.Mods;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class NEIProxy extends ModProxy
{
    private static boolean lock = false;

    private static List hideItems = new ArrayList();

    public NEIProxy()
    {
        super(Mods.NEI);
    }

    public static void hideItem(Item item)
    {
        hideItems.add(item);
    }

    public static void hideItem(Block item)
    {
        hideItems.add(item);
    }

    public static void hideItem(ItemStack item)
    {
        hideItems.add(item);
    }

    @Override
    public void postInit()
    {
        if (!lock)
        {
            //Only run once as the list is static
            lock = true;
            try
            {
                Class nei_api_class = Class.forName("codechicken.nei.api.API");
                Method method = nei_api_class.getMethod("hideItem", ItemStack.class);
                for (Object obj : hideItems)
                {
                    if (obj instanceof Block || obj instanceof Item)
                    {
                        List list = new ArrayList();
                        if (obj instanceof Block)
                        {
                            ((Block) obj).getSubBlocks(Item.getItemFromBlock((Block) obj), ((Block) obj).getCreativeTabToDisplayOn(), list);
                        }
                        else
                        {
                            ((Item) obj).getSubItems((Item) obj, ((Item) obj).getCreativeTab(), list);
                        }
                        for (Object o : list)
                        {
                            if (o instanceof ItemStack)
                            {
                                method.invoke(null, (ItemStack) o);
                            }
                        }
                    }
                    else if (obj instanceof ItemStack)
                    {
                        method.invoke(null, (ItemStack) obj);
                    }
                }
            } catch (ClassNotFoundException e)
            {
                Engine.logger().error("Failed to locate NEI API class", e);
            } catch (NoSuchMethodException e)
            {
                Engine.logger().error("Failed to locate NEI hideItem method", e);
            } catch (InvocationTargetException e)
            {
                Engine.logger().error("Failed to invoke NEI hideItem method", e);
            } catch (IllegalAccessException e)
            {
                Engine.logger().error("Failed to access NEI hideItem method", e);
            }
        }
    }

    @Override
    public boolean shouldLoad()
    {
        return FMLCommonHandler.instance().getSide() == Side.CLIENT && Loader.isModLoaded("NotEnoughItems");
    }
}
