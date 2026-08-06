package mel.Polokalap.ffa.Listener;

import mel.Polokalap.ffa.Commands.ArenasCommand;
import mel.Polokalap.ffa.GUI.EditArenaGUI;
import mel.Polokalap.ffa.GUI.EditArenasGUI;
import mel.Polokalap.ffa.Main;
import mel.Polokalap.ffa.Utils.Arena;
import mel.Polokalap.ffa.Utils.ArenaUtil;
import mel.Polokalap.ffa.Utils.ItemUtil;
import mel.Polokalap.ffa.Utils.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class EditArenasGUIListener implements Listener {

    private static HashMap<Player, Long> lastClicked = new HashMap<>();
    private static Main plugin = Main.getInstance();

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();

        if (inv == null) return;

        if (!(inv.getHolder() instanceof EditArenasGUI gui)) return;

        event.setCancelled(true);

        long now = System.currentTimeMillis();

        if (lastClicked.getOrDefault(player, 0L) + 200 > now) return;
        lastClicked.put(player, now);

        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();

        ConfigurationSection itemsSection = plugin.getConfig().getConfigurationSection("gui.add-arenas.items");

        if (ItemUtil.hasPDC("arena", item)) {

            Arena arena = ArenaUtil.getArenas().get(event.getSlot());
            ArenasCommand.openedArena.put(player, arena);
            new EditArenaGUI().openGUI(player);

        }

        if (ItemUtil.hasPDC("close", item)) {

            player.closeInventory();
            Sound.Close(player);

        }

    }

}
