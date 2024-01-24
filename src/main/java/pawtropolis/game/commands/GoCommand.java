package pawtropolis.game.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pawtropolis.game.gamecontroller.DirectionEnum;
import pawtropolis.game.gamecontroller.GameController;
import pawtropolis.game.model.Door;
import pawtropolis.game.model.Item;
import pawtropolis.game.model.Room;

import java.util.Scanner;

@RequiredArgsConstructor
@Component
public class GoCommand implements Command {
    private final GameController gamePopulation;

    private void goRoom(DirectionEnum direction) {
        if (gamePopulation.getCurrentRoom().getAdjacentsRoom().containsKey(direction)) {
            openDoor(direction);
        } else {
            System.out.println("Invalid direction. Try again.");
        }
    }

    public void openDoor(DirectionEnum direction) {
        Door door = gamePopulation.getCurrentRoom().getDoor(direction);
        Scanner scanner = new Scanner(System.in);
        if (!door.isOpen()) {
            System.out.println("The door is locked. Would you like to use an item to unlock it? Y/N");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("Y")) {
                unlockDoor(direction);
            } else if (answer.equalsIgnoreCase("N")) {
            } else {
                System.out.println("You must answer Y or N");
            }
        } else {
            changeRoom(direction);
        }
    }

    public void unlockDoor(DirectionEnum direction) {
        Door door = gamePopulation.getCurrentRoom().getDoor(direction);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the name of the chosen item");
        String itemName = scanner.nextLine();
        if (gamePopulation.getPlayer().isPresentInBag(itemName) && door.unlock(itemName)) {
            System.out.println("You unlocked the door!");
            Item removedItem = gamePopulation.getPlayer().getItemByName(itemName);
            gamePopulation.getPlayer().dropItem(removedItem);
            changeRoom(direction);
        } else {
            System.out.println("This is not the right item or not present in your bag");
        }
    }

    public void changeRoom(DirectionEnum direction) {
        Room nextRoom = gamePopulation.getCurrentRoom().getAdjacentsRoom().get(direction);
        gamePopulation.setCurrentRoom(nextRoom);
        System.out.println("You have entered " + nextRoom.getName());
    }


    @Override
    public void execute(String[] inputParts) {
        if (inputParts.length == 2) {
            goRoom(DirectionEnum.parseDirection(inputParts[1]));
        } else {
            System.out.println("Please specify a direction.");
        }
    }
}