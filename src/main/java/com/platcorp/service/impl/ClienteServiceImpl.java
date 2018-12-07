package com.platcorp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
	@Override
	public void alterar(Cliente paramCliente, Long id) throws BadRequestException{
		
		if (null == paramCliente) {
			throw new BadRequestException ("Cliente não pode ser nulo.");
		}

		Optional<Cliente> c = clienteRepository.findById(id);
		if (c.isPresent()) {
			if( !(null == paramCliente.getNome() || paramCliente.getNome().isEmpty()) )
				c.get().setNome(paramCliente.getNome());
			if( paramCliente.getIdade() <= 0)
				c.get().setIdade(paramCliente.getIdade());
			
		}else {
			throw new BadRequestException("Cliente não encontrado.");
		}
		
		clienteRepository.saveAndFlush(c.get());
		
	}

	@Override
	public void deletar(Long id) throws BadRequestException{
		if(id == null) {
			throw new BadRequestException("Parametro inválido ou não preenchido.");
		}
		clienteRepository.deleteById(id);
		
	}
	
	@Override
	public ResponseEntity<Cliente> buscaPorId(Long id) throws BadRequestException {
		
		if(id == null) {
			throw new BadRequestException("Parametro inválido ou não preenchido.");
		}
		
		Cliente cliente = clienteRepository.findById(id).get();
		
		if(cliente == null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok().body(cliente);
	}

	@Override
	public ResponseEntity<?> buscaTodos() {
		List<Cliente> lstCliente = clienteRepository.findAll();
		
		if(lstCliente.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok().body(lstCliente);
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