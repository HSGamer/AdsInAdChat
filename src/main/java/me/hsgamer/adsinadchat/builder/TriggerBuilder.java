package me.hsgamer.adsinadchat.builder;

import me.hsgamer.adsinadchat.api.BaseBuilder;
import me.hsgamer.adsinadchat.api.Trigger;
import me.hsgamer.adsinadchat.processor.trigger.BadWord;
import me.hsgamer.adsinadchat.processor.trigger.PatternCatcher;

/**
 * The trigger builder
 */
public class TriggerBuilder extends BaseBuilder<Trigger> {
    /**
     * The instance of the builder
     */
    public static final TriggerBuilder INSTANCE = new TriggerBuilder();

    private TriggerBuilder() {
        register(BadWord::new, "badword", "badword", "word");
        register(PatternCatcher::new, "patterncatcher", "regex", "pattern");
    }
}
