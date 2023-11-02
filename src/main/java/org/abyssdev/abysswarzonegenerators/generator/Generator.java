package org.abyssdev.abysswarzonegenerators.generator;

import gg.optimalgames.hologrambridge.hologram.Hologram;
import net.abyssdev.abysslib.chanced.ChancedCollection;
import org.abyssdev.abysswarzonegenerators.generator.item.GeneratorItem;
import org.bukkit.Location;

import java.util.List;

/**
 * The generator interface
 *
 * @author Relocation
 */
public interface Generator {

    String type();

    void spawn();

    void update();

    int interval();

    int secondsLeft();

    List<Hologram> holograms();

    List<Location> locations();

    ChancedCollection<GeneratorItem> items();

}
