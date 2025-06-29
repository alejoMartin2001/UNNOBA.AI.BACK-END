package unnoba.ai.back.demo.constants;

public class UnnobaUrls {

  // Base URLs
  public static final String BASE_ELEGI_URL = "https://elegi.unnoba.edu.ar";
  public static final String BASE_UNNOBA_URL = "https://unnoba.edu.ar";
  public static final String BASE_PLANES_URL = "https://planesdeestudio.unnoba.edu.ar";
  public static final String BASE_SIU_URL = "https://g3w3.unnoba.edu.ar/g3w3/";

  // Calendario y fechas
  public static final String CALENDARIO_URL = BASE_ELEGI_URL + "/calendario/";
  public static final String INSCRIPCION_URL = BASE_ELEGI_URL + "/inscripcion/";

  // URL de inscripción a materias
  public static final String INSCRIPCION_REGULARES_1C = CALENDARIO_URL
      + "inscripciones-asignaturas-de-1o-a-5o-ano-para-asignaturas-en-estado-regular-3/";
  public static final String INSCRIPCION_PENDIENTES_1C = CALENDARIO_URL
      + "inscripciones-asignaturas-de-1o-a-5o-ano-para-asignaturas-en-estado-pendientes-condicionales-2/";
  public static final String INSCRIPCION_REGULARES_2C = CALENDARIO_URL
      + "inscripciones-asignaturas-de-1o-a-5o-ano-para-asignaturas-en-estado-regular-4/";
  public static final String INSCRIPCION_PENDIENTES_2C = CALENDARIO_URL
      + "inscripciones-asignaturas-de-1o-a-5o-ano-para-asignaturas-en-estado-pendiente-condicionales-2/";

  // Distribución de aulas
  public static final String DISTRIBUCION_AULAS_JUNIN = BASE_UNNOBA_URL + "/distribucion-aulas/junin";
  public static final String DISTRIBUCION_AULAS_PERGAMINO = BASE_UNNOBA_URL + "/distribucion-aulas/pergamino";

  // Carreras - Informática
  public static final String INGENIERIA_INFORMATICA = BASE_ELEGI_URL + "/ingenieria-en-informatica/";
  public static final String LICENCIATURA_SISTEMAS = BASE_ELEGI_URL + "/licenciatura-en-sistemas/";
  public static final String ANALISTA_SISTEMAS = BASE_ELEGI_URL + "/analista-de-sistemas/";
  public static final String TECNICATURA_APPS = BASE_ELEGI_URL
      + "/tecnicatura-en-diseno-y-desarrollo-de-aplicaciones-multiplataforma/";

  // Carreras - Diseño
  public static final String DISEÑO_GRAFICO = BASE_ELEGI_URL + "/licenciatura-en-diseno-grafico/";
  public static final String DISEÑO_INDUMENTARIA = BASE_ELEGI_URL + "/licenciatura-en-diseno-de-indumentaria-y-textil/";
  public static final String DISEÑO_INDUSTRIAL = BASE_ELEGI_URL + "/licenciatura-en-diseno-industrial/";

  // Carreras - Salud
  public static final String MEDICINA = BASE_ELEGI_URL + "/medicina/";
  public static final String ENFERMERIA = BASE_ELEGI_URL + "/enfermeria/";

  // Carreras - Derecho y Económicas
  public static final String ABOGACIA = BASE_ELEGI_URL + "/abogacia/";
  public static final String CONTADOR_PUBLICO = BASE_ELEGI_URL + "/contador-publico/";
  public static final String LICENCIATURA_ADMINISTRACION = BASE_ELEGI_URL + "/licenciatura-en-administracion/";

  // Carreras - Ingeniería
  public static final String AGRONOMIA = BASE_ELEGI_URL + "/agronomia/";
  public static final String INGENIERIA_MECATRONICA = BASE_ELEGI_URL + "/ingenieria-mecatronica/";
  public static final String INGENIERIA_INDUSTRIAL = BASE_ELEGI_URL + "/ingenieria-industrial/";
  public static final String INGENIERIA_MECANICA = BASE_ELEGI_URL + "/ingenieria-mecanica/";
  public static final String INGENIERIA_ALIMENTOS = BASE_ELEGI_URL + "/ingenieria-en-alimentos/";

  // Carreras - Ciencias
  public static final String LICENCIATURA_GENETICA = BASE_ELEGI_URL + "/licenciatura-en-genetica/";

  // Tecnicaturas
  public static final String TECNICATURA_MANTENIMIENTO = BASE_ELEGI_URL + "/tecnicatura-en-mantenimiento-industrial/";
  public static final String TECNICATURA_PYMES = BASE_ELEGI_URL + "/tecnicatura-en-gestion-de-pymes/";
  public static final String TECNICATURA_PUBLICA = BASE_ELEGI_URL + "/tecnicatura-en-gestion-publica/";
  public static final String TECNICATURA_ALIMENTOS = BASE_ELEGI_URL
      + "/tecnicatura-universitaria-en-produccion-de-alimentos/";

  private UnnobaUrls() {
    // Private constructor to prevent instantiation
  }
}