package test;

import com.google.gson.Gson;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.PostBody;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.given;

public class PUTMethod {
    public static void main(String[] args) {
        String baseUri = "https://jsonplaceholder.typicode.com";

        RequestSpecification request = given();
        request.baseUri(baseUri);

        request.header(new Header("Content-type", "application/json; charset=UTF-8"));

        Gson gson = new Gson();
        PostBody postBody1 = new PostBody(1,1,"New Automation Title 1", "New Automation Body 1");
        PostBody postBody2 = new PostBody(1,1,"New Automation Title 2", "New Automation Body 2");
        PostBody postBody3 = new PostBody(1,1,"New Automation Title 3", "New Automation Body 3");

        List<PostBody> postBodies = Arrays.asList(postBody1,postBody2,postBody3);

        for (PostBody postBody : postBodies) {
            System.out.println(postBody);
            final int TARGET_POST_NUMBER = 1;
            Response response = request.body(gson.toJson(postBody)).put("/posts/" + String.valueOf(TARGET_POST_NUMBER));
//            response.prettyPrint();

            response.then().body("id", equalTo(postBody.getId()));
            response.then().body("userId", equalTo(postBody.getUserId()));
            response.then().body("title", equalTo(postBody.getTitle()));
            response.then().body("body", equalTo(postBody.getBody()));
        }



    }
}
