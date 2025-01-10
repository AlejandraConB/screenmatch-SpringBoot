package com.aluracursos.screenmatch.principal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

public class Principal {
	private Scanner teclado = new Scanner(System.in);
	private ConsumoAPI consumoApi = new ConsumoAPI();
	private final String URL_BASE = "https://www.omdbapi.com/?t=";
	private final String API_KEY = "&apikey=b9736b44";
	private ConvierteDatos conversor = new ConvierteDatos();

	public void muestraElMenu() {
		System.out.print("Por favor escribe el nombre de la serie que deseas buscar");
//		Busca los datos generales de las series
		var nombreSerie = teclado.nextLine();
		var json = consumoApi.obtenerDatos(url: URL_BASE + nombreSerie.replace(target: " ", replacement: "+") + API_KEY);
		var datos = conversor.obtenerDatos(json, DatosSerie.class);
		System.out.print(datos);
		
//		Busca los datos de todas las temporadas
		
		List<DatosTemporadas> temporadas = new ArrayList<>();
		for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
			json= consumoApi.obtenerDatos(url: URL_BASE + nombreSerie.replace(target: " ", replacement: "+") + "&Season="+i+API_KEY);
			var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
			temporadas.add(datosTemporadas);
			
			temporadas.forEach(System.out::println);
			
//			Mostrar solo el título de los episodios para las temporadas
			for (int i = 0; i < datos.totalDeTemporadas(); i++) {
				List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();	
			for (int j = 0; j < episodiosTemporada.size(); i++) {
				System.out.println(episodiosTemporada.get(j).titulo());
			}
	}
//	Mejoria usando funciones Lambda
	temporadas.forEach(t => t.episodios().forEach(e => System.out.println(e.titulo())));
	
//	Convertir todas las informaciones a una lista del tipo DatosEpisodio
	
	List<DatosEpisodio> datosEpisodios = temporadas.stream() Stream<DatosTemporadas>
		.flatMap(t => t.episodios().stream))Stream<DatosEpisodio>
		.collect(Collectors.toList());
	
//	Obtener los Top 5 episodios
	System.out.println("Top 5 episodios");
	datosEpisodios.stream() Stream<DatosEpisodio>
		.filter(e => !e.evaluacion().equalsIgnoreCase(anotherString:"N/A"))
		.peek(e => System.out.println("Primer filtro (N/A)" + e))
		.sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
		.peek(e => System.out.println("Segunda ordenación (M>m)" + e))
		.map(e => e.titulo().toUpperCase()) Stream<String>
		.peek(e => System.out.println("Tercer filtro mayúscula (m>M)" + e))
		.limit(maxSize:5)
		.forEach(System.out::println);
	
//	Convirtiendo los datos a una lista del tipo Episodio
	
	List<Episodio> episodios = temporadas.stream() Stream<DatosTemporadas>
		.flatMap(t => t.episodios().stream()
		.map(d => new Episodio(t.numero(),d))Stream<Episodio>
		.collect(Collectors.toList());
		
	episodios.forEach(System.out::println);
	
//	Busqueda de episodios a partir de x año
	
	System.out.println("Por favor indica el año a partir del cual deseas ver los episodios");
	var fecha = teclado.nextInt();
	teclado.nextLine();
	
	LocalDate fechaBusqueda = LocalDate.of(fecha, month: 1, dayOfMonth: 1);
	
	DateTimeFormatter dtf = DatoTimeFormatter.ofPattern("dd/MM/yyyy");
//	episodios.stream()
//		.filter(e => e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaDeBusqueda))
//		.forEach();
	
//	Busca episodios por un pedazo del título
	System.out.println("Por favor escriba el título del episodio que desea ver");
	var pedazoTitulo = teclado.nextLine();
	Optional<Episodio> episodioBuscado = episodios.stream()
		.filter(e => e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
		.findFirst();
	if(episodioBuscado.isPresent()) {
		System.out.println("Episodio encontrado");
		System.out.println("Los datos son: " + episodio	Buscado.get());
	}else {
		System.out.println("Episodio no encontrado");
	
	Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
			.filter(e => e.getEvaluacion() > 0.0)
			.collect(Collectors.groupingBy(Episodio::getTemporada,
					Collectors.averagingDouble(Episodio::getEvaluacion)));
	System.out.println(evaluacionesPorTemporada);
	
	DoubleSummaryStatistics est = episodios.stream()
			.filter(e => e.getEvaluacion() > 0.0)
			.collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
	System.out.println("Media de las evaluaciones: " + est.getAverage());
	System.out.println("Episodio mejor evaluado: " + est.getMax());
	System.out.println("Episodio peor evaluado: " + est.getMin());
	}
}
