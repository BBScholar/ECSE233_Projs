

class Main {

    public static void main(final String... args) {
        System.out.println("Testing AVLTreePhBook...");

        // allocate phone book structure
        AVLTreePhBook pb = new AVLTreePhBook();
        
        // allocate data for testing
        boolean res;
        String name;
        Long number;
    

        // insert new k-v pairs, these should all be valid
        res = pb.PhBInsert("John", 10);
        res &= pb.PhBInsert("Max", 33);
        res &= pb.PhBInsert("Lewis", 44);
        res &= pb.PhBInsert("Sebastian",5);
        res &= pb.PhBInsert("Lando", 4);
        assert res;
        
        // attempt to insert a new value with a duplicate name field
        // this should not be allowed
        res = pb.PhBInsert("John", 4857);
        assert !res;
        // ensure that nothing was pushed to the trees
        name = pb.PhBSearch(4857); 
        assert name == null;
        
        // test insertion with duplicate number field, should not be allowed
        res = pb.PhBInsert("Jose", 4);
        assert !res;
        // ensure that the value was not inserted
        number = pb.PhBSearch("Jose");
        assert number == null;

        // test number search
        number = pb.PhBSearch("Lewis");
        assert number == 44;

        // test name search
        name = pb.PhBSearch(4);
        assert name.equals("Lando");

        // test deletion of valid name-number pair
        res = pb.PhBDelete("Sebastian", 5);
        assert res;
        // ensure that teh values are deleted from the tree afterwards
        name = pb.PhBSearch(5);
        number = pb.PhBSearch("Sebastian");
        assert name == null && number == null;
        
        // test deletion of invalid name-number pair (with only number invalid)
        res = pb.PhBDelete("Lewis", 69);
        assert !res;
        // ensure that both values are still present, since they should not have been deleted
        name = pb.PhBSearch(44);
        number = pb.PhBSearch("Lewis");
        assert name.equals("Lewis") && number == 44;
        
        // test deletion of invalid name-number pair (with both a duplicate name and number)
        res = pb.PhBDelete("George", 63);
        assert !res;
        // ensure that neither of the values are present after delete operation
        name = pb.PhBSearch(63);
        number = pb.PhBSearch("George");
        assert name == null && number == null;

        System.out.println("Success");
    }

}
