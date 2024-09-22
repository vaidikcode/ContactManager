package com.scm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class SocialLink {
    private long id;
    private String link;
    private String title;

    //Social links can be many mapped to one user-for mapping see class name first and then the object of class you want to map it with
    @ManyToOne
    private Contact contact;
}
