package me.zyee.hibatis.dao.registry;

import me.zyee.hibatis.bytecode.DaoGenerator;
import me.zyee.hibatis.dao.DaoInfo;
import me.zyee.hibatis.exception.ByteCodeGenerateException;
import me.zyee.hibatis.exception.HibatisNotFountException;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yee
 * Created by yee on 2020/6/12
 **/
public class DaoRegistry {
    private final Map<Class<?>, LazyGet.SupplierLazyGet<Class<?>>> container = new ConcurrentHashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoRegistry.class);

    public void addDao(DaoInfo dao) {
        final Class<?> inf = dao.getId();
        container.put(inf, LazyGet.of(() -> {
            try {
                return DaoGenerator.generate(dao);
            } catch (Exception e) {
                LOGGER.error("generate error", e);
                throw new RuntimeException(e);
            }
        }));
    }

    public <T> T getDao(Class<T> daoClass, Session session) throws ByteCodeGenerateException {
        if (container.containsKey(daoClass)) {
            try {
                final Class<?> cls = container.get(daoClass).get();
                return daoClass.cast(MethodUtils.invokeStaticMethod(cls, "newInstance", session));
            } catch (Exception e) {
                throw new ByteCodeGenerateException(e);
            }
        }
        throw new ByteCodeGenerateException(new HibatisNotFountException("Instanse for Dao " + daoClass + " not found"));
    }

}
