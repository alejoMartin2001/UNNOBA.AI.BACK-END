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
        // Respuesta predefinida con enlaces bien formateados
        String respuesta = """
                📚 **INSCRIPCIÓN A MATERIAS - UNNOBA**

                Para inscribirte a las materias en la UNNOBA, debés acceder al sistema SIU-Guaraní durante el período de inscripción establecido en el calendario académico oficial.

                **Sistema de Inscripción:** https://g3w3.unnoba.edu.ar/g3w3/

                📅 **Fechas de inscripción:** https://elegi.unnoba.edu.ar/calendario/

                **Requisitos importantes:**
                ✅ Tener condición de alumno regular
                ✅ Respetar las fechas establecidas en el calendario
                ✅ Verificar correlatividades de las materias
                ✅ Contar con tu usuario y contraseña institucional

                **💡 Sistema de Regularidad:**
                La regularidad se verifica a fines de marzo de cada año. Para mantener la condición de alumno regular necesitás sumar al menos **4 puntos**:

                • **1 punto** = Cada materia cursada y aprobada
                • **2 puntos** = Cada examen final aprobado

                **Ejemplos:**
                • 4 materias cursadas y aprobadas = 4 puntos ✅
                • 2 materias cursadas + 1 final aprobado = 4 puntos ✅
                • 2 finales aprobados = 4 puntos ✅

                **📅 Fechas importantes del calendario:**
                """;

        // Incluir información específica extraída del calendario
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        String informacionCalendario = buscador.extraerInformacionCalendario();

        // Filtrar información relevante a inscripciones
        StringBuilder inscripciones = new StringBuilder();
        String[] lineas = informacionCalendario.split("\n");
        boolean enSeccionInscripciones = false;
        boolean enSeccionExamenes = false;

        for (String linea : lineas) {
            if (linea.contains("📝 **INSCRIPCIONES")) {
                enSeccionInscripciones = true;
                inscripciones.append(linea).append("\n");
            } else if (linea.contains("📋 **EXÁMENES")) {
                enSeccionInscripciones = false;
                enSeccionExamenes = true;
                inscripciones.append(linea).append("\n");
            } else if ((enSeccionInscripciones || enSeccionExamenes) && linea.startsWith("•")) {
                inscripciones.append(linea).append("\n");
            } else if ((enSeccionInscripciones || enSeccionExamenes) && linea.contains("**")
                    && !linea.contains("INSCRIPCIONES") && !linea.contains("EXÁMENES")) {
                break;
            }
        }

        if (inscripciones.length() == 0) {
            inscripciones.append("**Próximas fechas:**\n");
            inscripciones
                    .append("• Consultá el calendario académico para ver las fechas de inscripción actualizadas\n");
            inscripciones.append("• Las inscripciones abren según el cronograma del calendario oficial\n");
        }

        return respuesta + "\n" + inscripciones.toString();
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
                """;

        EnlacesInscripciones buscador = new EnlacesInscripciones();
        String fechas = buscador.extraerFechasEspecificas("vacaciones");

        return respuesta + "\n" + fechas + "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";
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
                return "🎉 **FERIADOS Y DÍAS NO LABORABLES**\n\n" +
                        buscador.extraerFechasEspecificas("feriados") +
                        "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";

            case "vacaciones":
                return "❄️ **VACACIONES DE INVIERNO**\n\n" +
                        buscador.extraerFechasEspecificas("vacaciones") +
                        "\n📅 **Calendario completo:** https://elegi.unnoba.edu.ar/calendario/";

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

    @GetMapping("/feriados")
    public String getFeriados() throws IOException {
        // Respuesta predefinida con enlace bien formateado
        String respuesta = """
                🎉 **FERIADOS Y DÍAS NO LABORABLES - UNNOBA**

                Para conocer los feriados de la UNNOBA en el año actual, consultá el calendario académico oficial.

                📅 https://elegi.unnoba.edu.ar/calendario/

                **Información actualizada del calendario:**
                """;

        // Incluir información específica extraída del calendario
        EnlacesInscripciones buscador = new EnlacesInscripciones();
        String informacionCalendario = buscador.extraerInformacionCalendario();

        // Filtrar solo información relevante a feriados
        StringBuilder feriados = new StringBuilder();
        String[] lineas = informacionCalendario.split("\n");
        boolean enSeccionFeriados = false;

        for (String linea : lineas) {
            if (linea.contains("🎉 **FERIADOS")) {
                enSeccionFeriados = true;
                feriados.append(linea).append("\n");
            } else if (enSeccionFeriados && linea.startsWith("•")) {
                feriados.append(linea).append("\n");
            } else if (enSeccionFeriados && linea.trim().isEmpty()) {
                break;
            } else if (enSeccionFeriados && linea.contains("**")) {
                break;
            }
        }

        if (feriados.length() == 0) {
            feriados.append("**Feriados próximos:**\n");
            feriados.append("• Consultá el calendario académico para ver las fechas actualizadas\n");
            feriados.append("• Los feriados incluyen días nacionales y días sin actividad académica\n");
        }

        return respuesta + "\n" + feriados.toString();
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
