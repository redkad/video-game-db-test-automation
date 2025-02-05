import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.example.services.AuthService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Feature("Authentication API")
@Story("Authenticate User")
public class AuthTests {
    @Test
    public void testAuthentication() {
        String token = AuthService.getAuthToken();
        assertNotNull(token, "Token should not be null");
    }
}
