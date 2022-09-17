package src.application;

import src.application.db.config.DB;
import src.application.db.exceptions.DbException;
import src.application.utils.Department;
import src.application.utils.Seller;

import java.sql.Connection;
import java.sql.SQLException;

public class Program {
    public static final Connection conn = null;

    public static void main(String[] args) throws SQLException {

            try{
                Department vDataDepartament = new Department();
                Seller vSellerData = new Seller();

                System.out.println("* Department DATA *");
                vDataDepartament.selectData(conn);

                System.out.println();

                System.out.println("* Department SELLER *");
                vSellerData.selectData(conn);

                System.out.println();

                System.out.println("* Insert into DB *");
                vSellerData.insertData(conn);


            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }
            finally {

                DB.closeConnection();

            }
    }
}
