package com.onlinedukaan.repository;

import com.onlinedukaan.model.CartItem;
import com.onlinedukaan.model.Product;
import com.onlinedukaan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    @Transactional
    @Modifying
    @Query("delete from CartItem c where c.product = ?1 and c.user = ?2")
    void deleteByProductAndUser(Product product, User user);

    @Transactional
    @Modifying
    @Query("update CartItem c set c.quantity = ?1 where c.id = ?2")
    void updateQuantityById(int quantity, Integer id);
    Optional<CartItem> findByUser_UserIdAndProduct_ProductId(long userId, long productId);
    boolean existsByUser_UserIdAndProduct_ProductId(long userId, long productId);

    @Query("select c from CartItem c where c.user.userId = ?1")
    List<CartItem> findByUserId(@NonNull long userId);
}