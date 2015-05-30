package jordiarjan.databases.opdracht2.simulations;

import jordiarjan.databases.opdracht2.DBManager;
import jordiarjan.databases.opdracht2.ProductRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeadLock implements Simulation {

    private DBManager dbManager;

    private int stock;

    public void setup(DBManager dbManager) {
        this.dbManager = dbManager;
        Statement statement = null;
        try{
            Connection connection = dbManager.getConnection();
            statement = connection.createStatement();
            this.stock = 50;
            statement.execute("INSERT INTO product SET name=\"Test\", instock=\"" + this.stock + "\"");
            connection.commit();
        }
        catch(SQLException e )
        {
            e.printStackTrace();
        }
    }

    public void simulate(int threads) {
        for(int i = 0; i < threads; i++) {
            new Thread(new Runnable() {
                public void run() {
                    int id = 1;
                    DBManager dbManager = new DBManager();
                    try {
                        dbManager.getConnection().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    ProductRepository product = new ProductRepository(dbManager);
                    while (true) {
                        try {
                            int instock = product.getInStock(id);
                            product.updateInStock(id, instock + 1);
                            product.commit();
                        } catch (SQLException e){
                            System.out.println(e.getMessage());
                            System.exit(1);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
