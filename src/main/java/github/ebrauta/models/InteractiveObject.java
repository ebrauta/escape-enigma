package github.ebrauta.models;

public abstract class InteractiveObject {
    private String id;
    private String name;
    private String description;
    private boolean isClosed;
    private final String neededItemId;

    protected InteractiveObject(String id, String name, String description,String neededItemId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isClosed = true;
        this.neededItemId = neededItemId != null ? neededItemId.toLowerCase() : null;
    }

    public String examine() {
        if (isClosed) return description + " está trancado.";
        return description;
    }

    public String useItem(Item item) {
        if (!isClosed) {
            return "Nada acontece. " + name + " já está destrancado.";
        }
        if (!neededItemId.isBlank() && item.id().toLowerCase().equals(neededItemId)) {
            openObject();
            return uponUnlocking(item);
        }
        return "Isso não parece funcionar em  " + name + ".";
    }

    protected String uponUnlocking(Item usedItem) {
        return "Você usou " + usedItem.name() + " e destrancou " + name + "!";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean objectIsClosed() {
        return isClosed;
    }
    public void openObject() {
        this.isClosed = false;
    }
    public void closeObject() {
        this.isClosed = true;
    }

    public String getNeededItemId() {
        return neededItemId;
    }
}
