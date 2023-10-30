package Filmiki.online;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SerwisWideo {
    private final RepozytoriumWideo wideoRepozytorium;
    private final SerwisPrzechowywania przechowywanieSerwis;

    @Autowired
    public SerwisWideo(RepozytoriumWideo wideoRepozytorium, SerwisPrzechowywania przechowywanieSerwis) {
        this.wideoRepozytorium = wideoRepozytorium;
        this.przechowywanieSerwis = przechowywanieSerwis;
    }

    public List<Wideo> getWszystkieWideo() {
        return wideoRepozytorium.findAll();
    }

    public Wideo getWideoPoId(Long id) {
        return wideoRepozytorium.findById(id).orElseThrow(() ->
                new IllegalStateException("Wideo o id: " + id + " nie istnieje"));
    }

    public void dodajWideo(MultipartFile plik, String tytul, String opis, String trwanie, String nowaNazwa) {
        String wideoSciezka = przechowywanieSerwis.zapisz(plik, nowaNazwa);
        Wideo wideo = new Wideo(tytul, opis, trwanie, wideoSciezka);
        wideoRepozytorium.save(wideo);
    }

    public void edytujWideo(Long id, String tytul, String opis, String trwanie) {
        Wideo wideo = getWideoPoId(id);
        wideo.setTytul(tytul);
        wideo.setOpis(opis);
        wideo.setTrwanie(trwanie);
        wideoRepozytorium.save(wideo);
    }

    public void usunWideo(Long id) {
        wideoRepozytorium.deleteById(id);
    }
}