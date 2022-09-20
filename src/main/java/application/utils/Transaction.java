package src.main.java.application.utils;

import src.main.java.application.db.config.DB;
import src.main.java.application.db.exceptions.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction {

    public void transactionData(Connection conn) {
        try{
            conn = DB.getConnection();
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();
            int changeONE = statement.executeUpdate("UPDATE seller SET BaseSalary = 1090 WHERE DepartmentId = 5");

            int x = 1;
            //noinspection ConstantConditions
            if(x < 2){
                throw new SQLException("Fake error");
            }

            int changeTWO = statement.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 6");

            conn.commit();

            System.out.println("One: "+ changeONE);
            System.out.println("Two: "+ changeTWO);
        }
        catch (SQLException e){
            try{
                conn.rollback();
                throw new DbException("Transaction rolled back!! "+ e.getMessage());
            }
            catch (SQLException rolledback){
               throw new DbException("Error trying to rollback " + rolledback.getMessage());
            }
        }
    }
}
