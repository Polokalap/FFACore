package mel.Polokalap.ffa.Commands;

import mel.Polokalap.ffa.Utils.ComponentUtil;
import mel.Polokalap.ffa.Utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static mel.Polokalap.ffa.Main.getInstance;

public class SelectArenaCommand implements CommandExecutor, TabExecutor {

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

        ConfigurationSection wandSection = getInstance().getConfig().getConfigurationSection("items.selection-wand");

        ItemStack selectionWand = new ItemStack(Material.valueOf(wandSection.getString("item")));
        ItemMeta selectionMeta = selectionWand.getItemMeta();
        ItemUtil.assignPDC("selection-wand", selectionMeta);
        selectionMeta.displayName(ComponentUtil.getComponent("items.selection-wand.name"));
        selectionWand.setItemMeta(selectionMeta);

        player.getInventory().addItem(selectionWand);

        player.sendMessage(ComponentUtil.getComponent("player.item-given"));

        return true;

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        return List.of();

    }

}
