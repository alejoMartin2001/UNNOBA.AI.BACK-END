package unnoba.ai.back.demo.model;

import java.time.LocalDate;

public class EventoCalendario {
  private String titulo;
  private String descripcion;
  private LocalDate fechaInicio;
  private LocalDate fechaFin;
  private String categoria;
  private String url;

  public EventoCalendario() {
  }

  public EventoCalendario(String titulo, String descripcion, LocalDate fechaInicio, String categoria) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.fechaInicio = fechaInicio;
    this.categoria = categoria;
  }

  // Getters and setters
  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public LocalDate getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(LocalDate fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public LocalDate getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(LocalDate fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}