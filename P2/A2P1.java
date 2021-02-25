import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class A2P1 {

    public static void printMatrix(final int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public static int[][] readMatrixFromFile(final String path, final int dim) {
        final int[][] matrix = new int[dim][dim];
        try {
            final File file = new File(path);
            final Scanner reader = new Scanner(file);

            int i = 0;
            while(++i <= dim*dim) {
                final int r = (i - 1) / dim;
                final int c = (i - 1) % dim;
                matrix[r][c] = reader.nextInt(); // throw an error if theres not enough elments
            }

            reader.close();
        } catch(final FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch(final java.util.NoSuchElementException e) {
            /* e.printStackTrace(); */
            System.out.println("Not enough elements in file");
            System.exit(-2);
        }


        return matrix;
    }

    public static boolean inMatrix(final int[][] matrix, final int x) {
        // 2  3  5  8 <- start here
        // 4  6  7 12 
        // 7 11 14 17
        // 9 13 19 21
        
        int r = 0;
        int c = matrix[0].length - 1;
        while(r < matrix.length && c >= 0){
            final int currentElem = matrix[r][c];
            if(currentElem == x) {
                return true;
            } else if(x > currentElem) {
                r++;
            } else {
                c--;
            }
        }
        return false;
    }

    public static void main(final String... args) {
    
        final String path = args[0];
        final int dim = Integer.parseInt(args[1]);
        final int x = Integer.parseInt(args[2]);

        final int[][] matrix = readMatrixFromFile(path, dim);

        boolean res;

        final long start = System.nanoTime();

        res = inMatrix(matrix, x);

        final long runtime = System.nanoTime() - start;

        System.out.println("Input Matrix: ");
        printMatrix(matrix);
        System.out.println("Result: " + res);
        System.out.println("Runtime: " + runtime + "ns");

    }


}
