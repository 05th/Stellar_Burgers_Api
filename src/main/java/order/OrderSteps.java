package order;
import constant.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Create order with authorized")
    public Response createOrderWithAuthorized(OrderModel order, String token) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(EndPoints.URL)
                .body(order)
                .post(EndPoints.ORDERS);
    }
    @Step("Create order without authorized")
    public Response createOrderWithoutAuthorized(OrderModel order) {
        return given()
                .header("Content-Type", "application/json")
                .baseUri(EndPoints.URL)
                .body(order)
                .post(EndPoints.ORDERS);
    }
    @Step("Get data orders from user by token")
    public Response getUserDataOrder(String accessToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .baseUri(EndPoints.URL)
                .get(EndPoints.ORDERS);
    }
    @Step("Get ingredients data")
    public Response getIngredientsData() {
        return given()
                .header("Content-Type", "application/json")
                .baseUri(EndPoints.URL)
                .get(EndPoints.INGREDIENTS);
    }
}