package me.hsgamer.adsinadchat.listener;

import me.hsgamer.adsinadchat.AdsInAdChat;
import me.hsgamer.adsinadchat.api.Converter;
import me.hsgamer.adsinadchat.api.Preprocessor;
import me.hsgamer.adsinadchat.api.Trigger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The chat listener
 */
public class ChatListener implements Listener {
    private final AdsInAdChat instance;

    public ChatListener(AdsInAdChat instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (player.hasPermission("aiac.bypass")) {
            return;
        }

        for (Preprocessor preprocessor : instance.getPreprocessorList()) {
            if (message == null) {
                break;
            }
            message = preprocessor.process(player, message);
        }
        if (message == null) {
            event.setCancelled(true);
            return;
        }

        List<Trigger> triggerList = instance.getTriggerList();
        if (triggerList.isEmpty()) {
            return;
        }
        boolean triggered = false;
        for (Trigger trigger : triggerList) {
            if (trigger.trigger(player, message)) {
                triggered = true;
                break;
            }
        }
        if (!triggered) {
            return;
        }

        if (instance.isSilentMode()) {
            final String format = event.getFormat();
            final String displayName = player.getDisplayName();
            final List<Player> recipients = new ArrayList<>(event.getRecipients());
            final AtomicReference<String> messageRef = new AtomicReference<>(message);
            event.getRecipients().removeIf(recipient -> recipient != player);
            Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
                for (Converter converter : instance.getConverterList()) {
                    if (messageRef.get() == null) {
                        break;
                    }
                    messageRef.set(converter.convert(player, messageRef.get()));
                }
                String finalMessage = messageRef.get();
                if (finalMessage == null) {
                    return;
                }
                String formatMessage = String.format(format, displayName, finalMessage);
                for (Player recipient : recipients) {
                    recipient.sendMessage(formatMessage);
                }
            });
        } else {
            for (Converter converter : instance.getConverterList()) {
                if (message == null) {
                    break;
                }
                message = converter.process(player, message);
            }
            if (message == null) {
                event.setCancelled(true);
                return;
            }
            event.setMessage(message);
        }
    }
}
