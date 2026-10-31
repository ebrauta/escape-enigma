package github.ebrauta.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inventory {
    private final List<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Optional<Item> getItemById(String id) {
        return items.stream()
                .filter(item -> item.id().equalsIgnoreCase(id))
                .findFirst();
    }

    public List<Item> getItems() {
        return items;
    }

    public List<String> seeInventory() {
        return items.stream().map(Item::name).toList();
    }
}
