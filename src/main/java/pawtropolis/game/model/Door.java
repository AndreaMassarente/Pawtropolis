package pawtropolis.game.model;

import lombok.Getter;

@Getter
public class Door {
    private boolean isOpen;

    private String requiredKey;

    public Door(boolean isOpen, String requiredKey) {
        this.isOpen = isOpen;
        this.requiredKey = requiredKey;
    }

    public Door(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean unlock(String itemName) {
        isOpen = !isOpen && itemName.equalsIgnoreCase(requiredKey);
        return isOpen;
    }
}
