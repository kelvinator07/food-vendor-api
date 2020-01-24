package com.byteworks;

import com.byteworks.model.Meal;
import com.byteworks.model.User;
import com.byteworks.repository.MealRepository;
import com.byteworks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class ByteworksFoodVendorApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ByteworksFoodVendorApiApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
    private MealRepository mealRepository;

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new User("Kelvinator","Kelvin", "Geek", "kel1@gmail.com", "$2a$12$LtbYtEzOBdnsiD/E9Wtj2Oz3852iKtnYWDpXNgKsWBpfXshoURT1K", new Date(), "08091714477"));
        mealRepository.save(new Meal("Rice", 500.0));
        mealRepository.save(new Meal("Beans", 500.0));
        mealRepository.save(new Meal("Chicken", 900.0));
        mealRepository.save(new Meal("Yam", 300.0));
        mealRepository.save(new Meal("Amala", 400.0));
        mealRepository.save(new Meal("Salad", 300.0));

    }
}
