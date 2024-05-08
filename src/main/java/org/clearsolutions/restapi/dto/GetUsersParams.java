package org.clearsolutions.restapi.dto;

import lombok.Data;
import org.clearsolutions.restapi.validation.ValidBirthDateRange;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@ValidBirthDateRange
public class GetUsersParams {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDateTo;
}
