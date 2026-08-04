package mel.Polokalap.ffa.GUI;

import mel.Polokalap.ffa.Commands.AddArenaCommand;
import mel.Polokalap.ffa.Utils.ComponentUtil;
import mel.Polokalap.ffa.Utils.ItemUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AddArenaGUI extends GUI implements InventoryHolder {

    @Override
    public void openGUI(Player player) {

        name = ComponentUtil.getComponent(
                "gui.add-arena.title",
                TagResolver.resolver(
                        Placeholder.unparsed("name", AddArenaCommand.arenaName.getOrDefault(player, "?"))
                )
        );
        size = 54;
        holder = this;

        super.openGUI(player);

        ConfigurationSection itemsSection = plugin.getConfig().getConfigurationSection("gui.add-arena.items");

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

    }

    @Override
    public Inventory getInventory() {

        return null;

    }

}
