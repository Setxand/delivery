package com.delivery.dto;

import com.delivery.model.Location;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class UpdateOrderingDTO {

	public String orderingId;

	@NotNull
	@JsonProperty("current_location")
	public LocationDTO currentLocation;

}
