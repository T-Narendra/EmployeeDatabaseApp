package IntershipTasks.EmployeeDatabaseApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class main {
    private static final String URL="jdbc:mysql://localhost:3306/employee_db";
    private static final String USER="root";
    private static final String password="Narendra@123";
    public static void main(String[] args) {
        try(Connection conn=DriverManager.getConnection(URL, USER, password)){
            Scanner s=new Scanner(System.in);
            while(true){
                System.out.println("------Employee Database------");
                System.out.println("1.Add Employee");
                System.out.println("2.View Employee");
                System.out.println("3.Update Employee");
                System.out.println("4.Delete Employee");
                System.out.println("5.Exit");
                System.out.println("Enter your choice:");
                int choice=s.nextInt();
                s.nextLine();
                switch(choice){
                    case 1:
                        addEmployee(conn,s);
                        break;
                    case 2:
                        viewEmployees(conn);
                        break;
                    case 3:
                        updateEmployee(conn,s);
                        break;
                    case 4:
                        deleteEmployee(conn,s);
                        break;
                    case 5:
                        System.out.println("Exiting......");
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();;
        }
    }
     private static void addEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter position: ");
        String position = sc.nextLine();
        System.out.print("Enter salary: ");
        double salary = sc.nextDouble();

        String sql = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, position);
            stmt.setDouble(3, salary);
            stmt.executeUpdate();
            System.out.println("Employee added successfully!");
        }
    }

    private static void viewEmployees(Connection conn) throws SQLException {
        String sql = "SELECT * FROM employees";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("\nID | Name | Position | Salary");
            System.out.println("--------------------------------");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %.2f%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary"));
            }
        }
    }
    private static void updateEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Employee ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new position: ");
        String position = sc.nextLine();
        System.out.print("Enter new salary: ");
        double salary = sc.nextDouble();

        String sql = "UPDATE employees SET position=?, salary=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, position);
            stmt.setDouble(2, salary);
            stmt.setInt(3, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Employee updated successfully!");
            else System.out.println("Employee not found.");
        }
    }

    private static void deleteEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Employee ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM employees WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Employee deleted successfully!");
            else System.out.println("Employee not found.");
        }
    }
}

