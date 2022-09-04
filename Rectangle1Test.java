import student.TestCase;

/**
 * Contains all the tests of classes
 * 
 * @author Kerem Bozgan kerembozgan
 * @version 2022-09-03
 */
public class Rectangle1Test extends TestCase {

    /**
     * Exhaustive test for classes
     */

    public void testProcessCommand() {
        // set up skipList
        Rectangle1.setSkipList();

        // test insert command
        String cmd1 = "insert test1 1   20  50 40 ";
        cmd1 = FileReader.removeSpace(cmd1);
        String[] commandArgs1 = cmd1.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs1, cmd1),
            "Inserted");

        String cmd2 = "insert test2 -1   20  50 40 ";
        cmd2 = FileReader.removeSpace(cmd2);
        String[] commandArgs2 = cmd2.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs2, cmd2),
            "Rejected");

        String cmd3 = "insert test3 1   -1  50 40 ";
        cmd3 = FileReader.removeSpace(cmd3);
        String[] commandArgs3 = cmd3.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs3, cmd3),
            "Rejected");

        String cmd4 = "insert test4 1   20  2000 40 ";
        cmd4 = FileReader.removeSpace(cmd4);
        String[] commandArgs4 = cmd4.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs4, cmd4),
            "Rejected");

        String cmd5 = "insert test5 1   20  50 2000";
        cmd5 = FileReader.removeSpace(cmd5);
        String[] commandArgs5 = cmd5.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs5, cmd5),
            "Rejected");

        String cmd6 = "insert test6 1   20  -1 2000";
        cmd6 = FileReader.removeSpace(cmd6);
        String[] commandArgs6 = cmd6.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs6, cmd6),
            "Rejected");

        String cmd7 = "insert test7 1   20  1 -1";
        cmd7 = FileReader.removeSpace(cmd7);
        String[] commandArgs7 = cmd7.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs7, cmd7),
            "Rejected");

        String cmd8 = "insert test8 a   20  1 -1";
        cmd8 = FileReader.removeSpace(cmd8);
        String[] commandArgs8 = cmd8.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs8, cmd8),
            "SomeArgumentsAreNotInt");

        String cmd9 = "insert test9 1   20  1 10 20 20";
        cmd9 = FileReader.removeSpace(cmd9);
        String[] commandArgs9 = cmd9.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs9, cmd9),
            "InvalidNumberOfArgs");

        // test remove command:
        String cmd = "remove 1   20  50 40 ";
        cmd = FileReader.removeSpace(cmd);
        String[] commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "RemovedByCoords");

        cmd = "remove 1   20  50 40 ";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "ListEmpty");

        cmd = "insert rect 1   20  1 20";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "intersections";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "NoIntersectsFound");

        cmd = "remove rect";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "RemovedByName");

        cmd = "remove 1";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "InvalidArgs");

        cmd = "remove 1 2 3 4 5";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "InvalidArgs");

        cmd = "remove a 2 3 4";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "InvalidArgs");

        cmd = "remove 1 a 3 4";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "InvalidArgs");

        cmd = "remove 1 2 a 4";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "InvalidArgs");

        cmd = "remove 1 2 3 a";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "InvalidArgs");

        cmd = "remove test";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "NotInList");

        // test search command
        cmd = "dump";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "search rect";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "NotFound");

        cmd = "insert rect 1   20  1 20";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "search rect";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Found");

        cmd = "insert rect2 1   20  1 20";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "search rect2";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Found");

        cmd = "insert rect2 1   20  1 20";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "search rect2";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "MultipleFound");

        cmd = "search rect3";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "NotFound");

        cmd = "search rect";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Found");

        // test dump command
        cmd = "dump";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Dumped");

        // test regionsearch command

        cmd = "insert rect4 500   500 100 100 ";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "regionsearch -5 -5 -3 50";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Rejected");

        cmd = "regionsearch -5 -5 20 -5";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Rejected");

        cmd = "regionsearch -5 -5 50 50";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "RectsFound");

        // test intersections command

        cmd = "intersections";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "IntersectsFound");

        cmd = "insert rect1 0  0 100 100 ";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "insert rect2 100 0 100 100 ";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "insert rect5 200 200 200 200 ";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "insert rect6 200 500 200 200";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "intersections";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "IntersectsFound");

        // test default
        cmd = "test";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "CommandNotRecognized");

        // further remove tests
        cmd = "insert rect6 1023 1 1 1 ";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "remove rect6";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "RemovedByName");

        cmd = "remove 20 20 20 20";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "EOL");

        // test getLevel inside SkipList:
        cmd = "dump";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        int level = Rectangle1.getSkipList().getHead().getLevel();
        assertEquals(Rectangle1.getSkipList().getLevel(), level);

        // test SkipNode
        assertEquals(Rectangle1.getSkipList().getHead().getForward()[0]
            .toString(), "rect, 1, 20, 1, 20");

        // test KVPair
        KVPair<String, String> aKVPair = Rectangle1.getSkipList().getHead()
            .getForward()[0].getRec();
        assertEquals(aKVPair.compareTo(aKVPair), 0);
        assertEquals(aKVPair.compareTo("rect"), 0);

        // test main
        cmd = "SkipListSampleInput.txt";
        String[] args = { cmd };
        Rectangle1.main(args);

// cmd = "test";
// args[0] = cmd;
// Rectangle1.main(args);

        // some more tests according to feedback from mutation test
        cmd = "insert test1 1000   20  1000 40 ";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        System.out.println(Rectangle1.processCommand(commandArgs, cmd));
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Rejected");

        cmd = "insert test2 100   1000  10 2000 ";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Rejected");

        cmd = "insert test3 10 10 -10 10 ";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Rejected");

        cmd = "insert test3 10 700 10 700";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Rejected");

        cmd = "dump";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "remove goodRect";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "RemovedByName");

        cmd = "insert test 100 100 10 10";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Inserted");

        cmd = "dump";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "intersections";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "NoIntersectsFound");

        cmd = "dump";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "regionsearch 1000 1000 50 50";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd),
            "NoRectsFound");

        int[] coordX = { 10, 10, 20, 20 };
        int[] coordY = { 10, 10, 20, 20 };
        assertEquals(Rectangle1.rectsDoNotOverlap(coordX, coordY), false);

        int[] coordX2 = { 30, 10, 40, 20 };
        int[] coordY2 = { 10, 10, 20, 20 };
        assertEquals(Rectangle1.rectsDoNotOverlap(coordX2, coordY2), true);

        int[] coordX3 = { 10, 10, 20, 20 };
        int[] coordY3 = { 30, 10, 20, 20 };
        assertEquals(Rectangle1.rectsDoNotOverlap(coordX3, coordY3), true);

        int[] coordX4 = { 10, 30, 20, 40 };
        int[] coordY4 = { 10, 10, 20, 20 };
        assertEquals(Rectangle1.rectsDoNotOverlap(coordX4, coordY4), true);

        int[] coordX5 = { 10, 10, 20, 20 };
        int[] coordY5 = { 10, 30, 20, 40 };
        assertEquals(Rectangle1.rectsDoNotOverlap(coordX5, coordY5), true);

        int[] coordX6 = { 30, 10, 40, 20 };
        int[] coordY6 = { 10, 10, 20, 20 };
        assertEquals(Rectangle1.rectsDoNotOverlap(coordX6, coordY6), true);

        int[] coordX7 = { 10, 10, 20, 20 };
        int[] coordY7 = { 30, 10, 40, 20 };
        assertEquals(Rectangle1.rectsDoNotOverlap(coordX7, coordY7), true);

        String filename = "SkipListSampleInput.txt";
        FileReader readerTest = new FileReader(filename);
        readerTest.readNextLine();
        assertEquals(readerTest.checkIfBlankCommand(), true);

        cmd = "dump";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "remove 1 1 0 1";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Rejected");

        cmd = "remove -1 1 1 1";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Rejected");

        cmd = "remove 1 -1 1 1";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Rejected");

        cmd = "remove 1 1 1 0";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Rejected");

        cmd = "remove 1 1 0 1";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Rejected");

        cmd = "remove 1 700 1 700";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Rejected");

        cmd = "remove 700 1 700 1";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Rejected");

        cmd = "dump";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);

        cmd = "insert rect 100 100 10 10";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(commandArgs, cmd), "Inserted");

        // test if items are inserted to skipList in the correct order
        assertEquals(Rectangle1.getSkipList().getHead().getForward()[0]
            .getForward()[0].key(), "rect");

        // test if the correct element is removed from the skipLIst
        cmd = "remove rect";
        cmd = FileReader.removeSpace(cmd);
        commandArgs = cmd.split(" ");
        Rectangle1.processCommand(commandArgs, cmd);
        assertEquals(Rectangle1.getSkipList().getHead().getForward()[0]
            .getForward()[0].key(), "test");

    }
}
