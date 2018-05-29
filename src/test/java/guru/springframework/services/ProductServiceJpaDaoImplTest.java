package guru.springframework.services;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by jt on 12/14/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JpaIntegrationConfig.class)
@ActiveProfiles("jpadao")
public class ProductServiceJpaDaoImplTest {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void testListMethod() throws Exception {
        List<Product> products = (List<Product>) productService.listAll();
        assert products.size() > 0;
    }

    @Test
    public void testGetMethod() throws Exception {
        Product product = (Product) productService.getById(2);
        assert product.getDescription() == "Product 2";
    }

    @Test
    public void testSaveMethod() throws Exception {
        Product savedProduct = productService.saveOrUpdate(createProduct());
        Product product = productService.getById(savedProduct.getId());

        assert savedProduct.getDescription().equals(product.getDescription());
    }

    @Test
    public void testUpdateMethod() throws Exception {
        Product product = productService.saveOrUpdate(createProduct());

        product.setDescription("Updated Product");
        Product savedProduct = productService.saveOrUpdate(product);
        assert savedProduct.getDescription().equals(product.getDescription());
    }

    @Test
    public void testDeleteMethod() throws Exception {
        Product product = productService.saveOrUpdate(createProduct());
        productService.delete(product.getId());

        Product product1 = productService.getById(product.getId());
        assert product1 == null;
    }

    private Product createProduct() {
        Product product = new Product();
        product.setDescription("Product 6");
        product.setPrice(new BigDecimal("12.34"));
        product.setImageUrl("http://example.com/product");

        return product;
    }
}
