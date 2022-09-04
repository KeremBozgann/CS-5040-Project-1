
/** 
 * Reference: Significant amount of this taken from OpenDSA code 
 * from the section 15.1. Skip Lists 
 * @author Kerem Bozgan kerembozgan@vt.edu
 * @version 2022-09-03 
 *  */
public class Rectangle1 {
    
    
    private static SkipList<String, String> skip_list;

    public static void main(String[] args) {
        skip_list= 
            new SkipList<String, String>();
        
        String filename = args[0];
        FileReader reader  = new FileReader (filename);
        String cmd; 
        String[] commandArgs; 
        boolean endOfFile = false; 
        while (true) {
             endOfFile = reader.readNextLine(); 
             if (endOfFile)
                 {
                 break;}
             else {
                 // if line is empty, do nothing and continue
                 if (reader.checkIfBlankCommand())
                     continue;
                 else
                 {cmd  = reader.getCurrentCommand();
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
     * */
    public static String processCommand(
        String[] command_args,
        String cmd
        ) {
        //if command is executed successfully or not:
        // Switch the first in the command line
        switch (command_args[0]) {
            case "insert":// Found an add command
                // test to check if the last 4 command args are integers (this
                // test was not really necessary to satisfy project
                // requirements):
                
                switch (checkInsertArgs(command_args)) {
                    case "InvalidNumberOfArgs":
                        return "InvalidNumberOfArgs";
                        
                    case "SomeArgumentsAreNotInt":
                        return "SomeArgumentsAreNotInt";
                        
                    case "Passed":
                        break;
                }

                // get rectangle name:
                String rect_name = command_args[1];
                // get rectangle coords and dims
                String rect_vals = getRectVal(command_args);

                // check if rectangle is in world box and have positive
                // dims:
                if (!checkRectValidity(command_args)) 
                {

                    System.out.println("Rectangle rejected: " + "("
                        + rect_name + ", " + rect_vals + ")");
                    return "Rejected";
                    }
                else {
                    // insert new rectangle:
                    return skip_list.insert(rect_name, rect_vals);
                    }

                

            case "remove":

                // remove by name:
                if (command_args.length == 2 && 
                !isInteger(command_args[1])) {
                    return skip_list.removeByName(command_args[1]);
                }
                // remove by coords (again some non-required input check sinside
                // the
                // following if):
                else if (command_args.length == 5 && isInteger(command_args[1])
                    && isInteger(command_args[2]) && isInteger(command_args[3])
                    && isInteger(command_args[4])) {
                    if (!checkRectValidityRemove(command_args)) 
                    {   
                        
                        System.out.println("Rectangle rejected: " + "("
                            + command_args[1]+ ", " 
                            + command_args[2]+ ", " 
                            + command_args[3]+ ", "
                            + command_args[4]+ ")");
                            
                        return "Rejected";
                        }
                    else
                    {// combine integer args in a string:
                    String rect_coords = getRectVal(command_args);
                    return skip_list.removeByCoords(rect_coords);}
                }
                else
                    return "InvalidArgs";

            case "search":// Found a search command
                return skip_list.find(command_args[1]);

            case "dump": // dump:
                skip_list.dump();
                return "Dumped";

            case "regionsearch":
                // check if rectangle satisfies requirements:
                if (Integer.parseInt(command_args[3]) <= 0 || 
                Integer.parseInt(command_args[4]) <= 0) {
                    System.out.println("Rectangle rejected: " + "(" + command_args[1]+ ", " 
                        + command_args[2]+", "  + command_args[3]+ ", " + command_args[4] + ")");
                    return "Rejected";
                }
                else {
                    String query_rect_vals = getRectVal(command_args);
                    return regionSearch(query_rect_vals);
                }

            case "intersections":
                return intersections();

            default:// Found an unrecognized command
                System.out.println("Unrecognized input " + cmd);
                System.out.println(
                    "Unsuccessful operation due to unrecognized input, moving on to next line to get command");
                return "CommandNotRecognized";
        }
    }

    /** 
     * Give the full list of intersecting rectangles.
     * 
     * 
     * @return string that gives info about the result of the execution 
     * which is then used to assert tests. 
     * */
    public static String intersections() {
        boolean intersectsFound = false; 
        int size = skip_list.getSize();
        SkipNode<String, String> head = skip_list.getHead();
        SkipNode<String, String> x = head;

        System.out.println("Intersections pairs:");
        // outer loop:
        for (int i = 0; i < size; i++) {
            x = x.getForward()[0];
            String x_val = x.element();
            // get upper-right and lower-left coordinates in an integer array:
            int[] x_coords = getUpperRightLowerLeft(x_val);
            SkipNode<String, String> y = head;
            // inner loop (note that j is iterated until i, since it is
            // redundant
            // to compare 2 rectangles twice
            for (int j = 0; j < size; j++) {
                y = y.getForward()[0];
                if (j==i)
                    continue;
                
                String y_val = y.element();
                // get coordinates:
                int[] y_coords = getUpperRightLowerLeft(y_val);

                // using coords, checki f rectangles overlap:
                if (rectsDoNotOverlap(x_coords, y_coords))
                    continue;
                intersectsFound = true;
                System.out.println("(" + x.key() + ", " + x.element() + " | "
                    + y.key() + ", " + y.element() + ")");
            }

        }
        if (intersectsFound)
            return "IntersectsFound" ; 
        else
            return "NoIntersectsFound";
    }

    /** 
     * List all the rectangles that overlap with the given region. 
     * 
     * @param region parameters
     * 
     * @return info about result
     * */
    public static String regionSearch(
        String val
        ) {
        SkipNode<String, String> x = skip_list.getHead();
        boolean rectsFound = false;
        System.out.println("Rectangles intersecting region " + "(" + val + ")"
            + ":");
        
        // iterate over all rects:
        for (int i = 1; i <= skip_list.getSize(); i++) {
            x = x.getForward()[0];
            String x_val = x.element();

            // get coords:
            int[] coords_x = getUpperRightLowerLeft(x_val);

            int[] coords_rect = getUpperRightLowerLeft(val);

            // check if overlap
            if (rectsDoNotOverlap(coords_x, coords_rect))
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
     * @param coords (upper-left lower right)
     * 
     * @return boolean
     * */
    public static boolean rectsDoNotOverlap(int[] coords_x, int[] coords_y)
    {
       
        return coords_x[1] >= coords_y[3]  
            ||coords_y[1] >= coords_x[3]
                || coords_x[0] >= coords_y[2] 
                    ||coords_y[0] >= coords_x[2];
    }

    /** 
     * Get upper left and lower right coordinates as a integer list
     * 
     * */
    public static int[] getUpperRightLowerLeft(String x_val) {
        String[] x_val_list = x_val.split(", ");
        int x_l = Integer.parseInt(x_val_list[0]);
        int x_u = Integer.parseInt(x_val_list[1]);
        // add width and height to upperleft coords to get lower right coords:
        int x_r = Integer.parseInt(x_val_list[0]) + Integer.parseInt(
            x_val_list[2]);
        int x_d = Integer.parseInt(x_val_list[1]) + Integer.parseInt(
            x_val_list[3]);

        int[] coord_list = new int[] { x_l, x_u, x_r, x_d };
        return coord_list;
    }


    // check if last arguments are integer following insert command:
    public static String checkInsertArgs(String[] command_args) {
        if (command_args.length != 6) {
            System.out.println(
                "Invalid number of arguments given after insert command");
            return "InvalidNumberOfArgs";
        }
        for (int i = 2; i < command_args.length; i++) {
            if (!isInteger(command_args[i]))
                return "SomeArgumentsAreNotInt";
        }
        return "Passed";
    }

/** 
 * 
 * check if argument is integer
 * 
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
     * from command get rectangle values, combine them into one string
     * 
     */
    public static String getRectVal(String[] command_args) {
        String rect_values = "";
        for (int i = command_args.length - 4; i <= command_args.length
            - 1; i++) {
            if (i != command_args.length - 1)
                rect_values += Integer.parseInt(command_args[i]) + ", ";
            else
                rect_values += Integer.parseInt(command_args[i]);
        }

        return rect_values;
        
        /** 
         * 
         * Check if given command parameters for the insert command are valid.
         * 
         */        
    }
    
    public static boolean checkRectValidity (String [] commandArgs)
    {
        if (Integer.parseInt(commandArgs[2]) < 0 || 
            Integer.parseInt(commandArgs[3]) < 0 || 
            Integer.parseInt(commandArgs[4]) <= 0 ||
                Integer.parseInt(commandArgs[5]) <= 0 || 
                    Integer.parseInt(commandArgs[2]) + Integer.parseInt(commandArgs[4]) > 1024 
                    || Integer.parseInt(commandArgs[3]) + Integer.parseInt(commandArgs[5]) > 1024)
        return false;
        else 
            return true;
    }
    /** 
     * 
     * Check if given command parameters for the remove(RemoveByCoord) command are valid.
     * 
     */        
    public static boolean checkRectValidityRemove (String [] commandArgs)
    {
        if (Integer.parseInt(commandArgs[1]) < 0 || 
            Integer.parseInt(commandArgs[2]) < 0 || 
            Integer.parseInt(commandArgs[3]) <= 0 ||
                Integer.parseInt(commandArgs[4]) <= 0 || 
                    Integer.parseInt(commandArgs[1]) + Integer.parseInt(commandArgs[3]) > 1024 
                    || Integer.parseInt(commandArgs[2]) + Integer.parseInt(commandArgs[4]) > 1024)
        return false;
        else 
            return true;
    }
    public static void setSkipList() { 
        skip_list = new SkipList<String, String>();
    }

    public static SkipList<String, String> getSkipList() { 
        return skip_list;
    }
    
}
