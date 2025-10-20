package ma.enset.glsid.hamzaelghazouani.ecommerce.customer.repositories;

import ma.enset.glsid.hamzaelghazouani.ecommerce.customer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

