package unnoba.ai.back.demo.service;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import unnoba.ai.back.demo.utils.ScrapingUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ScrapingService {

  /**
   * Procesa una carrera y obtiene todos los enlaces relacionados
   */
  public Set<String> procesarCarrera(String urlCarrera) throws IOException {
    Set<String> visitados = new HashSet<>();

    try {
      Document doc = ScrapingUtils.conectarUrl(urlCarrera);
      visitados = ScrapingUtils.extraerEnlacesPlanesEstudio(doc, urlCarrera);
    } catch (IOException e) {
      System.err.println("Error accediendo a: " + urlCarrera + " -> " + e.getMessage());
      throw e;
    }

    return visitados;
  }

  /**
   * Procesa múltiples URLs y extrae información específica
   */
  public Set<String> procesarMultiplesUrls(Set<String> urls) {
    Set<String> informacionExtraida = new HashSet<>();

    for (String url : urls) {
      try {
        Document doc = ScrapingUtils.conectarUrl(url);
        Set<String> eventos = ScrapingUtils.extraerEventosCalendario(doc);
        informacionExtraida.addAll(eventos);
      } catch (IOException e) {
        System.err.println("Error accediendo a: " + url + " -> " + e.getMessage());
      }
    }

    return informacionExtraida;
  }

  /**
   * Busca información específica en una URL con profundidad limitada
   */
  public Set<String> buscarInformacionConProfundidad(String url, int profundidadMaxima) {
    Set<String> informacionExtraida = new HashSet<>();
    Set<String> visitados = new HashSet<>();

    buscarInformacionRecursiva(url, 0, profundidadMaxima, visitados, informacionExtraida);

    return informacionExtraida;
  }

  /**
   * Método recursivo para buscar información con profundidad
   */
  private void buscarInformacionRecursiva(String url, int profundidadActual, int profundidadMaxima,
      Set<String> visitados, Set<String> informacionExtraida) {
    if (profundidadActual > profundidadMaxima || visitados.contains(url)) {
      return;
    }

    try {
      Document doc = ScrapingUtils.conectarUrl(url);
      visitados.add(url);

      // Extraer información específica
      Set<String> eventos = ScrapingUtils.extraerEventosCalendario(doc);
      informacionExtraida.addAll(eventos);

      // Continuar con enlaces relacionados si no hemos alcanzado la profundidad
      // máxima
      if (profundidadActual < profundidadMaxima) {
        buscarEnlacesRelacionados(doc, profundidadActual, profundidadMaxima, visitados, informacionExtraida);
      }

    } catch (IOException e) {
      System.err.println("Error accediendo a: " + url + " -> " + e.getMessage());
    }
  }

  /**
   * Busca enlaces relacionados en un documento
   */
  private void buscarEnlacesRelacionados(Document doc, int profundidadActual, int profundidadMaxima,
      Set<String> visitados, Set<String> informacionExtraida) {
    var links = doc.select("a[href]");

    for (var link : links) {
      String href = link.absUrl("href");
      String textoLink = link.text().toLowerCase();

      // Solo seguir enlaces relevantes
      if (href.contains("elegi.unnoba.edu.ar") &&
          (textoLink.contains("calendario") || textoLink.contains("evento") ||
              textoLink.contains("fecha") || textoLink.contains("examen"))
          &&
          !ScrapingUtils.esUrlExcluida(href) &&
          !visitados.contains(href)) {

        buscarInformacionRecursiva(href, profundidadActual + 1, profundidadMaxima, visitados, informacionExtraida);
      }
    }
  }
}