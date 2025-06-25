package unnoba.ai.back.demo.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import unnoba.ai.back.demo.carreras.EnlacesInformatica;
import unnoba.ai.back.demo.carreras.EnlacesDiseño;
import unnoba.ai.back.demo.carreras.EnlacesGenerales;

import java.io.IOException;
import java.util.*;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/unnoba")
public class CarrerasController {
    /*DISEÑO*/
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
    /*DISEÑO*/
    /*INFORMATICA */
    @GetMapping("/ingenieria-informatica")
    public String getInformatica() throws IOException{
        EnlacesInformatica b = new EnlacesInformatica(); 
        Set<String> listaEnlaces = b.ingenieriaInformatica();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/licenciatura-sistemas")
    public String getLicenciaturaSistemas() throws IOException{
        EnlacesInformatica b = new EnlacesInformatica(); 
        Set<String> listaEnlaces = b.licenciaturaSistemas();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/analista-sistemas")
    public String getAnalistaSistemas() throws IOException{
        EnlacesInformatica b = new EnlacesInformatica(); 
        Set<String> listaEnlaces = b.analistaSistemas();
        return getTextoUrls(listaEnlaces);
    }
     
    @GetMapping("/tecnicatura-diseño-desarrollo-apps")
    public String getTecDesarrolloAppsMult() throws IOException{
        EnlacesInformatica b = new EnlacesInformatica();
        Set<String> enlaces = b.disYDesarrolloDeAps();
        return getTextoUrls(enlaces);
    }
    
     /*SALUD */
    @GetMapping("/licenciatura-enfermeria")
    public String getLicenciaturaEnfermeria() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.licEnfermeria();
        return getTextoUrls(listaEnlaces);
    }
    @GetMapping("/enfermeria")
    public String getEnfermeria() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.enfermeria();
        return getTextoUrls(listaEnlaces);
    }

    /*JURIDICAS */
    @GetMapping("/abogacia")
    public String getAbogacia() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.abogacia();
        return getTextoUrls(listaEnlaces);
    }
    
    /*ECONOMICAS */
    @GetMapping("/contador-publico")
    public String getContadorPublico() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.contadorPublico();
        return getTextoUrls(listaEnlaces);
    }
    @GetMapping("/licenciatura-en-administacion")
    public String getLicAdministracion() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.licAdministracion();
        return getTextoUrls(listaEnlaces);
    }
    @GetMapping("/tecnicatura-gestion-pymes")
    public String getTecGestionPymes() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.tecGestionPymes();
        return getTextoUrls(listaEnlaces);
    }
    @GetMapping("/tecnicatura-gestion-publica")
    public String getTecGestionPublica() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.tecGestionPublica();
        return getTextoUrls(listaEnlaces);
    }
    /*ALIMENTOS */
    @GetMapping("/ingenieria-en-alimentos")
    public String getAlimentos() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.ingEnAlimentos();
        return getTextoUrls(listaEnlaces);
    }

    /*AGRONOMIA */
    @GetMapping("/ingenieria-agronomica")
    public String getAgronomia() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.ingenieriaAgronomica();
        return getTextoUrls(listaEnlaces);
    }
    /*INGENIERIAS */
    @GetMapping("/ingenieria-mecanica")
    public String getIngenieriaMecanica() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.ingenieriaMecanica();
        return getTextoUrls(listaEnlaces);
    }
    @GetMapping("/ingenieria-industrial")
    public String getIngenieriaIndustrial() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.ingenieriaIndustrial();
        return getTextoUrls(listaEnlaces);
    }
    
    @GetMapping("/tecnicatura-mantenimiento-industrial")
    public String getTecnicaturaMantIndustrial() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.tecnicaturaMantIndustrial();
        return getTextoUrls(listaEnlaces);
    }
    /*GENETICA */
    @GetMapping("/genetica")
    public String getGenetica() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.licenciaturaGenetica();
        return getTextoUrls(listaEnlaces);
    }

    public String getTextoUrls(Set<String> listaEnlaces){
        StringBuilder texto = new StringBuilder();

        for (String url : listaEnlaces) {
            try {
                Document doc = Jsoup.connect(url).get();
                Elements elementos = doc.select("*"); // Solo texto útil
                Set<String> textosUnicos = new LinkedHashSet<>();

                for (Element elemento : elementos) {
                    String contenido = elemento.ownText().trim(); // solo el texto propio, sin hijos
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
