package com.platcorp.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platcorp.domain.Erro;
import com.platcorp.domain.LocationData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationClientDTO {
	
	private String status;
	private LocationData data;
	private List<Erro> errors;
	
}
