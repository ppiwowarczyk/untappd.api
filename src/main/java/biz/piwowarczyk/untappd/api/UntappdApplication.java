package biz.piwowarczyk.untappd.api;

import biz.piwowarczyk.untappd.api.scraper.UntappdConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableConfigurationProperties(UntappdConfig.class)
public class UntappdApplication {

	public static void main(String[] args) {
		SpringApplication.run(UntappdApplication.class, args);
	}

}
