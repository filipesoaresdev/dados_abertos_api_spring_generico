package br.ufpi.dadosabertosapi.database.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.db.DBAppender;
import ch.qos.logback.core.db.DataSourceConnectionSource;
import ch.qos.logback.core.db.DriverManagerConnectionSource;

@Configuration
@PropertySource({"classpath:persistence-multiple-db.properties"})
@EnableJpaRepositories(
		basePackages="br.ufpi.dadosabertosapi.database.local.dao",
		entityManagerFactoryRef="localContext",
		transactionManagerRef="localTransactionManager"
		)
public class LocalDBConfig { 
	@Autowired
    private Environment env;
 
    @Bean(name="localContext")
    public LocalContainerEntityManagerFactoryBean localEntityManager() {
        LocalContainerEntityManagerFactoryBean em
          = new LocalContainerEntityManagerFactoryBean();
        
        em.setDataSource(localDataSource());
        em.setPackagesToScan(
         new String[] { "br.ufpi.dadosabertosapi.database.local.model" });
         //new String[] { "br.ufpi.dadosabertosapi.database.local.model" });
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
          "none");
        properties.put("hibernate.dialect",
          env.getProperty("spring.local-datasource.hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        

        
 
        return em;
    }
    
    @Bean
    public PlatformTransactionManager localTransactionManager() {
  
        JpaTransactionManager transactionManager
          = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
        		localEntityManager().getObject());
        

        return transactionManager;
    }
		@Bean
		@ConfigurationProperties(prefix="spring.local-datasource")
		public DataSource localDataSource() {
			
			return DataSourceBuilder.create().build();
			
		}
		
		@Bean
		
		public DBAppender dbAppender(@Qualifier("localDataSource") DataSource dataSource){
		    DBAppender dbAppender = new DBAppender();
		    DataSourceConnectionSource connectionSource = new DataSourceConnectionSource();
		    connectionSource.setDataSource(dataSource);
		    connectionSource.start();
		    dbAppender.setConnectionSource(connectionSource);
		    dbAppender.start();

		    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		    Logger logger = loggerContext.getLogger("ROOT");
		    logger.addAppender(dbAppender);

		    return dbAppender;
		}
	
}
