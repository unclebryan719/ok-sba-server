package com.noriental.oksbaserver.arthas;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableAdminServer
@SpringBootApplication
@EnableCaching
public class OkSbaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OkSbaServerApplication.class, args);
    }

}
