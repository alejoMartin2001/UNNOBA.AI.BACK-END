package unnoba.ai.back.demo.carreras;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnlacesInscripciones {

  private Set<String> visitados = new HashSet<>();
  private Set<String> informacionExtraida = new LinkedHashSet<>();

  public Set<String> inscripcionMaterias() {
    // URL principal del calendario académico
    procesarCalendario("https://elegi.unnoba.edu.ar/calendario/");

    // URLs adicionales relacionadas con inscripciones
    visitados.add("https://g3w3.unnoba.edu.ar/g3w3/");
    visitados.add("https://elegi.unnoba.edu.ar/inscripcion/");

    return visitados;
  }

  public Set<String> calendarioAcademico() {
    procesarCalendario("https://elegi.unnoba.edu.ar/calendario/");
    return visitados;
  }

  public Set<String> feriados() {
    procesarCalendario("https://elegi.unnoba.edu.ar/calendario/");
    return visitados;
  }

  private void procesarCalendario(String urlCalendario) {
    try {
      Document doc = Jsoup.connect(urlCalendario)
          .timeout(10000)
          .userAgent("Mozilla/5.0")
          .get();

      // Agrego la URL del calendario
      visitados.add(urlCalendario);

      // Buscar enlaces relacionados con calendario, inscripciones, etc.
      Elements links = doc.select("a[href]");
      for (Element link : links) {
        String href = link.absUrl("href");
        String texto = link.text().toLowerCase();

        // Enlaces útiles relacionados con inscripciones y calendario
        if ((href.contains("unnoba.edu.ar") || href.contains("elegi.unnoba.edu.ar")) &&
            (texto.contains("calendario") ||
                texto.contains("inscripción") ||
                texto.contains("inscripcion") ||
                texto.contains("académico") ||
                texto.contains("academico") ||
                texto.contains("examen") ||
                texto.contains("final"))
            &&
            !visitados.contains(href)) {

          visitados.add(href);
        }
      }

    } catch (IOException e) {
      System.err.println("Error accediendo a: " + urlCalendario + " -> " + e.getMessage());
    }
  }

  // Método mejorado para extraer información específica del calendario con
  // búsqueda en profundidad
  public String extraerInformacionCalendario() {
    informacionExtraida.clear();
    visitados.clear();

    try {
      // Buscar en la URL principal del calendario
      buscarInformacionCalendario("https://elegi.unnoba.edu.ar/calendario/", 0, 2);

      StringBuilder info = new StringBuilder();
      info.append("📅 **CALENDARIO ACADÉMICO UNNOBA**\n\n");
      info.append(
          "El calendario académico oficial de la UNNOBA contiene todas las fechas importantes del año lectivo.\n\n");
      info.append("**Acceso directo:** https://elegi.unnoba.edu.ar/calendario/\n\n");

      // Organizar información por categorías
      List<String> inscripciones = new ArrayList<>();
      List<String> examenes = new ArrayList<>();
      List<String> feriados = new ArrayList<>();
      List<String> clases = new ArrayList<>();
      List<String> confirmaciones = new ArrayList<>();

      for (String informacion : informacionExtraida) {
        String infoLower = informacion.toLowerCase();

        if ((infoLower.contains("inscripción") || infoLower.contains("inscripcion")) &&
            (infoLower.contains("turno") || infoLower.contains("inicio") || infoLower.contains("fecha"))) {
          inscripciones.add(informacion);
        } else if ((infoLower.contains("examen") || infoLower.contains("final") || infoLower.contains("turno")) &&
            (infoLower.contains("fecha") || infoLower.contains("mesa") || infoLower.contains("2025"))) {
          examenes.add(informacion);
        } else if (infoLower.contains("confirmación") || infoLower.contains("confirmacion")) {
          confirmaciones.add(informacion);
        } else if (infoLower.contains("feriado") || infoLower.contains("no laborable") ||
            infoLower.contains("día no laborable")) {
          feriados.add(informacion);
        } else if ((infoLower.contains("inicio") || infoLower.contains("fin")) &&
            (infoLower.contains("clase") || infoLower.contains("semestre") || infoLower.contains("cuatrimestre"))) {
          clases.add(informacion);
        }
      }

      // Construir respuesta organizada
      if (!inscripciones.isEmpty()) {
        info.append("📝 **INSCRIPCIONES:**\n");
        inscripciones.stream().distinct().limit(4).forEach(evento -> info.append("• ").append(evento).append("\n"));
        info.append("\n");
      }

      if (!examenes.isEmpty()) {
        info.append("📋 **EXÁMENES:**\n");
        examenes.stream().distinct().limit(4).forEach(evento -> info.append("• ").append(evento).append("\n"));
        info.append("\n");
      }

      if (!clases.isEmpty()) {
        info.append("🎓 **CLASES Y CUATRIMESTRES:**\n");
        clases.stream().distinct().limit(3).forEach(evento -> info.append("• ").append(evento).append("\n"));
        info.append("\n");
      }

      if (!feriados.isEmpty()) {
        info.append("🎉 **FERIADOS Y DÍAS NO LABORABLES:**\n");
        feriados.stream().distinct().limit(4).forEach(evento -> info.append("• ").append(evento).append("\n"));
        info.append("\n");
      }

      if (!confirmaciones.isEmpty()) {
        info.append("✅ **CONFIRMACIONES:**\n");
        confirmaciones.stream().distinct().limit(3).forEach(evento -> info.append("• ").append(evento).append("\n"));
        info.append("\n");
      }

      if (inscripciones.isEmpty() && examenes.isEmpty() && clases.isEmpty() && feriados.isEmpty()) {
        info.append(
            "**💡 Información importante:** El calendario se actualiza regularmente, por eso es recomendable consultarlo frecuentemente.\n\n");
        info.append(
            "Para obtener información específica sobre fechas de exámenes, inscripciones y eventos académicos, ");
        info.append("consultá directamente el calendario oficial.\n");
      }

      return info.toString();

    } catch (Exception e) {
      System.err.println("Error extrayendo información del calendario: " + e.getMessage());
      return "Error al acceder al calendario académico. Consultá directamente en: https://elegi.unnoba.edu.ar/calendario/";
    }
  }

  // Método para buscar información específica del calendario con profundidad
  private void buscarInformacionCalendario(String url, int profundidadActual, int profundidadMaxima) {
    if (profundidadActual > profundidadMaxima || visitados.contains(url))
      return;

    try {
      Document doc = Jsoup.connect(url)
          .timeout(15000)
          .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
          .get();

      visitados.add(url);

      // Extraer información específica del calendario
      extraerEventosEspecificos(doc);

      // Buscar enlaces relacionados solo si no hemos alcanzado la profundidad máxima
      if (profundidadActual < profundidadMaxima) {
        Elements links = doc.select("a[href]");
        for (Element link : links) {
          String href = link.absUrl("href");
          String textoLink = link.text().toLowerCase();

          // Solo seguir enlaces relevantes al calendario
          if (href.contains("elegi.unnoba.edu.ar") &&
              (textoLink.contains("calendario") || textoLink.contains("evento") ||
                  textoLink.contains("fecha") || textoLink.contains("examen"))
              &&
              !href.contains("drive.google.com") &&
              !href.contains("inscripcion") &&
              !visitados.contains(href)) {

            buscarInformacionCalendario(href, profundidadActual + 1, profundidadMaxima);
          }
        }
      }

    } catch (IOException e) {
      System.err.println("Error accediendo a: " + url + " -> " + e.getMessage());
    }
  }

  // Método para extraer eventos específicos del documento
  private void extraerEventosEspecificos(Document doc) {
    // Buscar eventos en diferentes estructuras HTML
    Set<String> selectores = Set.of(
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
        "*:contains(Fin)",
        "*:contains(Feriado)",
        "*:contains(No Laborable)");

    for (String selector : selectores) {
      try {
        Elements elementos = doc.select(selector);
        for (Element elemento : elementos) {
          procesarElementoEvento(elemento);
        }
      } catch (Exception e) {
        // Continuar con otros selectores si uno falla
        continue;
      }
    }

    // Buscar también en elementos que contengan fechas específicas
    buscarFechasEspecificas(doc);
  }

  // Método para procesar cada elemento de evento
  private void procesarElementoEvento(Element elemento) {
    String texto = elemento.text().trim();

    if (texto.length() < 10 || texto.length() > 300)
      return;

    String textoLower = texto.toLowerCase();

    // Filtrar contenido irrelevante
    if (textoLower.contains("menú") || textoLower.contains("copyright") ||
        textoLower.contains("search") || textoLower.contains("login") ||
        textoLower.contains("seguinos") || textoLower.contains("contacto")) {
      return;
    }

    // Solo agregar si contiene información relevante
    if (contieneInformacionRelevante(textoLower)) {
      String textoLimpio = limpiarTextoEvento(texto);
      if (!textoLimpio.isEmpty()) {
        informacionExtraida.add(textoLimpio);
      }
    }
  }

  // Método para verificar si el texto contiene información relevante
  private boolean contieneInformacionRelevante(String texto) {
    String[] palabrasClave = {
        "inscripción", "inscripcion", "examen", "final", "turno", "fecha",
        "confirmación", "confirmacion", "inicio", "fin", "feriado",
        "no laborable", "clase", "semestre", "cuatrimestre", "mesa",
        "enero", "febrero", "marzo", "abril", "mayo", "junio",
        "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre",
        "2025"
    };

    for (String palabra : palabrasClave) {
      if (texto.contains(palabra)) {
        return true;
      }
    }
    return false;
  }

  // Método para buscar fechas específicas en el documento
  private void buscarFechasEspecificas(Document doc) {
    // Patrones de fechas comunes
    String[] patronesFecha = {
        "\\d{4}-\\d{2}-\\d{2}", // 2025-06-15
        "\\d{1,2}\\s+de\\s+\\w+\\s+de\\s+\\d{4}", // 15 de junio de 2025
        "\\w+\\s+\\d{1,2}\\s+al?\\s+\\d{1,2}", // junio 9 al 14
        "\\d{1,2}/\\d{1,2}/\\d{4}" // 15/06/2025
    };

    String textoCompleto = doc.text();

    for (String patron : patronesFecha) {
      Pattern pattern = Pattern.compile(patron, Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(textoCompleto);

      while (matcher.find()) {
        int inicio = Math.max(0, matcher.start() - 50);
        int fin = Math.min(textoCompleto.length(), matcher.end() + 50);
        String contexto = textoCompleto.substring(inicio, fin).trim();

        if (contieneInformacionRelevante(contexto.toLowerCase())) {
          informacionExtraida.add(limpiarTextoEvento(contexto));
        }
      }
    }
  }

  // Método auxiliar para limpiar texto de eventos
  private String limpiarTextoEvento(String texto) {
    // Limpiar texto excesivo y mantener solo información relevante
    if (texto.length() > 150) {
      // Buscar fechas en el texto
      Pattern fechaPattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}|\\d{1,2}\\s+de\\s+\\w+|\\w+\\s+\\d{1,2}");
      Matcher matcher = fechaPattern.matcher(texto);

      if (matcher.find()) {
        int start = Math.max(0, matcher.start() - 30);
        int end = Math.min(texto.length(), matcher.end() + 50);
        return texto.substring(start, end).trim();
      }

      return texto.substring(0, 150) + "...";
    }
    return texto;
  }

  // Método para extraer fechas específicas del calendario
  public String extraerFechasEspecificas(String tipoConsulta) {
    informacionExtraida.clear();
    visitados.clear();

    try {
      // Buscar información específica en el calendario
      buscarInformacionCalendario("https://elegi.unnoba.edu.ar/calendario/", 0, 2);

      StringBuilder resultado = new StringBuilder();
      List<String> eventosEncontrados = new ArrayList<>();

      // Filtrar información según el tipo de consulta
      for (String informacion : informacionExtraida) {
        String infoLower = informacion.toLowerCase();
        boolean esRelevante = false;

        switch (tipoConsulta.toLowerCase()) {
          case "inicio_cuatrimestre":
          case "inicio_primer_cuatrimestre":
          case "inicio_segundo_cuatrimestre":
            if ((infoLower.contains("inicio") &&
                (infoLower.contains("semestre") || infoLower.contains("cuatrimestre") || infoLower.contains("clase")))
                ||
                (infoLower.contains("comienzo") && infoLower.contains("clase"))) {
              esRelevante = true;
            }
            break;

          case "fin_cuatrimestre":
          case "fin_primer_cuatrimestre":
          case "fin_segundo_cuatrimestre":
            if ((infoLower.contains("fin") || infoLower.contains("término") ||
                infoLower.contains("termino") || infoLower.contains("finalización")) &&
                (infoLower.contains("semestre") || infoLower.contains("cuatrimestre") || infoLower.contains("clase"))) {
              esRelevante = true;
            }
            break;

          case "examenes":
          case "mesas_finales":
            if ((infoLower.contains("examen") || infoLower.contains("final") || infoLower.contains("turno")) &&
                (infoLower.contains("fecha") || infoLower.contains("mesa") || infoLower.contains("inscripción"))) {
              esRelevante = true;
            }
            break;

          case "vacaciones":
          case "receso":
            if (infoLower.contains("vacacion") || infoLower.contains("receso") ||
                (infoLower.contains("invierno") && !infoLower.contains("cronograma"))) {
              esRelevante = true;
            }
            break;

          case "confirmacion":
          case "inscripcion_carrera":
            if (infoLower.contains("confirmación") || infoLower.contains("confirmacion") ||
                (infoLower.contains("inscripción") && infoLower.contains("carrera"))) {
              esRelevante = true;
            }
            break;

          case "feriados":
            if (infoLower.contains("feriado") || infoLower.contains("no laborable") ||
                infoLower.contains("día no laborable")) {
              esRelevante = true;
            }
            break;
        }

        if (esRelevante && informacion.length() > 10 && informacion.length() < 300) {
          eventosEncontrados.add(informacion);
        }
      }

      // Construir respuesta con información encontrada
      if (!eventosEncontrados.isEmpty()) {
        resultado.append("📅 **Información encontrada:**\n\n");
        eventosEncontrados.stream()
            .distinct()
            .limit(6)
            .forEach(evento -> resultado.append("• ").append(evento).append("\n"));
        resultado.append("\n");
      }

      // Si no se encontró información específica, buscar información relacionada
      if (eventosEncontrados.isEmpty()) {
        List<String> informacionRelacionada = buscarInformacionRelacionada(tipoConsulta);

        if (!informacionRelacionada.isEmpty()) {
          resultado.append("📋 **Información relacionada encontrada:**\n\n");
          informacionRelacionada.stream()
              .distinct()
              .limit(4)
              .forEach(evento -> resultado.append("• ").append(evento).append("\n"));
          resultado.append("\n");
        } else {
          resultado.append("• No se encontró información específica en el calendario actual.\n");
          resultado.append(
              "• Consultá el calendario académico para información actualizada: https://elegi.unnoba.edu.ar/calendario/\n");
          return resultado.toString();
        }
      }

      resultado.append("🔗 **Para información completa y actualizada:**\n");
      resultado.append("• **Calendario Académico:** https://elegi.unnoba.edu.ar/calendario/");

      return resultado.toString();

    } catch (Exception e) {
      System.err.println("Error extrayendo fechas específicas: " + e.getMessage());
      return "• Error al consultar el calendario. Accedé directamente en: https://elegi.unnoba.edu.ar/calendario/";
    }
  }

  // Método auxiliar para buscar información relacionada cuando no se encuentra
  // información específica
  private List<String> buscarInformacionRelacionada(String tipoConsulta) {
    List<String> relacionada = new ArrayList<>();

    // Palabras clave alternativas según el tipo de consulta
    String[] palabrasAlternativas = {};

    switch (tipoConsulta.toLowerCase()) {
      case "inicio_cuatrimestre":
      case "inicio_primer_cuatrimestre":
      case "inicio_segundo_cuatrimestre":
        palabrasAlternativas = new String[] { "clase", "semestre", "cuatrimestre", "comienzo", "marzo", "agosto" };
        break;
      case "fin_cuatrimestre":
      case "fin_primer_cuatrimestre":
      case "fin_segundo_cuatrimestre":
        palabrasAlternativas = new String[] { "clase", "semestre", "cuatrimestre", "término", "julio", "diciembre" };
        break;
      case "examenes":
      case "mesas_finales":
        palabrasAlternativas = new String[] { "turno", "mesa", "examen", "final", "inscripción" };
        break;
      case "vacaciones":
      case "receso":
        palabrasAlternativas = new String[] { "invierno", "julio", "agosto", "descanso" };
        break;
      case "feriados":
        palabrasAlternativas = new String[] { "laborable", "actividad", "académica", "feriado" };
        break;
    }

    for (String informacion : informacionExtraida) {
      String infoLower = informacion.toLowerCase();
      for (String palabra : palabrasAlternativas) {
        if (infoLower.contains(palabra) && informacion.length() > 15 && informacion.length() < 250) {
          relacionada.add(informacion);
          break;
        }
      }
    }

    return relacionada;
  }

  // Método mejorado para extraer exámenes por mes con información dinámica
  public String extraerExamenesPorMes(String mes) {
    informacionExtraida.clear();
    visitados.clear();

    try {
      // Buscar información específica del mes
      buscarInformacionCalendario("https://elegi.unnoba.edu.ar/calendario/", 0, 2);

      String mesCapitalizado = mes.substring(0, 1).toUpperCase() + mes.substring(1).toLowerCase();
      StringBuilder resultado = new StringBuilder();

      // Filtrar información específica del mes solicitado
      List<String> examenesDelMes = new ArrayList<>();
      List<String> inscripcionesDelMes = new ArrayList<>();
      List<String> fechasRelacionadas = new ArrayList<>();

      for (String informacion : informacionExtraida) {
        String infoLower = informacion.toLowerCase();

        // Buscar menciones específicas del mes
        if (infoLower.contains(mes.toLowerCase())) {
          if (infoLower.contains("examen") || infoLower.contains("final") || infoLower.contains("turno")
              || infoLower.contains("mesa")) {
            examenesDelMes.add(informacion);
          } else if (infoLower.contains("inscripción") || infoLower.contains("inscripcion")) {
            inscripcionesDelMes.add(informacion);
          } else {
            fechasRelacionadas.add(informacion);
          }
        }
      }

      resultado.append("📅 **MESAS DE EXAMEN - ").append(mesCapitalizado.toUpperCase()).append("**\n\n");

      // Mostrar información específica encontrada
      if (!examenesDelMes.isEmpty()) {
        resultado.append("🗓️ **Fechas de exámenes para ").append(mesCapitalizado).append(":**\n");
        examenesDelMes.stream()
            .distinct()
            .limit(5)
            .forEach(evento -> resultado.append("• ").append(evento).append("\n"));
        resultado.append("\n");
      }

      if (!inscripcionesDelMes.isEmpty()) {
        resultado.append("📝 **Inscripciones para ").append(mesCapitalizado).append(":**\n");
        inscripcionesDelMes.stream()
            .distinct()
            .limit(3)
            .forEach(evento -> resultado.append("• ").append(evento).append("\n"));
        resultado.append("\n");
      }

      if (!fechasRelacionadas.isEmpty()) {
        resultado.append("📋 **Otras fechas importantes de ").append(mesCapitalizado).append(":**\n");
        fechasRelacionadas.stream()
            .distinct()
            .limit(3)
            .forEach(evento -> resultado.append("• ").append(evento).append("\n"));
        resultado.append("\n");
      }

      // Si no se encontró información específica, mostrar información general pero
      // con más detalle
      if (examenesDelMes.isEmpty() && inscripcionesDelMes.isEmpty() && fechasRelacionadas.isEmpty()) {
        resultado.append("No se encontraron fechas específicas para ").append(mesCapitalizado)
            .append(" en el calendario actual.\n\n");

        // Buscar información general de exámenes que pueda ser relevante
        List<String> examenesGenerales = new ArrayList<>();
        for (String informacion : informacionExtraida) {
          String infoLower = informacion.toLowerCase();
          if ((infoLower.contains("examen") || infoLower.contains("final") || infoLower.contains("turno")) &&
              (infoLower.contains("fecha") || infoLower.contains("mesa") || infoLower.contains("2025"))) {
            examenesGenerales.add(informacion);
          }
        }

        if (!examenesGenerales.isEmpty()) {
          resultado.append("📋 **Información general de exámenes encontrada:**\n");
          examenesGenerales.stream()
              .distinct()
              .limit(4)
              .forEach(evento -> resultado.append("• ").append(evento).append("\n"));
          resultado.append("\n");
        }
      }

      // Información complementaria específica por mes
      resultado.append("📋 **Información general sobre mesas de examen:**\n");
      resultado.append("• **Turnos regulares:** ").append(obtenerTurnosDelMes(mes)).append("\n");
      resultado.append("• **Inscripción:** Se realiza a través del SIU-Guaraní\n");
      resultado.append("• **Plazo:** Hasta 48 horas hábiles antes del examen\n");
      resultado.append("• **Horarios:** Consultar en el calendario para horarios específicos\n\n");

      resultado.append("🔗 **Enlaces importantes:**\n");
      resultado.append("• **Calendario Académico:** https://elegi.unnoba.edu.ar/calendario/\n");
      resultado.append("• **SIU-Guaraní (Inscripciones):** https://g3w3.unnoba.edu.ar/g3w3/\n\n");

      resultado.append(
          "💡 **Recomendación:** Consultá el calendario académico regularmente, ya que las fechas pueden actualizarse.");

      return resultado.toString();

    } catch (Exception e) {
      System.err.println("Error extrayendo exámenes por mes: " + e.getMessage());
      return extraerExamenesPorMesGenerico(mes);
    }
  }

  // Método auxiliar para proporcionar información contextual según el mes
  private String obtenerTurnosDelMes(String mes) {
    switch (mes.toLowerCase()) {
      case "febrero":
      case "marzo":
        return "Turno de Verano (Febrero-Marzo)";
      case "abril":
      case "mayo":
        return "Turno de Otoño (Abril-Mayo)";
      case "junio":
        return "Turno de Junio (Medio del año)";
      case "julio":
      case "agosto":
        return "Turno de Invierno (Julio-Agosto)";
      case "septiembre":
        return "Turno de Septiembre";
      case "noviembre":
        return "Turno de Noviembre";
      case "diciembre":
        return "Turno de Diciembre (Fin de año)";
      default:
        return "Febrero, Marzo, Abril, Mayo, Junio, Julio, Agosto, Septiembre, Noviembre y Diciembre";
    }
  }

  // Método auxiliar para respuesta genérica cuando no se puede hacer scraping
  private String extraerExamenesPorMesGenerico(String mes) {
    String mesCapitalizado = mes.substring(0, 1).toUpperCase() + mes.substring(1).toLowerCase();

    StringBuilder resultado = new StringBuilder();
    resultado.append("📅 **CONSULTA DE EXÁMENES - ").append(mesCapitalizado.toUpperCase()).append("**\n\n");
    resultado.append("Para obtener las fechas exactas de las mesas de examen de **").append(mesCapitalizado)
        .append("**, ");
    resultado.append("necesitás consultar el calendario académico oficial de la UNNOBA.\n\n");

    resultado.append("🗓️ **¿Dónde encontrar la información?**\n");
    resultado.append("• El calendario académico se actualiza constantemente con las fechas oficiales\n");
    resultado.append("• Incluye fechas de inscripción, exámenes y todos los eventos académicos\n");
    resultado.append("• Es la fuente oficial y más actualizada de la universidad\n\n");

    resultado.append("📋 **Información general sobre mesas de examen:**\n");
    resultado.append(
        "• **Turnos regulares:** Febrero, Marzo, Abril, Mayo, Junio, Julio, Agosto, Septiembre, Noviembre y Diciembre\n");
    resultado.append("• **Inscripción:** Se realiza a través del SIU-Guaraní\n");
    resultado.append("• **Fechas:** Varían según la carrera y materia\n\n");

    resultado.append("🔗 **Enlaces importantes:**\n");
    resultado.append("• **Calendario Académico:** https://elegi.unnoba.edu.ar/calendario/\n");
    resultado.append("• **SIU-Guaraní (Inscripciones):** https://g3w3.unnoba.edu.ar/g3w3/");

    return resultado.toString();
  }

  // Nuevo método para consultas generales de fechas
  public String consultarFechaEspecifica(String consulta) {
    try {
      Document doc = Jsoup.connect("https://elegi.unnoba.edu.ar/calendario/")
          .timeout(10000)
          .userAgent("Mozilla/5.0")
          .get();

      StringBuilder resultado = new StringBuilder();
      Elements eventos = doc
          .select(".tribe-events-calendar-list__event-row, .tribe-event-item, .event-item, [class*='event']");

      List<String> eventosEncontrados = new ArrayList<>();
      String consultaLower = consulta.toLowerCase();

      for (Element evento : eventos) {
        String texto = evento.text().toLowerCase();

        // Buscar coincidencias con la consulta
        if (texto.contains(consultaLower) ||
            (consultaLower.contains("feriado") && texto.contains("no laborable")) ||
            (consultaLower.contains("vacacion") && texto.contains("receso")) ||
            (consultaLower.contains("inicio") && texto.contains("inicio")) ||
            (consultaLower.contains("fin") && texto.contains("fin"))) {

          String textoLimpio = limpiarTextoEvento(evento.text());
          if (!textoLimpio.isEmpty() && textoLimpio.length() > 15) {
            eventosEncontrados.add(textoLimpio);
          }
        }
      }

      if (!eventosEncontrados.isEmpty()) {
        resultado.append("📅 **Información encontrada:**\n\n");
        eventosEncontrados.stream()
            .distinct()
            .limit(5)
            .forEach(evento -> resultado.append("• ").append(evento).append("\n"));
        resultado.append("\n");
      } else {
        resultado.append("📅 **Consulta: ").append(consulta).append("**\n\n");
        resultado.append("No se encontró información específica en el calendario actual.\n\n");
      }

      resultado.append("🔗 **Para información completa y actualizada:**\n");
      resultado.append("• **Calendario Académico:** https://elegi.unnoba.edu.ar/calendario/");

      return resultado.toString();

    } catch (IOException e) {
      return "Error al consultar el calendario. Accedé directamente en: https://elegi.unnoba.edu.ar/calendario/";
    }
  }
}