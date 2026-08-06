package mel.Polokalap.ffa.Listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import mel.Polokalap.ffa.Commands.ArenasCommand;
import mel.Polokalap.ffa.GUI.EditArenaGUI;
import mel.Polokalap.ffa.Main;
import mel.Polokalap.ffa.Utils.*;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class EditArenaGUIListener implements Listener {

    private static HashMap<Player, Long> lastClicked = new HashMap<>();
    private static HashMap<Player, Boolean> isTyping = new HashMap<>();
    private static HashMap<Player, String> sentMessage = new HashMap<>();
    private static Main plugin = Main.getInstance();

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();

        if (inv == null) return;

        if (!(inv.getHolder() instanceof EditArenaGUI gui)) return;

        event.setCancelled(true);

        long now = System.currentTimeMillis();

        if (lastClicked.getOrDefault(player, 0L) + 200 > now) return;
        lastClicked.put(player, now);

        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();

        Arena arena = ArenasCommand.openedArena.get(player);
        ConfigurationSection itemsSection = plugin.getConfig().getConfigurationSection("gui.add-arena.items");

        if (ItemUtil.hasPDC("set-arena-name", item)) {

            isTyping.put(player, true);
            player.closeInventory();
            Sound.Click(player);
            player.sendMessage(ComponentUtil.getComponent("player.type-name"));

        }

        if (ItemUtil.hasPDC("delete", item)) {

            player.closeInventory();
            Sound.Ping(player);
            player.sendMessage(
                    ComponentUtil.getComponent(
                            "player.arena-deleted",
                            TagResolver.resolver(
                                    Placeholder.unparsed("name", arena.getName())
                            )
                    )
            );

            arena.remove();

        }

        if (ItemUtil.hasPDC("regenerate", item)) {

            Sound.Click(player);
            player.sendMessage(ComponentUtil.getComponent("player.arena-regenerated"));
            arena.regenerate();

        }

        if (ItemUtil.hasPDC("warp", item)) {

            Sound.Click(player);
            player.teleport(arena.getSpawn());
            arena.regenerate();

        }

    }

    @EventHandler
    public void onMessage(AsyncChatEvent event) {

        Player player = event.getPlayer();

        if (!isTyping.getOrDefault(player, false)) return;
        isTyping.put(player, false);

        sentMessage.put(player, MiniMessage.miniMessage().serialize(event.message()));
        ArenasCommand.openedArena.get(player).setName(MiniMessage.miniMessage().serialize(event.message()));

        Bukkit.getScheduler().runTask(plugin, () -> {

            new EditArenaGUI().openGUI(player);
            ArenasCommand.openedArena.get(player).writeToFile();

        });

        Sound.Ping(player);

        event.setCancelled(true);

    }

}
