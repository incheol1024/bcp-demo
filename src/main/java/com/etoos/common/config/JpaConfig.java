package com.etoos.common.config;

//@Configuration
//@Transactional
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = "com.etoos.bcp",
//        entityManagerFactoryRef = "entityManagerFactoryBean",
//        transactionManagerRef = "transactionManager")
//@ConfigurationProperties(prefix="spring.jpa")
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class JpaConfig {
    //
    //    public static final String PACKAGE_SCAN = "com.etoos.bcp";
    //
    //
    //    @Bean("dataSourceDefaultForJpa")
    //    @ConfigurationProperties(prefix = "datasource.postgres.default")
    //    public DataSource dataSourceDefaultForJpa() {
    //        return new HikariDataSource();
    //    }
    //
    //    @Bean("entityManagerFactoryBean")
    //    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("dataSourceDefaultForJpa") DataSource dataSource) {
    //        return builder.dataSource(dataSource).build();
    //    }
    //
    //    @Bean("transactionManager")
    //    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactoryBean") EntityManagerFactory managerFactory) {
    //        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    //        jpaTransactionManager.setEntityManagerFactory(managerFactory);
    //        return jpaTransactionManager;
    //    }

}
