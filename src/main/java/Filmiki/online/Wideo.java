package Filmiki.online;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tytul;
    private String opis;
    private String trwanie;
    private String sciezka;

    public Wideo(String tytul, String opis, String trwanie, String sciezka) {
        this.tytul = tytul;
        this.opis = opis;
        this.trwanie = trwanie;
        this.sciezka = sciezka;
    }
}