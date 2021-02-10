
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PhBArrayList implements PhoneBook {
    // storage type for the implementation of PhoneBook
    private final ArrayList<Person> mData;

    public PhBArrayList() {
        // constuct the array list
        mData =  new ArrayList<Person>();
    }

    // this method will be O(1) for all inputs, since we do not care about the order of the elements, we simply copy the 
    // element at index i to the end and set the element at index I to the new value of p
    @Override
    public void insert(final int i, final Person p) {
        // if the provided index is greater than the current 
        // size of the phonebook, simply add the element to the 
        // end of the arraylist, otherwise, insert at the desired index
        if(i >= size()) {
            // O(1) case
            mData.add(p);
        } else if(i < 0) {
            // we cant use negative indicies
            throw new IndexOutOfBoundsException("Negative indicies are bad!");
        } else {
            mData.add(mData.get(i));
            mData.set(i, p);
        }
    }
    
    // this method will be O(1) for all inputs. Once again, since we do not care about internal order of the elements, we can swap element i and the last element
    // then remove the last element, thus saving us a good ammount of copying
    @Override
    public Person remove(final int i) {
        // return null if index is greater than phonebook length, since this element cannt be removed, since it doesnt
        // exist yet
        if(i >= size()) return null;
        else if (i < 0) throw new IndexOutOfBoundsException("Negative indicies are bad");
        else {
            // calculate the last index in the arraylist
            final int lastIdx = size() - 1;

            // if we're removing the last element, save some copies and cpu cycles
            if(lastIdx == i) return mData.remove(i);
            
            // otherwise swap the last element with the element to be removed, then remove the last element.
            // This makes sure that we do not have to copy every element over by 1 in memory
            // this allows this implementation to be O(1)
            // this works since we are not sorting the data
            final Person temp = mData.get(i);
            mData.set(i, mData.get(lastIdx));
            mData.remove(lastIdx);
            return temp; 
        }
    }
    
    // This method will be constant time, O(1), since we are not searching, we are just getting whatever person is at index i
    @Override
    public Person lookup(final int i) {
        // we can only lookup people that exist
        if( i >= size() || i < 0) throw new IndexOutOfBoundsException();
        // get the person
        return mData.get(i);
    }

    // size of the phonebook.
    // O(1), unless the java implementation is dumb
    @Override
    public int size() {
        return mData.size();
    }

    @Override
    public Iterator<Person> iterator() {
        return mData.iterator();
    }
    
}
