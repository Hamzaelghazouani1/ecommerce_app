package ma.enset.glsid.hamzaelghazouani.ecommerce.inventory.repositories;

import ma.enset.glsid.hamzaelghazouani.ecommerce.inventory.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, String> {
}

