package jordiarjan.databases.opdracht2;

import jordiarjan.databases.opdracht2.simulations.DirtyRead;

public class StockChange {

    public static void main(String args[]) {
        final DirtyRead dirtyRead = new DirtyRead();
        dirtyRead.setup(new DBManager());
        dirtyRead.simulate(10);
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        int real = dirtyRead.getReal();
                        int actual = dirtyRead.getActual();
                        System.out.println("Actual: " + actual + " Real: " + real);
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
