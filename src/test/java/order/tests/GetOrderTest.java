package order.tests;
import constant.EndPoints;
import ingredient.IngredientModel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import order.OrderModel;
import order.OrderSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.UserModel;
import user.UserDataGenerator;
import user.UserSteps;

import static ingredient.IngredientRequest.getIngredientFromArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static user.UserDataGenerator.faker;

public class GetOrderTest {

    private final UserSteps userSteps = new UserSteps();
    private final OrderSteps orderSteps = new OrderSteps();
    private Response response;
    private String accessToken;
    private IngredientModel validIngredient;

    @Before
    public void setUp() {
        RestAssured.baseURI = EndPoints.URL;
        validIngredient = getIngredientFromArray();
    }


    @After
    public void cleanUp() {
        if (accessToken != null) {
            userSteps.userDelete(accessToken);
        }
    }

    @Test
    @DisplayName("Get order from authorized user")
    public void getOrderFromAuthorizedUserReturnOk() {
        UserModel user = UserDataGenerator.createUserWithRandomData();
        OrderModel order = new OrderModel(validIngredient);
        response = userSteps.userCreate(user);
        accessToken = response.then().extract().body().path("accessToken");
        response = orderSteps.createOrderWithAuthorized(order, accessToken);
        response = orderSteps.getUserDataOrder(accessToken);
        response.then()
                .body("orders", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Get order from notAuthorized user")
    public void getOrderFromNotAuthorizedUserReturnError() {
        OrderModel order = new OrderModel(validIngredient);
        response = orderSteps.createOrderWithAuthorized(order, String.valueOf(faker.random().hashCode()));
        response = orderSteps.getUserDataOrder(String.valueOf(faker.random().hashCode()));
        response.then()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }
}