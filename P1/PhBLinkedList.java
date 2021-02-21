
import java.util.LinkedList;
import java.util.Iterator;

public class PhBLinkedList implements PhoneBook {
    private final LinkedList<Person> mData;

    public PhBLinkedList() {
        mData = new LinkedList<Person>();
    }
    

    // This method will have O(1) runtime for adding elements at the 
    // head and tail of the LinkedList, this is (likely) done internally by java
    // and O(n) runtime for any element in the "middle"
    // of the collection
    @Override
    public void insert(final int i, final Person p) {
        if(i < 0) throw new IndexOutOfBoundsException();
        else {
            final int last_idx = size();
            mData.add(i > last_idx ? last_idx : i, p);
        }
    }
    
    // O(1) runtime for the removing the head and tail
    // and O(n) for removing any element in the middle
    // The java implementation does the head and tail operations internally
    // so no need to be redundant, just know they are there
    @Override
    public Person remove(final int i) {
        final int last_idx = size();
        if(i < 0) throw new IndexOutOfBoundsException();
        else if(i > last_idx) return null;
        else {
            return mData.remove(i);
        }
    }
    
    // O(1) runtime for looking up the head or tail,
    // O(n) for anything in the middle since we do not have direct
    // access to elements in the middle (in normal implementations)
    @Override
    public Person lookup(final int i) {
        if(i >= size() || i < 0) throw new IndexOutOfBoundsException();
        return mData.get(i);
    }
    
    // O(1), calculated for us by the internal datatype
    // For some reason, if the java LinkedList impl doesnt store the size, this would be O(n),
    // but I doubt it.
    @Override
    public int size() {
        return mData.size();
    }

    @Override
    public Iterator<Person> iterator() {
        return mData.iterator();
    }
}
