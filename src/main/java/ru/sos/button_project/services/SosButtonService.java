package ru.sos.button_project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sos.button_project.dto.FormRequest;
import ru.sos.button_project.models.SosButton;
import ru.sos.button_project.repositories.SosButtonRepository;

@Service
@RequiredArgsConstructor
public final class SosButtonService {

    private final SosButtonRepository sosButtonRepository;

    private static final long ACTIVATION_TIMEOUT = 60; // seconds
    private static final long ACTIVATION_COOLDOWN = 24; // hours

    /**
     * подготовка кнопки при создании, при создании кнопка готова к активации
     * ready true, и нет последней активации
     * @param request заполненная форма с телеграма
     * @param userId id пользователя
     * @return сохраненный в бд инстанс кнопки
     */
    public SosButton prepareButton(FormRequest request, String userId) {
        SosButton sosButton = new SosButton();
        sosButton.setUserId(userId);
        sosButton.setTelegramContact(request.telegramContact());
        sosButton.setActivationCode(request.activationCode());
        sosButton.setMeetingAddress(request.meetingAddress());
        sosButton.setMeetingTime(request.meetingTime());
        sosButton.setComment(request.comment());
        sosButton.setReady(true);
        sosButton.setLastActivatedAt(null);
        return sosButtonRepository.save(sosButton);
    }


}
