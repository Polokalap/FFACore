package mel.Polokalap.ffa.GUI;

import mel.Polokalap.ffa.Utils.Arena;
import mel.Polokalap.ffa.Utils.ArenaUtil;
import mel.Polokalap.ffa.Utils.ComponentUtil;
import mel.Polokalap.ffa.Utils.ItemUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EditArenasGUI extends GUI implements InventoryHolder {

    @Override
    public void openGUI(Player player) {

        name = ComponentUtil.getComponent("gui.edit-arenas.title");
        size = 54;
        holder = this;

        super.openGUI(player);

        ConfigurationSection itemsSection = plugin.getConfig().getConfigurationSection("gui.edit-arenas.items");

        for (String key : itemsSection.getKeys(false)) {

            ConfigurationSection section = itemsSection.getConfigurationSection(key);

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
                        MiniMessage.miniMessage().deserialize(
                                line
                        ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                );

            }

            meta.lore(lore);
            ItemUtil.assignPDC(section.getString("key", "default"), meta);

            item.setItemMeta(meta);

            menu.setItem(section.getInt("slot", 0), item);

        }

        int i = 0;

        for (Arena arena : ArenaUtil.getArenas()) {

            ItemStack item = new ItemStack(Material.MAP);
            ItemMeta meta = item.getItemMeta();

            meta.displayName(
                    MiniMessage.miniMessage().deserialize(
                            arena.getName()
                    )
                            .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                            .color(TextColor.color(0x00b7ff))
            );

            ItemUtil.assignPDC("arena", meta);

            item.setItemMeta(meta);
            menu.setItem(i, item);

            i++;

        }

    }

    @Override
    public Inventory getInventory() {

        return null;

    }

}
