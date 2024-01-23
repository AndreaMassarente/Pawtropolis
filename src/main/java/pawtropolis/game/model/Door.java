package pawtropolis.game.model;

public class Door {

    private boolean isOpen;

    public Door(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void unlock(String itemName) {
        isOpen = !isOpen && itemName.equalsIgnoreCase("key");
    }
}
