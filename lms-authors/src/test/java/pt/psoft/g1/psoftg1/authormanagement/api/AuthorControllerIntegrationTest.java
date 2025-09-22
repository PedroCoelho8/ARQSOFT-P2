package pt.psoft.g1.psoftg1.authormanagement.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Based on https://www.baeldung.com/spring-boot-testing
 * <p>
 * Adaptations to Junit 5 with ChatGPT
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}
