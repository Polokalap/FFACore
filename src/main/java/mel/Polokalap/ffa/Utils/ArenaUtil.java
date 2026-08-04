package mel.Polokalap.ffa.Utils;

import mel.Polokalap.ffa.Main;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class ArenaUtil {

    public static ArrayList<String> getArenaNames() {

        ArrayList<String> list = new ArrayList<>();
        ConfigurationSection arenasSection = Main.getArenasConfig().getConfigurationSection("arenas");

        for (String key : arenasSection.getKeys(false)) {

            list.add(arenasSection.getConfigurationSection(key).getString("name"));

        }

        return list;

    }

}
