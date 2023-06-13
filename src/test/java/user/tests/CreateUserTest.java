package user.tests;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import user.UserModel;
import user.UserDataGenerator;
import user.UserSteps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserTest {
    private final UserSteps userSteps = new UserSteps();
    private Response response;
    private UserModel user;
    private String accessToken;

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userSteps.userDelete(accessToken);
        }
    }
    @Test
    @DisplayName("Create new user with random valid data")
    @Description("Make sure that token is not empty")
    public void createUserWithRandomValidDataShouldBeSuccessful() {
        user = UserDataGenerator.createUserWithRandomData();
        response = userSteps.userCreate(user);
        accessToken = response
                .then().extract().body().path("accessToken");
        response
                .then().body("accessToken", notNullValue())
                .and()
                .statusCode(200);
    }
    @Test
    @DisplayName("Create user with registered data before")
    @Description("Response status code 403, error message \"user already exists\"")
    public void createUserWithRegisteredDataBeforeReturnError() {
        user = UserDataGenerator.createUserWithRandomData();
        response = userSteps.userCreate(user);
        accessToken = response
                .then().extract().body().path("accessToken");
        response.then()
                .statusCode(200);
        response = userSteps.userCreate(user);
        response.then()
                .body("message", equalTo("User already exists"))
                .and()
                .statusCode(403);
    }
    @Test
    @DisplayName("Create user with empty name field")
    @Description("Response status code 403, error message \"email password and name are required fields\"")
    public void createUserWithEmptyNameFieldReturnError() {
        user = UserDataGenerator.createUserWithEmptyData();
        response = userSteps.userCreate(user);
        accessToken = response
                .then().extract().body().path("accessToken");
        response.then()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }
}