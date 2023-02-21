package dev.phelliperodrigues.controlfinance.infrastructure.web.controllers.expense.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static dev.phelliperodrigues.controlfinance.infrastructure.web.controllers.URLRequestMappers.EXPENSE_CATEGORY_API;

@RequestMapping(path = EXPENSE_CATEGORY_API)
@Tag(name = "Expense Category API")
public interface CategoryController {
    @PostMapping
    @Operation(summary = "Create a expense category", description = "", method = "create(CategoryDTO dto)")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Object> create(@RequestBody Object request);
}
