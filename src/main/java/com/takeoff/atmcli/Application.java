package com.takeoff.atmcli;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.shell.jline.PromptProvider;

/**
 * {@code Application} represents the entry point for the Atm application.
 * <p/>
 *
 * @author Olga Zakharenko
 */
@SpringBootApplication
@EnableScheduling
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public PromptProvider myPromptProvider() {
    return () -> new AttributedString(">",
        AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
  }
}