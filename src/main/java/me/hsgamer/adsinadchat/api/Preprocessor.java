package me.hsgamer.adsinadchat.api;

import org.bukkit.entity.Player;

/**
 * Text preprocessor
 */
public abstract class Preprocessor extends Processor {

    public Preprocessor(String value) {
        super(value);
    }

    /**
     * Process the text
     *
     * @param player the player
     * @param text   the text
     * @return the processed text
     */
    public abstract String process(Player player, String text);
}
