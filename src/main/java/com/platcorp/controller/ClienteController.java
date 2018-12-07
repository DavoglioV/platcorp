package com.platcorp.controller;

import java.net.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platcorp.domain.entity.Cliente;
import com.platcorp.domain.entity.InfoCliente;
import com.platcorp.exception.BadRequestException;
import com.platcorp.exception.BusinessException;
import com.platcorp.exception.ExternalErrorException;
import com.platcorp.service.ClienteService;
import com.platcorp.service.InfoClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/cliente")
@Api(tags = "cliente", description = "API para manipulação de novos clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private InfoClienteService infoClienteService;

	@ApiOperation(value = "Persiste um novo cliente na base de dados")
	@ApiResponses(value = { @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Cliente persistido com sucesso"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Parâmetro obrigatório não informado ou regra de validação violada"),
			@ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Falha ao tentar estapelecer conexão com um dos servidores externos.") })
	@PostMapping
	@Transactional
	public ResponseEntity<?> persistir(@RequestParam("nome") String nome, @RequestParam("idade") int idade,
			HttpServletRequest request) throws BusinessException, ExternalErrorException {
		try {
			InfoCliente info = infoClienteService.gerarInformacoesCliente(request);
			clienteService.persistir(nome, idade, info);
			return ResponseEntity.ok().build();
		} catch (BadRequestException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		} catch (ExternalErrorException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}

	@ApiOperation(value = "Altera um cliente salvo na base de Dados")
	@ApiResponses(value = { @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Cliente alterado com sucesso"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Cliente não localizado"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Parâmetro obrigatório não informado ou regra de validação violada") })
	@PutMapping("/{id}")
	public ResponseEntity<?> alterar(@RequestBody() Cliente cliente, @PathVariable("id") Long id)
			throws BadRequestException {
		try {

			clienteService.alterar(cliente, id);
			return ResponseEntity.ok().build();
		} catch (BadRequestException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@ApiOperation(value = "Busca um cliente pelo ID salvo na base de Dados")
	@ApiResponses(value = { @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Cliente localizado com sucesso"),
			@ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Cliente não localizado"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Parâmetro obrigatório não informado ou regra de validação violada") })
	@GetMapping("/{id}")
	public ResponseEntity<?> buscaClientePorId(@PathVariable("id") Long id) throws BadRequestException {
		try {

			return clienteService.buscaPorId(id);
		} catch (BadRequestException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@ApiOperation(value = "Busca todos os clientes armazenados na base ")
	@ApiResponses(value = { @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Clientes localizados com sucesso"),
			@ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Não há registros na base de dados") } )
	@GetMapping()
	public ResponseEntity<?> buscaTodos(){
		return clienteService.buscaTodos();
	}

}
