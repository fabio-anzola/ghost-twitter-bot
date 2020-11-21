package at.anzola.gtposter;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bot_DB {
    public static void storeTwitterOauth(String[] oauth) throws SQLException {
        Statement s = Main.connection.createStatement();
        String sql = "INSERT INTO twitter_oauth (oauth1, oauth2) VALUES ('" + oauth[0] + "', '" + oauth[1] + "');";
        boolean rs = s.execute(sql);
    }

    public static String[] readTwitterOauth() throws SQLException {
        Statement s = Main.connection.createStatement();
        String sql = "SELECT oauth1, oauth2 FROM twitter_oauth;";
        ResultSet rs = s.executeQuery(sql);
        String[] auth = null;
        while (rs.next()) {
            auth = new String[]{rs.getString(1), rs.getString(2)};
        }
        return auth;
    }

    public static int connectdb() {
        try {
            String url;
            url = "jdbc:mysql://" + Main.HOSTNAME + '/' + Main.DATABASE + "user=" + Main.USERNAME + "&password=" + Main.PASSWORD;
            Main.connection = DriverManager.getConnection(url);
            System.out.println("Database connection established");
            return 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 1;
        }
    }

    public static boolean searchValue(String id) throws SQLException {
        boolean exists = false;
        Statement s = Main.connection.createStatement();
        String sql = "SELECT id FROM blog_id WHERE id='" + id + "';";
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            if ((rs.getString(1).equals((String) id))) {
                exists = true;
            }
        }
        return exists;
    }

    public static void putValue(String id) throws SQLException {
        Statement s = Main.connection.createStatement();
        String sql = "INSERT INTO blog_id (id) VALUES ('" + id + "');";
        boolean rs = s.execute(sql);
    }

    public static void clearValues() throws SQLException {
        Statement s = Main.connection.createStatement();
        String sql = "DELETE FROM blog_id WHERE TRUE;";
        boolean rs = s.execute(sql);
    }
}
