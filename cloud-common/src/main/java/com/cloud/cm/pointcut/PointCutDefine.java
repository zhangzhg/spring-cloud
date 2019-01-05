package com.cloud.cm.pointcut;

import org.aspectj.lang.annotation.Pointcut;

/**
 * AOP切面点定义
 * @author linliangkun
 * @date 15/8/21
 */
public class PointCutDefine {

    public static final String MYBATIS_MAPPER_EXECUTION = "execution(* com..mapper..find*(..)) || execution(* com..mapper..list*(..))";
    /**
     * mybatis-mapper查询分页
     */
    @Pointcut(MYBATIS_MAPPER_EXECUTION)
    public void aroundMybatisPaginationQuery() {
    }
}
