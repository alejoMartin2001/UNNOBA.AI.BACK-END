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
    // Buscar eventos en diferentes estructuras HTML del calendario UNNOBA
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
        "*:contains(Fecha de Examen – Turno)",
        "*:contains(Inscripción)",
        "*:contains(Confirmación)",
        "*:contains(Inicio)",
        "*:contains(Final)",
        "*:contains(Feriado)",
        "*:contains(No Laborable)",
        // Nuevos selectores específicos para el calendario académico
        "*:contains(enero)",
        "*:contains(febrero)",
        "*:contains(marzo)",
        "*:contains(abril)",
        "*:contains(mayo)",
        "*:contains(junio)",
        "*:contains(julio)",
        "*:contains(agosto)",
        "*:contains(septiembre)",
        "*:contains(octubre)",
        "*:contains(noviembre)",
        "*:contains(diciembre)");

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

    // Buscar específicamente elementos que contengan rangos de fechas del
    // calendario
    buscarEventosCalendarioAcademico(doc);

    // Buscar también en elementos que contengan fechas específicas
    buscarFechasEspecificas(doc);
  }

  // Método específico para buscar eventos del formato del calendario académico
  private void buscarEventosCalendarioAcademico(Document doc) {
    try {
      // Buscar elementos que contengan patrones típicos del calendario académico
      Elements eventosCalendario = doc
          .select("*:contains(Fecha de Examen), *:contains(Turno), *:contains(Inicio:), *:contains(Final:)");

      for (Element evento : eventosCalendario) {
        String textoEvento = evento.text();
        String textoLower = textoEvento.toLowerCase();

        // Buscar patrones específicos del calendario
        if ((textoLower.contains("fecha de examen") && textoLower.contains("turno")) ||
            (textoLower.contains("inicio:") && textoLower.contains("final:")) ||
            textoLower.matches(".*\\w+\\s+\\d{1,2}\\s*-\\s*\\w+\\s+\\d{1,2}.*")) {

          // Extraer información completa del evento incluyendo detalles
          StringBuilder textoCompleto = new StringBuilder(textoEvento);

          // Buscar elementos hermanos que pueden contener detalles adicionales
          Elements detalles = evento.parent().select("*:contains(Detalles), *:contains(Inicio:), *:contains(Final:)");
          for (Element detalle : detalles) {
            if (!detalle.text().equals(textoEvento)) {
              textoCompleto.append(" ").append(detalle.text());
            }
          }

          String textoFinal = textoCompleto.toString().trim();
          if (textoFinal.length() > 10 && textoFinal.length() < 500) {
            informacionExtraida.add(textoFinal);
          }
        }
      }

    } catch (Exception e) {
      System.err.println("Error buscando eventos del calendario académico: " + e.getMessage());
    }
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
        palabrasAlternativas = new String[] { "clase", "semestre", "cuatrimestre", "comienzo", "marzo", "agosto",
            "febrero", "mayo", "enero", "abril", "julio", "noviembre", "diciembre", "septiembre", "octubre", "junio" };
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

  // Método mejorado para extraer exámenes por mes con información específica del
  // calendario académico
  public String extraerExamenesPorMes(String mes) {
    try {
      String mesCapitalizado = mes.substring(0, 1).toUpperCase() + mes.substring(1).toLowerCase();

      // Verificar si es un mes sin mesas de finales (enero u octubre)
      if (mes.toLowerCase().equals("enero") || mes.toLowerCase().equals("octubre")) {
        return generarMensajeMesSinFinales(mesCapitalizado);
      }

      StringBuilder resultado = new StringBuilder();

      // Buscar información específica en el calendario académico de UNNOBA
      FechasExamenCalendario fechasCalendario = extraerFechasDelCalendarioAcademico(mes);

      resultado.append("📅 **MESAS DE FINALES - ").append(mesCapitalizado.toUpperCase()).append("**\n\n");

      // Fecha de inicio de inscripción
      if (fechasCalendario.fechaInicioInscripcion != null) {
        resultado.append("📝 **Fecha de inicio de inscripción:** ").append(fechasCalendario.fechaInicioInscripcion)
            .append("\n\n");
      } else {
        resultado.append("📝 **Fecha de inicio de inscripción:** Consultar calendario académico\n\n");
      }

      // Período de finales
      if (fechasCalendario.fechaInicioExamen != null && fechasCalendario.fechaFinExamen != null) {
        resultado.append("🗓️ **Período de Final de ").append(mesCapitalizado).append(":** ")
            .append(fechasCalendario.fechaInicioExamen).append(" a ").append(fechasCalendario.fechaFinExamen)
            .append("\n\n");
      } else if (fechasCalendario.fechaInicioExamen != null) {
        resultado.append("🗓️ **Período de Final de ").append(mesCapitalizado).append(":** ")
            .append("A partir del ").append(fechasCalendario.fechaInicioExamen).append("\n\n");
      } else {
        resultado.append("🗓️ **Período de Final de ").append(mesCapitalizado).append(":** ")
            .append("Consultar calendario académico\n\n");
      }

      // Fecha fin de inscripción (siempre la misma regla)
      resultado.append(
          "⏰ **Fecha fin de inscripción:** 48 hrs antes del final al que quieras inscribirte, sin contar los sábados y domingos.\n\n");

      // Ejemplo del tiempo límite
      resultado.append("💡 **Ejemplo de tiempo límite:**\n");
      resultado.append(
          "• Si un final es un **viernes a las 9:00 hs**, podés inscribirte hasta el **miércoles a las 9:00 hs**\n");
      resultado.append(
          "• Si un final es un **lunes a las 14:00 hs**, podés inscribirte hasta el **miércoles anterior a las 14:00 hs**\n");
      resultado.append("• Los sábados y domingos **NO cuentan** como días hábiles\n\n");

      // Enlaces importantes
      resultado.append("🔗 **Enlaces importantes:**\n");
      resultado.append("• **Calendario Académico:** https://elegi.unnoba.edu.ar/calendario/\n");
      resultado.append("• **SIU-Guaraní (Inscripciones):** https://g3w3.unnoba.edu.ar/g3w3/\n\n");

      // Información sobre horarios
      resultado.append("🕐 **Información sobre horarios:**\n");
      resultado.append("Los horarios específicos de los exámenes finales se proporcionan en el **SIU-Guaraní**. ");
      resultado.append("En el calendario académico solo encontrarás la información sobre cuándo se habilita ");
      resultado.append("la inscripción y cuál es el período de exámenes.\n\n");

      resultado.append("⚠️ **Importante:**\n");
      resultado.append("Consultá el calendario académico regularmente, ya que las fechas pueden actualizarse.");

      return resultado.toString();

    } catch (Exception e) {
      System.err.println("Error extrayendo exámenes por mes: " + e.getMessage());
      return extraerExamenesPorMesGenericoEstructurado(mes);
    }
  }

  // Clase auxiliar para almacenar fechas estructuradas
  private static class FechasExamen {
    String fechaInicioInscripcion;
    String fechaInicioFinales;
    String fechaFinFinales;
  }

  // Clase auxiliar para almacenar fechas del calendario académico específico
  private static class FechasExamenCalendario {
    String fechaInicioInscripcion;
    String fechaInicioExamen;
    String fechaFinExamen;
    String urlCalendario;
  }

  // Método para procesar y estructurar las fechas del mes específico
  private FechasExamen procesarFechasDelMes(String mes) {
    FechasExamen fechas = new FechasExamen();

    for (String informacion : informacionExtraida) {
      String infoLower = informacion.toLowerCase();

      // Buscar menciones específicas del mes
      if (infoLower.contains(mes.toLowerCase())) {

        // Buscar fechas de inicio de inscripción (patrón específico del calendario)
        if ((infoLower.contains("inscripción") || infoLower.contains("inscripcion"))
            && infoLower.contains("inicio")) {
          String fechaFormateada = extraerYFormatearFecha(informacion);
          if (fechaFormateada != null && fechas.fechaInicioInscripcion == null) {
            fechas.fechaInicioInscripcion = fechaFormateada;
          }
        }

        // Buscar patrón específico del calendario: "Fecha de Examen – Turno [Mes]"
        if (infoLower.contains("fecha de examen") && infoLower.contains("turno")
            && infoLower.contains(mes.toLowerCase())) {
          RangoFechas rango = extraerRangoFechasCalendario(informacion, mes);
          if (rango != null) {
            fechas.fechaInicioFinales = rango.inicio;
            fechas.fechaFinFinales = rango.fin;
          }
        }

        // Buscar patrón de rango: "julio 2 - julio 8"
        if (infoLower.contains("-") && contieneFechasDelMes(infoLower, mes)) {
          RangoFechas rango = extraerRangoFechasDirecto(informacion, mes);
          if (rango != null && fechas.fechaInicioFinales == null) {
            fechas.fechaInicioFinales = rango.inicio;
            fechas.fechaFinFinales = rango.fin;
          }
        }

        // Buscar patrones "Inicio:" y "Final:" del calendario
        if (infoLower.contains("inicio:") || infoLower.contains("final:")) {
          procesarDetallesCalendario(informacion, fechas, mes);
        }
      }
    }

    return fechas;
  }

  // Clase auxiliar para rangos de fechas
  private static class RangoFechas {
    String inicio;
    String fin;

    RangoFechas(String inicio, String fin) {
      this.inicio = inicio;
      this.fin = fin;
    }
  }

  // Método para extraer rango de fechas del formato del calendario
  private RangoFechas extraerRangoFechasCalendario(String texto, String mes) {
    // Buscar patrón: "julio 2 - julio 8"
    Pattern patronRango = Pattern.compile(
        "(" + mes.toLowerCase() + "\\s+\\d{1,2})\\s*-\\s*(" + mes.toLowerCase() + "\\s+\\d{1,2})",
        Pattern.CASE_INSENSITIVE);
    Matcher matcher = patronRango.matcher(texto.toLowerCase());

    if (matcher.find()) {
      String fechaInicio = matcher.group(1);
      String fechaFin = matcher.group(2);

      String inicioFormateado = formatearFechaDelCalendario(fechaInicio, mes);
      String finFormateado = formatearFechaDelCalendario(fechaFin, mes);

      if (inicioFormateado != null && finFormateado != null) {
        return new RangoFechas(inicioFormateado, finFormateado);
      }
    }

    return null;
  }

  // Método para extraer rango directo
  private RangoFechas extraerRangoFechasDirecto(String texto, String mes) {
    return extraerRangoFechasCalendario(texto, mes);
  }

  // Método para verificar si contiene fechas del mes
  private boolean contieneFechasDelMes(String texto, String mes) {
    return texto.contains(mes.toLowerCase() + " ") && (texto.contains("-") || texto.contains("al"));
  }

  // Método para procesar detalles del calendario con formato "Inicio:" "Final:"
  private void procesarDetallesCalendario(String texto, FechasExamen fechas, String mes) {
    String[] lineas = texto.split("\n");

    for (int i = 0; i < lineas.length; i++) {
      String linea = lineas[i].toLowerCase().trim();

      if (linea.startsWith("inicio:") && fechas.fechaInicioFinales == null) {
        String fecha = linea.replace("inicio:", "").trim();
        String fechaFormateada = formatearFechaDelCalendario(fecha, mes);
        if (fechaFormateada != null) {
          fechas.fechaInicioFinales = fechaFormateada;
        }
      }

      if (linea.startsWith("final:") && fechas.fechaFinFinales == null) {
        String fecha = linea.replace("final:", "").trim();
        String fechaFormateada = formatearFechaDelCalendario(fecha, mes);
        if (fechaFormateada != null) {
          fechas.fechaFinFinales = fechaFormateada;
        }
      }
    }
  }

  // Método para formatear fechas específicas del calendario (ej: "julio 2" ->
  // "02/07/2025")
  private String formatearFechaDelCalendario(String fechaTexto, String mes) {
    try {
      // Limpiar el texto de caracteres especiales
      String textoLimpio = fechaTexto.replaceAll("[^a-zA-Z0-9\\s]", " ").trim();

      // Patrón principal para "julio 2", "julio 8", etc.
      Pattern patron = Pattern.compile("(" + mes.toLowerCase() + ")\\s+(\\d{1,2})", Pattern.CASE_INSENSITIVE);
      Matcher matcher = patron.matcher(textoLimpio.toLowerCase());

      if (matcher.find()) {
        String mesNombre = matcher.group(1);
        String dia = matcher.group(2);
        String numeroMes = convertirMesANumero(mesNombre.toLowerCase());

        if (numeroMes != null) {
          return String.format("%02d/%s/2025", Integer.parseInt(dia), numeroMes);
        }
      }

      // Patrón alternativo: solo números después del mes
      Pattern patronNumero = Pattern.compile("(\\d{1,2})", Pattern.CASE_INSENSITIVE);
      Matcher matcherNumero = patronNumero.matcher(textoLimpio);

      if (matcherNumero.find() && textoLimpio.toLowerCase().contains(mes.toLowerCase())) {
        String dia = matcherNumero.group(1);
        String numeroMes = convertirMesANumero(mes.toLowerCase());

        if (numeroMes != null) {
          int diaInt = Integer.parseInt(dia);
          if (diaInt >= 1 && diaInt <= 31) { // Validar que sea un día válido
            return String.format("%02d/%s/2025", diaInt, numeroMes);
          }
        }
      }

      // Si no encuentra el patrón, intentar con el método general
      return extraerYFormatearFecha(fechaTexto);

    } catch (Exception e) {
      return null;
    }
  }

  // Método auxiliar para verificar si una fecha corresponde al mes correcto
  private boolean esFechaDelMesCorrecta(String fecha, String mes) {
    if (fecha == null || fecha.length() < 8)
      return false;

    try {
      String[] partes = fecha.split("/");
      if (partes.length != 3)
        return false;

      int mesNumerico = Integer.parseInt(partes[1]);
      String numeroMesEsperado = convertirMesANumero(mes.toLowerCase());

      if (numeroMesEsperado != null) {
        int mesEsperado = Integer.parseInt(numeroMesEsperado);

        // Permitir el mes actual y el siguiente (para períodos que pueden extenderse)
        return mesNumerico == mesEsperado || mesNumerico == (mesEsperado + 1) ||
            (mesEsperado == 12 && mesNumerico == 1); // Diciembre a Enero
      }
    } catch (NumberFormatException e) {
      return false;
    }

    return false;
  }

  // Método auxiliar para extraer y formatear fechas al formato DD/MM/AAAA
  private String extraerYFormatearFecha(String texto) {
    // Patrones de fecha a buscar - orden de prioridad
    Pattern patron2025 = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})"); // 2025-07-15
    Pattern patronFechaCompleta = Pattern.compile("(\\d{1,2})\\s+de\\s+(\\w+)\\s+de\\s+(\\d{4})"); // 15 de julio de
                                                                                                   // 2025
    Pattern patronDiaMes = Pattern.compile("(\\d{1,2})\\s+de\\s+(\\w+)"); // 15 de julio
    Pattern patronMesDia = Pattern.compile("(\\w+)\\s+(\\d{1,2})(?:\\s|$|-)"); // julio 15
    Pattern patronFechaSimple = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4})"); // 15/07/2025

    // Formato ISO (prioritario)
    Matcher matcher2025 = patron2025.matcher(texto);
    if (matcher2025.find()) {
      String año = matcher2025.group(1);
      String mes = matcher2025.group(2);
      String dia = matcher2025.group(3);
      return String.format("%02d/%02d/%s", Integer.parseInt(dia), Integer.parseInt(mes), año);
    }

    // Fecha completa con año
    Matcher matcherFechaCompleta = patronFechaCompleta.matcher(texto.toLowerCase());
    if (matcherFechaCompleta.find()) {
      String dia = matcherFechaCompleta.group(1);
      String mes = matcherFechaCompleta.group(2);
      String año = matcherFechaCompleta.group(3);
      String numeroMes = convertirMesANumero(mes);
      if (numeroMes != null) {
        return String.format("%02d/%s/%s", Integer.parseInt(dia), numeroMes, año);
      }
    }

    // Día de mes (sin año, asumir 2025)
    Matcher matcherDiaMes = patronDiaMes.matcher(texto.toLowerCase());
    if (matcherDiaMes.find()) {
      String dia = matcherDiaMes.group(1);
      String mes = matcherDiaMes.group(2);
      String numeroMes = convertirMesANumero(mes);
      if (numeroMes != null) {
        return String.format("%02d/%s/2025", Integer.parseInt(dia), numeroMes);
      }
    }

    // Mes día (julio 15)
    Matcher matcherMesDia = patronMesDia.matcher(texto.toLowerCase());
    if (matcherMesDia.find()) {
      String mes = matcherMesDia.group(1);
      String dia = matcherMesDia.group(2);
      String numeroMes = convertirMesANumero(mes);
      if (numeroMes != null) {
        return String.format("%02d/%s/2025", Integer.parseInt(dia), numeroMes);
      }
    }

    // Formato simple DD/MM/AAAA
    Matcher matcherFechaSimple = patronFechaSimple.matcher(texto);
    if (matcherFechaSimple.find()) {
      String dia = matcherFechaSimple.group(1);
      String mes = matcherFechaSimple.group(2);
      String año = matcherFechaSimple.group(3);
      return String.format("%02d/%02d/%s", Integer.parseInt(dia), Integer.parseInt(mes), año);
    }

    return null;
  }

  // Método auxiliar para convertir nombre del mes a número
  private String convertirMesANumero(String mes) {
    switch (mes.toLowerCase()) {
      case "enero":
        return "01";
      case "febrero":
        return "02";
      case "marzo":
        return "03";
      case "abril":
        return "04";
      case "mayo":
        return "05";
      case "junio":
        return "06";
      case "julio":
        return "07";
      case "agosto":
        return "08";
      case "septiembre":
        return "09";
      case "octubre":
        return "10";
      case "noviembre":
        return "11";
      case "diciembre":
        return "12";
      default:
        return null;
    }
  }

  // Método principal para extraer fechas del calendario académico específico
  private FechasExamenCalendario extraerFechasDelCalendarioAcademico(String mes) {
    FechasExamenCalendario fechas = new FechasExamenCalendario();

    try {
      // Obtener URL específica del calendario académico para el mes
      String urlCalendario = construirUrlCalendarioAcademico(mes);
      fechas.urlCalendario = urlCalendario;

      // Conectar al calendario académico específico
      Document doc = Jsoup.connect(urlCalendario)
          .timeout(15000)
          .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
          .get();

      // Extraer fechas de inicio y fin de exámenes
      extraerFechasExamenFromCalendario(doc, fechas, mes);

      // Buscar fecha de inicio de inscripción en páginas relacionadas
      buscarFechaInicioInscripcion(mes, fechas);

    } catch (IOException e) {
      System.err.println("Error accediendo al calendario académico para " + mes + ": " + e.getMessage());
    }

    return fechas;
  }

  // Método para generar mensaje para meses sin mesas de finales
  private String generarMensajeMesSinFinales(String mesCapitalizado) {
    StringBuilder resultado = new StringBuilder();

    resultado.append("📅 **MESAS DE FINALES - ").append(mesCapitalizado.toUpperCase()).append("**\n\n");
    resultado.append("❌ **No hay mesas de finales en ").append(mesCapitalizado).append("**\n\n");
    resultado.append("La Universidad Nacional del Noroeste de la Provincia de Buenos Aires (UNNOBA) ");
    resultado.append("no tiene programadas mesas de exámenes finales durante el mes de ")
        .append(mesCapitalizado.toLowerCase()).append(".\n\n");

    resultado.append("📅 **Meses con mesas de finales disponibles:**\n");
    resultado.append("• **Febrero** - Turno de exámenes de verano\n");
    resultado.append("• **Marzo** - Turno complementario\n");
    resultado.append("• **Abril** - Turno de otoño\n");
    resultado.append("• **Mayo** - Turno complementario\n");
    resultado.append("• **Junio** - Turno de invierno\n");
    resultado.append("• **Julio** - Turno complementario\n");
    resultado.append("• **Agosto** - Turno especial\n");
    resultado.append("• **Septiembre** - Turno de primavera\n");
    resultado.append("• **Noviembre** - Turno complementario\n");
    resultado.append("• **Diciembre** - Turno de verano\n\n");

    resultado.append("💡 **Recomendación:**\n");
    resultado.append("Te sugerimos consultar el calendario académico para planificar tus exámenes finales ");
    resultado.append("en los meses disponibles.\n\n");

    resultado.append("🔗 **Enlaces importantes:**\n");
    resultado.append("• **Calendario Académico:** https://elegi.unnoba.edu.ar/calendario/\n");
    resultado.append("• **SIU-Guaraní (Consultas):** https://g3w3.unnoba.edu.ar/g3w3/\n\n");

    resultado.append("⚠️ **Importante:**\n");
    resultado.append("Las fechas y turnos pueden estar sujetos a modificaciones. ");
    resultado.append("Consultá siempre el calendario académico oficial para obtener información actualizada.");

    return resultado.toString();
  }

  // Método para construir URL específica del calendario académico según el mes
  private String construirUrlCalendarioAcademico(String mes) {
    String baseUrl = "https://elegi.unnoba.edu.ar/calendarioacademico/";

    switch (mes.toLowerCase()) {
      case "febrero":
        return baseUrl + "fecha-de-examen-turno-febrero/";
      case "marzo":
        return baseUrl + "fecha-de-examen-turno-marzo/";
      case "abril":
        return baseUrl + "fecha-de-examen-turno-abril/";
      case "mayo":
        return baseUrl + "fecha-de-examen-turno-mayo/";
      case "junio":
        return baseUrl + "fecha-de-examen-turno-junio/";
      case "julio":
        return baseUrl + "fecha-de-examen-turno-julio-2/"; // Julio tiene patrón específico
      case "agosto":
        return baseUrl + "fecha-de-examen-turno-agosto-2/"; // Agosto tiene un patrón diferente
      case "septiembre":
        return baseUrl + "fecha-de-examen-turno-septiembre/";
      case "noviembre":
        return baseUrl + "fecha-de-examen-turno-noviembre/";
      case "diciembre":
        return baseUrl + "fecha-de-examen-turno-diciembre/";
      default:
        return baseUrl + "fecha-de-examen-turno-" + mes.toLowerCase() + "/";
    }
  }

  // Método para extraer fechas de examen del documento del calendario
  private void extraerFechasExamenFromCalendario(Document doc, FechasExamenCalendario fechas, String mes) {
    try {
      System.out.println("Extrayendo fechas de examen para " + mes);

      // 1. Buscar específicamente en la sección de detalles
      Elements seccionDetalles = doc.select("*:contains(Detalles)");
      for (Element seccion : seccionDetalles) {
        Element contenedor = seccion.parent();
        if (contenedor != null) {
          String textoContenedor = contenedor.text();
          System.out.println("Texto de sección detalles: " + textoContenedor);

          // Buscar "Inicio:" seguido de fecha
          Pattern patronInicio = Pattern.compile("Inicio:\\s*(" + mes + "\\s+\\d{1,2})", Pattern.CASE_INSENSITIVE);
          Matcher matcherInicio = patronInicio.matcher(textoContenedor);
          if (matcherInicio.find()) {
            String fechaInicio = matcherInicio.group(1);
            fechas.fechaInicioExamen = formatearFechaCalendario(fechaInicio, mes);
            System.out
                .println("Fecha inicio encontrada: " + fechas.fechaInicioExamen + " (de texto: " + fechaInicio + ")");
          }

          // Buscar "Final:" seguido de fecha
          Pattern patronFinal = Pattern.compile("Final:\\s*(" + mes + "\\s+\\d{1,2})", Pattern.CASE_INSENSITIVE);
          Matcher matcherFinal = patronFinal.matcher(textoContenedor);
          if (matcherFinal.find()) {
            String fechaFinal = matcherFinal.group(1);
            fechas.fechaFinExamen = formatearFechaCalendario(fechaFinal, mes);
            System.out.println("Fecha final encontrada: " + fechas.fechaFinExamen + " (de texto: " + fechaFinal + ")");
          }
        }
      }

      // 2. Si no encuentra en detalles, buscar en todo el documento
      if (fechas.fechaInicioExamen == null || fechas.fechaFinExamen == null) {
        System.out.println("Buscando fechas en todo el documento...");
        String textoCompleto = doc.text();

        // Buscar "Inicio: mes día"
        Pattern patronInicio = Pattern.compile("Inicio:?\\s*(" + mes + "\\s+\\d{1,2})", Pattern.CASE_INSENSITIVE);
        Matcher matcherInicio = patronInicio.matcher(textoCompleto);
        if (matcherInicio.find() && fechas.fechaInicioExamen == null) {
          String fechaInicio = matcherInicio.group(1);
          fechas.fechaInicioExamen = formatearFechaCalendario(fechaInicio, mes);
          System.out.println("Fecha inicio encontrada (búsqueda amplia): " + fechas.fechaInicioExamen);
        }

        // Buscar "Final: mes día"
        Pattern patronFinal = Pattern.compile("Final:?\\s*(" + mes + "\\s+\\d{1,2})", Pattern.CASE_INSENSITIVE);
        Matcher matcherFinal = patronFinal.matcher(textoCompleto);
        if (matcherFinal.find() && fechas.fechaFinExamen == null) {
          String fechaFinal = matcherFinal.group(1);
          fechas.fechaFinExamen = formatearFechaCalendario(fechaFinal, mes);
          System.out.println("Fecha final encontrada (búsqueda amplia): " + fechas.fechaFinExamen);
        }
      }

      // 3. Buscar patrón de rango "mes día - mes día" en títulos
      if (fechas.fechaInicioExamen == null || fechas.fechaFinExamen == null) {
        System.out.println("Buscando patrón de rango en títulos...");
        Elements titulos = doc.select("h1, h2, h3");
        for (Element titulo : titulos) {
          String textoTitulo = titulo.text();
          System.out.println("Analizando título: " + textoTitulo);
          extraerFechasDeRango(textoTitulo, fechas, mes);
          if (fechas.fechaInicioExamen != null && fechas.fechaFinExamen != null) {
            break;
          }
        }
      }

      System.out
          .println("Resultado extracción - Inicio: " + fechas.fechaInicioExamen + ", Final: " + fechas.fechaFinExamen);

    } catch (Exception e) {
      System.err.println("Error extrayendo fechas de examen del calendario: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Método para extraer fecha después de una etiqueta específica
  private String extraerFechaDesdeTexto(String texto, String etiqueta) {
    try {
      int inicio = texto.toLowerCase().indexOf(etiqueta);
      if (inicio != -1) {
        String despuesEtiqueta = texto.substring(inicio + etiqueta.length()).trim();
        // Extraer la primera línea o hasta encontrar otro campo
        String[] lineas = despuesEtiqueta.split("\n");
        if (lineas.length > 0) {
          return lineas[0].trim();
        }
      }
    } catch (Exception e) {
      System.err.println("Error extrayendo fecha desde texto: " + e.getMessage());
    }
    return null;
  }

  // Método para extraer fechas de un rango (ej: "agosto 8 - agosto 14")
  private void extraerFechasDeRango(String texto, FechasExamenCalendario fechas, String mes) {
    try {
      // Patrón para "agosto 8 - agosto 14" o "agosto 8, 2024 - agosto 14, 2024"
      Pattern patronRango = Pattern.compile(
          "(" + mes + "\\s+\\d{1,2}(?:,\\s+\\d{4})?)[\\s\\-–]+(" + mes + "\\s+\\d{1,2}(?:,\\s+\\d{4})?)",
          Pattern.CASE_INSENSITIVE);

      Matcher matcher = patronRango.matcher(texto);
      if (matcher.find()) {
        String fechaInicio = matcher.group(1);
        String fechaFin = matcher.group(2);

        fechas.fechaInicioExamen = formatearFechaCalendario(fechaInicio, mes);
        fechas.fechaFinExamen = formatearFechaCalendario(fechaFin, mes);
      }

    } catch (Exception e) {
      System.err.println("Error extrayendo fechas de rango: " + e.getMessage());
    }
  }

  // Método para formatear fechas del calendario académico
  private String formatearFechaCalendario(String fechaTexto, String mes) {
    if (fechaTexto == null)
      return null;

    try {
      // Limpiar texto y normalizar espacios
      String textoLimpio = fechaTexto.replaceAll("[^a-zA-Z0-9\\s,]", " ").replaceAll("\\s+", " ").trim();
      System.out.println("Formateando fecha: '" + fechaTexto + "' -> '" + textoLimpio + "' para mes: " + mes);

      // Patrón 1: "julio 2" o "agosto 8, 2025" - buscar CUALQUIER mes, no solo el
      // específico
      Pattern patron1 = Pattern.compile(
          "(enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|octubre|noviembre|diciembre)\\s+(\\d{1,2})(?:,?\\s+(\\d{4}))?",
          Pattern.CASE_INSENSITIVE);
      Matcher matcher1 = patron1.matcher(textoLimpio);

      if (matcher1.find()) {
        String mesEncontrado = matcher1.group(1).toLowerCase();
        String dia = matcher1.group(2);
        String año = matcher1.group(3) != null ? matcher1.group(3) : "2025"; // Defaultear a 2025
        String numeroMes = convertirMesANumero(mesEncontrado);

        if (numeroMes != null) {
          String fechaFormateada = String.format("%02d/%s/%s", Integer.parseInt(dia), numeroMes, año);
          System.out.println("Fecha formateada (patrón 1): " + fechaFormateada + " (de: " + matcher1.group() + ")");
          return fechaFormateada;
        }
      }

      // Patrón 2: Solo día si ya sabemos el mes del contexto
      Pattern patron2 = Pattern.compile("\\b(\\d{1,2})\\b");
      Matcher matcher2 = patron2.matcher(textoLimpio);

      if (matcher2.find()) {
        String dia = matcher2.group(1);
        int diaInt = Integer.parseInt(dia);

        // Validar que sea un día válido
        if (diaInt >= 1 && diaInt <= 31) {
          String numeroMes = convertirMesANumero(mes.toLowerCase());
          if (numeroMes != null) {
            String fechaFormateada = String.format("%02d/%s/2025", diaInt, numeroMes);
            System.out
                .println("Fecha formateada (patrón 2): " + fechaFormateada + " (día: " + dia + ", mes: " + mes + ")");
            return fechaFormateada;
          }
        }
      }

      // Patrón 3: Fecha ya en formato DD/MM/YYYY
      Pattern patron3 = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4})");
      Matcher matcher3 = patron3.matcher(textoLimpio);

      if (matcher3.find()) {
        String fechaFormateada = String.format("%02d/%02d/%s",
            Integer.parseInt(matcher3.group(1)),
            Integer.parseInt(matcher3.group(2)),
            matcher3.group(3));
        System.out.println("Fecha formateada (patrón 3): " + fechaFormateada);
        return fechaFormateada;
      }

    } catch (Exception e) {
      System.err.println("Error formateando fecha del calendario: " + e.getMessage());
      e.printStackTrace();
    }

    System.out.println("No se pudo formatear la fecha: " + fechaTexto);
    return null;
  }

  // Método para buscar fecha de inicio de inscripción usando el patrón correcto
  // de UNNOBA
  private void buscarFechaInicioInscripcion(String mes, FechasExamenCalendario fechas) {
    try {
      // URL específica para inicio de inscripción según el patrón de UNNOBA
      String urlInscripcion = "https://elegi.unnoba.edu.ar/calendarioacademico/1o-turno-" + mes.toLowerCase()
          + "-inicio-de-inscripcion/";

      System.out.println("Buscando fecha de inscripción en: " + urlInscripcion);

      try {
        Document docInscripcion = Jsoup.connect(urlInscripcion)
            .timeout(15000)
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
            .get();

        System.out.println("Documento cargado exitosamente para " + mes);

        // 1. Buscar en los encabezados principales (h1, h2, h3) - Primera prioridad
        Elements titulos = docInscripcion.select("h1, h2, h3");
        for (Element titulo : titulos) {
          String texto = titulo.text();
          System.out.println("Analizando título: " + texto);

          // Buscar patrón específico del calendario: buscar cualquier mes seguido de día
          String fechaExtraida = extraerFechaDeInscripcionEspecifica(texto);
          if (fechaExtraida != null) {
            fechas.fechaInicioInscripcion = fechaExtraida;
            System.out.println("Fecha encontrada en título: " + fechaExtraida);
            return;
          }
        }

        // 2. Buscar en elementos que contengan "Fecha:" - Segunda prioridad
        Elements elementosFecha = docInscripcion.select("*:contains(Fecha:)");
        for (Element elemento : elementosFecha) {
          String texto = elemento.text();
          System.out.println("Analizando elemento con 'Fecha:': " + texto);
          String fechaExtraida = extraerFechaDeInscripcionEspecifica(texto);
          if (fechaExtraida != null) {
            fechas.fechaInicioInscripcion = fechaExtraida;
            System.out.println("Fecha encontrada en elemento 'Fecha:': " + fechaExtraida);
            return;
          }
        }

        // 3. Búsqueda específica en todo el texto de la página
        String textoCompleto = docInscripcion.text();
        System.out.println("Buscando patrón específico en texto completo...");
        String fechaExtraida = extraerFechaDeInscripcionEspecifica(textoCompleto);
        if (fechaExtraida != null) {
          fechas.fechaInicioInscripcion = fechaExtraida;
          System.out.println("Fecha encontrada en texto completo: " + fechaExtraida);
          return;
        }

        // 4. Búsqueda en elementos individuales con números - Última opción
        Elements elementosConNumeros = docInscripcion.select("*");
        for (Element elemento : elementosConNumeros) {
          String texto = elemento.ownText(); // Solo el texto propio del elemento
          if (texto.matches(".*\\d+.*") && texto.length() < 100) { // Filtrar textos muy largos
            String fechaExtraida2 = extraerFechaDeInscripcionEspecifica(texto);
            if (fechaExtraida2 != null) {
              fechas.fechaInicioInscripcion = fechaExtraida2;
              System.out.println("Fecha encontrada en elemento individual: " + fechaExtraida2);
              return;
            }
          }
        }

        System.out.println("No se pudo encontrar fecha de inscripción para " + mes);

      } catch (IOException e) {
        System.err.println("Error accediendo a la página de inscripción para " + mes + ": " + e.getMessage());
      }

    } catch (Exception e) {
      System.err.println("Error buscando fecha de inicio de inscripción: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Método específico para extraer fechas de las páginas de inicio de inscripción
  private String extraerFechaDeInscripcion(String texto, String mes) {
    try {
      // Limpiar texto de caracteres especiales y normalizar espacios
      String textoLimpio = texto.replaceAll("\\s+", " ").trim();

      // Patrón 1: "julio 29", "octubre 21", "septiembre 2" - CUALQUIER mes
      Pattern patron1 = Pattern.compile(
          "\\b(enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|octubre|noviembre|diciembre)\\s+(\\d{1,2})\\b",
          Pattern.CASE_INSENSITIVE);
      Matcher matcher1 = patron1.matcher(textoLimpio);

      // Buscar TODAS las coincidencias, no solo la primera
      while (matcher1.find()) {
        String mesEncontrado = matcher1.group(1).toLowerCase();
        String dia = matcher1.group(2);

        // Validar que el día sea válido
        int diaInt = Integer.parseInt(dia);
        if (diaInt >= 1 && diaInt <= 31) {
          String numeroMes = convertirMesANumero(mesEncontrado);
          if (numeroMes != null) {
            String fechaFormateada = String.format("%02d/%s/2025", diaInt, numeroMes);
            System.out.println(
                "Fecha de inscripción encontrada: " + fechaFormateada + " (de texto: " + matcher1.group() + ")");
            return fechaFormateada;
          }
        }
      }

      // Patrón 2: "29 de julio", "21 de octubre", "2 de septiembre" - CUALQUIER mes
      Pattern patron2 = Pattern.compile(
          "\\b(\\d{1,2})\\s+de\\s+(enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|octubre|noviembre|diciembre)\\b",
          Pattern.CASE_INSENSITIVE);
      Matcher matcher2 = patron2.matcher(textoLimpio);

      while (matcher2.find()) {
        String dia = matcher2.group(1);
        String mesEncontrado = matcher2.group(2).toLowerCase();

        int diaInt = Integer.parseInt(dia);
        if (diaInt >= 1 && diaInt <= 31) {
          String numeroMes = convertirMesANumero(mesEncontrado);
          if (numeroMes != null) {
            String fechaFormateada = String.format("%02d/%s/2025", diaInt, numeroMes);
            System.out.println(
                "Fecha de inscripción encontrada: " + fechaFormateada + " (de texto: " + matcher2.group() + ")");
            return fechaFormateada;
          }
        }
      }

      // Patrón 3: Solo números que podrían ser días (última opción)
      Pattern patron3 = Pattern.compile("\\b(\\d{1,2})\\b");
      Matcher matcher3 = patron3.matcher(textoLimpio);

      while (matcher3.find()) {
        String dia = matcher3.group(1);
        int diaInt = Integer.parseInt(dia);

        // Solo considerar si parece un día válido y hay contexto de mes en el texto
        if (diaInt >= 1 && diaInt <= 31) {
          // Buscar si hay algún mes mencionado en el texto cercano
          for (String mesNombre : new String[] { "enero", "febrero", "marzo", "abril", "mayo", "junio",
              "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre" }) {
            if (textoLimpio.toLowerCase().contains(mesNombre)) {
              String numeroMes = convertirMesANumero(mesNombre);
              if (numeroMes != null) {
                String fechaFormateada = String.format("%02d/%s/2025", diaInt, numeroMes);
                System.out.println("Fecha de inscripción encontrada (patrón 3): " + fechaFormateada);
                return fechaFormateada;
              }
            }
          }
        }
      }

      // Patrón 4: Fecha en formato DD/MM/YYYY
      Pattern patron4 = Pattern.compile("\\b(\\d{1,2})/(\\d{1,2})/(\\d{4})\\b");
      Matcher matcher4 = patron4.matcher(textoLimpio);

      if (matcher4.find()) {
        String fechaFormateada = String.format("%02d/%02d/%s",
            Integer.parseInt(matcher4.group(1)),
            Integer.parseInt(matcher4.group(2)),
            matcher4.group(3));
        System.out.println("Fecha de inscripción encontrada (formato estándar): " + fechaFormateada);
        return fechaFormateada;
      }

    } catch (Exception e) {
      System.err.println("Error extrayendo fecha de inscripción: " + e.getMessage());
      e.printStackTrace();
    }

    return null;
  }

  // Método específico para extraer fechas de inscripción sin depender del mes de
  // exámenes
  private String extraerFechaDeInscripcionEspecifica(String texto) {
    try {
      System.out.println("Extrayendo fecha específica de: " + texto);

      // Limpiar y normalizar el texto
      String textoLimpio = texto.replaceAll("\\s+", " ").trim();

      // Patrón 1: Buscar "mes día" directamente (ej: "junio 24", "octubre 21")
      Pattern patron1 = Pattern.compile(
          "\\b(enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|octubre|noviembre|diciembre)\\s+(\\d{1,2})\\b",
          Pattern.CASE_INSENSITIVE);
      Matcher matcher1 = patron1.matcher(textoLimpio);

      while (matcher1.find()) {
        String mesEncontrado = matcher1.group(1).toLowerCase();
        String dia = matcher1.group(2);

        int diaInt = Integer.parseInt(dia);
        if (diaInt >= 1 && diaInt <= 31) {
          String numeroMes = convertirMesANumero(mesEncontrado);
          if (numeroMes != null) {
            String fechaFormateada = String.format("%02d/%s/2025", diaInt, numeroMes);
            System.out.println("Fecha específica encontrada: " + fechaFormateada + " (de: " + matcher1.group() + ")");
            return fechaFormateada;
          }
        }
      }

      // Patrón 2: "día de mes" (ej: "24 de junio", "21 de octubre")
      Pattern patron2 = Pattern.compile(
          "\\b(\\d{1,2})\\s+de\\s+(enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|octubre|noviembre|diciembre)\\b",
          Pattern.CASE_INSENSITIVE);
      Matcher matcher2 = patron2.matcher(textoLimpio);

      while (matcher2.find()) {
        String dia = matcher2.group(1);
        String mesEncontrado = matcher2.group(2).toLowerCase();

        int diaInt = Integer.parseInt(dia);
        if (diaInt >= 1 && diaInt <= 31) {
          String numeroMes = convertirMesANumero(mesEncontrado);
          if (numeroMes != null) {
            String fechaFormateada = String.format("%02d/%s/2025", diaInt, numeroMes);
            System.out.println(
                "Fecha específica encontrada (patrón 2): " + fechaFormateada + " (de: " + matcher2.group() + ")");
            return fechaFormateada;
          }
        }
      }

      System.out.println("No se encontró fecha específica en: " + texto);
      return null;

    } catch (Exception e) {
      System.err.println("Error extrayendo fecha específica: " + e.getMessage());
      return null;
    }
  }

  // Método auxiliar para extraer fecha de texto general
  private String extraerFechaDelTexto(String texto, String mes) {
    try {
      // Buscar patrones de fecha en el texto
      String[] patronesBusqueda = {
          mes + "\\s+(\\d{1,2})",
          "(\\d{1,2})\\s+de\\s+" + mes,
          "\\d{4}[-/]\\d{2}[-/]\\d{2}"
      };

      for (String patronStr : patronesBusqueda) {
        Pattern patron = Pattern.compile(patronStr, Pattern.CASE_INSENSITIVE);
        Matcher matcher = patron.matcher(texto);

        if (matcher.find()) {
          return formatearFechaCalendario(matcher.group(), mes);
        }
      }

    } catch (Exception e) {
      System.err.println("Error extrayendo fecha del texto: " + e.getMessage());
    }

    return null;
  }

  // Método auxiliar para proporcionar información contextual según el mes
  private String obtenerTurnosDelMes(String mes) {
    switch (mes.toLowerCase()) {
      case "febrero":
        return "Turno de Febrero";
      case "marzo":
        return "Turno de Marzo";
      case "abril":
        return "Turno de Abril";
      case "mayo":
        return "Turno de Mayo";
      case "junio":
        return "Turno de Junio";
      case "julio":
        return "Turno de Julio";
      case "agosto":
        return "Turno de Agosto";
      case "septiembre":
        return "Turno de Septiembre";
      case "noviembre":
        return "Turno de Noviembre";
      case "diciembre":
        return "Turno de Diciembre";
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

  // Método auxiliar para respuesta genérica estructurada cuando no se puede hacer
  // scraping
  private String extraerExamenesPorMesGenericoEstructurado(String mes) {
    String mesCapitalizado = mes.substring(0, 1).toUpperCase() + mes.substring(1).toLowerCase();

    StringBuilder resultado = new StringBuilder();
    resultado.append("📅 **MESAS DE FINALES - ").append(mesCapitalizado.toUpperCase()).append("**\n\n");

    // Información estructurada sin fechas específicas
    resultado.append("📝 **Fecha de inicio de inscripción:** Consultar calendario académico\n\n");
    resultado.append("🗓️ **Período de Final de ").append(mesCapitalizado)
        .append(":** Consultar calendario académico\n\n");
    resultado.append(
        "⏰ **Fecha fin de inscripción:** 48 hrs antes del final al que quieras inscribirte, sin contar los sábados y domingos.\n\n");

    // Ejemplo del tiempo límite
    resultado.append("💡 **Ejemplo de tiempo límite:**\n");
    resultado.append(
        "• Si un final es un **viernes a las 9:00 hs**, podés inscribirte hasta el **miércoles a las 9:00 hs**\n");
    resultado.append(
        "• Si un final es un **lunes a las 14:00 hs**, podés inscribirte hasta el **miércoles anterior a las 14:00 hs**\n");
    resultado.append("• Los sábados y domingos **NO cuentan** como días hábiles\n\n");

    // Enlaces importantes
    resultado.append("🔗 **Enlaces importantes:**\n");
    resultado.append("• **Calendario Académico:** https://elegi.unnoba.edu.ar/calendario/\n");
    resultado.append("• **SIU-Guaraní (Inscripciones):** https://g3w3.unnoba.edu.ar/g3w3/\n\n");

    // Información sobre horarios
    resultado.append("🕐 **Información sobre horarios:**\n");
    resultado.append("Los horarios específicos de los exámenes finales se proporcionan en el **SIU-Guaraní**. ");
    resultado.append("En el calendario académico solo encontrarás la información sobre cuándo se habilita ");
    resultado.append("la inscripción y cuál es el período de exámenes.\n\n");

    resultado.append("⚠️ **Importante:**\n");
    resultado.append("Consultá el calendario académico regularmente, ya que las fechas pueden actualizarse.");

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