/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.cloud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 动态路由配置表
 */
@Entity
public class SysZuulRoute implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 路由路径
     */
    @Column
    private String path;
    /**
     * 服务名称
     */
    @Column
    private String serviceId;
    /**
     * url代理
     */
    @Column
    private String url;
    /**
     * 转发去掉前缀
     */
    @Column
    private String stripPrefix;
    /**
     * 是否重试
     */
    @Column
    private String retryable;
    /**
     * 是否启用
     */
    @Column
    private String enabled;
    /**
     * 敏感请求头
     */
    @Column
    private String sensitiveheadersList;
    /**
     * 创建时间
     */
    @Column
    private Date createTime;
    /**
     * 更新时间
     */
    @Column
    private Date updateTime;
    /**
     * 删除标识（0-正常,1-删除）
     */
    @Column
    private String delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStripPrefix() {
        return stripPrefix;
    }

    public void setStripPrefix(String stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    public String getRetryable() {
        return retryable;
    }

    public void setRetryable(String retryable) {
        this.retryable = retryable;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getSensitiveheadersList() {
        return sensitiveheadersList;
    }

    public void setSensitiveheadersList(String sensitiveheadersList) {
        this.sensitiveheadersList = sensitiveheadersList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
