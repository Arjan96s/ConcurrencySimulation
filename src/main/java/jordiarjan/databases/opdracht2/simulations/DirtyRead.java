package jordiarjan.databases.opdracht2.simulations;

import jordiarjan.databases.opdracht2.DBManager;
import jordiarjan.databases.opdracht2.ProductRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
                            Thread.sleep((int) (Math.random() * 1000));
                            int instock2 = product.getInStock(id);
                            if (instock != instock2)
                                System.out.println("Dirtyread occured!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            new Thread(new Runnable() {
                public void run() {
                    DBManager dbManager = new DBManager();
                    ProductRepository product = new ProductRepository(dbManager);
                    while (true) {
                        try {
                            int instock = product.getInStock(id);
                            int mutation = (int) (Math.random() * 10);
                            product.updateInStock(id, instock + mutation);
                            product.rollback();
                            Thread.sleep((int) (Math.random() * 1000));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
