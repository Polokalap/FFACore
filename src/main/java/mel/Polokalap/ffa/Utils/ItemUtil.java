package mel.Polokalap.ffa.Utils;

import mel.Polokalap.ffa.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    private static Main plugin = Main.getInstance();
    private static FileConfiguration config = plugin.getConfig();

    public static boolean hasPDC(String key, ItemStack item) {

        if (!item.hasItemMeta() || item.getItemMeta() == null) return false;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        NamespacedKey usedKey = new NamespacedKey(plugin, key);

        return pdc.has(usedKey, PersistentDataType.INTEGER);

    }

    public static void assignPDC(String key, ItemMeta meta) {

        NamespacedKey newKey = new NamespacedKey(plugin, key);
        meta.getPersistentDataContainer().set(newKey, PersistentDataType.INTEGER, 42);

    }

    public static void removePDC(ItemStack item, NamespacedKey key) {

        if (item == null || !item.hasItemMeta() || key == null) return;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.remove(key);
        item.setItemMeta(meta);

    }

    public static ItemStack getConfigItem(FileConfiguration config, String key) {

        ConfigurationSection itemsSection = config.getConfigurationSection("gui.top-clans.items");
        if (itemsSection == null) {
            return null;
        }

        ConfigurationSection section = itemsSection.getConfigurationSection(key);
        if (section == null) {
            return null;
        }

        ItemStack item = new ItemStack(Material.valueOf(section.getString("type", "EGG")));
        ItemMeta meta = item.getItemMeta();

        meta.displayName(
                MiniMessage.miniMessage().deserialize(
                        section.getString("name", "?")
                ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
        );

        List<Component> lore = new ArrayList<>();
        for (String line : section.getStringList("lore")) {
            lore.add(
                    MiniMessage.miniMessage().deserialize(line)
                            .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
            );
        }
        meta.lore(lore);

        ItemUtil.assignPDC(section.getString("key", "default"), meta);

        item.setItemMeta(meta);

        return item;

    }

    public static ItemStack addItem(FileConfiguration config, ConfigurationSection itemsSection, String key, Inventory menu) {

        if (itemsSection == null) {
            return null;
        }

        ConfigurationSection section = itemsSection.getConfigurationSection(key);
        if (section == null) {
            return null;
        }

        ItemStack item = new ItemStack(Material.valueOf(section.getString("type", "EGG")));
        ItemMeta meta = item.getItemMeta();

        meta.displayName(
                MiniMessage.miniMessage().deserialize(
                        section.getString("name", "?")
                ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
        );

        List<Component> lore = new ArrayList<>();
        for (String line : section.getStringList("lore")) {
            lore.add(
                    MiniMessage.miniMessage().deserialize(line)
                            .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
            );
        }
        meta.lore(lore);

        ItemUtil.assignPDC(section.getString("key", "default"), meta);

        item.setItemMeta(meta);

        menu.setItem(section.getInt("slot", 0), item);

        return item;

    }

}
