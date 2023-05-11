package model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostBody {

    private int userId;
    private int id;
    private String title;
    private String body;

}
