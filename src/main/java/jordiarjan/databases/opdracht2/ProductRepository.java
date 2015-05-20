package jordiarjan.databases.opdracht2;

import java.sql.*;

public class ProductRepository {
    private Connection connection;

    public ProductRepository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:33060/opdracht2", "homestead", "secret");
            this.connection.setAutoCommit(false);
            this.connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getInStock(int id) throws Exception {
        PreparedStatement getInStock = null;
        getInStock = connection.prepareStatement("SELECT instock FROM products WHERE id=?");
        getInStock.setInt(1, id);
        if (getInStock.execute()) {
            ResultSet result = getInStock.getResultSet();
            result.first();
            return result.getInt("instock");
        }
        throw new RuntimeException("Product not found");
    }

    public void updateInStock(int id, int instock) throws SQLException {
        PreparedStatement update = connection.prepareStatement("UPDATE products SET instock=?");
        update.setInt(1, instock);
        update.execute();
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
