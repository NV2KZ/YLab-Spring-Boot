package com.edu.ulab.app.web.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BookRequest {
    private Long id;
    private String title;
    private String author;
    private long pageCount;
}
