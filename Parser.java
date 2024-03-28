import java.util.Scanner;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two-word command.
 * @author  Matthew Romond
 * @version 2024.03.25
 */
public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() 
    {
        String inputLine;   
        String commandString = null;
        String argument = null;

        System.out.print("> ");    

        inputLine = reader.nextLine().toLowerCase(); 

        
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            commandString = tokenizer.next();      
            if(tokenizer.hasNextLine()) {
                argument = tokenizer.nextLine().trim(); 
            }
        }

        if (commands.isCommand(commandString)) {
            CommandWord commandWord = commands.getCommandWord(commandString);
            return new Command(commandWord, argument);
        } else if (commandString.equals("look")) { 
            return new Command(CommandWord.LOOK, argument);
        } else if (commandString.equals("eat")) { 
            return new Command(CommandWord.EAT, argument);
        } else {
            return new Command(CommandWord.UNKNOWN, argument);
        }
    }

    /**
     * Print out a list of valid command words.
     */
    public void showCommands()
    {
        commands.showAll();
    }
}