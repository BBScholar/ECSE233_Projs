
import java.util.LinkedList;
import java.util.Optional;

final class DuplicateKeyBST<K, V> extends BST<K, V> {

    public DuplicateKeyBST() {
        super();
    }

    // Time complexity: O(1)
    private void deleteNode(Optional<Node<K, V>> parent, Optional<Node<K, V>> toDelete, boolean leftOfParent) {

        assert toDelete.isPresent();

        final boolean hasLeft = toDelete.get().left.isPresent();
        final boolean hasRight = toDelete.get().right.isPresent();

        if (!hasLeft || !hasRight) { // node has 0 or 1 children
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
        } else { // node has 2 children
            Optional<Node<K, V>> replacement, replacementParent;

            replacementParent = Optional.empty();

            // find minumum value in right subtree
            replacement = toDelete.get().right;
            while (replacement.get().left.isPresent()) {
                replacementParent = replacement;
                replacement = replacement.get().left;
            }

            toDelete.get().key = replacement.get().key;
            toDelete.get().value = replacement.get().value;

            // recurse to delete the replacement node
            // Time complexity of this loop is O(1) because
            // it will never recurse more than one
            deleteNode(replacementParent, parent, true);
        }
    }

    // time complexity: O(logn)
    @Override
    public boolean delete(final K key, final V value) {

        // getthe hash codes of the key and value
        final int kHash = key.hashCode();
        final int vHash = value.hashCode();

        Optional<Node<K, V>> cursor, parent;
        int kCmp, vCmp;
        boolean left = false;

        cursor = mRoot;
        parent = Optional.empty();

        while (cursor.isPresent()) {
            // calculate the two comparison values
            kCmp = kHash - cursor.get().key.hashCode();
            vCmp = vHash - cursor.get().value.hashCode();

            // if the key and value are equal, delete the node
            if (kCmp == 0 && vCmp == 0) {
                deleteNode(parent, cursor, left); // O(1)
                return true;
            }

            // otherwise traverse normally
            parent = cursor;

            if (kCmp >= 0) {
                left = false;
                cursor = cursor.get().right;
            } else {
                left = true;
                cursor = cursor.get().left;
            }
        }
        return false;

    }

    // runtime: O(logn) + O(logn) = O(logn)
    // finds all elements with a given key
    public LinkedList<V> findAll(final K key) {
        LinkedList<V> res = new LinkedList();

        // find the first instance of the desired key
        var node = findNode(key); // O(logn)

        // return empty list if no node was found
        if (node.isEmpty())
            return res;

        int cmp;
        final int hash = key.hashCode();

        // traverse the children of the found node.
        // Go right if the keys are equal
        // Go left if the key is less than the current nodes key
        // break if the key is greater than the current nodes key
        while (node.isPresent()) { // O(logn)
            cmp = hash - node.get().key.hashCode();
            assert cmp <= 0;
            if (cmp == 0) {
                // append the value to the list
                res.addLast(node.get().value);
                node = node.get().right;
            } else if (cmp < 0) {
                node = node.get().left;
            } else
                break;
        }

        return res;
    }

}
