package com.what.to.cook;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
final class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegisterValidUser() throws Exception {
        String email = "test@example.com";
        String password = "password123";

        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("email", email)
                    .param("password", password)
            )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.cookie().exists("session-token"));
    }

    @Test
    void testRegisterInvalidEmail() throws Exception {
        String email = "invalid-email";
        String password = "password123";

        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("email", email)
                    .param("password", password)
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string("Invalid email address"));
    }

    @Test
    void testRegisterExistingEmail() throws Exception {
        String email = "existing@example.com";
        String password = "password123";

        // Create a user with the same email before testing
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", email)
                .param("password", password)
        );

        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("email", email)
                    .param("password", password)
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string("Email already in use"));
    }
}
