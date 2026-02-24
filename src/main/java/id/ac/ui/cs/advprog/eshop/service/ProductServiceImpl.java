package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findById(String productID) {
        List<Product> allProduct = findAll();

        for (Product product : allProduct) {
            if (product.getProductId().equals(productID)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void edit(Product product, Optional<String> newProductName, Optional<Integer> newProductQuantity) {
        newProductName.ifPresent(product::setProductName);
        newProductQuantity.ifPresent(product::setProductQuantity);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }
}
