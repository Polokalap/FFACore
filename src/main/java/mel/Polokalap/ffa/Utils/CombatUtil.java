package mel.Polokalap.ffa.Utils;

import org.bukkit.entity.Player;

import static mel.Polokalap.ffa.Listener.CombatListener.lastAttack;
import static mel.Polokalap.ffa.Main.getInstance;

public class CombatUtil {

    public static boolean isInCombat(Player player) {

        long now = System.currentTimeMillis();
        long timestamp = lastAttack.getOrDefault(player, 0L);
        long limit = getInstance().getConfig().getLong("settings.combat") * 1000;

        return timestamp + limit - now > 0;

    }

}