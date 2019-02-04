package com.cloud.cm.config;

import com.cloud.cm.properties.CmJpaProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
@Configuration
@EnableTransactionManagement
@MapperScan({"com.**.mapper"})
public class MybatisConfig implements TransactionManagementConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    @Autowired
    private CmJpaProperties cmJpaProperties;
    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private MybatisProperties mybatisProperties;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return null;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ResourceLoader resourceLoader) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Resource[] resources = getResources(resourceLoader, mybatisProperties.getMapperLocations());
        sqlSessionFactoryBean.setMapperLocations(resources);
        //这个是配置类映射全部的类名直接写成类文件名，不需要，比较难以理解
        //sqlSessionFactoryBean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));

        SqlSessionFactory sqlSessionFactory;
        try {
            sqlSessionFactory = sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            // 打印日志, 否则spring容器会抑制bean创建的异常,只打印warn的不完整的信息,不利于开发调试
            logger.error("SqlSessionFactory创建失败." + e.getMessage(), e);
            throw e;
        }
        return sqlSessionFactory;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", cmJpaProperties.getShowSql());
        properties.setProperty("hibernate.format_sql", cmJpaProperties.getFormatSql());
        properties.setProperty("hibernate.physical_naming_strategy",
                jpaProperties.getHibernate().getNaming().getPhysicalStrategy());
        properties.setProperty("hibernate.jdbc.batch_size","50");
        properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
        entityManagerFactory.setPackagesToScan(new String[]{cmJpaProperties.getEntityPackage()});
        entityManagerFactory.setJpaProperties(properties);
        return entityManagerFactory;
    }

    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager =  new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabasePlatform(cmJpaProperties.getDialect());
        return hibernateJpaVendorAdapter;
    }

    private Resource[] getResources(ResourceLoader resourceLoader, String[] packagePath) throws IOException {
        ResourcePatternResolver resourceResolver = ResourcePatternUtils
                .getResourcePatternResolver(resourceLoader);
        List<Resource> resourceList = new ArrayList<>();
        Resource[] rs;
        for (String path : packagePath) {
            rs = resourceResolver.getResources(path);
            for (Resource it : rs) {
                resourceList.add(it);
            }
        }

        Resource[] resource = new Resource[resourceList.size()];
        return resourceList.toArray(resource);
    }
}
