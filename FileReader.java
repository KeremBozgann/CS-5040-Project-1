import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * File is read line by line and command args are returned in a list
 * 
 * @author Kerem Bozgan kerembozgan
 * @version 2022-09-03
 */
public class FileReader {
    private String filename;
    private Scanner scan;
    private String currentCommand;
    private String[] currentCommandArgs;
    private boolean blankCommand;

    /**
     * Initialize the scanner, catch exception if no file with the given name is
     * found
     * 
     * @param filename
     *            filename
     * 
     */
    public FileReader(String filename) {
        this.filename = filename;

        try {
            File file = new File(this.filename);
            this.scan = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }


    /**
     * Reads file line by line and returns command args in a list
     * 
     * @return true if end of the file is reached
     *         Also updates currentCommand (white space removed command string)
     *         and currentCommandArgs (string list that contains command and its
     *         arguments)
     */
    public boolean readNextLine() {

        if (scan.hasNextLine()) {
            String cmd = scan.nextLine();

            // if line is empty, do nothing and continue
            if (!cmd.isBlank()) {
                this.blankCommand = false;
                // remove multiple spaces between args:
                cmd = removeSpace(cmd);
                this.currentCommand = cmd;
                // Split cmd into componenets
                this.currentCommandArgs = cmd.split(" ");
                return false;
            }
            else
                this.blankCommand = true;
            return false;
        }
        else {
            scan.close();
            return true;
        }

    }


    /**
     * Removes white space
     * 
     * @param cmd
     *            command string
     * @return command
     */
    public static String removeSpace(String cmd) {
        // remove the leading and trailing white spaces:
        cmd = cmd.trim();
        // replace multiple white spaces with single white space:
        cmd = cmd.replaceAll(" +", " ");
        return cmd;
    }


    /**
     * @return private variable currentCommand
     * 
     */

    public String getCurrentCommand() {
        return this.currentCommand;
    }


    /**
     * @return private variable CurrentCommandArgs
     */
    public String[] getCurrentCommandArgs() {
        return this.currentCommandArgs;
    }


    /**
     * @return private variable blankCommand.
     */
    public boolean checkIfBlankCommand() {
        return this.blankCommand;
    }
}
