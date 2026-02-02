package com.demo.order.app.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@ConfigurationProperties(prefix = "notification")
@Getter
@Setter
@EnableRetry
public class NotificationConfiguration {

    private boolean enabled;
    private Channels channels = new Channels();

    @Getter
    @Setter
    public static class Channels {
        private Channel email;
        private Channel sms;
    }

    @Getter
    @Setter
    public static class Channel {
        private boolean enabled;
        private String endpoint;
    }
}

