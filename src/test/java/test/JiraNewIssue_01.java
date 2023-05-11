package test;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.IssueFields;
import model.RequestCapability;
import utilities.AuthenticationHelper;
import utilities.ProjectInfo;

import static io.restassured.RestAssured.given;

public class JiraNewIssue_01 implements RequestCapability {
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
        String summary = "New Automation Task";
        ProjectInfo projectInfo = new ProjectInfo(baseUri,projectKey);
        String taskTypeId = projectInfo.getIssueTypeId("task");

        IssueFields.IssueType issueType = new IssueFields.IssueType(taskTypeId);
        IssueFields.Project project = new IssueFields.Project(projectKey);
        IssueFields.Fields fields = new IssueFields.Fields(summary,project,issueType);
        IssueFields issueFields = new IssueFields(fields);

        // Send request
        Response response = request.body(new Gson().toJson(issueFields)).post(basePath);
        response.prettyPrint();
    }
}
