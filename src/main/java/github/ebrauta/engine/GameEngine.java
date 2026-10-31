package github.ebrauta.engine;

import github.ebrauta.models.InteractiveObject;
import github.ebrauta.models.Inventory;
import github.ebrauta.models.Item;
import github.ebrauta.models.Room;
import github.ebrauta.models.objects.Door;
import github.ebrauta.models.objects.Drawer;
import github.ebrauta.models.objects.Terminal;
import github.ebrauta.parser.Command;
import github.ebrauta.parser.ParserCommand;

import java.util.Optional;
import java.util.Scanner;

public class GameEngine {
    private final Room currentRoom;
    private final Inventory inventory;
    private boolean running;

    public GameEngine(Room currentRoom, Inventory inventory) {
        this.currentRoom = currentRoom;
        this.inventory = inventory;
        this.running = true;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== BEM-VINDO AO ESCAPE-ENIGMA ===");
        System.out.println(currentRoom.examine());
        while (running) {
            System.out.print("\n> ");
            String input = scanner.nextLine();
            Command command = ParserCommand.parse(input);
            processCommand(command);
        }
        scanner.close();
    }

    public void processCommand(Command command) {
        switch (command.action()) {
            case "EXAMINAR" -> treatExamineCmd(command.target());
            case "USAR" -> treateUseCmd(command.target(), command.complement());
            case "PEGAR" -> treatCatchCmd(command.target(), command.complement());
            case "DIGITAR" -> treatWriteCmd(command.target(), command.complement());
            case "INVENTARIO" -> treatInventaryCmd();
            case "SAIR" -> {
                System.out.println("Você desistiu do enigma e fugiu do Jogo.");
                this.running = false;
            }
            default -> System.out.println("Comando não reconhecido. Tente: examinar, usar, pegar, digitar ou mochila.");
        }
    }

    private void treatExamineCmd(String target) {
        if (target == null || target.equalsIgnoreCase("sala") || target.equalsIgnoreCase("ambiente")) {
            System.out.println(currentRoom.examine());
            return;
        }
        Optional<Item> itemInventory = inventory.getItemById(target);
        if (itemInventory.isPresent()) {
            Item item = itemInventory.get();
            System.out.println("[" + item.name() + "]: " + item.description());
            return;
        }

        Optional<InteractiveObject> obj = currentRoom.getObject(target);
        if (obj.isPresent()) {
            System.out.println(obj.get().examine());
            return;
        }
        System.out.println("Não há nada chamado '" + target + "' nesta sala");
    }

    private void treateUseCmd(String idItem, String idObject) {
        Optional<Item> itemOpt = inventory.getItemById(idItem);
        if (itemOpt.isEmpty()) {
            System.out.println("Você não possui o item '" + idItem + "' na sua mochila.");
            return;
        }

        Optional<InteractiveObject> objOpt = currentRoom.getObject(idObject);
        if (objOpt.isEmpty()) {
            System.out.println("Não existe o objeto '" + idObject + "' no local.");
            return;
        }

        InteractiveObject object = objOpt.get();
        String result = object.useItem(itemOpt.get());
        System.out.println(result);

        // Checagem de condição de vitória (se for a Door)
        if (object instanceof Door door && door.isEscaped()) {
            this.running = false;
        }
    }

    private void treatCatchCmd(String idObject) {
        Optional<InteractiveObject> objOpt = currentRoom.getObject(idObject);
        if (objOpt.isEmpty()) {
            System.out.println("Objeto '" + idObject + "' não encontrado.");
            return;
        }

        InteractiveObject object = objOpt.get();
        if (object instanceof Drawer drawer) {
            Optional<Item> collectedItem = drawer.takeItem();
            if (collectedItem.isPresent()) {
                inventory.addItem(collectedItem.get());
                System.out.println("Você guardou " + collectedItem.get().name() + " na mochila.");
            }
        } else {
            System.out.println("Não é possível pegar itens de " + object.getName() + ".");
        }
    }

    private void treatWriteCmd(String pass, String idTerminal) {
        Optional<InteractiveObject> objOpt = currentRoom.getObject(idTerminal);
        if (objOpt.isEmpty()) {
            System.out.println("Terminal '" + idTerminal + "' não encontrado.");
            return;
        }

        if (objOpt.get() instanceof Terminal terminal) {
            String response = terminal.tryPassword(pass, inventory);
            System.out.println(response);
        } else {
            System.out.println("O objeto '" + idTerminal + "' não aceita senhas.");
        }
    }

    private void treatInventaryCmd() {
        if (inventory.seeInventory().isEmpty()) {
            System.out.println("Sua mochila está vazia.");
        } else {
            System.out.println("--- Seus Itens ---");
            inventory.getItems().forEach(item ->
                    System.out.println("- " + item.name() + " (" + item.id() + "): " + item.description())
            );
        }
    }

    public boolean isRunning() {
        return running;
    }
}