package mel.Polokalap.ffa.Utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.util.SideEffectSet;
import com.sk89q.worldedit.util.io.Closer;
import mel.Polokalap.ffa.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WorldEdit {

    private static Main plugin = Main.getInstance();
    private static FileConfiguration config = plugin.getConfig();

    public static Location getMinCorner(Location loc1, Location loc2) {

        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());

        return new Location(loc1.getWorld(), minX, minY, minZ);

    }

    public static void saveSchem(Location loc1, Location loc2, String path) {

        File schemFile = new File(plugin.getDataFolder(), "Arenas/" + path + ".schem");

        if (!schemFile.getParentFile().exists()) schemFile.getParentFile().mkdirs();

        Region reg = new CuboidRegion(BukkitAdapter.asBlockVector(loc1), BukkitAdapter.asBlockVector(loc2));
        EditSession editSession = makeEditSession(loc1.getWorld());

        BlockArrayClipboard clipboard = new BlockArrayClipboard(reg);
        ForwardExtentCopy copy = new ForwardExtentCopy(editSession, reg, clipboard, reg.getMinimumPoint());

        try {

            Operations.complete(copy);

        } catch (final Throwable t) {

            throw new RuntimeException(t);

        }

        try (Closer closer = Closer.create()) {

            FileOutputStream outputStream = closer.register(new FileOutputStream(schemFile));
            ClipboardWriter writer = closer.register(BuiltInClipboardFormat.SPONGE_V3_SCHEMATIC.getWriter(outputStream));

            writer.write(clipboard);

        } catch (IOException e) {

            throw new RuntimeException(e);

        }

    }

    public static void placeSchem(Location loc1, Location loc2, String path) {

        File schemFile = new File(plugin.getDataFolder(), "Arenas/" + path + ".schem");

        if (!schemFile.exists()) {
            throw new IllegalStateException("Schematic file not found: " + schemFile.getAbsolutePath());
        }

        Location minCorner = getMinCorner(loc1, loc2);

        ClipboardFormat format = ClipboardFormats.findByFile(schemFile);

        if (format == null) {
            throw new IllegalStateException("Could not determine clipboard format for: " + schemFile.getAbsolutePath());
        }

        try (FileInputStream fis = new FileInputStream(schemFile);

             ClipboardReader reader = format.getReader(fis);
             EditSession editSession = com.sk89q.worldedit.WorldEdit.getInstance()
                     .newEditSession(BukkitAdapter.adapt(loc1.getWorld()))) {

            Clipboard clipboard = reader.read();

            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BukkitAdapter.asBlockVector(minCorner))
                    .build();

            Operations.complete(operation);

        } catch (Exception e) {

            throw new RuntimeException("Failed to paste schematic", e);

        }

    }

    private static EditSession makeEditSession(World bukkitWorld) {

        final EditSession session = com.sk89q.worldedit.WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(bukkitWorld));

        session.setSideEffectApplier(SideEffectSet.defaults());

        return session;

    }

}