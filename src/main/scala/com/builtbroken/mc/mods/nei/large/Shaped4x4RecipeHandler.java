package com.builtbroken.mc.mods.nei.large;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.builtbroken.mc.core.Engine;
import com.builtbroken.mc.core.References;
import com.builtbroken.mc.framework.recipe.item.grid.RecipeShapedOreLarge;
import com.builtbroken.mc.prefab.inventory.InventoryUtility;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 8/25/2017.
 */
public class Shaped4x4RecipeHandler extends TemplateRecipeHandler
{
    public static String GUI_TEXTURE = References.PREFIX + References.GUI_DIRECTORY + "crafting_table_4x4.png";

    @Override
    public void loadTransferRects()
    {
        this.transferRects.add(new RecipeTransferRect(new Rectangle(94, 33, 24, 18), "crafting.4x4", new Object[0])); //TODO no clue what this does
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return null;
    }

    @Override
    public String getRecipeName()
    {
        return NEIClientUtils.translate("recipe.shaped", new Object[0]); //TODO use different translation?
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals("crafting.4x4") && this.getClass() == Shaped4x4RecipeHandler.class)
        {
            Iterator it = CraftingManager.getInstance().getRecipeList().iterator();

            while (it.hasNext())
            {
                IRecipe irecipe = (IRecipe) it.next();
                try
                {
                    if (irecipe instanceof RecipeShapedOreLarge)
                    {
                        //TODO only load 4x4 recipes, as each size will need a new visual set
                        Shaped4x4RecipeHandler.CachedShapedRecipe recipe = new CachedShapedRecipe((RecipeShapedOreLarge) irecipe);
                        recipe.computeVisuals();
                        this.arecipes.add(recipe);
                    }
                }
                catch (Exception e)
                {
                    Engine.logger().error("Shaped4x4RecipeHandler: Found bad RecipeShapedOreLarge while generating recipe list for NEI, recipe = " + irecipe, e);
                }
            }
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }

    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        final Iterator it = CraftingManager.getInstance().getRecipeList().iterator();

        while (it.hasNext())
        {
            final IRecipe irecipe = (IRecipe) it.next();

            //Match target to output
            if (InventoryUtility.stacksMatch(irecipe.getRecipeOutput(), result))
            {
                //Convert recipe
                if (irecipe instanceof RecipeShapedOreLarge)
                {
                    Shaped4x4RecipeHandler.CachedShapedRecipe recipe = new CachedShapedRecipe((RecipeShapedOreLarge) irecipe);
                    recipe.computeVisuals();
                    this.arecipes.add(recipe);
                }
            }
        }

    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        final Iterator it = CraftingManager.getInstance().getRecipeList().iterator();

        while (it.hasNext())
        {
            final IRecipe irecipe = (IRecipe) it.next();

            //Convert recipe
            Shaped4x4RecipeHandler.CachedShapedRecipe recipe = null;
            if (irecipe instanceof RecipeShapedOreLarge)
            {
                recipe = new CachedShapedRecipe((RecipeShapedOreLarge) irecipe);
            }

            //Match ingredients
            if (recipe != null && recipe.contains(recipe.ingredients, ingredient.getItem()))
            {
                recipe.computeVisuals();
                if (recipe.contains(recipe.ingredients, ingredient))
                {
                    recipe.setIngredientPermutation(recipe.ingredients, ingredient);
                    this.arecipes.add(recipe);
                }
            }
        }
    }

    @Override
    public int recipiesPerPage()
    {
        return 1;
    }

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(16, 5, 0, 0, 134, 72);
    }

    @Override
    public void drawForeground(int recipe)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(2896);
        //GuiDraw.changeTexture(this.getGuiTexture());
        this.drawExtras(recipe);
    }

    @Override
    public void drawExtras(int recipe)
    {

    }

    @Override
    public String getGuiTexture()
    {
        return GUI_TEXTURE;
    }

    @Override
    public String getOverlayIdentifier()
    {
        return References.PREFIX + "crafting.4x4";
    }

    @Override
    public boolean hasOverlay(GuiContainer gui, Container container, int recipe)
    {
        return super.hasOverlay(gui, container, recipe);
    }

    @Override
    public IRecipeOverlayRenderer getOverlayRenderer(GuiContainer gui, int recipe)
    {
        return super.getOverlayRenderer(gui, recipe);
    }

    @Override
    public IOverlayHandler getOverlayHandler(GuiContainer gui, int recipe)
    {
        return super.getOverlayHandler(gui, recipe);
    }

    /**
     * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
     * Created by Dark(DarkGuardsman, Robert) on 8/25/2017.
     */
    public class CachedShapedRecipe extends TemplateRecipeHandler.CachedRecipe
    {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public CachedShapedRecipe(int width, int height, Object[] items, ItemStack out)
        {
            this.result = new PositionedStack(out, 129, 34);
            this.ingredients = new ArrayList();
            this.setIngredients(width, height, items);
        }

        public CachedShapedRecipe(RecipeShapedOreLarge recipe)
        {
            this(recipe.width, recipe.height, recipe.input, recipe.getRecipeOutput());
        }


        public void setIngredients(int width, int height, Object[] items)
        {
            for (int x = 0; x < width; ++x)
            {
                for (int y = 0; y < height; ++y)
                {
                    if (items[y * width + x] != null)
                    {
                        PositionedStack stack = new PositionedStack(items[y * width + x], 17 + x * 18, 6 + y * 18, false);
                        stack.setMaxSize(1);
                        this.ingredients.add(stack);
                    }
                }
            }
        }

        @Override
        public List<PositionedStack> getIngredients()
        {
            return this.getCycledIngredients(Shaped4x4RecipeHandler.this.cycleticks / 20, this.ingredients);
        }

        @Override
        public PositionedStack getResult()
        {
            return this.result;
        }

        public void computeVisuals()
        {
            Iterator it = this.ingredients.iterator();

            while (it.hasNext())
            {
                PositionedStack p = (PositionedStack) it.next();
                p.generatePermutations();
            }
        }
    }
}
