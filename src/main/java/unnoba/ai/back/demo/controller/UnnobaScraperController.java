package unnoba.ai.back.demo.controller;

import org.springframework.web.bind.annotation.*;
import unnoba.ai.back.demo.service.CarrerasService;
import unnoba.ai.back.demo.service.CalendarioService;
import unnoba.ai.back.demo.service.ExamenesService;
import unnoba.ai.back.demo.service.DistribucionAulasService;
import unnoba.ai.back.demo.constants.UnnobaUrls;

import java.io.IOException;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/unnoba")
public class UnnobaScraperController {

    private final CarrerasService carrerasService;
    private final CalendarioService calendarioService;
    private final ExamenesService examenesService;
    private final DistribucionAulasService distribucionAulasService;

    public UnnobaScraperController(CarrerasService carrerasService,
            CalendarioService calendarioService,
            ExamenesService examenesService,
            DistribucionAulasService distribucionAulasService) {
        this.carrerasService = carrerasService;
        this.calendarioService = calendarioService;
        this.examenesService = examenesService;
        this.distribucionAulasService = distribucionAulasService;
    }

    // Carreras de Diseño
    @GetMapping("/diseño-grafico")
    public String getDiseñoGrafico() throws IOException {
        return carrerasService.obtenerDiseñoGrafico();
    }

    @GetMapping("/diseño-indumentaria-y-textil")
    public String getDiseñoIndumentariaYTextil() throws IOException {
        return carrerasService.obtenerDiseñoIndumentaria();
    }

    @GetMapping("/diseño-industrial")
    public String getDiseñoIndustrial() throws IOException {
        return carrerasService.obtenerDiseñoIndustrial();
    }

    // Carreras de Informática
    @GetMapping("/ingenieria-informatica")
    public String getInformatica() throws IOException {
        return carrerasService.obtenerIngenieriaInformatica();
    }

    @GetMapping("/licenciatura-sistemas")
    public String getLicenciaturaSistemas() throws IOException {
        return carrerasService.obtenerLicenciaturaSistemas();
    }

    @GetMapping("/analista-sistemas")
    public String getAnalistaSistemas() throws IOException {
        return carrerasService.obtenerAnalistaSistemas();
    }

    @GetMapping("/tecnicatura-diseño-desarrollo-aplicaciones-multiplataforma")
    public String getTecnicaturaDiseñoDesarrolloAplicaciones() throws IOException {
        return carrerasService.obtenerTecnicaturaApps();
    }

    // Carreras de Salud
    @GetMapping("/medicina")
    public String getMedicina() throws IOException {
        return carrerasService.obtenerMedicina();
    }

    @GetMapping("/enfermeria")
    public String getEnfermeria() throws IOException {
        return carrerasService.obtenerEnfermeria();
    }

    // Carreras de Derecho y Económicas
    @GetMapping("/abogacia")
    public String getAbogacia() throws IOException {
        return carrerasService.obtenerAbogacia();
    }

    @GetMapping("/contador-publico")
    public String getContadorPublico() throws IOException {
        return carrerasService.obtenerContadorPublico();
    }

    @GetMapping("/licenciatura-administracion")
    public String getLicenciaturaAdministracion() throws IOException {
        return carrerasService.obtenerLicenciaturaAdministracion();
    }

    // Carreras de Ingeniería
    @GetMapping("/agronomia")
    public String getAgronomia() throws IOException {
        return carrerasService.obtenerAgronomia();
    }

    @GetMapping("/ingenieria-mecatronica")
    public String getMecatronica() throws IOException {
        return carrerasService.obtenerIngenieriaMecatronica();
    }

    @GetMapping("/ingenieria-industrial")
    public String getIngenieriaIndustrial() throws IOException {
        return carrerasService.obtenerIngenieriaIndustrial();
    }

    @GetMapping("/ingenieria-mecanica")
    public String getIngenieriaMecanica() throws IOException {
        return carrerasService.obtenerIngenieriaMecanica();
    }

    @GetMapping("/ingenieria-alimentos")
    public String getIngenieriaAlimentos() throws IOException {
        return carrerasService.obtenerIngenieriaAlimentos();
    }

    // Carreras de Ciencias
    @GetMapping("/genetica")
    public String getGenetica() throws IOException {
        return carrerasService.obtenerLicenciaturaGenetica();
    }

    // Tecnicaturas
    @GetMapping("/tecnicatura-mantenimiento-industrial")
    public String getTecnicaturaMantenimientoIndustrial() throws IOException {
        return carrerasService.obtenerTecnicaturaMantenimiento();
    }

    @GetMapping("/tecnicatura-gestion-pymes")
    public String getTecnicaturaGestionPymes() throws IOException {
        return carrerasService.obtenerTecnicaturaPymes();
    }

    @GetMapping("/tecnicatura-gestion-publica")
    public String getTecnicaturaGestionPublica() throws IOException {
        return carrerasService.obtenerTecnicaturaPublica();
    }

    @GetMapping("/tecnicatura-produccion-alimentos")
    public String getTecnicaturaProduccionAlimentos() throws IOException {
        return carrerasService.obtenerTecnicaturaAlimentos();
    }

    // Endpoints de inscripción a materias
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

    @GetMapping("/examenes-finales")
    public String getExamenesFinales() throws IOException {
        return examenesService.obtenerInformacionExamenesFinales();
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

    @GetMapping("/confirmacion-inscripcion")
    public String getConfirmacionInscripcion() throws IOException {
        return "✅ **CONFIRMACIÓN DE INSCRIPCIÓN - UNNOBA**\n\n" +
                calendarioService.extraerFechasEspecificas("confirmacion") +
                "\n🔗 **Sistema SIU-Guaraní:** https://g3w3.unnoba.edu.ar/g3w3/";
    }

    // Endpoint para distribución de aulas específica
    @GetMapping("/distribucion-aulas")
    public String getDistribucionAulas(@RequestParam(required = false) String consulta) {
        return distribucionAulasService.obtenerDistribucionAulas(consulta);
    }

    @GetMapping("/regularidad-estudiantes")
    public String getRegularidadEstudiantes() {
        return carrerasService.obtenerRegularidadEstudiantes();
    }

    @GetMapping("/informacion-calendario")
    public String getInformacionCalendario() {
        return "📅 **CALENDARIO ACADÉMICO - UNNOBA**\n\n" +
                "Para conocer todas las fechas importantes, como inicios de cuatrimestre, exámenes, feriados y recesos, te recomendamos consultar el calendario académico oficial.\n\n"
                +
                "🔗 **Ver calendario completo:** " + UnnobaUrls.CALENDARIO_URL;
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

            case "mesas-finales" ->
                "📝 **MESAS DE EXÁMENES FINALES**\n\n" +
                        calendarioService.extraerFechasEspecificas("mesas_finales") +
                        "\n🔗 **Inscripción:** https://g3w3.unnoba.edu.ar/g3w3/";

            case "inscripcion-carrera" ->
                "✅ **INSCRIPCIÓN A LA CARRERA**\n\n" +
                        calendarioService.extraerFechasEspecificas("inscripcion_carrera") +
                        "\n🔗 **Sistema SIU-Guaraní:** https://g3w3.unnoba.edu.ar/g3w3/";

            case "feriados" -> getFeriados();
            case "vacaciones" -> getVacacionesInvierno();

            default -> "❓ **CONSULTA NO RECONOCIDA**\n\nTipos de consulta disponibles:\n" +
                    "• inicio-primer-cuatrimestre\n• inicio-segundo-cuatrimestre\n" +
                    "• fin-primer-cuatrimestre\n• fin-segundo-cuatrimestre\n" +
                    "• mesas-finales\n• inscripcion-carrera\n• feriados\n• vacaciones\n\n" +
                    "📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";
        };
    }

    // Nuevo endpoint para consultas generales
    @GetMapping("/consultar")
    public String consultarEvento(@RequestParam String consulta) throws IOException {
        return calendarioService.consultarFechaEspecifica(consulta);
    }

    // Endpoints específicos para meses de exámenes
    @GetMapping("/examenes-enero")
    public String getExamenesEnero() throws IOException {
        return examenesService.extraerExamenesPorMes("enero");
    }

    @GetMapping("/examenes-febrero")
    public String getExamenesFebrero() throws IOException {
        return examenesService.extraerExamenesPorMes("febrero");
    }

    @GetMapping("/examenes-marzo")
    public String getExamenesMarzo() throws IOException {
        return examenesService.extraerExamenesPorMes("marzo");
    }

    @GetMapping("/examenes-abril")
    public String getExamenesAbril() throws IOException {
        return examenesService.extraerExamenesPorMes("abril");
    }

    @GetMapping("/examenes-mayo")
    public String getExamenesMayo() throws IOException {
        return examenesService.extraerExamenesPorMes("mayo");
    }

    @GetMapping("/examenes-junio")
    public String getExamenesJunio() throws IOException {
        return examenesService.extraerExamenesPorMes("junio");
    }

    @GetMapping("/examenes-julio")
    public String getExamenesJulio() throws IOException {
        return examenesService.extraerExamenesPorMes("julio");
    }

    @GetMapping("/examenes-agosto")
    public String getExamenesAgosto() throws IOException {
        return examenesService.extraerExamenesPorMes("agosto");
    }

    @GetMapping("/examenes-septiembre")
    public String getExamenesSeptiembre() throws IOException {
        return examenesService.extraerExamenesPorMes("septiembre");
    }

    @GetMapping("/examenes-octubre")
    public String getExamenesOctubre() throws IOException {
        return examenesService.extraerExamenesPorMes("octubre");
    }

    @GetMapping("/examenes-noviembre")
    public String getExamenesNoviembre() throws IOException {
        return examenesService.extraerExamenesPorMes("noviembre");
    }

    @GetMapping("/examenes-diciembre")
    public String getExamenesDiciembre() throws IOException {
        return examenesService.extraerExamenesPorMes("diciembre");
    }

    @GetMapping("/calendario-academico")
    public String getCalendarioAcademico() throws IOException {
        return calendarioService.obtenerInformacionCalendario();
    }
}
