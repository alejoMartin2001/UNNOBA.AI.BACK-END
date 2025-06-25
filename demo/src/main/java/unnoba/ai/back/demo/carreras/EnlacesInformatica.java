package unnoba.ai.back.demo.carreras;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class EnlacesInformatica {

    private Set<String> visitados = new HashSet<>();

    public Set<String> ingenieriaInformatica() {
        procesarCarrera("https://elegi.unnoba.edu.ar/ingenieria-en-informatica/");
        return visitados;
    }

    public Set<String> licenciaturaSistemas() {
        procesarCarrera("https://elegi.unnoba.edu.ar/licenciatura-en-sistemas/");
        return visitados;
    }

    public Set<String> analistaSistemas() {
        procesarCarrera("https://elegi.unnoba.edu.ar/analista-de-sistemas/");
        return visitados;
    }

    public Set<String> disYDesarrolloDeAps() {
        procesarCarrera("https://elegi.unnoba.edu.ar/tecnicatura-en-diseno-y-desarrollo-de-aplicaciones-multiplataforma/");
        return visitados;
    }

    private void procesarCarrera(String urlCarrera) {
        try {
            Document doc = Jsoup.connect(urlCarrera)
                    .timeout(10000)
                    .userAgent("Mozilla/5.0")
                    .get();
            visitados.add(urlCarrera);
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String href = link.absUrl("href");

                if (
                    href.startsWith("https://planesdeestudio.unnoba.edu.ar/?planversion=")
                    && !visitados.contains(href)
                ) {
                    visitados.add(href);
                }

                // NO agregar enlaces a Drive ni a inscripción
                if (href.contains("drive.google.com") || href.contains("inscripcion")) {
                    continue;
                }
            }

        } catch (IOException e) {
            System.err.println("Error accediendo a: " + urlCarrera + " -> " + e.getMessage());
        }
    }

    
}
