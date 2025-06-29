package unnoba.ai.back.demo.service;

import org.springframework.stereotype.Service;
import unnoba.ai.back.demo.constants.UnnobaUrls;

@Service
public class DistribucionAulasService {

  /**
   * Obtiene información de distribución de aulas
   */
  public String obtenerDistribucionAulas(String consulta) {
    if (consulta != null && !consulta.trim().isEmpty()) {
      return obtenerDistribucionEspecifica(consulta);
    } else {
      return obtenerDistribucionGeneral();
    }
  }

  /**
   * Obtiene información general de distribución de aulas
   */
  public String obtenerDistribucionGeneral() {
    return """
        🏢 **DISTRIBUCIÓN DE AULAS - UNNOBA**

        La distribución de aulas puede consultarse a través de los siguientes enlaces:

        **📚 Junín:**
        • **Distribución de aulas - Junín:** """ + UnnobaUrls.DISTRIBUCION_AULAS_JUNIN + """

        **📚 Pergamino:**
        • **Distribución de aulas - Pergamino:** """ + UnnobaUrls.DISTRIBUCION_AULAS_PERGAMINO + """

        **💡 Información importante:**
        • La distribución se actualiza cada cuatrimestre
        • Verificá siempre antes de dirigirte al aula
        • Consultá con bedeles ante cualquier duda
        """;
  }

  /**
   * Obtiene información específica de distribución según la consulta
   */
  public String obtenerDistribucionEspecifica(String consulta) {
    String consultaLower = consulta.toLowerCase();
    StringBuilder respuesta = new StringBuilder();

    respuesta.append("🏢 **DISTRIBUCIÓN DE AULAS - UNNOBA**\n\n");
    respuesta.append("**Consulta para: \"").append(consulta).append("\"**\n\n");

    if (consultaLower.contains("junin") || consultaLower.contains("junín")) {
      respuesta.append("**📚 Sede Junín:**\n");
      respuesta.append("• **Enlace directo:** ").append(UnnobaUrls.DISTRIBUCION_AULAS_JUNIN).append("\n");
      respuesta.append("• Todas las aulas y horarios de la sede Junín\n\n");
    }

    if (consultaLower.contains("pergamino")) {
      respuesta.append("**📚 Sede Pergamino:**\n");
      respuesta.append("• **Enlace directo:** ").append(UnnobaUrls.DISTRIBUCION_AULAS_PERGAMINO).append("\n");
      respuesta.append("• Todas las aulas y horarios de la sede Pergamino\n\n");
    }

    if (!consultaLower.contains("junin") && !consultaLower.contains("junín") &&
        !consultaLower.contains("pergamino")) {
      respuesta.append("**📋 Información encontrada:**\n");
      respuesta.append("• Para consultas específicas, accedé a los enlaces de distribución\n");
      respuesta.append("• La información se organiza por sede y carrera\n\n");

      respuesta.append("**📚 Enlaces directos:**\n");
      respuesta.append("• **Junín:** ").append(UnnobaUrls.DISTRIBUCION_AULAS_JUNIN).append("\n");
      respuesta.append("• **Pergamino:** ").append(UnnobaUrls.DISTRIBUCION_AULAS_PERGAMINO).append("\n\n");
    }

    respuesta.append("**💡 Recomendaciones:**\n");
    respuesta.append("• Verificá la distribución antes del inicio de cada cuatrimestre\n");
    respuesta.append("• Consultá con bedeles ante cambios de último momento\n");
    respuesta.append("• Guardá los horarios en tu celular para acceso rápido");

    return respuesta.toString();
  }
}