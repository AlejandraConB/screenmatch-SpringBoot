package com.aluracursos.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos{
	
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public <T> T obtenerDatos(String json, Class<T> clase) {
		// TODO Auto-generated method stub
		try {
			return mapper.readValue(json, clase);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	

}
