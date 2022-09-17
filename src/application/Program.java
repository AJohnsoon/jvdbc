package src.application;

import src.application.db.config.DB;
import src.application.db.exceptions.DbException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main(String[] args) throws SQLException {

            Connection conn = null;
            Statement statement = null;
            ResultSet resultSet = null;

            try{
                conn = DB.getConnection();
                statement = conn.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM department");

                while (resultSet.next()){
                    System.out.println(resultSet.getInt("Id") + ", " + resultSet.getString("Name"));
                }

            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
            finally {

                DB.closeResults(resultSet);
                DB.closeStatement(statement);
                DB.closeConnection();

            }
    }
}
