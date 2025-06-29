package unnoba.ai.back.demo.controller;

import org.springframework.web.bind.annotation.*;
import unnoba.ai.back.demo.service.CalendarioService;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/unnoba/calendario")
public class CalendarioController {

  private final CalendarioService calendarioService;

  public CalendarioController(CalendarioService calendarioService) {
    this.calendarioService = calendarioService;
  }

  // Endpoints para inscripción a materias
  @GetMapping("/inscripcion-materias")
  public String getInscripcionMaterias() throws IOException {
    return calendarioService.extraerFechasInscripcionMaterias();
  }

  @GetMapping("/inscripcion-materias-primer-cuatrimestre")
  public String getInscripcionMateriasPrimerCuatrimestre() throws IOException {
    return calendarioService.extraerFechasInscripcionPrimerCuatrimestre();
  }

  @GetMapping("/inscripcion-materias-segundo-cuatrimestre")
  public String getInscripcionMateriasSegundoCuatrimestre() throws IOException {
    return calendarioService.extraerFechasInscripcionSegundoCuatrimestre();
  }

  @GetMapping("/inscripcion-materias-detallada")
  public String getInscripcionMateriasDetallada() throws IOException {
    return calendarioService.extraerFechasInscripcionMateriasDetallada();
  }

  // Endpoints específicos para preguntas frecuentes del calendario
  @GetMapping("/inicio-cuatrimestres")
  public String getInicioCuatrimestres() throws IOException {
    return "🎓 **INICIO DE CUATRIMESTRES - UNNOBA**\n\n" +
        calendarioService.extraerFechasEspecificas("inicio_cuatrimestre") +
        "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";
  }

  @GetMapping("/fin-cuatrimestres")
  public String getFinCuatrimestres() throws IOException {
    return "📚 **FIN DE CUATRIMESTRES - UNNOBA**\n\n" +
        calendarioService.extraerFechasEspecificas("fin_cuatrimestre") +
        "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";
  }

  @GetMapping("/confirmacion-inscripcion")
  public String getConfirmacionInscripcion() throws IOException {
    return "✅ **CONFIRMACIÓN DE INSCRIPCIÓN - UNNOBA**\n\n" +
        calendarioService.extraerFechasEspecificas("confirmacion") +
        "\n🔗 **Sistema SIU-Guaraní:** https://g3w3.unnoba.edu.ar/g3w3/";
  }

  @GetMapping("/vacaciones-invierno")
  public String getVacacionesInvierno() {
    return """
        ❄️ **VACACIONES DE INVIERNO - UNNOBA**

        Para obtener las fechas exactas del receso invernal en la UNNOBA, te recomiendo consultar el calendario académico oficial de la universidad. La información allí es siempre la más precisa y actualizada.

        Por desgracia, no tengo acceso en tiempo real a esa información específica.

        📅 **Calendario Académico Oficial:** https://elegi.unnoba.edu.ar/calendario/

        **💡 En el calendario académico encontrarás:**
        • Fechas exactas del receso invernal
        • Duración de las vacaciones
        • Reinicio de actividades académicas
        • Otras fechas importantes del año lectivo
        """;
  }

  @GetMapping("/feriados")
  public String getFeriados() {
    return """
        🎉 **FERIADOS Y DÍAS NO LABORABLES - UNNOBA**

        Para obtener la información exacta sobre los feriados en la UNNOBA, te recomiendo consultar el calendario académico oficial de la universidad. La información allí es siempre la más precisa y actualizada.

        Por desgracia, no tengo acceso en tiempo real a esa información específica.

        📅 **Calendario Académico Oficial:** https://elegi.unnoba.edu.ar/calendario/

        **💡 En el calendario académico encontrarás:**
        • Feriados nacionales
        • Días no laborables universitarios
        • Recesos académicos
        • Fechas especiales de la universidad
        """;
  }

  // Nuevo endpoint para consultas específicas de fechas
  @GetMapping("/fechas/{tipo}")
  public String getFechasEspecificas(@PathVariable String tipo) throws IOException {
    return switch (tipo.toLowerCase()) {
      case "inicio-primer-cuatrimestre" ->
        "🎓 **INICIO PRIMER CUATRIMESTRE**\n\n" +
            calendarioService.extraerFechasEspecificas("inicio_primer_cuatrimestre") +
            "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";

      case "inicio-segundo-cuatrimestre" ->
        "🎓 **INICIO SEGUNDO CUATRIMESTRE**\n\n" +
            calendarioService.extraerFechasEspecificas("inicio_segundo_cuatrimestre") +
            "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";

      case "fin-primer-cuatrimestre" ->
        "📚 **FIN PRIMER CUATRIMESTRE**\n\n" +
            calendarioService.extraerFechasEspecificas("fin_primer_cuatrimestre") +
            "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";

      case "fin-segundo-cuatrimestre" ->
        "📚 **FIN SEGUNDO CUATRIMESTRE**\n\n" +
            calendarioService.extraerFechasEspecificas("fin_segundo_cuatrimestre") +
            "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";

      case "inscripcion-carrera" ->
        "✅ **INSCRIPCIÓN A LA CARRERA**\n\n" +
            calendarioService.extraerFechasEspecificas("inscripcion_carrera") +
            "\n🔗 **Sistema SIU-Guaraní:** https://g3w3.unnoba.edu.ar/g3w3/";

      case "feriados" -> getFeriados();
      case "vacaciones" -> getVacacionesInvierno();

      default -> "❓ **CONSULTA NO RECONOCIDA**\n\nTipos de consulta disponibles:\n" +
          "• inicio-primer-cuatrimestre\n• inicio-segundo-cuatrimestre\n" +
          "• fin-primer-cuatrimestre\n• fin-segundo-cuatrimestre\n" +
          "• inscripcion-carrera\n• feriados\n• vacaciones\n\n" +
          "📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";
    };
  }

  // Nuevo endpoint para consultas generales
  @GetMapping("/consultar")
  public String consultarEvento(@RequestParam String consulta) throws IOException {
    return calendarioService.consultarFechaEspecifica(consulta);
  }

  @GetMapping("/calendario-academico")
  public String getCalendarioAcademico() throws IOException {
    return calendarioService.obtenerInformacionCalendario();
  }
}