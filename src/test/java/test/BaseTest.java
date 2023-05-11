package test;

import builder.RequestBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.File;

public class BaseTest {
    protected String baseUri;
    protected String projectKey;
    protected RequestSpecification request;

    @BeforeSuite
    public void beforeSuite(){
        baseUri = "https://rest-assured-tutorial.atlassian.net";
        projectKey = "RAT";
        deleteAllFilesInFolderName("allure-results");
    }

    @BeforeTest
    public void beforeTest(){
        request = RequestBuilder.getRequest(baseUri);
    }

    private void deleteAllFilesInFolderName(String folderName) {
        try {
            String pathFolder = System.getProperty("user.dir") + "/" + folderName;
            File file = new File(pathFolder);
            File[] listOfFiles = file.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    new File(listOfFiles[i].toString()).delete();
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
