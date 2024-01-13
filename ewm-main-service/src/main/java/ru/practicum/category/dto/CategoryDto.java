package ru.practicum.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    @NotBlank
    @Length(max = 50)
    private String name;
}
