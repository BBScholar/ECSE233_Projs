import java.util.Random;

public class AVLTreeTest {

    public static void main(final String... args) {

        AVLTree<Integer, Integer> tree = new AVLTree<>();

        // ------- LEFT ROTATION ABOUT ROOT ----------------------------------------
        tree.clear();
        tree.insert(3, 1);
        tree.insert(2, 1);
        tree.insert(1, 1);

        for(int i = 1; i < 3; ++i)
            tree.delete(i);

        // --------- RIGHT ROTATION ABOUT ROOT ------------------
        tree.clear();
        tree.insert(1, 1);
        tree.insert(2, 1);
        tree.insert(3, 1);

        for(int i = 3; i >= 1; i--) {
            tree.delete(i);
        }

        // ------ RIGHT-LEFT ROTATION ABOUT ROOT -----------------------------------
        tree.clear();
        tree.insert(2, 1);
        tree.insert(5, 1);
        tree.insert(4, 1);

        // ------- lEFT-RIGHT ROTATION ABOUT ROOT
        tree.clear();
        tree.insert(5, 1);
        tree.insert(3, 1);
        tree.insert(4, 1);


        // random testing, this will likely let us know of any errors
        Random random = new Random(42);
        int[] array = random.ints(1500000).toArray();

        tree.clear();
        tree.insert(0, 1);
        for(int i : array) {
            tree.insert(i, 1);
        }

        for(int i = 0; i < 1000; ++i) {
            tree.delete(random.nextInt());
        }

        // remove root
        tree.delete(0);

    }
}
