package mel.Polokalap.ffa.GUI;

import mel.Polokalap.ffa.Commands.AddArenaCommand;
import mel.Polokalap.ffa.Utils.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static mel.Polokalap.ffa.Commands.ArenasCommand.openedArena;

public class EditArenaGUI extends GUI implements InventoryHolder {

    @Override
    public void openGUI(Player player) {

        Arena arena = openedArena.get(player);
        name = ComponentUtil.getComponent(
                "gui.edit-arena.title",
                TagResolver.resolver(
                        Placeholder.unparsed("name", arena.getName())
                )
        );
        size = 36;
        holder = this;

        super.openGUI(player);

        ConfigurationSection itemsSection = plugin.getConfig().getConfigurationSection("gui.edit-arena.items");

        for (String key : itemsSection.getKeys(false)) {

            ConfigurationSection section = itemsSection.getConfigurationSection(key);

            ItemStack item = new ItemStack(Material.valueOf(section.getString("type", "EGG")));
            ItemMeta meta = item.getItemMeta();

            meta.displayName(
                    MiniMessage.miniMessage().deserialize(
                            section.getString("name", "?")
                    ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
            );

            List<Component> lore = new ArrayList<>();

            for (String line : section.getStringList("lore")) {

                lore.add(
                        MiniMessage.miniMessage().deserialize(
                                line
                        ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                );

            }

            meta.lore(lore);
            ItemUtil.assignPDC(section.getString("key", "default"), meta);

            item.setItemMeta(meta);

            menu.setItem(section.getInt("slot", 0), item);

        }

        ItemStack setName = new ItemStack(Material.valueOf(itemsSection.getString("set-name.type")));
        ItemMeta setNameMeta = setName.getItemMeta();

        setNameMeta.displayName(ComponentUtil.getComponent("gui.edit-arena.items.set-name.name"));
        List<Component> setNameLore = new ArrayList<>();

        for (String line : itemsSection.getStringList("set-name.lore")) {

            setNameLore.add(
                    MiniMessage.miniMessage().deserialize(
                            line
                    ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
            );

        }

        setNameMeta.lore(setNameLore);
        ItemUtil.assignPDC(itemsSection.getString("set-name.key", "default"), setNameMeta);

        setName.setItemMeta(setNameMeta);
        menu.setItem(itemsSection.getInt("set-name.slot", 19), setName);
        ItemStack blockBreak = new ItemStack(Material.valueOf(itemsSection.getString("block-break.type")));
        ItemMeta blockBreakMeta = blockBreak.getItemMeta();

        blockBreakMeta.displayName(ComponentUtil.getComponent("gui.edit-arena.items.block-break.name"));
        List<Component> blockBreakLore = new ArrayList<>();

        for (String line : itemsSection.getStringList("block-break.lore")) {

            blockBreakLore.add(
                    MiniMessage.miniMessage().deserialize(
                            line,
                            TagResolver.resolver(
                                    Placeholder.component("state",
                                            MiniMessage.miniMessage().deserialize(
                                                    States.getString(openedArena.get(player).getBlockState()))
                                    )
                            )
                    ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
            );

        }

        blockBreakMeta.lore(blockBreakLore);
        ItemUtil.assignPDC(itemsSection.getString("block-break.key", "default"), blockBreakMeta);

        blockBreak.setItemMeta(blockBreakMeta);
        menu.setItem(itemsSection.getInt("block-break.slot", 20), blockBreak);

        ItemStack blockDecay = new ItemStack(Material.valueOf(itemsSection.getString("block-decay.type")));
        ItemMeta blockDecayMeta = blockDecay.getItemMeta();

        blockDecayMeta.displayName(ComponentUtil.getComponent("gui.edit-arena.items.block-decay.name"));
        List<Component> blockDecayLore = new ArrayList<>();

        for (String line : itemsSection.getStringList("block-decay.lore")) {

            blockDecayLore.add(
                    MiniMessage.miniMessage().deserialize(
                            line,
                            TagResolver.resolver(
                                    Placeholder.component("state",
                                            MiniMessage.miniMessage().deserialize(
                                                    States.getString(openedArena.get(player).getDecay()))
                                    )
                            )
                    ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
            );

        }

        blockDecayMeta.lore(blockDecayLore);
        ItemUtil.assignPDC(itemsSection.getString("block-decay.key", "default"), blockDecayMeta);

        blockDecay.setItemMeta(blockDecayMeta);
        menu.setItem(itemsSection.getInt("block-decay.slot", 21), blockDecay);

        ItemStack mapRegeneration = new ItemStack(Material.valueOf(itemsSection.getString("map-regeneration.type")));
        ItemMeta mapRegenerationMeta = mapRegeneration.getItemMeta();

        mapRegenerationMeta.displayName(ComponentUtil.getComponent("gui.edit-arena.items.map-regeneration.name"));
        List<Component> mapRegenerationLore = new ArrayList<>();

        for (String line : itemsSection.getStringList("map-regeneration.lore")) {

            mapRegenerationLore.add(
                    MiniMessage.miniMessage().deserialize(
                            line,
                            TagResolver.resolver(
                                    Placeholder.component("state",
                                            MiniMessage.miniMessage().deserialize(
                                                    States.getString(openedArena.get(player).getRegenerationTime()))
                                    )
                            )
                    ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
            );

        }

        mapRegenerationMeta.lore(mapRegenerationLore);
        ItemUtil.assignPDC(itemsSection.getString("map-regeneration.key", "default"), mapRegenerationMeta);

        mapRegeneration.setItemMeta(mapRegenerationMeta);
        menu.setItem(itemsSection.getInt("map-regeneration.slot", 22), mapRegeneration);

        ItemStack explosion = new ItemStack(Material.valueOf(itemsSection.getString("explosion.type")));
        ItemMeta explosionMeta = explosion.getItemMeta();

        explosionMeta.displayName(ComponentUtil.getComponent("gui.edit-arena.items.explosion.name"));
        List<Component> explosionLore = new ArrayList<>();

        for (String line : itemsSection.getStringList("explosion.lore")) {

            explosionLore.add(
                    MiniMessage.miniMessage().deserialize(
                            line,
                            TagResolver.resolver(
                                    Placeholder.component("state",
                                            MiniMessage.miniMessage().deserialize(
                                                    States.getString(openedArena.get(player).getExplosionState()))
                                    )
                            )
                    ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
            );

        }

        explosionMeta.lore(explosionLore);
        ItemUtil.assignPDC(itemsSection.getString("explosion.key", "default"), explosionMeta);

        explosion.setItemMeta(explosionMeta);
        menu.setItem(itemsSection.getInt("explosion.slot", 23), explosion);

        ItemStack drops = new ItemStack(Material.valueOf(itemsSection.getString("drops.type")));
        ItemMeta dropsMeta = drops.getItemMeta();

        dropsMeta.displayName(ComponentUtil.getComponent("gui.edit-arena.items.drops.name"));
        List<Component> dropsLore = new ArrayList<>();

        for (String line : itemsSection.getStringList("drops.lore")) {

            dropsLore.add(
                    MiniMessage.miniMessage().deserialize(
                            line,
                            TagResolver.resolver(
                                    Placeholder.component("state",
                                            MiniMessage.miniMessage().deserialize(
                                                    States.getString(openedArena.get(player).getDrops())
                                            )
                                    )
                            )
                    ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
            );

        }

        dropsMeta.lore(dropsLore);
        ItemUtil.assignPDC(itemsSection.getString("drops.key", "default"), dropsMeta);

        drops.setItemMeta(dropsMeta);
        menu.setItem(itemsSection.getInt("drops.slot", 24), drops);

    }

    @Override
    public Inventory getInventory() {

        return null;

    }

}
