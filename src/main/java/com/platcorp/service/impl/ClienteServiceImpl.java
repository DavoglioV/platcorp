package com.platcorp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platcorp.domain.entity.Cliente;
import com.platcorp.domain.entity.InfoCliente;
import com.platcorp.exception.BadRequestException;
import com.platcorp.exception.BusinessException;
import com.platcorp.repository.ClienteRepository;
import com.platcorp.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public Long persistir(String nome, int idade, InfoCliente info) throws BusinessException {

		validaCamposCliente(nome, idade);
		
		Cliente cliente = Cliente.builder()
				.nome(nome)
				.idade(idade)
				.info(info)
				.build();
		
		cliente = clienteRepository.saveAndFlush(cliente);
		return cliente.getId();
	}

	/** valida preenchimento dos campos para inserção de um novo cliente
	 * Dados obrigatorios: Nome e Idade
	 * 
	 * @param nome
	 * @param idade
	 * @throws BusinessException
	 */
	private void validaCamposCliente(String nome, int idade) throws BadRequestException {

		if (nome.isEmpty() || idade <= 0) {
			throw new BadRequestException("Campos não preenchidos ou preenchidos incorretamente.");
		}
	}

}