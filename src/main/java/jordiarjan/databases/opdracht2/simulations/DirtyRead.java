package jordiarjan.databases.opdracht2.simulations;

import jordiarjan.databases.opdracht2.DBManager;
import jordiarjan.databases.opdracht2.ProductRepository;

import java.sql.*;

public class DirtyRead implements Simulation {

    private int stock;

    private DBManager dbManager;

    public void setup(DBManager dbManager) {
        this.dbManager = dbManager;
        Statement statement = null;
        try {
            Connection connection = dbManager.getConnection();
            statement = connection.createStatement();
            this.stock = 50;
            statement.execute("INSERT INTO product SET name=\"Test\", instock=\"" + this.stock + "\"");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void simulate(int threads) {
        final int id = 1;
        for (int i = 0; i < threads; i++) {
            new Thread(new Runnable() {
                public void run() {
                    DBManager dbManager = new DBManager();
                    ProductRepository product = new ProductRepository(dbManager);
                    while (true) {
                        try {
                            int instock = product.getInStock(id);
                            int mutation = (int) (Math.random() * 10);
                            product.updateInStock(id, instock + mutation);
                            updateRealStock(mutation);
                            product.commit();
                            Thread.sleep((int) (Math.random() * 1000));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    public int getActual() throws Exception {
        PreparedStatement statement = dbManager.getConnection().prepareStatement("SELECT instock FROM product WHERE id=?");
        int id = 1;
        statement.setInt(1, id);
        if (statement.execute()) {
            ResultSet result = statement.getResultSet();
            result.first();
            return result.getInt("instock");
        }
        throw new Exception("Product not found");
    }

    public int getReal() {
        return this.stock;
    }

    public synchronized void updateRealStock(int mutation) {
        this.stock += mutation;
    }
}
