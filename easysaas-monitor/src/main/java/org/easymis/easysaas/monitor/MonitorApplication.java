package org.easymis.easysaas.monitor;




import org.easymis.easysaas.monitor.service.EasymisEurekaManagerBanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MonitorApplication {
	protected static final Logger logger = LoggerFactory.getLogger(MonitorApplication.class);

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(MonitorApplication.class);
		springApplication.setBanner(new EasymisEurekaManagerBanner());
		springApplication.run(args);
	}

}
