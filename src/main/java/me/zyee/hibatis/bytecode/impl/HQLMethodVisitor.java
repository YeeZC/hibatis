package me.zyee.hibatis.bytecode.impl;

import io.airlift.bytecode.ClassDefinition;
import me.zyee.hibatis.dao.DaoMethodInfo;
import me.zyee.hibatis.dao.registry.MapRegistry;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.lang.reflect.Method;

/**
 * @author yee
 * @version 1.0
 * Create by yee on 2020/6/15
 */
public class HQLMethodVisitor extends BaseMethodVisitor {

    public HQLMethodVisitor(ClassDefinition classDefinition, Method method, DaoMethodInfo methodInfo, MapRegistry maps) {
        super(classDefinition, method, methodInfo, maps, Query.class);
    }

    @Override
    protected Method createQueryMethod() {
        return MethodUtils.getAccessibleMethod(Session.class, "createQuery", String.class);
    }
}
