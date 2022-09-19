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

                System.out.println("* select into DB *");
                vDataDepartment.selectData(conn);

                System.out.println();

                System.out.println("* insert into DB *");
                vDataDepartment.insertData(conn);

                System.out.println();


                System.out.println("* Update into DB *");
                vDataDepartment.updateData(conn);

                System.out.println("______________________________________________________________");

                System.out.println();

                System.out.println("* Seller DATA *");

                System.out.println("* Select into DB *");
                vSellerData.selectData(conn);

                System.out.println();

                System.out.println("* Insert into DB *");
                vSellerData.insertData(conn);

                System.out.println();

                System.out.println("* Update into DB *");
                vSellerData.updateData(conn);
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
            finally {

                DB.closeConnection();

            }
    }
}
