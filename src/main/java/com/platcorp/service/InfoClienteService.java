package com.platcorp.service;

import javax.servlet.http.HttpServletRequest;

import com.platcorp.domain.entity.InfoCliente;
import com.platcorp.exception.BusinessException;
import com.platcorp.exception.ExternalErrorException;

public interface InfoClienteService {

	/**
	 * Gera informações de clima e localização do cliente de origem da requisição na data da solicitação.
	 * 
	 * @param request
	 * @return InfoCliente com informações do cliente de origem.
	 * @throws BusinessException
	 */
	InfoCliente gerarInformacoesCliente(HttpServletRequest request) throws BusinessException, ExternalErrorException;

}
