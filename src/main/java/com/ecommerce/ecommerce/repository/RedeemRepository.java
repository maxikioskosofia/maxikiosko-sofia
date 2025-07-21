package com.ecommerce.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.domain.Redeem;
import com.ecommerce.ecommerce.domain.User;

public interface RedeemRepository extends JpaRepository<Redeem, Long>{
    List<Redeem> findByUser(User user);
}
