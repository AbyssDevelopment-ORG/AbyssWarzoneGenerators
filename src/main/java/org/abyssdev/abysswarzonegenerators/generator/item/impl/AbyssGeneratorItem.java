package org.abyssdev.abysswarzonegenerators.generator.item.impl;

import lombok.Getter;
import net.abyssdev.abysslib.config.AbyssConfig;
import net.abyssdev.redempt.crunch.data.Pair;
import org.abyssdev.abysswarzonegenerators.generator.item.GeneratorItem;
import org.bukkit.inventory.ItemStack;

/**
 * The abyss generator item implemenation
 *
 * @author Relocation
 */
@Getter
public final class AbyssGeneratorItem implements GeneratorItem {

    private final ItemStack itemStack;
    private Pair<String, String> nbt;

    /**
     * Constructs a new AbyssGeneratorItem
     *
     * @param config The config
     * @param key The key
     */
    public AbyssGeneratorItem(final AbyssConfig config, final String key) {
        this.itemStack = config.getItemStack("items." + key);

        if (config.getBoolean("items." + key + ".nbt.enabled")) {
            this.nbt = new Pair<>(config.getString("items." + key + ".nbt.key"), config.getString("items." + key + ".nbt.value"));
        }
    }

    @Override
    public ItemStack item() {
        return this.itemStack;
    }

}