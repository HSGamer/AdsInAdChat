package me.hsgamer.adsinadchat.processor.preprocessor;

import me.hsgamer.adsinadchat.api.Preprocessor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StripColor extends Preprocessor {
    public StripColor(String value) {
        super(value);
    }

    @Override
    public String process(Player player, String text) {
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', text));
    }
}
