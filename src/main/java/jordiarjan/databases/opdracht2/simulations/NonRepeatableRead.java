package jordiarjan.databases.opdracht2.simulations;

import jordiarjan.databases.opdracht2.DBManager;
import jordiarjan.databases.opdracht2.ProductRepository;

import java.sql.*;

public class NonRepeatableRead implements Simulation {

    private int stock;

    private DBManager dbManager;

    public void setup(DBManager manager) {
        this.dbManager = manager;
        Statement statement = null;
        try {
            Connection connection = manager.getConnection();
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

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    ProductRepository product = new ProductRepository(dbManager);
                    try {
                        int stock = product.getInStock(id);
                        System.out.println("Current stock: " + stock);
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    ProductRepository product = new ProductRepository(dbManager);
                    try {
                        int instock = product.getInStock(id);
                        int mutation = (int) (Math.random() * 10);
                        product.updateInStock(id, instock + mutation);
                        updateRealStock(mutation);
                        product.commit();

                        System.out.println("update for stock: " + (instock + mutation));
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    ProductRepository product = new ProductRepository(dbManager);
                    try {
                        Thread.sleep(200);
                        int stock = product.getInStock(id);
                        System.out.println("Current stock:(update) " + stock);
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public synchronized void updateRealStock(int mutation) {
        this.stock += mutation;
    }
}