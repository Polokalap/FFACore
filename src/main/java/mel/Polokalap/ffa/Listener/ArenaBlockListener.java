package mel.Polokalap.ffa.Listener;

import mel.Polokalap.ffa.Utils.Arena;
import mel.Polokalap.ffa.Utils.ArenaUtil;
import mel.Polokalap.ffa.Utils.States;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import static mel.Polokalap.ffa.Main.getInstance;

public class ArenaBlockListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();

        if (player.hasPermission("ffa.admin") && !player.getGameMode().equals(GameMode.SURVIVAL)) return;

        if (event.getBlock().hasMetadata("player-breakable")) return;

        for (Arena arena : ArenaUtil.getArenas()) {

            if (arena.inArea(event.getBlock().getLocation())) {

                if (
                    arena.getBlockState().equals(States.BlockState.BREAK) ||
                    arena.getBlockState().equals(States.BlockState.BOTH)
                ) continue;

                event.setCancelled(true);

            }

        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();

        if (player.hasPermission("ffa.admin")) return;

        for (Arena arena : ArenaUtil.getArenas()) {

            if (arena.inArea(event.getBlock().getLocation())) {

                if (
                    arena.getBlockState().equals(States.BlockState.PLACE) ||
                    arena.getBlockState().equals(States.BlockState.BOTH)
                ) {

                    Block block = event.getBlockPlaced();
                    Material type = block.getType();

                    block.setMetadata("player-breakable", new FixedMetadataValue(getInstance(), "player-breakable"));

                    if (!arena.getDecay().equals(States.DecayTime.NEVER)) {

                        Bukkit.getScheduler().runTaskLater(getInstance(), () -> {

                            if (block.getType() != type) return;

                            block.setType(Material.AIR);
                            block.removeMetadata("player-breakable", getInstance());

                        }, arena.getDecay().toTicks());

                    }

                    continue;

                }

                event.setCancelled(true);

            }

        }

    }

}
