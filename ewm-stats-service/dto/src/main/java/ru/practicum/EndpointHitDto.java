package ru.practicum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHitDto {
    private Integer id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
