
import java.util.Optional;

class HashTable {
    // facctor to expand buckets by during rehash
    private static final float kExpansionFactor = 2.0f;
    // max load factor rehash threshold
    // rehash if load factor is above this value
    private static final float kMaxLoadingThreshold = 0.75f;
    // min load factor rehash threshold
    // rehash if load factor is below this value
    private static final float kMinLoadingThreshold = 0.1f;


    // Number of buckets in the hashtable
    private int mCapacity;
    // Number of items in the table
    private int mItems;
    // buckets 
    private Entry[] mBuckets;
    // load factor
    private float mLoadFactor;

    public HashTable() {
        mItems = 0;
        mCapacity = 1;

        // allocate buckets as empty
        mBuckets = new Entry[mCapacity];

        // load factor starts at 0
        mLoadFactor = 0f;
    }

    // O(n)
    private void checkForRehash() {
        mLoadFactor = (float) mItems / (float) mCapacity;

        // check if the current loadfactor is between our desired bounds, if it
        // isnt, we will need to rehash
        if (mLoadFactor > kMaxLoadingThreshold || mLoadFactor < kMinLoadingThreshold) {
            // store the old data and capacity for future operations
            final int oldCapacity = mCapacity;
            final Entry[] oldData = mBuckets;

            if (mLoadFactor > kMaxLoadingThreshold) {
                mCapacity = (int) Math.ceil(oldCapacity * kExpansionFactor);
            } else if (mLoadFactor < kMinLoadingThreshold) {
                mCapacity = (int) Math.ceil(oldCapacity / kExpansionFactor);
            }

            // allocate new storage
            mBuckets = new Entry[mCapacity];

            // O(n): insert every element from the old buckets into
            // the new buckets with the new hash function.
            for (int i = 0; i < oldCapacity; ++i) {
                Entry entry = oldData[i];
                while (entry != null) {
                    // O(1)
                    insert(entry.key, entry.frequency, false);
                    entry = entry.next;
                }
            }
        }
    }

    // O(1)
    private int getHash(final char c) {
        return Math.abs(Character.getNumericValue(c) % mCapacity);
    }
    
    // OVerride of the insert, sets rehash to false and teh frequnecy to 1
    public boolean insert(final char c) {
        return insert(c, 1);
    }
    

    // Override for the function below, sets `rehash` to true
    public boolean insert(final char c, final long freq) {
        return insert(c, freq, true);
    }

    // O(1) average, O(n) worst case
    // the `rehash` flag denotes whether the method will increment the number of items
    // and check for a rehash, this is set to false when a rehash is occuring to prevent unnessesary recursrsive
    // calls to the `checkForRehash` method.
    // returns true if insertion is successfull
    private boolean insert(final char c, final long freq, final boolean rehash) {
        final int hash = getHash(c);

        Entry parent = null;
        Entry entry = mBuckets[hash];
    
        // find the next available spot in the bucket
        while (entry != null) {
            // return false if this key already exists
            if (entry.key == c)
                return false;
            parent = entry;
            entry = entry.next;
        }
        
        // declare the new entry
        final Entry newEntry = new Entry(c, freq);
        
        // if the parent is null, we know that that bucket is empty,
        // so we can set the 'root' entry to the new entry
        // otherwise, append it to the back of the list
        if (parent == null) {
            mBuckets[hash] = newEntry;
        } else {
            parent.next = newEntry;
        }
        
        // increment item count and rehash if the rehash flag is set
        if (rehash) {
            mItems++;
            checkForRehash();
        }

        return true;
    }
    
    // O(1) average, O(n) worstcase
    public boolean delete(final char c) {
        // get the hash
        final int hash = getHash(c);
    
        // initialize cursors
        Entry parent = null;
        Entry entry = mBuckets[hash];

        while (entry != null) {
            // if we find a matching key, delete the entry
            if (entry.key == c) {
                // if the parent is null, we know this is the root entry, so just set
                // the root entry to the next entry (which could be null, but this is handled implicitly)
                // otherwise delete normally
                if (parent == null)
                    mBuckets[hash] = entry.next;
                else {
                    parent.next = entry.next;
                }
                
                // decrement item count
                // and check for rehash
                mItems--;
                checkForRehash();

                return true;
            }
            parent = entry;
            entry = entry.next;
        }

        // return false if we do not find an entry with a matching key 
        return false;
    }

    // O(1) average
    public Optional<Entry> search(final char c) {
        // get hash
        final int hash = getHash(c);
        
        // search the bucket for a matching key
        Entry entry = mBuckets[hash];
        while (entry != null && entry.key != c) {
            entry = entry.next;
        }
            
        // return optional empty if we do not find a valid entry
        return entry != null ? Optional.of(entry) : Optional.empty();
    }

    // O(1)
    public boolean isEmpty() {
        return mItems == 0;
    }

    // Entry class
    // contains the key and frequency of each char
    // also contains a ptr to the next entry in the bucket
    // should be fairly self explanatory
    public class Entry {
        private Entry(final char key, final long freq) {
            this.key = key;
            this.frequency = freq;
            this.next = null;
        }

        private final char key;
        private long frequency;
        private Entry next;

        public char getKey() {
            return key;
        }

        public long getFrequency() {
            return frequency;
        }

        public void setFrequency(final long frequency) {
            this.frequency = frequency;
        }

        public void incrementFrequency() {
            this.frequency++;
        }

        public void decrementFrequency() {
            this.frequency--;
        }

        @Override
        public String toString() {
            return "Entry [frequency=" + frequency + ", key=" + key + "]";
        }

    }
}
