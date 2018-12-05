package com.platcorp.service;

import com.platcorp.domain.entity.InfoCliente;
import com.platcorp.exception.BusinessException;

public interface ClienteService {
	
	Long persistir(String nome, int idade, InfoCliente info) throws BusinessException;
}
