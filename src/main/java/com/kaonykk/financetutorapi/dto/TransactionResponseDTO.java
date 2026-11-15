package com.kaonykk.financetutorapi.dto;

import com.kaonykk.financetutorapi.model.entity.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDTO {

	private final Long id;
	private final String type;
	private final BigDecimal amount;
	private final String description;
	private final BigDecimal balanceAfter;
	private final LocalDateTime createdAt;

	public TransactionResponseDTO(Transaction t) {
		this.id = t.getId();
		this.type = t.getType().name();
		this.amount = t.getAmount();
		this.description = t.getDescription();
		this.balanceAfter = t.getBalanceAfter();
		this.createdAt = t.getCreatedAt();
	}

	public Long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getBalanceAfter() {
		return balanceAfter;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
