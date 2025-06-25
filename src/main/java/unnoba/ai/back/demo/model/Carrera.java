package unnoba.ai.back.demo.model;

import java.util.Set;

public class Carrera {
  private String nombre;
  private String url;
  private String area;
  private Set<String> enlacesRelacionados;

  public Carrera() {
  }

  public Carrera(String nombre, String url, String area) {
    this.nombre = nombre;
    this.url = url;
    this.area = area;
  }

  // Getters and setters
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public Set<String> getEnlacesRelacionados() {
    return enlacesRelacionados;
  }

  public void setEnlacesRelacionados(Set<String> enlacesRelacionados) {
    this.enlacesRelacionados = enlacesRelacionados;
  }
}