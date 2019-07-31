package com.delivery.util;

import com.delivery.dto.LocationDTO;
import com.delivery.dto.CreateOrderingDTO;
import com.delivery.dto.UserDTO;
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

		dto.start = new LocationDTO();
		dto.start.address = ordering.getStartAddress();
		dto.start.lat = ordering.getStartLat();
		dto.start.lng = ordering.getStartLng();

		dto.current = new LocationDTO();
		dto.current.address = ordering.getCurrentAddress();
		dto.current.lat = ordering.getCurrentLat();
		dto.current.lng = ordering.getCurrentLng();

		dto.finish = new LocationDTO();
		dto.finish.address = ordering.getFinishAddress();
		dto.finish.lat = ordering.getFinishLat();
		dto.finish.lng = ordering.getFinishLng();

		return dto;
	}
}
