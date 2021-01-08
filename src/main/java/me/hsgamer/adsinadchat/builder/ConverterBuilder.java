package me.hsgamer.adsinadchat.builder;

import me.hsgamer.adsinadchat.api.BaseBuilder;
import me.hsgamer.adsinadchat.api.Converter;
import me.hsgamer.adsinadchat.processor.converter.DeathHole;
import me.hsgamer.adsinadchat.processor.converter.Hastebin;

public class ConverterBuilder extends BaseBuilder<Converter> {
    /**
     * The instance of the builder
     */
    public static final ConverterBuilder INSTANCE = new ConverterBuilder();

    private ConverterBuilder() {
        register(Hastebin::new, "hastebin", "hbin");
        register(DeathHole::new, "deathhole", "death");
    }
}
