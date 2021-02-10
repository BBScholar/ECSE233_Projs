
import java.util.LinkedList;
import java.util.Iterator;

public class PhBLinkedList implements PhoneBook {
    private final LinkedList<Person> mData;

    public PhBLinkedList() {
        mData = new LinkedList<Person>();
    }
    
    // This method will have O(1) runtime for adding elements at the head or tail of the linked list
    // and O(n) runtime for inserting an element in the middle of the list
    @Override
    public void insert(final int i, final Person p) {
        if(i >= size())
            mData.addLast(p);
        else if(i < 0)
            throw new IndexOutOfBoundsException("Negative indicies are bad!");
        else {
            mData.addLast(mData.get(i)); // you could also use addFirst here, doesnt matters
            mData.set(i, p);
        }
    }
    
    // O(1) runtime for the removing the head and tail
    // and O(n) for removing any element in the middle
    // The java implementation does the head and tail operations internally
    // so no need to be redundant, just know they are there
    @Override
    public Person remove(final int i) {
        if(i >= size())
            return null;
        else if(i < 0)
            throw new IndexOutOfBoundsException("Negative indicies are bad");
        else {
            // this will be inlined (probably, so maybe no stack space?)
            final int last_idx = size() - 1;
            
            // java does this internally, but we will call these manually
            // to avoid the later copy steps
            if(i == last_idx) return mData.removeLast();
            else if(i == 0) return mData.removeFirst();
            
            // swap the elements and 
            final Person temp = mData.get(i);
            mData.set(i, mData.getFirst()); 
            mData.removeFirst();

            return temp;
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
    
    // O(1), calculated for us by the internal datatype.
    @Override
    public int size() {
        return mData.size();
    }

    @Override
    public Iterator<Person> iterator() {
        return mData.iterator();
    }
}
