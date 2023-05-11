package utilities;

import builder.RequestBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.RequestCapability;
import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ProjectInfo implements RequestCapability {
    private final String baseUri;
    private final String projectKey;
    private List<Map<String, String>> issueTypes;
    private Map<String, List<Map<String, String>>> projectInfo;

    public ProjectInfo(String baseUri, String projectKey) {
        this.baseUri = baseUri;
        this.projectKey = projectKey;
        getProjectInfo();
    }

    private void getProjectInfo(){
        String getProjectPath = "/rest/api/3/project/" + projectKey;

        Response response = RequestBuilder.getRequest(baseUri).get(getProjectPath);
        projectInfo = JsonPath.from(response.asString()).get();
    }

    private void getIssueType(){
        issueTypes = projectInfo.get("issueTypes");
    }

    public String getIssueTypeId(String issueTypeStr){
        getIssueType();

        String issueTypeId = null;

        for (Map<String, String> issueType : issueTypes) {
            if (issueType.get("name").equalsIgnoreCase(issueTypeStr)){
                issueTypeId = issueType.get("id");
                break;
            }
        }

        if (issueTypeId == null){
            throw new IllegalArgumentException("[ERR] Could not find the id for " + issueTypeStr);
        }

        return issueTypeId;
    }


}
