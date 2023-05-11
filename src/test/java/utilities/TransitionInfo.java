package utilities;

import builder.RequestBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.RequestCapability;

import java.util.List;
import java.util.Map;

public class TransitionInfo implements RequestCapability {

    public TransitionInfo(String baseUri, String issueKey) {
        this.baseUri = baseUri;
        this.issueKey = issueKey;
        getTransitionInfo();
    }

    private String issueKey;

    private String baseUri;

    private Map<String, List<Map<String, String>>> transitionInfo;

    private List<Map<String, String>> transitionTypes;

    public String getTransitionId(String transitionTypeStr){
        getTransitionType();

        String transitionId = null;

        for (Map<String, String> transitionType : transitionTypes) {
            if (transitionType.get("name").equalsIgnoreCase(transitionTypeStr)){
                transitionId = transitionType.get("id");
                break;
            }
        }

        if (transitionId == null){
            throw new IllegalArgumentException("[ERR] Could not find the id for " + transitionTypeStr);
        }

        return transitionId;
    }

    private void getTransitionType(){
        transitionTypes = transitionInfo.get("transitions");
    }

    private void getTransitionInfo(){
        String getTransitionPath = "/rest/api/3/issue/" + issueKey + "/transitions";

        RequestSpecification request = RequestBuilder.getRequest(baseUri);

        Response response = request.get(getTransitionPath);
        transitionInfo = JsonPath.from(response.body().asString()).get();
    }
}
