package com.kaonykk.financetutorapi.dto;

public class AssignmentRequestDTO {
    private Long tutorId;
    private Long principalId;

    public AssignmentRequestDTO() { }

    public Long getTutorId() { return tutorId; }
    public void setTutorId(Long tutorId) { this.tutorId = tutorId; }

    public Long getPrincipalId() { return principalId; }
    public void setPrincipalId(Long principalId) { this.principalId = principalId; }
}