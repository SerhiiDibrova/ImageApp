package com.image.app.imageapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ImageappApplication {

  public static void main(String[] args) {
    SpringApplication.run(ImageappApplication.class, args);
  }
}
