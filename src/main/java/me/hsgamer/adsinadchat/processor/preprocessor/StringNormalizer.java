package me.hsgamer.adsinadchat.processor.preprocessor;

import me.hsgamer.adsinadchat.api.Preprocessor;
import org.bukkit.entity.Player;

import java.text.Normalizer;
import java.util.Locale;

public class StringNormalizer extends Preprocessor {
    private Normalizer.Form form = Normalizer.Form.NFD;

    public StringNormalizer(String value) {
        super(value);
    }

    @Override
    public String process(Player player, String text) {
        return Normalizer
                .normalize(text, form)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    @Override
    public void setup() {
        try {
            form = Normalizer.Form.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            // IGNORED
        }
    }
}
