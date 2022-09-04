import student.TestCase;

public class Rectangle1Test extends TestCase {
    private Rectangle1 aRectangle1;

    public void setUp() {
        aRectangle1 = new Rectangle1();
    }


    public void testProcessCommand() {
        setUp();
        // set up skip_list
        Rectangle1.setSkipList();

        // test insert command
        String cmd1 = "insert test1 1   20  50 40 ";
        cmd1 = FileReader.removeSpace(cmd1);
        String[] command_args1 = cmd1.split(" ");
        assertEquals(Rectangle1.processCommand(command_args1, cmd1), "Inserted");
        
        
        String cmd2 = "insert test2 -1   20  50 40 ";
        cmd2 =FileReader.removeSpace(cmd2);
        String[] command_args2 = cmd2.split(" ");
        assertEquals(Rectangle1.processCommand(command_args2, cmd2), "Rejected");

        String cmd3 = "insert test3 1   -1  50 40 ";
        cmd3 = FileReader.removeSpace(cmd3);
        String[] command_args3 = cmd3.split(" ");
        assertEquals(Rectangle1.processCommand(command_args3, cmd3), "Rejected");

        String cmd4 = "insert test4 1   20  2000 40 ";
        cmd4 = FileReader.removeSpace(cmd4);
        String[] command_args4 = cmd4.split(" ");
        assertEquals(Rectangle1.processCommand(command_args4, cmd4), "Rejected");

        String cmd5 = "insert test5 1   20  50 2000";
        cmd5 = FileReader.removeSpace(cmd5);
        String[] command_args5 = cmd5.split(" ");
        assertEquals(Rectangle1.processCommand(command_args5, cmd5), "Rejected");

        String cmd6 = "insert test6 1   20  -1 2000";
        cmd6 = FileReader.removeSpace(cmd6);
        String[] command_args6 = cmd6.split(" ");
        assertEquals(Rectangle1.processCommand(command_args6, cmd6), "Rejected");

        String cmd7 = "insert test7 1   20  1 -1";
        cmd7 = FileReader.removeSpace(cmd7);
        String[] command_args7 = cmd7.split(" ");
        assertEquals(Rectangle1.processCommand(command_args7, cmd7), "Rejected");

        String cmd8 = "insert test8 a   20  1 -1";
        cmd8 = FileReader.removeSpace(cmd8);
        String[] command_args8 = cmd8.split(" ");
        assertEquals(Rectangle1.processCommand(command_args8, cmd8), "SomeArgumentsAreNotInt");

        String cmd9 = "insert test9 1   20  1 10 20 20";
        cmd9 = FileReader.removeSpace(cmd9);
        String[] command_args9 = cmd9.split(" ");
        assertEquals(Rectangle1.processCommand(command_args9, cmd9), "InvalidNumberOfArgs");

        // test remove command:
        String cmd = "remove 1   20  50 40 ";
        cmd = FileReader.removeSpace(cmd);
        String[] command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "RemovedByCoords");

        cmd = "remove 1   20  50 40 ";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "ListEmpty");

        cmd = "insert rect 1   20  1 20";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        Rectangle1.processCommand(command_args, cmd);
        
        cmd = "intersections";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "NoIntersectsFound");
        
        
        cmd = "remove rect";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd),"RemovedByName");

        cmd = "remove 1";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "InvalidArgs");

        cmd = "remove 1 2 3 4 5";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "InvalidArgs");

        cmd = "remove a 2 3 4";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "InvalidArgs");

        cmd = "remove 1 a 3 4";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "InvalidArgs");

        cmd = "remove 1 2 a 4";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "InvalidArgs");

        cmd = "remove 1 2 3 a";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "InvalidArgs");

        cmd = "remove test";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "NotInList");

        // test search command
        cmd = "dump";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        Rectangle1.processCommand(command_args, cmd);

        cmd = "search rect";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "NotFound");

        cmd = "insert rect 1   20  1 20";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        Rectangle1.processCommand(command_args, cmd);
       
        
        cmd = "search rect";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "Found");

        cmd = "insert rect2 1   20  1 20";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        Rectangle1.processCommand(command_args, cmd);
        
        
        cmd = "search rect2";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "Found");

        cmd = "insert rect2 1   20  1 20";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        Rectangle1.processCommand(command_args, cmd);

        cmd = "search rect2";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "MultipleFound");

        cmd = "search rect3";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "NotFound");

        cmd = "search rect";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "Found");

        // test dump command
        cmd = "dump";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd),"Dumped");

        // test regionsearch command

        cmd = "insert rect4 500   500 100 100 ";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        Rectangle1.processCommand(command_args, cmd);

        cmd = "regionsearch -5 -5 -3 50";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "Rejected");

        cmd = "regionsearch -5 -5 20 -5";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "Rejected");

        cmd = "regionsearch -5 -5 50 50";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "RectsFound");

        // test intersections command

        cmd = "intersections";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "IntersectsFound");

        cmd = "insert rect1 0  0 100 100 ";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        Rectangle1.processCommand(command_args, cmd);

        cmd = "insert rect2 100 0 100 100 ";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        Rectangle1.processCommand(command_args, cmd);

        cmd = "insert rect5 200 200 200 200 ";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        Rectangle1.processCommand(command_args, cmd);

        cmd = "insert rect6 200 500 200 200";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        Rectangle1.processCommand(command_args, cmd);

        cmd = "intersections";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "IntersectsFound");
        
        

        
        
        // test default
        cmd = "test";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd),  "CommandNotRecognized");
        
        // further remove tests
        cmd = "insert rect6 1023 1 1 1 ";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        Rectangle1.processCommand(command_args, cmd);

        cmd = "remove rect6";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "RemovedByName");

        cmd = "remove 20 20 20 20";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        assertEquals(Rectangle1.processCommand(command_args, cmd), "EOL");

        // test getLevel inside SkipList:
        cmd = "dump";
        cmd = FileReader.removeSpace(cmd);
        command_args = cmd.split(" ");
        Rectangle1.processCommand(command_args, cmd);

        int level = Rectangle1.getSkipList().getHead().getLevel();
        assertEquals(Rectangle1.getSkipList().getLevel(), level);

        // test SkipNode
        assertEquals(Rectangle1.getSkipList().getHead().getForward()[0].toString(), 
            "rect, 1, 20, 1, 20");
        
        //test KVPair
        KVPair<String, String> aKVPair = Rectangle1.getSkipList().getHead().getForward()[0].getRec();
            assertEquals(aKVPair.compareTo(aKVPair), 0);
            assertEquals(aKVPair.compareTo("rect"),0);
        
        // test main
            cmd = "SkipListSampleInput.txt";
            String [] args = {cmd};
            Rectangle1.main(args);
            
//            cmd = "test";
//            args[0] = cmd;
//            Rectangle1.main(args);
            
       // some more tests according to feedback from mutation test
            cmd = "insert test1 1000   20  1000 40 ";
            cmd = FileReader.removeSpace(cmd);
            command_args = cmd.split(" ");
            System.out.println(Rectangle1.processCommand(command_args, cmd));
            assertEquals(Rectangle1.processCommand(command_args, cmd), "Rejected");

            cmd = "insert test2 100   1000  10 2000 ";
            cmd = FileReader.removeSpace(cmd);
            command_args = cmd.split(" ");
            assertEquals(Rectangle1.processCommand(command_args, cmd), "Rejected");
            
            cmd = "insert test3 10 10 -10 10 ";
            cmd = FileReader.removeSpace(cmd);
            command_args = cmd.split(" ");
            assertEquals(Rectangle1.processCommand(command_args, cmd), "Rejected");
            
            cmd = "insert test3 10 700 10 700";
            cmd = FileReader.removeSpace(cmd);
            command_args = cmd.split(" ");
            assertEquals(Rectangle1.processCommand(command_args, cmd), "Rejected");
            
            
            cmd = "dump";
            cmd = FileReader.removeSpace(cmd);
            command_args = cmd.split(" ");
            Rectangle1.processCommand(command_args, cmd);
            
            cmd = "remove goodRect";
            cmd = FileReader.removeSpace(cmd);
            command_args = cmd.split(" ");
            assertEquals(Rectangle1.processCommand(command_args, cmd), "RemovedByName");
            
            cmd = "insert test 100 100 10 10";
            cmd = FileReader.removeSpace(cmd);
            command_args = cmd.split(" ");
            assertEquals(Rectangle1.processCommand(command_args, cmd), "Inserted");
            
          cmd = "dump";
          cmd = FileReader.removeSpace(cmd);
          command_args = cmd.split(" ");
          Rectangle1.processCommand(command_args, cmd);
            
            cmd = "intersections";
            cmd = FileReader.removeSpace(cmd);
            command_args = cmd.split(" ");
            assertEquals(Rectangle1.processCommand(command_args, cmd), "NoIntersectsFound");
            
            cmd = "dump";
            cmd = FileReader.removeSpace(cmd);
            command_args = cmd.split(" ");
            Rectangle1.processCommand(command_args, cmd);
            
            
            cmd = "regionsearch 1000 1000 50 50";
            cmd = FileReader.removeSpace(cmd);
            command_args = cmd.split(" ");
            assertEquals(Rectangle1.processCommand(command_args, cmd), "NoRectsFound");
            
            
            int [] coordX= {10, 10, 20, 20};
            int [] coordY= {10, 10, 20, 20};
            assertEquals(Rectangle1.rectsDoNotOverlap(coordX, coordY), false);

            int [] coordX2= {30, 10, 40, 20};
            int [] coordY2= {10, 10, 20, 20};
            assert(Rectangle1.rectsDoNotOverlap(coordX2, coordY2));

            int [] coordX3= {10, 10, 20, 20};
            int [] coordY3= {30, 10, 20, 20};
            assert(Rectangle1.rectsDoNotOverlap(coordX3, coordY3));

            
            int [] coordX4= {10, 30, 20, 40};
            int [] coordY4= {10, 10, 20, 20};
            assert(Rectangle1.rectsDoNotOverlap(coordX4, coordY4));
            
            int [] coordX5= {10, 10, 20, 20};
            int [] coordY5= {10, 30, 20, 40};
            assert(Rectangle1.rectsDoNotOverlap(coordX5, coordY5));
            
            int [] coordX6= {30, 10, 40, 20};
            int [] coordY6= {10, 10, 20, 20};
            assert(Rectangle1.rectsDoNotOverlap(coordX6, coordY6));
            
            
            int [] coordX7= {10, 10, 20, 20};
            int [] coordY7= {30, 10, 40, 20};
            assert(Rectangle1.rectsDoNotOverlap(coordX7, coordY7));
            
            
            
            
            
            
            
            
            
//            cmd = "insert test3 -1   20  10 20 ";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), false);
//            
//            cmd = "insert test4 1   -1  10 20 ";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), false);
//            
//            cmd = "insert test5 1   1  -1 20 ";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), false);
//            
//            cmd = "insert test6 1   1  1 -1 ";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), false);
//            
//            cmd = "insert test1 3000   20  2 40 ";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            System.out.println(Rectangle1.processCommand(command_args, cmd));
//            assertEquals(Rectangle1.processCommand(command_args, cmd), false);
//
//            cmd = "insert test1 1   3000  2 40 ";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            System.out.println(Rectangle1.processCommand(command_args, cmd));
//            assertEquals(Rectangle1.processCommand(command_args, cmd), false);
//            
//            cmd = "dump";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            Rectangle1.processCommand(command_args, cmd);
//
//            cmd = "remove goodRect";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), true);
//
//            cmd = "remove 1";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), false);
//
//            cmd = "remove a b";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), false);
//
//            cmd = "regionsearch 1 1 1 1";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), true);
//
//            cmd = "regionsearch 20 20 10 -10";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), false);
//
//            cmd = "regionsearch 200 200 10 10";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), true);
//            
//            cmd = "regionsearch 0 0 -10 -10";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), false);
//
//            cmd = "intersections";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), true);
//
//            
//            cmd = "dump";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            Rectangle1.processCommand(command_args, cmd);
//
//            
//            Rectangle1.skip_list = new SkipList<String, String>();
//            cmd = "intersections";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), false);
//            
//            cmd = "insert test1 1   1  10 10";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            System.out.println(Rectangle1.processCommand(command_args, cmd));
//            assertEquals(Rectangle1.processCommand(command_args, cmd), true);
//            
//            cmd = "regionsearch 0 0 -10 10";
//            cmd = FileReader.removeSpace(cmd);
//            command_args = cmd.split(" ");
//            assertEquals(Rectangle1.processCommand(command_args, cmd), false);

    }
}
