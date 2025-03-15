package ru.sos.button_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sos.button_project.models.SosButton;

@Repository
public interface SosButtonRepository extends JpaRepository<SosButton, Long> {
}
