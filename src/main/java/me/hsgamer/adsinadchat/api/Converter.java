package me.hsgamer.adsinadchat.api;

import org.bukkit.entity.Player;

import java.net.URL;

/**
 * Convert the text
 */
public abstract class Converter extends Processor {

    public Converter(String value) {
        super(value);
    }

    /**
     * Process the converter
     *
     * @param player the player
     * @param text   the text
     * @return the converted text
     */
    public final String process(Player player, String text) {
        if (isURLRequired()) {
            try {
                new URL(text).toURI();
            } catch (Exception e) {
                return text;
            }
        }
        return convert(player, text);
    }

    /**
     * Convert the text
     *
     * @param player the player
     * @param text   the text
     * @return the converted text
     */
    public abstract String convert(Player player, String text);

    /**
     * Should we check if the input is an URL?
     *
     * @return True if we should
     */
    protected boolean isURLRequired() {
        return false;
    }
}
