package test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.RequestCapability;
import org.apache.commons.codec.binary.Base64;

import static io.restassured.RestAssured.given;

public class JiraIssueTypes_01 implements RequestCapability {
    public static void main(String[] args) {
        String baseUri = "https://rest-assured-tutorial.atlassian.net";
        String basePath = "/rest/api/3/project/RAT";

        String email = "nhieuautomationfc@gmail.com";
        String apiToken = "ATATT3xFfGF0ZToHvm7C9IRLp4Yq5zDKxfw7oJu6QazYYIPV-3CpbuoqbkPzqSDJtgAQOqZeL6YtfUh3pRuUBxCTzGOpPcgodqlGPf6a_lp9-PRrcYVEulsKGDIQfvxewbNG-udDFlxtvzeZGlXTacsLzmO01v_WNwJZop5nSODsyPPIsHILnO8=77D4E7C7";
        String credential = email + ":" + apiToken;
        byte[] encodedCred = Base64.encodeBase64(credential.getBytes());
        String encodedCredStr = new String(encodedCred);

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header("Authorization", "Basic " + encodedCredStr);

        Response response = request.get(basePath);
        response.prettyPrint();
    }
}
