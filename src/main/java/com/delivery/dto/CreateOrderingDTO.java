package com.delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateOrderingDTO {

	@NotBlank
	@JsonProperty("client_id")
	public String userId;

//	@JsonProperty("courier_id")
//	public String courierId;

	@JsonProperty("current_location")
	public LocationDTO current;

	@NotNull
	@JsonProperty("finish_location")
	public LocationDTO finish;

	@NotNull
	@JsonProperty("start_location")
	public LocationDTO start;

	@NotBlank
	@JsonProperty("name_goods")
	public String goods;

	public String uuid;

	@NotBlank
	@JsonProperty("uuid_delivery")
	public String deliveryUuid;

}
