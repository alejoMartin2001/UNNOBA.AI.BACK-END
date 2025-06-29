package unnoba.ai.back.demo.service;

import org.springframework.stereotype.Service;
import unnoba.ai.back.demo.constants.UnnobaUrls;
import unnoba.ai.back.demo.utils.ScrapingUtils;

import java.io.IOException;
import java.util.Set;

@Service
public class CarrerasService {

  private final ScrapingService scrapingService;

  public CarrerasService(ScrapingService scrapingService) {
    this.scrapingService = scrapingService;
  }

  // Carreras de Informática
  public String obtenerIngenieriaInformatica() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.INGENIERIA_INFORMATICA);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerLicenciaturaSistemas() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.LICENCIATURA_SISTEMAS);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerAnalistaSistemas() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.ANALISTA_SISTEMAS);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerTecnicaturaApps() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.TECNICATURA_APPS);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  // Carreras de Diseño
  public String obtenerDiseñoGrafico() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.DISEÑO_GRAFICO);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerDiseñoIndumentaria() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.DISEÑO_INDUMENTARIA);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerDiseñoIndustrial() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.DISEÑO_INDUSTRIAL);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  // Carreras de Salud
  public String obtenerMedicina() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.MEDICINA);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerEnfermeria() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.ENFERMERIA);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  // Carreras de Derecho y Económicas
  public String obtenerAbogacia() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.ABOGACIA);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerContadorPublico() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.CONTADOR_PUBLICO);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerLicenciaturaAdministracion() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.LICENCIATURA_ADMINISTRACION);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  // Carreras de Ingeniería
  public String obtenerAgronomia() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.AGRONOMIA);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerIngenieriaMecatronica() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.INGENIERIA_MECATRONICA);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerIngenieriaIndustrial() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.INGENIERIA_INDUSTRIAL);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerIngenieriaMecanica() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.INGENIERIA_MECANICA);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerIngenieriaAlimentos() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.INGENIERIA_ALIMENTOS);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  // Carreras de Ciencias
  public String obtenerLicenciaturaGenetica() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.LICENCIATURA_GENETICA);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  // Tecnicaturas
  public String obtenerTecnicaturaMantenimiento() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.TECNICATURA_MANTENIMIENTO);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerTecnicaturaPymes() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.TECNICATURA_PYMES);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerTecnicaturaPublica() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.TECNICATURA_PUBLICA);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  public String obtenerTecnicaturaAlimentos() throws IOException {
    Set<String> enlaces = scrapingService.procesarCarrera(UnnobaUrls.TECNICATURA_ALIMENTOS);
    return ScrapingUtils.extraerTextoDeUrls(enlaces);
  }

  // Información general
  public String obtenerRegularidadEstudiantes() {
    return """
        ✅ **REGULARIDAD DE ESTUDIANTES - UNNOBA**

        **¿Necesito ser alumno regular para inscribirme a materias?**
        Sí, para poder inscribirte a materias es necesario tener la condición de alumno regular.

        **¿Cuándo y cómo se verifica la regularidad?**
        La regularidad se verifica a fines de marzo de cada año. Por ejemplo, la regularidad 2025 se evalúa considerando el período comprendido entre marzo de 2024 y marzo de 2025.
        Para mantener la regularidad, es necesario sumar al menos **4 puntos** durante ese período.

        **¿Cómo se suman puntos para mantener la regularidad?**
        Podés sumar puntos de las siguientes maneras:
        • Cada materia cursada y aprobada suma **1 punto**.
        • Cada examen final aprobado suma **2 puntos**.
        • Se pueden combinar cursadas y finales, siempre que se llegue a 4 puntos como mínimo.

        *Ejemplos:*
        - Cursar y aprobar 4 materias = 4 puntos.
        - Cursar 2 materias (2 puntos) y aprobar 1 final (2 puntos) = 4 puntos.
        - Aprobar 2 exámenes finales = 4 puntos.

        **¿Qué pasa si no llego a los 4 puntos?**
        Si no alcanzás los 4 puntos requeridos en el período correspondiente, perdés la condición de alumno regular.

        **¿Puedo seguir cursando si pierdo la regularidad?**
        Sí, podés seguir cursando. Para eso debés **reinscribirte a la carrera**.
        En ese caso, mantenés todas las materias cursadas y aprobadas anteriormente.

        **¿Cuántas veces puedo reinscribirme?**
        Podés reinscribirte hasta **3 veces**.
        Si superás ese límite, perdés todas las materias aprobadas y tenés que inscribirte nuevamente desde cero en la carrera.
        """;
  }

  public String obtenerInformacionGeneral() {
    return "Soy un asistente virtual de la UNNOBA, actualmente en desarrollo. Mi propósito es ayudarte a resolver dudas sobre la universidad, proporcionando información sobre carreras, trámites, fechas importantes y otros temas de interés. ¡No dudes en preguntar! 😊";
  }
}