package org.clearsolutions.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
    Integer status;
    Error error;
}
