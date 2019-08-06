package com.delivery.repository;

import com.delivery.model.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderingRepository extends JpaRepository<Ordering, String> {


	List<Ordering> findAllByUserId(String userId);

	List<Ordering> findAllByCourierId(String courierId);

}
