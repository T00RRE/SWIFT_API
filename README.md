# SWIFT Codes API

REST API do zarządzania kodami SWIFT/BIC banków. Aplikacja umożliwia importowanie, przechowywanie i zarządzanie kodami SWIFT, wraz z informacjami o centralach i oddziałach banków. Dodatkowo dostępny jest plik `index.html`, który pozwala na sprawdzenie działania API poprzez interfejs użytkownika.

## Technologie

- Java 17
- Spring Boot 3.2.2
- PostgreSQL
- Docker & Docker Compose
- Maven
- Lombok
- Swagger/OpenAPI

## Funkcjonalności

- Import kodów SWIFT z pliku Excel
- Automatyczne wykrywanie central (kody kończące się na XXX) i oddziałów
- REST API do zarządzania kodami
- Interfejs użytkownika do importu i przeglądania danych
- Dokumentacja API (Swagger)
- Strona `index.html` do testowania działania API

## Wymagania

- Java 17 lub nowsza
- Docker Desktop
- Maven (opcjonalnie - projekt zawiera Maven Wrapper)

## Instalacja i uruchomienie

1. Sklonuj repozytorium:
```bash
git clone https://github.com/T00RRE/SWIFT_API.git
cd SWIFT_API
```
2. Uruchom aplikację używając Docker Compose:
```bash
docker-compose up --build
```

Aplikacja będzie dostępna pod adresem:

- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Interfejs użytkownika**: http://localhost:8080/index.html

## Endpointy API

1. Pobierz szczegóły kodu SWIFT  
   `GET /v1/swift-codes/{swift-code}`
2. Pobierz kody SWIFT dla kraju  
   `GET /v1/swift-codes/country/{countryISO2code}`
3. Dodaj nowy kod SWIFT  
   `POST /v1/swift-codes`
4. Importuj kody z pliku Excel  
   `POST /v1/swift-codes/import`
5. Usuń kod SWIFT  
   `DELETE /v1/swift-codes/{swift-code}`

## Testy

Uruchomienie testów:
```bash
./mvnw test
```

Projekt zawiera:
- Testy jednostkowe
- Testy integracyjne
- Testy kontrolerów

## Struktura projektu
```
swift-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/swift_api/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── exception/
│   │   └── resources/
│   └── test/
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

---

# SWIFT Codes API (English Version)

A REST API for managing SWIFT/BIC bank codes. The application allows importing, storing, and managing SWIFT codes, along with information about central banks and branches. Additionally, an `index.html` file is available for testing the API functionality through a user interface.

## Technologies

- Java 17
- Spring Boot 3.2.2
- PostgreSQL
- Docker & Docker Compose
- Maven
- Lombok
- Swagger/OpenAPI

## Features

- Import SWIFT codes from an Excel file
- Automatic detection of central banks (codes ending in XXX) and branches
- REST API for managing codes
- User interface for importing and viewing data
- API documentation (Swagger)
- `index.html` page for testing API functionality

## Requirements

- Java 17 or later
- Docker Desktop
- Maven (optional - project includes Maven Wrapper)

## Installation and Running

1. Clone the repository:
```bash
git clone https://github.com/T00RRE/SWIFT_API.git
cd SWIFT_API
```
2. Start the application using Docker Compose:
```bash
docker-compose up --build
```

The application will be available at:

- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **User Interface**: http://localhost:8080/index.html

## API Endpoints

1. Get SWIFT code details  
   `GET /v1/swift-codes/{swift-code}`
2. Get SWIFT codes for a country  
   `GET /v1/swift-codes/country/{countryISO2code}`
3. Add a new SWIFT code  
   `POST /v1/swift-codes`
4. Import SWIFT codes from an Excel file  
   `POST /v1/swift-codes/import`
5. Delete a SWIFT code  
   `DELETE /v1/swift-codes/{swift-code}`

## Testing

Run tests:
```bash
./mvnw test
```

The project includes:
- Unit tests
- Integration tests
- Controller tests

## Project Structure
```
swift-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/swift_api/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── exception/
│   │   └── resources/
│   └── test/
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```
