package se.lexicon.data;

import se.lexicon.db.MySQLConnection;
import se.lexicon.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PeopleImpl implements People {

    @Override
    public Person create(Person person) {
        String sql = "INSERT INTO person (first_name, last_name) VALUE (?, ?)";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating person failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    person.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating person failed, no ID obtained.");
                }
            }

            return person;

        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Person> findAll() {
        String sql = "SELECT * FROM person";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<Person> allPeople = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                Person person = new Person(id, firstName, lastName);
                allPeople.add(person);
            }
            return allPeople;

        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Person findById(int id) {
        String sql = "SELECT * FROM person WHERE person_id = ?";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Person person = new Person();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                person = new Person(id, firstName, lastName);
            }
            return person;
        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Collection<Person> findByName(String name) {
        String sql = "SELECT * FROM person WHERE CONCAT(first_name, ' ', last_name) = ?";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<Person> people = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                Person person = new Person(id, firstName, lastName);
                people.add(person);
            }
            return people;

        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Person update(Person person) {
        String sql = "UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setInt(3, person.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating person failed, no rows affected.");
            } else {
                System.out.println("You have successfully updated the Person object to this name: " + person.getFirstName() + " " + person.getLastName());
                return person;
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM person WHERE person_id = ?";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting person failed, no rows affected");
            }
            System.out.println("You successfully removed Person object with person_id " + id );
            return true;

        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
            return false;
        }
    }
}
