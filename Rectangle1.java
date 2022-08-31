
/* A significant part of this code are prepared with the help of the course material 
 * at OpenDSA*/
// module 2.7. :Reading Input (from Files or Otherwise)
// import student.TestCase;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Rectangle1 {

    public static void main(String[] args) {
        String filename = args[0];
        File file = new File(filename);
        SkipList<String, String> skip_list = new SkipList<String, String>();

        try {
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String cmd = scan.nextLine();

                // if line is empty, do nothing and continue
                if (cmd.isBlank())
                    continue;

                // remove the leading and trailing white spaces:
                cmd = cmd.trim();
                // replace multiple white spaces with single white space:
                cmd = cmd.replaceAll(" +", " ");
                System.out.println(
                    "================================================");
                System.out.println("Processing command: " + cmd);
                System.out.println("");
                // Split cmd to componenets
                String[] command_args;
                command_args = cmd.split(" ");
// for (int i = 0 ; i<command_args.length; i++)
// System.out.println(command_args[i]);

                skip_list = process_the_command(command_args, cmd, skip_list);
            }

        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }

    }


    public static SkipList<String, String> process_the_command(
        String[] command_args,
        String cmd,
        SkipList<String, String> skip_list) {
        // Switch the first element

        switch (command_args[0]) {
            case "insert":// Found an add command

                // check if the command args are correct:
                boolean test_input = checkInsertArgs(command_args);
                if (!test_input) {
                    System.out.println(
                        "Invalid arguments are given after insert");
                    System.out.println("Continuing to the next line");
                    break;
                }
                else {
                    String rect_name = command_args[1];
                    String rect_vals = getRectVal(command_args);

                    if (Integer.parseInt(command_args[2]) < 0 || Integer
                        .parseInt(command_args[3]) < 0 || Integer.parseInt(
                            command_args[4]) <= 0 || Integer.parseInt(
                                command_args[5]) <= 0 || Integer.parseInt(
                                    command_args[2]) + Integer.parseInt(
                                        command_args[4]) > 1024 || Integer
                                            .parseInt(command_args[3]) + Integer
                                                .parseInt(
                                                    command_args[5]) > 1024)
                        System.out.println("Rectangle rejected: " + "( "
                            + rect_name + ", " + rect_vals + " )");

                    else
                        // insert new rectangle:
                        skip_list.insert(rect_name, rect_vals);

                }

                break;
            case "remove":// Found a delete command
                if (command_args.length == 2 && !isInteger(command_args[1])) {
                    skip_list.removeByName(command_args[1]);
                }
                else if (command_args.length == 5 && isInteger(command_args[1])
                    && isInteger(command_args[2]) && isInteger(command_args[3])
                    && isInteger(command_args[4])) {
                    String rect_coords = getRectVal(command_args);
                    skip_list.removeByCoords(rect_coords);
                }

                break;
            case "search":// Found a search command
                skip_list.find(command_args[1]);
                break;

            case "dump": // dump:
                skip_list.dump();
                break;

            case "regionsearch":
                if (Integer.parseInt(command_args[3]) <= 0 || Integer.parseInt(
                    command_args[4]) <= 0) {
                    System.out.println("");
                    System.out.println("Rectangle rejected");
                }
                else {
                    String query_rect_vals = getRectVal(command_args);

                    regionSearch(query_rect_vals, skip_list);
                }
                break;

            case "intersections":
                intersections(skip_list);
                break;

            default:// Found an unrecognized command
                System.out.println("Unrecognized input " + cmd);
                System.out.println(
                    "Unsuccessful operation due to unrecognized input, moving on to next line to get command");
                break;
        }
        return skip_list;
    }


    public static void intersections(SkipList<String, String> skip_list) {

        int size = skip_list.getSize();
        SkipNode<String, String> head = skip_list.getHead();
        SkipNode<String, String> x = head;

        System.out.println("");
        System.out.println("Intersections pairs:");
        for (int i = 0; i < size; i++) {
            x = x.forward[0];
            String x_val = x.element();
            int[] x_coords = getUpperRightLowerLeft(x_val);
            SkipNode<String, String> y = head;

            for (int j = 0; j < i; j++) {
                y = y.forward[0];
                String y_val = y.element();

                int[] y_coords = getUpperRightLowerLeft(y_val);

                if (rectsDoNotOverlap(x_coords, y_coords))

                    continue;
                System.out.println("( " + x.key() + ", " + x.element() + " | "
                    + y.key() + ", " + y.element() + " )");

            }

        }
    }


    public static void regionSearch(
        String val,
        SkipList<String, String> skip_list) {

        SkipNode<String, String> x = skip_list.getHead();

        System.out.println("");
        System.out.println("Rectangles intersecting region " + "( " + val + ")"
            + " :");

        for (int i = 1; i <= skip_list.getSize(); i++) {
            x = x.forward[0];
            String x_val = x.element();

            int[] coords_x = getUpperRightLowerLeft(x_val);

            int[] coords_rect = getUpperRightLowerLeft(val);

            if (rectsDoNotOverlap(coords_x, coords_rect))
                continue;
            // means no overlapping
            System.out.println("( " + x.key() + ", " + x.element() + " )");
        }

    }


    public static boolean rectsDoNotOverlap(int[] coords_x, int[] coords_y) {
        return coords_x[1] >= coords_y[3] || coords_y[1] >= coords_x[3]
            || coords_x[0] >= coords_y[2] || coords_y[0] >= coords_x[2];
    }


    public static int[] getUpperRightLowerLeft(String x_val) {
        String[] x_val_list = x_val.split(", ");
        int x_l = Integer.parseInt(x_val_list[0]);
        int x_u = Integer.parseInt(x_val_list[1]);
        int x_r = Integer.parseInt(x_val_list[0]) + Integer.parseInt(
            x_val_list[2]);
        int x_d = Integer.parseInt(x_val_list[1]) + Integer.parseInt(
            x_val_list[3]);

        int[] coord_list = new int[] { x_l, x_u, x_r, x_d };
        return coord_list;
    }


    public static void intersections() {

    }


    // check if valid arguments are given after "insert" command:
    public static boolean checkInsertArgs(String[] command_args) {
        if (command_args.length != 6) {
            System.out.println(
                "Invalid number of arguments given after insert command");
            return false;
        }
        for (int i = 2; i < command_args.length; i++) {
            if (!isInteger(command_args[i]))
                return false;
        }
        return true;
    }


    // checks if given string has integer value
    public static boolean isInteger(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }

    }


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
    }

// Scanner scan = new Scanner (new )

}
