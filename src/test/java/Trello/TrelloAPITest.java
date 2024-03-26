package Trello;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TrelloAPITest {

    private static final String BASE_URL = "https://api.trello.com";
    private static final String API_KEY = "491ee48e1a56a175e458c729c3645c88";
    private static final String TOKEN = "ATTA510f1703929f648f22bd6977aaffd9023ccf2f50209a447c20817db49ea28ad3067052CC";
    private String boardId;
    private String listId;
    private String cardId;
    private String checkListid;

    // Board operations
    @Test(priority = 1)
    public void testCreateBoard() {
        HttpResponse<JsonNode> response = Unirest.post(BASE_URL + "/1/boards")
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .queryString("name", "New Board")
                .asJson();

        // Print full response body
        System.out.println("Response body: " + response.getBody().toString());

        // Extract board ID from the JSON response
        boardId = response.getBody().getObject().getString("id");

        // Print board ID
        System.out.println("Board created with ID: " + boardId);

        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");

        // Assert name and state code
        assertEquals(response.getBody().getObject().getString("name"), "New Board", "Board name is not correct");

    }

    @Test(priority = 2)
    public void testCreateList() {
        HttpResponse<JsonNode> response = Unirest.post(BASE_URL + "/1/lists")
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .queryString("name", "Facebook")
                .queryString("idBoard", boardId)
                .asJson();
        // Extract board ID from the JSON response
        listId = response.getBody().getObject().getString("id");
        // Print list ID
        System.out.println("list created with ID: " + listId);
        // Print full response body
        System.out.println("List create response body: " + response.getBody().toString());
        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");
        // Assert name and state code
        assertEquals(response.getBody().getObject().getString("name"), "Facebook", "List name is not correct");


    }

    @Test(priority = 3)
    public void testCreateCard() {
        HttpResponse<JsonNode> response = Unirest.post(BASE_URL + "/1/cards")
                .header("Accept", "application/json")
                .queryString("idList", listId)
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .queryString("name", "logging")
                .asJson();
        // Extract board ID from the JSON response
        cardId = response.getBody().getObject().getString("id");
        // Print list ID
        System.out.println("Card created with ID: " + cardId);
        // Print full response body
        System.out.println("Card create response body: " + response.getBody().toString());
        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");
        // Assert name and state code
        assertEquals(response.getBody().getObject().getString("name"), "logging", "Card name is not correct");

    }

    @Test(priority = 2)
    public void testUpdateBoard() {
        HttpResponse<String> response = Unirest.put(BASE_URL + "/1/boards/" + boardId)
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .queryString("desc", "facebook board ")
                .asString();


        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");
        System.out.println(response.getBody());

    }

    @Test(priority = 3)
    public void testUpdateList() {
        HttpResponse<String> response = Unirest.put(BASE_URL + "/1/lists/" + listId)
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .queryString("name", "facebook_logging")
                .asString();

        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");
        System.out.println(response.getBody());

    }
    @Test(priority = 3)
    public void testUpdateCard() {
        HttpResponse<String> response = Unirest.put(BASE_URL + "/1/cards/" + cardId)
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .queryString("name", "Sign in")
                .asString();

        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");
        System.out.println(response.getBody());

    }

    @Test(priority = 2)
    public void testReadBoard() {
        HttpResponse<JsonNode> response = Unirest.get(BASE_URL + "/1/boards/" + boardId)
                .header("Accept", "application/json")
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .asJson();

        System.out.println(response.getBody());
        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");
    }

    @Test(priority = 3)
    public void testReadList() {
        HttpResponse<JsonNode> response = Unirest.get(BASE_URL + "/1/lists/" + listId)
                .header("Accept", "application/json")
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .asJson();

        System.out.println(response.getBody());
        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");
    }

    @Test(priority = 4)
    public void testReadCard() {
        HttpResponse<JsonNode> response = Unirest.get(BASE_URL + "/1/cards/" + cardId)
                .header("Accept", "application/json")
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .asJson();

        System.out.println(response.getBody());
        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");
    }

    @Test(priority = 5)
    public void testCreateCheckList() {
        HttpResponse<JsonNode> response = Unirest.post(BASE_URL + "/1/checklists")
                .queryString("idCard", cardId)
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .queryString("name", "valid logging")
                .asJson();

        // Print response body
        System.out.println("Check List creation response body: " + response.getBody().toString());

        // Extract checklist ID from the JSON response
        checkListid = response.getBody().getObject().getString("id");

        // Print checklist ID
        System.out.println("Check List created with ID: " + checkListid);

        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");
        // Assert name and state code
        assertEquals(response.getBody().getObject().getString("name"), "valid logging", "Card name is not correct");
    }
    @Test(priority = 6)
    public void testCreateCheckList_Items() {
        HttpResponse<JsonNode> response = Unirest.post(BASE_URL + "/1/checklists/"+  checkListid +"/checkItems")
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .queryString("name", "verify login using valid email and valid password")
                .asJson();

        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");

    }
    @Test(priority = 6)
    public void testReadCheckList() {
        HttpResponse<JsonNode> response = Unirest.get(BASE_URL + "/1/checklists/" + checkListid)
                .header("Accept", "application/json")
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .asJson();

        System.out.println(response.getBody());
        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");
    }
    @Test(priority = 7)
    public void DeleteCheckList() {
        HttpResponse<JsonNode> response = Unirest.delete(BASE_URL + "/1/checklists/" + checkListid)
                .header("Accept", "application/json")
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .asJson();

        System.out.println(response.getBody());
        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");
    }
    @Test(priority = 8)
    public void DeleteCard() {
        HttpResponse<JsonNode> response = Unirest.delete(BASE_URL + "/1/cards/" + cardId)
                .header("Accept", "application/json")
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .asJson();

        System.out.println(response.getBody());
        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");
    }
    @Test(priority = 9)
    public void DeleteBoard() {
        HttpResponse<JsonNode> response = Unirest.delete(BASE_URL + "/1/boards/" + boardId)
                .header("Accept", "application/json")
                .queryString("key", API_KEY)
                .queryString("token", TOKEN)
                .asJson();

        System.out.println(response.getBody());
        // Assert status code
        assertEquals(response.getStatus(), 200, "Response status code is not 200");
    }
}


