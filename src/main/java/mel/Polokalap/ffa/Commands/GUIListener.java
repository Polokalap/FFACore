package mel.Polokalap.ffa.Commands;

import mel.Polokalap.ffa.GUI.AddArenaGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GUIListener implements Listener {

    private static HashMap<Player, Long> lastClicked = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();

        if (!(inv.getHolder() instanceof AddArenaGUI gui)) return;

        event.setCancelled(true);

        long now = System.currentTimeMillis();

        if (lastClicked.getOrDefault(player, 0L) + 200 > now) return;
        lastClicked.put(player, now);

        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        player.sendMessage("meow");

    }

}
