package test;

import builder.IssueContentBuilder;
import builder.RequestBuilder;
import builder.TransitionContentBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.IssueFields;
import model.RequestCapability;
import org.apache.commons.lang3.RandomStringUtils;
import utilities.IssueInfo;
import utilities.ProjectInfo;
import utilities.TransitionInfo;

import java.util.List;
import java.util.Map;

public class JiraDelateIssue implements RequestCapability {
    public static void main(String[] args) {
        // API Info
        String baseUri = "https://rest-assured-tutorial.atlassian.net";
        String projectKey = "RAT";

        // Request
        RequestSpecification request = RequestBuilder.getRequest(baseUri);

        // Create issue body data
        String summary = "Random Summary: " + RandomStringUtils.random(10, true, true);

        ProjectInfo projectInfo = new ProjectInfo(baseUri, projectKey);
        String taskTypeId = projectInfo.getIssueTypeId("task");

        IssueContentBuilder issueContentBuilder = new IssueContentBuilder();
        String issueFieldContent = issueContentBuilder.build(projectKey,taskTypeId, summary);

        // Send create issue request
        String createIssuePath = "/rest/api/3/issue";

        Response response = request.body(issueFieldContent).post(createIssuePath);
        System.out.println("Response create status line ====== " + response.statusLine());

        Map<String, String> responeBody = JsonPath.from(response.asString()).get();
        final String CREATED_ISSUE_KEY = responeBody.get("key");

        String getIssuePath = "/rest/api/3/issue/" + CREATED_ISSUE_KEY;

        response = request.get(getIssuePath);
        System.out.println("Response get status line ====== " + response.statusLine());

        // Verify Summary and Status

        IssueFields issueFields = issueContentBuilder.getIssueFields();
        String expectedSummary = issueFields.getFields().getSummary();
        String expectedStatus = "To Do";

        IssueInfo issueInfo = new IssueInfo(baseUri);
        Map<String, String> issueInfomation = new IssueInfo(baseUri).getIssueInfo(CREATED_ISSUE_KEY);

        System.out.println("Expected Summary = " + expectedSummary);
        System.out.println("Expected Status = " + expectedStatus);

        System.out.println("Actual Summary = " + issueInfomation.get("summary"));
        System.out.println("Actual Status = " + issueInfomation.get("status"));

        // Get transition list
        String getTransitionPath = "/rest/api/3/issue/" + CREATED_ISSUE_KEY + "/transitions";
        response = request.get(getTransitionPath);
        System.out.println("Response get status line ====== " + response.statusLine());

        // Set body
        expectedStatus = "Done";
        final String STATUS_ID = new TransitionInfo(baseUri,CREATED_ISSUE_KEY).getTransitionId(expectedStatus);

        String updateIssuePath = "/rest/api/3/issue/" + CREATED_ISSUE_KEY + "/transitions";

        TransitionContentBuilder transitionContentBuilder = new TransitionContentBuilder();
        String updateTransitionContent = transitionContentBuilder.build(STATUS_ID);

        // Send request update
        response = request.body(updateTransitionContent).post(updateIssuePath);
        System.out.println("Response update status line ====== " + response.statusLine());

        // Verify new status
        issueInfomation = issueInfo.getIssueInfo(CREATED_ISSUE_KEY);
        System.out.println("New status = " + issueInfomation.get("status"));

        // Delete Jira Task
        String deleteIssuePath = "/rest/api/3/issue/" + CREATED_ISSUE_KEY;
        request.delete(deleteIssuePath);

        // Verify task is deleted
        getIssuePath = "/rest/api/3/issue/" + CREATED_ISSUE_KEY;
        response = request.get(getIssuePath);
        System.out.println("Response delete status line ====== " + response.statusLine());
        Map<String, List<String>> errorMessageBody = JsonPath.from(response.body().asString()).get();
        List<String> errorMessages = errorMessageBody.get("errorMessages");
        System.out.println("After deleting task: " + errorMessages.get(0));
    }
}
