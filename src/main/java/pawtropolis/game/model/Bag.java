package pawtropolis.game.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
public class Bag {
    private List<Item> items;
    private int slot;

    public Bag(int slot) {
        this.items = new ArrayList<>();
        this.slot = slot;
    }

    public int bagUsedSlots() {
        return items.stream()
                .mapToInt(Item::getSlotRequired)
                .sum();
    }


    public void dropItem(Item item) {
        items.remove(item);
    }

    public boolean isPresent(String itemName) {
        return items.stream()
                .anyMatch(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public Item getItemByName(String itemName) {
        return items.stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }
}
