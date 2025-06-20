package unnoba.ai.back.demo;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class App {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(App.class, args);
		//BusquedaEnlaces buscador = new BusquedaEnlaces();
     //   Set<String> resultados = buscador.buscarRecursivo();

       // System.out.println("Enlaces encontrados (" + resultados.size() + "):");
        //resultados.forEach(System.out::println);
				
	}
}
