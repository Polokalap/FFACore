package mel.Polokalap.ffa.Commands;

import mel.Polokalap.ffa.GUI.AddArenaGUI;
import mel.Polokalap.ffa.Listener.AddGUIListener;
import mel.Polokalap.ffa.Utils.Arena;
import mel.Polokalap.ffa.Utils.ArenaUtil;
import mel.Polokalap.ffa.Utils.ComponentUtil;
import mel.Polokalap.ffa.Utils.States;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static mel.Polokalap.ffa.Listener.ArenaSelectionListener.pos1;
import static mel.Polokalap.ffa.Listener.ArenaSelectionListener.pos2;
import static mel.Polokalap.ffa.Main.getInstance;

public class AddArenaCommand implements CommandExecutor, TabExecutor {

    public static HashMap<Player, Arena> arena = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {

            getInstance().getLogger().info(getInstance().getConfig().getString("console.player"));
            return true;

        }

        if (!player.hasPermission("ffa.admin")) {

            player.sendMessage(ComponentUtil.getComponent("player.permission"));
            return true;

        }

        if (
                pos1.get(player) == null ||
                pos2.get(player) == null
        ) {

            player.sendMessage(ComponentUtil.getComponent("player.missing-selection"));
            return true;

        }

        if (args.length < 1) {

            player.sendMessage(ComponentUtil.getComponent("player.missing-name"));
            return true;

        }

        if (ArenaUtil.getArenaNames().contains(args[0].toLowerCase())) {

            player.sendMessage(ComponentUtil.getComponent("player.arena-already-exists"));
            return true;

        }

        if (ArenaUtil.getArenas().size() >= 45) {

            player.sendMessage(ComponentUtil.getComponent("player.too-many-arenas"));
            return true;

        }

        AddGUIListener.isTyping.put(player, false);

        arena.put(
                player,
                new Arena(
                        UUID.randomUUID(),
                        args[0].toLowerCase(),
                        player.getLocation(),
                        pos1.get(player),
                        pos2.get(player),
                        States.BlockState.NONE,
                        States.DecayTime.THREE_MINUTES,
                        States.RegenerationTime.NEVER,
                        States.ExplosionState.BOTH
                )
        );

        new AddArenaGUI().openGUI(player);

        return true;

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        return List.of();

    }

}
