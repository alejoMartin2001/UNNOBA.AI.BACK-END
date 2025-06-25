package unnoba.ai.back.demo.controller;

import org.springframework.web.bind.annotation.*;
import unnoba.ai.back.demo.service.CarrerasService;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/unnoba/carreras")
public class CarrerasController {

  private final CarrerasService carrerasService;

  public CarrerasController(CarrerasService carrerasService) {
    this.carrerasService = carrerasService;
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
  public String getIngenieriaInformatica() throws IOException {
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
  public String getIngenieriaMecatronica() throws IOException {
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

  // Información general
  @GetMapping("/regularidad-estudiantes")
  public String getRegularidadEstudiantes() {
    return carrerasService.obtenerRegularidadEstudiantes();
  }
}