package ru.practicum.event.dto;

import lombok.Data;
import ru.practicum.enums.State;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private State status;
}
