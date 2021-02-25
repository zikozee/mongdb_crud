package com.zikozee.mongdb.tutorial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorialDTO implements Serializable {
    private static final long serialVersionUID = 42L;

    private String id;
    private String title;
    private String description;
    private boolean published;
}
