package com.kaonykk.financetutorapi.dto;

public class DecisionRequestDTO {
    private Long requestId;
    private String action;

    public DecisionRequestDTO() { }

    public Long getRequestId() { return requestId; }
    public void setRequestId(Long requestId) { this.requestId = requestId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}