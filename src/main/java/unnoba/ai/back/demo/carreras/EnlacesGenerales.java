package unnoba.ai.back.demo.carreras;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class EnlacesGenerales {

    private Set<String> visitados = new HashSet<>();

    public Set<String> medicina() {
        procesarCarrera("https://elegi.unnoba.edu.ar/medicina/");
        return visitados;
    }

    public Set<String> enfermeria() {
        procesarCarrera("https://elegi.unnoba.edu.ar/enfermeria/");
        return visitados;
    }

    public Set<String> abogacia() {
        procesarCarrera("https://elegi.unnoba.edu.ar/abogacia/");
        return visitados;
    }

    public Set<String> contadorPublico() {
        procesarCarrera("https://elegi.unnoba.edu.ar/contador-publico/");
        return visitados;
    }

    public Set<String> agronomia() {
        procesarCarrera("https://elegi.unnoba.edu.ar/agronomia/");
        return visitados;
    }

    public Set<String> ingenieriaMecatronica() {
        procesarCarrera("https://elegi.unnoba.edu.ar/ingenieria-mecatronica/");
        return visitados;
    }

    public Set<String> licenciaturaGenetica() {
        procesarCarrera("https://elegi.unnoba.edu.ar/licenciatura-en-genetica/");
        return visitados;
    }

    public Set<String> ingenieriaIndustrial() {
        procesarCarrera("https://elegi.unnoba.edu.ar/ingenieria-industrial/");
        return visitados;
    }

    public Set<String> ingenieriaMecanica() {
        procesarCarrera("https://elegi.unnoba.edu.ar/ingenieria-mecanica/");
        return visitados;
    }

    public Set<String> tecnicaturaMantenimientoIndustrial() {
        procesarCarrera("https://elegi.unnoba.edu.ar/tecnicatura-en-mantenimiento-industrial/");
        return visitados;
    }

    public Set<String> licenciaturaAdministracion() {
        procesarCarrera("https://elegi.unnoba.edu.ar/licenciatura-en-administracion/");
        return visitados;
    }

    public Set<String> tecnicaturaGestionPymes() {
        procesarCarrera("https://elegi.unnoba.edu.ar/tecnicatura-en-gestion-de-pymes/");
        return visitados;
    }

    public Set<String> tecnicaturaGestionPublica() {
        procesarCarrera("https://elegi.unnoba.edu.ar/tecnicatura-en-gestion-publica/");
        return visitados;
    }

    public Set<String> ingenieriaAlimentos() {
        procesarCarrera("https://elegi.unnoba.edu.ar/ingenieria-en-alimentos/");
        return visitados;
    }

    public Set<String> tecnicaturaProduccionAlimentos() {
        procesarCarrera("https://elegi.unnoba.edu.ar/tecnicatura-universitaria-en-produccion-de-alimentos/");
        return visitados;
    }

    // Método general que ya conocés
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

                if (href.startsWith("https://planesdeestudio.unnoba.edu.ar/?planversion=")
                        && !visitados.contains(href)) {
                    visitados.add(href);
                }

                if (href.contains("drive.google.com") || href.contains("inscripcion")) {
                    continue;
                }
            }

        } catch (IOException e) {
            System.err.println("Error accediendo a: " + urlCarrera + " -> " + e.getMessage());
        }
    }
}
