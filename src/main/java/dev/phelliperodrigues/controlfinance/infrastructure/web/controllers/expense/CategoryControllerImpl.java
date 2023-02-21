package dev.phelliperodrigues.controlfinance.infrastructure.web.controllers.expense;

import dev.phelliperodrigues.controlfinance.infrastructure.web.controllers.expense.docs.CategoryController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {
    @Override
    public ResponseEntity<Object> create(Object request) {
        return null;
    }
}
