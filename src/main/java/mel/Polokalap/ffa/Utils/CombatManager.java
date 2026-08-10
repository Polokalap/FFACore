package mel.Polokalap.ffa.Utils;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

import static mel.Polokalap.ffa.Main.getInstance;

public class CombatManager {

    public static HashMap<Player, Long> lastAttack = new HashMap<>();
    public static HashMap<Player, Player> lastOpponent = new HashMap<>();

    public static void init() {

        new BukkitRunnable() {

            @Override
            public void run() {

                ArrayList<Player> players = new ArrayList<>();

                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (isInCombat(player)) players.add(player);

                }

                for (Player player : players) {

                    if (getCombatTime(player) == 0.1) {

                        Bukkit.getScheduler().runTaskLater(getInstance(), () -> {

                            endCombat(player);

                        }, 2L);

                    }

                    if (getCombatTime(player) <= 6.0) {

                        player.sendActionBar(
                                ComponentUtil.getComponent(
                                        "player.combat-time",
                                        TagResolver.resolver(
                                                Placeholder.unparsed("time", String.format("%.1f", getCombatTime(player) - 0.1))
                                        )
                                )
                        );

                    }

                }

            }

        }.runTaskTimer(getInstance(), 0L, 2L);

    }

    public static boolean isInCombat(Player player) {

        long now = System.currentTimeMillis();
        long timestamp = lastAttack.getOrDefault(player, 0L);
        long limit = getInstance().getConfig().getLong("settings.combat") * 1000;

        return Math.round((timestamp + limit - now) / 100.0) / 10.0 > 0;

    }

    public static double getCombatTime(Player player) {

        long now = System.currentTimeMillis();
        long timestamp = lastAttack.getOrDefault(player, 0L);
        long limit = getInstance().getConfig().getLong("settings.combat") * 1000;

        return Math.round((timestamp + limit - now) / 100.0) / 10.0;

    }

    public static void combatAction(Player player, Player damager) {

        if (!CombatManager.isInCombat(player)) player.sendMessage(ComponentUtil.getComponent("player.now-in-combat"));
        if (!CombatManager.isInCombat(damager)) damager.sendMessage(ComponentUtil.getComponent("player.now-in-combat"));

        lastAttack.put(player, System.currentTimeMillis());
        lastAttack.put(damager, System.currentTimeMillis());

        lastOpponent.put(player, damager);
        lastOpponent.put(damager, player);
    }

    public static void endCombat(Player player) {

        lastAttack.put(player, 0L);
        player.sendMessage(ComponentUtil.getComponent("player.combat-left"));

        Player opponent = lastOpponent.get(player);

        if (!opponent.isOnline()) return;
        if (lastOpponent.get(opponent).equals(player)) {

            lastOpponent.remove(player);
            endCombat(opponent);

        }

    }

}