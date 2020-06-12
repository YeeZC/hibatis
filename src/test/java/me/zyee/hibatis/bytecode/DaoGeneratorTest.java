package me.zyee.hibatis.bytecode;

import me.zyee.hibatis.config.HiBatisConfig;
import me.zyee.hibatis.template.HiBatisTemplate;
import me.zyee.hibatis.template.factory.TemplateFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

/**
 * @author yee
 * @version 1.0
 * Created by yee on 2020/6/11
 */
public class DaoGeneratorTest {

    @Test
    public void generate() throws Exception {

        final Configuration configuration = getConfiguration();
        final HiBatisConfig hiBatisConfig = new HiBatisConfig();
        hiBatisConfig.setConfiguration(configuration);
        hiBatisConfig.setDaoXmlScanPath("");
        final TemplateFactory templateFactory = hiBatisConfig.buildTemplateFactory();
        final HiBatisTemplate template = templateFactory.createTemplate();
        final Optional<TestEntity> hello = template.get(TestEntity.class, "hello");
        System.out.println(hello.get());
    }

    private Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        File confFile = new File("hibernate.cfg.xml");
        if (confFile.exists()) {
            configuration.configure(confFile);
        } else {
            configuration.configure("hibernate.cfg.xml");
        }

        configuration.addAnnotatedClass(TestEntity.class);
        return configuration;
    }

}