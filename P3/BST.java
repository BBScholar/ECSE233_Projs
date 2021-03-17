
import java.util.Optional;

public abstract class BST<K, V> {

    protected class Node<K, V> {
        public Node(K key, V value, Optional<Node<K, V>> left, Optional<Node<K, V>> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Node(K key, V value) {
            this(key, value, Optional.empty(), Optional.empty());
        }

        public K key;
        public V value;
        public Optional<Node<K, V>> left;
        public Optional<Node<K, V>> right;

        @Override
        public String toString() {
            return "Node [key=" + key + ", value=" + value + "]";
        }
    }

    protected Optional<Node<K, V>> mRoot;

    public BST() {
        mRoot = Optional.empty();
    }
    
    // override of the method below
    public final boolean insert(final K key, final V value) {
        return insert(key, value, true);
    }

    // Time complexity: O(logn)
    // if the `insertOnDuplicate` flag is set to false, this function will exit if a duplicate is found
    public final boolean insert(final K key, final V value, final boolean insertOnDuplicate) {

        // allocate new node
        final Node<K, V> new_node = new Node(key, value);

        // if we do not have a root node, set the root node to the new node
        if (mRoot.isEmpty()) {
            mRoot = Optional.of(new_node);
            return true;
        }

        // get the hash of the key for future comparisons
        final int hash = key.hashCode();
        // allocate a boolean to keep track of which branch we follow
        // this will save us a comparison later on
        boolean left = false;
        // allocate int for hashcode comparison, avoids allocation in loop
        int cmp;
        // allocate the cursor and parent ptrs
        Optional<Node<K, V>> cursor, parent;

        // set the parent to empty
        parent = Optional.empty();
        // set the cursor to root,
        cursor = mRoot;

        // traverse the tree
        while (cursor.isPresent()) {
            // calculate the comparison between the keys
            cmp = hash - cursor.get().key.hashCode();

            // if a duplicate is found, exit if flag is set
            if (!insertOnDuplicate && cmp == 0)
                return false;

            parent = cursor;

            // traverse down the left branch if cmp is less than 0
            // traverse down the right tree if cmp is greater than or equal to 0
            if (cmp >= 0) {
                left = false;
                cursor = cursor.get().right;
            } else {
                left = true;
                cursor = cursor.get().left;
            }

        }

        // append the new node to the desired branch of the parent.
        // we save a comparison here.
        if (left)
            parent.get().left = Optional.of(new_node);
        else
            parent.get().right = Optional.of(new_node);

        return true;
    }

    // Time complexity: O(logn) for both implementations
    public abstract boolean delete(final K key, final V value);

    // Time complexity: O(logn)
    // finds a node with key. Returns a pointer to the node
    // this method simpifies other methods
    protected final Optional<Node<K, V>> findNode(final K key) {
        // allocate comaprison value
        int cmp;
        // calculate key's hash for future comparisons
        final int hash = key.hashCode();

        // allocate cursor, pointing at root initially
        Optional<Node<K, V>> cursor = mRoot;

        // traverse the tree and return the node if the keys are equal
        while (cursor.isPresent()) {
            cmp = hash - cursor.get().key.hashCode();
            if (cmp == 0)
                return cursor;
            else if (cmp > 0)
                cursor = cursor.get().right;
            else
                cursor = cursor.get().left;
        }

        // return nothing if not node is found
        return Optional.empty();
    }

    // Time complexity: O(logn)
    public final Optional<V> findFirst(final K key) {
        // find node with desired key
        final var node = findNode(key); // O(logn)
        
        // return value of the node if it exists
        if (node.isPresent())
            return Optional.of(node.get().value);
        else
            return Optional.empty();
    }

    // Time complexity: O(n)
    public final void printTreeHelper(Optional<Node<K, V>> node, StringBuilder builder, int indent, boolean root, boolean left) {
        if (node.isEmpty())
            return;

        for (int i = 0; i < indent; i++)
            builder.append(' ');

        if (!root) {
            builder.append('-');
            if(left) builder.append("Left: ");
            else builder.append("Right: ");
        } else {
            builder.append("Root: ");
        }

        builder.append(node.get().key.toString());
        builder.append('\n');
    

        // traverse left tree
        printTreeHelper(node.get().left, builder, indent + 1, false, true);

        // traverse right tree
        printTreeHelper(node.get().right, builder, indent + 1, false, false);
    }

    // Time complexity: O(n)
    public final void print() {
        StringBuilder builder = new StringBuilder();
        printTreeHelper(mRoot, builder, 0, true, false); // O(n)
        System.out.println(builder.toString());
    }

}
