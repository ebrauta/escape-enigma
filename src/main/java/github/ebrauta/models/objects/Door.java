package github.ebrauta.models.objects;

import github.ebrauta.models.InteractiveObject;
import github.ebrauta.models.Item;

public class Door extends InteractiveObject {
    private boolean escaped;
    public Door(String id, String name, String description, String neededItemId) {
        super(id, name, description, neededItemId);
        this.escaped = false;
    }
    @Override
    protected String uponUnlocking(Item usedItem){
        this.escaped = true;
        setDescription(getName() + "(Aberta)");
        return "Você usou " + usedItem.name() + " e a pesada fechadura destrancou!\n" +
                "A porta de abre revelando a luz do dia. VOCÊ ESCAPOU COM SUCESSO!";
    }
    public boolean isEscaped() {
        return escaped;
    }
}
