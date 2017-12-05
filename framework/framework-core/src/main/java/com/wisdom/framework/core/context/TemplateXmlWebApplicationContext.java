package com.wisdom.framework.core.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.tinygroup.template.ResourceLoader;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.StringResourceLoader;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hyberbin on 2017/9/16.
 */
public class TemplateXmlWebApplicationContext extends XmlWebApplicationContext {

    private static final Logger log = LoggerFactory.getLogger(TemplateXmlWebApplicationContext.class);
    private static final ResourceLoader resourceLoader = new StringResourceLoader();
    private static final TemplateEngine engine = new TemplateEngineDefault();

    private static final Map propertiesMap = new HashMap();

    static {
        engine.addResourceLoader(resourceLoader);
        engine.setSafeVariable(true);
    }

    /**
     * Loads the bean definitions via an XmlBeanDefinitionReader.
     *
     * @param beanFactory
     * @see XmlBeanDefinitionReader
     * @see #initBeanDefinitionReader
     * @see #loadBeanDefinitions
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
        // Create a new XmlBeanDefinitionReader for the given BeanFactory.
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory) {
            @Override
            protected Document doLoadDocument(InputSource inputSource, Resource resource) throws Exception {
                if (!"applicationContext.xml".equals(resource.getFilename()) && propertiesMap.isEmpty()) {
                    SpringContextUtil springContextUtil=((DefaultListableBeanFactory) this.getBeanFactory()).getBean(SpringContextUtil.class);
                    propertiesMap.putAll(springContextUtil.getSystemProperties());
                }
                Template template = resourceLoader.createTemplate(new String(FileCopyUtils.copyToByteArray(resource.getURL().openStream()),"UTF-8"));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                TemplateContextDefault templateContext = new TemplateContextDefault(propertiesMap);
                engine.renderTemplate(template, templateContext, bos);
                log.info("render {},result:\n{}",resource.getFilename(),bos);
                final byte[] bytes = bos.toByteArray();
                return super.doLoadDocument(new InputSource(new ByteArrayInputStream(bytes)), new ByteArrayResource(bytes));
            }
        };

        // Configure the bean definition reader with this context's
        // resource loading environment.
        beanDefinitionReader.setEnvironment(getEnvironment());
        beanDefinitionReader.setResourceLoader(this);
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));

        // Allow a subclass to provide custom initialization of the reader,
        // then proceed with actually loading the bean definitions.
        initBeanDefinitionReader(beanDefinitionReader);
        loadBeanDefinitions(beanDefinitionReader);
    }
}
