package be.intecbrussel.blogcentral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@SpringBootApplication
public class BlogCentralApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogCentralApplication.class, args);
    }

}
