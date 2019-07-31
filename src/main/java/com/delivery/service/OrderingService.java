package com.delivery.service;

import com.delivery.dto.CreateOrderingDTO;
import com.delivery.model.Ordering;
import com.delivery.model.User;
import com.delivery.repository.OrderingRepository;
import com.delivery.repository.UserRepository;
import com.delivery.security.Auth;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderingService {

	private final OrderingRepository orderingRepo;
	private final UserRepository userRepo;

	public OrderingService(OrderingRepository orderingRepo, UserRepository userRepo) {
		this.orderingRepo = orderingRepo;
		this.userRepo = userRepo;
	}

	public void createOrdering(CreateOrderingDTO dto) {
		Ordering ordering = new Ordering();

//		ordering.setCourierId(dto.courierId);
		ordering.setUserId(dto.userId);
		ordering.setDeliveryId(dto.deliveryUuid);
		ordering.setGoods(dto.goods);

//		ordering.setCurrentAddress(dto.current.address);
//		ordering.setCurrentLat(dto.current.lat);
//		ordering.setCurrentLng(dto.current.lng);

		ordering.setStartAddress(dto.start.address);
		ordering.setStartLat(dto.start.lat);
		ordering.setStartLng(dto.start.lng);

		ordering.setFinishAddress(dto.finish.address);
		ordering.setFinishLat(dto.finish.lat);
		ordering.setFinishLng(dto.finish.lng);

		orderingRepo.save(ordering);
	}

	public List<Ordering> listOrderingsByUser(String userId) {
		Auth.courier();

		return orderingRepo.findAllByUserId(userId);
	}
}
