package com.vroozi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SolrDataReindexApplication {

  public static void main(String[] args) {
    SpringApplication.run(SolrDataReindexApplication.class, args);
  }
}
