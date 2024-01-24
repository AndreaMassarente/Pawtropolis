package pawtropolis.game.model;

import lombok.Getter;

@Getter
public class Door {
    private boolean isOpen;

    private final String requiredKey;

    public Door(boolean isOpen, String requiredKey) {
        this.isOpen = isOpen;
        this.requiredKey = requiredKey;
    }

    public boolean unlock(String itemName) {
        isOpen = !isOpen && itemName.equalsIgnoreCase(requiredKey);
        return isOpen;
    }
}
