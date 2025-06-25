package unnoba.ai.back.demo.controller;

import org.springframework.web.bind.annotation.*;
import unnoba.ai.back.demo.service.DistribucionAulasService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/unnoba/aulas")
public class DistribucionAulasController {

  private final DistribucionAulasService distribucionAulasService;

  public DistribucionAulasController(DistribucionAulasService distribucionAulasService) {
    this.distribucionAulasService = distribucionAulasService;
  }

  // Endpoint para distribución de aulas específica
  @GetMapping("/distribucion-aulas")
  public String getDistribucionAulas(@RequestParam(required = false) String consulta) {
    return distribucionAulasService.obtenerDistribucionAulas(consulta);
  }

  @GetMapping("/distribucion-general")
  public String getDistribucionGeneral() {
    return distribucionAulasService.obtenerDistribucionGeneral();
  }
}