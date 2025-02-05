package org.example.services;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.example.config.ConfigManager;

public class VideoGameService {
    public static Response getAllVideoGames() {
        RestAssured.baseURI = ConfigManager.getProperty("base.url");
        return given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .when()
                .get(ConfigManager.getProperty("videogame.endpoint"))
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public static Response createVideoGame() {
        RestAssured.baseURI = ConfigManager.getProperty("base.url");

        return given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .body(ConfigManager.getProperty("videogame.body"))
                .when()
                .post(ConfigManager.getProperty("videogame.endpoint"))
                .then()
                .statusCode(200)
                .extract()
                .response();


    }

    public static Response getVideoGameById(int id) {
        RestAssured.baseURI = ConfigManager.getProperty("base.url");

        return given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .when()
                .get(ConfigManager.getProperty("get.videogame.endpoint").replace("{id}", String.valueOf(id)))
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public static Response putVideoGameById(int id) {
        RestAssured.baseURI = ConfigManager.getProperty("base.url");

        return given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .body(ConfigManager.getProperty("put.videogame.body")) // Read JSON from config
                .when()
                .put(ConfigManager.getProperty("put.videogame.endpoint").replace("{id}", String.valueOf(id))) // Replace {id} with actual ID
                .then()
                .statusCode(200) // Assuming API returns 200 on success
                .extract()
                .response();
    }
    public static Response deleteVideoGameById(int id) {
        RestAssured.baseURI = ConfigManager.getProperty("base.url");

        return given()
                .header("Accept", "application/json")
                .when()
                .delete(ConfigManager.getProperty("delete.videogame.endpoint").replace("{id}", String.valueOf(id))) // Replace {id} with actual ID
                .then()
                .statusCode(200) // Assuming API returns 200 on successful deletion
                .extract()
                .response();
    }
}
