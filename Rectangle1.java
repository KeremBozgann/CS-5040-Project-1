// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * Reference: Significant amount of this taken from OpenDSA code
 * from the section 15.1. Skip Lists
 * 
 * @author Kerem Bozgan kerembozgan
 * @version 2022-09-03
 */
public class Rectangle1 {

    private static SkipList<String, String> skipList;

    /**
     * main method for Rectangle1
     * get filename from the command line
     * 
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        skipList = new SkipList<String, String>();

        String filename = args[0];
        FileReader reader = new FileReader(filename);
        String cmd;
        String[] commandArgs;
        boolean endOfFile = false;
        while (true) {
            endOfFile = reader.readNextLine();
            if (endOfFile) {
                break;
            }
            else {
                // if line is empty, do nothing and continue
                if (reader.checkIfBlankCommand())
                    continue;
                else {
                    cmd = reader.getCurrentCommand();
                    commandArgs = reader.getCurrentCommandArgs();
                    Rectangle1.processCommand(commandArgs, cmd);

                }
            }

        }
    }


    /**
     * Command is processed.
     * SkipList methods and static methods in Rectangle1
     * is called from here.
     * 
     * @param commandArgs
     *            command arguments in a list
     * @param cmd
     *            unsplit command string
     * @return info string
     */
    public static String processCommand(String[] commandArgs, String cmd) {
        // if command is executed successfully or not:
        // Switch the first in the command line
        switch (commandArgs[0]) {
            case "insert":// Found an add command
                // test to check if the last 4 command args are integers (this
                // test was not really necessary to satisfy project
                // requirements):

                switch (checkInsertArgs(commandArgs)) {
                    case "InvalidNumberOfArgs":
                        return "InvalidNumberOfArgs";

                    case "SomeArgumentsAreNotInt":
                        return "SomeArgumentsAreNotInt";

                    case "Passed":
                        break;
                }

                // get rectangle name:
                String rectName = commandArgs[1];
                // get rectangle coords and dims
                String rectVals = getRectVal(commandArgs);

                // check if rectangle is in world box and have positive
                // dims:
                if (!checkRectValidity(commandArgs)) {

                    System.out.println("Rectangle rejected: " + "(" + rectName
                        + ", " + rectVals + ")");
                    return "Rejected";
                }
                else {
                    // insert new rectangle:
                    return skipList.insert(rectName, rectVals);
                }

            case "remove":

                // remove by name:
                if (commandArgs.length == 2 && !isInteger(commandArgs[1])) {
                    return skipList.removeByName(commandArgs[1]);
                }
                // remove by coords (again some non-required input check sinside
                // the
                // following if):
                else if (commandArgs.length == 5 && isInteger(commandArgs[1])
                    && isInteger(commandArgs[2]) && isInteger(commandArgs[3])
                    && isInteger(commandArgs[4])) {
                    if (!checkRectValidityRemove(commandArgs)) {

                        System.out.println("Rectangle rejected: " + "("
                            + commandArgs[1] + ", " + commandArgs[2] + ", "
                            + commandArgs[3] + ", " + commandArgs[4] + ")");

                        return "Rejected";
                    }
                    else { // combine integer args in a string:
                        String rectCoords = getRectVal(commandArgs);
                        return skipList.removeByCoords(rectCoords);
                    }
                }
                else
                    return "InvalidArgs";

            case "search":// Found a search command
                return skipList.find(commandArgs[1]);

            case "dump": // dump:
                skipList.dump();
                return "Dumped";

            case "regionsearch":
                // check if rectangle satisfies requirements:
                if (Integer.parseInt(commandArgs[3]) <= 0 || Integer.parseInt(
                    commandArgs[4]) <= 0) {
                    System.out.println("Rectangle rejected: " + "("
                        + commandArgs[1] + ", " + commandArgs[2] + ", "
                        + commandArgs[3] + ", " + commandArgs[4] + ")");
                    return "Rejected";
                }
                else {
                    String queryRectVals = getRectVal(commandArgs);
                    return regionSearch(queryRectVals);
                }

            case "intersections":
                return intersections();

            default:// Found an unrecognized command
                System.out.println("Unrecognized input " + cmd);
                System.out.println(
                    "Unsuccessful operation due to unrecognized input,"
                    + " moving on to next line to get command");
                return "CommandNotRecognized";
        }
    }


    /**
     * Give the full list of intersecting rectangles.
     * 
     * 
     * @return string that gives info about the result of the execution
     *         which is then used to assert tests.
     */
    public static String intersections() {
        boolean intersectsFound = false;
        int size = skipList.getSize();
        SkipNode<String, String> head = skipList.getHead();
        SkipNode<String, String> x = head;

        System.out.println("Intersections pairs:");
        // outer loop:
        for (int i = 0; i < size; i++) {
            x = x.getForward()[0];
            String xVal = x.element();
            // get upper-right and lower-left coordinates in an integer array:
            int[] xCoords = getUpperRightLowerLeft(xVal);
            SkipNode<String, String> y = head;
            // inner loop (note that j is iterated until i, since it is
            // redundant
            // to compare 2 rectangles twice
            for (int j = 0; j < size; j++) {
                y = y.getForward()[0];
                if (j == i)
                    continue;

                String yVal = y.element();
                // get coordinates:
                int[] yCoords = getUpperRightLowerLeft(yVal);

                // using coords, checki f rectangles overlap:
                if (rectsDoNotOverlap(xCoords, yCoords))
                    continue;
                intersectsFound = true;
                System.out.println("(" + x.key() + ", " + x.element() + " | "
                    + y.key() + ", " + y.element() + ")");
            }

        }
        if (intersectsFound)
            return "IntersectsFound";
        else
            return "NoIntersectsFound";
    }


    /**
     * List all the rectangles that overlap with the given region.
     * 
     * @param val
     *            region parameters
     * 
     * @return info about result
     */
    public static String regionSearch(String val) {
        SkipNode<String, String> x = skipList.getHead();
        boolean rectsFound = false;
        System.out.println("Rectangles intersecting region " + "(" + val + ")"
            + ":");

        // iterate over all rects:
        for (int i = 1; i <= skipList.getSize(); i++) {
            x = x.getForward()[0];
            String xVal = x.element();

            // get coords:
            int[] coordsX = getUpperRightLowerLeft(xVal);

            int[] coordsRect = getUpperRightLowerLeft(val);

            // check if overlap
            if (rectsDoNotOverlap(coordsX, coordsRect))
                continue;
            rectsFound = true;
            // means no overlapping
            System.out.println("(" + x.key() + ", " + x.element() + ")");
        }
        if (rectsFound)
            return "RectsFound";
        else
            return "NoRectsFound";

    }


    /**
     * Check if rectangles with given coordinates overlap
     * 
     * @param coordsX
     *            (upper-left lower right) coords of rectangle x
     * @param coordsY
     *            (upper-left lower right) coords of rectangle y
     * @return boolean
     */
    public static boolean rectsDoNotOverlap(int[] coordsX, int[] coordsY) {

        return coordsX[1] >= coordsY[3] || coordsY[1] >= coordsX[3]
            || coordsX[0] >= coordsY[2] || coordsY[0] >= coordsX[2];
    }


    /**
     * Get upper left and lower right coordinates as a integer list
     * 
     * @param xVal
     *            values of rectangle
     * @return coordList
     */
    public static int[] getUpperRightLowerLeft(String xVal) {
        String[] xValList = xVal.split(", ");
        int xLeft = Integer.parseInt(xValList[0]);
        int xUp = Integer.parseInt(xValList[1]);
        // add width and height to upperleft coords to get lower right coords:
        int xRight = Integer.parseInt(xValList[0]) + Integer.parseInt(
            xValList[2]);
        int xDown = Integer.parseInt(xValList[1]) + Integer.parseInt(
            xValList[3]);

        int[] coordList = new int[] { xLeft, xUp, xRight, xDown};
        return coordList;
    }


    /**
     * 
     * check if last arguments are integer following insert command:
     * 
     * @param commandArgs
     *            a list of strings
     * @return info message
     */

    public static String checkInsertArgs(String[] commandArgs) {
        if (commandArgs.length != 6) {
            System.out.println(
                "Invalid number of arguments given after insert command");
            return "InvalidNumberOfArgs";
        }
        for (int i = 2; i < commandArgs.length; i++) {
            if (!isInteger(commandArgs[i]))
                return "SomeArgumentsAreNotInt";
        }
        return "Passed";
    }


    /**
     * 
     * check if argument is integer
     * 
     * @param s
     *            a string
     * @return false if String contents is not integer
     */
    // checks if given string has integer value
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }

    }


    /**
     * 
     * 
     * @param commandArgs
     *            list containing args past from the file
     * @return rectValues from commandArgs get rectangle parameters, combine
     *         them into one string
     */
    public static String getRectVal(String[] commandArgs) {
        String rectValues = "";
        for (int i = commandArgs.length - 4; i <= commandArgs.length - 1; i++) {
            if (i != commandArgs.length - 1)
                rectValues += Integer.parseInt(commandArgs[i]) + ", ";
            else
                rectValues += Integer.parseInt(commandArgs[i]);
        }

        return rectValues;

    }


    /**
     * 
     * Check if given command parameters for the insert command are valid.
     * 
     * @param commandArgs
     *            list containing args past from the file
     * @return false if parameters do not satisfy conditions
     */
    public static boolean checkRectValidity(String[] commandArgs) {
        if (Integer.parseInt(commandArgs[2]) < 0 || Integer.parseInt(
            commandArgs[3]) < 0 || Integer.parseInt(commandArgs[4]) <= 0
            || Integer.parseInt(commandArgs[5]) <= 0 || Integer.parseInt(
                commandArgs[2]) + Integer.parseInt(commandArgs[4]) > 1024
            || Integer.parseInt(commandArgs[3]) + Integer.parseInt(
                commandArgs[5]) > 1024)
            return false;
        else
            return true;
    }


    /**
     * 
     * Check if given command parameters for the remove(RemoveByCoord) command
     * are valid.
     * 
     * @param commandArgs
     *            list containing args past from the file
     * @return false if parameters do not satisfy conditions
     */
    public static boolean checkRectValidityRemove(String[] commandArgs) {
        if (Integer.parseInt(commandArgs[1]) < 0 || Integer.parseInt(
            commandArgs[2]) < 0 || Integer.parseInt(commandArgs[3]) <= 0
            || Integer.parseInt(commandArgs[4]) <= 0 || Integer.parseInt(
                commandArgs[1]) + Integer.parseInt(commandArgs[3]) > 1024
            || Integer.parseInt(commandArgs[2]) + Integer.parseInt(
                commandArgs[4]) > 1024)
            return false;
        else
            return true;
    }


    /**
     * 
     * initialize skipList outside of Rectangle1
     */
    public static void setSkipList() {
        skipList = new SkipList<String, String>();
    }


    /**
     * 
     * access skipList outside of Rectangle1
     * 
     * @return skipList
     */
    public static SkipList<String, String> getSkipList() {
        return skipList;
    }

}
