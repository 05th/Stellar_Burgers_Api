package user.tests;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.UserModel;
import user.UserDataGenerator;
import user.UserSteps;

import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {
    private final UserSteps userSteps = new UserSteps();
    private Response response;
    private UserModel userModel;
    private String accessToken;

    @Before
    public void setUp() {
        userModel = UserDataGenerator.createUserWithRandomData();
        response = userSteps.userCreate(userModel);
        accessToken = response
                .then().extract().body().path("accessToken");
    }



    @Test
    @DisplayName("Authorization with user data exist")
    @Description("Create new user and authorization with valid data")
    public void authorizationWithUserShouldBeSuccessful() {
        response = userSteps.userLoginToken(userModel, accessToken);
        response
                .then().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Authorization with invalid username and password")
    @Description("Response status code 401, create user with and authorization with invalid data")
    public void authorizationUserWithWrongPasswordAndEmailReturnError() {
        String email = userModel.getEmail();
        userModel.setEmail("wrong@email.ru");
        String password = userModel.getPassword();
        userModel.setPassword("wrongPassword");
        response = userSteps.userLoginToken(userModel, accessToken);
        userModel.setEmail(email);
        userModel.setPassword(password);
        response.then()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userSteps.userDelete(accessToken);
        }
    }
}