package jordiarjan.databases.opdracht2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private Connection connection;

    public DBManager() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://url:3306/db", "user", "pass");
            this.connection.setAutoCommit(false);
            this.connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
