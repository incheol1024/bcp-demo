# 3. Mybatis 

* **dependency**
 ```xml
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.1</version>
        </dependency>
```

* **DataSource**

* **Mapper interface 설정** 

* **Mapper xml 설정**  
```yaml
mybatis:
  mapper-locations: classpath:sql/mapper/**/*.xml
  type-aliases-package: com.etoos.bcp.**.model
  configuration:
    lazy-loading-enabled: true
    aggressive-lazy-loading: false
    map-underscore-to-camel-case: true
    cache-enabled: true
    default-executor-type: simple
```
