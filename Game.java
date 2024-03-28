import java.util.List;
import java.util.Stack;

/**
 * The main class of the "World of Zuul" game.
 * 
 * "World of Zuul" is a very simple, text-based adventure game where users can walk around some scenery.
 * 
 * To play this game, create an instance of this class and call the "play" method.
 * 
 * This class creates and initializes all the other classes: it creates all rooms, creates the parser, and 
 * starts the game.
 * 
 * @author Matthew Romond
 * @version 2024.03.25
 */
public class Game 
{
    private Parser parser;
    private Player currentPlayer;
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> roomHistory = new Stack<>();
    public static final int MAX_INVENTORY_SIZE = 10;
    int maxCarryWeight;

    /**
     * Main method to run the game outside of BlueJ.
     * 
     */
    public static void main(String[] args) 
    {
        Game game = new Game(); 
        game.play(); 
    }

    /**
     * Create the game and initialize its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomHistory = new Stack<>();
        int maxCarryWeight = 50;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, garden, library, courtyard, cafeteria, gym, dorm, hallway, kitchen, bathroom, attic;

        // Create the rooms
        outside = new Room("Outside the main entrance of the university");
        theater = new Room("Inside a lecture theater");
        pub = new Room("Inside the campus pub");
        lab = new Room("Inside a computing lab");
        office = new Room("Inside the computing admin office");
        garden = new Room("In the garden");
        library = new Room("In the library");
        courtyard = new Room("In the courtyard");
        cafeteria = new Room("In the cafeteria");
        gym = new Room("In the gym");
        dorm = new Room("In the student dorm");
        hallway = new Room("In the university hallway");
        kitchen = new Room("In the university kitchen");
        bathroom = new Room("In the university bathroom");
        attic = new Room("In the university attic");

        Item magicCookie = new Item("magic cookie", 0.1, "A magical cookie that grants strength.", Item.COOKIE_TYPE);
        pub.addItem(magicCookie);

        // Initialize room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", garden);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        garden.setExit("south", outside);
        garden.setExit("east", library);

        library.setExit("west", garden);
        library.setExit("north", courtyard);

        courtyard.setExit("south", library);
        courtyard.setExit("east", cafeteria);

        cafeteria.setExit("west", courtyard);
        cafeteria.setExit("north", gym);

        gym.setExit("south", cafeteria);
        gym.setExit("east", dorm);

        dorm.setExit("west", gym);
        dorm.setExit("north", hallway);

        hallway.setExit("south", dorm);
        hallway.setExit("east", kitchen);

        kitchen.setExit("west", hallway);
        kitchen.setExit("north", bathroom);

        bathroom.setExit("south", kitchen);
        bathroom.setExit("up", attic);

        attic.setExit("down", bathroom);

        currentRoom = outside;  // start game outside
        String playerName = "PlayerName"; 
        currentPlayer = new Player(playerName, outside, 50);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case LOOK:
                look();
                break;

            case BACK:
                goBack();
                break;

            case TAKE:
                takeItem(command);
                break;

            case DROP:
                dropItem(command);
                break;

            case EAT:
                eat();
                break;

            default:
                System.out.println("I don't know how to do that.");
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    /**
     * Gives a prompt asking what the player wants to take.
     * @param command The command specifying the item to take.
     */
    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = currentPlayer.getCurrentRoom().getItem(itemName);

        if (item != null) {
            if (currentPlayer.getInventory().size() < MAX_INVENTORY_SIZE) { 
                currentPlayer.addItem(item);
                currentPlayer.getCurrentRoom().removeItem(item);
                System.out.println("You took the " + itemName + ".");
            } else {
                System.out.println("You have no more space in your inventory.");
            }
        } else {
            System.out.println("There is no " + itemName + " in this room.");
        }
    }

    /**
     * Process the DROP command.
     * @param command The command specifying the item to drop.
     */
    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = currentPlayer.getItem(itemName); // Check if the item is in the player's inventory

        if (item != null) {
            currentPlayer.removeItem(item);
            currentPlayer.getCurrentRoom().addItem(item);
            System.out.println("You dropped the " + itemName + ".");
        } else {
            System.out.println("You don't have a " + itemName + ".");
        }
    }

    /**
     * Allow the user to go back to the previous room(s).
     */       
    public void goBack() 
    {
        if (!roomHistory.isEmpty()) 
        {
            // Move to the previous room
            Room previousRoom = roomHistory.pop();
            // Update the current room for the player
            currentPlayer.setCurrentRoom(previousRoom);
            // Provide feedback to the player
            System.out.println(currentPlayer.getCurrentRoom().getLongDescription());
        } 
        else 
        {
            // No previous room to go back to
            System.out.println("You cannot go back further.");
        }
    }

    /**
     * Print out some help information.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your commands are:");
        parser.showCommands();
        System.out.println();
        System.out.println("Type '" + CommandWord.HELP + "' if you need more help.");
    }

    /**
     * Gives the room info with items included.
     */
    public void printRoomInfo() 
    {
        System.out.println(currentRoom.getLongDescription());
        System.out.println(currentRoom.getItemDescription());
        List<Item> items = currentRoom.getItems();
        if (!items.isEmpty()) {
            System.out.println("Items currently in the room:");
            for (Item item : items) {
                System.out.println("- " + item.getDescription() + " (Weight: " + item.getWeight() + ")");
            }
        } else {
            System.out.println("You don't see any items in this room.");
        }
    }

    /**
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * @param command The command specifying the direction to go.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentPlayer.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {

            roomHistory.push(currentPlayer.getCurrentRoom());

            currentPlayer.setCurrentRoom(nextRoom);

            System.out.println(currentPlayer.getCurrentRoom().getLongDescription());
        }
    }

    /**
     * Process the QUIT command.
     * @param command The command to be processed.
     * @return true if the game should end, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Process the ITEMS command to print inventory information.
     */
    private void printInventoryInfo() {
        System.out.println("You are currently carrying:");
        List<Item> inventory = currentPlayer.getInventory();
        double totalWeight = 0;
        for (Item item : inventory) {
            System.out.println("- " + item.getName() + " (Weight: " + item.getWeight() + ")");
            totalWeight += item.getWeight();
        }
        System.out.println("Total weight: " + totalWeight);
    }

    /**
     * Give a description of the player's surroundings when they type LOOK.
     */
    private void look() 
    {
        currentRoom = currentPlayer.getCurrentRoom(); // Update currentRoom to reflect the player's current location
        System.out.println(currentRoom.getLongDescription());

        List<Item> items = currentRoom.getItems();
        if (!items.isEmpty()) {
            System.out.println("Items in the room:");
            for (Item item : items) {
                System.out.println("- " + item.getDescription());
            }
        } else {
            System.out.println("There are no items in this room.");
        }
    }

    /**
     * Process the EAT command.
     */
    private void eat() 
    {
        Item magicCookie = currentPlayer.getItem("magic cookie");

        if (magicCookie != null) {
            currentPlayer.removeItem(magicCookie); // Remove the magic cookie from the player's inventory
            System.out.println("You ate the magic cookie. You feel a lot stronger now!");
            // Adjust the player's maximum carry weight
            currentPlayer.increaseMaxCarryWeight(5);
        } else {
            System.out.println("You don't have a magic cookie to eat.");
        }
    }
}