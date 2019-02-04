package com.cloud.cm.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据源JPA配置信息
 */
@ConfigurationProperties(prefix = "spring.jpa")
@Component
public class CmJpaProperties {
    private String dialect;
    private String showSql;
    private String formatSql;
    private String entityPackage;
    private String hibernateEjbInterceptor;

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getShowSql() {
        return showSql;
    }

    public void setShowSql(String showSql) {
        this.showSql = showSql;
    }
    
    public String getFormatSql() {
		return formatSql;
	}

	public void setFormatSql(String formatSql) {
		this.formatSql = formatSql;
	}

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getHibernateEjbInterceptor() {
        return hibernateEjbInterceptor;
    }

    public void setHibernateEjbInterceptor(String hibernateEjbInterceptor) {
        this.hibernateEjbInterceptor = hibernateEjbInterceptor;
    }
}
