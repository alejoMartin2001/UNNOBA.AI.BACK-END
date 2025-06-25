package unnoba.ai.back.demo.dto;

public class FechasExamenDto {
  private String mes;
  private String fechaInicioInscripcion;
  private String fechaInicioExamen;
  private String fechaFinExamen;
  private String urlCalendario;
  private String mensaje;

  public FechasExamenDto() {
  }

  public FechasExamenDto(String mes, String fechaInicioInscripcion, String fechaInicioExamen, String fechaFinExamen) {
    this.mes = mes;
    this.fechaInicioInscripcion = fechaInicioInscripcion;
    this.fechaInicioExamen = fechaInicioExamen;
    this.fechaFinExamen = fechaFinExamen;
  }

  // Getters and setters
  public String getMes() {
    return mes;
  }

  public void setMes(String mes) {
    this.mes = mes;
  }

  public String getFechaInicioInscripcion() {
    return fechaInicioInscripcion;
  }

  public void setFechaInicioInscripcion(String fechaInicioInscripcion) {
    this.fechaInicioInscripcion = fechaInicioInscripcion;
  }

  public String getFechaInicioExamen() {
    return fechaInicioExamen;
  }

  public void setFechaInicioExamen(String fechaInicioExamen) {
    this.fechaInicioExamen = fechaInicioExamen;
  }

  public String getFechaFinExamen() {
    return fechaFinExamen;
  }

  public void setFechaFinExamen(String fechaFinExamen) {
    this.fechaFinExamen = fechaFinExamen;
  }

  public String getUrlCalendario() {
    return urlCalendario;
  }

  public void setUrlCalendario(String urlCalendario) {
    this.urlCalendario = urlCalendario;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }
}