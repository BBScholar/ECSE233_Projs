
import java.util.Optional;

class Main {
    
    // Time complexity: O(n) where n is the number of non-whitespace characters
    // in strings s1 and s2 given they have the same length.
    public static boolean anagrams(final String s1, final String s2) {
        
        // get the character arrays of both strings after removing
        // spaces and setting all characters to lowercase.
        // These are most likely O(n) operations(?)
        final char[] s1Chars = s1.replace(" ", "").toLowerCase().toCharArray();
        final char[] s2Chars = s2.replace(" ", "").toLowerCase().toCharArray();
    

        // If there are a different ammount of characters in each string,
        // then the strings cannot be anagrams
        // this saves us some CPU and allocations
        if(s1Chars.length != s2Chars.length)
            return false;
        
        // allocate the hashtable
        HashTable table = new HashTable();
        
        // for every character in the first string's array
        // add the character to the table if it doesnt exist yet,
        // else, increment the frequency of that character
        // O(n)
        for(final char c : s1Chars) { 
            Optional<HashTable.Entry> entry = table.search(c); // O(1) avg
            if(entry.isPresent()) {
                entry.get().incrementFrequency(); 
                continue;
            }
            table.insert(c); // O(n) worst
        }
    

        // next, for every character in the second string, 
        // search for that character in the table, if it doesnt exist as a key,
        // return false, else decrement the frequency and delete if the frequency is 0
        // O(n)
        for(final char c: s2Chars) {
            Optional<HashTable.Entry> entry = table.search(c); // O(1)
            if(entry.isEmpty()) return false;
            else {
                entry.get().decrementFrequency();

                if(entry.get().getFrequency() == 0) {
                    table.delete(c); // O(1) average, O(n) worst case
                }
            }
        }
        
        // return true if the table is empty, which signifies that they
        // are anagrams
        return table.isEmpty();
    }

    public static void main(final String... args) {
        if(args.length < 2)
            return;

        final String s1 = args[0];
        final String s2 = args[1];

        final boolean res = anagrams(s1, s2);

        System.out.println("Result: " + res);
        System.out.println("'" + s1 + "' and '" + s2 + "' are " + (res ? "" : "not ") + "anagrams");
    }


}
