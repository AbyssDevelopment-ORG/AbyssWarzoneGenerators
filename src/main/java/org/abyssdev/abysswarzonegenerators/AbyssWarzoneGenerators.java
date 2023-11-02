package org.abyssdev.abysswarzonegenerators;

import lombok.Getter;
import net.abyssdev.abysslib.config.AbyssConfig;
import net.abyssdev.abysslib.patterns.registry.Registry;
import net.abyssdev.abysslib.plugin.AbyssPlugin;
import org.abyssdev.abysswarzonegenerators.generator.Generator;
import org.abyssdev.abysswarzonegenerators.generator.item.GeneratorItem;
import org.abyssdev.abysswarzonegenerators.generator.item.registry.GeneratorItemRegistry;
import org.abyssdev.abysswarzonegenerators.generator.registry.GeneratorRegistry;

/**
 * The abyss warzone generators main class
 *
 * @author Relocation
 */
@Getter
public final class AbyssWarzoneGenerators extends AbyssPlugin {

    private final AbyssConfig config = this.getAbyssConfig("config");

    private final Registry<String, GeneratorItem> itemRegistry = new GeneratorItemRegistry(this);
    private Registry<String, Generator> generatorRegistry;

    @Override
    public void onEnable() {
        this.generatorRegistry = new GeneratorRegistry(this);
    }

}