package com.warehouseforgoods.warehouseforgoodsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WarehouseForGoodsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseForGoodsBackendApplication.class, args);
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