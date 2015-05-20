package jordiarjan.databases.opdracht2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Arjan on 20/05/2015.
 */
public class DBManager {

    private Connection connection;

    public DBManager() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:33060/opdracht2", "homestead", "secret");
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
