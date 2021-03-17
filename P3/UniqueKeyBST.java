
import java.util.Optional;

final class UniqueKeyBST<K, V> extends BST<K, V> {

    public UniqueKeyBST() {
        super();
    }

    // Time complexity: O(1)
    private void deleteNode(Optional<Node<K, V>> parent, Optional<Node<K, V>> toDelete, boolean leftOfParent) {
        assert toDelete.isPresent();

        // get state of left and right children
        final boolean hasLeft = toDelete.get().left.isPresent();
        final boolean hasRight = toDelete.get().right.isPresent();

        // If the node has 0 or 1 children, run this case
        // O(1) complexity
        if (!hasLeft || !hasRight) {
            Optional<Node<K, V>> child = Optional.empty();
            
            if (!hasLeft)
                child = toDelete.get().right;
            else
                child = toDelete.get().left;

            if (toDelete == mRoot)
                mRoot = Optional.empty();
            else if (leftOfParent)
                parent.get().left = child;
            else
                parent.get().right = child;
        } else { // If the node has 2 children, run this case
            Optional<Node<K, V>> replacement, replacementParent;
            boolean left;

            replacementParent = Optional.empty();

            // randomize which subtree we pull from, this keeps the
            // tree a bit more symetrical, thus increasing efficiency
            if (Math.random() > 0.5) { // find minimum value in right subtree
                replacement = toDelete.get().right;
                while (replacement.get().left.isPresent()) {
                    replacementParent = replacement;
                    replacement = replacement.get().left;
                }
                left = true;
            } else { // find maximum value in left subtree
                replacement = toDelete.get().left;
                while (replacement.get().right.isPresent()) {
                    replacementParent = replacement;
                    replacement = replacement.get().right;
                }
                left = false;
            }

            // copy the new kv pair into the toDelete node
            toDelete.get().key = replacement.get().key;
            toDelete.get().value = replacement.get().value;

            // recursivly delete the replacement node
            // This loop has a time complexity of O(1) since we will never
            // hit the recusrive case again after the first iteration
            deleteNode(replacementParent, replacement, left);
        }
    }

    // Time complexity: O(logn)
    // for this implementation of delete, we dont care about the value.
    // Since every key is unique. We can also use the better delete method
    // from lecture, that prevents the tree from becoming super asymetrical
    @Override
    public boolean delete(final K key, final V value) {
        final int kHash = key.hashCode();

        // allocate variables
        Optional<Node<K, V>> cursor, parent;
        int cmp;
        boolean left = false;

        cursor = mRoot;
        parent = Optional.empty();

        // traverse tree as usual, delete node if/when it is found
        while (cursor.isPresent()) { // O(logn)
            cmp = kHash - cursor.get().key.hashCode();

            if (cmp == 0) {
                // delete the node here
                deleteNode(parent, cursor, left); // O(1)
                return true;
            }

            parent = cursor;

            if (cmp > 0) {
                left = false;
                cursor = cursor.get().right;
            } else {
                left = true;
                cursor = cursor.get().left;
            }
        }
        return false;
    }

}
