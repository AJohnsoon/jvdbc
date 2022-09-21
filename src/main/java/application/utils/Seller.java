package src.main.java.application.utils;

import src.main.java.application.db.config.DB;
import src.main.java.application.db.exceptions.DbException;
import src.main.java.application.db.exceptions.DbIntegrityException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Seller {
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public void selectData(Connection conn){
        try{
            conn = DB.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM seller");

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
        try{
            conn = DB.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(
                    "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId)"
                    + "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "Marlon Orange");
            preparedStatement.setString(2,"marlonred@teste.com");
            preparedStatement.setDate(3, new java.sql.Date(sdf.parse("19/03/1987").getTime()));
            preparedStatement.setDouble(4, 2000.0);
            preparedStatement.setInt(5, 4);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()){
                    int id = resultSet.getInt(1);
                    System.out.println("Done ! Rows affected: "+ id);
                }
            }
            conn.commit();
        }
        catch (SQLException | ParseException e){
            try{
                conn.rollback();
                throw new DbException("Transaction rollback "+ e.getMessage());
            }
            catch (SQLException rolledback){
                throw new DbException("Error trying to rollback " + rolledback.getMessage());
            }
        } finally {
            DB.closePreparedStatement(preparedStatement);
        }
    }
    public void updateData(Connection conn) throws SQLException {
        try{
            conn = DB.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement("UPDATE seller SET Name = ? WHERE (Name = ?)");
            preparedStatement.setString(1, "Marlon Orange");
            preparedStatement.setString(2, "Marlon Red");

            int rowsAffect = preparedStatement.executeUpdate();
            System.out.println("Done ! Rows Affected: " + rowsAffect);
            conn.commit();
        }
        catch (SQLException e){
            try{
                conn.rollback();
                throw new DbException("Transaction rollback "+ e.getMessage());
            }
            catch (SQLException rolledback){
                throw new DbException("Error trying to rollback " + rolledback.getMessage());
            }        }
    }
    public void deleteData(Connection conn) throws SQLException {
        try{
            conn = DB.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
            preparedStatement.setInt(1,9);

            int rowsAffect = preparedStatement.executeUpdate();
            System.out.println("Done ! Rows Affected: " + rowsAffect);
            conn.commit();
        }
        catch (SQLException e){
            try{
                conn.rollback();
                throw new DbIntegrityException("Transaction rolled back!! "+ e.getMessage());
            }
            catch (SQLException rolledback){
                throw new DbIntegrityException("Error trying to rollback" + rolledback.getMessage());
            }        }
    }
}
