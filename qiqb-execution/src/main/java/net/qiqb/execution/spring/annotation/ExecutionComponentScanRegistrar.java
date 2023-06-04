package net.qiqb.execution.spring.annotation;

import net.qiqb.execution.spring.beans.CommandAnnotationPostProcessor;
import net.qiqb.execution.spring.beans.DomainAnnotationPostProcessor;
import net.qiqb.execution.spring.ExecutionSpringInitializer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

public class ExecutionComponentScanRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Set<String> packagesToScan = getPackagesToScan(importingClassMetadata);
        registerCommandAnnotationPostProcessor(packagesToScan, registry);
        registerDomainAnnotationPostProcessor(packagesToScan, registry);
        ExecutionSpringInitializer.initialize(registry);


    }

    private void registerCommandAnnotationPostProcessor(Set<String> packagesToScan, BeanDefinitionRegistry registry) {

        BeanDefinitionBuilder builder = rootBeanDefinition(CommandAnnotationPostProcessor.class);
        builder.addConstructorArgValue(packagesToScan);
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);

    }

    private void registerDomainAnnotationPostProcessor(Set<String> packagesToScan, BeanDefinitionRegistry registry) {

        BeanDefinitionBuilder builder = rootBeanDefinition(DomainAnnotationPostProcessor.class);
        builder.addConstructorArgValue(packagesToScan);
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);

    }

    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {

        Set<String> packagesToScan = getPackagesToScan0(metadata, ExecutionComponentScan.class, "basePackages", "basePackageClasses");

        if (packagesToScan.isEmpty()) {
            return Collections.singleton(ClassUtils.getPackageName(metadata.getClassName()));
        }
        return packagesToScan;
    }

    private Set<String> getPackagesToScan0(AnnotationMetadata metadata, Class annotationClass, String basePackagesName, String basePackageClassesName) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(annotationClass.getName()));
        if (attributes == null) {
            return Collections.emptySet();
        }

        Set<String> packagesToScan = new LinkedHashSet<>();
        // basePackages
        String[] basePackages = attributes.getStringArray(basePackagesName);
        packagesToScan.addAll(Arrays.asList(basePackages));

        Class<?>[] basePackageClasses = attributes.getClassArray(basePackageClassesName);
        for (Class<?> basePackageClass : basePackageClasses) {
            packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
        }
        // value
        if (attributes.containsKey("value")) {
            String[] value = attributes.getStringArray("value");
            packagesToScan.addAll(Arrays.asList(value));
        }
        return packagesToScan;
    }

}