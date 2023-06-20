package ingredient;

public class IngredientResponse {
    private boolean success;
    private IngredientModel[] data;

    public IngredientModel[] getIngredients() {
        return data;
    }

    public void setIngredients(IngredientModel[] ingredients) {
        this.data = ingredients;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}