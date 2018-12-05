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
	
	private Cliente clienteSalvo;
	
	private InfoCliente informacoesCliente;

	@Before
	public void before() {
		service = new ClienteServiceImpl();
		MockitoAnnotations.initMocks(this);
		
		clienteSalvo = buildCliente();
		informacoesCliente = buildInfoCliente();
	}

	@Test
	public void shouldPersistirNovoCliente() throws BusinessException {
		Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(clienteSalvo);
		Mockito.when(request.getRemoteAddr()).thenReturn("192.168.1.1");
		
		Long idCliente = service.persistir("Bruno Silva", 23, informacoesCliente);
		
		assertThat(idCliente, equalTo(100L));
		
	}
	
	@Test(expected = BadRequestException.class)
	public void shouldNotPersistirAndThrowsCamposInvalidos() throws BusinessException{
		Mockito.when(request.getRemoteAddr()).thenReturn("192.168.1.1");
		
		try {
			service.persistir("Bruno Silva",0, informacoesCliente);
			fail();
		}catch(BadRequestException bdException) {
			assertThat(bdException.getMessage(), equalTo("Campos não preenchidos ou preenchidos incorretamente."));
            throw bdException;
		}
	}
	
	private Cliente buildCliente() {
		Cliente cliente = Cliente.builder()
				.id(100L)
				.nome("Bruno da Silva")
				.build();
		return cliente;
	}
	
	private InfoCliente buildInfoCliente() {
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
