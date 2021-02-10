
public class Person {
    private String mName; 
    private String mPhoneNumber;
    private int mPersonId;

    public Person(String name, String phoneNumber, int id) {
        mName = name;
        mPhoneNumber = phoneNumber;
        mPersonId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public int getId() {
        return mPersonId;
    }

    public void setId(int id) {
        mPersonId = id;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mName == null) ? 0 : mName.hashCode());
        result = prime * result + mPersonId;
        result = prime * result + ((mPhoneNumber == null) ? 0 : mPhoneNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (mName == null) {
            if (other.mName != null)
                return false;
        } else if (!mName.equals(other.mName))
            return false;
        if (mPersonId != other.mPersonId)
            return false;
        if (mPhoneNumber == null) {
            if (other.mPhoneNumber != null)
                return false;
        } else if (!mPhoneNumber.equals(other.mPhoneNumber))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Person [Name=" + mName + ", Id=" + mPersonId + ", PhoneNumber=" + mPhoneNumber + "]";
    }

}
