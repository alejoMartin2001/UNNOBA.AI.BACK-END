package unnoba.ai.back.demo.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import unnoba.ai.back.demo.constants.ScrapingConstants;
import unnoba.ai.back.demo.constants.UnnobaUrls;
import unnoba.ai.back.demo.dto.FechasInscripcionDto;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;

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

  /**
   * Extrae fechas de exámenes para un mes específico desde el calendario
   * académico
   */
  public static String extraerFechasExamenesPorMes(String mes) throws IOException {
    String eventUrl = UnnobaUrls.CALENDARIO_URL + "fecha-de-examen-turno-" + mes.toLowerCase() + "-2/";

    try {
      Document eventPage = conectarUrl(eventUrl);

      Element titleElement = eventPage.select("h1").first();
      Element dateRangeElement = eventPage.select("h2").first();

      if (titleElement == null || dateRangeElement == null) {
        throw new IOException("No se pudo encontrar la información de la fecha de examen en la página para " + mes);
      }

      String title = titleElement.text();
      String dateRange = dateRangeElement.text();
      String formattedDate = formatDateRange(dateRange);

      // Formatea la respuesta
      StringBuilder respuesta = new StringBuilder();
      respuesta.append("📝 **").append(title.toUpperCase()).append("**\n\n");
      respuesta.append("• **Fechas:** ").append(formattedDate).append("\n");
      respuesta.append("• **Inscripción:** Recordá inscribirte a través del SIU-Guaraní con 48hs de anticipación.\n");

      return respuesta.toString();

    } catch (IOException e) {
      System.err.println("No se pudo acceder a la URL del evento para " + mes + ": " + eventUrl);
      throw e; // Lanza la excepción para que el servicio la maneje
    }
  }

  public static FechasInscripcionDto extraerFechasInscripcion(String url) throws IOException {
    try {
      Document eventPage = conectarUrl(url);

      Element titleElement = eventPage.select("h1").first();
      Element dateRangeElement = eventPage.select("h2").first();
      Element notesElement = eventPage.select(".tribe-events-single-event-description").first();

      if (titleElement == null || dateRangeElement == null) {
        throw new IOException("No se pudo encontrar la información del título o fecha en la página: " + url);
      }

      String title = titleElement.text();
      String dateRange = dateRangeElement.text();
      String notes = (notesElement != null) ? notesElement.text() : "";

      return new FechasInscripcionDto(title, dateRange, notes);

    } catch (IOException e) {
      System.err.println("No se pudo acceder a la URL de inscripción: " + url);
      throw e;
    }
  }

  public static String formatDateRange(String rangeString) {
    final int YEAR = 2025;
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    final Map<String, Month> monthMap = Map.ofEntries(
        Map.entry("enero", Month.JANUARY), Map.entry("febrero", Month.FEBRUARY),
        Map.entry("marzo", Month.MARCH), Map.entry("abril", Month.APRIL),
        Map.entry("mayo", Month.MAY), Map.entry("junio", Month.JUNE),
        Map.entry("julio", Month.JULY), Map.entry("agosto", Month.AUGUST),
        Map.entry("septiembre", Month.SEPTEMBER), Map.entry("octubre", Month.OCTOBER),
        Map.entry("noviembre", Month.NOVEMBER), Map.entry("diciembre", Month.DECEMBER));

    try {
      String[] parts = rangeString.toLowerCase(Locale.forLanguageTag("es-ES")).split("\\s*-\\s*");
      String[] startParts = parts[0].trim().split("\\s+");

      Month startMonth = monthMap.get(startParts[0]);
      int startDay = Integer.parseInt(startParts[1]);
      LocalDate startDate = LocalDate.of(YEAR, startMonth, startDay);

      if (parts.length == 1) {
        return startDate.format(formatter);
      }

      String[] endParts = parts[1].trim().split("\\s+");
      Month endMonth;
      int endDay;

      if (endParts.length > 1) {
        endMonth = monthMap.get(endParts[0]);
        endDay = Integer.parseInt(endParts[1]);
      } else {
        endMonth = startMonth;
        endDay = Integer.parseInt(endParts[0]);
      }

      LocalDate endDate = LocalDate.of(YEAR, endMonth, endDay);
      return startDate.format(formatter) + " - " + endDate.format(formatter);

    } catch (Exception e) {
      System.err.println("Error formateando el rango de fecha: " + rangeString + " -> " + e.getMessage());
      return rangeString; // Devuelve el original si falla el parseo
    }
  }
}