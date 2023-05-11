package test;

import builder.IssueContentBuilder;
import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.IssueFields;
import model.RequestCapability;
import org.apache.commons.lang3.RandomStringUtils;
import utilities.AuthenticationHelper;
import utilities.ProjectInfo;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class JiraGetIssue implements RequestCapability {
    public static void main(String[] args) {
        // API Info
        String baseUri = "https://rest-assured-tutorial.atlassian.net";
        String projectKey = "RAT";
        String basePath = "/rest/api/3/issue";

        String email = "nhieuautomationfc@gmail.com";
        String apiToken = "ATATT3xFfGF0ZToHvm7C9IRLp4Yq5zDKxfw7oJu6QazYYIPV-3CpbuoqbkPzqSDJtgAQOqZeL6YtfUh3pRuUBxCTzGOpPcgodqlGPf6a_lp9-PRrcYVEulsKGDIQfvxewbNG-udDFlxtvzeZGlXTacsLzmO01v_WNwJZop5nSODsyPPIsHILnO8=77D4E7C7";
        String encodedCredStr = AuthenticationHelper.getEncodedCred(email,apiToken);

        // Request
        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(getAuthenticatedHeader.apply(encodedCredStr));
        request.header(acceptJSONHeader);

        // Define body data
        int maxLength = 10;
        boolean hasLetters = true;
        boolean hasNumbers = true;
        String randomSummary = "Automation Summary: " + RandomStringUtils.random(maxLength,hasLetters,hasNumbers);

        ProjectInfo projectInfo = new ProjectInfo(baseUri,projectKey);
        String taskTypeId = projectInfo.getIssueTypeId("task");

        IssueContentBuilder issueContentBuilder = new IssueContentBuilder();
        String issueFiledContent = issueContentBuilder.build(projectKey,taskTypeId,randomSummary);
        IssueFields issueFields = issueContentBuilder.getIssueFields();

        // Create Jira Task
        Response response = request.body(issueFiledContent).post(basePath);
        response.prettyPrint();

        // Get response content
        Map<String, String> responesBody = JsonPath.from(response.body().asString()).get();
        String issuePath = "/rest/api/3/issue/" + responesBody.get("key");

        // Read created Jira task
        response = request.get(issuePath);

        String expectedSummary = issueFields.getFields().getSummary();
        String expectedStatus = "To Do";

        Map<String, Object> fields = JsonPath.from(response.body().asString()).get("fields");
        String actualSummary = fields.get("summary").toString();
        Map<String, Object> status = (Map<String, Object>) fields.get("status");
        Map<String, String> statusCategory = (Map<String, String>) status.get("statusCategory");
        String actualStatus = statusCategory.get("name");

        System.out.println("Expected Summary = " + expectedSummary);
        System.out.println("Expected Status = " + expectedStatus);

        System.out.println("Actual Summary = " + actualSummary);
        System.out.println("Actual Status = " + actualStatus);
    }
}
