package com.platcorp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationData {
	
	private String ipv4;
	private String continente;
	private String pais;
	private String subdivisao1;
	private String subdivisao2;
	private String cidade;
	private String latitude;
	private String longitude;
	
}
