package ie.cct.ger_garage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ie.cct.ger_garage.controller")
public class GerGarageApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerGarageApplication.class, args);
    }

}
