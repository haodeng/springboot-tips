Add 2 datasrouces properties

    # Mysql datasource for post
    spring.datasource1.jdbcUrl=jdbc:mysql://${MYSQL_HOST:localhost}:3306/test
    spring.datasource1.username=test
    spring.datasource1.password=test
    hibernate.datasource1.dialect=org.hibernate.dialect.MySQL5Dialect
    
    # H2 datasource for user
    spring.datasource2.jdbcUrl=jdbc:h2:mem:test
    spring.datasource2.username=sa
    spring.datasource2.password=
    hibernate.datasource2.dialect=org.hibernate.dialect.H2Dialect
    
Config datasource1 and datasource2

    @Configuration
    @EnableJpaRepositories(basePackages = "demo.hao.dao.ds1",
            entityManagerFactoryRef = "ds1EntityManager",
            transactionManagerRef = "ds1TransactionManager")
    public class DataSource1Config
    ...
    
    @Bean(name = "datasource1")
        @ConfigurationProperties("spring.datasource1")
        @Primary
        public DataSource datasource1(){
            return DataSourceBuilder.create().build();
        }
        
        ...

Load test data by DataLoader

Test:

     # get from h2 db
     curl http://localhost:8080/users
     
     # get from mysql db
     curl http://localhost:8080/posts
