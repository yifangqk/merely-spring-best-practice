package com.project.telegram.application;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationInfo {

    private String healthId;
    private String version;

}
