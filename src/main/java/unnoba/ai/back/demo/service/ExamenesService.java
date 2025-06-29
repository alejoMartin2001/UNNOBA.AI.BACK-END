package unnoba.ai.back.demo.service;

import java.io.IOException;
import org.springframework.stereotype.Service;
import unnoba.ai.back.demo.constants.UnnobaUrls;
import unnoba.ai.back.demo.utils.DateUtils;

@Service
public class ExamenesService {

  private final ScrapingService scrapingService;

  public ExamenesService(ScrapingService scrapingService) {
    this.scrapingService = scrapingService;
  }

  /**
   * Extrae información de exámenes por mes
   */
  public String extraerExamenesPorMes(String mes) throws IOException {
    try {
      String response = scrapingService.obtenerFechasExamenesPorMes(mes);
      return response + "\n\n🔗 **Inscripción a finales:** " + UnnobaUrls.BASE_SIU_URL + "\n" +
          "📅 **Calendario académico:** " + UnnobaUrls.CALENDARIO_URL;
    } catch (IOException e) {
      System.err.println("Error al extraer fechas de examen para " + mes + ": " + e.getMessage());
      String mesCapitalizado = DateUtils.capitalizarMes(mes);
      return "📝 **EXÁMENES FINALES - " + mesCapitalizado.toUpperCase() + "**\n\n" +
          "**📋 Información:**\n" +
          "• En " + mesCapitalizado
          + " generalmente no hay mesas de exámenes finales.\n" +
          "• Los turnos de examen suelen ser en: Febrero, Marzo, Abril, Mayo, Junio, Julio, Agosto, Septiembre, Noviembre y Diciembre.\n"
          +
          "• Consultá el calendario académico para confirmar.\n\n" +
          "🔗 **Inscripción a finales:** " + UnnobaUrls.BASE_SIU_URL + "\n" +
          "📅 **Calendario académico:** " + UnnobaUrls.CALENDARIO_URL;
    }
  }

  /**
   * Obtiene información general de exámenes finales
   */
  public String obtenerInformacionExamenesFinales() {
    return """
        **EXÁMENES FINALES**

        Las mesas de exámenes finales están programadas según las fechas establecidas en el calendario académico oficial de la UNNOBA. Generalmente, se realizan durante la segunda semana de cada mes. Sin embargo, es importante tener en cuenta que en los meses de enero y octubre no se habilita la inscripción a mesas de finales. Para conocer las fechas exactas y actualizadas, se recomienda consultar el calendario académico disponible en el sitio web de la universidad.

        🔗 **Inscripción a finales:** """
        + UnnobaUrls.BASE_SIU_URL + "\n" +
        "📅 **Calendario académico:** " + UnnobaUrls.CALENDARIO_URL;
  }

  // Métodos privados auxiliares

  private String obtenerMensajeError(String mes) {
    String mesCapitalizado = DateUtils.capitalizarMes(mes);
    return "📝 **EXÁMENES FINALES - " + mesCapitalizado.toUpperCase() + "**\n\n" +
        "**📋 Información:**\n" +
        "• En " + mesCapitalizado
        + " generalmente no hay mesas de exámenes finales.\n" +
        "• Los turnos de examen suelen ser en: Febrero, Marzo, Abril, Mayo, Junio, Julio, Agosto, Septiembre, Noviembre y Diciembre.\n"
        +
        "• Consultá el calendario académico para confirmar.\n\n" +
        "🔗 **Inscripción a finales:** " + UnnobaUrls.BASE_SIU_URL + "\n" +
        "📅 **Calendario académico:** " + UnnobaUrls.CALENDARIO_URL;
  }
}