package se.lexicon.data;

import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.util.Collection;

public interface ToDoItems {
    Todo create(Todo todo);

    Collection<Todo> findAll();

    Todo findById(int id);

    Collection<Todo> findByDoneStatus(boolean status);

    Collection<Todo> findByAssignee(int person_id);

    Collection<Todo> findByAssignee(Person person);

    Collection<Todo> findByUnassignedTodoItems();

    Todo update(Todo todo);

    boolean deleteById(int id);

}
