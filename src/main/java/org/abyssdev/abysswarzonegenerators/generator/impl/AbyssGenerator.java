package org.abyssdev.abysswarzonegenerators.generator.impl;

import gg.optimalgames.hologrambridge.HologramAPI;
import gg.optimalgames.hologrambridge.hologram.Hologram;
import gg.optimalgames.hologrambridge.hologram.impl.OptimalHologram;
import gg.optimalgames.hologrambridge.lines.impl.OptimalTextLine;
import lombok.Getter;
import net.abyssdev.abysslib.chanced.ChancedCollection;
import net.abyssdev.abysslib.location.LocationSerializer;
import net.abyssdev.abysslib.runnable.AbyssTask;
import net.abyssdev.abysslib.utils.Utils;
import org.abyssdev.abysswarzonegenerators.AbyssWarzoneGenerators;
import org.abyssdev.abysswarzonegenerators.generator.Generator;
import org.abyssdev.abysswarzonegenerators.generator.item.GeneratorItem;
import org.bukkit.Location;
import org.eclipse.collections.api.factory.Lists;

import java.util.List;
import java.util.Optional;

/**
 * The abyss generator implementation
 *
 * @author Relocation
 */
@Getter
public final class AbyssGenerator extends AbyssTask<AbyssWarzoneGenerators> implements Generator {

    private final String type;

    private final List<Hologram> holograms = Lists.mutable.empty();
    private final List<Location> locations = Lists.mutable.empty();
    private final List<String> lines;

    private final ChancedCollection<GeneratorItem> items = new ChancedCollection<>();

    private final int interval;
    private int seconds;

    /**
     * Constructs a new AbyssGenerator
     *
     * @param plugin The abyss warzone generators plugin
     * @param key The generator key
     */
    public AbyssGenerator(final AbyssWarzoneGenerators plugin, final String key) {
        super(plugin, 20);

        this.type = key;

        this.lines = plugin.getConfig().getColoredStringList("warzone-generators." + key + ".hologram.lines");
        this.interval = plugin.getConfig().getInt("warzone-generators." + key + ".spawn-interval");
        this.seconds = this.interval;

        for (final String item : plugin.getConfig().getStringList("warzone-generators." + key + ".items")) {
            final String[] data = item.split(";");

            final Optional<GeneratorItem> itemOpt = plugin.getItemRegistry().get(data[0]);

            if (!itemOpt.isPresent()) {
                continue;
            }

            this.items.put(itemOpt.get(), Double.parseDouble(data[1]));
        }

        for (final String loc : plugin.getConfig().getStringList("warzone-generators." + key + ".locations")) {
            final Location location = LocationSerializer.deserialize(loc);

            if (location.getWorld() == null) {
                continue;
            }

            this.locations.add(location.add(0.5, 0, 0.5));
        }

        for (final Location location : this.locations) {
            final Hologram hologram = HologramAPI.createHologram(location);

            for (final String line : this.lines) {
                hologram.appendTextLine(line.replace("%time%", Utils.getTimeFormat(this.seconds * 1000L)));
            }

            this.holograms.add(hologram);
        }
    }

    @Override
    public void run() {
        this.seconds--;

        this.update();

        if (this.seconds <= 0) {
            this.spawn();
        }
    }

    @Override
    public void spawn() {
        this.seconds = this.interval;

        for (final Location location : this.locations) {
            this.items.attemptUnbrokenSelection(item -> {
                if (location.getWorld() == null) {
                    return;
                }

                location.getWorld().dropItem(location, item.item());
            });

        }
    }

    @Override
    public void update() {
        for (final Hologram hologram : this.holograms) {
            int index = 0;

            for (final String line : this.lines) {
                if (index >= this.lines.size()) {
                    break;
                }

                if (!line.contains("%time%")) {
                    index++;
                    continue;
                }

                ((OptimalTextLine) hologram.getLineAt(index)).setText(line.replace("%time%", Utils.getTimeFormat(this.seconds * 1000L)));
                index++;
            }
        }
    }

    @Override
    public String type() {
        return this.type;
    }

    @Override
    public int interval() {
        return this.interval;
    }

    @Override
    public int secondsLeft() {
        return this.seconds;
    }

    @Override
    public List<Hologram> holograms() {
        return this.holograms;
    }

    @Override
    public List<Location> locations() {
        return this.locations;
    }

    @Override
    public ChancedCollection<GeneratorItem> items() {
        return this.items;
    }

}