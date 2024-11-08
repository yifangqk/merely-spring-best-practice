package com.project.telegram;

import com.project.telegram.application.ApplicationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {
    private static final String CHECK_ID = UUID.randomUUID().toString();

    private final BuildProperties buildProperties;

    @GetMapping("health-check")
    public ApplicationInfo healthCheck() {
        return ApplicationInfo.builder()
                .healthId(CHECK_ID)
                .version(buildProperties.getVersion())
                .build();
    }
}

