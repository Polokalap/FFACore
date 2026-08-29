package mel.Polokalap.ffa.Listener;

import mel.Polokalap.ffa.Commands.ArenasCommand;
import mel.Polokalap.ffa.GUI.EditArenaGUI;
import mel.Polokalap.ffa.GUI.FilterItemsGUI;
import mel.Polokalap.ffa.Main;
import mel.Polokalap.ffa.Utils.*;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterItemsGUIListener implements Listener {

    private static HashMap<Player, Long> lastClicked = new HashMap<>();
    private static Main plugin = Main.getInstance();

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();

        if (inv == null) return;

        if (!(event.getView().getTopInventory().getHolder() instanceof FilterItemsGUI gui)) return;

        event.setCancelled(true);

        long now = System.currentTimeMillis();

        if (lastClicked.getOrDefault(player, 0L) + 200 > now) return;
        lastClicked.put(player, now);

        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();

        Arena arena = ArenasCommand.openedArena.get(player);
        ConfigurationSection itemsSection = plugin.getConfig().getConfigurationSection("gui.edit-filter.items");

        if (ItemUtil.hasPDC("last-page", item)) {

            FilterItemsGUI.playerPage.put(player, FilterItemsGUI.playerPage.get(player) - 1);
            new FilterItemsGUI().openGUI(player, false);
            Sound.Click(player);

        }

        if (ItemUtil.hasPDC("next-page", item)) {

            FilterItemsGUI.playerPage.put(player, FilterItemsGUI.playerPage.get(player) + 1);
            new FilterItemsGUI().openGUI(player, false);
            Sound.Click(player);

        }

        if (ItemUtil.hasPDC("close", item)) {

            new EditArenaGUI().openGUI(player);

        }

        if (event.getView().getTopInventory().contains(item)) {

            if (!ItemUtil.hasPDC("filter-item", item)) return;

            ArrayList<Material> newFilter = arena.getFilter();
            newFilter.remove(item.getType());
            arena.setFilter(newFilter);
            Sound.Silent(player);
            player.getInventory().addItem(new ItemStack(item.getType()));
            new FilterItemsGUI().openGUI(player, false);

        }

        if (event.getView().getBottomInventory().contains(item)) {

            if (!item.getType().isBlock()) {

                player.sendMessage(ComponentUtil.getComponent("player.not-block"));
                Sound.Error(player);
                return;

            }

            if (arena.getFilter().contains(item.getType())) {

                player.sendMessage(ComponentUtil.getComponent("player.contains-block"));
                Sound.Error(player);
                return;

            }

            ArrayList<Material> newFilter = arena.getFilter();
            newFilter.add(item.getType());
            arena.setFilter(newFilter);
            Sound.Silent(player);
            item.setAmount(item.getAmount() - 1);
            player.updateInventory();
            new FilterItemsGUI().openGUI(player, false);

        }

    }

}
