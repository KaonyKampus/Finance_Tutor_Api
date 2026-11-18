package com.kaonykk.financetutorapi.dto;

import java.math.BigDecimal;

public class LimitRequestDTO {
    private Long accountId;
    private BigDecimal dailyLimit;

    public LimitRequestDTO() { }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public BigDecimal getDailyLimit() { return dailyLimit; }
    public void setDailyLimit(BigDecimal dailyLimit) { this.dailyLimit = dailyLimit; }
}