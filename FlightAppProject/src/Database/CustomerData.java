package Database;

import java.sql.*;

public class CustomerData {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/CustomerData";
    private static final String USER = "root";
    private static final String PASSWORD = "Ionicwave805.";

    // checks if password and username are correct
    public boolean pass(String user, String pass) {
        String sql = "select * from customer where username = ? and password = ?";

        try (
            Connection myConn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            PreparedStatement myStmt = myConn.prepareStatement(sql);
        ) {
            myStmt.setString(1, user);
            myStmt.setString(2, pass);

            try (ResultSet myRs = myStmt.executeQuery()) {
                return myRs.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // checks username and security answer
    public boolean checkUserName(String user, String securityAnswer) {
        String sql = "select * from customer where username = ? and securityAnswer = ?";

        try (
            Connection myConn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            PreparedStatement myStmt = myConn.prepareStatement(sql);
        ) {
            myStmt.setString(1, user);
            myStmt.setString(2, securityAnswer);

            try (ResultSet myRs = myStmt.executeQuery()) {
                return myRs.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // returns pass when called
    public String getPass(String user) {
        String pass = null;
        String sql = "select * from customer where username = ?";

        try (
            Connection myConn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            PreparedStatement myStmt = myConn.prepareStatement(sql);
        ) {
            myStmt.setString(1, user);

            try (ResultSet myRs = myStmt.executeQuery()) {
                while (myRs.next()) {
                    pass = myRs.getString("password");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return pass;
    }

    // register is called then the user's information is updated into the database
    public void register(String firstName, String lastName, String email,
            String address, String username, String password,
            String ssn, String securityAnswer, String zipcode,
            String state, String birthday) {
String sql = "INSERT INTO customer (customerID, firstName, lastName, email, address, username, password, SSN, securityAnswer, zipcode, state, birthday) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

try (
Connection myConn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
PreparedStatement myStmt = myConn.prepareStatement(sql);
) {
myStmt.setInt(1, 0);
myStmt.setString(2, firstName);
myStmt.setString(3, lastName);
myStmt.setString(4, email);
myStmt.setString(5, address);
myStmt.setString(6, username);
myStmt.setString(7, password);
myStmt.setString(8, ssn);
myStmt.setString(9, securityAnswer);
myStmt.setString(10, zipcode);
myStmt.setString(11, state);

// Set birthday if available, or set it to null
if (birthday != null) {
   myStmt.setString(12, birthday);
} else {
   myStmt.setNull(12, Types.DATE);
}

myStmt.executeUpdate();
} catch (SQLException ex) {
ex.printStackTrace();
}
    }

    // checks for preexisting username
    public boolean uniqueUser(String username) {
        String sql = "select * from customer where username = ?";

        try (
            Connection myConn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            PreparedStatement myStmt = myConn.prepareStatement(sql);
        ) {
            myStmt.setString(1, username);

            try (ResultSet myRs = myStmt.executeQuery()) {
                return !myRs.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // retrieve customer id
    public int custID(String user) {
        int custID = 0;
        String sql = "select * from customer where username = ?";

        try (
            Connection myConn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            PreparedStatement myStmt = myConn.prepareStatement(sql);
        ) {
            myStmt.setString(1, user);

            try (ResultSet myRs = myStmt.executeQuery()) {
                while (myRs.next()) {
                    custID = myRs.getInt("customerID");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return custID;
    }
    
    public String getPass(String user, String securityAnswer) {
        String pass = null;
        String sql = "select * from customer where username = ? and securityAnswer = ?";

        try (
            Connection myConn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            PreparedStatement myStmt = myConn.prepareStatement(sql);
        ) {
            myStmt.setString(1, user);
            myStmt.setString(2, securityAnswer);

            try (ResultSet myRs = myStmt.executeQuery()) {
                if (myRs.next()) {
                    pass = myRs.getString("password");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return pass;

}
    
    public void updatePassword(String username, String newPassword) {
        String sql = "UPDATE customer SET password = ? WHERE username = ?";

        try (
            Connection myConn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            PreparedStatement myStmt = myConn.prepareStatement(sql);
        ) {
            myStmt.setString(1, newPassword);
            myStmt.setString(2, username);

            myStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
}
    
}
