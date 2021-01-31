package com.tp.arqui;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TpArquiServer {

	public static void main(String[] args) {
		SpringApplication.run(TpArquiServer.class, args);
	}
	
	@Bean
	public ModelMapper createModel() {
		return new ModelMapper();
	}
	

}
