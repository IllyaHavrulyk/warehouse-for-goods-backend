package com.warehouseforgoods.warehouseforgoodsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableScheduling
@SpringBootApplication
public class WarehouseForGoodsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseForGoodsBackendApplication.class, args);
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://fierce-gorge-15492.herokuapp.com").allowCredentials(true).allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*").exposedHeaders("Access-Control-Allow-Origin", "authorization", "Authorization");
            }
        };
    }
}

/*spring.h2.console.enabled=true
        spring.datasource.url=jdbc:h2:mem:testdb
        spring.datasource.driverClassName=org.h2.Driver
        spring.datasource.username=sa
        spring.datasource.password=
        spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        */