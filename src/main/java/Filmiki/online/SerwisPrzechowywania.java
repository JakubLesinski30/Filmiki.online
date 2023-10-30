package Filmiki.online;

import org.springframework.web.multipart.MultipartFile;

public interface SerwisPrzechowywania {
    String zapisz(MultipartFile plik,  String nowaNazwa);
}
