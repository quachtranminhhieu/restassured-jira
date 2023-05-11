package test;

import api_flow.IssueFlow;
import builder.RequestBuilder;
import io.restassured.specification.RequestSpecification;
import model.RequestCapability;
import org.testng.annotations.Test;

public class JiraIssueCRUD_TestNG extends BaseTest {

    @Test
    public void TestCase() {
        IssueFlow issueFlow = new IssueFlow(request,baseUri,projectKey,"task");
        issueFlow.createIssue();
        issueFlow.verifyIssueDetails();
        issueFlow.updateIssue("Done");
        issueFlow.verifyIssueDetails();
        issueFlow.deleteIssue();
    }
}
