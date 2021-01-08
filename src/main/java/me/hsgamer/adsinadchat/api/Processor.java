package me.hsgamer.adsinadchat.api;

/**
 * The processor
 */
public abstract class Processor {
    protected final String value;

    public Processor(String value) {
        this.value = value;
    }

    /**
     * Setup the processor
     */
    public void setup() {
        // EMPTY
    }

    /**
     * Disable the processor
     */
    public void disable() {
        // EMPTY
    }
}
