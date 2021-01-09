package me.hsgamer.adsinadchat.builder;

import me.hsgamer.adsinadchat.api.BaseBuilder;
import me.hsgamer.adsinadchat.api.Converter;
import me.hsgamer.adsinadchat.processor.converter.Cuttly;
import me.hsgamer.adsinadchat.processor.converter.DeathHole;
import me.hsgamer.adsinadchat.processor.converter.Hastebin;
import me.hsgamer.adsinadchat.processor.converter.TextChanger;

public class ConverterBuilder extends BaseBuilder<Converter> {
    /**
     * The instance of the builder
     */
    public static final ConverterBuilder INSTANCE = new ConverterBuilder();

    private ConverterBuilder() {
        register(Hastebin::new, "hastebin", "hbin");
        register(DeathHole::new, "deathhole", "death");
        register(TextChanger::new, "textchanger", "text", "changer");
        register(Cuttly::new, "cuttly", "cutt.ly");
    }
}
