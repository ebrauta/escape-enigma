package github.ebrauta.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Room {
    private String name;
    private String description;
    private Map<String, InteractiveObject> objects;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.objects = new HashMap<>();
    }

    public void addObject(InteractiveObject object) {
        if (object != null) {
            objects.put(object.getId().toLowerCase(), object);
        }
    }

    public Optional<InteractiveObject> getObject(String id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(objects.get(id.toLowerCase()));
    }

    public String examine() {
        StringBuilder result = new StringBuilder();
        result.append(description);
        result.append("\n");
        result.append("O que vemos aqui:\n");
        objects.forEach((id, object) -> {
            result.append(object.getDescription());
            result.append("\n");
        });
        return result.toString();
    }

    public String getName() {
        return name;
    }

    public Map<String, InteractiveObject> getObjects() {
        return objects;
    }

    public String getDescription() {
        return description;
    }
}
