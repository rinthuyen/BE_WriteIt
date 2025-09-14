package com.writeit.write_it.config;
import java.util.Optional;
import java.util.Properties;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.databind.DeserializationFeature;

@Configuration
@EnableScheduling
@EnableJpaAuditing
public class AppConfig {
    @Bean
    Jackson2ObjectMapperBuilderCustomizer enumAsNullOnUnknown(){
        return builder -> builder.featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("phucni2004@gmail.com");
        mailSender.setPassword("cnfo tnkt tznq xhdg");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> Optional.ofNullable(SecurityContextHolder.getContext()
                        .getAuthentication())
                        .filter(auth -> auth.isAuthenticated())
                        .map(auth -> auth.getName());       
    }
}
