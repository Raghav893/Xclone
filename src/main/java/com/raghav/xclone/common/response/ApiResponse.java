package com.raghav.xclone.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private List<String> errors;

}
