package me.hsgamer.adsinadchat.processor.converter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.hsgamer.adsinadchat.api.Converter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;

public class Cuttly extends Converter {
    public Cuttly(String value) {
        super(value);
    }

    @Override
    public String convert(Player player, String text) {
        try {
            URL url = new URL("https://cutt.ly/api/api.php?key=" + value + "&short=" + URLEncoder.encode(text, "UTF-8"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                Bukkit.getLogger().warning("Failed to convert with Cuttly, Error code " + conn.getResponseCode());
                return text;
            }

            JsonObject jsonObject = new JsonParser().parse(new InputStreamReader((conn.getInputStream()))).getAsJsonObject();
            JsonObject urlResponse = jsonObject.getAsJsonObject("url");
            String shortLink = "";
            if (urlResponse.get("status").getAsString().equals("7")) {
                shortLink = urlResponse.get("shortLink").getAsString();
            }

            conn.disconnect();
            return shortLink;
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, "Error when converting with Cuttly", e);
            return text;
        }
    }
}
