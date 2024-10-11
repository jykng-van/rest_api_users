package com.jykng.thinkon.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("com.jykng.thinkon.usermanagement.*")
@ServletComponentScan
@EntityScan("com.jykng.thinkon.usermanagement.*")
public class UsermanagementApplication {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(UsermanagementApplication.class, args);
	}

	@PostConstruct
	public void initDB(){
		System.out.println("Creating Table: users");
		String sql = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, first_name VARCHAR(255), last_name VARCHAR(255), email VARCHAR(255), phone_number VARCHAR(20))";

		jdbcTemplate.execute(sql);

	}
}
