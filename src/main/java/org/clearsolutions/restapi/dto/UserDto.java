package org.clearsolutions.restapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.clearsolutions.restapi.validation.ValidBirthDay;
import org.clearsolutions.restapi.validation.group.UserBasicInfo;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;

    @Email(groups = {UserBasicInfo.class, Default.class})
    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @ValidBirthDay(groups = {UserBasicInfo.class, Default.class})
    @NotNull
    private LocalDate birthDate;

    private String address;

    @Pattern(regexp = "^\\+?\\d{1,3}?[-\\s]?\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d{4}$",
            groups = {UserBasicInfo.class, Default.class})
    private String phoneNumber;
}
