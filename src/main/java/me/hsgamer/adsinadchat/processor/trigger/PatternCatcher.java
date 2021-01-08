package me.hsgamer.adsinadchat.processor.trigger;

import me.hsgamer.adsinadchat.api.Trigger;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class PatternCatcher extends Trigger {
    private Pattern pattern;

    public PatternCatcher(String value) {
        super(value);
    }

    @Override
    public void setup() {
        this.pattern = Pattern.compile(value);
    }

    @Override
    public boolean trigger(Player player, String text) {
        return pattern.matcher(text).find();
    }
}
