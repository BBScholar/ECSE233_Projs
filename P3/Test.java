

import java.util.LinkedList;

class Test {
    
    static void printHeader() {
        System.out.println("---------------------------------");
        System.out.println("|          Homework #3          |");
        System.out.println("|-------------------------------|");
        System.out.println("| Name:      Benjamin Scholar   |");
        System.out.println("| CaseId:               bbs27   |");
        System.out.println("| Class:             CSDS 233   |");
        System.out.println("| Date:               3/11/21   |");
        System.out.println("---------------------------------\n\n");
    }

    public static void main(String... args) {
        printHeader();
        DoubleTreePhBook pb = new DoubleTreePhBook();

        // insert valid KV pairs
        pb.PhBInsert("Daniel", 4);
        pb.PhBInsert("Lando", 5);
        pb.PhBInsert("James", 1);
        pb.PhBInsert("Lewis", 2);
        pb.PhBInsert("Lewis", 3);
        pb.PhBInsert("Toto", 6);
        pb.PhBInsert("Lewis", 1000);
        
        pb.printNumberTree();
        pb.printNameTree();

        // attempt insert invalid KV Pair (Duplicate number)
        assert !pb.PhBInsert("Jane", 5);
        assert !pb.PhBInsert("Daniel", 4);


        // test numberFind method 
        assert pb.PhBPhoneSearch(5).equals("Lando");

        // test nameFind method
        final LinkedList<Integer> results = pb.PhBNameSearch("Lewis");
        assert results.size() == 3;

        System.out.println("Numbers for lewis: ");
        for(var number : results) {
            System.out.println(number);
        }

    }
}
