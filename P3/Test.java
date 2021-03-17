

import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;

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
        DoubleTreePhBook book =  new DoubleTreePhBook();
        boolean res;

        // insert some unique nodes
        res = book.PhBInsert("James", 100);
        assert res;
        res = book.PhBInsert("Lando", 52);
        assert res;
        res = book.PhBInsert("Toto", 150);
        assert res;

        // delete root
        res = book.PhBDelete("James", 100);
        assert res;

        // insert more nodes
        res = book.PhBInsert("Lewis", 200);
        assert res;

        // test deletion of left and right leaf nodes
        res = book.PhBDelete("Lando", 52);
        assert res;
        res = book.PhBDelete("Lewis", 200);
        assert res;

        // reinsert nodes
        res = book.PhBInsert("Lando", 52);
        assert res;
        res = book.PhBInsert("Lewis", 200);
        assert res;

        // attempt  to insert duplte number
        res = book.PhBInsert("Lando", 52);
        assert !res;

        // insert nodes on either side of "lando"
        res = book.PhBInsert("Sergio", 20);
        assert res;
        res = book.PhBInsert("Max", 65);
        assert res;

        res = book.PhBDelete("Lando", 52);
        assert res;


        int[] nums = {1, 2, 3, 400, 500};
        for(int n : nums) {
            res = book.PhBInsert("Lewis", n);
            assert res;
        }

        final var lewisNums = book.PhBNameSearch("Lewis");

        for(int n : nums) {
            res = lewisNums.contains(n);
            assert res;
        }

        final var max = book.PhBPhoneSearch(65);
        assert max.isPresent() && max.get().equals("Max");

        res = book.PhBInsert("Yuki", 350);
        assert res;

        res = book.PhBInsert("Jamie", 450);
        assert  res;

        book.printTrees();

    }

}
