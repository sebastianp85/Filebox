package me.code.filebox;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileboxApplication {

    public static void main(String[] args) {
        // Läs in .env-filen
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        // Sätt miljövariabler
        System.setProperty("DB_CONNECTION", dotenv.get("DB_CONNECTION"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        SpringApplication.run(FileboxApplication.class, args);
    }

}
