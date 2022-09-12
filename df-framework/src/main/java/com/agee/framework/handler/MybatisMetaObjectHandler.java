package com.agee.framework.handler;

import com.agee.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: df
 * @description 自动填充字段数据库字段
 * @author: 没用的阿吉
 * @create: 2022-09-12 10:48
 **/
@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName(BaseEntity.CREATE_TIME, metaObject);
        Object updateTime = getFieldValByName(BaseEntity.UPDATE_TIME, metaObject);
        if (createTime == null){
            setFieldValByName("createTime",new Date(), metaObject);
        }
        if (updateTime == null){
            setFieldValByName("updateTime",new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName(BaseEntity.UPDATE_TIME, metaObject);
        if (updateTime == null) {
            setFieldValByName(BaseEntity.UPDATE_TIME, new Date(), metaObject);
        }
    }

}
