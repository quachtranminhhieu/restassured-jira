package builder;

import lombok.Getter;
import model.TransitionFields;

public class TransitionContentBuilder {

    @Getter
    private TransitionFields transitionFields;

    public String build(String transitionId){
        TransitionFields.Transition transition = new TransitionFields.Transition(transitionId);
        transitionFields = new TransitionFields(transition);
        return BodyJSONBuilder.getJSONContent(transitionFields);
    }
}
