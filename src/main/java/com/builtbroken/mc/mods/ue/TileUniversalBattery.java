package com.builtbroken.mc.mods.ue;

import com.builtbroken.mc.api.energy.IEnergyBuffer;
import com.builtbroken.mc.api.energy.IEnergyBufferProvider;
import com.builtbroken.mc.api.InjectTemplate;
import com.builtbroken.mc.core.References;
import com.builtbroken.mc.lib.helper.LanguageUtility;
import com.builtbroken.mc.imp.transform.vector.Pos;
import com.builtbroken.mc.prefab.energy.EnergyBuffer;
import com.builtbroken.mc.prefab.tile.TileEnt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * Demo battery for testing all power support in the development environment. Should not be extended, implemented, or used outside of the development environment.
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/16/2016.
 */
@InjectTemplate(integration = "RF-IEnergyHandler;IC-IEnergySink")
public class TileUniversalBattery extends TileEnt implements IEnergyBufferProvider
{
    //TODO move to testing repo
    private int energy = 0;
    private boolean energyHadChanged = true;

    //Settings
    private static int maxEnergy = 1000;
    private static int iconsArrayLength = 16;

    @SideOnly(Side.CLIENT)
    private static IIcon[] icons;
    private static IIcon topBotIcon;

    private EnergyBuffer buffer;

    public TileUniversalBattery()
    {
        super("DemoUEBattery", Material.iron);
        this.creativeTab = CreativeTabs.tabRedstone;
        this.resistance = 10;
        this.hardness = 10;
    }

    @Override
    public TileUniversalBattery newTile()
    {
        return new TileUniversalBattery();
    }

    @Override
    public void update()
    {
        super.update();
        if (isServer() && ticks % 10 == 0 && energyHadChanged)
        {
            energyHadChanged = false;
            float percent = (float) energy / (float) maxEnergy;
            int meta = (int) (percent * iconsArrayLength);
            world().setBlockMetadataWithNotify(xi(), yi(), zi(), meta, 3);
        }
    }

    @Override
    protected boolean onPlayerRightClick(EntityPlayer player, int side, Pos hit)
    {
        //Ignore clicks with block to allow easy building
        if (player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemBlock))
        {
            if (isServer())
            {
                player.addChatComponentMessage(new ChatComponentText(LanguageUtility.getLocal("text.power.amount").replace("%1", "" + energy).replace("%2", "" + maxEnergy)));
            }
            return true;
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return side == 0 || side == 1 ? topBotIcon : icons[meta];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        topBotIcon = iconRegister.registerIcon(References.PREFIX + "metal_diamond");
        icons = new IIcon[iconsArrayLength];
        icons[0] = iconRegister.registerIcon(References.PREFIX + "battery/battery");
        for (int i = 1; i < iconsArrayLength; i++)
        {
            icons[i] = iconRegister.registerIcon(References.PREFIX + "battery/battery" + i);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        energy = nbt.getInteger("energy");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("energy", energy);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int i = 0; i < 15; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public void onPlaced(EntityLivingBase entityLiving, ItemStack itemStack)
    {
        super.onPlaced(entityLiving, itemStack);
        TileEntity tile = world().getTileEntity(xi(), yi(), zi());
        if (itemStack.getItemDamage() > 0 && tile instanceof TileUniversalBattery)
        {
            ((TileUniversalBattery) tile).energy = (int) (maxEnergy * (itemStack.getItemDamage() / 14f));
        }
    }

    @Override
    public IEnergyBuffer getEnergyBuffer(ForgeDirection side)
    {
        if(buffer == null)
        {
            buffer = new EnergyBuffer(maxEnergy);
        }
        return buffer;
    }
}
