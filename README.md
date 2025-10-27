## Stack Technique

- Java 21
- Spring Boot 3.2.0
- Spring Cloud 2023.0.0
- Netflix Eureka (Service Discovery)
- Spring Cloud Gateway (Reactive)
- H2 Database (In-memory)
- Lombok
- Spring Data REST

## Architecture

```
ecommerce_app/
├── pom.xml (parent)
├── discovery-service/     (Port 8761 - Eureka Server)
├── customer-service/      (Port 8081 - Gestion clients)
├── inventory-service/     (Port 8082 - Gestion produits)
└── gateway-service/       (Port 8888 - API Gateway)
```

## Modules

### Discovery Service (Port 8761)
- Eureka Server
- Service Registry pour tous les microservices

### Customer Service (Port 8081)
- Gestion des clients
- Entity: Customer (id, name, email)
- Spring Data REST API
- Projection: CustomerProjection

### Inventory Service (Port 8082)
- Gestion des produits
- Entity: Product (id UUID, name, price, quantity)
- Spring Data REST API

### Gateway Service (Port 8888)
- API Gateway
- Routage dynamique via Discovery
- Point d'entrée unique

## Ordre de Démarrage

1. Discovery Service (8761)
2. Customer Service (8081) + Inventory Service (8082)
3. Gateway Service (8888)

## URLs de Test

### Direct Access
| URL | Description |
|-----|-------------|
| http://localhost:8761 | Eureka Dashboard |
| http://localhost:8081/customers | Customer Service |
| http://localhost:8082/products | Inventory Service |
| http://localhost:8081/h2-console | H2 Console Customer |
| http://localhost:8082/h2-console | H2 Console Inventory |

### Via Gateway (Routage Dynamique)
| URL | Description |
|-----|-------------|
| http://localhost:8888/customer-service/customers | Customers via Gateway |
| http://localhost:8888/inventory-service/products | Products via Gateway |

## Configuration H2 Console

- JDBC URL Customer: `jdbc:h2:mem:customers-db`
- JDBC URL Inventory: `jdbc:h2:mem:products-db`
- Username: `sa`
- Password: (vide)

## Données de Test

### Customers
- Mohamed (mohamed@gmail.com)
- Yassine (yassine@gmail.com)
- Hassan (hassan@gmail.com)

### Products
- Computer (2500.00 MAD, qty: 10)
- Printer (350.00 MAD, qty: 25)
- Smartphone (1200.00 MAD, qty: 50)

