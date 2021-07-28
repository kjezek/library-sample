package au.kjezek.library;

import au.kjezek.library.config.LibraryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * This is a spring boot main class.
 */
@SpringBootApplication
@Import({LibraryConfig.class, SwaggerConfig.class})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
