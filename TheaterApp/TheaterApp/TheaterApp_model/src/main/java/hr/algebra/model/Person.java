package hr.algebra.model;

import hr.algebra.exceptions.InvalidOibException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public abstract class Person<T extends Person<T>> extends BaseClass<T>{
    private static final Logger log = LoggerFactory.getLogger(Person.class);
    private static final int OIB_LENGTH = 11;

    private String firstName;
    private String lastName;
    private String oib;


    protected Person(String firstName, String lastName, String oib) {
        this(null,firstName,lastName,oib);
    }

    protected Person(Long id, String firstName, String lastName, String oib) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        validateOib(oib);
        this.oib = oib;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOib() {
        return oib;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setOib(String oib) throws InvalidOibException {
        try {
            validateOib(oib);
        } catch (InvalidOibException e) {
            log.warn("Rejected invalid OIB for {} {}: {}", firstName, lastName, e.getMessage());
            throw e;
        }
        this.oib = oib;
    }

    private static void validateOib(String oib) throws InvalidOibException {
        if (oib == null || oib.length() != OIB_LENGTH) {
            throw new InvalidOibException("OIB: " + oib + " must be " + OIB_LENGTH + " characters long");
        }
        for (char c : oib.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new InvalidOibException("OIB: " + oib + " must have only numbers");
            }
        }
    }

    @Override
    public String toString() {
        return firstName+' '+ lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person<?> person = (Person<?>) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(oib, person.oib);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, oib);
    }

    @Override
    public int compareTo(T other) {
        int compareByLastName = this.getLastName().compareTo(other.getLastName());
        if(compareByLastName!=0){
            return compareByLastName;
        }
        return this.getFirstName().compareTo(other.getFirstName());
    }
}

