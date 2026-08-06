package mel.Polokalap.ffa.Commands;

import mel.Polokalap.ffa.GUI.AddArenaGUI;
import mel.Polokalap.ffa.GUI.EditArenaGUI;
import mel.Polokalap.ffa.GUI.EditArenasGUI;
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

import static mel.Polokalap.ffa.Listener.ArenaSelectionListener.pos1;
import static mel.Polokalap.ffa.Listener.ArenaSelectionListener.pos2;
import static mel.Polokalap.ffa.Main.getInstance;

public class ArenasCommand implements CommandExecutor, TabExecutor {

    public static HashMap<Player, Arena> openedArena = new HashMap<>();

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

        if (args.length < 1) {

            new EditArenasGUI().openGUI(player);
            return true;

        }

        if (!ArenaUtil.getArenaNames().contains(args[0].toLowerCase())) {

            player.sendMessage(ComponentUtil.getComponent("player.arena-doesnt-exist"));
            return true;

        }

        Arena selectedArena = null;

        for (Arena arena : ArenaUtil.getArenas()) {

            if (arena.getName().equalsIgnoreCase(args[0])) {

                selectedArena = arena;

            }

        }

        if (selectedArena == null) return true;

        openedArena.put(player, selectedArena);
        new EditArenaGUI().openGUI(player);

        return true;

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length == 1) return ArenaUtil.getArenaNames();
        return List.of();

    }

}
