package com.zikozee.mongdb.tutorial;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "tutorials")
public class Tutorial {

    @Id
    private String id;

    private String title;
    private String description;
    private boolean published;

}
