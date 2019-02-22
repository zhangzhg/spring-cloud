package com.cloud;

import com.cloud.security.SecurityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.Optional;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(repositoryBaseClass = SimpleJpaRepository.class)
@EntityScan("com.cloud.domain")
//@EnableRedisHttpSession
public class AuthServerApplication {

	@Bean(name = "auditorAware")
	public AuditorAware<String> auditorAware() {
		return ()-> Optional.ofNullable(SecurityUtils.getCurrentUserUsername());
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

}
