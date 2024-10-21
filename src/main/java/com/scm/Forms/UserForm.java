package com.scm.Forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    @NotBlank(message = "Username cannot cannot be blank")
    @Size(min = 3,message = "Need at least 3 characters")
    private String name;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid Email Address")
    private String email;
    @NotBlank(message = "Password is required ")
    @Size(min = 6,max = 12, message = "Min 6 characters is required")
    private String password;
    @NotBlank(message = "Contact number is required")
    @Size(min = 10, max = 12,message = "Invalid phone number")
    private String contactNumber;
    @NotBlank(message = "Please tell us something about yourself")
    private String about;
}
