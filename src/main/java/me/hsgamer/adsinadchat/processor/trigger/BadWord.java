package me.hsgamer.adsinadchat.processor.trigger;

import me.hsgamer.adsinadchat.api.Trigger;
import org.bukkit.entity.Player;

import java.util.Locale;

public class BadWord extends Trigger {

    public BadWord(String value) {
        super(value);
    }

    @Override
    public boolean trigger(Player player, String text) {
        return text.toLowerCase(Locale.ROOT).contains(value.toLowerCase(Locale.ROOT));
    }
}
