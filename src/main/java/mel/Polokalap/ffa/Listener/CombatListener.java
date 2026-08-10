package mel.Polokalap.ffa.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;

public class CombatListener implements Listener {

    public static HashMap<Player, Long> lastAttack = new HashMap<>();

    @EventHandler
    public void onCombat(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player damager)) return;
        if (!(event.getEntity() instanceof Player player)) return;

        lastAttack.put(damager, System.currentTimeMillis());
        lastAttack.put(player, System.currentTimeMillis());

    }

}
