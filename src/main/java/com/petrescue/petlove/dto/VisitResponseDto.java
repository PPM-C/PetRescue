package com.petrescue.petlove.dto;

import java.time.LocalDateTime;


public class VisitResponseDto {

    private Long id;
    private Long requestId;      // <-- define este si lo usas
    private VisitStatus status;
    private Long AdoptionRequestId;
    private LocalDateTime scheduledAt;
    private Object VisitStatus;           
    private String notes;

    public VisitResponseDto() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRequestId() { return requestId; }
    public void setRequestId(Long requestId) { this.requestId = requestId; }

    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    public Object getStatus() { return status; }
    public void setStatus(Object status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
