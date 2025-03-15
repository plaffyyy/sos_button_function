package ru.sos.button_project.dto;

import java.time.Instant;

public record FormRequest(
        String telegramContact,
        String activationCode,
        String meetingAddress,
        Instant meetingTime,
        String comment
) {
}
