package ma.enset.glsid.hamzaelghazouani.ecommerce.customer;

import ma.enset.glsid.hamzaelghazouani.ecommerce.customer.entities.Customer;
import ma.enset.glsid.hamzaelghazouani.ecommerce.customer.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository) {
        return args -> {
            customerRepository.save(Customer.builder()
                    .name("Mohamed")
                    .email("mohamed@gmail.com")
                    .build());
            customerRepository.save(Customer.builder()
                    .name("Yassine")
                    .email("yassine@gmail.com")
                    .build());
            customerRepository.save(Customer.builder()
                    .name("Hassan")
                    .email("hassan@gmail.com")
                    .build());

            customerRepository.findAll().forEach(System.out::println);
        };
    }
}

