package org.clearsolutions.restapi.configuration.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

interface SliceMapping<T> {
    @JsonProperty("data")
    List<T> getContent(); // rename property
}
