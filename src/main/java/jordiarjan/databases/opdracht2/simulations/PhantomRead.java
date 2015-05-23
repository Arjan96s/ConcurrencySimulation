package jordiarjan.databases.opdracht2.simulations;

import jordiarjan.databases.opdracht2.DBManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PhantomRead implements Simulation {

    private DBManager dbManager;

    public void setup(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void simulate(int threads) {
        for (int i = 0; i < threads; i++) {
            new Thread(new Runnable() {
                public void run() {
                    DBManager dbManager = new DBManager();
                    while (true) {
                        try {
                            int countBefore = countProducts();
                            insertNewProduct();
                            int countAfter = countProducts() - 1;
                            if (countBefore != countAfter)
                                System.out.println("Count before: " + countBefore + " count after: " + countAfter);
                            dbManager.getConnection().commit();
                            Thread.sleep((int) (Math.random() * 1000));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    protected void insertNewProduct() throws SQLException {
        PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement("INSERT INTO product (name, instock) VALUES (?,?)");
        preparedStatement.setString(1, "test");
        preparedStatement.setInt(2, (int) (Math.random() * 100));
        preparedStatement.execute();
    }

    protected int countProducts() throws SQLException {
        Statement statement = dbManager.getConnection().createStatement();
        statement.execute("SELECT count(*) FROM product");
        ResultSet result = statement.getResultSet();
        result.first();
        return result.getInt(1);
    }
}
