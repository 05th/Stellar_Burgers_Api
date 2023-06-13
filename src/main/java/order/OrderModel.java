package order;
import ingredient.IngredientModel;
public class OrderModel {
    private String[] ingredients;
    private String _id;
    private String status;
    private String number;

    public OrderModel() {
    }

    public OrderModel(IngredientModel ingredient) {
        this.ingredients = new String[]{
                ingredient.get_id()
        };
    }
}