package org.abyssdev.abysswarzonegenerators.generator.item.registry;

import net.abyssdev.abysslib.patterns.registry.Registry;
import org.abyssdev.abysswarzonegenerators.AbyssWarzoneGenerators;
import org.abyssdev.abysswarzonegenerators.generator.item.GeneratorItem;
import org.abyssdev.abysswarzonegenerators.generator.item.impl.AbyssGeneratorItem;
import org.eclipse.collections.api.factory.Maps;

import java.util.Map;

/**
 * The generator item registry
 *
 * @author Relocation
 */
public final class GeneratorItemRegistry implements Registry<String, GeneratorItem> {

    private final Map<String, GeneratorItem> items = Maps.mutable.empty();

    /**
     * Constructs a new GeneratorItemRegistry
     *
     * @param plugin the abyss warzone plugin
     */
    public GeneratorItemRegistry(final AbyssWarzoneGenerators plugin) {
        for (final String key : plugin.getConfig().getSectionKeys("items")) {
            this.items.put(key, new AbyssGeneratorItem(plugin.getConfig(), key));
        }
    }

    @Override
    public Map<String, GeneratorItem> getRegistry() {
        return this.items;
    }

}