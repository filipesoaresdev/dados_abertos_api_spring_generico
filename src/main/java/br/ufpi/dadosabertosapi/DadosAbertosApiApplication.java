package br.ufpi.dadosabertosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.ufpi.dadosabertosapi.vo.CKANProperties;
import br.ufpi.dadosabertosapi.vo.SecurityProperties;

@SpringBootApplication
@EnableConfigurationProperties({SecurityProperties.class,CKANProperties.class})
public class DadosAbertosApiApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(DadosAbertosApiApplication.class, args);
	}
	
//	@Bean("threadPoolTaskExecutor")
//	public TaskExecutor getAsyncExecutor() {
//		
//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		
//		executor.setCorePoolSize(20);
//		executor.setMaxPoolSize(1000);
//		executor.setWaitForTasksToCompleteOnShutdown(true);
//		executor.setThreadNamePrefix("Async-");
//		return executor;
//		
//	}

}
