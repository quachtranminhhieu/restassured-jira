package test;

import com.google.gson.Gson;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.PostBody;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;

public class POSTMethod {
    public static void main(String[] args) {
        String baseUri = "https://jsonplaceholder.typicode.com";

        // Request
        RequestSpecification request = given();
        request.baseUri(baseUri);

        request.header(new Header("Content-type", "application/json; charset=UTF-8"));

        // Gson
        Gson gson = new Gson();
        PostBody postBody = new PostBody();
        postBody.setUserId(1);
        postBody.setId(1);
        postBody.setTitle("Automation FC");
        postBody.setBody("Automation FC Body");

        // Response
        Response response = request.body(gson.toJson(postBody)).post("/posts");
        response.prettyPrint();

        // Verification
        response.then().statusCode(equalTo(201));
        response.then().statusLine(containsStringIgnoringCase("201 Created"));
        response.then().body("userId", equalTo(1));
        response.then().body("title", equalTo("Automation FC"));
        response.then().body("body", equalTo("Automation FC Body"));
    }
}
