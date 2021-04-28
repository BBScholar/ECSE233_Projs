
import java.lang.IndexOutOfBoundsException;

class PhBook {

    private int mItems;
    private final int mCapacity;

    private Person[] mPeople;

    public PhBook(final int capacity) {
        if (capacity <= 0)
            throw new IndexOutOfBoundsException();
        mItems = 0;
        mCapacity = capacity;
        mPeople = new Person[capacity];
    }

    // Time: O(1)
    public boolean PhBInsert(final String name, final long number) {
        // if we are at capacity, return false
        if (mItems == mCapacity)
            return false;

        // otherwise, append the item to the array
        mPeople[mItems++] = new Person(name, number);

        return true;
    }

    // Time: O(n)
    public void PhBDelete(final String name, final long number) {
        Person person;
        // loop through every entry
        for (int i = 0; i < mItems; ++i) {
            person = mPeople[i];

            // if we find a matching person, delete them
            if (person.name.equals(name) && person.ph_number == number) {
                // if they are in the middle of the array, we need to copy every entry over by one place
                for (int ii = i; ii < mItems - 1; ++ii) {
                    mPeople[ii] = mPeople[ii + 1];
                }
                // set the last entry to null
                mPeople[--mItems] = null;
                return;
            }
        }
    }

    // returns a reference to the array of entries
    public Person[] getPeople() {
        return mPeople;
    }

    // clears the array, for testing purposes
    public void clear() {
        mItems = 0;
        mPeople = new Person[mCapacity];
    }

    // swaps two elements within an array
    // Time: O(1)
    private static void swap(final Person[] pb, final int a, final int b) {
        final Person temp = pb[a];
        pb[a] = pb[b];
        pb[b] = temp;
    }

    // Time: O(logn)
    private static void siftDown(final Person[] pb, final int startIdx, final int endIdx, final int siftIdx) {
        final int leftIdx = 2 * siftIdx;
        final int rightIdx = leftIdx + 1;

        final boolean hasLeft = leftIdx <= endIdx;
        final boolean hasRight = rightIdx <= endIdx;

        // figure out which child to attempt a swap with
        int swapIdx;
        if (!hasLeft && !hasRight) {
            return;
        } else if (hasLeft && !hasRight) {
            swapIdx = leftIdx;
        } else if (!hasLeft && hasRight) {
            swapIdx = rightIdx;
        } else {
            // if both children are present,
            // compare them to find the max between the two
            final int cmp = pb[leftIdx].compareTo(pb[rightIdx]);
            if (cmp >= 0)
                swapIdx = leftIdx;
            else
                swapIdx = rightIdx;
        }
        // swap if current node is less than node to swap with
        // then recurse to the chosen child if we swapped
        if (pb[siftIdx].compareTo(pb[swapIdx]) < 0) {
            swap(pb, siftIdx, swapIdx);
            siftDown(pb, startIdx, endIdx, swapIdx);
        }
    }

    // Time: O(nlogn)
    private static void heapify(final Person[] pb, final int startIdx, final int endIdx) {
        final int n = endIdx - startIdx;
        // sift down from element (n + 2) / 2 down to 0
        for (int i = (n + 2) / 2; i >= 0; --i) { // n
            siftDown(pb, startIdx, endIdx, i); // logn
        }
    }

    // Time: O(nlogn)
    public static void heapSort(Person[] pb, final int startIdx, final int endIdx) {
        int sorted = endIdx;
        // heapify the array
        heapify(pb, startIdx, endIdx);// nlogn
        while (sorted > startIdx) { // nlogn
            // swap the max element with the last element, then
            swap(pb, startIdx, sorted--); // 1
            // sift down until no elements remain
            siftDown(pb, startIdx, sorted, 0); // logn
        }
    }

    public class Person implements Comparable<Person> {
        public String name;
        public Long ph_number;

        public Person(final String name, final Long number) {
            this.name = name;
            this.ph_number = number;
        }

        @Override
        public int compareTo(final Person other) {
            final int nameCmp = name.compareTo(other.name);
            if (nameCmp == 0) {
                return ph_number.compareTo(other.ph_number);
            } else {
                return nameCmp;
            }
        }

        @Override
        public String toString() {
            return "Person [name=" + name + ", ph_number=" + ph_number + "]";
        }

    }

}
