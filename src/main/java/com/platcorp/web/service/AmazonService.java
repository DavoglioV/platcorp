package com.platcorp.web.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.platcorp.exception.ExternalErrorException;

@Component
public class AmazonService {

	private static final String URL = "http://checkip.amazonaws.com";
	
	/**
	 * Obtem IP publico.
	 * @return String com endereço de IP
	 */
	public String getIpPublic() throws ExternalErrorException {
		
		String ipPublico;
		try{
			ipPublico = new RestTemplate().getForObject(URL, String.class);
		}catch(RestClientException e) {
			e.printStackTrace();
			throw new ExternalErrorException(this.getClass(), "Falha ao tentar se comunicar com Serviço de IP "
					+ "da Amazon. Favor tentar mais tarde.");
		}
		
		return ipPublico;
	}
}
