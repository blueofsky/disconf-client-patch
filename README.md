##Disconf项目的功能增强##
## 特点
0. **支持加载不同env/app/version的文件**

## 使用
1. **支持加载不同env/app/version的文件**
```xml
<bean id="configproperties_other_disconf" 
    class="com.baidu.disconf.client.addons.properties.FullQualifyPropertiesFactoryBean">
    <!-- 配置文件所属App -->
    <property name="app" value="msg_platform_ui"/>
    <!-- 配置文件所属env -->
    <property name="env" value="rd"/>
    <!-- 配置文件所属version -->
    <property name="version" value="1_0"/>
    <property name="locations">
        <list>
            <value>msg.properties</value>
        </list>
    </property>
 </bean>
 <bean id="propertyConfigurerForProject1" 
    class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
    <property name="ignoreResourceNotFound" value="true" />
    <property name="ignoreUnresolvablePlaceholders" value="true" />
    <property name="propertiesArray">
       <list>
          <ref bean="configproperties_other_disconf"/>
       </list>
    </property>
 </bean>
```
