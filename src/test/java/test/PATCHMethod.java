package test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.JSONHelper;
import model.PostBody;
import model.RequestCapability;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PATCHMethod implements RequestCapability {
    public static void main(String[] args) {
        String baseUri = "https://jsonplaceholder.typicode.com/";

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);

        PostBody postBody = new PostBody();
        postBody.setTitle("New Automation Title");

        final String TARGET_ID = "1";
        Response response = request.body(JSONHelper.getJSONString(postBody)).patch("/posts/" + TARGET_ID);
        response.prettyPrint();

        response.then().body("title", equalTo(postBody.getTitle()));

    }
}
