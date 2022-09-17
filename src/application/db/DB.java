package src.application.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {

        if(connection == null){
            try{
                Properties properties = loadProperties();
                String url = properties.getProperty("dburl");
                connection = DriverManager.getConnection(url, properties);
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() throws SQLException{
        if(connection != null){
            try {
                connection.close();
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties(){
        try (FileInputStream fs = new FileInputStream("./src/resources/config/db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;
        }
        catch (IOException e){
            throw new DbException(e.getMessage());
        }
    }
}
