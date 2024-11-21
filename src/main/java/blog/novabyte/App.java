package blog.novabyte;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Hello world!
 */
public final class App {

    /**
     * Says hello to the world.
     *
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/mydb";
        String user = "postgres";
        String password = "postgres";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the PostgreSQL server successfully.");

            User[] users = User.getAllUsers(conn);

            for (User u : users) {
                System.out.println(u.toString());
            }

            User selectedUser = users[0].getUser(conn, 1);
            System.out.println(selectedUser.toString());

            selectedUser.setFname("Bobbly");
            boolean success = selectedUser.updateUser(conn);
            System.out.println(success);
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }
}
