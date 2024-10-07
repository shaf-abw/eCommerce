package com.abw.ecommerce.ProxyClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProxyClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyClientApplication.class, args);
	}

}
