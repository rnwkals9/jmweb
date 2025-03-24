package edu.du.sbtest0313;

import edu.du.sbtest0313.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "edu.du.sbtest0313")

public class Sbtest0313Application {
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Sbtest0313Application.class, args);
    }
}

