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

public class CreateOrderTest {
    private final UserSteps userSteps = new UserSteps();
    private final OrderSteps orderSteps = new OrderSteps();
    private String accessToken;
    private Response response;
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
    @DisplayName("Create order with authorized user and valid ingredient hash")
    public void createOrderWithAuthorizedUserAndValidIngredientHashReturnOk() {
        UserModel user = UserDataGenerator.createUserWithRandomData();
        OrderModel order = new OrderModel(validIngredient);
        response = userSteps.userCreate(user);
        accessToken = response.then().extract().body().path("accessToken");
        response = orderSteps.createOrderWithAuthorized(order, accessToken);
        response.then()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Create order without authorized user and invalid ingredient hash")
    public void createOrderWithoutUserLoginAndEmptyIngHashShouldReturnOk() {
        OrderModel order = new OrderModel(validIngredient);
        response = orderSteps.createOrderWithAuthorized(order, "");
        response.then()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Create order without ingredient hash")
    public void createOrderWithoutIngredientHashReturnError() {
        UserModel user = UserDataGenerator.createUserWithRandomData();
        OrderModel order = new OrderModel();
        response = userSteps.userCreate(user);
        response = orderSteps.createOrderWithoutAuthorized(order);
        response.then()
                .body("success", equalTo(false))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Create order with empty ingredient hash")
    public void createOrderWithEmptyIngredientHashReturnError() {
        UserModel user = UserDataGenerator.createUserWithRandomData();
        OrderModel order = new OrderModel();
        response = userSteps.userCreate(user);
        response = orderSteps.createOrderWithoutAuthorized(order);
        response.then()
                .body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Create order with wrong ingredient hash")
    public void createOrderWithWrongIngredientHashReturnError() {
        validIngredient.set_id("MutantIngredientTokenWussHere.");
        OrderModel order = new OrderModel(validIngredient);
        UserModel user = UserDataGenerator.createUserWithRandomData();
        response = userSteps.userCreate(user);
        accessToken = response.then().extract().body().path("accessToken");
        response = orderSteps.createOrderWithAuthorized(order, accessToken);
        response.then().
                statusCode(500);
    }

    @Test
    @DisplayName("Check ingredient in database")
    public void getStatusOfIngredients() {
        response = orderSteps.getIngredientsData();
        response.then()
                .body("success", equalTo(true))
                .and()
                .body("data", notNullValue())
                .and()
                .statusCode(200);
    }

}
