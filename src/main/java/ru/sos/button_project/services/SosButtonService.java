package ru.sos.button_project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.sos.button_project.dto.FormRequest;
import ru.sos.button_project.exceptions.ButtonNotPreparedException;
import ru.sos.button_project.exceptions.CannotActivateButtonException;
import ru.sos.button_project.models.SosButton;
import ru.sos.button_project.repositories.SosButtonRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public final class SosButtonService {

    private final SosButtonRepository sosButtonRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

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

    /**
     * метод активации кнопки, по id пользователя
     * @param userId id пользователя
     * @param immediate срочность активации, если не срочно то отправляется через 60 секунд
     * @throws ButtonNotPreparedException если не найдена для данного пользователя, то есть не готова
     * @throws CannotActivateButtonException если кнопка активировалась менее чем 24 часа назад
     */
    public void activateButton(String userId, boolean immediate) {
        SosButton sosButton = sosButtonRepository.findByUserIdAndReady(userId)
                .orElseThrow(() -> new ButtonNotPreparedException("Кнопка не готова к использованию"));

        if (sosButton.getLastActivatedAt() != null &&
                Duration.between(sosButton.getLastActivatedAt(), Instant.now()).toHours() < ACTIVATION_COOLDOWN) {
            throw new CannotActivateButtonException("Кнопка активировалась меньше чем 24 часа назад");
        }

        sosButton.setReady(false);
        sosButton.setLastActivatedAt(Instant.now());
        sosButtonRepository.save(sosButton);

        if (immediate) {
            alert(sosButton);
        } else {
            scheduler.schedule(() -> alert(sosButton), ACTIVATION_TIMEOUT, TimeUnit.SECONDS);
        }
    }

    private void alert(SosButton sosButton) {
        // TODO: реализовать функцию отправления уведомления в телеграмм вместе с сервисным слоем для телеграмма
    }
}
