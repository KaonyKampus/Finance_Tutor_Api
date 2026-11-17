package com.kaonykk.financetutorapi.dto;

import java.math.BigDecimal;

public class MovementRequestDTO {
    private String type;
    private BigDecimal amount;
    private String description;

    public MovementRequestDTO() { }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}