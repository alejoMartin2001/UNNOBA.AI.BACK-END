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
public class UnnobaScraperController {

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

    @GetMapping("/medicina")
    public String getMedicina() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.medicina();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/enfermeria")
    public String getEnfermeria() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.enfermeria();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/abogacia")
    public String getAbogacia() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.abogacia();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/contador-publico")
    public String getContadorPublico() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.contadorPublico();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/agronomia")
    public String getAgronomia() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.agronomia();
        return getTextoUrls(listaEnlaces);
    }

    @GetMapping("/ingenieria-mecatronica")
    public String getMecatronica() throws IOException {
        EnlacesGenerales buscador = new EnlacesGenerales();
        Set<String> listaEnlaces = buscador.ingenieriaMecatronica();
        return getTextoUrls(listaEnlaces);
    }

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
                Elements elementos = doc.select("p,li,ul,h1,h2,h3,h4"); // Solo texto útil
                for (Element elemento : elementos) {
                    texto.append(elemento.text()).append(" ");
                }
            } catch (IOException e) {
                System.out.println("Error al acceder a: " + url);
            }
        }

        return texto.toString();
    }

}
