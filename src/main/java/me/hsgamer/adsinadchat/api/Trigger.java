package me.hsgamer.adsinadchat.api;

import org.bukkit.entity.Player;

/**
 * Trigger from the text
 */
public abstract class Trigger extends Processor {

    public Trigger(String value) {
        super(value);
    }

    /**
     * Check if the trigger is active on the text
     *
     * @param player the player
     * @param text   the text
     * @return whether it's triggered or not
     */
    public abstract boolean trigger(Player player, String text);
}
