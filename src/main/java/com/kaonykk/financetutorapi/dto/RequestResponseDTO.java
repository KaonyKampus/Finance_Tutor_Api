package com.kaonykk.financetutorapi.dto;

import com.kaonykk.financetutorapi.model.entity.AuthorizationRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RequestResponseDTO {
    private final Long id;
    private final String requestedBy;
    private final BigDecimal amount;
    private final String status;
    private final LocalDateTime createdAt;

    public RequestResponseDTO(AuthorizationRequest r) {
        this.id = r.getId();
        this.requestedBy = r.getRequestedBy().getName();
        this.amount = r.getAmount();
        this.status = r.getStatus().name();
        this.createdAt = r.getCreatedAt();
    }

    public Long getId() { return id; }
    public String getRequestedBy() { return requestedBy; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}