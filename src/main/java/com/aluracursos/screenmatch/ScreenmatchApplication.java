package com.aluracursos.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		var consumoApi = new ConsumoAPI();
		var json = consumoApi.obtenerDatos(url: "https://www.omdbapi.com/?t=Game+of+Thrones&apikey=b9736b44");
	//	var json = consumoApi.obtenerDatos(url: "https://coffee.alexflipnote.dev/random");
		System.out.print(json);
		ConvierteDatos conversor = new ConvierteDatos();
		var datos = conversor.obtenerDatos(json, DatosSerie.class);
		System.out.print(datos);
	}

}
