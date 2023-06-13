package ingredient;
import constant.EndPoints;
import static io.restassured.RestAssured.given;

public class IngredientRequest {

    public static IngredientModel[] getIngredientsArray() {
        return getIngredientResponse().getIngredients();
    }

    public static IngredientResponse getIngredientResponse() {
        return given()
                .get(EndPoints.INGREDIENTS)
                .as(IngredientResponse.class);
    }

    public static IngredientModel getIngredientFromArray() {
        return getIngredientsArray()[0];
    }
}