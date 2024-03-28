import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * The Room class represents a room in an adventure game.
 * 
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text-based adventure game.
 * 
 * A Room represents one location in the scenery of the game. It is
 * connected to other rooms via exits.
 * @author Matthew Romond
 * @version 2024.03.25
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits; // stores exits of this room.
    private Item item;
    private List<Item> items;

    /**
     * Constructor for objects of class Room.
     * 
     * @param description The room's description.
     * @param item The item present in the room (if any).
     */
    public Room(String description, Item item) 
    {
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
        this.item = item;
    }

    /**
     * Adds an item to the room.
     * 
     * @param item The item to add to the room.
     */
    public void addItem(Item item) 
    {
        items.add(item);
    }

    /**
     * Constructor for objects of class Room with no item.
     * 
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
    }

    /**
     * Check if the room contains a specific item.
     * 
     * @param itemName The name of the item to check.
     * @return true if the room contains the item, false otherwise.
     */
    public boolean hasItem(String itemName) 
    {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the list of items present in the room.
     * 
     * @return The list of items in the room.
     */
    public List<Item> getItems() 
    {
        return items;
    }

    /**
     * Retrieves a specific item from the room.
     * 
     * @param itemName The name of the item to retrieve.
     * @return The item if found, null otherwise.
     */
    public Item getItem(String itemName) 
    {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Removes an item from the room.
     * 
     * @param item The item to remove.
     * @return true if the item was successfully removed, false otherwise.
     */
    public boolean removeItem(Item item) 
    {
        return items.remove(item);
    }

    /**
     * Defines an exit from this room.
     * 
     * @param direction The direction of the exit.
     * @param neighbor The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Retrieves the short description of the room.
     * 
     * @return The short description of the room.
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Retrieves a long description of the room including items and exits.
     * 
     * @return A long description of the room.
     */
    public String getLongDescription() 
    {
        String itemDescription = (item != null) ? "There is a " + item.getDescription() + " in this room.\n" : "";
        return "You are " + description + ".\n" + itemDescription + getExitString();
    }

    /**
     * Retrieves the description of the item present in the room.
     * 
     * @return The description of the item in the room.
     */
    public String getItemDescription() 
    {
        return (item != null) ? "You found a " + item.getDescription() + " in this room.\n" : "";
    }

    /**
     * Retrieves a string describing the room's exits.
     * 
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Retrieves the room that is reached if we go from this room in a specific direction.
     * 
     * @param direction The exit's direction.
     * @return The room in the given direction, or null if there is no room in that direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}