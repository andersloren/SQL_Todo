package se.lexicon.data;

import se.lexicon.db.MySQLConnection;
import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class ToDoItemsImpl implements ToDoItems {

    @Override
    public Todo create(Todo todo) {
        String sql = "INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUE (?, ?, ?, ?, ?)";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, todo.getTitle());
            preparedStatement.setString(2, todo.getDescription());
            java.sql.Date sqlDate = java.sql.Date.valueOf(todo.getDeadline()); // ChatGPT trick...
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setBoolean(4, todo.isDone());
            if (todo.getAssignee_id() == null) {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(5, todo.getAssignee_id());
            }

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating Todo object failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    todo.setTodo_id(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating Todo object failed, no ID obtained from MySQL.");
                }
            }
            return todo;

        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Todo> findAll() {
        String sql = "SELECT * FROM todo_item";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<Todo> allTodoItems = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                java.sql.Date deadlineSql = resultSet.getDate(4); // sql Date to java.sql.Date
                LocalDate deadline = deadlineSql.toLocalDate(); // java.sql.Date to java.time.LocalDate
                int doneFromDb = resultSet.getInt(5); // getInt
                boolean done = doneFromDb == 1; // convert int to boolean
                Integer assignee_id = resultSet.getInt(6);
                Todo todo = new Todo(id, title, description, deadline, done, assignee_id);
                allTodoItems.add(todo);
            }
            System.out.println("You successfully retrieved all Todo objects!");
            return allTodoItems;

        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Todo findById(int todo_id) {
        String sql = "SELECT * FROM todo_item WHERE todo_id = ?";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, todo_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Todo todo = new Todo();
            while (resultSet.next()) {
                todo_id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                java.sql.Date deadlineSql = resultSet.getDate(4); // sql Date to java.sql.Date
                LocalDate deadline = deadlineSql.toLocalDate(); // java.sql.Date to java.time.LocalDate
                int doneFromDb = resultSet.getInt(5); // getInt
                boolean done = doneFromDb == 1; // convert int to boolean
                Integer assignee_id = resultSet.getInt(6);
                todo = new Todo(todo_id, title, description, deadline, done, assignee_id);
            }
            System.out.println("You successfully retrieved a Todo object with todo_id " + todo_id);
            return todo;
        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Todo> findByDoneStatus(boolean status) {
        String sql = "SELECT * FROM todo_item WHERE done = ?";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setBoolean(1, status);

            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<Todo> allTodoItems = new ArrayList<>();
            while (resultSet.next()) {
                int todo_id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                java.sql.Date deadlineSql = resultSet.getDate(4); // sql Date to java.sql.Date
                LocalDate deadline = deadlineSql.toLocalDate(); // java.sql.Date to java.time.LocalDate
                boolean done = resultSet.getBoolean(5);
                Integer assignee_id = resultSet.getInt(6);
                Todo todo = new Todo();
                todo = new Todo(todo_id, title, description, deadline, done, assignee_id);
                allTodoItems.add(todo);
            }
            System.out.println("You successfully retrieved all Todo objects whose status is set do done!");
            return allTodoItems;
        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Todo> findByAssignee(int person_id) {
        String sql = "SELECT * FROM todo_item WHERE assignee_id = ?";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, person_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<Todo> allTodoItems = new ArrayList<>();
            while (resultSet.next()) {
                int todo_id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                java.sql.Date deadlineSql = resultSet.getDate(4); // sql Date to java.sql.Date
                LocalDate deadline = deadlineSql.toLocalDate(); // java.sql.Date to java.time.LocalDate
                int doneFromDb = resultSet.getInt(5); // getInt
                boolean done = doneFromDb == 1; // convert int to boolean
                Integer assignee_id = resultSet.getInt(6);
                Todo todo = new Todo(todo_id, title, description, deadline, done, assignee_id);
                allTodoItems.add(todo);
            }
            System.out.println("You successfully retrieved all Todo objects assigned to person_id " + person_id);
            return allTodoItems;

        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Todo> findByAssignee(Person person) {
        String sql = "SELECT * FROM todo_item WHERE assignee_id = ?";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, person.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<Todo> allTodoItems = new ArrayList<>();
            while (resultSet.next()) {
                int todo_id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                java.sql.Date deadlineSql = resultSet.getDate(4); // sql Date to java.sql.Date
                LocalDate deadline = deadlineSql.toLocalDate(); // java.sql.Date to java.time.LocalDate
                int doneFromDb = resultSet.getInt(5); // getInt
                boolean done = doneFromDb == 1; // convert int to boolean
                Integer assignee_id = resultSet.getInt(6);
                Todo todo = new Todo(todo_id, title, description, deadline, done, assignee_id);
                allTodoItems.add(todo);
            }
            System.out.println("You successfully retrieved all Todo objects assigned to " + person.getFirstName() + " " + person.getLastName());
            return allTodoItems;

        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Todo> findByUnassignedTodoItems() {
        String sql = "SELECT * FROM todo_item WHERE assignee_id IS NULL";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<Todo> allTodoItems = new ArrayList<>();
            while (resultSet.next()) {
                int todo_id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                java.sql.Date deadlineSql = resultSet.getDate(4); // sql Date to java.sql.Date
                LocalDate deadline = deadlineSql.toLocalDate(); // java.sql.Date to java.time.LocalDate
                int doneFromDb = resultSet.getInt(5); // getInt
                boolean done = doneFromDb == 1; // convert int to boolean
                Integer assignee_id = resultSet.getInt(6);
                Todo todo = new Todo(todo_id, title, description, deadline, done, assignee_id);
                allTodoItems.add(todo);
            }
            System.out.println("You successfully retrieved all unassigned Todo objects!");
            return allTodoItems;

        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Todo update(Todo todo) {
        String sql = "UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, todo.getTitle());
            preparedStatement.setString(2, todo.getDescription());
            preparedStatement.setDate(3, java.sql.Date.valueOf(todo.getDeadline()));
            preparedStatement.setBoolean(4, todo.isDone());
            preparedStatement.setInt(5, todo.getAssignee_id());
            preparedStatement.setInt(6, todo.getTodo_id());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating Todo object failed, no rows affected.");
            } else {
                System.out.println("You successfully updated the Todo object to this: " + todo.toString());
                return todo;
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM todo_item WHERE todo_id = ?";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting Todo object failed, no rows affected.");
            } else {
                System.out.println("You successfully deleted Todo object with todo_id " + id);
                return true;
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
            return false;
        }
    }

    // TODO: 27/09/2023 After each, clear table and create set of todo_items?  
}
