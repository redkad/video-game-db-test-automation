import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.example.services.VideoGameService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Video Game Management API")
@Story("CRUD Operations for Video Games")
public class VideoGameTests {
    @Test
    @Description("Test to fetch all video games")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAllVideoGames() {
        Response response = VideoGameService.getAllVideoGames();
        assertEquals(200, response.getStatusCode());
        assertTrue(response.body().asString().contains("id"), "Response should contain 'id'");
        assertTrue(response.body().asString().contains("name"), "Response should contain 'name'");
        assertTrue(response.body().asString().contains("releaseDate"), "Response should contain 'releaseDate'");
        assertTrue(response.body().asString().contains("reviewScore"), "Response should contain 'reviewScore'");
        assertTrue(response.body().asString().contains("category"), "Response should contain 'category'");
        assertTrue(response.body().asString().contains("rating"), "Response should contain 'rating'");
        List<Integer> reviewScores = response.jsonPath().getList("reviewScore");
        for (Integer reviewScore : reviewScores) {
            assertTrue(reviewScore >= 0 && reviewScore <= 100, "Review score for game should be between 0 and 100");
        }
        List<String> ratings = response.jsonPath().getList("rating");
        for (String rating : ratings) {
            assertTrue(rating.equals("Universal") || rating.equals("PG-13") || rating.equals("Mature"), "Rating should be one of the valid values");
        }

        List<Integer> ids = response.jsonPath().getList("id");
        Set<Integer> uniqueIds = new HashSet<>(ids);
        assertEquals(ids.size(), uniqueIds.size(), "There are duplicate 'id' values in the response.");
    }

    @Test
    public void testCreateVideoGame() {
        Response response = VideoGameService.createVideoGame();
        response.then().statusCode(200);
    }

    @Test
    @Description("Test to get video game by Id")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetVideoGameById() {
        int gameId = 4;
        Response response = VideoGameService.getVideoGameById(gameId);
        response.then().statusCode(200);
        assertTrue(response.body().asString().contains("id"), "Response should contain 'id'");
        assertTrue(response.body().asString().contains("name"), "Response should contain 'name'");
        assertTrue(response.body().asString().contains("releaseDate"), "Response should contain 'releaseDate'");

        assertTrue(response.body().asString().contains("Super Mario 64"), "Response should contain 'Super Mario 64'");
        assertEquals(gameId, response.jsonPath().getInt("id"));
    }

    @Test
    public void testPutVideoGameById() {
        int videoGameId = 10;

        Response response = VideoGameService.putVideoGameById(videoGameId);

        assertEquals(200, response.getStatusCode(), "Expected status code 200");

        System.out.println("Updated Video Game Response: " + response.asString());

        assertTrue(response.asString().contains("RDR 2"), "Response should contain updated 'RDR 2'");
        assertTrue(response.asString().contains("Platform"), "Response should contain 'Platform' category");
    }

    @Test
    public void testDeleteVideoGameById() {
        int videoGameId = 10;        Response response = VideoGameService.deleteVideoGameById(videoGameId);

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        System.out.println("Delete Video Game Response: " + response.asString());

        assertTrue(response.asString().contains("Game deleted successfully"), "Response should confirm deletion");
    }
}