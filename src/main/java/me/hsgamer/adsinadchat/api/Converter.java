package me.hsgamer.adsinadchat.api;

import org.bukkit.entity.Player;

/**
 * Convert the text
 */
public abstract class Converter extends Processor {

    public Converter(String value) {
        super(value);
    }

    /**
     * Convert the text
     *
     * @param player the player
     * @param text   the text
     * @return the converted text
     */
    public abstract String convert(Player player, String text);
}
