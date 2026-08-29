package mel.Polokalap.ffa.GUI;

import mel.Polokalap.ffa.Main;
import mel.Polokalap.ffa.Utils.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUI implements InventoryHolder {

    Main plugin = Main.getInstance();
    FileConfiguration config = plugin.getConfig();

    int size;
    Component name;
    Inventory menu;
    InventoryHolder holder = this;

    boolean filler = true;

    public void openGUI(Player player) {

        openGUI(player, true);

    }

    public void openGUI(Player player, boolean sound) {

        menu = Bukkit.createInventory(holder, size, name);

        ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta emptyMeta = empty.getItemMeta();

        emptyMeta.setDisplayName(" ");
        emptyMeta.setHideTooltip(true);

        empty.setItemMeta(emptyMeta);

        for (int i = 0; i < menu.getSize(); i++) {

            if (filler) menu.setItem(i, empty);

        }

        player.openInventory(menu);

        if (sound) Sound.Open(player);

    }

    @Override
    public Inventory getInventory() {

        return null;

    }

}
