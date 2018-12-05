package com.platcorp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Clima {
	
	private String weather_state_name;
	private double min_temp;
	private double max_temp;
	
}
