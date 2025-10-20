package ma.enset.glsid.hamzaelghazouani.ecommerce.customer.projections;

import ma.enset.glsid.hamzaelghazouani.ecommerce.customer.entities.Customer;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "fullCustomer", types = Customer.class)
public interface CustomerProjection {
    Long getId();
    String getName();
    String getEmail();
}

