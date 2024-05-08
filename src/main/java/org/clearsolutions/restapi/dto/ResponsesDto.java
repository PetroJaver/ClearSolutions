package org.clearsolutions.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponsesDto<T> {
    private List<T> data;
}
