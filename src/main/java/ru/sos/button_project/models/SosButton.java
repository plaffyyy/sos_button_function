package ru.sos.button_project.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "sos_button")
public final class SosButton {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String telegramContact;
    private String activationCode;
    private String meetingAddress;
    private Instant meetingTime;
    private String comment;
    private boolean isReady;
    private Instant lastActivatedAt;


}
