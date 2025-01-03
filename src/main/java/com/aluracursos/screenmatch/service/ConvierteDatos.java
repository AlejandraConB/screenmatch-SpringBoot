package com.aluracursos.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos{
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public <T> T obtenerDatos(String json, Class<T> clase) {
		// TODO Auto-generated method stub
		try {
			return objectMapper.readValue(json, clase);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
