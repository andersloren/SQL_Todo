package se.lexicon.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ToDoItemsImplTest {

    private ToDoItemsImpl testObject;

    @BeforeEach
    public void setup() {
        testObject = new ToDoItemsImpl();
    }

    @Test
    @DisplayName("Checks if TodoItem object passed is created in todo_item table")
    void testCreate() {
        Todo dishes = new Todo(null, "Dishes", "Do the dishes", LocalDate.of(2023, 9, 28), true, 1);
        Todo createdTodo = new Todo();
        createdTodo = testObject.create(dishes);

        assertEquals(dishes, createdTodo);

        System.out.println(dishes.toString());
        System.out.println(createdTodo.toString());
    }

    @Test
    @DisplayName("Checks if TodoItem object passed is created in todo_item table when assignee_id is NULL")
    void testCreateWithAssigneeIdSetToNull() {
        Todo dishes = new Todo(null, "Dishes", "Do the dishes", LocalDate.of(2023, 9, 28), true);
        System.out.println(dishes.toString());
        Todo createdTodo = new Todo();
        createdTodo = testObject.create(dishes);

        assertEquals(dishes, createdTodo);

        System.out.println(dishes.toString());
        System.out.println(createdTodo.toString());
    }

    @Test
    @DisplayName("Returns list of all Todo objects from the todo_item table")
    void testFindAll() {
        Collection<Todo> allTodoItems;
        allTodoItems = testObject.findAll();
        for (Todo todo : allTodoItems) {
            System.out.println(todo.toString());
        }
    }

    @Test
    @DisplayName("Should find Todo object by ID in todo_item table")
    void testFindById() {
        Todo walk = new Todo(null, "Go for a walk", "Take a much needed walk", LocalDate.of(2023, 9, 25), true, 2);
        Todo todoCreated;
        todoCreated = testObject.create(walk);

        walk = testObject.findById(todoCreated.getTodo_id());

        System.out.println(todoCreated.getTodo_id());
        System.out.println(walk.getTodo_id());
    }

    @Test
    @DisplayName("Should return all Todo objects that have status 'true' from todo_item table")
    void testFindByDoneStatus() {
        boolean status = true; // check for all objects that have done = true
        Collection<Todo> allDoneStatusIsTrue = testObject.findByDoneStatus(status);
        for (Todo todo : allDoneStatusIsTrue) {
            assertTrue(todo.isDone());
            System.out.println(todo.toString());
        }
    }

    @Test
    @DisplayName("Should return all Todo objects assigned to specific Person object")
    void testFindByAssigneeId() {
        Person person = new Person(null, "John", "Doe");

        int person_id = 2;
        Collection<Todo> allTodoByAssigneeId = testObject.findByAssignee(person_id);
        for (Todo todo : allTodoByAssigneeId) {
            assertEquals(person_id, todo.getAssignee_id());
            System.out.println(todo.toString());
        }
    }

    @Test
    @DisplayName("Should return all Todo objects assigned to specific Person object")
    void testFindByAssigneePerson() {
        int id = 1;
        PeopleImpl people = new PeopleImpl();
        Person person = new Person();
        person = people.findById(id);

        Collection<Todo> allTodoByAssigneeId = testObject.findByAssignee(person);
        for (Todo todo : allTodoByAssigneeId) {
            assertEquals(person.getId(), todo.getAssignee_id());
            System.out.println(todo.toString());
        }
    }

    @Test
    @DisplayName("Should return all Todo objects where assignee_id is set to null")
    void testFindByUnassignedTodoItems() {
        Todo playWithDog = new Todo(null, "Play with the dog", "Throw sticks", LocalDate.of(2023, 10, 4), false);
        Todo createdTodo = new Todo();
        createdTodo = testObject.create(playWithDog);

        Collection<Todo> allUnassignedItems = testObject.findByUnassignedTodoItems();
        for (Todo todo : allUnassignedItems) {
            assertEquals(0, todo.getAssignee_id());
            System.out.println(todo.toString());
            System.out.println(allUnassignedItems.size());
        } // TODO: 28/09/2023 This doesn't work! 
    }

    @Test
    @DisplayName("Should return updated Todo object")
    void testUpdate() {
        Todo talkWithCat = new Todo(null, "Talk with cat", "Say 'meow'", LocalDate.of(2023, 11, 6), false, 3);
        talkWithCat = testObject.create(talkWithCat);

        Todo updatedTalkWithCat = new Todo(talkWithCat.getTodo_id(), "Talk with both cats", talkWithCat.getDescription(), talkWithCat.getDeadline(), talkWithCat.isDone(), talkWithCat.getAssignee_id());

        testObject.update(updatedTalkWithCat);

        System.out.println(talkWithCat.toString());
        System.out.println(updatedTalkWithCat.toString());
    }

    @Test
    @DisplayName("Should delete Todo object based off passed todo_id")
    void testDelete() {
        Todo talkWithCat = new Todo(null, "Talk with cat", "Say 'meow'", LocalDate.of(2023, 11, 6), false, 3);
        talkWithCat = testObject.create(talkWithCat);
        System.out.println(talkWithCat.toString());

        assertTrue(testObject.deleteById(talkWithCat.getTodo_id()));
        testObject.findAll().forEach(System.out::println);

    }
}