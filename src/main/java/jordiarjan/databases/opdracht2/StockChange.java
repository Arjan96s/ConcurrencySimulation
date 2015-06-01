package jordiarjan.databases.opdracht2;

import jordiarjan.databases.opdracht2.simulations.DeadLock;
import jordiarjan.databases.opdracht2.simulations.DirtyRead;
import jordiarjan.databases.opdracht2.simulations.NonRepeatableRead;
import jordiarjan.databases.opdracht2.simulations.PhantomRead;

public class StockChange {

    public static void main(String args[]) {
        if (args.length <= 0)
            return;
        if (args[0].equals("phantomread"))
            StockChange.phantomRead();
        else if (args[0].equals("dirtyread"))
            StockChange.dirtyRead();
        else if (args[0].equals("deadlock"))
            StockChange.deadlock();
        else if (args[0].equals("nonrepeatableread"))
            StockChange.nonrepeatableRead();
    }

    private static void nonrepeatableRead()
    {
        final NonRepeatableRead nonRepeatableRead = new NonRepeatableRead();
        nonRepeatableRead.setup(new DBManager());
        nonRepeatableRead.simulate(10);
    }

    private static void dirtyRead() {
        final DirtyRead dirtyRead = new DirtyRead();
        dirtyRead.setup(new DBManager());
        dirtyRead.simulate(10);
    }

    private static void phantomRead() {
        final PhantomRead phantomRead = new PhantomRead();
        phantomRead.setup(new DBManager());
        phantomRead.simulate(10);
    }

    private static void deadlock() {
        final DeadLock deadLock = new DeadLock();
        deadLock.setup(new DBManager());
        deadLock.simulate(10);
    }
}
