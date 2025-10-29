package ma.enset.glsid.hamzaelghazouani.ecommerce.billing.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import ma.enset.glsid.hamzaelghazouani.ecommerce.billing.model.Product;

@Entity
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productId;
    private double price;
    private int quantity;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Bill bill;

    @Transient
    private Product product;

    public ProductItem() {}

    public ProductItem(Long id, String productId, double price, int quantity, Bill bill, Product product) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.bill = bill;
        this.product = product;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Bill getBill() { return bill; }
    public void setBill(Bill bill) { this.bill = bill; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public static ProductItemBuilder builder() { return new ProductItemBuilder(); }

    public static class ProductItemBuilder {
        private Long id;
        private String productId;
        private double price;
        private int quantity;
        private Bill bill;
        private Product product;

        public ProductItemBuilder id(Long id) { this.id = id; return this; }
        public ProductItemBuilder productId(String productId) { this.productId = productId; return this; }
        public ProductItemBuilder price(double price) { this.price = price; return this; }
        public ProductItemBuilder quantity(int quantity) { this.quantity = quantity; return this; }
        public ProductItemBuilder bill(Bill bill) { this.bill = bill; return this; }
        public ProductItemBuilder product(Product product) { this.product = product; return this; }
        public ProductItem build() { return new ProductItem(id, productId, price, quantity, bill, product); }
    }
}
