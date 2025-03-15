package ru.sos.button_project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sos.button_project.repositories.SosButtonRepository;

@Service
@RequiredArgsConstructor
public final class SosButtonService {

    private final SosButtonRepository sosButtonRepository;


}
