package user;

import constant.EndPoints;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserSteps {

    public static RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(EndPoints.URL);
    }

    @Step("Create new user")
    public Response userCreate(UserModel user) {
        return requestSpec()
                .body(user)
                .post(EndPoints.REGISTER);
    }

    @Step("Change data of user")
    public Response userDataAccountChanging(UserModel newUser, String token) {
        return requestSpec()
                .header("Authorization", token)
                .body(newUser)
                .patch(EndPoints.USER);
    }

    @Step("User authorized by login and token")
    public Response userLoginToken(UserModel user, String token) {
        return requestSpec()
                .header("Authorization", token)
                .body(user)
                .post(EndPoints.LOGIN);
    }
    @Step("Delete user")
    public void userDelete(String token) {
        requestSpec()
                .header("Authorization", token)
                .delete(EndPoints.USER);
    }
}