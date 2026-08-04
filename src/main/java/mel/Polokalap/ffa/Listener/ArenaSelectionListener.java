package mel.Polokalap.ffa.Listener;

import mel.Polokalap.ffa.Utils.ComponentUtil;
import mel.Polokalap.ffa.Utils.ItemUtil;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ArenaSelectionListener implements Listener {

    public static HashMap<Player, Location> pos1 = new HashMap<>();
    public static HashMap<Player, Location> pos2 = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!ItemUtil.hasPDC("selection-wand", item)) return;
        if (!player.hasPermission("ffa.admin")) {

            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
            return;

        }

        event.setCancelled(true);

        Location loc = event.getBlock().getLocation();
        if (pos1.get(player) != null && pos1.get(player).equals(loc)) return;
        pos1.put(player, loc);
        player.sendMessage(
                ComponentUtil.getComponent(
                        "player.primary",
                        TagResolver.resolver(
                                Placeholder.unparsed("x", String.valueOf(Math.round(loc.getX()))),
                                Placeholder.unparsed("y", String.valueOf(Math.round(loc.getY()))),
                                Placeholder.unparsed("z", String.valueOf(Math.round(loc.getZ())))
                        )
                )
        );

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!event.hasBlock()) return;
        if (event.getClickedBlock() == null) return;
        if (!event.getAction().isRightClick()) return;
        if (!ItemUtil.hasPDC("selection-wand", item)) return;
        if (!player.hasPermission("ffa.admin")) {

            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
            return;

        }

        Location loc = event.getClickedBlock().getLocation();
        if (pos2.get(player) != null && pos2.get(player).equals(loc)) return;
        pos2.put(player, loc);
        player.sendMessage(
                ComponentUtil.getComponent(
                        "player.secondary",
                        TagResolver.resolver(
                                Placeholder.unparsed("x", String.valueOf(Math.round(loc.getX()))),
                                Placeholder.unparsed("y", String.valueOf(Math.round(loc.getY()))),
                                Placeholder.unparsed("z", String.valueOf(Math.round(loc.getZ())))
                        )
                )
        );

    }

}
