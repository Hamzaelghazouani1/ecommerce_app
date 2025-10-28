package ma.enset.glsid.hamzaelghazouani.ecommerce.billing.web;

import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.entities.Bill;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.entities.ProductItem;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.feign.CustomerRestClient;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.feign.ProductRestClient;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.model.Customer;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.model.Product;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.repositories.BillRepository;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.repositories.ProductItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BillRestController {

    private final BillRepository billRepository;
    private final ProductItemRepository productItemRepository;
    private final CustomerRestClient customerRestClient;
    private final ProductRestClient productRestClient;

    public BillRestController(BillRepository billRepository,
                              ProductItemRepository productItemRepository,
                              CustomerRestClient customerRestClient,
                              ProductRestClient productRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }

    @GetMapping("/fullBill/{id}")
    public Bill getFullBill(@PathVariable Long id) {
        // 1. Récupérer la facture
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found: " + id));

        // 2. Récupérer le client via Feign
        Customer customer = customerRestClient.getCustomerById(bill.getCustomerId());
        bill.setCustomer(customer);

        // 3. Récupérer les ProductItems et enrichir avec les informations produit
        List<ProductItem> productItems = productItemRepository.findByBillId(id);
        productItems.forEach(pi -> {
            Product product = productRestClient.getProductById(pi.getProductId());
            pi.setProduct(product);
        });
        bill.setProductItems(productItems);

        return bill;
    }

    @GetMapping("/bills")
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @GetMapping("/bills/customer/{customerId}")
    public List<Bill> getBillsByCustomer(@PathVariable Long customerId) {
        return billRepository.findByCustomerId(customerId);
    }
}

