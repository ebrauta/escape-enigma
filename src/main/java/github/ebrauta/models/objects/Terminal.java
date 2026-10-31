package github.ebrauta.models.objects;

import github.ebrauta.models.InteractiveObject;
import github.ebrauta.models.Inventory;
import github.ebrauta.models.Item;

public class Terminal extends InteractiveObject {
    private final String expectedPassword;
    private final Item rewardItem;

    public Terminal(String id, String name, String description, String neededItemId, String expectedPassword, Item rewardItem) {
        super(id, name, description, neededItemId);
        this.expectedPassword = expectedPassword.toUpperCase();
        this.rewardItem = rewardItem;
    }

    @Override
    public String useItem(Item item) {
        return "O " + getName() + " não tem fechadura física. Ele possui um teclado alfanumérico.";
    }

    public String tryPassword(String inputCode, Inventory inventory) {
        if (!objectIsClosed()) {
            return "O terminal já está desbloqueado.";
        }
        if (!inputCode.isBlank() && inputCode.trim().equalsIgnoreCase(expectedPassword)) {
            openObject();
            String msg = uponUnlocking(null);
            if(rewardItem != null) {
                inventory.addItem(rewardItem);
                msg += "\n[ITEM RECEBIDO]: " + rewardItem.name() + " foi adicionado à sua mochila.";
            }
            return msg;
        }
        return "ACESSO NEGADO: Senha incorreta!";
    }

    @Override
    protected String uponUnlocking(Item usedItem) {
        setDescription(getName() + "(SISTEMA DESBLOQUEADO)");
        return "ACESSO CONCEDIDO! O " + getName() + " emitiu um sinal sonoro e liberou o acesso.";
    }
}
