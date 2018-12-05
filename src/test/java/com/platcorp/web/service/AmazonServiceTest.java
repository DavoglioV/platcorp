package com.platcorp.web.service;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class AmazonServiceTest {

	private static final String URL = "192.168.1.1";
	
	@InjectMocks
	private AmazonService service;
	
	@Mock
	private RestTemplate template;
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}
	
//	@Test
//	public void shouldGetIpPublico() throws ExternalErrorException{
//		Mockito.when(template.getForObject("http://checkip.amazonaws.com", String.class)).thenReturn(URL);
//		String ip = service.getIpPublic();
//		assertThat(ip, equalTo(URL));
//	}
}
