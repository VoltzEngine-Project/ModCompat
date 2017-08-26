package com.builtbroken.mc.mods.nei;

import codechicken.nei.api.API;
import com.builtbroken.mc.framework.mod.ModProxy;
import com.builtbroken.mc.framework.mod.Mods;
import com.builtbroken.mc.mods.ModCompatLoader;
import com.builtbroken.mc.mods.nei.large.Shaped4x4RecipeHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
    public void preInit()
    {
        Shaped4x4RecipeHandler handler = new Shaped4x4RecipeHandler();
        API.registerRecipeHandler(handler);
        API.registerUsageHandler(handler);
    }

    @Override
    public void postInit()
    {
        if (!hideItems.isEmpty() && !lock)
        {
            ModCompatLoader.instance.logger().info("Running NEI proxy");

            //Only run once as the list is static
            lock = true;

            for (Object entry : hideItems)
            {
                ModCompatLoader.instance.logger().info("\tHiding: " + entry);
                if (entry instanceof Block || entry instanceof Item)
                {
                    List list = new ArrayList();
                    if (entry instanceof Block)
                    {
                        ((Block) entry).getSubBlocks(Item.getItemFromBlock((Block) entry), ((Block) entry).getCreativeTabToDisplayOn(), list);
                    }
                    else
                    {
                        ((Item) entry).getSubItems((Item) entry, ((Item) entry).getCreativeTab(), list);
                    }
                    for (Object o : list)
                    {
                        if (o instanceof ItemStack)
                        {
                            API.hideItem((ItemStack) o);
                        }
                    }
                }
                else if (entry instanceof ItemStack)
                {
                    API.hideItem((ItemStack) entry);
                }
            }

            ModCompatLoader.instance.logger().info("Done...");
        }
    }

    @Override
    public boolean shouldLoad()
    {
        return FMLCommonHandler.instance().getSide() == Side.CLIENT && Mods.NEI.isLoaded();
    }
}
