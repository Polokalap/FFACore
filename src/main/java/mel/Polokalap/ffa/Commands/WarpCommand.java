package mel.Polokalap.ffa.Commands;

import mel.Polokalap.ffa.Utils.*;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static mel.Polokalap.ffa.Main.getInstance;

public class WarpCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {

            getInstance().getLogger().info(getInstance().getConfig().getString("console.player"));
            return true;

        }

        if (args.length < 1) {

            return true;

        }

        if (!ArenaUtil.getArenaNames().contains(args[0].toLowerCase())) {

            player.sendMessage(ComponentUtil.getComponent("player.arena-doesnt-exist"));
            return true;

        }

        Arena arena = null;

        for (Arena tempArena : ArenaUtil.getArenas()) {

            if (tempArena.getName().equalsIgnoreCase(args[0])) {

                arena = tempArena;

            }

        }

        if (arena == null) return true;

        if (!player.hasPermission("ffa.warp." + arena.getName())) {

            player.sendMessage(ComponentUtil.getComponent("player.permission"));
            return true;

        }

        if (CombatManager.isInCombat(player)) {

            player.sendMessage(ComponentUtil.getComponent("player.combat"));
            return true;

        }

        Sound.Ping(player);
        player.sendMessage(
                ComponentUtil.getComponent(
                        "player.teleported",
                        TagResolver.resolver(
                                Placeholder.unparsed("name", arena.getName())
                        )
                )
        );
        player.teleport(arena.getSpawn());

        return true;

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) return List.of();
        if (args.length == 1) return ArenaUtil.getArenaNamesWithAccess(player);
        return List.of();

    }

}
