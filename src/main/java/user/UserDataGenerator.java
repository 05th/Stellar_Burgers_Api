package user;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class UserDataGenerator {

    public static Faker faker = new Faker();

    @Step("Create new user with random data")
    public static UserModel createUserWithRandomData() {
        return new UserModel(
                faker.name().firstName(),
                faker.internet().emailAddress(),
                faker.internet().password());
    }
    @Step("Create random  empty user ")
    public static UserModel createUserWithEmptyData() {
        return new UserModel(
                "",
                "",
                "");
    }
}