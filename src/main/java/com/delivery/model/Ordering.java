package com.delivery.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

	@OneToOne(cascade = CascadeType.ALL)
	private Location currentLocation;

	@OneToOne(cascade = CascadeType.ALL)
	private Location startLocation;

	@OneToOne(cascade = CascadeType.ALL)
	private Location finishLocation;
}
