package oop.model;

import java.util.Objects;

public class Person {
    private String fName;
    private String sName;

    public Person(String fName, String sName) {
        this.fName = Objects.requireNonNull(fName, "fName must not be null");
        this.sName = Objects.requireNonNull(sName, "sName must not be null");
    }

    public String getFName() {
        return fName;
    }

    public String getSName() {
        return sName;
    }

    @Override
    public int hashCode() {
        return fName.hashCode() * sName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            Person person = (Person) obj;
            return sName.equals(person.sName)
                    && fName.equals(person.fName);
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("%s %s", fName, sName);
    }
}
