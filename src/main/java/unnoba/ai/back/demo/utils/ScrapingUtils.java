package unnoba.ai.back.demo.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import unnoba.ai.back.demo.constants.ScrapingConstants;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScrapingUtils {

  /**
   * Conecta a una URL y obtiene el documento HTML
   */
  public static Document conectarUrl(String url) throws IOException {
    return Jsoup.connect(url)
        .timeout(ScrapingConstants.TIMEOUT_MS)
        .userAgent(ScrapingConstants.USER_AGENT)
        .get();
  }

  /**
   * Extrae texto de todas las URLs proporcionadas
   */
  public static String extraerTextoDeUrls(Set<String> urls) {
    StringBuilder texto = new StringBuilder();
    Set<String> textosUnicos = new LinkedHashSet<>();

    for (String url : urls) {
      try {
        Document doc = conectarUrl(url);
        Elements elementos = doc.select("*");

        for (Element elemento : elementos) {
          String contenido = elemento.ownText().trim();
          if (!contenido.isEmpty()) {
            textosUnicos.add(contenido);
          }
        }

        for (String linea : textosUnicos) {
          texto.append(linea).append(" ");
        }
      } catch (IOException e) {
        System.err.println("Error al acceder a: " + url + " -> " + e.getMessage());
      }
    }

    return texto.toString();
  }

  /**
   * Verifica si una URL debe ser excluida del scraping
   */
  public static boolean esUrlExcluida(String url) {
    for (String patron : ScrapingConstants.EXCLUDED_URL_PATTERNS) {
      if (url.contains(patron)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Verifica si el texto contiene información relevante
   */
  public static boolean contieneInformacionRelevante(String texto) {
    String textoLower = texto.toLowerCase();

    // Verificar palabras irrelevantes
    for (String palabra : ScrapingConstants.IRRELEVANT_KEYWORDS) {
      if (textoLower.contains(palabra)) {
        return false;
      }
    }

    // Verificar palabras relevantes
    for (String palabra : ScrapingConstants.RELEVANT_KEYWORDS) {
      if (textoLower.contains(palabra)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Limpia el texto del evento manteniendo solo información relevante
   */
  public static String limpiarTextoEvento(String texto) {
    if (texto.length() > 150) {
      // Buscar fechas en el texto
      for (String patron : ScrapingConstants.DATE_PATTERNS) {
        Pattern fechaPattern = Pattern.compile(patron);
        Matcher matcher = fechaPattern.matcher(texto);

        if (matcher.find()) {
          int start = Math.max(0, matcher.start() - 30);
          int end = Math.min(texto.length(), matcher.end() + 50);
          return texto.substring(start, end).trim();
        }
      }
      return texto.substring(0, 150) + "...";
    }
    return texto;
  }

  /**
   * Busca fechas específicas en el texto usando patrones regex
   */
  public static Set<String> buscarFechasEnTexto(String texto) {
    Set<String> fechasEncontradas = new HashSet<>();

    for (String patron : ScrapingConstants.DATE_PATTERNS) {
      Pattern pattern = Pattern.compile(patron, Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(texto);

      while (matcher.find()) {
        int inicio = Math.max(0, matcher.start() - 50);
        int fin = Math.min(texto.length(), matcher.end() + 50);
        String contexto = texto.substring(inicio, fin).trim();

        if (contieneInformacionRelevante(contexto)) {
          fechasEncontradas.add(limpiarTextoEvento(contexto));
        }
      }
    }

    return fechasEncontradas;
  }

  /**
   * Valida que el texto tenga una longitud apropiada
   */
  public static boolean esTextoValido(String texto) {
    return texto != null &&
        texto.length() >= ScrapingConstants.MIN_TEXT_LENGTH &&
        texto.length() <= ScrapingConstants.MAX_TEXT_LENGTH;
  }

  /**
   * Extrae enlaces de planes de estudio de un documento
   */
  public static Set<String> extraerEnlacesPlanesEstudio(Document doc, String urlBase) {
    Set<String> enlaces = new HashSet<>();
    enlaces.add(urlBase);

    Elements links = doc.select("a[href]");
    for (Element link : links) {
      String href = link.absUrl("href");

      if (href.startsWith("https://planesdeestudio.unnoba.edu.ar/?planversion=")
          && !enlaces.contains(href)) {
        enlaces.add(href);
      }

      if (esUrlExcluida(href)) {
        continue;
      }
    }

    return enlaces;
  }

  /**
   * Extrae información específica usando los selectores del calendario
   */
  public static Set<String> extraerEventosCalendario(Document doc) {
    Set<String> eventos = new HashSet<>();

    for (String selector : ScrapingConstants.CALENDAR_EVENT_SELECTORS) {
      try {
        Elements elementos = doc.select(selector);
        for (Element elemento : elementos) {
          String texto = elemento.text().trim();
          if (esTextoValido(texto) && contieneInformacionRelevante(texto)) {
            eventos.add(limpiarTextoEvento(texto));
          }
        }
      } catch (Exception e) {
        // Continuar con otros selectores si uno falla
        System.err.println("Error con selector: " + selector + " -> " + e.getMessage());
      }
    }

    return eventos;
  }
}