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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        if (player.hasPermission("aiac.bypass")) {
            return;
        }

        String message = event.getMessage();
        if (!isTriggered(player, message)) {
            return;
        }

        if (instance.isSilentMode()) {
            silentConvert(event, player, message);
        } else {
            normalConvert(event, player, message);
        }
    }

    private void normalConvert(AsyncPlayerChatEvent event, Player player, String message) {
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

    private void silentConvert(AsyncPlayerChatEvent event, Player player, String message) {
        final String format = event.getFormat();
        final String displayName = player.getDisplayName();
        final AtomicReference<String> messageRef = new AtomicReference<>(message);
        final List<Player> recipients = new ArrayList<>();
        event.getRecipients().removeIf(recipient -> {
            if (recipient != player) {
                recipients.add(recipient);
                return true;
            }
            return false;
        });
        if (recipients.isEmpty()) {
            return;
        }
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
    }

    private boolean isTriggered(Player player, String message) {
        List<Trigger> triggerList = instance.getTriggerList();
        if (triggerList.isEmpty()) {
            return false;
        }

        Set<String> preprocessedMessage = new HashSet<>();
        if (instance.isDeepTrigger()) {
            preprocessedMessage.add(message);
        }
        for (Preprocessor preprocessor : instance.getPreprocessorList()) {
            if (message == null) {
                break;
            }
            message = preprocessor.process(player, message);
            if (message != null && instance.isDeepTrigger()) {
                preprocessedMessage.add(message);
            }
        }
        if (message != null) {
            preprocessedMessage.add(message);
        }

        for (String preprocessed : preprocessedMessage) {
            if (triggerList.parallelStream().anyMatch(trigger -> trigger.trigger(player, preprocessed))) {
                return true;
            }
        }
        return false;
    }
}
