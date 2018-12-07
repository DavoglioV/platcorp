package com.platcorp.service;

import org.springframework.http.ResponseEntity;

import com.platcorp.domain.entity.Cliente;
import com.platcorp.domain.entity.InfoCliente;
import com.platcorp.exception.BadRequestException;
import com.platcorp.exception.BusinessException;

public interface ClienteService {
	
	/** 
	 * Salva um novo cliente na base de dados.
	 * 
	 * @param nome
	 * @param idade
	 * @param info
	 * @return Long com id do cliente criado
	 * @throws BusinessException
	 */
	Long persistir(String nome, int idade, InfoCliente info) throws BusinessException;

	/** 
	 * Altera as informações de um cliente registrado na base de dados.
	 * 
	 * @param cliente
	 * @param id
	 * @throws BadRequestException
	 */
	void alterar(Cliente cliente, Long id) throws BadRequestException;

	/** 
	 * Deleta um cliente já cadastrado na base de dados
	 * 
	 * @param id
	 * @throws BadRequestException
	 */
	void deletar(Long id) throws BadRequestException;

	/** 
	 * Busca um cliente por id cadastrado na base.
	 * 
	 * @param id
	 * @return ResponseEntity Retorna um response entity com o Cliente localizado no corpo da resposta.
	 * @throws BadRequestException
	 */
	ResponseEntity<Cliente> buscaPorId(Long id) throws BadRequestException;

	/** 
	 * Busca todos os clientes cadastrados na base.
	 * 
	 * @return ResponseEntity com a lista de clientes cadastrados no corpo da resposta.
	 */
	ResponseEntity<?> buscaTodos();

}
