package com.pactera.common.database;

import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA默认数据源配置
 * 
 * @ClassName：JpaRepositoryDefaultConfig
 * @Description：JpaRepositoryDefaultConfig
 * @author pactera
 * @date 2018年12月18日 下午2:03:12
 * @version 1.0.0
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManagerPrimary", basePackages = {
		"com.pactera" })
public class JpaRepositoryDefaultConfig {

	@Autowired
	private JpaProperties jpaProperties;

	@Autowired
	private DynamicDataSource dynamicDataSource;
	@Autowired
	HibernateProperties hibernateProperties;

	@Bean(name = "entityManagerPrimary")
	@Primary
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
		return entityManagerFactory(builder).getObject().createEntityManager();
	}

	@Bean(name = "entityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
		Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(),
				new HibernateSettings());
		return builder.dataSource(dynamicDataSource).properties(properties).packages("com.pactera").build();
	}

	@Bean(name = "transactionManagerPrimary")
	@Primary
	PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(entityManagerFactory(builder).getObject());
	}

}
