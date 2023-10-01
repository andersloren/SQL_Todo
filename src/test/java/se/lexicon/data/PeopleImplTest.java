package se.lexicon.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.lexicon.model.Person;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class PeopleImplTest {

    private PeopleImpl testObject;

    @BeforeEach
    public void setup() {
        testObject = new PeopleImpl();
    }

    @Test
    @DisplayName("Checks if Person object passed is created in person table")
    void testCreate() {
        Person johnBlund = new Person(null, "John", "Blund");
        Person personCreated;

        personCreated = testObject.create(johnBlund);

        assertEquals(johnBlund, personCreated);

        System.out.println(johnBlund.toString());
        System.out.println(personCreated.toString());
    }

    @Test
    @DisplayName("Returns list of all Person objects from the person table")
    void testFindAll() {
        Collection<Person> allPeople = testObject.findAll();
        allPeople.forEach(System.out::println);
    }

    @Test
    @DisplayName("Should find Person object by ID in person table")
    void testFindBydId() {
        Person johnWick = new Person(null, "John", "Wick");
        Person personCreated;
        personCreated = testObject.create(johnWick);

        johnWick = testObject.findById(personCreated.getId());

        System.out.println(personCreated.getId());
        System.out.println(johnWick.getId());

        assertEquals(personCreated.getId(), johnWick.getId());
    }

    @Test
    @DisplayName("Should find Person object by ID in person table")
    void testFindBydIdHardcoded() {
        Person person = testObject.findById(100);
        System.out.println(person.toString());
    }

    @Test
    @DisplayName("Should find Person object by full name in person table")
    void testFindByName() {
        Person johnWick = new Person(null, "John", "Wick");
        johnWick = testObject.create(johnWick);

        Collection<Person> people = testObject.findByName("John Wick");

        people.forEach(System.out::println);

        assertFalse(people.isEmpty());
    }

    @Test
    @DisplayName("Should update Person object in person table")
    void testUpdate() {
        Person johnWick = new Person(null, "John", "Wick");
        johnWick = testObject.create(johnWick);

        Person updatedJohnWick = new Person(johnWick.getId(), "Anders", "Loren");
        Person updatedPerson = testObject.update(updatedJohnWick);

        System.out.println(johnWick.toString());
        System.out.println(updatedJohnWick.toString());

        assertEquals(johnWick.getId(), updatedPerson.getId());
    }

    @Test
    @DisplayName("Should delete the Person object passed in from the person table")
    void testDeleteById() {
        Person johnDoe = new Person(null, "John", "Doe");
        johnDoe = testObject.create(johnDoe);

        Collection<Person> people;
        people = testObject.findByName("John Doe");

        people.forEach(System.out::println);

        assertTrue(testObject.deleteById(johnDoe.getId()));

        people = testObject.findByName("John Doe");
        if (!people.isEmpty()) {
            System.out.println("Here are all the John Doe in the todo_item table:");
            people.forEach(System.out::println);
        } else {
            System.out.println("There is no longer a John Doe in the table");
        }
    }
}