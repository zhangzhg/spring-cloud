package com.cloud.cm.domain;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
* 领域模型基类
*/
@MappedSuperclass
public class BaseModel implements Serializable {

	private static final long serialVersionUID = 2458742039986328707L;

    /** 编号、ID字段，采用hibernate的uuid自动生成标示符策略 */
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id",columnDefinition = "VARCHAR(32)")
    @Id
    protected String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BaseModel baseModel = (BaseModel) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(id, baseModel.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
