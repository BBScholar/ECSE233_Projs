
class AVLTree<K extends Comparable<K>, V> {
    private Node mRoot;

    public AVLTree() {
        mRoot = null;
    }
    
    // Time: O(logn)
    public boolean insert(final K key, final V value) {
        Node newNode = new Node(key, value);

        if (mRoot == null) {
            mRoot = newNode;
            return true;
        }

        Node cursor = mRoot;
        Node parent = null;

        int cmp;
        boolean travLeft = false;

        // O(logn)
        // traverse tree normally, return false if duplicate is found
        while (cursor != null) {
            cmp = key.compareTo(cursor.key);

            if (cmp == 0) {
                return false;
            }

            parent = cursor;

            if (cmp > 0) {
                cursor = cursor.right;
                travLeft = false;
            } else {
                cursor = cursor.left;
                travLeft = true;
            }
        }
        
        // assign new node to correct branch
        if (travLeft) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        
        // set parent to parent
        newNode.parent = parent;
        // set height to 0, since we always insert at bottom of tree
        newNode.height = 0;
        
        // rebalance the tree
        rebalance(parent); // O(logn)

        return true;
    }

    // Time: log(n)
    // rebalances the tree
    private void rebalance(Node node) {
        if (node == null)
            return;
        
        // recalculate height and balances
        node.calculate();

        final int balance = node.getBalance();
            
        // if |balance| = 2, we need to do some rotations
        // if |balance| = 1, we continue to the parent
        // if |balance| = 0, we exit the loop
        if (Math.abs(balance) == 2) {
            // figure out which rotations to do using balances of trees
            boolean left = node.getBalance() < 0;
            // shouldnt need to check for nulls here since we can only get to balance = 2 if
            // there are two child nodes
            boolean lastLeft = (left ? node.left : node.right).getBalance() < 0;
            
            // execute correct rotations
            if (left && lastLeft) {
                rightRotation(node);
            } else if (left && !lastLeft) {
                leftRightRotation(node);
            } else if (!left && !lastLeft) {
                leftRotation(node);
            } else {
                rightLeftRotation(node);
            }
            return;
        } else if (Math.abs(balance) == 1) {
            // continue to parent
            rebalance(node.parent);
            return;
        } else if (balance == 0) {
            // balances are good, return
            return;
        } else {
            System.out.println("Somehow got a balance value of: " + balance);
            assert false;
            // This is bad. Should not happen;
        }
    }

    // Time: O(logn)
    private void deleteNode(Node toDelete) {
        final boolean leftOfParent = toDelete.parent != null && toDelete.parent.left == toDelete;
        
        // check if the node has a left and right child node
        final boolean hasLeft = toDelete.left != null;
        final boolean hasRight = toDelete.right != null;
        
        // if the node has 0 or 1 child nodes, execute this procedure
        if (!hasLeft || !hasRight) { // O(1)
            final Node parent = toDelete.parent;
            Node child = null;

            if (!hasLeft)
                child = toDelete.right;
            else
                child = toDelete.left;
            
            // if the delted node was the root, we need to point
            // the root at the child
            if (toDelete == mRoot) {
                mRoot = child;
                return;
            } else if (leftOfParent) {
                toDelete.parent.left = child;
            } else {
                toDelete.parent.right = child;
            }
            
            // rebalance from the parent of the removed node
            rebalance(parent);
        } else {
            Node replacement;
            
            // these loops will be O(logn) either way
            // not sure if we need to do the randomization with AVL tree, 
            // but it cant hurt. 
            if (Math.random() > 0.5) {
                replacement = toDelete.right;
                while (replacement.left != null) {
                    replacement = replacement.left;
                }
            } else {
                replacement = toDelete.left;
                while (replacement.right != null) {
                    replacement = replacement.right;
                }
            }
            
            // swap the kes and values
            toDelete.key = replacement.key;
            toDelete.value = replacement.value;
                
            // recurse this method to the replaced node
            // this will only recurse once, so should be O(1)
            deleteNode(replacement); // O(1)
        }

    }
    
    // Time: O(logn)
    public boolean delete(final K key) {
        if (mRoot == null)
            return false;

        Node cursor = mRoot;
        int cmp;
        
        // logn * logn = 2logn = logn
        // traverse until we find the correct node, then call the deleteNode method
        while (cursor != null) { // O(logn)
            cmp = key.compareTo(cursor.key);
            if (cmp == 0) {
                deleteNode(cursor); // O(logn)
                return true;
            }
            cursor = (cmp > 0) ? cursor.right : cursor.left;
        }

        return false;
    }

    // Time: O(logn)
    public V search(final K key) {
        if (mRoot == null)
            return null;

        Node cursor = mRoot;
        int cmp;
        
        // traverse normally until we find the correct key
        // return null if it isnt found
        while (cursor != null) {
            cmp = key.compareTo(cursor.key);
            if (cmp == 0) {
                return cursor.value;
            } else if (cmp > 0) {
                cursor = cursor.right;
            } else {
                cursor = cursor.left;
            }
        }

        return null;
    }

    public void clear() {
        mRoot = null;
    }

    // Time complexity: O(1)
    private void leftRotation(Node y) {
        // nodes y and z should always be present by definition of the rotations
        // we need to check if p and temp exist
        Node p = y.parent;
        Node z = y.right;
        Node temp = z.left;

        final boolean hasParent = (p != null);
        final boolean leftOfParent = hasParent && p.left == y;
        final boolean yIsRoot = !hasParent && (y == mRoot);

        // perform rotations
        z.left = y;
        y.parent = z;

        y.right = temp;
        if (temp != null)
            temp.parent = y;

        z.parent = p;

        if (hasParent && leftOfParent)
            p.left = z;
        else if (hasParent)
            p.right = z;

        // if y used to be the root, we need to change the root to point at z
        if (yIsRoot)
            mRoot = z;

        // recalculate the heights of each of the nodes that
        // would be affected
        y.calculate();
        z.calculate();
        if (hasParent)
            p.calculate();
    }

    // Time complexity: O(1)
    private void rightRotation(Node y) {
        // nodes y and z should always be present by definition of the rotations
        // we need to check if p and temp exist
        Node p = y.parent;
        Node z = y.left;
        Node temp = z.right;

        final boolean hasParent = (p != null);
        final boolean leftOfParent = hasParent && p.left == y;
        final boolean yIsRoot = !hasParent && (y == mRoot);
        
        // perform rotations
        z.right = y;
        y.parent = z;

        y.left = temp;
        if (temp != null)
            temp.parent = y;

        z.parent = p;

        if (hasParent && leftOfParent)
            p.left = z;
        else if (hasParent)
            p.right = z;
        
        // if y used to be the root, we need to change the root to point at z
        if (yIsRoot)
            mRoot = z;
        
        // recalculate the heights of each of the nodes that
        // would be affected
        y.calculate();
        z.calculate();
        if (hasParent)
            p.calculate();
    }

    // Time complexity: O(1)
    private void leftRightRotation(Node node) {
        // first do the left rotation around left child node
        leftRotation(node.left); // O(1)
        // then do right rotation about this node
        rightRotation(node); // O(1)
    }

    // Time complexity: O(1)
    private void rightLeftRotation(Node node) {
        // first do the right rotation around right child node
        rightRotation(node.right); // O(1)
        // then do left rotation about this node
        leftRotation(node); // O(1)
    }

    private class Node {
        K key;
        V value;

        private int height, balance;

        Node parent;
        Node left;
        Node right;

        @Override
        public String toString() {
            return "Node [balance=" + balance + ", key=" + key + ", value=" + value + ", height=" + height + "]";
        }

        public Node(final K key, final V value, final Node parent, final Node right, final Node left) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.right = right;
            this.left = left;
            this.height = 0;
        }

        public Node(final K key, final V value) {
            this.key = key;
            this.value = value;
            this.height = 0;
        }

        public int getHeight() {
            return this.height;
        }

        public int getBalance() {
            return this.balance;
        }

        public void calculate() {
            int leftHeight = (left == null ? -1 : left.height) + 1;
            int rightHeight = (right == null ? -1 : right.height) + 1;
            this.height = Math.max(leftHeight, rightHeight);
            this.balance = rightHeight - leftHeight;
        }

    }

    // Time complexity: O(n)
    public final void printTreeHelper(Node node, StringBuilder builder, int indent, boolean root, boolean left) {
        if (node == null)
            return;

        for (int i = 0; i < indent; i++)
            builder.append(' ');

        if (!root) {
            builder.append('-');
            if (left)
                builder.append("Left: ");
            else
                builder.append("Right: ");
        } else {
            builder.append("Root: ");
        }

        builder.append(node.key.toString());
        builder.append('\n');

        // traverse left tree
        printTreeHelper(node.left, builder, indent + 1, false, true);

        // traverse right tree
        printTreeHelper(node.right, builder, indent + 1, false, false);
    }

    // Time complexity: O(n)
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        printTreeHelper(mRoot, builder, 0, true, false); // O(n)
        return builder.toString();
    }

}
