package me.hsgamer.adsinadchat.processor.converter;

import me.hsgamer.adsinadchat.api.Converter;
import org.bukkit.entity.Player;

public class TextChanger extends Converter {
    public TextChanger(String value) {
        super(value);
    }

    @Override
    public String convert(Player player, String text) {
        return value.replace("{text}", text);
    }
}
