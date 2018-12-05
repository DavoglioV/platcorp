package com.platcorp.web.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.platcorp.domain.dto.LocationSearchDTO;
import com.platcorp.domain.dto.WeatherClientDTO;
import com.platcorp.exception.ExternalErrorException;

@Component
public class MetaweatherService {
	private static final String URL_LOCATION_SEARCH = "https://www.metaweather.com/api/location/search/?lattlong=";
	private static final String URL_WEATHER_SEARCH = "https://www.metaweather.com/api/location/";
	
	/** 
	 * Busca WOEID (Where On Earth ID)  baseado na latitude e longitude
	 * 
	 * @param latLong
	 * @param template
	 * @throws ExternalErrorException se houver falha na comunicação com o servidor.
	 * @return Inteiro com o valor do woeid
	 */
	public int getWoeid(String latLong, RestTemplate template) throws ExternalErrorException {
		
		LocationSearchDTO[] locationSearch;
		
		try{
			locationSearch = template.getForObject(URL_LOCATION_SEARCH 
				+ latLong, LocationSearchDTO[].class);
		}catch(RestClientException e) {
			e.printStackTrace();
			throw new ExternalErrorException(this.getClass(), "Falha ao tentar se comunicar com Serviço de Clima "
					+ "MetaWeather. Favor tentar mais tarde.");
		}
		
		return locationSearch[0].getWoeid();
	}
	
	/** 
	 * Obtem informações do clima do woeid informado.
	 * 
	 * @param woeid - Where On Earth ID
	 * @param template a new RestTemplate
	 * @throws ExternalErrorException se houver falha na comunicação com o servidor.
	 * @return WeatherClientDTO com informações do clima
	 */
	public WeatherClientDTO getInfoClima(int woeid, RestTemplate template) throws ExternalErrorException {
		WeatherClientDTO weatherInfo;
		
		try{
			weatherInfo = template.getForObject(URL_WEATHER_SEARCH 
				+ woeid, WeatherClientDTO.class);
		}catch(RestClientException e) {
			e.printStackTrace();
			throw new ExternalErrorException(this.getClass(), "Falha ao tentar se comunicar com Serviço de Clima "
					+ "MetaWeather. Favor tentar mais tarde.");
		}
		return weatherInfo;
	}
}
