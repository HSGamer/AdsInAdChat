package me.hsgamer.adsinadchat.processor.preprocessor;

import me.hsgamer.adsinadchat.api.Preprocessor;
import net.gcardone.junidecode.Junidecode;
import org.bukkit.entity.Player;

public class UnicodeDecoder extends Preprocessor {
    public UnicodeDecoder(String value) {
        super(value);
    }

    @Override
    public String process(Player player, String text) {
        return Junidecode.unidecode(text);
    }
}
