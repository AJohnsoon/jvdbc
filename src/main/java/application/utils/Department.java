package src.main.java.application.utils;

import src.main.java.application.db.config.DB;
import src.main.java.application.db.exceptions.DbException;
import src.main.java.application.db.exceptions.DbIntegrityException;

import java.sql.*;

public class Department {
    public void selectData(Connection conn) throws SQLException {
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
        }

    }
    public void insertData(Connection conn){
        PreparedStatement preparedStatement = null;
        try{
            conn = DB.getConnection();
            preparedStatement = conn.prepareStatement(
                    "INSERT INTO department (Name)"
                            + "VALUE (?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "Computers");

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()){
                    int id = resultSet.getInt(1);
                    System.out.println("Done ! Rows affected: "+ id);
                }
            }
        }
        catch (SQLException e){
            e.getStackTrace();
        } finally {
            DB.closePreparedStatement(preparedStatement);
        }
    }
    public void updateData(Connection conn) throws SQLException {
        PreparedStatement preparedStatement = null;

        try{
            conn = DB.getConnection();
            preparedStatement = conn.prepareStatement("UPDATE department SET Name = ? WHERE (Name = ?)");
            preparedStatement.setString(1, "Technology");
            preparedStatement.setString(2, "Computers");

            int rowsAffect = preparedStatement.executeUpdate();
            System.out.println("Done ! Rows Affected: " + rowsAffect);
        }
        catch (SQLException e){
            e.getStackTrace();
        }
    }
    public void deleteData(Connection conn) throws SQLException {
        PreparedStatement preparedStatement = null;

        try{
            conn = DB.getConnection();
            preparedStatement = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
            preparedStatement.setInt(1,6);

            int rowsAffect = preparedStatement.executeUpdate();
            System.out.println("Done ! Rows Affected: " + rowsAffect);
        }
        catch (SQLException e){
            throw new DbIntegrityException(e.getMessage());
        }
    }
}
