package jordiarjan.databases.opdracht2;

import jordiarjan.databases.opdracht2.simulations.DirtyRead;
import jordiarjan.databases.opdracht2.simulations.PhantomRead;

public class StockChange {

    public static void main(String args[]) {
        if (args.length <= 0)
            return;
        if (args[0].equals("phantomread"))
            StockChange.phantomRead();
        else if (args[0].equals("dirtyread"))
            StockChange.dirtyRead();
    }

    private static void dirtyRead() {
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

    private static void phantomRead() {
        final PhantomRead phantomRead = new PhantomRead();
        phantomRead.setup(new DBManager());
        phantomRead.simulate(10);
    }
}
