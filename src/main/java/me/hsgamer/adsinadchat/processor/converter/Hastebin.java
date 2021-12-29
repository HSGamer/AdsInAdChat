package me.hsgamer.adsinadchat.processor.converter;

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
    public Hastebin(String value) {
        super(value);
    }

    @Override
    public String convert(Player player, String text) {
        try {
            byte[] postData = text.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            URL url = new URL("https://hastebin.com/documents");
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

            if (response.contains("\"key\"")) {
                response = response.substring(response.indexOf(":") + 2, response.length() - 2);

                String postURL = "https://hastebin.com/raw/";
                response = postURL + response;
            }

            return response;
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, "Error when converting with Hastebin", e);
            return text;
        }
    }
}
