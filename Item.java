/**
 * The Item class represents an item that can be found or carried by players in the game.
 * 
 * @author  Matthew Romond
 * @version 2024.03.25
 */
public class Item {
    private String description; // Description of the item
    private double weight;      // Weight of the item
    private String name;        // Name of the item
    public static final String COOKIE_TYPE = "cookie"; // Constant for item type
    private String type;        // Type of the item

    /**
     * Constructor for creating an Item object.
     * 
     * @param name The name of the item.
     * @param weight The weight of the item.
     * @param description The description of the item.
     * @param type The type of the item.
     */
    public Item(String name, double weight, String description, String type) 
    {
        this.name = name;
        this.weight = weight;
        this.description = description;
        this.type = type;
    }
    
    /**
     * Get the name of the item.
     * 
     * @return The name of the item.
     */
    public String getName() 
    {
        return name;
    }
    
    /**
     * Get the description of the item.
     * 
     * @return The description of the item.
     */
    public String getDescription() 
    {
        return description;
    }

    /**
     * Get the weight of the item.
     * 
     * @return The weight of the item.
     */
    public double getWeight() 
    {
        return weight;
    }
    
    /**
     * Get the type of the item.
     * 
     * @return The type of the item.
     */
    public String getType() 
    {
        return type;
    }
}
