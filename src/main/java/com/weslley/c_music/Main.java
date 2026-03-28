package com.weslley.c_music;


import com.weslley.c_music.repository.MusicaRepository;
import com.weslley.c_music.service.MusicaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	CommandLineRunner teste(MusicaService musicaService, MusicaRepository musicaRepository) {
		return args -> {
			System.out.println("Rodando!!!");
		};
	}
}