package src.application.utils;

import src.application.db.config.DB;
import src.application.db.exceptions.DbException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Seller {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public void selectData(Connection conn){
        Statement statement = null;
        ResultSet resultSet = null;
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
        PreparedStatement preparedStatement = null;
        try{
            conn = DB.getConnection();
            preparedStatement = conn.prepareStatement(
                    "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId)"
                    + "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "Marlon Red");
            preparedStatement.setString(2,"marlonred@teste.com");
            preparedStatement.setDate(3, new java.sql.Date(sdf.parse("19/03/1987").getTime()));
            preparedStatement.setDouble(4, 2000.0);
            preparedStatement.setInt(5, 4);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected >0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()){
                    int id = resultSet.getInt(1);
                    System.out.println("Done ! Rows affected: "+ id);
                }
            }
        }
        catch (SQLException | ParseException e){
            e.getStackTrace();
        } finally {
            DB.closePreparedStatement(preparedStatement);
        }
    }
}
