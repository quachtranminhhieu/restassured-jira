package test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.RequestCapability;
import org.apache.commons.codec.binary.Base64;
import utilities.ProjectInfo;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JiraIssueTypes_02 implements RequestCapability {
    public static void main(String[] args) {
        String baseUri = "https://rest-assured-tutorial.atlassian.net";
        String projectKey = "RAT";

        ProjectInfo projectInfo = new ProjectInfo(baseUri,projectKey);
        System.out.println("Task ID: " + projectInfo.getIssueTypeId("task"));
    }
}
