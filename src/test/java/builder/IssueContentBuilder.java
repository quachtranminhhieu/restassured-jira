package builder;

import lombok.Getter;
import model.IssueFields;

public class IssueContentBuilder {

    @Getter
    private IssueFields issueFields;

    public String build(String projectKey, String taskTypeId, String summary){
        IssueFields.IssueType issueType = new IssueFields.IssueType(taskTypeId);
        IssueFields.Project project = new IssueFields.Project(projectKey);
        IssueFields.Fields fields = new IssueFields.Fields(summary,project,issueType);
        issueFields = new IssueFields(fields);
        return BodyJSONBuilder.getJSONContent(issueFields);
    }
}
