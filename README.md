# SWIFT Codes API

REST API do zarządzania kodami SWIFT/BIC banków. Aplikacja umożliwia importowanie, przechowywanie i zarządzanie kodami SWIFT, wraz z informacjami o centralach i oddziałach banków.

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

## Wymagania

- Java 17 lub nowsza
- Docker Desktop
- Maven (opcjonalnie - projekt zawiera Maven Wrapper)

## Instalacja i uruchomienie

1. Sklonuj repozytorium:
```bash
git clone https://github.com/T00RRE/SWIFT_API.git
cd SWIFT_API
Uruchom aplikację używając Docker Compose:

bashCopydocker-compose up --build

Aplikacja będzie dostępna pod adresem:


API: http://localhost:8080
Swagger UI: http://localhost:8080/swagger-ui.html
Interfejs użytkownika: http://localhost:8080

Endpointy API
1. Pobierz szczegóły kodu SWIFT
CopyGET /v1/swift-codes/{swift-code}
2. Pobierz kody SWIFT dla kraju
CopyGET /v1/swift-codes/country/{countryISO2code}
3. Dodaj nowy kod SWIFT
CopyPOST /v1/swift-codes
4. Importuj kody z pliku Excel
CopyPOST /v1/swift-codes/import
5. Usuń kod SWIFT
CopyDELETE /v1/swift-codes/{swift-code}
Testy
Uruchomienie testów:
bashCopy./mvnw test
Projekt zawiera:

Testy jednostkowe
Testy integracyjne
Testy kontrolerów

Struktura projektu
Copyswift-api/
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
