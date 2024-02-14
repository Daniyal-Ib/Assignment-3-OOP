import java.sql.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3306/Volleyball_markets";
        ResultSet res = null;
        Connection con = null;
        Statement stat = null;
        //SQL запросы
        String sqlCreate = "CREATE TABLE Volleyball_markets (Id INT PRIMARY KEY, ProductName VARCHAR(30), Type VARCHAR(30), Count INT, Price INT)";
        String sqlAdd = "INSERT INTO Volleyball_markets(Id, ProductName, Type, Count, Price) VALUES " +
                "(1,'Asics Metarise', 'Shoes', 3, 99000)," +
                "(2,'Mikasa v300w', 'Ball', 7, 36000), " +
                "(3,'Sport Costume Errea', 'Costume', 9, 40000), " +
                "(4,'Volleyball net KV.REZAC','Net',2,160000)";

        String sqlUpdate = "UPDATE Volleyball_markets SET Price = 50000 WHERE ProductName = 'Mikasa v300w'";
        String sqlDeleteOne = "DELETE FROM Volleyball_markets WHERE ProductName = 'Volleyball net KV.REZAC'";
        String sqlDelete = "DROP TABLE Volleyball_markets";
        String sqlWatch = "SELECT * FROM Volleyball_markets";
        try(Connection conn = DriverManager.getConnection(url, "root", "password")) {
            DatabaseMetaData metaData = conn.getMetaData();
            String tableName = "Volleyball_markets";
            Class.forName("com.mysql.cj.jdbc.Driver");

            ResultSet tables = metaData.getTables(null, null, tableName, null);
            if (tables.next()) {
                Statement statement = conn.createStatement();
                int n;
                do {
                    System.out.println("What do you want to do with table?: ");
                    System.out.println("1.Delete one string\n2.Update\n3.View all\n4.Delete table\n5.Exit");
                    n = scanner.nextInt();
                    switch (n){

                        case 1:
                            statement.executeUpdate(sqlDeleteOne);
                            System.out.println("Part of table deleted");
                            break;
                        case 2:
                            statement.executeUpdate(sqlUpdate);
                            System.out.println("Table Updated");
                            break;
                        case 3:
                            ResultSet resultSet2 = statement.executeQuery(sqlWatch);
                            while (resultSet2.next()) {
                                System.out.println(resultSet2.getInt("Id") + " " + resultSet2.getString("ProductName") + " " +
                                        resultSet2.getString("Type") + " " + resultSet2.getInt("Count") + " " + resultSet2.getInt("Price"));
                            }
                            break;
                        case 4:
                            statement.executeUpdate(sqlDelete);
                            System.out.println("Table deleted");
                            break;
                        case 5:
                            System.out.println("Exit from program");
                            break;
                        default:
                            System.out.println("Enter a number from 1 to 5");
                            break;
                    }
                } while (n != 5);
            } else {
                Statement statement = conn.createStatement();
                statement.executeUpdate(sqlCreate);
                System.out.println("Table created");
                statement.executeUpdate(sqlAdd);
                ResultSet resultSet3 = statement.executeQuery(sqlWatch);
                while (resultSet3.next()) {
                    System.out.println(resultSet3.getInt("Id") + " " + resultSet3.getString("ProductName") + " " +
                            resultSet3.getString("Type") + " " + resultSet3.getInt("Count") + " " +  resultSet3.getInt("Price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
