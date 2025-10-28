package ma.enset.glsid.hamzaelghazouani.ecommerce.billing.repositories;

import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
    List<ProductItem> findByBillId(Long billId);
}

