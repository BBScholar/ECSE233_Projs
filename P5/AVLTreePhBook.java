

class AVLTreePhBook {

    private AVLTree<String, Long> mNameKeyTree;
    private AVLTree<Long, String> mNumberKeyTree;

    public AVLTreePhBook() {
        mNameKeyTree = new AVLTree<>();
        mNumberKeyTree = new AVLTree<>();
    }

    // Time: O(logn)
    public boolean PhBInsert(final String name, final long number) {
        // ensure that the name and number are unique
        if(mNameKeyTree.search(name) == null && mNumberKeyTree.search(number) == null) { // O(logn) + O(logn) = O(logn)
            // if keys are unique, insert elments into both trees
            mNameKeyTree.insert(name, number); // O(logn)
            mNumberKeyTree.insert(number, name); // O(logn)
            return true;
        }
        return false;
    }

    // Time: O(logn)
    public boolean PhBDelete(final String name, final long number) {
        // check to see if this is a valid key, value pair within the tree
        // if it is, then we can delete the nodes from both trees
        final String res = mNumberKeyTree.search(number); // O(logn)
        if(res != null && res.equals(name)) {
            mNumberKeyTree.delete(number); // O(logn)
            mNameKeyTree.delete(name); // O(logn)
            return true;
        }
        return false;
    }


    // Time: O(logn)
    public Long PhBSearch(final String name) {
        // invoke search method from base AVLTree class, retursn null if nothing found
        return mNameKeyTree.search(name);
    }

    // Time: O(logn)
    public String PhBSearch(final long number) {
        // invoke search method from base AVLTree class, retursn null if nothing found
        return mNumberKeyTree.search(number);
    }


}
