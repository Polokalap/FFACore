package mel.Polokalap.ffa.GUI;

import mel.Polokalap.ffa.Utils.*;
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

import java.util.HashMap;

public class FilterItemsGUI extends GUI implements InventoryHolder {

    public static HashMap<Player, Arena> openedArena = new HashMap<>();
    public static HashMap<Player, Integer> playerPage = new HashMap<>();

    @Override
    public void openGUI(Player player, boolean sound) {

        Arena arena = openedArena.get(player);
        name = ComponentUtil.getComponent(
                "gui.edit-filter.title",
                TagResolver.resolver(
                        Placeholder.unparsed("name", arena.getName()),
                        Placeholder.unparsed("current", String.valueOf(playerPage.get(player))),
                        Placeholder.unparsed("max", String.valueOf(Math.max((arena.getFilter().size() + 44) / 45, 1)))
                )
        );
        size = 54;
        holder = this;

        super.openGUI(player, sound);

        for (int slot = 45; slot < 54; slot++) {

            ItemStack item = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
            ItemMeta meta = item.getItemMeta();

            meta.displayName(MiniMessage.miniMessage().deserialize(" "));
            meta.setHideTooltip(true);

            item.setItemMeta(meta);

            menu.setItem(slot, item);

        }

        ConfigurationSection itemsSection = plugin.getConfig().getConfigurationSection("gui.edit-filter.items");

        if (playerPage.get(player) != 1) ItemUtil.addItem(config, itemsSection, "last-page", menu);
        ItemUtil.addItem(config, itemsSection, "close", menu);
        if (playerPage.get(player) < Math.max((arena.getFilter().size() + 44) / 45, 1)) ItemUtil.addItem(config, itemsSection, "next-page", menu);

        int slot = 0;
        int i = 0;

        for (Material material : arena.getFilter()) {

            i++;
            if ((playerPage.get(player) - 1) * 46 > i) continue;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            ItemUtil.assignPDC("filter-item", meta);

            item.setItemMeta(meta);

            menu.setItem(slot, item);

            if (slot >= 44) break;
            slot++;

        }

    }

    @Override
    public Inventory getInventory() {

        return null;

    }

}
