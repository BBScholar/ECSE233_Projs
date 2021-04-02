
import java.util.Optional;

class Main {

    public static boolean anagrams(final String s1, final String s2) {
        HashTable table = new HashTable();

        final char[] s1Chars = s1.replace(" ", "").toLowerCase().toCharArray();
        final char[] s2Chars = s2.replace(" ", "").toLowerCase().toCharArray();

        for(final char c : s1Chars) {
            Optional<HashTable.Entry> entry = table.search(c);
            if(entry.isPresent()) {
                entry.get().frequency++; 
                continue;
            }
            table.insert(c);
        }

        for(final char c: s2Chars) {
            Optional<HashTable.Entry> entry = table.search(c);
            if(entry.isEmpty()) return false;
            else {
                entry.get().frequency--;

                if(entry.get().frequency == 0) {
                    table.delete(c);
                }
            }
        }
        
        return table.isEmpty();
    }

    public static void main(String... args) {
        String s1 = "cdeezs";
        String s2 = "eezd";
        System.out.println(anagrams(s1, s2));
    }


}
