package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.*;

public class DBUtil {
    private static final String CONFIG_FILE = "config.txt";

    public static Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE))) {
            props.load(reader);
        }

        String url = props.getProperty("db.url");
        return DriverManager.getConnection(url);
    }
}
