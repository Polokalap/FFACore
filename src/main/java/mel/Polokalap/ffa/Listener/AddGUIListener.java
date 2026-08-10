package mel.Polokalap.ffa.Listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import mel.Polokalap.ffa.Commands.AddArenaCommand;
import mel.Polokalap.ffa.GUI.AddArenaGUI;
import mel.Polokalap.ffa.Main;
import mel.Polokalap.ffa.Utils.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static mel.Polokalap.ffa.Commands.AddArenaCommand.arena;

public class AddGUIListener implements Listener {

    private static HashMap<Player, Long> lastClicked = new HashMap<>();
    public static HashMap<Player, Boolean> isTyping = new HashMap<>();
    private static HashMap<Player, String> sentMessage = new HashMap<>();
    private static Main plugin = Main.getInstance();

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();

        if (inv == null) return;

        if (!(inv.getHolder() instanceof AddArenaGUI gui)) return;

        event.setCancelled(true);

        long now = System.currentTimeMillis();

        if (lastClicked.getOrDefault(player, 0L) + 200 > now) return;
        lastClicked.put(player, now);

        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();

        Arena arena = AddArenaCommand.arena.get(player);
        ConfigurationSection itemsSection = plugin.getConfig().getConfigurationSection("gui.add-arena.items");

        if (ItemUtil.hasPDC("set-arena-name", item)) {

            isTyping.put(player, true);
            player.closeInventory();
            Sound.Click(player);
            player.sendMessage(ComponentUtil.getComponent("player.type-name"));

        }

        if (ItemUtil.hasPDC("block-breaking-state", item)) {

            arena.setBlockState(arena.getBlockState().next());
            List<Component> lore = new ArrayList<>();

            for (String line : itemsSection.getStringList("block-break.lore")) {

                lore.add(
                        MiniMessage.miniMessage().deserialize(
                                line,
                                TagResolver.resolver(
                                        Placeholder.component("state",
                                                MiniMessage.miniMessage().deserialize(
                                                        States.getString(arena.getBlockState()))
                                        )
                                )
                        ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                );

            }

            meta.lore(lore);
            item.setItemMeta(meta);

            Sound.Click(player);

        }

        if (ItemUtil.hasPDC("block-decay", item)) {

            arena.setDecayTime(arena.getDecay().next());
            List<Component> lore = new ArrayList<>();

            for (String line : itemsSection.getStringList("block-decay.lore")) {

                lore.add(
                        MiniMessage.miniMessage().deserialize(
                                line,
                                TagResolver.resolver(
                                        Placeholder.component("state",
                                                MiniMessage.miniMessage().deserialize(
                                                        States.getString(arena.getDecay()))
                                        )
                                )
                        ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                );

            }

            meta.lore(lore);
            item.setItemMeta(meta);

            Sound.Click(player);

        }

        if (ItemUtil.hasPDC("map-regeneration", item)) {

            arena.setRegenerationTime(arena.getRegenerationTime().next());
            List<Component> lore = new ArrayList<>();

            for (String line : itemsSection.getStringList("map-regeneration.lore")) {

                lore.add(
                        MiniMessage.miniMessage().deserialize(
                                line,
                                TagResolver.resolver(
                                        Placeholder.component("state",
                                                MiniMessage.miniMessage().deserialize(
                                                        States.getString(arena.getRegenerationTime()))
                                        )
                                )
                        ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                );

            }

            meta.lore(lore);
            item.setItemMeta(meta);

            Sound.Click(player);

        }

        if (ItemUtil.hasPDC("explosions", item)) {

            arena.setExplosionState(arena.getExplosionState().next());
            List<Component> lore = new ArrayList<>();

            for (String line : itemsSection.getStringList("explosion.lore")) {

                lore.add(
                        MiniMessage.miniMessage().deserialize(
                                line,
                                TagResolver.resolver(
                                        Placeholder.component("state",
                                                MiniMessage.miniMessage().deserialize(
                                                        States.getString(arena.getExplosionState()))
                                        )
                                )
                        ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                );

            }

            meta.lore(lore);
            item.setItemMeta(meta);

            Sound.Click(player);

        }

        if (ItemUtil.hasPDC("drops", item)) {

            arena.setDrops(!arena.getDrops());
            List<Component> lore = new ArrayList<>();

            for (String line : itemsSection.getStringList("drops.lore")) {

                lore.add(
                        MiniMessage.miniMessage().deserialize(
                                line,
                                TagResolver.resolver(
                                        Placeholder.component("state",
                                                MiniMessage.miniMessage().deserialize(
                                                        States.getString(arena.getDrops()))
                                        )
                                )
                        ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                );

            }

            meta.lore(lore);
            item.setItemMeta(meta);

            Sound.Click(player);

        }

        if (ItemUtil.hasPDC("save-arena", item)) {

            arena.writeToFile();
            player.closeInventory();
            player.sendMessage(ComponentUtil.getComponent("player.saved-arena"));
            Sound.Ping(player);
            arena.init();

        }

    }

    @EventHandler
    public void onMessage(AsyncChatEvent event) {

        Player player = event.getPlayer();

        if (!isTyping.getOrDefault(player, false)) return;
        isTyping.put(player, false);

        sentMessage.put(player, MiniMessage.miniMessage().serialize(event.message()));
        arena.get(player).setName(MiniMessage.miniMessage().serialize(event.message()));

        Bukkit.getScheduler().runTask(plugin, () -> {

            new AddArenaGUI().openGUI(player);

        });

        Sound.Ping(player);

        event.setCancelled(true);

    }

}
