package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.dto.Product;
import ru.netology.exceptions.AlreadyExistsException;
import ru.netology.exceptions.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ShopRepositoryTest {

    ShopRepository shopRepository;

    @BeforeEach
    void setUp() {
        shopRepository = new ShopRepository();
        shopRepository.add(new Product(1, "product1", 10));
        shopRepository.add(new Product(2, "product2", 20));
    }

    @Test
    void addSuccessfully() {
        shopRepository.add(new Product(3, "product3", 30));
        Product[] expected = new Product[]{
                new Product(1, "product1", 10),
                new Product(2, "product2", 20),
                new Product(3, "product3", 30)
        };
        Product[] actual = shopRepository.findAll();
        assertArrayEquals(expected, actual);
    }

    @Test
    void addFailure() {
        RuntimeException exception = assertThrows(AlreadyExistsException.class, () -> {
            shopRepository.add(new Product(1, "product734", 734));
        });
        assertEquals("Element with id: 1 already exists", exception.getMessage());
    }

    @Test
    void removeSuccessfully() {
        shopRepository.remove(1);
        Product[] expected = new Product[]{
                new Product(2, "product2", 20)
        };
        Product[] actual = shopRepository.findAll();
        assertArrayEquals(expected, actual);
    }

    @Test
    void removeFailure() {
        RuntimeException exception = assertThrows(NotFoundException.class, () -> {
            shopRepository.remove(3);
        });
        assertEquals("Element with id: 3 not found", exception.getMessage());
    }
}