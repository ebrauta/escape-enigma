package github.ebrauta.models.objects;

import github.ebrauta.models.InteractiveObject;
import github.ebrauta.models.Item;

import java.util.Optional;

public class Drawer extends InteractiveObject {
    private final Item containedItem;
    private boolean looted;

    public Drawer(String id, String name, String description, String neededItemId, Item containedItem) {
        super(id, name, description, neededItemId);
        this.containedItem = containedItem;
        this.looted = false;
    }

    @Override
    protected String uponUnlocking(Item usedItem) {
        return "Você abriu a gaveta! Nela está o " + containedItem.name() + ".";
    }

    public Optional<Item> takeItem() {
        if (objectIsClosed() || looted) {
            return Optional.empty();
        }
        looted = true;
        setDescription(getName() + "(vazia)");
        return Optional.of(containedItem);

    }
}
