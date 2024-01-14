package com.algaworks.algafood;

import com.algaworks.algafood.core.io.Base64pProtocolResolver;
import com.algaworks.algafood.infraestructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.TimeZone;

@EnableWebMvc
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		final SpringApplication springApplication
				= new SpringApplication(AlgafoodApiApplication.class);
		springApplication.addListeners(new Base64pProtocolResolver());
		springApplication.run(args);
	}

}
