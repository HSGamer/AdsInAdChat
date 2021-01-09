package me.hsgamer.adsinadchat.builder;

import me.hsgamer.adsinadchat.api.BaseBuilder;
import me.hsgamer.adsinadchat.api.Preprocessor;
import me.hsgamer.adsinadchat.processor.preprocessor.PatternReplacer;
import me.hsgamer.adsinadchat.processor.preprocessor.StripColor;

public class PreprocessorBuilder extends BaseBuilder<Preprocessor> {
    /**
     * The instance of the builder
     */
    public static final PreprocessorBuilder INSTANCE = new PreprocessorBuilder();

    private PreprocessorBuilder() {
        register(StripColor::new, "stripcolor", "strip-color");
        register(PatternReplacer::new, "patternreplacer", "replacer", "pattern-replacer");
    }
}
