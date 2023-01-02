package me.hsgamer.adsinadchat.processor.converter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.hsgamer.adsinadchat.api.Converter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class Hastebin extends Converter {
    private static final String URL = "https://paste.helpch.at/";
    private static final String API_URL = URL + "documents";
    private static final String DOCUMENT_URL = URL + "%s";

    public Hastebin(String value) {
        super(value);
    }

    @Override
    public String convert(Player player, String text) {
        try {
            byte[] postData = text.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            URL url = new URL(API_URL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", value.isEmpty()
                    ? "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36"
                    : value
            );
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);

            String response;
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            response = reader.readLine();

            JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
            return String.format(DOCUMENT_URL, jsonObject.get("key").getAsString());
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, "Error when converting with Hastebin", e);
            return text;
        }
    }
}
