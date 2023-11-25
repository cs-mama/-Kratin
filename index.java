import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

public class MedicationReminderApp {
    private static Connection connection;

    public static void main(String[] args) {
        // Connect to the SQLite database
        connectToDatabase();

        // User authentication (simplified for demonstration)
        String username = authenticateUser("Sunita", "password");

        if (username != null) {
            System.out.println("Welcome, " + username + "!");

            // Set up a medication reminder
            scheduleMedicationReminder(username, "Aspirin", 10); // Example medication every 10 seconds

            // Simulate the application running indefinitely
            while (true) {
                // Application logic goes here
            }
        } else {
            System.out.println("Authentication failed. Exiting.");
        }
    }

    private static void connectToDatabase() {
        try {
            // SQLite database connection
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:medication.db");
            createTable(); // Create a table if not exists
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT, password TEXT)";
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String authenticateUser(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void scheduleMedicationReminder(String username, String medication, int intervalSeconds) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(username + ", it's time to take your medication: " + medication);
            }
        }, 0, intervalSeconds * 1000);
    }
}
