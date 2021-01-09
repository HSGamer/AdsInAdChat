package me.hsgamer.adsinadchat.processor.converter;

import me.hsgamer.adsinadchat.api.Converter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;

public class AdFocus extends Converter {
    public AdFocus(String value) {
        super(value);
    }

    @Override
    public String convert(Player player, String text) {
        try {
            URL url = new URL("http://adfoc.us/api/?key=" + value + "&url=" + URLEncoder.encode(text, "UTF-8"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Adfocus Java Api");

            if (conn.getResponseCode() != 200) {
                Bukkit.getLogger().warning("Failed to convert with Adfocus, Error code " + conn.getResponseCode());
                return text;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = reader.readLine();

            if (response.equals("0")) {
                response = text;
            }

            conn.disconnect();
            return response;
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, "Error when converting with Adfocus", e);
            return text;
        }
    }

    @Override
    protected boolean isURLRequired() {
        return true;
    }
}
