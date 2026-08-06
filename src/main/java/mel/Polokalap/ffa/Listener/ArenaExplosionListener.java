package mel.Polokalap.ffa.Listener;

import mel.Polokalap.ffa.Utils.Arena;
import mel.Polokalap.ffa.Utils.ArenaUtil;
import mel.Polokalap.ffa.Utils.States;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Iterator;

public class ArenaExplosionListener implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {

        Iterator<Block> iterator = event.blockList().iterator();

        while (iterator.hasNext()) {

            Block block = iterator.next();

            if (block.hasMetadata("player-breakable")) {
                continue;
            }

            for (Arena arena : ArenaUtil.getArenas()) {

                if (arena.inArea(block.getLocation())) {

                    if (
                        arena.getExplosionState() == States.ExplosionState.DAMAGE ||
                        arena.getExplosionState() == States.ExplosionState.NONE
                    ) {

                        iterator.remove();

                    }

                    break;

                }

            }

        }

    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {

        Iterator<Block> iterator = event.blockList().iterator();

        while (iterator.hasNext()) {

            Block block = iterator.next();

            if (block.hasMetadata("player-breakable")) {
                continue;
            }

            for (Arena arena : ArenaUtil.getArenas()) {

                if (arena.inArea(block.getLocation())) {

                    if (
                            arena.getExplosionState() == States.ExplosionState.DAMAGE ||
                            arena.getExplosionState() == States.ExplosionState.NONE
                    ) {

                        iterator.remove();

                    }

                    break;

                }

            }

        }

    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        Entity entity = event.getEntity();

        if (!(entity instanceof Player player)) return;
        if (
            event.getCause() != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION &&
            event.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
        ) return;

        for (Arena arena : ArenaUtil.getArenas()) {

            if (
                arena.getExplosionState() == States.ExplosionState.DAMAGE ||
                arena.getExplosionState() == States.ExplosionState.BOTH
            ) return;

            if (arena.inArea(player)) {

                event.setCancelled(true);
                return;

            }

        }

    }

}
