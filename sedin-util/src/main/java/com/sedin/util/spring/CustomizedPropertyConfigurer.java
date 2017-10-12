package com.sedin.util.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {
  
  private static Map<String, Object> ctxPropertiesMap;

  @Override
  protected void processProperties(ConfigurableListableBeanFactory beanFactory,  
          Properties props)throws BeansException {  

      super.processProperties(beanFactory, props);  
      //load properties to ctxPropertiesMap  
      ctxPropertiesMap = new HashMap<String, Object>();  
      for (Object key : props.keySet()) {  
          String keyStr = key.toString();  
          String value = props.getProperty(keyStr);  
          ctxPropertiesMap.put(keyStr, value);  
      }  
  }  

  //static method for accessing context properties  
  public static String getContextProperty(String name) {  
      return   ctxPropertiesMap.get(name)== null ? "":(String)ctxPropertiesMap.get(name);  
  }


}  
