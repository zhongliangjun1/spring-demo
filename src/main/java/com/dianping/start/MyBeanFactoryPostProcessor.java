package com.dianping.start;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.ManagedList;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-2-27
 * Time: PM9:34
 * To change this template use File | Settings | File Templates.
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory---------begin");

        System.out.println("decide to process myBean");
        BeanDefinition myBeanDefinition = configurableListableBeanFactory.getBeanDefinition("myBean");
        if(myBeanDefinition!=null){
            System.out.println("myBeanDefinition.isLazyInit() : "+myBeanDefinition.isLazyInit());
            System.out.println("myBeanDefinition.isSingleton() : "+myBeanDefinition.isSingleton());
            MutablePropertyValues propertyValues = myBeanDefinition.getPropertyValues();
            PropertyValue propertyValue = propertyValues.getPropertyValue("sex");
            TypedStringValue value = (TypedStringValue) propertyValue.getValue();
            String strValue = value.getValue();
            propertyValue.setConvertedValue("guess ni mei");
            System.out.println("modify myBean sex");
        }

        System.out.println("get info about listBean");
        BeanDefinition listBeanDefinition = configurableListableBeanFactory.getBeanDefinition("listBean");
        if ( listBeanDefinition!=null ) {
            MutablePropertyValues propertyValues = listBeanDefinition.getPropertyValues();
            PropertyValue propertyValue = propertyValues.getPropertyValue("names");
            ManagedList<TypedStringValue> managedList = (ManagedList<TypedStringValue>) propertyValue.getValue();
            System.out.println(managedList.get(0).getValue());
        }

        String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();
        if (beanDefinitionNames!=null && beanDefinitionNames.length>0) {
            for (String beanDefinitionName : beanDefinitionNames) {
                BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);
                if ( beanDefinition.isSingleton() ) {
                    String beanClassName = beanDefinition.getBeanClassName();
                    try {
                        Class c = Class.forName(beanClassName);
                        if ( SingletonBeanUpdateTest.class.isAssignableFrom(c) ) {
                            beanDefinition.setScope("prototype");
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        System.out.println("postProcessBeanFactory---------end");
    }



}
