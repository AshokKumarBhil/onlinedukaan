package com.onlinedukaan.repository;

import com.onlinedukaan.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    List<Product> findAll();

    @Override
    Product getById(Long aLong);

    @Override
    List<Product> findAllById(Iterable<Long> longs);

    @Override
    void deleteById(Long aLong);
    @Query(
            value = "SELECT * FROM product p WHERE p.category = 'GROCERY'",
            nativeQuery = true)
    List<Product> getGroceryProducts();

    @Query(
            value = "SELECT * FROM product p WHERE p.category = 'STATIONARY'",
            nativeQuery = true)
    List<Product> getStationaryProducts();


}
