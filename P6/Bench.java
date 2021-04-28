import java.io.IOException;
import java.util.Random;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.io.FileWriter;

class Bench {

    private static final String kFs = "data.csv";

    private static Random sRandom;

    static {
        // create a static rng instance
        sRandom = new Random();
    }

    // generates a random string
    public static String randomString() {
        final int leftLimit = 'a';
        final int rightLimit = 'z';
        final int len = 10;

        return sRandom.ints(leftLimit, rightLimit + 1)
            .limit(len)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    // generates a random number
    public static long randomNumber() {
        return sRandom.nextLong();
    }

    // checks if an array is sorted, used to ensure that the sorting methods are actually working
    public static boolean isSorted(final PhBook.Person[] people, final int start, final int end) {
        boolean res = true;
        for(int i = start; i < end - 1; ++i)
            res &= people[i].compareTo(people[i + 1]) < 0;
        return res;
    }

    // runs a benchmark on the two sorting algos
    public static long[][] benchRun() {
        // allocate result array
        long[][] results = new long[3][5];
        int n;
        // allocate phonebook class with enough storage for all elements
        PhBook book = new PhBook(1000000);

        // iterate through each n
        for(int i = 2; i <= 6; ++i) {
            book.clear();
            // calculate n
            n = (int) Math.pow(10, i);
            // generate n random people
            for(int j = 0; j < n; ++j)
                book.PhBInsert(randomString(), randomNumber());

            // clone the arrays so ensure that both algos are sorting the same
            // exact array
            PhBook.Person[] clone1 = book.getPeople().clone();
            PhBook.Person[] clone2 = Arrays.copyOfRange(clone1.clone(), 0, n);

            results[0][i - 2] = n;

            long start;

            // time the heapsort method
            start = System.nanoTime();
            PhBook.heapSort(clone1, 0, n - 1);
            results[1][i - 2] = System.nanoTime() - start;
            // ensure that it works
            assert isSorted(clone1, 0, n - 1);

            // time the Arrays sort method
            start = System.nanoTime();
            Arrays.sort(clone2);
            results[2][i - 2] = System.nanoTime() - start;
            // ensure that it works
            assert isSorted(clone2, 0, clone2.length - 1);
        }
        return results;
    }

    public static void main(String... args) {
        // run the bench once to get rid of any
        // class loading delays and other weird java things
        benchRun();

        // run the bench
        final var results = benchRun();

        // write the data to a csv file
        try {
            FileWriter writer = new FileWriter(kFs);
            writer.write("n,h,a\n");
            for (int i = 0; i < 5; ++i) {
                final long n = results[0][i];
                final long h = results[1][i];
                final long a = results[2][i];
                writer.write(n + "," + h + "," + a + "\n");
            }
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }



    }
}
