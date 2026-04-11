package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Model.User;

public class DataBase
{
    // MySQL Connection Details
    static String conStr = "jdbc:mysql://localhost:3306/converter_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    static String user = "root";
    static String pass = "admin_ahmed_2006_dev!!%";

    // Constructor to load the driver
    public DataBase() 
    {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(conStr, user, pass);
            System.out.println("MySQL Driver Loaded");
        } catch (SQLException | ClassNotFoundException e) 
        {
            System.out.println("DataBase initialization: " + e.getMessage());
        } finally
        {
            if (con != null) 
            {
                try
                {
                    con.close();
                } catch (SQLException e) 
                {
                    e.printStackTrace();
                }
            }
        }
    }

    // To get connection
    private Connection getConnection() throws SQLException 
    {
        return DriverManager.getConnection(conStr, user, pass);
    }

    // Initialize tables
    public void initTables() 
    {
        String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "fname VARCHAR(50) NOT NULL," +
                "lname VARCHAR(50) NOT NULL," +
                "age INT CHECK (age > 0 AND age < 120)," +
                "username VARCHAR(100) UNIQUE NOT NULL," +
                "password VARCHAR(100) NOT NULL)";

        String historyTable = "CREATE TABLE IF NOT EXISTS conversion_history (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "user_id INT NOT NULL," +
                "input_value VARCHAR(50) NOT NULL," +
                "from_base INT NOT NULL," +
                "to_base INT NOT NULL," +
                "result VARCHAR(50) NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE)";

        try{
        	Connection con = getConnection(); 
        	Statement stmt = con.createStatement();
            stmt.execute(usersTable);
            stmt.execute(historyTable);
            System.out.println(" Database tables ready");
        } catch (SQLException e) 
        {
            System.err.println(" Table init note: " + e.getMessage());
        }
    }

    public boolean insertUser(User u) 
    {
        String sql = "INSERT INTO users (fname, lname, age, username, password) VALUES (?, ?, ?, ?, ?)";

        try{
        	Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, u.getFname());
            pstmt.setString(2, u.getLname());
            pstmt.setInt(3, u.getAge());
            pstmt.setString(4, u.getUserName());
            pstmt.setString(5, u.getPassword());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    public User ValidateUser(String username, String password) 
    {
        String sql = "SELECT fname, lname, age FROM users WHERE username = ? AND password = ?";

        try{
        	Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) 
            {
            	User u=new User();
            	u.setFname(rs.getString("fname"));
            	u.setLname(rs.getString("lname"));
            	u.setAge(rs.getInt("age"));
                u.setPassword(password);
                return u;
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // User not found
    }

    public static boolean testConnection() 
    {
        try{
        	Connection con = DriverManager.getConnection(conStr, user, pass);
            if (con != null && !con.isClosed()) 
            {
                System.out.println("DataBase Connected Successfully");
                return true;
            }
        } catch (SQLException e) 
        {
            System.out.println("DataBase Connection Failed!");
            e.printStackTrace();
        }
        return false;
    }
}