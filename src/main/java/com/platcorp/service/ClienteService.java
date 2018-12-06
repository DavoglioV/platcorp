package com.platcorp.service;

import java.util.List;

import com.platcorp.domain.entity.Cliente;
import com.platcorp.domain.entity.InfoCliente;
import com.platcorp.exception.BadRequestException;
import com.platcorp.exception.BusinessException;

public interface ClienteService {
	
	Long persistir(String nome, int idade, InfoCliente info) throws BusinessException;

	void alterar(Cliente cliente, Long id) throws BadRequestException;

	void deletar(Long id) throws BadRequestException;

	Cliente findClienteById(Long id) throws BadRequestException;

	List<Cliente> findAll();
}
