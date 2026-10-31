package github.ebrauta;

import github.ebrauta.models.objects.Door;
import github.ebrauta.models.objects.Drawer;
import github.ebrauta.models.Inventory;
import github.ebrauta.models.Item;
import github.ebrauta.models.Room;
import github.ebrauta.models.objects.Terminal;
import github.ebrauta.parser.Command;
import github.ebrauta.parser.ParserCommand;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // --- 1. LEVEL DESIGN (Itens e Recompensas) ---
        Item chaveGaveta = new Item("chave-gaveta", "Chave de Latão", "Uma chave pequena e gasta.");
        Item bilheteCifrado = new Item("bilhete", "Bilhete Cifrado", "O papel diz: 'KHOOR ZRUOG' (Shift: 3).");
        Item chaveSaida = new Item("chave-saida", "Chave Mestra", "Uma chave pesada de ferro.");

        // --- 2. OBJETOS INTERATIVOS ---
        // A gaveta precisa da chave-gaveta e contém o bilhete
        Drawer gaveta = new Drawer("gaveta", "Gaveta da Mesa", "Gaveta de madeira antiga.",  "chave-gaveta", bilheteCifrado);

        // O terminal exige a senha decifrada ("HELLO WORLD")
        Terminal terminal = new Terminal("terminal", "Terminal Verde", "Um computador antigo emitindo luz verde.", "bilhete","HELLO WORLD", chaveSaida);

        // A porta de saída exige a chave-saida
        Door portaSaida = new Door("porta", "Porta Principal", "Uma porta pesada de ferro trancada.", "chave-saida");

        // --- 3. MONTAGEM DA SALA E MUNDO ---
        Room sala = new Room("Escritório Secreto", "Você está em uma sala fria com cheiro de mofo. Há uma mesa com uma gaveta, um terminal na parede e uma porta de ferro.");
        sala.addObject(gaveta);
        sala.addObject(terminal);
        sala.addObject(portaSaida);

        // --- 4. INVENTÁRIO INICIAL ---
        Inventory mochila = new Inventory();
        mochila.addItem(chaveGaveta); // Jogador já começa com a chave da gaveta no bolso

        // --- 5. EXECUÇÃO DO JOGO ---
        GameEngine engine = new GameEngine(sala, mochila);
        Scanner scanner = new Scanner(System.in);

        exibirBoasVindas(sala);

        // Loop principal do jogo
        while (engine.isRunning()) {
            System.out.print("\n> ");
            String entrada = scanner.nextLine();

            Command comando = ParserCommand.parse(entrada);
            engine.processCommand(comando);
        }

        System.out.println("\nObrigado por jogar o Escape-Enigma!");
        scanner.close();
    }

    private static void exibirBoasVindas(Room sala) {
        System.out.println("=".repeat(30));
        System.out.println("         BEM-VINDO AO ESCAPE-ENIGMA              ");
        System.out.println("=".repeat(30));
        System.out.println("Comandos disponíveis:");
        System.out.println("  - examinar [sala / objeto]");
        System.out.println("  - usar [item] em [objeto]");
        System.out.println("  - pegar [item] de [objeto]");
        System.out.println("  - digitar [senha] em [terminal]");
        System.out.println("  - mochila (ou inventario)");
        System.out.println("  - sair");
        System.out.println("=".repeat(30));
        System.out.println(sala.examine());
    }
}