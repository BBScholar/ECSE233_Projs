import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class A2P1 {

    // helper method for printing the matrix in a nice format.
    // Time complexity: O(n^2) where n is the matrix dimension
    public static void printMatrix(final int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
    }

    // helper method for reading the matrix from a file
    // Time complexity: O(n^2) where n is the matrix dimension
    public static int[][] readMatrixFromFile(final String path, final int dim) {
        // allocate the matrix
        final int[][] matrix = new int[dim][dim];
        try {
            final File file = new File(path);
            final Scanner reader = new Scanner(file);

            // read ints from file. THe numbers are seperated by spaces and newlines, so
            // just read the next
            // int until the matrix is full
            int i = -1;
            while (++i < dim * dim) {
                final int r = i / dim;
                final int c = i % dim;
                matrix[r][c] = reader.nextInt(); // throw an error if theres not enough elments
            }

            reader.close();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (final java.util.NoSuchElementException e) {
            /* e.printStackTrace(); */
            System.out.println("Not enough elements in file");
            System.exit(-2);
        }

        return matrix;
    }

    // finds a value within a matrix, returns true if it exists.
    // Time complexity: O(n)
    public static boolean inMatrix(final int[][] matrix, final int x) {
        // <<<<- elements get smaller
        // 2 3 5 8 <- start here
        // 4 6 7 12 |
        // 7 11 14 17 | elements get larger
        // 9 13 19 21 \/

        // start at the top right corner
        int r = 0;
        int c = matrix[0].length - 1;

        // while we are still in the matrix,
        // go down a row if the current element is less than the target.
        // go left a col if the current element is greater than the target
        // return true if current element is equal to the target
        // at most we will have 2n - 1 iterations, thus the method has
        // a time complexity of O(n)
        while (r < matrix.length && c >= 0) {
            final int currentElem = matrix[r][c];
            if (currentElem == x) {
                return true;
            } else if (x > currentElem) {
                // go down a row
                r++;
            } else {
                // go 1 column to the left
                c--;
            }
        }
        return false;
    }

    public static void main(final String... args) {

        // get arguments
        final String path = args[0];
        final int dim = Integer.parseInt(args[1]);
        final int x = Integer.parseInt(args[2]);

        // read the matrix from the file
        final int[][] matrix = readMatrixFromFile(path, dim);

        // not sure if this is the case in java, but allocating before we start
        // the timer may give us a *slightly* more accurate time, since we'd
        // avoiding timing the stack allocation.
        boolean res;

        // start timer
        final long start = System.nanoTime();

        // invoke method
        res = inMatrix(matrix, x);

        // record runtime
        final long runtime = System.nanoTime() - start;

        // print out results
        System.out.println("Input Matrix: ");
        printMatrix(matrix);
        System.out.println("Result: " + res);
        System.out.println("Runtime: " + runtime + "ns");

    }

}
