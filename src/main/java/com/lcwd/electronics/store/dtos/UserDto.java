package com.lcwd.electronics.store.dtos;

import com.lcwd.electronics.store.validate.ImageNameValid;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String userId;

    @Size(min=3,max=15,message = "Invalid name!!!")
    private String name;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password required")
    private String password;

    @Size(min = 4,max = 6,message = "Invalid gender")
    private String gender;

    @NotBlank(message = "Writing something about your self")
    private String about;

    @ImageNameValid  //custom validator khud se banaya h
    private String imageName;

}
