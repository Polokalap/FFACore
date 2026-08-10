package mel.Polokalap.ffa;

import mel.Polokalap.ffa.Commands.*;
import mel.Polokalap.ffa.Listener.*;
import mel.Polokalap.ffa.Utils.Arena;
import mel.Polokalap.ffa.Utils.ArenaUtil;
import mel.Polokalap.ffa.Utils.CombatManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

    private static Main instance;
    private File arenas;
    private static FileConfiguration arenasConfig;

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults(true);
        saveConfig();

        instance = this;

        arenas = new File(getDataFolder(), "arenas.yml");
        if (!arenas.exists()) saveResource("arenas.yml", false);
        arenasConfig = YamlConfiguration.loadConfiguration(arenas);

        getLogger().info(getConfig().getString("console.startup"));

        Bukkit.getPluginManager().registerEvents(new ArenaSelectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new AddGUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new EditArenaGUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new EditArenasGUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArenaBlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArenaExplosionListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArenaDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new CombatListener(), this);

        getCommand("selection-wand").setExecutor(new SelectArenaCommand());
        getCommand("selection-wand").setTabCompleter(new SelectArenaCommand());
        getCommand("add-arena").setExecutor(new AddArenaCommand());
        getCommand("add-arena").setTabCompleter(new AddArenaCommand());
        getCommand("arenas").setExecutor(new ArenasCommand());
        getCommand("arenas").setTabCompleter(new ArenasCommand());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("warp").setTabCompleter(new WarpCommand());

        CombatManager.init();

        for (Arena arena : ArenaUtil.getArenas()) {

            arena.init();

        }

    }

    @Override
    public void onDisable() {

        getLogger().info(getConfig().getString("console.startup"));

    }

    public static Main getInstance() {

        return instance;

    }

    public static FileConfiguration getArenasConfig() {

        return arenasConfig;

    }

    public void saveArenasConfig() {

        try {
            arenasConfig.save(arenas);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
