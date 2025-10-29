package ma.enset.glsid.hamzaelghazouani.ecommerce.billing;

import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.entities.Bill;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.entities.ProductItem;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.feign.CustomerRestClient;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.feign.ProductRestClient;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.model.Customer;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.model.Product;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.repositories.BillRepository;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemRepository productItemRepository,
                            CustomerRestClient customerRestClient,
                            ProductRestClient productRestClient) {
        return args -> {
            // Get all customers from Customer Service
            PagedModel<Customer> customers = customerRestClient.getAllCustomers();
            // Get all products from Inventory Service
            PagedModel<Product> products = productRestClient.getAllProducts();

            Random random = new Random();

            // Create bills for each customer
            customers.getContent().forEach(customer -> {
                Bill bill = Bill.builder()
                        .billingDate(new Date())
                        .customerId(customer.getId())
                        .build();
                Bill savedBill = billRepository.save(bill);

                // Add random products to the bill
                products.getContent().forEach(product -> {
                    if (random.nextBoolean()) {
                        ProductItem productItem = ProductItem.builder()
                                .productId(product.getId())
                                .price(product.getPrice())
                                .quantity(1 + random.nextInt(5))
                                .bill(savedBill)
                                .build();
                        productItemRepository.save(productItem);
                    }
                });
            });

            System.out.println("============ Bills Created ============");
            billRepository.findAll().forEach(bill -> {
                System.out.println("Bill ID: " + bill.getId() + " | Customer ID: " + bill.getCustomerId());
            });
        };
    }
}

