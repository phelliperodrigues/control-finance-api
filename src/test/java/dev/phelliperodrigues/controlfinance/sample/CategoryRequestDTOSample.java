package dev.phelliperodrigues.controlfinance.sample;

import com.github.javafaker.Faker;
import dev.phelliperodrigues.controlfinance.application.dto.expense.CategoryRequestDTO;

public class CategoryRequestDTOSample {

    public static CategoryRequestDTO createComplete() {
        var faker = new Faker();
        return CategoryRequestDTO.builder()
                .name(faker.book().genre())
                .description(faker.lorem().paragraph())
                .build();
    }

    public static CategoryRequestDTO createWithoutDescription() {
        var faker = new Faker();
        return CategoryRequestDTO.builder()
                .name(faker.book().genre())
                .build();
    }
    public static CategoryRequestDTO createWithoutName() {
        var faker = new Faker();
        return CategoryRequestDTO.builder()
                .description(faker.lorem().paragraph())
                .build();
    }

}
