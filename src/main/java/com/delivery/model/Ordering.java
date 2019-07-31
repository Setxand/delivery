package com.delivery.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ordering {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;

	private String courierId;
	private String userId;
	private String deliveryId;
	private String goods;

	private String currentAddress;
	private Double currentLat;
	private Double currentLng;

	private String startAddress;
	private Double startLat;
	private Double startLng;

	private String finishAddress;
	private Double finishLat;
	private Double finishLng;
}
