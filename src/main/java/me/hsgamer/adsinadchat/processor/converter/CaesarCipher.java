package me.hsgamer.adsinadchat.processor.converter;

import me.hsgamer.adsinadchat.api.Converter;
import org.bukkit.entity.Player;

public class CaesarCipher extends Converter {
    private int shift = 0;

    public CaesarCipher(String value) {
        super(value);
    }

    private static String encrypt(String text, int shift) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                chars[i] = (char) (((int) chars[i] + shift - 65) % 26 + 65);
            } else {
                chars[i] = (char) (((int) chars[i] + shift - 97) % 26 + 97);
            }
        }
        return new String(chars);
    }

    @Override
    public String convert(Player player, String text) {
        return encrypt(text, shift);
    }

    @Override
    public void setup() {
        this.shift = Integer.parseInt(value);
    }
}
