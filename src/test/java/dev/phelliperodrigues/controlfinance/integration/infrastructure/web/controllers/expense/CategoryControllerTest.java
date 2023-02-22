package dev.phelliperodrigues.controlfinance.integration.infrastructure.web.controllers.expense;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.phelliperodrigues.controlfinance.application.dto.expense.CategoryRequestDTO;
import dev.phelliperodrigues.controlfinance.infrastructure.web.controllers.expense.CategoryControllerImpl;
import dev.phelliperodrigues.controlfinance.sample.CategoryRequestDTOSample;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = CategoryControllerImpl.class)
@AutoConfigureMockMvc
class CategoryControllerTest {

    static String EXPENSE_CATEGORY_API = "/api/v1/expenses/categories";

    @Autowired
    MockMvc mvc;

    @Test()
    @DisplayName("Should return BadRequest if name it empty")
    public void shouldReturnBadRequestIfNameItEmpty() throws Exception {
        var requestDTO = CategoryRequestDTO.builder().build();
        var json = new ObjectMapper().writeValueAsString(requestDTO);

        var request = MockMvcRequestBuilders.post(EXPENSE_CATEGORY_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].defaultMessage").value("Nome não pode ser vazio"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].objectName").value("categoryRequestDTO"));

    }

    @TestFactory
    Stream<DynamicTest> shouldCreateACategoryWithSuccess() {

        record TestCase(String name, MockMvc mvc, CategoryRequestDTO dto) {

            @Test
            @DisplayName("${name}")
            void execute() throws Exception {
                var json = new ObjectMapper().writeValueAsString(dto);

                var request = MockMvcRequestBuilders.post(EXPENSE_CATEGORY_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json);
                mvc.perform(request)
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andExpect(MockMvcResultMatchers.jsonPath("name").value(dto.getName()))
                        .andExpect(MockMvcResultMatchers.jsonPath("description").value(dto.getDescription()))
                        .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                        .andExpect(MockMvcResultMatchers.jsonPath("created_at").isNotEmpty())
                        .andExpect(MockMvcResultMatchers.jsonPath("updated_at").isNotEmpty());

            }
        }

        var testCases = Stream.of(
                new TestCase("Valid Complete Payload", mvc, CategoryRequestDTOSample.createComplete()),
                new TestCase("Valid With Description Empty", mvc, CategoryRequestDTOSample.createWithoutDescription())
        );

        return DynamicTest.stream(testCases.flatMap(this::expandTestCases), this::generateDisplayName, TestCaseMethod::invoke);
    }

    String generateDisplayName(TestCaseMethod tcm) {
        Method m = tcm.method();
        DisplayName displayName = m.getAnnotation(DisplayName.class);
        String testName = m.getName();
        if (displayName != null) {
            testName = renderNameFromRecordComponents(displayName.value(), tcm.testCase(), tcm.testCase().getClass().getRecordComponents());
        }
        return testName;
    }

    Stream<TestCaseMethod> expandTestCases(Object testCaseRecord) {
        List<TestCaseMethod> tcms = new ArrayList<>();
        for (Method m : testCaseRecord.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                tcms.add(new TestCaseMethod(testCaseRecord, m));
            }
        }
        return tcms.stream();
    }

    private static String renderNameFromRecordComponents(String value, Object record, RecordComponent[] recordComponents) {
        String name = value;
        for (RecordComponent rc : recordComponents) {
            Object result = null;
            try {
                result = rc.getAccessor().invoke(record);
            } catch (Exception e) {
                e.printStackTrace();
            }
            name = name.replaceAll("\\$\\{" + rc.getName() + "}", String.valueOf(result));
        }
        return name;
    }

    record TestCaseMethod(Object testCase, Method method) {
        void invoke() throws InvocationTargetException, IllegalAccessException {
            method.invoke(testCase);
        }
    }

}