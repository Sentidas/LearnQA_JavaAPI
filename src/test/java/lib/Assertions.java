package lib;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.response.Response;

public class Assertions {

     public static void assertJsonByName(Response Response, String name, int expectedValue) {
         Response.then().assertThat().body("$", hasKey(name));

         int value = Response.jsonPath().getInt(name);
         assertEquals(expectedValue, value, "JSON value is not equal to expected value");
     }

    public static void assertJsonByName(Response Response, String name, String expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));

        String value = Response.jsonPath().getString(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }

     public static void assertResponseTextEquals(Response Response, String expectedAnswer) {
         assertEquals(expectedAnswer, Response.asString(), "Response text is not as expected");
     }

     public static void asssertResponseCodeEquals(Response Response, int expectedStatusCode) {
         assertEquals(expectedStatusCode, Response.statusCode(), "Response code is not as expected");
     }

     public static void assertJsonHasField(Response Response, String expectedFieldName) {
         Response.then().assertThat().body("$", hasKey(expectedFieldName));
     }

    public static void assertJsonHasNotField(Response Response, String expectedFieldName) {
        Response.then().assertThat().body("$", not(hasKey(expectedFieldName)));
    }

     public static void assertJsonHasFields(Response Response, String[] expectedFieldNames) {
         for (String expectedFieldName : expectedFieldNames) {
             Assertions.assertJsonHasField(Response, expectedFieldName);
     }
}


}


