
import java.util.Iterator;

public interface PhoneBook extends Iterable<Person> {
    int size();
    void insert(int i, Person p);
    Person remove(int i);

    Person lookup(int i);

    static void print(final PhoneBook phb) {
        // a nice little lambda/consumer method
        phb.iterator().forEachRemaining((person) -> System.out.println(person.toString()));
    }
}
