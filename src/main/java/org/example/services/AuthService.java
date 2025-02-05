package org.example.services;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.example.config.ConfigManager;

public class AuthService {
    public static String getAuthToken() {
        RestAssured.baseURI = ConfigManager.getProperty("base.url"); // Base URL

        Response response = given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .body("{ \"username\": \"" + ConfigManager.getProperty("username") + "\", "
                        + "\"password\": \"" + ConfigManager.getProperty("password") + "\" }") // JSON Body
                .when()
                .post(ConfigManager.getProperty("auth.endpoint"))             // Endpoint from your cURL command
                .then()
                .statusCode(200)                       // Expect 200 OK
                .extract()
                .response();

        // Extract and return the token
        return response.jsonPath().getString("token");
    }
}
