package com.project.telegram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralResponse {

    private String status;
    private String message;

    public static GeneralResponse success(String message) {
        return new GeneralResponse("SUCCESS", message);
    }

    public static GeneralResponse fromError(String status, String message) {
        return new GeneralResponse(status, message);
    }

}
