package mel.Polokalap.ffa.Utils;

import mel.Polokalap.ffa.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ArenaUtil {

    public static ArrayList<String> getArenaNames() {

        ArrayList<String> list = new ArrayList<>();
        ConfigurationSection arenasSection = Main.getArenasConfig().getConfigurationSection("arenas");

        for (String key : arenasSection.getKeys(false)) {

            list.add(arenasSection.getConfigurationSection(key).getString("name"));

        }

        return list;

    }

    public static ArrayList<Arena> getArenas() {

        ArrayList<Arena> list = new ArrayList<>();
        ConfigurationSection arenasSection = Main.getArenasConfig().getConfigurationSection("arenas");

        if (arenasSection == null) return list;

        for (String key : arenasSection.getKeys(false)) {

            ConfigurationSection section = arenasSection.getConfigurationSection(key);
            if (section == null) continue;

            ArrayList<Material> filter = new ArrayList<>();

            for (String entry : section.getStringList("filter")) {

                filter.add(Material.valueOf(entry));

            }

            list.add(
                    new Arena(
                            UUID.fromString(section.getString("uuid")),
                            section.getString("name"),
                            loadLocation(section.getConfigurationSection("spawn")),
                            loadLocation(section.getConfigurationSection("pos1")),
                            loadLocation(section.getConfigurationSection("pos2")),
                            States.BlockState.valueOf(section.getString("block-state")),
                            States.DecayTime.valueOf(section.getString("decay-time")),
                            States.RegenerationTime.valueOf(section.getString("regeneration-time")),
                            States.ExplosionState.valueOf(section.getString("explosion-state")),
                            Boolean.parseBoolean(section.getString("drops")),
                            filter
                    )
            );

        }

        return list;

    }

    private static Location loadLocation(ConfigurationSection section) {

        if (section == null) return null;

        World world = Bukkit.getWorld(section.getString("world"));
        if (world == null) return null;

        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        float yaw = (float) section.getDouble("yaw");
        float pitch = (float) section.getDouble("pitch");

        return new Location(world, x, y, z, yaw, pitch);

    }

    public static ArrayList<String> getArenaNamesWithAccess(Player player) {

        ArrayList<String> list = new ArrayList<>();
        ConfigurationSection arenasSection = Main.getArenasConfig().getConfigurationSection("arenas");

        for (String key : arenasSection.getKeys(false)) {

            String name = arenasSection.getConfigurationSection(key).getString("name");

            if (!player.hasPermission("ffa.warp." + name)) continue;
            list.add(name);

        }

        return list;

    }

    public static boolean inAnyArena(Player player) {

        ConfigurationSection arenasSection = Main.getArenasConfig().getConfigurationSection("arenas");

        for (Arena arena : getArenas()) {

            if (arena.inArea(player)) return true;

        }

        return false;

    }

}
