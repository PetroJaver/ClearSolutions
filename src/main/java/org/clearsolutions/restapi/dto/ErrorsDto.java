package org.clearsolutions.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorsDto {
    private Integer status;
    private List<Error> errors;
}
