
import java.util.ArrayList;

import java.io.PrintWriter;

class Test {
    

    // generates matricies in the correct format (in increasing order works fine)
    public static int[][] generateMatrix(final int n) {
        int[][] matrix = new int[n][n];
        int num = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = ++num;
            }
        }

        return matrix;
    }
    
    // runs a benchmark
    public static long[] runBench(final ArrayList<int[][]> matricies, final int iters) {

        // allocate runtimes array
        long[] runtimes = new long[matricies.size()];
        int m = 0;
        long start;
        // iterate over every matrix
        for (final var matrix : matricies) {
            long totalRuntime = 0;

            // target the bottom left corner of the matrix, as that is the worst case senario in terms
            // of runtime
            final int target = matrix[matrix.length - 1][0];

            for (int i = 0; i < iters; i++) {
                start = System.nanoTime();
                // invoke the method
                A2P1.inMatrix(matrix, target);
                final long runtime = System.nanoTime() - start;
                // add to the total runtime 
                totalRuntime += runtime;
            }
            // calcuate average runtime
            runtimes[m] = totalRuntime / iters;
            m++;
        }

        return runtimes;
    }

    public static void main(String... args) {
        // number of times to invoke the method on each matrix
        final int numIters = 100;

        // max dimensionn of matrices
        final int numMatrixies = 500;
    

        // allocate spaces for the matrixies
        var matricies = new ArrayList<int[][]>();
        matricies.ensureCapacity(numMatrixies);
    

        // generate the matricies. Start at n=2 because 0 and 1 are meaningless for us
        for (int i = 0; i < numMatrixies; i++) {
            matricies.add(generateMatrix(i + 2));
        }
        
        // "Warm up" the jvm, doing this results in a much cleaner plot in the end.
        // This allows java to get any funny business out of its system (such as class loading, etc).
        // 'bad_plot.png' shows the plot if this is not run before the actual benchmark
        final var badRuntimes = runBench(matricies, numIters);
        
        // run the benchmark for real this time
        final var avgRuntimes = runBench(matricies, numIters);
        
        // print the data and write it to a csv for plotting
        try {
            PrintWriter badWriter = new PrintWriter("bad_runtimes.csv");
            PrintWriter writer = new PrintWriter("runtimes.csv");

            // write header
            badWriter.write("n, r\n");
            writer.write("n,r\n");

            System.out.println("Results:");
            for (int i = 0; i < numMatrixies; i++) {
                System.out.println("N = " + (i + 2) + ": Runtime: " + avgRuntimes[i]);
                writer.write("" + (i + 2) + "," + avgRuntimes[i] + "\n");
                badWriter.write("" + (i + 2) + "," + badRuntimes[i] + "\n");
            }

            writer.close();
            badWriter.close();
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
