package unnoba.ai.back.demo.dto;

public class FechasInscripcionDto {

  private String titulo;
  private String rangoFechas;
  private String notas;

  public FechasInscripcionDto(String titulo, String rangoFechas, String notas) {
    this.titulo = titulo;
    this.rangoFechas = rangoFechas;
    this.notas = notas;
  }

  // Getters and Setters
  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getRangoFechas() {
    return rangoFechas;
  }

  public void setRangoFechas(String rangoFechas) {
    this.rangoFechas = rangoFechas;
  }

  public String getNotas() {
    return notas;
  }

  public void setNotas(String notas) {
    this.notas = notas;
  }
}