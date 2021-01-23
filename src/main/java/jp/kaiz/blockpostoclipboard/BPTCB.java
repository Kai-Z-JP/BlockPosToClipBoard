package jp.kaiz.blockpostoclipboard;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

@Mod(modid = BPTCB.MODID, version = BPTCB.VERSION, name = BPTCB.MODID)
public class BPTCB extends Item {
    public final static String MODID = "bptcb";
    public final static String VERSION = "1.0";
    private boolean commaMode;

    public BPTCB() {
        setCreativeTab(CreativeTabs.tabTools);
        setUnlocalizedName(BPTCB.MODID + ":" + "itemBPTCB");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        if (world.isRemote) {
            this.commaMode ^= true;
            player.addChatMessage(new ChatComponentText(String.format("CommaMode=%s", this.commaMode)));
        }
        return itemstack;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float p, float q, float r) {
        if (world.isRemote) {
            String pos = String.format(this.commaMode ? "%s, %s, %s" : "%s %s %s", x, y, z);
            player.addChatMessage(new ChatComponentText(String.format("Copied! (%s)", pos)));
            GuiScreen.setClipboardString(pos);
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon(BPTCB.MODID + ":" + "itembptcb");
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerItem(new BPTCB(), BPTCB.MODID + ":" + "itemBPTCB");
    }
}
