package unnoba.ai.back.demo.carreras;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class EnlacesInscripciones {

  private Set<String> visitados = new HashSet<>();

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

  // Método especializado para extraer información específica del calendario
  public String extraerInformacionCalendario() {
    try {
      Document doc = Jsoup.connect("https://elegi.unnoba.edu.ar/calendario/")
          .timeout(10000)
          .userAgent("Mozilla/5.0")
          .get();

      StringBuilder info = new StringBuilder();

      // Extraer eventos específicos del calendario
      Elements eventos = doc.select("h3, h4, .event-title, [class*='evento']");

      // Mapas para organizar la información por tipo
      Set<String> inscripciones = new HashSet<>();
      Set<String> examenes = new HashSet<>();
      Set<String> feriados = new HashSet<>();
      Set<String> clases = new HashSet<>();
      Set<String> confirmaciones = new HashSet<>();

      for (Element evento : eventos) {
        String texto = evento.text().toLowerCase();
        String textoCompleto = evento.text();

        // Clasificar eventos por tipo
        if (texto.contains("inscripción") || texto.contains("inscripcion")) {
          if (texto.contains("turno") || texto.contains("inicio")) {
            inscripciones.add(textoCompleto);
          }
          if (texto.contains("confirmación") || texto.contains("confirmacion")) {
            confirmaciones.add(textoCompleto);
          }
        } else if (texto.contains("examen") || texto.contains("final")) {
          examenes.add(textoCompleto);
        } else if (texto.contains("feriado") || texto.contains("no laborable") ||
            texto.contains("día no laborable") || texto.contains("actividad académica")) {
          feriados.add(textoCompleto);
        } else if (texto.contains("inicio")
            && (texto.contains("clase") || texto.contains("semestre") || texto.contains("cuatrimestre"))) {
          clases.add(textoCompleto);
        } else if (texto.contains("fin")
            && (texto.contains("clase") || texto.contains("semestre") || texto.contains("cuatrimestre"))) {
          clases.add(textoCompleto);
        }
      }

      // Construir respuesta organizada
      if (!inscripciones.isEmpty()) {
        info.append("📝 **INSCRIPCIONES:**\n");
        int count = 0;
        for (String evento : inscripciones) {
          if (count++ < 5) {
            info.append("• ").append(evento).append("\n");
          }
        }
        info.append("\n");
      }

      if (!examenes.isEmpty()) {
        info.append("📋 **EXÁMENES:**\n");
        int count = 0;
        for (String evento : examenes) {
          if (count++ < 5) {
            info.append("• ").append(evento).append("\n");
          }
        }
        info.append("\n");
      }

      if (!clases.isEmpty()) {
        info.append("🎓 **CLASES Y CUATRIMESTRES:**\n");
        int count = 0;
        for (String evento : clases) {
          if (count++ < 3) {
            info.append("• ").append(evento).append("\n");
          }
        }
        info.append("\n");
      }

      if (!feriados.isEmpty()) {
        info.append("🎉 **FERIADOS Y DÍAS NO LABORABLES:**\n");
        int count = 0;
        for (String evento : feriados) {
          if (count++ < 5) {
            info.append("• ").append(evento).append("\n");
          }
        }
        info.append("\n");
      }

      if (!confirmaciones.isEmpty()) {
        info.append("✅ **CONFIRMACIONES:**\n");
        int count = 0;
        for (String evento : confirmaciones) {
          if (count++ < 3) {
            info.append("• ").append(evento).append("\n");
          }
        }
        info.append("\n");
      }

      return info.toString();

    } catch (IOException e) {
      System.err.println("Error extrayendo información del calendario: " + e.getMessage());
      return "Error al acceder al calendario académico. Consultá directamente en: https://elegi.unnoba.edu.ar/calendario/";
    }
  }

  // Método para extraer fechas específicas del calendario
  public String extraerFechasEspecificas(String tipoConsulta) {
    try {
      Document doc = Jsoup.connect("https://elegi.unnoba.edu.ar/calendario/")
          .timeout(10000)
          .userAgent("Mozilla/5.0")
          .get();

      StringBuilder resultado = new StringBuilder();

      // Extraer todos los elementos que contengan fechas y eventos
      Elements eventos = doc.select("h3, h4, .event-title, [class*='evento'], [class*='fecha']");

      switch (tipoConsulta.toLowerCase()) {
        case "inicio_cuatrimestre":
          for (Element evento : eventos) {
            String texto = evento.text().toLowerCase();
            if ((texto.contains("inicio")
                && (texto.contains("semestre") || texto.contains("cuatrimestre") || texto.contains("clase"))) ||
                (texto.contains("comienzo") && texto.contains("clase"))) {
              resultado.append("• ").append(evento.text()).append("\n");
            }
          }
          break;

        case "fin_cuatrimestre":
          for (Element evento : eventos) {
            String texto = evento.text().toLowerCase();
            if ((texto.contains("fin") || texto.contains("término") || texto.contains("termino")) &&
                (texto.contains("semestre") || texto.contains("cuatrimestre") || texto.contains("clase"))) {
              resultado.append("• ").append(evento.text()).append("\n");
            }
          }
          break;

        case "examenes":
          for (Element evento : eventos) {
            String texto = evento.text().toLowerCase();
            if (texto.contains("examen") || texto.contains("final") || texto.contains("turno")) {
              resultado.append("• ").append(evento.text()).append("\n");
            }
          }
          break;

        case "vacaciones":
          for (Element evento : eventos) {
            String texto = evento.text().toLowerCase();
            if (texto.contains("vacacion") || texto.contains("receso") ||
                (texto.contains("invierno") && !texto.contains("cronograma"))) {
              resultado.append("• ").append(evento.text()).append("\n");
            }
          }
          break;

        case "confirmacion":
          for (Element evento : eventos) {
            String texto = evento.text().toLowerCase();
            if (texto.contains("confirmación") || texto.contains("confirmacion")) {
              resultado.append("• ").append(evento.text()).append("\n");
            }
          }
          break;
      }

      if (resultado.length() == 0) {
        return "• Consultá el calendario académico para información actualizada: https://elegi.unnoba.edu.ar/calendario/";
      }

      return resultado.toString();

    } catch (IOException e) {
      System.err.println("Error extrayendo fechas específicas: " + e.getMessage());
      return "• Error al consultar el calendario. Accedé directamente en: https://elegi.unnoba.edu.ar/calendario/";
    }
  }

  // Método específico para extraer exámenes por mes - Respuesta genérica
  public String extraerExamenesPorMes(String mes) {
    // Respuesta genérica que dirige al calendario académico oficial
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
}