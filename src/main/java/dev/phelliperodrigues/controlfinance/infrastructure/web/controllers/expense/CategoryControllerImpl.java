package dev.phelliperodrigues.controlfinance.infrastructure.web.controllers.expense;

import dev.phelliperodrigues.controlfinance.application.dto.expense.CategoryRequestDTO;
import dev.phelliperodrigues.controlfinance.application.dto.expense.CategoryResponseDTO;
import dev.phelliperodrigues.controlfinance.infrastructure.web.controllers.expense.docs.CategoryController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {
    @Override
    public ResponseEntity<CategoryResponseDTO> create(@Valid CategoryRequestDTO request) {
        return ResponseEntity.created(URI.create("")).body(CategoryResponseDTO.builder()
                        .id(UUID.randomUUID())
                .description(request.getDescription())
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()
        );
    }
}
