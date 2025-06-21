package unnoba.ai.back.demo.carreras;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class EnlacesDistribucionAulas {

  // Mapeo de edificios por campus
  private final Map<String, String> edificiosJunin = Map.of(
      "eva peron", "evaperon",
      "sarmiento", "evaperon",
      "maria eva duarte", "evaperon",
      "eva duarte", "evaperon",
      "elvira rawson", "anexo",
      "rawson", "anexo",
      "anexo", "anexo",
      "manuel belgrano", "argenlac",
      "belgrano", "argenlac",
      "argenlac", "argenlac");

  private final Map<String, String> edificiosPergamino = Map.of(
      "monteagudo", "monteagudo",
      "principal", "monteagudo",
      "agrarias", "agrarias",
      "ciencias agrarias", "agrarias",
      "naturales", "agrarias",
      "ambientales", "agrarias");

  // Mapeo de nombres descriptivos
  private final Map<String, String> nombresEdificios = Map.of(
      "evaperon", "Edificio María Eva Duarte de Perón (Newbery 355 y Sarmiento)",
      "anexo", "Edificio Elvira Rawson de Dellepiane (Rivadavia 453 y Newbery)",
      "argenlac", "Predio Manuel Belgrano (Gaucho Argentino y Ruta Nacional Nº7)",
      "monteagudo", "Edificio Principal (Monteagudo 2772)",
      "agrarias", "Edificio Esc. Cs. Agrarias Naturales y Ambientales");

  public String obtenerDistribucionEspecifica(String consulta) {
    try {
      // Detectar campus y edificio desde la consulta
      String campus = detectarCampus(consulta);
      String edificio = detectarEdificio(consulta, campus);

      if (campus == null) {
        return "⚠️ No pude identificar el campus. Podés consultar por:\n" +
            "• **Junín**: Eva Perón/Sarmiento, Rawson/Anexo, Belgrano/Argenlac\n" +
            "• **Pergamino**: Monteagudo/Principal, Agrarias";
      }

      if (edificio == null) {
        String edificiosDisponibles = campus.equals("junin") ? "Eva Perón/Sarmiento, Rawson/Anexo, Belgrano/Argenlac"
            : "Monteagudo/Principal, Agrarias";
        return String.format("⚠️ No pude identificar el edificio en %s. Edificios disponibles:\n• %s",
            campus.toUpperCase(), edificiosDisponibles);
      }

      // Obtener fecha actual
      LocalDate hoy = LocalDate.now();
      String fechaFormateada = hoy.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

      // Construir URL
      String url = String.format("https://unnoba.edu.ar/distribucion-aulas/download?campus=%s&building=%s&date=%s",
          campus, edificio, fechaFormateada);

      // Hacer scraping del contenido
      Document doc = Jsoup.connect(url).get();

      // Verificar si hay contenido
      Elements tablas = doc.select("*");
      if (tablas.isEmpty()) {
        return String.format("📅 **DISTRIBUCIÓN DE AULAS**\n\n" +
            "⚠️ No hay distribución disponible para hoy en %s.\n\n" +
            "📋 **Consultá la distribución completa en:**\n" +
            "• https://unnoba.edu.ar/distribucion-aulas/%s",
            nombresEdificios.getOrDefault(edificio, edificio), campus);
      }

      // Extraer información de la tabla
      StringBuilder resultado = new StringBuilder();
      resultado.append("🏢 **DISTRIBUCIÓN DE AULAS - ").append(hoy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
          .append("**\n\n");
      resultado.append("📍 **").append(nombresEdificios.getOrDefault(edificio, edificio)).append("**\n\n");

      // Procesar tabla principal
      Element tablaMain = tablas.first();
      Elements filas = tablaMain.select("tr");

      if (filas.size() > 2) { // Tiene header y al menos una fila de datos
        resultado.append("📋 **Horarios de hoy:**\n");

        // Obtener headers (horarios)
        Element headerRow = filas.get(0);
        Elements headers = headerRow.select("th, td");

        // Procesar cada fila de aulas
        for (int i = 1; i < Math.min(filas.size(), 11); i++) { // Limitar a 10 aulas
          Element fila = filas.get(i);
          Elements celdas = fila.select("td");

          if (celdas.size() > 0) {
            String aula = celdas.get(0).text().trim();
            if (!aula.isEmpty() && !aula.toLowerCase().contains("no usar")) {
              resultado.append("• **").append(aula).append("**\n");

              // Mostrar actividades del día
              for (int j = 1; j < Math.min(celdas.size(), 6); j++) {
                String actividad = celdas.get(j).text().trim();
                if (!actividad.isEmpty()) {
                  String horario = j < headers.size() ? headers.get(j).text().trim() : "";
                  resultado.append("  ").append(horario).append(": ").append(actividad).append("\n");
                }
              }
              resultado.append("\n");
            }
          }
        }
      } else {
        resultado.append("📋 Sin actividades programadas para hoy\n\n");
      }

      resultado.append("🔗 **Ver distribución completa:**\n");
      resultado.append("• ").append(url).append("\n");
      resultado.append("• https://unnoba.edu.ar/distribucion-aulas/").append(campus);

      return resultado.toString();

    } catch (Exception e) {
      return "❌ **Error al consultar distribución de aulas**\n\n" +
          "Por favor, consultá directamente en:\n" +
          "• **Junín:** https://unnoba.edu.ar/distribucion-aulas/junin\n" +
          "• **Pergamino:** https://unnoba.edu.ar/distribucion-aulas/pergamino";
    }
  }

  private String detectarCampus(String consulta) {
    String consultaLower = consulta.toLowerCase();

    if (consultaLower.contains("junin") || consultaLower.contains("junín")) {
      return "junin";
    }
    if (consultaLower.contains("pergamino")) {
      return "pergamino";
    }

    // Detectar por edificios característicos
    if (edificiosJunin.keySet().stream().anyMatch(consultaLower::contains)) {
      return "junin";
    }
    if (edificiosPergamino.keySet().stream().anyMatch(consultaLower::contains)) {
      return "pergamino";
    }

    return null;
  }

  private String detectarEdificio(String consulta, String campus) {
    if (campus == null)
      return null;

    String consultaLower = consulta.toLowerCase();
    Map<String, String> edificiosMap = campus.equals("junin") ? edificiosJunin : edificiosPergamino;

    for (Map.Entry<String, String> entry : edificiosMap.entrySet()) {
      if (consultaLower.contains(entry.getKey())) {
        return entry.getValue();
      }
    }

    return null;
  }
}