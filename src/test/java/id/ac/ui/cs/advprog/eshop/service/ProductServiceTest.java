package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    Product product1;
    Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setProductId("id1");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);

        product2 = new Product();
        product2.setProductId("id2");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
    }

    @Test
    void createTest() {
        Product created = productService.create(product1);

        assertNotNull(created.getProductId());
        assertEquals(product1.getProductName(), created.getProductName());

        verify(productRepository).create(product1);
    }

    @Test
    void findAllTest() {
        when(productRepository.findAll())
                .thenReturn(List.of(product1, product2).iterator());

        List<Product> products = productService.findAll();

        assertEquals(2, products.size());

        Iterator<Product> iterator = products.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(product1.getProductId(), iterator.next().getProductId());

        assertTrue(iterator.hasNext());
        assertEquals(product2.getProductId(), iterator.next().getProductId());

        assertFalse(iterator.hasNext());
    }

    @Test
    void findByIdFoundTest() {
        when(productRepository.findAll())
                .thenReturn(List.of(product1, product2).iterator());

        Product found = productService.findById("id1");

        assertNotNull(found);
        assertEquals("id1", found.getProductId());
    }

    @Test
    void findByIdNotFoundTest() {
        when(productRepository.findAll())
                .thenReturn(List.of(product1).iterator());

        Product found = productService.findById("id0");

        assertNull(found);
    }

    @Test
    void editNameOnlyTest() {
        productService.edit(product1,
                Optional.of("Nama Baru"),
                Optional.empty());

        assertEquals("Nama Baru", product1.getProductName());
        assertEquals(100, product1.getProductQuantity());
    }

    @Test
    void editQuantityOnlyTest() {
        productService.edit(product1,
                Optional.empty(),
                Optional.of(999));

        assertEquals("Sampo Cap Bambang", product1.getProductName());
        assertEquals(999, product1.getProductQuantity());
    }

    @Test
    void editBothTest() {
        productService.edit(product1,
                Optional.of("Baru"),
                Optional.of(10));

        assertEquals("Baru", product1.getProductName());
        assertEquals(10, product1.getProductQuantity());
    }

    @Test
    void deleteTest() {
        productService.delete(product1);

        verify(productRepository).delete(product1);
    }
}