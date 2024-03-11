public class Main {
    public static  int Suites_Run = 0 ;
    public static int Suites_Passed = 0 ;

    public static int Tests_Run = 0 ;
    public static int Tests_Passed = 0 ;

    public static void end_tests() {
        if (Suites_Passed == Suites_Run) {
            System.out.println("All Suites were passed");
        } else {
            System.out.println("Some Suites Failed : " + Suites_Passed + "/" + Suites_Run);
        }
    }

    public static void start_suite(String name)
    {
        Suites_Run ++ ;
        System.out.print(name +"\n==========================================\n") ;
    }

    public static void end_suite()
    {
        if (Tests_Run == Tests_Passed)
        {
            Suites_Passed ++ ;
            System.out.print("All tests have passed\n");
        }
        else
        {
            System.out.print("Some Tests Failed : " + Tests_Passed + "/" + Tests_Run + "\n\n");
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
    public static void assertEquals(boolean got , boolean expected)
    {
        Tests_Run ++ ;
        if (got == expected)
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

    public static void main(String[] args) {
        start_suite("Constructors");

        SplayTree tree1 = new SplayTree(tree1OneLine);
        assertEquals(tree1.toStringOneLine(), tree1OneLine);

        end_suite();

        //==========================================================

        start_suite("Access");

        assertEquals(tree1.access(236, 5).toString(), "[u236:5%]");

        assertEquals(tree1.toStringOneLine(), tree1OneLineAfterAccess236);

        end_suite();
        start_suite("Removal");

        assertEquals(tree1.remove(237).toString(), "[u237:10%]");
        assertEquals(tree1.toStringOneLine(), "{[u236:5%]{[u234:20%]{}{[u235:null%]{}{}}}{}}");

        end_suite();

        //==========================================================

        start_suite("Sort by Student number");

        assertEquals(tree1.sortByStudentNumber(), "[u234:20%][u235:null%][u236:5%]");


        end_suite();

        //==========================================================

        start_suite("Sort by student mark");

        assertEquals(tree1.sortByMark(), "[u235:null%][u236:5%][u234:20%]");

        end_suite();



        end_tests();
    }
}