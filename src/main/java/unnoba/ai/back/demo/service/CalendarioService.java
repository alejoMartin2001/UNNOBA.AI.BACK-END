package unnoba.ai.back.demo.service;

import org.springframework.stereotype.Service;
import unnoba.ai.back.demo.constants.ScrapingConstants;
import unnoba.ai.back.demo.constants.UnnobaUrls;
import unnoba.ai.back.demo.dto.FechasInscripcionDto;
import unnoba.ai.back.demo.utils.ScrapingUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalendarioService {

  private final ScrapingService scrapingService;

  public CalendarioService(ScrapingService scrapingService) {
    this.scrapingService = scrapingService;
  }

  /**
   * Obtiene información completa del calendario académico
   */
  public String obtenerInformacionCalendario() throws IOException {
    Set<String> informacionExtraida = scrapingService.buscarInformacionConProfundidad(
        UnnobaUrls.CALENDARIO_URL, ScrapingConstants.MAX_SEARCH_DEPTH);

    StringBuilder info = new StringBuilder();
    info.append("📅 **CALENDARIO ACADÉMICO UNNOBA**\n\n");
    info.append(
        "El calendario académico oficial de la UNNOBA contiene todas las fechas importantes del año lectivo.\n\n");
    info.append("**Acceso directo:** ").append(UnnobaUrls.CALENDARIO_URL).append("\n\n");

    return info.toString();
  }

  /**
   * Extrae fechas específicas según el tipo de consulta
   */
  public String extraerFechasEspecificas(String tipoConsulta) throws IOException {
    Set<String> informacionExtraida = scrapingService.buscarInformacionConProfundidad(
        UnnobaUrls.CALENDARIO_URL, ScrapingConstants.MAX_SEARCH_DEPTH);

    StringBuilder resultado = new StringBuilder();
    resultado.append("📅 **Información encontrada:**\n\n");
    resultado.append("• Consultá el calendario académico para información actualizada: ")
        .append(UnnobaUrls.CALENDARIO_URL).append("\n");

    return resultado.toString();
  }

  /**
   * Extrae fechas de inscripción a materias
   */
  public String extraerFechasInscripcionMaterias() throws IOException {
    StringBuilder respuesta = new StringBuilder();
    respuesta.append("📝 **FECHAS DE INSCRIPCIÓN A MATERIAS - UNNOBA**\n\n");
    respuesta.append("🔗 **Sistema SIU-Guaraní:** ").append(UnnobaUrls.BASE_SIU_URL).append("\n");
    respuesta.append("📅 **Calendario completo:** ").append(UnnobaUrls.CALENDARIO_URL);

    return respuesta.toString();
  }

  /**
   * Extrae fechas específicas para primer cuatrimestre
   */
  public String extraerFechasInscripcionPrimerCuatrimestre() throws IOException {
    return extraerFechasInscripcionMaterias(); // Reutiliza la lógica existente
  }

  /**
   * Extrae fechas específicas para segundo cuatrimestre
   */
  public String extraerFechasInscripcionSegundoCuatrimestre() throws IOException {
    FechasInscripcionDto fechas = extraerFechasDelCalendarioInscripciones();

    StringBuilder respuesta = new StringBuilder();
    respuesta.append("📝 **FECHAS DE INSCRIPCIÓN - SEGUNDO CUATRIMESTRE**\n\n");

    if (fechas.getFechaInicioSegundoCuatrimestre() != null || fechas.getFechaFinSegundoCuatrimestre() != null) {
      respuesta.append("**📚 SEGUNDO CUATRIMESTRE - Materias Regulares:**\n");
      if (fechas.getFechaInicioSegundoCuatrimestre() != null) {
        respuesta.append("• **Inicio:** ").append(fechas.getFechaInicioSegundoCuatrimestre()).append("\n");
      }
      if (fechas.getFechaFinSegundoCuatrimestre() != null) {
        respuesta.append("• **Fin:** ").append(fechas.getFechaFinSegundoCuatrimestre()).append("\n");
      }
      respuesta.append("\n");
    }

    respuesta.append("🔗 **Sistema SIU-Guaraní:** ").append(UnnobaUrls.BASE_SIU_URL).append("\n");
    respuesta.append("📅 **Calendario completo:** ").append(UnnobaUrls.CALENDARIO_URL);

    return respuesta.toString();
  }

  /**
   * Extrae información detallada de inscripción a materias
   */
  public String extraerFechasInscripcionMateriasDetallada() throws IOException {
    Set<String> informacionExtraida = scrapingService.buscarInformacionConProfundidad(
        UnnobaUrls.CALENDARIO_URL, ScrapingConstants.MAX_SEARCH_DEPTH);

    StringBuilder respuesta = new StringBuilder();
    respuesta.append("📝 **INSCRIPCIÓN A MATERIAS - INFORMACIÓN DETALLADA**\n\n");

    // Filtrar información específica sobre inscripciones
    List<String> inscripciones = informacionExtraida.stream()
        .filter(info -> {
          String infoLower = info.toLowerCase();
          return (infoLower.contains("inscripción") || infoLower.contains("inscripcion")) &&
              (infoLower.contains("materia") || infoLower.contains("regular") || infoLower.contains("pendiente"));
        })
        .distinct()
        .limit(10)
        .collect(Collectors.toList());

    if (!inscripciones.isEmpty()) {
      respuesta.append("**📋 Información encontrada:**\n");
      inscripciones.forEach(info -> respuesta.append("• ").append(info).append("\n"));
      respuesta.append("\n");
    }

    respuesta.append("**💡 Información importante:**\n");
    respuesta.append("• Las fechas pueden variar según la carrera\n");
    respuesta.append("• Verificá siempre en el sistema SIU-Guaraní\n");
    respuesta.append("• Consultá el calendario académico para fechas exactas\n\n");

    respuesta.append("🔗 **Sistema SIU-Guaraní:** ").append(UnnobaUrls.BASE_SIU_URL).append("\n");
    respuesta.append("📅 **Calendario completo:** ").append(UnnobaUrls.CALENDARIO_URL);

    return respuesta.toString();
  }

  /**
   * Consulta fecha específica mediante texto libre
   */
  public String consultarFechaEspecifica(String consulta) throws IOException {
    Set<String> informacionExtraida = scrapingService.buscarInformacionConProfundidad(
        UnnobaUrls.CALENDARIO_URL, ScrapingConstants.MAX_SEARCH_DEPTH);

    String consultaLower = consulta.toLowerCase();

    // Buscar información relacionada con la consulta
    List<String> resultados = informacionExtraida.stream()
        .filter(info -> {
          String infoLower = info.toLowerCase();
          return Arrays.stream(consultaLower.split("\\s+"))
              .anyMatch(palabra -> infoLower.contains(palabra) && palabra.length() > 3);
        })
        .distinct()
        .limit(5)
        .collect(Collectors.toList());

    StringBuilder respuesta = new StringBuilder();
    respuesta.append("🔍 **Resultados para: \"").append(consulta).append("\"**\n\n");

    if (!resultados.isEmpty()) {
      respuesta.append("**📋 Información encontrada:**\n");
      resultados.forEach(resultado -> respuesta.append("• ").append(resultado).append("\n"));
      respuesta.append("\n");
    } else {
      respuesta.append("• No se encontró información específica para tu consulta\n");
      respuesta.append("• Intentá con términos más generales\n");
      respuesta.append("• Consultá directamente el calendario académico\n\n");
    }

    respuesta.append("📅 **Calendario completo:** ").append(UnnobaUrls.CALENDARIO_URL);

    return respuesta.toString();
  }

  // Métodos privados auxiliares

  private Map<String, List<String>> organizarEventosPorCategoria(Set<String> informacionExtraida) {
    Map<String, List<String>> categorias = new HashMap<>();
    categorias.put("inscripciones", new ArrayList<>());
    categorias.put("examenes", new ArrayList<>());
    categorias.put("feriados", new ArrayList<>());
    categorias.put("clases", new ArrayList<>());
    categorias.put("confirmaciones", new ArrayList<>());

    for (String informacion : informacionExtraida) {
      String infoLower = informacion.toLowerCase();

      if ((infoLower.contains("inscripción") || infoLower.contains("inscripcion")) &&
          (infoLower.contains("turno") || infoLower.contains("inicio") || infoLower.contains("fecha"))) {
        categorias.get("inscripciones").add(informacion);
      } else if ((infoLower.contains("examen") || infoLower.contains("final") || infoLower.contains("turno")) &&
          (infoLower.contains("fecha") || infoLower.contains("mesa") || infoLower.contains("2025"))) {
        categorias.get("examenes").add(informacion);
      } else if (infoLower.contains("confirmación") || infoLower.contains("confirmacion")) {
        categorias.get("confirmaciones").add(informacion);
      } else if (infoLower.contains("feriado") || infoLower.contains("no laborable") ||
          infoLower.contains("día no laborable")) {
        categorias.get("feriados").add(informacion);
      } else if ((infoLower.contains("inicio") || infoLower.contains("fin")) &&
          (infoLower.contains("clase") || infoLower.contains("semestre") || infoLower.contains("cuatrimestre"))) {
        categorias.get("clases").add(informacion);
      }
    }

    return categorias;
  }

  private void construirRespuestaOrganizada(StringBuilder info, Map<String, List<String>> eventosPorCategoria) {
    if (!eventosPorCategoria.get("inscripciones").isEmpty()) {
      info.append("📝 **INSCRIPCIONES:**\n");
      eventosPorCategoria.get("inscripciones").stream().distinct().limit(4)
          .forEach(evento -> info.append("• ").append(evento).append("\n"));
      info.append("\n");
    }

    if (!eventosPorCategoria.get("examenes").isEmpty()) {
      info.append("📋 **EXÁMENES:**\n");
      eventosPorCategoria.get("examenes").stream().distinct().limit(4)
          .forEach(evento -> info.append("• ").append(evento).append("\n"));
      info.append("\n");
    }

    if (!eventosPorCategoria.get("clases").isEmpty()) {
      info.append("🎓 **CLASES Y CUATRIMESTRES:**\n");
      eventosPorCategoria.get("clases").stream().distinct().limit(3)
          .forEach(evento -> info.append("• ").append(evento).append("\n"));
      info.append("\n");
    }

    if (eventosPorCategoria.values().stream().allMatch(List::isEmpty)) {
      info.append(
          "**💡 Información importante:** El calendario se actualiza regularmente, por eso es recomendable consultarlo frecuentemente.\n\n");
      info.append("Para obtener información específica sobre fechas de exámenes, inscripciones y eventos académicos, ");
      info.append("consultá directamente el calendario oficial.\n");
    }
  }

  private List<String> filtrarEventosPorTipo(Set<String> informacionExtraida, String tipoConsulta) {
    return informacionExtraida.stream()
        .filter(informacion -> {
          String infoLower = informacion.toLowerCase();
          return switch (tipoConsulta.toLowerCase()) {
            case "inicio_cuatrimestre", "inicio_primer_cuatrimestre", "inicio_segundo_cuatrimestre" ->
              (infoLower.contains("inicio") &&
                  (infoLower.contains("semestre") || infoLower.contains("cuatrimestre") || infoLower.contains("clase")))
                  ||
                  (infoLower.contains("comienzo") && infoLower.contains("clase"));

            case "fin_cuatrimestre", "fin_primer_cuatrimestre", "fin_segundo_cuatrimestre" ->
              (infoLower.contains("fin") || infoLower.contains("término") ||
                  infoLower.contains("termino") || infoLower.contains("finalización")) &&
                  (infoLower.contains("semestre") || infoLower.contains("cuatrimestre") || infoLower.contains("clase"));

            case "examenes", "mesas_finales" ->
              (infoLower.contains("examen") || infoLower.contains("final") || infoLower.contains("turno")) &&
                  (infoLower.contains("fecha") || infoLower.contains("mesa") || infoLower.contains("inscripción"));

            case "confirmacion", "inscripcion_carrera" ->
              infoLower.contains("confirmación") || infoLower.contains("confirmacion") ||
                  (infoLower.contains("inscripción") && infoLower.contains("carrera"));

            case "feriados" ->
              infoLower.contains("feriado") || infoLower.contains("no laborable") ||
                  infoLower.contains("día no laborable");

            default -> false;
          };
        })
        .filter(info -> ScrapingUtils.esTextoValido(info))
        .collect(Collectors.toList());
  }

  private FechasInscripcionDto extraerFechasDelCalendarioInscripciones() throws IOException {
    FechasInscripcionDto fechas = new FechasInscripcionDto();

    // Implementar lógica de extracción de fechas específicas
    // Por ahora retorna un DTO básico
    fechas.setFechaInicioRegular("Consultar calendario académico");
    fechas.setFechaFinRegular("Consultar calendario académico");
    fechas.setUrlRegular(UnnobaUrls.CALENDARIO_URL);

    return fechas;
  }
}