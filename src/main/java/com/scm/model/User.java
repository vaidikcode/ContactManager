package com.scm.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.util.Lazy;

import java.util.ArrayList;
import java.util.List;

//it tells jpa that the class used be treated as table in database
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {
    //info regarding user
    @Id
    private String userId;
    @Column(name = "user_name", nullable = false)
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(length = 10000)
    private String about;
    @Column(length = 10000)
    private String profilePic;
    private String phoneNumber;
    //verifications and login signup status
    private boolean enabled = false;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;
    //Login with SELF,GOOGLE,GITHUB
    private Provider provider = Provider.SELF;
    private String providerUserId;
    //mapping-Cascade all means if we update delete user all contacts will be updated or deleted-fetch lazy means to query will only run when we fetch contacts
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();
}
