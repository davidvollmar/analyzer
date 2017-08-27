package nl.vollmar.xmlanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "nl.vollmar.xmlanalyzer" })
public class Application {

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }
}
