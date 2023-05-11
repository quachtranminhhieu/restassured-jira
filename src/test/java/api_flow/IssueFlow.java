package api_flow;

import builder.IssueContentBuilder;
import builder.TransitionContentBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.IssueFields;
import org.apache.commons.lang3.RandomStringUtils;
import utilities.IssueInfo;
import utilities.ProjectInfo;
import utilities.TransitionInfo;

import java.util.List;
import java.util.Map;

public class IssueFlow {

    private static final String CREATE_ISSUE_PATH = "/rest/api/3/issue";
    private String createdIssueKey;
    private RequestSpecification request;
    private Response response;
    private String baseUri;
    private String projectKey;
    private IssueInfo issueInfo;
    private String issueTypeStr;
    private IssueFields issueFields;
    private String issueStatus;

    public IssueFlow(RequestSpecification request, String baseUri, String projectKey, String issueTypeStr) {
        this.request = request;
        this.baseUri = baseUri;
        this.projectKey = projectKey;
        this.issueTypeStr = issueTypeStr;
        this.issueStatus = "To Do";
        this.issueInfo = new IssueInfo(baseUri);
    }

    public void createIssue(){
        String summary = "Random Summary: " + RandomStringUtils.random(10, true, true);

        ProjectInfo projectInfo = new ProjectInfo(baseUri, projectKey);
        String taskTypeId = projectInfo.getIssueTypeId(issueTypeStr);

        IssueContentBuilder issueContentBuilder = new IssueContentBuilder();
        String issueFieldContent = issueContentBuilder.build(projectKey,taskTypeId, summary);

        issueFields = issueContentBuilder.getIssueFields();

        response = request.body(issueFieldContent).post(CREATE_ISSUE_PATH);

        Map<String, String> responeBody = JsonPath.from(response.asString()).get();
        createdIssueKey = responeBody.get("key");

        System.out.println("\n----------AFTER CREATING ISSUE----------");
        System.out.println(response.statusLine());
        System.out.println("----------------------------------------");
    }

    public void verifyIssueDetails(){
        String expectedSummary = issueFields.getFields().getSummary();
        String expectedStatus = issueStatus;

        Map<String, String> issueInfo = this.issueInfo.getIssueInfo(createdIssueKey);
        String actualSummary = issueInfo.get("summary");
        String actualStatus = issueInfo.get("status");

        System.out.println("\n------------VERIFYING ISSUE-------------");
        System.out.println("Expected Summary = " + expectedSummary);
        System.out.println("Expected Status = " + expectedStatus);

        System.out.println("Actual Summary = " + actualSummary);
        System.out.println("Actual Status = " + actualStatus);
        System.out.println("----------------------------------------");
    }

    public void updateIssue(String expectedStatus){
        issueStatus = expectedStatus;

        final String STATUS_ID = new TransitionInfo(baseUri,createdIssueKey).getTransitionId(issueStatus);

        String updateIssuePath = CREATE_ISSUE_PATH + "/" + createdIssueKey + "/transitions";

        TransitionContentBuilder transitionContentBuilder = new TransitionContentBuilder();
        String updateTransitionContent = transitionContentBuilder.build(STATUS_ID);

        response = request.body(updateTransitionContent).post(updateIssuePath);

        Map<String, String> issueInfo = this.issueInfo.getIssueInfo(createdIssueKey);

        System.out.println("\n----------AFTER UPDATING ISSUE----------");
        System.out.println("Expected New Status = " + issueStatus);
        System.out.println("Actual New status = " + issueInfo.get("status"));
        System.out.println("----------------------------------------");
    }

    public void deleteIssue(){
        String deleteIssuePath = CREATE_ISSUE_PATH + "/" + createdIssueKey;
        request.delete(deleteIssuePath);

        String getIssuePath = CREATE_ISSUE_PATH + "/" + createdIssueKey;
        response = request.get(getIssuePath);

        Map<String, List<String>> errorMessageBody = JsonPath.from(response.body().asString()).get();
        List<String> errorMessages = errorMessageBody.get("errorMessages");

        System.out.println("\n----------AFTER DELETING ISSUE----------");
        System.out.println(errorMessages.get(0));
        System.out.println("----------------------------------------");
    }
}
