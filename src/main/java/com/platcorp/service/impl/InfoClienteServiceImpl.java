package com.platcorp.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.platcorp.domain.dto.WeatherClientDTO;
import com.platcorp.domain.entity.InfoCliente;
import com.platcorp.exception.BusinessException;
import com.platcorp.exception.ExternalErrorException;
import com.platcorp.repository.InfoClienteRepository;
import com.platcorp.service.InfoClienteService;
import com.platcorp.web.service.AmazonService;
import com.platcorp.web.service.IpVigilantService;
import com.platcorp.web.service.MetaweatherService;

@Service
public class InfoClienteServiceImpl implements InfoClienteService{

	@Autowired
	private InfoClienteRepository repository;
	
	@Autowired 
	private IpVigilantService ipVigilant;
	
	@Autowired
	private MetaweatherService metaWeather;
	
	@Autowired
	private AmazonService amazon;
	
	private static final Logger logger = LogManager.getLogger(InfoClienteService.class);
	
	@Override
	public InfoCliente gerarInformacoesCliente(HttpServletRequest request) throws BusinessException, ExternalErrorException{
		
		logger.info("Iniciando Serviço de informação do cliente");
		
		String ipRequest = getIp(request);
		
		RestTemplate template = new RestTemplate();
		
		String latLongWithComma = ipVigilant.getLatitudeLongitude(ipRequest, template);
		int woeid = metaWeather.getWoeid(latLongWithComma, template);
		WeatherClientDTO weatherInfo = metaWeather.getInfoClima(woeid, template);
		
		return repository.save(createWeatherInfo(weatherInfo, latLongWithComma));
	}
	
	/**
	 * Obtem o endereço IP da maquina que realizou a requisição
	 * 
	 * @param request
	 * @return String com IP de origem
	 * @throws BusinessException
	 */
	public String getIp(HttpServletRequest request) throws ExternalErrorException {

		// Obtem o endereço ip da requisição
		String ipRequest = (request.getHeader("X-FORWARDED-FOR") == null ? request.getRemoteAddr()
				: request.getHeader("X-FORWARDED-FOR"));

		if (isPrivateIp(ipRequest)) {
			ipRequest = amazon.getIpPublic();
		}
		return ipRequest;
	}
	
	/**
	 * Valida se o ip é privado, ou seja, se o IP da requisição é de uma intranet
	 * neste caso, o ip utilizado é da maquina servidora, já que por estarem na
	 * mesma rede, pressupõe-se que estão geograficamente próximos.
	 * 
	 * @param ipRequest
	 * @return boolean
	 */
	private static boolean isPrivateIp(String ipRequest) {

		// Ip de origem é o mesmo do Servidor
		if (ipRequest.startsWith("0:") || ipRequest.startsWith("127")) {
			return true;
		}
		// Classe A
		if (ipRequest.startsWith("10.")) {
			return true;
		} else {

			// Classe B
			if (ipRequest.startsWith("172.")) {

				int subIp = Integer.parseInt(ipRequest.substring(4, 5));
				if (subIp > 15 || subIp < 32)
					return true;
				else
					return false;
			} else {
				// Classe C
				if (ipRequest.startsWith("192.168"))
					return true;
				else
					return false;
			}
		}
	}
	
	private InfoCliente createWeatherInfo(WeatherClientDTO weatherInfo, String latLong) {
		InfoCliente info = InfoCliente.builder()
				.latitudeAndLongitude(latLong)
				.tempMaxima(weatherInfo.getConsolidated_weather().get(0).getMax_temp())
				.tempMinima(weatherInfo.getConsolidated_weather().get(0).getMin_temp())
				.descricaoTempo(weatherInfo.getConsolidated_weather().get(0).getWeather_state_name())
				.build();
		
		return info;
	}

}
