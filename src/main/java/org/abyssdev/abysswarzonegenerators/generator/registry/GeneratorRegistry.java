package org.abyssdev.abysswarzonegenerators.generator.registry;

import net.abyssdev.abysslib.patterns.registry.Registry;
import org.abyssdev.abysswarzonegenerators.AbyssWarzoneGenerators;
import org.abyssdev.abysswarzonegenerators.generator.Generator;
import org.abyssdev.abysswarzonegenerators.generator.impl.AbyssGenerator;
import org.eclipse.collections.api.factory.Maps;

import java.util.Map;

/**
 * The generator registry
 *
 * @author Relocation
 */
public final class GeneratorRegistry implements Registry<String, Generator> {

    private final Map<String, Generator> generators = Maps.mutable.empty();

    /**
     * Constructs a new GeneratorRegistry
     *
     * @param plugin The abyss warzone generators plugin
     */
    public GeneratorRegistry(final AbyssWarzoneGenerators plugin) {
        for (final String key : plugin.getConfig().getSectionKeys("generators")) {
            this.generators.put(key, new AbyssGenerator(plugin, key));
        }
    }

    @Override
    public Map<String, Generator> getRegistry() {
        return this.generators;
    }

}