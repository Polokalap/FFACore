package mel.Polokalap.ffa.Listener;

import mel.Polokalap.ffa.Utils.ArenaUtil;
import mel.Polokalap.ffa.Utils.CombatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CombatListener implements Listener {

    @EventHandler
    public void onCombat(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player player)) return;
        if (!(event.getDamager() instanceof Player damager)) return;

        if (!ArenaUtil.inAnyArena(player) || !ArenaUtil.inAnyArena(damager)) return;

        CombatManager.combatAction(player, damager);

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getPlayer();

        CombatManager.endCombat(player);

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (CombatManager.isInCombat(player)) player.setHealth(0.0d);

    }

}
