package demo.hao;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "demo.hao.dao.ds1",
        entityManagerFactoryRef = "ds1EntityManager",
        transactionManagerRef = "transactionManager")
public class DataSource1Config {

    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Autowired
    JpaProperties jpaProperties;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.ds1")
    public DataSource dataSource1() {
        return new AtomikosDataSourceBean();
    }

    @Primary
    @Bean(name = "ds1EntityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean ds1EntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource1());
        em.setPackagesToScan("demo.hao.model.ds1");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setPersistenceUnitName("ds1");
        em.setJpaPropertyMap(jpaProperties.getProperties());

        return em;
    }
}
