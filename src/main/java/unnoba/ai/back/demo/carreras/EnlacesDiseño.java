package unnoba.ai.back.demo.carreras;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class EnlacesDiseño {

    private Set<String> visitados = new HashSet<>();

    public Set<String> buscarLICENCIATURADISEÑOINDUMENTARIAYTEXTIL() {
      int profundidadMaxima = 3;
      buscar("https://elegi.unnoba.edu.ar/licenciatura-en-disenio-de-indumentaria-y-textil/",0 ,profundidadMaxima);
      return visitados;
    }

    public Set<String> buscarLicenciaturaDiseñoGrafico() {
      int profundidadMaxima = 3;
      buscar("https://elegi.unnoba.edu.ar/licenciatura-en-disenio-grafico/",0 ,profundidadMaxima);
      return visitados;
    }

    public Set<String> buscarLicenciaturaDiseñoIndustrial() {
      int profundidadMaxima = 3;
      buscar("https://elegi.unnoba.edu.ar/licenciatura-en-disenio-industrial/",0 ,profundidadMaxima);
      return visitados;
    }

    private void buscar(String url, int profundidaActual, int profundidadMaxima) {
        if (profundidaActual > profundidadMaxima) return;
        try {
            Document doc = Jsoup.connect(url)
                    .timeout(10000)
                    .userAgent("Mozilla/5.0")
                    .get();

            visitados.add(url); // Marca como visitado

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
        }catch (IOException e) {
            System.err.println("Error accediendo a: " + url + " -> " + e.getMessage());
        }
    }
}

