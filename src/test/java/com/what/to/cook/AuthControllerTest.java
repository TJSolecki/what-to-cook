package com.what.to.cook;

import jakarta.servlet.http.Cookie;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
final class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerShouldRegisterUserAndReturnValidSession() throws Exception {
        MvcResult result = mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {"email": "test44@example.com", "password": "password123"}
                        """
                    )
            )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.cookie().exists("session-token"))
            .andReturn();
    }

    @Test
    void testRegisterInvalidEmail() throws Exception {
        String email = "invalid-email";
        String password = "password123";

        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {"email": "test44@", "password": "password123"}
                                """
                    )
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(
                MockMvcResultMatchers.content()
                    .json(
                        """
                        {"message": "Invalid email address"}
                        """
                    )
            );
    }

    @Test
    void testRegisterExistingEmail() throws Exception {
        String json =
            """
            {"email": "existing@example.com", "password": "password123"}
            """;
        // Create a user with the same email before testing
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content(json)
        );

        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content(json)
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(
                MockMvcResultMatchers.content()
                    .json(
                        """
                        {"message": "Email already in use"}
                        """
                    )
            );
    }
}
