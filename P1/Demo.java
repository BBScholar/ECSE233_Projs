
import java.util.Iterator;

public class Demo {

    static void printHeader() {
        System.out.println("---------------------------------");
        System.out.println("|          Homework #1          |");
        System.out.println("|-------------------------------|");
        System.out.println("| Name:      Benjamin Scholar   |");
        System.out.println("| CaseId:               bbs27   |");
        System.out.println("| Class:             CSDS 233   |");
        System.out.println("| Date:               2/10/21   |");
        System.out.println("---------------------------------\n\n");
    }

    // this has a runtime complexity of O(a*b), where a and b represent the lengths of phonebooks a and b
    // O(n^2) if the phonebooks have the same number of elements, n
    static void removeDuplicates(PhoneBook a, PhoneBook b) {

        // allocate variable here to avoid making excess function calls within the loop
        // unclear if java would do this small optimization automatically
        Person outer;

        // loop through every element in phonebook a
        for(int i = 0; i < a.size(); i++) {

            // get current element of phonebook a
            // 1 function call per iteration
            outer = a.lookup(i);

            // loop through every element in phonebook b
            // do not increment loop variable automatically
            for(int ii = 0; ii < b.size();) {

                // if phonebook b contains the same element as phonebook a, remove that
                // element from b.
                // only increment the loop variable if we do not find a duplicate on this iteration
                // otherwise we will get index out of bounds errors
                if(outer.getName().equals(b.lookup(ii).getName()))  {
                    b.remove(ii);
                    // if theres guranteed to be only one duplicate, we could break here to
                    // save some time, but problem is unclear

                    // break;
                } else ++ii; 
                
            }
        }
        
        // this implementation would work and is probably faster due to the optimized standard libraries
        // but im pretty sure this is against the essence of the project since it doesnt use the interface methods
        /*
        a.iterator().forEachRemaining((val) -> {
            Iterator<Person> inner = b.iterator();
            while(inner.hasNext()) {
                if(val.equals(inner.next())) inner.remove();
            }
        });
        */
    }


    public static void main(String... args) {
        // declare out phonebooks 
        PhoneBook linked = new PhBLinkedList();
        PhoneBook array = new PhBArrayList();

        // add elements to phonebooks

        // for the linked list implementation, we can minimize runtime by inserting elements at the front and back of the 
        // list, since inserting at the head and tail has a O(1) time complexity instead of O(n) in the middle. For simplicity sake, 
        // I will insert at the head of the collection
        linked.insert(0, new Person("James", "3451515151555", 420));
        linked.insert(0, new Person("Lando", "4385783495734", 4));
        linked.insert(0, new Person("Daniel", "823748923754", 3));
        linked.insert(0, new Person("Lance", "232382387423", 18));
        linked.insert(0, new Person("Sebastian", "9472829045", 5));
        linked.insert(0, new Person("Mattia", "4545128345", 69)); // <-- Same personId as christian below
    
        // for the arraylist implementation, we can minimize runtime by adding all elements to
        // the back of the list, since adding elements to the back has a runtime of O(1) instead of O(n) for inserting in the middle
        array.insert(0, new Person("James", "3451515151555", 420));
        array.insert(1, new Person("Valterri", "34343434", 77));
        array.insert(2, new Person("Lewis", "5843758943", 44));
        array.insert(3, new Person("Sebastian", "9472829045", 5));
        array.insert(4, new Person("Christian", "45438239", 69));
       
        printHeader();
        // print elements
        System.out.println(" --------- Before removeDuplicates -------");
        System.out.println("LinkedList version:");
        PhoneBook.print(linked);
        System.out.println("\nArrayList version:");
        PhoneBook.print(array);
        System.out.println("------------------------------------------");

        // remove duplicates
        removeDuplicates(linked, array);

        // print elements again
        System.out.println("\n");
        System.out.println(" --------- After removeDuplicates -------");
        System.out.println("LinkedList version:");
        PhoneBook.print(linked);
        System.out.println("\nArrayList version:");
        PhoneBook.print(array);
        System.out.println("------------------------------------------");
    }

}
