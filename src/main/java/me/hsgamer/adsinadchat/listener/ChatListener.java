package me.hsgamer.adsinadchat.listener;

import me.hsgamer.adsinadchat.api.Converter;
import me.hsgamer.adsinadchat.api.Preprocessor;
import me.hsgamer.adsinadchat.api.Trigger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

import static me.hsgamer.adsinadchat.AdsInAdChat.getInstance;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (player.hasPermission("aiac.bypass")) {
            return;
        }

        for (Preprocessor preprocessor : getInstance().getPreprocessorList()) {
            message = preprocessor.process(player, message);
        }
        if (message == null) {
            event.setCancelled(true);
            return;
        }

        String finalMessage = message;
        List<Trigger> triggerList = getInstance().getTriggerList();
        if (triggerList.isEmpty() || triggerList.parallelStream().noneMatch(trigger -> trigger.trigger(player, finalMessage))) {
            return;
        }

        for (Converter converter : getInstance().getConverterList()) {
            message = converter.process(player, message);
        }
        if (message == null) {
            event.setCancelled(true);
            return;
        }

        event.setMessage(message);
    }
}
