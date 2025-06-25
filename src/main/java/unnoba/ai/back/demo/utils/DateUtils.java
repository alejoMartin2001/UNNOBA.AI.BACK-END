package unnoba.ai.back.demo.utils;

import unnoba.ai.back.demo.constants.ScrapingConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {

  private static final Map<String, String> MESES_NUMEROS = new HashMap<>();

  static {
    MESES_NUMEROS.put("enero", "01");
    MESES_NUMEROS.put("febrero", "02");
    MESES_NUMEROS.put("marzo", "03");
    MESES_NUMEROS.put("abril", "04");
    MESES_NUMEROS.put("mayo", "05");
    MESES_NUMEROS.put("junio", "06");
    MESES_NUMEROS.put("julio", "07");
    MESES_NUMEROS.put("agosto", "08");
    MESES_NUMEROS.put("septiembre", "09");
    MESES_NUMEROS.put("octubre", "10");
    MESES_NUMEROS.put("noviembre", "11");
    MESES_NUMEROS.put("diciembre", "12");
  }

  /**
   * Convierte el nombre del mes en español a número
   */
  public static String convertirMesANumero(String mes) {
    return MESES_NUMEROS.getOrDefault(mes.toLowerCase(), "01");
  }

  /**
   * Capitaliza la primera letra del mes
   */
  public static String capitalizarMes(String mes) {
    if (mes == null || mes.isEmpty()) {
      return mes;
    }
    return mes.substring(0, 1).toUpperCase() + mes.substring(1).toLowerCase();
  }

  /**
   * Verifica si una fecha pertenece al mes especificado
   */
  public static boolean esFechaDelMes(String fecha, String mes) {
    String numeroMes = convertirMesANumero(mes);
    String mesLower = mes.toLowerCase();
    String fechaLower = fecha.toLowerCase();

    // Verificar por nombre del mes
    if (fechaLower.contains(mesLower)) {
      return true;
    }

    // Verificar por número de mes en formato MM
    if (fecha.contains("-" + numeroMes + "-") || fecha.contains("/" + numeroMes + "/")) {
      return true;
    }

    return false;
  }

  /**
   * Extrae y formatea una fecha desde texto
   */
  public static String formatearFechaDesdeTexto(String textoFecha, String mes) {
    if (textoFecha == null || textoFecha.trim().isEmpty()) {
      return "Fecha no disponible";
    }

    String texto = textoFecha.trim();

    // Si contiene el mes, intentar formatear
    if (esFechaDelMes(texto, mes)) {
      return limpiarFormatoFecha(texto);
    }

    return "Fecha no disponible para " + capitalizarMes(mes);
  }

  /**
   * Limpia el formato de fecha eliminando caracteres innecesarios
   */
  public static String limpiarFormatoFecha(String fecha) {
    return fecha.replaceAll("\\s+", " ")
        .replaceAll("^[^\\w\\d]+", "")
        .replaceAll("[^\\w\\d\\s/\\-:]+$", "")
        .trim();
  }

  /**
   * Obtiene la fecha actual formateada
   */
  public static String obtenerFechaActual() {
    return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }

  /**
   * Verifica si un texto contiene fechas del mes especificado
   */
  public static boolean contieneFechasDelMes(String texto, String mes) {
    if (texto == null || mes == null) {
      return false;
    }

    String textoLower = texto.toLowerCase();
    String mesLower = mes.toLowerCase();
    String numeroMes = convertirMesANumero(mes);

    return textoLower.contains(mesLower) ||
        texto.contains("-" + numeroMes + "-") ||
        texto.contains("/" + numeroMes + "/");
  }

  /**
   * Extrae rango de fechas de un texto
   */
  public static String[] extraerRangoFechas(String texto) {
    String[] rango = new String[2];

    // Buscar patrones como "del X al Y de mes"
    if (texto.contains(" al ") && texto.contains(" de ")) {
      String[] partes = texto.split(" al ");
      if (partes.length == 2) {
        rango[0] = partes[0].trim();
        rango[1] = partes[1].trim();
        return rango;
      }
    }

    // Buscar patrones con guión
    if (texto.contains(" - ")) {
      String[] partes = texto.split(" - ");
      if (partes.length == 2) {
        rango[0] = partes[0].trim();
        rango[1] = partes[1].trim();
        return rango;
      }
    }

    // Si no se encuentra rango, devolver el texto completo
    rango[0] = texto;
    rango[1] = texto;
    return rango;
  }

  /**
   * Valida si una cadena representa una fecha válida
   */
  public static boolean esFechaValida(String fecha) {
    if (fecha == null || fecha.trim().isEmpty()) {
      return false;
    }

    // Verificar patrones básicos de fecha
    return fecha.matches(".*\\d{1,2}.*\\d{4}.*") || // Contiene día y año
        fecha.matches(".*\\d{4}-\\d{2}-\\d{2}.*") || // Formato ISO
        fecha.matches(".*\\d{1,2}/\\d{1,2}/\\d{4}.*"); // Formato DD/MM/YYYY
  }

  /**
   * Obtiene el mes siguiente al proporcionado
   */
  public static String obtenerMesSiguiente(String mes) {
    String[] meses = ScrapingConstants.MONTHS_ES;
    for (int i = 0; i < meses.length; i++) {
      if (meses[i].equalsIgnoreCase(mes)) {
        return i == meses.length - 1 ? meses[0] : meses[i + 1];
      }
    }
    return mes;
  }

  /**
   * Obtiene el mes anterior al proporcionado
   */
  public static String obtenerMesAnterior(String mes) {
    String[] meses = ScrapingConstants.MONTHS_ES;
    for (int i = 0; i < meses.length; i++) {
      if (meses[i].equalsIgnoreCase(mes)) {
        return i == 0 ? meses[meses.length - 1] : meses[i - 1];
      }
    }
    return mes;
  }
}