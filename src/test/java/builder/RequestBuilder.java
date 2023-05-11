package builder;

import io.restassured.specification.RequestSpecification;
import model.RequestCapability;
import utilities.AuthenticationHelper;

import static io.restassured.RestAssured.given;

public class RequestBuilder implements RequestCapability{

    public static RequestSpecification getRequest(String baseUri){
        String email = "nhieuautomationfc@gmail.com";
        String apiToken = "ATATT3xFfGF0ZToHvm7C9IRLp4Yq5zDKxfw7oJu6QazYYIPV-3CpbuoqbkPzqSDJtgAQOqZeL6YtfUh3pRuUBxCTzGOpPcgodqlGPf6a_lp9-PRrcYVEulsKGDIQfvxewbNG-udDFlxtvzeZGlXTacsLzmO01v_WNwJZop5nSODsyPPIsHILnO8=77D4E7C7";
        String encodedCredStr = AuthenticationHelper.getEncodedCred(email,apiToken);

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(getAuthenticatedHeader.apply(encodedCredStr));

        return request;
    }
}
