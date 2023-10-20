
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class GetConnect {

    public static Connection getConnection() {
        Properties properties = new Properties();
        InputStream inputStream = GetConnect.class.getClassLoader().getResourceAsStream("JDBC-connection.properties");
        Connection conn = null;
        try {
            properties.load(inputStream);
            // Retrieve the database connection properties
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String db_password = properties.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, db_password);

        } catch (Exception e) {
            System.out.println("The Error  " + e.getMessage());
        }
        return conn;
    }

}
