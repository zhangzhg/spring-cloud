package com.cloud.cm.interceptor;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;

public class PageObjectWrapperFactory extends DefaultObjectWrapperFactory {
    @Override
    public boolean hasWrapperFor(Object object) {
        if(object instanceof CustomPageImpl) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        if(hasWrapperFor(object)) {
            return  new PageWrapper(metaObject, object);
        } else {
            throw new ReflectionException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
        }
    }
}
