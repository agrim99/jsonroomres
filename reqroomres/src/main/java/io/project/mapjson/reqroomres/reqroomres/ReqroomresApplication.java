package io.project.mapjson.reqroomres.reqroomres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ReqroomresApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReqroomresApplication.class, args);
    }

}
