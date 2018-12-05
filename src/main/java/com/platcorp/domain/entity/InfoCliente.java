package com.platcorp.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InfoCliente {
	
	@Id
    @GeneratedValue
    @Column(name = "id_infoCliente")
    private Long id;
	private String latitudeAndLongitude;
	private String cidade;
	private double tempMaxima;
	private double tempMinima;
	private String descricaoTempo;
}
