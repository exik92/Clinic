package com.company.clinic;

import com.company.clinic.service.NotificationService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class AppTestConfig {
    @Bean
    public NotificationService notificationService() {
        return mock(NotificationService.class);
    }
}
