package unnoba.ai.back.demo.constants;

public class ScrapingConstants {

  // Configuración de conexión
  public static final int TIMEOUT_MS = 15000;
  public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";

  // Selectores CSS para scraping de calendario
  public static final String[] CALENDAR_EVENT_SELECTORS = {
      ".tribe-events-list-event-title",
      ".tribe-event-title",
      ".event-title",
      "[class*='event']",
      ".tribe-events-calendar-list__event-title",
      "h3:contains(2025)",
      "h4:contains(2025)",
      "*:contains(Turno)",
      "*:contains(Fecha de Examen)",
      "*:contains(Inscripción)",
      "*:contains(Confirmación)",
      "*:contains(Inicio)",
      "*:contains(Final)",
      "*:contains(Feriado)",
      "*:contains(No Laborable)"
  };

  // Patrones de fechas para extracción
  public static final String[] DATE_PATTERNS = {
      "\\d{4}-\\d{2}-\\d{2}", // 2025-06-15
      "\\d{1,2}\\s+de\\s+\\w+\\s+de\\s+\\d{4}", // 15 de junio de 2025
      "\\w+\\s+\\d{1,2}\\s+al?\\s+\\d{1,2}", // junio 9 al 14
      "\\d{1,2}/\\d{1,2}/\\d{4}" // 15/06/2025
  };

  // Palabras clave para filtrado de contenido relevante
  public static final String[] RELEVANT_KEYWORDS = {
      "inscripción", "inscripcion", "examen", "final", "turno", "fecha",
      "confirmación", "confirmacion", "inicio", "fin", "feriado",
      "no laborable", "clase", "semestre", "cuatrimestre", "mesa",
      "enero", "febrero", "marzo", "abril", "mayo", "junio",
      "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre",
      "2025"
  };

  // Palabras clave para filtrar contenido irrelevante
  public static final String[] IRRELEVANT_KEYWORDS = {
      "menú", "copyright", "search", "login", "seguinos", "contacto",
      "drive.google.com", "inscripcion"
  };

  // Límites de texto
  public static final int MIN_TEXT_LENGTH = 10;
  public static final int MAX_TEXT_LENGTH = 300;
  public static final int MAX_SEARCH_DEPTH = 2;
  public static final int MAX_RESULTS_PER_CATEGORY = 6;

  // URLs que deben ser excluidas del scraping
  public static final String[] EXCLUDED_URL_PATTERNS = {
      "drive.google.com",
      "inscripcion"
  };

  // Meses del año
  public static final String[] MONTHS_ES = {
      "enero", "febrero", "marzo", "abril", "mayo", "junio",
      "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
  };

  private ScrapingConstants() {
    // Private constructor to prevent instantiation
  }
}