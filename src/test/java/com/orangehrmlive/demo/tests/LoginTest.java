package com.orangehrmlive.demo.tests;

import com.orangehrmlive.demo.annotations.Steps;
import com.orangehrmlive.demo.steps.PageLoginSteps;
import com.orangehrmlive.demo.utils.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class LoginTest extends BaseTest {

    @Steps
    private PageLoginSteps pageLogin;

    private static Stream<Arguments> correctLoginParameters() {
        return Stream.of(
                Arguments.of("Admin", "admin123"),
                Arguments.of("admin", "admin123"),
                Arguments.of("ADMIN", "admin123"),
                Arguments.of(" Admin ", "admin123"),
                Arguments.of("Admin ", "admin123")
        );
    }

    @DisplayName("Correct login")
//    @ParameterizedTest(name = "{index} login with: username=''{0}'', password=''{1}''")
    @MethodSource("correctLoginParameters")
    public void correctLogin(String username, String password) {
        pageLogin.open();
        pageLogin.login(username, password);
        pageLogin.user_is_logged_in();
    }

    private static Stream<Arguments> incorrectLoginParameters() {
        return Stream.of(

                Arguments.of("", "", "Username cannot be empty"),
                Arguments.of("    ", "admin123", "Username cannot be empty"),
                Arguments.of("", "admin123", "Username cannot be empty"),
                Arguments.of("Admin", "", "Password cannot be empty"),
                Arguments.of("Admin", "    ", "Invalid credentials"),
                Arguments.of("Adm", "admin12", "Invalid credentials"),
                Arguments.of("Admin", "admin12", "Invalid credentials"),
                Arguments.of("Adm", "admin123", "Invalid credentials"),
                Arguments.of("Adm in", "admin123", "Invalid credentials"),
                Arguments.of("admin123", "Admin", "Invalid credentials"),
                Arguments.of("Admin", "ADMIN123", "Invalid credentials"),
                Arguments.of("Admin", "AdMiN123", "Invalid credentials"),
                Arguments.of("<script>alert(123)</script>", "admin123", "Invalid credentials"),
                Arguments.of("♣☺♂” , “”‘~!@#$%^&*()?>,./\\<][ /*<!–”\", “${code}", "admin123", "Invalid credentials"),
                Arguments.of("Admin", "♣☺♂” , “”‘~!@#$%^&*()?>,./\\<][ /*<!–”\", “${code}", "Invalid credentials"),
                Arguments.of("</body>", "admin123", "Invalid credentials")
        );
    }

    @DisplayName("Incorrect login")
//    @ParameterizedTest(name = "{index} login with: username=''{0}'', password=''{1}'' -> ''{2}''")
    @MethodSource("incorrectLoginParameters")
    public void incorrectLogin(String username, String password, String message) {
        pageLogin.open();
        pageLogin.login(username, password);
        pageLogin.validation_message_is_showed(message);
    }
}
