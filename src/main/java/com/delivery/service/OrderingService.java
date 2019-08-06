package com.delivery.service;

import com.delivery.dto.CreateOrderingDTO;
import com.delivery.dto.LocationDTO;
import com.delivery.dto.UpdateOrderingDTO;
import com.delivery.model.Location;
import com.delivery.model.Ordering;
import com.delivery.repository.OrderingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderingService {

	private final OrderingRepository orderingRepo;
	private final UserService userService;

	public OrderingService(OrderingRepository orderingRepo, UserService userService) {
		this.orderingRepo = orderingRepo;
		this.userService = userService;
	}

	public void createOrdering(CreateOrderingDTO dto) {
		Ordering ordering = new Ordering();

		ordering.setCourierId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		ordering.setUserId(dto.userId);
		ordering.setDeliveryId(dto.deliveryUuid);
		ordering.setGoods(dto.goods);

		ordering.setCurrentLocation(createLocation(dto.start));
		ordering.setStartLocation(createLocation(dto.start));
		ordering.setFinishLocation(createLocation(dto.finish));

		orderingRepo.save(ordering);
	}

	public List<Ordering> listOrderingsByUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String authorities = authentication.getAuthorities().toString();

		if (authorities.contains("ROLE_COURIER")) {
			return orderingRepo.findAllByCourierId(authentication.getPrincipal().toString());
		}
		else
			return orderingRepo.findAllByUserId(authentication.getPrincipal().toString());
	}

	@Transactional
	public void updateOrdering(UpdateOrderingDTO dto) {
		Ordering ordering = orderingRepo
				.findById(dto.orderingId).orElseThrow(() -> new IllegalArgumentException("Invalid Ordering ID"));

		if (dto.currentLocation != null) {
			Location location = new Location();
			location.setAddress(dto.currentLocation.address);
			location.setLat(dto.currentLocation.lat);
			location.setLng(dto.currentLocation.lng);

			ordering.setCurrentLocation(location);
		}
	}

	@Transactional
	public void deleteOrdering(String orderId) {
		orderingRepo.deleteById(orderId);
	}

	private Location createLocation(LocationDTO dto) {
		Location location = new Location();
		location.setAddress(dto.address);
		location.setLat(dto.lat);
		location.setLng(dto.lng);
		return location;
	}
}
