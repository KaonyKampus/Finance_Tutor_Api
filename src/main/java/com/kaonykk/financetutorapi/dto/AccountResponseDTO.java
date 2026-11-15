package com.kaonykk.financetutorapi.dto;

import com.kaonykk.financetutorapi.model.entity.Account;
import java.math.BigDecimal;

public class AccountResponseDTO {

	private final Long id;
	private final String ownerName;
	private final BigDecimal balance;
	private final BigDecimal dailyLimit;

	public AccountResponseDTO(Account account) {
		this.id = account.getId();
		this.ownerName = account.getUser().getName();
		this.balance = account.getBalance();
		this.dailyLimit = account.getDailyLimit();

	}

	public Long getId() {
		return id;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public BigDecimal getDailyLimit() {
		return dailyLimit;
	}
}
