package Filmiki.online;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class SerwisPrzechowywaniaSystemuPlikow implements SerwisPrzechowywania {
    private final Path sciezkaZapisu;

    @Autowired
    public SerwisPrzechowywaniaSystemuPlikow() {
        this.sciezkaZapisu = Paths.get("src/main/resources/static/videos");
    }

    @Override
public String zapisz(MultipartFile plik, String nowaNazwa) {
    if (plik.isEmpty()) {
        throw new WyjatekPrzechowania("Nie udało się zapisać pustego pliku");
    }
    try {
        Files.copy(plik.getInputStream(), this.sciezkaZapisu.resolve(nowaNazwa));
        return nowaNazwa;
    } catch (IOException e) {
        throw new WyjatekPrzechowania("Nie udało się zapisać pliku " + nowaNazwa, e);
    }
}

public String zapisz(MultipartFile plik) {
    return zapisz(plik, plik.getOriginalFilename());
}
}