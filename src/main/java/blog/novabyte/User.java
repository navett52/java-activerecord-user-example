package blog.novabyte;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private long userid;
    private String fname;
    private String name;
    private Timestamp signupdate;

    public User(long userid, String fname, String name, Timestamp signupdate) {
        this.userid = userid;
        this.fname = fname;
        this.name = name;
        this.signupdate = signupdate;
    }

    public String toString() {
        return this.userid + " | " + this.fname + " " + this.name;
    }

    /**
     * Gets an array of all users.
     *
     * @param conn the connection to use to select the users.
     * @return an array of all users in the database.
     * @throws SQLException if the database was unable to complete the query.
     */
    public static User[] getAllUsers(Connection conn) {
        ArrayList<User> userList = new ArrayList<>();

        try {
            ResultSet results = conn.createStatement().executeQuery("SELECT * FROM users;");

            while (results.next()) {
                long id = results.getLong("userid");
                String fname = results.getString("fname");
                String name = results.getString("name");
                Timestamp signupdate = results.getTimestamp("signupdate");

                userList.add(new User(id, fname, name, signupdate));
            }

            return userList.toArray(new User[0]);

        } catch (SQLException e) {
            System.out.println("getAllUsers query failed!");
            e.printStackTrace();
        }

        return new User[0]; // Return an empty array in case of failure
    }

    /**
     * Update the current user with any new values assigned to it.
     *
     * @param conn the connection to use to update the user.
     * @return true if a single row was updated, false otherwise.
     * @throws SQLException if the database was unable to complete the query.
     */
    public boolean updateUser(Connection conn) {
        try {
            String query = "UPDATE users SET fname = ?, \"name\" = ? WHERE userid = ?;";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, this.fname);
            statement.setString(2, this.name);
            statement.setLong(3, this.userid);

            System.out.println(statement.toString());

            int rowsChanged = statement.executeUpdate();
            return rowsChanged == 1;
        } catch (Exception e) {
            System.out.println("updateUser query failed!");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Gets a user based on the passed userid.
     *
     * @param conn   the connection to use to get the user.
     * @param userid the id of the user to get.
     * @return the user that matches the passed userid. If no user is found
     *         a message is logged with the passed userid and null is returned.
     * @throws SQLException if the database was unable to complete the query.
     */
    public User getUser(Connection conn, long userid) {
        try {
            String query = "SELECT * FROM users WHERE userid = ?;";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, userid);

            ResultSet results = statement.executeQuery();
            if (results.next()) { // Check if a record is returned
                long id = results.getLong("userid");
                String fname = results.getString("fname");
                String name = results.getString("name");
                Timestamp signupdate = results.getTimestamp("signupdate");

                return new User(id, fname, name, signupdate);
            } else {
                System.out.println("No user found with userid: " + userid);
            }
        } catch (Exception e) {
            System.out.println("getUser query failed!");
            e.printStackTrace();
        }

        return null;
    }

    public long getUserid() {
        return this.userid;
    }

    public String getFname() {
        return this.fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp get() {
        return this.signupdate;
    }
}
