package dev.phelliperodrigues.controlfinance.integration.infrastructure.web.controllers.expense;

import dev.phelliperodrigues.controlfinance.infrastructure.web.controllers.expense.CategoryControllerImpl;
import dev.phelliperodrigues.controlfinance.infrastructure.web.controllers.expense.docs.CategoryController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = CategoryControllerImpl.class)
@AutoConfigureMockMvc
class CategoryControllerTest {

    static String EXPENSE_CATEGORY_API = "/api/v1/expenses/categories";

    @Autowired
    MockMvc mvc;

    @Test()
    @DisplayName("Should create a expense category with success")
    public void shouldCreateACategoryWithSuccess() throws Exception {
        var request = MockMvcRequestBuilders.post(EXPENSE_CATEGORY_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("");

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath("description").value(""));

    }

}