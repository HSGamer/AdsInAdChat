package me.hsgamer.adsinadchat.api;

import me.hsgamer.hscore.builder.Builder;
import me.hsgamer.hscore.common.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BaseBuilder<T extends Processor> extends Builder<String, T> {

    /**
     * Build the list
     *
     * @param object the object
     * @return the list
     */
    public List<T> buildList(Object object) {
        List<T> list = CollectionUtils.createStringListFromObject(object, true)
                .stream()
                .flatMap(string -> {
                    String[] split = string.split(":", 2);
                    String name = split[0];
                    String value = split.length > 1 ? split[1] : "";
                    return build(name.trim(), value.trim()).map(Stream::of).orElse(Stream.empty());
                })
                .collect(Collectors.toList());
        list.forEach(Processor::setup);
        return list;
    }
}
