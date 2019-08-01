package com.delivery.util;

import com.delivery.dto.LocationDTO;
import com.delivery.dto.CreateOrderingDTO;
import com.delivery.dto.UserDTO;
import com.delivery.model.Location;
import com.delivery.model.Ordering;
import com.delivery.model.User;

public class DtoUtils {


	public static UserDTO user(User user) {
		UserDTO dto = new UserDTO();
		dto.name = user.getName();
		dto.email = user.getEmail();
		dto.role = user.getRole();
		dto.id = user.getId();

		return dto;
	}

	public static CreateOrderingDTO ordering(Ordering ordering) {
		CreateOrderingDTO dto = new CreateOrderingDTO();

		dto.uuid = ordering.getId();
		dto.courierId = ordering.getCourierId();
		dto.userId = ordering.getUserId();
		dto.deliveryUuid = ordering.getDeliveryId();
		dto.goods = ordering.getGoods();

		dto.start = location(ordering.getStartLocation());
		dto.current = location(ordering.getCurrentLocation());
		dto.finish = location(ordering.getFinishLocation());

		return dto;
	}

	private static LocationDTO location(Location entity) {
		LocationDTO dto = new LocationDTO();
		dto.address = entity.getAddress();
		dto.lng = entity.getLng();
		dto.lat = entity.getLat();
		return dto;
	}
}
