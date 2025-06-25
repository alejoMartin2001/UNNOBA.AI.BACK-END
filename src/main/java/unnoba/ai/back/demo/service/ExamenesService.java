package unnoba.ai.back.demo.service;

import org.springframework.stereotype.Service;
import unnoba.ai.back.demo.constants.UnnobaUrls;
import unnoba.ai.back.demo.utils.DateUtils;

import java.io.IOException;

@Service
public class ExamenesService {

  /**
   * Extrae información de exámenes por mes
   */
  public String extraerExamenesPorMes(String mes) throws IOException {
    String mesCapitalizado = DateUtils.capitalizarMes(mes);

    StringBuilder respuesta = new StringBuilder();
    respuesta.append("📝 **EXÁMENES FINALES - ").append(mesCapitalizado.toUpperCase()).append(" 2025**\n\n");

    // Verificar si es un mes con mesas de examen
    if (esMesConMesasDeExamen(mes)) {
      respuesta.append("**📋 Información de exámenes:**\n");
      respuesta.append("• Consultá las fechas exactas en el calendario académico\n");
      respuesta.append("• Las inscripciones suelen abrir con anticipación\n");
      respuesta.append("• Verificá los turnos disponibles en el SIU-Guaraní\n\n");
    } else {
      respuesta.append("**📋 Información:**\n");
      respuesta.append("• En ").append(mesCapitalizado).append(" generalmente no hay mesas de exámenes finales\n");
      respuesta.append(
          "• Los turnos de examen suelen ser en: Febrero, Marzo, Abril, Mayo, Junio, Julio, Agosto, Septiembre, Noviembre y Diciembre\n");
      respuesta.append("• Consultá el calendario académico para confirmar\n\n");
    }

    respuesta.append("🔗 **Inscripción a finales:** ").append(UnnobaUrls.BASE_SIU_URL).append("\n");
    respuesta.append("📅 **Calendario académico:** ").append(UnnobaUrls.CALENDARIO_URL);

    return respuesta.toString();
  }

  /**
   * Obtiene información general de exámenes finales
   */
  public String obtenerInformacionExamenesFinales() {
    return """
        📝 **EXÁMENES FINALES - UNNOBA**

        **📋 Turnos de exámenes:**
        • **Febrero-Marzo:** Turno principal del verano
        • **Julio:** Turno de invierno
        • **Diciembre:** Turno de diciembre

        **💡 Información importante:**
        • Las inscripciones abren con anticipación
        • Verificá siempre las fechas en el calendario académico
        • Podés inscribirte a través del SIU-Guaraní

        **Inscripción a finales:** """ + UnnobaUrls.BASE_SIU_URL + "\n" +
        "**Calendario académico:** " + UnnobaUrls.CALENDARIO_URL;
  }

  // Métodos privados auxiliares

  private boolean esMesConMesasDeExamen(String mes) {
    String mesLower = mes.toLowerCase();
    return mesLower.equals("febrero") ||
        mesLower.equals("marzo") ||
        mesLower.equals("abril") ||
        mesLower.equals("mayo") ||
        mesLower.equals("junio") ||
        mesLower.equals("julio") ||
        mesLower.equals("agosto") ||
        mesLower.equals("septiembre") ||
        mesLower.equals("noviembre") ||
        mesLower.equals("diciembre");
  }
}