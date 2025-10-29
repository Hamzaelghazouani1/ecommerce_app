## Stack Technique

- Java 21
- Spring Boot 3.2.0
- Spring Cloud 2023.0.0
- Netflix Eureka (Service Discovery)
- Spring Cloud Config (Configuration Server)
- Spring Cloud Gateway (Reactive)
- OpenFeign (Inter-service Communication)
- H2 Database (In-memory)
- Lombok
- Spring Data REST
- Spring HATEOAS

## Architecture

```
ecommerce_app/
├── pom.xml (parent)
├── config-repo/           (Git repository for configurations)
├── config-service/        (Port 9999 - Config Server)
├── discovery-service/     (Port 8761 - Eureka Server)
├── customer-service/      (Port 8081 - Gestion clients)
├── inventory-service/     (Port 8082 - Gestion produits)
├── gateway-service/       (Port 8888 - API Gateway)
└── billing-service/       (Port 8083 - Facturation)
```

## Modules

### Config Service (Port 9999)
- Centralized Configuration Server
- Git-based configuration repository
- Endpoint: `/config-service/{application}/{profile}`

### Discovery Service (Port 8761)
- Eureka Server
- Service Registry pour tous les microservices
- Dashboard: http://localhost:8761

### Customer Service (Port 8081)
- Gestion des clients
- Entity: Customer (id, name, email)
- Spring Data REST API
- Config Client enabled

### Inventory Service (Port 8082)
- Gestion des produits
- Entity: Product (id UUID, name, price, quantity)
- Spring Data REST API
- Config Client enabled

### Gateway Service (Port 8888)
- API Gateway (Reactive)
- Routage dynamique via Discovery
- Point d'entrée unique
- Config Client enabled

### Billing Service (Port 8083)
- Service de Facturation (Composite Service)
- Entities: Bill, ProductItem
- OpenFeign pour communication inter-services
- Endpoint: `/fullBill/{id}` - Facture complète avec détails

## Ordre de Démarrage

1. **Discovery Service** (8761)
2. **Config Service** (9999)
3. **Customer Service** (8081) + **Inventory Service** (8082)
4. **Gateway Service** (8888)
5. **Billing Service** (8083)

## URLs de Test

### Direct Access
| URL | Description |
|-----|-------------|
| http://localhost:8761 | Eureka Dashboard |
| http://localhost:9999/customer-service/default | Config for Customer |
| http://localhost:8081/customers | Customer Service |
| http://localhost:8082/products | Inventory Service |
| http://localhost:8083/fullBill/1 | Full Bill with details |
| http://localhost:8081/h2-console | H2 Console Customer |
| http://localhost:8082/h2-console | H2 Console Inventory |
| http://localhost:8083/h2-console | H2 Console Billing |

### Via Gateway (Routage Dynamique)
| URL | Description |
|-----|-------------|
| http://localhost:8888/customer-service/customers | Customers via Gateway |
| http://localhost:8888/inventory-service/products | Products via Gateway |
| http://localhost:8888/billing-service/fullBill/1 | Bill via Gateway |

## Configuration H2 Console

- JDBC URL Customer: `jdbc:h2:mem:customers-db`
- JDBC URL Inventory: `jdbc:h2:mem:products-db`
- JDBC URL Billing: `jdbc:h2:mem:billing-db`
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

### Bills
- Generated automatically at startup
- Each customer gets a bill with random products

## Communication Inter-services

```
Billing Service
    │
    ├──► Customer Service (via Feign)
    │    └── GET /customers/{id}
    │
    └──► Inventory Service (via Feign)
         └── GET /products/{id}
```

## API Endpoints Billing Service

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/fullBill/{id}` | Facture complète avec client et produits |
| GET | `/bills` | Liste toutes les factures |
| GET | `/bills/customer/{customerId}` | Factures par client |
