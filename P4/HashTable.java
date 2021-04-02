
import java.util.Optional;
import java.util.ArrayList;

class HashTable {
    // constants
    private static final float kExpansionFactor = 2.0f;
    private static final float kMaxLoadingThreshold = 0.75f;
    private static final float kMinLoadingThreshold = 0.1f;

    // member items

    // Number of buckets in the hashtable
    private int mCapacity;
    // Number of items in the table
    private int mItems;
    // buckets
    private ArrayList<Optional<Entry>> mData;
    // load factor
    private float mLoadFactor;
        
    public HashTable() {
        mItems = 0;
        mCapacity = 1;
        
        // allocate buckets as empty
        mData = new ArrayList<Optional<Entry>>(mCapacity);
        mData.ensureCapacity(mCapacity);
        for (int i = 0; i < mCapacity; ++i)
            mData.add(Optional.empty());
        
        // load factor starts at 0
        mLoadFactor = 0f;
    }

    // O(n)
    private void checkForRehash() {
        mLoadFactor = (float) mItems / (float) mCapacity;

        if (mLoadFactor > kMaxLoadingThreshold || mLoadFactor < kMinLoadingThreshold) {
            final int oldCapacity = mCapacity;
            ArrayList<Optional<Entry>> oldData = mData;

            if (mLoadFactor > kMaxLoadingThreshold) {
                mCapacity = (int) Math.ceil(oldCapacity * kExpansionFactor);
            } else if (mLoadFactor < kMinLoadingThreshold) {
                mCapacity = (int) Math.ceil(oldCapacity / kExpansionFactor);
            }

            mData = new ArrayList<>(mCapacity);

            // O(2*n)
            for(int i = 0; i < mCapacity; ++i) {
                mData.add(Optional.empty());
            }

            for(int i = 0; i < oldCapacity; ++i) {
                Optional<Entry> entry = oldData.get(i);
                while(entry.isPresent()) {
                    // O(n)
                    insert(entry.get().key, entry.get().frequency, false);
                    entry = entry.get().next;
                }
            }

            oldData.clear();
        }
    }

    //O(1)
    private int getHash(char c) {
        return Math.abs(Character.getNumericValue(c) % mCapacity);
    }

    public boolean insert(char c) {
        return insert(c, 1);
    }

    public boolean insert(char c, long freq) {
        return insert(c, freq, true);
    }

    // O(1) average, O(n) worst case
    private boolean insert(char c, long freq, boolean rehash) {
        final int hash = getHash(c);

        Optional<Entry> parent = Optional.empty();
        Optional<Entry> entry = mData.get(hash);

        while (entry.isPresent()) {
            if (entry.get().key == c)
                return false;
            parent = entry;
            entry = entry.get().next;
        }

        final Optional<Entry> newEntry = Optional.of(new Entry(c, freq));

        if (parent.isEmpty()) {
            mData.set(hash, newEntry);
        } else {
            parent.get().next = newEntry;
        }

        if(rehash) {
            mItems++;
            checkForRehash();
        }

        return true;
    }

    public boolean delete(char c) {
        final int hash = getHash(c);

        Optional<Entry> parent = Optional.empty();
        Optional<Entry> entry = mData.get(hash);

        while (entry.isPresent()) {
            if (entry.get().key == c) {
                // delete

                if (parent.isEmpty())
                    mData.set(hash, entry.get().next);
                else {
                    parent.get().next = entry.get().next;
                }

                mItems--;
                
                checkForRehash();

                return true;
            }
            parent = entry;
            entry = entry.get().next;
        }

        return false;
    }
    
    // O(1) average, O(n) worstcase
    public Optional<Entry> search(char c) {
        final int hash = getHash(c);

        Optional<Entry> entry = mData.get(hash);
        while (entry.isPresent() && entry.get().key != c) {
            entry = entry.get().next;
        }

        return entry;
    }
    
    // O(1)
    public boolean isEmpty() {
        return mItems == 0;
    }

    public class Entry {
        private Entry(char key, long freq) {
            this.key = key;
            this.frequency = freq;
            this.next = Optional.empty();
        }

        public final char key;
        public long frequency;

        private Optional<Entry> next;


    }
}
