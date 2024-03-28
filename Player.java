import java.util.ArrayList;
import java.util.List;

/**
 * The Player class represents the player in the game.
 * 
 * @author  Matthew Romond
 * @version 2024.03.25
 */
public class Player
{
    private Room currentRoom;        // The current room the player is in
    private String name;             // The name of the player
    private List<Item> inventory;    // The player's inventory of items
    private String playerName;       // The name of the player
    private int maxCarryWeight;      // The maximum weight the player can carry
    private int currentWeight;       // The current weight of items in the player's inventory

    /**
     * Constructor for creating a Player object.
     * 
     * @param name The name of the player.
     * @param startingRoom The room where the player starts.
     * @param maxCarryWeight The maximum weight the player can carry.
     */
    public Player(String name, Room startingRoom, int maxCarryWeight) 
    {
        this.name = name;
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<>();
        this.maxCarryWeight = maxCarryWeight;
        this.currentWeight = 0;
    }

    /**
     * Increase the maximum carry weight of the player by the specified amount.
     * 
     * @param amount The amount by which to increase the maximum carry weight.
     */
    public void increaseMaxCarryWeight(int amount) 
    {
        this.maxCarryWeight += amount;
    }

    /**
     * Add an item to the player's inventory if there is enough space.
     * 
     * @param item The item to add to the inventory.
     * @return true if the item was successfully added, false otherwise.
     */
    public boolean addItem(Item item) {
        if (currentWeight + item.getWeight() <= maxCarryWeight) {
            inventory.add(item);
            currentWeight += item.getWeight();
            return true;
        } else {
            System.out.println("You cannot carry more. Your inventory is too heavy.");
            return false;
        }
    }

    /**
     * Get a string representation of all items currently carried and their total weight.
     * 
     * @return A string containing information about the items in the inventory.
     */
    public String getInventoryInfo() 
    {
        StringBuilder inventoryInfo = new StringBuilder();
        double totalWeight = 0.0;

        // Iterate over each item in the inventory
        for (Item item : inventory) {
            inventoryInfo.append("- ").append(item.getDescription()).append(" (Weight: ").append(item.getWeight()).append(")\n");
            totalWeight += item.getWeight();
        }

        // Append total weight to the inventory info
        inventoryInfo.append("\nTotal weight: ").append(totalWeight);

        return inventoryInfo.toString();
    }

    /**
     * Remove an item from the player's inventory.
     * 
     * @param item The item to remove from the inventory.
     * @return true if the item was successfully removed, false otherwise.
     */
    public boolean removeItem(Item item) 
    {
        return inventory.remove(item);
    }

    /**
     * Check if the player has a specific item in their inventory.
     * 
     * @param itemName The name of the item to check for.
     * @return true if the player has the item, false otherwise.
     */
    public boolean hasItem(String itemName) 
    {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the item with the specified name from the player's inventory.
     * 
     * @param itemName The name of the item to retrieve.
     * @return The item object if found, null otherwise.
     */
    public Item getItem(String itemName) 
    {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Get the name of the player.
     * 
     * @return The name of the player.
     */
    public String getName() 
    {
        return name;
    }

    /**
     * Set the name of the player.
     * 
     * @param name The new name for the player.
     */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
     * Get the name of the player.
     * 
     * @return The name of the player.
     */
    public String getPlayerName() 
    {
        return playerName;
    }

    /**
     * Set the name of the player.
     * 
     * @param playerName The new name for the player.
     */
    public void setPlayerName(String playerName) 
    {
        this.playerName = playerName;
    }

    /**
     * Get the current room of the player.
     * 
     * @return The current room of the player.
     */
    public Room getCurrentRoom() 
    {
        return currentRoom;
    }

    /**
     * Set the current room of the player.
     * 
     * @param currentRoom The new current room for the player.
     */
    public void setCurrentRoom(Room currentRoom) 
    {
        this.currentRoom = currentRoom;
    }

    /**
     * Get the inventory of the player.
     * 
     * @return The inventory of the player.
     */
    public List<Item> getInventory() 
    {
        return inventory;
    }
}