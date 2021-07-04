package demo.hao;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "demo.hao.dao.ds2",
        entityManagerFactoryRef = "ds2EntityManager",
        transactionManagerRef = "transactionManager")
public class DataSource2Config {
    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Autowired
    JpaProperties jpaProperties;

    @Bean
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.ds2")
    public DataSource dataSource2() {
        return new AtomikosDataSourceBean();
    }

    @Bean(name = "ds2EntityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean ds2EntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource2());
        em.setPackagesToScan("demo.hao.model.ds2");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setPersistenceUnitName("ds2");
        em.setJpaPropertyMap(jpaProperties.getProperties());
        em.afterPropertiesSet();
        return em;
    }
}
