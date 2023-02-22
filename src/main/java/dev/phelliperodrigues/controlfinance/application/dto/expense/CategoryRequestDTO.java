package dev.phelliperodrigues.controlfinance.application.dto.expense;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class CategoryRequestDTO {
    private UUID id;
    @NotEmpty(message = "{name.notempty}")
    private String name;
    private String description;
}
