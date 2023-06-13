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
import static user.UserDataGenerator.faker;

public class DataChangingUserTest {
    private final UserSteps userSteps = new UserSteps();
    private Response response;
    private UserModel user;
    private String accessToken;
    protected String name;
    protected String email;
    protected String password;

    @Before
    public void setUp() {
        user = UserDataGenerator.createUserWithRandomData();
        response = userSteps.userCreate(user);
        accessToken = response
                .then().extract().body().path("accessToken");
    }
    @Test
    @DisplayName("Change authorized user data")
    @Description("Response status code 200, create user, change name, change email")
    public void changeAuthorizedUserDataUpdateUserAccount() {
        user.setName(faker.name().firstName());
        user.setEmail(faker.internet().emailAddress());
        response = userSteps.userDataAccountChanging(user, accessToken);
        user.setName(name);
        user.setEmail(email);
        response.then()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }
    @Test
    @DisplayName("Change authorized user password data")
    @Description("Response status code 200, create user, change password")
    public void changeAuthorizedUserPasswordDataReturnOk() {
        user.setPassword(faker.internet().password());
        response = userSteps.userDataAccountChanging(user, accessToken);
        user.setPassword(password);
        response.then()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }
    @Test
    @DisplayName("Change user data password without authorization")
    @Description("Response status code 401, crate user, change password")
    public void changeUserDataPasswordWithoutAuthorizationReturnError() {
        user.setPassword(faker.internet().password());
        response = userSteps.userDataAccountChanging(user, "");
        user.setPassword(password);
        response.then()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
    }
    @Test
    @DisplayName("Change unauthorized user data")
    @Description("Response status code 401, create user, change name, change email")
    public void changeUnauthorizedUserDataReturnError() {
        user.setName(faker.name().firstName());
        user.setEmail(faker.internet().emailAddress());
        response = userSteps.userDataAccountChanging(user, "");
        user.setName(name);
        user.setEmail(email);
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