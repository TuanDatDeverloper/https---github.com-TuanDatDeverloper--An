import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public void addEvent(String title, Timestamp startTime, Timestamp endTime, String description, String location, String repeatType, Integer reminderTime) {
        String query = "INSERT INTO events (title, start_time, end_time, description, location, repeat_type, reminder_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, title);
            statement.setTimestamp(2, startTime);
            statement.setTimestamp(3, endTime);
            statement.setString(4, description);
            statement.setString(5, location);
            statement.setString(6, repeatType);
            if (reminderTime != null) {
                statement.setInt(7, reminderTime);
            } else {
                statement.setNull(7, java.sql.Types.INTEGER);
            }
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getTimestamp("start_time"),
                        resultSet.getTimestamp("end_time"),
                        resultSet.getString("description"),
                        resultSet.getString("location"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getString("repeat_type"),
                        resultSet.getObject("reminder_time", Integer.class)
                );
                events.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public void updateEvent(int id, String title, Timestamp startTime, Timestamp endTime, String description, String location, String repeatType, Integer reminderTime) {
        String query = "UPDATE events SET title = ?, start_time = ?, end_time = ?, description = ?, location = ?, repeat_type = ?, reminder_time = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, title);
            statement.setTimestamp(2, startTime);
            statement.setTimestamp(3, endTime);
            statement.setString(4, description);
            statement.setString(5, location);
            statement.setString(6, repeatType);
            if (reminderTime != null) {
                statement.setInt(7, reminderTime);
            } else {
                statement.setNull(7, java.sql.Types.INTEGER);
            }
            statement.setInt(8, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEvent(int id) {
        String query = "DELETE FROM events WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add method to delete event by title
    public void deleteEventByTitle(String title) {
        String query = "DELETE FROM events WHERE title = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, title);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add method to search events by title or description
    public List<Event> searchEvents(String query) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE title LIKE ? OR description LIKE ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchQuery = "%" + query + "%";
            statement.setString(1, searchQuery);
            statement.setString(2, searchQuery);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Event event = new Event(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getTimestamp("start_time"),
                            resultSet.getTimestamp("end_time"),
                            resultSet.getString("description"),
                            resultSet.getString("location"),
                            resultSet.getTimestamp("created_at")
                    );
                    events.add(event);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}