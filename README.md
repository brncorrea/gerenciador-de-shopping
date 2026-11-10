# gerenciador-de-shopping

![CI](https://github.com/brncorrea/gerenciador-de-shopping/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-21-orange)
![Maven](https://img.shields.io/badge/Maven-build-blue)
![License](https://img.shields.io/badge/License-MIT-green)

A console application written in **Java** for managing shopping malls and their stores —
a study project covering object-oriented programming fundamentals: inheritance,
polymorphism, composition, encapsulation and unit testing.

## Features

- Create a shopping mall with a maximum number of store slots
- Create stores and add them to the mall:
  - **Alimentação** (food store, holds its sanitary permit date)
  - **Bijuteria** (jewelry store, monthly sales target)
  - **Cosmético** (cosmetics store, commercialization fee)
  - **Informática** (electronics store, electronics insurance)
  - **Vestuário** (clothing store, imported products flag)
  - Generic store (base type)
- Add products with optional expiration date to a store's inventory
- Check whether a product is expired against today's date
- List stores, count stores by type and find the store with the most
  expensive electronics insurance
- Remove stores from the mall
- Validated console input (never crashes on invalid typing)

## Project structure

```
src/main/java/com/brncorrea/shopping/
├── model/   Data, Endereco, Produto
├── store/   Loja, Alimentacao, Bijuteria, Cosmetico, Informatica, Vestuario
├── mall/    Shopping
└── app/     Principal (menu), ConsoleInput (validated input)
src/test/java/...  JUnit 5 unit tests
```

## Requirements

- JDK 21 or newer
- Apache Maven 3.9+

## How to run

```bash
# build the project
mvn clean compile

# run the application
mvn exec:java

# run the unit tests
mvn test
```

## Running with GitHub Actions

Every push to `main` triggers the CI workflow (`.github/workflows/ci.yml`),
which builds the project and runs all tests on Java 21 and 24.

## License

This project is licensed under the [MIT License](LICENSE).
