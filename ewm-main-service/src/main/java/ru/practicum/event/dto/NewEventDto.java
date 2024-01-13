package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.event.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    private Long id;
    @NotBlank
    @NotNull
    @Length(min = 20, max = 2000)
    private String annotation;
    private Integer category;
    @NotBlank
    @NotNull
    @Length(min = 20, max = 7000)
    private String description;
    private String eventDate;
    @NotNull
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @Length(min = 3, max = 120)
    private String title;
}