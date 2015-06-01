# Mysql concurrency problems simulation
This repository contains code which simulates concurrency problems which can occur when not using the right isolation levels for a specific use cases.

The simulations are all based on a case in which you have a table with products and mutations for the stock. The max amount of one product in stock is 100.
When there are at delivery more than 100 products the remaining products are for the employees.

Concurrency problems included:
* Dirty read
* Non repeatable read
* Phantom read
* Dead lock
