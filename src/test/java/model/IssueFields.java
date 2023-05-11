package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class IssueFields {

    @Getter
    private Fields fields;

    @AllArgsConstructor
    @Getter
    public static class Fields{
        private String summary;
        private Project project;
        private IssueType issuetype;
    }

    @AllArgsConstructor
    @Getter
    public static class Project{
        private String key;
    }

    @AllArgsConstructor
    @Getter
    public static class IssueType{
        private String id;
    }
}
