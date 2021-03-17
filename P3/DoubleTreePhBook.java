
import java.util.Optional;
import java.util.LinkedList;

class DoubleTreePhBook {

    private final UniqueKeyBST<Integer, String> mNumberLookup;
    private final DuplicateKeyBST<String, Integer> mNameLookup;

    public DoubleTreePhBook() {
        this.mNumberLookup = new UniqueKeyBST();
        this.mNameLookup = new DuplicateKeyBST();
    }

    public boolean PhBInsert(final String name, final int number) {
        final var success = mNumberLookup.insert(number, name, false);
        if (!success)
            return false;

        mNameLookup.insert(name, number);

        return true;
    }

    public boolean PhBDelete(final String name, final int number) {
        final boolean found = mNumberLookup.delete(number, name);
        if (!found)
            return false;

        mNameLookup.delete(name, number);

        return true;
    }

    public Optional<String> PhBPhoneSearch(final int number) {
        return mNumberLookup.findFirst(number);
    }

    public LinkedList<Integer> PhBNameSearch(final String name) {
        return mNameLookup.findAll(name);
    }

    public void printNumberTree() {
        mNumberLookup.print();
    }

    public void printNameTree() {
        mNameLookup.print();
    }

}
