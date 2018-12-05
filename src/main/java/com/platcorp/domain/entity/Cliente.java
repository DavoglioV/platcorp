package com.platcorp.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente {

	@Id
    @GeneratedValue
    @Column(name = "id_cliente")
    private Long id;
	
	@NotNull
	@Column(length = 100, nullable = false)
	private String nome;
	
	@NotNull
	@Column(nullable = false)
	private int idade;
	
	@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_info", referencedColumnName = "id_infoCliente", nullable = false)
	private InfoCliente info;
	
	public Cliente (String nome, int idade) {
		this.nome = nome;
		this.idade = idade;
	}
	

}
