package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class UserEditTest extends BaseTestCase {
    @Test
    public void testEditJustCreatedUser() {

        // GENERATE USER
        Map<String,String> userData = DataGenerator.getRegistrationData();
        Response responseCreateUser = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        int userId = getIntFromJson(responseCreateUser, "id");

        // LOGIN USER
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));
        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        // EDIT USER
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth,"x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();
        responseEditUser.prettyPrint();

        // GET USER
        Response responseUserData = RestAssured
                .given()
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .header("x-csrf-token", this.getHeader(responseGetAuth,"x-csrf-token"))
                .get("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();
        responseUserData.prettyPrint();

        Assertions.assertJsonByName(responseUserData, "firstName", newName);
    }
}
