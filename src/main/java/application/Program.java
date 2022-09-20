package src.main.java.application;

import src.main.java.application.db.config.DB;
import src.main.java.application.db.exceptions.DbException;
import src.main.java.application.utils.Department;
import src.main.java.application.utils.Seller;

import java.sql.Connection;
import java.sql.SQLException;

public class Program {
    public static final Connection conn = null;

    public static void main(String[] args) throws SQLException {
        Department vDataDepartment = new Department();
        Seller vSellerData = new Seller();

            try{

                System.out.println("* Department DATA *");
                vDataDepartment.selectData(conn);
                vDataDepartment.insertData(conn);
                vDataDepartment.updateData(conn);
                vDataDepartment.deleteData(conn);

                System.out.println("______________________________________________________________");

                System.out.println("* Seller DATA *");
                vSellerData.selectData(conn);
                vSellerData.insertData(conn);
                vSellerData.updateData(conn);
                vSellerData.deleteData(conn);
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
            finally {

                DB.closeConnection();

            }
    }
}
