package com.example.newproject.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Embeddable
public class Varification {
    private boolean status = false;
    private LocalDateTime startedAt;
    private LocalDateTime endsAt;
    private String planType;
}
