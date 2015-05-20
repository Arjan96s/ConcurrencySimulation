package jordiarjan.databases.opdracht2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRepository {

    private DBManager dbManager;

    public ProductRepository(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public int getInStock(int id) throws Exception {
        PreparedStatement getInStock = null;
        getInStock = dbManager.getConnection().prepareStatement("SELECT instock FROM product WHERE id=?");
        getInStock.setInt(1, id);
        if (getInStock.execute()) {
            ResultSet result = getInStock.getResultSet();
            result.first();
            return result.getInt("instock");
        }
        throw new RuntimeException("Product not found");
    }

    public void updateInStock(int id, int instock) throws SQLException {
        PreparedStatement update = dbManager.getConnection().prepareStatement("UPDATE product SET instock=?");
        update.setInt(1, instock);
        update.execute();
    }

    public void rollback() {
        try {
            dbManager.getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            dbManager.getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
