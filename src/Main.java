public class Main {
    public static  int Suites_Run = 0 ;
    public static int Suites_Passed = 0 ;

    public static int Tests_Run = 0 ;
    public static int Tests_Passed = 0 ;

    static StringBuilder suites = new StringBuilder();

    public static void end_tests() {
        if (Suites_Passed == Suites_Run) {
            System.out.println("All Suites were passed");
        } else {
            System.out.println("Some Suites Failed : " + Suites_Passed + "/" + Suites_Run + '\n' + suites.toString());
        }
    }

    public static void start_suite(String name)
    {
        Suites_Run ++ ;
        System.out.print(name +"\n==========================================\n") ;
        suites.append(name).append(": ");
    }

    public static void end_suite()
    {
        if (Tests_Run == Tests_Passed)
        {
            Suites_Passed ++ ;
            System.out.print("All tests have passed\n");
            suites.append("Passed\n");
        }
        else
        {
            System.out.print("Some Tests Failed : " + Tests_Passed + "/" + Tests_Run + "\n\n");
            suites.append("Failed\n");
        }
        System.out.println("==========================================\n");
        Tests_Run = 0;
        Tests_Passed = 0 ;
    }

    public static boolean isEqual(String got, String expected) {
        if (got.length() != expected.length()) {
            return false;
        }
        for (int i = 0; i < got.length(); i++) {
            if (got.charAt(i) != expected.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    public static void assertEquals(String got , String expected)
    {
        Tests_Run ++ ;
        if (isEqual(got, expected))
        {
            Tests_Passed ++ ;
            System.out.print("Test "+ Tests_Run + " Passed\n");
        }
        else
        {
            System.out.print("Test " + Tests_Run + " Failed\n" + "You got : \n" + got + "\nYou expected : \n" + expected + "\n");
        }
    }

    static String tree1OneLine = "{[u234:20%]{}{[u237:10%]{[u235:null%]{}{[u236:null%]{}{}}}{}}}";
    static String tree1OneLineAfterAccess236 = "{[u236:5%]{[u234:20%]{}{[u235:null%]{}{}}}{[u237:10%]{}{}}}";
    static String tree1OneLineAfterAccess105_50 = "{[u105:50%]{}{[u234:20%]{}{[u236:5%]{[u235:null%]{}{}}{[u237:10%]{}{}}}}}";

    static String tree2OneLine = "{[u123:34%]{[u113:50%]{[u105:90%]{[u103:80%]{}{}}{}}{}}{[u158:45%]{}{[u301" +
            ":35%]{[u194:null%]{[u160:59%]{}{}}{[u256:20%]{[u235:null%]{[u205:null%]{}{}}{}}{}}}{[u567:99%]{}{}}}}}";
    static String tree2ToString =   "│           ┌── [u567:99%]\n" +
                                    "│       ┌── [u301:35%]\n" +
                                    "│       │   │   ┌── [u256:20%]\n" +
                                    "│       │   │   │   └── [u235:null%]\n" +
                                    "│       │   │   │       └── [u205:null%]\n" +
                                    "│       │   └── [u194:null%]\n" +
                                    "│       │       └── [u160:59%]\n" +
                                    "│   ┌── [u158:45%]\n" +
                                    "└── [u123:34%]\n" +
                                    "    └── [u113:50%]\n" +
                                    "        └── [u105:90%]\n" +
                                    "            └── [u103:80%]\n";

    public static void main(String[] args) {
        start_suite("Constructors");

        SplayTree tree1 = new SplayTree(tree1OneLine);
        assertEquals(tree1.toStringOneLine(), tree1OneLine);

        SplayTree emptyTree = new SplayTree();
        assertEquals(emptyTree.toString(), "Empty Tree");

        SplayTree emptyTree2 = new SplayTree("Empty Tree");
        assertEquals(emptyTree2.toString(), "Empty Tree");

        SplayTree tree2 = new SplayTree(tree2OneLine);
        assertEquals(tree2.toStringOneLine(), tree2OneLine);
        assertEquals(tree2.toString(), tree2ToString);

        end_suite();


        //==========================================================


        start_suite("Access");

        assertEquals(tree1.access(236, 5).toString(), "[u236:5%]");
        assertEquals(tree1.toStringOneLine(), tree1OneLineAfterAccess236);
        assertEquals(tree1.access(105, 50).toString(), "[u105:50%]");
        assertEquals(tree1.toStringOneLine(), tree1OneLineAfterAccess105_50);

        assertEquals(tree1.remove(105).toString(), "[u105:50%]");

        SplayTree tree1Copy = new SplayTree(tree1.toStringOneLine());

        assertEquals(tree1Copy.access(235, 10).toString(), "[u235:10%]");
        assertEquals(tree1Copy.toStringOneLine(), "{[u235:10%]{[u234:20%]{}{}}{[u236:5%]{}{[u237:10%]{}{}}}}");

        assertEquals(tree1Copy.access(236, 99).toString(), "[u236:99%]");
        assertEquals(tree1Copy.toStringOneLine(), "{[u236:99%]{[u235:10%]{[u234:20%]{}{}}{}}{[u237:10%]{}{}}}");

        assertEquals(tree1Copy.access(235, 15).toString(), "[u235:15%]");
        assertEquals(tree1Copy.toStringOneLine(), "{[u235:15%]{[u234:20%]{}{}}{[u236:99%]{}{[u237:10%]{}{}}}}");

        assertEquals(emptyTree.access(205, 100).toString(), "[u205:100%]");

        assertEquals(tree1Copy.access(1000).toString(), "[u1000:null%]");

        assertEquals(tree1Copy.toStringOneLine(), "{[u1000:null%]{[u235:15%]{[u234:20%]{}{}}{[u237:10%]" +
                "{[u236:99%]{}{}}{}}}{}}");


        end_suite();


        //==========================================================


        start_suite("Removal");
        // 1, 2
        assertEquals(tree1Copy.remove(237).toString(), "[u237:10%]");
        assertEquals(tree1Copy.toStringOneLine(), "{[u236:99%]{[u235:15%]{[u234:20%]{}{}}{}}{[u1000:null%]{}{}}}");


        SplayTree tree2Copy = new SplayTree(tree2.toStringOneLine());

        // 3, 4
        assertEquals(tree2Copy.remove(123).toString(), "[u123:34%]");
        assertEquals(tree2Copy.toStringOneLine(), "{[u113:50%]{[u105:90%]{[u103:80%]" +
                "{}{}}{}}{[u158:45%]{}{[u301:35%]{[u194:null%]{[u160:59%]{}{}}{[u256:20%]" +
                "{[u235:null%]{[u205:null%]{}{}}{}}{}}}{[u567:99%]{}{}}}}}");

        // 5, 6
        assertEquals(tree2Copy.remove(160).toString(), "[u160:59%]");
        assertEquals(tree2Copy.toStringOneLine(), "{[u158:45%]{[u113:50%]{[u105:90%]" +
                "{[u103:80%]{}{}}{}}{}}{[u194:null%]{}{[u301:35%]{[u256:20%]{[u235:null%]" +
                "{[u205:null%]{}{}}{}}{}}{[u567:99%]{}{}}}}}");

        // 7, 8
        assertEquals(tree2Copy.remove(194).toString(), "[u194:null%]");
        assertEquals(tree2Copy.toStringOneLine(), "{[u158:45%]{[u113:50%]{[u105:90%]" +
                "{[u103:80%]{}{}}{}}{}}{[u301:35%]{[u256:20%]{[u235:null%]{[u205:null%]{}{}}" +
                "{}}{}}{[u567:99%]{}{}}}}");

        // 9, 10
        assertEquals(tree2Copy.remove(301).toString(), "[u301:35%]");
        assertEquals(tree2Copy.toStringOneLine(), "{[u256:20%]{[u158:45%]{[u113:50%]" +
                "{[u105:90%]{[u103:80%]{}{}}{}}{}}{[u235:null%]{[u205:null%]{}{}}{}}}{[u567:99%]{}{}}}");

        // 11, 12
        assertEquals(tree2Copy.remove(1).toString(), "[u1:null%]");
        assertEquals(tree2Copy.toStringOneLine(), "{[u256:20%]{[u113:50%]{[u103:80%]{}" +
                "{[u105:90%]{}{}}}{[u158:45%]{}{[u235:null%]{[u205:null%]{}{}}{}}}}{[u567:99%]{}{}}}");

        // 13, 14
        assertEquals(tree2Copy.remove(194).toString(), "[u194:null%]");
        assertEquals(tree2Copy.toStringOneLine(), "{[u158:45%]{[u113:50%]{[u103:80%]{}" +
                "{[u105:90%]{}{}}}{}}{[u256:20%]{[u205:null%]{}{[u235:null%]{}{}}}{[u567:99%]{}{}}}}");

//        System.out.println(tree2Copy.toStringOneLine());
        // 15, 16
        assertEquals(tree2Copy.remove(600).toString(), "[u600:null%]");
        assertEquals(tree2Copy.toStringOneLine(), "{[u158:45%]{[u113:50%]{[u103:80%]{}" +
                "{[u105:90%]{}{}}}{}}{[u567:99%]{[u256:20%]{[u205:null%]{}{[u235:null%]{}{}}}{}}{}}}");

        // 17, 18
        assertEquals(tree2Copy.remove(567).toString(), "[u567:99%]");
        assertEquals(tree2Copy.toStringOneLine(), "{[u158:45%]{[u113:50%]{[u103:80%]{}" +
                "{[u105:90%]{}{}}}{}}{[u256:20%]{[u205:null%]{}{[u235:null%]{}{}}}{}}}");

        // 19, 20
        assertEquals(tree2Copy.remove(100).toString(), "[u100:null%]");
        assertEquals(tree2Copy.toStringOneLine(), "{[u158:45%]{[u103:80%]{}{[u113:50%]" +
                "{[u105:90%]{}{}}{}}}{[u256:20%]{[u205:null%]{}{[u235:null%]{}{}}}{}}}");

        end_suite();


        //==========================================================


        start_suite("Sort by Student number");

        assertEquals(tree1.sortByStudentNumber(), "[u234:20%][u235:null%][u236:5%][u237:10%]");

        tree2Copy = new SplayTree(tree2.toStringOneLine());

        assertEquals(tree2Copy.sortByStudentNumber(), "[u103:80%][u105:90%][u113:50%]" +
                "[u123:34%][u158:45%][u160:59%][u194:null%][u205:null%][u235:null%][u256:20%]" +
                "[u301:35%][u567:99%]");

        assertEquals(tree2Copy.access(600, 35).toString(), "[u600:35%]");
        assertEquals(tree2Copy.access(104, 90).toString(), "[u104:90%]");
        assertEquals(tree2Copy.access(110, 50).toString(), "[u110:50%]");
        assertEquals(tree2Copy.access(220, 50).toString(), "[u220:50%]");

        assertEquals(tree2Copy.sortByStudentNumber(), "[u103:80%][u104:90%][u105:90%][u110:50%]" +
                "[u113:50%][u123:34%][u158:45%][u160:59%][u194:null%][u205:null%][u220:50%][u235:null%]" +
                "[u256:20%][u301:35%][u567:99%][u600:35%]");

        end_suite();


        //==========================================================


        start_suite("Sort by student mark");

        assertEquals(tree1.sortByMark(), "[u235:null%][u236:5%][u237:10%][u234:20%]");

        tree2Copy = new SplayTree(tree2.toStringOneLine());

        assertEquals(tree2Copy.sortByMark(), "[u194:null%][u205:null%][u235:null%]" +
                "[u256:20%][u123:34%][u301:35%][u158:45%][u113:50%][u160:59%][u103:80%][u105:90%][u567:99%]");

        assertEquals(tree2Copy.access(600, 35).toString(), "[u600:35%]");
        assertEquals(tree2Copy.access(104, 90).toString(), "[u104:90%]");
        assertEquals(tree2Copy.access(110, 50).toString(), "[u110:50%]");
        assertEquals(tree2Copy.access(220, 50).toString(), "[u220:50%]");

        assertEquals(tree2Copy.sortByMark(), "[u194:null%][u205:null%][u235:null%][u256:20%]" +
                "[u123:34%][u301:35%][u600:35%][u158:45%][u110:50%][u113:50%][u220:50%][u160:59%][u103:80%]" +
                "[u104:90%][u105:90%][u567:99%]");

        end_suite();



        end_tests();
    }
}