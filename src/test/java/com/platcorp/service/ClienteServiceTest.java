package com.platcorp.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.platcorp.domain.entity.Cliente;
import com.platcorp.domain.entity.InfoCliente;
import com.platcorp.exception.BadRequestException;
import com.platcorp.exception.BusinessException;
import com.platcorp.repository.ClienteRepository;
import com.platcorp.service.impl.ClienteServiceImpl;

public class ClienteServiceTest{

	@InjectMocks
	private ClienteService service;
	
	@Mock
	private ClienteRepository repository;
	
	@Mock
	private HttpServletRequest request;
	
	private Cliente clienteSalvo, clienteAlterado;
	
	private InfoCliente informacoesCliente;

	@Before
	public void antes() {
		service = new ClienteServiceImpl();
		MockitoAnnotations.initMocks(this);
		
		clienteSalvo = geraCliente();
		clienteAlterado = geraClienteAlterado();
		informacoesCliente = geraInfoCliente();
	}

	@Test
	public void devePersistirNovoCliente() throws BusinessException {
		Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(clienteSalvo);
		Mockito.when(request.getRemoteAddr()).thenReturn("192.168.1.1");
		
		Long idCliente = service.persistir("Bruno Silva", 23, informacoesCliente);
		
		assertThat(idCliente, equalTo(100L));
		
	}
	
	@Test(expected = BadRequestException.class)
	public void naoDevePersistirELancaCamposInvalidos() throws BusinessException{
		Mockito.when(request.getRemoteAddr()).thenReturn("192.168.1.1");
		
		try {
			service.persistir("Bruno Silva",0, informacoesCliente);
			fail();
		}catch(BadRequestException bdException) {
			assertThat(bdException.getMessage(), equalTo("Campos não preenchidos ou preenchidos incorretamente."));
            throw bdException;
		}
	}
	
	@Test(expected = BadRequestException.class)
	public void NaoDeveAlterarELancaClienteNulo() throws BusinessException{
			try {
				service.alterar(null, 01L);
				fail();
			}catch (BadRequestException e) {
				assertThat(e.getMessage(), equalTo("Cliente não pode ser nulo."));
				throw e;
			}
	}
	
	@Test(expected = BadRequestException.class)
	public void NaoDeveAlterarELancaClienteNaoEncontrado() throws BusinessException{
		Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(clienteAlterado);
			try {
				clienteSalvo.setNome("Jose da Silva");
				service.alterar(clienteSalvo, 01L);
				fail();
			}catch (BadRequestException e) {
				assertThat(e.getMessage(), equalTo("Cliente não encontrado."));
				throw e;
			}
	}
	
	@Test(expected = BadRequestException.class)
	public void NaoDeveDeletarELancaParametroInvalido() throws BusinessException{
		
			try {
				service.deletar(null);
				fail();
			}catch (BadRequestException e) {
				assertThat(e.getMessage(), equalTo("Parametro inválido ou não preenchido."));
				throw e;
			}
	}
	
	private Cliente geraCliente() {
		Cliente cliente = Cliente.builder()
				.id(100L)
				.nome("Bruno da Silva")
				.idade(25)
				.build();
		return cliente;
	}
	
	private Cliente geraClienteAlterado() {
		Cliente cliente = Cliente.builder()
				.id(100L)
				.nome("Bruno da Silva")
				.idade(35)
				.build();
		return cliente;
	}
	
	private InfoCliente geraInfoCliente() {
		InfoCliente info = InfoCliente.builder()
				.cidade("Salvador")
				.id(01L)
				.descricaoTempo("Ensolarado")
				.latitudeAndLongitude("2.234, 4.678")
				.tempMaxima(33.1)
				.tempMinima(27.98)
				.build();
		return info;
	}
	
}
