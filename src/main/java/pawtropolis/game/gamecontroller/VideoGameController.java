package pawtropolis.game.gamecontroller;

import lombok.Getter;
import lombok.Setter;
import pawtropolis.animals.Eagle;
import pawtropolis.animals.Lion;
import pawtropolis.animals.Tiger;
import pawtropolis.game.commands.*;
import pawtropolis.game.model.Item;
import pawtropolis.game.model.Player;
import pawtropolis.game.model.Room;

import java.time.LocalDate;
import java.util.*;

public class VideoGameController {
    @Getter
    private final Player player;
    private final EnumMap<CommandEnum, CommandController> commandActions;
    @Setter
    @Getter
    private Room currentRoom;

    public Room roomMonstadt = new Room("Monstadt");
    public Room roomLiyue = new Room("Liyue");
    public Room roomInazuma = new Room("Inazuma");
    public Room roomSumeru = new Room("Sumeru");
    public Room roomFontaine = new Room("Fontaine");


    public VideoGameController(Player player) {
        this.player = player;
        this.commandActions = new EnumMap<>(CommandEnum.class);
    }

    public void gamePopulation() {

        Item item1 = new Item("long sword", "A Sword user’s Normal Attack is typically a chain of “rapid strikes”", 5);
        Item item2 = new Item("bow", "A Bow user’s Normal Attack launches a chain of fast, mid-ranged shots", 10);
        Item item3 = new Item("polearm", "A Polearm user’s Normal Attack performs a few rapid, consecutive spear strikes", 10);
        Item item4 = new Item("catalyst", "A Catalyst user applies element to enemies when they are hit with Normal Attack", 11);

        Lion lion1 = new Lion("Venti", "Ribs", 4, LocalDate.of(2019, 1, 23), 2.0, 1.28, 40);
        Lion lion2 = new Lion("Zhongli", "Chicken", 8, LocalDate.of(2015, 4, 10), 1.09, 1.17, 36);
        Lion lion3 = new Lion("Raiden", "Pork", 10, LocalDate.of(2013, 12, 5), 2.80, 1.20, 55);

        Tiger tiger1 = new Tiger("Nahida", "Meat", 3, LocalDate.of(2020, 8, 20), 2.50, 0.80, 39);
        Tiger tiger2 = new Tiger("Furina", "Ribs", 14, LocalDate.of(2009, 11, 30), 1.88, 1.10, 47);
        Tiger tiger3 = new Tiger("Neuvilette", "Pork", 8, LocalDate.of(2015, 3, 24), 1.50, 1.80, 34);

        Eagle eagle1 = new Eagle("Xiao", "Rabbit", 30, LocalDate.of(1993, 10, 18), 3.40, 0.69, 23);
        Eagle eagle2 = new Eagle("Dvalin", "Chicken", 30, LocalDate.of(1993, 5, 1), 2.48, 0.90, 33);
        Eagle eagle3 = new Eagle("Ayaka", "Mouse", 1, LocalDate.of(2023, 6, 28), 1.98, 0.45, 13);

        roomMonstadt.addAdjacents(DirectionEnum.WEST, roomLiyue);
        roomLiyue.addAdjacents(DirectionEnum.SOUTH, roomInazuma);
        roomLiyue.addAdjacents(DirectionEnum.WEST, roomSumeru);
        roomSumeru.addAdjacents(DirectionEnum.NORTH, roomFontaine);

        roomMonstadt.addItem(item1);
        roomLiyue.addItem(item2);
        roomInazuma.addItem(item3);
        roomFontaine.addItem(item3);
        player.addItem(item4);

        roomMonstadt.addAnimal(lion1);
        roomMonstadt.addAnimal(eagle2);
        roomLiyue.addAnimal(lion2);
        roomLiyue.addAnimal(eagle1);
        roomInazuma.addAnimal(lion3);
        roomInazuma.addAnimal(eagle3);
        roomSumeru.addAnimal(tiger1);
        roomFontaine.addAnimal(tiger2);
        roomFontaine.addAnimal(tiger3);

        currentRoom = roomMonstadt;
    }

    public void commandAssignment(VideoGameController populateGame){

        commandActions.put(CommandEnum.GO, new GoCommandAction(populateGame));
        commandActions.put(CommandEnum.LOOK, new LookCommandAction( populateGame));
        commandActions.put(CommandEnum.BAG, new BagCommandAction(player));
        commandActions.put(CommandEnum.GET, new GetCommandAction(player, populateGame ));
        commandActions.put(CommandEnum.DROP, new DropCommandAction(player, populateGame));
        commandActions.put(CommandEnum.EXIT, new ExitCommandAction());
    }

    public void startGame() {
        String playerInput;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Pawtropolis!");
        System.out.println("What do want to do next? ");
        System.out.println("Type go to change the room");
        System.out.println("Type look to see what's inside the room ");
        System.out.println("Type bag to view  what's inside the bag");
        System.out.println("Type Exit to end your journey");

        boolean gameEnd = false;
        do {
            System.out.print(" -> ");
            playerInput = scanner.nextLine().toLowerCase().trim();
            String[] inputParts = playerInput.split(" ", 2);

            CommandEnum command = getCommand(inputParts[0]);
            if (command != null) {
                if (commandActions.containsKey(command)) {
                    commandActions.get(command).execute(inputParts);
                } else {
                    System.out.println("Invalid command, try again");
                }
            } else {
                System.out.println("Invalid Input, try again");
            }
            if(inputParts[0].equals("exit")){
                gameEnd = true;
            }
        } while (!gameEnd);
    }

    private CommandEnum getCommand(String input) {
        for (CommandEnum command : CommandEnum.values()) {
            if (command.getCommand().equalsIgnoreCase(input.trim())) {
                return command;
            }
        }
        return null;
    }

}
