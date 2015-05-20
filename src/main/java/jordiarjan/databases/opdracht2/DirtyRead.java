package jordiarjan.databases.opdracht2;

/**
 * Created by Arjan on 20/05/2015.
 */
public class DirtyRead implements Simulation {

    public void simulate() {
        final int id = 1;
        new Thread(new Runnable() {
            public void run() {
                ProductRepository product = new ProductRepository();

                while (true) {
                    try {
                        int instock = product.getInStock(id);
                        product.updateInStock(id, instock + (int) (Math.random() * 10));
                        Thread.sleep(200);
                        product.rollback();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                ProductRepository product = new ProductRepository();
                while (true) {
                    try {
                        int instock = product.getInStock(id);
                        System.out.println(instock);
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
