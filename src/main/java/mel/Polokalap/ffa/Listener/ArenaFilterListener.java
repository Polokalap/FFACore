package mel.Polokalap.ffa.Listener;

import io.papermc.paper.event.inventory.ItemCraftedEvent;
import mel.Polokalap.ffa.Utils.Arena;
import mel.Polokalap.ffa.Utils.ArenaUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class ArenaFilterListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Arena arena = null;

        for (Arena arenas : ArenaUtil.getArenas()) {

            if (!arenas.inArea(block.getLocation())) continue;

            arena = arenas;
            break;

        }

        if (arena == null) return;
        if (!arena.getFilter().contains(block.getType())) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Arena arena = null;

        for (Arena arenas : ArenaUtil.getArenas()) {

            if (!arenas.inArea(block.getLocation())) continue;

            arena = arenas;
            break;

        }

        if (arena == null) return;
        if (!arena.getFilter().contains(block.getType())) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        Arena arena = null;

        for (Arena arenas : ArenaUtil.getArenas()) {

            if (!arenas.inArea(player.getLocation())) continue;

            arena = arenas;
            break;

        }

        if (item == null) return;
        if (arena == null) return;
        if (!arena.getFilter().contains(item.getType())) return;

        event.setCancelled(true);

    }

}
