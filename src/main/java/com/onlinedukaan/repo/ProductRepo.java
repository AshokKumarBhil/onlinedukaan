package com.onlinedukaan.repo;

import com.onlinedukaan.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Override
    List<Product> findAll();

    @Override
    Product getById(Long aLong);

    @Override
    List<Product> findAllById(Iterable<Long> longs);

    @Override
    void deleteById(Long aLong);



}
