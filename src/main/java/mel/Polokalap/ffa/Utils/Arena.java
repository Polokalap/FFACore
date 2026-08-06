package mel.Polokalap.ffa.Utils;

import mel.Polokalap.ffa.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

import static mel.Polokalap.ffa.Main.getInstance;

public class Arena {

    private String name;
    private States.BlockState blockState;
    private States.DecayTime decayTime;
    private States.RegenerationTime regenerationTime;
    private States.ExplosionState explosionState;
    private Location spawn;
    private Location pos1;
    private Location pos2;
    private UUID uuid;
    private BukkitTask task;

    public Arena(UUID uuid, String name, Location spawn, Location pos1, Location pos2, States.BlockState block, States.DecayTime decay, States.RegenerationTime regeneration, States.ExplosionState explosion) {

        this.name = name;
        this.spawn = spawn;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.blockState = block;
        this.decayTime = decay;
        this.regenerationTime = regeneration;
        this.explosionState = explosion;
        this.uuid = uuid;

    }

    public void init() {

        States.RegenerationTime initialTime = regenerationTime;

        if (initialTime.equals(States.RegenerationTime.NEVER)) return;

        task = new BukkitRunnable() {

            @Override
            public void run() {

                regenerate();

            }

        }.runTaskTimer(getInstance(), 0L, regenerationTime.toTicks());

    }

    public void stop() {

        if (task != null) {

            task.cancel();

        }

    }

    public void restart() {

        stop();
        init();

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public States.DecayTime getDecay() {

        return decayTime;

    }

    public void setDecayTime(States.DecayTime decayTime) {

        this.decayTime = decayTime;

    }

    public States.BlockState getBlockState() {

        return blockState;

    }

    public void setBlockState(States.BlockState blockState) {

        this.blockState = blockState;

    }

    public States.RegenerationTime getRegenerationTime() {

        return regenerationTime;

    }

    public void setRegenerationTime(States.RegenerationTime regenerationTime) {

        this.regenerationTime = regenerationTime;

    }

    public States.ExplosionState getExplosionState() {

        return explosionState;

    }

    public void setExplosionState(States.ExplosionState explosionState) {

        this.explosionState = explosionState;

    }

    public Location getPos1() {

        return pos1;

    }

    public void setPos1(Location pos1) {

        this.pos1 = pos1;

    }

    public Location getPos2() {

        return pos2;

    }

    public void setPos2(Location pos2) {

        this.pos2 = pos2;

    }

    public Location getSpawn() {

        return spawn;

    }

    public void setSpawn(Location spawn) {

        this.spawn = spawn;

    }

    public UUID getUniqueId() {

        return uuid;

    }

    public void writeToFile() {

        WorldEdit.saveSchem(pos1, pos2, uuid.toString());

        String path = "arenas." + uuid.toString();
        var config = Main.getArenasConfig();

        config.set(path + ".name", name);
        config.set(path + ".uuid", uuid.toString());

        config.set(path + ".block-state", blockState.name());
        config.set(path + ".decay-time", decayTime.name());
        config.set(path + ".regeneration-time", regenerationTime.name());
        config.set(path + ".explosion-state", explosionState.name());

        saveLocation(path + ".spawn", spawn);
        saveLocation(path + ".pos1", pos1);
        saveLocation(path + ".pos2", pos2);

        getInstance().saveArenasConfig();

    }

    private void saveLocation(String path, Location location) {

        var config = Main.getArenasConfig();

        if (location == null) {
            config.set(path, null);
            return;
        }

        config.set(path + ".world", location.getWorld().getName());
        config.set(path + ".x", location.getX());
        config.set(path + ".y", location.getY());
        config.set(path + ".z", location.getZ());
        config.set(path + ".yaw", location.getYaw());
        config.set(path + ".pitch", location.getPitch());

    }

    public void regenerate() {

        WorldEdit.placeSchem(pos1, pos2, uuid.toString());

    }

    public void remove() {

        String path = "arenas." + uuid.toString();
        var config = Main.getArenasConfig();
        config.set(path, null);
        getInstance().saveArenasConfig();

    }

    public boolean inArea(Location loc) {

        if (loc.getWorld() == null || pos1.getWorld() == null || pos2.getWorld() == null) return false;
        if (!loc.getWorld().equals(pos1.getWorld()) || !loc.getWorld().equals(pos2.getWorld())) return false;

        double minX = Math.min(pos1.getX(), pos2.getX());
        double maxX = Math.max(pos1.getX(), pos2.getX());

        double minY = Math.min(pos1.getY(), pos2.getY());
        double maxY = Math.max(pos1.getY(), pos2.getY());

        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        return x >= minX && x <= maxX
                && y >= minY && y <= maxY
                && z >= minZ && z <= maxZ;

    }

    public boolean inArea(Player player) {

        Location loc = player.getLocation();
        return inArea(loc);

    }

}