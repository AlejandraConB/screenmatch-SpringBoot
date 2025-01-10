package com.aluracursos.screenmatch.principal;

import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
 
	public void muestraEjemplo() {
		List<String> nombres = Arrays.asList("Brenda", "Luis", "María Fernanda", "Eric", "Genesys");
		
		nombres.stream()
		.sorted()
		.limit( maxSize: 4)
		.filter(n => )
		.forEach(System.out::println);
	}
}
