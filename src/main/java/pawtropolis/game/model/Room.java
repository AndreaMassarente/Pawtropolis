package pawtropolis.game.model;

import lombok.Data;
import org.springframework.stereotype.Component;
import pawtropolis.animals.Animal;
import pawtropolis.game.gamecontroller.DirectionEnum;

import java.util.*;

@Data
public class Room {
    private String name;
    private final List<Item> items;
    private List<Animal> animals;
    private EnumMap<DirectionEnum, Room> adjacentsRoom;
    private EnumMap<DirectionEnum, Door> doors;


    public Room(String roomName) {
        this.name = roomName;
        this.adjacentsRoom = new EnumMap<>(DirectionEnum.class);
        this.items = new ArrayList<>();
        this.animals = new ArrayList<>();
        this.doors = new EnumMap<>(DirectionEnum.class);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void dropItem(Item item) {
        items.remove(item);
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void addAdjacents(DirectionEnum direction, Room nextRoom) {
        adjacentsRoom.put(direction, nextRoom);
        nextRoom.adjacentsRoom.put(DirectionEnum.getOppositeDirection(direction), this);
    }

    public void addDoor(DirectionEnum direction, Door door) {
        doors.put(direction, door);
    }

    public Map<DirectionEnum, Room> getAdjacentsRoom() {
        return adjacentsRoom;
    }





}
