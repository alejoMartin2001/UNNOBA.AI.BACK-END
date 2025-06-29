package unnoba.ai.back.demo.controller;

import org.springframework.web.bind.annotation.*;
import unnoba.ai.back.demo.service.ExamenesService;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/unnoba/examenes")
public class ExamenesController {

  private final ExamenesService examenesService;

  public ExamenesController(ExamenesService examenesService) {
    this.examenesService = examenesService;
  }

  @GetMapping("/examenes-finales")
  public String getExamenesFinales() {
    return examenesService.obtenerInformacionExamenesFinales();
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
}