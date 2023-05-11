package utilities;

import builder.RequestBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class IssueInfo {
    private String baseUri;

    public IssueInfo(String baseUri) {
        this.baseUri = baseUri;
    }

    public Map<String, String> getIssueInfo(String createdIssueKey){
        String getIssuePath = "/rest/api/3/issue/" + createdIssueKey;

        Response response = RequestBuilder.getRequest(baseUri).get(getIssuePath);

        Map<String, Object> fields = JsonPath.from(response.body().asString()).get("fields");
        Map<String, Object> status = (Map<String, Object>) fields.get("status");
        String actualSummary = fields.get("summary").toString();
        Map<String, String> statusCategory = (Map<String, String>) status.get("statusCategory");
        String actualStatus = statusCategory.get("name");

        Map<String, String> issueInfo = new HashMap<>();
        issueInfo.put("summary", actualSummary);
        issueInfo.put("status", actualStatus);

        return issueInfo;
    }

}
