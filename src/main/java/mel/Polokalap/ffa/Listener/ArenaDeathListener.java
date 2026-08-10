package mel.Polokalap.ffa.Listener;

import mel.Polokalap.ffa.Utils.Arena;
import mel.Polokalap.ffa.Utils.ArenaUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ArenaDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getPlayer();

        for (Arena arena : ArenaUtil.getArenas()) {

            if (!arena.inArea(player)) continue;

            if (arena.getDrops()) continue;

            event.setShouldDropExperience(false);
            event.getDrops().clear();

        }

    }

}
