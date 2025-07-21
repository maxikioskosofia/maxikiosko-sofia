package com.ecommerce.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.domain.Point;
import com.ecommerce.ecommerce.domain.User;

public interface PointRepository extends JpaRepository<Point, Long>{
    List<Point> findByUser(User user);
    List<Point> findByAssignedBy(User assignedBy);
}
