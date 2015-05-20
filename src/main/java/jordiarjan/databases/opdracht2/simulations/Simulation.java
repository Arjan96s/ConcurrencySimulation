package jordiarjan.databases.opdracht2.simulations;

import jordiarjan.databases.opdracht2.DBManager;

public interface Simulation {

    void setup(DBManager dbManager);

    void simulate(int threads);
}
