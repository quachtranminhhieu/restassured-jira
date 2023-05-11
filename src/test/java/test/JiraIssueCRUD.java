package test;

import api_flow.IssueFlow;
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

public class JiraIssueCRUD implements RequestCapability {
    public static void main(String[] args) {
        // API Info
        String baseUri = "https://rest-assured-tutorial.atlassian.net";
        String projectKey = "RAT";

        // Request
        RequestSpecification request = RequestBuilder.getRequest(baseUri);

        IssueFlow issueFlow = new IssueFlow(request,baseUri,projectKey,"task");
        issueFlow.createIssue();
        issueFlow.verifyIssueDetails();
        issueFlow.updateIssue("Done");
        issueFlow.verifyIssueDetails();
        issueFlow.deleteIssue();

    }
}
