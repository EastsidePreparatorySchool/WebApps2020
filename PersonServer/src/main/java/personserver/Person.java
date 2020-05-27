package personserver;

public class Person {
    
    String firstName;
    String lastName;
    int age;
    String hairColor;
    
    Person (String f, String l, int a, String hc) {
        this.firstName = f;
        this.lastName = l;
        this.age = a;
        this.hairColor = hc;
    }
    
    
    // this is to make a nice String WITHOUT JSON
    @Override
    public String toString() {
        return "(Person:"+firstName+" "+lastName+",age:"+age+",haircolor:"+hairColor+")";
    }
}
