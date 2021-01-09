package me.hsgamer.adsinadchat.processor.preprocessor;

import me.hsgamer.adsinadchat.api.Preprocessor;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class PatternReplacer extends Preprocessor {
    private static final Pattern splitPattern = Pattern.compile(Pattern.quote("-->"));
    private Pattern matchPattern;
    private String replace;

    public PatternReplacer(String value) {
        super(value);
    }

    @Override
    public String process(Player player, String text) {
        if (matchPattern == null) {
            return text;
        }
        return matchPattern.matcher(text).replaceAll(replace);
    }

    @Override
    public void setup() {
        String[] split = splitPattern.split(value, 2);
        matchPattern = Pattern.compile(split[0].trim());
        replace = (split.length > 1 ? split[1] : "").trim();
    }
}
