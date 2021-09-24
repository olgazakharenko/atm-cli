package com.takeoff.atmcli;

import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.SyntaxError;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.shell.jline.JLineShellAutoConfiguration;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration(exclude = {JLineShellAutoConfiguration.class})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public Parser parser() {
    return (var1, var2, var3) -> null;
  }
}