package unnoba.ai.back.demo.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import unnoba.ai.back.demo.carreras.EnlacesInformatica;
import unnoba.ai.back.demo.carreras.EnlacesDiseño;
import unnoba.ai.back.demo.carreras.EnlacesGenerales;
import unnoba.ai.back.demo.carreras.EnlacesInscripciones;
import unnoba.ai.back.demo.carreras.EnlacesDistribucionAulas;

import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/unnoba")
public class UnnobaScraperController {

    @GetMapping("/diseño-grafico")
    public String getDiseñoGrafico() throws IOException {
        EnlacesDiseño busquedaEnlaces = new EnlacesDiseño();
        Set<String> listaEnlaces = busquedaEnlaces.buscarLicenciaturaDiseñoGrafico();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/diseño-indumentaria-y-textil")
    public String getDiseñoIndumentariaYTextil() throws IOException {
        EnlacesDiseño busquedaEnlaces = new EnlacesDiseño();
        Set<String> listaEnlaces = busquedaEnlaces.buscarLICENCIATURADISEÑOINDUMENTARIAYTEXTIL();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/diseño-industrial")
    public String getDiseñoIndustrial() throws IOException {
        EnlacesDiseño busquedaEnlaces = new EnlacesDiseño();
        Set<String> listaEnlaces = busquedaEnlaces.buscarLicenciaturaDiseñoIndustrial();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/ingenieria-informatica")
    public String getInformatica() throws IOException {
        EnlacesInformatica b = new EnlacesInformatica();
        Set<String> listaEnlaces = b.ingenieriaInformatica();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/licenciatura-sistemas")
    public String getLicenciaturaSistemas() throws IOException {
        EnlacesInformatica b = new EnlacesInformatica();
        Set<String> listaEnlaces = b.licenciaturaSistemas();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/analista-sistemas")
    public String getAnalistaSistemas() throws IOException {
        EnlacesInformatica b = new EnlacesInformatica();
        Set<String> listaEnlaces = b.analistaSistemas();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/medicina")
    public String getMedicina() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.medicina();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/enfermeria")
    public String getEnfermeria() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.enfermeria();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/abogacia")
    public String getAbogacia() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.abogacia();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/contador-publico")
    public String getContadorPublico() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.contadorPublico();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/agronomia")
    public String getAgronomia() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.agronomia();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/ingenieria-mecatronica")
    public String getMecatronica() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.ingenieriaMecatronica();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/genetica")
    public String getGenetica() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.licenciaturaGenetica();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/inscripcion-materias")
    public String getInscripcionMaterias() throws IOException {
        // Obtener fechas dinámicamente del calendario académico
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerFechasInscripcionMaterias();
    }

    @GetMapping("/inscripcion-materias-primer-cuatrimestre")
    public String getInscripcionMateriasPrimerCuatrimestre() throws IOException {
        // Obtener fechas dinámicamente del calendario académico
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerFechasInscripcionPrimerCuatrimestre();
    }

    @GetMapping("/inscripcion-materias-segundo-cuatrimestre")
    public String getInscripcionMateriasSegundoCuatrimestre() throws IOException {
        // Obtener fechas dinámicamente del calendario académico
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerFechasInscripcionSegundoCuatrimestre();
    }

    @GetMapping("/inscripcion-materias-detallada")
    public String getInscripcionMateriasDetallada() throws IOException {
        // Obtener información detallada dinámicamente del calendario académico
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerFechasInscripcionMateriasDetallada();
    }

    // Endpoints específicos para preguntas frecuentes del calendario

    @GetMapping("/inicio-cuatrimestres")
    public String getInicioCuatrimestres() throws IOException {
        String respuesta = """
                🎓 **INICIO DE CUATRIMESTRES - UNNOBA**
                """;

        EnlacesInscripciones buscador = new EnlacesInscripciones();
        String fechas = buscador.extraerFechasEspecificas("inicio_cuatrimestre");

        return respuesta + "\n" + fechas + "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";
    }

    @GetMapping("/fin-cuatrimestres")
    public String getFinCuatrimestres() throws IOException {
        String respuesta = """
                📚 **FIN DE CUATRIMESTRES - UNNOBA**
                """;

        EnlacesInscripciones buscador = new EnlacesInscripciones();
        String fechas = buscador.extraerFechasEspecificas("fin_cuatrimestre");

        return respuesta + "\n" + fechas + "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";
    }

    @GetMapping("/examenes-finales")
    public String getExamenesFinales() throws IOException {
        String respuesta = """
                📝 **EXÁMENES FINALES - UNNOBA**
                """;

        EnlacesInscripciones buscador = new EnlacesInscripciones();
        String fechas = buscador.extraerFechasEspecificas("examenes");

        return respuesta + "\n" + fechas + "\n🔗 **Inscripción a finales:** https://g3w3.unnoba.edu.ar/g3w3/";
    }

    @GetMapping("/vacaciones-invierno")
    public String getVacacionesInvierno() throws IOException {
        String respuesta = """
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

        return respuesta;
    }

    @GetMapping("/confirmacion-inscripcion")
    public String getConfirmacionInscripcion() throws IOException {
        String respuesta = """
                ✅ **CONFIRMACIÓN DE INSCRIPCIÓN - UNNOBA**
                """;

        EnlacesInscripciones buscador = new EnlacesInscripciones();
        String fechas = buscador.extraerFechasEspecificas("confirmacion");

        return respuesta + "\n" + fechas + "\n🔗 **Sistema SIU-Guaraní:** https://g3w3.unnoba.edu.ar/g3w3/";
    }

    // Endpoint específico para consultas de exámenes por mes
    @GetMapping("/examenes-mes/{mes}")
    public String getExamenesPorMes(@PathVariable String mes) throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerExamenesPorMes(mes);
    }

    // Nuevo endpoint para consultas específicas de fechas
    @GetMapping("/fechas/{tipo}")
    public String getFechasEspecificas(@PathVariable String tipo) throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();

        switch (tipo.toLowerCase()) {
            case "inicio-primer-cuatrimestre":
                return "🎓 **INICIO PRIMER CUATRIMESTRE**\n\n" +
                        buscador.extraerFechasEspecificas("inicio_primer_cuatrimestre") +
                        "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";

            case "inicio-segundo-cuatrimestre":
                return "🎓 **INICIO SEGUNDO CUATRIMESTRE**\n\n" +
                        buscador.extraerFechasEspecificas("inicio_segundo_cuatrimestre") +
                        "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";

            case "fin-primer-cuatrimestre":
                return "📚 **FIN PRIMER CUATRIMESTRE**\n\n" +
                        buscador.extraerFechasEspecificas("fin_primer_cuatrimestre") +
                        "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";

            case "fin-segundo-cuatrimestre":
                return "📚 **FIN SEGUNDO CUATRIMESTRE**\n\n" +
                        buscador.extraerFechasEspecificas("fin_segundo_cuatrimestre") +
                        "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";

            case "mesas-finales":
                return "📝 **MESAS DE EXÁMENES FINALES**\n\n" +
                        buscador.extraerFechasEspecificas("mesas_finales") +
                        "\n🔗 **Inscripción:** https://g3w3.unnoba.edu.ar/g3w3/";

            case "inscripcion-carrera":
                return "✅ **INSCRIPCIÓN A LA CARRERA**\n\n" +
                        buscador.extraerFechasEspecificas("inscripcion_carrera") +
                        "\n🔗 **Sistema SIU-Guaraní:** https://g3w3.unnoba.edu.ar/g3w3/";

            case "feriados":
                return "🎉 **FERIADOS Y DÍAS NO LABORABLES - UNNOBA**\n\n" +
                        "Para obtener la información exacta sobre los feriados en la UNNOBA, te recomiendo consultar el calendario académico oficial de la universidad. La información allí es siempre la más precisa y actualizada.\n\n"
                        +
                        "Por desgracia, no tengo acceso en tiempo real a esa información específica.\n\n" +
                        "📅 **Calendario Académico Oficial:** https://elegi.unnoba.edu.ar/calendario/\n\n" +
                        "**💡 En el calendario académico encontrarás:**\n" +
                        "• Feriados nacionales\n" +
                        "• Días no laborables universitarios\n" +
                        "• Recesos académicos\n" +
                        "• Fechas especiales de la universidad";

            case "vacaciones":
                return "❄️ **VACACIONES DE INVIERNO - UNNOBA**\n\n" +
                        "Para obtener las fechas exactas del receso invernal en la UNNOBA, te recomiendo consultar el calendario académico oficial de la universidad. La información allí es siempre la más precisa y actualizada.\n\n"
                        +
                        "Por desgracia, no tengo acceso en tiempo real a esa información específica.\n\n" +
                        "📅 **Calendario Académico Oficial:** https://elegi.unnoba.edu.ar/calendario/\n\n" +
                        "**💡 En el calendario académico encontrarás:**\n" +
                        "• Fechas exactas del receso invernal\n" +
                        "• Duración de las vacaciones\n" +
                        "• Reinicio de actividades académicas\n" +
                        "• Otras fechas importantes del año lectivo";

            default:
                return "❓ **CONSULTA NO RECONOCIDA**\n\nTipos de consulta disponibles:\n" +
                        "• inicio-primer-cuatrimestre\n• inicio-segundo-cuatrimestre\n" +
                        "• fin-primer-cuatrimestre\n• fin-segundo-cuatrimestre\n" +
                        "• mesas-finales\n• inscripcion-carrera\n• feriados\n• vacaciones\n\n" +
                        "📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";
        }
    }

    // Nuevo endpoint para consultas generales
    @GetMapping("/consultar")
    public String consultarEvento(@RequestParam String consulta) throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.consultarFechaEspecifica(consulta);
    }

    // Endpoints específicos para meses de exámenes (incluye meses sin mesas)
    @GetMapping("/examenes-enero")
    public String getExamenesEnero() throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerExamenesPorMes("enero");
    }

    @GetMapping("/examenes-febrero")
    public String getExamenesFebrero() throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerExamenesPorMes("febrero");
    }

    @GetMapping("/examenes-marzo")
    public String getExamenesMarzo() throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerExamenesPorMes("marzo");
    }

    @GetMapping("/examenes-abril")
    public String getExamenesAbril() throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerExamenesPorMes("abril");
    }

    @GetMapping("/examenes-mayo")
    public String getExamenesMayo() throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerExamenesPorMes("mayo");
    }

    @GetMapping("/examenes-junio")
    public String getExamenesJunio() throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerExamenesPorMes("junio");
    }

    @GetMapping("/examenes-julio")
    public String getExamenesJulio() throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerExamenesPorMes("julio");
    }

    @GetMapping("/examenes-agosto")
    public String getExamenesAgosto() throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerExamenesPorMes("agosto");
    }

    @GetMapping("/examenes-septiembre")
    public String getExamenesSeptiembre() throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerExamenesPorMes("septiembre");
    }

    @GetMapping("/examenes-octubre")
    public String getExamenesOctubre() throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerExamenesPorMes("octubre");
    }

    @GetMapping("/examenes-noviembre")
    public String getExamenesNoviembre() throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerExamenesPorMes("noviembre");
    }

    @GetMapping("/examenes-diciembre")
    public String getExamenesDiciembre() throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerExamenesPorMes("diciembre");
    }

    @GetMapping("/calendario-academico")
    public String getCalendarioAcademico() throws IOException {
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        return buscador.extraerInformacionCalendario();
    }

    // Endpoint para distribución de aulas específica
    @GetMapping("/distribucion-aulas")
    public String getDistribucionAulas(@RequestParam(required = false) String consulta) throws IOException {
        EnlacesDistribucionAulas buscador = new EnlacesDistribucionAulas();

        if (consulta != null && !consulta.trim().isEmpty()) {
            return buscador.obtenerDistribucionEspecifica(consulta);
        } else {
            return """
                    🏢 **DISTRIBUCIÓN DE AULAS - UNNOBA**
                    La distribución de aulas puede consultarse a través de los siguientes enlaces:
                    **📚 Junín:**
                    • **Distribución de aulas - Junín:** https://unnoba.edu.ar/distribucion-aulas/junin

                    **📚 Pergamino:**
                    • **Distribución de aulas - Pergamino:** https://unnoba.edu.ar/distribucion-aulas/pergamino""";
        }
    }

    // Nuevos endpoints para todas las carreras faltantes

    @GetMapping("/tecnicatura-diseño-desarrollo-aplicaciones-multiplataforma")
    public String getTecnicaturaDiseñoDesarrolloAplicaciones() throws IOException {
        EnlacesInformatica b = new EnlacesInformatica();
        Set<String> listaEnlaces = b.disYDesarrolloDeAps();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/ingenieria-industrial")
    public String getIngenieriaIndustrial() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.ingenieriaIndustrial();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/ingenieria-mecanica")
    public String getIngenieriaMecanica() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.ingenieriaMecanica();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/tecnicatura-mantenimiento-industrial")
    public String getTecnicaturaMantenimientoIndustrial() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.tecnicaturaMantenimientoIndustrial();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/licenciatura-administracion")
    public String getLicenciaturaAdministracion() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.licenciaturaAdministracion();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/tecnicatura-gestion-pymes")
    public String getTecnicaturaGestionPymes() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.tecnicaturaGestionPymes();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/tecnicatura-gestion-publica")
    public String getTecnicaturaGestionPublica() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.tecnicaturaGestionPublica();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/ingenieria-alimentos")
    public String getIngenieriaAlimentos() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.ingenieriaAlimentos();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/tecnicatura-produccion-alimentos")
    public String getTecnicaturaProduccionAlimentos() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.tecnicaturaProduccionAlimentos();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/regularidad-estudiantes")
    public String getRegularidadEstudiantes() throws IOException {
        String respuesta = """
                💡 **SISTEMA DE REGULARIDAD - UNNOBA**

                **¿Cuándo se verifica la regularidad?**
                La regularidad se verifica a fines de marzo de cada año. Para mantener la condición de alumno regular necesitás sumar al menos **4 puntos**.

                **¿Cómo se suman puntos?**
                • **1 punto** = Cada materia cursada y aprobada
                • **2 puntos** = Cada examen final aprobado

                **Ejemplos para mantener la regularidad:**
                • Cursar y aprobar 4 materias = 4 puntos ✅
                • Cursar 2 materias + aprobar 1 final = 4 puntos ✅
                • Aprobar 2 exámenes finales = 4 puntos ✅

                **¿Qué pasa si no llego a los 4 puntos?**
                Si no alcanzás los 4 puntos requeridos, perdés la condición de alumno regular, pero podés seguir cursando reinscribiéndote a la carrera.

                **¿Cuántas veces puedo reinscribirme?**
                Podés reinscribirte hasta 3 veces. Si superás ese límite, perdés todas las materias aprobadas.

                🔗 **Sistema SIU-Guaraní:** https://g3w3.unnoba.edu.ar/g3w3/
                """;

        return respuesta;
    }

    @GetMapping("/feriados")
    public String getFeriados() throws IOException {
        String respuesta = """
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

        return respuesta;
    }

    public String getTextoUrls(Set<String> listaEnlaces) {
        StringBuilder texto = new StringBuilder();

        for (String url : listaEnlaces) {
            try {
                Document doc = Jsoup.connect(url).get();
                Elements elementos = doc.select("*");
                Set<String> textosUnicos = new LinkedHashSet<>();

                for (Element elemento : elementos) {
                    String contenido = elemento.ownText().trim();
                    if (!contenido.isEmpty()) {
                        textosUnicos.add(contenido);
                    }
                }

                for (String linea : textosUnicos) {
                    texto.append(linea).append(" ");
                }
            } catch (IOException e) {
                System.out.println("Error al acceder a: " + url);
            }
        }

        return texto.toString();
    }
}
