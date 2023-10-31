package Filmiki.online;


import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
@RequestMapping("/wideo")
public class WideoController {

    
    private final SerwisWideo wideoSerwis;

    @Autowired
    public WideoController(SerwisWideo wideoSerwis) {
        this.wideoSerwis = wideoSerwis;
    }

    

    @GetMapping
    public String listaWideo(Model model) {
        List<Wideo> wideo = wideoSerwis.getWszystkieWideo();
        model.addAttribute("wideo", wideo);
        return "index";
    }

    @GetMapping("/{id}")
    public String pokazWideo(@PathVariable String id, Model model) {
        try {
            Long wideoId = Long.parseLong(id);
            Wideo wideo = wideoSerwis.getWideoPoId(wideoId);
            model.addAttribute("wideo", wideo);
            return "wideo";
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Blędne ID wideo");
            return "error";
        }
    }

    @PostMapping
    public String dodajWideo(@RequestParam("plik") MultipartFile plik,
                           @RequestParam("tytul") String tytul,
                           @RequestParam("opis") String opis,
                           @RequestParam("trwanie") String trwanie,
                           RedirectAttributes redirectAttributes) {

                            String contentType = plik.getContentType();
    if (!"video/mp4".equals(contentType) && !"video/x-msvideo".equals(contentType)) {
        redirectAttributes.addFlashAttribute("errorMessage", "Nieprawidłowy format pliku. Dozwolone są tylko pliki .mp4 i .avi.");
        return "redirect:/wideo";
    }

                            String orignalnaNazwa = plik.getOriginalFilename();

                            LocalDateTime data = LocalDateTime.now();
                    
                            DateTimeFormatter formatDaty = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                            String sformatowanaData = data.format(formatDaty);
                    
                            String nowaNazwa = sformatowanaData + "_" + orignalnaNazwa;
                            
        wideoSerwis.dodajWideo(plik, tytul, opis, trwanie, nowaNazwa);
        redirectAttributes.addFlashAttribute("powiadomienie", "Wideo zostało dodane!");
        return "redirect:/wideo";
    }

    @PostMapping("/{id}")
    public String edytujWideo(@PathVariable Long id,
                              @RequestParam("tytul") String tytul,
                              @RequestParam("opis") String opis,
                              @RequestParam("trwanie") String trwanie,
                              RedirectAttributes redirectAttributes) {
        wideoSerwis.edytujWideo(id, tytul, opis, trwanie);
        redirectAttributes.addFlashAttribute("powiadomienie", "Wideo zostało zmienione!");
        return "redirect:/wideo";
    }

    @PostMapping("/{id}/usun")
    public String usunWideo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        wideoSerwis.usunWideo(id);
        redirectAttributes.addFlashAttribute("powiadomienie", "Wideo zostało usunięte!");
        return "redirect:/wideo";
    }

    @GetMapping("/plik/{nazwapliku:.+}")
    public ResponseEntity<Resource> getWideo(@PathVariable String nazwapliku) {
        try {
            Path file = Paths.get("src/main/resources/static/videos").resolve(nazwapliku);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}