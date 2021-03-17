
import java.util.Optional;
import java.util.LinkedList;

class DoubleTreePhBook {

    private final UniqueKeyBST<Integer, String> mNumberLookup;
    private final DuplicateKeyBST<String, Integer> mNameLookup;

    public DoubleTreePhBook() {
        this.mNumberLookup = new UniqueKeyBST();
        this.mNameLookup = new DuplicateKeyBST();
    }
    
    // Time complexity: O(logn)
    // O(logn) + O(logn) = O(logn^2) = O(logn)
    public boolean PhBInsert(final String name, final int number) {
        final boolean success = mNumberLookup.insert(number, name, false);
        if (!success)
            return false;

        mNameLookup.insert(name, number);

        return true;
    }

    // Time complexity: O(logn)
    // O(logn) + O(logn) = O(logn^2) = O(logn)
    public boolean PhBDelete(final String name, final int number) {
        final boolean success = mNumberLookup.delete(number, name);
        if (!success)
            return false;

        mNameLookup.delete(name, number);

        return true;
    }

    // Time complexity: O(logn)
    public Optional<String> PhBPhoneSearch(final int number) {
        return mNumberLookup.findFirst(number);
    }

    // Time complexity: O(logn)
    public LinkedList<Integer> PhBNameSearch(final String name) {
        return mNameLookup.findAll(name);
    }

    public void printNumberTree() {
        mNumberLookup.print();
    }

    public void printNameTree() {
        mNameLookup.print();
    }

    public void printTrees() {
        final String tmp = "===============================";
        System.out.println(tmp);
        System.out.println("Number Keys:");
        mNumberLookup.print();
        System.out.println("Name Keys:");
        mNameLookup.print();
        System.out.println(tmp);
    }

}
