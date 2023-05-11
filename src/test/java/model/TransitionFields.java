package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TransitionFields {

    @Getter
    private Transition transition;

    @AllArgsConstructor
    @Getter
    public static class Transition{
        private String id;
    }
}
