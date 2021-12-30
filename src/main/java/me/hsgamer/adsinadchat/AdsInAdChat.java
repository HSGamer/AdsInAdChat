package me.hsgamer.adsinadchat;

import me.hsgamer.adsinadchat.api.Converter;
import me.hsgamer.adsinadchat.api.Preprocessor;
import me.hsgamer.adsinadchat.api.Trigger;
import me.hsgamer.adsinadchat.builder.ConverterBuilder;
import me.hsgamer.adsinadchat.builder.PreprocessorBuilder;
import me.hsgamer.adsinadchat.builder.TriggerBuilder;
import me.hsgamer.adsinadchat.listener.ChatListener;
import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;

import java.util.*;

public final class AdsInAdChat extends BasePlugin {
    private final List<Preprocessor> preprocessorList = new LinkedList<>();
    private final List<Converter> converterList = new LinkedList<>();
    private final List<Trigger> triggerList = new ArrayList<>();
    private boolean silentMode = false;
    private boolean deepTrigger = false;

    @Override
    public void load() {
        getConfig().options().copyDefaults(true);
        getConfig().addDefault("preprocessor-list", Collections.singletonList("StripColor"));
        getConfig().addDefault("trigger-list", Arrays.asList(
                "word: fuck",
                "word: bitch",
                "regex: [-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)"
        ));
        getConfig().addDefault("converter-list", Collections.singletonList("Hastebin"));
        getConfig().addDefault("silent-mode", false);
        getConfig().addDefault("deep-trigger", false);
        saveConfig();
    }

    @Override
    public void enable() {
        registerListener(new ChatListener(this));
    }

    @Override
    public void postEnable() {
        preprocessorList.addAll(PreprocessorBuilder.INSTANCE.buildList(getConfig().get("preprocessor-list")));
        triggerList.addAll(TriggerBuilder.INSTANCE.buildList(getConfig().get("trigger-list")));
        converterList.addAll(ConverterBuilder.INSTANCE.buildList(getConfig().get("converter-list")));
        silentMode = getConfig().getBoolean("silent-mode");
        deepTrigger = getConfig().getBoolean("deep-trigger");
    }

    @Override
    public void disable() {
        preprocessorList.forEach(Preprocessor::disable);
        triggerList.forEach(Trigger::disable);
        converterList.forEach(Converter::disable);
        preprocessorList.clear();
        triggerList.clear();
        converterList.clear();
    }

    public List<Preprocessor> getPreprocessorList() {
        return preprocessorList;
    }

    public List<Converter> getConverterList() {
        return converterList;
    }

    public List<Trigger> getTriggerList() {
        return triggerList;
    }

    public boolean isSilentMode() {
        return silentMode;
    }

    public boolean isDeepTrigger() {
        return deepTrigger;
    }
}
