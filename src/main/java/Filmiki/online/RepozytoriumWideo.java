package Filmiki.online;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepozytoriumWideo extends JpaRepository<Wideo, Long> {
}