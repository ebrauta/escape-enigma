package github.ebrauta.parser;

import java.util.Locale;

public class ParserCommand {
    private ParserCommand() {}
    public static Command parse(String bruteEntry) {
        if (bruteEntry == null || bruteEntry.trim().isEmpty()) {
            return new Command("DESCONHECIDO", null, null);
        }
        String text = bruteEntry.trim();
        String textLower = text.toLowerCase(Locale.ROOT);
        if(textLower.equals("inventario")||textLower.equals("mochila")){
            return new Command("INVENTARIO", null, null);
        }
        if(textLower.startsWith("usar") && textLower.contains(" em ")){
            String[] parts = text.substring(5).split("(?i) em", 2);
            return new Command("USAR", parts[0].trim(), parts[1].trim());
        }
        if (textLower.startsWith("pegar ") && textLower.contains(" de ")) {
            String[] partes = text.substring(6).split("(?i) de ", 2);
            return new Command("PEGAR", partes[0].trim(), partes[1].trim());
        }

        // Caso: "digitar [senha] em [terminal]"
        if (textLower.startsWith("digitar ") && textLower.contains(" em ")) {
            String[] partes = text.substring(8).split("(?i) em ", 2);
            return new Command("DIGITAR", partes[0].trim(), partes[1].trim());
        }

        // Caso: "examinar [alvo]" (ex: "examinar sala" ou "examinar gaveta")
        if (textLower.startsWith("examinar ")) {
            String alvo = text.substring(9).trim();
            return new Command("EXAMINAR", alvo, null);
        }

        return new Command("DESCONHECIDO", text, null);
    }
}
